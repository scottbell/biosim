package biosim.server.food;

// The package containing our stubs.
import ALSS.*;

public class BiomassRSImpl extends BiomassRSPOA {
	public void tick(){
		System.out.println("BiomassRS has been ticked!");
	}
	public String getModuleName(){
		return "BiomassRS";
	}
}
