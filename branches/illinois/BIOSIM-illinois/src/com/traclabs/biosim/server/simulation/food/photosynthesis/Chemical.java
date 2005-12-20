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
    private float myQuantity;
    private float myInitialQuantity;
    
    public Chemical(float pInitialQuantity){
    	myQuantity = myInitialQuantity = pInitialQuantity;
    }
    
    public float take(float amountRequested){
        //idiot check
        if (amountRequested <= 0f) {
            return 0f;
        }
        float takenAmount;
        //asking for more stuff than exists, reaction can't happen
        if (amountRequested > myQuantity) {
            return 0f;
        }
		takenAmount = amountRequested;
		myQuantity -= takenAmount;
        return takenAmount;
    }
    
    public void add(float amountRequested) {
        myQuantity += amountRequested;
    }
    
    public void tick(){
    }

    public float getQuantity() {
        return myQuantity;
    }
    
    public String toString(){
        return Float.toString(myQuantity);
    }
    
    public void setQuantity(float pQuantity){
        myQuantity = pQuantity;
    }

	public void reset() {
		myQuantity = myInitialQuantity;
	}

}
