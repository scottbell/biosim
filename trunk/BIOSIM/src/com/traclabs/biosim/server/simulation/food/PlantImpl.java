package biosim.server.simulation.food;

import biosim.idl.simulation.food.*;
import biosim.idl.simulation.air.*;
import biosim.idl.util.log.*;
import biosim.idl.simulation.environment.*;
import biosim.server.util.*;
/**
 * Plant
 * @author    Scott Bell
 */

public abstract class PlantImpl extends PlantPOA{
	int myAge = 0;
	private LogIndex myLogIndex;
	private boolean logInitialized = false;
	ShelfImpl myShelfImpl;

	public PlantImpl(ShelfImpl pShelfImpl){
		myShelfImpl = pShelfImpl;
	}

	public void reset(){
	}

	public void tick(){
	}
	
	public void shine(){
	}
	
	public float getWaterNeeded(){
		return 0.1f;
	}
	
	public abstract String getPlantType();

	public void log(LogNode myLogHead){
		//If not initialized, fill in the log
		if (!logInitialized){
			myLogIndex = new LogIndex();
			LogNode typeHead = myLogHead.addChild("plant_type");
			myLogIndex.typeIndex = typeHead.addChild(""+getPlantType());
			logInitialized = true;
		}
		else{
			myLogIndex.typeIndex.setValue(""+getPlantType());
		}
	}

	/**
	* For fast reference to the log tree
	*/
	private class LogIndex{
		public LogNode typeIndex;
	}
}
