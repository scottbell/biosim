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
    
    // Registry of modules by ID and name
    private static final Map<Integer, Map<String, Object>> moduleRegistry = new ConcurrentHashMap<>();
    
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
        // Health check endpoint
        app.get("/health", ctx -> ctx.result("OK"));
        
        // Get all modules
        app.get("/modules", ctx -> {
            ctx.json(moduleRegistry);
        });
        
        // Get modules by ID
        app.get("/modules/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Map<String, Object> modules = moduleRegistry.get(id);
            if (modules != null) {
                ctx.json(modules);
            } else {
                ctx.status(404).result("No modules found with ID: " + id);
            }
        });
        
        // Get specific module by ID and name
        app.get("/modules/{id}/{name}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            String name = ctx.pathParam("name");
            Map<String, Object> modules = moduleRegistry.get(id);
            if (modules != null && modules.containsKey(name)) {
                ctx.json(modules.get(name));
            } else {
                ctx.status(404).result("Module not found: " + id + "/" + name);
            }
        });
    }
    
    /**
     * Register a module with the registry
     * 
     * @param id The ID of the module
     * @param name The name of the module
     * @param module The module object
     */
    public static void registerModule(int id, String name, Object module) {
        initialize();
        moduleRegistry.computeIfAbsent(id, k -> new ConcurrentHashMap<>()).put(name, module);
        logger.info("Registered module: {}/{}", id, name);
    }
    
    /**
     * Get a module from the registry
     * 
     * @param id The ID of the module
     * @param name The name of the module
     * @return The module object, or null if not found
     */
    public static Object getModule(int id, String name) {
        initialize();
        Map<String, Object> modules = moduleRegistry.get(id);
        if (modules != null) {
            return modules.get(name);
        }
        return null;
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
