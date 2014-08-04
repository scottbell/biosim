package com.traclabs.biosim.idl.simulation.thermal;
/**
 *	Generated from IDL definition of enum "IFHXBypassState"
 *	@author JacORB IDL compiler 
 */

public final class IFHXBypassState
	implements org.omg.CORBA.portable.IDLEntity
{
	private int value = -1;
	public static final int _bypass = 0;
	public static final IFHXBypassState bypass = new IFHXBypassState(_bypass);
	public static final int _flowthrough = 1;
	public static final IFHXBypassState flowthrough = new IFHXBypassState(_flowthrough);
	public int value()
	{
		return value;
	}
	public static IFHXBypassState from_int(int value)
	{
		switch (value) {
			case _bypass: return bypass;
			case _flowthrough: return flowthrough;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected IFHXBypassState(int i)
	{
		value = i;
	}
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
