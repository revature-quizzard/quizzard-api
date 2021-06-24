FROM openjdk:8-jdk-alpine

ARG JAR_FILE=target/quizzard-api-1.0-SNAPSHOT.jar

EXPOSE 5000
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
