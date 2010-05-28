package com.traclabs.biosim.idl.simulation.power;

/**
 *	Generated from IDL interface "PowerProducerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface PowerProducerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setPowerOutputs(com.traclabs.biosim.idl.simulation.power.PowerStore[] destinations, float[] maxFlowRates, float[] desiredFlowRates);
}
