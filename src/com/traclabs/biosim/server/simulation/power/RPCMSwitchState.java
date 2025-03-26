package com.traclabs.biosim.server.simulation.power;

public final class RPCMSwitchState
{
    private int value = -1;
    public static final int _open = 0;
    public static final RPCMSwitchState open = new RPCMSwitchState(_open);
    public static final int _closed = 1;
    public static final RPCMSwitchState closed = new RPCMSwitchState(_closed);
    public int value()
    {
        return value;
    }
    public static RPCMSwitchState from_int(int value)
    {
        switch (value) {
            case _open: return open;
            case _closed: return closed;
            default: throw new IllegalArgumentException();
        }
    }
    public String toString()
    {
        switch (value) {
            case _open: return "open";
            case _closed: return "closed";
            default: throw new IllegalArgumentException();
        }
    }
    protected RPCMSwitchState(int i)
    {
        value = i;
    }
}
