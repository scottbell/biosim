package com.traclabs.biosim.server.simulation.air.vccr;

public class Valve extends VCCRSubsystem {
    private int state = 0;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

}
