package com.traclabs.biosim.idl.simulation.power;

/**
 *	Generated from IDL interface "PowerConsumerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface PowerConsumerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setPowerInputs(com.traclabs.biosim.idl.simulation.power.PowerStore[] sources, float[] maxFlowRates, float[] desiredFlowRates);
}
