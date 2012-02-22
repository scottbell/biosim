package com.traclabs.biosim.idl.simulation.environment;

import org.omg.PortableServer.POA;

/**
 *	Generated from IDL interface "SimEnvironment"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */

public class SimEnvironmentPOATie
	extends SimEnvironmentPOA
{
	private SimEnvironmentOperations _delegate;

	private POA _poa;
	public SimEnvironmentPOATie(SimEnvironmentOperations delegate)
	{
		_delegate = delegate;
	}
	public SimEnvironmentPOATie(SimEnvironmentOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public com.traclabs.biosim.idl.simulation.environment.SimEnvironment _this()
	{
		return com.traclabs.biosim.idl.simulation.environment.SimEnvironmentHelper.narrow(_this_object());
	}
	public com.traclabs.biosim.idl.simulation.environment.SimEnvironment _this(org.omg.CORBA.ORB orb)
	{
		return com.traclabs.biosim.idl.simulation.environment.SimEnvironmentHelper.narrow(_this_object(orb));
	}
	public SimEnvironmentOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(SimEnvironmentOperations delegate)
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

	public com.traclabs.biosim.idl.simulation.environment.EnvironmentNitrogenStore getNitrogenStore()
	{
		return _delegate.getNitrogenStore();
	}

	public com.traclabs.biosim.idl.simulation.environment.EnvironmentCO2Store getCO2Store()
	{
		return _delegate.getCO2Store();
	}

	public com.traclabs.biosim.idl.simulation.environment.EnvironmentOtherStore getOtherStore()
	{
		return _delegate.getOtherStore();
	}

	public void setAirlockVolume(float pAirLockVolume)
	{
_delegate.setAirlockVolume(pAirLockVolume);
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

	public float getTotalMoles()
	{
		return _delegate.getTotalMoles();
	}

	public com.traclabs.biosim.idl.framework.Malfunction startMalfunction(com.traclabs.biosim.idl.framework.MalfunctionIntensity pIntensity, com.traclabs.biosim.idl.framework.MalfunctionLength pLength)
	{
		return _delegate.startMalfunction(pIntensity,pLength);
	}

	public void setInitialVolumeAtSeaLevel(float pInitialVolume)
	{
_delegate.setInitialVolumeAtSeaLevel(pInitialVolume);
	}

	public float getLeakRate()
	{
		return _delegate.getLeakRate();
	}

	public float getTemperature()
	{
		return _delegate.getTemperature();
	}

	public void notifyCommandSent(java.lang.String commandName)
	{
_delegate.notifyCommandSent(commandName);
	}

	public int getID()
	{
		return _delegate.getID();
	}

	public void setCurrentVolumeAtSeaLevel(float pInitialVolume)
	{
_delegate.setCurrentVolumeAtSeaLevel(pInitialVolume);
	}

	public void fixMalfunction(long id)
	{
_delegate.fixMalfunction(id);
	}

	public float getInitialTotalPressure()
	{
		return _delegate.getInitialTotalPressure();
	}

	public int getMyTicks()
	{
		return _delegate.getMyTicks();
	}

	public com.traclabs.biosim.idl.framework.Malfunction[] getMalfunctions()
	{
		return _delegate.getMalfunctions();
	}

	public com.traclabs.biosim.idl.simulation.environment.EnvironmentVaporStore getVaporStore()
	{
		return _delegate.getVaporStore();
	}

	public float getInitialVolume()
	{
		return _delegate.getInitialVolume();
	}

	public void reset()
	{
_delegate.reset();
	}

	public float getCurrentVolume()
	{
		return _delegate.getCurrentVolume();
	}

	public void registerBioCommandListener(com.traclabs.biosim.idl.simulation.framework.BioCommandListener listener)
	{
_delegate.registerBioCommandListener(listener);
	}

	public float getRelativeHumidity()
	{
		return _delegate.getRelativeHumidity();
	}

	public java.lang.String getModuleName()
	{
		return _delegate.getModuleName();
	}

	public float getMaxLumens()
	{
		return _delegate.getMaxLumens();
	}

	public float randomFilter(float preFilteredValue)
	{
		return _delegate.randomFilter(preFilteredValue);
	}

	public float getAirlockVolume()
	{
		return _delegate.getAirlockVolume();
	}

	public boolean isMalfunctioning()
	{
		return _delegate.isMalfunctioning();
	}

	public void setHourOfDayStart(float hourOfDayStart)
	{
_delegate.setHourOfDayStart(hourOfDayStart);
	}

	public void setInitialVolume(float pInitialCO2Moles, float pInitialO2Moles, float pInitialOtherMoles, float pInitialWaterMoles, float pInitialNitrogenMoles, float pInitialVolume)
	{
_delegate.setInitialVolume(pInitialCO2Moles,pInitialO2Moles,pInitialOtherMoles,pInitialWaterMoles,pInitialNitrogenMoles,pInitialVolume);
	}

	public com.traclabs.biosim.idl.simulation.environment.EnvironmentO2Store getO2Store()
	{
		return _delegate.getO2Store();
	}

	public void log()
	{
_delegate.log();
	}

	public float getTotalPressure()
	{
		return _delegate.getTotalPressure();
	}

	public void removeAirlockPercentage(float percentage)
	{
_delegate.removeAirlockPercentage(percentage);
	}

	public void maintain()
	{
_delegate.maintain();
	}

	public float getDayLength()
	{
		return _delegate.getDayLength();
	}

	public void doSomeRepairWork(long id)
	{
_delegate.doSomeRepairWork(id);
	}

	public float getDangerousOxygenThreshold()
	{
		return _delegate.getDangerousOxygenThreshold();
	}

	public float getWaterDensity()
	{
		return _delegate.getWaterDensity();
	}

	public void setEnableFailure(boolean pValue)
	{
_delegate.setEnableFailure(pValue);
	}

	public void clearAllMalfunctions()
	{
_delegate.clearAllMalfunctions();
	}

	public void setMaxLumens(float maxLumens)
	{
_delegate.setMaxLumens(maxLumens);
	}

	public void setDayLength(float dayLength)
	{
_delegate.setDayLength(dayLength);
	}

	public void setDangerousOxygenThreshold(float pDangerousOxygenThreshold)
	{
_delegate.setDangerousOxygenThreshold(pDangerousOxygenThreshold);
	}

	public void tick()
	{
_delegate.tick();
	}

	public float getHourOfDayStart()
	{
		return _delegate.getHourOfDayStart();
	}

	public void setTickLength(float pInterval)
	{
_delegate.setTickLength(pInterval);
	}

	public float getLightIntensity()
	{
		return _delegate.getLightIntensity();
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
