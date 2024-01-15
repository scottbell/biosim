FROM ubuntu:22.04

# Set the working directory
WORKDIR /biosim

# Install dependencies
RUN apt-get update && \
    apt-get install -y git ant openjdk-11-jdk

# Clone the repository and build the project
RUN git clone https://github.com/scottbell/biosim.git && \
    cd biosim && \
    ant

# Indicate that the container listens on port 16315 at runtime
EXPOSE 16315

# Change to the directory where the BioSim binaries are located
WORKDIR /biosim/biosim/bin

# Start the biosim headless version (ensure that the ant run-biosim-headless target starts a long-running process)
CMD ["bash", "run-biosim-headless"]