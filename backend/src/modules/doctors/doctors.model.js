const mongoose = require('mongoose');

const doctorSchema = new mongoose.Schema(
    {
        name: {
            type: String,
            required: true,
        },

        specialty: {
            type: String,
            required: true,
            index: true,
        },

        clinicLocation: {
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

        clinicAddress: {
            type: String,
            required: true,
        },

        consultationFee: {
            type: Number,
            required: true,
        },
        email:{
            type: String,
        },
        phoneNumber: {
            type: String,
            required: true,
            unique: true,
            index: true,
        },
        about:{
            type: String,
        },
        profileUrl :{
            type: String,
        },
        documentUrl:{
            type: String,
            required: true,
        },
        rating:{
            type: Number,
            default: 0,
        },
        isVerified: {
            type: Boolean,
            default: false,

        }, fcmToken:{
            type: String
        },
        role:{
            type: String,
            enum: ['PATIENT','DOCTOR'],
        }

    },
    { timestamps: true }
);

//  Geo index for distance search
doctorSchema.index({ clinicLocation: '2dsphere' });

module.exports = mongoose.model('Doctor', doctorSchema);
