package com.traclabs.biosim.server.simulation.thermal;

public final class IATCSActivation {
	private int value = -1;
	public static final int _inProgress = 0;
	public static final IATCSActivation inProgress = new IATCSActivation(_inProgress);
	public static final int _notInProgress = 1;
	public static final IATCSActivation notInProgress = new IATCSActivation(_notInProgress);
	public int value()
	{
		return value;
	}
	public static IATCSActivation from_int(int value)
	{
		switch (value) {
			case _inProgress: return inProgress;
			case _notInProgress: return notInProgress;
			default: throw new IllegalArgumentException();
		}
	}
	public String toString()
	{
		switch (value) {
			case _inProgress: return "inProgress";
			case _notInProgress: return "notInProgress";
			default: throw new IllegalArgumentException();
		}
	}
	protected IATCSActivation(int i)
	{
		value = i;
	}
}
