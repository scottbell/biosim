package com.traclabs.biosim.idl.simulation.crew;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "CrewGroup"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class CrewGroupPOATie
	extends CrewGroupPOA
{
	private CrewGroupOperations _delegate;

	private POA _poa;
	public CrewGroupPOATie(CrewGroupOperations delegate)
	{
		_delegate = delegate;
	}
	public CrewGroupPOATie(CrewGroupOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public com.traclabs.biosim.idl.simulation.crew.CrewGroup _this()
	{
		return com.traclabs.biosim.idl.simulation.crew.CrewGroupHelper.narrow(_this_object());
	}
	public com.traclabs.biosim.idl.simulation.crew.CrewGroup _this(org.omg.CORBA.ORB orb)
	{
		return com.traclabs.biosim.idl.simulation.crew.CrewGroupHelper.narrow(_this_object(orb));
	}
	public CrewGroupOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(CrewGroupOperations delegate)
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
	public float getProductivity()
	{
		return _delegate.getProductivity();
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

	public float getPotableWaterConsumed()
	{
		return _delegate.getPotableWaterConsumed();
	}

	public com.traclabs.biosim.idl.simulation.water.GreyWaterProducerDefinition getGreyWaterProducerDefinition()
	{
		return _delegate.getGreyWaterProducerDefinition();
	}

	public float getDirtyWaterProduced()
	{
		return _delegate.getDirtyWaterProduced();
	}

	public com.traclabs.biosim.idl.framework.Malfunction startMalfunction(com.traclabs.biosim.idl.framework.MalfunctionIntensity pIntensity, com.traclabs.biosim.idl.framework.MalfunctionLength pLength)
	{
		return _delegate.startMalfunction(pIntensity,pLength);
	}

	public void notifyCommandSent(java.lang.String commandName)
	{
_delegate.notifyCommandSent(commandName);
	}

	public int getID()
	{
		return _delegate.getID();
	}

	public com.traclabs.biosim.idl.simulation.waste.DryWasteProducerDefinition getDryWasteProducerDefinition()
	{
		return _delegate.getDryWasteProducerDefinition();
	}

	public void scheduleRepair(java.lang.String moduleName, long malfunctionID, int timeLength)
	{
_delegate.scheduleRepair(moduleName,malfunctionID,timeLength);
	}

	public void killCrew()
	{
_delegate.killCrew();
	}

	public com.traclabs.biosim.idl.simulation.crew.CrewPerson createCrewPerson(java.lang.String name, float age, float weight, com.traclabs.biosim.idl.simulation.crew.Sex pSex, int arrivalTick, int departureTick)
	{
		return _delegate.createCrewPerson(name,age,weight,pSex,arrivalTick,departureTick);
	}

	public void fixMalfunction(long id)
	{
_delegate.fixMalfunction(id);
	}

	public void attachCrewPerson(com.traclabs.biosim.idl.simulation.crew.CrewPerson pCrewPerson)
	{
_delegate.attachCrewPerson(pCrewPerson);
	}

	public int getMyTicks()
	{
		return _delegate.getMyTicks();
	}

	public float getGreyWaterProduced()
	{
		return _delegate.getGreyWaterProduced();
	}

	public float getO2Consumed()
	{
		return _delegate.getO2Consumed();
	}

	public boolean anyDead()
	{
		return _delegate.anyDead();
	}

	public com.traclabs.biosim.idl.framework.Malfunction[] getMalfunctions()
	{
		return _delegate.getMalfunctions();
	}

	public com.traclabs.biosim.idl.simulation.food.FoodConsumerDefinition getFoodConsumerDefinition()
	{
		return _delegate.getFoodConsumerDefinition();
	}

	public void reset()
	{
_delegate.reset();
	}

	public void registerBioCommandListener(com.traclabs.biosim.idl.simulation.framework.BioCommandListener listener)
	{
_delegate.registerBioCommandListener(listener);
	}

	public float getCO2Produced()
	{
		return _delegate.getCO2Produced();
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

	public float getFoodConsumed()
	{
		return _delegate.getFoodConsumed();
	}

	public void log()
	{
_delegate.log();
	}

	public void detachCrewPerson(java.lang.String name)
	{
_delegate.detachCrewPerson(name);
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

	public boolean isDead()
	{
		return _delegate.isDead();
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

	public com.traclabs.biosim.idl.simulation.crew.CrewPerson[] getCrewPeople()
	{
		return _delegate.getCrewPeople();
	}

	public void tick()
	{
_delegate.tick();
	}

	public void setTickLength(float pInterval)
	{
_delegate.setTickLength(pInterval);
	}

	public com.traclabs.biosim.idl.simulation.crew.CrewPerson getCrewPerson(java.lang.String name)
	{
		return _delegate.getCrewPerson(name);
	}

	public com.traclabs.biosim.idl.simulation.water.DirtyWaterProducerDefinition getDirtyWaterProducerDefinition()
	{
		return _delegate.getDirtyWaterProducerDefinition();
	}

	public int getCrewSize()
	{
		return _delegate.getCrewSize();
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
