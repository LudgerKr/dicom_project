FROM openjdk:11
WORKDIR /app

COPY target/demo-0.0.1-SNAPSHOT.jar /app/app.jar

CMD ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app/app.jar"]

