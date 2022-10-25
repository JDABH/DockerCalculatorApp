FROM openjdk:17
WORKDIR /opt
EXPOSE 8080
COPY target/*.jar /opt/app.jar
CMD ["java","-jar","app.jar"]