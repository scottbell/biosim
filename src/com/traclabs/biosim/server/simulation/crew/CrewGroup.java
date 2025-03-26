package com.traclabs.biosim.server.simulation.crew;

import com.traclabs.biosim.server.framework.Malfunction;
import com.traclabs.biosim.server.framework.MalfunctionIntensity;
import com.traclabs.biosim.server.framework.MalfunctionLength;
import com.traclabs.biosim.server.simulation.environment.AirConsumerDefinition;
import com.traclabs.biosim.server.simulation.environment.AirProducerDefinition;
import com.traclabs.biosim.server.simulation.food.FoodConsumerDefinition;
import com.traclabs.biosim.server.simulation.framework.SimBioModule;
import com.traclabs.biosim.server.simulation.waste.DryWasteProducerDefinition;
import com.traclabs.biosim.server.simulation.water.DirtyWaterProducerDefinition;
import com.traclabs.biosim.server.simulation.water.GreyWaterProducerDefinition;
import com.traclabs.biosim.server.simulation.water.PotableWaterConsumerDefinition;
import com.traclabs.biosim.util.MersenneTwister;

import ch.qos.logback.classic.Level;

import java.util.*;

/**
 * The Crew implementation. Holds multiple crew persons and their schedule.
 * 
 * @author Scott Bell
 */

public class CrewGroup extends SimBioModule {

    //Consumers, Producers
    private FoodConsumerDefinition myFoodConsumerDefinition;

    private AirConsumerDefinition myAirConsumerDefinition;

    private PotableWaterConsumerDefinition myPotableWaterConsumerDefinition;

    private GreyWaterProducerDefinition myGreyWaterProducerDefinition;

    private DirtyWaterProducerDefinition myDirtyWaterProducerDefinition;

    private AirProducerDefinition myAirProducerDefinition;

    private DryWasteProducerDefinition myDryWasteProducerDefinition;

    //The crew persons that make up the crew.
    //They are the ones consuming air/food/water and producing air/water/waste
    // as they perform activities
    private Map<String, CrewPerson> crewPeople;

    private Random myRandom = new MersenneTwister();
    
    private boolean myDeathEnabled = true;

    private List<String> crewScheduledForRemoval = new Vector<String>();

    private List<CrewPerson> crewScheduledForAddition = new Vector<CrewPerson>();
    
    /**
     * Default constructor. Uses a default schedule.
     */
    public CrewGroup() {
    	this(0, "Unnamed CrewGroup");
    }

    public CrewGroup(int pID, String pName) {
        super(pID, pName);
        crewPeople = new Hashtable<String, CrewPerson>();

        myFoodConsumerDefinition = new FoodConsumerDefinition(this);
        myAirConsumerDefinition = new AirConsumerDefinition(this);
        myPotableWaterConsumerDefinition = new PotableWaterConsumerDefinition(this);
        myGreyWaterProducerDefinition = new GreyWaterProducerDefinition(this);
        myDirtyWaterProducerDefinition = new DirtyWaterProducerDefinition(this);
        myAirProducerDefinition = new AirProducerDefinition(this);
        myDryWasteProducerDefinition = new DryWasteProducerDefinition(this);
    }

    /**
     * Creates a crew person and adds them to the crew
     * 
     * @param pName
     *            the name of the new crew person
     * @param pAge
     *            the age of the new crew person
     * @param pWeight
     *            the weight of the new crew person
     * @param pSex
     *            the sex of the new crew person
     * @return the crew person just created
     */
    public CrewPerson createCrewPerson(String pName, float pAge, float pWeight,
            Sex pSex, int pArrivalTick, int pDepartureTick) {
        return createCrewPerson(pName, pAge, pWeight, pSex, pArrivalTick, pDepartureTick, new Schedule(getCrewGroup()));
    }
    
    private CrewGroup getCrewGroup(){
    	return this;
    }

    public CrewPerson createCrewPerson(String pName, float pAge, float pWeight,
            Sex pSex, int pArrivalTick, int pDepartureTick, Schedule pSchedule) {
        CrewPerson newCrewPerson = new CrewPerson(pName, pAge,
                pWeight, pSex, pArrivalTick, pDepartureTick, getCrewGroup(),
                pSchedule);
        crewPeople.put(pName, newCrewPerson);
        return newCrewPerson;
    }
    
    public CrewPerson createCrewPerson() {
        return createCrewPerson("UnnamedAstronaut"+ (getCrewSize() + 1), 30f, 75f, Sex.male, 0, Integer.MAX_VALUE);
    }
    
    public CrewPerson createCrewPerson(Schedule pSchedule) {
        return createCrewPerson("UnnamedAstronaut"+ (getCrewSize() + 1), 30f, 75f, Sex.male, 0, Integer.MAX_VALUE, pSchedule);
    }

    /**
     * Returns all the current crew persons who are in the crew
     * 
     * @return an array of the crew persons in the crew
     */
    public CrewPerson[] getCrewPeople() {
        CrewPerson[] theCrew = new CrewPerson[crewPeople.size()];
        int i = 0;
        for (Iterator iter = crewPeople.values().iterator(); iter.hasNext(); i++) {
            theCrew[i] = (CrewPerson) (iter.next());
        }
        return theCrew;
    }

    public void scheduleRepair(String moduleName, long malfunctionID,
            int timeLength) {
        int randomCrewIndex = myRandom.nextInt(crewPeople.size());
        CrewPerson randomCrewPerson = (CrewPerson) ((crewPeople.values()
                .toArray())[randomCrewIndex]);
        RepairActivity newRepairActivity = new RepairActivity(
                moduleName, malfunctionID, timeLength);
        randomCrewPerson.insertActivityInScheduleNow(newRepairActivity);
    }

    public void resetSchedule() {
    	for (CrewPerson currentPerson : crewPeople.values())
    		currentPerson.reset();
    }
    
    public void setLogLevel(Level pLevel){
    	super.setLogLevel(pLevel);
    	for (CrewPerson currentPerson : crewPeople.values()){
    		currentPerson.setLogLevel(pLevel);
    	}
    }

    /**
     * Returns a crew person given their name
     * 
     * @param crewPersonName
     *            the name of the crew person to fetch
     * @return the crew person asked for
     */
    public CrewPerson getCrewPerson(String crewPersonName) {
        CrewPerson foundPerson = (crewPeople.get(crewPersonName));
        return foundPerson;
    }

    protected String getMalfunctionName(MalfunctionIntensity pIntensity,
            MalfunctionLength pLength) {
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

    private void clearActualFlowRates() {
        Arrays.fill(getPotableWaterConsumerDefinition().getActualFlowRates(),
                0f);
        Arrays.fill(getGreyWaterProducerDefinition().getActualFlowRates(), 0f);
        Arrays.fill(getDirtyWaterProducerDefinition().getActualFlowRates(), 0f);
        Arrays.fill(getDryWasteProducerDefinition().getActualFlowRates(), 0f);
    }

    /**
     * Processes a tick by ticking each crew person it knows about.
     */
    public void tick() {
        super.tick();
        clearActualFlowRates();
    	for (CrewPerson currentPerson : crewPeople.values())
    		currentPerson.tick();
        //Add those scheduled
    	for (CrewPerson crewPersonToAdd : crewScheduledForAddition)
            crewPeople.put(crewPersonToAdd.getName(), crewPersonToAdd);
        crewScheduledForAddition.clear();
        //Remove those scheduled
    	for (String crewPersonNameToRemove : crewScheduledForRemoval)
            crewPeople.remove(crewPersonNameToRemove);
        crewScheduledForRemoval.clear();
    }

    protected void performMalfunctions() {
        float healthyPercentage = 1f;
        for (Malfunction currentMalfunction : myMalfunctions.values()){
                if (currentMalfunction.getLength() == MalfunctionLength.TEMPORARY_MALF) {
                if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
                    healthyPercentage *= 0.50; // 50% reduction
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
                    healthyPercentage *= 0.75; // 25% reduction
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
                    healthyPercentage *= 0.90; // 10% reduction
            } else if (currentMalfunction.getLength() == MalfunctionLength.PERMANENT_MALF) {
                if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
                    healthyPercentage *= 0.50; // 50% reduction
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
                    healthyPercentage *= 0.75; // 25% reduction
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
                    healthyPercentage *= 0.90; // 10% reduction
            }
            currentMalfunction.setPerformed(true);
        }

        int peopleAsleep = (Float.valueOf((1 - healthyPercentage)
                * crewPeople.size())).intValue();
        for (int i = 0; i < peopleAsleep; i++) {
            int randomIndex = myRandom.nextInt(crewPeople.size());
            CrewPerson tempPerson = (CrewPerson) ((crewPeople.values()
                    .toArray())[randomIndex]);
            tempPerson.sicken();
        }
    }

    /**
     * Resets the schedule and deletes all the crew persons
     */
    public void reset() {
        super.reset();
    	for (CrewPerson currentPerson : crewPeople.values())
    		currentPerson.reset();
        myFoodConsumerDefinition.reset();
        myAirConsumerDefinition.reset();
        myPotableWaterConsumerDefinition.reset();
        myGreyWaterProducerDefinition.reset();
        myDirtyWaterProducerDefinition.reset();
        myAirProducerDefinition.reset();
        myDryWasteProducerDefinition.reset();
    }
    
    public void killCrew(){
    	for (CrewPerson currentPerson : crewPeople.values())
    		currentPerson.kill();
    }

    /**
     * Gets the productivity of the crew
     */
    public float getProductivity() {
        float productivity = 0f;
    	for (CrewPerson currentPerson : crewPeople.values())
    		productivity += currentPerson.getProductivity();
        return productivity;
    }

    public boolean anyDead() {
        if (crewPeople.size() < 1)
            return false;
    	for (CrewPerson currentPerson : crewPeople.values()){
    		if (currentPerson.isDead())
                return true;
        }
        return false;
    }

    public boolean isDead() {
        if (crewPeople.size() < 1)
            return false;
        boolean areTheyDead = true;

    	for (CrewPerson currentPerson : crewPeople.values())
            areTheyDead = areTheyDead && currentPerson.isDead();
        return areTheyDead;
    }

    public int getCrewSize() {
        return crewPeople.size();
    }

    public float getGreyWaterProduced() {
        float totalGreyWaterProduced = 0f;
    	for (CrewPerson currentPerson : crewPeople.values())
            totalGreyWaterProduced += currentPerson.getGreyWaterProduced();
        return totalGreyWaterProduced;
    }

    public float getDirtyWaterProduced() {
        float totalDirtyWaterProduced = 0f;
    	for (CrewPerson currentPerson : crewPeople.values())
            totalDirtyWaterProduced += currentPerson.getDirtyWaterProduced();
        return totalDirtyWaterProduced;
    }

    public float getPotableWaterConsumed() {
        float totalPotableWaterConsumed = 0f;
    	for (CrewPerson currentPerson : crewPeople.values())
            totalPotableWaterConsumed += currentPerson
                    .getPotableWaterConsumed();
        return totalPotableWaterConsumed;
    }

    public float getFoodConsumed() {
        float totalFoodConsumed = 0f;
    	for (CrewPerson currentPerson : crewPeople.values())
            totalFoodConsumed += currentPerson.getFoodConsumed();
        return totalFoodConsumed;
    }

    public float getCO2Produced() {
        float totalCO2Produced = 0f;
    	for (CrewPerson currentPerson : crewPeople.values())
            totalCO2Produced += currentPerson.getCO2Produced();
        return totalCO2Produced;
    }

    public float getO2Consumed() {
        float totalO2Consumed = 0f;
    	for (CrewPerson currentPerson : crewPeople.values())
            totalO2Consumed += currentPerson.getO2Consumed();
        return totalO2Consumed;
    }

    public void detachCrewPerson(String name) {
        crewScheduledForRemoval.add(name);
    }

    public void attachCrewPerson(CrewPerson pCrewPerson) {
        crewScheduledForAddition.add(pCrewPerson);
    }

    /**
     * @return Returns the myAirConsumerDefinition.
     */
    protected AirConsumerDefinition getAirConsumerDefinition() {
        return myAirConsumerDefinition;
    }

    /**
     * @return Returns the myAirProducerDefinition.
     */
    protected AirProducerDefinition getAirProducerDefinition() {
        return myAirProducerDefinition;
    }

    /**
     * @return Returns the myDirtyWaterProducerDefinition.
     */
    protected DirtyWaterProducerDefinition getDirtyWaterProducerDefinition() {
        return myDirtyWaterProducerDefinition;
    }

    /**
     * @return Returns the myDryWasteProducerDefinition.
     */
    protected DryWasteProducerDefinition getDryWasteProducerDefinition() {
        return myDryWasteProducerDefinition;
    }

    /**
     * @return Returns the myFoodConsumerDefinition.
     */
    protected FoodConsumerDefinition getFoodConsumerDefinition() {
        return myFoodConsumerDefinition;
    }

    /**
     * @return Returns the myGreyWaterProducerDefinition.
     */
    protected GreyWaterProducerDefinition getGreyWaterProducerDefinition() {
        return myGreyWaterProducerDefinition;
    }

    /**
     * @return Returns the myPotableWaterConsumerDefinition.
     */
    protected PotableWaterConsumerDefinition getPotableWaterConsumerDefinition() {
        return myPotableWaterConsumerDefinition;
    }

	public boolean getDeathEnabled() {
		return myDeathEnabled;
	}

	public void setDeathEnabled(boolean deathEnabled) {
		this.myDeathEnabled = deathEnabled;
	}
}
