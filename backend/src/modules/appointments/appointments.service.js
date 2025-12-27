const Appointment = require('./appointments.model');
const AvailabilityService = require('../availability/availability.services');
const Doctor = require('../doctors/doctors.model');


const getISTDateString = () => {
    const now = new Date();

    // Convert to IST (UTC + 5:30)
    const istOffset = 5.5 * 60 * 60 * 1000;
    const istDate = new Date(now.getTime() + istOffset);

    return istDate.toISOString().split('T')[0]; // YYYY-MM-DD
};

const getISTTimeInMinutes = () => {
    const now = new Date();
    const istOffset = 5.5 * 60 * 60 * 1000;
    const ist = new Date(now.getTime() + istOffset);

    return ist.getHours() * 60 + ist.getMinutes();
};

const toMinutes = (time) => {
    const [h, m] = time.split(':').map(Number);
    return h * 60 + m;
};

exports.bookAppointment = async ({
                                     doctorId,
                                     patientId,
                                     date,
                                     startTime,
                                     endTime,
                                     paymentStatus,
                                 }) => {
    // 1️⃣ Check availability

    console.log( doctorId,
        patientId,
        date,
        startTime,
        endTime,
        paymentStatus,)
    const slots = await AvailabilityService.getSlotsForDate(doctorId, date);

    const isValidSlot = slots.some(
        (s) =>
            s.startTime === startTime &&
            s.endTime === endTime );
    console.log('BOOKING doctorId:',slots);

    if (!isValidSlot) {
        throw new Error('Selected slot is not available');
    }
    const doctor = await Doctor.findById(doctorId);
    if (!doctor) throw new Error('Doctor not found');

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
        paymentStatus,
        fees : doctor.consultationFee
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
                                          }) => {

    // 1️⃣ Only same-day booking (IST)
    const today = getISTDateString();
    if (date !== today) {
        throw new Error('Emergency booking allowed only for today');
    }

    // 2️⃣ Get doctor
    const doctor = await Doctor.findById(doctorId);
    if (!doctor) throw new Error('Doctor not found');

    // 3️⃣ Get today's slots
    const slots = await AvailabilityService.getSlotsForDate(doctorId, date);
    if (!slots.length) {
        throw new Error('No slots available today');
    }

    const nowMinutes = getISTTimeInMinutes();

    // 4️⃣ Pick nearest future available slot
    const nearestSlot = slots.find((slot) => {
        const slotStartMinutes = toMinutes(slot.startTime);
        return slot.isAvailable && slotStartMinutes >= nowMinutes;
    });

    if (!nearestSlot) {
        throw new Error('No emergency slot available for today');
    }

    // 5️⃣ Create emergency appointment
    return Appointment.create({
        doctorId,
        patientId,
        date,
        startTime: nearestSlot.startTime,
        endTime: nearestSlot.endTime,
        isEmergency: true,
        fees: doctor.emergencyFee,
        paymentStatus: 'PENDING',
    });
};
