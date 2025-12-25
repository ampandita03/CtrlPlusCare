const mongoose = require('mongoose');

const userSchema = new mongoose.Schema(
    {
        phone: {
            type: String,
            required: true,
            unique: true,
        },
        role: {
            type: String,
            enum: ['PATIENT', 'DOCTOR'],
            default: 'PATIENT',
        },
        otp: String,
        otpExpiry: Date,
        fcmToken: String,
    },
    { timestamps: true }
);

module.exports = mongoose.model('User', userSchema);
