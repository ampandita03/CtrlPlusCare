const Availability = require('./availability.model');
const Appointment = require('../appointments/appointments.model');

exports.getSlotsForDate = async (doctorId, date) => {
    const today = new Date().toISOString().split('T')[0];
    if (date < today) return [];

    const availability = await Availability.findOne({ doctorId, date });
    if (!availability) return [];

    const appointments = await Appointment.find({
        doctorId,
        date,
        status: 'BOOKED',
    }).select('startTime');

    const bookedTimes = new Set(
        appointments.map((a) => a.startTime)
    );

    let slots = [];

    let start = toMinutes(availability.startTime);
    let end = toMinutes(availability.endTime);

    while (start + availability.slotDuration <= end) {
        const startTime = fromMinutes(start);
        const endTime = fromMinutes(start + availability.slotDuration);

        slots.push({
            startTime,
            endTime,
            isAvailable: !bookedTimes.has(startTime),
        });

        start += availability.slotDuration;
    }

    return slots;
};

// Helpers
const toMinutes = (time) => {
    const [h, m] = time.split(':').map(Number);
    return h * 60 + m;
};

const fromMinutes = (mins) => {
    const h = Math.floor(mins / 60);
    const m = mins % 60;
    return `${String(h).padStart(2, '0')}:${String(m).padStart(2, '0')}`;
};
