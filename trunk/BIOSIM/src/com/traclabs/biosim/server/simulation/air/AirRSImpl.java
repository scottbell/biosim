package biosim.server.air;

// The package containing our stubs.
import ALSS.*;

public class AirRSImpl extends AirRSPOA {
	public void tick(){
		System.out.println("AirRS has been ticked!");
	}
	
	public String getModuleName(){
		return "AirRS";
	}
}
