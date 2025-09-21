FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/*.jar spring-jpa-02-app.jar
ENTRYPOINT ["java","-jar","spring-jpa-02-app.jar"]