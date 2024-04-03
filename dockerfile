FROM openjdk:17-jdk-alpine
COPY target/alumno-maeteria-0.0.1-SNAPSHOT.jar api-university.jar
WORKDIR /app
CMD ["java", "-jar", "api-university.jar"]