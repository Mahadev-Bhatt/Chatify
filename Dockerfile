# Build stage: compile the project with Maven and package the JAR
FROM maven:3.9.4-eclipse-temurin-21 as build

WORKDIR /app

# Copy pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the app and skip tests for faster build
RUN mvn clean package -DskipTests

# Run stage: run the packaged app on Java 21 JRE
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy the JAR built in the build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port 8080 (default for Spring Boot)
EXPOSE 8080

# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]

