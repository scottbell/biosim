package com.traclabs.biosim.idl.simulation.water;


/**
 * Generated from IDL interface "DirtyWaterProducerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface DirtyWaterProducerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.water.WaterProducerDefinitionOperations
{
	/* constants */
	/* operations  */
	void setDirtyWaterOutputs(com.traclabs.biosim.idl.simulation.water.DirtyWaterStore[] destinations, float[] maxFlowRates, float[] desiredFlowRates);
}
