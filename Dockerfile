# Stage 1: Build the application using a lighter Gradle image
FROM gradle:jdk21-alpine AS build

# Set the working directory inside the container
WORKDIR /app

# Copy only the Gradle build files to cache dependencies
COPY build.gradle settings.gradle /app/

# Download dependencies before copying the rest of the source code
RUN gradle --no-daemon build || return 0

# Copy the rest of the source code
COPY . .

# Build the Spring Boot project (creates an executable JAR)
RUN gradle bootJar --no-daemon

# Stage 2: Create a custom JRE with jlink
FROM eclipse-temurin:21-alpine AS jre-builder

# Create a minimal JRE using jlink
RUN $JAVA_HOME/bin/jlink \
    --compress=2 \
    --strip-debug \
    --no-man-pages \
    --no-header-files \
    --add-modules java.base,java.logging,java.sql,java.naming,java.xml,java.desktop \
    --output /custom-jre

# Stage 3: Run the application using a minimal Java runtime
FROM alpine:3.18

# Set environment variables for the custom JRE
ENV JAVA_HOME=/jre
ENV PATH="$JAVA_HOME/bin:$PATH"

# Copy the custom JRE from the previous stage
COPY --from=jre-builder /custom-jre /jre

# Set the working directory
WORKDIR /app

# Copy the Spring Boot JAR file from the build stage
COPY --from=build /app/build/libs/*.jar app.jar

# Expose port 8000
EXPOSE 7001

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "app.jar"]
