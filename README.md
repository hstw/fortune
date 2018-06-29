fortune

# Introduction


# Running The Application

You need to use `curl` command to interact with your program.

You need to use `git` to clone this repository to your computer.
* https://git-scm.com/downloads
* git clone https://github.com/borderxlab/fortune.git
* go to the directory

You need to use MAVEN to compile java projects. https://maven.apache.org/

For MacOS, best is to use brew to install maven.
* https://brew.sh/
* http://brewformulas.org/Maven

For Linux, use apt-get to install `git` and `maven`.

For Windows, it may be tricky. See https://maven.apache.org/ Another option is ot use cygwin on Windows if you don't already know how to use `git` and `maven`.

To test the example application run the following commands.

* To create the example, package the application using [Apache Maven](https://maven.apache.org/) from the root dropwizard directory.

        mvn clean package

* To run the server run.

        java -jar target/dropwizard-example-1.4.0-SNAPSHOT.jar server example.yml

* To hit the Hello World example from browser (hit refresh a few times).

	http://localhost:8080/hello-world

* Look at HelloWorldResource class source code comments to see how to interact with the API


