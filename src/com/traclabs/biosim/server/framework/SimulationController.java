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
        app.get("/api/simulation/{simID}/globals", this::getGlobalInfo);
        app.get("/api/simulation/{simID}/modules/{moduleName}", this::getModuleInfo);
        app.post("/api/simulation/{simID}/modules/{moduleName}/consumers/{type}", this::updateConsumerDefinition);
        app.post("/api/simulation/{simID}/modules/{moduleName}/producers/{type}", this::updateProducerDefinition);
        app.get("/api/simulation/{simID}", this::getSimulationDetails);
        app.post("/api/simulation/{simID}/modules/{moduleName}/malfunctions", this::postMalfunction);
        app.get("/api/simulation/{simID}/modules/{moduleName}/malfunctions", this::getModuleMalfunctions);
        app.delete("/api/simulation/{simID}/modules/{moduleName}/malfunctions/{malfunctionID}", this::deleteMalfunction);
        app.delete("/api/simulation/{simID}/modules/{moduleName}/malfunctions", this::deleteAllMalfunctions);
    }

    /**
     * Gets the list of all simulation IDs.
     *
     * @param context The Javalin context
     */
    private void listSimulations(Context context) {
        // Return the keys of the simulations map as a JSON response.
        context.json(Map.of("simulations", simulations.keySet()));
    }

    /**
     * Starts a new simulation with the given XML configuration.
     *
     * @param context The Javalin context
     */
    private void startSimulation(Context context) {
        String xmlConfig = context.body();
        if (xmlConfig == null || xmlConfig.isEmpty()) {
            context.status(400).json(Map.of("error", "XML configuration is required in the request body."));
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

            context.json(Map.of("simId", simID));
            logger.info("🏃‍♂️ Started simulation with ID {}", simID);
        } catch (Exception e) {
            logger.error("Failed to start simulation: {}", e.getMessage());
            e.printStackTrace();
            context.status(500).json(Map.of("error", "Failed to start simulation: " + e.getMessage()));
        }
    }

    /**
     * Advances the simulation by one tick.
     *
     * @param context The Javalin context
     */
    private void tickSimulation(Context context) {
        int simID = Integer.parseInt(context.pathParam("simID"));
        BioDriver bioDriver = simulations.get(simID);

        if (bioDriver == null) {
            context.status(404).json(Map.of("error", "Simulation ID not found."));
            return;
        }

        bioDriver.advanceOneTick();
        context.json(Map.of("ticks", bioDriver.getTicks()));
        logger.info("Simulation {} advanced to tick {}", simID, bioDriver.getTicks());
    }

    /**
     * Returns detailed information for a module of a simulation.
     * The output JSON will contain consumer/producer definitions (if any)
     * along with flow rate arrays and connection info (from the underlying
     * definition objects) or store properties if it is a store.
     *
     * @param context the Javalin context from the request.
     */
    private void getModuleInfo(Context context) {
        BioDriver bioDriver = getBioDriver(context);
        if (bioDriver == null)
            return;

        String moduleName = context.pathParam("moduleName");
        IBioModule targetModule = findModule(bioDriver, moduleName);
        if (targetModule == null) {
            context.status(404).json(Map.of("error", "Module not found."));
            return;
        }
        Map<String, Object> moduleInfo = buildModuleInfo(targetModule);
        context.json(moduleInfo);
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
     * @param context the Javalin context from the request.
     */
    private void getSimulationDetails(Context context) {
        BioDriver bioDriver = getBioDriver(context);
        if (bioDriver == null)
            return;

        Map<String, Object> result = new LinkedHashMap<>();
        // Build globals from BioDriver
        result.put("globals", getGlobalDetails(bioDriver));

        // Build module info for each module in BioDriver
        Map<String, Object> modulesMap = new LinkedHashMap<>();
        IBioModule[] modules = bioDriver.getModules();
        for (IBioModule module : modules) {
            modulesMap.put(module.getModuleName(), buildModuleInfo(module));
        }
        result.put("modules", modulesMap);
        context.json(result);
    }

    // Helper method to extract the definition type from a method name.
    private String getDefinitionType(String methodName) {
        if (methodName.contains("ConsumerDefinition")) {
            String type = methodName.substring(3, methodName.indexOf("ConsumerDefinition"));
            return type.charAt(0) + type.substring(1);
        } else if (methodName.contains("ProducerDefinition")) {
            String type = methodName.substring(3, methodName.indexOf("ProducerDefinition"));
            return type.charAt(0) + type.substring(1);
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
        info.put("moduleType", module.getClass().getSimpleName());

        // Add definitions (consumers & producers) if any.
        Map<String, Object> definitions = buildDefinitionInfo(module);
        info.putAll(definitions);

        // Add properties based on module type.
        Map<String, Object> properties = buildModuleProperties(module);
        if (!properties.isEmpty()) {
            info.put("properties", properties);
        }

        info.put("malfunctions", buildMalfunctionsInfo(module));

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

    private void getGlobalInfo(Context context) {
        BioDriver bioDriver = getBioDriver(context);
        if (bioDriver == null)
            return;

        Map<String, Object> globals = getGlobalDetails(bioDriver);
        context.json(globals);
    }

    /**
     * Builds a globals map containing primitive properties from the BioDriver.
     * It uses public getters and reflection for fields without public accessors.
     *
     * @param bioDriver the BioDriver instance
     * @return a Map of global simulation properties
     */
    private Map<String, Object> getGlobalDetails(BioDriver bioDriver) {
        Map<String, Object> globals = new LinkedHashMap<>();
        globals.put("myID", bioDriver.getID());
        globals.put("simulationIsPaused", bioDriver.isPaused());
        globals.put("simulationStarted", bioDriver.isStarted());
        globals.put("ticksGoneBy", bioDriver.getTicks());
        try {
            Field nTicksField = bioDriver.getClass().getDeclaredField("nTicks");
            nTicksField.setAccessible(true);
            globals.put("runTillN", nTicksField.get(bioDriver));
        } catch (Exception e) {
            globals.put("runTillN", 0);
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

    /**
     * Updates a definition (consumer or producer) of a module based on the given suffixes.
     */
    private void updateDefinition(Context context, String definitionSuffix, String setterSuffix, String definitionLabel) {
        try {
            int simID = Integer.parseInt(context.pathParam("simID"));
            String moduleName = context.pathParam("moduleName");
            String type = context.pathParam("type");

            BioDriver bioDriver = getBioDriver(context);
            if (bioDriver == null)
                return;

            IBioModule targetModule = findModule(bioDriver, moduleName);
            if (targetModule == null) {
                context.status(404).json(Map.of("error", "Module not found."));
                return;
            }
            Object definition = findDefinition(targetModule, definitionSuffix, type);
            if (definition == null) {
                context.status(404).json(Map.of("error", definitionLabel + " type '" + type + "' not found for module " + moduleName + "."));
                return;
            }
            SingleFlowRateControllable sfc = (SingleFlowRateControllable) definition;
            float[] oldDesired = sfc.getDesiredFlowRates();
            float[] oldMax = sfc.getMaxFlowRates();

            String[] oldConnections = null;
            if (definition instanceof StoreFlowRateControllable sfg) {
                IBioModule[] stores = sfg.getStores();
                oldConnections = new String[stores.length];
                for (int i = 0; i < stores.length; i++) {
                    oldConnections[i] = (stores[i] != null ? stores[i].getModuleName() : null);
                }
            }
            Map<String, Object> payload = context.bodyAsClass(Map.class);
            List<?> desiredList = (List<?>) payload.get("desiredFlowRates");
            List<?> connectionsList = (List<?>) payload.get("connections");

            float[] newDesired = (desiredList != null) ? convertListToFloatArray(desiredList, oldDesired) : oldDesired;
            String[] newConnections = oldConnections;
            if (connectionsList != null) {
                if (connectionsList.size() != newDesired.length) {
                    context.status(400).json(Map.of("error", "Length of connections array does not match desiredFlowRates array."));
                    return;
                }
                newConnections = new String[connectionsList.size()];
                for (int i = 0; i < connectionsList.size(); i++) {
                    newConnections[i] = connectionsList.get(i).toString();
                }
            }

            // Fix setter name casing for potable water
            if (type.equalsIgnoreCase("potablewater")) {
                type = "potableWater";
            }
            String setterName = "set" + Character.toUpperCase(type.charAt(0)) + type.substring(1) + setterSuffix;
            Method setter = findSetter(definition, setterName);
            if (setter == null) {
                context.status(500).json(Map.of("error", "Setter method " + setterName + " not found."));
                return;
            }

            Object newInputs = null;
            if (definition instanceof StoreFlowRateControllable) {
                int n = newConnections.length;
                // Create an array of the expected type using reflection.
                Class<?> expectedInputType = setter.getParameterTypes()[0].getComponentType();
                newInputs = java.lang.reflect.Array.newInstance(expectedInputType, n);
                for (int i = 0; i < n; i++) {
                    IBioModule inputModule = findModule(bioDriver, newConnections[i]);
                    if (inputModule == null) {
                        context.status(400).json(Map.of("error", "Connection module '" + newConnections[i] + "' not found."));
                        return;
                    }
                    java.lang.reflect.Array.set(newInputs, i, inputModule);
                }
            }
            setter.invoke(definition, newInputs, oldMax, newDesired);
            context.json(Map.of("message", definitionLabel + " " + type + " updated successfully."));
        } catch (Exception e) {
            context.status(500).json(Map.of("error", "Failed to update " + definitionLabel.toLowerCase() + ": " + e.getMessage()));
        }
    }

    // Updated consumer definition update method
    private void updateConsumerDefinition(Context context) {
        updateDefinition(context, "ConsumerDefinition", "Inputs", "Consumer");
    }

    // Updated producer definition update method
    private void updateProducerDefinition(Context context) {
        updateDefinition(context, "ProducerDefinition", "Outputs", "Producer");
    }

    /**
     * Retrieves the BioDriver instance for a given simulation context.
     */
    private BioDriver getBioDriver(Context context) {
        int simID = Integer.parseInt(context.pathParam("simID"));
        BioDriver bioDriver = simulations.get(simID);
        if (bioDriver == null) {
            context.status(404).json(Map.of("error", "Simulation ID not found."));
        }
        return bioDriver;
    }

    /**
     * Finds a module within a BioDriver based on its name.
     */
    private IBioModule findModule(BioDriver bioDriver, String moduleName) {
        for (IBioModule module : bioDriver.getModules()) {
            if (module.getModuleName().equalsIgnoreCase(moduleName)) {
                return module;
            }
        }
        return null;
    }

    /**
     * Finds the definition object (consumer or producer) from a module based on the specified suffix and type.
     */
    private Object findDefinition(IBioModule module, String definitionSuffix, String type) throws Exception {
        for (Method m : module.getClass().getMethods()) {
            String mName = m.getName();
            if (mName.startsWith("get") && mName.endsWith(definitionSuffix) && m.getParameterCount() == 0) {
                String defType = getDefinitionType(mName);
                if (defType.equalsIgnoreCase(type)) {
                    return m.invoke(module);
                }
            }
        }
        return null;
    }

    /**
     * Finds a setter method with the given name that takes 3 parameters.
     */
    private Method findSetter(Object definition, String setterName) {
        for (Method m : definition.getClass().getMethods()) {
            if (m.getName().equals(setterName) && m.getParameterCount() == 3) {
                return m;
            }
        }
        return null;
    }

    private List<Map<String, Object>> buildMalfunctionsInfo(IBioModule module) {
        List<Map<String, Object>> malfunctionList = new ArrayList<>();
        Malfunction[] malfunctions = module.getMalfunctions();
        if (malfunctions != null) {
            for (Malfunction m : malfunctions) {
                Map<String, Object> malfunctionInfo = new LinkedHashMap<>();
                malfunctionInfo.put("id", m.getID());
                malfunctionInfo.put("name", m.getName());
                malfunctionInfo.put("intensity", m.getIntensity().toString());
                malfunctionInfo.put("length", m.getLength().toString());
                malfunctionInfo.put("performed", m.hasPerformed());
                malfunctionInfo.put("tickToMalfunction", m.getTickToMalfunction());
                malfunctionInfo.put("doneEnoughRepairWork", m.doneEnoughRepairWork());
                malfunctionList.add(malfunctionInfo);
            }
        }
        return malfunctionList;
    }

    private void getModuleMalfunctions(Context context) {
        BioDriver bioDriver = getBioDriver(context);
        if (bioDriver == null)
            return;
        String moduleName = context.pathParam("moduleName");
        IBioModule targetModule = findModule(bioDriver, moduleName);
        if (targetModule == null) {
            context.status(404).json(Map.of("error", "Module not found."));
            return;
        }
        List<Map<String, Object>> malfunctionList = buildMalfunctionsInfo(targetModule);
        context.json(malfunctionList);
    }

    /**
     * Converts a list of numbers to a float array, or returns the fallback if the list is null.
     */
    private float[] convertListToFloatArray(List<?> list, float[] fallback) {
        if (list != null) {
            float[] result = new float[list.size()];
            for (int i = 0; i < list.size(); i++) {
                result[i] = ((Number) list.get(i)).floatValue();
            }
            return result;
        }
        return fallback;
    }

    /**
     * Consolidates property information from a module. Returns the evaluated properties based on module type.
     */
    private Map<String, Object> buildModuleProperties(IBioModule module) {
        if (module instanceof Store) {
            return buildStoreInfo((Store) module);
        } else if (module instanceof SimEnvironment) {
            return buildEnvironmentInfo((SimEnvironment) module);
        } else if (module instanceof CrewGroup) {
            Map<String, Object> crewGroupProps = new LinkedHashMap<>();
            crewGroupProps.put("crewPeople", buildCrewGroupPeopleInfo((CrewGroup) module));
            return crewGroupProps;
        } else if (module instanceof GenericSensor) {
            return buildSensorInfo((GenericSensor) module);
        } else if (module instanceof GenericActuator) {
            return buildActuatorInfo((GenericActuator) module);
        }
        return new LinkedHashMap<>();
    }

    private void postMalfunction(Context context) {
        try {
            String moduleName = context.pathParam("moduleName");

            BioDriver bioDriver = getBioDriver(context);
            if (bioDriver == null) return;

            IBioModule targetModule = findModule(bioDriver, moduleName);
            if (targetModule == null) {
                context.status(404).json(Map.of("error", "Module not found."));
                return;
            }
            Map<String, Object> payload = context.bodyAsClass(Map.class);
            if (payload.get("intensity") == null || payload.get("length") == null) {
                context.status(400).json(Map.of("error", "Both 'intensity' and 'length' fields are required."));
                return;
            }
            String intensityStr = payload.get("intensity").toString();
            String lengthStr = payload.get("length").toString();
            MalfunctionIntensity intensity;
            MalfunctionLength length;
            try {
                intensity = MalfunctionIntensity.valueOf(intensityStr);
                length = MalfunctionLength.valueOf(lengthStr);
            } catch (IllegalArgumentException e) {
                context.status(400).json(Map.of("error", "Invalid 'intensity' or 'length' value."));
                return;
            }
            long malfunctionID = -1;
            Object tickObj = payload.get("tickToOccur");
            if (tickObj != null) {
                int tickToOccur = Integer.parseInt(tickObj.toString());
                targetModule.scheduleMalfunction(intensity, length, tickToOccur);
                context.json(Map.of("message", "Malfunction scheduled for tick " + tickToOccur));
                return;
            } else {
                Malfunction malfunction = targetModule.startMalfunction(intensity, length);
                if (malfunction == null) {
                    context.status(500).json(Map.of("error", "Failed to start malfunction."));
                    return;
                }
                malfunctionID = malfunction.getID();
            }
            context.json(Map.of("malfunctionID", malfunctionID));
        } catch (Exception e) {
            context.status(500).json(Map.of("error", "Error processing malfunction: " + e.getMessage()));
        }
    }

    private void deleteMalfunction(Context context) {
        try {
            String moduleName = context.pathParam("moduleName");
            long malfunctionID = Long.parseLong(context.pathParam("malfunctionID"));

            BioDriver bioDriver = getBioDriver(context);
            if (bioDriver == null) return;

            IBioModule targetModule = findModule(bioDriver, moduleName);
            if (targetModule == null) {
                context.status(404).json(Map.of("error", "Module not found."));
                return;
            }

            targetModule.clearMalfunction(malfunctionID);
            context.json(Map.of("message", "Malfunction " + malfunctionID + " cleared."));
        } catch (Exception e) {
            context.status(500).json(Map.of("error", "Error clearing malfunction: " + e.getMessage()));
        }
    }

    private void deleteAllMalfunctions(Context context) {
        try {
            int simID = Integer.parseInt(context.pathParam("simID"));
            String moduleName = context.pathParam("moduleName");

            BioDriver bioDriver = getBioDriver(context);
            if (bioDriver == null) return;

            IBioModule targetModule = findModule(bioDriver, moduleName);
            if (targetModule == null) {
                context.status(404).json(Map.of("error", "Module not found."));
                return;
            }

            targetModule.clearAllMalfunctions();
            context.json(Map.of("message", "All malfunctions cleared."));
        } catch (Exception e) {
            context.status(500).json(Map.of("error", "Error clearing all malfunctions: " + e.getMessage()));
        }
    }
}