# About
This is my implementation of the incrementing integers service.

I chose to implement this in Java because it is my strongest language.
I chose to implement this using Spring Boot and Web since it makes it super easy to build a REST interface, allowing me to worry only about my application logic. 

This implementation assumed everything can be in-memory and that our deployment is fault-tolerant and will be able to resume from complete data loss :) That is not too useful in a real-world implementation, but for this small exam it seems suitable.

### Class structure

There is a single class for the model of the service. It is so minimal, it mostly only passes calls to the data-layer.
```
IncrementingService.java
```

The following classes are the in-memory implementations of this service:
```
InMemoryUserManagementService.java
InMemoryIncrementingData.java
``` 

I have stubbed out a class for persisting the data. They are:
```
KeyCloakUserManagementService.java
RedisIncrementingData.java
```

The REST API uses the service model directly and is implemented in:
```
RestApi.java
```

# The REST API
A full OpenAPI v3 formated definition of the API can be found in the local file `openApiSpec.yaml`. 

A small preview of the API is:

### 
```
  "/v1/register":
    post:
      summary: Creates a new user.
    delete:
      summary: Deletes a user. This is an authenticated endpoint.
  "/v1/current":
    get:
      summary: Returns the current value for the user. This is an authenticated endpoint.
    put:
      summary: Stores the provided integer in the service. The integer MUST be non-negative.
        This is an authenticated endpoint.
  "/v1/next":
    get:
      summary: Increments the user stored value and returns that value. This is an
        authenticated endpoint.
```

# How to run the service
The easiest way to run is to use the `spring-boot` maven goal.
```
./mvnw spring-boot:run
```


# How to build using Maven
This project uses maven so you can run your typical maven command to compile and install this project into your local maven repository. This project, like most pre-canned Spring projects, come with a maven executable in the project sources. You can run the command with the `mvnw` file.

Eg:
```
./mvnw clean install
```


# How to build a Docker image of the service
Run the following command in the root directory of this project:
```
./mvnw spring-boot:build-image
```
This uses Spring's built in `buildpack`, which handles all of the docker-ifcation of the project for us. Normally a `Dockerfile` is needed to define a) the parent image, b) what ports to expose c) etc. but with this handy tool we do not need to do any of that.

After this command is run, you will be able to find the image in your local Docker image repository.

Eg:
```
$ docker image ls -a
incrementer-core             0.0.1-SNAPSHOT      3ea5c51a0fc2        41 seconds ago        254MB
``` 