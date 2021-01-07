# About
This is my implementation of the incrementing integers service.

I chose to implement this in Java because it is my strongest language.
I chose to implement this using Spring Boot and Web since it makes it super easy to build a REST interface, allowing me to worry only about my application logic. 

This implementation assumed everything can be in-memory and that our deployment is fault-tolerant and will be able to resume from complete data loss :) That is not too useful in a real-world implementation, but for this small exam it seems suitable.

See the following class for the in-memory implementations of this service:
```
InMemoryUserManagementService.java
InMemoryIncrementingData.java
``` 

I have stubbed out a class for persisting the data. They are:
```
RedisIncrementingData.java
KeyCloakUserManagementService.java
``` 

# How to run
TODO