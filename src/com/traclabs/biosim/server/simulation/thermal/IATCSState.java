package com.traclabs.biosim.server.simulation.thermal;

public final class IATCSState {
    public static final int _idle = 0;
    public static final IATCSState idle = new IATCSState(_idle);
    public static final int _operational = 1;
    public static final IATCSState operational = new IATCSState(_operational);
    public static final int _armed = 2;
    public static final IATCSState armed = new IATCSState(_armed);
    public static final int _transitioning = 3;
    public static final IATCSState transitioning = new IATCSState(_transitioning);
    private int value = -1;

    private IATCSState(int i) {
        value = i;
    }

    public static IATCSState from_int(int value) {
        switch (value) {
            case _idle:
                return idle;
            case _operational:
                return operational;
            case _armed:
                return armed;
            case _transitioning:
                return transitioning;
            default:
                throw new IllegalArgumentException();
        }
    }

    public int value() {
        return value;
    }

    public String toString() {
        switch (value) {
            case _idle:
                return "idle";
            case _operational:
                return "operational";
            case _armed:
                return "armed";
            case _transitioning:
                return "transitioning";
            default:
                throw new IllegalArgumentException();
        }
    }
}
