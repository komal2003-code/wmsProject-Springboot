## ER Diagram
![ER Diagram](er-diagram.png)

# Warehouse Management System (WMS)

A full-stack Warehouse Management System developed using Spring Boot, React.js, MySQL, and JWT Authentication.  
The system is designed to manage warehouse inventory, products, authentication, and user roles efficiently through a secure and responsive web application.

This project provides complete inventory management functionality including product management, stock tracking, secure login authentication, and role-based access control for administrators and operators.

---

# Project Description

The Warehouse Management System (WMS) is a web-based inventory management application built to simplify warehouse operations and product tracking.

The application allows administrators to:
- Add, update, delete, and manage products
- Monitor inventory quantity
- Manage user authentication and authorization
- Control access using roles (ADMIN / OPERATOR)

Operators can:
- View products
- Manage stock operations
- Access dashboard features based on permissions

The project follows a full-stack architecture:
- Backend developed using Spring Boot REST APIs
- Frontend developed using React.js
- MySQL used as the relational database
- JWT used for secure authentication
- Spring Security used for authorization and API protection

The system also includes unit testing using JUnit 5 and Mockito for service layer validation and repository mocking.

---

# Features

## Backend Features
- Spring Boot REST APIs
- JWT Authentication & Authorization
- Role-Based Access (ADMIN / OPERATOR)
- CRUD Operations for Products
- Inventory Quantity Management
- MySQL Database Integration
- Spring Security Configuration
- Exception Handling
- Layered Architecture
- Unit Testing using JUnit 5 & Mockito

## Frontend Features
- React.js User Interface
- Login Authentication
- Dashboard UI
- Product Management Pages
- API Integration using Axios
- Responsive Design
- Form Validation
- Navigation Sidebar

---

# Technologies Used

## Backend
- Java
- Spring Boot
- Spring Security
- JWT Authentication
- Spring Data JPA
- Hibernate
- MySQL
- Maven

## Frontend
- React.js
- Axios
- Bootstrap
- CSS

## Testing
- JUnit 5
- Mockito

## Tools
- Postman
- Git & GitHub
- STS / IntelliJ IDEA
- VS Code

---

# Project Architecture

The project follows layered architecture:

- Controller Layer
- Service Layer
- Repository Layer
- Entity Layer
- Security Layer

This structure improves:
- Code maintainability
- Scalability
- Testability
- Separation of concerns

---

# Project Structure

backend/
│
├── controller/
├── service/
├── repository/
├── entity/
├── security/
├── config/
├── exception/
└── test/

frontend/
│
├── src/pages/
├── src/components/
├── src/services/
├── src/context/
└── src/styles/

---

# Installation & Setup

## Backend Setup

### 1. Clone Repository

```bash
git clone https://github.com/komal2003-code/wmsProject-Springboot.git