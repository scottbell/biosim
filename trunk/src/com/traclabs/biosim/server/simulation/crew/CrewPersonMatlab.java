package com.traclabs.biosim.server.simulation.crew;

import com.traclabs.biosim.idl.simulation.crew.CrewGroup;
import com.traclabs.biosim.idl.simulation.crew.Sex;

public class CrewPersonMatlab extends BaseCrewPersonImpl {
	public CrewPersonMatlab(String name, float age, float weight, Sex sex,
			int arrivalTick, int departureTick, CrewGroup crewGroup,
			Schedule schedule) {
		super(name, age, weight, sex, arrivalTick, departureTick, crewGroup, schedule);
	}

	/**
	 * Take and give resources from stores. See CrewPersonImpl's implementation as a guide.
	 */
	@Override
	protected void consumeAndProduceResources() {
		// TODO
		myLogger.info("Consuming and producing inside MatLab");
	}
	
	/**
	 * Hurt the crew if enough resources haven't been consumed
	 * Heal the crew if enough resources have been consumed
	 */
	@Override
	protected void afflictAndRecover() {
		// TODO
	}

	/**
	 * @return The amount of CO2 exhaled (in moles) by the crew member
	 */
	public float getCO2Produced() {
		// TODO
		return 0;
	}
	
	/**
	 * @return The amount of dirty water produced (in liters) by the crew member
	 */
	public float getDirtyWaterProduced() {
		// TODO
		return 0;
	}

	/**
	 * @return The amount of food consumed (in kilograms) by the crew member
	 */
	public float getFoodConsumed() {
		// TODO
		return 0;
	}

	/**
	 * @return The amount of grey water produced (in liters) by the crew member
	 */
	public float getGreyWaterProduced() {
		// TODO
		return 0;
	}

	/**
	 * @return The amount of oxygen consumed (in moles) by the crew member
	 */
	public float getO2Consumed() {
		// TODO
		return 0;
	}

	/**
	 * @return The amount of potable water consumed (in liters) by the crew member
	 */
	public float getPotableWaterConsumed() {
		// TODO
		return 0;
	}

	/**
	 * @return Return a metric on how well the crew member is doing on his or her's mission
	 */
	public float getProductivity() {
		// TODO
		return 0;
	}

	/**
	 * @return Return true if the crew is poisoned from CO2
	 */
	public boolean isPoisoned() {
		// TODO
		return false;
	}

	/**
	 * @return Return true if the crew is starving
	 */
	public boolean isStarving() {
		// TODO 
		return false;
	}

	/**
	 * @return Return true if the crew is suffocating
	 */
	public boolean isSuffocating() {
		// TODO
		return false;
	}

	/**
	 * @return Return true if the crew is thirsty
	 */
	public boolean isThirsty() {
		// TODO
		return false;
	}
	
}
