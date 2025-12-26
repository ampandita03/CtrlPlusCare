const express = require('express');
const router = express.Router();
const controller = require('./notification.controller');
const auth = require('../../middlewares/auth.middleware');

router.get('/', auth, controller.getMyNotifications);
router.patch('/:notificationId/read', auth, controller.markAsRead);
router.patch('/read-all', auth, controller.markAllAsRead);

module.exports = router;
