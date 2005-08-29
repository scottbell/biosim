package com.traclabs.biosim.server.simulation.crew;

import java.text.DecimalFormat;
import java.util.Random;

import org.apache.log4j.Logger;

import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.framework.BioModuleHelper;
import com.traclabs.biosim.idl.simulation.crew.Activity;
import com.traclabs.biosim.idl.simulation.crew.CrewGroup;
import com.traclabs.biosim.idl.simulation.crew.CrewGroupHelper;
import com.traclabs.biosim.idl.simulation.crew.CrewPersonHelper;
import com.traclabs.biosim.idl.simulation.crew.CrewPersonPOA;
import com.traclabs.biosim.idl.simulation.crew.EVAActivity;
import com.traclabs.biosim.idl.simulation.crew.MaitenanceActivity;
import com.traclabs.biosim.idl.simulation.crew.RepairActivity;
import com.traclabs.biosim.idl.simulation.crew.Sex;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.food.FoodMatter;
import com.traclabs.biosim.server.simulation.food.FoodConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.food.FoodStoreImpl;
import com.traclabs.biosim.server.simulation.framework.SimpleBuffer;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllableImpl;
import com.traclabs.biosim.server.util.MathUtils;
import com.traclabs.biosim.util.OrbUtils;

/**
 * The Crew Person Implementation. Eats/drinks/excercises away resources
 * according to a set schedule.
 * 
 * @author Scott Bell
 */

public class CrewPersonImpl extends CrewPersonPOA {
    //The name of this crew memeber
    private String myName = "No Name";

    //The current activity this crew member is performing
    private Activity myCurrentActivity;

    //The current activity order this crew member is on
    private int currentOrder = 0;

    //How long this crew member has been performing thier current activity (in
    // ticks)
    private int timeActivityPerformed = 0;

    //Flag to determine if the person has died
    private boolean hasDied = false;

    //The current age of the crew memeber
    private float age = 30f;

    //The current weight of the crew memeber
    private float weight = 170f;

    //The sex of the crew memeber
    private Sex sex = Sex.male;

    //How much O2 this crew member consumed in the current tick
    private float O2Consumed = 0f;

    //How much CO2 this crew member produced in the current tick
    private float CO2Produced = 0f;

    //How much food this crew member consumed in the current tick
    private float caloriesConsumed = 0f;

    //How much food this crew member consumed in the current tick (in kg)
    private float foodMassConsumed = 0f;

    //How much potable water this crew member consumed in the current tick
    private float potableWaterConsumed = 0f;

    //How much dirty water this crew member produced in the current tick
    private float dirtyWaterProduced = 0f;

    //How much dry waste this crew member produced in the current tick
    private float dryWasteProduced = 0f;

    //How much grey water this crew member produced in the current tick
    private float greyWaterProduced = 0f;

    //How much O2 this crew member needs to consume in one tick
    private float O2Needed = 0f;

    //How much potable water this crew member needs to consume in one tick
    private float potableWaterNeeded = 0f;

    //How much food this crew member needs to consume in one tick
    private float caloriesNeeded = 0f;

    //How much water (in moles) is produced in one tick
    private float vaporProduced = 0f;

    //Whether this crew member is sick or not. If the crew member is sick, puts
    // them into sleep like state.
    private boolean sick = false;

    private boolean onBoard = true;

    private boolean thirsty = false;

    private boolean starving = false;

    private boolean suffocating = false;

    private boolean poisoned = false;

    private boolean fireRisked = false;

    //A mission productivity measure, used when "mission" is specified in the
    // schedule. Not implemented correctly yet.
    private float myMissionProductivity = 0;

    //The schedule used for this crew memeber
    private Schedule mySchedule;

    private int myArrivalTick = 0;

    private int myDepartureTick = Integer.MAX_VALUE;

    //The crew group associated with this crew member
    private CrewGroupImpl myBaseCrewGroupImpl;

    private CrewGroup myCurrentCrewGroup;

    //Used to format floats
    private DecimalFormat numFormat;

    private Random myRandomGen;

    private SimpleBuffer consumedWaterBuffer;

    private SimpleBuffer consumedLowOxygenBuffer;

    private SimpleBuffer highOxygenBuffer;

    private SimpleBuffer consumedCaloriesBuffer;

    private SimpleBuffer consumedCO2Buffer;

    private SimpleBuffer sleepBuffer;

    private SimpleBuffer leisureBuffer;

    private static final float WATER_TILL_DEAD = 5.3f;

    private static final float WATER_RECOVERY_RATE = 0.01f;

    private static final float CALORIE_TILL_DEAD = 180000f;

    private static final float CALORIE_RECOVERY_RATE = 0.0001f;

    private static final float CO2_HIGH_RATIO = 0.06f;

    private static final float CO2_HIGH_TILL_DEAD = 4f;

    private static final float CO2_HIGH_RECOVERY_RATE = 0.005f;

    private static final float O2_HIGH_TILL_DEAD = 24f;

    private static final float O2_HIGH_RECOVERY_RATE = 0.01f;

    private static final float O2_LOW_RATIO = 0.1f;

    private static final float O2_LOW_TILL_DEAD = 2f;

    private static final float O2_LOW_RECOVERY_RATE = 0.01f;

    private static final float LEISURE_TILL_BURNOUT = 168f;

    private static final float LEISURE_RECOVERY_RATE = 90f;

    private static final float AWAKE_TILL_EXHAUSTION = 120f;

    private static final float SLEEP_RECOVERY_RATE = 12f;

    private Logger myLogger;

    /**
     * Constructor that creates a new crew person
     * 
     * @param pName
     *            the name of the new crew person
     * @param pAge
     *            the age of the new crew person
     * @param pSex
     *            the sex of the new crew person
     * @param pCrewGroup
     *            the crew that the new crew person belongs in
     */
    CrewPersonImpl(String pName, float pAge, float pWeight, Sex pSex,
            int pArrivalTick, int pDepartureTick,
            CrewGroupImpl pBaseCrewGroupImpl, CrewGroup pCurrentCrewGroup) {
        this(pName, pAge, pWeight, pSex, pArrivalTick, pDepartureTick,
                pBaseCrewGroupImpl, pCurrentCrewGroup, new Schedule(pBaseCrewGroupImpl));
    }

    CrewPersonImpl(String pName, float pAge, float pWeight, Sex pSex,
            int pArrivalTick, int pDepartureTick,
            CrewGroupImpl pBaseCrewGroupImpl, CrewGroup pCurrentCrewGroup,
            Schedule pSchedule) {
        myLogger = Logger.getLogger(this.getClass());
        myName = pName;
        age = pAge;
        weight = pWeight;
        sex = pSex;
        myArrivalTick = pArrivalTick;
        myDepartureTick = pDepartureTick;
        myBaseCrewGroupImpl = pBaseCrewGroupImpl;
        myCurrentCrewGroup = pCurrentCrewGroup;
        mySchedule = pSchedule;
        consumedWaterBuffer = new SimpleBuffer(WATER_TILL_DEAD, WATER_TILL_DEAD);
        consumedCaloriesBuffer = new SimpleBuffer(CALORIE_TILL_DEAD,
                CALORIE_TILL_DEAD);
        consumedCO2Buffer = new SimpleBuffer(CO2_HIGH_TILL_DEAD
                * CO2_HIGH_RATIO, CO2_HIGH_TILL_DEAD * CO2_HIGH_RATIO);
        consumedLowOxygenBuffer = new SimpleBuffer(O2_LOW_TILL_DEAD
                * O2_LOW_RATIO, O2_LOW_TILL_DEAD * O2_LOW_RATIO);
        highOxygenBuffer = new SimpleBuffer(O2_HIGH_TILL_DEAD * 1,
                O2_HIGH_TILL_DEAD * 1);
        sleepBuffer = new SimpleBuffer(AWAKE_TILL_EXHAUSTION * myBaseCrewGroupImpl.getTickLength(),
                AWAKE_TILL_EXHAUSTION * myBaseCrewGroupImpl.getTickLength());
        leisureBuffer = new SimpleBuffer(LEISURE_TILL_BURNOUT,
                LEISURE_TILL_BURNOUT);
        myRandomGen = new Random();
        numFormat = new DecimalFormat("#,##0.0;(#)");
        myCurrentActivity = mySchedule
                .getScheduledActivityByOrder(currentOrder);
    }

    /**
     * Resets the state of this crew member
     */
    public void reset() {
        consumedWaterBuffer.reset();
        consumedLowOxygenBuffer.reset();
        highOxygenBuffer.reset();
        consumedCaloriesBuffer.reset();
        consumedCO2Buffer.reset();
        sleepBuffer.reset();
        leisureBuffer.reset();
        myMissionProductivity = 0f;
        currentOrder = 0;
        myCurrentActivity = mySchedule
                .getScheduledActivityByOrder(currentOrder);
        timeActivityPerformed = 0;
        sick = false;
        onBoard = true;
        hasDied = false;
        thirsty = false;
        starving = false;
        suffocating = false;
        poisoned = false;
        fireRisked = false;
        O2Consumed = 0f;
        CO2Produced = 0f;
        caloriesConsumed = 0f;
        foodMassConsumed = 0f;
        potableWaterConsumed = 0f;
        dirtyWaterProduced = 0f;
        dryWasteProduced = 0f;
        greyWaterProduced = 0f;
        O2Needed = 0f;
        potableWaterNeeded = 0f;
        caloriesNeeded = 0f;
        vaporProduced = 0f;
    }

    public void setArrivalTick(int pArrivalTick) {
        myArrivalTick = pArrivalTick;
    }

    public int getArrivalTick() {
        return myArrivalTick;
    }

    public void setDepartureTick(int pDepartureTick) {
        myDepartureTick = pDepartureTick;
    }

    public int getDepartureTick() {
        return myDepartureTick;
    }

    /**
     * Returns the name given to this crew memeber
     * 
     * @return the name of the crew member
     */
    public String getName() {
        return myName;
    }

    /**
     * Returns the age given to this crew memeber
     * 
     * @return the age of the crew member
     */
    public float getAge() {
        return age;
    }

    /**
     * Returns the grey water produced (in liters) by this crew member during
     * the current tick
     * 
     * @return the grey water produced (in liters) by this crew member during
     *         the current tick
     */
    public float getGreyWaterProduced() {
        return greyWaterProduced;
    }

    /**
     * Returns the dirty water produced (in liters) by this crew member during
     * the current tick
     * 
     * @return the dirty water produced (in liters) by this crew member during
     *         the current tick
     */
    public float getDirtyWaterProduced() {
        return dirtyWaterProduced;
    }

    /**
     * Returns the dry waste produced (in liters) by this crew member during the
     * current tick
     * 
     * @return the dry waste produced (in liters) by this crew member during the
     *         current tick
     */
    public float getDryWasteProduced() {
        return dirtyWaterProduced;
    }

    /**
     * Returns the potable water consumed (in liters) by this crew member during
     * the current tick
     * 
     * @return the potable water consumed (in liters) by this crew member during
     *         the current tick
     */
    public float getPotableWaterConsumed() {
        return potableWaterConsumed;
    }

    /**
     * Returns the food consumed (in liters) by this crew member during the
     * current tick
     * 
     * @return the food consumed (in liters) by this crew member during the
     *         current tick
     */
    public float getFoodConsumed() {
        return caloriesConsumed;
    }

    /**
     * Returns the CO2 produced (in moles) by this crew member during the
     * current tick
     * 
     * @return the CO2 produced (in moles) by this crew member during the
     *         current tick
     */
    public float getCO2Produced() {
        return CO2Produced;
    }

    /**
     * Returns the O2 consumed (in moles) by this crew member during the current
     * tick
     * 
     * @return the O2 consumed (in moles) by this crew member during the current
     *         tick
     */
    public float getO2Consumed() {
        return O2Consumed;
    }

    /**
     * Checks whether the crew member is starving or not
     * 
     * @return <code>true</code> if the crew memeber is starving,
     *         <code>false</code> if not.
     */
    public boolean isStarving() {
        return starving;
    }

    /**
     * Checks whether the crew member is CO2 poisoned or not
     * 
     * @return <code>true</code> if the crew memeber is CO2 poisoned,
     *         <code>false</code> if not.
     */
    public boolean isPoisoned() {
        return poisoned;
    }

    /**
     * Checks whether the crew member is thirsty or not
     * 
     * @return <code>true</code> if the crew memeber is thirsty,
     *         <code>false</code> if not.
     */
    public boolean isThirsty() {
        return thirsty;
    }

    /**
     * Checks whether the crew member is suffocating from lack of O2 or not
     * 
     * @return <code>true</code> if the crew memeber is suffocating from lack
     *         of O2, <code>false</code> if not.
     */
    public boolean isSuffocating() {
        return suffocating;
    }

    public boolean hasFireRisk() {
        return fireRisked;
    }

    /**
     * Returns how much the crew member weighs (in kilograms)
     * 
     * @return the crew person's weight in kilograms
     */
    public float getWeight() {
        return weight;
    }

    /**
     * Checks whether the crew member is dead or not
     * 
     * @return <code>true</code> if the crew memeber is dead,
     *         <code>false</code> if not.
     */
    public boolean isDead() {
        return hasDied;
    }

    /**
     * Returns the crew member's sex
     * 
     * @return the crew person's sex
     */
    public Sex getSex() {
        return sex;
    }

    public void setSchedule(Schedule pSchedule) {
        mySchedule = pSchedule;
    }

    /**
     * Makes this crew member sick (sleep like)
     */
    public void sicken() {
        sick = true;
        myCurrentActivity = mySchedule.getActivityByName("sick");
    }

    /**
     * Returns the crew member's current activity
     * 
     * @return the crew member's current activity
     */
    public Activity getCurrentActivity() {
        return myCurrentActivity;
    }

    /**
     * Sets the crew memeber's activity to a new one specified.
     * 
     * @param pActivity
     *            the crew member's new activity
     */
    public void setCurrentActivity(Activity pActivity) {
        myCurrentActivity = pActivity;
        currentOrder = mySchedule.getOrderOfScheduledActivity(myCurrentActivity
                .getName());
    }

    /**
     * Returns a scheduled activity by name (like "sleeping")
     * 
     * @param name
     *            the name of the activity to fetch
     * @return the activity in the schedule asked for by name
     */
    public Activity getActivityByName(String name) {
        return mySchedule.getActivityByName(name);
    }

    /**
     * Returns a scheduled activity by order
     * 
     * @param order
     *            the order of the activity to fetch
     * @return the activity in the schedule asked for by order
     */
    public Activity getScheduledActivityByOrder(int order) {
        return mySchedule.getScheduledActivityByOrder(order);
    }

    /**
     * Returns how long the crew member's been performing the current activity
     * 
     * @return the time the crew memeber has been performing the current
     *         activity
     */
    public int getTimeActivityPerformed() {
        return timeActivityPerformed;
    }

    /**
     * Returns a string representation of the crew memeber in the form: [name]
     * now performing [activity] for [x] hours
     * 
     * @return a string representation of the crew member
     */
    public String toString() {
        return (myName + " now performing activity "
                + myCurrentActivity.getName() + " for " + timeActivityPerformed
                + " of " + myCurrentActivity.getTimeLength() + " hours");
    }

    public boolean isSick() {
        return sick;
    }

    /**
     * If the crew memeber has been performing the current activity long enough,
     * the new scheduled activity is assigned.
     */
    private void advanceActivity() {
        checkForMeaningfulActivity();
        if (timeActivityPerformed >= myCurrentActivity.getTimeLength()) {
            if (sick)
                sick = false;
            currentOrder++;
            if (currentOrder >= (mySchedule.getNumberOfScheduledActivities()))
                currentOrder = 1;
            myCurrentActivity = mySchedule
                    .getScheduledActivityByOrder(currentOrder);
            timeActivityPerformed = 0;
        }
    }

    /**
     * Looks at current activity to see if it's "meaningful" i.e., the activity
     * affects other modules. mission - productivity measure, used for metrics.
     * the longer the crew does this, the better evaluation (not implemented
     * yet) maitenance - prevents other modules from breaking down (not
     * implemented yet) repair - attempts to fix a module. may have to be called
     * several time depending on the severity of the malfunction
     * 
     * New Stuff: 1) Put new environment to join in activity (make it like a
     * repair activity) 2) sap 10% of air from old environment (look at air
     * input of crew) 3) hook up old environment at end of EVA task (keep a
     * record) 4) allow modules to be reconfigured using acutators (work on this
     * later)
     */
    private void checkForMeaningfulActivity() {
        myLogger.debug("Checking to see if " + myCurrentActivity.getName()
                + " is a meaningful activity");
        if (myCurrentActivity.getName().equals("mission")) {
            addProductivity();
        } else if (myCurrentActivity.getName().startsWith("sleep")
                || myCurrentActivity.getName().startsWith("sick")) {
            sleepBuffer.add(SLEEP_RECOVERY_RATE);
        } else if (myCurrentActivity.getName().equals("leisure")) {
            leisureBuffer.add(LEISURE_RECOVERY_RATE);
        } else if (myCurrentActivity instanceof RepairActivity) {
            RepairActivity repairActivity = (RepairActivity) (myCurrentActivity);
            repairModule(repairActivity.getModuleNameToRepair(), repairActivity
                    .getMalfunctionIDToRepair());
        } else if (myCurrentActivity instanceof EVAActivity) {
            handleEVA((EVAActivity) (myCurrentActivity));
        } else if (myCurrentActivity instanceof MaitenanceActivity) {
            MaitenanceActivity maitenanceActivity = (MaitenanceActivity) (myCurrentActivity);
            maintainModule(maitenanceActivity.getModuleNameToMaintain());
        }
    }

    /**
     * @param pActivity
     */
    private void handleEVA(EVAActivity pEVAActivity) {
        myLogger.debug("Handling EVA");
        if (timeActivityPerformed <= 1)
            startEVA(pEVAActivity.getEVACrewGroupName());
        else if (timeActivityPerformed >= pEVAActivity.getTimeLength())
            endEVA(pEVAActivity.getBaseCrewGroupName());
    }

    /**
     * @param evaCrewGroupName
     */
    private void startEVA(String evaCrewGroupName) {
        myLogger.debug("Starting EVA");
        // remove 5% from base environment (assume 3.7 m3 airlock)
        myCurrentCrewGroup.getAirConsumerDefinition().getEnvironments()[0]
                .removeAirlockPercentage(0.05f);
        // detach from current crew group
        myCurrentCrewGroup.detachCrewPerson(getName());
        //attach to eva crew group
        CrewGroup evaCrewGroup = CrewGroupHelper.narrow(OrbUtils.getBioModule(
                myCurrentCrewGroup.getID(), evaCrewGroupName));
        evaCrewGroup.attachCrewPerson(CrewPersonHelper.narrow((OrbUtils
                .poaToCorbaObj(this))));
        myCurrentCrewGroup = evaCrewGroup;
        // perform activity for X ticks
    }

    /**
     * @param baseCrewGroupName
     */
    private void endEVA(String baseCrewGroupName) {
        myLogger.debug("Ending EVA");
        // detach from EVA crew group
        myCurrentCrewGroup.detachCrewPerson(getName());
        // reattach to base crew group
        CrewGroup baseCrewGroup = CrewGroupHelper.narrow((OrbUtils
                .getBioModule(myCurrentCrewGroup.getID(), baseCrewGroupName)));
        baseCrewGroup.attachCrewPerson(CrewPersonHelper.narrow((OrbUtils
                .poaToCorbaObj(this))));
        myCurrentCrewGroup = baseCrewGroup;
        // remove 5% from base environment (assume 3.7 m3 airlock)
        myCurrentCrewGroup.getAirConsumerDefinition().getEnvironments()[0]
                .removeAirlockPercentage(0.05f);
    }

    /**
     * productivity measure, used for metrics. the longer the crew does this,
     * the better evaluation (not implemented yet) invoked when crew person
     * performs "mission" activity
     */
    private void addProductivity() {
        float caloriePercentFull = MathUtils.sigmoidLikeProbability(consumedCaloriesBuffer
                .getLevel()
                / consumedCaloriesBuffer.getCapacity());
        float waterPercentFull = MathUtils.sigmoidLikeProbability(consumedWaterBuffer
                .getLevel()
                / consumedWaterBuffer.getCapacity());
        float oxygenPercentFull = MathUtils.sigmoidLikeProbability(consumedLowOxygenBuffer
                .getLevel()
                / consumedLowOxygenBuffer.getCapacity());
        float CO2PercentFull = MathUtils.sigmoidLikeProbability(consumedCO2Buffer
                .getLevel()
                / consumedCO2Buffer.getCapacity());
        float sleepPercentFull = sleepBuffer.getLevel()
                / sleepBuffer.getCapacity();
        float leisurePercentFull = leisureBuffer.getLevel()
                / leisureBuffer.getCapacity();

        float averagePercentFull = (caloriePercentFull + waterPercentFull
                + oxygenPercentFull + CO2PercentFull + sleepPercentFull + leisurePercentFull) / 6f;
        myMissionProductivity += myBaseCrewGroupImpl
                .randomFilter(averagePercentFull * getCurrentCrewGroup().getTickLength());
    }

    /**
     * Returns mission productivity.
     */
    public float getProductivity() {
        return myMissionProductivity;
    }

    /**
     * attempts to fix a module. may have to be called several time depending on
     * the severity of the malfunction invoked when crew person performs
     * "repair" activity
     */
    private void repairModule(String moduleName, long id) {
        try {
            BioModule moduleToRepair = BioModuleHelper.narrow(OrbUtils
                    .getNamingContext(myCurrentCrewGroup.getID()).resolve_str(
                            moduleName));
            moduleToRepair.doSomeRepairWork(id);
        } catch (org.omg.CORBA.UserException e) {
            myLogger.warn("CrewPersonImp:" + myCurrentCrewGroup.getID()
                    + ": Couldn't locate " + moduleName
                    + " to repair, skipping...");
        }
    }

    /**
     * prevents other modules from breaking down invoked when crew person
     * performs "maitenance" activity
     */
    private void maintainModule(String moduleName) {
        try {
            BioModule module = BioModuleHelper.narrow(OrbUtils
                    .getNamingContext(myCurrentCrewGroup.getID()).resolve_str(
                            moduleName));
            module.maitenance();
        } catch (org.omg.CORBA.UserException e) {
            myLogger.warn("CrewPersonImp:" + myCurrentCrewGroup.getID()
                    + ": Couldn't locate " + moduleName
                    + " to repair, skipping...");
        }
    }

    private void checkIfOnBoard() {
        if ((myCurrentCrewGroup.getMyTicks() >= myArrivalTick)
                && (myCurrentCrewGroup.getMyTicks() < myDepartureTick)) {
            onBoard = true;
        } else if (onBoard) {
            myCurrentActivity = mySchedule.getActivityByName("absent");
            onBoard = false;
        }
    }

    public boolean isOnBoard() {
        return onBoard;
    }

    /**
     * When the CrewGroup ticks the crew member, the member: 1) increases the
     * time the activity has been performed by 1. on the condition that the crew
     * memeber isn't dead he/she then:. 2) attempts to collect references to
     * various server (if not already done). 3) possibly advances to the next
     * activity. 4) consumes air/food/water, exhales and excretes. 5) takes
     * afflictions from lack of any resources. 6) checks whether afflictions (if
     * any) are fatal.
     */
    public void tick() {
        myLogger.debug(getName() + " ticked");
        timeActivityPerformed++;
        myLogger.debug(getName() + " performed activity "
                + myCurrentActivity.getName() + " for " + timeActivityPerformed
                + " of " + myCurrentActivity.getTimeLength() + " ticks");
        if (!hasDied) {
            checkIfOnBoard();
            if (onBoard) {
                advanceActivity();
                consumeResources();
                afflictCrew();
                healthCheck();
                recoverCrew();
            }
        }
        if (myLogger.isDebugEnabled())
            log();
    }

    /**
     * Calculate the current O2 needed by the crew memeber given the intensity
     * of the activity for the current tick. Algorithm derived from "Top Level
     * Modeling of Crew Component of ALSS" by Goudrazi and Ting
     * 
     * @param currentActivityIntensity
     *            the intensity of the current activity
     * @return O2 needed in liters
     */
    private float calculateO2Needed(int currentActivityIntensity) {
        if (currentActivityIntensity < 0)
            return 0f;
        float heartRate = (currentActivityIntensity * 30f) + 15f;
        float a = 0.223804f;
        float b = 5.64f * pow(10f, -7f);
        float resultInLiters = (a + (b * pow(heartRate, 3f) * 60f)); //liters per hour, 5
        myLogger.debug("resultInLiters "+resultInLiters);
        //equation assumes STP
        float moleOfAirPerLiterAtSTP = 22.4f;
        float resultInMoles = resultInLiters / moleOfAirPerLiterAtSTP ; //moles per hour
        float adjustForTickLength = resultInMoles * getCurrentCrewGroup().getTickLength();
        myLogger.debug("resultInMoles "+resultInMoles);
        myLogger.debug("adjustForTickLength "+adjustForTickLength);
        myLogger.debug("adjustForTickLength "+adjustForTickLength);
        return myBaseCrewGroupImpl.randomFilter(adjustForTickLength); 
    }

    private float pow(float a, float b) {
        return (float)(Math.pow(a, b));
    }

    /**
     * Calculate the current CO2 produced by the crew memeber given the O2
     * consumed for the current tick. Algorithm derived from "Top Level Modeling
     * of Crew Component of ALSS" by Goudrazi and Ting
     * 
     * @param O2Consumed
     *            the O2 consumed (in moles) during this tick
     * @return CO2 produced in moles
     */
    private float calculateCO2Produced(float pO2Consumed) {
    	float CO2Produced = pO2Consumed * 0.86f;
    	myLogger.debug("O2Consumed = "+O2Consumed);
    	myLogger.debug("CO2Produced = "+CO2Produced);
        return CO2Produced;
    }

    public void insertActivityInSchedule(Activity pActivity, int pOrder) {
        mySchedule.insertActivityInSchedule(pActivity, pOrder);
    }

    public void insertActivityInScheduleNow(Activity pActivity) {
        mySchedule.insertActivityInSchedule(pActivity, currentOrder + 1);
    }

    public int getOrderOfScheduledActivity(String activityName) {
        return mySchedule.getOrderOfScheduledActivity(activityName);
    }

    /**
     * Calculate the current food needed (in calories) by the crew memeber given
     * the intensity of the activity for the current tick. Algorithm derived
     * from "Top Level Modeling of Crew Component of ALSS" by Goudrazi and Ting
     * 
     * @param currentActivityIntensity
     *            the activity intensity for the current tick
     * @return food needed in kilograms
     */
    private float calculateFoodNeeded(int currentActivityIntensity) {
        if (currentActivityIntensity < 0f)
            return 0f;
        float activityCoefficient = (0.5f * (currentActivityIntensity - 1f)) + 1f;
        float joulesNeeded = 0f;
        if (sex == Sex.male)
            if (age < 30f)
                joulesNeeded = (106f * weight) + (5040f * activityCoefficient);
            else
                joulesNeeded = (86f * weight) + (5990f * activityCoefficient);
        else if (age < 30f)
            joulesNeeded = (106f * weight) + (3200f * activityCoefficient);
        else
            joulesNeeded = (106f * weight) + (6067f * activityCoefficient);
        //make it for one hour
        joulesNeeded /= 24f;
        //make it for the tick interval
        joulesNeeded *= getCurrentCrewGroup().getTickLength();
        caloriesNeeded = joulesNeeded / 4.2f;
        return myBaseCrewGroupImpl.randomFilter(caloriesNeeded);
    }

    /**
     * Calculate the dirty water produced given the potable water consumed for
     * the current tick.
     * 
     * @param potableWaterConsumed
     *            the potable water consumed (in liters) during this tick
     * @return dirty water produced in liters
     */
    private float calculateDirtyWaterProduced(float pPotableWaterConsumed) {
        return myBaseCrewGroupImpl.randomFilter(pPotableWaterConsumed * 0.3625f);
    }

    /**
     * Calculate the dry waste produced given the potable water consumed for the
     * current tick.
     * 
     * @param foodConsumed
     *            the food consumed (in kg) during this tick
     * @return dry waste produced in kg
     */
    private float calculateDryWasteProduced(float foodConsumed) {
        return myBaseCrewGroupImpl.randomFilter(foodConsumed * 0.022f);
    }

    /**
     * Calculate the grey water produced given the potable water consumed for
     * the current tick.
     * 
     * @param potableWaterConsumed
     *            the potable water consumed (in liters) during this tick
     * @return grey water produced in liters
     */
    private float calculateGreyWaterProduced(float pPotableWaterConsumed) {
        return myBaseCrewGroupImpl.randomFilter(pPotableWaterConsumed * 0.5375f);
    }

    /**
     * Calculate the current vapor produced by the crew memeber given the O2
     * consumed for the current tick.
     * 
     * @param potableWaterConsumed
     *            the potable water consumed (in liters) during this tick
     * @return vapor produced in moles
     */
    private float calculateVaporProduced(float pPotableWaterConsumed) {
        return waterLitersToMoles(pPotableWaterConsumed * 0.175f);
    }

    /**
     * Calculate the clean water needed given the activity intensity for the
     * current tick.
     * 
     * @param currentActivityIntensity
     *            the activity intensity for the current tick
     * @return potable water needed in liters
     */
    private float calculateCleanWaterNeeded(int currentActivityIntensity) {
        if (currentActivityIntensity > 0)
            return myBaseCrewGroupImpl.randomFilter(0.1667f * getCurrentCrewGroup().getTickLength());
		return myBaseCrewGroupImpl.randomFilter(0f);
    }

    /**
     * Calculate the CO2 ratio in the breath of air inhaled by the crew member.
     * Used to see if crew member has inhaled lethal amount of CO2
     * 
     * @param aBreath
     *            the breath inhaled by the crew memeber this tick
     * @return percentage of CO2 in air
     */
    private float getCO2Ratio() {
        SimEnvironment[] myAirInputs = myCurrentCrewGroup
                .getAirConsumerDefinition().getEnvironments();
        if (myAirInputs.length < 1) {
            return 0f;
        }
		if (myAirInputs[0].getTotalMoles() <= 0)
		    return 0f;
		return (myAirInputs[0].getCO2Store().getCurrentLevel() / myAirInputs[0]
		        .getTotalMoles());
    }

    /**
     * Calculate the O2 ratio in the breath of air inhaled by the crew member.
     * Used to see if crew member has inhaled lethal amount of O2
     * 
     * @param aBreath
     *            the breath inhaled by the crew memeber this tick
     * @return percentage of O2 in air
     */
    private float getO2Ratio() {
        SimEnvironment[] myAirInputs = myCurrentCrewGroup
                .getAirConsumerDefinition().getEnvironments();
        if (myAirInputs.length < 1) {
            return 0f;
        }
		if (myAirInputs[0].getTotalMoles() <= 0)
		    return 0f;
		return (myAirInputs[0].getO2Store().getCurrentLevel() / myAirInputs[0]
		        .getTotalMoles());
    }

    private void recoverCrew() {
    	float tickInterval = getCurrentCrewGroup().getTickLength();
        consumedCaloriesBuffer.add(CALORIE_RECOVERY_RATE
                * consumedCaloriesBuffer.getCapacity() * tickInterval);
        consumedWaterBuffer.add(WATER_RECOVERY_RATE
                * consumedWaterBuffer.getCapacity() * tickInterval);
        consumedLowOxygenBuffer.add(O2_LOW_RECOVERY_RATE
                * consumedLowOxygenBuffer.getCapacity() * tickInterval);
        consumedCO2Buffer.add(CO2_HIGH_RECOVERY_RATE
                * consumedCO2Buffer.getCapacity() * tickInterval);
    }

    /**
     * If not all the resources required were consumed, we damage the crew
     * member.
     */
    private void afflictCrew() {
    	float tickInterval = getCurrentCrewGroup().getTickLength();
        sleepBuffer.take(1 * tickInterval);
        leisureBuffer.take(1 * tickInterval);
        consumedCaloriesBuffer.take(caloriesNeeded - caloriesConsumed);
        if (caloriesNeeded - caloriesConsumed > 100){
            myLogger.debug("starving: caloriesNeeded="+caloriesNeeded+ " caloriesConsumed="+caloriesConsumed+" deficit=" +(caloriesNeeded - caloriesConsumed));
            starving = true;
        }
        else
            starving = false;
        consumedWaterBuffer.take(potableWaterNeeded - potableWaterConsumed);
        if (potableWaterNeeded - potableWaterConsumed > 0)
            thirsty = true;
        else
            thirsty = false;
    	myLogger.debug("getO2Ratio() = "+getO2Ratio());
        if (getO2Ratio() < O2_LOW_RATIO) {
            consumedLowOxygenBuffer.take((O2_LOW_RATIO - getO2Ratio()) * tickInterval);
            suffocating = true;
        } else
            suffocating = false;

        float dangerousOxygenThreshold = myCurrentCrewGroup
                .getAirConsumerDefinition().getEnvironments()[0]
                .getDangerousOxygenThreshold();
        if (getO2Ratio() > dangerousOxygenThreshold) {
        	highOxygenBuffer.take((getO2Ratio() - dangerousOxygenThreshold) * tickInterval);
            fireRisked = true;
        } else if (getCO2Ratio() > CO2_HIGH_RATIO) {
            consumedCO2Buffer.take((getCO2Ratio() - CO2_HIGH_RATIO) * tickInterval);
            poisoned = true;
        } else
            poisoned = false;
    }

    

    /**
     * Checks to see if crew memeber has been lethally damaged (i.e., hasn't
     * received a resource for too many ticks)
     */
    private void healthCheck() {
        //check for death
        float randomNumber = myRandomGen.nextFloat();
        myLogger.debug("random number this tick is "+randomNumber);
        float calorieRiskReturn = MathUtils.sigmoidLikeProbability((consumedCaloriesBuffer
                .getCapacity() - consumedCaloriesBuffer.getLevel())
                / consumedCaloriesBuffer.getCapacity());
        float waterRiskReturn = MathUtils.sigmoidLikeProbability((consumedWaterBuffer
                .getCapacity() - consumedWaterBuffer.getLevel())
                / consumedWaterBuffer.getCapacity());
        float oxygenLowRiskReturn = MathUtils.sigmoidLikeProbability((consumedLowOxygenBuffer
                .getCapacity() - consumedLowOxygenBuffer.getLevel())
                / consumedLowOxygenBuffer.getCapacity());
        float oxygenHighRiskReturn = MathUtils.sigmoidLikeProbability((highOxygenBuffer
                .getCapacity() - highOxygenBuffer.getLevel())
                / highOxygenBuffer.getCapacity());
        float CO2RiskReturn = MathUtils.sigmoidLikeProbability((consumedCO2Buffer
                .getCapacity() - consumedCO2Buffer.getLevel())
                / consumedCO2Buffer.getCapacity());
        float sleepRiskReturn = MathUtils.sigmoidLikeProbability((sleepBuffer
                .getCapacity() - sleepBuffer.getLevel())
                / sleepBuffer.getCapacity());
        myLogger.debug(getName());
        myLogger
                .debug("\tcalorie taken="
                        + (caloriesNeeded - caloriesConsumed)
                        + ", recovered "
                        + CALORIE_RECOVERY_RATE
                        * consumedCaloriesBuffer.getCapacity()
                        + " calorie risk level="
                        + calorieRiskReturn + " (level="
                        + consumedCaloriesBuffer.getLevel() + ", capacity="
                        + consumedCaloriesBuffer.getCapacity() + ")");
        myLogger.debug("\twater taken="
                + (potableWaterNeeded - potableWaterConsumed)
                + ", recovered "
                + WATER_RECOVERY_RATE
                * consumedWaterBuffer.getCapacity()
                + " thirst risk level="
                + waterRiskReturn
                + " (level=" + consumedWaterBuffer.getLevel() + ", capacity="
                + consumedWaterBuffer.getCapacity() + ")");
        myLogger
                .debug("\tlow oxygen taken="
                        + (O2Needed - O2Consumed)
                        + ", low recovered "
                        + O2_LOW_RECOVERY_RATE
                        * consumedLowOxygenBuffer.getCapacity()
                        + " low O2 risk level="
                        + oxygenLowRiskReturn+ " (level="
                        + consumedLowOxygenBuffer.getLevel() + ", capacity="
                        + consumedLowOxygenBuffer.getCapacity() + ")");
        float dangerousOxygenThreshold = myCurrentCrewGroup
        .getAirConsumerDefinition().getEnvironments()[0]
        .getDangerousOxygenThreshold();
        myLogger
                .debug("\thigh oxygen taken="
                        + (O2Needed - O2Consumed)
                        + ", high recovered "
                        + O2_HIGH_RECOVERY_RATE
                        * highOxygenBuffer.getCapacity()
                        + " high O2 risk level="
                        + oxygenHighRiskReturn + " (level="
                        + highOxygenBuffer.getLevel() + ", capacity="
                        + highOxygenBuffer.getCapacity() + ")");
        myLogger.debug("\tCO2 taken="
                + (getCO2Ratio() - CO2_HIGH_RATIO)
                + ", recovered "
                + CO2_HIGH_RECOVERY_RATE
                * consumedCO2Buffer.getCapacity()
                + " CO2 risk level="
                + CO2RiskReturn
                + " (level=" + consumedCO2Buffer.getLevel() + ", capacity="
                + consumedCO2Buffer.getCapacity() + ")");
        myLogger.debug("\tsleep (level=" + sleepBuffer.getLevel()
                + ", capacity=" + sleepBuffer.getCapacity() + ")");
        myLogger.debug("\tCO2 ration =" + getCO2Ratio()
                + ", DANGEROUS_CO2_RATION=" + CO2_HIGH_RATIO);

        if (sleepRiskReturn > (randomNumber + 0.05f)) {
            sicken();
            myLogger.info(getName()
                    + " has fallen ill from exhaustion (risk was "
                    + numFormat.format(sleepRiskReturn * 100) + "%) @ tick "
                    + myCurrentCrewGroup.getMyTicks());
        }

        if (calorieRiskReturn > randomNumber) {
            hasDied = true;
            myLogger.info(getName() + " has died from starvation on tick "
                    + myCurrentCrewGroup.getMyTicks() + " (risk was "
                    + numFormat.format(calorieRiskReturn * 100) + "%)");
        } else if (waterRiskReturn > randomNumber) {
            hasDied = true;
            myLogger.info(getName() + " has died from thirst on tick "
                    + myCurrentCrewGroup.getMyTicks() + " (risk was "
                    + numFormat.format(waterRiskReturn * 100) + "%)");
        } else if (oxygenLowRiskReturn > randomNumber) {
            hasDied = true;
            SimEnvironment[] myAirInputs = myCurrentCrewGroup
                    .getAirConsumerDefinition().getEnvironments();
            myLogger.info(getName() + " has died from lack of oxygen on tick "
                    + myCurrentCrewGroup.getMyTicks() + " (risk was "
                    + numFormat.format(oxygenLowRiskReturn * 100) + "%)");
            logEnvironmentConditions();
        } else if (oxygenHighRiskReturn > randomNumber) {
            hasDied = true;
            SimEnvironment[] myAirInputs = myCurrentCrewGroup
                    .getAirConsumerDefinition().getEnvironments();
            myLogger.info(getName()
                    + " has died from oxygen flammability on tick "
                    + myCurrentCrewGroup.getMyTicks() + " (risk was "
                    + numFormat.format(oxygenHighRiskReturn * 100) + "%)");
            logEnvironmentConditions();
        } else if (CO2RiskReturn > randomNumber) {
            hasDied = true;
            SimEnvironment[] myAirInputs = myCurrentCrewGroup
                    .getAirConsumerDefinition().getEnvironments();
            myLogger.info(getName() + " has died from CO2 poisoning on tick "
                    + myCurrentCrewGroup.getMyTicks() + " (risk was "
                    + numFormat.format(CO2RiskReturn * 100) + "%)");
            
        }
        //if died, kill
        if (hasDied) {
            O2Consumed = 0f;
            CO2Produced = 0f;
            caloriesConsumed = 0f;
            potableWaterConsumed = 0f;
            dirtyWaterProduced = 0f;
            greyWaterProduced = 0f;
            caloriesConsumed = 0f;
            myCurrentActivity = mySchedule.getActivityByName("dead");
            timeActivityPerformed = 0;
        }
    }
    
    private void logEnvironmentConditions(){
        SimEnvironment[] myAirInputs = myCurrentCrewGroup
        .getAirConsumerDefinition().getEnvironments();
    	myLogger.info(getName() + " Environmental conditions were: 02="
    	+ myAirInputs[0].getO2Store().getCurrentLevel() + ", CO2="
        + myAirInputs[0].getCO2Store().getCurrentLevel() + ", N="
        + myAirInputs[0].getNitrogenStore().getCurrentLevel() + ", water="
        + myAirInputs[0].getVaporStore().getCurrentLevel() + ", other="
        + myAirInputs[0].getOtherStore().getCurrentLevel());
    }

    private static float waterLitersToMoles(float pLiters) {
        return (pLiters * 1000f) / 18.01524f; // 1000g/liter, 18.01524g/mole
    }

    private void eatFood() {
        FoodMatter[] foodConsumed = FoodConsumerDefinitionImpl
                .getCaloriesFromStore(myCurrentCrewGroup
                        .getFoodConsumerDefinition(), caloriesNeeded);
        foodMassConsumed = calculateFoodMass(foodConsumed);
        if ((foodConsumed.length == 0)
                || (myCurrentCrewGroup.getFoodConsumerDefinition().getStores().length == 0))
            caloriesConsumed = 0f;
        else {
            caloriesConsumed = FoodStoreImpl.calculateCalories(foodConsumed);
            potableWaterNeeded -= FoodStoreImpl
                    .calculateWaterContent(foodConsumed);
        }
    }

    private static float calculateFoodMass(FoodMatter[] pMatter) {
        float mass = 0f;
        if (pMatter == null)
            return 0f;
        for (int i = 0; i < pMatter.length; i++)
            mass += pMatter[i].mass;
        return mass;
    }

    /**
     * Attempts to consume resource for this crew memeber. inhales/drinks/eats,
     * then exhales/excretes
     */
    private void consumeResources() {
        //calculate consumption
        int currentActivityIntensity = myCurrentActivity.getActivityIntensity();
        O2Needed = calculateO2Needed(currentActivityIntensity);
        potableWaterNeeded = calculateCleanWaterNeeded(currentActivityIntensity);
        caloriesNeeded = calculateFoodNeeded(currentActivityIntensity);
        dirtyWaterProduced = calculateDirtyWaterProduced(potableWaterNeeded);
        dryWasteProduced = calculateDryWasteProduced(foodMassConsumed);
        greyWaterProduced = calculateGreyWaterProduced(potableWaterNeeded);
        CO2Produced = calculateCO2Produced(O2Needed);
        vaporProduced = calculateVaporProduced(potableWaterNeeded);
        //adjust tanks
        eatFood();
        potableWaterConsumed = StoreFlowRateControllableImpl
                .getFractionalResourceFromStores(myCurrentCrewGroup
                        .getPotableWaterConsumerDefinition(),
                        potableWaterNeeded, 1f / myCurrentCrewGroup
                                .getCrewSize());
        StoreFlowRateControllableImpl
                .getFractionalResourceFromStores(myCurrentCrewGroup
                        .getDirtyWaterProducerDefinition(), dirtyWaterProduced,
                        1f / myCurrentCrewGroup.getCrewSize());
        StoreFlowRateControllableImpl
                .getFractionalResourceFromStores(myCurrentCrewGroup
                        .getGreyWaterProducerDefinition(), greyWaterProduced,
                        1f / myCurrentCrewGroup.getCrewSize());
        StoreFlowRateControllableImpl
                .getFractionalResourceFromStores(myCurrentCrewGroup
                        .getDryWasteProducerDefinition(), dryWasteProduced,
                        1f / myCurrentCrewGroup.getCrewSize());

        SimEnvironment[] myAirInputs = myCurrentCrewGroup
                .getAirConsumerDefinition().getEnvironments();
        SimEnvironment[] myAirOutputs = myCurrentCrewGroup
                .getAirProducerDefinition().getEnvironments();
        if (myAirInputs.length < 1) {
            O2Consumed = 0f;
        } else {
            O2Consumed = myAirInputs[0].getO2Store().take(O2Needed);
        }
        if (myAirOutputs.length > 0) {
            myAirOutputs[0].getCO2Store().add(CO2Produced);
            myAirOutputs[0].getVaporStore().add(vaporProduced);
        }
    }

    public void log() {
        myLogger.debug("name=" + myName);
        myLogger.debug("current_activity=" + myCurrentActivity.getName());
        myLogger.debug("current_activity_order=" + currentOrder);
        myLogger.debug("duration_of_activity=" + timeActivityPerformed);
        myLogger.debug("has_died=" + hasDied);
        myLogger.debug("age=" + age);
        myLogger.debug("weight" + weight);
        if (sex == Sex.male)
            myLogger.debug("sex=male");
        else if (sex == Sex.female)
            myLogger.debug("sex=female");
        myLogger.debug("O2_consumed=" + O2Consumed);
        myLogger.debug("CO2_produced=" + CO2Produced);
        myLogger.debug("calories_consumed=" + caloriesConsumed);
        myLogger.debug("potable_water_consumed=" + potableWaterConsumed);
        myLogger.debug("dirty_water_produced=" + dirtyWaterProduced);
        myLogger.debug("grey_water_produced=" + greyWaterProduced);
        myLogger.debug("O2_needed=" + O2Needed);
        myLogger.debug("potable_water_needed=" + potableWaterNeeded);
        myLogger.debug("calories_needed=" + caloriesNeeded);
        myLogger.debug("vapor_produced=" + vaporProduced);
        logEnvironmentConditions();
    }

	public CrewGroup getCurrentCrewGroup() {
		return myCurrentCrewGroup;
	}

}