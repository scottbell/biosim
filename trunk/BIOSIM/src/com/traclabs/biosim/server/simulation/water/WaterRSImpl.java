package biosim.server.water;

// The package containing our stubs.
import SIMULATION.*;

public class WaterRSImpl extends WaterRSPOA {
	public void tick(){
		System.out.println("WaterRS has been ticked!");
	}
	
	public String getModuleName(){
		return "WaterRS";
	}
}
