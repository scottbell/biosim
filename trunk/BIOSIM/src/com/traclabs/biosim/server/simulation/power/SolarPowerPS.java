package biosim.server.power;

import biosim.idl.power.*;
/**
 * Solar Power Production System
 * @author    Scott Bell
 */

public class SolarPowerPS extends PowerPSImpl {
	
	public SolarPowerPS(int pID){
		super(pID);
	}
	
	protected void calculatePowerProduced(){
		//Varying stream of power
		currentPowerProduced = randomFilter(new Double(mySimEnvironment.getLightIntensity() *.05).floatValue());
	}
	
}
