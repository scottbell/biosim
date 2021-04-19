package com.traclabs.biosim.idl.actuator.air;


/**
 * Generated from IDL interface "AirRSActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface AirRSActuatorOperations
	extends com.traclabs.biosim.idl.actuator.framework.GenericActuatorOperations
{
	/* constants */
	/* operations  */
	void setOutput(com.traclabs.biosim.idl.simulation.air.AirRS source);
	com.traclabs.biosim.idl.simulation.air.AirRS getOutput();
}
