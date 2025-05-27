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

## Running with Docker

This project can be built and run using Docker and Docker Compose.

### Prerequisites

*   Docker: [Install Docker](https://docs.docker.com/get-docker/)
*   Docker Compose: [Install Docker Compose](https://docs.docker.com/compose/install/) (usually included with Docker Desktop)

### Building and Running

1.  **Clone the repository:**
    ```bash
    git clone <repository-url>
    cd <repository-name>
    ```

2.  **Environment Variables:**
    This application uses environment variables for configuration. The `docker-compose.yml` file is set up to pass some of these through from your host environment or you can set them directly.
    Ensure you have a `.env` file in the project root or that the required environment variables are set in your shell. Key variables include:
    *   `SPRING_DATASOURCE_URL` (if not using the default `db` service name in `docker-compose.yml`)
    *   `SPRING_DATASOURCE_USERNAME`
    *   `SPRING_DATASOURCE_PASSWORD`
    *   `JWT_SECRET_KEY`
    *   `SUPPORT_EMAIL`
    *   `APP_PASSWORD`

    The `docker-compose.yml` file defines a PostgreSQL service. You will need to replace the placeholder values for `POSTGRES_DB`, `POSTGRES_USER`, and `POSTGRES_PASSWORD` in `docker-compose.yml` or ensure the application service's environment variables (`SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`) match these.

3.  **Build and run the application using Docker Compose:**
    ```bash
    docker-compose up --build
    ```
    This command will:
    *   Build the Docker image for the application as defined in the `Dockerfile`.
    *   Start containers for the application and the PostgreSQL database.
    *   The application will be accessible at `http://localhost:1000`.

4.  **To stop the application:**
    Press `CTRL+C` in the terminal where `docker-compose up` is running, or run:
    ```bash
    docker-compose down
    ```

### Building the JAR file (if needed separately)

If you need to build the JAR file manually (e.g., before running `docker build` without compose), you can use the Gradle wrapper:

*   On Linux/macOS:
    ```bash
    ./gradlew build
    ```
*   On Windows:
    ```bash
    gradlew.bat build
    ```
    The JAR file will be located in `build/libs/`. The Dockerfile expects this location.
```
