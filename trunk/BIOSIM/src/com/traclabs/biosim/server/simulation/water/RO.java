package biosim.server.water;

import biosim.idl.util.*;
import biosim.server.util.*;
import biosim.idl.power.*;
/**
 * The RO is the second stage of water purification.  It takes water from the BWP, filters it some, and
 * sends the water to the AES
 *
 * @author    Scott Bell
 */

public class RO extends WaterRSSubSystem{
	//The subsystem to send the water to next
	private AES myAES;
	private PPS myPPS;
	private float currentAESWaterProduced = 0f;
	private float currentPPSWaterProduced = 0f;

	/**
	* Constructor that creates the RO
	* @param pWaterRSImpl The Water RS system the RO is contained in
	*/
	public RO(WaterRSImpl pWaterRSImpl){
		super(pWaterRSImpl);
	}
	
	public float getAESWaterProduced(){
		return currentAESWaterProduced;
	}
	
	public float getPPSWaterProduced(){
		return currentPPSWaterProduced;
	}
	
	/**
	* Flushes the water from this subsystem to the AES
	*/
	private void pushWater(){
		if (myAES.isEnabled()){
			currentAESWaterProduced = (new Double(waterLevel * 0.15f)).floatValue();
			myAES.addWater(currentAESWaterProduced);
		}
		else{
			currentAESWaterProduced = 0f;
		}
		currentPPSWaterProduced = (new Double(waterLevel * 0.85f)).floatValue();
		myPPS.addWater(currentPPSWaterProduced);
		waterLevel = 0;
	}

	/**
	* In one tick, this subsystem:
	* 1) Collects references (if needed).
	* 2) Flushes the water from this subsystem to the AES.
	*/
	public void tick(){
		if (enabled){
			collectReferences();
			gatherPower();
			if (hasEnoughPower){
				pushWater();
			}
			else{
				currentAESWaterProduced = 0f;
				currentPPSWaterProduced = 0f;
			}
		}
		else{
			currentAESWaterProduced = 0f;
			currentPPSWaterProduced = 0f;
			currentPowerConsumed = 0;
		}
	}

	/**
	* Collects references to subsystems needed for putting/getting resources
	*/
	private void collectReferences(){
		if (!hasCollectedReferences){
			try{
				myAES = myWaterRS.getAES();
				myPPS = myWaterRS.getPPS();
				myPowerStore = PowerStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("PowerStore"));
				hasCollectedReferences = true;
			}
			catch (org.omg.CORBA.UserException e){
				e.printStackTrace();
			}
		}
	}

	public void reset(){
		currentPowerConsumed = 0;
		currentAESWaterProduced = 0f;
		currentPPSWaterProduced = 0f;
		hasEnoughPower = false;
		hasEnoughWater = false;
		waterLevel = 0;
	}
}
