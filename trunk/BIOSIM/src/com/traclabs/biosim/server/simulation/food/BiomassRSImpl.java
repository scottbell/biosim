package biosim.server.food;

import biosim.idl.food.*;

public class BiomassRSImpl extends BiomassRSPOA {
	private float currentPowerConsumed = 0f;
	private float currentGreyWaterConsumed = 0f;
	private float currentBiomassProduced = 0f;
	private float currentCO2Consumed= 0f;

	public float getPowerConsumed(){
		return currentPowerConsumed;
	}
	
	public float getCO2Consumed(){
		return currentCO2Consumed;
	}

	public float getGreyWaterConsumed(){
		return currentGreyWaterConsumed;
	}

	public float getBiomassProduced(){
		return currentBiomassProduced;
	}

	public void tick(){
	}
	public String getModuleName(){
		return "BiomassRS";
	}
}
