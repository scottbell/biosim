package com.traclabs.biosim.idl.simulation.crew;


/**
 * Generated from IDL interface "CrewPerson".
 *
 * @author JacORB IDL compiler V 3.9
 * @version generated at Apr 19, 2021, 1:35:30 PM
 */

public interface CrewPersonOperations
{
	/* constants */
	/* operations  */
	com.traclabs.biosim.idl.simulation.crew.Activity getCurrentActivity();
	void setCurrentActivity(com.traclabs.biosim.idl.simulation.crew.Activity newActivity);
	int getOrderOfScheduledActivity(java.lang.String name);
	void insertActivityInSchedule(com.traclabs.biosim.idl.simulation.crew.Activity newActivity, int order);
	void insertActivityInScheduleNow(com.traclabs.biosim.idl.simulation.crew.Activity newActivity);
	void sicken();
	java.lang.String getName();
	float getAge();
	float getWeight();
	int getTimeActivityPerformed();
	com.traclabs.biosim.idl.simulation.crew.Sex getSex();
	boolean isStarving();
	boolean isPoisoned();
	boolean isThirsty();
	boolean isSick();
	boolean isOnBoard();
	boolean isSuffocating();
	boolean isDead();
	float getProductivity();
	float getGreyWaterProduced();
	float getDirtyWaterProduced();
	float getPotableWaterConsumed();
	float getFoodConsumed();
	float getCO2Produced();
	float getO2Consumed();
	com.traclabs.biosim.idl.simulation.crew.Activity getActivityByName(java.lang.String name);
	com.traclabs.biosim.idl.simulation.crew.Activity getScheduledActivityByOrder(int order);
	void setArrivalTick(int arrivalTick);
	int getArrivalTick();
	void setDepartureTick(int departureTick);
	int getDepartureTick();
	void tick();
	void reset();
	com.traclabs.biosim.idl.simulation.crew.CrewGroup getCurrentCrewGroup();
	void setLogLevel(com.traclabs.biosim.idl.framework.LogLevel pLogLevel);
	void kill();
}
