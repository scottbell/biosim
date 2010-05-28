package com.traclabs.biosim.idl.simulation.water;

/**
 *	Generated from IDL interface "WaterConsumerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface WaterConsumerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setWaterInputs(com.traclabs.biosim.idl.simulation.water.WaterStore[] sources, float[] maxFlowRates, float[] desiredFlowRates);
}
