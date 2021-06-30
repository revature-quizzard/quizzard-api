FROM openjdk:8-jdk-alpine

ARG JAR_FILE=target/*.jar
ARG PROFILE=local
ARG DB_URL
ARG DB_PASSWORD
ARG DB_USERNAME
ARG AWS_ACCESS_KEY_ID
ARG AWS_SECRET_ACCESS_KEY

ENV db_url=$DB_URL
ENV db_username=$DB_USERNAME
ENV db_password=$DB_PASSWORD
ENV AWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_ID
ENV AWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY

COPY ${JAR_FILE} app.jar
WORKDIR /home/docker/data
#RUN chmod +x /app.jar
RUN echo "java -Dspring.profiles.active=$PROFILE -jar /app.jar" >> ./entry.sh
EXPOSE 5000
ENTRYPOINT ["sh", "entry.sh"]
