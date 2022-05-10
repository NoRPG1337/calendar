# Project's structure

Package                                         | Description
------------------------------------------------|------------------------------------------------------------
/.mvn/wrapper                                   | Maven Wrapper
/src/main/resources                             | Configuration files
/src/main/java/calendar/config                  | Application configuration
/src/main/java/calendar/security/config         | JWT and UserDetails configuration
/src/main/java/calendar/controller              | API controllers
/src/main/java/calendar/entity                  | DB entities
/src/main/java/calendar/exception               | Custom exceptions
/src/main/java/calendar/helper                  | Helper classes
/src/main/java/calendar/repository              | JPA repositories for DB connection
/src/main/java/calendar/request                 | Request structures for API 
/src/main/java/calendar/request/projection      | Entities' projections
/src/main/java/calendar/response                | Response structures returned by API
/src/main/java/calendar/service                 | Service classes for business-logic

# Entities

Entity            | Description
------------------|------------------------------------------------------------------------------
User              | User
Event             | Calendar event


# Swagger API Documentation

localhost:8084/swagger-ui.html

# Important notes

Insert your database host,port, name and username, password in the following context variables in application.properties
```
DB_HOST = X
DB_PORT = X
DB_NAME = X
DB_USER = X
DB_PASSWORD = X
```