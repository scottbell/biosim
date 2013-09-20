package com.traclabs.biosim.idl.actuator.power;

/**
 *	Generated from IDL interface "PowerPSActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface PowerPSActuatorOperations
	extends com.traclabs.biosim.idl.actuator.framework.GenericActuatorOperations
{
	/* constants */
	/* operations  */
	void setOutput(com.traclabs.biosim.idl.simulation.power.PowerPS source);
	com.traclabs.biosim.idl.simulation.power.PowerPS getOutput();
}
