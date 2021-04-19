package com.traclabs.biosim.idl.simulation.water;


/**
 * Generated from IDL interface "DirtyWaterConsumerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface DirtyWaterConsumerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.water.WaterConsumerDefinitionOperations
{
	/* constants */
	/* operations  */
	void setDirtyWaterInputs(com.traclabs.biosim.idl.simulation.water.DirtyWaterStore[] sources, float[] maxFlowRates, float[] desiredFlowRates);
}
