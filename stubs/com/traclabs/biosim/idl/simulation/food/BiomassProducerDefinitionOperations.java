package com.traclabs.biosim.idl.simulation.food;


/**
 * Generated from IDL interface "BiomassProducerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface BiomassProducerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setBiomassOutputs(com.traclabs.biosim.idl.simulation.food.BiomassStore[] destinations, float[] maxFlowRates, float[] desiredFlowRates);
}
