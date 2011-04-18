package com.traclabs.biosim.idl.actuator.air;

/**
 *	Generated from IDL interface "NitrogenOutFlowRateActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface NitrogenOutFlowRateActuatorOperations
	extends com.traclabs.biosim.idl.actuator.framework.GenericActuatorOperations
{
	/* constants */
	/* operations  */
	void setOutput(com.traclabs.biosim.idl.simulation.air.NitrogenProducer pProducer, int pIndex);
	com.traclabs.biosim.idl.simulation.air.NitrogenProducer getOutput();
	int getIndex();
}
