package com.traclabs.biosim.idl.actuator.crew;


/**
 * Generated from IDL interface "CrewGroupActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface CrewGroupActuatorOperations
	extends com.traclabs.biosim.idl.actuator.framework.GenericActuatorOperations
{
	/* constants */
	/* operations  */
	void setOutput(com.traclabs.biosim.idl.simulation.crew.CrewGroup source);
	com.traclabs.biosim.idl.simulation.crew.CrewGroup getOutput();
}
