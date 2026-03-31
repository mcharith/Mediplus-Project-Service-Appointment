# Appointment-Service

---

A dedicated microservice responsible for managing **patient channeling and appointment bookings**.  
It integrates with the Patient-Service via REST APIs to enrich appointment data with patient details, ensuring a complete and connected workflow across the system.

---

## 👤 Student Information

- **Student Name:** Charith Mihiranga Siriwardana
- **Student Number:** 2301691075
- **Slack:** https://ijse-eca-hdse-69-70.slack.com/team/U0AHD5TQ4H5
- **GCP Project ID:** its-2130-eca-gdse-491417

---

## 📖 About

Appointment-Service handles the complete lifecycle of appointment scheduling within the Channeling Center Management System. It allows users to create, update, retrieve, and delete appointment records while maintaining relationships between patients and doctors.

The service communicates with the Patient-Service to fetch additional patient information, enabling enriched responses without duplicating data. Built using a microservice architecture, it ensures scalability, loose coupling, and high maintainability.

Using **Spring Boot** and **Spring Cloud**, the service integrates seamlessly with the API Gateway and Service Registry for centralized routing and service discovery. Data is persisted in **MySQL**, and the application follows best practices such as DTO mapping with **MapStruct**, validation with **Spring Validation**, and reduced boilerplate using **Lombok**.

---

## 🛠️ Tech Stack

| Technology                       | Details                                           |
|----------------------------------|--------------------------------------------------|
| Java                              | 25                                               |
| Spring Boot                        | 4.0.3                                            |
| Spring Cloud                       | 2025.1.0                                         |
| Spring Data JPA                   | ORM / persistence layer                           |
| MySQL                             | Relational database (port 14500)                 |
| MapStruct                         | DTO ↔ Entity mapping                              |
| Lombok                            | Boilerplate reduction                             |
| Spring Validation                 | Bean validation                                   |
| Spring Cloud Netflix Eureka Client| Service registration & discovery                |
| Spring Cloud Config Client        | Fetches config from Config-Server               |
| Spring Boot Actuator              | Health & management endpoints                     |

---

## ⚙️ Service Details

| Property      | Value                                                         |
|---------------|---------------------------------------------------------------|
| Port          | 8003                                                          |
| Artifact ID   | appointment-service                                           |
| Group ID      | lk.ijse.eca                                                   |
| Database      | MySQL — jdbc:mysql://localhost:14500/medi-plus               |

---

## 📡 API Endpoints

**Base path:** `/api/v1/appointment`

| Method | Path                                   | Description                          | Content-Type      |
|--------|----------------------------------------|--------------------------------------|------------------|
| POST   | /api/v1/appointment                    | Create a new appointment             | application/json |
| GET    | /api/v1/appointment                    | Get all appointments                 | —                |
| GET    | /api/v1/appointment?doctorId={id}      | Get appointments filtered by doctor  | —                |
| GET    | /api/v1/appointment/{id}               | Get an appointment by ID             | —                |
| PUT    | /api/v1/appointment/{id}               | Update an appointment                | application/json |
| DELETE | /api/v1/appointment/{id}               | Delete an appointment                | —                |

---

## 📝 Sample Request Body

> Requests must use `Content-Type: application/json`

```json
{
  "patientId": "123456789V",
  "doctorId": "DOC",
  "date": "2025-03-30"
}
```

---

## 🚀 Getting Started

### Prerequisites

- Config-Server, Service-Registry, and API Gateway must be running
- Patient-Service and Doctor-Service should be available for full functionality
- A MySQL instance must be running on port `14500`

---

### 🔄 Startup Order

1. Config-Server (9100)
2. Service-Registry (9001)
3. API Gateway (7001)
4. Patient-Service (8001)
5. Doctor-Service (8002)
6. Appointment-Service (8003)

---

### ▶️ Run the Service

```bash
./mvnw spring-boot:run
```

The service will start on **port 8003**.
