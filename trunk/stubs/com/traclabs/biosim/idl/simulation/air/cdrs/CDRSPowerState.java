package com.traclabs.biosim.idl.simulation.air.cdrs;
/**
 *	Generated from IDL definition of enum "CDRSPowerState"
 *	@author JacORB IDL compiler 
 */

public final class CDRSPowerState
	implements org.omg.CORBA.portable.IDLEntity
{
	private int value = -1;
	public static final int _off = 0;
	public static final CDRSPowerState off = new CDRSPowerState(_off);
	public static final int _on = 1;
	public static final CDRSPowerState on = new CDRSPowerState(_on);
	public int value()
	{
		return value;
	}
	public static CDRSPowerState from_int(int value)
	{
		switch (value) {
			case _off: return off;
			case _on: return on;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected CDRSPowerState(int i)
	{
		value = i;
	}
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
