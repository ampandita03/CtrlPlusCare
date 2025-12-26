const Doctor = require('./doctors.model');
const PatientProfile = require('../patient/patient.model');
exports.signupDoctor = async (data) => {
    const [patientDB,doctorDB] = await Promise.all([
        PatientProfile.findOne({
            phoneNumber: data.phoneNumber,
        }),
        Doctor.findOne({
            phoneNumber: data.phoneNumber,
        })
    ])
    console.log(data.phoneNumber)

    if (patientDB || doctorDB) {
        throw new Error('Patient already exists');
    }
    const doctor = new Doctor(data);
    return await doctor.save();
};
exports.getDoctorsBySpecialty = async (specialty) => {
    return Doctor.find({specialty});
};

exports.getAllDoctors = async () => {
    return Doctor.find();
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

exports.getDoctorProfile = async (phone) => {
    return Doctor.findOne({phoneNumber: phone});
};

