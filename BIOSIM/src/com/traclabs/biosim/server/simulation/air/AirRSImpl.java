package biosim.server.air;

import biosim.idl.air.*;

public class AirRSImpl extends AirRSPOA {
	private float currentO2Produced = 0f;
	private float currentCO2Consumed = 0f;
	private float currentPowerConsumed = 0f;

	public  float getPowerConsumed(){
		return currentPowerConsumed;
	}

	public  float getCO2Consumed(){
		return currentCO2Consumed;
	}

	public  float getO2Produced(){
		return currentO2Produced;
	}

	public void tick(){
	}
	
	public String getModuleName(){
		return "AirRS";
	}
}
