package biosim.server.crew;

import java.io.*;
import java.net.*;
import java.util.*;
import biosim.server.util.*;
import biosim.idl.crew.*;
import biosim.idl.util.*;
import biosim.server.framework.*;
/**
 * The Crew Implementation.  Holds multiple crew persons and their schedule.
 *
 * @author    Scott Bell
 */

public class CrewGroupImpl extends BioModuleImpl implements CrewGroupOperations {
	//The schedule using by the crew.  Contains the activities (and order) which the crew persons perform.
	private Schedule mySchedule;
	//The crew persons that make up the crew.  
	//They are the ones consuming air/food/water and producing air/water/waste as they perform activities
	private Hashtable crewPeople;
	private Hashtable crewPeopleLogs;
	
	/**
	* Default constructor.  Uses a default schedule.
	*/
	public CrewGroupImpl(){
		//use default schedule
		mySchedule = new Schedule();
		crewPeople = new Hashtable();
	}
	
	/**
	* Constructor that takes a schedule file and uses it.
	* @param pScheduleFile the schedule file for the crew persons to use.
	*/
	public CrewGroupImpl(File pScheduleFile){
		mySchedule = new Schedule(pScheduleFile);
		crewPeople = new Hashtable();
	}
	
	/**
	* Returns the name of this module (CrewGroup)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "CrewGroup";
	}
	
	/**
	* Creates a crew person and adds them to the crew
	* @param pName the name of the new crew person
	* @param pAge the age of the new crew person
	* @param pWeight the weight of the new crew person
	* @param pSex the sex of the new crew person
	* @return the crew person just created
	*/
	public CrewPerson createCrewPerson(String pName, float pAge, float pWeight, Sex pSex){
		CrewPersonImpl newCrewPerson = new CrewPersonImpl(pName, pAge, pWeight, pSex, this);
		crewPeople.put(pName, newCrewPerson);
		return CrewPersonHelper.narrow((OrbUtils.poaToCorbaObj(newCrewPerson)));
	}
	
	/**
	* Returns all the current crew persons who are in the crew
	* @return an array of the crew persons in the crew
	*/
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
	
	/**
	* Returns a scheduled activity by name (like "sleeping")
	* @param name the name of the activity to fetch
	* @return the activity in the schedule asked for by name
	*/
	public Activity getScheduledActivityByName(String name){
		ActivityImpl foundActivity = mySchedule.getActivityByName(name);
		if (foundActivity != null)
			return ActivityHelper.narrow((OrbUtils.poaToCorbaObj(foundActivity)));
		else{
			System.out.println("Couldn't find Activity by that name!");
			return null;
		}
	}
	
	/**
	* Returns a scheduled activity by order
	* @param order the order of the activity to fetch
	* @return the activity in the schedule asked for by order
	*/
	public Activity getScheduledActivityByOrder(int order){
		ActivityImpl foundActivity = mySchedule.getActivityByOrder(order);
		if (foundActivity != null)
			return ActivityHelper.narrow((OrbUtils.poaToCorbaObj(foundActivity)));
		else{
			System.out.println("Couldn't find Activity by that order!");
			return null;
		}
	}
	
	/**
	* Returns a crew person given their name
	* @param crewPersonName the name of the crew person to fetch
	* @return the crew person asked for
	*/
	public CrewPerson getCrewPerson(String crewPersonName){
		CrewPersonImpl foundPerson = (CrewPersonImpl)(crewPeople.get(crewPersonName));
		return CrewPersonHelper.narrow((OrbUtils.poaToCorbaObj(foundPerson)));
	}
	
	/**
	* Returns the number of activities in the schedule
	* @return the number of activities in the schedule
	*/
	protected int getNumberOfActivities(){
		return mySchedule.getNumberOfActivities();
	}
	
	/**
	* Processes a tick by ticking each crew person it knows about.
	*/
	public void tick(){
		for (Enumeration e = crewPeople.elements(); e.hasMoreElements(); ){
			CrewPersonImpl tempPerson = (CrewPersonImpl)(e.nextElement());
			tempPerson.tick();
		}
		if (moduleLogging)
			log();
	}
	
	/**
	* Resets the schedule and deletes all the crew persons
	*/
	public void reset(){
		mySchedule.reset();
		crewPeople = new Hashtable();
	}
	
	private void log(){
		//If not initialized, fill in the log
		if (!logInitialized){
			crewPeopleLogs = new Hashtable();
			int i = 0;
			for (Enumeration e = crewPeople.elements(); e.hasMoreElements(); ){
				CrewPersonImpl currentPerson = (CrewPersonImpl)(e.nextElement());
				LogNode newPersonLabel = myLog.getHead().addChild(currentPerson.getName());
				crewPeopleLogs.put(currentPerson, newPersonLabel);
				currentPerson.log(newPersonLabel);
				i++;
			}
			logInitialized = true;
		}
		else{
			for (Enumeration e = crewPeopleLogs.keys(); e.hasMoreElements();){
				CrewPersonImpl currentPerson = (CrewPersonImpl)(e.nextElement());
				LogNode crewPersonLabel = (LogNode)(crewPeopleLogs.get(currentPerson));
				currentPerson.log(crewPersonLabel);
			}
		}
		sendLog(myLog);
	}
}
