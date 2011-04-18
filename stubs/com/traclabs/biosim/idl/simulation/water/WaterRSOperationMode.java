package com.traclabs.biosim.idl.simulation.water;
/**
 *	Generated from IDL definition of enum "WaterRSOperationMode"
 *	@author JacORB IDL compiler 
 */

public final class WaterRSOperationMode
	implements org.omg.CORBA.portable.IDLEntity
{
	private int value = -1;
	public static final int _FULL = 0;
	public static final WaterRSOperationMode FULL = new WaterRSOperationMode(_FULL);
	public static final int _OFF = 1;
	public static final WaterRSOperationMode OFF = new WaterRSOperationMode(_OFF);
	public static final int _GREY_WATER_ONLY = 2;
	public static final WaterRSOperationMode GREY_WATER_ONLY = new WaterRSOperationMode(_GREY_WATER_ONLY);
	public static final int _PARTIAL = 3;
	public static final WaterRSOperationMode PARTIAL = new WaterRSOperationMode(_PARTIAL);
	public int value()
	{
		return value;
	}
	public static WaterRSOperationMode from_int(int value)
	{
		switch (value) {
			case _FULL: return FULL;
			case _OFF: return OFF;
			case _GREY_WATER_ONLY: return GREY_WATER_ONLY;
			case _PARTIAL: return PARTIAL;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected WaterRSOperationMode(int i)
	{
		value = i;
	}
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
