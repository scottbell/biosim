package biosim.server.food;

import biosim.idl.food.*;
import biosim.idl.environment.*;
import biosim.idl.water.*;
import biosim.idl.power.*;
import biosim.server.util.*;
import biosim.idl.air.*;
import biosim.idl.util.*;
import biosim.server.framework.*;
/**
 * The Biomass RS is essentially responsible for growing plants.  The plant matter (biomass) is fed into the food processor to create food
 * for the crew.  The plants can also (along with the AirRS) take CO2 out of the air and add O2.
 *
 * @author    Scott Bell
 */

public class BiomassRSImpl extends BioModuleImpl implements BiomassRSOperations {
	//During any given tick, this much power is needed for the Biomass RS to provide light to the plants
	private float powerNeeded = 100;
	//The power consumed (in watts) by the Biomass RS at the current tick
	private float currentPowerConsumed = 0f;
	//Flag switched when the Biomass RS has collected references to other servers it need
	private boolean hasCollectedReferences = false;
	//Flag to determine if the Biomass RS has enough power
	private boolean systemHasEnoughPower = false;
	//References to the servers the Biomass RS takes/puts resources (like air, water, etc)
	private PowerStore myPowerStore;
	private LogIndex myLogIndex;

	/**
	* Resets production/consumption levels and death/affliction flags
	*/
	public void reset(){
		currentPowerConsumed = 0f;
		systemHasEnoughPower = false;
	}

	/**
	* Returns the power consumed (in watts) by the Biomass RS during the current tick
	* @return the power consumed (in watts) by the Biomass RS during the current tick
	*/
	public float getPowerConsumed(){
		return currentPowerConsumed;
	}

	/**
	* Collects references to servers needed for putting/getting resources.
	*/
	private void collectReferences(){
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
	* Attempts to collect enough power from the Power PS to run the Biomass RS for one tick.
	*/
	private void gatherPower(){
		currentPowerConsumed = myPowerStore.take(powerNeeded);
		if (currentPowerConsumed < powerNeeded){
			systemHasEnoughPower = false;
		}
		else{
			systemHasEnoughPower = true;
		}
	}
	
	// 16 mils / min
	

	/**
	* Checks whether Biomass RS has enough power or not
	* @return <code>true</code> if the Biomass RS has enough power, <code>false</code> if not.
	*/
	public boolean hasPower(){
		return systemHasEnoughPower;
	}


	/**
	* Attempts to consume resource for the Biomass RS.
	*/
	private void consumeResources(){
		//gather power for trays
		gatherPower();
	}


	/**
	* When ticked, the Biomass RS does the following
	* on the condition that the plants aren't dead it.
	* 1) attempts to collect references to various server (if not already done).
	* 4) consumes
	*/
	public void tick(){
		collectReferences();
		consumeResources();
		if (moduleLogging)
			log();
	}

	/**
	* Returns the name of this module (BiomassRS)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "BiomassRS";
	}

	private void log(){
		//If not initialized, fill in the log
		if (!logInitialized){
			myLogIndex = new LogIndex();
			LogNode powerNeededHead = myLog.getHead().addChild("Power Needed");
			myLogIndex.powerNeededIndex = powerNeededHead.addChild(""+powerNeeded);
			LogNode systemHasEnoughPowerHead = myLog.getHead().addChild("System has enough power");
			myLogIndex.systemHasEnoughPowerIndex = systemHasEnoughPowerHead.addChild(""+systemHasEnoughPower);

			logInitialized = true;
		}
		else{
			myLogIndex.powerNeededIndex.setValue(""+powerNeeded);
			myLogIndex.systemHasEnoughPowerIndex.setValue(""+systemHasEnoughPower);
		}
		sendLog(myLog);
	}

	/**
	* For fast reference to the log tree
	*/
	private class LogIndex{
		public LogNode powerNeededIndex;
		public LogNode systemHasEnoughPowerIndex;
	}
}
