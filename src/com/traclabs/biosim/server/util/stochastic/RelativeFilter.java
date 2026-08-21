package com.traclabs.biosim.server.util.stochastic;

import com.traclabs.biosim.server.util.MathUtils;

/**
 * Relative process noise: N(x, σ·|x|). Does not go through {@link NormalFilter}.
 *
 * Absolute σ on NormalFilter forged mass (one knob for W, L, and mol;
 * negatives clamped to 0). This filter is dimensionless, returns 0 when
 * the command is 0, and resamples rather than rectifying negatives.
 */
public class RelativeFilter extends StochasticFilter {
    private static final int MAX_RESAMPLES = 16;
    private final double mySigma;

    public RelativeFilter(double sigma) {
        this.mySigma = sigma;
    }

    public double getSigma() {
        return mySigma;
    }

    protected float internalFilter(float pValue) {
        if (pValue == 0f || mySigma <= 0)
            return pValue == 0f ? 0f : pValue;
        double deviation = mySigma * Math.abs(pValue);
        for (int i = 0; i < MAX_RESAMPLES; i++) {
            double result = MathUtils.gaussian(pValue, deviation);
            if (result >= 0)
                return (float) result;
        }
        // Skip rather than clamp to 0 (rectification creates mass).
        return pValue;
    }
}
