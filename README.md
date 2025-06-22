# June-2025-microservice

📘 Overview This repository implements a Spring Boot microservices architecture featuring:

Service discovery via Eureka Server

Centralized configuration with Spring Cloud Config Server

Inter-service communication using Feign clients

A secure API Gateway built with Spring Cloud Gateway and a custom JwtAuthenticationFilter

Spring Security across the system

Two core services:

Employee Service – manages employee data (CRUD, business logic)

Department Service – handles department-related information

Authenticated, JWT‑protected access enforced through the API Gateway (filtering and token validation)

🧩 Tech Stack Spring Boot – the microservice framework

Feign Client – declarative REST client for inter-service calls

Spring Cloud Config Server – centralized configuration management

Eureka Server – service registry and discovery

Spring Cloud Gateway – API Gateway setup with route-based request handling

JwtAuthenticationFilter – custom filter for JWT verification in the gateway

Spring Security – securing endpoints within each microservice

Employee Service – RESTful microservice for employee management

Department Service – RESTful microservice for department management
