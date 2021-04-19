package com.traclabs.biosim.idl.simulation.air;


/**
 * Generated from IDL interface "NitrogenProducerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface NitrogenProducerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setNitrogenOutputs(com.traclabs.biosim.idl.simulation.air.NitrogenStore[] destinations, float[] maxFlowRates, float[] desiredFlowRates);
}
