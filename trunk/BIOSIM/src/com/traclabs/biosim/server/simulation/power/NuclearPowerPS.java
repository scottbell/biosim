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
	protected void calculatePowerProduced(){
		//Constant steady stream of power
		currentPowerProduced = randomFilter(500f);
	}
	
}
