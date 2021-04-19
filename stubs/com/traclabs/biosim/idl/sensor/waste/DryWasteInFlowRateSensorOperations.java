package com.traclabs.biosim.idl.sensor.waste;


/**
 * Generated from IDL interface "DryWasteInFlowRateSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface DryWasteInFlowRateSensorOperations
	extends com.traclabs.biosim.idl.sensor.framework.GenericSensorOperations
{
	/* constants */
	/* operations  */
	void setInput(com.traclabs.biosim.idl.simulation.waste.DryWasteConsumer pConsumer, int pIndex);
	com.traclabs.biosim.idl.simulation.waste.DryWasteConsumer getInput();
	int getIndex();
}
