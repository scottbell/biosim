package biosim.server.simulation.power;

import biosim.idl.simulation.power.*;
/**
 * Solar Power Production System
 * @author    Scott Bell
 */

public class SolarPowerPS extends PowerPSImpl {
	
	public SolarPowerPS(int pID){
		super(pID);
	}
	
	float calculatePowerProduced(){
		//Varying stream of power
		if (getLightInput() != null)
			return randomFilter(new Double(getLightInput().getLightIntensity() *.09).floatValue());
		else
			return randomFilter(0);
	}
	
}
