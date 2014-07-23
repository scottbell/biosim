package com.traclabs.biosim.idl.simulation.water;

/**
 *	Generated from IDL interface "WaterStore"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
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
