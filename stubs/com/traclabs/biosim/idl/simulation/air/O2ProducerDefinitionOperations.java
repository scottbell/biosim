package com.traclabs.biosim.idl.simulation.air;


/**
 * Generated from IDL interface "O2ProducerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface O2ProducerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setO2Outputs(com.traclabs.biosim.idl.simulation.air.O2Store[] destinations, float[] maxFlowRates, float[] desiredFlowRates);
}
