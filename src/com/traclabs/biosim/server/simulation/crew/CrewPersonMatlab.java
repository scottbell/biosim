package com.traclabs.biosim.server.simulation.crew;

import com.traclabs.biosim.idl.simulation.crew.CrewGroup;
import com.traclabs.biosim.idl.simulation.crew.Sex;

public class CrewPersonMatlab extends BaseCrewPersonImpl {
	public CrewPersonMatlab(String name, float age, float weight, Sex sex,
			int arrivalTick, int departureTick, CrewGroup crewGroup,
			Schedule schedule) {
		super(name, age, weight, sex, arrivalTick, departureTick, crewGroup, schedule);
	}

	@Override
	protected void afflictAndRecover() {
		// TODO Auto-generated method stub
	}

	@Override
	protected void consumeResources() {
		// TODO Auto-generated method stub
	}

	public float getCO2Produced() {
		// TODO Auto-generated method stub
		return 0;
	}

	public float getCO2Ratio() {
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

	public float getO2Consumed() {
		// TODO Auto-generated method stub
		return 0;
	}

	public float getO2Ratio() {
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

	public boolean isPoisoned() {
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
	
}
