package com.traclabs.biosim.idl.simulation.air.cdrs;
/**
 *	Generated from IDL definition of enum "CDRSArmedStatus"
 *	@author JacORB IDL compiler 
 */

public final class CDRSArmedStatus
	implements org.omg.CORBA.portable.IDLEntity
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
	public static CDRSArmedStatus from_int(int value)
	{
		switch (value) {
			case _armed: return armed;
			case _in_progress: return in_progress;
			case _not_armed: return not_armed;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected CDRSArmedStatus(int i)
	{
		value = i;
	}
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
