# Stage 1: Build the application using a lighter Gradle image
FROM gradle:jdk21-alpine AS build

# Set the working directory inside the container
WORKDIR /app

# Copy only the Gradle build files (to cache dependencies)
COPY build.gradle settings.gradle /app/

# Download dependencies before copying the rest of the source code
RUN gradle --no-daemon build || return 0

# Copy the rest of the source code
COPY . .

# Build the Spring Boot project (creates an executable JAR)
RUN gradle bootJar --no-daemon

# Stage 2: Run the applicaation using a small Java runtime
FROM eclipse-temurin:21-alpine

# Set the working directory
WORKDIR /app

# Copy the Spring Boot JAR file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose port 8080
EXPOSE 8090

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
