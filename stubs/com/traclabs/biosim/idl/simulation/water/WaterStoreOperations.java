package com.traclabs.biosim.idl.simulation.water;


/**
 * Generated from IDL interface "WaterStore".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface WaterStoreOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreOperations
{
	/* constants */
	/* operations  */
	void setInitialWaterTemperature(float temperature);
	float addWaterWithTemperature(float mass, float temperature);
	float getCurrentTemperature();
}
