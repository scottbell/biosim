package com.traclabs.biosim.idl.simulation.air;

/**
 *	Generated from IDL interface "H2ProducerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface H2ProducerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setH2Outputs(com.traclabs.biosim.idl.simulation.air.H2Store[] destinations, float[] maxFlowRates, float[] desiredFlowRates);
}
