# Book Review API

A RESTful API for managing books and reviews, built with Spring Boot. This API provides authentication, book management, and review functionality with JWT-based security.

## Features

- 🔐 **JWT Authentication** - Secure user authentication and authorization
- 📚 **Book Management** - Create, read, update, and delete books
- ⭐ **Review System** - Add, view, update, and delete reviews for books
- 👥 **User Management** - User registration and role-based access control
- 📄 **Pagination** - Paginated book listings
- 🗄️ **Database Migrations** - Liquibase for version-controlled database changes
- 📖 **API Documentation** - OpenAPI/Swagger documentation
- 🐳 **Docker Support** - Containerized deployment with Docker Compose

## Tech Stack

- **Java** 21
- **Spring Boot** 3.5.7
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Database persistence
- **PostgreSQL** - Relational database
- **Liquibase** - Database migration tool
- **JWT** (jjwt 0.11.5) - JSON Web Token authentication
- **Lombok** - Reducing boilerplate code
- **SpringDoc OpenAPI** - API documentation
- **Maven** - Build tool

## Prerequisites

- Java 21 or higher
- Maven 3.6+
- PostgreSQL 15+ (or use Docker Compose)
- Docker and Docker Compose (optional, for containerized deployment)

## Getting Started

### Local Development Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd api
   ```

2. **Set up PostgreSQL database**
   - Create a database named `bookapi`
   - Update database credentials in `application.properties` or use environment variables

3. **Configure environment variables**
   
   Create an `application-secret.properties` file in `src/main/resources/` with:
   ```properties
   SECRET_DB_PASSWORD=your_db_password
   SECRET_JWTSECRET=your_jwt_secret_key
   SECRET_JWTEXPIRATIONINMS=86400000
   ```
   
   Or set these as environment variables:
   - `SECRET_DB_PASSWORD`
   - `SECRET_JWTSECRET`
   - `SECRET_JWTEXPIRATIONINMS`

4. **Run database migrations**
   - Liquibase will automatically run migrations on application startup

5. **Build the project**
   ```bash
   mvn clean install
   ```

6. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

   The API will be available at `http://localhost:8081`

### Docker Setup

1. **Create a `.env` file** in the project root:
   ```env
   POSTGRES_DB=bookapi
   POSTGRES_USER=postgres
   POSTGRES_PASSWORD=your_password
   SECRET_DB_PASSWORD=your_password
   SECRET_JWTSECRET=your_jwt_secret_key
   SECRET_JWTEXPIRATIONINMS=86400000
   ```

2. **Build and run with Docker Compose**
   ```bash
   docker-compose up --build
   ```

   This will:
   - Start a PostgreSQL container
   - Build and start the Spring Boot application
   - Run database migrations automatically

3. **Access the API**
   - API: `http://localhost:8081`
   - API Documentation: `http://localhost:8081/swagger-ui.html`

## API Endpoints

### Authentication

- `POST /api/auth/register` - Register a new user
  ```json
  {
    "username": "user123",
    "password": "password123"
  }
  ```

- `POST /api/auth/login` - Login and get JWT token
  ```json
  {
    "username": "user123",
    "password": "password123"
  }
  ```
  Returns:
  ```json
  {
    "accessToken": "jwt_token_here"
  }
  ```

### Books

- `GET /api/books` - Get all books (with pagination)
  - Query parameters: `pageNumber` (default: 0), `pageSize` (default: 10)
  - Requires authentication

- `GET /api/books/{id}` - Get book by ID
  - Requires authentication

- `POST /api/books` - Create a new book
  - Requires authentication
  ```json
  {
    "title": "Book Title",
    "author": "Author Name",
    "description": "Book description"
  }
  ```

- `PUT /api/books/{id}` - Update a book
  - Requires authentication

- `DELETE /api/books/{id}` - Delete a book
  - Requires authentication

### Reviews

- `GET /api/book/{bookId}/reviews` - Get all reviews for a book
  - Requires authentication

- `GET /api/book/{bookId}/reviews/{id}` - Get a specific review
  - Requires authentication

- `POST /api/book/{bookId}/review` - Create a review for a book
  - Requires authentication
  ```json
  {
    "title": "Review Title",
    "content": "Review content",
    "stars": 5
  }
  ```

- `PUT /api/book/{bookId}/reviews/{id}` - Update a review
  - Requires authentication

- `DELETE /api/book/{bookId}/reviews/{id}` - Delete a review
  - Requires authentication

## Authentication

Most endpoints require JWT authentication. Include the token in the Authorization header:

```
Authorization: Bearer <your_jwt_token>
```

## API Documentation

Once the application is running, you can access the interactive API documentation at:
- Swagger UI: `http://localhost:8081/swagger-ui.html`
- OpenAPI JSON: `http://localhost:8081/v3/api-docs`

## Database Schema

The application uses the following main entities:
- **UserEntity** - User accounts with roles
- **Role** - User roles (ROLE_USER, etc.)
- **Book** - Book information
- **Review** - Reviews associated with books

Database migrations are managed by Liquibase and located in `src/main/resources/db/changelog/`.

## Testing

Run tests with Maven:
```bash
mvn test
```

The project includes:
- Unit tests for services
- Repository tests
- Controller tests with Spring Security test support

## Project Structure

```
src/
├── main/
│   ├── java/com/bookreview/api/
│   │   ├── controllers/     # REST controllers
│   │   ├── dto/             # Data Transfer Objects
│   │   ├── exceptions/      # Exception handlers
│   │   ├── models/          # Entity models
│   │   ├── repository/      # JPA repositories
│   │   ├── security/        # Security configuration
│   │   └── service/         # Business logic
│   └── resources/
│       ├── application.properties
│       └── db/changelog/    # Liquibase migrations
└── test/                    # Test files
```

## Configuration

Key configuration files:
- `application.properties` - Main application configuration
- `application-docker.properties` - Docker-specific configuration
- `application-secret.properties` - Secret values (not in version control)

## Building for Production

1. **Build the JAR file**
   ```bash
   mvn clean package
   ```

2. **Run the JAR**
   ```bash
   java -jar target/book-review.jar
   ```

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests if applicable
5. Submit a pull request

## License

This project is licensed under the MIT License.

