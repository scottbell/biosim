# BioSim: An Integrated Simulation of an Advanced Life Support System for Intelligent Control Research

## Table of Contents

1. [Introduction](#introduction)
2. [Background on Advanced Life Support](#background-on-advanced-life-support)
   - [Modules](#modules)
     - [Environment](#environment)
     - [Crew](#crew)
     - [Water](#water)
     - [Air](#air)
     - [Biomass](#biomass)
     - [Food Processing](#food-processing)
     - [Waste](#waste)
     - [Thermal Control](#thermal-control)
     - [Power](#power)
     - [Accumulators](#accumulators)
     - [Injectors](#injectors)
3. [Simulation Properties](#simulation-properties)
   - [Producer/Consumer Model](#producerconsumer-model)
   - [Success Criteria](#success-criteria)
   - [Stochastic Processes](#stochastic-processes)
   - [Malfunctions](#malfunctions)
   - [Crew Activity Scheduling](#crew-activity-scheduling)
   - [Mass Balance](#mass-balance)
4. [Simulation Implementation](#simulation-implementation)
5. [Installing the Simulation](#installing-the-simulation)
   - [Installing Source Code](#installing-source-code)
6. [Running the Simulation](#running-the-simulation)
7. [Configuring the Simulation](#configuring-the-simulation)
   - [Initial Conditions](#initial-conditions)
   - [Enabling Stochastic Processes](#enabling-stochastic-processes)
   - [Logging](#logging)
8. [Controlling the Simulation](#controlling-the-simulation)
   - [Controlling Simulation Runs](#controlling-simulation-runs)
   - [Accessing Sensors and Actuators](#accessing-sensors-and-actuators)
   - [Environments](#environments)
   - [Crew](#crew-1)
   - [Air](#air-1)
   - [Water](#water-1)
   - [Biomass](#biomass-1)
   - [Dry Waste](#dry-waste)
   - [Power](#power-1)
9. [Using the REST API](#using-the-rest-api)
   - [Simulation Endpoints](#simulation-endpoints)
   - [Malfunction Endpoints](#malfunction-endpoints)
10. [Using the Provided Scripts](#using-the-provided-scripts)
11. [Control Examples](#control-examples)
    - [JSC Genetic Algorithm](#jsc-genetic-algorithm)
    - [Rice Reinforcement Learner](#rice-reinforcement-learner)
    - [Texas Tech Reinforcement Learner](#texas-tech-reinforcement-learner)
12. [Conclusions](#conclusions)

## Introduction

Advanced life support systems have multiple interacting subsystems, which makes control a particularly challenging task. The simulation described in this document provides a testbed for integrated control research. There have been other integrated life support simulations, and we have learned from those efforts. This simulation is designed exclusively for integrated controls research, which imposes different requirements.

For example, the simulation is accessed through sensors and actuators, just as a real system would be. Noise and uncertainty are built in and controllable. Malfunctions and failures of subsystems are modeled and manifest themselves through anomalous readings in the sensors. Crew members are taskable and their tasks have purpose and meaning in the simulation. In essence, the simulation is a replacement for the Advanced Life Support (ALS) hardware and crew, allowing for testing of control approaches in advance of any integrated test.

We want the simulation to be used to develop and evaluate integrated control techniques. There are still many open research questions with respect to controlling advanced life support systems. For example, is a distributed or hierarchical approach better? What role does machine learning play in control? How can symbolic, qualitative control approaches be integrated with continuous, quantitative approaches? How can we evaluate different control philosophies? We hope that the controls community can use this simulation to begin to build systems which will provide us with answers to these kinds of questions.

The simulation is written in Java and is accessed using a RESTful API. This allows for integration with any programming language that supports HTTP requests. The distributed nature of the simulation allows for multiple instances to be run in parallel, making it ideal for testing advanced control concepts such as genetic algorithms or reinforcement learning, which require multiple trials.

This document begins with a brief introduction to advanced life support systems and provides details on installing, running and interacting with the simulation.

## Background on Advanced Life Support

An advanced life support system is one in which many resources are reused or regenerated. Such systems will be necessary for long duration human missions, such as those to Mars, where resupply opportunities are limited. Typically they have thin margins for mass, power and buffers, which requires optimization and tight control. Also, advanced life support systems consist of many interconnected subsystems all of which interact in both predictable and unpredictable ways. Autonomous control of these systems is desirable. This section gives enough background so that users of the simulation can understand the major components of an advanced life support system. 

### Modules

An advanced life support system consists of many interacting subsystems. While each is self-contained, they rely on each other for various resources. The simulation is built from a set of modules that each produce and consume different resources.

#### Environment

An environment contains air that is consumed by either people or crops. Air contains a mixture of gases -- in our simulation these gases are oxygen (O2), carbon dioxide (CO2), nitrogen (N), water vapor (H20), and other gasses (trace). The initial composition of the gases is set by the simulation initialization file. As the simulation runs, modules may consume air from the simulation and replace it with air of a different composition. Thus, the composition of gases and pressure in the air change over time and can be measured by environment sensors. As with all modules, there can be multiple environments. For example, it is common for crew members and crops to have different air compositions, and that is the default in this simulation.

#### Crew

The crew module is implemented using models described in the literature. The number, sex, age and weight of the crew are settable as input parameters. The crew cycles through a set of activities (sleep, maintenance, recreation, etc.). As they do so they consume O2, food and water and produce CO2, dirty water and solid waste. The amount of resources consumed and produced varies according to crew member attributes and their activities. The crew's activities can be adjusted by passing a new crew schedule to the crew module. A default schedule can also be used. The crew module is connected to a crew environment that contains an atmosphere that they breathe. The initial size and gas composition (percentages of O2, CO2, H2O and inert gases) are input parameters and the default is an atmosphere equivalent to sea level air. As the simulation progresses the mixture of gases in the atmosphere changes.

#### Water

**Consumes:** Power, Grey Water, Dirty Water  
**Produces:** Potable Water

The water recovery module consumes dirty water, grey water (i.e., water that can be used for washing but not drinking), and power and produces potable water. The water recovery module consists of four subsystems that process the water. The biological water processing (BWP) subsystem removes organic compounds. Then the water passes to a reverse osmosis (RO) subsystem, which makes 85% of the water passing through it grey. The 15% of water remaining from the RO (called brine) is passed to the air evaporation subsystem (AES), which recovers the rest. These two streams of grey water (from the RO and the AES) are passed through a post-processing subsystem (PPS) to create potable water. An external controller can turn on or off various subsystems. For example, all water can pass through the AES at a higher power cost.

#### Air

**Consumes:** Power, H2, Air, CO2, Potable Water  
**Produces:** Air (with less CO2), O2, H2, CO2, Potable Water

The air component takes in exhalant CO2 and produces O2 as long as there is sufficient power being provided to the system. There are three interacting air subsystems: the Variable Configuration Carbon Dioxide Removal (VCCR) System in which CO2 is removed from the air stream; the Carbon Dioxide Reduction System (CRS), which also removes CO2 from the air stream using a different process and producing different gases than the VCCR; and the Oxygen Generation System (OGS) in which O2 is added to the air stream by breaking water down into hydrogen and oxygen. It is important to note that both the removal of CO2 and the addition of O2 are required for human survival. It is also important to note that the biomass component (next subsection) also removes CO2 and adds O2.

#### Biomass

**Consumes:** Power, Potable Water, Grey Water, Air  
**Produces:** Air (with more CO2), Biomass, Dirty Water, CO2, Potable Water

The biomass component is where crops are grown. It produces both biomass, which can be turned into food, and O2 and consumes water, power (light) and CO2. The system is modeled as shelves that contain plants, lights and water. Shelves are planted and harvested and there is growth cycle for each shelf. Currently, ten crops are modeled and can be planted in any ratio.

#### Food Processing

**Consumes:** Power, Biomass  
**Produces:** Food

Before biomass can be consumed by the crew it must be converted to food. The food processing component takes biomass, power and crew time and produces food and solid waste. The crew needs to be involved in this process as it is labor intensive.

#### Waste

**Consumes:** Power, Dry Waste, O2  
**Produces:** CO2

The waste component consumes power, O2 and solid waste and produces CO2. It is modeled on an incinerator. Incineration can be scheduled.

#### Thermal Control

The thermal control component regulates the air temperature in the habitat. This component is not implemented for this simulation and it is assumed that external controllers are maintaining chamber temperature.

#### Power

**Produces:** Power

The power component supplies power to all of the other components that need it. There are two choices for power in the simulation. The first is nuclear power, which supplies a fixed amount throughout the lifetime of the simulation. The second is solar power, which supplies a varying amount (day/night cycle) of power to each component.

#### Accumulators

**Consumes:** All  
**Produces:** All

The accumulator component can take a resource from any store or environment and place it into another environment or store. It is functionally equivalent to an injector.

#### Injectors

**Consumes:** All  
**Produces:** All

The injector component can take a resource from any store or environment and place it into another environment or store. It is functionally equivalent to an accumulator.

## Simulation Properties

The simulation implements the modules outlined in the previous section. BioSim does *not* simulate at the level of valves, pumps, switches, etc. Instead, modules are implemented in a producer/consumer relationship, which is described in the next subsection. The simulation also provides additional functionality to help test and debug control programs.

### Producer/Consumer Model

No component in BioSim directly interacts with other components. Instead, each component has a rigid set of resources that it consumes and produces. The resources are taken from stores/environments and put into stores/environments. The resources for BioSim currently consist of power, potable water, grey water, dirty water, air, H2, nitrogen, O2, CO2, biomass, food, and dry waste. At each simulation tick, each module takes resource from its store (its consumables), and puts resources into stores (its products). Stores always report their values (level and capacity) from the last tick and report new values after every component in the simulation has been ticked. Resource conflicts can occur when two components ask for a limited resource, i.e., two WaterRS's both need 20 liters of dirty water and there is only 20 liters in the dirty water tank. This is currently resolved on a winner-take-all basis that is for all practical purposes randomized.

### Success Criteria

In order to compare different control approaches there should be objective criteria for a successful advanced life support system mission. Several possibilities exist already in the simulation. For example, the length of the mission before consumables are gone is a success criterion. As is minimizing the starting levels of stores or minimizing the sizes of intermediary buffers. However, many of these fail to get at the true success of a mission, the productivity of the crew in performing their science objectives. Thus, the simulation includes an artificial productivity measure. As the crew cycles through their activities, the amount of time they spend doing "mission" tasks is accumulated. This number is also multiplied by a factor that takes into account the amount of sleep, exercise, water, food and oxygen they are getting to approximate crew effectiveness at performing the task (a happy crew is a productive crew!).

### Stochastic Processes

Any sufficiently complex process, especially one with biological components, will not be deterministic. That is, given the same starting conditions and inputs it will not produce the exact same outputs each time it is run. A stochastic process is one in which chance or probability affect the outcome. This simulation offers both deterministic and stochastic operations. In the former case, running the simulation twice with the same inputs and initial conditions will produce the same results. This may be useful for quantitatively comparing different control approaches against each other. In the latter case, the user can control the amount of variability in the simulation. This is modeled using a Gaussian distribution. The deviation is determined by the stochastic intensity; a higher intensity will yield a higher deviation. The filter is then appropriately applied to certain variables of the model. This can be used to test a control system's ability to deal with stochastic processes.

### Malfunctions

The simulation also has the ability to accept malfunction requests. These requests will change the operating regime of the simulation. For example, by causing a crew member to be sick, power supplies to drop, water to leak, plants to die, etc. Different modules will have different sensitivities, for example once plants die they cannot recover, but if the water module is damaged and then repaired it will operate normally. Of course, if levels of CO2, water or food reach hard-coded critical levels the crew will abandon the mission and return home.

### Crew Activity Scheduling

Crew members in an advanced life support system do not just consume and produce -- they do activities. These may be science, maintenance, exercise, sleep, etc. The different activities go for a specified length and for a specified intensity. The intensity is tied to how many resources the crew member will consume performing the activity (e.g., sleep takes less O2 to perform than exercise). The following shows the nominal daily routine for a crew member:

1. Sleep for 8 hours
2. Hygiene for 1 hour
3. Exercise for 1 hour
4. Eating for 1 hour
5. Mission tasks for 8 hours
6. Health for 1 hour
7. Maintenance for 1 hour
8. Leisure for 2 hours

This schedule can be interrupted by malfunctions (a crew member's activity changed to repair), sickness, or even mission end (if the crew member hasn't received proper resources).

### Mass Balance

Mass balanced means resources consumed by components are returned in totality (but usually in a different form) to stores, no mass is lost. This is important for closed loop simulations as it verifies the models (under perfect conditions) are keeping with the conservation of energy and mass. Our simulation has been mass balanced to a reasonable degree. There is a slight loss in mass from the crew (they don't gain weight from eating or produce heat) and from the plants (who don't produce heat). We plan to address these issues in the future, but the simulation is currently mass balanced enough to provide a very decent closed loop simulation.

## Simulation Implementation

All of the simulation components are written in Java to make the simulation portable, and can run anywhere Java can run. We also have a Docker installation to ease in building and running. A user interface for BioSim is provided by an [Open MCT plugin](https://github.com/scottbell/openmct-biosim).

The simulation exposes a RESTful API that can be accessed from any programming language that supports HTTP requests. This makes it easy to integrate with your preferred language and tools.

## Installing and Running the Simulation

Please read the [README.md](../README.md) file in the root directory of your BioSim distribution. The [README.md](../README.md) contains detailed instructions for building and running the simulation.

## Configuring the Simulation

The previous chapter discussed installing and running the default BioSim. This section will discuss configuring BioSim to address specific user interests. This includes changing the underlying producer/consumer relationship, changing the stochastic nature of BioSim and changing the logging of data.

### Initial Conditions

BioSim is almost infinitely reconfigurable -- from the number of crew members to the size and number of the different modules and should be able to provide scenarios ranging from transit vehicles to Mars colonies. The start-up configuration of BioSim is controlled by an XML file that is read in during BioSim initialization. If you want to change BioSim's configuration you need to change the XML file.

You can use the XML configuration files in the `configurations` directory as templates, modify them, and then provide the path to your configuration when starting the simulation using the `run-simulation` script with the `--config` option.

#### XML File Format

A snippet from a typical initialization XML file might look like this:

```xml
<SimBioModules>
  <crew>
    <CrewGroup name="CrewGroup">
      <potableWaterConsumer maxFlowRates="100" desiredFlowRates="100" inputs="PotableWaterStore"/>
      <airConsumer maxFlowRates="0" desiredFlowRates="0" inputs="CrewEnvironment"/>
      <foodConsumer maxFlowRates="100" desiredFlowRates="100" inputs="FoodStore"/>
      <dirtyWaterProducer maxFlowRates="100" desiredFlowRates="100" outputs="DirtyWaterStore"/>
      <greyWaterProducer maxFlowRates="100" desiredFlowRates="100" outputs="GreyWaterStore"/>
      <airProducer maxFlowRates="0" desiredFlowRates="0" outputs="CrewEnvironment"/>
      <!-- ... -->
      <crewPerson name="Bob Roberts" age="43" weight="77" sex="MALE">
        <schedule>
          <activity name="sleep" length="8" intensity="1"/>
          <activity name="hygiene" length="1" intensity="2"/>
          <activity name="exercise" length="1" intensity="5"/>
          <activity name="eating" length="1" intensity="2"/>
          <activity name="mission" length="9" intensity="3"/>
          <activity name="health" length="1" intensity="2"/>
          <activity name="maintenance" length="1" intensity="2"/>
          <activity name="leisure" length="2" intensity="2"/>
        </schedule>
      </crewPerson>
      <!-- ... -->
    </CrewGroup>
  </crew>
  <!-- ... other modules ... -->
</SimBioModules>
```

This file implements the producer/consumer relationship at the heart of the simulation. Each module has a name and a list of resources that it consumes and produces along with max and desired flow rates and what source(s) produce(s) that resource (the `inputs`) or from where the resource is drawn (`outputs`). More than one source or sink is allowed, but the module tries to draw as much as it can from the first. In the above snippet some flow rates are 0 because the user has no control over them (e.g., crew air consumption is based on crew physiology and can't be directly controlled). As shown above, you can have as many (or as few) crew members as you want of various ages, weights and sexes. You can give a default schedule for their day (which can be changed via the REST API during simulation runs).

The best way to change the configuration is to start with an existing file from the `configurations` directory and edit it to reflect your requirements.

### Enabling Stochastic Processes

BioSim provides the ability to change the stochastic nature of the underlying modules via the XML initialization file. If you want a purely deterministic simulation you can do that. If you want a very unpredictable simulation you can do that as well. This section provides an overview of the stochastic controls available in BioSim. The type `StochasticIntensity` includes the following values:

- `HIGH_STOCH`
- `MEDIUM_STOCH`
- `LOW_STOCH`
- `NONE_STOCH`

Each of these controls the width of the Gaussian function that provides the variations in output. Setting the stochastic intensity to NONE_STOCH means that the simulation is deterministic. All noise is Gaussian. The outputs of a module as determined by the module's underlying equations are run through a Gaussian filter before being output by the simulation. The Gaussian filter's parameters are controlled by the stochastic intensity, namely the higher the stochastic intensity, the higher the deviation.

#### Setting the Stochastic Intensity via the XML Configuration File

To change the stochastic intensity of a module in XML, simply add setStochasticIntensity as an attribute to the module (if one isn't listed, it defaults to NONE_STOCH). For example:

```xml
<AirRS name="AirRS" setStochasticIntensity="HIGH_STOCH">
```

The stochastic level for the entire simulation (which is equivalent to setting each module's stochastic intensity) can be set by adding setStochasticIntensity to the Globals section at the head of the XML document. For example:

```xml
<Globals crewsToWatch="CrewGroup" setStochasticIntensity="LOW_STOCH">
```

## Controlling the Simulation

The previous chapters showed you how to run BioSim, monitor its internal state, and configure it to your specifications. This chapter will show you how to connect to BioSim and change its underlying operation using the REST API.

### Using the REST API

BioSim exposes a RESTful API that allows you to control the simulation, access sensor data, and inject malfunctions. The API can be accessed from any programming language that supports HTTP requests. To get a full list of the endpoints, see the [README.md](../README.md).

## Conclusions

BioSim provides a flexible platform for researching integrated control approaches for advanced life support systems. By providing a realistic simulation of the complex interactions between life support subsystems, it enables researchers to develop and test control strategies before implementing them in hardware testbeds.

The RESTful API allows integration with any programming language that supports HTTP requests, making it accessible to a wide range of researchers and control approaches. The simulation's ability to model stochastic processes and system malfunctions provides a realistic environment for testing the robustness of control strategies.

We encourage the controls community to use BioSim to explore different control philosophies and approaches, and to help answer the many open research questions in advanced life support control.
