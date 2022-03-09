FROM maven:3.8-openjdk-11

COPY . /usr/src/home-inc-api

WORKDIR /usr/src/home-inc-api

RUN mvn clean install

ENV PG_DB_NAME=homeincdb
ENV PG_USERNAME=postgres
ENV PG_PASSWORD=localpass
ENV PG_SERVER_ADDRESS=localhost
ENV PG_SERVER_PORT=5432

ENV USER_MAIL=homeincapi@gmail.com
ENV USER_MAIL_PASSWORD=ehnvjdavtdpldqll

CMD ["java", "-Dspring.profiles.active=prod", "-jar", "./target/home-inc-api-0.0.1-SNAPSHOT.jar"]