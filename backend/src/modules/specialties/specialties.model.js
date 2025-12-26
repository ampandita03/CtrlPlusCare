const mongoose = require('mongoose');

const specialtySchema = new mongoose.Schema(
    {
        _id: {
            type: String,
            required: true,
        },
        label: {
            type: String,
            required: true,
        },
        category: {
            type: String,
            required: true,
        },
        isActive: {
            type: Boolean,
            default: true,
        },
    },
    { timestamps: true }
);

module.exports = mongoose.model('Specialty', specialtySchema);
