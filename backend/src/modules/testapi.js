const controller = require("./testnotifiController");
const express = require("express");
const router = express.Router();
router.post(
    '/notification',
    controller.testNotification
);


module.exports = router;