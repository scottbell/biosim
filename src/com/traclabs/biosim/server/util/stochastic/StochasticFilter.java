package com.traclabs.biosim.server.util.stochastic;

public abstract class StochasticFilter {
	private boolean myEnabledState = true;
	
	/**
     * Randomizes a number passed through it.
     * 
     * @param pValue
     *            Filters using a gaussian function.
     * @return the randomized result
     */
    public float randomFilter(float pValue){
    	if (getEnabled())
    		return internalFilter(pValue);
    	else
    		return pValue;
    }
    
    protected abstract float internalFilter(float pValue);
    
    public void setEnabled(boolean enabled){
    	this.myEnabledState = enabled;
    }
    
    public boolean getEnabled(){
    	return myEnabledState;
    }

	public void reset() {
	}
}
