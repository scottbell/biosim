# Use a Java 21 base image
FROM eclipse-temurin:21-jdk

# Set the working directory
WORKDIR /app

# Copy the Maven project files
COPY pom.xml .
COPY src/ ./src/
COPY etc/ ./etc/
COPY bin/ ./bin/

# Install Maven and build the project
RUN apt-get update && apt-get install -y maven
RUN mvn clean package

# Make the scripts in bin executable
RUN chmod +x bin/*

# Expose the port for the REST API
EXPOSE 8009

# Run the BioSim server using the start script
CMD ["./bin/start-biosim-server", "0.0.0.0", "8009"]