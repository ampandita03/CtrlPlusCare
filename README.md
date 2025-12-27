# CtrlPlusCare – Smart Healthcare Appointment Platform

CtrlPlusCare is a mobile-first healthcare appointment platform built to simplify doctor discovery, appointment booking, emergency consultations, and real-time notifications.

The project is developed as part of a hackathon / placement-ready system, focusing on clean backend architecture, real-world flows, and scalability.

# Key Features
- Patient

Phone number + OTP authentication
Create and manage patient profile
Discover doctors by specialty & location
Book appointments with real-time slot availability
Emergency booking (same-day, priority slots)
Appointment reminders via push notifications
Appointment history & cancellation

- Doctor

Doctor profile with clinic details
Set availability slots (date-wise)
View upcoming appointments
Emergency appointment handling
Ratings & consultation fees

- Emergency Booking

Same-day emergency appointments only
Automatically picks nearest available slot
Higher emergency consultation fee
Separate emergency workflow

- Notifications

Firebase Cloud Messaging (FCM)
Appointment booking notifications
Cancellation notifications
Automated reminders via cron jobs
Persistent notification storage per user



# System Architecture
Frontend : Kotlin + JetPack Compose
Backend: Node.js + Express
Database: MongoDB (Mongoose ODM)
Authentication: OTP-based login with JWT
Notifications: Firebase Cloud Messaging (FCM)
Scheduling: node-cron (appointment reminders)
API Documentation: Swagger (OpenAPI)
Deployment: Docker + AWS EC2

 # API Documentation (Swagger)
 All APIs are documented using Swagger (OpenAPI) and are publicly accessible.
 
 
 # 🔗 Swagger URL
 http://13.60.22.16:5050/api-docs/#


# Features of Swagger Docs:
One-click API access
Request & response schemas
Live API testing
Public (no auth required for viewing)



⚙️ How to Run Locally

1️⃣ Clone Repository
git clone https://github.com/<your-username>/ctrlpluscare-backend.git
cd ctrlpluscare-backend

2️⃣ Install Dependencies
npm install

3️⃣ Environment Variables (.env)
PORT=5050
MONGO_URI=mongodb+srv://<user>:<password>@cluster.mongodb.net/db
JWT_SECRET=your_secret_key
JWT_EXPIRES_IN=7d


4️⃣ Run Server
npm run dev

5️⃣ Open Swagger
http://13.60.22.16:5050/api-docs/#


🐳 Docker Setup

Build Image
docker build -t ctrlpluscare-backend .


Run Container
docker run -p 5050:5050 --env-file .env ctrlpluscare-backend



🧪 Testing
APIs tested using Postman
Push notifications tested using FCM test tokens
Edge cases handled:
Double booking prevention
Date-based slot validation
Role-based access



📜 License
This project is for educational and hackathon purposes.

⭐ If you like this project, don’t forget to star the repo!
