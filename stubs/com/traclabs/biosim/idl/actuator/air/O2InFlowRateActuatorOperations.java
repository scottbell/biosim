package com.traclabs.biosim.idl.actuator.air;


/**
 * Generated from IDL interface "O2InFlowRateActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface O2InFlowRateActuatorOperations
	extends com.traclabs.biosim.idl.actuator.framework.GenericActuatorOperations
{
	/* constants */
	/* operations  */
	void setOutput(com.traclabs.biosim.idl.simulation.air.O2Consumer pConsumer, int pIndex);
	com.traclabs.biosim.idl.simulation.air.O2Consumer getOutput();
	int getIndex();
}
