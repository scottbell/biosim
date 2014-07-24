package com.traclabs.biosim.idl.simulation.thermal;
/**
 *	Generated from IDL definition of enum "IATCSActivation"
 *	@author JacORB IDL compiler 
 */

public final class IATCSActivation
	implements org.omg.CORBA.portable.IDLEntity
{
	private int value = -1;
	public static final int _inProgress = 0;
	public static final IATCSActivation inProgress = new IATCSActivation(_inProgress);
	public static final int _notInProgress = 1;
	public static final IATCSActivation notInProgress = new IATCSActivation(_notInProgress);
	public int value()
	{
		return value;
	}
	public static IATCSActivation from_int(int value)
	{
		switch (value) {
			case _inProgress: return inProgress;
			case _notInProgress: return notInProgress;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected IATCSActivation(int i)
	{
		value = i;
	}
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
