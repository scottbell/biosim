package com.traclabs.biosim.idl.simulation.water;


/**
 * Generated from IDL interface "GreyWaterConsumerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface GreyWaterConsumerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.water.WaterConsumerDefinitionOperations
{
	/* constants */
	/* operations  */
	void setGreyWaterInputs(com.traclabs.biosim.idl.simulation.water.GreyWaterStore[] sources, float[] maxFlowRates, float[] desiredFlowRates);
}
