package biosim.server.food;

import biosim.idl.food.*;
import biosim.idl.power.*;
import biosim.idl.util.*;
import biosim.server.util.*;
import biosim.server.framework.*;
/**
 * The Food Processor takes biomass (plants matter) and refines it to food for the crew members.
 *
 * @author    Scott Bell
 */

public class FoodProcessorImpl extends BioModuleImpl implements FoodProcessorOperations {
	//During any given tick, this much power is needed for the food processor to run at all
	private float powerNeeded = 100;
	//During any given tick, this much biomass is needed for the food processor to run optimally
	private float biomassNeeded = 0.2f;
	//Flag switched when the Food Processor has collected references to other servers it need
	private boolean hasCollectedReferences = false;
	//Flag to determine if the Food Processor has enough power to function
	private boolean hasEnoughPower = false;
	//Flag to determine if the Food Processor has enough biomass to function nominally
	private boolean hasEnoughBiomass = false;
	//The biomass consumed (in kilograms) by the Food Processor at the current tick
	private float currentBiomassConsumed = 0f;
	//The power consumed (in watts) by the Food Processor at the current tick
	private float currentPowerConsumed = 0f;
	//The food produced (in kilograms) by the Food Processor at the current tick
	private float currentFoodProduced = 0f;
	//References to the servers the Food Processor takes/puts resources (like power, biomass, etc)
	private FoodStore myFoodStore;
	private PowerStore myPowerStore;
	private BiomassStore myBiomassStore;
	private LogIndex myLogIndex;

	/**
	* Resets production/consumption levels
	*/
	public void reset(){
		currentBiomassConsumed = 0f;
		currentPowerConsumed = 0f;
		currentFoodProduced = 0f;
	}

	/**
	* Returns the biomass consumed (in kilograms) by the Food Processor during the current tick
	* @return the biomass consumed (in kilograms) by the Food Processor during the current tick
	*/
	public float getBiomassConsumed(){
		return currentBiomassConsumed;
	}

	/**
	* Returns the power consumed (in watts) by the Food Processor during the current tick
	* @return the power consumed (in watts) by the Food Processor during the current tick
	*/
	public float getPowerConsumed(){
		return currentPowerConsumed;
	}

	/**
	* Returns the food produced (in kilograms) by the Food Processor during the current tick
	* @return the food produced (in kilograms) by the Food Processor during the current tick
	*/
	public float getFoodProduced(){
		return currentFoodProduced;
	}

	/**
	* Checks whether Food Processor has enough power or not
	* @return <code>true</code> if the Food Processor has enough power, <code>false</code> if not.
	*/
	public boolean hasPower(){
		return hasEnoughPower;
	}

	/**
	* Checks whether Food Processor has enough biomass to run optimally or not
	* @return <code>true</code> if the Food Processor has enough biomass, <code>false</code> if not.
	*/
	public boolean hasBiomass(){
		return hasEnoughBiomass;
	}

	/**
	* Collects references to servers needed for putting/getting resources.
	*/
	private void collectReferences(){
		try{
			if (!hasCollectedReferences){
				myPowerStore = PowerStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("PowerStore"));
				myFoodStore = FoodStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("FoodStore"));
				myBiomassStore = BiomassStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("BiomassStore"));
				hasCollectedReferences = true;
			}
		}
		catch (org.omg.CORBA.UserException e){
			e.printStackTrace(System.out);
		}
	}

	/**
	* Attempts to collect enough power from the Power PS to run the Food Processor for one tick.
	*/
	private void gatherPower(){
		currentPowerConsumed = myPowerStore.take(powerNeeded);
		if (currentPowerConsumed < powerNeeded){
			hasEnoughPower = false;
		}
		else{
			hasEnoughPower = true;
		}
	}

	/**
	* Attempts to collect enough biomass from the Biomass Store to run the Food Processor optimally for one tick.
	*/
	private void gatherBiomass(){
		currentBiomassConsumed = myBiomassStore.take(biomassNeeded);
		if (currentBiomassConsumed < biomassNeeded){
			hasEnoughBiomass = false;
		}
		else{
			hasEnoughBiomass = true;
		}
	}

	/**
	* If the Food Processor has any biomass and enough power, it provides some food to put into the store.
	*/
	private void createFood(){
		if (hasEnoughPower){
			currentFoodProduced = currentBiomassConsumed * 0.8f;
			myFoodStore.add(currentFoodProduced);
		}
	}

	/**
	* Attempts to consume resource (power and biomass) for Food Processor
	*/
	private void consumeResources(){
		gatherPower();
		gatherBiomass();
	}

	/**
	* When ticked, the Food Processor does the following: 
	* 1) attempts to collect references to various server (if not already done).
	* 2) consumes power and biomass.
	* 3) creates food (if possible)
	*/
	public void tick(){
		collectReferences();
		consumeResources();
		createFood();
		if (moduleLogging)
			log();
	}

	/**
	* Returns the name of this module (FoodProcessor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "FoodProcessor";
	}

	private void log(){
		//If not initialized, fill in the log
		if (!logInitialized){
			myLogIndex = new LogIndex();
			LogNode powerNeededHead = myLog.getHead().addChild("Power Needed");
			myLogIndex.powerNeededIndex = powerNeededHead.addChild(""+powerNeeded);
			LogNode hasEnoughPowerHead = myLog.getHead().addChild("Has enough power");
			myLogIndex.hasEnoughPowerIndex = hasEnoughPowerHead.addChild(""+hasEnoughPower);
			LogNode biomassNeededHead = myLog.getHead().addChild("Biomass Needed");
			myLogIndex.biomassNeededIndex = biomassNeededHead.addChild(""+biomassNeeded);
			LogNode currentBiomassConsumedHead = myLog.getHead().addChild("Current Biomass Consumed");
			myLogIndex.currentBiomassConsumedIndex = currentBiomassConsumedHead.addChild(""+currentBiomassConsumed);
			LogNode currentPowerConsumedHead = myLog.getHead().addChild("Current Power Consumed");
			myLogIndex.currentPowerConsumedIndex = currentPowerConsumedHead.addChild(""+currentPowerConsumed);
			LogNode currentFoodProducedHead = myLog.getHead().addChild("Current Food Produced");
			myLogIndex.currentFoodProducedIndex = currentFoodProducedHead.addChild(""+currentFoodProduced);
			logInitialized = true;
		}
		else{
			myLogIndex.powerNeededIndex.setValue(""+powerNeeded);
			myLogIndex.hasEnoughPowerIndex.setValue(""+hasEnoughPower);
			myLogIndex.biomassNeededIndex.setValue(""+biomassNeeded);
			myLogIndex.currentBiomassConsumedIndex.setValue(""+currentBiomassConsumed);
			myLogIndex.currentPowerConsumedIndex.setValue(""+currentPowerConsumed);
			myLogIndex.currentFoodProducedIndex.setValue(""+currentFoodProduced);
		}
		sendLog(myLog);
	}

	/**
	* For fast reference to the log tree
	*/
	private class LogIndex{
		public LogNode powerNeededIndex;
		public LogNode hasEnoughPowerIndex;
		public LogNode biomassNeededIndex;
		public LogNode hasEnoughBiomassIndex;
		public LogNode currentBiomassConsumedIndex;
		public LogNode currentPowerConsumedIndex;
		public LogNode currentFoodProducedIndex;
	}
}
