package com.traclabs.biosim.idl.simulation.air;

/**
 *	Generated from IDL interface "H2ConsumerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface H2ConsumerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setH2Inputs(com.traclabs.biosim.idl.simulation.air.H2Store[] sources, float[] maxFlowRates, float[] desiredFlowRates);
}
