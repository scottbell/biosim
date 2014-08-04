package com.traclabs.biosim.idl.simulation.thermal;
/**
 *	Generated from IDL definition of enum "IFHXValveCommandStatus"
 *	@author JacORB IDL compiler 
 */

public final class IFHXValveCommandStatus
	implements org.omg.CORBA.portable.IDLEntity
{
	private int value = -1;
	public static final int _enabled = 0;
	public static final IFHXValveCommandStatus enabled = new IFHXValveCommandStatus(_enabled);
	public static final int _inhibited = 1;
	public static final IFHXValveCommandStatus inhibited = new IFHXValveCommandStatus(_inhibited);
	public int value()
	{
		return value;
	}
	public static IFHXValveCommandStatus from_int(int value)
	{
		switch (value) {
			case _enabled: return enabled;
			case _inhibited: return inhibited;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected IFHXValveCommandStatus(int i)
	{
		value = i;
	}
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
