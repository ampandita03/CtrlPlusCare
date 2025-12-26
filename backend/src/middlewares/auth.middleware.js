const jwt = require('jsonwebtoken');

module.exports = (req, res, next) => {
    const authHeader = req.headers.authorization;

    console.log('AUTH HEADER:', authHeader);

    const token = authHeader?.split(' ')[1];

    console.log('RAW TOKEN:', token);

    if (!token) {
        return res.status(401).json({ message: 'Unauthorized' });
    }

    try {
        const decoded = jwt.verify(token, process.env.JWT_SECRET);
        console.log('DECODED TOKEN:', decoded);

        req.user = decoded;
        next();
    } catch (err) {
        console.log('JWT ERROR:', err.message);
        res.status(401).json({ message: 'Invalid token' });
    }
};
