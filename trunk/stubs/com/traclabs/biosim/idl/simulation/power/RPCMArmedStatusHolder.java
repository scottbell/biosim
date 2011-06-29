package com.traclabs.biosim.idl.simulation.power;
/**
 *	Generated from IDL definition of enum "RPCMArmedStatus"
 *	@author JacORB IDL compiler 
 */

public final class RPCMArmedStatusHolder
	implements org.omg.CORBA.portable.Streamable
{
	public RPCMArmedStatus value;

	public RPCMArmedStatusHolder ()
	{
	}
	public RPCMArmedStatusHolder (final RPCMArmedStatus initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return RPCMArmedStatusHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = RPCMArmedStatusHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		RPCMArmedStatusHelper.write (out,value);
	}
}
