const cron = require('node-cron');
const Appointment = require('../appointments/appointments.model');
const Patient = require('../patient/patient.model');
const fcm = require('./fcm.service');

const getISTDateTime = () => {
    const now = new Date();
    const istOffset = 5.5 * 60 * 60 * 1000;
    return new Date(now.getTime() + istOffset);
};

const toMinutes = (time) => {
    const [h, m] = time.split(':').map(Number);
    return h * 60 + m;
};

cron.schedule('*/5 * * * *', async () => {
    try {
        console.log('🔔 Running appointment reminder job');

        const nowIST = getISTDateTime();
        const reminderWindowStart = new Date(nowIST.getTime() + 25 * 60000);
        const reminderWindowEnd = new Date(nowIST.getTime() + 30 * 60000);

        const date = reminderWindowStart.toISOString().split('T')[0];

        const startMin = reminderWindowStart.getHours() * 60 + reminderWindowStart.getMinutes();
        const endMin = reminderWindowEnd.getHours() * 60 + reminderWindowEnd.getMinutes();

        const appointments = await Appointment.find({
            date,
            status: 'BOOKED',
            reminderSent: false,
        });

        for (const appt of appointments) {
            const apptStartMin = toMinutes(appt.startTime);

            // Check if appointment starts in 25–30 min window
            if (apptStartMin < startMin || apptStartMin > endMin) continue;

            const user = await Patient.findById(appt.patientId);
            if (!user?.fcmToken) continue;

            await fcm.sendNotification(
                user.fcmToken,
                'Appointment Reminder',
                'You have an appointment in 30 minutes'
            );

            appt.reminderSent = true;
            await appt.save();

            console.log(`✅ Reminder sent for appointment ${appt._id}`);
        }

    } catch (err) {
        console.error('❌ Reminder job failed:', err.message);
    }
}, {
    timezone: 'Asia/Kolkata',
});
