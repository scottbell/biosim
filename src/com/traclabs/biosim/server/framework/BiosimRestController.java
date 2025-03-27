package com.traclabs.biosim.server.framework;

import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.plugin.bundled.CorsPluginConfig;
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
        this.driver = new BioDriver(0);
        
        this.app = Javalin.create(config -> {
            config.bundledPlugins.enableCors(cors -> cors.addRule(CorsPluginConfig.CorsRule::anyHost));
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
     * Stop the REST API server
     */
    public void stop() {
        app.stop();
        logger.info("BioSim REST API stopped");
    }
}
