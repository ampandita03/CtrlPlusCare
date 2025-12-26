const mongoose = require('mongoose');

const doctorSchema = new mongoose.Schema(
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

        }

    },
    { timestamps: true }
);

//  Geo index for distance search
doctorSchema.index({ clinicLocation: '2dsphere' });

module.exports = mongoose.model('Doctor', doctorSchema);
