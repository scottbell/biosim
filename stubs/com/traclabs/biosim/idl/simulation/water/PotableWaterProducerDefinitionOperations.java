package com.traclabs.biosim.idl.simulation.water;


/**
 * Generated from IDL interface "PotableWaterProducerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface PotableWaterProducerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.water.WaterProducerDefinitionOperations
{
	/* constants */
	/* operations  */
	void setPotableWaterOutputs(com.traclabs.biosim.idl.simulation.water.PotableWaterStore[] destinations, float[] maxFlowRates, float[] desiredFlowRates);
}
