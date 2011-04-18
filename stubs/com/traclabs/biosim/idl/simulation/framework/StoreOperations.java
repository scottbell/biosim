package com.traclabs.biosim.idl.simulation.framework;

/**
 *	Generated from IDL interface "Store"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface StoreOperations
	extends com.traclabs.biosim.idl.simulation.framework.PassiveModuleOperations
{
	/* constants */
	/* operations  */
	void setInitialCapacity(float metricAmount);
	void setCurrentCapacity(float metricAmount);
	void setInitialLevel(float metricAmount);
	void setCurrentLevel(float metricAmount);
	float add(float metricAmount);
	float take(float metricAmount);
	float getInitialLevel();
	float getInitialCapacity();
	float getCurrentLevel();
	float getCurrentCapacity();
	float getOverflow();
	float getPercentageFilled();
	boolean isPipe();
	void setPipe(boolean pPipe);
}
