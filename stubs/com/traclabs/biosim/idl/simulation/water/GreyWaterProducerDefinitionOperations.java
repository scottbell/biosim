package com.traclabs.biosim.idl.simulation.water;


/**
 * Generated from IDL interface "GreyWaterProducerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface GreyWaterProducerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.water.WaterProducerDefinitionOperations
{
	/* constants */
	/* operations  */
	void setGreyWaterOutputs(com.traclabs.biosim.idl.simulation.water.GreyWaterStore[] destinations, float[] maxFlowRates, float[] desiredFlowRates);
}
