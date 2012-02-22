package com.traclabs.biosim.idl.simulation.framework;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "CommandListener"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class CommandListenerPOATie
	extends CommandListenerPOA
{
	private CommandListenerOperations _delegate;

	private POA _poa;
	public CommandListenerPOATie(CommandListenerOperations delegate)
	{
		_delegate = delegate;
	}
	public CommandListenerPOATie(CommandListenerOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public com.traclabs.biosim.idl.simulation.framework.CommandListener _this()
	{
		return com.traclabs.biosim.idl.simulation.framework.CommandListenerHelper.narrow(_this_object());
	}
	public com.traclabs.biosim.idl.simulation.framework.CommandListener _this(org.omg.CORBA.ORB orb)
	{
		return com.traclabs.biosim.idl.simulation.framework.CommandListenerHelper.narrow(_this_object(orb));
	}
	public CommandListenerOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(CommandListenerOperations delegate)
	{
		_delegate = delegate;
	}
	public POA _default_POA()
	{
		if (_poa != null)
		{
			return _poa;
		}
		else
		{
			return super._default_POA();
		}
	}
	public void newCommandSent(java.lang.String commandName)
	{
_delegate.newCommandSent(commandName);
	}

}
