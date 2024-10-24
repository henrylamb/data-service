# Stage 1: Build the application using a lighter Gradle image
FROM gradle:jdk21-alpine AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the Gradle build files and the source code
COPY . .

# Clean and build the Spring Boot project
RUN gradle clean bootJar --no-daemon

# Stage 2: Use a minimal Java runtime
FROM eclipse-temurin:21-jre-alpine AS runtime

# Set the working directory
WORKDIR /app

# Copy the Spring Boot JAR file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose port 8080 (default for Spring Boot)
EXPOSE 8000

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
