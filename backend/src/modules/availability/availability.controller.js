const Availability = require('./availability.model');
const service = require('./availability.services');

exports.setAvailability = async (req, res) => {
    try {
        const { date, startTime, endTime, slotDuration } = req.body;
        const today = new Date().toISOString().split('T')[0];
        if (date < today) {
            return res.status(400).json({
                success: false,
                message: 'Cannot set availability for past dates',
            });
        }

        const availability = await Availability.findOneAndUpdate(
            {
                doctorId: req.user.profileId,
                date,
            },
            {
                startTime,
                endTime,
                slotDuration,
            },
            { upsert: true, new: true }
        );

        res.json({ success: true, data: availability });
    } catch (err) {
        res.status(400).json({ success: false, message: err.message });
    }
};

exports.getAvailableSlots = async (req, res) => {
    try {
        const { doctorId, date } = req.query;

        const slots = await service.getSlotsForDate(doctorId, date);

        res.json({ success: true, data: slots });
    } catch (err) {
        res.status(400).json({ success: false, message: err.message });
    }
};
