package com.traclabs.biosim.server.simulation.crew;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import com.traclabs.biosim.idl.framework.LogLevel;
import com.traclabs.biosim.idl.framework.Malfunction;
import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.simulation.crew.CrewGroup;
import com.traclabs.biosim.idl.simulation.crew.CrewGroupOperations;
import com.traclabs.biosim.idl.simulation.crew.CrewGroupPOATie;
import com.traclabs.biosim.idl.simulation.crew.CrewPerson;
import com.traclabs.biosim.idl.simulation.crew.CrewPersonHelper;
import com.traclabs.biosim.idl.simulation.crew.CrewPersonPOA;
import com.traclabs.biosim.idl.simulation.crew.RepairActivity;
import com.traclabs.biosim.idl.simulation.crew.RepairActivityHelper;
import com.traclabs.biosim.idl.simulation.crew.RepairActivityPOATie;
import com.traclabs.biosim.idl.simulation.crew.Sex;
import com.traclabs.biosim.idl.simulation.environment.AirConsumerDefinition;
import com.traclabs.biosim.idl.simulation.environment.AirConsumerOperations;
import com.traclabs.biosim.idl.simulation.environment.AirProducerDefinition;
import com.traclabs.biosim.idl.simulation.environment.AirProducerOperations;
import com.traclabs.biosim.idl.simulation.food.FoodConsumerDefinition;
import com.traclabs.biosim.idl.simulation.food.FoodConsumerOperations;
import com.traclabs.biosim.idl.simulation.waste.DryWasteProducerDefinition;
import com.traclabs.biosim.idl.simulation.waste.DryWasteProducerOperations;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterProducerOperations;
import com.traclabs.biosim.idl.simulation.water.GreyWaterProducerDefinition;
import com.traclabs.biosim.idl.simulation.water.GreyWaterProducerOperations;
import com.traclabs.biosim.idl.simulation.water.PotableWaterConsumerDefinition;
import com.traclabs.biosim.idl.simulation.water.PotableWaterConsumerOperations;
import com.traclabs.biosim.server.simulation.environment.AirConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.environment.AirProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.food.FoodConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.simulation.waste.DryWasteProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.water.DirtyWaterProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.water.GreyWaterProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.water.PotableWaterConsumerDefinitionImpl;
import com.traclabs.biosim.util.MersenneTwister;
import com.traclabs.biosim.util.OrbUtils;

/**
 * The Crew Implementation. Holds multiple crew persons and their schedule.
 * 
 * @author Scott Bell
 */

public class CrewGroupImpl extends SimBioModuleImpl implements
        CrewGroupOperations, AirConsumerOperations,
        PotableWaterConsumerOperations, FoodConsumerOperations,
        AirProducerOperations, GreyWaterProducerOperations,
        DirtyWaterProducerOperations, DryWasteProducerOperations {

    //Consumers, Producers
    private FoodConsumerDefinitionImpl myFoodConsumerDefinitionImpl;

    private AirConsumerDefinitionImpl myAirConsumerDefinitionImpl;

    private PotableWaterConsumerDefinitionImpl myPotableWaterConsumerDefinitionImpl;

    private GreyWaterProducerDefinitionImpl myGreyWaterProducerDefinitionImpl;

    private DirtyWaterProducerDefinitionImpl myDirtyWaterProducerDefinitionImpl;

    private AirProducerDefinitionImpl myAirProducerDefinitionImpl;

    private DryWasteProducerDefinitionImpl myDryWasteProducerDefinitionImpl;

    //The crew persons that make up the crew.
    //They are the ones consuming air/food/water and producing air/water/waste
    // as they perform activities
    private Map<String, CrewPerson> crewPeople;

    private float healthyPercentage = 1f;

    private Random myRandom = new MersenneTwister();
    
    private boolean myDeathEnabled = true;

    private List<String> crewScheduledForRemoval = new Vector<String>();

    private List<CrewPerson> crewScheduledForAddition = new Vector<CrewPerson>();
    
    /**
     * Default constructor. Uses a default schedule.
     */
    public CrewGroupImpl() {
    	this(0, "Unnamed CrewGroup");
    }

    public CrewGroupImpl(int pID, String pName) {
        super(pID, pName);
        crewPeople = new Hashtable<String, CrewPerson>();

        myFoodConsumerDefinitionImpl = new FoodConsumerDefinitionImpl(this);
        myAirConsumerDefinitionImpl = new AirConsumerDefinitionImpl(this);
        myPotableWaterConsumerDefinitionImpl = new PotableWaterConsumerDefinitionImpl(this);
        myGreyWaterProducerDefinitionImpl = new GreyWaterProducerDefinitionImpl(this);
        myDirtyWaterProducerDefinitionImpl = new DirtyWaterProducerDefinitionImpl(this);
        myAirProducerDefinitionImpl = new AirProducerDefinitionImpl(this);
        myDryWasteProducerDefinitionImpl = new DryWasteProducerDefinitionImpl(this);
    }

    public FoodConsumerDefinition getFoodConsumerDefinition() {
        return myFoodConsumerDefinitionImpl.getCorbaObject();
    }

    public AirConsumerDefinition getAirConsumerDefinition() {
        return myAirConsumerDefinitionImpl.getCorbaObject();
    }

    public PotableWaterConsumerDefinition getPotableWaterConsumerDefinition() {
        return myPotableWaterConsumerDefinitionImpl.getCorbaObject();
    }

    public GreyWaterProducerDefinition getGreyWaterProducerDefinition() {
        return myGreyWaterProducerDefinitionImpl.getCorbaObject();
    }

    public DirtyWaterProducerDefinition getDirtyWaterProducerDefinition() {
        return myDirtyWaterProducerDefinitionImpl.getCorbaObject();
    }

    public AirProducerDefinition getAirProducerDefinition() {
        return myAirProducerDefinitionImpl.getCorbaObject();
    }

    public DryWasteProducerDefinition getDryWasteProducerDefinition() {
        return myDryWasteProducerDefinitionImpl.getCorbaObject();
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
    
    public CrewPerson createCrewPerson(String pName, float pAge, float pWeight,
            Sex pSex, int pArrivalTick, int pDepartureTick, Schedule pSchedule) {
        return createCrewPerson("NORMAL", pName, pAge, pWeight, pSex, pArrivalTick, pDepartureTick, pSchedule);
    }
    
    private CrewGroup getCrewGroup(){
    	CrewGroupPOATie tie = new CrewGroupPOATie(this);
    	CrewGroup crewGroup = tie._this(OrbUtils.getORB());
    	return crewGroup;
    }

    public CrewPerson createCrewPerson(String implementation, String pName, float pAge, float pWeight,
            Sex pSex, int pArrivalTick, int pDepartureTick, Schedule pSchedule) {
    	CrewPersonPOA newCrewPersonPOA = null;
    	if (implementation.equals("NORMAL")){
    		newCrewPersonPOA = new CrewPersonImpl(pName, pAge,
                pWeight, pSex, pArrivalTick, pDepartureTick, getCrewGroup(),
                pSchedule);
    	}
    	else
    		newCrewPersonPOA = new CrewPersonMatlab(pName, pAge,
                    pWeight, pSex, pArrivalTick, pDepartureTick, getCrewGroup(),
                    pSchedule);
        CrewPerson newCrewPerson = CrewPersonHelper.narrow((OrbUtils
                .poaToCorbaObj(newCrewPersonPOA)));
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
        RepairActivityImpl newRepairActivityImpl = new RepairActivityImpl(
                moduleName, malfunctionID, timeLength);
        RepairActivity newRepairActivity = RepairActivityHelper
                .narrow(OrbUtils.poaToCorbaObj(new RepairActivityPOATie(
                        newRepairActivityImpl)));
        randomCrewPerson.insertActivityInScheduleNow(newRepairActivity);
    }

    public void resetSchedule() {
    	for (CrewPerson currentPerson : crewPeople.values())
    		currentPerson.reset();
    }
    
    public void setLogLevel(LogLevel pLevel){
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
        healthyPercentage = 1f;
    	for (Malfunction currentMalfunction : myMalfunctions.values()){
    		if (currentMalfunction.getLength() == MalfunctionLength.TEMPORARY_MALF) {
                if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
                    healthyPercentage *= 0.50;
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
                    healthyPercentage *= 0.25;
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
                    healthyPercentage *= 0.10;
            } else if (currentMalfunction.getLength() == MalfunctionLength.PERMANENT_MALF) {
                if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
                    healthyPercentage *= 0.50;
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
                    healthyPercentage *= 0.25;
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
                    healthyPercentage *= 0.10;
            }
            currentMalfunction.setPerformed(true);
        }

        int peopleAsleep = (new Float((1 - healthyPercentage)
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
        myFoodConsumerDefinitionImpl.reset();
        myAirConsumerDefinitionImpl.reset();
        myPotableWaterConsumerDefinitionImpl.reset();
        myGreyWaterProducerDefinitionImpl.reset();
        myDirtyWaterProducerDefinitionImpl.reset();
        myAirProducerDefinitionImpl.reset();
        myDryWasteProducerDefinitionImpl.reset();
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
     * @return Returns the myAirConsumerDefinitionImpl.
     */
    protected AirConsumerDefinitionImpl getAirConsumerDefinitionImpl() {
        return myAirConsumerDefinitionImpl;
    }

    /**
     * @return Returns the myAirProducerDefinitionImpl.
     */
    protected AirProducerDefinitionImpl getAirProducerDefinitionImpl() {
        return myAirProducerDefinitionImpl;
    }

    /**
     * @return Returns the myDirtyWaterProducerDefinitionImpl.
     */
    protected DirtyWaterProducerDefinitionImpl getDirtyWaterProducerDefinitionImpl() {
        return myDirtyWaterProducerDefinitionImpl;
    }

    /**
     * @return Returns the myDryWasteProducerDefinitionImpl.
     */
    protected DryWasteProducerDefinitionImpl getDryWasteProducerDefinitionImpl() {
        return myDryWasteProducerDefinitionImpl;
    }

    /**
     * @return Returns the myFoodConsumerDefinitionImpl.
     */
    protected FoodConsumerDefinitionImpl getFoodConsumerDefinitionImpl() {
        return myFoodConsumerDefinitionImpl;
    }

    /**
     * @return Returns the myGreyWaterProducerDefinitionImpl.
     */
    protected GreyWaterProducerDefinitionImpl getGreyWaterProducerDefinitionImpl() {
        return myGreyWaterProducerDefinitionImpl;
    }

    /**
     * @return Returns the myPotableWaterConsumerDefinitionImpl.
     */
    protected PotableWaterConsumerDefinitionImpl getPotableWaterConsumerDefinitionImpl() {
        return myPotableWaterConsumerDefinitionImpl;
    }

	public boolean getDeathEnabled() {
		return myDeathEnabled;
	}

	public void setDeathEnabled(boolean deathEnabled) {
		this.myDeathEnabled = deathEnabled;
	}
}