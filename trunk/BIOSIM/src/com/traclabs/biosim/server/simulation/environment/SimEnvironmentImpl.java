package biosim.server.environment;

import biosim.idl.environment.*;
import biosim.idl.air.*; 

public class SimEnvironmentImpl extends SimEnvironmentPOA {
	private float O2Level;
	private float CO2Level;
	private float otherLevel;
	private float capacity;
	
	private int ticks	= 0;
	
	public SimEnvironmentImpl(){
		capacity = 1000f;
		O2Level = (new Double(capacity * 0.21)).floatValue();
		otherLevel = (new Double(capacity * 0.21)).floatValue();
		CO2Level = (new Double(capacity * 0.786)).floatValue();
	}

	public SimEnvironmentImpl(float initialCapacity){
		capacity = initialCapacity;
		O2Level = (new Double(capacity * 0.21)).floatValue();
		otherLevel = (new Double(capacity * 0.21)).floatValue();
		CO2Level = (new Double(capacity * 0.786)).floatValue();
	}
	
	public SimEnvironmentImpl (float initialCO2Level, float initialO2Level, float initialOtherLevel, float initialCapacity){
		CO2Level = initialCO2Level;
		O2Level = initialO2Level;
		otherLevel = initialOtherLevel;
		capacity = initialCapacity;
	}
	
	public void resetLevels(){
		O2Level = (new Double(capacity * 0.21)).floatValue();
		otherLevel = (new Double(capacity * 0.21)).floatValue();
		CO2Level = (new Double(capacity * 0.786)).floatValue();
	}
	
	public int getTicks(){
		return ticks;
	}
	
	public void setCO2Level(float litersRequested){
		CO2Level = litersRequested;
	}
	
	public void setO2Level(float litersRequested){
		O2Level = litersRequested;
	}
	
	public void setOtherLevel(float litersRequested){
		otherLevel = litersRequested;
	}
	
	public void setCapacity(float litersRequested){
		capacity = litersRequested;
	}
	
	public float getTotalLevel(){
		return CO2Level + O2Level + otherLevel;
	}
	
	public void setTotalLevel(float litersRequested){
		CO2Level = litersRequested;
		O2Level = litersRequested;
		otherLevel = litersRequested;
	}
	
	public float getOtherLevel(){
		return otherLevel;
	}

	public float getO2Level(){
		return O2Level;
	}

	public float getCO2Level(){
		return CO2Level;
	}

	public float addCO2(float litersRequested){
		float acutallyAdded = 0f;
		if ((litersRequested + getTotalLevel()) > capacity){
			//adding more CO2 than capacity
			acutallyAdded = (capacity - getTotalLevel());
			CO2Level += acutallyAdded;
			return  acutallyAdded;
		}
		else{
			acutallyAdded = litersRequested;
			CO2Level = CO2Level + litersRequested;
			return acutallyAdded;
		}
	}
	
	public float addO2(float litersRequested){
		float acutallyAdded = 0f;
		if ((litersRequested + getTotalLevel()) > capacity){
			//adding more O2 than capacity
			acutallyAdded = (capacity - getTotalLevel());
			O2Level += acutallyAdded;
			return  acutallyAdded;
		}
		else{
			acutallyAdded = litersRequested;
			O2Level = O2Level + litersRequested;
			return acutallyAdded;
		}
	}
	
	public float addOther(float litersRequested){
		float acutallyAdded = 0f;
		if ((litersRequested + getTotalLevel()) > capacity){
			//adding more Other than capacity
			acutallyAdded = (capacity - getTotalLevel());
			otherLevel += acutallyAdded;
			return  acutallyAdded;
		}
		else{
			acutallyAdded = litersRequested;
			otherLevel = otherLevel + litersRequested;
			return acutallyAdded;
		}
	}
	
	public Breath takeBreath(float litersRequested){
		//idiot check
		if (litersRequested < 0){
			return new Breath(0,0,0);
		}
		//asking for more gas than exists
		if (litersRequested > getTotalLevel()){
			float litersActuallyReceived = getTotalLevel();
			float takenCO2 = (CO2Level * litersActuallyReceived) / getTotalLevel();
			float takenO2 = (O2Level * litersActuallyReceived) / getTotalLevel();
			float takenOther = (otherLevel * litersActuallyReceived) / getTotalLevel();
			setTotalLevel(0);
			return new Breath(takenO2, takenCO2, takenOther);
		}
		//gas exists for request
		else{
			float takenCO2 = (CO2Level * litersRequested) / getTotalLevel();
			float takenO2 = (O2Level * litersRequested) / getTotalLevel();
			float takenOther = (otherLevel * litersRequested) / getTotalLevel();
			return new Breath(takenO2, takenCO2, takenOther);
		}
	}
	
	public void tick(){
		ticks++;
		System.out.println(getModuleName() + ": advanced to timestep @ "+ticks);
	}

	public String getModuleName(){
		return "SimEnvironment";
	}
}
