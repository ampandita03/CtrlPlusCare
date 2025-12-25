const service = require('./patient.service');

exports.saveProfile = async (req, res) => {
    try {
        const profile = await service.upsertProfile(req.user.userId, req.body);

        res.json({
            success: true,
            data: profile,
        });
    } catch (err) {
        res.status(400).json({
            success: false,
            message: err.message,
        });
    }
};

exports.getMyProfile = async (req, res) => {
    try {
        const profile = await service.getProfile(req.user.userId);

        res.json({
            success: true,
            data: profile,
        });
    } catch (err) {
        res.status(400).json({
            success: false,
            message: err.message,
        });
    }
};
