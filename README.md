# DVOR
Task DVOR
Create a REST service that can be used for scheduling fitness events.

Entities:
Client

FirstName
LastName
Age
Account (money)
Coach

FirstName
LastName
Age
Rewards
Fitness room

Name
Address
Size (how many people can attend a class)
Event cost
Event

Fitness room
Coach
List of clients
Date and time
Duration
Business requirements:
Provide a possibility to create/update/delete client.
Provide a possibility to create/update/delete coach.
Provide a possibility to create/update/delete fitness room.
Get all clients by page and size.
Get all fitness rooms by page and size.
Coach can schedule an Event (he should provide fitness room and time). Event can be scheduled only if provided fitness room is available for this time.
Get all future events
Get all future events by coach.
Get all events by fitness room.
Get all events by client.
Client can attend an event but only if current number of clients who wants to participate is lower than fitness room size. Client also should have enough money in account. Please note that clients pay for events only after event has been completed.
Create a job that will take money from client's account after event has been completed.
Get the most productive coach (under which coach there was spent the highest amount of money).
Tech requirements:
Use java 11 or more, Spring boot, gradle, JPA, Hibernate.
Use DTO pattern, mapstruct.
Use swagger for REST API.
REST principles and best practices should be used.
We will run this application in two environments: test and prod. Use postgresql for prod and mysql for test.
Create another service which will send http requests to our service and check responses.
