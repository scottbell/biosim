package biosim.server.power;

import biosim.idl.power.*;
/**
 * Solar Power Production System
 * @author    Scott Bell
 */

public class SolarPowerPS extends PowerPSImpl {
	
	/**
	* When ticked, the Food Processor does the following:
	* 1) attempts to collect references to various server (if not already done).
	* 2) creates power and places it into the power store.
	*/
	public void tick(){
		collectReferences();
		myPowerStore.add(currentPowerProduced);
		if (moduleLogging)
			log();
	}
	
	/**
	* Reset does nothing right now
	*/
	public void reset(){
	}
}
