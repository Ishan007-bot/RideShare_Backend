# RideShare Backend

A Spring Boot-based ride-sharing backend application with JWT authentication, MongoDB integration, and role-based access control. This project implements a complete REST API for managing ride requests, driver assignments, and ride completion.

## 🚀 Features

- **User Authentication & Authorization**
  - User registration with role-based access (ROLE_USER / ROLE_DRIVER)
  - JWT-based authentication
  - BCrypt password encoding
  - Secure API endpoints with role-based access control

- **Ride Management**
  - Users can request rides
  - Drivers can view and accept pending ride requests
  - Ride status tracking (REQUESTED → ACCEPTED → COMPLETED)
  - Users can view their ride history

- **Input Validation**
  - Jakarta Bean Validation
  - Comprehensive error handling
  - Global exception handler with structured error responses

## 🛠️ Tech Stack

- **Framework**: Spring Boot 3.2.0
- **Language**: Java 17
- **Database**: MongoDB (MongoDB Atlas)
- **Security**: Spring Security with JWT
- **Build Tool**: Maven
- **Validation**: Jakarta Validation API

## 📋 Prerequisites

- Java 17 or higher
- Maven 3.6+ (or use Maven Wrapper included)
- MongoDB Atlas account (or local MongoDB instance)
- Git

## ⚙️ Configuration

1. **Clone the repository**
   ```bash
   git clone https://github.com/Ishan007-bot/RideShare_Backend.git
   cd RideShare_Backend
   ```

2. **Configure Application Properties**
   
   Copy the example configuration file:
   ```bash
   cp src/main/resources/application.yaml.example src/main/resources/application.yaml
   ```

   Edit `src/main/resources/application.yaml` and update:
   - MongoDB connection URI
   - JWT secret key (minimum 64 characters recommended)
   - Server port (default: 8081)

   Example:
   ```yaml
   server:
     port: 8081
   
   spring:
     data:
       mongodb:
         uri: mongodb+srv://username:password@cluster.mongodb.net/database?appName=AppName
   
   rideshare:
     app:
       jwtSecret: YOUR_SECURE_JWT_SECRET_KEY_MIN_64_CHARACTERS
       jwtExpirationMs: 86400000
   ```

## 🏃 Running the Application

### Using Maven Wrapper (Recommended)
```bash
# Windows
.\mvnw.cmd spring-boot:run

# Linux/Mac
./mvnw spring-boot:run
```

### Using Maven (if installed)
```bash
mvn spring-boot:run
```

### Building and Running JAR
```bash
mvn clean package
java -jar target/uber-rideshare-1.0.0.jar
```

The application will start on `http://localhost:8081` (or the port specified in `application.yaml`).

## 📡 API Endpoints

### Authentication Endpoints (Public)

#### Register User
```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "john",
  "password": "1234",
  "role": "ROLE_USER"  // or "ROLE_DRIVER"
}
```

#### Login
```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "john",
  "password": "1234"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "type": "Bearer"
}
```

### Ride Endpoints (Protected)

All ride endpoints require JWT authentication. Include the token in the Authorization header:
```
Authorization: Bearer <your-jwt-token>
```

#### Request a Ride (ROLE_USER only)
```http
POST /api/v1/rides
Authorization: Bearer <token>
Content-Type: application/json

{
  "pickupLocation": "Koramangala",
  "dropLocation": "Indiranagar"
}
```

#### View My Rides (ROLE_USER only)
```http
GET /api/v1/user/rides
Authorization: Bearer <token>
```

#### View Pending Ride Requests (ROLE_DRIVER only)
```http
GET /api/v1/driver/rides/requests
Authorization: Bearer <token>
```

#### Accept a Ride (ROLE_DRIVER only)
```http
POST /api/v1/driver/rides/{rideId}/accept
Authorization: Bearer <token>
```

#### Complete a Ride (ROLE_USER or ROLE_DRIVER)
```http
POST /api/v1/rides/{rideId}/complete
Authorization: Bearer <token>
```

## 📊 API Summary

| Role | Endpoint | Method | Description |
|------|----------|--------|-------------|
| PUBLIC | `/api/auth/register` | POST | Register new user |
| PUBLIC | `/api/auth/login` | POST | Login and get JWT token |
| USER | `/api/v1/rides` | POST | Request a new ride |
| USER | `/api/v1/user/rides` | GET | View my rides |
| DRIVER | `/api/v1/driver/rides/requests` | GET | View pending requests |
| DRIVER | `/api/v1/driver/rides/{id}/accept` | POST | Accept a ride |
| USER/DRIVER | `/api/v1/rides/{id}/complete` | POST | Complete a ride |

## 🧪 Testing with cURL

### Register a User
```bash
curl -X POST http://localhost:8081/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"john","password":"1234","role":"ROLE_USER"}'
```

### Register a Driver
```bash
curl -X POST http://localhost:8081/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"driver1","password":"abcd","role":"ROLE_DRIVER"}'
```

### Login
```bash
curl -X POST http://localhost:8081/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"john","password":"1234"}'
```

### Create a Ride (replace TOKEN with actual JWT token)
```bash
curl -X POST http://localhost:8081/api/v1/rides \
  -H "Authorization: Bearer TOKEN" \
  -H "Content-Type: application/json" \
  -d '{"pickupLocation":"Koramangala","dropLocation":"Indiranagar"}'
```

## 📁 Project Structure

```
src/
├── main/
│   ├── java/com/ishan/
│   │   ├── config/          # Security and JWT configuration
│   │   ├── controller/      # REST API endpoints
│   │   ├── dto/            # Data Transfer Objects
│   │   ├── exception/      # Custom exceptions and handlers
│   │   ├── model/          # Entity models (User, Ride)
│   │   ├── repository/     # MongoDB repositories
│   │   ├── service/        # Business logic
│   │   └── util/           # Utility classes (JWT, Security)
│   └── resources/
│       ├── application.yaml.example  # Configuration template
│       └── application.yaml         # Your configuration (not in repo)
└── test/                   # Unit tests
```

## 🔒 Security Features

- JWT token-based authentication
- BCrypt password hashing
- Role-based access control (RBAC)
- Input validation
- Global exception handling
- CSRF protection disabled (stateless API)

## 📝 Error Responses

The API returns structured error responses:

```json
{
  "error": "BAD_REQUEST",
  "message": "Username is already taken!",
  "timestamp": "2025-12-06T15:30:00"
}
```

Common error types:
- `BAD_REQUEST` - Invalid input or business logic error
- `NOT_FOUND` - Resource not found
- `VALIDATION_ERROR` - Input validation failed

## 🗄️ Database Schema

### User Collection
```json
{
  "id": "string",
  "username": "string",
  "password": "string (BCrypt hashed)",
  "role": "ROLE_USER | ROLE_DRIVER"
}
```

### Ride Collection
```json
{
  "id": "string",
  "userId": "string (passenger ID)",
  "driverId": "string (nullable, driver ID)",
  "pickupLocation": "string",
  "dropLocation": "string",
  "status": "REQUESTED | ACCEPTED | COMPLETED",
  "createdAt": "Date"
}
```

## 🚨 Important Notes

- **Never commit `application.yaml`** - It contains sensitive credentials
- Use environment variables or a secrets manager in production
- JWT tokens expire after 24 hours (configurable)
- MongoDB connection requires proper network access (IP whitelist for Atlas)

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Ensure all tests pass
5. Submit a pull request

## 📄 License

This project is open source and available for educational purposes .

## 👤 Author 

Ishan007-bot

## 🔗 Repository

https://github.com/Ishan007-bot/RideShare_Backend.git

