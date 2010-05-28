package com.traclabs.biosim.idl.actuator.water;

/**
 *	Generated from IDL interface "WaterRSActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface WaterRSActuatorOperations
	extends com.traclabs.biosim.idl.actuator.framework.GenericActuatorOperations
{
	/* constants */
	/* operations  */
	void setOutput(com.traclabs.biosim.idl.simulation.water.WaterRS source);
	com.traclabs.biosim.idl.simulation.water.WaterRS getOutput();
}
