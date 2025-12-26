const service = require('./patient.service');

exports.signup = async (req, res) => {
    try {
        const profile = await service.signupPatient(req.body);
        res.status(201).json({
            success: true,
            data: {
                patientId: profile._id,
                message: 'Signup successful',
            },
        });

    } catch (err) {
        res.status(400).json({
            success: false,
            message: err.message,
        });
    }
};

exports.updateProfile = async (req, res) => {
    try {
        console.log(req.user.userId);
        console.log(req.body);
        const profile = await service.updateProfile(
            req.user.userId,
            req.body
        );

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

exports.getProfile = async (req, res) => {
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
