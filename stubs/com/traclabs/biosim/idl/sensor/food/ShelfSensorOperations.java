package com.traclabs.biosim.idl.sensor.food;


/**
 * Generated from IDL interface "ShelfSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface ShelfSensorOperations
	extends com.traclabs.biosim.idl.sensor.framework.GenericSensorOperations
{
	/* constants */
	/* operations  */
	void setInput(com.traclabs.biosim.idl.simulation.food.BiomassPS source, int index);
	com.traclabs.biosim.idl.simulation.food.Shelf getInput();
}
