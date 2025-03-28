package com.traclabs.biosim.server.simulation.power;

public final class RPCMArmedStatus {
    public static final int _enabled = 0;
    public static final RPCMArmedStatus enabled = new RPCMArmedStatus(_enabled);
    public static final int _inhibited = 1;
    public static final RPCMArmedStatus inhibited = new RPCMArmedStatus(_inhibited);
    private int value = -1;

    private RPCMArmedStatus(int i) {
        value = i;
    }

    public static RPCMArmedStatus from_int(int value) {
        switch (value) {
            case _enabled:
                return enabled;
            case _inhibited:
                return inhibited;
            default:
                throw new IllegalArgumentException();
        }
    }

    public int value() {
        return value;
    }

    public String toString() {
        switch (value) {
            case _enabled:
                return "enabled";
            case _inhibited:
                return "inhibited";
            default:
                throw new IllegalArgumentException();
        }
    }
}
