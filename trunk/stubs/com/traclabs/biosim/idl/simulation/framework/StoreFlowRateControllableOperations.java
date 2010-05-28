package com.traclabs.biosim.idl.simulation.framework;

/**
 *	Generated from IDL interface "StoreFlowRateControllable"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface StoreFlowRateControllableOperations
	extends com.traclabs.biosim.idl.simulation.framework.SingleFlowRateControllableOperations
{
	/* constants */
	/* operations  */
	com.traclabs.biosim.idl.simulation.framework.Store[] getStores();
	void setInitialStores(com.traclabs.biosim.idl.simulation.framework.Store[] pStores);
	void setStore(com.traclabs.biosim.idl.simulation.framework.Store pStore, int index);
	boolean connectsTo(com.traclabs.biosim.idl.simulation.framework.Store pStore);
}
