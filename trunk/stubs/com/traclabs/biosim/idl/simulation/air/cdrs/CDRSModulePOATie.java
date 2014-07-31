package com.traclabs.biosim.idl.simulation.air.cdrs;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "CDRSModule"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class CDRSModulePOATie
	extends CDRSModulePOA
{
	private CDRSModuleOperations _delegate;

	private POA _poa;
	public CDRSModulePOATie(CDRSModuleOperations delegate)
	{
		_delegate = delegate;
	}
	public CDRSModulePOATie(CDRSModuleOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public com.traclabs.biosim.idl.simulation.air.cdrs.CDRSModule _this()
	{
		return com.traclabs.biosim.idl.simulation.air.cdrs.CDRSModuleHelper.narrow(_this_object());
	}
	public com.traclabs.biosim.idl.simulation.air.cdrs.CDRSModule _this(org.omg.CORBA.ORB orb)
	{
		return com.traclabs.biosim.idl.simulation.air.cdrs.CDRSModuleHelper.narrow(_this_object(orb));
	}
	public CDRSModuleOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(CDRSModuleOperations delegate)
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
	public com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatus getBlowerArmedStatus()
	{
		return _delegate.getBlowerArmedStatus();
	}

	public void setWaterPumpState(com.traclabs.biosim.idl.simulation.air.cdrs.CDRSPowerState state)
	{
_delegate.setWaterPumpState(state);
	}

	public com.traclabs.biosim.idl.simulation.air.cdrs.CDRSValveState getAirInletValveState()
	{
		return _delegate.getAirInletValveState();
	}

	public void clearMalfunction(long id)
	{
_delegate.clearMalfunction(id);
	}

	public void setAirInletValveArmedStatus(com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatus status)
	{
_delegate.setAirInletValveArmedStatus(status);
	}

	public void setCO2IsolationValveArmedStatus(com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatus status)
	{
_delegate.setCO2IsolationValveArmedStatus(status);
	}

	public com.traclabs.biosim.idl.simulation.air.cdrs.CDRSValveState getAirReturnValveState()
	{
		return _delegate.getAirReturnValveState();
	}

	public float getPrimaryHeatProduction()
	{
		return _delegate.getPrimaryHeatProduction();
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

	public com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatus getCO2VentValveArmedStatus()
	{
		return _delegate.getCO2VentValveArmedStatus();
	}

	public com.traclabs.biosim.idl.simulation.water.GreyWaterProducerDefinition getGreyWaterProducerDefinition()
	{
		return _delegate.getGreyWaterProducerDefinition();
	}

	public com.traclabs.biosim.idl.framework.Malfunction startMalfunction(com.traclabs.biosim.idl.framework.MalfunctionIntensity pIntensity, com.traclabs.biosim.idl.framework.MalfunctionLength pLength)
	{
		return _delegate.startMalfunction(pIntensity,pLength);
	}

	public void setAirInletValveState(com.traclabs.biosim.idl.simulation.air.cdrs.CDRSValveState state)
	{
_delegate.setAirInletValveState(state);
	}

	public void setCO2IsolationValveState(com.traclabs.biosim.idl.simulation.air.cdrs.CDRSValveState state)
	{
_delegate.setCO2IsolationValveState(state);
	}

	public void notifyCommandSent(java.lang.String commandName)
	{
_delegate.notifyCommandSent(commandName);
	}

	public int getID()
	{
		return _delegate.getID();
	}

	public void setBlowerArmedStatus(com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatus status)
	{
_delegate.setBlowerArmedStatus(status);
	}

	public void setBlowerState(com.traclabs.biosim.idl.simulation.air.cdrs.CDRSPowerState state)
	{
_delegate.setBlowerState(state);
	}

	public com.traclabs.biosim.idl.simulation.air.cdrs.CDRSValveState getCO2VentValveState()
	{
		return _delegate.getCO2VentValveState();
	}

	public com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatus getAirReturnValveArmedStatus()
	{
		return _delegate.getAirReturnValveArmedStatus();
	}

	public com.traclabs.biosim.idl.simulation.air.cdrs.CDRSPowerState getWaterPumpState()
	{
		return _delegate.getWaterPumpState();
	}

	public void setArmedStatus(com.traclabs.biosim.idl.simulation.air.cdrs.CDRSArmedStatus status)
	{
_delegate.setArmedStatus(status);
	}

	public void fixMalfunction(long id)
	{
_delegate.fixMalfunction(id);
	}

	public void setAirReturnValveArmedStatus(com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatus status)
	{
_delegate.setAirReturnValveArmedStatus(status);
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

	public com.traclabs.biosim.idl.simulation.air.cdrs.CDRSState getState()
	{
		return _delegate.getState();
	}

	public com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatus getAirInletValveArmedStatus()
	{
		return _delegate.getAirInletValveArmedStatus();
	}

	public void reset()
	{
_delegate.reset();
	}

	public void setState(com.traclabs.biosim.idl.simulation.air.cdrs.CDRSState state)
	{
_delegate.setState(state);
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

	public void log()
	{
_delegate.log();
	}

	public com.traclabs.biosim.idl.simulation.air.cdrs.CDRSValveState getCO2IsolationValveState()
	{
		return _delegate.getCO2IsolationValveState();
	}

	public com.traclabs.biosim.idl.simulation.environment.AirProducerDefinition getAirProducerDefinition()
	{
		return _delegate.getAirProducerDefinition();
	}

	public void maintain()
	{
_delegate.maintain();
	}

	public float getSecondaryHeatProduction()
	{
		return _delegate.getSecondaryHeatProduction();
	}

	public void setCO2VentValveArmedStatus(com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatus status)
	{
_delegate.setCO2VentValveArmedStatus(status);
	}

	public void setAirReturnValveState(com.traclabs.biosim.idl.simulation.air.cdrs.CDRSValveState state)
	{
_delegate.setAirReturnValveState(state);
	}

	public com.traclabs.biosim.idl.simulation.air.CO2ProducerDefinition getCO2ProducerDefinition()
	{
		return _delegate.getCO2ProducerDefinition();
	}

	public void doSomeRepairWork(long id)
	{
_delegate.doSomeRepairWork(id);
	}

	public void setDayNightState(com.traclabs.biosim.idl.simulation.air.cdrs.CDRSDayNightState status)
	{
_delegate.setDayNightState(status);
	}

	public com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatus getWaterPumpArmedStatus()
	{
		return _delegate.getWaterPumpArmedStatus();
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

	public com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinition getPowerConsumerDefinition()
	{
		return _delegate.getPowerConsumerDefinition();
	}

	public com.traclabs.biosim.idl.simulation.air.cdrs.CDRSArmedStatus getArmedStatus()
	{
		return _delegate.getArmedStatus();
	}

	public void tick()
	{
_delegate.tick();
	}

	public void setTickLength(float pInterval)
	{
_delegate.setTickLength(pInterval);
	}

	public com.traclabs.biosim.idl.simulation.air.cdrs.CDRSPowerState getBlowerState()
	{
		return _delegate.getBlowerState();
	}

	public com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatus getCO2IsolationValveArmedStatus()
	{
		return _delegate.getCO2IsolationValveArmedStatus();
	}

	public void setCO2VentValveState(com.traclabs.biosim.idl.simulation.air.cdrs.CDRSValveState state)
	{
_delegate.setCO2VentValveState(state);
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

	public com.traclabs.biosim.idl.simulation.air.cdrs.CDRSDayNightState getDayNightState()
	{
		return _delegate.getDayNightState();
	}

	public void setWaterPumpArmedStatus(com.traclabs.biosim.idl.simulation.air.cdrs.CDRSCommandStatus status)
	{
_delegate.setWaterPumpArmedStatus(status);
	}

}
