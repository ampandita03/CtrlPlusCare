const express = require('express');
const router = express.Router();
const controller = require('./patient.controller');
const auth = require('../../middlewares/auth.middleware');
const role = require('../../middlewares/role.middleware');

router.post(
    '/signup',
    controller.signup
);

router.put(
    '/profile',
    auth,
    role.allowRoles('PATIENT'),
    controller.updateProfile
);

router.get(
    '/profile',
    auth,
    role.allowRoles('PATIENT'),
    controller.getProfile
);

module.exports = router;
