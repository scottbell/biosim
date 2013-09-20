package com.traclabs.biosim.idl.sensor.framework;

/**
 *	Generated from IDL interface "GenericSensor"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface GenericSensorOperations
	extends com.traclabs.biosim.idl.framework.BioModuleOperations
{
	/* constants */
	/* operations  */
	float getValue();
	float getMax();
	float getMin();
	com.traclabs.biosim.idl.framework.BioModule getInputModule();
}
