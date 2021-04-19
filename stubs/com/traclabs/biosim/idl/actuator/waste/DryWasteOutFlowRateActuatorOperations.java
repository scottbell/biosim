package com.traclabs.biosim.idl.actuator.waste;


/**
 * Generated from IDL interface "DryWasteOutFlowRateActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
