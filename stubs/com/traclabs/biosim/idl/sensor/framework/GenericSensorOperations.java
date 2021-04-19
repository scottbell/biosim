package com.traclabs.biosim.idl.sensor.framework;


/**
 * Generated from IDL interface "GenericSensor".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
