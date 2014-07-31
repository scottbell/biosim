package com.traclabs.biosim.idl.simulation.water;

/**
 *	Generated from IDL interface "DirtyWaterConsumerDefinition"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface DirtyWaterConsumerDefinitionOperations
	extends com.traclabs.biosim.idl.simulation.water.WaterConsumerDefinitionOperations
{
	/* constants */
	/* operations  */
	void setDirtyWaterInputs(com.traclabs.biosim.idl.simulation.water.DirtyWaterStore[] sources, float[] maxFlowRates, float[] desiredFlowRates);
}
