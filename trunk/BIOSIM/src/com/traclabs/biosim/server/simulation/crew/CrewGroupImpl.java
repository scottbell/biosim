package biosim.server.crew;

// The package containing our stubs.
import SIMULATION.*;
import java.io.*;
import java.net.*;

public class CrewImpl extends CrewPOA {
	
	private Activity currentActivity;
	private Schedule mySchedule;

	public CrewImpl(){
		//use default schedule
		mySchedule = new Schedule();
	}

	public CrewImpl(File pScheduleFile){
		mySchedule = new Schedule(pScheduleFile);
	}

	public void setCurrentActivity(Activity newActivity){
		if (currentActivity != newActivity){
			currentActivity = newActivity;
			System.out.println("Changed activity to "+currentActivity.name);
		}
	}
	
	public String getModuleName(){
		return "Crew";
	}

	public Activity getScheduledActivityByName(String name){
		Activity foundActivity = mySchedule.getActivityByName(name);
		if (foundActivity != null)
			return foundActivity;
		else{
			System.out.println("Couldn't find Activity by that name!");
			return null;
		}
	}

	public Activity getScheduledActivityByOrder(int order){
		Activity foundActivity = mySchedule.getActivityByOrder(order);
		if (foundActivity != null)
			return foundActivity;
		else{
			System.out.println("Couldn't find Activity by that order!");
			return null;
		}
	}

	public Activity getCurrentActivity(){
		return currentActivity;
	}

	public void tick(){
		processState();
	}
	
	private void processState(){
	}
}
