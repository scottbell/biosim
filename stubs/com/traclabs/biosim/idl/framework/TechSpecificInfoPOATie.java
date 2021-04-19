package com.traclabs.biosim.idl.framework;

import org.omg.PortableServer.POA;

/**
 * Generated from IDL interface "TechSpecificInfo".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
		org.omg.CORBA.Object __o = _this_object() ;
		com.traclabs.biosim.idl.framework.TechSpecificInfo __r = com.traclabs.biosim.idl.framework.TechSpecificInfoHelper.narrow(__o);
		return __r;
	}
	public com.traclabs.biosim.idl.framework.TechSpecificInfo _this(org.omg.CORBA.ORB orb)
	{
		org.omg.CORBA.Object __o = _this_object(orb) ;
		com.traclabs.biosim.idl.framework.TechSpecificInfo __r = com.traclabs.biosim.idl.framework.TechSpecificInfoHelper.narrow(__o);
		return __r;
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
		return super._default_POA();
	}
	public java.lang.String print()
	{
		return _delegate.print();
	}

}
