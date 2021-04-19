package com.traclabs.biosim.idl.actuator.air;


/**
 * Generated from IDL interface "H2OutFlowRateActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface H2OutFlowRateActuatorOperations
	extends com.traclabs.biosim.idl.actuator.framework.GenericActuatorOperations
{
	/* constants */
	/* operations  */
	void setOutput(com.traclabs.biosim.idl.simulation.air.H2Producer pProducer, int pIndex);
	com.traclabs.biosim.idl.simulation.air.H2Producer getOutput();
	int getIndex();
}
