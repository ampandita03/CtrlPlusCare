const mongoose = require('mongoose');

const patientProfileSchema = new mongoose.Schema(
    {
        userId: {
            type: mongoose.Schema.Types.ObjectId,
            ref: 'User',
            required: true,
            unique: true,
        },

        name: {
            type: String,
            required: true,
            trim: true,
        },

        age: {
            type: Number,
            required: true,
            min: 0,
            max: 120,
        },

        gender: {
            type: String,
            enum: ['MALE', 'FEMALE', 'OTHER'],
            required: true,
        },

        address: {
            type: String,
            required: true,
            trim: true,
        },
    },
    { timestamps: true }
);

module.exports = mongoose.model('PatientProfile', patientProfileSchema);
