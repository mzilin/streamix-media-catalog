# Video Streaming Platform - Content Management Service


## Introduction

The `Content Service` is an integral component of the `Video Streaming Platform`, responsible for content creation, updates, retrieval and deletion.

Built with Spring Boot, this microservice uses MongoDB as its database, providing a robust and scalable solution for data storage and manipulation.


## Technologies Used
This microservice utilises a comprehensive suite of technologies and dependencies, ensuring robust and scalable functionality:

- **Spring Boot** `3.2.5`:
  - **Actuator**: Monitors and manages the application, providing insights into runtime operations and health.
  - **RabbitMQ (AMQP)**: Facilitates robust, asynchronous message communication between microservices, enhancing scalability and decoupling components.
  - **Cache**: Implements caching using Redis to enhance performance and reduce database load.
  - **Data Redis**: Integrates with Redis for caching and other data storage needs.
  - **Data MongoDB**: Simplifies access to MongoDB, allowing storage and retrieval of NoSQL data.
  - **Validation**: Ensures that incoming data meets the application's expectations, crucial for maintaining data integrity and proper functioning.
  - **Web**: Enables building web-based applications and services, supporting RESTful endpoints and traditional MVC setups.

- **Spring Cloud** `2023.0.1`:
  - **Config**: Manages externalised configuration, allowing applications to fetch their settings from a centralized source.
  - **Netflix Eureka Client**: Enables the microservice to register with a Eureka server for service discovery.

- **Java** `JDK 17`: Essential for secure, portable, high-performance software development.

- **Lombok**: Reduces boilerplate in Java code significantly, automating the generation of getters, setters, constructors, and other common methods.

- **Testing**:
  - **Mockito Core** `5.3.1`: Provides essential mocking capabilities for unit testing, thereby facilitating thorough and effective test cases.


### Dependency Management

- **Gradle**: A powerful build automation tool that streamlines the compilation, testing, and deployment processes for software projects.


### Containerization

- **Docker** (Optional): Automates OS-level virtualization on Windows and Linux.


## Requirements

To successfully set up and run the application, ensure you have the following installed:

- [Java JDK 17](https://www.oracle.com/uk/java/technologies/downloads/#java17)
- [Gradle](https://gradle.org/)
- [Docker](https://docs.docker.com/get-docker/) (optional)

Ensure that PostgreSQL is correctly set up and running, as it is required for the data storage.


## Installation

Follow these steps to get the User Service up and running:

1. Navigate into the app's directory
```shell
cd vsp-content-service
```

2. Clean and build the microservice

```shell
./gradlew clean build
```

3. Start the microservice

```shell
./gradlew bootRun
```


## Testing

Ensure the application is working as expected by executing the unit tests:

```shell
./gradlew clean test
```


## License

This project is private and proprietary. Unauthorised copying, modification, distribution, or use of this software, via any medium, is strictly prohibited without explicit permission from the owner.


## Contact

For any questions or clarifications about the project, please reach out to the project owner via [www.mariuszilinskas.com](https://www.mariuszilinskas.com).

Marius Zilinskas

------

###### All rights are reserved. - Marius Zilinskas, 2024 to present