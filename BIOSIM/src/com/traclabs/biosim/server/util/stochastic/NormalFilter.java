package com.traclabs.biosim.server.util.stochastic;

import com.traclabs.biosim.server.util.MathUtils;

public class NormalFilter implements StochasticFilter {
	private double myDeviation;
	
	public NormalFilter(double deviation){
		this.myDeviation = deviation;
	}
	
	/**
     * Randomizes a number passed through it.
     * 
     * @param pValue
     *            Filters using a gaussian function.
     * @return the randomized result
     */
    public float randomFilter(float pValue) {
        if (myDeviation <= 0)
            return pValue;
        double result = MathUtils.gaussian(pValue, myDeviation);
        if (result < 0)
            return 0;
		return (float)result;
    }
}
