package com.traclabs.biosim.idl.simulation.air.cdrs;
/**
 *	Generated from IDL definition of enum "CDRSValveState"
 *	@author JacORB IDL compiler 
 */

public final class CDRSValveState
	implements org.omg.CORBA.portable.IDLEntity
{
	private int value = -1;
	public static final int _open = 0;
	public static final CDRSValveState open = new CDRSValveState(_open);
	public static final int _closed = 1;
	public static final CDRSValveState closed = new CDRSValveState(_closed);
	public int value()
	{
		return value;
	}
	public static CDRSValveState from_int(int value)
	{
		switch (value) {
			case _open: return open;
			case _closed: return closed;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected CDRSValveState(int i)
	{
		value = i;
	}
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
