package biosim.client.framework;

import java.util.*;
import java.io.*;
import biosim.idl.framework.BioDriver;
import biosim.idl.framework.BioDriverHelper;
import biosim.idl.framework.BioModule;
import biosim.idl.sensor.air.*;
import biosim.idl.sensor.crew.*;
import biosim.idl.sensor.environment.*;
import biosim.idl.sensor.food.*;
import biosim.idl.sensor.framework.*;
import biosim.idl.sensor.power.*;
import biosim.idl.sensor.water.*;
import biosim.idl.actuator.air.*;
import biosim.idl.actuator.crew.*;
import biosim.idl.actuator.environment.*;
import biosim.idl.actuator.food.*;
import biosim.idl.actuator.framework.*;
import biosim.idl.actuator.power.*;
import biosim.idl.actuator.water.*;
import biosim.idl.simulation.crew.*;
import biosim.idl.simulation.water.*;
import biosim.idl.simulation.air.*;
import biosim.idl.simulation.food.*;
import biosim.idl.simulation.power.*;
import biosim.idl.simulation.environment.*;
import biosim.idl.simulation.framework.*;
import biosim.client.util.*;


//javac -classpath .:$BIOSIM_HOME/lib/jacorb/jacorb.jar:$BIOSIM_HOME/generated/client/classes StateMap.java


public class StateMap { 
	private Map myMap;
	BioHolder myBioHolder; 
	DirtyWaterStore myDirtyWaterStore;
	PotableWaterStore myPotableWaterStore;
	GreyWaterStore myGreyWaterStore; 
	O2Store myO2Store;
	CO2Store myCO2Store;
	H2Store myH2Store;
	public static float[] capacities; 
			
	public GenericSensor[] stateSources; 
				
	public static String[] stateNames = {"carbondioxide", "dirtywater",  "greywater", "hydrogen", "oxygen", "potablewater"}; 
							
	StateMap() { 
		myMap = new TreeMap(); 
		myBioHolder = BioHolderInitializer.getBioHolder(); 
		System.out.println(BioHolderInitializer.getID()); 

		myDirtyWaterStore = (DirtyWaterStore)myBioHolder.theDirtyWaterStores.get(0);
		myPotableWaterStore = (PotableWaterStore)myBioHolder.thePotableWaterStores.get(0);
		myGreyWaterStore = (GreyWaterStore)myBioHolder.theGreyWaterStores.get(0);

		myO2Store = (O2Store)myBioHolder.theO2Stores.get(0);
		myCO2Store = (CO2Store)myBioHolder.theCO2Stores.get(0);
		myH2Store = (H2Store)myBioHolder.theH2Stores.get(0);
		
		stateSources = new GenericSensor[6]; 
		stateSources[0] =(GenericSensor)myBioHolder.getSensorAttachedTo(myBioHolder.theCO2StoreLevelSensors, myCO2Store);
		stateSources[1] =(GenericSensor)myBioHolder.getSensorAttachedTo(myBioHolder.theDirtyWaterStoreLevelSensors, myDirtyWaterStore);
		stateSources[2] =(GenericSensor)myBioHolder.getSensorAttachedTo(myBioHolder.theGreyWaterStoreLevelSensors, myGreyWaterStore);
		stateSources[3] =(GenericSensor)myBioHolder.getSensorAttachedTo(myBioHolder.theH2StoreLevelSensors, myH2Store);
		stateSources[4] =(GenericSensor)myBioHolder.getSensorAttachedTo(myBioHolder.theO2StoreLevelSensors, myO2Store);
		stateSources[5] =(GenericSensor)myBioHolder.getSensorAttachedTo(myBioHolder.thePotableWaterStoreLevelSensors, myPotableWaterStore);
		
		capacities = new float[6]; 
		capacities[0] =	myCO2Store.getCapacity(); 
		capacities[1] =	myDirtyWaterStore.getCapacity();
		capacities[2] =	myDirtyWaterStore.getCapacity();
		capacities[3] =	myH2Store.getCapacity();
		capacities[4] =	myO2Store.getCapacity();
		capacities[5] =	myPotableWaterStore.getCapacity();
	} 
		
	
	public void updateState() {
		// gathers continuous valued state variables
		int i;
		GenericSensor currentSensor;

		for (i=0;i<stateSources.length;i++) {
			currentSensor = (GenericSensor)(stateSources[i]);
			myMap.put(stateNames[i], new Float(currentSensor.getValue()));
		}

	}
	
	
	public void setStatefromVector(float[] outputvector) { 
		int i; 
		for (i=0;i<stateNames.length;i++) 
			myMap.put(stateNames[i], new Float(capacities[i]*outputvector[i]));  
	}
	
	public Map getState() { return myMap;} 
	
	public GenericSensor[] getStateSources() {return stateSources;} 
	
	public float getStateValue(String name) { return ((Float)myMap.get(name)).floatValue();} 
	
	public int size() {return stateSources.length;} 
	
	public void printMe() { System.out.println(myMap);}
	
	public Map getMap() {return myMap;} 
	
}


