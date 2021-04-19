package com.traclabs.biosim.idl.simulation.air;


/**
 * Generated from IDL interface "NitrogenConsumerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface NitrogenConsumerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setNitrogenInputs(com.traclabs.biosim.idl.simulation.air.NitrogenStore[] sources, float[] maxFlowRates, float[] desiredFlowRates);
}
