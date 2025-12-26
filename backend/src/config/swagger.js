const swaggerJsdoc = require('swagger-jsdoc');

const options = {
    definition: {
        openapi: '3.0.0',
        info: {
            title: 'CtrlPlusCare API',
            version: '1.0.0',
            description: 'API documentation for CtrlPlusCare backend',
        },
        servers: [
            {
                url: 'http://localhost:5050',
                description: 'Local server',
            },
            {
                url: 'https://13.60.22.16',
                description: 'Production server',
            },
        ],
        components: {
            securitySchemes: {
                bearerAuth: {
                    type: 'http',
                    scheme: 'bearer',
                    bearerFormat: 'JWT',
                },
            },
        },
    },
    apis: ['./src/**/*.js']
};

module.exports = swaggerJsdoc(options);
