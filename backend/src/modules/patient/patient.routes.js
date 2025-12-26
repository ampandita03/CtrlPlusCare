const express = require('express');
const router = express.Router();
const controller = require('./patient.controller');
const auth = require('../../middlewares/auth.middleware');
const role = require('../../middlewares/role.middleware');

/**
 * @swagger
 * tags:
 *   name: Patient
 *   description: Patient profile management APIs
 */

/**
 * @swagger
 * /api/patient/signup:
 *   post:
 *     summary: Register a new patient
 *     tags: [Patient]
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             $ref: '#/components/schemas/PatientSignupRequest'
 *     responses:
 *       201:
 *         description: Patient signup successful
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 success:
 *                   type: boolean
 *                   example: true
 *                 data:
 *                   type: object
 *                   properties:
 *                     message:
 *                       type: string
 *                       example: Signup successful
 *       400:
 *         description: Patient already exists or validation error
 */
router.post('/signup', controller.signup);

/**
 * @swagger
 * /api/patient/profile:
 *   put:
 *     summary: Update patient profile
 *     tags: [Patient]
 *     security:
 *       - bearerAuth: []
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             $ref: '#/components/schemas/PatientUpdateRequest'
 *     responses:
 *       200:
 *         description: Profile updated successfully
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/PatientProfileResponse'
 *       401:
 *         description: Unauthorized
 *       403:
 *         description: Access denied (non-patient role)
 */
router.put(
    '/profile',
    auth,
    role.allowRoles('PATIENT'),
    controller.updateProfile
);

/**
 * @swagger
 * /api/patient/profile:
 *   get:
 *     summary: Get logged-in patient profile
 *     tags: [Patient]
 *     security:
 *       - bearerAuth: []
 *     responses:
 *       200:
 *         description: Patient profile fetched successfully
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/PatientProfileResponse'
 *       401:
 *         description: Unauthorized
 *       403:
 *         description: Access denied
 */
router.get(
    '/profile',
    auth,
    role.allowRoles('PATIENT'),
    controller.getProfile
);

module.exports = router;
