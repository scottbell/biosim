package com.traclabs.biosim.api.rest;

import com.traclabs.biosim.api.BioDriver;
import com.traclabs.biosim.api.BioModule;
import com.traclabs.biosim.api.MalfunctionIntensity;
import com.traclabs.biosim.api.MalfunctionLength;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * REST API controller for BioSim.
 * Exposes BioModule and BioDriver functionality via RESTful endpoints.
 */
public class BiosimRestController {
    private static final Logger logger = LoggerFactory.getLogger(BiosimRestController.class);
    
    private final Javalin app;
    private final BioDriver driver;
    
    /**
     * Constructor
     * 
     * @param port The port to run the server on
     */
    public BiosimRestController(int port) {
        this.driver = new BioDriver("BioSim REST Driver");
        
        this.app = Javalin.create(config -> {
            config.plugins.enableCors(cors -> cors.add(it -> {
                it.anyHost();
            }));
            config.http.defaultContentType = "application/json";
        });
        
        setupRoutes();
        
        app.start(port);
        logger.info("BioSim REST API started on port {}", port);
    }
    
    /**
     * Set up the REST API routes
     */
    private void setupRoutes() {
        // Driver endpoints
        app.get("/api/driver", this::getDriverInfo);
        app.post("/api/driver/start", this::startSimulation);
        app.post("/api/driver/stop", this::stopSimulation);
        app.post("/api/driver/pause", this::pauseSimulation);
        app.post("/api/driver/resume", this::resumeSimulation);
        app.post("/api/driver/reset", this::resetSimulation);
        app.post("/api/driver/tick", this::advanceOneTick);
        app.post("/api/driver/run-till-n", this::setRunTillN);
        app.post("/api/driver/tick-length", this::setTickLength);
        
        // Module endpoints
        app.get("/api/modules", this::getModules);
        app.get("/api/modules/{name}", this::getModule);
        app.post("/api/modules/{name}/malfunction", this::startMalfunction);
        app.post("/api/modules/{name}/fix", this::fixMalfunction);
        app.post("/api/modules/{name}/reset", this::resetModule);
        
        // Sensor endpoints
        app.get("/api/sensors", this::getSensors);
        
        // Actuator endpoints
        app.get("/api/actuators", this::getActuators);
    }
    
    /**
     * Get information about the driver
     * 
     * @param ctx The Javalin context
     */
    private void getDriverInfo(Context ctx) {
        Map<String, Object> info = new HashMap<>();
        info.put("name", driver.getName());
        info.put("started", driver.isStarted());
        info.put("paused", driver.isPaused());
        info.put("ticks", driver.getTicks());
        info.put("tickLength", driver.getTickLength());
        info.put("looping", driver.isLooping());
        info.put("done", driver.isDone());
        
        ctx.json(info);
    }
    
    /**
     * Start the simulation
     * 
     * @param ctx The Javalin context
     */
    private void startSimulation(Context ctx) {
        driver.startSimulation();
        ctx.json(Map.of("status", "started"));
    }
    
    /**
     * Stop the simulation
     * 
     * @param ctx The Javalin context
     */
    private void stopSimulation(Context ctx) {
        driver.endSimulation();
        ctx.json(Map.of("status", "stopped"));
    }
    
    /**
     * Pause the simulation
     * 
     * @param ctx The Javalin context
     */
    private void pauseSimulation(Context ctx) {
        driver.setPauseSimulation(true);
        ctx.json(Map.of("status", "paused"));
    }
    
    /**
     * Resume the simulation
     * 
     * @param ctx The Javalin context
     */
    private void resumeSimulation(Context ctx) {
        driver.setPauseSimulation(false);
        ctx.json(Map.of("status", "resumed"));
    }
    
    /**
     * Reset the simulation
     * 
     * @param ctx The Javalin context
     */
    private void resetSimulation(Context ctx) {
        driver.reset();
        ctx.json(Map.of("status", "reset"));
    }
    
    /**
     * Advance the simulation by one tick
     * 
     * @param ctx The Javalin context
     */
    private void advanceOneTick(Context ctx) {
        driver.advanceOneTick();
        ctx.json(Map.of("ticks", driver.getTicks()));
    }
    
    /**
     * Set the number of ticks to run the simulation for
     * 
     * @param ctx The Javalin context
     */
    private void setRunTillN(Context ctx) {
        try {
            long ticks = Long.parseLong(ctx.formParam("ticks"));
            driver.setRunTillN(ticks);
            ctx.json(Map.of("runTillN", ticks));
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("error", "Invalid ticks value"));
        }
    }
    
    /**
     * Set the tick length
     * 
     * @param ctx The Javalin context
     */
    private void setTickLength(Context ctx) {
        try {
            float tickLength = Float.parseFloat(ctx.formParam("tickLength"));
            driver.setTickLength(tickLength);
            ctx.json(Map.of("tickLength", tickLength));
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("error", "Invalid tick length value"));
        }
    }
    
    /**
     * Get all modules
     * 
     * @param ctx The Javalin context
     */
    private void getModules(Context ctx) {
        List<Map<String, Object>> moduleInfos = driver.getModules().stream()
                .map(this::createModuleInfo)
                .collect(Collectors.toList());
        
        ctx.json(moduleInfos);
    }
    
    /**
     * Get a specific module
     * 
     * @param ctx The Javalin context
     */
    private void getModule(Context ctx) {
        String name = ctx.pathParam("name");
        BioModule module = driver.getModule(name);
        
        if (module == null) {
            ctx.status(404).json(Map.of("error", "Module not found: " + name));
            return;
        }
        
        ctx.json(createModuleInfo(module));
    }
    
    /**
     * Start a malfunction in a module
     * 
     * @param ctx The Javalin context
     */
    private void startMalfunction(Context ctx) {
        String name = ctx.pathParam("name");
        BioModule module = driver.getModule(name);
        
        if (module == null) {
            ctx.status(404).json(Map.of("error", "Module not found: " + name));
            return;
        }
        
        String intensityStr = ctx.formParam("intensity", "MEDIUM_MALF");
        String lengthStr = ctx.formParam("length", "TEMPORARY_MALF");
        
        MalfunctionIntensity intensity;
        try {
            intensity = MalfunctionIntensity.valueOf(intensityStr);
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("error", "Invalid intensity value: " + intensityStr));
            return;
        }
        
        MalfunctionLength length;
        try {
            length = MalfunctionLength.valueOf(lengthStr);
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("error", "Invalid length value: " + lengthStr));
            return;
        }
        
        long id = module.startMalfunction(intensity, length);
        ctx.json(Map.of("id", id, "module", name, "intensity", intensity, "length", length));
    }
    
    /**
     * Fix a malfunction in a module
     * 
     * @param ctx The Javalin context
     */
    private void fixMalfunction(Context ctx) {
        String name = ctx.pathParam("name");
        BioModule module = driver.getModule(name);
        
        if (module == null) {
            ctx.status(404).json(Map.of("error", "Module not found: " + name));
            return;
        }
        
        try {
            long id = Long.parseLong(ctx.formParam("id", "-1"));
            if (id == -1) {
                module.fixAllMalfunctions();
                ctx.json(Map.of("status", "all malfunctions fixed", "module", name));
            } else {
                module.fixMalfunction(id);
                ctx.json(Map.of("status", "malfunction fixed", "module", name, "id", id));
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(Map.of("error", "Invalid malfunction ID"));
        }
    }
    
    /**
     * Reset a module
     * 
     * @param ctx The Javalin context
     */
    private void resetModule(Context ctx) {
        String name = ctx.pathParam("name");
        BioModule module = driver.getModule(name);
        
        if (module == null) {
            ctx.status(404).json(Map.of("error", "Module not found: " + name));
            return;
        }
        
        module.reset();
        ctx.json(Map.of("status", "reset", "module", name));
    }
    
    /**
     * Get all sensors
     * 
     * @param ctx The Javalin context
     */
    private void getSensors(Context ctx) {
        List<Map<String, Object>> sensorInfos = driver.getSensors().stream()
                .map(this::createModuleInfo)
                .collect(Collectors.toList());
        
        ctx.json(sensorInfos);
    }
    
    /**
     * Get all actuators
     * 
     * @param ctx The Javalin context
     */
    private void getActuators(Context ctx) {
        List<Map<String, Object>> actuatorInfos = driver.getActuators().stream()
                .map(this::createModuleInfo)
                .collect(Collectors.toList());
        
        ctx.json(actuatorInfos);
    }
    
    /**
     * Create a map of module information
     * 
     * @param module The module
     * @return A map of module information
     */
    private Map<String, Object> createModuleInfo(BioModule module) {
        Map<String, Object> info = new HashMap<>();
        info.put("id", module.getID());
        info.put("name", module.getModuleName());
        info.put("ticks", module.getMyTicks());
        info.put("tickLength", module.getTickLength());
        info.put("malfunctioning", module.isMalfunctioning());
        info.put("failureEnabled", module.isFailureEnabled());
        
        List<Map<String, Object>> malfunctions = module.getMalfunctions().stream()
                .map(m -> {
                    Map<String, Object> mInfo = new HashMap<>();
                    mInfo.put("id", m.getID());
                    mInfo.put("name", m.getName());
                    mInfo.put("intensity", m.getIntensity());
                    mInfo.put("length", m.getLength());
                    mInfo.put("performed", m.hasPerformed());
                    mInfo.put("doneEnoughRepairWork", m.doneEnoughRepairWork());
                    return mInfo;
                })
                .collect(Collectors.toList());
        
        info.put("malfunctions", malfunctions);
        
        return info;
    }
    
    /**
     * Stop the REST API server
     */
    public void stop() {
        app.stop();
        logger.info("BioSim REST API stopped");
    }
}
