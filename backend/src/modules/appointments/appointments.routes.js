const express = require('express');
const router = express.Router();
const controller = require('./appointments.controller');
const auth = require('../../middlewares/auth.middleware');
const role = require("../../middlewares/role.middleware");

router.get('/my', auth, controller.getMyAppointments);
router.patch(
    '/:appointmentId/cancel',
    auth,
    role.allowRoles('PATIENT'),
    controller.cancel
);
router.post(
    '/',
    auth,
    role.allowRoles('PATIENT'),
    controller.book
);

router.post(
    '/book',
    auth,
    controller.bookEmergency
);

module.exports = router;
