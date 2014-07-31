package com.traclabs.biosim.idl.simulation.food;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "FoodProcessor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class FoodProcessorPOATie
	extends FoodProcessorPOA
{
	private FoodProcessorOperations _delegate;

	private POA _poa;
	public FoodProcessorPOATie(FoodProcessorOperations delegate)
	{
		_delegate = delegate;
	}
	public FoodProcessorPOATie(FoodProcessorOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public com.traclabs.biosim.idl.simulation.food.FoodProcessor _this()
	{
		return com.traclabs.biosim.idl.simulation.food.FoodProcessorHelper.narrow(_this_object());
	}
	public com.traclabs.biosim.idl.simulation.food.FoodProcessor _this(org.omg.CORBA.ORB orb)
	{
		return com.traclabs.biosim.idl.simulation.food.FoodProcessorHelper.narrow(_this_object(orb));
	}
	public FoodProcessorOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(FoodProcessorOperations delegate)
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
	public float getTickLength()
	{
		return _delegate.getTickLength();
	}

	public void registerBioCommandListener(com.traclabs.biosim.idl.simulation.framework.BioCommandListener listener)
	{
_delegate.registerBioCommandListener(listener);
	}

	public com.traclabs.biosim.idl.simulation.waste.DryWasteProducerDefinition getDryWasteProducerDefinition()
	{
		return _delegate.getDryWasteProducerDefinition();
	}

	public void setLogLevel(com.traclabs.biosim.idl.framework.LogLevel pLogLevel)
	{
_delegate.setLogLevel(pLogLevel);
	}

	public void clearMalfunction(long id)
	{
_delegate.clearMalfunction(id);
	}

	public void notifyCommandSent(java.lang.String commandName)
	{
_delegate.notifyCommandSent(commandName);
	}

	public boolean isMalfunctioning()
	{
		return _delegate.isMalfunctioning();
	}

	public void clearAllMalfunctions()
	{
_delegate.clearAllMalfunctions();
	}

	public void setEnableFailure(boolean pValue)
	{
_delegate.setEnableFailure(pValue);
	}

	public float randomFilter(float preFilteredValue)
	{
		return _delegate.randomFilter(preFilteredValue);
	}

	public void reset()
	{
_delegate.reset();
	}

	public int getMyTicks()
	{
		return _delegate.getMyTicks();
	}

	public com.traclabs.biosim.idl.simulation.food.FoodProducerDefinition getFoodProducerDefinition()
	{
		return _delegate.getFoodProducerDefinition();
	}

	public void maintain()
	{
_delegate.maintain();
	}

	public com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinition getPowerConsumerDefinition()
	{
		return _delegate.getPowerConsumerDefinition();
	}

	public void doSomeRepairWork(long id)
	{
_delegate.doSomeRepairWork(id);
	}

	public int getID()
	{
		return _delegate.getID();
	}

	public void scheduleMalfunction(com.traclabs.biosim.idl.framework.MalfunctionIntensity pIntensity, com.traclabs.biosim.idl.framework.MalfunctionLength pLength, int pTickToMalfunction)
	{
_delegate.scheduleMalfunction(pIntensity,pLength,pTickToMalfunction);
	}

	public java.lang.String getModuleName()
	{
		return _delegate.getModuleName();
	}

	public void tick()
	{
_delegate.tick();
	}

	public com.traclabs.biosim.idl.simulation.food.BiomassConsumerDefinition getBiomassConsumerDefinition()
	{
		return _delegate.getBiomassConsumerDefinition();
	}

	public void fixMalfunction(long id)
	{
_delegate.fixMalfunction(id);
	}

	public java.lang.String[] getMalfunctionNames()
	{
		return _delegate.getMalfunctionNames();
	}

	public float getBiomassConsumed()
	{
		return _delegate.getBiomassConsumed();
	}

	public void setTickLength(float pInterval)
	{
_delegate.setTickLength(pInterval);
	}

	public boolean isFailureEnabled()
	{
		return _delegate.isFailureEnabled();
	}

	public com.traclabs.biosim.idl.framework.Malfunction startMalfunction(com.traclabs.biosim.idl.framework.MalfunctionIntensity pIntensity, com.traclabs.biosim.idl.framework.MalfunctionLength pLength)
	{
		return _delegate.startMalfunction(pIntensity,pLength);
	}

	public float getFoodProduced()
	{
		return _delegate.getFoodProduced();
	}

	public boolean hasPower()
	{
		return _delegate.hasPower();
	}

	public boolean hasBiomass()
	{
		return _delegate.hasBiomass();
	}

	public void log()
	{
_delegate.log();
	}

	public com.traclabs.biosim.idl.framework.Malfunction[] getMalfunctions()
	{
		return _delegate.getMalfunctions();
	}

	public void fixAllMalfunctions()
	{
_delegate.fixAllMalfunctions();
	}

	public float getPowerConsumed()
	{
		return _delegate.getPowerConsumed();
	}

}
