FROM openjdk:11-jre-slim

EXPOSE 4000

WORKDIR /app
COPY ./lib-four-0.0.1-SNAPSHOT.jar free-speak.jar

CMD [ "java", "-Dspring.profiles.active=prod", "-jar", "free-speak.jar" ]
