const jwt = require('jsonwebtoken');
const User = require('./auth.model');
const PatientProfile = require('../patient/patient.model');
const Doctor = require('../doctors/doctors.model');
const generateOTP = () => '123456';

const generateToken = (user) => {
    return jwt.sign(
        { userId: user._id, role: user.role },
        process.env.JWT_SECRET,
        { expiresIn: process.env.JWT_EXPIRES_IN }
    );
};

exports.sendOtp = async (phone) => {
    const otp = generateOTP();
    const otpExpiry = new Date(Date.now() + 5 * 60 * 1000);

    let user = await User.findOne({ phone });

    if (!user) {
        await User.create({phone, otp, otpExpiry});
    } else {
        user.otp = otp;
        user.otpExpiry = otpExpiry;
        await user.save();
    }

    console.log(`OTP for ${phone} is ${otp}`);
    return true;
};


exports.verifyOtp = async ({ phone, otp ,fcmToken }) => {
    if (otp !== '123456') {
        throw new Error('Invalid OTP');
    }

    let user = await User.findOne({ phone });

    if (!user) {
        user = await User.create({
            phone,
            role: 'PATIENT',
            fcmToken
        });
    }

    let roleUpdated = false;

    const patient = await PatientProfile.findOneAndUpdate(
        { phoneNumber: phone, userId: { $exists: false } },
        { userId: user._id }
    );

    if (patient) {
        user.role = 'PATIENT';
        roleUpdated = true;
    }

    const doctor = await Doctor.findOneAndUpdate(
        { phoneNumber: phone, userId: { $exists: false } },
        { userId: user._id }
    );

    if (doctor) {
        user.role = 'DOCTOR';
        roleUpdated = true;
    }

    if (roleUpdated) {
        await user.save();
    }

    const token = jwt.sign(
        { userId: user._id, role: user.role },
        process.env.JWT_SECRET,
        { expiresIn: process.env.JWT_EXPIRES_IN }
    );

    return {
        token,
        user: {
            id: user._id,
            role: user.role,
        },
    };
};
