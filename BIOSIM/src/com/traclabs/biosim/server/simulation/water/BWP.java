package biosim.server.water;
/**
 * The Biological Waste Processor is the first stage of water purification.  It takes dirty/grey water, filters it some, and
 * sends the water to the RO
 *
 * @author    Scott Bell
 */

public class BWP extends WaterRSSubSystem{
	//The subsystem to send the water to next
	private RO myRO;
	//Flag switched when the BWP has collected references to other subsystems it needs
	private boolean hasCollectedReferences = false;
	
	/**
	* Constructor that creates the BWP
	* @param pWaterRSImpl The Water RS system the BWP is contained in
	*/
	public BWP(WaterRSImpl pWaterRSImpl){
		super(pWaterRSImpl);
	}
	
	/**
	* Collects references to subsystems needed for putting/getting resources
	*/
	private void collectReferences(){
		if (!hasCollectedReferences){
			myRO = myWaterRS.getRO();
			hasCollectedReferences = true;
		}
	}
	
	/**
	* Returns the amount of water (in liters) the BWP would like for this tick
	* @return the amount of water (in liters the BWP wants, 0 if the BWP has no power
	*/
	public float getWaterWanted(){
		if (!hasEnoughPower){
			hasEnoughWater = true;
			return 0;
		}
		else
			return waterNeeded;
	}
	
	/**
	* Flushes the water from this subsystem to the RO
	*/
	private void pushWater(){
		myWaterRS.getRO().addWater(waterLevel);
		waterLevel = 0;
	}
	
	
	/**
	* In one tick, this subsystem:
	* 1) Collects references (if needed).
	* 2) Flushes the water from this subsystem to the RO.
	*/
	public void tick(){
		collectReferences();
		pushWater();
	}
}
