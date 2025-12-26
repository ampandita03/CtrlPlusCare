const doctorService = require('./doctors.service');
exports.createDoctor = async (req, res) => {
    try {
        const doctor = await doctorService.signupDoctor({
            ...req.body,
        });

        res.status(201).json({
            success: true,
            data: {
                patientId: doctor._id,
                message: 'Signup successful',
            },
        });    } catch (err) {
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

exports.getAllDoctors = async (req, res) => {
    try {
        const doctors = await doctorService.getAllDoctors();
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

exports.updateProfile = async (req, res) => {
    try {
        const doctor = await doctorService.updateDoctorProfile(
            req.user.profileId,
            req.body
        );

        res.json({
            success: true,
            data: doctor,
        });
    } catch (err) {
        res.status(400).json({
            success: false,
            message: err.message,
        });
    }
};


exports.getProfile = async (req, res) => {
    try {
        const profile = await doctorService.getDoctorProfile(req.user.phone);
        console.log(req.user)
        res.json({
            success: true,
            data: profile,
        });
    } catch (err) {
        res.status(400).json({
            success: false,
            message: err.message,
        });
    }
};
