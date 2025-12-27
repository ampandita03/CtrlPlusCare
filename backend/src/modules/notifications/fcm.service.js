const admin = require('firebase-admin');
const serviceAccount = require('../../config/services-firebase.json');



admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
});

exports.sendNotification = async (token, title, body) => {
    if (!token) return;

    await admin.messaging().send({
        token,
        notification: {
            title,
            body,
        },
    });
};


