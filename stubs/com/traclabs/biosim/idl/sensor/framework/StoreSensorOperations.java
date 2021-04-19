package com.traclabs.biosim.idl.sensor.framework;


/**
 * Generated from IDL interface "StoreSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface StoreSensorOperations
	extends com.traclabs.biosim.idl.sensor.framework.GenericSensorOperations
{
	/* constants */
	/* operations  */
	void setInput(com.traclabs.biosim.idl.simulation.framework.Store source);
	com.traclabs.biosim.idl.simulation.framework.Store getInput();
}
