package biosim.server.food;

import biosim.idl.food.*;
import biosim.server.framework.*;

public class FoodProcessorImpl extends BioModuleImpl implements FoodProcessorOperations {
	private float currentBiomassConsumed = 0f;
	private float currentPowerConsumed = 0f;
	private float currentFoodProduced = 0f;
	
	public void reset(){
		 currentBiomassConsumed = 0f;
		 currentPowerConsumed = 0f;
		 currentFoodProduced = 0f;
	}

	public float getBiomassConsumed(){
		return currentBiomassConsumed;
	}

	public float getPowerConsumed(){
		return currentPowerConsumed;
	}

	public float getFoodProduced(){
		return currentFoodProduced;
	}

	public void tick(){
	}
	public String getModuleName(){
		return "FoodProcessor";
	}
}
