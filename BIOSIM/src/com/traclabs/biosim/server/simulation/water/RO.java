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
	
	/**
	* Constructor that creates the RO
	* @param pWaterRSImpl The Water RS system the RO is contained in
	*/
	public RO(WaterRSImpl pWaterRSImpl){
		super(pWaterRSImpl);
	}
	
	/**
	* Flushes the water from this subsystem to the AES
	*/
	private void pushWater(){
		myAES.addWater(waterLevel);
		waterLevel = 0;
	}
	
	/**
	* In one tick, this subsystem:
	* 1) Collects references (if needed).
	* 2) Flushes the water from this subsystem to the AES.
	*/
	public void tick(){
		collectReferences();
		gatherPower();
		if (hasEnoughPower){
			pushWater();
		}
	}
	
	/**
	* Collects references to subsystems needed for putting/getting resources
	*/
	private void collectReferences(){
		if (!hasCollectedReferences){
			try{
				myAES = myWaterRS.getAES();
				myPowerStore = PowerStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("PowerStore"));
				hasCollectedReferences = true;
			}
			catch (org.omg.CORBA.UserException e){
				e.printStackTrace(System.out);
			}
		}
	}
	
	public void reset(){
		currentPowerConsumed = 0;
		hasEnoughPower = false;
		hasEnoughWater = false;
		waterLevel = 0;
	}
}
