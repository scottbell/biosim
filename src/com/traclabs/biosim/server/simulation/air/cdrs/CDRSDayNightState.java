package com.traclabs.biosim.server.simulation.air.cdrs;

public final class CDRSDayNightState {
    public static final int _day = 0;
    public static final CDRSDayNightState day = new CDRSDayNightState(_day);
    public static final int _night = 1;
    public static final CDRSDayNightState night = new CDRSDayNightState(_night);
    private int value = -1;

    private CDRSDayNightState(int value) {
        this.value = value;
    }

    public static CDRSDayNightState from_int(int value) {
        switch (value) {
            case _day:
                return day;
            case _night:
                return night;
            default:
                throw new IllegalArgumentException();
        }
    }

    public int value() {
        return value;
    }

    public String toString() {
        switch (value) {
            case _day:
                return "day";
            case _night:
                return "night";
            default:
                throw new IllegalArgumentException();
        }
    }
}
