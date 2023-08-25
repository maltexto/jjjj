FROM maven:3.8.4-openjdk-17-slim

WORKDIR /usr/src/pivete

COPY pom.xml .

RUN mvn dependency:go-offline

COPY src/main/java/dev/maltexto/pivete .

USER 1001

ENV PORT 4567
EXPOSE $PORT

ENTRYPOINT ["mvn", "clean", "verify", "exec:java"]