package com.traclabs.biosim.server.simulation.thermal;

public final class IFHXValveState {
    public static final int _open = 0;
    public static final IFHXValveState open = new IFHXValveState(_open);
    public static final int _closed = 1;
    public static final IFHXValveState closed = new IFHXValveState(_closed);
    private int value = -1;

    private IFHXValveState(int i) {
        value = i;
    }

    public static IFHXValveState from_int(int value) {
        switch (value) {
            case _open:
                return open;
            case _closed:
                return closed;
            default:
                throw new IllegalArgumentException();
        }
    }

    public int value() {
        return value;
    }

    public String toString() {
        switch (value) {
            case _open:
                return "open";
            case _closed:
                return "closed";
            default:
                throw new IllegalArgumentException();
        }
    }
}
