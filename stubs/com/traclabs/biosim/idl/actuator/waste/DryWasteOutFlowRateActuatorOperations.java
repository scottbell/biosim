package com.traclabs.biosim.idl.actuator.waste;

/**
 *	Generated from IDL interface "DryWasteOutFlowRateActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface DryWasteOutFlowRateActuatorOperations
	extends com.traclabs.biosim.idl.actuator.framework.GenericActuatorOperations
{
	/* constants */
	/* operations  */
	void setOutput(com.traclabs.biosim.idl.simulation.waste.DryWasteProducer pProducer, int pIndex);
	com.traclabs.biosim.idl.simulation.waste.DryWasteProducer getOutput();
	int getIndex();
}
