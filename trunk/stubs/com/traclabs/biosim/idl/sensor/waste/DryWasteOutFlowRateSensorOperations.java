package com.traclabs.biosim.idl.sensor.waste;

/**
 *	Generated from IDL interface "DryWasteOutFlowRateSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface DryWasteOutFlowRateSensorOperations
	extends com.traclabs.biosim.idl.sensor.framework.GenericSensorOperations
{
	/* constants */
	/* operations  */
	void setInput(com.traclabs.biosim.idl.simulation.waste.DryWasteProducer pProducer, int pIndex);
	com.traclabs.biosim.idl.simulation.waste.DryWasteProducer getInput();
	int getIndex();
}
