# Demo Reservation System

## Introduction
Greetings, My name is Simon, and I would like to share insights into the project I've been working on — a Reservation System designed to streamline the booking process for an airline company. In here, I'll provide an overview of the technologies used, the user interface, and the process of bringing this project to life.

## Project Overview
The project consists of 3 main components, Reservation Service, Payment Service, and Frontend.

<img src="https://github.com/mrozowski/reservation-lock-demo/assets/67066372/d37b18a9-4a73-4c2c-947d-46f10577f6cb" alt="main-project-diagram" width="70%"/>

### Main features
- Searching trips
- Making reservation
- Making payment
- Checking reservation details
- Canceling reservation
- Seat Locking - to give a user enough time to finish payment without worrying about someone else reserving this same seat 

### Technologies Used
The Reservation System uses a robust stack of technologies:

| Reservation Service | Payment Service | Frontend | Other      |
|---------------------|-----------------|----------|------------|
| Java 17             | Golang 1.21     | React.js | Docker     |
| Spring Boot 3.2.0   |                 | axios    | Kubernetes |
| Postgresql 12       |                 |          | Minikube   |
| Liquibase           |                 |          | Intellij   |
| Hibernate           |                 |          | Figma      |
| Cucumber            |                 |          | Whimsical  |
| Spock               |                 |          |            |
| Testcontainers      |                 |          |            |


### Payment process
I tried to make the payment process similar to the Stripe flow
1. The user sends a request to backend to make a payment.
2. Backend calls Payment Service to create new Payment intent with client secret key.
3. Backend returns client secret to Frontend user.
4. The frontend user calls Payment Service to make a payment using a bank card.
5. After payment is finished Payment Service automatically calls Backend using webhook.
6. Backend finish processing payment.

<img src="https://github.com/mrozowski/reservation-lock-demo/assets/67066372/c13ef325-c0be-4c37-ba75-f20be855132b" alt="main-project-diagram" width="100%"/>


### Frontend
Main page with trip search
![image](https://github.com/mrozowski/reservation-lock-demo/assets/67066372/9b85713d-190e-4afa-925c-f8191b354f2b)


Reservation Details
![image](https://github.com/mrozowski/reservation-lock-demo/assets/67066372/69d7ec7c-d1fd-480b-b1e9-28e94b7442c7)



## The Development Process

### Requirements and Planning
The project began with a consideration of both functional and non-functional requirements. To visualize the system's structure, I used Whimsical to create diagrams and database relation diagram.

For the project implementation I used **Domain-Driven Design (DDD)** approach, and **hexagonal architecture**. This allowed for a clean separation of concerns and facilitated the implementation of domain-specific logic.

### Developer Journal
1. **First Implementations:**
   - I started by implementing the Reservation Service and the Trip List feature, enabling users to search and filter available trips.
   - I added unit tests, and I implemented Cucumber end-to-end test to align with a Behavior-Driven Development (BDD) approach.
   - Implementing e2e test was a bit challenging. I had to solves issues like run Spring App and Database and fill it with test data automatically for e2e test.
2. **Evolution of backend service:**
   - With some initial implementation in place, I started aligning with Test-Driven Development (TDD) and Behavior-Driven Development (BDD) approach. I have to admit implementing all scenarios of e2e test before implementing actual code is quite helpful. It made me think about functionalities and my service API. Thanks to this approach later on I didn't have to make many changes in my code. 
   - I implemented other features like retrieving reservation details, canceling reservation and the main feature - making reservation.
   - Thanks to upfront implemented e2e test scenarios during development I didn't have to run the app and manually call endpoints for testing. I only run e2e test scenarios to validate if implemented feature works properly.
   - As the last feature I implemented a seat lock system to lock the seat after user choose it from UI. Thanks to this we can avoid issue with other customer reserving this same seat before first customer finishes his reservation.
3. **Frontend in React.js**
   - First I started with designing a User Interface in Figma. Thanks to this I have prepared, colors, required pages, and functionalities for the website. So that during implementation I can focus only on implementing a website and not on how it should look like.
   - I chose React because I wanted to practice it more. I did a few simple websites in React in the past. I feel good with React I didn't have many issues. However, I know it was just a simple website.
   - The only problem I had was with dynamic typing in JavaScript. Very often I didn't know what field name object has that I passed as a parameter. IDE often didn't show errors and later on, I had to spend some time on debugging issues - which were caused by using a wrong field name. Probably that's why TypeScipt exists :D 
4. **Payment microservice in Golang**
   - I found that there are more and more job offers for Golang developers so I wanted to try this language and create a simple microservice for handling payment transactions.
   - The language seems to be very light however it has a lot of useful tools implemented into the language eg: unit testing or handling Rest API. Certainly, I would like to practice more with this language in the future.
5. **Kubernetes**
   - Kubernetes is a very common way of managing services so I wanted to use it as well in my project. In this case, I use Minikube to run it locally.
   - I created a docker image of my Spring Boot, React, and Go apps and ran it on the Kubernetes cluster.
   - I prepared Manifest yaml files that can be found in `/kubernetes/chars/` directory
