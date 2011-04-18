package com.traclabs.biosim.idl.simulation.crew;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "RepairActivity"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class RepairActivityPOATie
	extends RepairActivityPOA
{
	private RepairActivityOperations _delegate;

	private POA _poa;
	public RepairActivityPOATie(RepairActivityOperations delegate)
	{
		_delegate = delegate;
	}
	public RepairActivityPOATie(RepairActivityOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public com.traclabs.biosim.idl.simulation.crew.RepairActivity _this()
	{
		return com.traclabs.biosim.idl.simulation.crew.RepairActivityHelper.narrow(_this_object());
	}
	public com.traclabs.biosim.idl.simulation.crew.RepairActivity _this(org.omg.CORBA.ORB orb)
	{
		return com.traclabs.biosim.idl.simulation.crew.RepairActivityHelper.narrow(_this_object(orb));
	}
	public RepairActivityOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(RepairActivityOperations delegate)
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
	public java.lang.String getModuleNameToRepair()
	{
		return _delegate.getModuleNameToRepair();
	}

	public long getMalfunctionIDToRepair()
	{
		return _delegate.getMalfunctionIDToRepair();
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
