package biosim.server.crew;

import biosim.idl.crew.*;
import biosim.idl.environment.*;
import biosim.idl.water.*;
import biosim.idl.food.*;
import biosim.idl.air.*;
import biosim.server.util.*;

public class CrewPersonImpl extends CrewPersonPOA {
	private String myName;
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

	public CrewPersonImpl(String pName, CrewGroupImpl pCrewGroup){
		myCrewGroup = pCrewGroup;
		myName = pName;
		myCurrentActivity = myCrewGroup.getRawActivityByOrder(currentOrder);
	}

	public String getName(){
		return myName;
	}

	public org.omg.CORBA.Object getCurrentActivity(){
		return (BioSimUtilsImpl.poaToCorbaObj(myCurrentActivity));
	}

	public void setCurrentActivity(org.omg.CORBA.Object pActivity){
		myCurrentActivity = (ActivityImpl)(BioSimUtilsImpl.corbaObjToPoa(pActivity));
	}

	public String toString(){
		return (myName + " now performing activity " +myCurrentActivity.getName() +
		        " for " + myCurrentActivity .getTimeLength() + " hours");
	}

	private void collectReferences(){
		try{
			if (!hasCollectedReferences){
				myCurrentEnvironment = SimEnvironmentHelper.narrow(BioSimUtilsImpl.getNCRef().resolve_str("SimEnvironment"));
				myFoodStore = FoodStoreHelper.narrow(BioSimUtilsImpl.getNCRef().resolve_str("FoodStore"));
				myPotableWaterStore = PotableWaterStoreHelper.narrow(BioSimUtilsImpl.getNCRef().resolve_str("PotableWaterStore"));
				myDirtyWaterStore = DirtyWaterStoreHelper.narrow(BioSimUtilsImpl.getNCRef().resolve_str("DirtyWaterStore"));
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
		if (!(myCurrentActivity.getName().equals("Dead"))){
			if (timeActivityPerformed >= myCurrentActivity.getTimeLength()){
				advanceCurrentOrder();
				myCurrentActivity = myCrewGroup.getRawActivityByOrder(currentOrder);
				timeActivityPerformed = 0;
			}
		}
		consumeResources();
		deathCheck();
		System.out.println("CrewPerson: "+toString());
	}
	
	private void deathCheck(){
		boolean dead = false;
		if (starvingTime > 504)
			dead = true;
		if (thirstTime > 72)
			dead = true;
		if (suffocateTime > 1)
			dead = true;
		if (dead)
			myCurrentActivity = myCrewGroup.getRawActivityByName("Dead");
	}
	
	private void consumeResources(){
		int currentActivityIntensity = myCurrentActivity.getActivityIntensity();
		float O2In = 0f;
		float CO2Out = 0f;
		float waterIn = 0f;
		float waterOut = 0f;
		float foodIn = 0f;
		if (currentActivityIntensity == 0){
			//born, human needs nothing
		}
		else if (currentActivityIntensity == 1){
			O2In = 1f;
			CO2Out = 1f;
			waterIn = 1f;
			waterOut = 1f;
			foodIn = 0.5f;
		}
		else if (currentActivityIntensity == 2){
			O2In = 2f;
			CO2Out = 2f;
			waterIn = 1f;
			waterOut = 1f;
			foodIn = 0.5f;
		}
		else if (currentActivityIntensity == 3){
			O2In = 2f;
			CO2Out = 2f;
			waterIn = 2f;
			waterOut = 2f;
			foodIn = 0.5f;
		}
		else if (currentActivityIntensity == 4){
			O2In = 3f;
			CO2Out = 3f;
			waterIn = 2f;
			waterOut = 2f;
			foodIn = 0.5f;
		}
		else if (currentActivityIntensity == 5){
			O2In = 3f;
			CO2Out = 3f;
			waterIn = 3f;
			waterOut = 3f;
			foodIn = 0.5f;
		}
		//adjust tanks
		float foodRetrieved = myFoodStore.addFood(foodIn);
		float waterRetrieved = myPotableWaterStore.addWater(waterIn);
		float O2Retrieved = myCurrentEnvironment.addO2(O2In);
		myDirtyWaterStore.addWater(waterOut);
		myCurrentEnvironment.addCO2(CO2Out);
		//afflict crew
		if (foodRetrieved != foodIn){
			starvingTime++;
		}
		if (waterRetrieved != waterIn){
			thirstTime++;
		}
		if (O2Retrieved != O2In){
			suffocateTime++;
		}
	}
}
