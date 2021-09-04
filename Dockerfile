FROM node:latest as web

WORKDIR /code
COPY ./gradle /code/gradle
COPY ./src /code/src

COPY ./.prettierignore /code
COPY ./.prettierrc /code
COPY ./build.gradle /code
COPY ./checkstyle.xml /code
COPY ./gradle.properties /code
COPY ./settings.gradle /code
COPY ./sonar-project.properties /code

WORKDIR /code/src/main/webapp
RUN npm install
RUN npm i --save-dev @types/crypto-js
RUN npm run build

###############################################
FROM gradle:jdk11 as back

WORKDIR /code
COPY --from=web /code /code
RUN gradle build

###############################################
FROM openjdk:11-jre-slim

EXPOSE 8080

WORKDIR /app
COPY --from=back /code/build/libs/lib-four-0.0.1-SNAPSHOT.jar petj.jar

CMD [ "java", "-Dspring.profiles.active=prod", "-jar", "petj.jar" ]
