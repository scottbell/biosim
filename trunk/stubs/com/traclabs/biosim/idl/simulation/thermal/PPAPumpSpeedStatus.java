package com.traclabs.biosim.idl.simulation.thermal;
/**
 *	Generated from IDL definition of enum "PPAPumpSpeedStatus"
 *	@author JacORB IDL compiler 
 */

public final class PPAPumpSpeedStatus
	implements org.omg.CORBA.portable.IDLEntity
{
	private int value = -1;
	public static final int _pumpArmed = 0;
	public static final PPAPumpSpeedStatus pumpArmed = new PPAPumpSpeedStatus(_pumpArmed);
	public static final int _notArmed = 1;
	public static final PPAPumpSpeedStatus notArmed = new PPAPumpSpeedStatus(_notArmed);
	public int value()
	{
		return value;
	}
	public static PPAPumpSpeedStatus from_int(int value)
	{
		switch (value) {
			case _pumpArmed: return pumpArmed;
			case _notArmed: return notArmed;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected PPAPumpSpeedStatus(int i)
	{
		value = i;
	}
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
