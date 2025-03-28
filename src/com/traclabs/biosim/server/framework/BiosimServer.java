package com.traclabs.biosim.server.framework;

import io.javalin.Javalin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class for the BioSim server.
 * Starts the Javalin REST API server.
 */
public class BiosimServer {
    private static final Logger logger = LoggerFactory.getLogger(BiosimServer.class);
    private static final int DEFAULT_PORT = 8009;
    // Default host now uses 0.0.0.0 so that Docker can reach the service
    private static final String DEFAULT_HOST = "0.0.0.0";

    public static void main(String[] args) {
        String host = DEFAULT_HOST;
        int port = DEFAULT_PORT;

        // If at least one argument is provided, treat it as the host
        if (args.length > 0) {
            host = args[0];
        }

        // If a second argument is provided, treat it as the port
        if (args.length > 1) {
            try {
                port = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                logger.warn("Invalid port number: {}, using default: {}", args[1], DEFAULT_PORT);
            }
        }

        // Test XML configuration for errors
        BiosimInitializer.getInstance(-1);

        // Start the Javalin server
        Javalin app = Javalin.create(config -> {
            // Using Javalin 6.0.0's bundledPlugins method:
            config.bundledPlugins.enableCors(cors -> cors.addRule(it -> it.anyHost()));
            config.http.defaultContentType = "application/json";
        });

        // Register REST endpoints
        SimulationController simulationController = new SimulationController();
        simulationController.registerEndpoints(app);

        // Bind to the host and port provided
        app.start(host, port);
        logger.info("🌎 BioSim server started 🌎 on {}:{}", host, port);

        // Add shutdown hook to stop the server gracefully
        Runtime.getRuntime().addShutdownHook(new Thread(app::stop));
    }
}