package com.traclabs.biosim.idl.simulation.power;
/**
 *	Generated from IDL definition of enum "RPCMArmedStatus"
 *	@author JacORB IDL compiler 
 */

public final class RPCMArmedStatus
	implements org.omg.CORBA.portable.IDLEntity
{
	private int value = -1;
	public static final int _enabled = 0;
	public static final RPCMArmedStatus enabled = new RPCMArmedStatus(_enabled);
	public static final int _inhibited = 1;
	public static final RPCMArmedStatus inhibited = new RPCMArmedStatus(_inhibited);
	public int value()
	{
		return value;
	}
	public static RPCMArmedStatus from_int(int value)
	{
		switch (value) {
			case _enabled: return enabled;
			case _inhibited: return inhibited;
			default: throw new org.omg.CORBA.BAD_PARAM();
		}
	}
	protected RPCMArmedStatus(int i)
	{
		value = i;
	}
	java.lang.Object readResolve()
	throws java.io.ObjectStreamException
	{
		return from_int(value());
	}
}
