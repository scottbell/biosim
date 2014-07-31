package com.traclabs.biosim.idl.simulation.water;

/**
 *	Generated from IDL interface "GreyWaterProducerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface GreyWaterProducerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.water.WaterProducerDefinitionOperations
{
	/* constants */
	/* operations  */
	void setGreyWaterOutputs(com.traclabs.biosim.idl.simulation.water.GreyWaterStore[] destinations, float[] maxFlowRates, float[] desiredFlowRates);
}
