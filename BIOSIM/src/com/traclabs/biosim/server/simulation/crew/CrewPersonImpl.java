package biosim.server.crew;

import biosim.idl.crew.*;
import biosim.idl.environment.*;
import biosim.idl.water.*;
import biosim.idl.food.*;
import biosim.idl.air.*;
import biosim.server.util.*;

public class CrewPersonImpl extends CrewPersonPOA {
	private String myName = "No Name";
	private Activity myCurrentActivity;
	private CrewGroupImpl myCrewGroup;
	private boolean hasCollectedReferences = false;
	private int currentOrder = 0;
	private int timeActivityPerformed = 0;
	private int starvingTime = 0;
	private int thirstTime = 0;
	private int suffocateTime = 0;
	private int poisonTime = 0;
	private boolean personStarving = false;
	private boolean personThirsty = false;
	private boolean personSuffocating = false;
	private boolean personPoisoned = false;
	private boolean hasDied = false;
	private SimEnvironment myCurrentEnvironment;
	private FoodStore myFoodStore;
	private PotableWaterStore myPotableWaterStore;
	private DirtyWaterStore myDirtyWaterStore;
	private GreyWaterStore myGreyWaterStore;
	private float age = 30f;
	private float weight = 170f;
	private Sex sex = Sex.male;
	private float O2Consumed= 0f;
	private float CO2Produced = 0f;
	private float foodConsumed = 0f;
	private float cleanWaterConsumed = 0f;
	private float dirtyWaterProduced = 0f;
	private float greyWaterProduced = 0f;
	private float O2Needed = 0f;
	private float cleanWaterNeeded = 0f;
	private float foodNeeded = 0f;
	private Breath airRetrieved;

	public CrewPersonImpl(String pName, float pAge, float pWeight, Sex pSex, CrewGroupImpl pCrewGroup){
		myCrewGroup = pCrewGroup;
		myName = pName;
		age = pAge;
		weight = pWeight;
		sex = pSex;
		myCurrentActivity = myCrewGroup.getScheduledActivityByOrder(currentOrder);
	}

	public String getName(){
		return myName;
	}

	public float getAge(){
		return age;
	}

	public float getGreyWaterProduced(){
		return greyWaterProduced;
	}

	public float getDirtyWaterProduced(){
		return dirtyWaterProduced;
	}

	public float getPotableWaterConsumed(){
		return cleanWaterConsumed;
	}

	public float getFoodConsumed(){
		return foodConsumed;
	}

	public float getCO2Produced(){
		return CO2Produced;
	}

	public float getO2Consumed(){
		return O2Consumed;
	}

	public boolean isStarving(){
		return personStarving;
	}

	public boolean isPoisoned(){
		return personPoisoned;
	}

	public boolean isThirsty(){
		return personThirsty;
	}

	public boolean isSuffocating(){
		return personSuffocating;
	}

	public float getWeight(){
		return weight;
	}

	public boolean isDead(){
		return hasDied;
	}

	public Sex getSex(){
		return sex;
	}

	public Activity getCurrentActivity(){
		return myCurrentActivity;
	}

	public void setCurrentActivity(Activity pActivity){
		myCurrentActivity = pActivity;
		currentOrder = myCurrentActivity.getOrder();
	}

	public int getTimeActivityPerformed(){
		return timeActivityPerformed;
	}

	public String toString(){
		return (myName + " now performing activity " +myCurrentActivity.getName() +
		        " for " + timeActivityPerformed + " of "+myCurrentActivity .getTimeLength() +" hours");
	}

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

	private void advanceCurrentOrder(){
		currentOrder++;
		if (currentOrder >= (myCrewGroup.getNumberOfActivities()))
			currentOrder = 1;
	}

	public void processTick(){
		collectReferences();
		//done with this timestep, advance 1 timestep
		timeActivityPerformed++;
		if (!hasDied){
			if (timeActivityPerformed >= myCurrentActivity.getTimeLength()){
				advanceCurrentOrder();
				myCurrentActivity = myCrewGroup.getScheduledActivityByOrder(currentOrder);
				timeActivityPerformed = 0;
			}
		}
		consumeResources();
		afflictCrew();
		if (deathCheck()){
			myCurrentActivity = myCrewGroup.getScheduledActivityByName("dead");
			hasDied = true;
			timeActivityPerformed = 0;
		}
	}

	private float calculateO2Needed(int currentActivityIntensity){
		if (currentActivityIntensity < 0)
			return 0f;
		double heartRate = (currentActivityIntensity * 30f) + 15f;
		double a = 0.223804f;
		double b = 5.64f * (Math.pow(10f, -7f));
		Double result = new Double((a+b * Math.pow(heartRate, 3f)) * 60f);
		return result.floatValue(); //Liters/hour
	}

	private float calculateCO2Produced(float O2Consumed){
		Double result = new Double(O2Consumed * 0.86);
		return result.floatValue();
	}

	private float calculateO2Produced(float O2Consumed){
		Double result = new Double(O2Consumed * 0.14);
		return result.floatValue();
	}

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

	private float calculateDirtyWaterProduced(float cleanWaterConsumed){
		Double result = new Double(cleanWaterConsumed * 0.3625);
		return result.floatValue();
	}

	private float calculateGreyWaterProduced(float cleanWaterConsumed){
		Double result = new Double(cleanWaterConsumed * 0.6375);
		return result.floatValue();
	}

	private float calculateCleanWaterNeeded(int currentActivityIntensity){
		if (currentActivityIntensity > 0)
			return 0.1667f;
		else
			return 0f;
	}

	private float getCO2Ratio(Breath aBreath){
		Double ratio = new Double(aBreath.CO2 / (aBreath.O2 + aBreath.CO2 + aBreath.other));
		return ratio.floatValue();
	}

	private void afflictCrew(){
		//the dead need not be afflicted
		if (hasDied)
			return;
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

	private boolean deathCheck(){
		//no need to die again!
		if (hasDied)
			return false;
		//check for death
		if (starvingTime > 504){
			return true;
		}
		else if (thirstTime > 72){
			return true;
		}
		else if (suffocateTime > 1){
			return true;
		}
		else if (poisonTime > 5){
			return true;
		}
		else{
			return false;
		}
	}

	private void consumeResources(){
		if (hasDied){
			O2Consumed= 0f;
			CO2Produced = 0f;
			foodConsumed = 0f;
			cleanWaterConsumed = 0f;
			dirtyWaterProduced = 0f;
			greyWaterProduced = 0f;
			return;
		}
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
