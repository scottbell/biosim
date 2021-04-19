package com.traclabs.biosim.idl.actuator.framework;


/**
 * Generated from IDL interface "GenericActuator".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface GenericActuatorOperations
	extends com.traclabs.biosim.idl.framework.BioModuleOperations
{
	/* constants */
	/* operations  */
	void setValue(float pValue);
	float getValue();
	float getMax();
	float getMin();
	com.traclabs.biosim.idl.framework.BioModule getOutputModule();
}
