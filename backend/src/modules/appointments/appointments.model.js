const mongoose = require('mongoose');

const appointmentSchema = new mongoose.Schema(
    {
        doctorId: {
            type: mongoose.Schema.Types.ObjectId,
            ref: 'Doctor',
            required: true,
        },

        patientId: {
            type: mongoose.Schema.Types.ObjectId,
            ref: 'User',
            required: true,
        },

        date: {
            type: String, // YYYY-MM-DD
            required: true,
        },

        startTime: {
            type: String, // "10:00"
            required: true,
        },

        endTime: {
            type: String, // "10:30"
            required: true,
        },

        status: {
            type: String,
            enum: ['BOOKED', 'CANCELLED'],
            default: 'BOOKED',
        },
        paymentStatus: {
            type: String,
            enum: ['PENDING','SUCCESS', 'FAILED'], //pending for pay at counter
            default: 'PENDING',
        },
        isEmergency: {
            type: Boolean,
            default: false,
        },

        emergencyFee: {
            type: Number,
        },

    },
    { timestamps: true }
);

appointmentSchema.index(
    { doctorId: 1, date: 1, startTime: 1  },
    { unique: true,
        partialFilterExpression: { status: 'BOOKED' },
    }
);

module.exports = mongoose.model('Appointment', appointmentSchema);
