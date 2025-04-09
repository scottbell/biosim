package com.traclabs.biosim.server.simulation.thermal;

public final class IATCSActivation {
    public static final int _inProgress = 0;
    public static final IATCSActivation inProgress = new IATCSActivation(_inProgress);
    public static final int _notInProgress = 1;
    public static final IATCSActivation notInProgress = new IATCSActivation(_notInProgress);
    private int value = -1;

    private IATCSActivation(int i) {
        value = i;
    }

    public static IATCSActivation from_int(int value) {
        switch (value) {
            case _inProgress:
                return inProgress;
            case _notInProgress:
                return notInProgress;
            default:
                throw new IllegalArgumentException();
        }
    }

    public int value() {
        return value;
    }

    public String toString() {
        switch (value) {
            case _inProgress:
                return "inProgress";
            case _notInProgress:
                return "notInProgress";
            default:
                throw new IllegalArgumentException();
        }
    }
}
