/**
 * The Crew Person Implementation.  Eats/drinks/excercises away resources according to a set schedule.
 *
 * @author    Scott Bell
 */

package biosim.server.crew;

import biosim.idl.crew.*;
import biosim.idl.environment.*;
import biosim.idl.water.*;
import biosim.idl.food.*;
import biosim.idl.air.*;
import biosim.server.util.*;

public class CrewPersonImpl extends CrewPersonPOA {
	//The name of this crew memeber
	private String myName = "No Name";
	//The current activity this crew member is performing
	private Activity myCurrentActivity;
	//The Crew that this crew person belongs to
	private CrewGroupImpl myCrewGroup;
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
	//References to the servers the crew member takes/puts resources (like air, food, etc)
	private SimEnvironment myCurrentEnvironment;
	private FoodStore myFoodStore;
	private PotableWaterStore myPotableWaterStore;
	private DirtyWaterStore myDirtyWaterStore;
	private GreyWaterStore myGreyWaterStore;
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

	/**
	* Constructor that creates a new crew person
	* @param pName the name of the new crew person
	* @param pAge the age of the new crew person
	* @param pSex the sex of the new crew person
	* @param pCrewGroup the crew that the new crew person belongs in
	*/
	protected CrewPersonImpl(String pName, float pAge, float pWeight, Sex pSex, CrewGroupImpl pCrewGroup){
		myCrewGroup = pCrewGroup;
		myName = pName;
		age = pAge;
		weight = pWeight;
		sex = pSex;
		myCurrentActivity = myCrewGroup.getScheduledActivityByOrder(currentOrder);
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
		currentOrder = myCurrentActivity.getOrder();
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
	* Collects references to servers needed for putting/getting resources.
	*/
	private void collectReferences(){
		try{
			if (!hasCollectedReferences){
				myCurrentEnvironment = SimEnvironmentHelper.narrow(OrbUtils.getNCRef().resolve_str("SimEnvironment"));
				myFoodStore = FoodStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("FoodStore"));
				myPotableWaterStore = PotableWaterStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("PotableWaterStore"));
				myDirtyWaterStore = DirtyWaterStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("DirtyWaterStore"));
				myGreyWaterStore = GreyWaterStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("GreyWaterStore"));
			}
		}
		catch (org.omg.CORBA.UserException e){
			System.out.println("Couldn't find SimEnvironment!!");
			e.printStackTrace(System.out);
		}
	}

	/**
	* If the crew memeber has been performing the current activity long enough, the new scheduled activity is assigned.
	*/
	private void advanceActivity(){
		if (timeActivityPerformed >= myCurrentActivity.getTimeLength()){
			currentOrder++;
			if (currentOrder >= (myCrewGroup.getNumberOfActivities()))
				currentOrder = 1;
			myCurrentActivity = myCrewGroup.getScheduledActivityByOrder(currentOrder);
			timeActivityPerformed = 0;
		}
	}
	
	/**
	* When the CrewGroup ticks the crew member, the member
	* 1) increases the time the activity has been performed by 1
	* on the condition that the crew memeber isn't dead he/she then:
	* 2) attempts to collect references to various server (if not already done)
	* 3) possibly advances to the next activity
	* 4) consumes air/food/water, exhales and excretes
	* 5) takes afflictions from lack of any resources
	* 6) checks whether afflictions (if any) are fatal
	*/
	public void processTick(){
		timeActivityPerformed++;
		if (!hasDied){
			collectReferences();
			advanceActivity();
			consumeResources();
			afflictCrew();
			deathCheck();
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
		return result.floatValue(); //Liters/hour
	}
	
	/**
	* Calculate the current CO2 produced by the crew memeber given the O2 consumed for the current tick.
	* Algorithm derived from "Top Level Modeling of Crew Component of ALSS" by Goudrazi and Ting
	* @param O2Consumed the O2 consumed (in liters) during this tick
	* @return CO2 produced in liters
	*/
	private float calculateCO2Produced(float O2Consumed){
		Double result = new Double(O2Consumed * 0.86);
		return result.floatValue();
	}
	
	/**
	* Calculate the current O2 produced by the crew memeber given the O2 consumed for the current tick.
	* Algorithm derived from "Top Level Modeling of Crew Component of ALSS" by Goudrazi and Ting
	* @param O2Consumed the O2 consumed (in liters) during this tick
	* @return O2 produced in liters
	*/
	private float calculateO2Produced(float O2Consumed){
		Double result = new Double(O2Consumed * 0.14);
		return result.floatValue();
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
		return kgFoodNeeded.floatValue();
	}
	
	/**
	* Calculate the dirty water produced given the potable water consumed for the current tick.
	* @param cleanWaterConsumed the potable water consumed (in liters) during this tick
	* @return dirty water produced in liters
	*/
	private float calculateDirtyWaterProduced(float cleanWaterConsumed){
		Double result = new Double(cleanWaterConsumed * 0.3625);
		return result.floatValue();
	}
	
	/**
	* Calculate the grey water produced given the potable water consumed for the current tick.
	* @param cleanWaterConsumed the potable water consumed (in liters) during this tick
	* @return grey water produced in liters
	*/
	private float calculateGreyWaterProduced(float cleanWaterConsumed){
		Double result = new Double(cleanWaterConsumed * 0.6375);
		return result.floatValue();
	}
	
	/**
	* Calculate the clean water needed given the activity intensity for the current tick.
	* @param currentActivityIntensity the activity intensity for the current tick
	* @return potable water needed in liters
	*/
	private float calculateCleanWaterNeeded(int currentActivityIntensity){
		if (currentActivityIntensity > 0)
			return 0.1667f;
		else
			return 0f;
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
			System.out.println(myName + " dead from starvation");
			hasDied = true;
		}
		else if (thirstTime > 72){
			System.out.println(myName + " dead from thirst");
			hasDied = true;
		}
		else if (suffocateTime > 1){
			System.out.println(myName + " dead from suffocation");
			hasDied = true;
		}
		else if (poisonTime > 5){
			System.out.println(myName + " dead from CO2 poisoning");
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
			myCurrentActivity = myCrewGroup.getScheduledActivityByName("dead");
			hasDied = true;
			timeActivityPerformed = 0;
		}
	}
	
	/**
	* Attempts to consume resource for this crew memeber.
	* inhales/drinks/eats, then exhales/excretes
	*/
	private void consumeResources(){
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
}
