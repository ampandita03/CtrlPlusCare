const express = require('express');
const router = express.Router();
const controller = require('./specialties.controller');

// Public API
router.get('/', controller.getSpecialties);

module.exports = router;
