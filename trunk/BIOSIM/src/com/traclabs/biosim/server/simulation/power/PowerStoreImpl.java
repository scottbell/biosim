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

	public float addPower(float watts){
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

	public float takePower(float watts){
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
	public float getPowerLevel(){
		return powerLevel;
	}

	public void tick(){
	}
	public String getModuleName(){
		return "PowerStore";
	}
}
