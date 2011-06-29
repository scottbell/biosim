package com.traclabs.biosim.idl.simulation.power;
/**
 *	Generated from IDL definition of enum "RPCMSwitchState"
 *	@author JacORB IDL compiler 
 */

public final class RPCMSwitchState
	implements org.omg.CORBA.portable.IDLEntity
{
	private int value = -1;
	public static final int _open = 0;
	public static final RPCMSwitchState open = new RPCMSwitchState(_open);
	public static final int _closed = 1;
	public static final RPCMSwitchState closed = new RPCMSwitchState(_closed);
	public int value()
	{
		return value;
	}
	public static RPCMSwitchState from_int(int value)
	{
		switch (value) {
			case _open: return open;
			case _closed: return closed;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected RPCMSwitchState(int i)
	{
		value = i;
	}
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
