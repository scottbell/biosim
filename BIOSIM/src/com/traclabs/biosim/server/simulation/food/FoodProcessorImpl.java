package biosim.server.food;

// The package containing our stubs.
import ALSS.*;

public class FoodProcessorImpl extends FoodProcessorPOA {
	private float currentBiomassConsumed = 0f;
	private float currentPowerConsumed = 0f;
	private float currentFoodProduced = 0f;

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
		System.out.println("FoodProcessor has been ticked!");
	}
	public String getModuleName(){
		return "FoodProcessor";
	}
}
