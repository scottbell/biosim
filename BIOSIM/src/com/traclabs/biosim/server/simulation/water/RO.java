package biosim.server.water;
/**
 * The RO is the second stage of water purification.  It takes water from the BWP, filters it some, and
 * sends the water to the AES
 *
 * @author    Scott Bell
 */

public class RO extends WaterRSSubSystem{
	//The subsystem to send the water to next
	private AES myAES;
	//Flag switched when the RO has collected references to other subsystems it needs
	private boolean hasCollectedReferences = false;
	
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
		myWaterRS.getAES().addWater(waterLevel);
		waterLevel = 0;
	}
	
	/**
	* In one tick, this subsystem:
	* 1) Collects references (if needed).
	* 2) Flushes the water from this subsystem to the AES.
	*/
	public void tick(){
		collectReferences();
		pushWater();
	}
	
	/**
	* Collects references to subsystems needed for putting/getting resources
	*/
	private void collectReferences(){
		if (!hasCollectedReferences){
			myAES = myWaterRS.getAES();
			hasCollectedReferences = true;
		}
	}
}
