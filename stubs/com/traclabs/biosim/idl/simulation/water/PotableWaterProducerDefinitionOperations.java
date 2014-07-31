package com.traclabs.biosim.idl.simulation.water;

/**
 *	Generated from IDL interface "PotableWaterProducerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface PotableWaterProducerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.water.WaterProducerDefinitionOperations
{
	/* constants */
	/* operations  */
	void setPotableWaterOutputs(com.traclabs.biosim.idl.simulation.water.PotableWaterStore[] destinations, float[] maxFlowRates, float[] desiredFlowRates);
}
