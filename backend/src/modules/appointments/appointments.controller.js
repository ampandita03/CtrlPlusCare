const service = require('./appointments.service');
const Appointment = require('./appointments.model');

exports.book = async (req, res) => {
    try {
        console.log('BODY:', req.body);
        console.log('USER:', req.user);
        const appointment = await service.bookAppointment({
            doctorId: req.body.doctorId,
            patientId: req.user.userId,
            date: req.body.date,
            startTime: req.body.startTime,
            endTime: req.body.endTime,
        });

        res.status(201).json({ success: true, data: appointment });
    } catch (err) {
        res.status(400).json({ success: false, message: err.message });
    }
};

exports.getMyAppointments = async (req, res) => {
    try {
        const appointments = await Appointment.find({
            patientId: req.user.userId,
        }).populate('doctorId');

        res.json({ success: true, data: appointments });
    } catch (err) {
        res.status(400).json({ success: false, message: err.message });
    }
};


exports.cancel = async (req, res) => {
    try {
        const { appointmentId } = req.params;

        const appointment = await service.cancelAppointment(
            appointmentId,
            req.user.userId
        );

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
