# Use a suitable base image with Java and Maven
FROM apache/maven:3.9.5-jdk-21

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and source code
COPY pom.xml .
COPY src ./src

# Package the application (skip tests for faster build in Docker)
RUN mvn clean package -DskipTests

# Use a lightweight base image for running the JAR
FROM eclipse-temurin:21-jre-alpine

# Set the working directory
WORKDIR /app

# Copy the built JAR from the previous stage
COPY --from=0 /app/target/*.jar app.jar

# Expose the port your Spring Boot app runs on (default is 8080)
EXPOSE 8080

# Command to run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
