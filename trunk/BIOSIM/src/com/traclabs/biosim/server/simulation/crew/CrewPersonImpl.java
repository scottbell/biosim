package biosim.server.crew;

import biosim.idl.air.*;
import biosim.idl.crew.*;
import biosim.idl.environment.*;
import biosim.idl.food.*;
import biosim.idl.power.*;
import biosim.idl.water.*;
import biosim.idl.framework.*;
import biosim.idl.util.log.*;
import biosim.server.util.*;
import java.util.*;
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
	//How long this crew member has been starving (in ticks)
	private int starvingTime = 0;
	//How long this crew member has been thirsty (in ticks)
	private int thirstTime = 0;
	//How long this crew member has been suffocating (in ticks)
	private int suffocateTime = 0;
	//How long this crew member has been CO2 poisoned (in ticks)
	private int poisonTime = 0;
	//Flag to determine if the person is starving
	private boolean personStarving = false;
	//Flag to determine if the person is thirsty
	private boolean personThirsty = false;
	//Flag to determine if the person is suffocating
	private boolean personSuffocating = false;
	//Flag to determine if the person is CO2 poisoned
	private boolean personPoisoned = false;
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
	private float foodConsumed = 0f;
	//How much potable water this crew member consumed in the current tick
	private float cleanWaterConsumed = 0f;
	//How much dirty water this crew member produced in the current tick
	private float dirtyWaterProduced = 0f;
	//How much grey water this crew member produced in the current tick
	private float greyWaterProduced = 0f;
	//How much O2 this crew member needs to consume in one tick
	private float O2Needed = 0f;
	//How much potable water this crew member needs to consume in one tick
	private float cleanWaterNeeded = 0f;
	//How much food this crew member needs to consume in one tick
	private float foodNeeded = 0f;
	//The breath inhaled by this crew member in the current tick
	private Breath airRetrieved;
	private boolean logInitialized = false;
	private LogIndex myLogIndex;
	private boolean isSick = false;
	private int myMissionProductivity = 0;
	private Schedule mySchedule;
	private CrewGroupImpl myCrewGroup;

	public final static String powerPSName = "PowerPS";
	public final static String powerStoreName = "PowerStore";
	public final static String airRSName = "AirRS";
	public final static String CO2StoreName = "CO2Store";
	public final static String O2StoreName = "O2Store";
	public final static String biomassRSName = "BiomassRS";
	public final static String biomassStoreName = "BiomassStore";
	public final static String foodProcessorName = "FoodProcessor";
	public final static String foodStoreName = "FoodStore";
	public final static String waterRSName = "WaterRS";
	public final static String dirtyWaterStoreName = "DirtyWaterStore";
	public final static String potableWaterStoreName = "PotableWaterStore";
	public final static String greyWaterStoreName = "GreyWaterStore";
	public final static String simEnvironmentName = "SimEnvironment";
	//A hastable containing the server references
	private static Map myModules;

	/**
	* Constructor that creates a new crew person
	* @param pName the name of the new crew person
	* @param pAge the age of the new crew person
	* @param pSex the sex of the new crew person
	* @param pCrewGroup the crew that the new crew person belongs in
	*/
	protected CrewPersonImpl(String pName, float pAge, float pWeight, Sex pSex, CrewGroupImpl pCrewGroup){
		myName = pName;
		age = pAge;
		weight = pWeight;
		sex = pSex;
		myCrewGroup = pCrewGroup;
		mySchedule = new Schedule();
		myCurrentActivity = mySchedule.getScheduledActivityByOrder(currentOrder);
	}

	protected void reset(){
		airRetrieved = new Breath(0f, 0f, 0f);
		mySchedule = new Schedule();
		myMissionProductivity = 0;
		currentOrder = 0;
		myCurrentActivity = mySchedule.getScheduledActivityByOrder(currentOrder);
		timeActivityPerformed = 0;
		starvingTime = 0;
		thirstTime = 0;
		suffocateTime = 0;
		poisonTime = 0;
		isSick = false;
		personStarving = false;
		personThirsty = false;
		personSuffocating = false;
		personPoisoned = false;
		hasDied = false;
		O2Consumed= 0f;
		CO2Produced = 0f;
		foodConsumed = 0f;
		cleanWaterConsumed = 0f;
		dirtyWaterProduced = 0f;
		greyWaterProduced = 0f;
		O2Needed = 0f;
		cleanWaterNeeded = 0f;
		foodNeeded = 0f;
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
		return foodConsumed;
	}

	/**
	* Returns the CO2 produced (in liters) by this crew member during the current tick
	* @return the CO2 produced (in liters)  by this crew member during the current tick
	*/
	public float getCO2Produced(){
		return CO2Produced;
	}

	/**
	* Returns the O2 consumed (in liters) by this crew member during the current tick
	* @return the O2 consumed (in liters) by this crew member during the current tick
	*/
	public float getO2Consumed(){
		return O2Consumed;
	}

	/**
	* Checks whether the crew member is starving or not
	* @return <code>true</code> if the crew memeber is starving, <code>false</code> if not.
	*/
	public boolean isStarving(){
		return personStarving;
	}

	/**
	* Checks whether the crew member is CO2 poisoned or not
	* @return <code>true</code> if the crew memeber is CO2 poisoned, <code>false</code> if not.
	*/
	public boolean isPoisoned(){
		return personPoisoned;
	}

	/**
	* Checks whether the crew member is thirsty or not
	* @return <code>true</code> if the crew memeber is thirsty, <code>false</code> if not.
	*/
	public boolean isThirsty(){
		return personThirsty;
	}

	/**
	* Checks whether the crew member is suffocating from lack of O2 or not
	* @return <code>true</code> if the crew memeber is suffocating from lack of O2, <code>false</code> if not.
	*/
	public boolean isSuffocating(){
		return personSuffocating;
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

	public void sicken(){
		isSick = true;
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

	/**
	* Tries to collect references to all the servers and adds them to a hashtable than can be accessed by outside classes.
	*/
	private void collectReferences(){
		if (hasCollectedReferences)
			return;
		// resolve the Objects Reference in Naming
		try{
			if (myModules == null)
				myModules = new Hashtable();
			int myID = myCrewGroup.getID();
			PowerPS myPowerPS = PowerPSHelper.narrow(OrbUtils.getNCRef().resolve_str(powerPSName+myID));
			myModules.put(powerPSName , myPowerPS);
			PowerStore myPowerStore = PowerStoreHelper.narrow(OrbUtils.getNCRef().resolve_str(powerStoreName+myID));
			myModules.put(powerStoreName , myPowerStore);
			AirRS myAirRS = AirRSHelper.narrow(OrbUtils.getNCRef().resolve_str(airRSName+myID));
			myModules.put(airRSName , myAirRS);
			SimEnvironment mySimEnvironment = SimEnvironmentHelper.narrow(OrbUtils.getNCRef().resolve_str(simEnvironmentName+myID));
			myModules.put(simEnvironmentName , mySimEnvironment);
			GreyWaterStore myGreyWaterStore = GreyWaterStoreHelper.narrow(OrbUtils.getNCRef().resolve_str(greyWaterStoreName+myID));
			myModules.put(greyWaterStoreName , myGreyWaterStore);
			PotableWaterStore myPotableWaterStore = PotableWaterStoreHelper.narrow(OrbUtils.getNCRef().resolve_str(potableWaterStoreName+myID));
			myModules.put(potableWaterStoreName , myPotableWaterStore);
			DirtyWaterStore myDirtyWaterStore = DirtyWaterStoreHelper.narrow(OrbUtils.getNCRef().resolve_str(dirtyWaterStoreName+myID));
			myModules.put(dirtyWaterStoreName , myDirtyWaterStore);
			FoodProcessor myFoodProcessor = FoodProcessorHelper.narrow(OrbUtils.getNCRef().resolve_str(foodProcessorName+myID));
			myModules.put(foodProcessorName , myFoodProcessor);
			FoodStore myFoodStore= FoodStoreHelper.narrow(OrbUtils.getNCRef().resolve_str(foodStoreName+myID));
			myModules.put(foodStoreName , myFoodStore);
			CO2Store myCO2Store = CO2StoreHelper.narrow(OrbUtils.getNCRef().resolve_str(CO2StoreName+myID));
			myModules.put(CO2StoreName , myCO2Store);
			O2Store myO2Store = O2StoreHelper.narrow(OrbUtils.getNCRef().resolve_str(O2StoreName+myID));
			myModules.put(O2StoreName , myO2Store);
			BiomassRS myBiomassRS = BiomassRSHelper.narrow(OrbUtils.getNCRef().resolve_str(biomassRSName+myID));
			myModules.put(biomassRSName , myBiomassRS);
			BiomassStore myBiomassStore = BiomassStoreHelper.narrow(OrbUtils.getNCRef().resolve_str(biomassStoreName+myID));
			myModules.put(biomassStoreName, myBiomassStore);
			WaterRS myWaterRS = WaterRSHelper.narrow(OrbUtils.getNCRef().resolve_str(waterRSName+myID));
			myModules.put(waterRSName , myWaterRS);
			hasCollectedReferences = true;
		}
		catch (org.omg.CORBA.UserException e){
			System.err.println("BioHolder: Had problems collecting server references, polling again...");
			OrbUtils.sleepAwhile();
			collectReferences();
		}
		catch (Exception e){
			System.err.println("BioHolder: Had problems collecting server references, polling again...");
			OrbUtils.resetInit();
			OrbUtils.sleepAwhile();
			collectReferences();
		}
	}

	/**
	* If the crew memeber has been performing the current activity long enough, the new scheduled activity is assigned.
	*/
	private void advanceActivity(){
		checkForMeaningfulActivity();
		if (timeActivityPerformed >= myCurrentActivity.getTimeLength()){
			currentOrder++;
			if (currentOrder >= (mySchedule.getNumberOfScheduledActivities()))
				currentOrder = 1;
			myCurrentActivity = mySchedule.getScheduledActivityByOrder(currentOrder);
			timeActivityPerformed = 0;
		}
	}

	private void checkForMeaningfulActivity(){
		if (myCurrentActivity.getName().equals("Mission")){
			addProductivity();
		}
		else if (myCurrentActivity.getName().startsWith("Maitenance")){
			StringTokenizer strtok = new StringTokenizer(myCurrentActivity.getName(), "-");
			if (strtok.countTokens() > 2){
				strtok.nextToken();
				maitenanceModule(strtok.nextToken());
			}
		}
		else if (myCurrentActivity.getName().startsWith("Repair")){
			StringTokenizer strtok = new StringTokenizer(myCurrentActivity.getName(), "-");
			if (strtok.countTokens() > 2){
				strtok.nextToken();
				repairModule(strtok.nextToken());
			}
		}
	}

	private void addProductivity(){
	}

	private void repairModule(String moduleName){
	}

	private void maitenanceModule(String moduleName){
	}

	private BioModule getBioModule(String moduleName){
		return null;
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
		if (!hasDied){
			collectReferences();
			advanceActivity();
			consumeResources();
			afflictCrew();
			deathCheck();
		}
		else if (isSick){
			collectReferences();
			consumeResources();
			afflictCrew();
			deathCheck();
			advanceActivity();
			isSick = false;
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
		double heartRate = (currentActivityIntensity * 30f) + 15f;
		double a = 0.223804f;
		double b = 5.64f * (Math.pow(10f, -7f));
		Double result = new Double((a+b * Math.pow(heartRate, 3f)) * 60f);
		return myCrewGroup.randomFilter(result.floatValue()); //Liters/hour
	}

	/**
	* Calculate the current CO2 produced by the crew memeber given the O2 consumed for the current tick.
	* Algorithm derived from "Top Level Modeling of Crew Component of ALSS" by Goudrazi and Ting
	* @param O2Consumed the O2 consumed (in liters) during this tick
	* @return CO2 produced in liters
	*/
	private float calculateCO2Produced(float O2Consumed){
		Double result = new Double(O2Consumed * 0.86);
		return myCrewGroup.randomFilter(result.floatValue());
	}

	/**
	* Calculate the current O2 produced by the crew memeber given the O2 consumed for the current tick.
	* Algorithm derived from "Top Level Modeling of Crew Component of ALSS" by Goudrazi and Ting
	* @param O2Consumed the O2 consumed (in liters) during this tick
	* @return O2 produced in liters
	*/
	private float calculateO2Produced(float O2Consumed){
		Double result = new Double(O2Consumed * 0.14);
		return myCrewGroup.randomFilter(result.floatValue());
	}

	public int hashCode(){
		return myName.hashCode();
	}

	/**
	* Calculate the current food needed (in kilograms) by the crew memeber given the intensity of the activity for the current tick.
	* Algorithm derived from "Top Level Modeling of Crew Component of ALSS" by Goudrazi and Ting
	* @param currentActivityIntensity the activity intensity for the current tick
	* @return food needed in kilograms
	*/
	private float calculateFoodNeeded(int currentActivityIntensity){
		if (currentActivityIntensity < 0)
			return 0f;
		double activityCoefficient = (0.7 * (currentActivityIntensity - 1)) + 1;
		double caloriesNeeded  = 0;
		if (sex == Sex.male)
			if (age < 30)
				caloriesNeeded = (106 * weight) + (5040 * activityCoefficient);
			else
				caloriesNeeded = (86 * weight) + (5990 * activityCoefficient);
		else
			if (age < 30)
				caloriesNeeded = (106 * weight) + (3200 * activityCoefficient);
			else
				caloriesNeeded = (106 * weight) + (6067 * activityCoefficient);
		//make it for one hour
		caloriesNeeded = caloriesNeeded / 24;
		//assume they're eating only carbs
		double energyFromFood = 17.22 * 1000;
		Double kgFoodNeeded = new Double(caloriesNeeded / energyFromFood);
		return myCrewGroup.randomFilter(kgFoodNeeded.floatValue());
	}

	/**
	* Calculate the dirty water produced given the potable water consumed for the current tick.
	* @param cleanWaterConsumed the potable water consumed (in liters) during this tick
	* @return dirty water produced in liters
	*/
	private float calculateDirtyWaterProduced(float cleanWaterConsumed){
		Double result = new Double(cleanWaterConsumed * 0.3625);
		return myCrewGroup.randomFilter(result.floatValue());
	}

	/**
	* Calculate the grey water produced given the potable water consumed for the current tick.
	* @param cleanWaterConsumed the potable water consumed (in liters) during this tick
	* @return grey water produced in liters
	*/
	private float calculateGreyWaterProduced(float cleanWaterConsumed){
		Double result = new Double(cleanWaterConsumed * 0.6375);
		return myCrewGroup.randomFilter(result.floatValue());
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
	private float getCO2Ratio(Breath aBreath){
		Double ratio = new Double(aBreath.CO2 / (aBreath.O2 + aBreath.CO2 + aBreath.other));
		return ratio.floatValue();
	}

	/**
	* If not all the resources required were consumed, we damage the crew member.
	*/
	private void afflictCrew(){
		//afflict crew
		if (foodConsumed < foodNeeded){
			personStarving = true;
			starvingTime++;
		}
		else{
			personStarving = false;
			starvingTime = 0;
		}
		if (cleanWaterConsumed < cleanWaterNeeded){
			personThirsty = true;
			thirstTime++;
		}
		else{
			personThirsty = false;
			thirstTime = 0;
		}
		if (getCO2Ratio(airRetrieved) > 0.06){
			personPoisoned = true;
			poisonTime++;
		}
		else{
			personPoisoned = false;
			poisonTime = 0;
		}
		if (O2Consumed < O2Needed){
			personSuffocating = true;
			System.out.println("CrewPersonImpl"+myCrewGroup.getID()+": "+myName + " needed "+O2Needed+" of 02, got "+O2Consumed);
			suffocateTime++;
		}
		else{
			personSuffocating = false;
			suffocateTime = 0;
		}
	}

	/**
	* Checks to see if crew memeber has been lethally damaged (i.e., hasn't received a resource for too many ticks)
	*/
	private void deathCheck(){
		//check for death
		if (starvingTime > 504){
			hasDied = true;
		}
		else if (thirstTime > 72){
			System.out.println("CrewPersonImpl"+myCrewGroup.getID()+": "+myName + " dead from thirst");
			hasDied = true;
		}
		else if (suffocateTime > 2){
			System.out.println("CrewPersonImpl"+myCrewGroup.getID()+": "+myName + " dead from suffocation");
			hasDied = true;
		}
		else if (poisonTime > 5){
			System.out.println("CrewPersonImpl"+myCrewGroup.getID()+": "+myName + " dead from CO2 poisoning");
			hasDied = true;
		}
		else{
			hasDied = false;
		}
		if (hasDied){
			O2Consumed= 0f;
			CO2Produced = 0f;
			foodConsumed = 0f;
			cleanWaterConsumed = 0f;
			dirtyWaterProduced = 0f;
			greyWaterProduced = 0f;
			myCurrentActivity = mySchedule.getActivityByName("dead");
			hasDied = true;
			timeActivityPerformed = 0;
		}
	}

	/**
	* Attempts to consume resource for this crew memeber.
	* inhales/drinks/eats, then exhales/excretes
	*/
	private void consumeResources(){
		//get server references from List
		FoodStore myFoodStore = (FoodStore)(myModules.get(foodStoreName));
		PotableWaterStore myPotableWaterStore = (PotableWaterStore)(myModules.get(potableWaterStoreName));
		SimEnvironment myCurrentEnvironment = (SimEnvironment)(myModules.get(simEnvironmentName));
		DirtyWaterStore myDirtyWaterStore = (DirtyWaterStore)(myModules.get(dirtyWaterStoreName));
		GreyWaterStore myGreyWaterStore = (GreyWaterStore)(myModules.get(greyWaterStoreName));
		//consume
		int currentActivityIntensity = myCurrentActivity.getActivityIntensity();
		O2Needed = calculateO2Needed(currentActivityIntensity);
		cleanWaterNeeded = calculateCleanWaterNeeded(currentActivityIntensity);
		foodNeeded = calculateFoodNeeded(currentActivityIntensity);
		dirtyWaterProduced = calculateDirtyWaterProduced(cleanWaterNeeded);
		greyWaterProduced = calculateGreyWaterProduced(dirtyWaterProduced);
		CO2Produced = calculateCO2Produced(O2Needed);
		//adjust tanks
		foodConsumed = myFoodStore.take(foodNeeded);
		cleanWaterConsumed = myPotableWaterStore.take(cleanWaterNeeded);
		airRetrieved = myCurrentEnvironment.takeO2Breath(O2Needed);
		O2Consumed = airRetrieved.O2;
		myDirtyWaterStore.add(dirtyWaterProduced);
		myGreyWaterStore.add(greyWaterProduced);
		myCurrentEnvironment.addCO2(CO2Produced);
		myCurrentEnvironment.addOther(airRetrieved.other);
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
			LogNode starvingTimeHead = myLogHead.addChild("starving_time");
			myLogIndex.starvingTimeIndex = starvingTimeHead.addChild(""+starvingTime);
			LogNode thirstTimeHead = myLogHead.addChild("thirst_time");
			myLogIndex.thirstTimeIndex = thirstTimeHead.addChild(""+thirstTime);
			LogNode suffocateTimeHead = myLogHead.addChild("suffocate_time");
			myLogIndex.suffocateTimeIndex = suffocateTimeHead.addChild(""+suffocateTime);
			LogNode poisonTimeHead = myLogHead.addChild("poison_time");
			myLogIndex.poisonTimeIndex = poisonTimeHead.addChild(""+poisonTime);
			LogNode personStarvingHead = myLogHead.addChild("person_starving");
			myLogIndex.personStarvingIndex = personStarvingHead.addChild(""+personStarving);
			LogNode personThirstyHead = myLogHead.addChild("person_thirsty");
			myLogIndex.personThirstyIndex = personThirstyHead.addChild(""+personThirsty);
			LogNode personSuffocatingHead = myLogHead.addChild("person_suffocating");
			myLogIndex.personSuffocatingIndex = personSuffocatingHead.addChild(""+personSuffocating);
			LogNode personPoisonedHead = myLogHead.addChild("person_poisoned");
			myLogIndex.personPoisonedIndex = personPoisonedHead.addChild(""+personPoisoned);
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
			LogNode foodConsumedHead = myLogHead.addChild("food_consumed");
			myLogIndex.foodConsumedIndex = foodConsumedHead.addChild(""+foodConsumed);
			LogNode cleanWaterConsumedHead = myLogHead.addChild("potable_water_consumed");
			myLogIndex.cleanWaterConsumedIndex = cleanWaterConsumedHead.addChild(""+cleanWaterConsumed);
			LogNode dirtyWaterProducedHead = myLogHead.addChild("dirty_water_produced");
			myLogIndex.dirtyWaterProducedIndex = dirtyWaterProducedHead.addChild(""+dirtyWaterProduced);
			LogNode greyWaterProducedHead = myLogHead.addChild("grey_water_produced");
			myLogIndex.greyWaterProducedIndex = greyWaterProducedHead.addChild(""+greyWaterProduced);
			LogNode O2NeededHead = myLogHead.addChild("O2_needed");
			myLogIndex.O2NeededIndex = O2NeededHead.addChild(""+O2Needed);
			LogNode cleanWaterNeededHead = myLogHead.addChild("potable_water_needed");
			myLogIndex.cleanWaterNeededIndex = cleanWaterNeededHead.addChild(""+cleanWaterNeeded);
			LogNode foodNeededHead = myLogHead.addChild("food_needed");
			myLogIndex.foodNeededIndex = foodNeededHead.addChild(""+foodNeeded);
			LogNode airRetrievedHead = myLogHead.addChild("air_retrieved");
			LogNode O2RetrievedHead = airRetrievedHead.addChild("O2_retrieved");
			myLogIndex.O2RetrievedIndex = O2RetrievedHead.addChild(""+airRetrieved.O2);
			LogNode CO2RetrievedHead = airRetrievedHead.addChild("CO2_retrieved");
			myLogIndex.CO2RetrievedIndex = CO2RetrievedHead.addChild(""+airRetrieved.CO2);
			LogNode otherRetrievedHead = airRetrievedHead.addChild("other_retrieved");
			myLogIndex.otherRetrievedIndex = otherRetrievedHead.addChild(""+airRetrieved.other);
			logInitialized = true;
		}
		else{
			myLogIndex.nameIndex.setValue(""+myName);
			myLogIndex.currentActivityIndex.setValue(""+myCurrentActivity.getName());
			myLogIndex.currentActivityOrderIndex.setValue(""+currentOrder);
			myLogIndex.timeActivityPerformedIndex.setValue(""+timeActivityPerformed);
			myLogIndex.starvingTimeIndex.setValue(""+starvingTime);
			myLogIndex.thirstTimeIndex.setValue(""+thirstTime);
			myLogIndex.suffocateTimeIndex.setValue(""+suffocateTime);
			myLogIndex.poisonTimeIndex.setValue(""+poisonTime);
			myLogIndex.personStarvingIndex.setValue(""+personStarving);
			myLogIndex.personThirstyIndex.setValue(""+personThirsty);
			myLogIndex.personSuffocatingIndex.setValue(""+personSuffocating);
			myLogIndex.personPoisonedIndex.setValue(""+personPoisoned);
			myLogIndex.hasDiedIndex.setValue(""+hasDied);
			myLogIndex.ageIndex.setValue(""+age);
			myLogIndex.weightIndex.setValue(""+weight);
			if (sex == Sex.male)
				myLogIndex.sexIndex.setValue("male");
			else if (sex == Sex.female)
				myLogIndex.sexIndex.setValue("female");
			myLogIndex.O2ConsumedIndex.setValue(""+O2Consumed);
			myLogIndex.CO2ProducedIndex.setValue(""+CO2Produced);
			myLogIndex.foodConsumedIndex.setValue(""+foodConsumed);
			myLogIndex.cleanWaterConsumedIndex.setValue(""+cleanWaterConsumed);
			myLogIndex.dirtyWaterProducedIndex.setValue(""+dirtyWaterProduced);
			myLogIndex.greyWaterProducedIndex.setValue(""+greyWaterProduced);
			myLogIndex.O2NeededIndex.setValue(""+O2Needed);
			myLogIndex.cleanWaterNeededIndex.setValue(""+cleanWaterNeeded);
			myLogIndex.foodNeededIndex.setValue(""+foodNeeded);
			myLogIndex.O2RetrievedIndex.setValue(""+airRetrieved.O2);
			myLogIndex.CO2RetrievedIndex.setValue(""+airRetrieved.CO2);
			myLogIndex.otherRetrievedIndex.setValue(""+airRetrieved.other);
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
		public LogNode starvingTimeIndex;
		public LogNode thirstTimeIndex;
		public LogNode suffocateTimeIndex;
		public LogNode poisonTimeIndex;
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
		public LogNode foodConsumedIndex;
		public LogNode cleanWaterConsumedIndex;
		public LogNode dirtyWaterProducedIndex;
		public LogNode greyWaterProducedIndex;
		public LogNode O2NeededIndex;
		public LogNode cleanWaterNeededIndex;
		public LogNode foodNeededIndex;
		public LogNode O2RetrievedIndex;
		public LogNode CO2RetrievedIndex;
		public LogNode otherRetrievedIndex;
	}
}
