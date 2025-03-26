package com.traclabs.biosim.server.simulation.thermal;

public final class IFHXValveState
{
    private int value = -1;
    public static final int _open = 0;
    public static final IFHXValveState open = new IFHXValveState(_open);
    public static final int _closed = 1;
    public static final IFHXValveState closed = new IFHXValveState(_closed);
    public int value()
    {
        return value;
    }
    public static IFHXValveState from_int(int value)
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
    protected IFHXValveState(int i)
    {
        value = i;
    }
}
