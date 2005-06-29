/*
 * Created on Jun 14, 2005
 *
 * TODO
 */
package com.traclabs.biosim.server.simulation.food.photosynthesis;

/**
 * @author scott
 *
 */
public class Chemical {
    private float myQuantityCurrent;
    private float myQuantityCached;
    private float myInitialQuantity;
    
    public Chemical(float pInitialQuantity){
    	myInitialQuantity = myQuantityCurrent = myQuantityCached = pInitialQuantity;
    }
    
    public float take(float amountRequested){
        //idiot check
        if (amountRequested <= 0f) {
            return 0f;
        }
        float takenAmount;
        //asking for more stuff than exists, reaction can't happen
        if (amountRequested > myQuantityCurrent) {
            return 0f;
        }
		takenAmount = amountRequested;
		myQuantityCurrent -= takenAmount;
        return takenAmount;
    }
    
    public void add(float amountRequested) {
        myQuantityCurrent += amountRequested;
    }
    
    public void tick(){
        myQuantityCached = myQuantityCurrent; 
    }

    public float getQuantity() {
        return myQuantityCached;
    }
    
    public String toString(){
        return Float.toString(myQuantityCurrent);
    }
    
    public void setQuantity(float pQuantity){
        myQuantityCached = myQuantityCurrent = pQuantity;
    }

	public void reset() {
		myQuantityCurrent = myQuantityCached = myInitialQuantity;
	}

}
