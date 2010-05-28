package com.traclabs.biosim.idl.sensor.crew;

/**
 *	Generated from IDL interface "CrewGroupSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface CrewGroupSensorOperations
	extends com.traclabs.biosim.idl.sensor.framework.GenericSensorOperations
{
	/* constants */
	/* operations  */
	void setInput(com.traclabs.biosim.idl.simulation.crew.CrewGroup source);
	com.traclabs.biosim.idl.simulation.crew.CrewGroup getInput();
}
