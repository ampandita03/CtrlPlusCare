const authService = require('./auth.service');

exports.sendOtp = async (req, res) => {
    try {
        const { phone } = req.body;
        if (!phone.match(/^[6-9]\d{9}$/)) {
            res.status(400).send({"error": "Enter valid phone number."});
        }
        await authService.sendOtp(phone);
        res.json({ success: true, message: 'OTP sent' });
    } catch (err) {
        res.status(400).json({ success: false, message: err.message });
    }
};

exports.verifyOtp = async (req, res) => {
    try {
        const data = await authService.verifyOtp(req.body);
        res.json({ success: true, data });
    } catch (err) {
        res.status(400).json({ success: false, message: err.message });
    }
};
