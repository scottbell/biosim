package biosim.server.framework;

import biosim.idl.framework.*;
import biosim.idl.util.*;
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
	public StoreImpl(){
		level = 0.0f;
		capacity = 10.0f;
	}
	
	/**
	* Creates a Store with an initial level and capacity user specified
	* @param initialLevel the initial level of the store
	* @param initialCapacity the initial capacity of the store
	*/
	public StoreImpl (float initialLevel, float  initialCapacity){
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
	
	/**
	* Attempts to add to the store.  If the level is near capacity, it will only up to capacity
	* @param amountRequested the amount wanted to add to the store
	* @return the amount actually added to the store
	*/
	public float add(float amountRequested){
		float acutallyAdded = 0f;
		if ((amountRequested + level) > capacity){
			//adding more than capacity
			acutallyAdded = (capacity - level);
			level += acutallyAdded;
			return  acutallyAdded;
		}
		else{
			acutallyAdded = amountRequested;
			level += amountRequested;
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
			takenAmount = level;
			level = 0;
		}
		//stuff exists for request
		else{
			takenAmount = amountRequested;
			level -= amountRequested; 
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
			LogNode levelHead = myLog.getHead().addChild("level");
			myLogIndex.levelIndex = levelHead.addChild((""+level));
			LogNode capacityHead = myLog.getHead().addChild("capacity");
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
