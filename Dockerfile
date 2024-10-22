# Stage 1: Build the application
FROM amazoncorretto:21-alpine AS build
WORKDIR /app
COPY build/libs/*.jar app.jar

# Stage 2: Create a minimal image using JRE
FROM amazoncorretto:21-alpine
WORKDIR /app
COPY --from=build /app/app.jar app.jar

# Expose the port the application runs on
EXPOSE 8000

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
