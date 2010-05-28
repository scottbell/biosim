package com.traclabs.biosim.idl.simulation.air;

/**
 *	Generated from IDL interface "NitrogenProducerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface NitrogenProducerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setNitrogenOutputs(com.traclabs.biosim.idl.simulation.air.NitrogenStore[] destinations, float[] maxFlowRates, float[] desiredFlowRates);
}
