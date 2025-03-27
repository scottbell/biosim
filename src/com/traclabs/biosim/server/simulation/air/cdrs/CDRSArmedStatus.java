package com.traclabs.biosim.server.simulation.air.cdrs;
public final class CDRSArmedStatus
{
	private int value = -1;
	public static final int _armed = 0;
	public static final CDRSArmedStatus armed = new CDRSArmedStatus(_armed);
	public static final int _in_progress = 1;
	public static final CDRSArmedStatus in_progress = new CDRSArmedStatus(_in_progress);
	public static final int _not_armed = 2;
	public static final CDRSArmedStatus not_armed = new CDRSArmedStatus(_not_armed);
	public int value()
	{
		return value;
	}
	private CDRSArmedStatus(int value) {
		this.value = value;
	}
	public static CDRSArmedStatus from_int(int value)
	{
		switch (value) {
			case _armed: return armed;
			case _in_progress: return in_progress;
			case _not_armed: return not_armed;
			default: throw new IllegalArgumentException();
		}
	}
	public String toString()
	{
		switch (value) {
			case _armed: return "armed";
			case _in_progress: return "in_progress";
			case _not_armed: return "not_armed";
			default: throw new IllegalArgumentException();
		}
	}
}
