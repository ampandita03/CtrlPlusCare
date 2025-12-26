const service = require('./patient.service');

exports.signup = async (req, res) => {
    try {
        const profile = await service.signupPatient(req.body);
        if (!profile) {
            return res.status(400).send({
                message: 'Error in registration',
            })
        }
        res.status(201).json({
            success: true,
            data: {
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
