const Availability = require('./availability.model');
const Appointment = require('../appointments/appointments.model');

exports.getSlotsForDate = async (doctorId, date) => {
    const availability = await Availability.findOne({ doctorId });
    if (!availability) return [];

    // ✅ FIXED DATE PARSING
    const [year, month, day] = date.split('-').map(Number);
    const dayOfWeek = new Date(year, month - 1, day).getDay();

    const daySlots = availability.slots.filter(
        (s) => s.dayOfWeek === dayOfWeek
    );

    const appointments = await Appointment.find({
        doctorId,
        date,
        status: 'BOOKED',
    }).select('startTime');

    const bookedTimes = new Set(
        appointments.map((a) => a.startTime)
    );

    let generatedSlots = [];

    daySlots.forEach((slot) => {
        let start = toMinutes(slot.startTime);
        let end = toMinutes(slot.endTime);

        while (start + slot.slotDuration <= end) {
            const startTime = fromMinutes(start);
            const endTime = fromMinutes(start + slot.slotDuration);

            generatedSlots.push({
                startTime,
                endTime,
                isAvailable: !bookedTimes.has(startTime),
            });

            start += slot.slotDuration;
        }
    });

    return generatedSlots;
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
