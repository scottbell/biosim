package com.traclabs.biosim.idl.simulation.air;

/**
 *	Generated from IDL interface "O2ProducerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface O2ProducerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setO2Outputs(com.traclabs.biosim.idl.simulation.air.O2Store[] destinations, float[] maxFlowRates, float[] desiredFlowRates);
}
