package com.traclabs.biosim.server.util.stochastic;

public class NoFilter extends StochasticFilter {
    protected float internalFilter(float pValue) {
        return pValue;
    }

}
