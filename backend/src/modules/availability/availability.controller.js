const Availability = require('./availability.model');
const service = require('./availability.services');

exports.setAvailability = async (req, res) => {
    try {
        const data = await Availability.findOneAndUpdate(
            { doctorId: req.user.userId },
            { slots: req.body.slots },
            { upsert: true, new: true }
        );

        res.json({ success: true, data });
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
