# BioSim
BioSim is a research project being developed at NASA Johnson Space Center. The objective is to create a portable simulation of a typical integrated advanced life support system in a typical mission scenario with malfunctions and perturbations.

The simulation is written entirely in Java for the sake of portability, maintenance, and development.
It uses a RESTful API for communication between components, allowing any language with HTTP capabilities to interface with the simulation. We have tested the simulation on Windows, Linux and Mac platforms. Also provided is a user interface showing the internals of the simulation and a logging facility.

## Building
Download the [Java Development Kit](https://adoptium.net/) necessary to run BioSim (version 21 or greater).

Checkout the source code from GitHub.

To compile BioSim, you'll need [Maven](https://maven.apache.org/).

Put Maven in your PATH. To build BioSim, simply open a console inside the BioSim directory and type:
```
mvn clean package
```

## Running
There are bash scripts in BIOSIM/bin for Unix-like systems and batch scripts in BIOSIM/bin/win for Windows.

Open a terminal and cd to one of the above directories and type:
```
run-biosim
```

This will start both the server and client. The server runs on port 8080 by default, but you can specify a different port as a command-line argument:
```
run-biosim 8081
```

You can also run the server and client separately:
```
run-server [port]
run-client [server-url]
```

For headless operation (server only), use:
```
run-biosim-headless [port]
```

## REST API
BioSim now uses a RESTful API for communication between components. The API is documented below:

### Driver Endpoints
- `GET /api/driver` - Get information about the driver
- `POST /api/driver/start` - Start the simulation
- `POST /api/driver/stop` - Stop the simulation
- `POST /api/driver/pause` - Pause the simulation
- `POST /api/driver/resume` - Resume the simulation
- `POST /api/driver/reset` - Reset the simulation
- `POST /api/driver/tick` - Advance the simulation by one tick
- `POST /api/driver/run-till-n` - Set the number of ticks to run the simulation for
- `POST /api/driver/tick-length` - Set the tick length

### Module Endpoints
- `GET /api/modules` - Get all modules
- `GET /api/modules/{name}` - Get a specific module
- `POST /api/modules/{name}/malfunction` - Start a malfunction in a module
- `POST /api/modules/{name}/fix` - Fix a malfunction in a module
- `POST /api/modules/{name}/reset` - Reset a module

### Sensor Endpoints
- `GET /api/sensors` - Get all sensors

### Actuator Endpoints
- `GET /api/actuators` - Get all actuators

## Docker
To build and run using Docker, run
```sh
docker build -t biosim .
```

and then to run the container:
```sh
docker run -p 8080:8080 biosim 
```

## License
GPL v3
