package com.traclabs.biosim.idl.simulation.framework;

import org.omg.PortableServer.POA;

/**
 * Generated from IDL interface "BioCommandListener".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
		org.omg.CORBA.Object __o = _this_object() ;
		com.traclabs.biosim.idl.simulation.framework.BioCommandListener __r = com.traclabs.biosim.idl.simulation.framework.BioCommandListenerHelper.narrow(__o);
		return __r;
	}
	public com.traclabs.biosim.idl.simulation.framework.BioCommandListener _this(org.omg.CORBA.ORB orb)
	{
		org.omg.CORBA.Object __o = _this_object(orb) ;
		com.traclabs.biosim.idl.simulation.framework.BioCommandListener __r = com.traclabs.biosim.idl.simulation.framework.BioCommandListenerHelper.narrow(__o);
		return __r;
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
		return super._default_POA();
	}
	public void newCommandSent(java.lang.String commandName)
	{
_delegate.newCommandSent(commandName);
	}

}
