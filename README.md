# Spring Boot JWT Authentication with Email Verification

A simple and secure Spring Boot application that supports user registration, email verification, and JWT-based login.

---

## Features

- User registration
- Email verification via verification code
- Resend verification code
- Secure login using JWT (HS256)
- Stateless session with token-based authentication
- Built with Spring Security and JPA

---

## Technologies Used

- Java 17+
- Spring Boot 3.x
- Spring Security 6
- JSON Web Token (jjwt 0.11.5)
- JPA + Hibernate
- H2 / PostgreSQL (configurable)
- Lombok
- Maven

---

## API Endpoints

| Method | URL                        | Description                     |
|--------|----------------------------|---------------------------------|
| POST   | `/register`                | Register a new user             |
| POST   | `/login`                   | Authenticate and receive JWT    |
| POST   | `/resend-verification`     | Resend verification code email  |

---

## Request Examples

### 📌 Register

**POST** `/register`

```json
{
  "userName": "john",
  "email": "john@example.com",
  "password": "test123"
}
