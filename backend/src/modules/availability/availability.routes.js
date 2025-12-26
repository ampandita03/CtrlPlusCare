const express = require('express');
const router = express.Router();
const controller = require('./availability.controller');
const auth = require('../../middlewares/auth.middleware');
const { allowRoles } = require("../../middlewares/role.middleware");

/**
 * @swagger
 * tags:
 *   name: Availability
 *   description: Doctor availability and slot generation APIs
 */

/**
 * @swagger
 * /api/availability/slots:
 *   get:
 *     summary: Get available slots for a doctor on a specific date
 *     tags: [Availability]
 *     parameters:
 *       - in: query
 *         name: doctorId
 *         required: true
 *         schema:
 *           type: string
 *         example: 65c123abc456def789012345
 *       - in: query
 *         name: date
 *         required: true
 *         schema:
 *           type: string
 *           example: "2025-02-10"
 *     responses:
 *       200:
 *         description: Available slots fetched successfully
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/AvailabilitySlotsResponse'
 *       400:
 *         description: Invalid request
 */
router.get('/slots', controller.getAvailableSlots);

/**
 * @swagger
 * /api/availability:
 *   post:
 *     summary: Set or update doctor availability (doctor only)
 *     tags: [Availability]
 *     security:
 *       - bearerAuth: []
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             $ref: '#/components/schemas/AvailabilityRequest'
 *     responses:
 *       200:
 *         description: Availability saved successfully
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/AvailabilityResponse'
 *       400:
 *         description: Cannot set availability for past dates
 *       403:
 *         description: Only doctors can set availability
 */
router.post(
    '/',
    auth,
    allowRoles('DOCTOR'),
    controller.setAvailability
);

module.exports = router;
