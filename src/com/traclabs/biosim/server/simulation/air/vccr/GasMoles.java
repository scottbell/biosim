package com.traclabs.biosim.server.simulation.air.vccr;

public class GasMoles {
    private final float myO2Moles;
    private final float myCo2Moles;
    private final float myVaporMoles;
    private final float myNitrogenMoles;
    private final float myOtherMoles;

    /**
     * Assume sea level concentration
     */
    public GasMoles(float molesOfAir) {
        myO2Moles = molesOfAir * 0.20f;
        myCo2Moles = molesOfAir * 0.00111f;
        myVaporMoles = molesOfAir * 0.01f;
        myNitrogenMoles = molesOfAir * 0.7896f;
        myOtherMoles = molesOfAir * 0.01f;
    }

}
