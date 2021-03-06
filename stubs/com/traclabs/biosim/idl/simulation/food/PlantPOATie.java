package com.traclabs.biosim.idl.simulation.food;

import org.omg.PortableServer.POA;

/**
 * Generated from IDL interface "Plant".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public class PlantPOATie
	extends PlantPOA
{
	private PlantOperations _delegate;

	private POA _poa;
	public PlantPOATie(PlantOperations delegate)
	{
		_delegate = delegate;
	}
	public PlantPOATie(PlantOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public com.traclabs.biosim.idl.simulation.food.Plant _this()
	{
		org.omg.CORBA.Object __o = _this_object() ;
		com.traclabs.biosim.idl.simulation.food.Plant __r = com.traclabs.biosim.idl.simulation.food.PlantHelper.narrow(__o);
		return __r;
	}
	public com.traclabs.biosim.idl.simulation.food.Plant _this(org.omg.CORBA.ORB orb)
	{
		org.omg.CORBA.Object __o = _this_object(orb) ;
		com.traclabs.biosim.idl.simulation.food.Plant __r = com.traclabs.biosim.idl.simulation.food.PlantHelper.narrow(__o);
		return __r;
	}
	public PlantOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(PlantOperations delegate)
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
	public float getMolesOfCO2Inhaled()
	{
		return _delegate.getMolesOfCO2Inhaled();
	}

}
