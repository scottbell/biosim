package com.traclabs.biosim.server.simulation.framework;

import org.apache.log4j.Logger;

import com.traclabs.biosim.idl.simulation.framework.SingleFlowRateControllablePOA;

/**
 * @author Scott Bell
 */

public abstract class SingleFlowRateControllableImpl extends
        SingleFlowRateControllablePOA {
    private float[] myMaxFlowRates = new float[0];

    private float[] myActualFlowRates = new float[0];

    private float[] myDesiredFlowRates = new float[0];
    
    private float[] myInitalMaxFlowRates = new float[0];
    private float[] myInitialActualFlowRates = new float[0];
    private float[] myInitialDesiredFlowRates = new float[0];
    
    protected Logger myLogger;

    public SingleFlowRateControllableImpl() {
    	myLogger = Logger.getLogger(this.getClass());
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
    
    public void setActualFlowRate(float value, int index) {
        myActualFlowRates[index] = value;
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

    public void setInitialMaxFlowRates(float[] pMaxFlowRates) {
        myInitalMaxFlowRates = pMaxFlowRates;
        myMaxFlowRates = new float[myInitalMaxFlowRates.length];
        System.arraycopy(myInitalMaxFlowRates, 0, myMaxFlowRates, 0, myInitalMaxFlowRates.length);
    }

    public void setInitialDesiredFlowRates(float[] pDesiredFlowRates) {
        myInitialDesiredFlowRates = pDesiredFlowRates;
        myDesiredFlowRates = new float[myInitialDesiredFlowRates.length];
        System.arraycopy(myInitialDesiredFlowRates, 0, myDesiredFlowRates, 0, myInitialDesiredFlowRates.length);
    }

    public void setInitialActualFlowRates(float[] pActualFlowRates) {
        myInitialActualFlowRates = pActualFlowRates;
        myActualFlowRates = new float[myInitialActualFlowRates.length];
        System.arraycopy(myInitialActualFlowRates, 0, myActualFlowRates, 0, myInitialActualFlowRates.length);
    }
    
    public int getFlowRateCardinality(){
    	return myActualFlowRates.length;
    }
    
    public float getTotalMaxFlowRate(){
    	float totalMaxFlowRate = 0;
    	for (float maxFlowRate : myMaxFlowRates)
    		totalMaxFlowRate += maxFlowRate;
    	return totalMaxFlowRate;
    }
    
    public float getTotalDesiredFlowRate(){
    	float totalDesiredFlowRate = 0;
    	for (float maxFlowRate : myDesiredFlowRates)
    		totalDesiredFlowRate += maxFlowRate;
    	return totalDesiredFlowRate;
    }
    
    public float getTotalActualFlowRate(){
    	float totalActualFlowRate = 0;
    	for (float actualFlowRate : myActualFlowRates)
    		totalActualFlowRate += actualFlowRate;
    	return totalActualFlowRate;
    }
    
    public float getAveragePercentageFull(){
    	float totalDesiredFlowRate = getTotalDesiredFlowRate();
    	if (totalDesiredFlowRate <= 0)
    		return 1f;
    	return getTotalActualFlowRate() / totalDesiredFlowRate;
    }
    
    public float getPercentageFull(int index){
    	if (myDesiredFlowRates[index] <= 0)
    		return 1f;
    	return myActualFlowRates[index] / myDesiredFlowRates[index];
    }
    
    public void reset(){
        System.arraycopy(myInitalMaxFlowRates, 0, myMaxFlowRates, 0, myInitalMaxFlowRates.length);
        System.arraycopy(myInitialDesiredFlowRates, 0, myDesiredFlowRates, 0, myInitialDesiredFlowRates.length);
        System.arraycopy(myInitialActualFlowRates, 0, myActualFlowRates, 0, myInitialActualFlowRates.length);
    }
}