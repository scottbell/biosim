package com.traclabs.biosim.server.util.stochastic;

import com.traclabs.biosim.server.util.MathUtils;

public class NormalFilter extends StochasticFilter {
    private final double myDeviation;

    public NormalFilter(double deviation) {
        this.myDeviation = deviation;
    }

    protected float internalFilter(float pValue) {
        if (myDeviation <= 0)
            return pValue;
        double result = MathUtils.gaussian(pValue, myDeviation);
        if (result < 0)
            return 0;
        return (float) result;
    }
}
