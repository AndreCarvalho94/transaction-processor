# Transaction Processor

This project is a transaction processing API. The application allows you to create accounts, add transactions associated with accounts, and check the balance of an account.

## Requirements
Before running the application, make sure the following are installed:

 - Java 17 or higher
 - Maven 3.6+
 - Docker and Docker Compose

## Setup
Clone the repository

    git clone https://github.com/your-user/transaction-processor.git
    cd transaction-processor
### Set up the Database

The application uses PostgreSQL as the main database. The database will be automatically configured via Testcontainers for integration tests and using Docker in the development environment.

## Running with Docker Compose
To set up the database using Docker Compose, use the docker-compose.yml file available in the project:

    docker-compose up -d
This will start the PostgreSQL service, with the tables being automatically created when the application starts.

### Database Configuration
   
The database connection can be configured via environment variables in the application.yml file or in your local setup. Here are the main variables used:


      spring:
         datasource:
            url: jdbc:postgresql://localhost:5432/transactions
            username: user
            password: password
      jpa:
         hibernate:
            ddl-auto: update
            show-sql: true
         properties:
            hibernate:
               dialect: org.hibernate.dialect.PostgreSQLDialect
If you're using Docker Compose, the database will be started with the correct credentials.

## Running the Application
1. Run Locally
   Once the database is set up, you can run the application locally with Maven:


      mvn spring-boot:run
This will start the application at 

      http://localhost:8080

2. Running with Docker Compose
   Alternatively, you can run the entire application, including the database, directly with Docker Compose:


      docker-compose up --build
This will build the application image and start the PostgreSQL and API containers.

3. Running Tests
   To run the integration tests, use the following command:

      
      mvn test
The integration tests use Testcontainers to spin up a PostgreSQL container, ensuring an isolated test environment.

## API Documentation
The API follows the OpenAPI standard. Once the application is running, you can access the API documentation via Swagger:

      http://localhost:8080/swagger-ui.html
This interface provides details about the available endpoints and allows you to make API calls directly.

## Main Endpoints
 - Create Account
  
   POST /accounts

        {
            "document_number": "12345678900"
        }
        Response:
        {
           "account_id": 1,
           "document_number": "12345678900"
        }

 - Create Transaction
 
   POST /transactions
   
         {
            "account_id": 1,
            "operation_type_id": 1,
            "amount": 100.00
         }
         Response:
         {
            "transaction_id": 1,
            "account_id": 1,
            "operation_type_id": 1,
            "amount": 100.00
         }