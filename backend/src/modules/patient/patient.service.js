const PatientProfile = require('./patient.model');
const Doctor = require('../doctors/doctors.model');
exports.signupPatient = async (data) => {
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

    const profile = new PatientProfile(data);
    return await profile.save();
};

exports.updateProfile = async (userId, data) => {
    const profile = await PatientProfile.findOneAndUpdate(
        { userId },
        data,
        { new: true }
    );

    if (!profile) {
        throw new Error('Profile not found');
    }

    return profile;
};

exports.getProfile = async (userId) => {
    return await PatientProfile.findOne({ userId });
};
