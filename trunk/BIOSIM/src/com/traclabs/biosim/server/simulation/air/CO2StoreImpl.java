package biosim.server.air;

import biosim.idl.air.*;

public class CO2StoreImpl extends CO2StorePOA {
	private float CO2Level;
	private float CO2Capacity;
 
	public CO2StoreImpl(){
		CO2Level = 0.0f;
		CO2Capacity = 10.0f;
	}

	public CO2StoreImpl (float initialCO2Level, float  initialCO2Capacity){
		CO2Level = initialCO2Level;
		CO2Capacity = initialCO2Capacity;
	}

	public void setCO2Capacity(float liters){
		CO2Capacity = liters;
	}
	
	public void setCO2Level(float liters){
		CO2Level = liters;
	}

	public float addCO2(float litersRequested){
		float acutallyAdded = 0f;
		if ((litersRequested + CO2Level) > CO2Capacity){
			//adding more CO2 than capacity
			acutallyAdded = (CO2Capacity - CO2Level);
			CO2Level += acutallyAdded;
			return  acutallyAdded;
		}
		else{
			acutallyAdded = litersRequested;
			CO2Level += litersRequested;
			return acutallyAdded;
		}
	}

	public float takeCO2(float litersRequested){
		//idiot check
		if (litersRequested < 0){
			return 0f;
		}
		//asking for more CO2 than exists
		if (litersRequested > CO2Level){
			float takenCO2 = CO2Level;
			CO2Level = 0;
			return takenCO2;
		}
		//CO2 exists for request
		else{
			float takenCO2 = litersRequested;
			CO2Level -= litersRequested; 
			return takenCO2;
		}
	}
	
	public float getCO2Level(){
		return CO2Level;
	}

	public void tick(){
	}
	public String getModuleName(){
		return "CO2Store";
	}
}
