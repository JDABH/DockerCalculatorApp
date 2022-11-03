FROM openjdk:17
WORKDIR /opt
EXPOSE 8080
ADD . /opt
#COPY target/*.jar /opt/app.jar
CMD ["java","-jar","app.jar"]
