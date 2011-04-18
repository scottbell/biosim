package com.traclabs.biosim.idl.simulation.air;

/**
 *	Generated from IDL interface "MethaneConsumerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface MethaneConsumerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setMethaneInputs(com.traclabs.biosim.idl.simulation.air.MethaneStore[] sources, float[] maxFlowRates, float[] desiredFlowRates);
}
