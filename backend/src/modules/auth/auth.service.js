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

    let user = await PatientProfile.findOne({ phone });

    if (!user) {
        await PatientProfile.create({phone, otp, otpExpiry});
    } else {
        user.otp = otp;
        user.otpExpiry = otpExpiry;
        await user.save();
    }

    console.log(`OTP for ${phone} is ${otp}`);
    return true;
};


exports.verifyOtp = async ({ phone, otp }) => {
    if (otp !== '123456') {
        throw new Error('Invalid OTP');
    }
    const [patient, doctor] = await Promise.all([
        PatientProfile.findOne({ phoneNumber: phone }),
        Doctor.findOne({ phoneNumber: phone }),
    ]);

    if (!patient && !doctor) {
        throw new Error('User not found');
    }

    let token;
    let userId;
    let role;
    if (patient) {
        userId = patient._id;
        role = patient.role
         token = jwt.sign(
            { userId:  patient._id, role: patient.role },
            process.env.JWT_SECRET,
            { expiresIn: process.env.JWT_EXPIRES_IN }
        );
    }else {
        userId = patient._id;
        role = patient.role
        token = jwt.sign(
            { userId:  doctor._id, role: doctor.role },
            process.env.JWT_SECRET,
            { expiresIn: process.env.JWT_EXPIRES_IN })
    }


    return {
        token,
        user: {
            id: userId,
            role: role,
        },
    };
};

