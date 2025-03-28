# Use a Java 21 base image
FROM eclipse-temurin:21-jdk

# Set the working directory
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src/ ./src/
COPY resources/ ./resources/

# Install Maven and build the project
RUN apt-get update && apt-get install -y maven
RUN mvn clean package

# Expose the port for the REST API
EXPOSE 8080

# Run the BioSim server (using the jar built with dependencies)
CMD ["java", "-jar", "target/biosim-1.0.0-jar-with-dependencies.jar", "8080"]