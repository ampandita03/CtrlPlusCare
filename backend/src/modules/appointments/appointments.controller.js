    const service = require('./appointments.service');
    const Appointment = require('./appointments.model');
    const Patient = require('../patient/patient.model');
    const Doctor = require('../doctors/doctors.model');
    const {sendNotificationToUser} = require("../notifications/notification.controller");

    exports.book = async (req, res) => {
        try {
            console.log('BODY:', req.body);
            console.log('USER:', req.user);
            const appointment = await service.bookAppointment({
                doctorId: req.body.doctorId,
                patientId: req.user.profileId,
                date: req.body.date,
                startTime: req.body.startTime,
                endTime: req.body.endTime,
                paymentStatus: req.body.paymentStatus,
            });

            const patient = await Patient.findById(req.user.profileId);

            await sendNotificationToUser({
                userId: req.user.profileId,
                fcmToken: patient?.fcmToken,
                title: 'Appointment Booked',
                message: 'Your appointment has been booked successfully',
                type: 'APPOINTMENT',
                meta: {
                    appointmentId: appointment._id,
                },
            });

            const doctor = await Doctor.findById(req.body.doctorId);

            await sendNotificationToUser({
                userId: doctor._id,
                fcmToken: doctor?.fcmToken,
                title: 'New Appointment',
                message: 'You have a new appointment scheduled',
                type: 'APPOINTMENT',
                meta: {
                    appointmentId: appointment._id,
                },
            });


            res.status(201).json({ success: true, data: appointment });
        } catch (err) {
            res.status(400).json({ success: false, message: err.message });
        }
    };

    exports.getMyAppointments = async (req, res) => {
        try {
            const appointments = await Appointment.find({
                patientId: req.user.profileId,
            }).populate('doctorId');

            res.json({ success: true, data: appointments });
        } catch (err) {
            res.status(400).json({ success: false, message: err.message });
        }
    };

    exports.getDocAppointments = async (req, res) => {
        try {
            const appointments = await Appointment.find({
                doctorId: req.user.profileId,
            })

            res.json({ success: true, data: appointments });
        } catch (err) {
            res.status(400).json({ success: false, message: err.message });
        }
    };



    exports.cancel = async (req, res) => {
        try {
            const { appointmentId } = req.params;
            console.log('BODY:', req.params);
            console.log('USER:', req.user.profileId);
            const appointment = await service.cancelAppointment(
                appointmentId,
                req.user.profileId
            );
            const patient = await Patient.findById(req.user.profileId);

            await sendNotificationToUser({
                userId: req.user.profileId,
                fcmToken: patient?.fcmToken,
                title: 'Appointment Cancelled',
                message: 'Your appointment has been cancelled',
                type: 'APPOINTMENT',
                meta: {
                    appointmentId: appointment._id,
                },
            });

            res.json({
                success: true,
                data: appointment,
            });
        } catch (err) {
            res.status(400).json({
                success: false,
                message: err.message,
            });
        }
    };


    exports.bookEmergency = async (req, res) => {
        try {
            const appointment = await service.bookEmergencyAppointment({
                doctorId: req.body.doctorId,
                patientId: req.user.profileId,
                date: req.body.date,
            });

            const patient = await Patient.findById(req.user.profileId);
            const doctor = await Doctor.findById(req.body.doctorId);

            await sendNotificationToUser({
                userId: req.user.profileId,
                fcmToken: patient?.fcmToken,
                title: 'Emergency Appointment Booked',
                message: 'Your emergency appointment has been scheduled',
                type: 'EMERGENCY',
                meta: {
                    appointmentId: appointment._id,
                },
            });

            await sendNotificationToUser({
                userId: doctor._id,
                fcmToken: doctor?.fcmToken,
                title: 'Emergency Appointment',
                message: 'You have an emergency appointment',
                type: 'EMERGENCY',
                meta: {
                    appointmentId: appointment._id,
                },
            });

            res.status(201).json({
                success: true,
                message: 'Emergency appointment booked',
                data: appointment,
            });
        } catch (err) {
            res.status(400).json({ success: false, message: err.message });
        }
    };
