const express = require('express');
const router = express.Router();
const controller = require('./specialties.controller');

/**
 * @swagger
 * tags:
 *   name: Specialties
 *   description: Doctor specialties master data
 */

/**
 * @swagger
 * /api/specialties:
 *   get:
 *     summary: Get all active doctor specialties
 *     tags: [Specialties]
 *     responses:
 *       200:
 *         description: Specialties fetched successfully
 *         content:
 *           application/json:
 *             schema:
 *               $ref: '#/components/schemas/SpecialtyListResponse'
 *       500:
 *         description: Server error
 */
router.get('/', controller.getSpecialties);

module.exports = router;
