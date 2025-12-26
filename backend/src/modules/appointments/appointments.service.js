const Appointment = require('./appointments.model');
const AvailabilityService = require('../availability/availability.services');
const Doctor = require('../doctors/doctors.model');

exports.bookAppointment = async ({
                                     doctorId,
                                     patientId,
                                     date,
                                     startTime,
                                     endTime,
                                     paymentStatus,
                                 }) => {
    // 1️⃣ Check availability
    const slots = await AvailabilityService.getSlotsForDate(doctorId, date);

    const isValidSlot = slots.some(
        (s) =>
            s.startTime === startTime &&
            s.endTime === endTime );
    console.log('BOOKING doctorId:',slots);

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
        paymentStatus
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



exports.bookEmergencyAppointment = async ({
                                              doctorId,
                                              patientId,
                                              date,
                                              startTime,
                                          }) => {
    // 1️⃣ Only same-day allowed
    const today = new Date().toISOString().split('T')[0];
    if (date !== today) {
        throw new Error('Emergency booking allowed only for today');
    }

    // 2️⃣ Get doctor
    const doctor = await Doctor.findById(doctorId);
    if (!doctor) throw new Error('Doctor not found');

    // 3️⃣ Get slots for today
    const slots = await AvailabilityService.getSlotsForDate(doctorId, date);

    const slot = slots.find(
        (s) => s.startTime === startTime
    );

    if (!slot) {
        throw new Error('Invalid slot');
    }

    // ⚠️ Override availability (emergency)
    // even if slot.isAvailable === false

    // 4️⃣ Create emergency appointment
    return Appointment.create({
        doctorId,
        patientId,
        date,
        startTime,
        endTime: slot.endTime,
        isEmergency: true,
        emergencyFee: doctor.emergencyFee,
        paymentStatus: 'PENDING',
    });
};
