package com.traclabs.biosim.idl.actuator.power;


/**
 * Generated from IDL interface "PowerPSActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface PowerPSActuatorOperations
	extends com.traclabs.biosim.idl.actuator.framework.GenericActuatorOperations
{
	/* constants */
	/* operations  */
	void setOutput(com.traclabs.biosim.idl.simulation.power.PowerPS source);
	com.traclabs.biosim.idl.simulation.power.PowerPS getOutput();
}
