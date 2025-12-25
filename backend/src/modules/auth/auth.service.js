const jwt = require('jsonwebtoken');
const User = require('./auth.model');

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

exports.verifyOtp = async ({ phone, otp, fcmToken }) => {
    const user = await User.findOne({ phone });

    if (!user) throw new Error('User not found');
    if (user.otp !== otp) throw new Error('Invalid OTP');
    if (user.otpExpiry < new Date()) throw new Error('OTP expired');

    user.otp = null;
    user.otpExpiry = null;
    if (fcmToken) user.fcmToken = fcmToken;

    await user.save();

    const token = generateToken(user);

    return { user, token };
};
