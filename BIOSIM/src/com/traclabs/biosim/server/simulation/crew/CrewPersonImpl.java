package biosim.server.crew;

import biosim.idl.crew.*;
import biosim.idl.environment.*;
import biosim.idl.water.*;
import biosim.idl.food.*;
import biosim.idl.air.*;
import biosim.server.util.*;

public class CrewPersonImpl extends CrewPersonPOA {
	private String myName = "No Name";
	private ActivityImpl myCurrentActivity;
	private CrewGroupImpl myCrewGroup;
	private boolean hasCollectedReferences = false;
	private int currentOrder = 0;
	private int timeActivityPerformed = 0;
	private int starvingTime = 0;
	private int thirstTime = 0;
	private int suffocateTime = 0;
	private SimEnvironment myCurrentEnvironment;
	private FoodStore myFoodStore;
	private PotableWaterStore myPotableWaterStore;
	private DirtyWaterStore myDirtyWaterStore;
	private float age = 30f;
	private float weight = 170f;
	private CrewPersonSex sex = CrewPersonSex.male;

	public CrewPersonImpl(String pName, float pAge, float pWeight, CrewPersonSex pSex, CrewGroupImpl pCrewGroup){
		myCrewGroup = pCrewGroup;
		myName = pName;
		age = pAge;
		weight = pWeight;
		sex = pSex;
		myCurrentActivity = myCrewGroup.getRawActivityByOrder(currentOrder);
		System.out.println("CrewPerson: "+myName+" is "+myCurrentActivity.getName());
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
	
	public CrewPersonSex getSex(){
		return sex;
	}
	
	public org.omg.CORBA.Object getCurrentActivity(){
		return (OrbUtils.poaToCorbaObj(myCurrentActivity));
	}

	public void setCurrentActivity(org.omg.CORBA.Object pActivity){
		myCurrentActivity = (ActivityImpl)(OrbUtils.corbaObjToPoa(pActivity));
	}

	public String toString(){
		return (myName + " now performing activity " +myCurrentActivity.getName() +
		        " for " + timeActivityPerformed + " of "+myCurrentActivity .getTimeLength() +"hours");
	}

	private void collectReferences(){
		try{
			if (!hasCollectedReferences){
				myCurrentEnvironment = SimEnvironmentHelper.narrow(OrbUtils.getNCRef().resolve_str("SimEnvironment"));
				myFoodStore = FoodStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("FoodStore"));
				myPotableWaterStore = PotableWaterStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("PotableWaterStore"));
				myDirtyWaterStore = DirtyWaterStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("DirtyWaterStore"));
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
				myCurrentActivity = myCrewGroup.getRawActivityByOrder(currentOrder);
				timeActivityPerformed = 0;
			}
			consumeResources();
			deathCheck();
		}
		System.out.println("CrewPerson: "+toString());
	}
	
	private void deathCheck(){
		if (starvingTime > 504){
			myCurrentActivity = myCrewGroup.getRawActivityByName("dead");
			timeActivityPerformed = 0;
			System.out.println("CrewPerson: "+myName+" has starved to death!");
		}
		if (thirstTime > 72){
			myCurrentActivity = myCrewGroup.getRawActivityByName("dead");
			timeActivityPerformed = 0;
			System.out.println("CrewPerson: "+myName+" has died of thirst!");
		}
		if (suffocateTime > 1){
			myCurrentActivity = myCrewGroup.getRawActivityByName("dead");
			timeActivityPerformed = 0;
			System.out.println("CrewPerson: "+myName+" has suffocated!!");
		}
	}
	
	private double calculateO2Needed(int currentActivityIntensity){
		double heartRate = (currentActivityIntensity * 30f) + 15f;
		double a = 0.223804f;
		double b = 5.64f * (Math.pow(10f, -7f));
		double result = (a+b * Math.pow(heartRate, 3f)) * 60f;
		return result; //Liters/hour
	}
	
	private float calculateFoodNeeded(int currentActivityIntensity){
		return 0.0f;
	}
	
	private void consumeResources(){
		int currentActivityIntensity = myCurrentActivity.getActivityIntensity();
		float O2Needed = 0f;
		float CO2Out = 0f;
		float waterNeeded = 0f;
		float waterOut = 0f;
		float foodNeeded = 0f;
		if (currentActivityIntensity == 0){
			//born, human needs nothing
		}
		else if (currentActivityIntensity == 1){
			O2Needed = 1f;
			CO2Out = 1f;
			waterNeeded = 1f;
			waterOut = 1f;
			foodNeeded = 0.5f;
		}
		else if (currentActivityIntensity == 2){
			O2Needed = 2f;
			CO2Out = 2f;
			waterNeeded = 1f;
			waterOut = 1f;
			foodNeeded = 0.5f;
		}
		else if (currentActivityIntensity == 3){
			O2Needed = 2f;
			CO2Out = 2f;
			waterNeeded = 2f;
			waterOut = 2f;
			foodNeeded = 0.5f;
		}
		else if (currentActivityIntensity == 4){
			O2Needed = 3f;
			CO2Out = 3f;
			waterNeeded = 2f;
			waterOut = 2f;
			foodNeeded = 0.5f;
		}
		else if (currentActivityIntensity == 5){
			O2Needed = 3f;
			CO2Out = 3f;
			waterNeeded = 3f;
			waterOut = 3f;
			foodNeeded = 0.5f;
		}
		//adjust tanks
		float foodRetrieved = myFoodStore.takeFood(foodNeeded);
		float waterRetrieved = myPotableWaterStore.takeWater(waterNeeded);
		Breath airRetrieved = myCurrentEnvironment.takeO2(O2Needed);
		float O2Retrieved = airRetrieved.O2;
		myDirtyWaterStore.addWater(waterOut);
		myCurrentEnvironment.addCO2(CO2Out);
		//afflict crew
		if (foodRetrieved < foodNeeded){
			starvingTime++;
		}
		if (waterRetrieved < waterNeeded){
			thirstTime++;
		}
		if (O2Retrieved < O2Needed){
			suffocateTime++;
		}
	}
}
