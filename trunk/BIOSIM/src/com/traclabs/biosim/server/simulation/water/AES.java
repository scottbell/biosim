/**
 * The AES is the third stage of water purification.  It takes water from the RO, filters it some, and
 * sends the water to the PPS
 *
 * @author    Scott Bell
 */

package biosim.server.water;

public class AES extends WaterRSSubSystem{
	//The subsystem to send the water to next
	private PPS myPPS;
	//Flag switched when the RO has collected references to other subsystems it needs
	private boolean hasCollectedReferences = false;
	
	/**
	* Constructor that creates the AES
	* @param pWaterRSImpl The Water RS system the AES is contained in
	*/
	public AES(WaterRSImpl pWaterRSImpl){
		super(pWaterRSImpl);
	}
	
	/**
	* Flushes the water from this subsystem to the PPS
	*/
	private void pushWater(){
		myWaterRS.getPPS().addWater(waterLevel);
		waterLevel = 0;
	}
	
	/**
	* Collects references to subsystems needed for putting/getting resources
	*/
	private void collectReferences(){
		if (!hasCollectedReferences){
			myPPS = myWaterRS.getPPS();
			hasCollectedReferences = true;
		}
	}
	
	/**
	* In one tick, this subsystem:
	* 1) Collects references (if needed)
	* 2) Flushes the water from this subsystem to the PPS
	*/
	public void tick(){
		collectReferences();
		pushWater();
	}
}
