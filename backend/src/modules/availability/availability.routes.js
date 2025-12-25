const express = require('express');
const router = express.Router();
const controller = require('./availability.controller');
const auth = require('../../middlewares/auth.middleware');

router.post('/', auth, controller.setAvailability);
router.get('/slots', controller.getAvailableSlots);

module.exports = router;
