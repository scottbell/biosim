package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.server.framework.BioModule;

import java.util.Arrays;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * @author Scott Bell
 */

public abstract class SingleFlowRateControllable {
    private float[] myMaxFlowRates = new float[0];

    private float[] myActualFlowRates = new float[0];

    private float[] myDesiredFlowRates = new float[0];
    
    private float[] myInitalMaxFlowRates = new float[0];
    private float[] myInitialActualFlowRates = new float[0];
    private float[] myInitialDesiredFlowRates = new float[0];
    
    private BioModule myModule = null;
    
    protected Logger myLogger;

    public SingleFlowRateControllable(BioModule pModule) {
    	myLogger = LoggerFactory.getLogger(this.getClass());
    	myModule = pModule;
    }
    
    public void malfunction(){
    	Arrays.fill(myMaxFlowRates, 0);
    	Arrays.fill(myDesiredFlowRates, 0);
    	Arrays.fill(myActualFlowRates, 0);
    }

	private void checkArguments(int index, float[] flowRateArray) {
		if (index >= flowRateArray.length){
			throw new IllegalArgumentException("Something referenced " + myModule.getModuleName() + " with an index ("+index+") greater than the flowrate array length ("+flowRateArray.length+")");
		}
	}
	
	protected float randomFilter(float value){
		return myModule.getStochasticFilter().randomFilter(value);
	}

    public void setMaxFlowRate(float value, int index) {
    	checkArguments(index, myMaxFlowRates);
        myMaxFlowRates[index] = value;
    }

	public float getMaxFlowRate(int index) {
    	checkArguments(index, myMaxFlowRates);
        return myMaxFlowRates[index];
    }

    public void setDesiredFlowRate(float value, int index) {
    	checkArguments(index, myDesiredFlowRates);
        myDesiredFlowRates[index] = value;
    }

    public float getDesiredFlowRate(int index) {
    	checkArguments(index, myDesiredFlowRates);
        return myDesiredFlowRates[index];
    }

    public float getActualFlowRate(int index) {
    	checkArguments(index, myActualFlowRates);
        return myActualFlowRates[index];
    }
    
    public void setActualFlowRate(float value, int index) {
    	checkArguments(index, myActualFlowRates);
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
    
    public float[] getInitialDesiredFlowRates(){
    	return myInitialDesiredFlowRates;
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
    	for (float desiredFlowRate : myDesiredFlowRates)
    		totalDesiredFlowRate += desiredFlowRate;
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
    	checkArguments(index, myDesiredFlowRates);
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