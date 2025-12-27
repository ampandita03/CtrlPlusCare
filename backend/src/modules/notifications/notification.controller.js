const {sendNotification} = require("./fcm.service");
const fcm = require('./fcm.service');
const Notification = require("../../modules/notifications/notifications.model");
exports.testNotification = async (req, res) => {
    const { token ,title,description} = req.body;

    await sendNotification(
        token,
        title,
        description
    );

    res.json({ success: true });
};


exports.sendNotificationToUser = async ({
                                            userId,
                                            fcmToken,
                                            title,
                                            message,
                                            type = 'SYSTEM',
                                            meta = {},
                                        }) => {
    const notification = await Notification.create({
        userId,
        title,
        message,
        type,
        meta,
    });
    if (fcmToken) {
        await fcm.sendNotification(fcmToken, title, message);
    }

    return notification;
};


exports.getMyNotifications = async (req, res) => {
    try {
        const notifications = await Notification.find({
            userId: req.user.profileId,
        }).sort({ createdAt: -1 });

        res.json({ success: true, data: notifications });
    } catch (err) {
        res.status(400).json({ success: false, message: err.message });
    }
};

exports.markAsRead = async (req, res) => {
    try {
        const { notificationId } = req.params;

        await Notification.updateOne(
            { _id: notificationId, userId: req.user.profileId },
            { isRead: true }
        );

        res.json({ success: true });
    } catch (err) {
        res.status(400).json({ success: false, message: err.message });
    }
};

exports.markAllAsRead = async (req, res) => {
    try {
        await Notification.updateMany(
            { userId: req.user.profileId, isRead: false },
            { isRead: true }
        );

        res.json({ success: true });
    } catch (err) {
        res.status(400).json({ success: false, message: err.message });
    }
};
