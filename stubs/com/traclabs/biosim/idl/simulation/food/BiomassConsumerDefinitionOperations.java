package com.traclabs.biosim.idl.simulation.food;


/**
 * Generated from IDL interface "BiomassConsumerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface BiomassConsumerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setBiomassInputs(com.traclabs.biosim.idl.simulation.food.BiomassStore[] sources, float[] maxFlowRates, float[] desiredFlowRates);
}
