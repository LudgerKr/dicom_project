FROM maven:3.6-jdk-11-slim as builder

ADD . /app/

WORKDIR /app

RUN mvn dependency:resolve

RUN  mvn package -e

FROM openjdk:11

WORKDIR /root/

COPY --from=builder /app/target/ .

CMD ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]
