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
		//Constant steady stream of power
		currentPowerProduced = new Double(mySimEnvironment.getLightIntensity() *.05).floatValue();
	}
	
}
