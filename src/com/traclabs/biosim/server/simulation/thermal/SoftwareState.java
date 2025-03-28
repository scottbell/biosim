package com.traclabs.biosim.server.simulation.thermal;

public final class SoftwareState {
    public static final int _shutdown = 0;
    public static final SoftwareState shutdown = new SoftwareState(_shutdown);
    public static final int _softwareArmed = 1;
    public static final SoftwareState softwareArmed = new SoftwareState(_softwareArmed);
    public static final int _running = 2;
    public static final SoftwareState running = new SoftwareState(_running);
    private int value = -1;

    private SoftwareState(int i) {
        value = i;
    }

    public static SoftwareState from_int(int value) {
        switch (value) {
            case _shutdown:
                return shutdown;
            case _softwareArmed:
                return softwareArmed;
            case _running:
                return running;
            default:
                throw new IllegalArgumentException();
        }
    }

    public int value() {
        return value;
    }

    public String toString() {
        switch (value) {
            case _shutdown:
                return "shutdown";
            case _softwareArmed:
                return "softwareArmed";
            case _running:
                return "running";
            default:
                throw new IllegalArgumentException();
        }
    }
}
