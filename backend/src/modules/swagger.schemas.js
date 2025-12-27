/**
 * @swagger
 * components:
 *   schemas:
 *     GeoLocation:
 *       type: object
 *       required:
 *         - type
 *         - coordinates
 *       properties:
 *         type:
 *           type: string
 *           enum: [Point]
 *           example: Point
 *         coordinates:
 *           type: array
 *           items:
 *             type: number
 *           example: [77.5946, 12.9716]
 *
 *     PatientSignupRequest:
 *       type: object
 *       required:
 *         - name
 *         - age
 *         - gender
 *         - address
 *         - phoneNumber
 *         - location
 *       properties:
 *         name:
 *           type: string
 *           example: Rahul Sharma
 *         age:
 *           type: number
 *           example: 28
 *         gender:
 *           type: string
 *           enum: [MALE, FEMALE, OTHER]
 *           example: MALE
 *         address:
 *           type: string
 *           example: Sector 62, Noida
 *         height:
 *           type: string
 *           example: 175 cm
 *         weight:
 *           type: string
 *           example: 70 kg
 *         phoneNumber:
 *           type: string
 *           example: "9876543210"
 *         location:
 *           $ref: '#/components/schemas/GeoLocation'
 *         imageLink:
 *           type: string
 *           example: https://cdn.app.com/profile.png
 *         fcmToken:
 *           type: string
 *         role:
 *           type: string
 *           enum: [PATIENT]
 *
 *     PatientUpdateRequest:
 *       type: object
 *       properties:
 *         name:
 *           type: string
 *         age:
 *           type: number
 *         gender:
 *           type: string
 *           enum: [MALE, FEMALE, OTHER]
 *         address:
 *           type: string
 *         height:
 *           type: string
 *         weight:
 *           type: string
 *         imageLink:
 *           type: string
 *         fcmToken:
 *           type: string
 *         location:
 *           $ref: '#/components/schemas/GeoLocation'
 *
 *     PatientProfile:
 *       type: object
 *       properties:
 *         _id:
 *           type: string
 *         name:
 *           type: string
 *         age:
 *           type: number
 *         gender:
 *           type: string
 *         address:
 *           type: string
 *         height:
 *           type: string
 *         weight:
 *           type: string
 *         phoneNumber:
 *           type: string
 *         location:
 *           $ref: '#/components/schemas/GeoLocation'
 *         imageLink:
 *           type: string
 *         fcmToken:
 *           type: string
 *         role:
 *           type: string
 *         createdAt:
 *           type: string
 *           format: date-time
 *         updatedAt:
 *           type: string
 *           format: date-time
 *
 *     PatientProfileResponse:
 *       type: object
 *       properties:
 *         success:
 *           type: boolean
 *           example: true
 *         data:
 *           $ref: '#/components/schemas/PatientProfile'
 */
/**
 * @swagger
 * components:
 *   schemas:
 *     ClinicLocation:
 *       type: object
 *       required:
 *         - type
 *         - coordinates
 *       properties:
 *         type:
 *           type: string
 *           enum: [Point]
 *           example: Point
 *         coordinates:
 *           type: array
 *           items:
 *             type: number
 *           example: [77.5946, 12.9716]
 *
 *     DoctorSignupRequest:
 *       type: object
 *       required:
 *         - name
 *         - specialty
 *         - clinicLocation
 *         - clinicAddress
 *         - consultationFee
 *         - emergencyFee
 *         - phoneNumber
 *         - documentUrl
 *       properties:
 *         name:
 *           type: string
 *           example: Dr. Amit Verma
 *         specialty:
 *           type: string
 *           example: Cardiologist
 *         clinicLocation:
 *           $ref: '#/components/schemas/ClinicLocation'
 *         clinicAddress:
 *           type: string
 *           example: Sector 18, Noida
 *         consultationFee:
 *           type: number
 *           example: 500
 *         emergencyFee:
 *           type: number
 *           example: 1000
 *         email:
 *           type: string
 *           example: doctor@email.com
 *         phoneNumber:
 *           type: string
 *           example: "9876543210"
 *         about:
 *           type: string
 *         profileUrl:
 *           type: string
 *         documentUrl:
 *           type: string
 *           example: https://cdn.app.com/license.pdf
 *         fcmToken:
 *           type: string
 *         role:
 *           type: string
 *           enum: [DOCTOR]
 *
 *     DoctorUpdateRequest:
 *       type: object
 *       properties:
 *         name:
 *           type: string
 *         clinicAddress:
 *           type: string
 *         consultationFee:
 *           type: number
 *         emergencyFee:
 *           type: number
 *         about:
 *           type: string
 *         profileUrl:
 *           type: string
 *         fcmToken:
 *           type: string
 *         clinicLocation:
 *           $ref: '#/components/schemas/ClinicLocation'
 *
 *     Doctor:
 *       type: object
 *       properties:
 *         _id:
 *           type: string
 *         name:
 *           type: string
 *         specialty:
 *           type: string
 *         clinicLocation:
 *           $ref: '#/components/schemas/ClinicLocation'
 *         clinicAddress:
 *           type: string
 *         consultationFee:
 *           type: number
 *         emergencyFee:
 *           type: number
 *         phoneNumber:
 *           type: string
 *         rating:
 *           type: number
 *         isVerified:
 *           type: boolean
 *         role:
 *           type: string
 *         createdAt:
 *           type: string
 *           format: date-time
 *         updatedAt:
 *           type: string
 *           format: date-time
 *
 *     DoctorResponse:
 *       type: object
 *       properties:
 *         success:
 *           type: boolean
 *         data:
 *           $ref: '#/components/schemas/Doctor'
 *
 *     DoctorListResponse:
 *       type: object
 *       properties:
 *         success:
 *           type: boolean
 *         data:
 *           type: array
 *           items:
 *             $ref: '#/components/schemas/Doctor'
 */
/**
 * @swagger
 * components:
 *   schemas:
 *     AppointmentBookingRequest:
 *       type: object
 *       required:
 *         - doctorId
 *         - date
 *         - startTime
 *         - endTime
 *       properties:
 *         doctorId:
 *           type: string
 *           example: 65c123abc456def789012345
 *         date:
 *           type: string
 *           example: "2025-02-10"
 *         startTime:
 *           type: string
 *           example: "10:00"
 *         endTime:
 *           type: string
 *           example: "10:30"
 *         paymentStatus:
 *           type: string
 *           enum: [PENDING, SUCCESS, FAILED]
 *           example: PENDING
 *
 *     EmergencyAppointmentRequest:
 *       type: object
 *       required:
 *         - doctorId
 *         - date
 *       properties:
 *         doctorId:
 *           type: string
 *         date:
 *           type: string
 *           example: "2025-02-10"
 *
 *     Appointment:
 *       type: object
 *       properties:
 *         _id:
 *           type: string
 *         doctorId:
 *           type: string
 *         patientId:
 *           type: string
 *         date:
 *           type: string
 *         startTime:
 *           type: string
 *         endTime:
 *           type: string
 *         status:
 *           type: string
 *           enum: [BOOKED, CANCELLED]
 *         paymentStatus:
 *           type: string
 *           enum: [PENDING, SUCCESS, FAILED]
 *         isEmergency:
 *           type: boolean
 *         fees:
 *           type: number
 *         reminderSent:
 *           type: boolean
 *         createdAt:
 *           type: string
 *           format: date-time
 *         updatedAt:
 *           type: string
 *           format: date-time
 *
 *     AppointmentResponse:
 *       type: object
 *       properties:
 *         success:
 *           type: boolean
 *         data:
 *           $ref: '#/components/schemas/Appointment'
 *
 *     AppointmentListResponse:
 *       type: object
 *       properties:
 *         success:
 *           type: boolean
 *         data:
 *           type: array
 *           items:
 *             $ref: '#/components/schemas/Appointment'
 */
/**
 * @swagger
 * components:
 *   schemas:
 *     AvailabilityRequest:
 *       type: object
 *       required:
 *         - date
 *         - startTime
 *         - endTime
 *         - slotDuration
 *       properties:
 *         date:
 *           type: string
 *           example: "2025-02-10"
 *         startTime:
 *           type: string
 *           example: "09:00"
 *         endTime:
 *           type: string
 *           example: "17:00"
 *         slotDuration:
 *           type: number
 *           example: 30
 *
 *     Availability:
 *       type: object
 *       properties:
 *         _id:
 *           type: string
 *         doctorId:
 *           type: string
 *         date:
 *           type: string
 *         startTime:
 *           type: string
 *         endTime:
 *           type: string
 *         slotDuration:
 *           type: number
 *         createdAt:
 *           type: string
 *           format: date-time
 *         updatedAt:
 *           type: string
 *           format: date-time
 *
 *     AvailabilitySlot:
 *       type: object
 *       properties:
 *         startTime:
 *           type: string
 *           example: "10:00"
 *         endTime:
 *           type: string
 *           example: "10:30"
 *         isAvailable:
 *           type: boolean
 *           example: true
 *
 *     AvailabilityResponse:
 *       type: object
 *       properties:
 *         success:
 *           type: boolean
 *         data:
 *           $ref: '#/components/schemas/Availability'
 *
 *     AvailabilitySlotsResponse:
 *       type: object
 *       properties:
 *         success:
 *           type: boolean
 *         data:
 *           type: array
 *           items:
 *             $ref: '#/components/schemas/AvailabilitySlot'
 */
/**
 * @swagger
 * components:
 *   schemas:
 *     Notification:
 *       type: object
 *       properties:
 *         _id:
 *           type: string
 *         userId:
 *           type: string
 *         title:
 *           type: string
 *           example: Appointment Booked
 *         message:
 *           type: string
 *           example: Your appointment has been booked successfully
 *         type:
 *           type: string
 *           enum: [APPOINTMENT, REMINDER, EMERGENCY, SYSTEM]
 *           example: APPOINTMENT
 *         isRead:
 *           type: boolean
 *           example: false
 *         meta:
 *           type: object
 *           example:
 *             appointmentId: 65c123abc456def789012345
 *         createdAt:
 *           type: string
 *           format: date-time
 *         updatedAt:
 *           type: string
 *           format: date-time
 *
 *     NotificationListResponse:
 *       type: object
 *       properties:
 *         success:
 *           type: boolean
 *         data:
 *           type: array
 *           items:
 *             $ref: '#/components/schemas/Notification'
 */
/**
 * @swagger
 * components:
 *   schemas:
 *     Specialty:
 *       type: object
 *       properties:
 *         _id:
 *           type: string
 *           example: CARDIOLOGY
 *         label:
 *           type: string
 *           example: Cardiology
 *         category:
 *           type: string
 *           example: Heart
 *
 *     SpecialtyListResponse:
 *       type: object
 *       properties:
 *         success:
 *           type: boolean
 *           example: true
 *         data:
 *           type: array
 *           items:
 *             $ref: '#/components/schemas/Specialty'
 */
