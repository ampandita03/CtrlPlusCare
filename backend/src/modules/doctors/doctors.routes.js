const express = require('express');
const router = express.Router();
const controller = require('./doctors.controller');
const {allowRoles} = require("../../middlewares/role.middleware");
const auth = require("../../middlewares/auth.middleware");
router.post('/', controller.createDoctor);
router.get('/', controller.getDoctorsBySpecialty);
router.get('/my', auth,controller.getProfile);

router.get('/nearby', controller.getNearbyDoctors);
router.put(
    '/profile',
    auth,
    allowRoles('DOCTOR'),
    controller.updateProfile
);

router.get('/all/doc', auth,controller.getAllDoctors);

module.exports = router;
