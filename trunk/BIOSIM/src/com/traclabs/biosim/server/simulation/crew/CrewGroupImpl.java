package com.traclabs.biosim.server.simulation.crew;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Vector;

import com.traclabs.biosim.idl.framework.Malfunction;
import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.simulation.crew.CrewGroup;
import com.traclabs.biosim.idl.simulation.crew.CrewGroupOperations;
import com.traclabs.biosim.idl.simulation.crew.CrewPerson;
import com.traclabs.biosim.idl.simulation.crew.CrewPersonHelper;
import com.traclabs.biosim.idl.simulation.crew.RepairActivity;
import com.traclabs.biosim.idl.simulation.crew.RepairActivityHelper;
import com.traclabs.biosim.idl.simulation.crew.RepairActivityPOATie;
import com.traclabs.biosim.idl.simulation.crew.ScheduleType;
import com.traclabs.biosim.idl.simulation.crew.Sex;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.food.FoodMatter;
import com.traclabs.biosim.idl.simulation.food.FoodStore;
import com.traclabs.biosim.idl.simulation.framework.AirConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.AirProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.DirtyWaterProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.DryWasteProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.FoodConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.GreyWaterProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterConsumerOperations;
import com.traclabs.biosim.idl.simulation.waste.DryWasteStore;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterStore;
import com.traclabs.biosim.idl.simulation.water.GreyWaterStore;
import com.traclabs.biosim.idl.simulation.water.PotableWaterStore;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.util.OrbUtils;

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
    //The crew persons that make up the crew.
    //They are the ones consuming air/food/water and producing air/water/waste
    // as they perform activities
    private Map crewPeople;

    private float healthyPercentage = 1f;

    private Random myRandom;

    private DryWasteStore[] myDryWasteStores;

    private FoodStore[] myFoodStores;

    private PotableWaterStore[] myPotableWaterStores;

    private GreyWaterStore[] myGreyWaterStores;

    private DirtyWaterStore[] myDirtyWaterStores;

    private SimEnvironment[] myAirInputs;

    private SimEnvironment[] myAirOutputs;

    private float[] foodMaxFlowRates;

    private float[] foodActualFlowRates;

    private float[] foodDesiredFlowRates;

    private float[] dryWasteMaxFlowRates;

    private float[] dryWasteActualFlowRates;

    private float[] dryWasteDesiredFlowRates;

    private float[] potableWaterMaxFlowRates;

    private float[] potableWaterActualFlowRates;

    private float[] potableWaterDesiredFlowRates;

    private float[] greyWaterMaxFlowRates;

    private float[] greyWaterActualFlowRates;

    private float[] greyWaterDesiredFlowRates;

    private float[] dirtyWaterMaxFlowRates;

    private float[] dirtyWaterActualFlowRates;

    private float[] dirtyWaterDesiredFlowRates;

    private float[] airInMaxFlowRates;

    private float[] airInDesiredFlowRates;

    private float[] airInActualFlowRates;

    private float[] airOutMaxFlowRates;

    private float[] airOutActualFlowRates;

    private float[] airOutDesiredFlowRates;
    
    private List crewScheduledForRemoval;
    private List crewScheduledForAddition;

    /**
     * Default constructor. Uses a default schedule.
     */
    public CrewGroupImpl(int pID, String pName) {
        super(pID, pName);
        crewScheduledForRemoval = new Vector();
        crewScheduledForAddition = new Vector();
        crewPeople = new Hashtable();
        myRandom = new Random();
        myFoodStores = new FoodStore[0];
        myDryWasteStores = new DryWasteStore[0];
        myPotableWaterStores = new PotableWaterStore[0];
        myGreyWaterStores = new GreyWaterStore[0];
        myDirtyWaterStores = new DirtyWaterStore[0];
        myAirInputs = new SimEnvironment[0];
        myAirOutputs = new SimEnvironment[0];
        foodMaxFlowRates = new float[0];
        foodActualFlowRates = new float[0];
        foodDesiredFlowRates = new float[0];
        dryWasteMaxFlowRates = new float[0];
        dryWasteActualFlowRates = new float[0];
        dryWasteDesiredFlowRates = new float[0];
        potableWaterMaxFlowRates = new float[0];
        potableWaterActualFlowRates = new float[0];
        potableWaterDesiredFlowRates = new float[0];
        greyWaterMaxFlowRates = new float[0];
        greyWaterActualFlowRates = new float[0];
        greyWaterDesiredFlowRates = new float[0];
        dirtyWaterMaxFlowRates = new float[0];
        dirtyWaterActualFlowRates = new float[0];
        dirtyWaterDesiredFlowRates = new float[0];
        airInMaxFlowRates = new float[0];
        airInActualFlowRates = new float[0];
        airInDesiredFlowRates = new float[0];
        airOutMaxFlowRates = new float[0];
        airOutActualFlowRates = new float[0];
        airOutDesiredFlowRates = new float[0];
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
            Sex pSex, int pArrivalTick, int pDepartureTick, CrewGroup crewGroup) {
        CrewPersonImpl newCrewPersonImpl = new CrewPersonImpl(pName, pAge, pWeight,
                pSex, pArrivalTick, pDepartureTick, this, crewGroup);
        CrewPerson newCrewPerson = CrewPersonHelper.narrow((OrbUtils.poaToCorbaObj(newCrewPersonImpl)));
        crewPeople.put(pName, newCrewPerson);
        return newCrewPerson;
    }

    public CrewPerson createCrewPerson(String pName, float pAge, float pWeight,
            Sex pSex, int pArrivalTick, int pDepartureTick, Schedule pSchedule, CrewGroup crewGroup) {
        CrewPersonImpl newCrewPersonImpl = new CrewPersonImpl(pName, pAge, pWeight,
                pSex, pArrivalTick, pDepartureTick, this, crewGroup, pSchedule);
        CrewPerson newCrewPerson = CrewPersonHelper.narrow((OrbUtils.poaToCorbaObj(newCrewPersonImpl)));
        crewPeople.put(pName, newCrewPerson);
        return newCrewPerson;
    }

    protected static FoodMatter[] getCaloriesFromStore(FoodStore[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates,
            float[] pActualFlowRates, float amountNeeded) {
        float gatheredResource = 0f;
        List gatheredBioMatterArrays = new Vector();
        int sizeOfMatter = 0;
        for (int i = 0; (i < pStores.length)
                && (gatheredResource < amountNeeded); i++) {
            float limitingMassFactor = Math.min(pDesiredFlowRates[i],
                    pMaxFlowRates[i]);
            FoodMatter[] takenMatter = pStores[i].takeFoodMatterCalories(
                    amountNeeded, limitingMassFactor);
            sizeOfMatter += takenMatter.length;
            gatheredBioMatterArrays.add(takenMatter);
            pActualFlowRates[i] += calculateSizeOfFoodMatter(takenMatter);
            gatheredResource += pStores[i].calculateCalories(takenMatter);
        }
        FoodMatter[] fullMatterTaken = new FoodMatter[sizeOfMatter];
        int lastPosition = 0;
        for (Iterator iter = gatheredBioMatterArrays.iterator(); iter.hasNext();) {
            FoodMatter[] matterArray = (FoodMatter[]) (iter.next());
            System.arraycopy(matterArray, 0, fullMatterTaken, lastPosition,
                    matterArray.length);
            lastPosition = matterArray.length;
        }
        return fullMatterTaken;
    }

    private static float calculateSizeOfFoodMatter(FoodMatter[] arrayOfMatter) {
        float totalSize = 0f;
        for (int i = 0; i < arrayOfMatter.length; i++)
            totalSize += arrayOfMatter[i].mass;
        return totalSize;
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
        CrewPerson randomCrewPerson = (CrewPerson) ((crewPeople
                .values().toArray())[randomCrewIndex]);
        RepairActivityImpl newRepairActivityImpl = new RepairActivityImpl(
                moduleName, malfunctionID, timeLength);
        RepairActivity newRepairActivity = RepairActivityHelper
                .narrow(OrbUtils.poaToCorbaObj(new RepairActivityPOATie(
                        newRepairActivityImpl)));
        randomCrewPerson.insertActivityInScheduleNow(newRepairActivity);
    }

    public void setSchedule(ScheduleType pSchedule) {
        for (Iterator iter = crewPeople.values().iterator(); iter.hasNext();) {
            CrewPerson currentPerson = (CrewPerson) (iter.next());
            currentPerson.reset();
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
        CrewPerson foundPerson = (CrewPerson) (crewPeople
                .get(crewPersonName));
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
        Arrays.fill(potableWaterActualFlowRates, 0f);
        Arrays.fill(greyWaterActualFlowRates, 0f);
        Arrays.fill(dirtyWaterActualFlowRates, 0f);
        Arrays.fill(dryWasteActualFlowRates, 0f);
    }

    /**
     * Processes a tick by ticking each crew person it knows about.
     */
    public void tick() {
        super.tick();
        clearActualFlowRates();
        for (Iterator iter = crewPeople.values().iterator(); iter.hasNext();) {
            CrewPerson tempPerson = (CrewPerson) (iter.next());
            tempPerson.tick();
        }
        //Add those scheduled
        for (Iterator iter = crewScheduledForAddition.iterator(); iter.hasNext();){
            CrewPerson crewPersonToAdd = (CrewPerson)(iter.next());
            crewPeople.put(crewPersonToAdd.getName(), crewPersonToAdd);
        }
        crewScheduledForAddition.clear();
        //Remove those scheduled
        for (Iterator iter = crewScheduledForRemoval.iterator(); iter.hasNext();){
            String crewPersonNameToRemove = (String)(iter.next());
            crewPeople.remove(crewPersonNameToRemove);
        }
        crewScheduledForRemoval.clear();
    }

    protected void performMalfunctions() {
        healthyPercentage = 1f;
        for (Iterator iter = myMalfunctions.values().iterator(); iter.hasNext();) {
            Malfunction currentMalfunction = (Malfunction) (iter.next());
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
        for (Iterator iter = crewPeople.values().iterator(); iter.hasNext();) {
            CrewPerson currentPerson = (CrewPerson) (iter.next());
            currentPerson.reset();
        }
    }

    /**
     * Gets the productivity of the crew
     */
    public float getProductivity() {
        float productivity = 0f;
        for (Iterator iter = crewPeople.values().iterator(); iter.hasNext();) {
            CrewPerson currentPerson = (CrewPerson) (iter.next());
            productivity += currentPerson.getProductivity();
        }
        return productivity;
    }

    public boolean anyDead() {
        if (crewPeople.size() < 1)
            return false;
        for (Iterator iter = crewPeople.values().iterator(); iter.hasNext();) {
            CrewPerson currentPerson = (CrewPerson) (iter.next());
            if (currentPerson.isDead())
                return true;
        }
        return false;
    }

    public boolean isDead() {
        if (crewPeople.size() < 1)
            return false;
        boolean areTheyDead = true;
        for (Iterator iter = crewPeople.values().iterator(); iter.hasNext();) {
            CrewPerson currentPerson = (CrewPerson) (iter.next());
            areTheyDead = areTheyDead && currentPerson.isDead();
        }
        return areTheyDead;
    }

    public int getCrewSize() {
        return crewPeople.size();
    }

    public float getGreyWaterProduced() {
        float totalGreyWaterProduced = 0f;
        for (Iterator iter = crewPeople.values().iterator(); iter.hasNext();) {
            CrewPerson currentPerson = (CrewPerson) (iter.next());
            totalGreyWaterProduced += currentPerson.getGreyWaterProduced();
        }
        return totalGreyWaterProduced;
    }

    public float getDirtyWaterProduced() {
        float totalDirtyWaterProduced = 0f;
        for (Iterator iter = crewPeople.values().iterator(); iter.hasNext();) {
            CrewPerson currentPerson = (CrewPerson) (iter.next());
            totalDirtyWaterProduced += currentPerson.getDirtyWaterProduced();
        }
        return totalDirtyWaterProduced;
    }

    public float getPotableWaterConsumed() {
        float totalPotableWaterConsumed = 0f;
        for (Iterator iter = crewPeople.values().iterator(); iter.hasNext();) {
            CrewPerson currentPerson = (CrewPerson) (iter.next());
            totalPotableWaterConsumed += currentPerson
                    .getPotableWaterConsumed();
        }
        return totalPotableWaterConsumed;
    }

    public float getFoodConsumed() {
        float totalFoodConsumed = 0f;
        for (Iterator iter = crewPeople.values().iterator(); iter.hasNext();) {
            CrewPerson currentPerson = (CrewPerson) (iter.next());
            totalFoodConsumed += currentPerson.getFoodConsumed();
        }
        return totalFoodConsumed;
    }

    public float getCO2Produced() {
        float totalCO2Produced = 0f;
        for (Iterator iter = crewPeople.values().iterator(); iter.hasNext();) {
            CrewPerson currentPerson = (CrewPerson) (iter.next());
            totalCO2Produced += currentPerson.getCO2Produced();
        }
        return totalCO2Produced;
    }

    public float getO2Consumed() {
        float totalO2Consumed = 0f;
        for (Iterator iter = crewPeople.values().iterator(); iter.hasNext();) {
            CrewPerson currentPerson = (CrewPerson) (iter.next());
            totalO2Consumed += currentPerson.getO2Consumed();
        }
        return totalO2Consumed;
    }

    //Air Input
    public void setAirInputMaxFlowRate(float moles, int index) {
        airInMaxFlowRates[index] = moles;
    }

    public float getAirInputMaxFlowRate(int index) {
        return airInMaxFlowRates[index];
    }

    public float[] getAirInputMaxFlowRates() {
        return airInMaxFlowRates;
    }

    public void setAirInputDesiredFlowRate(float moles, int index) {
        airInDesiredFlowRates[index] = moles;
    }

    public float getAirInputDesiredFlowRate(int index) {
        return airInDesiredFlowRates[index];
    }

    public float[] getAirInputDesiredFlowRates() {
        return airInDesiredFlowRates;
    }

    public float getAirInputActualFlowRate(int index) {
        return airInActualFlowRates[index];
    }

    public float[] getAirInputActualFlowRates() {
        return airInActualFlowRates;
    }

    public void setAirInputs(SimEnvironment[] sources, float[] maxFlowRates,
            float[] desiredFlowRates) {
        myAirInputs = sources;
        airInMaxFlowRates = maxFlowRates;
        airInDesiredFlowRates = desiredFlowRates;
        airInActualFlowRates = new float[airInDesiredFlowRates.length];
    }

    public SimEnvironment[] getAirInputs() {
        return myAirInputs;
    }

    //Air Output
    public void setAirOutputMaxFlowRate(float moles, int index) {
        airOutMaxFlowRates[index] = moles;
    }

    public float getAirOutputMaxFlowRate(int index) {
        return airOutMaxFlowRates[index];
    }

    public float[] getAirOutputMaxFlowRates() {
        return airOutMaxFlowRates;
    }

    public void setAirOutputDesiredFlowRate(float moles, int index) {
        airOutDesiredFlowRates[index] = moles;
    }

    public float getAirOutputDesiredFlowRate(int index) {
        return airOutDesiredFlowRates[index];
    }

    public float[] getAirOutputDesiredFlowRates() {
        return airOutDesiredFlowRates;
    }

    public float getAirOutputActualFlowRate(int index) {
        return airOutActualFlowRates[index];
    }

    public float[] getAirOutputActualFlowRates() {
        return airOutActualFlowRates;
    }

    public void setAirOutputs(SimEnvironment[] sources, float[] maxFlowRates,
            float[] desiredFlowRates) {
        myAirOutputs = sources;
        airOutMaxFlowRates = maxFlowRates;
        airOutDesiredFlowRates = desiredFlowRates;
        airOutActualFlowRates = new float[airOutDesiredFlowRates.length];
    }

    public SimEnvironment[] getAirOutputs() {
        return myAirOutputs;
    }

    //Potable Water Input
    public void setPotableWaterInputMaxFlowRate(float liters, int index) {
        potableWaterMaxFlowRates[index] = liters;
    }

    public float getPotableWaterInputMaxFlowRate(int index) {
        return potableWaterMaxFlowRates[index];
    }

    public float[] getPotableWaterInputMaxFlowRates() {
        return potableWaterMaxFlowRates;
    }

    public void setPotableWaterInputDesiredFlowRate(float liters, int index) {
        potableWaterDesiredFlowRates[index] = liters;
    }

    public float getPotableWaterInputDesiredFlowRate(int index) {
        return potableWaterDesiredFlowRates[index];
    }

    public float[] getPotableWaterInputDesiredFlowRates() {
        return potableWaterDesiredFlowRates;
    }

    public float getPotableWaterInputActualFlowRate(int index) {
        return potableWaterActualFlowRates[index];
    }

    public float[] getPotableWaterInputActualFlowRates() {
        return potableWaterActualFlowRates;
    }

    public void setPotableWaterInputs(PotableWaterStore[] sources,
            float[] maxFlowRates, float[] desiredFlowRates) {
        myPotableWaterStores = sources;
        potableWaterMaxFlowRates = maxFlowRates;
        potableWaterDesiredFlowRates = desiredFlowRates;
        potableWaterActualFlowRates = new float[potableWaterDesiredFlowRates.length];
    }

    public PotableWaterStore[] getPotableWaterInputs() {
        return myPotableWaterStores;
    }

    //Grey Water Output
    public void setGreyWaterOutputMaxFlowRate(float liters, int index) {
        greyWaterMaxFlowRates[index] = liters;
    }

    public float getGreyWaterOutputMaxFlowRate(int index) {
        return greyWaterMaxFlowRates[index];
    }

    public float[] getGreyWaterOutputMaxFlowRates() {
        return greyWaterMaxFlowRates;
    }

    public void setGreyWaterOutputDesiredFlowRate(float liters, int index) {
        greyWaterDesiredFlowRates[index] = liters;
    }

    public float getGreyWaterOutputDesiredFlowRate(int index) {
        return greyWaterDesiredFlowRates[index];
    }

    public float[] getGreyWaterOutputDesiredFlowRates() {
        return greyWaterDesiredFlowRates;
    }

    public float getGreyWaterOutputActualFlowRate(int index) {
        return greyWaterActualFlowRates[index];
    }

    public float[] getGreyWaterOutputActualFlowRates() {
        return greyWaterActualFlowRates;
    }

    public void setGreyWaterOutputs(GreyWaterStore[] destinations,
            float[] maxFlowRates, float[] desiredFlowRates) {
        myGreyWaterStores = destinations;
        greyWaterMaxFlowRates = maxFlowRates;
        greyWaterDesiredFlowRates = desiredFlowRates;
        greyWaterActualFlowRates = new float[greyWaterDesiredFlowRates.length];
    }

    public GreyWaterStore[] getGreyWaterOutputs() {
        return myGreyWaterStores;
    }

    //Dirty Water Output
    public void setDirtyWaterOutputMaxFlowRate(float liters, int index) {
        dirtyWaterMaxFlowRates[index] = liters;
    }

    public float getDirtyWaterOutputMaxFlowRate(int index) {
        return dirtyWaterMaxFlowRates[index];
    }

    public float[] getDirtyWaterOutputMaxFlowRates() {
        return dirtyWaterMaxFlowRates;
    }

    public void setDirtyWaterOutputDesiredFlowRate(float liters, int index) {
        dirtyWaterDesiredFlowRates[index] = liters;
    }

    public float getDirtyWaterOutputDesiredFlowRate(int index) {
        return dirtyWaterDesiredFlowRates[index];
    }

    public float[] getDirtyWaterOutputDesiredFlowRates() {
        return dirtyWaterDesiredFlowRates;
    }

    public float getDirtyWaterOutputActualFlowRate(int index) {
        return dirtyWaterActualFlowRates[index];
    }

    public float[] getDirtyWaterOutputActualFlowRates() {
        return dirtyWaterActualFlowRates;
    }

    public void setDirtyWaterOutputs(DirtyWaterStore[] destinations,
            float[] maxFlowRates, float[] desiredFlowRates) {
        myDirtyWaterStores = destinations;
        dirtyWaterMaxFlowRates = maxFlowRates;
        dirtyWaterDesiredFlowRates = desiredFlowRates;
        dirtyWaterActualFlowRates = new float[dirtyWaterDesiredFlowRates.length];
    }

    public DirtyWaterStore[] getDirtyWaterOutputs() {
        return myDirtyWaterStores;
    }

    //Dirty Water Output
    public void setDryWasteOutputMaxFlowRate(float liters, int index) {
        dryWasteMaxFlowRates[index] = liters;
    }

    public float getDryWasteOutputMaxFlowRate(int index) {
        return dryWasteMaxFlowRates[index];
    }

    public float[] getDryWasteOutputMaxFlowRates() {
        return dryWasteMaxFlowRates;
    }

    public void setDryWasteOutputDesiredFlowRate(float liters, int index) {
        dryWasteDesiredFlowRates[index] = liters;
    }

    public float getDryWasteOutputDesiredFlowRate(int index) {
        return dryWasteDesiredFlowRates[index];
    }

    public float[] getDryWasteOutputDesiredFlowRates() {
        return dryWasteDesiredFlowRates;
    }

    public float getDryWasteOutputActualFlowRate(int index) {
        return dryWasteActualFlowRates[index];
    }

    public float[] getDryWasteOutputActualFlowRates() {
        return dryWasteActualFlowRates;
    }

    public void setDryWasteOutputs(DryWasteStore[] destinations,
            float[] maxFlowRates, float[] desiredFlowRates) {
        myDryWasteStores = destinations;
        dryWasteMaxFlowRates = maxFlowRates;
        dryWasteDesiredFlowRates = desiredFlowRates;
        dryWasteActualFlowRates = new float[dryWasteDesiredFlowRates.length];
    }

    public DryWasteStore[] getDryWasteOutputs() {
        return myDryWasteStores;
    }

    //Food Water Input
    public void setFoodInputMaxFlowRate(float kilograms, int index) {
        foodMaxFlowRates[index] = kilograms;
    }

    public float getFoodInputMaxFlowRate(int index) {
        return foodMaxFlowRates[index];
    }

    public float[] getFoodInputMaxFlowRates() {
        return foodMaxFlowRates;
    }

    public void setFoodInputDesiredFlowRate(float kilograms, int index) {
        foodDesiredFlowRates[index] = kilograms;
    }

    public float getFoodInputDesiredFlowRate(int index) {
        return foodDesiredFlowRates[index];
    }

    public float[] getFoodInputDesiredFlowRates() {
        return foodDesiredFlowRates;
    }

    public float getFoodInputActualFlowRate(int index) {
        return foodActualFlowRates[index];
    }

    public float[] getFoodInputActualFlowRates() {
        return foodActualFlowRates;
    }

    public void setFoodInputs(FoodStore[] sources, float[] maxFlowRates,
            float[] desiredFlowRates) {
        myFoodStores = sources;
        foodMaxFlowRates = maxFlowRates;
        foodDesiredFlowRates = desiredFlowRates;
        foodActualFlowRates = new float[foodDesiredFlowRates.length];
    }

    public FoodStore[] getFoodInputs() {
        return myFoodStores;
    }

    public void detachCrewPerson(String name) {
        crewScheduledForRemoval.add(name);
    }
    
    public void attachCrewPerson(CrewPerson pCrewPerson) {
        crewScheduledForAddition.add(pCrewPerson);
    }
}