package biosim.server.crew;

import java.io.*;
import java.net.*;
import java.util.*;
import biosim.server.util.*;

public class CrewGroupImpl extends ALSS.CrewPOA {
	private Schedule mySchedule;
	private Hashtable crewPeople;

	public CrewGroupImpl(){
		//use default schedule
		mySchedule = new Schedule();
		crewPeople = new Hashtable();
	}

	public CrewGroupImpl(File pScheduleFile){
		mySchedule = new Schedule(pScheduleFile);
		crewPeople = new Hashtable();
	}
	
	public String getModuleName(){
		return "CrewGroup";
	}

	public CrewPersonImpl createCrewPerson(String name){
		CrewPersonImpl newCrewPerson = new CrewPersonImpl(name);
		crewPeople.put(name, newCrewPerson);
		return newCrewPerson;
	}

	public org.omg.CORBA.Object getScheduledActivityByName(String name){
		ActivityImpl foundActivity = mySchedule.getActivityByName(name);
		if (foundActivity != null)
			return (ALSSUtils.poaToCorbaObj(foundActivity));
		else{
			System.out.println("Couldn't find Activity by that name!");
			return null;
		}
	}

	public ActivityImpl getScheduledActivityByOrder(int order){
		return null;
		/*
		ALSS.Activity foundActivity = mySchedule.getActivityByOrder(order);
		if (foundActivity != null)
			return foundActivity;
		else{
			System.out.println("Couldn't find Activity by that order!");
			return null;
		}
		*/
	}
	
	public CrewPersonImpl getCrewPerson(String crewPersonName){
		return (CrewPersonImpl)(crewPeople.get(crewPersonName));
	}

	public void tick(){
		processState();
		System.out.println("CrewGroup has been ticked!");
	}
	
	private void processState(){
	}
}
