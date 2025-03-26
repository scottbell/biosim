package com.traclabs.biosim.server.simulation.thermal;

public final class IFHXBypassState
{
    private int value = -1;
    public static final int _bypass = 0;
    public static final IFHXBypassState bypass = new IFHXBypassState(_bypass);
    public static final int _flowthrough = 1;
    public static final IFHXBypassState flowthrough = new IFHXBypassState(_flowthrough);
    public int value()
    {
        return value;
    }
    public static IFHXBypassState from_int(int value)
    {
        switch (value) {
            case _bypass: return bypass;
            case _flowthrough: return flowthrough;
            default: throw new IllegalArgumentException();
        }
    }
    public String toString()
    {
        switch (value) {
            case _bypass: return "bypass";
            case _flowthrough: return "flowthrough";
            default: throw new IllegalArgumentException();
        }
    }
    protected IFHXBypassState(int i)
    {
        value = i;
    }
}
