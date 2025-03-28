package com.traclabs.biosim.server.framework;

import com.traclabs.biosim.server.actuator.framework.GenericActuator;
import com.traclabs.biosim.server.sensor.framework.GenericSensor;
import com.traclabs.biosim.server.simulation.crew.Activity;
import com.traclabs.biosim.server.simulation.crew.CrewGroup;
import com.traclabs.biosim.server.simulation.crew.CrewPerson;
import com.traclabs.biosim.server.simulation.crew.Schedule;
import com.traclabs.biosim.server.simulation.environment.SimEnvironment;
import com.traclabs.biosim.server.simulation.framework.SingleFlowRateControllable;
import com.traclabs.biosim.server.simulation.framework.Store;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Controller class to handle simulation REST endpoints.
 */
public class SimulationController {
    private static final Logger logger = LoggerFactory.getLogger(SimulationController.class);
    private final AtomicInteger simInitializerCounter = new AtomicInteger();

    // Map to store simulation IDs and their corresponding BioDriver instances
    private final Map<Integer, BioDriver> simulations = new ConcurrentHashMap<>();

    /**
     * Registers the simulation endpoints with the Javalin app.
     *
     * @param app The Javalin application instance
     */
    public void registerEndpoints(Javalin app) {
        app.get("/api/simulation", this::listSimulations);
        app.post("/api/simulation/start", this::startSimulation);
        app.post("/api/simulation/{simID}/tick", this::tickSimulation);
        app.get("/api/simulation/{simID}/modules/{moduleName}", this::getModuleInfo);
        app.get("/api/simulation/{simID}", this::getSimulationDetails);
    }

    /**
     * Gets the list of all simulation IDs.
     *
     * @param ctx The Javalin context
     */
    private void listSimulations(Context ctx) {
        // Return the keys of the simulations map as a JSON response.
        ctx.json(Map.of("simulations", simulations.keySet()));
    }

    /**
     * Starts a new simulation with the given XML configuration.
     *
     * @param ctx The Javalin context
     */
    private void startSimulation(Context ctx) {
        String xmlConfig = ctx.body();
        if (xmlConfig == null || xmlConfig.isEmpty()) {
            ctx.status(400).json(Map.of("error", "XML configuration is required in the request body."));
            return;
        }

        try {
            // Generate a unique simulation ID
            int simID = simInitializerCounter.incrementAndGet();
            // Initialize the simulation using BiosimInitializer
            BiosimInitializer initializer = BiosimInitializer.getInstance(simID);
            initializer.parseXmlConfiguration(xmlConfig);

            // Create a new BioDriver instance for the simulation
            BioDriver bioDriver = initializer.getBioDriver();
            simulations.put(simID, bioDriver);

            // Start the simulation (without ticking)
            bioDriver.startSimulation();

            ctx.json(Map.of("simId", simID));
            logger.info("🏃‍♂️ Started simulation with ID {}", simID);
        } catch (Exception e) {
            logger.error("Failed to start simulation: {}", e.getMessage());
            e.printStackTrace();
            ctx.status(500).json(Map.of("error", "Failed to start simulation: " + e.getMessage()));
        }
    }

    /**
     * Advances the simulation by one tick.
     *
     * @param ctx The Javalin context
     */
    private void tickSimulation(Context ctx) {
        int simID = Integer.parseInt(ctx.pathParam("simID"));
        BioDriver bioDriver = simulations.get(simID);

        if (bioDriver == null) {
            ctx.status(404).json(Map.of("error", "Simulation ID not found."));
            return;
        }

        bioDriver.advanceOneTick();
        ctx.json(Map.of("ticks", bioDriver.getTicks()));
        logger.info("Simulation {} advanced to tick {}", simID, bioDriver.getTicks());
    }


    /**
     * Returns detailed information for a module of a simulation.
     * The output JSON will contain consumer/producer definitions (if any)
     * along with flow rate arrays and connection info (from the underlying
     * definition objects) or store properties if it is a store.
     *
     * @param ctx the Javalin context from the request.
     */
    private void getModuleInfo(Context ctx) {
        int simID = Integer.parseInt(ctx.pathParam("simID"));
        String moduleName = ctx.pathParam("moduleName");
        BioDriver bioDriver = simulations.get(simID);

        if (bioDriver == null) {
            ctx.status(404).json(Map.of("error", "Simulation ID not found."));
            return;
        }

        IBioModule[] modules = bioDriver.getModules();
        IBioModule targetModule = null;
        for (IBioModule module : modules) {
            if (module.getModuleName().equalsIgnoreCase(moduleName)) {
                targetModule = module;
                break;
            }
        }
        if (targetModule == null) {
            ctx.status(404).json(Map.of("error", "Module not found."));
            return;
        }
        Map<String, Object> moduleInfo = buildModuleInfo(targetModule);
        ctx.json(moduleInfo);
    }

    /**
     * Returns detailed information for a simulation including global simulation properties
     * and detailed module information for each module declared in BioDriver.
     *
     * The JSON structure looks like:
     *
     * {
     *   "globals": { ... },
     *   "modules": {
     *       "FoodProcessor": { ... },
     *       "O2Store": { ... },
     *       ...
     *   }
     * }
     *
     * @param ctx the Javalin context from the request.
     */
    private void getSimulationDetails(Context ctx) {
        int simID = Integer.parseInt(ctx.pathParam("simID"));
        BioDriver bioDriver = simulations.get(simID);

        if (bioDriver == null) {
            ctx.status(404).json(Map.of("error", "Simulation ID not found."));
            return;
        }

        Map<String, Object> result = new LinkedHashMap<>();
        // Build globals from BioDriver
        result.put("globals", getGlobalInfo(bioDriver));

        // Build module info for each module in BioDriver
        Map<String, Object> modulesMap = new LinkedHashMap<>();
        IBioModule[] modules = bioDriver.getModules();
        for (IBioModule module : modules) {
            modulesMap.put(module.getModuleName(), buildModuleInfo(module));
        }
        result.put("modules", modulesMap);
        ctx.json(result);
    }

    // Helper method to extract the definition type from a method name.
    private String getDefinitionType(String methodName) {
        if (methodName.contains("ConsumerDefinition")) {
            String type = methodName.substring(3, methodName.indexOf("ConsumerDefinition"));
            return type.substring(0, 1).toLowerCase() + type.substring(1);
        } else if (methodName.contains("ProducerDefinition")) {
            String type = methodName.substring(3, methodName.indexOf("ProducerDefinition"));
            return type.substring(0, 1).toLowerCase() + type.substring(1);
        }
        return "";
    }

    // Helper method to build the flow rate information from a SingleFlowRateControllable.
    private Map<String, Object> buildRatesInfo(SingleFlowRateControllable sfc) {
        Map<String, Object> rates = new LinkedHashMap<>();
        rates.put("maxFlowRates", toList(sfc.getMaxFlowRates()));
        rates.put("desiredFlowRates", toList(sfc.getDesiredFlowRates()));
        rates.put("actualFlowRates", toList(sfc.getActualFlowRates()));
        rates.put("initialFlowRates", toList(sfc.getInitialDesiredFlowRates()));
        return rates;
    }

    // Helper method to extract store connections from a StoreFlowRateControllable.
    private List<String> getConnections(StoreFlowRateControllable sfg) {
        List<String> connections = new ArrayList<>();
        try {
            Method getStoresMethod = sfg.getClass().getMethod("getStores");
            Object storesObj = getStoresMethod.invoke(sfg);
            if (storesObj instanceof Object[] stores) {
                for (Object storeObj : stores) {
                    if (storeObj != null) {
                        try {
                            Method getModuleNameMethod = storeObj.getClass().getMethod("getModuleName");
                            String storeName = (String) getModuleNameMethod.invoke(storeObj);
                            connections.add(storeName);
                        } catch (Exception ex) {
                            // If unable to obtain store name, skip this store.
                        }
                    }
                }
            }
        } catch (Exception ex) {
            // If the getStores method is not available, return an empty list.
        }
        return connections;
    }

    /**
     * Refactored method to build definition information (consumer/producer) from the module using reflection.
     */
    private Map<String, Object> buildDefinitionInfo(IBioModule module) {
        Map<String, Object> definition = new LinkedHashMap<>();
        List<Map<String, Object>> consumers = new ArrayList<>();
        List<Map<String, Object>> producers = new ArrayList<>();

        for (Method m : module.getClass().getMethods()) {
            String methodName = m.getName();
            if (!methodName.equals("getClass") && methodName.startsWith("get") &&
                    methodName.endsWith("Definition") && m.getParameterCount() == 0) {
                try {
                    Object defObject = m.invoke(module);
                    if (defObject == null)
                        continue;

                    String type = getDefinitionType(methodName);
                    Map<String, Object> defMap = new LinkedHashMap<>();
                    defMap.put("type", type);

                    // If the definition object supports flow rate control, extract its arrays.
                    if (defObject instanceof SingleFlowRateControllable sfc) {
                        defMap.put("rates", buildRatesInfo(sfc));

                        // Also check if store connections are available.
                        if (defObject instanceof StoreFlowRateControllable sfg) {
                            defMap.put("connections", getConnections(sfg));
                        } else {
                            defMap.put("connections", new ArrayList<String>());
                        }
                    }

                    if (methodName.contains("ConsumerDefinition")) {
                        consumers.add(defMap);
                    } else if (methodName.contains("ProducerDefinition")) {
                        producers.add(defMap);
                    }
                } catch (Exception e) {
                    logger.error("Error processing definition method {}: {}", methodName, e.getMessage());
                }
            }
        }
        if (!consumers.isEmpty()) {
            definition.put("consumers", consumers);
        }
        if (!producers.isEmpty()) {
            definition.put("producers", producers);
        }
        return definition;
    }

    /**
     * Builds environment-specific information (native properties and store info).
     */
    private Map<String, Object> buildEnvironmentInfo(SimEnvironment simEnv) {
        Map<String, Object> envInfo = new LinkedHashMap<>();
        envInfo.put("temperature", simEnv.getTemperature());
        envInfo.put("temperatureInKelvin", simEnv.getTemperatureInKelvin());
        envInfo.put("lightIntensity", simEnv.getLightIntensity());
        envInfo.put("currentVolume", simEnv.getCurrentVolume());
        envInfo.put("initialVolume", simEnv.getInitialVolume());
        envInfo.put("dayLength", simEnv.getDayLength());
        envInfo.put("hourOfDayStart", simEnv.getHourOfDayStart());
        envInfo.put("maxLumens", simEnv.getMaxLumens());

        Map<String, Object> stores = new LinkedHashMap<>();
        stores.put("O2Store", buildStoreInfo(simEnv.getO2Store()));
        stores.put("CO2Store", buildStoreInfo(simEnv.getCO2Store()));
        stores.put("NitrogenStore", buildStoreInfo(simEnv.getNitrogenStore()));
        stores.put("OtherStore", buildStoreInfo(simEnv.getOtherStore()));
        stores.put("VaporStore", buildStoreInfo(simEnv.getVaporStore()));
        envInfo.put("stores", stores);

        return envInfo;
    }

    /**
     * Helper method to build a schedule's information.
     * It outputs the ordered schedule as a list of activities and a string representation of all activities.
     */
    private Map<String, Object> buildScheduleInfo(Schedule schedule) {
        Map<String, Object> scheduleInfo = new LinkedHashMap<>();
        List<Map<String, Object>> ordered = new ArrayList<>();
        for (int i = 0; i < schedule.getNumberOfScheduledActivities(); i++) {
            Activity act = schedule.getScheduledActivityByOrder(i);
            Map<String, Object> actMap = new LinkedHashMap<>();
            actMap.put("name", act.getName());
            actMap.put("timeLength", act.getTimeLength());
            actMap.put("activityIntensity", act.getActivityIntensity());
            ordered.add(actMap);
        }
        scheduleInfo.put("orderedSchedule", ordered);
        return scheduleInfo;
    }

    /**
     * Builds a list of crew people details from a CrewGroup.
     */
    private List<Map<String, Object>> buildCrewGroupPeopleInfo(CrewGroup crewGroup) {
        List<Map<String, Object>> peopleList = new ArrayList<>();
        for (CrewPerson person : crewGroup.getCrewPeople()) {
            Map<String, Object> personInfo = new LinkedHashMap<>();
            personInfo.put("name", person.getName());
            personInfo.put("age", person.getAge());
            personInfo.put("weight", person.getWeight());
            personInfo.put("sex", person.getSex().toString());
            
            // Current Activity Info
            Activity currentActivity = person.getCurrentActivity();
            Map<String, Object> activityInfo = new LinkedHashMap<>();
            if (currentActivity != null) {
                activityInfo.put("name", currentActivity.getName());
                activityInfo.put("timeLength", currentActivity.getTimeLength());
                activityInfo.put("activityIntensity", currentActivity.getActivityIntensity());
            }
            personInfo.put("currentActivity", activityInfo);
            personInfo.put("timeActivityPerformed", person.getTimeActivityPerformed());
            
            // Resource metrics (if getters are available)
            personInfo.put("O2Consumed", person.getO2Consumed());
            personInfo.put("CO2Produced", person.getCO2Produced());
            personInfo.put("caloriesConsumed", person.getFoodConsumed());
            personInfo.put("potableWaterConsumed", person.getPotableWaterConsumed());
            personInfo.put("dirtyWaterProduced", person.getDirtyWaterProduced());
            personInfo.put("greyWaterProduced", person.getGreyWaterProduced());
            // Additional fields could be added here if getters exist (e.g., O2Needed, O2Ratio, etc.)

            // Attach schedule details. Here we assume a getSchedule() method exists on CrewPerson.
            personInfo.put("schedule", buildScheduleInfo(person.getSchedule()));
            
            peopleList.add(personInfo);
        }
        return peopleList;
    }

    /**
     * Helper method to build sensor-specific information.
     */
    private Map<String, Object> buildSensorInfo(GenericSensor sensor) {
        Map<String, Object> sensorInfo = new LinkedHashMap<>();
        sensorInfo.put("value", sensor.getValue());
        IBioModule inputModule = sensor.getInputModule();
        sensorInfo.put("input", inputModule != null ? inputModule.getModuleName() : "none");
        return sensorInfo;
    }

    /**
     * Helper method to build actuator-specific information.
     */
    private Map<String, Object> buildActuatorInfo(GenericActuator actuator) {
        Map<String, Object> actuatorInfo = new LinkedHashMap<>();
        actuatorInfo.put("value", actuator.getValue());
        actuatorInfo.put("max", actuator.getMax());
        actuatorInfo.put("min", actuator.getMin());
        IBioModule outputModule = actuator.getOutputModule();
        actuatorInfo.put("output", outputModule != null ? outputModule.getModuleName() : "none");
        return actuatorInfo;
    }

    /**
     * Consolidated method to build module information.
     */
    private Map<String, Object> buildModuleInfo(IBioModule module) {
        Map<String, Object> info = new LinkedHashMap<>();
        info.put("moduleName", module.getModuleName());

        // Add definitions (consumers & producers) if any.
        Map<String, Object> definitions = buildDefinitionInfo(module);
        info.putAll(definitions);

        // If it's a store module, append its property details.
        if (module instanceof Store) {
            info.put("properties", buildStoreInfo((Store) module));
        }

        // Add environment details for SimEnvironment types.
        if (module instanceof SimEnvironment) {
            info.put("environment", buildEnvironmentInfo((SimEnvironment) module));
        }

        // Add crew group details if module is a CrewGroup.
        if (module instanceof CrewGroup) {
            info.put("people", buildCrewGroupPeopleInfo((CrewGroup) module));
        }

        // Add crew group details if module is a CrewGroup.
        if (module instanceof CrewGroup) {
            info.put("people", buildCrewGroupPeopleInfo((CrewGroup) module));
        }

        // Add sensor details if module is a GenericSensor.
        if (module instanceof GenericSensor sensor) {
            info.put("sensor", buildSensorInfo(sensor));
        }

        return info;
    }

    /**
     * Helper method to convert a float array into a List of Floats.
     *
     * @param arr the float array
     * @return a List of Float values
     */
    private List<Float> toList(float[] arr) {
        List<Float> list = new ArrayList<>();
        if (arr != null) {
            for (float f : arr) {
                list.add(f);
            }
        }
        return list;
    }

    /**
     * Builds a globals map containing primitive properties from the BioDriver.
     * It uses public getters and reflection for fields without public accessors.
     *
     * @param bioDriver the BioDriver instance
     * @return a Map of global simulation properties
     */
    private Map<String, Object> getGlobalInfo(BioDriver bioDriver) {
        Map<String, Object> globals = new LinkedHashMap<>();
        globals.put("myID", bioDriver.getID());
        globals.put("simulationIsPaused", bioDriver.isPaused());
        globals.put("simulationStarted", bioDriver.isStarted());
        globals.put("ticksGoneBy", bioDriver.getTicks());
        try {
            Field nTicksField = bioDriver.getClass().getDeclaredField("nTicks");
            nTicksField.setAccessible(true);
            globals.put("nTicks", nTicksField.get(bioDriver));
        } catch (Exception e) {
            globals.put("nTicks", 0);
        }
        try {
            Field runTillCrewDeathField = bioDriver.getClass().getDeclaredField("runTillCrewDeath");
            runTillCrewDeathField.setAccessible(true);
            globals.put("runTillCrewDeath", runTillCrewDeathField.get(bioDriver));
        } catch (Exception e) {
            globals.put("runTillCrewDeath", false);
        }
        try {
            Field runTillPlantDeathField = bioDriver.getClass().getDeclaredField("runTillPlantDeath");
            runTillPlantDeathField.setAccessible(true);
            globals.put("runTillPlantDeath", runTillPlantDeathField.get(bioDriver));
        } catch (Exception e) {
            globals.put("runTillPlantDeath", false);
        }
        globals.put("looping", bioDriver.isLooping());
        globals.put("driverStutterLength", bioDriver.getDriverStutterLength());
        globals.put("tickLength", bioDriver.getTickLength());
        return globals;
    }

    /**
     * Builds a map of primitive properties from a store.
     *
     * @param store the store instance
     * @return a Map of store properties
     */
    private Map<String, Object> buildStoreInfo(Store store) {
        Map<String, Object> properties = new LinkedHashMap<>();
        properties.put("currentLevel", store.getCurrentLevel());
        properties.put("currentCapacity", store.getCurrentCapacity());
        properties.put("overflow", store.getOverflow());
        try {
            Method isPipeMethod = store.getClass().getMethod("isPipe");
            Boolean isPipe = (Boolean) isPipeMethod.invoke(store);
            properties.put("isPipe", isPipe);
        } catch (Exception ex) {
            // If not available, omit isPipe.
        }
        return properties;
    }
}