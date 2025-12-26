const express = require('express');
const cors = require('cors');

const routes = require('./routes');
const swaggerUi = require('swagger-ui-express');
const swaggerSpec = require('./config/swagger');


const app = express();
app.use(cors());
app.use(express.json());
app.use((req, res, next) => {
    console.log('INCOMING:', req.method, req.url);
    next();
});

app.use('/api', routes);

app.use('/api-docs', swaggerUi.serve, swaggerUi.setup(swaggerSpec));

module.exports = app;
