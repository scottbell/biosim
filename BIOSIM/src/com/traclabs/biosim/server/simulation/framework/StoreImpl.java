package biosim.server.simulation.framework;

import biosim.idl.simulation.framework.*;
import biosim.idl.framework.*;
import java.util.*;
import biosim.server.util.*;
import biosim.idl.util.log.*;
import biosim.server.simulation.framework.*;
/**
 * The basic Store Implementation.  Allows for basic store functionality (like adding, removing).<br>
 * Stores report information about their levels, etc. from currentTick-1 until ALL modules have advanced to currentTick.<br>
 * This allows for simulated parralelism.
 * @author    Scott Bell
 */

public abstract class StoreImpl extends SimBioModuleImpl implements StoreOperations{
	//The level of whatever this store is holding (at t)
	protected float level = 0f;
	//The level of whatever this store is holding (at t-1)
	protected float oldLevel = 0f;
	//The capacity of what this store can hold (at t)
	protected float capacity = 0f;
	//The capacity of what this store can hold (at t-1)
	protected float oldCapacity = 0f;
	//What this store has leaked (at t-1)
	protected float oldOverflow = 0f;
	//What this store has leaked (at t)
	protected float overflow = 0f;
	//What the capacity was before the permanent malfunction
	private float preMalfunctionCapacity = 0.0f;
	//Used for finding what the current tick is (to see if we're behind or ahead)
	private BioDriver myDriver;
	//An index into the LogNode (speeds up performance)
	private LogIndex myLogIndex;
	//Whether this Store has collected a reference to the BioDriver or not.
	private boolean hasCollectedReferences = false;
	private boolean pipe = false;
	protected float initialLevel = 0f;
	protected float initialCapacity = 0f;
	private int resupplyFrequency = 0;
	private float resupplyAmount = 0f;

	/**
	* Creates a Store with an initial level and capacity of 0
	*/
	public StoreImpl(int pID, String pName){
		super(pID, pName);
		level = oldLevel = initialLevel = 0f;
		capacity = preMalfunctionCapacity = oldCapacity = initialCapacity = 10f;
	}

	/**
	* Creates a Store with an initial level and capacity user specified
	* @param initialLevel the initial level of the store
	* @param initialCapacity the initial capacity of the store
	* @param pPipe whether this store should act like a pipe.  dynamic capcity == level == whatever is added THIS tick (0 if nothing added, maxFlowRate should dictate pipe size, infinite otherwise)
	*/
	public StoreImpl (int pID, String pName, float pInitialLevel, float pInitialCapacity, boolean pPipe){
		super(pID, pName);
		pipe = pPipe;
		level = oldLevel = initialLevel = pInitialLevel;
		capacity = preMalfunctionCapacity = oldCapacity = initialCapacity = pInitialCapacity;
	}
	
	/**
	* If this store acts like a pipe.  dynamic capcity == level == whatever is added THIS tick (0 if nothing added, maxFlowRate should dictate pipe size, infinite otherwise)
	* @return pPipe whether this store acts like a pipe.
	*/
	public boolean isPipe(){
		return pipe;
	}
	
	/**
	* Sets this store to act like a pipe.  dynamic capcity == level == whatever is added THIS tick (0 if nothing added, maxFlowRate should dictate pipe size, infinite otherwise)
	* @param pPipe whether this store should act like a pipe.
	*/
	public void setPipe(boolean pPipe){
		pipe = pPipe;
	}
	
	public void setResupply(int pResupplyFrequency, float pResupplyAmount){
		resupplyFrequency = pResupplyFrequency;
		resupplyAmount = pResupplyAmount;
	}

	/**
	* Sets the capacity of the store (how much it can hold)
	* @param metricAmount the new volume of the store
	*/
	public void setCapacity(float metricAmount){
		capacity = preMalfunctionCapacity = oldCapacity = initialCapacity = metricAmount;
	}

	/**
	* Sets the level to a set amount
	* @param the level to set the store to
	*/
	public void setLevel(float metricAmount){
		level = oldLevel = initialLevel = metricAmount;
	}

	public void tick(){
		super.tick();
		oldLevel = level;
		oldCapacity = capacity;
		oldOverflow = overflow;
		if ((getMyTicks() > 0) && (resupplyFrequency > 0)){
			int remainder = getMyTicks() % resupplyFrequency;
			if (remainder == 0){
				add(resupplyAmount);
			}
		}
		if (pipe){
			level = 0f;
			capacity = 0f;
		}
	}

	/**
	* Collects references to BioDriver for getting current tick
	*/
	private void collectReferences(){
		if (!hasCollectedReferences){
			try{
				myDriver = BioDriverHelper.narrow(OrbUtils.getNamingContext(getID()).resolve_str("BioDriver"));
				hasCollectedReferences = true;

			}
			catch (org.omg.CORBA.UserException e){
				System.err.println("StoreImpl: Couldn't find BioDriver!!");
				e.printStackTrace(System.out);
			}
		}
	}
	
	/**
	* Gives a decent name/description of the malfunction as it relates to this module.
	* @param pIntensity The intensity of the malfunction (severe, medium, low)
	* @param pIntensity The temporal length of the malfunction (temporary, permanent)
	* @return the description/name of the malfunction
	*/
	protected String getMalfunctionName(MalfunctionIntensity pIntensity, MalfunctionLength pLength){
		StringBuffer returnBuffer = new StringBuffer();
		if (pIntensity == MalfunctionIntensity.SEVERE_MALF)
			returnBuffer.append("Severe ");
		else if (pIntensity == MalfunctionIntensity.MEDIUM_MALF)
			returnBuffer.append("Medium ");
		else if (pIntensity == MalfunctionIntensity.LOW_MALF)
			returnBuffer.append("Low ");
		if (pLength == MalfunctionLength.TEMPORARY_MALF)
			returnBuffer.append("Leak");
		else if (pLength == MalfunctionLength.PERMANENT_MALF)
			returnBuffer.append("Capacity Reduction");
		return returnBuffer.toString();
	}
	
	/**
	* Actually performs the malfunctions.  Reduces levels/capacity
	*/
	protected void performMalfunctions(){
		for (Iterator iter = myMalfunctions.values().iterator(); iter.hasNext(); ){
			Malfunction currentMalfunction = (Malfunction)(iter.next());
			if (currentMalfunction.getLength() == MalfunctionLength.TEMPORARY_MALF){
				float leakRate = 0f;
				if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
					leakRate = .20f;
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
					leakRate = .10f;
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
					leakRate = .05f;
				level -= (level * leakRate);
				currentMalfunction.setPerformed(true);
			}
			else if ((currentMalfunction.getLength() == MalfunctionLength.PERMANENT_MALF) && (!currentMalfunction.hasPerformed())){
				float percentage;
				if (capacity <= 0){
					level = 0;
					percentage = 0;
					currentMalfunction.setPerformed(true);
					return;
				}
				percentage = level / capacity;
				if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
					capacity = 0f;
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
					capacity *= 0.5;
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
					capacity *= .25f;
				level = percentage * capacity;
				currentMalfunction.setPerformed(true);
			}
		}
	}

	/**
	* Attempts to add to the store.  If the level is near capacity, it will only up to capacity
	* @param amountRequested the amount wanted to add to the store
	* @return the amount actually added to the store
	*/
	public float add(float amountRequested){
		//idiot check
		if (amountRequested < 0)
			return 0f;
		if (pipe)
			capacity += amountRequested;
		float acutallyAdded = 0f;
		if ((amountRequested + level) > capacity){
			//adding more than capacity
			acutallyAdded = randomFilter(capacity - level);
			level += acutallyAdded;
			overflow += (amountRequested - acutallyAdded);
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
		if (pipe)
			capacity -= amountRequested;
		//idiot check
		if (amountRequested <= 0f){
			return 0f;
		}
		float takenAmount;
		//asking for more stuff than exists
		if (amountRequested > level){
			takenAmount = randomFilter(level);
			level = 0f;
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
		collectReferences();
		if (getMyTicks() == myDriver.getTicks())
			return oldLevel;
		else
			return level;
	}
	
	/**
	* Retrieves the overflow of the store
	* @return the overflow of the store
	*/
	public float getOverflow(){
		collectReferences();
		if (getMyTicks() == myDriver.getTicks())
			return oldOverflow;
		else
			return overflow;
	}

	/**
	* Retrieves the capacity of the store
	* @return the capacity of the store
	*/
	public float getCapacity(){
		collectReferences();
		if (getMyTicks() == myDriver.getTicks())
			return oldCapacity;
		else
			return capacity;
	}

	/**
	* Resets the level to 0
	*/
	public void reset(){
		super.reset();
		level = oldLevel = initialLevel;
		capacity = preMalfunctionCapacity = oldCapacity = initialCapacity;
		overflow = oldOverflow = 0f;
	}
	
	/**
	* Logs this store and sends it to the Logger to be processed
	*/
	public void log(){
		//If not initialized, fill in the log
		if (!logInitialized){
			myLogIndex = new LogIndex();
			LogNode levelHead = myLog.addChild("level");
			myLogIndex.levelIndex = levelHead.addChild((""+level));
			LogNode capacityHead = myLog.addChild("capacity");
			myLogIndex.capacityIndex = capacityHead.addChild((""+capacity));
			LogNode overflowHead = myLog.addChild("overflow");
			myLogIndex.overflowIndex = overflowHead.addChild((""+overflow));
			logInitialized = true;
		}
		else{
			myLogIndex.capacityIndex.setValue(""+capacity);
			myLogIndex.levelIndex.setValue(""+level);
			myLogIndex.overflowIndex.setValue(""+overflow);
		}
		sendLog(myLog);
	}

	/**
	* For fast reference to the log tree
	*/
	private class LogIndex{
		public LogNode levelIndex;
		public LogNode capacityIndex;
		public LogNode overflowIndex;
	}

}
