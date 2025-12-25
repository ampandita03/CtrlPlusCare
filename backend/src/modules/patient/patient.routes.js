const express = require('express');
const router = express.Router();
const controller = require('./patient.controller');
const auth = require('../../middlewares/auth.middleware');

router.post('/profile', auth, controller.saveProfile);
router.get('/profile', auth, controller.getMyProfile);

module.exports = router;
