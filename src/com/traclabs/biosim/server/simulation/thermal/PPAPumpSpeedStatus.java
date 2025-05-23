package com.traclabs.biosim.server.simulation.thermal;


public final class PPAPumpSpeedStatus {
    public static final int _pumpArmed = 0;
    public static final PPAPumpSpeedStatus pumpArmed = new PPAPumpSpeedStatus(_pumpArmed);
    public static final int _notArmed = 1;
    public static final PPAPumpSpeedStatus notArmed = new PPAPumpSpeedStatus(_notArmed);
    private int value = -1;

    private PPAPumpSpeedStatus(int i) {
        value = i;
    }

    public static PPAPumpSpeedStatus from_int(int value) {
        switch (value) {
            case _pumpArmed:
                return pumpArmed;
            case _notArmed:
                return notArmed;
            default:
                throw new IllegalArgumentException();
        }
    }

    public int value() {
        return value;
    }

    public String toString() {
        switch (value) {
            case _pumpArmed:
                return "pumpArmed";
            case _notArmed:
                return "notArmed";
            default:
                throw new IllegalArgumentException();
        }
    }
}
