package biosim.server.crew;

// The package containing our stubs.
import SIMULATION.*;
import java.io.*;
import java.net.*;

public class CrewImpl extends CrewPOA {
	
	private CrewState currentState;
	private Schedule mySchedule;

	public CrewImpl(){
		//use default schedule
		mySchedule = new Schedule();
	}

	public CrewImpl(File pScheduleFile){
		mySchedule = new Schedule(pScheduleFile);
	}

	public void setState(CrewState newState){
		if (currentState != newState){
			currentState = newState;
			System.out.println("Changed state to "+getStateName());
		}
	}
	
	public String getModuleName(){
		return "Crew";
	}

	public CrewState getState(){
		return currentState;
	}
	
	public String getStateName(){
		if (currentState == CrewState.ASLEEP)
			return "asleep";
		else if (currentState == CrewState.HYGIENE)
			return "hygiene";
		else if (currentState == CrewState.EXCERCISE)
			return "excercise";
		else if (currentState == CrewState.EATING)
			return "eating";
		else if (currentState == CrewState.RECREATION)
			return "recreation";
		else if (currentState == CrewState.REPORTING)
			return "reporting";
		else if (currentState == CrewState.HEALTH)
			return "health";
		else if (currentState == CrewState.MAINTENANCE)
			return "maintenance";
		else if (currentState == CrewState.SCIENCE)
			return "science";
		else
			return "unknown";
	}

	public void tick(){
		processState();
	}
	
	private void processState(){
	}
}
