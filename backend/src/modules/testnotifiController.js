const service = require("./notifications/fcm.service");
exports.testNotification = async (req, res) => {
    try {
        const {token,title,description} = req.body;
        const profile = await service.sendNotification(token,title,description);
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