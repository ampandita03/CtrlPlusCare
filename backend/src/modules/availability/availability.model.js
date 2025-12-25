const mongoose = require('mongoose');

const availabilitySchema = new mongoose.Schema(
    {
        doctorId: {
            type: mongoose.Schema.Types.ObjectId,
            ref: 'Doctor',
            required: true,
            unique: true,
        },

        slots: [
            {
                dayOfWeek: {
                    type: Number, // 0 = Sunday, 6 = Saturday
                    required: true,
                },
                startTime: {
                    type: String, // "09:00"
                    required: true,
                },
                endTime: {
                    type: String, // "17:00"
                    required: true,
                },
                slotDuration: {
                    type: Number, // minutes (e.g. 30)
                    required: true,
                },
            },
        ],
    },
    { timestamps: true }
);

module.exports = mongoose.model('Availability', availabilitySchema);
