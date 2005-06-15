/*
 * Created on Jun 14, 2005
 *
 * TODO
 */
package com.traclabs.biosim.server.simulation.food.photosynthesis;

import org.apache.log4j.Logger;

/**
 * @author scott
 *
 * TODO
 */
public abstract class Enzyme {
    protected Logger myLogger;
    public Enzyme(){
        myLogger = Logger.getLogger(getClass());
    }
    public abstract void tick();
}
