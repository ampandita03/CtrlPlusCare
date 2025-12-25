const cron = require('node-cron');
const Appointment = require('../appointments/appointments.model');
const User = require('../auth/auth.model');
const fcm = require('./fcm.service');

cron.schedule('*/5 * * * *', async () => {
    try {
        console.log('Running appointment reminder job');

        const now = new Date();
        const reminderTime = new Date(now.getTime() + 30 * 60000);

        const date = reminderTime.toISOString().split('T')[0];
        const time = reminderTime.toTimeString().slice(0, 5);

        const appointments = await Appointment.find({
            date,
            startTime: time,
            status: 'BOOKED',
            reminderSent: false,
        });

        for (const appt of appointments) {
            const user = await User.findById(appt.patientId);
            if (!user?.fcmToken) continue;

            await fcm.sendNotification(
                user.fcmToken,
                'Appointment Reminder',
                'You have an appointment in 30 minutes'
            );

            appt.reminderSent = true;
            await appt.save();
        }
    } catch (err) {
        console.error('Reminder job failed:', err.message);
    }
}, {
    timezone: 'Asia/Kolkata',
});
