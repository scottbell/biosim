package biosim.server.simulation.crew;

import biosim.idl.simulation.air.*;
import biosim.idl.simulation.crew.*;
import biosim.idl.simulation.environment.*;
import biosim.idl.simulation.food.*;
import biosim.idl.simulation.power.*;
import biosim.idl.simulation.water.*;
import biosim.idl.simulation.framework.*;
import biosim.server.simulation.framework.*;
import biosim.idl.framework.*;
import biosim.idl.util.log.*;
import biosim.server.util.*;
import java.util.*;
import java.net.*;
import java.text.*;
/**
 * The Crew Person Implementation.  Eats/drinks/excercises away resources according to a set schedule.
 * 
 * @author    Scott Bell
 */


public class CrewPersonImpl extends CrewPersonPOA {
	//The name of this crew memeber
	private String myName = "No Name";
	//The current activity this crew member is performing
	private Activity myCurrentActivity;
	//Flag to determine if the server references have been collected for putting/taking resources
	private boolean hasCollectedReferences = false;
	//The current activity order this crew member is on
	private int currentOrder = 0;
	//How long this crew member has been performing thier current activity (in ticks)
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
	private float O2Consumed= 0f;
	//How much CO2 this crew member produced in the current tick
	private float CO2Produced = 0f;
	//How much food this crew member consumed in the current tick
	private float caloriesConsumed = 0f;
	//How much food this crew member consumed in the current tick (in kg)
	private float foodMassConsumed = 0f;
	//How much potable water this crew member consumed in the current tick
	private float cleanWaterConsumed = 0f;
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
	//Whether this crew member is sick or not.  If the crew member is sick, puts them into sleep like state.
	private boolean sick = false;
	//A mission productivity measure, used when "mission" is specified in the schedule.  Not implemented correctly yet.
	private float myMissionProductivity = 0;
	//The schedule used for this crew memeber
	private Schedule mySchedule;
	//The crew group associated with this crew member
	private CrewGroupImpl myCrewGroup;
	//Used to format floats
	private DecimalFormat numFormat;
	private boolean logInitialized = false;
	private LogIndex myLogIndex;
	private Random myRandomGen;
	private SimpleBuffer consumedWaterBuffer;
	private SimpleBuffer consumedOxygenBuffer;
	private SimpleBuffer consumedCaloriesBuffer;
	private SimpleBuffer consumedCO2Buffer;
	private SimpleBuffer sleepBuffer;
	private SimpleBuffer leisureBuffer;
	private static final float WATER_TILL_DEAD = 8.1f;
	private static final float WATER_RECOVERY_RATE=0.01f;
	private static final float OXYGEN_TILL_DEAD = 3f;
	private static final float OXYGEN_RECOVERY_RATE=0.01f;
	private static final float CALORIE_TILL_DEAD = 180000f;
	private static final float CALORIE_RECOVERY_RATE=0.0001f;
	private static final float DANGEROUS_CO2_RATION = 0.06f;
	private static final float CO2_TILL_DEAD = 10f;
	private static final float CO2_RECOVERY_RATE=0.001f;
	private static final float LEISURE_TILL_BURNOUT = 168f;
	private static final float LEISURE_RECOVERY_RATE=90f;
	private static final float AWAKE_TILL_EXHAUSTION = 120f;
	private static final float SLEEP_RECOVERY_RATE=12f;

	/**
	* Constructor that creates a new crew person
	* @param pName the name of the new crew person
	* @param pAge the age of the new crew person
	* @param pSex the sex of the new crew person
	* @param pCrewGroup the crew that the new crew person belongs in
	*/
	CrewPersonImpl(String pName, float pAge, float pWeight, Sex pSex, CrewGroupImpl pCrewGroup){
		this(pName, pAge, pWeight, pSex, pCrewGroup, new Schedule());
	}

	CrewPersonImpl(String pName, float pAge, float pWeight, Sex pSex, CrewGroupImpl pCrewGroup, Schedule pSchedule){
		myName = pName;
		age = pAge;
		weight = pWeight;
		sex = pSex;
		myCrewGroup = pCrewGroup;
		mySchedule = pSchedule;
		consumedWaterBuffer = new SimpleBuffer(WATER_TILL_DEAD, WATER_TILL_DEAD);
		consumedOxygenBuffer = new SimpleBuffer(OXYGEN_TILL_DEAD, OXYGEN_TILL_DEAD);
		consumedCaloriesBuffer = new SimpleBuffer(CALORIE_TILL_DEAD, CALORIE_TILL_DEAD);
		consumedCO2Buffer = new SimpleBuffer(CO2_TILL_DEAD, CO2_TILL_DEAD);
		sleepBuffer = new SimpleBuffer(AWAKE_TILL_EXHAUSTION, AWAKE_TILL_EXHAUSTION);
		leisureBuffer = new SimpleBuffer(LEISURE_TILL_BURNOUT, LEISURE_TILL_BURNOUT);
		myRandomGen = new Random();
		numFormat = new DecimalFormat("#,##0.0;(#)");
		myCurrentActivity = mySchedule.getScheduledActivityByOrder(currentOrder);
	}

	/**
	* Resets the state of this crew member
	*/
	void reset(){
		consumedWaterBuffer.reset();
		consumedOxygenBuffer.reset();
		consumedCaloriesBuffer.reset();
		consumedCO2Buffer.reset();
		sleepBuffer.reset();
		leisureBuffer.reset();
		myMissionProductivity = 0f;
		currentOrder = 0;
		myCurrentActivity = mySchedule.getScheduledActivityByOrder(currentOrder);
		timeActivityPerformed = 0;
		sick = false;
		hasDied = false;
		O2Consumed= 0f;
		CO2Produced = 0f;
		caloriesConsumed = 0f;
		foodMassConsumed = 0f;
		cleanWaterConsumed = 0f;
		dirtyWaterProduced = 0f;
		dryWasteProduced = 0f;
		greyWaterProduced = 0f;
		O2Needed = 0f;
		potableWaterNeeded = 0f;
		caloriesNeeded = 0f;
		vaporProduced = 0f;
	}

	/**
	* Returns the name given to this crew memeber
	* @return the name of the crew member
	*/
	public String getName(){
		return myName;
	}

	/**
	* Returns the age given to this crew memeber
	* @return the age of the crew member
	*/
	public float getAge(){
		return age;
	}

	/**
	* Returns the grey water produced (in liters) by this crew member during the current tick
	* @return the grey water produced (in liters) by this crew member during the current tick
	*/
	public float getGreyWaterProduced(){
		return greyWaterProduced;
	}

	/**
	* Returns the dirty water produced (in liters) by this crew member during the current tick
	* @return the dirty water produced (in liters) by this crew member during the current tick
	*/
	public float getDirtyWaterProduced(){
		return dirtyWaterProduced;
	}
	
	/**
	* Returns the dry waste produced (in liters) by this crew member during the current tick
	* @return the dry waste produced (in liters) by this crew member during the current tick
	*/
	public float getDryWasteProduced(){
		return dirtyWaterProduced;
	}

	/**
	* Returns the potable water consumed (in liters) by this crew member during the current tick
	* @return the potable water consumed (in liters) by this crew member during the current tick
	*/
	public float getPotableWaterConsumed(){
		return cleanWaterConsumed;
	}

	/**
	* Returns the food consumed (in liters) by this crew member during the current tick
	* @return the food consumed (in liters) by this crew member during the current tick
	*/
	public float getFoodConsumed(){
		return caloriesConsumed;
	}

	/**
	* Returns the CO2 produced (in moles) by this crew member during the current tick
	* @return the CO2 produced (in moles)  by this crew member during the current tick
	*/
	public float getCO2Produced(){
		return CO2Produced;
	}

	/**
	* Returns the O2 consumed (in moles) by this crew member during the current tick
	* @return the O2 consumed (in moles) by this crew member during the current tick
	*/
	public float getO2Consumed(){
		return O2Consumed;
	}

	/**
	* Checks whether the crew member is starving or not
	* @return <code>true</code> if the crew memeber is starving, <code>false</code> if not.
	*/
	public boolean isStarving(){
		return false;
	}

	/**
	* Checks whether the crew member is CO2 poisoned or not
	* @return <code>true</code> if the crew memeber is CO2 poisoned, <code>false</code> if not.
	*/
	public boolean isPoisoned(){
		return false;
	}

	/**
	* Checks whether the crew member is thirsty or not
	* @return <code>true</code> if the crew memeber is thirsty, <code>false</code> if not.
	*/
	public boolean isThirsty(){
		return false;
	}

	/**
	* Checks whether the crew member is suffocating from lack of O2 or not
	* @return <code>true</code> if the crew memeber is suffocating from lack of O2, <code>false</code> if not.
	*/
	public boolean isSuffocating(){
		return false;
	}

	/**
	* Returns how much the crew member weighs (in kilograms)
	* @return the crew person's weight in kilograms
	*/
	public float getWeight(){
		return weight;
	}

	/**
	* Checks whether the crew member is dead or not
	* @return <code>true</code> if the crew memeber is dead, <code>false</code> if not.
	*/
	public boolean isDead(){
		return hasDied;
	}

	/**
	* Returns the crew member's sex
	* @return the crew person's sex
	*/
	public Sex getSex(){
		return sex;
	}

	public void setSchedule(Schedule pSchedule){
		mySchedule = pSchedule;
	}

	/**
	* Makes this crew member sick (sleep like)
	*/
	public void sicken(){
		sick = true;
		myCurrentActivity = mySchedule.getActivityByName("sick");
	}

	/**
	* Returns the crew member's current activity
	* @return the crew member's current activity
	*/
	public Activity getCurrentActivity(){
		return myCurrentActivity;
	}

	/**
	* Sets the crew memeber's activity to a new one specified.
	* @param pActivity the crew member's new activity
	*/
	public void setCurrentActivity(Activity pActivity){
		myCurrentActivity = pActivity;
		currentOrder = mySchedule.getOrderOfScheduledActivity(myCurrentActivity.getName());
	}

	/**
	* Returns a scheduled activity by name (like "sleeping")
	* @param name the name of the activity to fetch
	* @return the activity in the schedule asked for by name
	*/
	public Activity getActivityByName(String name){
		return mySchedule.getActivityByName(name);
	}

	/**
	* Returns a scheduled activity by order
	* @param order the order of the activity to fetch
	* @return the activity in the schedule asked for by order
	*/
	public Activity getScheduledActivityByOrder(int order){
		return mySchedule.getScheduledActivityByOrder(order);
	}

	/**
	* Returns how long the crew member's been performing the current activity
	* @return the time the crew memeber has been performing the current activity
	*/
	public int getTimeActivityPerformed(){
		return timeActivityPerformed;
	}

	/**
	* Returns a string representation of the crew memeber in the form:   
	* [name] now performing [activity] for [x] hours
	* @return a string representation of the crew member
	*/
	public String toString(){
		return (myName + " now performing activity " +myCurrentActivity.getName() +
		        " for " + timeActivityPerformed + " of "+myCurrentActivity .getTimeLength() +" hours");
	}

	public boolean isSick(){
		return sick;
	}

	/**
	* If the crew memeber has been performing the current activity long enough, the new scheduled activity is assigned.
	*/
	private void advanceActivity(){
		checkForMeaningfulActivity();
		if (timeActivityPerformed >= myCurrentActivity.getTimeLength()){
			if (sick)
				sick = false;
			currentOrder++;
			if (currentOrder >= (mySchedule.getNumberOfScheduledActivities()))
				currentOrder = 1;
			myCurrentActivity = mySchedule.getScheduledActivityByOrder(currentOrder);
			timeActivityPerformed = 0;
		}
	}

	/**
	* Looks at current activity to see if it's "meaningful"
	* i.e., the activity affects other modules.
	* mission - productivity measure, used for metrics.  the longer the crew does this, the better evaluation (not implemented yet)
	* maitenance - prevents other modules from breaking down (not implemented yet)
	* repair - attempts to fix a module.  may have to be called several time depending on the severity of the malfunction
	*/
	private void checkForMeaningfulActivity(){
		//System.out.println("Checking to see if "+myCurrentActivity.getName()+" is a meaningful activity");
		if (myCurrentActivity.getName().equals("mission")){
			addProductivity();
			//System.out.println("adding mission!");
		}
		else if (myCurrentActivity.getName().equals("maitenance")){
			//System.out.println("");
		}
		else if (myCurrentActivity.getName().startsWith("sleep") || myCurrentActivity.getName().startsWith("sick")){
			sleepBuffer.add(SLEEP_RECOVERY_RATE);
			//System.out.println("adding "+(SLEEP_RECOVERY_RATE)+" to sleep buffer ("+sleepBuffer.getLevel()+"/"+sleepBuffer.getCapacity()+")");
		}
		else if (myCurrentActivity.getName().equals("leisure")){
			leisureBuffer.add(LEISURE_RECOVERY_RATE);
			//System.out.println("adding maitenance!");
		}
		//System.out.println("here2");
		if (myCurrentActivity instanceof RepairActivity){
			RepairActivity repairActivity = (RepairActivity)(myCurrentActivity);
			repairModule(repairActivity.getModuleNameToRepair(), repairActivity.getMalfunctionIDToRepair());
		}
	}

	/**
	* productivity measure, used for metrics.  the longer the crew does this, the better evaluation (not implemented yet)
	* invoked when crew person performs "mission" activity
	*/
	private void addProductivity(){
		float caloriePercentFull = sigmoidLikeProbability(consumedCaloriesBuffer.getLevel() / consumedCaloriesBuffer.getCapacity());
		float waterPercentFull = sigmoidLikeProbability(consumedWaterBuffer.getLevel() / consumedWaterBuffer.getCapacity());
		float oxygenPercentFull = sigmoidLikeProbability(consumedOxygenBuffer.getLevel() / consumedOxygenBuffer.getCapacity());
		float CO2PercentFull = sigmoidLikeProbability(consumedCO2Buffer.getLevel() / consumedCO2Buffer.getCapacity());
		float sleepPercentFull = sleepBuffer.getLevel() / sleepBuffer.getCapacity();
		float leisurePercentFull = leisureBuffer.getLevel() / leisureBuffer.getCapacity();
		
		float averagePercentFull = (caloriePercentFull + waterPercentFull + oxygenPercentFull + CO2PercentFull + sleepPercentFull + leisurePercentFull) / 6f;
		myMissionProductivity += myCrewGroup.randomFilter(averagePercentFull);
	}
	
	/**
	* Returns mission productivity.
	*/
	public float getProductivity(){
		return myMissionProductivity;
	}

	/**
	* attempts to fix a module.  may have to be called several time depending on the severity of the malfunction
	* invoked when crew person performs "repair" activity
	*/
	private void repairModule(String moduleName, long id){
		try{
			BioModule moduleToRepair = BioModuleHelper.narrow(OrbUtils.getNamingContext(myCrewGroup.getID()).resolve_str(moduleName));
			moduleToRepair.repair(id);
		}
		catch (org.omg.CORBA.UserException e){
			System.err.println("CrewPersonImp:"+myCrewGroup.getID()+": Couldn't locate "+moduleName+" to repair, skipping...");
		}
	}

	/**
	* prevents other modules from breaking down (not implemented yet)
	* invoked when crew person performs "maitenance" activity
	*/
	private void maitenanceModule(String moduleName){
	}

	/**
	* When the CrewGroup ticks the crew member, the member:
	* 1) increases the time the activity has been performed by 1.
	* on the condition that the crew memeber isn't dead he/she then:.
	* 2) attempts to collect references to various server (if not already done).
	* 3) possibly advances to the next activity.
	* 4) consumes air/food/water, exhales and excretes.
	* 5) takes afflictions from lack of any resources.
	* 6) checks whether afflictions (if any) are fatal.
	*/
	public void tick(){
		timeActivityPerformed++;
		//System.out.println(getName()+" performed activity "+myCurrentActivity.getName()+" for "+timeActivityPerformed+" of "+myCurrentActivity.getTimeLength()+" ticks");
		if (!hasDied){
			advanceActivity();
			consumeResources();
			afflictCrew();
			healthCheck();
			recoverCrew();
		}
	}

	/**
	* Calculate the current O2 needed by the crew memeber given the intensity of the activity for the current tick.
	* Algorithm derived from "Top Level Modeling of Crew Component of ALSS" by Goudrazi and Ting
	* @param currentActivityIntensity the intensity of the current activity
	* @return O2 needed in liters
	*/
	private float calculateO2Needed(int currentActivityIntensity){
		if (currentActivityIntensity < 0)
			return 0f;
		float heartRate = (currentActivityIntensity * 30f) + 15f;
		float a = 0.223804f;
		float b = 5.64f * pow(10f, -7f);
		float resultInLiters = a + (b * pow(heartRate, 3f) * 60f); //liters per hour
		float idealGasConstant = 0.08206f;
		float resultInMoles = (resultInLiters) / (idealGasConstant * 298); //moles per hour
		return myCrewGroup.randomFilter(resultInMoles); //Liters/hour
	}

	private float pow(float a, float b){
		return (new Double(Math.pow(a,b))).floatValue();
	}

	/**
	* Calculate the current CO2 produced by the crew memeber given the O2 consumed for the current tick.
	* Algorithm derived from "Top Level Modeling of Crew Component of ALSS" by Goudrazi and Ting
	* @param O2Consumed the O2 consumed (in moles) during this tick
	* @return CO2 produced in moles
	*/
	private float calculateCO2Produced(float O2Consumed){
		return O2Consumed * 0.86f;
	}

	/**
	* Calculate the current O2 produced by the crew memeber given the O2 consumed for the current tick.
	* Algorithm derived from "Top Level Modeling of Crew Component of ALSS" by Goudrazi and Ting
	* @param O2Consumed the O2 consumed (in liters) during this tick
	* @return O2 produced in liters
	*/
	private float calculateO2Produced(float O2Consumed){
		return myCrewGroup.randomFilter(O2Consumed * 0.14f);
	}

	public void insertActivityInSchedule(Activity pActivity, int pOrder){
		mySchedule.insertActivityInSchedule(pActivity, pOrder);
	}

	public void insertActivityInScheduleNow(Activity pActivity){
		mySchedule.insertActivityInSchedule(pActivity, currentOrder + 1);
	}

	public int getOrderOfScheduledActivity(String activityName){
		return mySchedule.getOrderOfScheduledActivity(activityName);
	}

	/**
	* Calculate the current food needed (in calories) by the crew memeber given the intensity of the activity for the current tick.
	* Algorithm derived from "Top Level Modeling of Crew Component of ALSS" by Goudrazi and Ting
	* @param currentActivityIntensity the activity intensity for the current tick
	* @return food needed in kilograms
	*/
	private float calculateFoodNeeded(int currentActivityIntensity){
		if (currentActivityIntensity < 0f)
			return 0f;
		float activityCoefficient = (0.5f * (currentActivityIntensity - 1f)) + 1f;
		float joulesNeeded  = 0f;
		if (sex == Sex.male)
			if (age < 30f)
				joulesNeeded = (106f * weight) + (5040f * activityCoefficient);
			else
				joulesNeeded = (86f * weight) + (5990f * activityCoefficient);
		else
			if (age < 30f)
				joulesNeeded = (106f * weight) + (3200f * activityCoefficient);
			else
				joulesNeeded = (106f * weight) + (6067f * activityCoefficient);
		//make it for one hour
		joulesNeeded /= 24f;
		caloriesNeeded = joulesNeeded / 4.2f;
		return myCrewGroup.randomFilter(caloriesNeeded);
	}

	/**
	* Calculate the dirty water produced given the potable water consumed for the current tick.
	* @param potableWaterConsumed the potable water consumed (in liters) during this tick
	* @return dirty water produced in liters
	*/
	private float calculateDirtyWaterProduced(float potableWaterConsumed){
		return myCrewGroup.randomFilter(potableWaterConsumed * 0.3625f);
	}
	
	/**
	* Calculate the dry waste produced given the potable water consumed for the current tick.
	* @param foodConsumed the food consumed (in kg) during this tick
	* @return dry waste produced in kg
	*/
	private float calculateDryWasteProduced(float foodConsumed){
		return myCrewGroup.randomFilter(foodConsumed * 0.022f);
	}

	/**
	* Calculate the grey water produced given the potable water consumed for the current tick.
	* @param potableWaterConsumed the potable water consumed (in liters) during this tick
	* @return grey water produced in liters
	*/
	private float calculateGreyWaterProduced(float potableWaterConsumed){
		return myCrewGroup.randomFilter(potableWaterConsumed * 0.5375f);
	}

	/**
	* Calculate the current vapor produced by the crew memeber given the O2 consumed for the current tick.
	* @param potableWaterConsumed the potable water consumed (in liters) during this tick
	* @return vapor produced in moles
	*/
	private float calculateVaporProduced(float potableWaterConsumed){
		return waterLitersToMoles(potableWaterConsumed * 0.1f);
	}

	/**
	* Calculate the clean water needed given the activity intensity for the current tick.
	* @param currentActivityIntensity the activity intensity for the current tick
	* @return potable water needed in liters
	*/
	private float calculateCleanWaterNeeded(int currentActivityIntensity){
		if (currentActivityIntensity > 0)
			return myCrewGroup.randomFilter(0.1667f);
		else
			return myCrewGroup.randomFilter(0f);
	}

	/**
	* Calculate the CO2 ratio in the breath of air inhaled by the crew member.
	* Used to see if crew member has inhaled lethal amount of CO2
	* @param aBreath the breath inhaled by the crew memeber this tick
	* @return percentage of CO2 in air
	*/
	private float getCO2Ratio(){
		SimEnvironment[] myAirInputs = myCrewGroup.getAirInputs();
		if (myAirInputs.length < 1){
			return 0f;
		}
		else{
			if (myAirInputs[0].getTotalMoles() <= 0)
				return 0f;
			else
				return (myAirInputs[0].getCO2Moles() / myAirInputs[0].getTotalMoles());
		}
	}

	private void recoverCrew(){
		consumedCaloriesBuffer.add(CALORIE_RECOVERY_RATE * consumedCaloriesBuffer.getCapacity());
		consumedWaterBuffer.add(WATER_RECOVERY_RATE * consumedWaterBuffer.getCapacity());
		consumedOxygenBuffer.add(OXYGEN_RECOVERY_RATE * consumedOxygenBuffer.getCapacity());
		consumedCO2Buffer.add(CO2_RECOVERY_RATE * consumedCO2Buffer.getCapacity());
	}

	/**
	* If not all the resources required were consumed, we damage the crew member.
	*/
	private void afflictCrew(){
		sleepBuffer.take(1);
		leisureBuffer.take(1);
		consumedCaloriesBuffer.take(caloriesNeeded - caloriesConsumed);
		consumedWaterBuffer.take(potableWaterNeeded - cleanWaterConsumed);
		consumedOxygenBuffer.take(O2Needed - O2Consumed);
		if (getCO2Ratio() > DANGEROUS_CO2_RATION)
			consumedCO2Buffer.take(getCO2Ratio() - DANGEROUS_CO2_RATION);
		
		
	}

	private float abs(float a){
		return (new Double(Math.abs(a))).floatValue();
	}

	private float sigmoidLikeProbability(float x){
		if (x >= 1f)
			return 1f;
		else if ((x < 1f) && (x > 0f))
			return 0.3f * x * (1f - abs(x - 2f) / 2f);
		else
			return 0f;
	}

	/**
	* Checks to see if crew memeber has been lethally damaged (i.e., hasn't received a resource for too many ticks)
	*/
	private void healthCheck(){
		//check for death
		float randomNumber = myRandomGen.nextFloat();
		float calorieRiskReturn = sigmoidLikeProbability((consumedCaloriesBuffer.getCapacity() - consumedCaloriesBuffer.getLevel()) / consumedCaloriesBuffer.getCapacity());
		float waterRiskReturn = sigmoidLikeProbability((consumedWaterBuffer.getCapacity() - consumedWaterBuffer.getLevel()) / consumedWaterBuffer.getCapacity());
		float oxygenRiskReturn = sigmoidLikeProbability((consumedOxygenBuffer.getCapacity() - consumedOxygenBuffer.getLevel()) / consumedOxygenBuffer.getCapacity());
		float CO2RiskReturn = sigmoidLikeProbability((consumedCO2Buffer.getCapacity() - consumedCO2Buffer.getLevel()) / consumedCO2Buffer.getCapacity());
		float sleepRiskReturn = sigmoidLikeProbability((sleepBuffer.getCapacity() - sleepBuffer.getLevel()) / sleepBuffer.getCapacity());

		//System.out.println(getName());
		//System.out.println("\tcalorie taken="+(caloriesNeeded - caloriesConsumed)+", recovered "+CALORIE_RECOVERY_RATE * consumedCaloriesBuffer.getCapacity()+" calorie risk level="+(consumedCaloriesBuffer.getCapacity() - consumedCaloriesBuffer.getLevel()) / consumedCaloriesBuffer.getCapacity()+" (level="+consumedCaloriesBuffer.getLevel()+", capacity="+consumedCaloriesBuffer.getCapacity()+")");
		//System.out.println("\twater taken="+(potableWaterNeeded - cleanWaterConsumed)+", recovered "+WATER_RECOVERY_RATE * consumedWaterBuffer.getCapacity()+" thirst risk level="+(consumedWaterBuffer.getCapacity() - consumedWaterBuffer.getLevel()) / consumedWaterBuffer.getCapacity()+" (level="+consumedWaterBuffer.getLevel()+", capacity="+consumedWaterBuffer.getCapacity()+")");
		//System.out.println("\toxygen taken="+(O2Needed - O2Consumed)+", recovered "+OXYGEN_RECOVERY_RATE * consumedOxygenBuffer.getCapacity()+" O2 risk level="+(consumedOxygenBuffer.getCapacity() - consumedOxygenBuffer.getLevel()) / consumedOxygenBuffer.getCapacity()+" (level="+consumedOxygenBuffer.getLevel()+", capacity="+consumedOxygenBuffer.getCapacity()+")");
		//System.out.println("\tCO2 taken="+(getCO2Ratio() - DANGEROUS_CO2_RATION)+", recovered "+CO2_RECOVERY_RATE * consumedCO2Buffer.getCapacity()+" CO2 risk level="+(consumedCO2Buffer.getCapacity() - consumedCO2Buffer.getLevel()) / consumedCO2Buffer.getCapacity()+" (level="+consumedCO2Buffer.getLevel()+", capacity="+consumedCO2Buffer.getCapacity()+")");
		//System.out.println("\tsleep (level="+sleepBuffer.getLevel()+", capacity="+sleepBuffer.getCapacity()+")");
		//System.out.println("\tCO2 ration ="+getCO2Ratio()+", DANGEROUS_CO2_RATION="+DANGEROUS_CO2_RATION);
		
		if (sleepRiskReturn > (randomNumber + 0.05f)){
			sicken();
			System.out.println(getName()+" has fallen ill from exhaustion (risk was "+numFormat.format(sleepRiskReturn * 100)+"%) @ tick "+myCrewGroup.getMyTicks());
		}
		
		if (calorieRiskReturn > randomNumber){
			hasDied = true;
			System.out.println(getName()+" has died from starvation (risk was "+numFormat.format(calorieRiskReturn * 100)+"%)");
		}
		else if (waterRiskReturn > randomNumber){
			hasDied = true;
			System.out.println(getName()+" has died from thirst (risk was "+numFormat.format(waterRiskReturn * 100)+"%)");
		}
		else if (oxygenRiskReturn > randomNumber){
			hasDied = true;
			System.out.println(getName()+" has died from lack of oxygen (risk was "+numFormat.format(oxygenRiskReturn * 100)+"%)");
		}
		else if (CO2RiskReturn > randomNumber){
			hasDied = true;
			System.out.println(getName()+" has died from CO2 poisoning (risk was "+numFormat.format(CO2RiskReturn * 100)+"%)");
		}
		//if died, kill
		if (hasDied){
			O2Consumed= 0f;
			CO2Produced = 0f;
			caloriesConsumed = 0f;
			cleanWaterConsumed = 0f;
			dirtyWaterProduced = 0f;
			greyWaterProduced = 0f;
			caloriesConsumed = 0f;
			myCurrentActivity = mySchedule.getActivityByName("dead");
			timeActivityPerformed = 0;
		}
	}

	private static float waterLitersToMoles(float pLiters){
		return (pLiters * 1000f) / 18.01524f; // 1000g/liter, 18.01524g/mole
	}

	private void eatFood(float pFoodNeeded){
		FoodMatter[] foodConsumed = myCrewGroup.getCaloriesFromStore(myCrewGroup.getFoodInputs(), myCrewGroup.getFoodInputMaxFlowRates(), myCrewGroup.getFoodInputDesiredFlowRates(), myCrewGroup.getFoodInputActualFlowRates(), caloriesNeeded);
		foodMassConsumed = calculateFoodMass(foodConsumed);
		if ((foodConsumed.length == 0) || (myCrewGroup.getFoodInputs().length == 0))
			caloriesConsumed = 0f;
		else
			caloriesConsumed = myCrewGroup.getFoodInputs()[0].calculateCalories(foodConsumed);
	}
	
	private static float calculateFoodMass(FoodMatter[] pMatter){
		float mass = 0f;
		if (pMatter == null)
			return 0f;
		for (int i = 0; i < pMatter.length; i++)
			mass += pMatter[i].mass;
		return mass;
	}

	/**
	* Attempts to consume resource for this crew memeber.
	* inhales/drinks/eats, then exhales/excretes
	*/
	private void consumeResources(){
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
		eatFood(caloriesNeeded);
		cleanWaterConsumed = myCrewGroup.getFractionalResourceFromStore(myCrewGroup.getPotableWaterInputs(), myCrewGroup.getPotableWaterInputMaxFlowRates(), myCrewGroup.getPotableWaterInputDesiredFlowRates(), myCrewGroup.getPotableWaterInputActualFlowRates(), potableWaterNeeded, 1f / myCrewGroup.getCrewSize());
		float distributedDirtyWater = myCrewGroup.pushFractionalResourceToStore(myCrewGroup.getDirtyWaterOutputs(), myCrewGroup.getDirtyWaterOutputMaxFlowRates(), myCrewGroup.getDirtyWaterOutputDesiredFlowRates(), myCrewGroup.getDirtyWaterOutputActualFlowRates(), dirtyWaterProduced, 1f / myCrewGroup.getCrewSize());
		float distributedGreyWater = myCrewGroup.pushFractionalResourceToStore(myCrewGroup.getGreyWaterOutputs(), myCrewGroup.getGreyWaterOutputMaxFlowRates(), myCrewGroup.getGreyWaterOutputDesiredFlowRates(), myCrewGroup.getGreyWaterOutputActualFlowRates(), greyWaterProduced, 1f / myCrewGroup.getCrewSize());
		float distributedDryWaste = myCrewGroup.pushFractionalResourceToStore(myCrewGroup.getDryWasteOutputs(), myCrewGroup.getDryWasteOutputMaxFlowRates(), myCrewGroup.getDryWasteOutputDesiredFlowRates(), myCrewGroup.getDryWasteOutputActualFlowRates(), greyWaterProduced, 1f / myCrewGroup.getCrewSize());

		SimEnvironment[] myAirInputs = myCrewGroup.getAirInputs();
		SimEnvironment[] myAirOutputs = myCrewGroup.getAirOutputs();
		if (myAirInputs.length < 1){
			O2Consumed = 0f;
		}
		else{
			O2Consumed = myAirInputs[0].takeO2Moles(O2Needed);
		}
		if (myAirOutputs.length > 0){
			myAirOutputs[0].addCO2Moles(CO2Produced);
			myAirOutputs[0].addWaterMoles(vaporProduced);
		}
	}

	public void log(LogNode myLogHead){
		//If not initialized, fill in the log
		if (!logInitialized){
			myLogIndex = new LogIndex();
			LogNode nameHead = myLogHead.addChild("name");
			myLogIndex.nameIndex = nameHead.addChild(""+myName);
			LogNode currentActivityHead = myLogHead.addChild("current_activity");
			myLogIndex.currentActivityIndex = currentActivityHead.addChild(""+myCurrentActivity.getName());
			LogNode currentActivityOrderHead = myLogHead.addChild("current_activity_order");
			myLogIndex.currentActivityOrderIndex = currentActivityOrderHead.addChild(""+currentOrder);
			LogNode timeActivityPerformedHead = myLogHead.addChild("duration_of_activity");
			myLogIndex.timeActivityPerformedIndex = timeActivityPerformedHead.addChild(""+timeActivityPerformed);
			LogNode hasDiedHead = myLogHead.addChild("has_died");
			myLogIndex.hasDiedIndex = hasDiedHead.addChild(""+hasDied);
			LogNode ageHead = myLogHead.addChild("age");
			myLogIndex.ageIndex = ageHead.addChild(""+age);
			LogNode weightHead = myLogHead.addChild("weight");
			myLogIndex.weightIndex = weightHead.addChild(""+weight);
			LogNode sexHead = myLogHead.addChild("sex");
			if (sex == Sex.male)
				myLogIndex.sexIndex = sexHead.addChild("male");
			else if (sex == Sex.female)
				myLogIndex.sexIndex = sexHead.addChild("female");
			LogNode O2ConsumedHead = myLogHead.addChild("O2_consumed");
			myLogIndex.O2ConsumedIndex = O2ConsumedHead.addChild(""+O2Consumed);
			LogNode CO2ProducedHead = myLogHead.addChild("CO2_produced");
			myLogIndex.CO2ProducedIndex = CO2ProducedHead.addChild(""+CO2Produced);
			LogNode caloriesConsumedHead = myLogHead.addChild("food_consumed");
			myLogIndex.caloriesConsumedIndex = caloriesConsumedHead.addChild(""+caloriesConsumed);
			LogNode cleanWaterConsumedHead = myLogHead.addChild("potable_water_consumed");
			myLogIndex.cleanWaterConsumedIndex = cleanWaterConsumedHead.addChild(""+cleanWaterConsumed);
			LogNode dirtyWaterProducedHead = myLogHead.addChild("dirty_water_produced");
			myLogIndex.dirtyWaterProducedIndex = dirtyWaterProducedHead.addChild(""+dirtyWaterProduced);
			LogNode greyWaterProducedHead = myLogHead.addChild("grey_water_produced");
			myLogIndex.greyWaterProducedIndex = greyWaterProducedHead.addChild(""+greyWaterProduced);
			LogNode O2NeededHead = myLogHead.addChild("O2_needed");
			myLogIndex.O2NeededIndex = O2NeededHead.addChild(""+O2Needed);
			LogNode potableWaterNeededHead = myLogHead.addChild("potable_water_needed");
			myLogIndex.potableWaterNeededIndex = potableWaterNeededHead.addChild(""+potableWaterNeeded);
			LogNode caloriesNeededHead = myLogHead.addChild("food_needed");
			myLogIndex.caloriesNeededIndex = caloriesNeededHead.addChild(""+caloriesNeeded);
			logInitialized = true;
		}
		else{
			myLogIndex.nameIndex.setValue(""+myName);
			myLogIndex.currentActivityIndex.setValue(""+myCurrentActivity.getName());
			myLogIndex.currentActivityOrderIndex.setValue(""+currentOrder);
			myLogIndex.timeActivityPerformedIndex.setValue(""+timeActivityPerformed);
			myLogIndex.hasDiedIndex.setValue(""+hasDied);
			myLogIndex.ageIndex.setValue(""+age);
			myLogIndex.weightIndex.setValue(""+weight);
			if (sex == Sex.male)
				myLogIndex.sexIndex.setValue("male");
			else if (sex == Sex.female)
				myLogIndex.sexIndex.setValue("female");
			myLogIndex.O2ConsumedIndex.setValue(""+O2Consumed);
			myLogIndex.CO2ProducedIndex.setValue(""+CO2Produced);
			myLogIndex.caloriesConsumedIndex.setValue(""+caloriesConsumed);
			myLogIndex.cleanWaterConsumedIndex.setValue(""+cleanWaterConsumed);
			myLogIndex.dirtyWaterProducedIndex.setValue(""+dirtyWaterProduced);
			myLogIndex.greyWaterProducedIndex.setValue(""+greyWaterProduced);
			myLogIndex.O2NeededIndex.setValue(""+O2Needed);
			myLogIndex.potableWaterNeededIndex.setValue(""+potableWaterNeeded);
			myLogIndex.caloriesNeededIndex.setValue(""+caloriesNeeded);
		}
	}

	/**
	* For fast reference to the log tree
	*/
	private class LogIndex{
		public LogNode nameIndex;
		public LogNode currentActivityIndex;
		public LogNode currentActivityOrderIndex;
		public LogNode timeActivityPerformedIndex;
		public LogNode personStarvingIndex;
		public LogNode personThirstyIndex;
		public LogNode personSuffocatingIndex;
		public LogNode personPoisonedIndex;
		public LogNode hasDiedIndex;
		public LogNode ageIndex;
		public LogNode weightIndex;
		public LogNode sexIndex;
		public LogNode O2ConsumedIndex;
		public LogNode CO2ProducedIndex;
		public LogNode caloriesConsumedIndex;
		public LogNode cleanWaterConsumedIndex;
		public LogNode dirtyWaterProducedIndex;
		public LogNode greyWaterProducedIndex;
		public LogNode O2NeededIndex;
		public LogNode potableWaterNeededIndex;
		public LogNode caloriesNeededIndex;
	}
}
