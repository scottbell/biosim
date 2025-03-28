package com.traclabs.biosim.server.framework;

import io.javalin.Javalin;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        app.post("/api/simulation/{simId}/tick", this::tickSimulation);
        app.get("/api/simulation/{simId}/ticks", this::getSimulationTicks);
        app.get("/api/simulation/{simId}/modules", this::getSimulationModules);
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
            ctx.status(400).result("XML configuration is required in the request body.");
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
            logger.info("Started simulation with ID {}", simID);
        } catch (Exception e) {
            logger.error("Failed to start simulation: {}", e.getMessage());
            ctx.status(500).result("Failed to start simulation: " + e.getMessage());
        }
    }

    /**
     * Advances the simulation by one tick.
     *
     * @param ctx The Javalin context
     */
    private void tickSimulation(Context ctx) {
        String simId = ctx.pathParam("simId");
        BioDriver bioDriver = simulations.get(simId);

        if (bioDriver == null) {
            ctx.status(404).result("Simulation ID not found.");
            return;
        }

        bioDriver.advanceOneTick();
        ctx.json(Map.of("ticks", bioDriver.getTicks()));
        logger.info("Simulation {} advanced to tick {}", simId, bioDriver.getTicks());
    }

    /**
     * Gets the current tick count of the simulation.
     *
     * @param ctx The Javalin context
     */
    private void getSimulationTicks(Context ctx) {
        String simId = ctx.pathParam("simId");
        BioDriver bioDriver = simulations.get(simId);

        if (bioDriver == null) {
            ctx.status(404).result("Simulation ID not found.");
            return;
        }

        ctx.json(Map.of("ticks", bioDriver.getTicks()));
    }

    /**
     * Gets all the registered modules of the simulation.
     *
     * @param ctx The Javalin context
     */
    private void getSimulationModules(Context ctx) {
        String simId = ctx.pathParam("simId");
        BioDriver bioDriver = simulations.get(simId);

        if (bioDriver == null) {
            ctx.status(404).result("Simulation ID not found.");
            return;
        }

        String[] moduleNames = bioDriver.getModuleNames();
        ctx.json(Map.of("modules", moduleNames));
    }
}