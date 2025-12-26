const express = require('express');
const router = express.Router();
const controller = require('./appointments.controller');
const auth = require('../../middlewares/auth.middleware');
const role = require("../../middlewares/role.middleware");

/**
 * @swagger
 * tags:
 *   name: Appointments
 *   description: Appointment booking and management APIs
 */

/**
 * @swagger
 * /api/appointments/my:
 *   get:
 *     summary: Get logged-in patient's appointments
 *     tags: [Appointments]
 *     security:
 *       - bearerAuth: []
 *     responses:
 *       200:
 *         description: Patient appointments fetched
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/AppointmentListResponse'
 */
router.get('/my', auth, controller.getMyAppointments);

/**
 * @swagger
 * /api/appointments/doc:
 *   get:
 *     summary: Get logged-in doctor's appointments
 *     tags: [Appointments]
 *     security:
 *       - bearerAuth: []
 *     responses:
 *       200:
 *         description: Doctor appointments fetched
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/AppointmentListResponse'
 */
router.get('/doc', auth, controller.getDocAppointments);

/**
 * @swagger
 * /api/appointments/{appointmentId}/cancel:
 *   patch:
 *     summary: Cancel an appointment (patient only)
 *     tags: [Appointments]
 *     security:
 *       - bearerAuth: []
 *     parameters:
 *       - in: path
 *         name: appointmentId
 *         required: true
 *         schema:
 *           type: string
 *     responses:
 *       200:
 *         description: Appointment cancelled successfully
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/AppointmentResponse'
 *       403:
 *         description: Only patient can cancel
 *       400:
 *         description: Appointment not found or already cancelled
 */
router.patch(
    '/:appointmentId/cancel',
    auth,
    role.allowRoles('PATIENT'),
    controller.cancel
);

/**
 * @swagger
 * /api/appointments:
 *   post:
 *     summary: Book a normal appointment (patient only)
 *     tags: [Appointments]
 *     security:
 *       - bearerAuth: []
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             $ref: '#/components/schemas/AppointmentBookingRequest'
 *     responses:
 *       201:
 *         description: Appointment booked successfully
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/AppointmentResponse'
 *       400:
 *         description: Slot unavailable or conflict
 */
router.post(
    '/',
    auth,
    role.allowRoles('PATIENT'),
    controller.book
);

/**
 * @swagger
 * /api/appointments/book:
 *   post:
 *     summary: Book an emergency appointment (same day)
 *     tags: [Appointments]
 *     security:
 *       - bearerAuth: []
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             $ref: '#/components/schemas/EmergencyAppointmentRequest'
 *     responses:
 *       201:
 *         description: Emergency appointment booked
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/AppointmentResponse'
 *       400:
 *         description: Emergency booking not allowed
 */
router.post('/book', auth, controller.bookEmergency);

module.exports = router;
