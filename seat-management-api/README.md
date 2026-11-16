# Seat Management System – Automated CI/CD Pipeline & Kubernetes Deployment

Enterprise Java/Spring Boot 3.4.3 REST API for Study Hall & Lab Seat Management (`com.seat.management`), complete with JUnit 5 integration tests, Jenkins declarative CI pipeline, Ansible local deployment role, non-root Docker containerization, and Kubernetes manifests.

## Endpoints
- `GET /seats` | `GET /seats/available` | `GET /seats/zone/{zone}` | `GET /seats/floor/{floor}` | `GET /seats/{id}`
- `POST /seats` | `PUT /seats/{id}` | `PATCH /seats/{id}/status` | `DELETE /seats/{id}`
- `POST /reservations` | `GET /reservations` | `GET /reservations/active` | `GET /reservations/{id}`
- `PUT /reservations/{id}/release` | `PUT /reservations/{id}/cancel`
- `GET /actuator/health`
