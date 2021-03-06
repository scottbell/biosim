package com.traclabs.biosim.idl.simulation.crew;

import org.omg.PortableServer.POA;

/**
 * Generated from IDL interface "MaitenanceActivity".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public class MaitenanceActivityPOATie
	extends MaitenanceActivityPOA
{
	private MaitenanceActivityOperations _delegate;

	private POA _poa;
	public MaitenanceActivityPOATie(MaitenanceActivityOperations delegate)
	{
		_delegate = delegate;
	}
	public MaitenanceActivityPOATie(MaitenanceActivityOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public com.traclabs.biosim.idl.simulation.crew.MaitenanceActivity _this()
	{
		org.omg.CORBA.Object __o = _this_object() ;
		com.traclabs.biosim.idl.simulation.crew.MaitenanceActivity __r = com.traclabs.biosim.idl.simulation.crew.MaitenanceActivityHelper.narrow(__o);
		return __r;
	}
	public com.traclabs.biosim.idl.simulation.crew.MaitenanceActivity _this(org.omg.CORBA.ORB orb)
	{
		org.omg.CORBA.Object __o = _this_object(orb) ;
		com.traclabs.biosim.idl.simulation.crew.MaitenanceActivity __r = com.traclabs.biosim.idl.simulation.crew.MaitenanceActivityHelper.narrow(__o);
		return __r;
	}
	public MaitenanceActivityOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(MaitenanceActivityOperations delegate)
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
	public int getActivityIntensity()
	{
		return _delegate.getActivityIntensity();
	}

	public java.lang.String getModuleNameToMaintain()
	{
		return _delegate.getModuleNameToMaintain();
	}

	public int getTimeLength()
	{
		return _delegate.getTimeLength();
	}

	public java.lang.String getName()
	{
		return _delegate.getName();
	}

}
