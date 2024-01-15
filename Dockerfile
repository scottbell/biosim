FROM ubuntu:22.04

# Set the working directory
WORKDIR /biosim

# Install dependencies
RUN apt-get update && \
    apt-get install -y ant openjdk-11-jdk

# Copy project from disk to container
COPY . /biosim

# Build the project
RUN ant

# Indicate that the container listens on port 16315 at runtime
EXPOSE 16315

# Change to the directory where the BioSim binaries are located
WORKDIR /biosim/bin

# Start the biosim headless version (ensure that the ant run-biosim-headless target starts a long-running process)
CMD ["bash", "run-biosim-headless"]