package com.traclabs.biosim.idl.simulation.power;
/**
 *	Generated from IDL definition of enum "RPCMSwitchState"
 *	@author JacORB IDL compiler 
 */

public final class RPCMSwitchStateHolder
	implements org.omg.CORBA.portable.Streamable
{
	public RPCMSwitchState value;

	public RPCMSwitchStateHolder ()
	{
	}
	public RPCMSwitchStateHolder (final RPCMSwitchState initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return RPCMSwitchStateHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = RPCMSwitchStateHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		RPCMSwitchStateHelper.write (out,value);
	}
}
