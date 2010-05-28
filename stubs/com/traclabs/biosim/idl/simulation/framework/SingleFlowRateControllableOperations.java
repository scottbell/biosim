package com.traclabs.biosim.idl.simulation.framework;

/**
 *	Generated from IDL interface "SingleFlowRateControllable"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface SingleFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	void setMaxFlowRate(float value, int index);
	float getMaxFlowRate(int index);
	void setDesiredFlowRate(float value, int index);
	float getDesiredFlowRate(int index);
	float getActualFlowRate(int index);
	float[] getMaxFlowRates();
	float[] getDesiredFlowRates();
	float[] getActualFlowRates();
	void setInitialMaxFlowRates(float[] flowrates);
	void setInitialDesiredFlowRates(float[] flowrates);
	void setInitialActualFlowRates(float[] flowrates);
	float getTotalMaxFlowRate();
	float getTotalDesiredFlowRate();
	float getTotalActualFlowRate();
	float getAveragePercentageFull();
	float getPercentageFull(int index);
	void reset();
}
