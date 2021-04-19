package com.traclabs.biosim.idl.simulation.crew;

import org.omg.PortableServer.POA;

/**
 * Generated from IDL interface "Activity".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public class ActivityPOATie
	extends ActivityPOA
{
	private ActivityOperations _delegate;

	private POA _poa;
	public ActivityPOATie(ActivityOperations delegate)
	{
		_delegate = delegate;
	}
	public ActivityPOATie(ActivityOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public com.traclabs.biosim.idl.simulation.crew.Activity _this()
	{
		org.omg.CORBA.Object __o = _this_object() ;
		com.traclabs.biosim.idl.simulation.crew.Activity __r = com.traclabs.biosim.idl.simulation.crew.ActivityHelper.narrow(__o);
		return __r;
	}
	public com.traclabs.biosim.idl.simulation.crew.Activity _this(org.omg.CORBA.ORB orb)
	{
		org.omg.CORBA.Object __o = _this_object(orb) ;
		com.traclabs.biosim.idl.simulation.crew.Activity __r = com.traclabs.biosim.idl.simulation.crew.ActivityHelper.narrow(__o);
		return __r;
	}
	public ActivityOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(ActivityOperations delegate)
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

	public int getTimeLength()
	{
		return _delegate.getTimeLength();
	}

	public java.lang.String getName()
	{
		return _delegate.getName();
	}

}
