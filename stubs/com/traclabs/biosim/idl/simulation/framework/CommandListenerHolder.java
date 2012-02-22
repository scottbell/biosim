package com.traclabs.biosim.idl.simulation.framework;

/**
 *	Generated from IDL interface "CommandListener"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public final class CommandListenerHolder	implements org.omg.CORBA.portable.Streamable{
	 public CommandListener value;
	public CommandListenerHolder()
	{
	}
	public CommandListenerHolder (final CommandListener initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return CommandListenerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = CommandListenerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		CommandListenerHelper.write (_out,value);
	}
}
