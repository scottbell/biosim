package com.traclabs.biosim.idl.actuator.water;


/**
 * Generated from IDL interface "PotableWaterInFlowRateActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface PotableWaterInFlowRateActuatorOperations
	extends com.traclabs.biosim.idl.actuator.framework.GenericActuatorOperations
{
	/* constants */
	/* operations  */
	void setOutput(com.traclabs.biosim.idl.simulation.water.PotableWaterConsumer pConsumer, int pIndex);
	com.traclabs.biosim.idl.simulation.water.PotableWaterConsumer getOutput();
	int getIndex();
}
