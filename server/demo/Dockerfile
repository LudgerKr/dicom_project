FROM maven:3.5-jdk-8-alpine as builder

ADD . /app/

WORKDIR /app

RUN mvn dependency:resolve

RUN  mvn package -e

FROM openjdk:8-jre-alpine

WORKDIR /root/

COPY --from=builder /app/target/ .

CMD ["java", "-jar", "demo-0.0.1-SNAPSHOT.jar"]
