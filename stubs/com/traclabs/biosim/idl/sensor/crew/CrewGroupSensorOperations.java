package com.traclabs.biosim.idl.sensor.crew;


/**
 * Generated from IDL interface "CrewGroupSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface CrewGroupSensorOperations
	extends com.traclabs.biosim.idl.sensor.framework.GenericSensorOperations
{
	/* constants */
	/* operations  */
	void setInput(com.traclabs.biosim.idl.simulation.crew.CrewGroup source);
	com.traclabs.biosim.idl.simulation.crew.CrewGroup getInput();
}
