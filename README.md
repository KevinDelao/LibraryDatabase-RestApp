# Library Managament System (REST APP)
## Author: Kevin Delao



## Table of contents

* [Introduction](#intro)
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [Site](#site)
## Introduction
The goal of this project was to create simple library managment system. This project was inspired from a part time job I had working in a library during my undergraduate studies.
I wanted to see if I could build a simple software application that replicated the software I used working as a librarian in order to see what goes into the software thats used to
manage book returns and book borrowing. This applications allows for the creation of books and students and also also for students to return and borrow books. Overall this project 
was also a way to work on various aspects of programming that I learned from my CS courses.
## General info
This application is a full stack application in that HTML and CSS is used for the front end and JAVA and MYSQL is used to handle the backend. More specifically HTML + Thymeleaf is used to
to show forms entries and data from the MySQL database. Thymeleaf was chosen over JSP due the simplicity of Thymeleaf at displaying class objects. For the backend Spring REST was used to easily
communicate between Java and my local MySQL database. 
	
## Technologies
Project was created with:
* Java JDK 11
* HTML
* CSS
* MYSQL
* Thymeleaf
* Spring REST
* IDE: IntelliJ Community Edition (2020)

## Site

### Homepage
The homepage serves to display the currently available books available in the library. The Books have attributes assoicated with it like ISBN, Title, Author, Publisher, and Stock. 
Each book also has an action tab that lets someone edit the book information or delete the book if its not being borrowed.
index_home_book.jpg

<img src="spring_images/index_home_no_book.png" alt="phone image" width="100" height="100"/>
<img src="spring_images/index_home_book.jpg"/>
## Setup
To run this project, simply run SpringBootLibraryApplication which starts the spring application. The pom file included with will download all the necessary libraries needed to 
succesfully run the project. Additionally, to connect to a database locally or on AWS simply edit the application.yml for the database url as well as include a username or password
if the database is password protected.

