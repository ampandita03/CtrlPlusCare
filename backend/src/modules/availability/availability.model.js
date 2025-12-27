const mongoose = require('mongoose');

const availabilitySchema = new mongoose.Schema(
    {
        doctorId: {
            type: mongoose.Schema.Types.ObjectId,
            ref: 'Doctor',
            required: true,
            index: true,
        },

        date: {
            type: String, // YYYY-MM-DD
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
            type: Number, // minutes
            required: true,
        },
    },
    { timestamps: true }
);

availabilitySchema.index(
    { doctorId: 1, date: 1 },
    { unique: true }
);

module.exports = mongoose.model('Availability', availabilitySchema);
