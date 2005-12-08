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
	private float myQuantity = 1f;
    protected Logger myLogger;
    public Enzyme(){
        myLogger = Logger.getLogger(getClass());
    }
    public abstract void tick();
	public abstract void reset();
	
	public void setQuantity(float pQuantity){
		myQuantity = pQuantity;
	}
	
	public float getQuantity(){
		return myQuantity;
	}
	
}
