# Project Name
vml-backend-Java

## About

Java Spring Boot for core backend services.

### Key Features

- **Feature 1**: User Registration and Login
- **Feature 2**: Algorithm-Related Frontend and Backend Interaction


## Installation Guide

### Prerequisites

Before you begin installation, ensure you have the following software or tools installed:
- docker

### Installation Steps

1. **build image**

docker build -t backend:latest -f Dockerfile

2.**run container**
docker run --name my-spring-boot-app -d \
  -p 8080:8080 \
  -e "SPRING_OUTPUT_ANSI_ENABLED=ALWAYS" \
  -e "JAVA_OPTS=-Xmx512m -Xms256m" \
  Backend:latest

