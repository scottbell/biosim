package com.traclabs.biosim.idl.simulation.power;
/**
 * Generated from IDL enum "RPCMArmedStatus".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
