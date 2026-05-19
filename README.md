# Java JDBC User Management System

A console-based user management application built with Java JDBC, demonstrating CRUD operations with PostgreSQL database.

## Features

- Create users with name, email, password, and profile URL
- Search users by UUID or name
- Update user information
- Delete users
- List all users in a formatted table

## Project Structure

```
src/
├── Main.java                      # Application entry point
├── controller/
│   └── UserController.java        # Request handling and routing
├── model/
│   ├── User.java                  # User entity
│   ├── UserDao.java               # Data access layer
│   ├── UserDatabase.java          # Database schema SQL
│   └── service/
│       ├── UserService.java       # Service interface
│       └── UserServiceImpl.java   # Service implementation
├── model/dto/
│   ├── CreateUserDto.java         # DTO for creating users
│   ├── UpdateRequestDto.java      # DTO for updating users
│   └── UserResponseDto.java       # DTO for user responses
├── mapper/
│   └── UserMapper.java            # Map between User and DTOs
├── utils/
│   ├── APIResponseTemplate.java   # API response wrapper
│   └── DataConnectionConfigure.java # Database connection config
└── view/
    └── UI.java                    # Console user interface
```

## Requirements

- Java 17+
- PostgreSQL 12+
- Lombok (for getter/setter generation)

## Setup

1. Create a PostgreSQL database named `user_db`
2. Update database credentials in `src/utils/DataConnectionConfigure.java`:
   ```java
   private static String username = "your_username";
   private static String password = "your_password";
   private static String url = "jdbc:postgresql://localhost:5432/user_db";
   ```

3. Run the SQL schema from `src/model/UserDatabase.java` to create the users table

## Running the Application

```bash
# Compile
javac -d out/production/JDBC src/**/*.java

# Run
java -cp out/production/JDBC Main
```

Or use your IDE to run `Main.java`

## Usage

```
==============|Welcome to Our System|===============
1. Create User
2. Search User by UUID
3. Search User by Name
4. Delete User by UUID
5. Update User by UUID
6. List All Users
0. Exit
```

## Database Schema

```sql
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    uuid VARCHAR(36) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    profile VARCHAR(255)
);
```