const service = require('./specialties.service');

exports.getSpecialties = async (req, res) => {
    try {
        const specialties = await service.getAllSpecialties();

        res.json({
            success: true,
            data: specialties,
        });
    } catch (err) {
        res.status(500).json({
            success: false,
            message: err.message,
        });
    }
};
