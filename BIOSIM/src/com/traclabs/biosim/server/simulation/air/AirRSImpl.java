package biosim.server.air;

import biosim.idl.air.*;
import biosim.server.framework.*;

public class AirRSImpl extends BioModuleImpl implements AirRSOperations {
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
	
	public void reset(){
		currentO2Produced = 0.0f;
		currentCO2Consumed = 0.0f;
		currentPowerConsumed = 0.0f;
	}
	
	public String getModuleName(){
		return "AirRS";
	}
}
