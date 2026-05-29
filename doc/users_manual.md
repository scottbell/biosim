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
   - [Configuration File Format](#configuration-file-format)
   - [XML Document Structure](#xml-document-structure)
   - [Units of Measurement](#units-of-measurement)
   - [Global Run-Control Settings](#global-run-control-settings)
   - [Attributes Shared by Every Module](#attributes-shared-by-every-module)
   - [Flow Definitions (Producers and Consumers)](#flow-definitions-producers-and-consumers)
   - [Stores: Levels, Capacities, and Resupply](#stores-levels-capacities-and-resupply)
   - [Environment Settings](#environment-settings)
   - [Crew Configuration](#crew-configuration)
   - [Crop and Biomass Configuration](#crop-and-biomass-configuration)
   - [Air Equipment](#air-equipment)
   - [Water Equipment](#water-equipment)
   - [Power Equipment](#power-equipment)
   - [Waste Equipment](#waste-equipment)
   - [Thermal Control Equipment](#thermal-control-equipment)
   - [Resource Movers (Framework Modules)](#resource-movers-framework-modules)
   - [Sensors and Actuators](#sensors-and-actuators)
   - [Stochastic Processes](#stochastic-processes-1)
   - [Reliability and Malfunctions](#reliability-and-malfunctions)
   - [A Complete Minimal Example](#a-complete-minimal-example)
   - [Further Reading](#further-reading)
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

The previous chapter discussed installing and running the default BioSim. This section is a reference for everything you can configure in a simulation. BioSim is almost infinitely reconfigurable -- from the number of crew members to the size and number of the different modules -- and is meant to span scenarios ranging from short transit vehicles to long-duration Mars surface habitats.

A BioSim scenario is defined entirely by a single XML document that is read in during initialization. Every option described below is grounded in the project's XML schema (`etc/schema/BiosimInitSchema.xsd` and the files it includes) and the ready-made scenarios in the `configuration/` directory. The easiest way to build a new scenario is to copy an existing file (for example `configuration/default.biosim`) and edit it to match your requirements.

You can launch a custom configuration with the `run-simulation` script and the `--config` option, or by POSTing the XML to `/api/simulation/start` (see [Using the REST API](#using-the-rest-api)).

### Configuration File Format

A configuration file is an XML document whose root element is `<biosim>`, bound to the BioSim namespace. The standard preamble (also used by every shipped scenario) is:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<biosim xmlns="http://www.traclabs.com/biosim"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.traclabs.com/biosim ../../schema/BiosimInitSchema.xsd">
  ...
</biosim>
```

> **Tip:** Every module's `moduleName` must be unique across the whole document -- the schema enforces this as a key. Producers and consumers reference other modules (stores and environments) by that name, so a typo in a `moduleName`, `inputs`, or `outputs` value will break the connection.

### XML Document Structure

The `<biosim>` element contains up to four top-level sections, which must appear in this order:

| Section | Required | Purpose |
|---------|----------|---------|
| `<Globals>` | Yes | Run-control settings for the whole simulation (see below). |
| `<SimBioModules>` | No | The physical modules: environments, stores, life-support equipment, crew, crops, power, etc. |
| `<Sensors>` | No | Read-only sensors that observe modules and raise alarms. |
| `<Actuators>` | No | Actuators that let an external controller command flow rates and valves. |

`<SimBioModules>`, `<Sensors>`, and `<Actuators>` are each organized into the same nine functional groups, any of which may be omitted: `air`, `crew`, `environment`, `food`, `power`, `water`, `waste`, `thermal`, and `framework`.

```xml
<biosim ...>
  <Globals .../>
  <SimBioModules>
    <environment> ... </environment>
    <air>         ... </air>
    <water>       ... </water>
    <power>       ... </power>
    <food>        ... </food>
    <waste>       ... </waste>
    <thermal>     ... </thermal>
    <crew>        ... </crew>
    <framework>   ... </framework>
  </SimBioModules>
  <Sensors>   ... </Sensors>
  <Actuators> ... </Actuators>
</biosim>
```

### Units of Measurement

BioSim is a mass-balanced simulation, so every quantity is expressed in a consistent physical unit. Knowing these units is essential when choosing flow rates, capacities, and initial levels.

| Quantity | Unit |
|----------|------|
| Power (production, consumption, store level/capacity) | watts (W) |
| Potable, grey, and dirty water | liters (L) |
| O2, CO2, H2, nitrogen, methane, and water vapor | moles |
| Biomass, food, and dry waste | kilograms (kg) |
| Environment and airlock volume | liters (L) |
| Gas partial pressure and total pressure | kilopascals (kPa) |
| Crop growing area (`cropArea`) | square meters (m²) |
| Light intensity (`maxLumens`) | lumens |
| Crew member age | years |
| Crew member weight | kilograms (kg) |
| Time | ticks |

A *tick* is the simulation's unit of time. At the default `tickLength` of `1`, one tick represents one hour of simulated time, which is why crew activity lengths and the environment `dayLength` (default `24`) are expressed in hours.

### Global Run-Control Settings

The `<Globals>` element controls how the run starts, stops, and reports. All attributes are optional and fall back to the defaults below. `<Globals>` may also contain optional `<comment>`, `<author>`, and `<nameServiceLocation>` child elements for documentation/bookkeeping.

| Attribute | Type | Default | Description |
|-----------|------|---------|-------------|
| `startPaused` | boolean | `true` | If `true`, the simulation loads but waits for `tick`/start commands before advancing. |
| `runTillN` | integer | `-1` | Stop after this many ticks. `-1` means no tick limit. |
| `runTillCrewDeath` | boolean | `true` | Stop the run when a watched crew group dies. |
| `runTillPlantDeath` | boolean | `false` | Stop the run when a watched plant/crop dies. |
| `crewsToWatch` | string list | – | Space-separated `moduleName`s of crew groups whose death ends the run and feeds productivity metrics. |
| `plantsToWatch` | string list | – | Space-separated `moduleName`s of biomass producers to watch for plant death. |
| `exitWhenFinished` | boolean | `false` | Exit the server process when end criteria are met. |
| `driverStutterLength` | integer | `-1` | Milliseconds to pause between ticks (useful for visualizing a live run). `-1`/`0` runs as fast as possible. |
| `isLooping` | boolean | `false` | Restart the scenario automatically when it ends. |
| `tickLength` | non-negative float | `1` | Hours of simulated time per tick. |
| `stochasticIntensity` | enum | `NONE_STOCH` | Legacy global noise hint; the current engine drives variability through per-module `normalStochasticFilter` elements instead (see [Stochastic Processes](#stochastic-processes-1)). |

```xml
<Globals driverStutterLength="500" crewsToWatch="Crew_Quarters_Group"
         runTillCrewDeath="false" tickLength="1" startPaused="false"/>
```

### Attributes Shared by Every Module

Every module inside `<SimBioModules>` (and every sensor/actuator) inherits a common set of attributes and optional child elements:

| Attribute | Type | Default | Description |
|-----------|------|---------|-------------|
| `moduleName` | string | *required* | Unique name used to reference the module elsewhere. |
| `logLevel` | enum | `INFO` | Logging verbosity: `OFF`, `INFO`, `DEBUG`, `ERROR`, `WARN`, `FATAL`, or `ALL`. |
| `createLocally` | boolean | `true` | Reserved for distributed deployments. |
| `isBionetEnabled` | boolean | `false` | Reserved for Bionet integration. |

Optional children that may appear on **any** module:
- `<malfunction>` -- schedule a malfunction at start-up (see [Reliability and Malfunctions](#reliability-and-malfunctions)).
- One failure-decider element -- statistically model hardware reliability (see [Reliability and Malfunctions](#reliability-and-malfunctions)).
- `<normalStochasticFilter>` -- add Gaussian noise to the module's outputs (see [Stochastic Processes](#stochastic-processes-1)).

### Flow Definitions (Producers and Consumers)

The producer/consumer model is the heart of BioSim. Equipment modules declare which resources they **consume** (pull from stores/environments) and which they **produce** (push into stores/environments). Each flow element carries three attributes:

| Attribute | Description |
|-----------|-------------|
| `inputs` (consumers) / `outputs` (producers) | Space-separated list of store/environment `moduleName`s this flow connects to. |
| `maxFlowRates` | Space-separated list of upper bounds on the flow, in the resource's unit per tick. |
| `desiredFlowRates` | Space-separated list of target flows; clamped to `maxFlowRates`. |

The three lists are positional and **must be the same length**. When several connections are listed, the module satisfies them in order, drawing (or pushing) as much as possible from the first entry before moving on. A flow rate of `0` disables that flow or marks it as not externally controllable (for example, crew air exchange is governed by physiology, so it is configured as `0`).

```xml
<!-- A single connection -->
<powerConsumer inputs="General_Power_Store" desiredFlowRates="1000" maxFlowRates="1000"/>

<!-- Multiple connections: the module draws from each store in turn -->
<powerConsumer inputs="Backup_Power_Store General_Power_Store"
               desiredFlowRates="100 100" maxFlowRates="100 100"/>
```

The resource-specific element names are: `powerConsumer`/`powerProducer`, `potableWaterConsumer`/`potableWaterProducer`, `greyWaterConsumer`/`greyWaterProducer`, `dirtyWaterConsumer`/`dirtyWaterProducer`, `airConsumer`/`airProducer`, `O2Consumer`/`O2Producer`, `CO2Consumer`/`CO2Producer`, `H2Consumer`/`H2Producer`, `nitrogenConsumer`/`nitrogenProducer`, `methaneConsumer`/`methaneProducer`, `biomassConsumer`/`biomassProducer`, `foodConsumer`/`foodProducer`, `dryWasteConsumer`/`dryWasteProducer`, and `LightConsumer`. Which flows a module accepts is fixed by its type (documented in each section below). Desired flow rates can also be changed at runtime through the REST API.

### Stores: Levels, Capacities, and Resupply

Stores are buffers that hold a single resource. Every store type shares the same attributes:

| Attribute | Type | Default | Description |
|-----------|------|---------|-------------|
| `capacity` | non-negative float | *required* | Maximum amount the store can hold (in the resource's unit). |
| `level` | non-negative float | *required* | Initial amount in the store at tick 0. |
| `resupplyFrequency` | non-negative integer | `0` | Add `resupplyAmount` every this-many ticks. `0` disables resupply. |
| `resupplyAmount` | non-negative float | `0` | Amount added on each resupply event. |

The available store elements (placed inside the matching group) are:

| Group | Store elements |
|-------|----------------|
| `air` | `O2Store`, `CO2Store`, `H2Store`, `NitrogenStore`, `MethaneStore` |
| `water` | `PotableWaterStore`, `GreyWaterStore`, `DirtyWaterStore` |
| `power` | `PowerStore` |
| `food` | `BiomassStore`, `FoodStore` |
| `waste` | `DryWasteStore` |

```xml
<O2Store moduleName="O2_Store" capacity="10000" level="1000"/>

<!-- A small food cache topped up by 0.01 kg every tick -->
<FoodStore moduleName="FoodStore" capacity="0.01" level="0.01"
           resupplyAmount="0.01" resupplyFrequency="1"/>
```

`FoodStore` and `BiomassStore` carry extra attributes that describe the stored matter:

- **`FoodStore`**: `waterContent` (kg of water per stored unit, default `5`) and `cropType` (default `WHEAT`).
- **`BiomassStore`**: `inedibleFraction` (0–1, default `0.25`), `edibleWaterContent` (default `5`), `inedibleWaterContent` (default `5`), and `cropType` (default `WHEAT`).

### Environment Settings

Environments (`<environment>` group) hold the air that crews and crops breathe. The main element is `SimEnvironment`:

| Attribute | Type | Default | Description |
|-----------|------|---------|-------------|
| `initialVolume` | non-negative float | *required* | Volume of the chamber in liters. |
| `leakRate` | non-negative float | `0` | Fraction of air lost per tick. |
| `dayLength` | non-negative float | `24` | Length of a day/night cycle in hours (used by solar power and lighting). |
| `hourOfDayStart` | non-negative float | `0` | Hour of the day at tick 0. |
| `maxLumens` | non-negative float | `50000` | Peak light intensity available to crops. |
| `airlockVolume` | non-negative float | `3.7` | Volume vented per airlock cycle (EVA). |
| `dangerousOxygenThreshold` | fraction 0–1 | `1.0` | O2 fraction above which a fire/oxygen hazard is flagged. |

By default an environment starts at a sea-level-equivalent atmosphere. You can override the starting gas mixture with **one** optional child element:

- `<moleInitialization>` -- specify exact moles: `initialCO2Moles`, `initialO2Moles`, `initialNitrogenMoles`, `initialWaterMoles`, `initialOtherMoles` (all required).
- `<percentageInitialization>` -- specify a `totalPressure` (kPa) plus gas fractions: `co2Percentage`, `o2Percentage`, `nitrogenPercentage`, `waterPercentage`, `otherPercentage` (all required, summing to 1).

```xml
<SimEnvironment moduleName="Crew_Quarters" initialVolume="2700000">
  <percentageInitialization totalPressure="55"
      o2Percentage="0.33" nitrogenPercentage="0.659" co2Percentage="0"
      waterPercentage="0.01" otherPercentage="0.001"/>
</SimEnvironment>
```

Two helper modules also live in the `environment` group and move air between chambers:

- **`Dehumidifier`** -- pulls water vapor out of the air. Flows: `airConsumer`, `dirtyWaterProducer`.
- **`Fan`** -- pushes air from one chamber to another. Flows: `airConsumer`, `powerConsumer`, `airProducer`.

### Crew Configuration

The `<crew>` group contains one or more `CrewGroup` modules; each group shares an environment and a list of crew members.

`CrewGroup` attribute: `isDeathEnabled` (boolean, default `true`) -- when `false`, crew never die from lack of resources, which is handy for open-loop experiments.

A `CrewGroup` must declare these flows (use `0` for those you don't control): `potableWaterConsumer`, `airConsumer`, `foodConsumer`, `dirtyWaterProducer`, `greyWaterProducer`, `airProducer`, `dryWasteProducer`. It then contains zero or more `crewPerson` elements:

| `crewPerson` attribute | Type | Default | Description |
|------------------------|------|---------|-------------|
| `name` | string | *required* | Crew member's name. |
| `age` | non-negative float | *required* | Age in years. |
| `weight` | non-negative float | *required* | Weight in kilograms. |
| `sex` | enum | *required* | `MALE` or `FEMALE`. |
| `arrivalDate` | non-negative integer | `0` | Tick at which the member joins. |
| `departureDate` | integer | `-1` | Tick at which the member leaves (`-1` = never). |
| `implementation` | enum | `NORMAL` | Crew model: `NORMAL` or `MATLAB`. |

Each `crewPerson` has a `<schedule>` of one or more `<activity>` elements that repeats daily. An activity has a `name`, a `length` in hours, and an `intensity` (a non-negative integer; higher intensity consumes more O2/water/food). Activities drive the resources a crew member uses, so a realistic 24-hour schedule should cover the full day.

```xml
<CrewGroup moduleName="Crew_Quarters_Group">
  <potableWaterConsumer inputs="Potable_Water_Store" desiredFlowRates="3" maxFlowRates="3"/>
  <airConsumer          inputs="Crew_Quarters_Environment" desiredFlowRates="0" maxFlowRates="0"/>
  <foodConsumer         inputs="Food_Store" desiredFlowRates="5" maxFlowRates="5"/>
  <dirtyWaterProducer   outputs="Dirty_Water_Store" desiredFlowRates="100" maxFlowRates="100"/>
  <greyWaterProducer    outputs="Grey_Water_Store"  desiredFlowRates="100" maxFlowRates="100"/>
  <airProducer          outputs="Crew_Quarters_Environment" desiredFlowRates="0" maxFlowRates="0"/>
  <dryWasteProducer     outputs="Dry_Waste_Store"   desiredFlowRates="10"  maxFlowRates="10"/>
  <crewPerson name="Buck Rogers" age="35" weight="75" sex="MALE">
    <schedule>
      <activity name="sleep"    length="8"  intensity="0"/>
      <activity name="mission"  length="12" intensity="2"/>
      <activity name="exercise" length="2"  intensity="5"/>
      <activity name="eating"   length="2"  intensity="2"/>
    </schedule>
  </crewPerson>
</CrewGroup>
```

**Extravehicular activity (EVA):** an activity can move a crew member into a different (suit/airlock) environment for its duration by giving it the EVA type and naming the destination crew group:

```xml
<activity name="EVA" length="2" intensity="4"
          xsi:type="EVAActivityType" evaCrewGroup="EVA_Crew_Group"/>
```

### Crop and Biomass Configuration

Crops are grown in a `BiomassPS` (biomass production system) module inside the `<food>` group. It is modeled as a set of planting *shelves*; biomass is harvested into a `BiomassStore` and converted to edible food by a `FoodProcessor`.

`BiomassPS` attributes: `autoHarvestAndReplant` (boolean, default `true`) and `isDeathEnabled` (boolean, default `true`). Required flows: `powerConsumer`, `potableWaterConsumer`, `greyWaterConsumer`, `airConsumer`, `dirtyWaterProducer`, `biomassProducer`, `airProducer`. It may contain any number of `<shelf>` elements:

| `shelf` attribute | Type | Default | Description |
|-------------------|------|---------|-------------|
| `cropType` | enum | *required* | The crop planted on this shelf (see list below). |
| `cropArea` | non-negative float | *required* | Growing area in square meters. |
| `startTick` | non-negative integer | `0` | Tick at which this shelf is first planted. |

Supported `cropType` values: `DRY_BEAN`, `LETTUCE`, `PEANUT`, `RICE`, `SOYBEAN`, `SWEET_POTATO`, `TOMATO`, `WHEAT`, and `WHITE_POTATO`. Crops can be planted in any mix by adding multiple shelves. The growth, light, water, and gas-exchange behavior of each crop follows NASA's Modified Energy Cascade crop models and the *Baseline Values and Assumptions Document* (BVAD).

```xml
<BiomassPS moduleName="BiomassPS" autoHarvestAndReplant="true">
  <shelf cropType="SOYBEAN" cropArea="1"/>
  <powerConsumer        inputs="General_Power_Store"     desiredFlowRates="400" maxFlowRates="400"/>
  <potableWaterConsumer inputs="Potable_Water_Store"     desiredFlowRates="100" maxFlowRates="100"/>
  <greyWaterConsumer    inputs="Grey_Water_Store"        desiredFlowRates="100" maxFlowRates="100"/>
  <airConsumer          inputs="Crew_Quarters_Environment" desiredFlowRates="0" maxFlowRates="0"/>
  <dirtyWaterProducer   outputs="Dirty_Water_Store"      desiredFlowRates="100" maxFlowRates="100"/>
  <biomassProducer      outputs="Biomass_Store"          desiredFlowRates="100" maxFlowRates="100"/>
  <airProducer          outputs="Crew_Quarters_Environment" desiredFlowRates="0" maxFlowRates="0"/>
</BiomassPS>
```

The **`FoodProcessor`** turns harvested biomass into food. Required flows: `powerConsumer`, `biomassConsumer`, `foodProducer`, `dryWasteProducer`, `waterProducer`.

### Air Equipment

The `<air>` group holds the atmosphere-revitalization equipment and the gas stores listed under [Stores](#stores-levels-capacities-and-resupply). The equipment modules and their required flows are:

| Module | Required flows | Notes |
|--------|----------------|-------|
| `OGS` (Oxygen Generation System) | `powerConsumer`, `potableWaterConsumer`, `O2Producer`, `H2Producer` | Splits water into O2 and H2. |
| `VCCR` (Variable Config. CO2 Removal) | `powerConsumer`, `airConsumer`, `airProducer`, `CO2Producer` | Scrubs CO2 from the air. Attribute `implementation`: `LINEAR` (default) or `DETAILED`. |
| `CDRS` (CO2 Removal System) | `powerConsumer`, `greyWaterConsumer`, `greyWaterProducer`, `airConsumer`, `airProducer`, `CO2Producer` | Alternative CO2 scrubber (ISS-style). |
| `CRS` (CO2 Reduction System) | `powerConsumer`, `CO2Consumer`, `H2Consumer`, `potableWaterProducer`, `methaneProducer` | Sabatier reactor; recovers water from CO2 + H2. |
| `Pyrolizer` | `powerConsumer`, `methaneConsumer`, `H2Producer`, `dryWasteProducer` | Cracks methane to recover hydrogen. |

```xml
<OGS moduleName="OGS">
  <powerConsumer        inputs="General_Power_Store" desiredFlowRates="1000" maxFlowRates="1000"/>
  <potableWaterConsumer inputs="Potable_Water_Store" desiredFlowRates="10"   maxFlowRates="10"/>
  <O2Producer outputs="O2_Store" desiredFlowRates="1000" maxFlowRates="1000"/>
  <H2Producer outputs="H2_Store" desiredFlowRates="1000" maxFlowRates="1000"/>
</OGS>
```

### Water Equipment

The `<water>` group holds the water stores and the recovery system:

- **`WaterRS`** (Water Recovery System) -- required flows: `powerConsumer`, `dirtyWaterConsumer`, `greyWaterConsumer`, `potableWaterProducer`. Attribute `implementation`: `LINEAR` (default) or `NORMAL` (the detailed BWP/RO/AES/PPS subsystem model).

```xml
<WaterRS moduleName="WaterRS" implementation="LINEAR">
  <powerConsumer      inputs="General_Power_Store" desiredFlowRates="1000" maxFlowRates="1000"/>
  <dirtyWaterConsumer inputs="Dirty_Water_Store"   desiredFlowRates="10"   maxFlowRates="10"/>
  <greyWaterConsumer  inputs="Grey_Water_Store"    desiredFlowRates="10"   maxFlowRates="10"/>
  <potableWaterProducer outputs="Potable_Water_Store" desiredFlowRates="1000" maxFlowRates="1000"/>
</WaterRS>
```

### Power Equipment

The `<power>` group supplies and routes electrical power:

| Module | Required flows | Notes |
|--------|----------------|-------|
| `PowerPS` (Power Production System) | `powerProducer` (+ optional `lightConsumer`) | Attributes: `generationType` = `NUCLEAR` (default, constant output), `SOLAR` (varies with the environment day/night cycle), or `STATE_MACHINE`; `upperPowerGeneration` (max watts, default `500`). |
| `GenericPowerConsumer` | `powerConsumer` | A standalone load. Attribute `powerRequired` (watts, required). |
| `RPCM` (Remote Power Controller Module) | `powerConsumer`, `powerProducer` | A switchable power router. Attribute `switchValues` (list of `0`/`1`). |

```xml
<PowerPS moduleName="Nuclear_Source" generationType="NUCLEAR" upperPowerGeneration="3000">
  <powerProducer outputs="General_Power_Store" desiredFlowRates="3000" maxFlowRates="3000"/>
</PowerPS>
```

### Waste Equipment

The `<waste>` group handles solid waste:

- **`Incinerator`** -- burns dry waste. Required flows: `powerConsumer`, `O2Consumer`, `dryWasteConsumer`, `CO2Producer`.
- **`DryWasteStore`** -- buffers solid waste (see [Stores](#stores-levels-capacities-and-resupply)).

### Thermal Control Equipment

The `<thermal>` group models active thermal control:

- **`IATCS`** (Internal Active Thermal Control System) -- required flows: `powerConsumer`, `greyWaterConsumer`, `greyWaterProducer`.

### Resource Movers (Framework Modules)

The `<framework>` group contains general-purpose modules that move *any* resource from one store/environment to another. Each may declare any subset of the consumer/producer flow pairs (power, all three waters, air, O2, CO2, H2, nitrogen, methane, light, biomass, food, dry waste):

- **`Injector`** and **`Accumulator`** -- functionally identical; pull a resource from a source and push it into a destination (e.g., inject nitrogen from a tank into a chamber to maintain pressure).
- **`InfluentValve`** and **`EffluentValve`** -- gate resource flow between modules and can be opened/closed by an actuator.

```xml
<Injector moduleName="Crew_Quarters_Nitrogen_Injector">
  <nitrogenConsumer inputs="Crew_Quarter_Nitrogen_Store" desiredFlowRates="0" maxFlowRates="100"/>
  <nitrogenProducer outputs="Crew_Quarters" desiredFlowRates="0" maxFlowRates="100"/>
</Injector>
```

### Sensors and Actuators

The optional `<Sensors>` and `<Actuators>` sections let an external controller observe and command the simulation, just like real hardware. They use the same nine functional groups as `<SimBioModules>`.

**Sensors** share an `input` attribute (the `moduleName` they observe). Flow-rate sensors additionally need an `index` (which entry of the module's flow array to read). Common types include `GasConcentrationSensor` and `GasPressureSensor` (both take a `gasType` of `O2`, `CO2`, `NITROGEN`, `VAPOR`, or `OTHER`), `TotalPressureSensor`, `TotalMolesSensor`, the air in/out flow-rate sensors, `StoreLevelSensor`, `StoreOverflowSensor`, and `TimeSensor`.

A sensor may carry an optional `<alarms>` block defining `[min, max]` bands for `watch`, `warning`, `distress`, `critical`, and `severe` levels (each as a `_low`/`_high` pair):

```xml
<GasConcentrationSensor input="Crew_Quarters_Environment"
                        moduleName="Co2GasConcentrationSensor" gasType="CO2">
  <alarms>
    <distress_high min="0.002" max="0.003"/>
    <critical_high min="0.003" max="0.004"/>
    <severe_high   min="0.004" max="1"/>
  </alarms>
</GasConcentrationSensor>
```

**Actuators** share an `output` attribute (the `moduleName` they command); flow-rate actuators also need an `index`. They include per-resource flow-rate actuators plus `InfluentValveActuator` and `EffluentValveActuator`.

### Stochastic Processes

Any sufficiently complex process -- especially one with biological components -- is not perfectly deterministic. BioSim lets you add controllable Gaussian noise so you can test how robust a controller is to variability. By default a simulation is deterministic (running it twice with the same inputs produces identical results).

To make a module noisy, add a `<normalStochasticFilter>` child element to it. The module's computed outputs are passed through a Gaussian filter before being reported:

| Attribute | Type | Default | Description |
|-----------|------|---------|-------------|
| `deviation` | double | *required* | Standard deviation of the Gaussian noise; larger values mean more variability. |
| `isFilterEnabled` | boolean | `true` | Set to `false` to disable the filter without removing it. |

```xml
<CDRS moduleName="CDRS">
  <normalStochasticFilter deviation="0.005"/>
  ...
</CDRS>
```

> **Note:** Earlier versions of BioSim used a `setStochasticIntensity` attribute with `HIGH_STOCH`/`MEDIUM_STOCH`/`LOW_STOCH`/`NONE_STOCH` levels. The schema still defines a `stochasticIntensity` attribute on `<Globals>` for backward compatibility, but the current engine applies noise through the per-module `<normalStochasticFilter>` element shown above.

### Reliability and Malfunctions

BioSim can model both *random hardware failures* (reliability) and *scripted malfunctions*.

**Failure deciders** attach a statistical lifetime distribution to a module: at each tick the decider stochastically determines whether the module fails. Add **one** of the following child elements to a module. All of them accept `isFailureEnabled` (boolean, default `true`); the remaining attributes parameterize the distribution:

| Element | Required attributes |
|---------|---------------------|
| `expFailureDecider` | `lambda` |
| `normalFailureDecider` | `logmean`, `logsd` |
| `lognormalFailureDecider` | `logmean`, `logsd` |
| `logisticFailureDecider` | `mu`, `sd` |
| `cauchyFailureDecider` | `mu`, `sd` |
| `uniformFailureDecider` | `alpha`, `beta` |
| `weibull2FailureDecider` | `lambda`, `beta` |
| `weibull3FailureDecider` | `lambda`, `beta`, `hold` |

```xml
<WaterRS moduleName="WaterRS">
  <weibull2FailureDecider lambda="0.000231" beta="5"/>
  ...
</WaterRS>
```

**Scheduled malfunctions** force a specific fault at a known time. Add a `<malfunction>` child element to a module:

| Attribute | Type | Description |
|-----------|------|-------------|
| `intensity` | enum | `SEVERE_MALF`, `MEDIUM_MALF`, or `LOW_MALF`. |
| `length` | enum | `TEMPORARY_MALF` (repairable) or `PERMANENT_MALF`. |
| `occursAtTick` | non-negative integer | Tick at which the malfunction fires. |

```xml
<OGS moduleName="OGS">
  <malfunction intensity="MEDIUM_MALF" length="TEMPORARY_MALF" occursAtTick="100"/>
  ...
</OGS>
```

Malfunctions and repairs can also be injected at runtime via the malfunction REST endpoints documented in the [README](../README.md#malfunction-endpoints). Different modules react differently: a damaged-then-repaired water system returns to normal, but once plants die they cannot recover.

### A Complete Minimal Example

The following self-contained configuration runs a single crew member in one chamber with oxygen generation, CO2 removal, water recovery, food, and nuclear power. It mirrors `configuration/default.biosim`, which is a good starting template.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<biosim xmlns="http://www.traclabs.com/biosim"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.traclabs.com/biosim ../../schema/BiosimInitSchema.xsd">
  <Globals driverStutterLength="500" crewsToWatch="Crew_Quarters_Group"
           runTillCrewDeath="false" tickLength="1" startPaused="false"/>
  <SimBioModules>
    <environment>
      <SimEnvironment moduleName="Crew_Quarters_Environment" initialVolume="2700000"/>
    </environment>
    <air>
      <CO2Store moduleName="CO2_Store" capacity="1000"  level="0"/>
      <H2Store  moduleName="H2_Store"  capacity="10000" level="0"/>
      <O2Store  moduleName="O2_Store"  capacity="10000" level="1000"/>
      <VCCR moduleName="VCCR">
        <powerConsumer inputs="General_Power_Store" desiredFlowRates="1000" maxFlowRates="1000"/>
        <airConsumer   inputs="Crew_Quarters_Environment" desiredFlowRates="1000" maxFlowRates="1000"/>
        <airProducer   outputs="Crew_Quarters_Environment" desiredFlowRates="1000" maxFlowRates="1000"/>
        <CO2Producer   outputs="CO2_Store" desiredFlowRates="1000" maxFlowRates="1000"/>
      </VCCR>
      <OGS moduleName="OGS">
        <powerConsumer        inputs="General_Power_Store" desiredFlowRates="1000" maxFlowRates="1000"/>
        <potableWaterConsumer inputs="Potable_Water_Store" desiredFlowRates="10"   maxFlowRates="10"/>
        <O2Producer outputs="O2_Store" desiredFlowRates="1000" maxFlowRates="1000"/>
        <H2Producer outputs="H2_Store" desiredFlowRates="1000" maxFlowRates="1000"/>
      </OGS>
    </air>
    <water>
      <PotableWaterStore moduleName="Potable_Water_Store" capacity="10000" level="10000"/>
      <GreyWaterStore    moduleName="Grey_Water_Store"    capacity="10000" level="10000"/>
      <DirtyWaterStore   moduleName="Dirty_Water_Store"   capacity="10000" level="0"/>
    </water>
    <power>
      <PowerStore moduleName="General_Power_Store" capacity="100000" level="100000"/>
      <PowerPS moduleName="Nuclear_Source" generationType="NUCLEAR" upperPowerGeneration="3000">
        <powerProducer outputs="General_Power_Store" desiredFlowRates="3000" maxFlowRates="3000"/>
      </PowerPS>
    </power>
    <food>
      <FoodStore moduleName="Food_Store" capacity="10000" level="10000"/>
    </food>
    <waste>
      <DryWasteStore moduleName="Dry_Waste_Store" capacity="1000000" level="0"/>
    </waste>
    <crew>
      <CrewGroup moduleName="Crew_Quarters_Group">
        <potableWaterConsumer inputs="Potable_Water_Store" desiredFlowRates="3" maxFlowRates="3"/>
        <airConsumer          inputs="Crew_Quarters_Environment" desiredFlowRates="0" maxFlowRates="0"/>
        <foodConsumer         inputs="Food_Store" desiredFlowRates="5" maxFlowRates="5"/>
        <dirtyWaterProducer   outputs="Dirty_Water_Store" desiredFlowRates="100" maxFlowRates="100"/>
        <greyWaterProducer    outputs="Grey_Water_Store"  desiredFlowRates="100" maxFlowRates="100"/>
        <airProducer          outputs="Crew_Quarters_Environment" desiredFlowRates="0" maxFlowRates="0"/>
        <dryWasteProducer     outputs="Dry_Waste_Store"   desiredFlowRates="10"  maxFlowRates="10"/>
        <crewPerson name="Buck Rogers" age="35" weight="75" sex="MALE">
          <schedule>
            <activity name="sleep"    length="8"  intensity="0"/>
            <activity name="leisure"  length="12" intensity="2"/>
            <activity name="exercise" length="2"  intensity="5"/>
          </schedule>
        </crewPerson>
      </CrewGroup>
    </crew>
  </SimBioModules>
</biosim>
```

### Further Reading

These public references provide background on the life-support terminology BioSim models and are useful when choosing realistic configuration values:

- TRACLabs, *BioSim* project page -- <https://traclabs.com/projects/biosim/>.
- Kortenkamp, D. and Bell, S., *BioSim: An Integrated Simulation of an Advanced Life Support System for Intelligent Control Research* -- describes the producer/consumer architecture and module models implemented here.
- NASA, *Life Support Baseline Values and Assumptions Document* (BVAD), NASA/TP-2015–218570 -- the source of many baseline consumable rates and the crop growth parameters BioSim uses.

## Controlling the Simulation

The previous chapters showed you how to run BioSim, monitor its internal state, and configure it to your specifications. This chapter will show you how to connect to BioSim and change its underlying operation using the REST API.

### Using the REST API

BioSim exposes a RESTful API that allows you to control the simulation, access sensor data, and inject malfunctions. The API can be accessed from any programming language that supports HTTP requests. To get a full list of the endpoints, see the [README.md](../README.md).

## Conclusions

BioSim provides a flexible platform for researching integrated control approaches for advanced life support systems. By providing a realistic simulation of the complex interactions between life support subsystems, it enables researchers to develop and test control strategies before implementing them in hardware testbeds.

The RESTful API allows integration with any programming language that supports HTTP requests, making it accessible to a wide range of researchers and control approaches. The simulation's ability to model stochastic processes and system malfunctions provides a realistic environment for testing the robustness of control strategies.

We encourage the controls community to use BioSim to explore different control philosophies and approaches, and to help answer the many open research questions in advanced life support control.
