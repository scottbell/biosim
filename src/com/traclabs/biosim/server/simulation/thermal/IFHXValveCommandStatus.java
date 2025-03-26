package com.traclabs.biosim.server.simulation.thermal;

public final class IFHXValveCommandStatus {
	private int value = -1;
	public static final int _enabled = 0;
	public static final IFHXValveCommandStatus enabled = new IFHXValveCommandStatus(_enabled);
	public static final int _inhibited = 1;
	public static final IFHXValveCommandStatus inhibited = new IFHXValveCommandStatus(_inhibited);
	public int value()
	{
		return value;
	}
	public static IFHXValveCommandStatus from_int(int value)
	{
		switch (value) {
			case _enabled: return enabled;
			case _inhibited: return inhibited;
			default: throw new IllegalArgumentException();
		}
	}
	public String toString()
	{
		switch (value) {
			case _enabled: return "enabled";
			case _inhibited: return "inhibited";
			default: throw new IllegalArgumentException();
		}
	}
	protected IFHXValveCommandStatus(int i)
	{
		value = i;
	}
}
