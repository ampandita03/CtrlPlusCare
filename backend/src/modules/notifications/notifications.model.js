const mongoose = require('mongoose');

const notificationSchema = new mongoose.Schema(
    {
        userId: {
            type: mongoose.Schema.Types.ObjectId,
            required: true,
            index: true,
        },

        title: {
            type: String,
            required: true,
        },

        message: {
            type: String,
            required: true,
        },

        type: {
            type: String,
            enum: ['APPOINTMENT', 'REMINDER', 'EMERGENCY', 'SYSTEM'],
            default: 'SYSTEM',
        },

        isRead: {
            type: Boolean,
            default: false,
        },

        meta: {
            type: Object, // optional extra data
        },
    },
    { timestamps: true }
);

module.exports = mongoose.model('Notification', notificationSchema);
