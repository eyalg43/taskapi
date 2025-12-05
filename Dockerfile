# Use eclipse-temurin instead of openjdk
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy the JAR file into the container
COPY target/taskapi-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]