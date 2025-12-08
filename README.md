# API Security Sandbox: Spring Boot & JWT
This project serves as a Sandbox environment for learning and experimenting with API security mechanisms using Spring Boot Security and JSON Web Tokens (JWT) in Java.
The main goal is to understand how to protect a RESTful API from common threats such as Unauthorized Access, Brute Force attacks, and how to manage Authorization (access rights) based on user roles.

### Key Security Features Covered
| Feature | Description |
| :--- | :--- | 
| JWT Authentication | Generation, validation, and parsing of JSON Web Tokens for stateless communication. | 
| Role-Based Access Control | Managing access rights based on user roles. |
| Password Hashing | Securely hashing passwords before storage. | jjjaoant |
| Brute Force Protection | Mechanism to count and handle consecutive failed login attempts. |
| Admin Logging | Creating dedicated Loggers for suspicious events (ATTACK Log) and critical actions (AUDIT Log). |
| Security Filters | Custom filters integrated into the Spring Security chain. |

### Technology Stack
**Language:** Java
**Framework:** Spring Boot 3.x
**Security:** Spring Security, JWT
**Database:** PostgreSQL / H2 (In-memory for development)
**ORM:** Spring Data JPA / Hibernate
**Build Tool:** Maven
