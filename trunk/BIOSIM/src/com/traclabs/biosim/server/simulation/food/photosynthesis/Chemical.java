/*
 * Created on Jun 14, 2005
 *
 * TODO
 */
package com.traclabs.biosim.server.simulation.food.photosynthesis;

/**
 * @author scott
 *
 * TODO
 */
public class Chemical {
    private float quantityCurrent;
    private float quantityCached;
    
    public Chemical(float initialQuantity){
        quantityCurrent = quantityCached = initialQuantity;
    }
    
    public float take(float amountRequested){
        //idiot check
        if (amountRequested <= 0f) {
            return 0f;
        }
        float takenAmount;
        //asking for more stuff than exists, reaction can't happen
        if (amountRequested > quantityCurrent) {
            return 0f;
        }
        //stuff exists for request
        else {
            takenAmount = amountRequested;
            quantityCurrent -= takenAmount;
        }
        return takenAmount;
    }
    
    public void add(float amountRequested) {
        quantityCurrent += amountRequested;
    }
    
    public void update(){
        quantityCached = quantityCurrent; 
    }

    /**
     * @return
     */
    public float getQuantity() {
        return quantityCached;
    }
    
    public String toString(){
        return Float.toString(quantityCurrent);
    }
    
    public void setQuantity(float pQuantity){
        quantityCached = quantityCurrent = pQuantity;
    }

}
