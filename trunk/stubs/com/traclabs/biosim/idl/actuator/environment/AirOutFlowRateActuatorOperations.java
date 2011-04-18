package com.traclabs.biosim.idl.actuator.environment;

/**
 *	Generated from IDL interface "AirOutFlowRateActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
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
