package com.traclabs.biosim.idl.simulation.thermal;
/**
 *	Generated from IDL definition of enum "IFHXValveState"
 *	@author JacORB IDL compiler 
 */

public final class IFHXValveState
	implements org.omg.CORBA.portable.IDLEntity
{
	private int value = -1;
	public static final int _open = 0;
	public static final IFHXValveState open = new IFHXValveState(_open);
	public static final int _closed = 1;
	public static final IFHXValveState closed = new IFHXValveState(_closed);
	public int value()
	{
		return value;
	}
	public static IFHXValveState from_int(int value)
	{
		switch (value) {
			case _open: return open;
			case _closed: return closed;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected IFHXValveState(int i)
	{
		value = i;
	}
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
