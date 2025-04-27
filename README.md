# ProductService

## Overview
ProductService is a Spring Boot application designed to manage product-related operations for an e-commerce platform. It uses Java, Spring Boot, and JPA for backend development and SQL for database management.

## Features
- Product management (CRUD operations)
- Database auditing with JPA Auditing
- Maven-based project structure

## Technologies Used
- **Java**: Programming language
- **Spring Boot**: Framework for building the application
- **Spring Data JPA**: For database interaction
- **SQL**: Database management
- **Redis**: In-memory data management
- **Maven**: Dependency management and build tool

## Prerequisites
- Java 17 or higher
- Maven 3.8 or higher
- A running Redis instance
- A running SQL database (e.g., MySQL)

## Getting Started

### Clone the Repository
```bash
git clone https://github.com/rajvicky16/ProductService.git
cd productservice
```

### Build the Project
Run the following command to build the project:
```bash
mvn clean install
```

### Database Configuration
Update the application.properties file in the src/main/resources directory with your database credentials:
```bash
spring.datasource.url=jdbc:your_database_url
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

### Run the Application
You can run the ProductServiceApplication class directly from your IDE.
Alternatively, You can use the following command to start the application:
```bash
mvn spring-boot:run
```
  
## Endpoints
The application exposes RESTful APIs for managing products. Below are some example endpoints:

- `GET /products`: Retrieve all products  
- `POST /products`: Add a new product  
- `PUT /products/{id}`: Update an existing product  
- `DELETE /products/{id}`: Delete a product
