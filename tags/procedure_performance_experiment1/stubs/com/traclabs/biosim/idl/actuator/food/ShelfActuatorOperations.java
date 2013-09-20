package com.traclabs.biosim.idl.actuator.food;

/**
 *	Generated from IDL interface "ShelfActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface ShelfActuatorOperations
	extends com.traclabs.biosim.idl.actuator.framework.GenericActuatorOperations
{
	/* constants */
	/* operations  */
	void setOutput(com.traclabs.biosim.idl.simulation.food.BiomassPS source, int index);
	com.traclabs.biosim.idl.simulation.food.Shelf getOutput();
}
