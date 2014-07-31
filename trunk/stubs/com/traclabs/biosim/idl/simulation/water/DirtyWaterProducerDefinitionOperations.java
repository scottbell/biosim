package com.traclabs.biosim.idl.simulation.water;

/**
 *	Generated from IDL interface "DirtyWaterProducerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface DirtyWaterProducerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.water.WaterProducerDefinitionOperations
{
	/* constants */
	/* operations  */
	void setDirtyWaterOutputs(com.traclabs.biosim.idl.simulation.water.DirtyWaterStore[] destinations, float[] maxFlowRates, float[] desiredFlowRates);
}
