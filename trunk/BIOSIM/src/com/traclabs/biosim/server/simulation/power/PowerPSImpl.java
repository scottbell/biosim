package biosim.server.power;

// The package containing our stubs.
import SIMULATION.*;

public class PowerPSImpl extends PowerPSPOA {
	public void tick(){
		System.out.println("PowerPS has been ticked!");
	}
	
	public String getModuleName(){
		return "PowerPS";
	}
}
