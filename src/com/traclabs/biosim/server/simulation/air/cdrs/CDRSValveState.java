package com.traclabs.biosim.server.simulation.air.cdrs;

public final class CDRSValveState
{
	private int value = -1;
	public static final int _open = 0;
	public static final CDRSValveState open = new CDRSValveState(_open);
	public static final int _closed = 1;
	public static final CDRSValveState closed = new CDRSValveState(_closed);
	public int value()
	{
		return value;
	}
	private CDRSValveState(int value) {
		this.value = value;
	}
	public static CDRSValveState from_int(int value)
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
}
