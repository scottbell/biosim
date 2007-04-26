package com.traclabs.biosim.server.util.stochastic;

public interface StochasticFilter {
	/**
     * Randomizes a number passed through it.
     * 
     * @param pValue
     *            Filters using a gaussian function.
     * @return the randomized result
     */
    public float randomFilter(float pValue);
}
