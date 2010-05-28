package com.traclabs.biosim.idl.actuator.framework;

/**
 *	Generated from IDL interface "GenericActuator"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
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
