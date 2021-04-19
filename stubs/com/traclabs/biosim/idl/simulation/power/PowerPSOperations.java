package com.traclabs.biosim.idl.simulation.power;


/**
 * Generated from IDL interface "PowerPS".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
