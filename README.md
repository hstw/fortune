FORTUNE
#### Prerequstie
This project connects to a `fortune` database on postgres \
`postgresql://localhost:5432/fortune`
Database configuration can be modified in `config.yml`

#### Running The Application
Try this to see MAVEN is working.

        mvn --version

To test the example application run the following commands.

* To create the example, package the application using [Apache Maven](https://maven.apache.org/) from the directory of the project.

        mvn clean package

* To run the server run.

        java -jar target/fortune-1.4.0-SNAPSHOT.jar server config.yml

#### APIs
        GET     /fortune 
        GET     /fortunes 
        POST    /fortunes 
        DELETE  /fortunes/{fortuneId} 
        GET     /fortunes/{fortuneId} 
