const express = require('express');
const router = express.Router();
const controller = require('./doctors.controller');
const { allowRoles } = require("../../middlewares/role.middleware");
const auth = require("../../middlewares/auth.middleware");

/**
 * @swagger
 * tags:
 *   name: Doctors
 *   description: Doctor management and discovery APIs
 */

/**
 * @swagger
 * /api/doctors:
 *   post:
 *     summary: Register a new doctor
 *     tags: [Doctors]
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             $ref: '#/components/schemas/DoctorSignupRequest'
 *     responses:
 *       201:
 *         description: Doctor signup successful
 *         content:
 *           application/json:
 *             schema:
 *               type: object
 *               properties:
 *                 success:
 *                   type: boolean
 *                 data:
 *                   type: object
 *                   properties:
 *                     patientId:
 *                       type: string
 *                     message:
 *                       type: string
 *                       example: Signup successful
 *       400:
 *         description: Doctor already exists or validation error
 */
router.post('/', controller.createDoctor);

/**
 * @swagger
 * /api/doctors:
 *   get:
 *     summary: Get doctors by specialty
 *     tags: [Doctors]
 *     parameters:
 *       - in: query
 *         name: specialty
 *         required: true
 *         schema:
 *           type: string
 *         example: Cardiologist
 *     responses:
 *       200:
 *         description: Doctors fetched successfully
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/DoctorListResponse'
 */
router.get('/', controller.getDoctorsBySpecialty);

/**
 * @swagger
 * /api/doctors/my:
 *   get:
 *     summary: Get logged-in doctor's profile
 *     tags: [Doctors]
 *     security:
 *       - bearerAuth: []
 *     responses:
 *       200:
 *         description: Doctor profile fetched successfully
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/DoctorResponse'
 *       401:
 *         description: Unauthorized
 */
router.get('/my', auth, controller.getProfile);

/**
 * @swagger
 * /api/doctors/nearby:
 *   get:
 *     summary: Get nearby doctors based on location
 *     tags: [Doctors]
 *     parameters:
 *       - in: query
 *         name: lat
 *         required: true
 *         schema:
 *           type: number
 *         example: 12.9716
 *       - in: query
 *         name: lng
 *         required: true
 *         schema:
 *           type: number
 *         example: 77.5946
 *     responses:
 *       200:
 *         description: Nearby doctors fetched successfully
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/DoctorListResponse'
 */
router.get('/nearby', controller.getNearbyDoctors);

/**
 * @swagger
 * /api/doctors/profile:
 *   put:
 *     summary: Update doctor profile
 *     tags: [Doctors]
 *     security:
 *       - bearerAuth: []
 *     requestBody:
 *       required: true
 *       content:
 *         application/json:
 *           schema:
 *             $ref: '#/components/schemas/DoctorUpdateRequest'
 *     responses:
 *       200:
 *         description: Doctor profile updated successfully
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/DoctorResponse'
 *       403:
 *         description: Only doctors can update profile
 */
router.put(
    '/profile',
    auth,
    allowRoles('DOCTOR'),
    controller.updateProfile
);

/**
 * @swagger
 * /api/doctors/all/doc:
 *   get:
 *     summary: Get all doctors (admin/internal use)
 *     tags: [Doctors]
 *     security:
 *       - bearerAuth: []
 *     responses:
 *       200:
 *         description: All doctors fetched successfully
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/DoctorListResponse'
 */
router.get('/all/doc', auth, controller.getAllDoctors);

module.exports = router;
