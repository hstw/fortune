FORTUNE

## Project Description

A “fortune” service (https://en.wikipedia.org/wiki/Fortune_(Unix))  \
The key use cases of this service are:

  1. When a user launches our app, the app will call this service to retrieve a random fortune message and display it to the user.

  1. Our operations team can add candidate fortune messages.

  1. Our operations team can remove individual fortune message.

#### APIs
        GET     /fortune 
        GET     /fortunes 
        POST    /fortunes 
        DELETE  /fortunes/{fortuneId} 
        GET     /fortunes/{fortuneId} 

#### Assumptions
  1. Only one server
  1. Allows duplicated fortunes ( to increase the chance of being selected )
  1. Calls by our App ( not public APIs )

#### Design
  * Data Structures: 3 HashMaps + 1 ArrayList
    1. An list of `fortunes`
    1. A map of `Fortune id` -> `location in list`
    1. A map of `Fortune content` -> `set of ids ` ( enables duplicates )
    1. A map of `Fortune id` -> `content`

  * Method Implementation
    * Get a random fortune: O(1)
    * Add a fortune: O(1)
    * Delete a fortune: O(1)
    * ...


</br>
</br>
## Running The Application

#### Prerequstie
This project connects to a `fortune` database on postgres \
`postgresql://localhost:5432/fortune`
Database configuration can be modified in `config.yml`

#### Start the server
        mvn clean package 
        java -jar target/fortune-1.4.0-SNAPSHOT.jar server config.yml


