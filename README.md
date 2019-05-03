# UserManagement

Simple demo User management backend project 

- The project uses Java 8 and Spring Boot.
- MVC architectural pattern with RESTful HTTP services
- Build tool - Gradle ver. 5.4.1  
- ORM - Hibernate
- Database - MySQL

 The project was created under following System environment:
   - Ubuntu  18.04
   - Intellij Idea ver. 2019.1.1
   - HeidiSQL
   
 Configuring:
   In application.properties set following properties according your MySql user credentials :
   
     - spring.datasource.username 
     - spring.datasource.password 
     
 Database is set to be created automaticaly if not exist.
   
 Building:
  - Enter the project directory 
  - Run gradle wrapper
  - Run command: gradlew
  
  Start project by running command:  gradle bootRun
 
 The project can be tested with REST client like "Postman" or Frontend application
