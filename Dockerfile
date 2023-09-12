# Use a base image with Java (choose the appropriate version)
FROM openjdk:11-jre-slim

# Copy the Spring Boot application JAR file into the container
COPY target/*.jar app.jar

# Expose the ports your application uses (e.g., 8080 for Spring Boot)
EXPOSE 8080

# Command to run the Spring Boot application
CMD ["java", "-jar", "app.jar"]
