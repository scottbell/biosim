package biosim.server.food;

// The package containing our stubs.
import ALSS.*;

public class FoodProcessorImpl extends FoodProcessorPOA {
	public void tick(){
		System.out.println("FoodProcessor has been ticked!");
	}
	public String getModuleName(){
		return "FoodProcessor";
	}
}
