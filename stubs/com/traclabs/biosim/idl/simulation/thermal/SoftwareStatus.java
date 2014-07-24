package com.traclabs.biosim.idl.simulation.thermal;
/**
 *	Generated from IDL definition of enum "SoftwareStatus"
 *	@author JacORB IDL compiler 
 */

public final class SoftwareStatus
	implements org.omg.CORBA.portable.IDLEntity
{
	private int value = -1;
	public static final int _shutdown = 0;
	public static final SoftwareStatus shutdown = new SoftwareStatus(_shutdown);
	public static final int _softwareArmed = 1;
	public static final SoftwareStatus softwareArmed = new SoftwareStatus(_softwareArmed);
	public static final int _running = 2;
	public static final SoftwareStatus running = new SoftwareStatus(_running);
	public int value()
	{
		return value;
	}
	public static SoftwareStatus from_int(int value)
	{
		switch (value) {
			case _shutdown: return shutdown;
			case _softwareArmed: return softwareArmed;
			case _running: return running;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected SoftwareStatus(int i)
	{
		value = i;
	}
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
