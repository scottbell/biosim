package com.traclabs.biosim.idl.simulation.food;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "BiomassPS"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class BiomassPSPOATie
	extends BiomassPSPOA
{
	private BiomassPSOperations _delegate;

	private POA _poa;
	public BiomassPSPOATie(BiomassPSOperations delegate)
	{
		_delegate = delegate;
	}
	public BiomassPSPOATie(BiomassPSOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public com.traclabs.biosim.idl.simulation.food.BiomassPS _this()
	{
		return com.traclabs.biosim.idl.simulation.food.BiomassPSHelper.narrow(_this_object());
	}
	public com.traclabs.biosim.idl.simulation.food.BiomassPS _this(org.omg.CORBA.ORB orb)
	{
		return com.traclabs.biosim.idl.simulation.food.BiomassPSHelper.narrow(_this_object(orb));
	}
	public BiomassPSOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(BiomassPSOperations delegate)
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

	public com.traclabs.biosim.idl.framework.Malfunction startMalfunction(com.traclabs.biosim.idl.framework.MalfunctionIntensity pIntensity, com.traclabs.biosim.idl.framework.MalfunctionLength pLength)
	{
		return _delegate.startMalfunction(pIntensity,pLength);
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

	public void clearShelves()
	{
_delegate.clearShelves();
	}

	public void killPlants()
	{
_delegate.killPlants();
	}

	public void fixMalfunction(long id)
	{
_delegate.fixMalfunction(id);
	}

	public com.traclabs.biosim.idl.simulation.water.GreyWaterConsumerDefinition getGreyWaterConsumerDefinition()
	{
		return _delegate.getGreyWaterConsumerDefinition();
	}

	public int getMyTicks()
	{
		return _delegate.getMyTicks();
	}

	public com.traclabs.biosim.idl.framework.Malfunction[] getMalfunctions()
	{
		return _delegate.getMalfunctions();
	}

	public com.traclabs.biosim.idl.simulation.food.Shelf createNewShelf(com.traclabs.biosim.idl.simulation.food.PlantType pType, float pCropArea, int pStartTick)
	{
		return _delegate.createNewShelf(pType,pCropArea,pStartTick);
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

	public com.traclabs.biosim.idl.simulation.food.Shelf[] getShelves()
	{
		return _delegate.getShelves();
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

	public void setDeathEnabled(boolean deathEnabled)
	{
_delegate.setDeathEnabled(deathEnabled);
	}

	public boolean isAnyPlantDead()
	{
		return _delegate.isAnyPlantDead();
	}

	public void setAutoHarvestAndReplantEnabled(boolean pHarvestEnabled)
	{
_delegate.setAutoHarvestAndReplantEnabled(pHarvestEnabled);
	}

	public com.traclabs.biosim.idl.simulation.food.Shelf getShelf(int index)
	{
		return _delegate.getShelf(index);
	}

	public boolean getDeathEnabled()
	{
		return _delegate.getDeathEnabled();
	}

	public void doSomeRepairWork(long id)
	{
_delegate.doSomeRepairWork(id);
	}

	public com.traclabs.biosim.idl.simulation.environment.AirConsumerDefinition getAirConsumerDefinition()
	{
		return _delegate.getAirConsumerDefinition();
	}

	public void setEnableFailure(boolean pValue)
	{
_delegate.setEnableFailure(pValue);
	}

	public void clearAllMalfunctions()
	{
_delegate.clearAllMalfunctions();
	}

	public boolean autoHarvestAndReplantEnabled()
	{
		return _delegate.autoHarvestAndReplantEnabled();
	}

	public com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinition getPowerConsumerDefinition()
	{
		return _delegate.getPowerConsumerDefinition();
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
