FROM openjdk:8-alpine
COPY target/demo-0.0.1-SNAPSHOT.jar /consumidor-1.jar
CMD ["java","-jar","/consumidor-1.jar"]


