package biosim.server.air;

import biosim.idl.util.*;
import biosim.idl.power.*;
/**
 * The abstract class all the air subsystems derive from (the VCCR, CRS, OGS, ...)
 *
 * @author    Scott Bell
 */

public abstract class AirRSSubSystem{
	//The power consumed (in watts) by this air subsystem at this tick (default)
	protected float currentPowerConsumed = 0;
	//During any given tick, this much power (in watts) is needed for a air subsystem (default)
	protected float powerNeeded =100;
	protected AirRSImpl myAirRS;
	protected PowerStore myPowerStore;
	//Flag to determine whether the air subsystem has received enough power for this tick
	protected boolean hasEnoughPower = false;
	private boolean logInitialized = false;
	private LogIndex myLogIndex;
	protected boolean hasCollectedReferences = false;

	public AirRSSubSystem(AirRSImpl pAirRSImpl){
		myAirRS = pAirRSImpl;
	}
	
	public abstract void reset();
	
	public float getPowerConsumed(){
		return currentPowerConsumed;
	}

	/**
	* Returns whether the air subsytem has enough power at the current tick.
	* @return <code>true</code> if the air subsytem has enough power to function, <code>false</code> if not
	*/
	public boolean hasPower(){
		return hasEnoughPower;
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

	public abstract void tick();

	public void log(LogNode myHead){
		//If not initialized, fill in the log
		if (!logInitialized){
			myLogIndex = new LogIndex();
			LogNode powerNeededHead = myHead.addChild("Power Needed");
			myLogIndex.powerNeededIndex = powerNeededHead.addChild(""+powerNeeded);
			logInitialized = true;
		}
		else{
			myLogIndex.powerNeededIndex.setValue(""+powerNeeded);
		}
	}

	/**
	* For fast reference to the log tree
	*/
	private class LogIndex{
		public LogNode powerNeededIndex;
	}
}
