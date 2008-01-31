package com.traclabs.biosim.server.simulation.crew;

import com.traclabs.biosim.idl.framework.LogLevel;
import com.traclabs.biosim.idl.simulation.crew.Activity;
import com.traclabs.biosim.idl.simulation.crew.CrewGroup;
import com.traclabs.biosim.idl.simulation.crew.CrewPersonPOA;
import com.traclabs.biosim.idl.simulation.crew.Sex;

public class CrewPersonMatlab extends CrewPersonPOA {

	public CrewPersonMatlab(String name, float age, float weight, Sex sex,
			int arrivalTick, int departureTick, CrewGroupImpl crewGroupImpl,
			CrewGroup crewGroup, Schedule schedule) {
		// TODO Auto-generated constructor stub
	}

	public Activity getActivityByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public float getAge() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getArrivalTick() {
		// TODO Auto-generated method stub
		return 0;
	}

	public float getCO2Produced() {
		// TODO Auto-generated method stub
		return 0;
	}

	public float getCO2Ratio() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Activity getCurrentActivity() {
		// TODO Auto-generated method stub
		return null;
	}

	public CrewGroup getCurrentCrewGroup() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getDepartureTick() {
		// TODO Auto-generated method stub
		return 0;
	}

	public float getDirtyWaterProduced() {
		// TODO Auto-generated method stub
		return 0;
	}

	public float getFoodConsumed() {
		// TODO Auto-generated method stub
		return 0;
	}

	public float getGreyWaterProduced() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public float getO2Consumed() {
		// TODO Auto-generated method stub
		return 0;
	}

	public float getO2Ratio() {
		// TODO Auto-generated method stub
		return 0;
	}

	public int getOrderOfScheduledActivity(String name) {
		// TODO Auto-generated method stub
		return 0;
	}

	public float getPotableWaterConsumed() {
		// TODO Auto-generated method stub
		return 0;
	}

	public float getProductivity() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Activity getScheduledActivityByOrder(int order) {
		// TODO Auto-generated method stub
		return null;
	}

	public Sex getSex() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getTimeActivityPerformed() {
		// TODO Auto-generated method stub
		return 0;
	}

	public float getWeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void insertActivityInSchedule(Activity newActivity, int order) {
		// TODO Auto-generated method stub

	}

	public void insertActivityInScheduleNow(Activity newActivity) {
		// TODO Auto-generated method stub

	}

	public boolean isDead() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isOnBoard() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isPoisoned() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isSick() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isStarving() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isSuffocating() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean isThirsty() {
		// TODO Auto-generated method stub
		return false;
	}

	public void kill() {
		// TODO Auto-generated method stub

	}

	public void reset() {
		// TODO Auto-generated method stub

	}

	public void setArrivalTick(int arrivalTick) {
		// TODO Auto-generated method stub

	}

	public void setCurrentActivity(Activity newActivity) {
		// TODO Auto-generated method stub

	}

	public void setDepartureTick(int departureTick) {
		// TODO Auto-generated method stub

	}

	public void setLogLevel(LogLevel logLevel) {
		// TODO Auto-generated method stub

	}

	public void sicken() {
		// TODO Auto-generated method stub

	}

	public void tick() {
		// TODO Auto-generated method stub

	}

}
