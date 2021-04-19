package com.traclabs.biosim.idl.actuator.power;


/**
 * Generated from IDL interface "PowerOutFlowRateActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface PowerOutFlowRateActuatorOperations
	extends com.traclabs.biosim.idl.actuator.framework.GenericActuatorOperations
{
	/* constants */
	/* operations  */
	void setOutput(com.traclabs.biosim.idl.simulation.power.PowerProducer pProducer, int pIndex);
	com.traclabs.biosim.idl.simulation.power.PowerProducer getOutput();
	int getIndex();
}
