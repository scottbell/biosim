package com.traclabs.biosim.idl.actuator.air;

/**
 *	Generated from IDL interface "MethaneOutFlowRateActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface MethaneOutFlowRateActuatorOperations
	extends com.traclabs.biosim.idl.actuator.framework.GenericActuatorOperations
{
	/* constants */
	/* operations  */
	void setOutput(com.traclabs.biosim.idl.simulation.air.MethaneProducer pProducer, int pIndex);
	com.traclabs.biosim.idl.simulation.air.MethaneProducer getOutput();
	int getIndex();
}
