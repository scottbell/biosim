package com.traclabs.biosim.idl.sensor.food;


/**
 * Generated from IDL interface "BiomassStoreSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface BiomassStoreSensorOperations
	extends com.traclabs.biosim.idl.sensor.framework.GenericSensorOperations
{
	/* constants */
	/* operations  */
	void setInput(com.traclabs.biosim.idl.simulation.food.BiomassStore source);
	com.traclabs.biosim.idl.simulation.food.BiomassStore getInput();
}
