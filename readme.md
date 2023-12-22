# Demo of Professional Reservation System

## Introduction
Greetings, My name is Simon, and I would like to share insights into the project I've been working on — a Reservation System designed to streamline the booking process for an airline company. In here, I'll provide an overview of the technologies used, the user interface, and the process of bringing this project to life.

## Project Overview
### Technologies Used
The Reservation System uses a robust stack of technologies:

**Backend**:

- Java 17
- Spring
- Postgresql 12
- Liquibase
- Hibernate
- Cucumber for end-to-end testing
- Spock
- Testcontainers

## The Development Process

### Requirements and Planning
The project began with a meticulous consideration of both functional and non-functional requirements. To visualize the system's structure, I used Whimsical to create diagrams and a comprehensive database relation diagram.

### Setting Up the Project
Using IntelliJ, the project was initiated, and in alignment with a **Domain-Driven Design (DDD)** approach, I used **hexagonal architecture**. This allowed for a clean separation of concerns and facilitated the implementation of domain-specific logic.

### Iterative Development Approach
1. **First Implementations:**
   - I started by implementing the Trip List feature, enabling users to search and filter available trips.
   - I added unit tests, and I implemented Cucumber end-to-end test to align with a Behavior-Driven Development (BDD) approach.
   - Implementing e2e test was a bit challenging. I had to solves issues like run Spring App and Database and fill it with test data automatically for e2e test.
2. **Evolution of backend service:**
   - With some initial implementation in place, I started aligning with Test-Driven Development (TDD) and Behavior-Driven Development (BDD) approach. I have to admit implementing all scenarios of e2e test before implementing actual code is quite helpful. It made me think about functionalities and my service API. Thanks to this approach later on I didn't have to make many changes in my code. 
   - I implemented other features like retrieving reservation details, canceling reservation and the main feature - making reservation.
   - Thanks to upfront implemented e2e test scenarios during development I didn't have to run the app and manually call endpoints for testing. I only run e2e test scenarios to validate if implemented feature works properly.
   - As the last feature I implemented seat lock system to lock seat after user choose it from UI. Thanks to this we can avoid issue with other customer reserving this same seat before first customer finishes his reservation.
3. **Kubernetes**
