# BioSim
BioSim is a research project developed at NASA Johnson Space Center. The objective is to create a portable simulation of an integrated advanced life support system for mission scenarios with malfunctions and perturbations.

The simulation is written in Java. It uses a RESTful API for communication, enabling integration with any HTTP-capable language.

## Building
1. **Prerequisites:**
   - Install the [Java Development Kit](https://adoptium.net/) (version 21 or greater).
   - Ensure [Maven](https://maven.apache.org/) is installed and added to your PATH.
   - Clone the repository from GitHub.

2. **Compile the Project:**
   Open a terminal in the project root directory and run:
   ```
   mvn clean package
   ```

## Running BioSim

### Using the Scripts in the `bin` Directory
The `bin` directory contains scripts to launch the simulation:

- **`start-biosim-server`**: Starts the BioSim server. It accepts optional command-line arguments for host and port. By default, the server listens on host `0.0.0.0` and port `8009`.

- **`run-simulation`**: Launches a simulation using the default configuration. Other configurations can be specified (see the `configurations` directory) with the `--config` option. Run using `--help` for more options.

### Using Docker
You can also run BioSim using Docker. The repository includes a `docker-compose.yml` configuration file. To use it, simply run:
```
docker compose up
```
This command builds (if needed) and starts the BioSim container, with the server listening on port `8009`.


## REST API Endpoints

The simulation exposes several REST endpoints:

### Simulation Endpoints
- **GET** `/api/simulation`  
  Retrieves a list of active simulation IDs.

- **POST** `/api/simulation/start`  
  Starts a new simulation. Provide a valid XML configuration in the request body. Returns a `simID` that can subsequently be used in calls below.

- **POST** `/api/simulation/{simID}/tick`  
  Advances the simulation with the specified simulation ID by one tick. 

- **GET** `/api/simulation/{simID}/modules/{moduleName}`  
  Provides detailed information about a specific module, including consumer/producer definitions and connection details.

- **GET** `/api/simulation/{simID}`  
  Retrieves global simulation properties and all the module details.

### Server Details
By default, it binds to host `0.0.0.0` and port `8009`. You can override these defaults by passing a different host or port as command-line arguments when starting the server.

## License
GPL v3