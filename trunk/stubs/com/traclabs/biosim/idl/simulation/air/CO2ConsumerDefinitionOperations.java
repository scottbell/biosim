package com.traclabs.biosim.idl.simulation.air;

/**
 *	Generated from IDL interface "CO2ConsumerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface CO2ConsumerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setCO2Inputs(com.traclabs.biosim.idl.simulation.air.CO2Store[] sources, float[] maxFlowRates, float[] desiredFlowRates);
}
