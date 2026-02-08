A. Project Overview

I created SpringBoot RESTful API for Movie Streaming Platform that uses design patterns, implementing SOLID and component principles with exception handling.

B. REST API Documentation

Base URL: http://localhost:8080

Endpoints:

/api/films/ with methods POST, GET;
/api/films/{id} with methods GET, PUT, DELETE;
/api/series/ with methods POST, GET;
/api/series/{id} with methods GET, PUT, DELETE;
/api/episodes/series/{seriesId} with method POST;
/api/episodes/ with method GET;
/api/episodes/{id} with methods GET, PUT, DELETE.


Examples of requests/responses on screenshots

Request:
POST /api/series/

{
"name": "Breaking Bad",
"rating": 9.5
}

Response:

{
"id": 1,
"name": "Breaking Bad",
"rating": 9.5,
"episodes": []
}

Request:
POST /api/episodes/series/1

{ 
"name": "Pilot",
"duration": 58 
}

Response:

{
"id": 1,
"name": "Pilot",
"duration": 58
}

Error:
GET /api/episodes/series/2

{
"status": 404,
"message": "Episode with id 2 not found"
}

C. Design Patterns

Purpose of Singleton is to guarantee one shared instance for cross-cutting services and configuration.
In API used in services for logs.

Purpose of Factory is to create subclasses of an abstract/base entity through one creation point.
In API I made ContentFactory to create Series in service.

Purpose of Builder is to build complex objects with validation.
In API I used Builder in services of Film and Episode to create entities.

D. Component Principles

REP: Reusable modules that release together are separated into packages(repositories, services and etc.)

CCP: Classes that change together are grouped together(controllers changes, services changes and etc.)
Different classes in different packages(services are not in controllers)

CRP: Packages avoid forcing dependencies on unused code(controllers only depend on services, not on repositories directly)

E. SOLID and OOP summary

SOLID:

SRP: controllers delegate to services; services manage; repositories only access database

OCP: adding new content types can be done by extending model and updating factory

LSP: Film and Series are managed as Content object

ISP: repositories as interfaces are focused per-entity

DIP: controllers and services depend on abstractions

OOP:
I created abstract superclass Content and subclasses Film, Series, and another class Episode.

Here I apply Inheritance, Abstraction principles in Content and subclasses. Encapsulation in every class, Polymorphism is applicable through ContentFactory.

Advanced OOP features are implemented in package utils but was not used in this API.

F. Database Schema

films(id BIGSERIAL PK, name VARCHAR NOT NULL UNIQUE, duration INT NOT NULL, rating DOUBLE PRECISION NOT NULL)
series(series_id BIGSERIAL PK, name VARCHAR UNIQUE, rating DOUBLE PRECISION NOT NULL)
episodes(id BIGSERIAL PK, name VARCHAR UNIQUE, duration INT NOT NULL, series_id BIGINT NOT NULL FK)

G. System Architecture Diagram

UML diagram

H. Instructions to run Spring Boot Application

java Application + Maven + Postman

I. Reflection Section

I learned how to create Spring Boot API and how to work with it through HTTP-requests within Postman.
I faced challenges with creating design patterns because it is new feature for me.
