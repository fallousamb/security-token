# Security Token Project

## Overview

The Security Token Project is a Java-based application that provides authentication and authorization services using
JSON Web Tokens (JWT). It includes mechanisms for user sign-in, token generation, and refresh token handling.

## Features

- User authentication with email and password
- JWT generation for authenticated users
- Support for refresh tokens
- Role-based access control (User and Admin roles)
- Maven Wrapper for easy build and execution

## Technologies Used

- Spring Boot
- Spring Security
- JSON Web Tokens (JWT)
- Maven

## Getting Started

### Prerequisites

- Java JDK 8 or later
- Maven

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/security-token-project.git
   cd security-token-project
   ```

2. Build the project using Maven:
   ```bash
   ./mvnw clean install
   ```

### Running the Application

1. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

2. Access the application at `http://localhost:8080`.

## API Endpoints

- `POST /api/v1/auth/signin` - Authenticate a user and receive a JWT
- `GET /api/v1/user/hello` - Greet the user (requires user role)
- `GET /api/v1/admin` - Access admin features (requires admin role)

## Contributing

1. Fork the repository.
2. Create a new feature branch.
3. Commit your changes.
4. Push to the branch.
5. Open a Pull Request.

## License

This project is licensed under the Apache License 2.0. See the [LICENSE](LICENSE) file for more details.