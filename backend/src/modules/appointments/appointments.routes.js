const express = require('express');
const router = express.Router();
const controller = require('./appointments.controller');
const auth = require('../../middlewares/auth.middleware');

router.post('/', auth, controller.book);
router.get('/my', auth, controller.getMyAppointments);

module.exports = router;
