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
	private SimEnvironment myCurrentEnvironment;
	private FoodStore myFoodStore;
	private PotableWaterStore myPotableWaterStore;
	private DirtyWaterStore myDirtyWaterStore;
	private GreyWaterStore myGreyWaterStore;
	private float age = 30f;
	private float weight = 170f;
	private Sex sex = Sex.male;
	private String status = "nominal";

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

	public float getWeight(){
		return weight;
	}

	public Sex getSex(){
		return sex;
	}
	
	public String getStatus(){
		return status;
	}

	public Activity getCurrentActivity(){
		return myCurrentActivity;
	}

	public void setCurrentActivity(Activity pActivity){
		myCurrentActivity = pActivity;
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
		if (!(myCurrentActivity.getName().equals("dead"))){
			if (timeActivityPerformed >= myCurrentActivity.getTimeLength()){
				advanceCurrentOrder();
				myCurrentActivity = myCrewGroup.getScheduledActivityByOrder(currentOrder);
				timeActivityPerformed = 0;
			}
			consumeResources();
			deathCheck();
		}
	}

	private void deathCheck(){
		if (starvingTime > 504){
			myCurrentActivity = myCrewGroup.getScheduledActivityByName("dead");
			timeActivityPerformed = 0;
			status = "starved to death";
		}
		if (thirstTime > 72){
			myCurrentActivity = myCrewGroup.getScheduledActivityByName("dead");
			timeActivityPerformed = 0;
			status = "died of thirst";
		}
		if (suffocateTime > 1){
			myCurrentActivity = myCrewGroup.getScheduledActivityByName("dead");
			timeActivityPerformed = 0;
			status = "suffocated";
		}
		if (poisonTime > 5){
			myCurrentActivity = myCrewGroup.getScheduledActivityByName("dead");
			timeActivityPerformed = 0;
			status = "died of CO2 poisioning";
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

	private void consumeResources(){
		int currentActivityIntensity = myCurrentActivity.getActivityIntensity();
		float O2Needed = calculateO2Needed(currentActivityIntensity);
		float CO2Out = calculateCO2Produced(O2Needed);
		float cleanWaterNeeded = calculateCleanWaterNeeded(currentActivityIntensity);
		float dirtyWaterOut = calculateDirtyWaterProduced(cleanWaterNeeded);
		float greyWaterOut = calculateGreyWaterProduced(dirtyWaterOut);
		float foodNeeded = calculateFoodNeeded(currentActivityIntensity);

		//adjust tanks
		float foodRetrieved = myFoodStore.takeFood(foodNeeded);
		float waterRetrieved = myPotableWaterStore.takeWater(cleanWaterNeeded);
		Breath airRetrieved = myCurrentEnvironment.takeO2Breath(O2Needed);
		float O2Retrieved = airRetrieved.O2;
		myDirtyWaterStore.addWater(dirtyWaterOut);
		myGreyWaterStore.addWater(greyWaterOut);
		myCurrentEnvironment.addCO2(CO2Out);
		myCurrentEnvironment.addOther(airRetrieved.other);
		float theCO2Ratio = getCO2Ratio(airRetrieved);
		//afflict crew

		if (foodRetrieved < foodNeeded){
			status = "needed "+foodNeeded+" kgs of food, got only "+foodRetrieved +" kgs";
			starvingTime++;
		}
		if (waterRetrieved < cleanWaterNeeded){
			status = "needed "+cleanWaterNeeded+" liters of waters, got only "+waterRetrieved +" liters";
			thirstTime++;
		}
		if (O2Retrieved < O2Needed){
			status = "needed "+O2Needed+" liters of O2, got only "+O2Retrieved +" liters";
			suffocateTime++;
		}
		if (theCO2Ratio > 0.06){
			status = "needed a ratio of "+0.06+" liters of CO2, got "+theCO2Ratio;
			poisonTime++;
		}
	}
}
