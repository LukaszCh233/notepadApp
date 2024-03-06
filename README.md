# notepadApp - Project Spring Boot
Welcome to the NotePadApp, this is an application for creating your own notes online
# About
The notepadApp project is a Spring Boot based application that provides a simple online notepad. The application was added using Java version 17 and uses various Spring technologies and MySQL data completion

User functions:
- Register and login user
- Create note
- Update and delete note
- Display note

# Technologies used:
- Java 17
- Spring Boot
- Lombok
- Spring Security
- Hibernate
- JSON Web Token
- log4j
- Spring Data REST
- Database MySQL/H2

# Installation

## Prerequisites
Before you begin, ensure you have the following installed on your machine:
- Java 17: [Download and Install Java](https://adoptopenjdk.net/)
- MySQL: [Download and Install MySQL](https://dev.mysql.com/downloads/installer/)
- Maven: https://maven.apache.org/download.cgi
- Git: https://git-scm.com/downloads
- Postman: https://www.postman.com/downloads/

## Clone the Repository
git clone https://github.com/LukaszCh233/notepadApp.git
cd notepadApp

## Configure Database
```bash
spring.datasource.url=jdbc:mysql://localhost:3306/books_store
spring.datasource.username=your_username
spring.datasource.password=your_password
server.port = 8088

```
1. Open MySQL MySQL Workbench
2. Login to the administrator user of MySql
3. Use your username and password from configuration
4. Create database notepadApp