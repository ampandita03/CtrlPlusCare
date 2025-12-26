const mongoose = require('mongoose');

const patientProfileSchema = new mongoose.Schema(
    {
        userId: {
            type: mongoose.Schema.Types.ObjectId,
            ref: 'User',
            required: false,
            unique: true,
            sparse: true,
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
        height : {
            type: String,
        },
        weight : {
            type: String,
        },
        phoneNumber: {
            type: String,
            required: true,
        },
        location: {
            type: {
                type: String,
                enum: ['Point'],
                required: true,
            },
            coordinates: {
                type: [Number], // [longitude, latitude]
                required: true,
            },
        },
        imageLink:{
            type: String,
        }
    },
    { timestamps: true }
);

module.exports = mongoose.model('PatientProfile', patientProfileSchema);
