package com.traclabs.biosim.idl.simulation.air;


/**
 * Generated from IDL interface "H2ConsumerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface H2ConsumerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setH2Inputs(com.traclabs.biosim.idl.simulation.air.H2Store[] sources, float[] maxFlowRates, float[] desiredFlowRates);
}
