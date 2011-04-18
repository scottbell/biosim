package com.traclabs.biosim.idl.simulation.food;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "Plant"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
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
		return com.traclabs.biosim.idl.simulation.food.PlantHelper.narrow(_this_object());
	}
	public com.traclabs.biosim.idl.simulation.food.Plant _this(org.omg.CORBA.ORB orb)
	{
		return com.traclabs.biosim.idl.simulation.food.PlantHelper.narrow(_this_object(orb));
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
		else
		{
			return super._default_POA();
		}
	}
	public float getMolesOfCO2Inhaled()
	{
		return _delegate.getMolesOfCO2Inhaled();
	}

}
