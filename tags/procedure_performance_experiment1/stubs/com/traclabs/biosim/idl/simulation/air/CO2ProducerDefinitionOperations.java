package com.traclabs.biosim.idl.simulation.air;

/**
 *	Generated from IDL interface "CO2ProducerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface CO2ProducerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setCO2Outputs(com.traclabs.biosim.idl.simulation.air.CO2Store[] destinations, float[] maxFlowRates, float[] desiredFlowRates);
}
