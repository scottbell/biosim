package biosim.server.simulation.crew;

import java.io.*;
import java.net.*;
import java.util.*;
import biosim.server.util.*;
import biosim.idl.simulation.crew.*;
import biosim.idl.util.log.*;
import biosim.idl.simulation.air.*;
import biosim.idl.simulation.water.*;
import biosim.idl.simulation.food.*;
import biosim.idl.simulation.environment.*;
import biosim.server.simulation.framework.*;
import biosim.idl.simulation.framework.*;
import biosim.idl.framework.*;
/**
 * The Crew Implementation.  Holds multiple crew persons and their schedule.
 *
 * @author    Scott Bell
 */

public class CrewGroupImpl extends SimBioModuleImpl implements CrewGroupOperations, AirConsumerOperations, PotableWaterConsumerOperations, FoodConsumerOperations, AirProducerOperations, GreyWaterProducerOperations, DirtyWaterProducerOperations {
	//The crew persons that make up the crew.
	//They are the ones consuming air/food/water and producing air/water/waste as they perform activities
	private Map crewPeople;
	private Map crewPeopleLogs;
	private float healthyPercentage = 1f;
	private Random myRandom;
	private FoodStore[] myFoodStores;
	private PotableWaterStore[] myPotableWaterStores;
	private GreyWaterStore[] myGreyWaterStores;
	private DirtyWaterStore[] myDirtyWaterStores;
	private SimEnvironment[] myAirInputs;
	private SimEnvironment[] myAirOutputs;
	private float[] foodMaxFlowRates;
	private float[] potableWaterMaxFlowRates;
	private float[] greyWaterMaxFlowRates;
	private float[] dirtyWaterMaxFlowRates;
	private float[] airInMaxFlowRates;
	private float[] airOutMaxFlowRates;
	private float[] foodActualFlowRates;
	private float[] potableWaterActualFlowRates;
	private float[] greyWaterActualFlowRates;
	private float[] dirtyWaterActualFlowRates;
	private float[] airInActualFlowRates;
	private float[] airOutActualFlowRates;
	private float[] foodDesiredFlowRates;
	private float[] potableWaterDesiredFlowRates;
	private float[] greyWaterDesiredFlowRates;
	private float[] dirtyWaterDesiredFlowRates;
	private float[] airInDesiredFlowRates;
	private float[] airOutDesiredFlowRates;

	/**
	* Default constructor.  Uses a default schedule.
	*/
	public CrewGroupImpl(int pID){
		super(pID);
		crewPeople = new Hashtable();
		myRandom = new Random();
		myFoodStores = new FoodStore[0];
		myPotableWaterStores = new PotableWaterStore[0];
		myGreyWaterStores = new GreyWaterStore[0];
		myDirtyWaterStores = new DirtyWaterStore[0];
		myAirInputs = new SimEnvironment[0];
		myAirOutputs = new SimEnvironment[0];
		foodMaxFlowRates = new float[0];
		potableWaterMaxFlowRates = new float[0];
		greyWaterMaxFlowRates = new float[0];
		dirtyWaterMaxFlowRates = new float[0];
		airInMaxFlowRates = new float[0];
		airOutMaxFlowRates = new float[0];
		foodActualFlowRates = new float[0];
		potableWaterActualFlowRates = new float[0];
		greyWaterActualFlowRates = new float[0];
		dirtyWaterActualFlowRates = new float[0];
		airInActualFlowRates = new float[0];
		airOutActualFlowRates = new float[0];
		foodDesiredFlowRates = new float[0];
		potableWaterDesiredFlowRates = new float[0];
		greyWaterDesiredFlowRates = new float[0];
		dirtyWaterDesiredFlowRates = new float[0];
		airInDesiredFlowRates = new float[0];
		airOutDesiredFlowRates = new float[0];
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
		for (Iterator iter = crewPeople.values().iterator(); iter.hasNext(); i++){
			CrewPersonImpl tempPerson = (CrewPersonImpl)(iter.next());
			theCrew[i] = CrewPersonHelper.narrow(OrbUtils.poaToCorbaObj(tempPerson));
		}
		return theCrew;
	}

	public void scheduleRepair(String moduleName, long malfunctionID, int timeLength){
		int randomCrewIndex = myRandom.nextInt(crewPeople.size());
		CrewPersonImpl randomCrewPerson = (CrewPersonImpl)((crewPeople.values().toArray())[randomCrewIndex]);
		RepairActivityImpl newRepairActivityImpl = new RepairActivityImpl(moduleName, malfunctionID, timeLength);
		RepairActivity newRepairActivity = RepairActivityHelper.narrow(OrbUtils.poaToCorbaObj(new RepairActivityPOATie(newRepairActivityImpl)));
		randomCrewPerson.insertActivityInScheduleNow(newRepairActivity);
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

	protected String getMalfunctionName(MalfunctionIntensity pIntensity, MalfunctionLength pLength){
		StringBuffer returnBuffer = new StringBuffer();
		if (pIntensity == MalfunctionIntensity.SEVERE_MALF)
			returnBuffer.append("Severe ");
		else if (pIntensity == MalfunctionIntensity.MEDIUM_MALF)
			returnBuffer.append("Medium ");
		else if (pIntensity == MalfunctionIntensity.LOW_MALF)
			returnBuffer.append("Low ");
		if (pLength == MalfunctionLength.TEMPORARY_MALF)
			returnBuffer.append("Sickness (Temporary)");
		else if (pLength == MalfunctionLength.PERMANENT_MALF)
			returnBuffer.append("Sickness (Permanent)");
		return returnBuffer.toString();
	}

	/**
	* Processes a tick by ticking each crew person it knows about.
	*/
	public void tick(){
		super.tick();
		for (Iterator iter = crewPeople.values().iterator(); iter.hasNext();){
			CrewPersonImpl tempPerson = (CrewPersonImpl)(iter.next());
			tempPerson.tick();
		}

	}

	protected void performMalfunctions(){
		healthyPercentage = 1f;
		for (Iterator iter = myMalfunctions.values().iterator(); iter.hasNext(); ){
			Malfunction currentMalfunction = (Malfunction)(iter.next());
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

		int peopleAsleep = (new Float((1 - healthyPercentage) * crewPeople.size())).intValue();
		for (int i = 0; i < peopleAsleep; i ++){
			int randomIndex = myRandom.nextInt(crewPeople.size());
			CrewPersonImpl tempPerson = (CrewPersonImpl)((crewPeople.values().toArray())[randomIndex]);
			tempPerson.sicken();
		}
	}

	/**
	* Resets the schedule and deletes all the crew persons
	*/
	public void reset(){
		super.reset();
		for (Iterator iter = crewPeople.values().iterator(); iter.hasNext();){
			CrewPersonImpl currentPerson = (CrewPersonImpl)(iter.next());
			currentPerson.reset();
		}
	}

	public boolean isDead(){
		if (crewPeople.size() < 1)
			return false;
		boolean areTheyDead = true;
		for (Iterator iter = crewPeople.values().iterator(); iter.hasNext();){
			CrewPersonImpl currentPerson = (CrewPersonImpl)(iter.next());
			areTheyDead = areTheyDead && currentPerson.isDead();
		}
		return areTheyDead;
	}

	protected void log(){
		//If not initialized, fill in the log
		if (!logInitialized){
			crewPeopleLogs = new Hashtable();
			int i = 0;
			for (Iterator iter = crewPeople.values().iterator(); iter.hasNext();){
				CrewPersonImpl currentPerson = (CrewPersonImpl)(iter.next());
				LogNode newPersonLabel = myLog.addChild("crew_person");
				crewPeopleLogs.put(currentPerson, newPersonLabel);
				currentPerson.log(newPersonLabel);
				i++;
			}
			logInitialized = true;
		}
		else{
			for (Iterator iter = crewPeopleLogs.keySet().iterator(); iter.hasNext();){
				CrewPersonImpl currentPerson = (CrewPersonImpl)(iter.next());
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
		for (Iterator iter = crewPeople.values().iterator(); iter.hasNext();){
			CrewPersonImpl currentPerson = (CrewPersonImpl)(iter.next());
			totalGreyWaterProduced += currentPerson.getGreyWaterProduced();
		}
		return totalGreyWaterProduced;
	}

	public float getDirtyWaterProduced(){
		float totalDirtyWaterProduced = 0f;
		for (Iterator iter = crewPeople.values().iterator(); iter.hasNext();){
			CrewPersonImpl currentPerson = (CrewPersonImpl)(iter.next());
			totalDirtyWaterProduced += currentPerson.getDirtyWaterProduced();
		}
		return totalDirtyWaterProduced;
	}

	public float getPotableWaterConsumed(){
		float totalPotableWaterConsumed = 0f;
		for (Iterator iter = crewPeople.values().iterator(); iter.hasNext();){
			CrewPersonImpl currentPerson = (CrewPersonImpl)(iter.next());
			totalPotableWaterConsumed += currentPerson.getPotableWaterConsumed();
		}
		return totalPotableWaterConsumed;
	}

	public float getFoodConsumed(){
		float totalFoodConsumed = 0f;
		for (Iterator iter = crewPeople.values().iterator(); iter.hasNext();){
			CrewPersonImpl currentPerson = (CrewPersonImpl)(iter.next());
			totalFoodConsumed += currentPerson.getFoodConsumed();
		}
		return totalFoodConsumed;
	}

	public float getCO2Produced(){
		float totalCO2Produced = 0f;
		for (Iterator iter = crewPeople.values().iterator(); iter.hasNext();){
			CrewPersonImpl currentPerson = (CrewPersonImpl)(iter.next());
			totalCO2Produced += currentPerson.getCO2Produced();
		}
		return totalCO2Produced;
	}

	public float getO2Consumed(){
		float totalO2Consumed = 0f;
		for (Iterator iter = crewPeople.values().iterator(); iter.hasNext();){
			CrewPersonImpl currentPerson = (CrewPersonImpl)(iter.next());
			totalO2Consumed += currentPerson.getO2Consumed();
		}
		return totalO2Consumed;
	}

	//Air Input
	public void setAirInputMaxFlowRate(float liters, int index){
		airInMaxFlowRates[index] = liters;
	}
	public float getAirInputMaxFlowRate(int index){
		return airInMaxFlowRates[index];
	}
	public float[] getAirInputMaxFlowRates(){
		return airInMaxFlowRates;
	}
	public void setAirInputDesiredFlowRate(float liters, int index){
		airInDesiredFlowRates[index] = liters;
	}
	public float getAirInputDesiredFlowRate(int index){
		return airInDesiredFlowRates[index];
	}
	public float[] getAirInputDesiredFlowRates(){
		return airInDesiredFlowRates;
	}
	public float getAirInputActualFlowRate(int index){
		return airInActualFlowRates[index];
	}
	public float[] getAirInputActualFlowRates(){
		return airInActualFlowRates;
	}
	public void setAirInputs(SimEnvironment[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		myAirInputs = sources;
		airInMaxFlowRates = maxFlowRates;
		airInDesiredFlowRates = desiredFlowRates;
		airInActualFlowRates = new float[airInDesiredFlowRates.length];
	}
	public SimEnvironment[] getAirInputs(){
		return myAirInputs;
	}

	//Air Output
	public void setAirOutputMaxFlowRate(float liters, int index){
		airOutMaxFlowRates[index] = liters;
	}
	public float getAirOutputMaxFlowRate(int index){
		return airOutMaxFlowRates[index];
	}
	public float[] getAirOutputMaxFlowRates(){
		return airOutMaxFlowRates;
	}
	public void setAirOutputDesiredFlowRate(float liters, int index){
		airOutDesiredFlowRates[index] = liters;
	}
	public float getAirOutputDesiredFlowRate(int index){
		return airOutDesiredFlowRates[index];
	}
	public float[] getAirOutputDesiredFlowRates(){
		return airOutDesiredFlowRates;
	}
	public float getAirOutputActualFlowRate(int index){
		return airOutActualFlowRates[index];
	}
	public float[] getAirOutputActualFlowRates(){
		return airOutActualFlowRates;
	}
	public void setAirOutputs(SimEnvironment[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		myAirOutputs = sources;
		airOutMaxFlowRates = maxFlowRates;
		airOutDesiredFlowRates = desiredFlowRates;
		airOutActualFlowRates = new float[airOutDesiredFlowRates.length];
	}
	public SimEnvironment[] getAirOutputs(){
		return myAirOutputs;
	}

	//Potable Water Input
	public void setPotableWaterInputMaxFlowRate(float liters, int index){
		potableWaterMaxFlowRates[index] = liters;
	}
	public float getPotableWaterInputMaxFlowRate(int index){
		return potableWaterMaxFlowRates[index];
	}
	public float[] getPotableWaterInputMaxFlowRates(){
		return potableWaterMaxFlowRates;
	}
	public void setPotableWaterInputDesiredFlowRate(float liters, int index){
		potableWaterDesiredFlowRates[index] = liters;
	}
	public float getPotableWaterInputDesiredFlowRate(int index){
		return potableWaterDesiredFlowRates[index];
	}
	public float[] getPotableWaterInputDesiredFlowRates(){
		return potableWaterDesiredFlowRates;
	}
	public float getPotableWaterInputActualFlowRate(int index){
		return potableWaterActualFlowRates[index];
	}
	public float[] getPotableWaterInputActualFlowRates(){
		return potableWaterActualFlowRates;
	}
	public void setPotableWaterInputs(PotableWaterStore[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		myPotableWaterStores = sources;
		potableWaterMaxFlowRates = maxFlowRates;
		potableWaterDesiredFlowRates = desiredFlowRates;
		potableWaterActualFlowRates = new float[potableWaterDesiredFlowRates.length];
	}
	public PotableWaterStore[] getPotableWaterInputs(){
		return myPotableWaterStores;
	}

	//Grey Water Output
	public void setGreyWaterOutputMaxFlowRate(float liters, int index){
		greyWaterMaxFlowRates[index] = liters;
	}
	public float getGreyWaterOutputMaxFlowRate(int index){
		return greyWaterMaxFlowRates[index];
	}
	public float[] getGreyWaterOutputMaxFlowRates(){
		return greyWaterMaxFlowRates;
	}
	public void setGreyWaterOutputDesiredFlowRate(float liters, int index){
		greyWaterDesiredFlowRates[index] = liters;
	}
	public float getGreyWaterOutputDesiredFlowRate(int index){
		return greyWaterDesiredFlowRates[index];
	}
	public float[] getGreyWaterOutputDesiredFlowRates(){
		return greyWaterDesiredFlowRates;
	}
	public float getGreyWaterOutputActualFlowRate(int index){
		return greyWaterActualFlowRates[index];
	}
	public float[] getGreyWaterOutputActualFlowRates(){
		return greyWaterActualFlowRates;
	}
	public void setGreyWaterOutputs(GreyWaterStore[] destinations, float[] maxFlowRates, float[] desiredFlowRates){
		myGreyWaterStores = destinations;
		greyWaterMaxFlowRates = maxFlowRates;
		greyWaterDesiredFlowRates = desiredFlowRates;
		greyWaterActualFlowRates = new float[greyWaterDesiredFlowRates.length];
	}
	public GreyWaterStore[] getGreyWaterOutputs(){
		return myGreyWaterStores;
	}

	//Dirty Water Output
	public void setDirtyWaterOutputMaxFlowRate(float liters, int index){
		dirtyWaterMaxFlowRates[index] = liters;
	}
	public float getDirtyWaterOutputMaxFlowRate(int index){
		return dirtyWaterMaxFlowRates[index];
	}
	public float[] getDirtyWaterOutputMaxFlowRates(){
		return dirtyWaterMaxFlowRates;
	}
	public void setDirtyWaterOutputDesiredFlowRate(float liters, int index){
		dirtyWaterDesiredFlowRates[index] = liters;
	}
	public float getDirtyWaterOutputDesiredFlowRate(int index){
		return dirtyWaterDesiredFlowRates[index];
	}
	public float[] getDirtyWaterOutputDesiredFlowRates(){
		return dirtyWaterDesiredFlowRates;
	}
	public float getDirtyWaterOutputActualFlowRate(int index){
		return dirtyWaterActualFlowRates[index];
	}
	public float[] getDirtyWaterOutputActualFlowRates(){
		return dirtyWaterActualFlowRates;
	}
	public void setDirtyWaterOutputs(DirtyWaterStore[] destinations, float[] maxFlowRates, float[] desiredFlowRates){
		myDirtyWaterStores = destinations;
		dirtyWaterMaxFlowRates = maxFlowRates;
		dirtyWaterDesiredFlowRates = desiredFlowRates;
		dirtyWaterActualFlowRates = new float[dirtyWaterDesiredFlowRates.length];
	}
	public DirtyWaterStore[] getDirtyWaterOutputs(){
		return myDirtyWaterStores;
	}

	//Food Water Input
	public void setFoodInputMaxFlowRate(float kilograms, int index){
		foodMaxFlowRates[index] = kilograms;
	}
	public float getFoodInputMaxFlowRate(int index){
		return foodMaxFlowRates[index];
	}
	public float[] getFoodInputMaxFlowRates(){
		return foodMaxFlowRates;
	}
	public void setFoodInputDesiredFlowRate(float kilograms, int index){
		foodDesiredFlowRates[index] = kilograms;
	}
	public float getFoodInputDesiredFlowRate(int index){
		return foodDesiredFlowRates[index];
	}
	public float[] getFoodInputDesiredFlowRates(){
		return foodDesiredFlowRates;
	}
	public float getFoodInputActualFlowRate(int index){
		return foodActualFlowRates[index];
	}
	public float[] getFoodInputActualFlowRates(){
		return foodActualFlowRates;
	}
	public void setFoodInputs(FoodStore[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		myFoodStores = sources;
		foodMaxFlowRates = maxFlowRates;
		foodDesiredFlowRates = desiredFlowRates;
		foodActualFlowRates = new float[foodDesiredFlowRates.length];
	}
	public FoodStore[] getFoodInputs(){
		return myFoodStores;
	}
}
