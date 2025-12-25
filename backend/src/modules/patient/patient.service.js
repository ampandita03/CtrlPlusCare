const PatientProfile = require('./patient.model');

exports.upsertProfile = async (userId, data) => {
    return PatientProfile.findOneAndUpdate(
        {userId},
        {...data, userId},
        {upsert: true, new: true}
    );
};

exports.getProfile = async (userId) => {
    return PatientProfile.findOne({userId});
};
