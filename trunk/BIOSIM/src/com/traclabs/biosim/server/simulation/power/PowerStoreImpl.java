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
	
	void setPowerCapacity(float watts){
		powerCapacity = watts;
	}

	void setPowerLevel(float watts){
		powerLevel = watts;
	}

	float addPower(float watts){
		if ((watts +powerLevel) > powerCapacity){
			float returnValue = (powerCapacity - powerLevel);
			powerLevel = powerCapacity;
			return returnValue;
		}
		else{
			powerLevel = powerLevel + watts;
			return watts;
		}
	}

	float takePower(float watts){
		if ((powerLevel - watts) < 0){
			powerLevel = 0;
			if (watts < 0)
				return 0;
			else
				return powerLevel;
		}
		else{
			powerLevel = powerLevel - watts;
			return watts;
		}
	}
	float getPowerLevel(){
		return powerLevel;
	}

	public void tick(){
		System.out.println("Power Store has been ticked!");
	}
	public String getModuleName(){
		return "PowerStore";
	}
}
