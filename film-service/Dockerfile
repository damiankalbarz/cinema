FROM openjdk:17-alpine

WORKDIR /app

COPY target/film-service-0.0.1-SNAPSHOT.jar app.jar
COPY src/main/resources/application.properties application.properties

EXPOSE 8084

CMD ["java", "-jar", "app.jar"]
