package com.traclabs.biosim.server.framework;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Main class for the BioSim server.
 * Starts the REST API server.
 */
public class BiosimServer {
    private static final Logger logger = LoggerFactory.getLogger(BiosimServer.class);

    private static final int DEFAULT_PORT = 8080;

    private final BiosimRestController controller;

    /**
     * Constructor
     *
     * @param port The port to run the server on
     */
    public BiosimServer(int port) {
        logger.info("Starting BioSim server on port {}", port);
        controller = new BiosimRestController(port);
    }

    /**
     * Main method
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        int port = DEFAULT_PORT;

        // Parse command line arguments
        if (args.length > 0) {
            try {
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                logger.warn("Invalid port number: {}, using default: {}", args[0], DEFAULT_PORT);
            }
        }

        // Start the server
        BiosimServer server = new BiosimServer(port);

        // Add shutdown hook to stop the server when the JVM exits
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            logger.info("Shutting down BioSim server");
            server.stop();
        }));

        logger.info("BioSim server started on port {}", port);
        logger.info("Press Ctrl+C to stop");
    }

    /**
     * Stop the server
     */
    public void stop() {
        logger.info("Stopping BioSim server");
        controller.stop();
    }
}
