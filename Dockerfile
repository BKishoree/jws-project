# Use a suitable base image for a Java 21 Spring Boot application
FROM eclipse-temurin:21-jdk-jammy

# Set up the working directory
WORKDIR /app

# Copy the build artifacts (the JAR file) into the Docker image
# Assuming the JAR file is in build/libs/ and is named jws.project-0.0.1-SNAPSHOT.jar
# Adjust the name if necessary
COPY build/libs/*.jar app.jar

# Expose the application port
EXPOSE 1000

# Define the command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
