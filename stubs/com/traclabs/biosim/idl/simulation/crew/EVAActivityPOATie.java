package com.traclabs.biosim.idl.simulation.crew;

import org.omg.PortableServer.POA;

/**
 * Generated from IDL interface "EVAActivity".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
		org.omg.CORBA.Object __o = _this_object() ;
		com.traclabs.biosim.idl.simulation.crew.EVAActivity __r = com.traclabs.biosim.idl.simulation.crew.EVAActivityHelper.narrow(__o);
		return __r;
	}
	public com.traclabs.biosim.idl.simulation.crew.EVAActivity _this(org.omg.CORBA.ORB orb)
	{
		org.omg.CORBA.Object __o = _this_object(orb) ;
		com.traclabs.biosim.idl.simulation.crew.EVAActivity __r = com.traclabs.biosim.idl.simulation.crew.EVAActivityHelper.narrow(__o);
		return __r;
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
		return super._default_POA();
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
