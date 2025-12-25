const express = require('express');
const router = express.Router();
const controller = require('./availability.controller');
const auth = require('../../middlewares/auth.middleware');
const {allowRoles} = require("../../middlewares/role.middleware");

router.get('/slots', controller.getAvailableSlots);
router.post(
    '/',
    auth,
    allowRoles('DOCTOR'),
    controller.setAvailability
);

module.exports = router;
