package biosim.server.power;

// The package containing our stubs.
import ALSS.*;

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
			powerLevel = powerCapacity;
			if (watts >=  powerCapacity)
				return 0;
			else
				return (powerCapacity - powerLevel);
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
