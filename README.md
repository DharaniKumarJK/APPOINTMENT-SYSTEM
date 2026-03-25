# Appointment Management System

A Spring Boot-based backend for managing doctor appointments with JWT security and role-based access control.

## Technologies
- Java 21
- Spring Boot 3.2.5
- Spring Security & JWT
- MySQL
- Hibernate/JPA
- Swagger/OpenAPI

## Getting Started

### Prerequisites
- JDK 21
- Maven
- MySQL Server

### Database Configuration
Update `src/main/resources/application.properties` with your MySQL credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/appointment_db
spring.datasource.username=root
spring.datasource.password=your_password
```

## API Documentation

The visual API documentation is available via Swagger UI:
- **URL**: `http://localhost:8080/swagger-ui.html`

### Authentication Endpoints

#### 1. Common Login
Authenticates any registered user and returns a JWT token.
- **Endpoint**: `POST /api/v1/auth/authenticate`
- **Body**:
```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

#### 2. Admin Registration
- **Endpoint**: `POST /api/v1/auth/register/admin`
- **Body**:
```json
{
  "name": "Admin Name",
  "email": "admin@example.com",
  "password": "password123"
}
```

#### 3. Doctor Registration
Captures doctor-specific details.
- **Endpoint**: `POST /api/v1/auth/register/doctor`
- **Body**:
```json
{
  "name": "Dr. Smith",
  "email": "doctor@example.com",
  "password": "password123",
  "specialization": "Cardiology",
  "experienceYears": 10
}
```

#### 4. Patient Registration
Captures patient-specific details.
- **Endpoint**: `POST /api/v1/auth/register/patient`
- **Body**:
```json
{
  "name": "John Doe",
  "email": "patient@example.com",
  "password": "password123",
  "age": 30,
  "gender": "Male"
}
```

### Planned Business Endpoints (To be implemented)

#### Patient Endpoints
- `GET /api/v1/patient/doctors`: List all doctors.
- `GET /api/v1/patient/doctors/{id}/availability`: View specific doctor's available slots.
- `POST /api/v1/patient/appointments`: Book a new appointment.
- `GET /api/v1/patient/appointments`: View my appointments.
- `DELETE /api/v1/patient/appointments/{id}`: Cancel an appointment.

#### Doctor Endpoints
- `POST /api/v1/doctor/availability`: Set/Update working hours.
- `GET /api/v1/doctor/appointments`: View my scheduled appointments.
- `PUT /api/v1/doctor/appointments/{id}/status`: Update appointment status (COMPLETE/CANCELLED).

#### Admin Endpoints
- `GET /api/v1/admin/users`: View all users.
- `DELETE /api/v1/admin/users/{id}`: Delete a user.
- `GET /api/v1/admin/appointments`: View all appointments in the system.

## Security
- All endpoints under `/api/v1/auth/**` are public.
- Swagger UI (`/swagger-ui/**`, `/v3/api-docs/**`) is public.
- All other endpoints require a valid JWT token in the `Authorization` header as a Bearer token.
- Role-based access is enforced:
  - `/api/v1/admin/**` requires `ROLE_ADMIN`
  - `/api/v1/doctor/**` requires `ROLE_DOCTOR`
  - `/api/v1/patient/**` requires `ROLE_PATIENT`
