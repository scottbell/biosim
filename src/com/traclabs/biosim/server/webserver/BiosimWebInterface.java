package com.traclabs.biosim.server.webserver;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.server.framework.BiosimInitializer;

import java.io.IOException;
import java.net.URI;


/**
 * Main class.
 *
 */
public class BiosimWebInterface {
    // Base URI the Grizzly HTTP server will listen on
    public static final String BASE_URI = "http://localhost:8080/biosim/";
    private static BiosimInitializer biosimInit;
    private static BioHolder biosimHolder;
//    public static final String BASE_URI = "http://192.168.63.79:8080/myapp/";

    public BiosimWebInterface(BiosimInitializer passedInitializer){
    	try {
    		biosimInit = passedInitializer;
    		BioHolder holder = BioHolderInitializer.getBioHolder();
            // create a resource config that scans for JAX-RS resources and providers
            // in com.traclabs.biosim.server.webserver package
            final ResourceConfig rc = new ResourceConfig().packages("com.traclabs.biosim.server.webserver");
            // create and start a new instance of grizzly http server
            // exposing the Jersey application at BASE_URI
            final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
            System.in.read();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	public static BiosimInitializer getBiosimInit() {
		return biosimInit;
	}

	public static BioHolder getHolder() {
		return biosimHolder;
	}
}

