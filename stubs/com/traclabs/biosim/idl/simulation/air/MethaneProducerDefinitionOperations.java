package com.traclabs.biosim.idl.simulation.air;


/**
 * Generated from IDL interface "MethaneProducerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface MethaneProducerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setMethaneOutputs(com.traclabs.biosim.idl.simulation.air.MethaneStore[] destinations, float[] maxFlowRates, float[] desiredFlowRates);
}
