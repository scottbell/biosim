package biosim.server.crew;

import java.io.*;
import java.net.*;
import java.util.*;
import biosim.server.util.*;
import biosim.idl.crew.*;
import biosim.server.framework.*;

public class CrewGroupImpl extends BioModuleImpl implements CrewGroupOperations {
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

	public CrewPerson createCrewPerson(String pName, float pAge, float pWeight, Sex pSex){
		CrewPersonImpl newCrewPerson = new CrewPersonImpl(pName, pAge, pWeight, pSex, this);
		crewPeople.put(pName, newCrewPerson);
		return CrewPersonHelper.narrow((OrbUtils.poaToCorbaObj(newCrewPerson)));
	}
	
	public CrewPerson[] getCrewPeople(){
		CrewPerson[] theCrew = new CrewPerson[crewPeople.size()];
		int i = 0;
		for (Enumeration e = crewPeople.elements(); e.hasMoreElements(); ){
			CrewPersonImpl tempPerson = (CrewPersonImpl)(e.nextElement());
			theCrew[i] = CrewPersonHelper.narrow(OrbUtils.poaToCorbaObj(tempPerson));
			i++;
		}
		return theCrew;
	}

	public Activity getScheduledActivityByName(String name){
		ActivityImpl foundActivity = mySchedule.getActivityByName(name);
		if (foundActivity != null)
			return ActivityHelper.narrow((OrbUtils.poaToCorbaObj(foundActivity)));
		else{
			System.out.println("Couldn't find Activity by that name!");
			return null;
		}
	}
	
	public Activity getScheduledActivityByOrder(int order){
		ActivityImpl foundActivity = mySchedule.getActivityByOrder(order);
		if (foundActivity != null)
			return ActivityHelper.narrow((OrbUtils.poaToCorbaObj(foundActivity)));
		else{
			System.out.println("Couldn't find Activity by that order!");
			return null;
		}
	}

	public CrewPerson getCrewPerson(String crewPersonName){
		CrewPersonImpl foundPerson = (CrewPersonImpl)(crewPeople.get(crewPersonName));
		return CrewPersonHelper.narrow((OrbUtils.poaToCorbaObj(foundPerson)));
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
