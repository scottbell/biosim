package biosim.server.simulation.water;

import biosim.server.util.*;
import biosim.idl.simulation.power.*;
/**
 * The AES is the third stage of water purification.  It takes water from the RO, filters it some, and
 * sends the water to the PPS
 *
 * @author    Scott Bell
 */

public class AES extends WaterRSSubSystem{
	private float currentPPSWaterProduced = 0f;

	/**
	* Constructor that creates the AES
	* @param pWaterRSImpl The Water RS system the AES is contained in
	*/
	public AES(WaterRSImpl pWaterRSImpl){
		super(pWaterRSImpl);
	}
	
	public float getPPSWaterProduced(){
		return currentPPSWaterProduced;
	}

	/**
	* Flushes the water from this subsystem to the PPS
	*/
	private void pushWater(){
		currentPPSWaterProduced = waterLevel;
		myWaterRS.getPPS().addWater(currentPPSWaterProduced);
		waterLevel = 0;
	}

	/**
	* In one tick, this subsystem:
	* 1) Collects references (if needed).
	* 2) Flushes the water from this subsystem to the PPS.
	*/
	public void tick(){
		super.tick();
		if (enabled)
			if (hasEnoughPower)
				pushWater();
			else
				currentPPSWaterProduced = 0f;
		else
				currentPPSWaterProduced = 0f;
	}

	public void reset(){
		super.reset();
		currentPPSWaterProduced = 0f;
	}
}
