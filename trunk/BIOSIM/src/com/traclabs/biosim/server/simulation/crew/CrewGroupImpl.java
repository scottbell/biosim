package biosim.server.crew;

// The package containing our stubs.
import SIMULATION.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class CrewImpl extends CrewPOA {
	private Schedule mySchedule;
	private Hashtable crewPeople;

	public CrewImpl(){
		//use default schedule
		mySchedule = new Schedule();
		crewPeople = new Hashtable();
	}

	public CrewImpl(File pScheduleFile){
		mySchedule = new Schedule(pScheduleFile);
		crewPeople = new Hashtable();
	}
	
	public String getModuleName(){
		return "Crew";
	}

	public void createCrewPerson(String name){
		CrewPersonImpl newCrewPerson = new CrewPersonImpl(name);
		crewPeople.put(name, newCrewPerson);
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
	
	public CrewPersonImpl getCrewPerson(String crewPersonName){
		return (CrewPersonImpl)(crewPeople.get(crewPersonName));
	}

	public void tick(){
		processState();
	}
	
	private void processState(){
	}
}
