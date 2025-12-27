const Specialty = require('./specialties.model');

exports.getAllSpecialties = async () => {
    return Specialty.find({ isActive: true })
        .select('_id label category')
        .sort({ category: 1, label: 1 });
};
