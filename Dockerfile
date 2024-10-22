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

# Stage 2: Use jlink to create a custom Java runtime and minimize image size
FROM eclipse-temurin:21-jre-alpine AS runtime

# Set the working directory
WORKDIR /app

# Copy the Spring Boot JAR file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose port 8080
EXPOSE 8000

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
