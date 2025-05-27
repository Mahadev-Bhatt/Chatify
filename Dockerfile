# First stage: build the JAR with Maven
eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy pom.xml and source code
COPY pom.xml .
COPY src ./src

# Build the application, skipping tests for faster build
RUN mvn clean package -DskipTests

# Second stage: run the application with lightweight JRE
FROM eclipse-temurin:21-jre-alpine

WORKDIR /app

# Copy the JAR from the build stage
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]

