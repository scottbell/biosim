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
	
	protected float calculatePowerProduced(){
		//Varying stream of power
		return randomFilter(new Double(mySimEnvironment.getLightIntensity() *.09).floatValue());
	}
	
}
