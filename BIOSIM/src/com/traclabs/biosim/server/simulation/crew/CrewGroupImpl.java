package biosim.server.crew;

import java.io.*;
import java.net.*;
import java.util.*;
import biosim.server.util.*;
import biosim.idl.crew.*;
import biosim.idl.util.log.*;
import biosim.server.framework.*;
import biosim.idl.framework.*;
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
	private float healthyPercentage = 1f;
	private Random myRandom;

	/**
	* Default constructor.  Uses a default schedule.
	*/
	public CrewGroupImpl(int pID){
		super(pID);
		//use default schedule
		mySchedule = new Schedule();
		crewPeople = new Hashtable();
		myRandom = new Random();
	}

	/**
	* Constructor that takes a schedule file and uses it.
	* @param pScheduleFile the schedule file for the crew persons to use.
	*/
	public CrewGroupImpl(int pID, URL pScheduleURL){
		super(pID);
		mySchedule = new Schedule(pScheduleURL);
		crewPeople = new Hashtable();
	}

	/**
	* Returns the name of this module (CrewGroup)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "CrewGroup"+getID();
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
			System.err.println("Couldn't find Activity by that name!");
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
			System.err.println("Couldn't find Activity by that order!");
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
	
	protected String getMalfunctionName(MalfunctionIntensity pIntensity, MalfunctionLength pLength){
		String returnName = new String();
		if (pIntensity == MalfunctionIntensity.SEVERE_MALF)
			returnName += "Severe ";
		else if (pIntensity == MalfunctionIntensity.MEDIUM_MALF)
			returnName += "Medium ";
		else if (pIntensity == MalfunctionIntensity.LOW_MALF)
			returnName += "Low ";
		if (pLength == MalfunctionLength.TEMPORARY_MALF)
			returnName += "Sickness (Temporary)";
		else if (pLength == MalfunctionLength.PERMANENT_MALF)
			returnName += "Sickness (Permanent)";
		return returnName;
	}

	/**
	* Processes a tick by ticking each crew person it knows about.
	*/
	public void tick(){
		if (isMalfunctioning())
			performMalfunctions();
		int peopleAsleep = (new Float((1 - healthyPercentage) * crewPeople.size())).intValue();
		for (int i = 0; i < peopleAsleep; i ++){
			int randomIndex = myRandom.nextInt(crewPeople.size());
			CrewPersonImpl tempPerson = (CrewPersonImpl)((crewPeople.values().toArray())[randomIndex]);
			tempPerson.sicken();
		}
		for (Enumeration e = crewPeople.elements(); e.hasMoreElements();){
			CrewPersonImpl tempPerson = (CrewPersonImpl)(e.nextElement());
			tempPerson.tick();
		}
		if (moduleLogging)
			log();
	}
	
	private void performMalfunctions(){
		healthyPercentage = 1f;
		for (Enumeration e = myMalfunctions.elements(); e.hasMoreElements();){
			Malfunction currentMalfunction = (Malfunction)(e.nextElement());
			if (currentMalfunction.getLength() == MalfunctionLength.TEMPORARY_MALF){
				if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
					healthyPercentage *= 0.50;
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
					healthyPercentage *= 0.25;
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
					healthyPercentage *= 0.10;
			}
			else if (currentMalfunction.getLength() == MalfunctionLength.PERMANENT_MALF){
				if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
					healthyPercentage *= 0.50;
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
					healthyPercentage *= 0.25;
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
					healthyPercentage *= 0.10;
			}
		}
	}

	/**
	* Resets the schedule and deletes all the crew persons
	*/
	public void reset(){
		mySchedule.reset();
		crewPeople = new Hashtable();
	}
	
	public boolean isDead(){
		if (crewPeople.size() < 1)
			return false;
		boolean areTheyDead = true;
		for (Enumeration e = crewPeople.elements(); e.hasMoreElements(); ){
				CrewPersonImpl currentPerson = (CrewPersonImpl)(e.nextElement());
				areTheyDead = areTheyDead && currentPerson.isDead();
		}
		return areTheyDead;
	}

	private void log(){
		//If not initialized, fill in the log
		if (!logInitialized){
			crewPeopleLogs = new Hashtable();
			int i = 0;
			for (Enumeration e = crewPeople.elements(); e.hasMoreElements(); ){
				CrewPersonImpl currentPerson = (CrewPersonImpl)(e.nextElement());
				LogNode newPersonLabel = myLog.addChild("crew_person");
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
	
	public int getCrewSize(){
		return crewPeople.size();
	}

	public float getGreyWaterProduced(){
		float totalGreyWaterProduced = 0f;
		for (Enumeration e = crewPeople.elements(); e.hasMoreElements(); ){
				CrewPersonImpl currentPerson = (CrewPersonImpl)(e.nextElement());
				totalGreyWaterProduced += currentPerson.getGreyWaterProduced();
		}
		return totalGreyWaterProduced;
	}
	
	public float getDirtyWaterProduced(){
		float totalDirtyWaterProduced = 0f;
		for (Enumeration e = crewPeople.elements(); e.hasMoreElements(); ){
				CrewPersonImpl currentPerson = (CrewPersonImpl)(e.nextElement());
				totalDirtyWaterProduced += currentPerson.getDirtyWaterProduced();
		}
		return totalDirtyWaterProduced;
	}
	
	public float getPotableWaterConsumed(){
		float totalPotableWaterConsumed = 0f;
		for (Enumeration e = crewPeople.elements(); e.hasMoreElements(); ){
				CrewPersonImpl currentPerson = (CrewPersonImpl)(e.nextElement());
				totalPotableWaterConsumed += currentPerson.getPotableWaterConsumed();
		}
		return totalPotableWaterConsumed;
	}
	
	public float getFoodConsumed(){
		float totalFoodConsumed = 0f;
		for (Enumeration e = crewPeople.elements(); e.hasMoreElements(); ){
				CrewPersonImpl currentPerson = (CrewPersonImpl)(e.nextElement());
				totalFoodConsumed += currentPerson.getFoodConsumed();
		}
		return totalFoodConsumed;
	}
	
	public float getCO2Produced(){
		float totalCO2Produced = 0f;
		for (Enumeration e = crewPeople.elements(); e.hasMoreElements(); ){
				CrewPersonImpl currentPerson = (CrewPersonImpl)(e.nextElement());
				totalCO2Produced += currentPerson.getCO2Produced();
		}
		return totalCO2Produced;
	}
	
	public float getO2Consumed(){
		float totalO2Consumed = 0f;
		for (Enumeration e = crewPeople.elements(); e.hasMoreElements(); ){
				CrewPersonImpl currentPerson = (CrewPersonImpl)(e.nextElement());
				totalO2Consumed += currentPerson.getO2Consumed();
		}
		return totalO2Consumed;
	}
}
