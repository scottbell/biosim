package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.framework.SingleFlowRateControllablePOA;

/**
 * @author Scott Bell
 */

public abstract class SingleFlowRateControllableImpl extends SingleFlowRateControllablePOA {
    private float[] myMaxFlowRates;

    private float[] myActualFlowRates;

    private float[] myDesiredFlowRates;
    
    public SingleFlowRateControllableImpl(){
        myMaxFlowRates = new float[0];
        myActualFlowRates = new float[0];
        myDesiredFlowRates = new float[0];
    }
    
    public void setMaxFlowRate(float value, int index) {
        myMaxFlowRates[index] = value;
    }
    
    public float getMaxFlowRate(int index) {
        return myMaxFlowRates[index];
    }

    public void setDesiredFlowRate(float value, int index) {
        myDesiredFlowRates[index] = value;
    }

    public float getDesiredFlowRate(int index) {
        return myDesiredFlowRates[index];
    }

    public float getActualFlowRate(int index) {
        return myActualFlowRates[index];
    }
    
    public float[] getMaxFlowRates() {
        return myMaxFlowRates;
    }
    
    public float[] getDesiredFlowRates() {
        return myDesiredFlowRates;
    }

    public float[] getActualFlowRates() {
        return myActualFlowRates;
    }
    
    protected void setMaxFlowRates(float[] pMaxFlowRates) {
        myMaxFlowRates = pMaxFlowRates;
    }
    
    protected void setDesiredFlowRates(float[] pDesiredFlowRates) {
        myDesiredFlowRates = pDesiredFlowRates;
    }

    protected void setActualFlowRates(float[] pActualFlowRates) {
        myActualFlowRates = pActualFlowRates;
    }
}