package biosim.server.power;

import biosim.idl.power.*;
import biosim.idl.util.*;
import biosim.server.util.*;
import biosim.server.framework.*;
/**
 * The Power Production System creates power from a generator (say a solar panel) and stores it in the power store.
 * This provides power to all the biomodules in the system.
 *
 * @author    Scott Bell
 */

public abstract class PowerPSImpl extends BioModuleImpl implements PowerPSOperations {
	//The power produced (in watts) by the Power PS at the current tick
	protected float currentPowerProduced = 6720f;
	//Flag switched when the Power PS has collected references to other servers it need
	private boolean hasCollectedReferences = false;
	//References to the PowerStore the Power PS takes/puts power into
	protected PowerStore myPowerStore;
	private LogIndex myLogIndex;
	
	/**
	* When ticked, the Food Processor does the following:
	* 1) attempts to collect references to various server (if not already done).
	* 2) creates power and places it into the power store.
	*/
	public void tick(){
		collectReferences();
		myPowerStore.add(currentPowerProduced);
		if (moduleLogging)
			log();
	}
	
	/**
	* Reset does nothing right now
	*/
	public void reset(){
	}
	
	/**
	* Collects reference to PowerStore needed for putting/getting power.
	*/
	protected void collectReferences(){
		try{
			if (!hasCollectedReferences){
				myPowerStore = PowerStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("PowerStore"));
				hasCollectedReferences = true;
			}
		}
		catch (org.omg.CORBA.UserException e){
			e.printStackTrace(System.out);
		}
	}
	
	/**
	* Returns the power produced (in watts) by the Power PS during the current tick
	* @return the power produced (in watts) by the Power PS during the current tick
	*/
	public  float getPowerProduced(){
		return currentPowerProduced;
	}
	
	/**
	* Returns the name of this module (PowerPS)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "PowerPS";
	}
	
	protected void log(){
		//If not initialized, fill in the log
		if (!logInitialized){
			myLogIndex = new LogIndex();
			LogNode powerProducedHead = myLog.getHead().addChild("Power Produced");
			myLogIndex.powerProducedIndex = powerProducedHead.addChild(""+currentPowerProduced);
			logInitialized = true;
		}
		else{
			myLogIndex.powerProducedIndex.setValue(""+currentPowerProduced);
		}
		sendLog(myLog);
	}

	/**
	* For fast reference to the log tree
	*/
	private class LogIndex{
		public LogNode powerProducedIndex;
	}
}
