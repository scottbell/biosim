package biosim.server.water;

import biosim.server.util.*;
import biosim.idl.power.*;
/**
 * The AES is the third stage of water purification.  It takes water from the RO, filters it some, and
 * sends the water to the PPS
 *
 * @author    Scott Bell
 */

public class AES extends WaterRSSubSystem{
	//The subsystem to send the water to next
	private PPS myPPS;
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
		myPPS.addWater(currentPPSWaterProduced);
		waterLevel = 0;
	}

	/**
	* Collects references to subsystems needed for putting/getting resources
	*/
	private void collectReferences(){
		if (!hasCollectedReferences){
			try{
				myPPS = myWaterRS.getPPS();
				hasCollectedReferences = true;
				myPowerStore = PowerStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("PowerStore"));
			}
			catch (org.omg.CORBA.UserException e){
				e.printStackTrace();
			}
		}
	}

	/**
	* In one tick, this subsystem:
	* 1) Collects references (if needed).
	* 2) Flushes the water from this subsystem to the PPS.
	*/
	public void tick(){
		if (enabled){
			collectReferences();
			gatherPower();
			if (hasEnoughPower){
				pushWater();
			}
			else{
				currentPPSWaterProduced = 0f;
			}
		}
		else{
				currentPowerConsumed = 0f;
				currentPPSWaterProduced = 0f;
		}
	}

	public void reset(){
		currentPowerConsumed = 0f;
		currentPPSWaterProduced = 0f;
		hasEnoughPower = false;
		hasEnoughWater = false;
		waterLevel = 0;
	}
}
