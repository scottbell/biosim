package biosim.server.simulation.food;

import biosim.idl.simulation.food.*;
import biosim.idl.simulation.water.*;
import biosim.server.util.*;
import biosim.idl.simulation.power.*;
import biosim.idl.util.log.*;
import java.util.*;
/**
 * Tray contains Plants
 * @author    Scott Bell
 */

public class ShelfImpl extends ShelfPOA {
	private PlantImpl myCrop;
	private float totalArea = 8.24f;
	private LogIndex myLogIndex;
	private boolean logInitialized = false;
	private BiomassRSImpl myBiomassImpl;
	
	public ShelfImpl(BiomassRSImpl pBiomassImpl, PlantType pType){
		myBiomassImpl = pBiomassImpl;
		if (pType == PlantType.WHEAT)
			myCrop = new Wheat(this);
	}
	
	public Plant getPlant(){
		return PlantHelper.narrow(OrbUtils.poaToCorbaObj(myCrop));
	}
	
	public void reset(){
		 myCrop.reset();
	}
	
	public void tick(){
		myCrop.tick();
	}
	
	public void replant(PlantType pType){
		if (pType == PlantType.WHEAT)
			myCrop = new Wheat(this);
	}
	
	public void log(LogNode myLogHead){
		//If not initialized, fill in the log
		if (!logInitialized){
			myLogIndex = new LogIndex();
			myLogIndex.plantHead = myLogHead.addChild("plant");
			myCrop.log(myLogIndex.plantHead);
			logInitialized = true; 
		}
		else{
			myCrop.log(myLogIndex.plantHead);
		}
	}
	
	/**
	* For fast reference to the log tree
	*/
	private class LogIndex{
		public LogNode plantHead;
	}
}
