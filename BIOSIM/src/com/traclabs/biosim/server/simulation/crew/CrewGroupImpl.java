package biosim.server.crew;

import java.io.*;
import java.net.*;
import java.util.*;
import biosim.server.util.*;
import biosim.idl.crew.*;

public class CrewGroupImpl extends CrewGroupPOA {
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

	public org.omg.CORBA.Object createCrewPerson(String name){
		CrewPersonImpl newCrewPerson = new CrewPersonImpl(name, this);
		crewPeople.put(name, newCrewPerson);
		return (OrbUtils.poaToCorbaObj(newCrewPerson));
	}

	public org.omg.CORBA.Object getScheduledActivityByName(String name){
		ActivityImpl foundActivity = mySchedule.getActivityByName(name);
		if (foundActivity != null)
			return (OrbUtils.poaToCorbaObj(foundActivity));
		else{
			System.out.println("Couldn't find Activity by that name!");
			return null;
		}
	}

	public org.omg.CORBA.Object getScheduledActivityByOrder(int order){
		ActivityImpl foundActivity = mySchedule.getActivityByOrder(order);
		if (foundActivity != null)
			return (OrbUtils.poaToCorbaObj(foundActivity));
		else{
			System.out.println("Couldn't find Activity by that order!");
			return null;
		}
	}
	
	protected ActivityImpl getRawActivityByName(String name){
		ActivityImpl foundActivity = mySchedule.getActivityByName(name);
		if (foundActivity != null)
			return foundActivity;
		else{
			System.out.println("Couldn't find Activity by that name!");
			return null;
		}
	}

	protected ActivityImpl getRawActivityByOrder(int order){
		ActivityImpl foundActivity = mySchedule.getActivityByOrder(order);
		if (foundActivity != null)
			return foundActivity;
		else{
			System.out.println("Couldn't find Activity by that order!");
			return null;
		}
	}

	public org.omg.CORBA.Object getCrewPerson(String crewPersonName){
		CrewPersonImpl foundPerson = (CrewPersonImpl)(crewPeople.get(crewPersonName));
		return (OrbUtils.poaToCorbaObj(foundPerson));
	}
	
	protected int getNumberOfActivities(){
		return mySchedule.getNumberOfActivities();
	}

	public void tick(){
		processTick();
	}

	private void processTick(){
		for (Enumeration e = crewPeople.elements(); e.hasMoreElements(); ){
			CrewPersonImpl tempPerson = (CrewPersonImpl)(e.nextElement());
			tempPerson.processTick();
		}
	}
}
