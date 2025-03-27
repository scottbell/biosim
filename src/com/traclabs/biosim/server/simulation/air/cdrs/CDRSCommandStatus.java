package com.traclabs.biosim.server.simulation.air.cdrs;

public final class CDRSCommandStatus {
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	private int value = -1;
	public static final int _inhibited = 0;
	public static final CDRSCommandStatus inhibited = new CDRSCommandStatus(_inhibited);
	public static final int _enabled = 1;
	public static final CDRSCommandStatus enabled = new CDRSCommandStatus(_enabled);
	public int value()
	{
		return value;
	}
	private CDRSCommandStatus(int value) {
		this.value = value;
	}
	public static CDRSCommandStatus from_int(int value)
	{
		switch (value) {
			case _inhibited: return inhibited;
			case _enabled: return enabled;
			default: throw new IllegalArgumentException();
		}
	}
	public String toString()
	{
		switch (value) {
			case _inhibited: return "inhibited";
			case _enabled: return "enabled";
			default: throw new IllegalArgumentException();
		}
	}
}
