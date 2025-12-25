const Availability = require('./availability.model');

exports.getSlotsForDate = async (doctorId, date) => {
    const availability = await Availability.findOne({ doctorId });
    if (!availability) return [];

    const dayOfWeek = new Date(date).getDay();

    const daySlots = availability.slots.filter(
        (s) => s.dayOfWeek === dayOfWeek
    );

    let generatedSlots = [];

    daySlots.forEach((slot) => {
        let start = toMinutes(slot.startTime);
        let end = toMinutes(slot.endTime);

        while (start + slot.slotDuration <= end) {
            generatedSlots.push({
                startTime: fromMinutes(start),
                endTime: fromMinutes(start + slot.slotDuration),
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
