package com.traclabs.biosim.server.simulation.air.cdrs;

public final class CDRSDayNightState
{
	private int value = -1;
	public static final int _day = 0;
	public static final CDRSDayNightState day = new CDRSDayNightState(_day);
	public static final int _night = 1;
	public static final CDRSDayNightState night = new CDRSDayNightState(_night);
	public int value()
	{
		return value;
	}
	public static CDRSDayNightState from_int(int value)
	{
		switch (value) {
			case _day: return day;
			case _night: return night;
			default: throw new IllegalArgumentException();
		}
	}
	public String toString()
	{
		switch (value) {
			case _day: return "day";
			case _night: return "night";
			default: throw new IllegalArgumentException();
		}
	}
}
