package com.traclabs.biosim.server.util;

import org.apache.log4j.Logger;

public abstract class Engine {
    protected Logger myLogger;
    
    public Engine() {
    	myLogger = Logger.getLogger(this.getClass());
    }

    public abstract void open();

    public abstract void put(double[] inputVector);

    public abstract double[] get();

    public abstract void close();
}