const doctorService = require('./doctors.service');

exports.createDoctor = async (req, res) => {
    try {
        const doctor = await doctorService.createDoctorProfile({
            ...req.body,
            userId: req.user.userId,
        });

        res.status(201).json({ success: true, data: doctor });
    } catch (err) {
        res.status(400).json({ success: false, message: err.message });
    }
};

exports.getDoctorsBySpecialty = async (req, res) => {
    try {
        const { specialty } = req.query;
        const doctors = await doctorService.getDoctorsBySpecialty(specialty);
        res.json({ success: true, data: doctors });
    } catch (err) {
        res.status(400).json({ success: false, message: err.message });
    }
};

exports.getNearbyDoctors = async (req, res) => {
    try {
        const { lat, lng } = req.query;

        const doctors = await doctorService.getNearbyDoctors({
            latitude: parseFloat(lat),
            longitude: parseFloat(lng),
        });

        res.json({ success: true, data: doctors });
    } catch (err) {
        res.status(400).json({ success: false, message: err.message });
    }
};
