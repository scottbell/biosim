package com.traclabs.biosim.idl.simulation.food;

/**
 *	Generated from IDL interface "BiomassStore"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface BiomassStoreOperations
	extends com.traclabs.biosim.idl.simulation.framework.StoreOperations
{
	/* constants */
	/* operations  */
	void setInitialBioMatterLevel(com.traclabs.biosim.idl.simulation.food.BioMatter pBioMatter);
	float addBioMatter(com.traclabs.biosim.idl.simulation.food.BioMatter pBioMatter);
	com.traclabs.biosim.idl.simulation.food.BioMatter[] takeBioMatterMass(float mass);
	float calculateWaterContentInStore();
}
