package biosim.server.framework;

import biosim.idl.framework.*;
import java.util.*;
import biosim.idl.util.log.*;
/**
 * The basic Store Implementation.  Allows for basic store functionality (like adding, removing).
 *
 * @author    Scott Bell
 */

public abstract class StoreImpl extends BioModuleImpl implements StoreOperations{
	//The level of whatever this store is holding
	protected float level = 0.0f;
	//The capacity of what this store can hold
	protected float capacity = 0.0f;
	private LogIndex myLogIndex;

	/**
	* Creates a Store with an initial level and capacity of 0
	*/
	public StoreImpl(int pID){
		super(pID);
		level = 0.0f;
		capacity = 10.0f;
	}

	/**
	* Creates a Store with an initial level and capacity user specified
	* @param initialLevel the initial level of the store
	* @param initialCapacity the initial capacity of the store
	*/
	public StoreImpl (int pID, float initialLevel, float  initialCapacity){
		super(pID);
		level = initialLevel;
		capacity = initialCapacity;
	}

	/**
	* Sets the capacity of the store (how much it can hold)
	* @param metricAmount the new volume of the store
	*/
	public void setCapacity(float metricAmount){
		capacity = metricAmount;
	}

	/**
	* Sets the level to a set amount
	* @param the level to set the store to
	*/
	public void setLevel(float metricAmount){
		level = metricAmount;
	}

	public void tick(){
		if (isMalfunctioning())
			performMalfunctions();
		if (moduleLogging)
			log();
	}
	
	protected String getMalfunctionName(MalfunctionIntensity pIntensity, MalfunctionLength pLength){
		String returnName = new String();
		if (pIntensity == MalfunctionIntensity.SEVERE_MALF)
			returnName += "Severe ";
		else if (pIntensity == MalfunctionIntensity.MEDIUM_MALF)
			returnName += "Medium ";
		else if (pIntensity == MalfunctionIntensity.LOW_MALF)
			returnName += "Low ";
		if (pLength == MalfunctionLength.TEMPORARY_MALF)
			returnName += "Leak";
		else if (pLength == MalfunctionLength.PERMANENT_MALF)
			returnName += "Capacity Reduction";
		return returnName;
	}

	private void performMalfunctions(){
		for (Enumeration e = myMalfunctions.elements(); e.hasMoreElements();){
			Malfunction currentMalfunction = (Malfunction)(e.nextElement());
			if (currentMalfunction.getLength() == MalfunctionLength.TEMPORARY_MALF){
				float leakRate = 0f;
				if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
					leakRate = .20f;
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
					leakRate = .10f;
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
					leakRate = .05f;
				level -= (level * leakRate);
			}
			else if (currentMalfunction.getLength() == MalfunctionLength.PERMANENT_MALF){
				if (capacity < 0){
					level = 0;
					return;
				}
				float percentage = level / capacity;
				if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
					capacity = 0f;
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
					capacity *= 0.5;
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
					capacity *= .2f;
				level = percentage * capacity;
			}
		}
	}

	/**
	* Attempts to add to the store.  If the level is near capacity, it will only up to capacity
	* @param amountRequested the amount wanted to add to the store
	* @return the amount actually added to the store
	*/
	public float add(float amountRequested){
		float acutallyAdded = 0f;
		if ((amountRequested + level) > capacity){
			//adding more than capacity
			acutallyAdded = randomFilter(capacity - level);
			level += acutallyAdded;
			return  acutallyAdded;
		}
		else{
			acutallyAdded = randomFilter(amountRequested);
			level += acutallyAdded;
			return acutallyAdded;
		}
	}

	/**
	* Attemps to take the amount requested from the store
	* @param amountRequested the amount wanted
	* @return the amount actually retrieved
	*/
	public float take(float amountRequested){
		//idiot check
		if (amountRequested < 0){
			return 0f;
		}
		float takenAmount;
		//asking for more stuff than exists
		if (amountRequested > level){
			takenAmount = randomFilter(level);
			level = 0;
		}
		//stuff exists for request
		else{
			takenAmount = randomFilter(amountRequested);
			level -= takenAmount;
		}
		return takenAmount;
	}

	/**
	* Retrieves the level of the store
	* @return the level of the store
	*/
	public float getLevel(){
		return level;
	}

	/**
	* Retrieves the capacity of the store
	* @return the capacity of the store
	*/
	public float getCapacity(){
		return capacity;
	}

	/**
	* Resets the level to 0
	*/
	public void reset(){
		level = 0.0f;
	}

	private void log(){
		//If not initialized, fill in the log
		if (!logInitialized){
			myLogIndex = new LogIndex();
			LogNode levelHead = myLog.addChild("level");
			myLogIndex.levelIndex = levelHead.addChild((""+level));
			LogNode capacityHead = myLog.addChild("capacity");
			myLogIndex.capacityIndex = capacityHead.addChild((""+capacity));
			logInitialized = true;
		}
		else{
			myLogIndex.capacityIndex.setValue(""+capacity);
			myLogIndex.levelIndex.setValue(""+level);
		}
		sendLog(myLog);
	}

	/**
	* For fast reference to the log tree
	*/
	private class LogIndex{
		public LogNode levelIndex;
		public LogNode capacityIndex;
	}

}
