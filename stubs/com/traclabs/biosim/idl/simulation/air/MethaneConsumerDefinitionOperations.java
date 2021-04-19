package com.traclabs.biosim.idl.simulation.air;


/**
 * Generated from IDL interface "MethaneConsumerDefinition".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface MethaneConsumerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setMethaneInputs(com.traclabs.biosim.idl.simulation.air.MethaneStore[] sources, float[] maxFlowRates, float[] desiredFlowRates);
}
