const express = require('express');
const router = express.Router();

router.use('/auth', require('./modules/auth/auth.routes'));
router.use('/doctors', require('./modules/doctors/doctors.routes'));
router.use('/availability', require('./modules/availability/availability.routes'));
router.use('/patient', require('./modules/patient/patient.routes'));
router.use('/appointments', require('./modules/appointments/appointments.routes'));
router.use('/testNotification', require('./modules/testapi'));
router.use('/specialties', require('./modules/specialties/specialties.routes'));

module.exports = router;
