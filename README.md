# Task Management API

A full-stack RESTful API built with Spring Boot demonstrating **polyglot persistence** - using both PostgreSQL (SQL) and MongoDB (NoSQL) in a single application with JWT authentication.

## 🚀 Features

- ✅ **JWT Authentication** - Secure token-based authentication
- ✅ **User Management** - Registration and login
- ✅ **Task Management** - Full CRUD operations (PostgreSQL)
- ✅ **Note Management** - Flexible document storage (MongoDB)
- ✅ **Exception Handling** - Global error handling with custom exceptions
- ✅ **Unit Testing** - Comprehensive test coverage with JUnit & Mockito
- ✅ **Security** - Protected endpoints with Spring Security
- ✅ **Polyglot Persistence** - SQL and NoSQL in one application

## 🛠️ Tech Stack

### Backend
- **Framework:** Spring Boot 3.x
- **Language:** Java 17
- **Build Tool:** Maven

### Databases
- **PostgreSQL** - User accounts and task storage
- **MongoDB Atlas** - Flexible note storage

### Security
- **Spring Security** - Authentication and authorization
- **JWT** - JSON Web Token for stateless authentication
- **BCrypt** - Password encryption

### Testing
- **JUnit 5** - Unit testing framework
- **Mockito** - Mocking framework

## 📋 API Endpoints

### Authentication Endpoints (Public)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register new user |
| POST | `/api/auth/login` | Login and receive JWT token |

### Task Endpoints (Protected - PostgreSQL)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/tasks` | Get all tasks for current user |
| GET | `/api/tasks/{id}` | Get task by ID |
| POST | `/api/tasks` | Create new task |
| PUT | `/api/tasks/{id}` | Update task |
| DELETE | `/api/tasks/{id}` | Delete task |

### Note Endpoints (Protected - MongoDB)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/notes` | Get all notes |
| GET | `/api/notes/{id}` | Get note by ID |
| GET | `/api/notes/search?title={keyword}` | Search notes by title |
| GET | `/api/notes/tag/{tag}` | Get notes by tag |
| POST | `/api/notes` | Create new note |
| PUT | `/api/notes/{id}` | Update note |
| PATCH | `/api/notes/{id}/archive` | Archive note |
| DELETE | `/api/notes/{id}` | Delete note |

## 🔧 Setup & Installation

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+
- MongoDB Atlas account (or local MongoDB)
- Git

### 1. Clone the Repository
```bash
git clone https://github.com/eyalg43/taskapi.git
cd taskapi
```

### 2. Setup PostgreSQL
```bash
# Create database
createdb taskdb

# Or using psql
psql -U postgres
CREATE DATABASE taskdb;
```

### 3. Setup MongoDB Atlas

1. Create free account at [MongoDB Atlas](https://www.mongodb.com/cloud/atlas)
2. Create a cluster (M0 - Free tier)
3. Create database user
4. Whitelist your IP (or use 0.0.0.0/0 for development)
5. Get connection string

### 4. Configure Application

Create `src/main/resources/application.properties`:
```properties
# PostgreSQL Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/taskdb
spring.datasource.username=YOUR_POSTGRES_USERNAME
spring.datasource.password=YOUR_POSTGRES_PASSWORD
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# MongoDB Configuration
spring.data.mongodb.uri=mongodb+srv://USERNAME:PASSWORD@cluster0.xxxxx.mongodb.net/notesdb?retryWrites=true&w=majority

# Server Configuration
server.port=8080
```

### 5. Build and Run
```bash
# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

## 📖 Usage Examples

### 1. Register a User
```bash
POST http://localhost:8080/api/auth/register
Content-Type: application/json

{
  "username": "john",
  "password": "password123",
  "email": "john@example.com"
}
```

**Response:**
```json
{
  "message": "User registered successfully",
  "username": "john"
}
```

### 2. Login
```bash
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "username": "john",
  "password": "password123"
}
```

**Response:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huIiwiaWF0IjoxNjMyMTU...",
  "username": "john"
}
```

### 3. Create a Task (PostgreSQL)
```bash
POST http://localhost:8080/api/tasks
Authorization: Bearer YOUR_JWT_TOKEN
Content-Type: application/json

{
  "title": "Complete project",
  "description": "Finish the Spring Boot API",
  "completed": false
}
```

### 4. Create a Note (MongoDB)
```bash
POST http://localhost:8080/api/notes
Authorization: Bearer YOUR_JWT_TOKEN
Content-Type: application/json

{
  "title": "Meeting Notes",
  "content": "Discussed project timeline and deliverables",
  "tags": ["meeting", "project", "important"]
}
```

## 🧪 Running Tests
```bash
# Run all tests
mvn test

# Run tests with coverage
mvn test jacoco:report
```

## 🏗️ Project Structure
```
taskapi/
├── src/
│   ├── main/
│   │   ├── java/com/example/taskapi/
│   │   │   ├── controller/          # REST controllers
│   │   │   │   ├── AuthController.java
│   │   │   │   ├── TaskController.java
│   │   │   │   └── NoteController.java
│   │   │   ├── model/               # Data models
│   │   │   │   ├── User.java        (PostgreSQL)
│   │   │   │   ├── Task.java        (PostgreSQL)
│   │   │   │   └── Note.java        (MongoDB)
│   │   │   ├── repository/          # Data access layer
│   │   │   │   ├── UserRepository.java
│   │   │   │   ├── TaskRepository.java
│   │   │   │   └── NoteRepository.java
│   │   │   ├── service/             # Business logic
│   │   │   │   ├── AuthService.java
│   │   │   │   ├── TaskService.java
│   │   │   │   └── NoteService.java
│   │   │   ├── security/            # Security configuration
│   │   │   │   ├── JwtUtil.java
│   │   │   │   ├── JwtAuthenticationFilter.java
│   │   │   │   └── SecurityConfig.java
│   │   │   ├── exception/           # Exception handling
│   │   │   │   ├── GlobalExceptionHandler.java
│   │   │   │   ├── ResourceNotFoundException.java
│   │   │   │   └── ...
│   │   │   └── TaskapiApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/com/example/taskapi/
│           └── service/
│               └── TaskServiceTest.java
└── pom.xml
```

## 🔐 Security

- Passwords are encrypted using BCrypt
- JWT tokens expire after 24 hours
- All endpoints except `/api/auth/**` require authentication
- Users can only access their own tasks
- CSRF protection is disabled (stateless API)

## 🎯 Key Concepts Demonstrated

### 1. Polyglot Persistence
- Using both SQL (PostgreSQL) and NoSQL (MongoDB)
- Each database for its strengths:
    - PostgreSQL: Structured data with relationships (Users, Tasks)
    - MongoDB: Flexible documents (Notes with varying fields)

### 2. RESTful API Design
- Proper HTTP methods (GET, POST, PUT, PATCH, DELETE)
- Appropriate status codes (200, 201, 204, 404, 500)
- Resource-based URLs

### 3. Authentication & Authorization
- Stateless JWT authentication
- Role-based access control
- Secure password storage

### 4. Exception Handling
- Global exception handler
- Custom exceptions
- Consistent error responses

### 5. Testing
- Unit tests with mocking
- Service layer testing
- Test coverage reporting

## 📚 Learning Resources

This project was built following a structured learning path:
- **Days 1-2:** Spring Boot basics and REST API
- **Day 3:** PostgreSQL integration
- **Day 4:** JWT authentication
- **Day 5:** Exception handling and testing
- **Day 6:** MongoDB integration
- **Day 7:** Code review and polish

## 🚧 Future Enhancements

- [ ] Add pagination for list endpoints
- [ ] Implement role-based authorization (ADMIN, USER)
- [ ] Add API documentation with Swagger/OpenAPI
- [ ] Docker containerization
- [ ] CI/CD pipeline with GitHub Actions
- [ ] Deploy to AWS/Cloud platform
- [ ] Add caching with Redis
- [ ] Implement refresh tokens

## 👤 Author

**Your Name**
- GitHub: [@eyalg43](https://github.com/eyalg43)
