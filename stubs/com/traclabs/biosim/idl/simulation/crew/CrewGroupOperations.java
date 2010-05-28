package com.traclabs.biosim.idl.simulation.crew;

/**
 *	Generated from IDL interface "CrewGroup"
 *	@author JacORB IDL compiler V 2.2.3, 10-Dec-2005
 */


public interface CrewGroupOperations
	extends com.traclabs.biosim.idl.simulation.framework.SimBioModuleOperations , com.traclabs.biosim.idl.simulation.environment.AirConsumerOperations , com.traclabs.biosim.idl.simulation.water.PotableWaterConsumerOperations , com.traclabs.biosim.idl.simulation.food.FoodConsumerOperations , com.traclabs.biosim.idl.simulation.environment.AirProducerOperations , com.traclabs.biosim.idl.simulation.water.GreyWaterProducerOperations , com.traclabs.biosim.idl.simulation.water.DirtyWaterProducerOperations , com.traclabs.biosim.idl.simulation.waste.DryWasteProducerOperations
{
	/* constants */
	/* operations  */
	com.traclabs.biosim.idl.simulation.crew.CrewPerson getCrewPerson(java.lang.String name);
	com.traclabs.biosim.idl.simulation.crew.CrewPerson createCrewPerson(java.lang.String name, float age, float weight, com.traclabs.biosim.idl.simulation.crew.Sex pSex, int arrivalTick, int departureTick);
	com.traclabs.biosim.idl.simulation.crew.CrewPerson[] getCrewPeople();
	void scheduleRepair(java.lang.String moduleName, long malfunctionID, int timeLength);
	int getCrewSize();
	float getGreyWaterProduced();
	float getDirtyWaterProduced();
	float getPotableWaterConsumed();
	float getFoodConsumed();
	float getCO2Produced();
	float getO2Consumed();
	float getProductivity();
	boolean isDead();
	boolean anyDead();
	void detachCrewPerson(java.lang.String name);
	void attachCrewPerson(com.traclabs.biosim.idl.simulation.crew.CrewPerson pCrewPerson);
	void killCrew();
	void setDeathEnabled(boolean deathEnabled);
	boolean getDeathEnabled();
}
