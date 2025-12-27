const jwt = require('jsonwebtoken');
const PatientProfile = require('../patient/patient.model');
const Doctor = require('../doctors/doctors.model');
const generateOTP = () => '123456';

exports.sendOtp = async (phone) => {
    const otp = generateOTP();
    const otpExpiry = new Date(Date.now() + 5 * 60 * 1000);

    const normalizedPhone = String(phone).trim();

    let user = await PatientProfile.findOne({
        phoneNumber: normalizedPhone,
    });

    if (!user) {
        await PatientProfile.create({
            phoneNumber: normalizedPhone,
            otp,
            otpExpiry,
        });
    } else {
        user.otp = otp;
        user.otpExpiry = otpExpiry;
        await user.save();
    }

    console.log(`OTP for ${normalizedPhone} is ${otp}`);
    return true;
};



exports.verifyOtp = async ({ phone, otp }) => {
    if (otp !== '123456') {
        throw new Error('Invalid OTP');
    }

    const normalizedPhone = String(phone).trim();

    const [patient, doctor] = await Promise.all([
        PatientProfile.findOne({ phoneNumber: normalizedPhone }),
        Doctor.findOne({ phoneNumber: normalizedPhone }),
    ]);

    if (!patient && !doctor) {
        throw new Error('User not found');
    }

    let profile;
    let role;

    if (patient) {
        profile = patient;
        role = patient.role || 'PATIENT';
    } else {
        profile = doctor;
        role = doctor.role || 'DOCTOR';
    }

    const token = jwt.sign(
        {
            profileId: profile._id,
            role,
            phone: normalizedPhone,
        },
        process.env.JWT_SECRET,
        { expiresIn: process.env.JWT_EXPIRES_IN }
    );

    return {
        token,
        user: {
            id: profile._id,
            role,
            phone: normalizedPhone,
        },
    };
};