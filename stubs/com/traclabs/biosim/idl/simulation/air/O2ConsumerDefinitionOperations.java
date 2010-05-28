package com.traclabs.biosim.idl.simulation.air;

/**
 *	Generated from IDL interface "O2ConsumerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface O2ConsumerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setO2Inputs(com.traclabs.biosim.idl.simulation.air.O2Store[] sources, float[] maxFlowRates, float[] desiredFlowRates);
}
