FROM openjdk:8-alpine

COPY . /usr/src/home-inc-api

WORKDIR /usr/src/home-inc-api

RUN apk add maven && mvn clean install

CMD ["java", "-Dspring.profiles.active=prod", "-jar", "./target/home-inc-api-0.0.1-SNAPSHOT.jar"]