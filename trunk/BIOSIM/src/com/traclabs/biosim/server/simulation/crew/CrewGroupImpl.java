package biosim.server.crew;

// The package containing our stubs.
import SIMULATION.*;

public class CrewImpl extends CrewPOA {
	
	private CrewState currentState;

	public CrewImpl(){
		//Crew is initially asleep
		currentState = CrewState.ASLEEP;
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
		else
			return "unknown";
	}

	public void tick(){
		System.out.println("Crew has been ticked!");
	}
}
