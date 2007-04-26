package com.traclabs.biosim.server.util.stochastic;

public class NoFilter implements StochasticFilter {
	public float randomFilter(float pValue) {
		return pValue;
	}

}
