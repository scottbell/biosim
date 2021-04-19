package com.traclabs.biosim.idl.simulation.food;


/**
 * Generated from IDL interface "BiomassStore".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
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
