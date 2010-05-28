package com.traclabs.biosim.idl.simulation.air;

/**
 *	Generated from IDL interface "MethaneProducerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface MethaneProducerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setMethaneOutputs(com.traclabs.biosim.idl.simulation.air.MethaneStore[] destinations, float[] maxFlowRates, float[] desiredFlowRates);
}
