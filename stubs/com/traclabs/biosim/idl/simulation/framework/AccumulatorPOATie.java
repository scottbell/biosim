package com.traclabs.biosim.idl.simulation.framework;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "Accumulator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class AccumulatorPOATie
	extends AccumulatorPOA
{
	private AccumulatorOperations _delegate;

	private POA _poa;
	public AccumulatorPOATie(AccumulatorOperations delegate)
	{
		_delegate = delegate;
	}
	public AccumulatorPOATie(AccumulatorOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public com.traclabs.biosim.idl.simulation.framework.Accumulator _this()
	{
		return com.traclabs.biosim.idl.simulation.framework.AccumulatorHelper.narrow(_this_object());
	}
	public com.traclabs.biosim.idl.simulation.framework.Accumulator _this(org.omg.CORBA.ORB orb)
	{
		return com.traclabs.biosim.idl.simulation.framework.AccumulatorHelper.narrow(_this_object(orb));
	}
	public AccumulatorOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(AccumulatorOperations delegate)
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
	public void clearMalfunction(long id)
	{
_delegate.clearMalfunction(id);
	}

	public com.traclabs.biosim.idl.simulation.air.NitrogenProducerDefinition getNitrogenProducerDefinition()
	{
		return _delegate.getNitrogenProducerDefinition();
	}

	public float getTickLength()
	{
		return _delegate.getTickLength();
	}

	public void fixAllMalfunctions()
	{
_delegate.fixAllMalfunctions();
	}

	public void setLogLevel(com.traclabs.biosim.idl.framework.LogLevel pLogLevel)
	{
_delegate.setLogLevel(pLogLevel);
	}

	public com.traclabs.biosim.idl.simulation.water.GreyWaterProducerDefinition getGreyWaterProducerDefinition()
	{
		return _delegate.getGreyWaterProducerDefinition();
	}

	public com.traclabs.biosim.idl.framework.Malfunction startMalfunction(com.traclabs.biosim.idl.framework.MalfunctionIntensity pIntensity, com.traclabs.biosim.idl.framework.MalfunctionLength pLength)
	{
		return _delegate.startMalfunction(pIntensity,pLength);
	}

	public com.traclabs.biosim.idl.simulation.water.DirtyWaterConsumerDefinition getDirtyWaterConsumerDefinition()
	{
		return _delegate.getDirtyWaterConsumerDefinition();
	}

	public void notifyCommandSent(java.lang.String commandName)
	{
_delegate.notifyCommandSent(commandName);
	}

	public com.traclabs.biosim.idl.simulation.food.BiomassProducerDefinition getBiomassProducerDefinition()
	{
		return _delegate.getBiomassProducerDefinition();
	}

	public int getID()
	{
		return _delegate.getID();
	}

	public com.traclabs.biosim.idl.simulation.food.FoodProducerDefinition getFoodProducerDefinition()
	{
		return _delegate.getFoodProducerDefinition();
	}

	public com.traclabs.biosim.idl.simulation.waste.DryWasteProducerDefinition getDryWasteProducerDefinition()
	{
		return _delegate.getDryWasteProducerDefinition();
	}

	public com.traclabs.biosim.idl.simulation.air.NitrogenConsumerDefinition getNitrogenConsumerDefinition()
	{
		return _delegate.getNitrogenConsumerDefinition();
	}

	public void fixMalfunction(long id)
	{
_delegate.fixMalfunction(id);
	}

	public com.traclabs.biosim.idl.simulation.water.GreyWaterConsumerDefinition getGreyWaterConsumerDefinition()
	{
		return _delegate.getGreyWaterConsumerDefinition();
	}

	public com.traclabs.biosim.idl.simulation.water.PotableWaterProducerDefinition getPotableWaterProducerDefinition()
	{
		return _delegate.getPotableWaterProducerDefinition();
	}

	public int getMyTicks()
	{
		return _delegate.getMyTicks();
	}

	public com.traclabs.biosim.idl.framework.Malfunction[] getMalfunctions()
	{
		return _delegate.getMalfunctions();
	}

	public com.traclabs.biosim.idl.simulation.food.BiomassConsumerDefinition getBiomassConsumerDefinition()
	{
		return _delegate.getBiomassConsumerDefinition();
	}

	public com.traclabs.biosim.idl.simulation.food.FoodConsumerDefinition getFoodConsumerDefinition()
	{
		return _delegate.getFoodConsumerDefinition();
	}

	public com.traclabs.biosim.idl.simulation.waste.DryWasteConsumerDefinition getDryWasteConsumerDefinition()
	{
		return _delegate.getDryWasteConsumerDefinition();
	}

	public void reset()
	{
_delegate.reset();
	}

	public void registerBioCommandListener(com.traclabs.biosim.idl.simulation.framework.BioCommandListener listener)
	{
_delegate.registerBioCommandListener(listener);
	}

	public java.lang.String getModuleName()
	{
		return _delegate.getModuleName();
	}

	public float randomFilter(float preFilteredValue)
	{
		return _delegate.randomFilter(preFilteredValue);
	}

	public boolean isMalfunctioning()
	{
		return _delegate.isMalfunctioning();
	}

	public com.traclabs.biosim.idl.simulation.water.PotableWaterConsumerDefinition getPotableWaterConsumerDefinition()
	{
		return _delegate.getPotableWaterConsumerDefinition();
	}

	public void log()
	{
_delegate.log();
	}

	public com.traclabs.biosim.idl.simulation.environment.AirProducerDefinition getAirProducerDefinition()
	{
		return _delegate.getAirProducerDefinition();
	}

	public void maintain()
	{
_delegate.maintain();
	}

	public com.traclabs.biosim.idl.simulation.power.PowerProducerDefinition getPowerProducerDefinition()
	{
		return _delegate.getPowerProducerDefinition();
	}

	public com.traclabs.biosim.idl.simulation.air.O2ProducerDefinition getO2ProducerDefinition()
	{
		return _delegate.getO2ProducerDefinition();
	}

	public com.traclabs.biosim.idl.simulation.air.CO2ProducerDefinition getCO2ProducerDefinition()
	{
		return _delegate.getCO2ProducerDefinition();
	}

	public void doSomeRepairWork(long id)
	{
_delegate.doSomeRepairWork(id);
	}

	public void setEnableFailure(boolean pValue)
	{
_delegate.setEnableFailure(pValue);
	}

	public com.traclabs.biosim.idl.simulation.environment.AirConsumerDefinition getAirConsumerDefinition()
	{
		return _delegate.getAirConsumerDefinition();
	}

	public void clearAllMalfunctions()
	{
_delegate.clearAllMalfunctions();
	}

	public com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinition getPowerConsumerDefinition()
	{
		return _delegate.getPowerConsumerDefinition();
	}

	public com.traclabs.biosim.idl.simulation.air.O2ConsumerDefinition getO2ConsumerDefinition()
	{
		return _delegate.getO2ConsumerDefinition();
	}

	public void tick()
	{
_delegate.tick();
	}

	public void setTickLength(float pInterval)
	{
_delegate.setTickLength(pInterval);
	}

	public com.traclabs.biosim.idl.simulation.water.DirtyWaterProducerDefinition getDirtyWaterProducerDefinition()
	{
		return _delegate.getDirtyWaterProducerDefinition();
	}

	public com.traclabs.biosim.idl.simulation.air.CO2ConsumerDefinition getCO2ConsumerDefinition()
	{
		return _delegate.getCO2ConsumerDefinition();
	}

	public boolean isFailureEnabled()
	{
		return _delegate.isFailureEnabled();
	}

	public java.lang.String[] getMalfunctionNames()
	{
		return _delegate.getMalfunctionNames();
	}

	public void scheduleMalfunction(com.traclabs.biosim.idl.framework.MalfunctionIntensity pIntensity, com.traclabs.biosim.idl.framework.MalfunctionLength pLength, int pTickToMalfunction)
	{
_delegate.scheduleMalfunction(pIntensity,pLength,pTickToMalfunction);
	}

}
