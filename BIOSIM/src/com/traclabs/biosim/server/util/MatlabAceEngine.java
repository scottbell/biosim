package com.traclabs.biosim.server.util;

import java.io.IOException;
import java.net.Socket;

public class MatlabAceEngine extends Engine {
	private Socket mySocket;
	
	private static final String DEFAULT_HOSTNAME = "localhost";
	private static final int DEFAULT_PORT = 12759;
	
    public void open() {
    	try{
    		mySocket = new Socket(DEFAULT_HOSTNAME, DEFAULT_PORT);
    	}
    	catch (IOException e){
    		myLogger.error(e);
    		myLogger.error("Couldn't connect to "+DEFAULT_HOSTNAME+" on port "+DEFAULT_PORT);
    	}
    }

    public void put(double[] inputVector) {
        myLogger.debug("matlabAceEngine::put");
    }

    public double[] get() {
        myLogger.debug("matlabAceEngine::get");
        double[] out = null;
        return out;
    }

    public void close() {
    	try{
    		mySocket = new Socket(DEFAULT_HOSTNAME, DEFAULT_PORT);
    	}
    	catch (IOException e){
    		myLogger.error(e);
    		myLogger.error("Couldn't connect to "+DEFAULT_HOSTNAME+" on port "+DEFAULT_PORT);
    	}
    }
}