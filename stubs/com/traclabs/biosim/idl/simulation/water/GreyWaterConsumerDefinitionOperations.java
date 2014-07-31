package com.traclabs.biosim.idl.simulation.water;

/**
 *	Generated from IDL interface "GreyWaterConsumerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface GreyWaterConsumerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.water.WaterConsumerDefinitionOperations
{
	/* constants */
	/* operations  */
	void setGreyWaterInputs(com.traclabs.biosim.idl.simulation.water.GreyWaterStore[] sources, float[] maxFlowRates, float[] desiredFlowRates);
}
