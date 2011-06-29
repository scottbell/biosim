package com.traclabs.biosim.idl.simulation.air.cdrs;
/**
 *	Generated from IDL definition of enum "CDRSDayNightState"
 *	@author JacORB IDL compiler 
 */

public final class CDRSDayNightState
	implements org.omg.CORBA.portable.IDLEntity
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
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected CDRSDayNightState(int i)
	{
		value = i;
	}
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
