package biosim.server.power;

import biosim.idl.power.*;
/**
 * Nuclear Power Production System
 * @author    Scott Bell
 */

public class NuclearPowerPS extends PowerPSImpl {
	
	protected void calculatePowerProduced(){
		//Constant steady stream of power
		currentPowerProduced = 500f;
	}
	
}
