# Qwizzard (API)
This repository contains the backend of Qwizzard. 

 See the UI side of Qwizzard here:
 https://github.com/revature-quizzard/quizzard-ui

 Qwizzard is a self-study web application made to assist Revature associates 
with QC study. Qwizzard has two main functions, study and quiz.
Both study and quiz are flashcard-centric.

### Flashcards:
Flashcards are made up of questions and answers. This is the basic 
unit of knowledge in Qwizzard. Flashcards should contain a meaningful
question, and a brief but complete answer. Flashcards are grouped into
study sets, and associated with a category.

### Study:
A user can select a study set and launch study mode. In study mode users 
are presented with the questions one-by-one. At first the answer is obscured.
A user can try to answer the question on their own, and then reveal the answer
in order to check their knowledge. 

### Quiz:
A user can also create a quiz from a study set to test themselves. A quiz 
randomizes the order of questions, and then generates a selection of wrong 
answers for each, randomly selected from the correct answers to other 
questions. A user selects an answer for each question, and is given a score.

## API Structure:
Qwizzard is a Spring application. It utilizes Spring modules for many problems,
and should continue to implement more as the application grows. Current Spring 
modules in use include:
 - Spring Core
 - Spring Web
 - Spring JPA
 - Spring Security
 - Spring AOP

### Controller Layer:
The controller layer contains rest controllers, which are mapped to API 
endpoints. While there is some logic here, most should be in the service layer.
These controllers should handle only the logic pertinent to handling HTTP 
communications. Servicing the requests in earnest should be handled elsewhere.

### Service Layer:
The service layer contains service classes which should handle most of the 
business logic pertaining to servicing incoming requests. JSON request and 
response bodies should be marshalled to/from DTOs here, and validation logic
should happen here. Access to low-level functionality, like data persistence
should be invoked from here.

## Resources:
The following files are not tracked in this repo and may be necessary:

### application.yml
This file describes server and datasource settings necessary to run the 
application. 

### import.sql
This file contains SQL scripts used to import boilerplate data into the 
datasource for development purposes.