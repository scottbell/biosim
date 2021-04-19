package com.traclabs.biosim.idl.simulation.power;
/**
 * Generated from IDL enum "RPCMSwitchState".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
