
#FROM openjdk:8-jdk-alpine
#
#ARG JAR_FILE=target/quizzard-api-1.0-SNAPSHOT.jar
#
#EXPOSE 5000
#ADD ${JAR_FILE} app.jar
#ENTRYPOINT ["java", "-jar", "/app.jar"]

FROM openjdk:8-jdk-alpine

<<<<<<< HEAD
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
=======
ARG JAR_FILE=target/quizzard-api-1.0-SNAPSHOT.jar
<<<<<<< HEAD
#ARG DB_URL
#ARG DB_PASSWORD
#ARG DB_USERNAME
#ARG AWS_ACCESS_KEY_ID
#ARG AWS_SECRET_ACCESS_KEY
#ARG EMAIL_USERNAME
#ARG EMAIL_PASSWORD
#ARG REDDIT_PUBLIC
#ARG REDDIT_PRIVATE
#ARG REDDIT_USERNAME
#ARG REDDIT_PASSWORD
#ARG TWITTER_BEARER_TOKEN
#
#ENV db_url=$DB_URL
#ENV db_username=$DB_USERNAME
#ENV db_password=$DB_PASSWORD
#ENV AWS_ACCESS_KEY_ID=$AWS_ACCESS_KEY_ID
#ENV AWS_SECRET_ACCESS_KEY=$AWS_SECRET_ACCESS_KEY
#ENV email_username=$EMAIL_USERNAME
#ENV email_password=$EMAIL_PASSWORD
#ENV reddit_public=$REDDIT_PUBLIC
#ENV reddit_private=$REDDIT_PRIVATE
#ENV reddit_username=$REDDIT_USERNAME
#ENV reddit_password=$REDDIT_PASSWORD
#ENV twitter_bearer_token=$TWITTER_BEARER_TOKEN

#COPY ${JAR_FILE} app.jar
#WORKDIR /home/docker/data
#RUN chmod +x /app.jar
=======
>>>>>>> pre_dev
>>>>>>> origin/contribute-public-study-set-sean

COPY ${JAR_FILE} app.jar
WORKDIR /home/docker/data
#RUN chmod +x /app.jar
RUN echo "java -Dspring.profiles.active=$PROFILE -jar /app.jar" >> ./entry.sh
EXPOSE 5000
<<<<<<< HEAD
ENTRYPOINT ["sh", "entry.sh"]
=======
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
<<<<<<< HEAD

=======
>>>>>>> pre_dev
>>>>>>> origin/contribute-public-study-set-sean
