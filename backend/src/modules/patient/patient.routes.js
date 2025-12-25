const express = require('express');
const router = express.Router();
const controller = require('./patient.controller');
const auth = require('../../middlewares/auth.middleware');
const {allowRoles} = require("../../middlewares/role.middleware");

router.post(
    '/profile',
    auth,
    allowRoles('PATIENT'),
    controller.saveProfile
);

router.get(
    '/profile',
    auth,
    allowRoles('PATIENT'),
    controller.getMyProfile
);


module.exports = router;
