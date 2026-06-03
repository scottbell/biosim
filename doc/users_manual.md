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
   - [Configuration File Structure](#configuration-file-structure)
   - [Units and Conventions](#units-and-conventions)
   - [Global Run-Control Settings](#global-run-control-settings)
   - [Modules, Stores, and Flows](#modules-stores-and-flows)
   - [Environment Configuration](#environment-configuration)
   - [Crew Configuration](#crew-configuration)
   - [Crop and Biomass Configuration](#crop-and-biomass-configuration)
   - [Air Revitalization Equipment](#air-revitalization-equipment)
   - [Water Recovery Equipment](#water-recovery-equipment)
   - [Power Generation and Distribution](#power-generation-and-distribution)
   - [Waste Processing](#waste-processing)
   - [Thermal Control](#thermal-control)
   - [Resource Plumbing (Accumulators, Injectors, Valves)](#resource-plumbing-accumulators-injectors-valves)
   - [Sensors and Actuators](#sensors-and-actuators)
   - [Reliability, Malfunctions, and Stochastic Noise](#reliability-malfunctions-and-stochastic-noise)
   - [Complete Examples](#complete-examples)
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

The biomass component is where crops are grown. It produces both biomass, which can be turned into food, and O2 and consumes water, power (light) and CO2. The system is modeled as shelves that contain plants, lights and water. Shelves are planted and harvested and there is growth cycle for each shelf. Currently, nine crops are modeled (dry bean, lettuce, peanut, rice, soybean, sweet potato, tomato, wheat, and white potato) and can be planted in any ratio.

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

The previous chapter discussed installing and running the default BioSim. This section is
a complete reference for what you can configure in a simulation. The start-up state of
BioSim is controlled by a single XML configuration file that is read during
initialization, so configuring BioSim means editing (or authoring) that file.

The configurable surface includes:

- **Global run-control settings** (when to stop, pacing, tick length).
- **Modules and stores** for every subsystem (air, crew, environment, food/biomass,
  power, water, waste, thermal) plus generic resource "plumbing".
- **Producer/consumer flow definitions** that wire modules to stores and environments.
- **Crew** (number, demographics, daily activity schedules).
- **Crops/biomass** (crop types, growing area, harvest behavior).
- **Environment** (volume, initial gas composition, day/night, leaks).
- **Reliability knobs**: scheduled malfunctions, statistical failure deciders, and
  stochastic (Gaussian) noise.
- **Sensors and Actuators** (instrumentation and alarm bands used by controllers).

All field names, defaults, enumerated values, and units below are taken from the BioSim
XML Schema (`etc/schema/`) and the simulation source; that schema is the source of truth.

> BioSim is a research project from NASA Johnson Space Center, developed with TRACLabs,
> that models an *integrated* Advanced Life Support (ALS) / Environmental Control and Life
> Support System (ECLSS). The subsystem names used below (OGS, VCCR, CRS, WRS, BWP/RO/AES,
> etc.) follow the ALS/ECLSS literature; see the
> [TRACLabs BioSim project page](https://traclabs.com/projects/biosim/) and
> "[BioSim: An Integrated Simulation of an Advanced Life Support System for Intelligent
> Control Research](https://traclabs.com/wp-content/uploads/2024/05/isairas_simulation.pdf)"
> for background. For ARS subsystem terminology (electrolysis-based O2 generation, CO2
> removal/reduction) see NASA's
> [ECLSS overview](https://www.nasa.gov/reference/environmental-control-and-life-support-systems-eclss/).

### Initial Conditions

BioSim is almost infinitely reconfigurable -- from the number of crew members to the size
and number of the different modules -- and can represent scenarios ranging from transit
vehicles to Mars colonies. To change BioSim's configuration you change the XML file.

The simplest workflow is to start from an existing file in the `configuration/` directory
(for example `configuration/default.biosim`), copy it, edit it, and then pass it to the
launcher with the `--config` option:

```bash
bin/run-simulation --config /path/to/my_scenario.biosim
```

Configuration files conventionally use the `.biosim` extension; older example files use
`.xml`. Both are plain XML and are read identically.

### Configuration File Structure

A configuration is a single XML document whose root element is `<biosim>`, declared in the
`http://www.traclabs.com/biosim` namespace and (optionally) pointing at the schema for
editor validation:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<biosim xmlns="http://www.traclabs.com/biosim"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.traclabs.com/biosim ../../schema/BiosimInitSchema.xsd">
  <Globals .../>            <!-- run-control settings (required)        -->
  <SimBioModules> ... </SimBioModules>  <!-- the modules and stores     -->
  <Sensors> ... </Sensors>              <!-- optional instrumentation    -->
  <Actuators> ... </Actuators>          <!-- optional actuators          -->
</biosim>
```

The four children appear in this order. Only `<Globals>` is required; `<SimBioModules>`,
`<Sensors>`, and `<Actuators>` are each optional (though a useful simulation needs
modules). Every module and store has a `moduleName` that must be **unique across the whole
file** -- names are the keys used to wire modules together and to address them over the
REST API.

### Units and Conventions

| Quantity | Unit | Notes |
|----------|------|-------|
| Power | watts (W) | `desiredFlowRates`/`level`/`capacity` on power elements |
| Liquid water (potable, grey, dirty) | liters (L) | per-tick flow rates are liters/tick |
| Gases (O2, CO2, H2, N2, water vapor, methane) | moles (mol) | environment composition is in moles or partial-pressure % |
| Biomass, food, dry waste | kilograms (kg) | |
| Environment volume | liters (L) | `initialVolume`, `airlockVolume` |
| Time | ticks | each tick represents `tickLength` hours of model time (default `1`) |

Flow rates are expressed **per tick**. Because each tick equals `tickLength` model hours,
the amount actually moved per tick is scaled by `tickLength` (e.g. `tickLength="0.1"`
makes each tick a 6-minute step). `driverStutterLength` is unrelated to model time -- it is
the wall-clock delay (in milliseconds) the driver waits between automatic ticks.

### Global Run-Control Settings

`<Globals>` controls when and how the run executes. All attributes are optional and fall
back to the defaults shown.

| Attribute | Type | Default | Meaning |
|-----------|------|---------|---------|
| `startPaused` | boolean | `true` | If true, the simulation loads paused and advances only when you call the tick endpoint. |
| `runTillN` | integer | `-1` | Stop after N ticks. `-1` means no tick limit. |
| `runTillCrewDeath` | boolean | `true` | Stop the run if any watched crew member dies. |
| `runTillPlantDeath` | boolean | `false` | Stop the run if any watched crop dies. |
| `crewsToWatch` | string list | (none) | Space-separated `CrewGroup` module names monitored for crew death. |
| `plantsToWatch` | string list | (none) | Space-separated `BiomassPS` module names monitored for plant death. |
| `exitWhenFinished` | boolean | `false` | Exit the server process when the run ends. |
| `driverStutterLength` | integer (ms) | `-1` | Wall-clock pause between automatic ticks. Values `>= 0` are applied. |
| `isLooping` | boolean | `false` | Restart the run from the beginning when it ends. |
| `stochasticIntensity` | enum | `NONE_STOCH` | Schema-level stochastic intensity (`HIGH_STOCH`, `MEDIUM_STOCH`, `LOW_STOCH`, `NONE_STOCH`). See [Reliability, Malfunctions, and Stochastic Noise](#reliability-malfunctions-and-stochastic-noise) for the mechanism actually applied. |
| `tickLength` | float `>= 0` | `1` | Model hours represented by one tick. Smaller values give finer time resolution. |

`<Globals>` may also contain optional `<comment>` and `<author>` text elements for
documenting the scenario.

```xml
<Globals crewsToWatch="Crew_Quarters_Group"
         driverStutterLength="500"
         runTillCrewDeath="false"
         tickLength="1"
         startPaused="false"/>
```

### Modules, Stores, and Flows

`<SimBioModules>` groups the configurable modules by subsystem. Each group is optional and
holds a specific set of element types:

| Group | Module / store element types |
|-------|------------------------------|
| `<air>` | `OGS`, `VCCR`, `CDRS`, `CRS`, `Pyrolizer`, `O2Store`, `CO2Store`, `H2Store`, `NitrogenStore`, `MethaneStore` |
| `<water>` | `WaterRS`, `PotableWaterStore`, `GreyWaterStore`, `DirtyWaterStore` |
| `<power>` | `PowerPS`, `PowerStore`, `GenericPowerConsumer`, `RPCM` |
| `<food>` | `BiomassPS`, `FoodProcessor`, `BiomassStore`, `FoodStore` |
| `<crew>` | `CrewGroup` |
| `<environment>` | `SimEnvironment`, `Dehumidifier`, `Fan` |
| `<waste>` | `Incinerator`, `DryWasteStore` |
| `<thermal>` | `IATCS` |
| `<framework>` | `Accumulator`, `Injector`, `InfluentValve`, `EffluentValve` |

You may include any number of each element type (including several environments, crew
groups, or stores).

#### Common Module Attributes

Every module and store (anything based on the schema's `BioModuleType`) accepts:

| Attribute | Type | Default | Meaning |
|-----------|------|---------|---------|
| `moduleName` | string | (required) | Unique name used for wiring and REST access. |
| `createLocally` | boolean | `true` | Reserved distribution flag; leave default for normal use. |
| `logLevel` | enum | `INFO` | `OFF`, `INFO`, `DEBUG`, `ERROR`, `WARN`, `FATAL`, or `ALL`. |
| `isBionetEnabled` | boolean | `false` | Reserved integration flag. |

Each module may also carry an optional `<malfunction>`, one optional failure decider, and
one optional `<normalStochasticFilter>` (see
[Reliability, Malfunctions, and Stochastic Noise](#reliability-malfunctions-and-stochastic-noise)).
When present, these fault elements must appear **first**, in the order malfunction ->
failure decider -> stochastic filter, before the module's producer/consumer children
(and, for sensors, before `<alarms>`).

#### Producer/Consumer Flow Definitions

BioSim modules never talk to each other directly. Instead each module declares the
resources it **consumes** (pulls from a store/environment) and **produces** (pushes into a
store/environment). Each consumer/producer is a child element named for its resource, e.g.
`powerConsumer`, `potableWaterConsumer`, `airConsumer`, `O2Producer`, `CO2Producer`,
`biomassProducer`, `dryWasteProducer`, and so on.

Every consumer/producer takes the same attributes:

| Attribute | Type | Required | Meaning |
|-----------|------|----------|---------|
| `desiredFlowRates` | float list | yes | Amount the module *tries* to move per tick, per connection. |
| `maxFlowRates` | float list | yes | Hard upper bound per tick, per connection. |
| `inputs` | string list | consumers | Source store/environment `moduleName`(s) to draw from. |
| `outputs` | string list | producers | Destination store/environment `moduleName`(s) to push to. |

The lists are **space-separated** and positionally aligned: the *i*-th flow rate applies to
the *i*-th connection. With multiple connections the module draws (or pushes) as much as it
can from the first before moving to the next. A flow rate of `0` means the module's value
is computed internally and cannot be directly commanded (for example, crew air
consumption is driven by physiology, so its `airConsumer` rate is `0`).

```xml
<powerConsumer inputs="General_Power_Store" desiredFlowRates="1000" maxFlowRates="1000"/>
<O2Producer    outputs="O2_Store"           desiredFlowRates="1000" maxFlowRates="1000"/>
```

When two modules compete for a scarce resource in the same tick, the conflict is currently
resolved on an effectively random, winner-take-all basis.

#### Stores

Stores hold a single resource. All store types share the `Store` attributes:

| Attribute | Type | Default | Meaning |
|-----------|------|---------|---------|
| `capacity` | float `>= 0` | (required) | Maximum amount the store can hold. |
| `level` | float `>= 0` | (required) | Initial amount in the store. |
| `resupplyFrequency` | integer `>= 0` | `0` | Resupply interval in ticks (`0` disables resupply). |
| `resupplyAmount` | float `>= 0` | `0` | Amount added to the store each resupply interval. |

Store types by group: air -- `O2Store`, `CO2Store`, `H2Store`, `NitrogenStore`,
`MethaneStore`; water -- `PotableWaterStore`, `GreyWaterStore`, `DirtyWaterStore`; power --
`PowerStore`; food -- `BiomassStore`, `FoodStore` (extra attributes below); waste --
`DryWasteStore`.

```xml
<O2Store moduleName="O2_Store" capacity="10000" level="1000"/>
<PotableWaterStore moduleName="Potable_Water_Store" capacity="10000" level="10000"
                   resupplyFrequency="24" resupplyAmount="50"/>
```

### Environment Configuration

A `<SimEnvironment>` is a sealed atmosphere that crew and crops breathe. Multiple
environments are common (e.g. a crew cabin and a separate plant chamber).

| Attribute | Type | Default | Meaning |
|-----------|------|---------|---------|
| `initialVolume` | float | (required) | Atmosphere volume in liters. |
| `leakRate` | float | `0` | Baseline atmospheric leak rate. |
| `dayLength` | float | `24` | Length of a day (hours) for the light cycle. |
| `hourOfDayStart` | float | `0` | Hour of day the simulation starts at. |
| `maxLumens` | float | `50000` | Peak illumination at solar noon. |
| `airlockVolume` | float | `3.7` | Volume lost per airlock cycle. |
| `dangerousOxygenThreshold` | float 0--1 | `1.0` | O2 fraction considered a fire/biological hazard. |

Initial gas composition is set with **one** of two optional child elements (omit both for
a sea-level-equivalent default):

- `<moleInitialization>` -- absolute amounts, all required:
  `initialCO2Moles`, `initialO2Moles`, `initialOtherMoles`, `initialWaterMoles`,
  `initialNitrogenMoles`.
- `<percentageInitialization>` -- a total pressure plus fractions, all required:
  `totalPressure`, `co2Percentage`, `o2Percentage`, `otherPercentage`,
  `waterPercentage`, `nitrogenPercentage`.

```xml
<SimEnvironment moduleName="Crew_Cabin" initialVolume="2700000" dayLength="24">
  <percentageInitialization totalPressure="101"
      o2Percentage="0.21" co2Percentage="0.0004" nitrogenPercentage="0.78"
      waterPercentage="0.0096" otherPercentage="0.0"/>
</SimEnvironment>
```

Two helper modules also live in `<environment>`: `Dehumidifier` (consumes air, produces
dirty water) and `Fan` (consumes air + power, produces air), each wired with the usual
producer/consumer children.

### Crew Configuration

A `<CrewGroup>` models a set of astronauts sharing one environment. Beyond the common
module attributes it adds `isDeathEnabled` (boolean, default `true`). A crew group declares
the standard crew flows in order -- `potableWaterConsumer`, `airConsumer`, `foodConsumer`,
`dirtyWaterProducer`, `greyWaterProducer`, `airProducer`, `dryWasteProducer` -- followed by
any number of `<crewPerson>` elements.

`<crewPerson>` attributes:

| Attribute | Type | Default | Meaning |
|-----------|------|---------|---------|
| `name` | string | (required) | Crew member name. |
| `age` | float `>= 0` | (required) | Age in years. |
| `weight` | float `>= 0` | (required) | Mass in kilograms. |
| `sex` | enum | (required) | `MALE` or `FEMALE`. |
| `arrivalDate` | integer `>= 0` | `0` | Tick the crew member arrives. |
| `departureDate` | integer | `-1` | Tick the crew member departs (`-1` = never). |
| `implementation` | enum | `NORMAL` | `NORMAL` or `MATLAB`. |

Each crew member contains a `<schedule>` of one or more `<activity>` elements. Each
activity has `name` (string), `length` (whole hours), and `intensity` (non-negative
integer; higher intensity consumes more O2/water/food). A nominal day might be sleep,
hygiene, exercise, eating, mission, health, maintenance, and leisure; an `EVA` activity may
additionally name an `evaCrewGroup`. Schedules can also be changed at run time over the
REST API.

```xml
<crewPerson name="Buck Rogers" age="35" weight="75" sex="MALE">
  <schedule>
    <activity name="sleep"    length="8" intensity="0"/>
    <activity name="mission"  length="12" intensity="3"/>
    <activity name="exercise" length="2" intensity="5"/>
  </schedule>
</crewPerson>
```

### Crop and Biomass Configuration

Crops are grown in a `<BiomassPS>` (Biomass Production System). It adds two attributes:
`autoHarvestAndReplant` (boolean, default `true`) and `isDeathEnabled` (boolean, default
`true`), and contains any number of `<shelf>` elements followed by its standard flow
children (`powerConsumer`, `potableWaterConsumer`, `greyWaterConsumer`, `airConsumer`,
`dirtyWaterProducer`, `biomassProducer`, `airProducer`).

A `<shelf>` is one planted area:

| Attribute | Type | Default | Meaning |
|-----------|------|---------|---------|
| `cropType` | enum | (required) | One of the crop types below. |
| `cropArea` | float `>= 0` | (required) | Growing area (m^2). |
| `startTick` | integer `>= 0` | `0` | Tick at which this shelf is planted. |

Valid `cropType` values (9): `DRY_BEAN`, `LETTUCE`, `PEANUT`, `RICE`, `SOYBEAN`,
`SWEET_POTATO`, `TOMATO`, `WHEAT`, `WHITE_POTATO`. Crops can be mixed in any ratio by
adding multiple shelves.

Biomass becomes food through the optional `<FoodProcessor>` module (consumes biomass +
power, produces food, dry waste, and water). The related stores carry extra attributes:

- `<BiomassStore>`: `inedibleFraction` (0--1, default `0.25`), `edibleWaterContent`
  (default `5`), `inedibleWaterContent` (default `5`), `cropType` (default `WHEAT`).
- `<FoodStore>`: `waterContent` (default `5`), `cropType` (default `WHEAT`).

```xml
<BiomassPS moduleName="BiomassPS" autoHarvestAndReplant="true">
  <shelf cropType="SOYBEAN" cropArea="20"/>
  <shelf cropType="WHEAT"   cropArea="10" startTick="48"/>
  <powerConsumer        inputs="General_Power_Store" desiredFlowRates="400" maxFlowRates="400"/>
  <potableWaterConsumer inputs="Potable_Water_Store" desiredFlowRates="100" maxFlowRates="100"/>
  <greyWaterConsumer    inputs="Grey_Water_Store"    desiredFlowRates="100" maxFlowRates="100"/>
  <airConsumer          inputs="Crew_Cabin"          desiredFlowRates="0"   maxFlowRates="0"/>
  <dirtyWaterProducer   outputs="Dirty_Water_Store"  desiredFlowRates="100" maxFlowRates="100"/>
  <biomassProducer      outputs="Biomass_Store"      desiredFlowRates="100" maxFlowRates="100"/>
  <airProducer          outputs="Crew_Cabin"         desiredFlowRates="0"   maxFlowRates="0"/>
</BiomassPS>
```

### Air Revitalization Equipment

| Module | Role | Required flow children | Special attributes |
|--------|------|------------------------|--------------------|
| `OGS` | Oxygen Generation System: electrolyzes water into O2 + H2 | `powerConsumer`, `potableWaterConsumer`, `O2Producer`, `H2Producer` | -- |
| `VCCR` | Variable Configuration CO2 Removal | `powerConsumer`, `airConsumer`, `airProducer`, `CO2Producer` | `implementation` = `LINEAR` (default) or `DETAILED` |
| `CDRS` | CO2 Removal System (grey-water variant) | `powerConsumer`, `greyWaterConsumer`, `greyWaterProducer`, `airConsumer`, `airProducer`, `CO2Producer` | -- |
| `CRS` | CO2 Reduction (Sabatier): CO2 + H2 -> water + methane | `powerConsumer`, `CO2Consumer`, `H2Consumer`, `potableWaterProducer`, `methaneProducer` | -- |
| `Pyrolizer` | Cracks methane into H2 + dry waste | `powerConsumer`, `methaneConsumer`, `H2Producer`, `dryWasteProducer` | -- |

### Water Recovery Equipment

`<WaterRS>` is the Water Recovery System (its internal subsystems are the Biological Water
Processor, Reverse Osmosis, Air Evaporation System, and Post-Processing System). Required
flow children: `powerConsumer`, `dirtyWaterConsumer`, `greyWaterConsumer`,
`potableWaterProducer`. Special attribute `implementation` = `LINEAR` (default),
`NORMAL`, or `VCD`.

#### `VCD` water recovery (Vapor Compression Distillation)

The `VCD` implementation models a distillation-based water recovery system after the
International Space Station Urine Processor Assembly (UPA). Unlike `LINEAR`, which recovers
potable water in direct proportion to power with no losses, the `VCD` system processes an
*energy-limited* amount of waste water each tick (dirty/urine water first, then grey water)
and recovers only a configurable fraction of it as potable water; the remainder is rejected
as brine that leaves the recovery loop.

Per tick: `processed = (powerConsumed / processingEnergyPerLiter) * tickLength`, drawn from
the dirty-water store first and then the grey-water store (bounded by the consumer flow
rates and store availability); `potable = waterRecoveryRatio * processed` is pushed to the
potable-water store, and `(1 - waterRecoveryRatio) * processed` is accounted as brine. With
no power or no waste water, the system produces nothing and never drives a store negative.
Malfunctions reduce throughput (LOW → 75%, MEDIUM → 50%, SEVERE → 0%) and a cleared
temporary malfunction restores nominal throughput.

| Attribute | Type | Default | Meaning |
|-----------|------|---------|---------|
| `waterRecoveryRatio` | float `(0, 1]` | `0.85` | Fraction of processed waste water recovered as potable water (ISS UPA single-pass ≈ 85%). Values `> 1` are clamped to `1`; non-positive values are ignored. |
| `processingEnergyPerLiter` | float `> 0` | `361.5` | Power (watts) required to process one liter of waste water per tick. The default matches BioSim's existing linear water relationship (1540 W → 4.26 L/tick). |

Both attributes are optional; omit them to use the defaults. Existing `LINEAR`/`NORMAL`
configurations are unaffected.

Example:

```xml
<water>
    <DirtyWaterStore moduleName="DirtyWaterStore" capacity="1000000.0" level="100000.0"/>
    <GreyWaterStore moduleName="GreyWaterStore" capacity="1000000.0" level="100000.0"/>
    <PotableWaterStore moduleName="PotableWaterStore" capacity="1000000.0" level="0.0"/>
    <WaterRS moduleName="VCDWaterRS" implementation="VCD"
             waterRecoveryRatio="0.85" processingEnergyPerLiter="361.5">
        <powerConsumer desiredFlowRates="1540.0" maxFlowRates="1540.0" inputs="PowerStore"/>
        <dirtyWaterConsumer desiredFlowRates="1000.0" maxFlowRates="1000.0" inputs="DirtyWaterStore"/>
        <greyWaterConsumer desiredFlowRates="1000.0" maxFlowRates="1000.0" inputs="GreyWaterStore"/>
        <potableWaterProducer desiredFlowRates="1000.0" maxFlowRates="1000.0" outputs="PotableWaterStore"/>
    </WaterRS>
</water>
```

A ready-to-run sample is provided at `configuration/test/VCDWaterInit.xml`.

### Power Generation and Distribution

| Module | Role | Required flow children | Special attributes |
|--------|------|------------------------|--------------------|
| `PowerPS` | Power Production System | `powerProducer` (and optional `lightConsumer`) | `generationType` = `NUCLEAR` (default), `SOLAR`, or `STATE_MACHINE`; `upperPowerGeneration` (float, default `500`) |
| `RPCM` | Remote Power Controller Module (switchable distribution) | `powerConsumer`, `powerProducer` | `switchValues` = list of `0`/`1` |
| `GenericPowerConsumer` | A generic load | `powerConsumer` | `powerRequired` (float, required) |

`NUCLEAR` supplies a constant amount; `SOLAR` varies with the environment's day/night
cycle (and may draw from a `lightConsumer`).

### Waste Processing

`<Incinerator>` consumes power, O2, and dry waste and produces CO2. Required flow children:
`powerConsumer`, `O2Consumer`, `dryWasteConsumer`, `CO2Producer`.

### Thermal Control

`<IATCS>` (Internal Active Thermal Control System) consumes power and grey water and
returns grey water. Required flow children: `powerConsumer`, `greyWaterConsumer`,
`greyWaterProducer`.

### Resource Plumbing (Accumulators, Injectors, Valves)

The `<framework>` group provides generic transfer modules that can move *any* resource
between stores/environments. `Accumulator`, `Injector`, `InfluentValve`, and
`EffluentValve` each accept any combination of the consumer/producer children for any
resource (power, the three waters, air, H2, N2, O2, CO2, light, biomass, food, dry waste).
Accumulators and injectors are functionally equivalent and are typically used to dose a
resource into an environment.

```xml
<Injector moduleName="Oxygen_Injector">
  <O2Consumer inputs="O2_Store"  desiredFlowRates="0.195" maxFlowRates="0.195"/>
  <O2Producer outputs="Crew_Cabin" desiredFlowRates="0.195" maxFlowRates="0.195"/>
</Injector>
```

### Sensors and Actuators

`<Sensors>` and `<Actuators>` describe the instrumentation a controller reads from and
writes to. Sensors are grouped by subsystem (e.g. `environment`) and include types such as
`GasConcentrationSensor`, `GasPressureSensor`, and `TotalPressureSensor`; each names the
module it observes via `input` (and, for gas sensors, a `gasType` such as `CO2`, `O2`, or
`VAPOR`). A sensor may attach a `<normalStochasticFilter>` to add reading noise and may
declare `<alarms>` with banded ranges (`watch_low`/`watch_high`,
`warning_low`/`warning_high`, `distress_low`/`distress_high`,
`critical_low`/`critical_high`, `severe_low`/`severe_high`), each with `min`/`max` bounds.
As with any module, the stochastic filter comes before `<alarms>`.

```xml
<Sensors>
  <environment>
    <GasConcentrationSensor input="Crew_Cabin" moduleName="CO2_Sensor" gasType="CO2">
      <normalStochasticFilter deviation="0.005"/>
      <alarms>
        <distress_high min="0.002" max="0.003"/>
        <critical_high min="0.003" max="0.004"/>
        <severe_high   min="0.004" max="1"/>
      </alarms>
    </GasConcentrationSensor>
  </environment>
</Sensors>
```

### Reliability, Malfunctions, and Stochastic Noise

BioSim models failures three ways. Each is an optional child of a module (or, for stores,
the same module attributes), and they appear in the order **malfunction -> failure decider
-> stochastic filter**.

**1. Scheduled malfunctions.** A `<malfunction>` element forces a fault at a known tick:

| Attribute | Values | Meaning |
|-----------|--------|---------|
| `intensity` | `SEVERE_MALF`, `MEDIUM_MALF`, `LOW_MALF` | How badly the module is degraded. |
| `length` | `TEMPORARY_MALF`, `PERMANENT_MALF` | Whether it self-clears or persists. |
| `occursAtTick` | non-negative integer | Tick at which the malfunction begins. |

```xml
<VCCR moduleName="Main_VCCR">
  <malfunction intensity="MEDIUM_MALF" length="PERMANENT_MALF" occursAtTick="200"/>
  <powerConsumer inputs="General_Power_Store" desiredFlowRates="340" maxFlowRates="1000"/>
  <airConsumer   inputs="Crew_Cabin" desiredFlowRates="10000" maxFlowRates="10000"/>
  <airProducer   outputs="Crew_Cabin" desiredFlowRates="10000" maxFlowRates="10000"/>
  <CO2Producer   outputs="CO2_Store"  desiredFlowRates="10000" maxFlowRates="10000"/>
</VCCR>
```

Malfunctions can also be triggered, scheduled, and cleared at run time through the
[malfunction REST endpoints](../README.md#malfunction-endpoints).

**2. Statistical failure deciders.** Instead of (or in addition to) a fixed tick, a module
can fail according to a probability distribution. Choose at most one decider; all accept
`isFailureEnabled` (boolean, default `true`) plus the distribution parameters:

| Element | Required parameters |
|---------|---------------------|
| `cauchyFailureDecider` | `mu`, `sd` |
| `expFailureDecider` | `lambda` |
| `logisticFailureDecider` | `mu`, `sd` |
| `lognormalFailureDecider` | `logmean`, `logsd` |
| `normalFailureDecider` | `logmean`, `logsd` |
| `uniformFailureDecider` | `alpha`, `beta` |
| `weibull2FailureDecider` | `lambda`, `beta` |
| `weibull3FailureDecider` | `lambda`, `beta`, `hold` |

```xml
<weibull2FailureDecider lambda="0.001" beta="1.5"/>
```

**3. Stochastic (Gaussian) noise.** Add a `<normalStochasticFilter>` to a module to pass
its outputs through a Gaussian filter, modeling sensor/process noise:

| Attribute | Type | Default | Meaning |
|-----------|------|---------|---------|
| `deviation` | double | (required) | Standard deviation of the Gaussian noise. |
| `isFilterEnabled` | boolean | `true` | Toggle the filter without removing it. |

```xml
<normalStochasticFilter deviation="0.05"/>
```

The schema also defines a `stochasticIntensity` attribute on `<Globals>`
(`HIGH_STOCH` / `MEDIUM_STOCH` / `LOW_STOCH` / `NONE_STOCH`, default `NONE_STOCH`) intended
as a simulation-wide setting. In the current build, however, noise is applied per module
through `<normalStochasticFilter>`; the global application path is not active, so prefer
the per-module filter when you want noise to take effect. A purely deterministic run uses
no filters (the default).

### Complete Examples

#### Minimal Configuration

A small but complete, schema-valid closed-loop scenario: one cabin, air revitalization,
water recovery, nuclear power, a crop shelf, and a single crew member.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<biosim xmlns="http://www.traclabs.com/biosim"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.traclabs.com/biosim ../../schema/BiosimInitSchema.xsd">
  <Globals crewsToWatch="Crew_Quarters_Group" runTillCrewDeath="false"
           tickLength="1" startPaused="false" driverStutterLength="500"/>
  <SimBioModules>
    <environment>
      <SimEnvironment moduleName="Crew_Quarters_Environment" initialVolume="2700000"/>
    </environment>
    <air>
      <CO2Store moduleName="CO2_Store" capacity="1000" level="0"/>
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
        <potableWaterConsumer inputs="Potable_Water_Store" desiredFlowRates="10" maxFlowRates="10"/>
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
      <PowerPS moduleName="Nuclear_Source" generationType="NUCLEAR">
        <powerProducer outputs="General_Power_Store" desiredFlowRates="3000" maxFlowRates="3000"/>
      </PowerPS>
    </power>
    <food>
      <FoodStore    moduleName="Food_Store"    capacity="10000" level="10000"/>
      <BiomassStore moduleName="Biomass_Store" capacity="10000" level="10000"/>
      <BiomassPS moduleName="BiomassPS" autoHarvestAndReplant="true">
        <shelf cropType="SOYBEAN" cropArea="1"/>
        <powerConsumer        inputs="General_Power_Store" desiredFlowRates="400" maxFlowRates="400"/>
        <potableWaterConsumer inputs="Potable_Water_Store" desiredFlowRates="100" maxFlowRates="100"/>
        <greyWaterConsumer    inputs="Grey_Water_Store"    desiredFlowRates="100" maxFlowRates="100"/>
        <airConsumer          inputs="Crew_Quarters_Environment" desiredFlowRates="0" maxFlowRates="0"/>
        <dirtyWaterProducer   outputs="Dirty_Water_Store"  desiredFlowRates="100" maxFlowRates="100"/>
        <biomassProducer      outputs="Biomass_Store"      desiredFlowRates="100" maxFlowRates="100"/>
        <airProducer          outputs="Crew_Quarters_Environment" desiredFlowRates="0" maxFlowRates="0"/>
      </BiomassPS>
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
        <dryWasteProducer     outputs="Dry_Waste_Store" desiredFlowRates="10" maxFlowRates="10"/>
        <crewPerson name="Buck Rogers" age="35" weight="75" sex="MALE">
          <schedule>
            <activity name="leisure"  length="12" intensity="2"/>
            <activity name="sleep"    length="8"  intensity="0"/>
            <activity name="exercise" length="2"  intensity="5"/>
          </schedule>
        </crewPerson>
      </CrewGroup>
    </crew>
  </SimBioModules>
</biosim>
```

#### Scheduling a Malfunction

The same scenario with reliability knobs added: the VCCR suffers a permanent medium
malfunction at tick 200, the OGS carries a Weibull failure decider and Gaussian output
noise, and a CO2 sensor with alarm bands monitors the cabin.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<biosim xmlns="http://www.traclabs.com/biosim"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xsi:schemaLocation="http://www.traclabs.com/biosim ../../schema/BiosimInitSchema.xsd">
  <Globals crewsToWatch="Crew_Quarters_Group" runTillCrewDeath="true"
           tickLength="1" startPaused="false" driverStutterLength="200"/>
  <SimBioModules>
    <environment>
      <SimEnvironment moduleName="Crew_Quarters_Environment" initialVolume="2700000"/>
    </environment>
    <air>
      <CO2Store moduleName="CO2_Store" capacity="1000" level="0"/>
      <H2Store  moduleName="H2_Store"  capacity="10000" level="0"/>
      <O2Store  moduleName="O2_Store"  capacity="10000" level="1000"/>
      <VCCR moduleName="Main_VCCR">
        <malfunction intensity="MEDIUM_MALF" length="PERMANENT_MALF" occursAtTick="200"/>
        <powerConsumer inputs="General_Power_Store" desiredFlowRates="1000" maxFlowRates="1000"/>
        <airConsumer   inputs="Crew_Quarters_Environment" desiredFlowRates="1000" maxFlowRates="1000"/>
        <airProducer   outputs="Crew_Quarters_Environment" desiredFlowRates="1000" maxFlowRates="1000"/>
        <CO2Producer   outputs="CO2_Store" desiredFlowRates="1000" maxFlowRates="1000"/>
      </VCCR>
      <OGS moduleName="OGS">
        <weibull2FailureDecider lambda="0.001" beta="1.5"/>
        <normalStochasticFilter deviation="0.05"/>
        <powerConsumer        inputs="General_Power_Store" desiredFlowRates="1000" maxFlowRates="1000"/>
        <potableWaterConsumer inputs="Potable_Water_Store" desiredFlowRates="10" maxFlowRates="10"/>
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
      <PowerPS moduleName="Nuclear_Source" generationType="NUCLEAR">
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
        <dryWasteProducer     outputs="Dry_Waste_Store" desiredFlowRates="10" maxFlowRates="10"/>
        <crewPerson name="Wilma Deering" age="35" weight="55" sex="FEMALE">
          <schedule>
            <activity name="mission" length="12" intensity="3"/>
            <activity name="sleep"   length="8"  intensity="0"/>
            <activity name="leisure" length="4"  intensity="2"/>
          </schedule>
        </crewPerson>
      </CrewGroup>
    </crew>
  </SimBioModules>
  <Sensors>
    <environment>
      <GasConcentrationSensor input="Crew_Quarters_Environment" moduleName="CO2_Sensor" gasType="CO2">
        <normalStochasticFilter deviation="0.005"/>
        <alarms>
          <distress_high min="0.002" max="0.003"/>
          <critical_high min="0.003" max="0.004"/>
          <severe_high   min="0.004" max="1"/>
        </alarms>
      </GasConcentrationSensor>
    </environment>
  </Sensors>
</biosim>
```

## Controlling the Simulation

The previous chapters showed you how to run BioSim, monitor its internal state, and configure it to your specifications. This chapter will show you how to connect to BioSim and change its underlying operation using the REST API.

### Using the REST API

BioSim exposes a RESTful API that allows you to control the simulation, access sensor data, and inject malfunctions. The API can be accessed from any programming language that supports HTTP requests. To get a full list of the endpoints, see the [README.md](../README.md).

## Conclusions

BioSim provides a flexible platform for researching integrated control approaches for advanced life support systems. By providing a realistic simulation of the complex interactions between life support subsystems, it enables researchers to develop and test control strategies before implementing them in hardware testbeds.

The RESTful API allows integration with any programming language that supports HTTP requests, making it accessible to a wide range of researchers and control approaches. The simulation's ability to model stochastic processes and system malfunctions provides a realistic environment for testing the robustness of control strategies.

We encourage the controls community to use BioSim to explore different control philosophies and approaches, and to help answer the many open research questions in advanced life support control.
