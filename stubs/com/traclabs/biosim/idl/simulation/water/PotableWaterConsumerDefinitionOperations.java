package com.traclabs.biosim.idl.simulation.water;

/**
 *	Generated from IDL interface "PotableWaterConsumerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface PotableWaterConsumerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.water.WaterConsumerDefinitionOperations
{
	/* constants */
	/* operations  */
	void setPotableWaterInputs(com.traclabs.biosim.idl.simulation.water.PotableWaterStore[] sources, float[] maxFlowRates, float[] desiredFlowRates);
}
