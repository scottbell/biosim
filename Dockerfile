# Use a Java 21 base image
FROM eclipse-temurin:21-jdk

# Set the working directory
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src/ ./src/
COPY resources/ ./resources/

# Build the project with Maven
RUN apt-get update && apt-get install -y maven
RUN mvn clean package

# Expose the port for the REST API
EXPOSE 8080

# Run the BioSim server in headless mode
CMD ["java", "-cp", "target/biosim-1.0.0.jar", "com.traclabs.biosim.server.framework.BiosimServer", "8080"]
