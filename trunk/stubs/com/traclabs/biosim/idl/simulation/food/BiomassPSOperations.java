package com.traclabs.biosim.idl.simulation.food;

/**
 *	Generated from IDL interface "BiomassPS"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface BiomassPSOperations
	extends com.traclabs.biosim.idl.simulation.framework.SimBioModuleOperations , com.traclabs.biosim.idl.simulation.power.PowerConsumerOperations , com.traclabs.biosim.idl.simulation.water.PotableWaterConsumerOperations , com.traclabs.biosim.idl.simulation.water.GreyWaterConsumerOperations , com.traclabs.biosim.idl.simulation.food.BiomassProducerOperations , com.traclabs.biosim.idl.simulation.environment.AirConsumerOperations , com.traclabs.biosim.idl.simulation.environment.AirProducerOperations , com.traclabs.biosim.idl.simulation.water.DirtyWaterProducerOperations
{
	/* constants */
	/* operations  */
	com.traclabs.biosim.idl.simulation.food.Shelf[] getShelves();
	com.traclabs.biosim.idl.simulation.food.Shelf getShelf(int index);
	com.traclabs.biosim.idl.simulation.food.Shelf createNewShelf(com.traclabs.biosim.idl.simulation.food.PlantType pType, float pCropArea, int pStartTick);
	void clearShelves();
	void setAutoHarvestAndReplantEnabled(boolean pHarvestEnabled);
	boolean autoHarvestAndReplantEnabled();
	boolean isAnyPlantDead();
	void killPlants();
	void setDeathEnabled(boolean deathEnabled);
	boolean getDeathEnabled();
}
