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
This command builds (if needed) and starts the BioSim container, with the server listening on http://localhost:8009/api/simulation
It also starts the [Open MCT](https://github.com/nasa/openmct) [plugin for BioSim](https://github.com/scottbell/openmct-biosim/) on http://localhost:9091, allowing you to graph and view details in the simulation.

<img width="1607" alt="biosim-with-openmct" src="https://github.com/user-attachments/assets/0812e361-6a93-4dd9-8006-8a2b6e21133f" />

## REST API Endpoints

The simulation exposes several REST endpoints:

### Simulation Endpoints

- **GET** `/api/simulation`  
  Retrieves a list of active simulation IDs. E.g.:
  ```json
  {"simulations":[1,2]}
  ```

- **GET** `/api/simulation/{simID}`  
  Retrieves global simulation properties and detailed module information for all modules. E.g.:
  ```json
  {
    "globals": {
      "myID": 1,
      "simulationIsPaused": false,
      "simulationStarted": true,
      "ticksGoneBy": 1367,
      "nTicks": 0,
      "runTillCrewDeath": true,
      "runTillPlantDeath": false,
      "looping": false,
      "driverStutterLength": 500,
      "tickLength": 1
    },
    "modules": {
      "CO2_Store": {
        "moduleName": "Backup_CO2_Store",
        "moduleType": "CO2Store",
        "properties": {
          "currentLevel": 0,
          "currentCapacity": 1000,
          "overflow": 0,
          "isPipe": false
        }
      }
    }
  }
  ```

- **GET** `/api/simulation/{simID}/modules/{moduleName}`  
  Provides detailed information about a specific module, including consumer/producer definitions, flow rate arrays, or store properties if it is a store. E.g.:
  ```json
  {
    "moduleName": "Backup_CO2_Store",
    "moduleType": "CO2Store",
    "properties": {
      "currentLevel": 0,
      "currentCapacity": 1000,
      "overflow": 0,
      "isPipe": false
    }
  }
  ```

- **POST** `/api/simulation/start`  
  Starts a new simulation.  
  **Request Body:**  
  The XML configuration for the simulation should be provided as plain text.  
  **Response:**  
  JSON containing the simulation ID. E.g.:
  ```json
  {"simId":2}
  ```
  **Example:**
  ```
  curl -X POST http://localhost:8009/api/simulation/start \
       -H "Content-Type: text/plain" \
       -d '<xml><configuration>...</configuration></xml>'
  ```

- **POST** `/api/simulation/{simID}/tick`  
  Advances the simulation with the specified simulation ID by one tick.  
  **Response:**  
  JSON indicating the updated tick count. E.g.:
  ```json
  {"ticks":1597}
  ```
  **Example:**
  ```
  curl -X POST http://localhost:8009/api/simulation/1/tick
  ```

- **POST** `/api/simulation/{simID}/modules/{moduleName}/consumers/{type}`  
  Updates the consumer definition for a specified module.  
  **Request Body:**  
  A JSON object with the following keys:
  - `desiredFlowRates`: An array of floats indicating the desired flow rates.
  - `connections`: (Optional) An array of strings representing connection module names; the array length must match that of `desiredFlowRates`.
  
  **Response:**  
  A JSON confirmation message indicating successful update.
  
  **Example:**
  ```
  curl -X POST http://localhost:8009/api/simulation/1/modules/OGS/consumers/potableWater \
       -H "Content-Type: application/json" \
       -d '{"desiredFlowRates": [10.0], "connections": ["Potable_Water_Store"]}'
  ```

- **POST** `/api/simulation/{simID}/modules/{moduleName}/producers/{type}`  
  Updates the producer definition for a specified module.  
  **Request Body:**  
  A JSON object with the following keys:
  - `desiredFlowRates`: An array of floats indicating the desired flow rates.
  - `connections`: (Optional) An array of strings representing connection module names; the array length must match that of `desiredFlowRates`.
  
  **Response:**  
  A JSON confirmation message indicating successful update.
  
  **Example:**
  ```
  curl -X POST http://localhost:8009/api/simulation/1/modules/OGS/producers/H2 \
       -H "Content-Type: application/json" \
       -d '{"desiredFlowRates": [10.0], "connections": ["H2_Store"]}'
  ```

### Malfunction Endpoints

- **GET** /api/simulation/{simID}/modules/{moduleName}/malfunctions

  This endpoint retrieves the list of malfunctions for the specified module. It returns a JSON array where each object represents a malfunction with the following properties:

  - **id**: The unique identifier of the malfunction.
  - **name**: The name of the malfunction.
  - **intensity**: The malfunction intensity (one of `SEVERE_MALF`, `MEDIUM_MALF`, or `LOW_MALF`).
  - **length**: The malfunction length (either `TEMPORARY_MALF` or `PERMANENT_MALF`).
  - **performed**: A boolean indicating whether the malfunction has been executed.
  - **tickToMalfunction**: The simulation tick at which the malfunction is scheduled to occur or did occur.
  - **doneEnoughRepairWork**: A boolean indicating if the required repair work has been completed for the malfunction.

  **Response:**
  ```json
  [
    {
      "id": 2,
      "name": "Temporary Medium Malfunction",
      "intensity": "MEDIUM_MALF",
      "length": "TEMPORARY_MALF",
      "performed": true,
      "tickToMalfunction": 0,
      "doneEnoughRepairWork": false
    }
  ]

- **POST** `/api/simulation/{simID}/modules/{moduleName}/malfunctions`  
  Starts or schedules a malfunction for a specific module.  
  **Request Body:**  
  A JSON object that must include:
  - `intensity`: A string representing the malfunction intensity (`SEVERE_MALF`, `MEDIUM_MALF`, or `LOW_MALF`).
  - `length`: A string representing the malfunction length (`TEMPORARY_MALF` or `PERMANENT_MALF`).
  
  Optionally, you can include:
  - `tickToOccur`: An integer specifying when the malfunction should occur. If provided, the malfunction will be scheduled using `scheduleMalfunction`; otherwise, `startMalfunction` is called.
  
  **Response:**  
  A JSON object containing the `malfunctionID`.
  ```json
  {"malfunctionID":2}
  ```
  **Example:**
  ```
  curl -X POST http://localhost:8009/api/simulation/1/modules/OGS/malfunctions \
       -H "Content-Type: application/json" \
       -d '{"intensity": "MEDIUM_MALF", "length": "TEMPORARY_MALF", "tickToOccur": 3}'
  ```

- **DELETE** `/api/simulation/{simID}/modules/{moduleName}/malfunctions/{malfunctionID}`  
  Clears a specific malfunction from a module.  
  **Response:**  
  A JSON confirmation message.
  ```json
  {"message":"Malfunction 2 cleared."}
  ```  
  **Example:**
  ```
  curl -X DELETE http://localhost:8009/api/simulation/1/modules/OGS/malfunctions/2
  ```

- **DELETE** `/api/simulation/{simID}/modules/{moduleName}/malfunctions`  
  Clears all malfunctions from a specific module.  
  **Response:**  
  A JSON confirmation message.
  ```json
  {"message":"All malfunctions cleared."}
  ```
  **Example:**
  ```
  curl -X DELETE http://localhost:8009/api/simulation/1/modules/OGS/malfunctions
  ```

## WebSocket Interface
In addition to the REST API endpoints, there is a WebSocket interface for real-time simulation updates. To subscribe to live simulation state updates, connect to:  
  ws://<host>:<port>/ws/simulation/{simID}
Upon connection, the server immediately sends the current simulation state and then broadcasts further updates on each tick.

## Configuring BioSim
For more detailed instructions on configuring BioSim, please refer to the [Users Manual](doc/users_manual.md#configuring-the-simulation). This manual covers configuration options such as initial conditions and stochastic processes.

## License
GPL v3
