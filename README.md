# DockerCalculatorApp
Calculator App implementing Continuous Delivery through the use of Docker for Assignment 2 of the CSU33012 Software Engineering module.

This program can be used in two ways, either by cloning the Github repository or by pulling a Docker image of the release on Docker Hub.

## Docker Use

To use this program through Docker, first the Docker Desktop application needs to be installed at  
https://www.docker.com/products/docker-desktop/

Once installed, the Docker image of the repository can be pulled through the terminal using the command  
> docker pull jdabh/interactive_calculator:v1.1.0

Once the image has been pulled, you need to make a network for a Docker container to run on, which can be made using  
> docker network create our-nework

Finally, we can create a Docker container based off of the image we pulled and using the network we created  
> docker run --name=calculator-container --rm -d -p 8080:8080 --network=our-network jdabh/interactive_calculator:v1.1.0

Once the container has compiled and run the program (may take a few seconds), you can search for the locally run web app by typing into your browser  
> localhost:8080

## Github use

To use the Web Calculator app through the Github repository, Java Version 17 (https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html) and Maven,
which can be installed using a package manager. Once both are installed open up your terminal in the location of the Github repository, at the location of the pom.xml
file. Once there, enter either  
> ./mvnw clean install spring-boot:run

or  
> mvn clean install spring-boot:run

if using Windows. Then same as with Docker, go into your browser and type  
> localhost:8080
