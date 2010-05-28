package com.traclabs.biosim.idl.simulation.food;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "Shelf"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class ShelfPOATie
	extends ShelfPOA
{
	private ShelfOperations _delegate;

	private POA _poa;
	public ShelfPOATie(ShelfOperations delegate)
	{
		_delegate = delegate;
	}
	public ShelfPOATie(ShelfOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public com.traclabs.biosim.idl.simulation.food.Shelf _this()
	{
		return com.traclabs.biosim.idl.simulation.food.ShelfHelper.narrow(_this_object());
	}
	public com.traclabs.biosim.idl.simulation.food.Shelf _this(org.omg.CORBA.ORB orb)
	{
		return com.traclabs.biosim.idl.simulation.food.ShelfHelper.narrow(_this_object(orb));
	}
	public ShelfOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(ShelfOperations delegate)
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
	public void setStartTick(int tick)
	{
_delegate.setStartTick(tick);
	}

	public com.traclabs.biosim.idl.simulation.food.Plant getPlant()
	{
		return _delegate.getPlant();
	}

	public void replant(com.traclabs.biosim.idl.simulation.food.PlantType pType, float area)
	{
_delegate.replant(pType,area);
	}

	public void harvest()
	{
_delegate.harvest();
	}

	public boolean isDead()
	{
		return _delegate.isDead();
	}

	public void kill()
	{
_delegate.kill();
	}

	public com.traclabs.biosim.idl.simulation.food.BiomassPS getBiomassPS()
	{
		return _delegate.getBiomassPS();
	}

	public float getHarvestInterval()
	{
		return _delegate.getHarvestInterval();
	}

	public java.lang.String getCropTypeString()
	{
		return _delegate.getCropTypeString();
	}

	public com.traclabs.biosim.idl.simulation.food.PlantType getCropType()
	{
		return _delegate.getCropType();
	}

	public boolean isReadyForHavest()
	{
		return _delegate.isReadyForHavest();
	}

	public float getCropAreaUsed()
	{
		return _delegate.getCropAreaUsed();
	}

	public float getTimeTillCanopyClosure()
	{
		return _delegate.getTimeTillCanopyClosure();
	}

	public float getCropAreaTotal()
	{
		return _delegate.getCropAreaTotal();
	}

}
