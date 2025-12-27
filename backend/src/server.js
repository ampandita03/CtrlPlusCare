require('dotenv').config();
const app = require('./app');
const connectDB = require('./config/db');
require('./modules/notifications/reminder.job');

const PORT = process.env.PORT || 5000;

connectDB().then(r => console.log(r));

app.listen(PORT, () => {
    console.log(`Server running on port ${PORT}`);
});
