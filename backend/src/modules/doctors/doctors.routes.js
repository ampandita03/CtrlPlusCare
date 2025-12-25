const express = require('express');
const router = express.Router();
const controller = require('./doctors.controller');
const auth = require('../../middlewares/auth.middleware');

router.post('/', auth, controller.createDoctor);
router.get('/', controller.getDoctorsBySpecialty);
router.get('/nearby', controller.getNearbyDoctors);

module.exports = router;
