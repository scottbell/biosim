package biosim.server.power;

import biosim.idl.power.*;

public class PowerStoreImpl extends PowerStorePOA {
	private float powerLevel;
	private float powerCapacity;

	public PowerStoreImpl(){
		powerLevel = 0.0f;
		powerCapacity = 10.0f;
	}

	public PowerStoreImpl (float initialPowerLevel, float  initialPowerCapacity){
		powerLevel = initialPowerLevel;
		powerCapacity = initialPowerCapacity;
	}

	public void setPowerCapacity(float watts){
		powerCapacity = watts;
	}

	public void setPowerLevel(float watts){
		powerLevel = watts;
	}

	public float addPower(float wattsRequested){
		float acutallyAdded = 0f;
		if ((wattsRequested + powerLevel) > powerCapacity){
			//adding more power than capacity
			acutallyAdded = (powerCapacity - powerLevel);
			powerLevel += acutallyAdded;
			return  acutallyAdded;
		}
		else{
			acutallyAdded = wattsRequested;
			powerLevel += wattsRequested;
			return acutallyAdded;
		}
	}

	public float takePower(float wattsRequested){
		//idiot check
		if (wattsRequested < 0){
			return 0f;
		}
		//asking for more power than exists
		if (wattsRequested > powerLevel){
			float takenPower = powerLevel;
			powerLevel = 0;
			return takenPower;
		}
		//power exists for request
		else{
			float takenPower = wattsRequested;
			powerLevel -= wattsRequested; 
			return takenPower;
		}
	}
	
	public float getPowerLevel(){
		return powerLevel;
	}

	public void tick(){
	}
	public String getModuleName(){
		return "PowerStore";
	}
}
