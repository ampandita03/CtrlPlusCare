const PatientProfile = require('./patient.model');


exports.signupPatient = async (data) => {
    const existing = await PatientProfile.findOne({
        phoneNumber: data.phoneNumber,
    });

    if (existing) {
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
