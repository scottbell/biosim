package com.traclabs.biosim.idl.simulation.air;


/**
 * Generated from IDL interface "CO2ConsumerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface CO2ConsumerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setCO2Inputs(com.traclabs.biosim.idl.simulation.air.CO2Store[] sources, float[] maxFlowRates, float[] desiredFlowRates);
}
