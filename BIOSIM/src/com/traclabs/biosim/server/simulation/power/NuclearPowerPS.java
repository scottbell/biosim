package biosim.server.power;

import biosim.idl.power.*;
/**
 * Nuclear Power Production System
 * @author    Scott Bell
 */

public class NuclearPowerPS extends PowerPSImpl {
	public NuclearPowerPS(int pID){
		super(pID);
	}
	float calculatePowerProduced(){
		//Constant steady stream of power
		return randomFilter(500f);
	}
	
}
