const mongoose = require('mongoose');

const doctorSchema = new mongoose.Schema(
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
    },
    { timestamps: true }
);

// 🔥 Geo index for distance search
doctorSchema.index({ clinicLocation: '2dsphere' });

module.exports = mongoose.model('Doctor', doctorSchema);
