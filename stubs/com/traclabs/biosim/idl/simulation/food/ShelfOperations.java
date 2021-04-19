package com.traclabs.biosim.idl.simulation.food;


/**
 * Generated from IDL interface "Shelf".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface ShelfOperations
{
	/* constants */
	/* operations  */
	void harvest();
	float getHarvestInterval();
	boolean isReadyForHavest();
	boolean isDead();
	com.traclabs.biosim.idl.simulation.food.PlantType getCropType();
	java.lang.String getCropTypeString();
	float getTimeTillCanopyClosure();
	void replant(com.traclabs.biosim.idl.simulation.food.PlantType pType, float area);
	float getCropAreaTotal();
	float getCropAreaUsed();
	com.traclabs.biosim.idl.simulation.food.BiomassPS getBiomassPS();
	void setStartTick(int tick);
	void kill();
	com.traclabs.biosim.idl.simulation.food.Plant getPlant();
}
