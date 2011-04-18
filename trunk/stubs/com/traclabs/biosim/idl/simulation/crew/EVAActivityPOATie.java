package com.traclabs.biosim.idl.simulation.crew;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "EVAActivity"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class EVAActivityPOATie
	extends EVAActivityPOA
{
	private EVAActivityOperations _delegate;

	private POA _poa;
	public EVAActivityPOATie(EVAActivityOperations delegate)
	{
		_delegate = delegate;
	}
	public EVAActivityPOATie(EVAActivityOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public com.traclabs.biosim.idl.simulation.crew.EVAActivity _this()
	{
		return com.traclabs.biosim.idl.simulation.crew.EVAActivityHelper.narrow(_this_object());
	}
	public com.traclabs.biosim.idl.simulation.crew.EVAActivity _this(org.omg.CORBA.ORB orb)
	{
		return com.traclabs.biosim.idl.simulation.crew.EVAActivityHelper.narrow(_this_object(orb));
	}
	public EVAActivityOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(EVAActivityOperations delegate)
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
	public java.lang.String getEVACrewGroupName()
	{
		return _delegate.getEVACrewGroupName();
	}

	public int getActivityIntensity()
	{
		return _delegate.getActivityIntensity();
	}

	public java.lang.String getBaseCrewGroupName()
	{
		return _delegate.getBaseCrewGroupName();
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
