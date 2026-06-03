package com.traclabs.biosim.server.simulation.power;

/**
 * Radioisotope Thermoelectric Generator (RTG) Power Production System.
 *
 * Output decays exponentially with mission time following
 * {@code P(t) = P0 * 2 ^ (-elapsedTime / halfLife)}, where {@code P0} is the
 * beginning-of-life output ({@code upperPowerGeneration}) and {@code elapsedTime}
 * is accumulated from the module's ticks ({@code getMyTicks() * getTickLength()}).
 *
 * @author BioSim
 */

public class RTGPowerPS extends PowerPS {

    // Half-life is expressed in the same time unit as getMyTicks() * getTickLength().
    // 87600 ~ Pu-238 half-life (~87.7 years) expressed in hours.
    private float halfLife = 87600f;

    public RTGPowerPS(int pID, String pName) {
        super(pID, pName);
    }

    public float getHalfLife() {
        return halfLife;
    }

    public void setHalfLife(float pHalfLife) {
        // Guard against a zero/negative half-life (division-by-zero / nonsensical decay).
        if (pHalfLife > 0f) {
            halfLife = pHalfLife;
        }
    }

    @Override
    protected float calculatePowerProduced() {
        float elapsedTime = getMyTicks() * getTickLength();
        float decay = (float) Math.pow(2.0, -((double) elapsedTime / (double) halfLife));
        float power = getTickLength() * getCurrentUpperPowerGeneration() * decay;
        return Math.max(0f, power);
    }
}
