const Doctor = require('./doctors.model');

exports.createDoctorProfile = async (data) => {
    return await Doctor.create(data);
};

exports.getDoctorsBySpecialty = async (specialty) => {
    return Doctor.find({specialty});
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
