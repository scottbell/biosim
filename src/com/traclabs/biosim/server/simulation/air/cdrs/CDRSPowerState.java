package com.traclabs.biosim.server.simulation.air.cdrs;

public final class CDRSPowerState {
    public static final int _on = 0;
    public static final CDRSPowerState on = new CDRSPowerState(_on);
    public static final int _off = 1;
    public static final CDRSPowerState off = new CDRSPowerState(_off);
    private int value = -1;

    private CDRSPowerState(int value) {
        this.value = value;
    }

    public static CDRSPowerState from_int(int value) {
        switch (value) {
            case _on:
                return on;
            case _off:
                return off;
            default:
                throw new IllegalArgumentException();
        }
    }

    public int value() {
        return value;
    }

    public String toString() {
        switch (value) {
            case _on:
                return "on";
            case _off:
                return "off";
            default:
                throw new IllegalArgumentException();
        }
    }
}
