package biosim.server.water;

import biosim.idl.util.*;
import biosim.idl.power.*;
/**
 * The abstract class all the water subsystems derive from (the AES, BWP, PPS, and RO).
 *
 * @author    Scott Bell
 */

public abstract class WaterRSSubSystem{
	//The power consumed (in watts) by this water subsystem at this tick (default)
	protected float currentPowerConsumed = 0;
	//During any given tick, this much power (in watts) is needed for a water subsystem (default)
	protected float powerNeeded =100;
	//During any given tick, this much water (in liters) is needed for a water subsystem (default)
	//Pete says WRS consumes/produces 16 mL/s -> 576 L/H
	protected float waterNeeded = 576.0f;
	//Reference to the WaterRS to get other watersubsystems
	protected WaterRSImpl myWaterRS;
	protected PowerStore myPowerStore;
	//Flag to determine whether the water subsystem has received enough power for this tick
	protected boolean hasEnoughPower = false;
	//Flag to determine whether the water subsystem has received enough water for this tick
	protected boolean hasEnoughWater = false;
	//Amount of water in this subsystem at the current tick
	protected float waterLevel = 0;
	private boolean logInitialized = false;
	private LogIndex myLogIndex;
	//Flag switched when the BWP has collected references to other subsystems it needs
	protected boolean hasCollectedReferences = false;
	protected boolean enabled = true;

	/**
	* Constructor that creates the subsystem
	* @param pWaterRSImpl The Water RS system this subsystem is contained in
	*/
	public WaterRSSubSystem(WaterRSImpl pWaterRSImpl){
		myWaterRS = pWaterRSImpl;
	}
	
	/**
	* Resets power consumption and water levels.
	*/
	public abstract void reset();
	
	public float getPowerConsumed(){
		return currentPowerConsumed;
	}

	/**
	* Returns whether the water subsytem has enough power at the current tick.
	* @return <code>true</code> if the water subsytem has enough power to function, <code>false</code> if not
	*/
	public boolean hasPower(){
		return hasEnoughPower;
	}

	public boolean isEnabled(){
		return enabled;
	}

	public void setEnabled(boolean pEnabled){
		enabled = pEnabled;
		if (!enabled){
			hasEnoughWater = true;
			hasEnoughPower = true;
		}
	}

	/**
	* Returns whether the water subsytem has enough water at the current tick.
	* @return <code>true</code> if the water subsytem has enough water to function, <code>false</code> if not
	*/
	public boolean hasWater(){
		return hasEnoughWater;
	}

	/**
	* Adds power to the subsystem for this tick
	*/
	protected void gatherPower(){
		currentPowerConsumed = myPowerStore.take(powerNeeded);
		if (currentPowerConsumed < powerNeeded){
			hasEnoughPower = false;
		}
		else{
			hasEnoughPower = true;
		}
	}

	/**
	* Adds water to the subsystem for this tick
	* @param pWater the amount of water to add (in liters)
	*/
	public void addWater(float pWater){
		waterLevel = pWater;
		if (waterLevel < 0){
			hasEnoughWater = false;
		}
		else{
			hasEnoughWater = true;
		}
	}

	/**
	* Tick does nothing by default
	*/
	public void tick(){
	}

	public void log(LogNode myHead){
		//If not initialized, fill in the log
		if (!logInitialized){
			myLogIndex = new LogIndex();
			LogNode enabledHead = myHead.addChild("Enabled");
			myLogIndex.enabledIndex = enabledHead.addChild(""+enabled);
			LogNode powerNeededHead = myHead.addChild("Power Needed");
			myLogIndex.powerNeededIndex = powerNeededHead.addChild(""+powerNeeded);
			LogNode currentPowerConsumedHead = myHead.addChild("Power Consumed");
			myLogIndex.currentPowerConsumedIndex = currentPowerConsumedHead.addChild(""+currentPowerConsumed);
			LogNode waterNeededHead = myHead.addChild("Water Needed");
			myLogIndex.waterNeededIndex = waterNeededHead.addChild(""+waterNeeded);
			LogNode hasEnoughPowerHead = myHead.addChild("Has Enough Power");
			myLogIndex.hasEnoughPowerIndex = hasEnoughPowerHead.addChild(""+hasEnoughPower);
			LogNode hasEnoughWaterHead = myHead.addChild("Has Enough Water");
			myLogIndex.hasEnoughWaterIndex = hasEnoughWaterHead.addChild(""+hasEnoughWater);
			LogNode waterLevelHead = myHead.addChild("Water Level");
			myLogIndex.waterLevelIndex = waterLevelHead.addChild(""+waterLevel);
			logInitialized = true;
		}
		else{
			myLogIndex.enabledIndex.setValue(""+enabled);
			myLogIndex.powerNeededIndex.setValue(""+powerNeeded);
			myLogIndex.currentPowerConsumedIndex.setValue(""+currentPowerConsumed);
			myLogIndex.waterNeededIndex.setValue(""+waterNeeded);
			myLogIndex.hasEnoughPowerIndex.setValue(""+hasEnoughPower);
			myLogIndex.hasEnoughWaterIndex.setValue(""+hasEnoughWater);
			myLogIndex.waterLevelIndex.setValue(""+waterLevel);
		}
	}

	/**
	* For fast reference to the log tree
	*/
	private class LogIndex{
		public LogNode enabledIndex;
		public LogNode powerNeededIndex;
		public LogNode currentPowerConsumedIndex;
		public LogNode waterNeededIndex;
		public LogNode hasEnoughPowerIndex;
		public LogNode hasEnoughWaterIndex;
		public LogNode waterLevelIndex;
	}
}
