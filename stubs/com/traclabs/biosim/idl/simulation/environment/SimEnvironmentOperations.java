package com.traclabs.biosim.idl.simulation.environment;

/**
 *	Generated from IDL interface "SimEnvironment"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface SimEnvironmentOperations
	extends com.traclabs.biosim.idl.simulation.framework.PassiveModuleOperations
{
	/* constants */
	/* operations  */
	com.traclabs.biosim.idl.simulation.environment.EnvironmentO2Store getO2Store();
	com.traclabs.biosim.idl.simulation.environment.EnvironmentCO2Store getCO2Store();
	com.traclabs.biosim.idl.simulation.environment.EnvironmentOtherStore getOtherStore();
	com.traclabs.biosim.idl.simulation.environment.EnvironmentVaporStore getVaporStore();
	com.traclabs.biosim.idl.simulation.environment.EnvironmentNitrogenStore getNitrogenStore();
	void setInitialVolume(float pInitialCO2Moles, float pInitialO2Moles, float pInitialOtherMoles, float pInitialWaterMoles, float pInitialNitrogenMoles, float pInitialVolume);
	void setInitialVolumeAtSeaLevel(float pInitialVolume);
	void setCurrentVolumeAtSeaLevel(float pInitialVolume);
	float getInitialVolume();
	float getInitialTotalPressure();
	float getCurrentVolume();
	float getTotalPressure();
	float getRelativeHumidity();
	float getWaterDensity();
	float getTotalMoles();
	float getTemperature();
	float getLightIntensity();
	float getLeakRate();
	float getDayLength();
	void setDayLength(float dayLength);
	float getHourOfDayStart();
	void setHourOfDayStart(float hourOfDayStart);
	float getMaxLumens();
	void setMaxLumens(float maxLumens);
	void setAirlockVolume(float pAirLockVolume);
	float getAirlockVolume();
	void removeAirlockPercentage(float percentage);
	float getDangerousOxygenThreshold();
	void setDangerousOxygenThreshold(float pDangerousOxygenThreshold);
}
