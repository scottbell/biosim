package com.traclabs.biosim.idl.simulation.framework;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "BioCommandListener"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class BioCommandListenerPOATie
	extends BioCommandListenerPOA
{
	private BioCommandListenerOperations _delegate;

	private POA _poa;
	public BioCommandListenerPOATie(BioCommandListenerOperations delegate)
	{
		_delegate = delegate;
	}
	public BioCommandListenerPOATie(BioCommandListenerOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public com.traclabs.biosim.idl.simulation.framework.BioCommandListener _this()
	{
		return com.traclabs.biosim.idl.simulation.framework.BioCommandListenerHelper.narrow(_this_object());
	}
	public com.traclabs.biosim.idl.simulation.framework.BioCommandListener _this(org.omg.CORBA.ORB orb)
	{
		return com.traclabs.biosim.idl.simulation.framework.BioCommandListenerHelper.narrow(_this_object(orb));
	}
	public BioCommandListenerOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(BioCommandListenerOperations delegate)
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
