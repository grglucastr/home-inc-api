FROM maven:3-openjdk-11-slim

ENV HOME=/usr/src/home-inc-api

WORKDIR $HOME

ADD pom.xml $HOME

RUN ["/usr/local/bin/mvn-entrypoint.sh", "mvn", "verify", "clean", "--fail-never"]

ADD . $HOME

RUN mvn clean package

EXPOSE 8080

CMD ["java", "-jar", "./target/homeincapiv3.jar"]
