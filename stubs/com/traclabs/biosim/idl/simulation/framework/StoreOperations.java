package com.traclabs.biosim.idl.simulation.framework;


/**
 * Generated from IDL interface "Store".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
