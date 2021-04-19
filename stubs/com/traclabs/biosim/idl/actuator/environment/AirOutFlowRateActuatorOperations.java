package com.traclabs.biosim.idl.actuator.environment;


/**
 * Generated from IDL interface "AirOutFlowRateActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface AirOutFlowRateActuatorOperations
	extends com.traclabs.biosim.idl.actuator.framework.GenericActuatorOperations
{
	/* constants */
	/* operations  */
	void setOutput(com.traclabs.biosim.idl.simulation.environment.AirProducer pProducer, int pIndex);
	com.traclabs.biosim.idl.simulation.environment.AirProducer getOutput();
	int getIndex();
}
