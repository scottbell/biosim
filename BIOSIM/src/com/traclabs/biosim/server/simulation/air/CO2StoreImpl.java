package biosim.server.air;

import biosim.idl.air.*;

public class CO2StoreImpl extends CO2StorePOA {
	private float CO2level;
	private float CO2capacity;
 
	public CO2StoreImpl(){
		CO2level = 0.0f;
		CO2capacity = 10.0f;
	}

	public CO2StoreImpl (float initialCO2level, float  initialCO2capacity){
		CO2level = initialCO2level;
		CO2capacity = initialCO2capacity;
	}

	public void setCO2Capacity(float liters){
		CO2capacity = liters;
	}
	
	public void setCO2Level(float liters){
		CO2level = liters;
	}

	public float addCO2(float liters){
		if ((liters + CO2level) > CO2capacity){
			float returnValue = (CO2capacity - CO2level);
			CO2level = CO2capacity;
			return returnValue;
		}
		else{
			CO2level = CO2level + liters;
			return liters;
		}
	}

	public float takeCO2(float liters){
		if ((CO2level - liters) < 0){
			CO2level = 0;
			if (liters < 0)
				return 0;
			else
				return CO2level;
		}
		else{
			CO2level = CO2level - liters;
			return liters;
		}
	}
	public float getCO2Level(){
		return CO2level;
	}

	public void tick(){
	}
	public String getModuleName(){
		return "CO2Store";
	}
}
