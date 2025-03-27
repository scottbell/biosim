package com.traclabs.biosim.util;

import io.javalin.Javalin;
import io.javalin.http.Handler;
import io.javalin.plugin.bundled.CorsPluginConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RestUtils {
    private static final Logger logger = LoggerFactory.getLogger(RestUtils.class);
    
    // Default port for the REST API server
    private static final int DEFAULT_SERVER_PORT = 8080;
    
    // The Javalin instance for the server
    private static Javalin app;
    
    
    // Flag to make sure RestUtils only runs initialize once
    private static boolean initialized = false;
    
    /**
     * Shouldn't be called (everything static!)
     */
    private RestUtils() {
    }
    
    /**
     * Initialize the REST API server
     */
    public static synchronized void initialize() {
        if (initialized) {
            return;
        }

        app = Javalin.create(config -> {
            config.http.defaultContentType = "application/json";
            config.bundledPlugins.enableCors(cors -> {
                cors.addRule(CorsPluginConfig.CorsRule::anyHost);
            });
        });
        
        // Set up basic routes
        setupRoutes();
        
        // Start the server
        app.start(DEFAULT_SERVER_PORT);
        
        logger.info("REST API server started on port {}", DEFAULT_SERVER_PORT);
        initialized = true;
    }
    
    /**
     * Set up the basic routes for the REST API
     */
    private static void setupRoutes() {
    }
    
    /**
     * Add a custom route to the REST API
     * 
     * @param method The HTTP method (GET, POST, PUT, DELETE)
     * @param path The path for the route
     * @param handler The handler for the route
     */
    public static void addRoute(String method, String path, Handler handler) {
        initialize();
        switch (method.toUpperCase()) {
            case "GET":
                app.get(path, handler);
                break;
            case "POST":
                app.post(path, handler);
                break;
            case "PUT":
                app.put(path, handler);
                break;
            case "DELETE":
                app.delete(path, handler);
                break;
            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }
        logger.info("Added route: {} {}", method, path);
    }
    
    /**
     * Shutdown the REST API server
     */
    public static void shutdown() {
        if (app != null) {
            app.stop();
            initialized = false;
            logger.info("REST API server stopped");
        }
    }
    
    /**
     * Sleeps for a few seconds. Used when we need to wait for something.
     */
    public static void sleepAwhile() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * Sleeps for a specified number of milliseconds.
     */
    public static void sleepAwhile(int pMilliseconds) {
        try {
            Thread.sleep(pMilliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
