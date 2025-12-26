const Doctor = require('./doctors.model');

exports.signupDoctor = async (data) => {
    const existing = await Doctor.findOne({
        phoneNumber: data.phoneNumber,
    });

    if (existing) {
        throw new Error('Doctor already exists');
    }

    const doctor = new Doctor(data);
    return await doctor.save();
};
exports.getDoctorsBySpecialty = async (specialty) => {
    return Doctor.find({specialty});
};


exports.updateDoctorProfile = async (userId, data) => {
    const doctor = await Doctor.findOneAndUpdate(
        { userId },
        data,
        { new: true }
    );

    if (!doctor) {
        throw new Error('Doctor profile not found');
    }

    return doctor;
};


exports.getNearbyDoctors = async ({ latitude, longitude, maxDistance = 5000 }) => {
    return Doctor.find({
        clinicLocation: {
            $near: {
                $geometry: {
                    type: 'Point',
                    coordinates: [longitude, latitude],
                },
                $maxDistance: maxDistance,
            },
        },
    });
};
