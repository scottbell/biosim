package com.traclabs.biosim.idl.simulation.power;

/**
 *	Generated from IDL interface "PowerPS"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface PowerPSOperations
	extends com.traclabs.biosim.idl.simulation.framework.SimBioModuleOperations , com.traclabs.biosim.idl.simulation.power.PowerProducerOperations , com.traclabs.biosim.idl.simulation.environment.LightConsumerOperations
{
	/* constants */
	/* operations  */
	float getPowerProduced();
	float getCurrentUpperPowerGeneration();
	void setCurrentUpperPowerGeneration(float pUpperPowerGenerationInWatts);
	float getInitialUpperPowerGeneration();
	void setInitialUpperPowerGeneration(float pUpperPowerGenerationInWatts);
}
