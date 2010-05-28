package com.traclabs.biosim.idl.actuator.water;

/**
 *	Generated from IDL interface "WaterOutFlowRateActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface WaterOutFlowRateActuatorOperations
	extends com.traclabs.biosim.idl.actuator.framework.GenericActuatorOperations
{
	/* constants */
	/* operations  */
	void setOutput(com.traclabs.biosim.idl.simulation.water.WaterProducer pProducer, int pIndex);
	com.traclabs.biosim.idl.simulation.water.WaterProducer getOutput();
	int getIndex();
}
