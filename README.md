# BioSim
BioSim is a research project being developed at NASA Johnson Space Center. The objective is to create a portable simulation of a typical integrated advanced life support system in a typical mission scenario with malfunctions and perturbations.

Currently the simulation is written entirely in Java for the sake of portability, maintenance, and development.
Each component, however, uses CORBA for communication and so any language with an ORB can interface with the simulation.  We have tested the simulation on Windows, Linux and Mac platforms. Also provided is a user interface showing the internals of the simulation and a logging facility.
## Building
Download the <a href="http://java.sun.com/getjava">Java Runtime Environment</a> necessary to run BioSim (version 1.8 or greater).

Checkout the source code from GitHub.

To compile BioSim, you'll need <a href="http://ant.apache.org/">Ant</a>.

Put Ant in your PATH.  To build BioSim, simply open a console inside the BioSim directory and type "ant".

## Running
There are bash scripts in BIOSIM/bin and batch scripts in BIOSIM/bin/win.

Open a terminal and cd to one of the above directories and type:
```
run-biosim
```

## License
GPL v3
