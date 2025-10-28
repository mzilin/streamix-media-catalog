# Streamix – Catalog Service

![Build](https://img.shields.io/github/actions/workflow/status/mzilin/streamix-media-catalog/build.yml?label=Build&logo=github&logoColor=white&style=flat)
![Coverage](https://img.shields.io/codecov/c/github/mzilin/streamix-media-catalog?label=Coverage&logo=codecov&logoColor=white&style=flat)
![Status](https://img.shields.io/badge/status-in_progress-yellow?label=Status)


This repository contains the **Catalog Service** microservice for the **Streamix** (Video Streaming Platform), deployed in the **Media** cluster. It is responsible for content creation, updates, retrieval and deletion, as well as media uploads.

For a complete system overview and links to all microservices, please refer to the [Microservices Hub Repository](https://github.com/mzilin/streamix-microservices-hub).


## Table of Contents

* [Introduction](#introduction)
* [Media Upload Flow](#media-upload-flow)
* [Technology Stack](#technology-stack)
* [Dependencies](#dependencies)
* [Setting Up Your Environment](#setting-up-your-environment)
  * [Prerequisites](#prerequisites)
  * [Installation & Running](#installation--running)
  * [Environment Variables](#environment-variables)
* [Testing](#testing)
* [Endpoints](#endpoints)
* [CI/CD & Deployment](#cicd--deployment)
* [Future Improvements](#future-improvements)
* [License](#license)
* [Contact](#contact)


## Introduction

The **Catalog Service** is a core component of the **Video Streaming Platform**, dedicated to managing the platform’s media catalog. It handles key responsibilities such as:

- **Content Creation**: Enables admins and content teams to add new media assets, including metadata like titles, descriptions and categories.
- **Media Upload Management**: Orchestrates secure, large-file uploads via pre-signed S3 URLs and integrates with downstream processing (e.g. transcoding).
- **Content Updates**: Supports modifications to existing content records, ensuring up-to-date and accurate information.
- **Content Retrieval**: Provides APIs for retrieving media details for display in client applications and other services.
- **Content Deletion**: Facilitates secure and compliant removal of media content, maintaining data integrity and consistency.

By centralising content management, the **Catalog Service** ensures a consistent and high-quality user experience, enabling users to browse and discover media efficiently.


## Media Upload Flow

This section illustrates how the Catalog Service enables secure media ingestion and orchestrates the automated processing pipeline. The flow below shows each step from metadata creation and multipart upload, through event-driven transcoding and publishing, to content indexing for discovery.

```mermaid
sequenceDiagram
  autonumber
  participant Admin Tool (SPA)
  participant Catalog Service
  participant S3
  participant SNS/SQS
  participant Transcoder
  participant Indexer

  Admin Tool (SPA)-->>Catalog Service: Create Media Metadata
  Catalog Service-->>Catalog Service: Generate media_id
  Catalog Service-->>S3: Get Presigned Multipart Upload URLs
  Catalog Service-->>Catalog Service: Save upload metadata
  Catalog Service-->>Admin Tool (SPA): Return media_id & presigned URLs

  par Parallel Upload
    Admin Tool (SPA)-->>Admin Tool (SPA): Split file into parts
    Admin Tool (SPA)-->>S3: Upload each part (with acceleration)
  end

  Admin Tool (SPA)-->>Catalog Service: Notify upload complete
  Catalog Service-->>S3: Finalise Multipart Upload
  Catalog Service-->>SNS/SQS: Publish media.uploaded event

  SNS/SQS-->>Transcoder: Trigger on media.uploaded event
  Transcoder-->>S3: Download uploaded media
  Transcoder-->>Transcoder: Generate video variants (1080p, 720p, etc.)
  Transcoder-->>SNS/SQS: Publish media.transcoded event

  SNS/SQS-->>Catalog Service: Trigger on media.transcoded event
  Catalog Service-->>Catalog Service: Update content with new video URLs and metadata
  Catalog Service-->>Catalog Service: Mark content ready for discovery
  Catalog Service-->>SNS/SQS: Publish media.published event

  SNS/SQS-->>Indexer: Trigger on media.published event
  Indexer-->>Indexer: Index content in Elasticsearch DB
```


## Technology Stack

This service is built using a modern, cloud-native Java stack, optimised for reactive, scalable microservices:

- **Java** `21`: LTS version with enhanced performance and modern language features.
- **Spring Boot** `3.4.5`: Rapid development framework for standalone, production-ready Java apps.
- **Spring Cloud** `2024.0.0`: Provides essential microservice components like config management, service discovery and API routing.
- **Gradle** `8.14`: Powerful build tool with fast incremental builds and powerful dependency management.
- **Docker**: Containerises apps for consistent, portable development and deployment.


## Dependencies

This project relies on a set of key libraries and frameworks that support its core functionality, infrastructure and testing:

- **Spring Boot**
  - **Actuator**: Exposes app health, metrics, and monitoring endpoints.
  - **RabbitMQ (AMQP)**: Facilitates robust, asynchronous messaging between microservices, enhancing scalability and decoupling.
  - **Cache**: Implements caching with Redis to improve performance and reduce database load.
  - **Data Redis**: Provides seamless integration with Redis for caching and data storage needs.
  - **Data MongoDB**: Simplifies integration with MongoDB, enabling efficient storage and retrieval of NoSQL data.
  - **Validation**: Provides declarative validation using JSR-380 annotations.
  - **Web**: Supports building RESTful endpoints and traditional MVC-based web applications.

- **Spring Cloud**
  - **Config Client**: Integrates with a centralised Spring Cloud Config Server for dynamic configuration management.
  - **Netflix Eureka Client**: Integrates with the Eureka Server for service registration and discovery.

- **Developer Experience**
  - **Lombok**: Reduces boilerplate with annotations for getters, setters, and constructors.
  - **DevTools**: Enables hot reloading for faster development iterations.

- **Testing**
  - **JUnit**: Framework for writing and running unit and integration tests in Java.
  - **Mockito**: Allows mocking and stubbing dependencies to isolate components during testing.

- **Code Quality**
  - **Jacoco**: Generates code coverage reports for ensuring test completeness.


## Setting Up Your Environment

Follow the steps below to set up your local development environment and run the application.


### Prerequisites

Ensure you have the following installed on your machine:
- [Java JDK 21](https://www.oracle.com/uk/java/technologies/downloads/#java21)
- [Gradle 8.14](https://gradle.org/)
- [Docker](https://docs.docker.com/get-started/get-docker/)
- [Docker Compose](https://docs.docker.com/compose/)


### Installation & Running

1. Clone the repository:
    ```bash
    git clone https://github.com/mzilin/streamix-media-catalog.git
    ```

2. Switch to the `main` branch:
    ```bash
    cd streamix-media-catalog
    ```

3. Build the project and run tests:
    ```bash
    ./gradlew clean build
    ```

4. Start the service:
    ```bash
    ./gradlew bootRun
    ```

   The service will start on http://localhost:8120 using the embedded Tomcat web server.


### Environment Variables

This microservice requires the following environment variable to be configured:

TBC


## Testing

This project uses a combination of **unit tests** and **integration tests** to ensure reliability, correctness and maintainability.

To execute all tests, run:
```bash
./gradlew test
```

This setup ensures that changes can be safely verified and that the codebase remains robust, maintainable and well-documented.


## Endpoints

TBC


## CI/CD & Deployment

This project uses **GitHub Actions** to automate the build, test and deployment processes, ensuring continuous integration and delivery.

- The **Build** workflow runs on every push and pull request to the `main` and `prod` branches. This step verifies that all commits compile successfully and pass unit tests before they are merged, maintaining code quality.
- The **Codecov** workflow runs on pushes to the main branch, where it executes tests, generates a Jacoco coverage report and uploads the results to Codecov.
- Finally, a **Deploy** workflow is triggered when a pull request is merged into the `prod` branch. It builds a Docker image of the microservice, pushes it to the **AWS ECR** container registry and deploys it to the **AWS ECS** environment.

Branch protections and **GitHub Secrets** are used to manage credentials and safeguard the CI/CD pipelines.


## Future Improvements

TBC


## License

This project is private and proprietary. Unauthorised copying, modification, distribution or use of this software, via any medium, is strictly prohibited without explicit written permission from the owner.


## Contact

For any questions or clarifications about the project, please [reach out](https://www.mariuszilinskas.com/contact) to the project owner.


------
###### © 2024–present Marius Zilinskas. All rights reserved.