package com.traclabs.biosim.idl.framework;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "TechSpecificInfo"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class TechSpecificInfoPOATie
	extends TechSpecificInfoPOA
{
	private TechSpecificInfoOperations _delegate;

	private POA _poa;
	public TechSpecificInfoPOATie(TechSpecificInfoOperations delegate)
	{
		_delegate = delegate;
	}
	public TechSpecificInfoPOATie(TechSpecificInfoOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public com.traclabs.biosim.idl.framework.TechSpecificInfo _this()
	{
		return com.traclabs.biosim.idl.framework.TechSpecificInfoHelper.narrow(_this_object());
	}
	public com.traclabs.biosim.idl.framework.TechSpecificInfo _this(org.omg.CORBA.ORB orb)
	{
		return com.traclabs.biosim.idl.framework.TechSpecificInfoHelper.narrow(_this_object(orb));
	}
	public TechSpecificInfoOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(TechSpecificInfoOperations delegate)
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
	public java.lang.String print()
	{
		return _delegate.print();
	}

}
