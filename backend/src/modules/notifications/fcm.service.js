const admin = require('firebase-admin');
const serviceAccount = require('../../config/ctrlpluscare-de810-firebase-adminsdk-fbsvc-9cd32c77ea.json');

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


