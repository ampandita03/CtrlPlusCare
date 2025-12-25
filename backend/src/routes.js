const express = require('express');
const router = express.Router();

router.use('/auth', require('./modules/auth/auth.routes'));
router.use('/doctors', require('./modules/doctors/doctors.routes'));
router.use('/availability', require('./modules/availability/availability.routes'));

module.exports = router;
