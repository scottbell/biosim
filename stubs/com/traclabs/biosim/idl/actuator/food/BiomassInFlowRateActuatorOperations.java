package com.traclabs.biosim.idl.actuator.food;


/**
 * Generated from IDL interface "BiomassInFlowRateActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface BiomassInFlowRateActuatorOperations
	extends com.traclabs.biosim.idl.actuator.framework.GenericActuatorOperations
{
	/* constants */
	/* operations  */
	void setOutput(com.traclabs.biosim.idl.simulation.food.BiomassConsumer pConsumer, int pIndex);
	com.traclabs.biosim.idl.simulation.food.BiomassConsumer getOutput();
	int getIndex();
}
