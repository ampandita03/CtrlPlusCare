const Appointment = require('./appointments.model');
const AvailabilityService = require('../availability/availability.services');

exports.bookAppointment = async ({
                                     doctorId,
                                     patientId,
                                     date,
                                     startTime,
                                     endTime,
                                 }) => {
    // 1️⃣ Check availability
    const slots = await AvailabilityService.getSlotsForDate(doctorId, date);

    const isValidSlot = slots.some(
        (s) => s.startTime === startTime && s.endTime === endTime
    );

    if (!isValidSlot) {
        throw new Error('Selected slot is not available');
    }

    // 2️⃣ Doctor conflict
    const doctorConflict = await Appointment.findOne({
        doctorId,
        date,
        startTime,
        status: 'BOOKED',
    });

    if (doctorConflict) {
        throw new Error('Doctor already booked for this slot');
    }

    // 3️⃣ Patient conflict
    const patientConflict = await Appointment.findOne({
        patientId,
        date,
        startTime,
        status: 'BOOKED',
    });

    if (patientConflict) {
        throw new Error('You already have an appointment at this time');
    }

    // 4️⃣ Create appointment
    return await Appointment.create({
        doctorId,
        patientId,
        date,
        startTime,
        endTime,
    });
};


exports.cancelAppointment = async (appointmentId, userId) => {
    const appointment = await Appointment.findOne({
        _id: appointmentId,
        patientId: userId,
        status: 'BOOKED',
    });

    if (!appointment) {
        throw new Error('Appointment not found or already cancelled');
    }

    appointment.status = 'CANCELLED';
    await appointment.save();

    return appointment;
};
