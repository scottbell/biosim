package com.traclabs.biosim.idl.simulation.water;


/**
 * Generated from IDL interface "PotableWaterConsumerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface PotableWaterConsumerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.water.WaterConsumerDefinitionOperations
{
	/* constants */
	/* operations  */
	void setPotableWaterInputs(com.traclabs.biosim.idl.simulation.water.PotableWaterStore[] sources, float[] maxFlowRates, float[] desiredFlowRates);
}
