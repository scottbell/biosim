package biosim.server.food;

import biosim.idl.food.*;
import biosim.idl.power.*;
import biosim.idl.framework.*;
import biosim.idl.util.log.*;
import biosim.server.util.*;
import biosim.server.framework.*;
import java.util.*;
/**
 * The Food Processor takes biomass (plants matter) and refines it to food for the crew members.
 *
 * @author    Scott Bell
 */

public class FoodProcessorImpl extends BioModuleImpl implements FoodProcessorOperations, PowerConsumerOperations, BiomassConsumerOperations, FoodProducerOperations{
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
	private FoodStore[] myFoodStores;
	private PowerStore[] myPowerStores;
	private BiomassStore[] myBiomassStores;
	private LogIndex myLogIndex;
	private float myProductionRate;
	private float[] powerFlowRates;
	private float[] biomassFlowRates;
	private float[] foodFlowRates;
	
	public FoodProcessorImpl(int pID){
		super(pID);
	}
	
	/**
	* Resets production/consumption levels
	*/
	public void reset(){
		super.reset();
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
	* Attempts to collect enough power from the Power PS to run the Food Processor for one tick.
	*/
	private void gatherPower(){
		float gatheredPower = 0f;
		for (int i = 0; (i < myPowerStores.length) && (gatheredPower >= powerNeeded); i++){
			gatheredPower += myPowerStores[i].take(powerNeeded);
		}
		currentPowerConsumed = gatheredPower;
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
		float gatheredBiomass = 0f;
		for (int i = 0; (i < myBiomassStores.length) && (gatheredBiomass >= biomassNeeded); i++){
			gatheredBiomass += myBiomassStores[i].take(biomassNeeded);
		}
		currentBiomassConsumed = gatheredBiomass;
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
			currentFoodProduced = randomFilter(currentBiomassConsumed * 0.8f) * myProductionRate;
			float distributedFoodLeft = currentFoodProduced;
			for (int i = 0; (i < myFoodStores.length) && (distributedFoodLeft <= 0); i++){
				distributedFoodLeft -= myFoodStores[i].add(distributedFoodLeft);
			}
		}
	}

	/**
	* Attempts to consume resource (power and biomass) for Food Processor
	*/
	private void consumeResources(){
		gatherPower();
		gatherBiomass();
	}
	
	private void setProductionRate(float pProductionRate){
		myProductionRate = pProductionRate;
	}
	
	private void performMalfunctions(){
		float productionRate = 1f;
		for (Iterator iter = myMalfunctions.values().iterator(); iter.hasNext(); ){
			Malfunction currentMalfunction = (Malfunction)(iter.next());
			if (currentMalfunction.getLength() == MalfunctionLength.TEMPORARY_MALF){
				if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
					productionRate *= 0.50;
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
					productionRate *= 0.25;
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
					productionRate *= 0.10;
			}
			else if (currentMalfunction.getLength() == MalfunctionLength.PERMANENT_MALF){
				if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
					productionRate *= 0.50;
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
					productionRate *= 0.25;
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
					productionRate *= 0.10;
			}
		}
		setProductionRate(productionRate);
	}

	/**
	* When ticked, the Food Processor does the following: 
	* 1) attempts to collect references to various server (if not already done).
	* 2) consumes power and biomass.
	* 3) creates food (if possible)
	*/
	public void tick(){
		consumeResources();
		if (isMalfunctioning())
			performMalfunctions();
		createFood();
		if (moduleLogging)
			log();
	}
	
	protected String getMalfunctionName(MalfunctionIntensity pIntensity, MalfunctionLength pLength){
		StringBuffer returnBuffer = new StringBuffer();
		if (pIntensity == MalfunctionIntensity.SEVERE_MALF)
			returnBuffer.append("Severe ");
		else if (pIntensity == MalfunctionIntensity.MEDIUM_MALF)
			returnBuffer.append("Medium ");
		else if (pIntensity == MalfunctionIntensity.LOW_MALF)
			returnBuffer.append("Low ");
		if (pLength == MalfunctionLength.TEMPORARY_MALF)
			returnBuffer.append("Production Rate Decrease (Temporary)");
		else if (pLength == MalfunctionLength.PERMANENT_MALF)
			returnBuffer.append("Production Rate Decrease (Permanent)");
		return returnBuffer.toString();
	}

	/**
	* Returns the name of this module (FoodProcessor)
	* @return the name of the module
	*/
	public String getModuleName(){
		return "FoodProcessor"+getID();
	}

	private void log(){
		//If not initialized, fill in the log
		if (!logInitialized){
			myLogIndex = new LogIndex();
			LogNode powerNeededHead = myLog.addChild("power_needed");
			myLogIndex.powerNeededIndex = powerNeededHead.addChild(""+powerNeeded);
			LogNode hasEnoughPowerHead = myLog.addChild("has_enough_power");
			myLogIndex.hasEnoughPowerIndex = hasEnoughPowerHead.addChild(""+hasEnoughPower);
			LogNode biomassNeededHead = myLog.addChild("biomass_needed");
			myLogIndex.biomassNeededIndex = biomassNeededHead.addChild(""+biomassNeeded);
			LogNode currentBiomassConsumedHead = myLog.addChild("current_biomass_consumed");
			myLogIndex.currentBiomassConsumedIndex = currentBiomassConsumedHead.addChild(""+currentBiomassConsumed);
			LogNode currentPowerConsumedHead = myLog.addChild("current_power_consumed");
			myLogIndex.currentPowerConsumedIndex = currentPowerConsumedHead.addChild(""+currentPowerConsumed);
			LogNode currentFoodProducedHead = myLog.addChild("current_food_produced");
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
	
	public void setPowerInputFlowrate(float watts, int index){
		powerFlowRates[index] = watts;
	}
	
	public float getPowerInputFlowrate(int index){
		return powerFlowRates[index];
	}
	
	public void setPowerInputs(PowerStore[] sources, float[] flowRates){
		myPowerStores = sources;
		powerFlowRates = flowRates;
	}
	
	public PowerStore[] getPowerInputs(){
		return myPowerStores;
	}
	
	public void setBiomassInputFlowrate(float kilograms, int index){
		biomassFlowRates[index] = kilograms;
	}
	
	public float getBiomassInputFlowrate(int index){
		return biomassFlowRates[index];
	}
	
	public void setBiomassInputs(BiomassStore[] sources, float[] flowRates){
		myBiomassStores = sources;
		biomassFlowRates = flowRates;
	}
	
	public BiomassStore[] getBiomassInputs(){
		return myBiomassStores;
	}
	
	public void setFoodOutputFlowrate(float kilograms, int index){
		foodFlowRates[index] = kilograms;
	}
	
	public float getFoodOutputFlowrate(int index){
		return foodFlowRates[index];
	}
	
	public void setFoodOutputs(FoodStore[] destinations, float[] flowRates){
		myFoodStores = destinations;
		foodFlowRates = flowRates;
	}
	
	public FoodStore[] getFoodOutputs(){
		return myFoodStores;
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
