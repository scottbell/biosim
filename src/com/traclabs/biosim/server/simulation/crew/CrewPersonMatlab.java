package com.traclabs.biosim.server.simulation.crew;

import com.traclabs.biosim.server.simulation.crew.CrewGroup;
import com.traclabs.biosim.server.simulation.crew.Sex;

public class CrewPersonMatlab extends BaseCrewPerson {
	public CrewPersonMatlab(String name, float age, float weight, Sex sex,
			int arrivalTick, int departureTick, CrewGroup crewGroup,
			Schedule schedule) {
		super(name, age, weight, sex, arrivalTick, departureTick, crewGroup, schedule);
	}

	/**
	 * Take and give resources from stores. See CrewPerson's implementation as a guide.
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
	
}
