package biosim.server.simulation.food;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import biosim.idl.framework.BiomassConsumerOperations;
import biosim.idl.framework.DryWasteProducerOperations;
import biosim.idl.framework.FoodProducerOperations;
import biosim.idl.framework.Malfunction;
import biosim.idl.framework.MalfunctionIntensity;
import biosim.idl.framework.MalfunctionLength;
import biosim.idl.framework.PowerConsumerOperations;
import biosim.idl.framework.WaterProducerOperations;
import biosim.idl.simulation.food.BioMatter;
import biosim.idl.simulation.food.BiomassStore;
import biosim.idl.simulation.food.FoodMatter;
import biosim.idl.simulation.food.FoodProcessorOperations;
import biosim.idl.simulation.food.FoodStore;
import biosim.idl.simulation.power.PowerStore;
import biosim.idl.simulation.waste.DryWasteStore;
import biosim.idl.simulation.water.WaterStore;
import biosim.idl.util.log.LogNode;
import biosim.server.simulation.framework.SimBioModuleImpl;
/**
 * The Food Processor takes biomass (plants matter) and refines it to food for the crew members.
 *
 * @author    Scott Bell
 */

public class FoodProcessorImpl extends SimBioModuleImpl implements FoodProcessorOperations, PowerConsumerOperations, BiomassConsumerOperations, FoodProducerOperations, DryWasteProducerOperations, WaterProducerOperations{
	//During any given tick, this much power is needed for the food processor to run at all
	private float powerNeeded = 100;
	//During any given tick, this much biomass is needed for the food processor to run optimally
	private float biomassNeeded = 200f;
	//Flag switched when the Food Processor has collected references to other servers it need
	private boolean hasCollectedReferences = false;
	//Flag to determine if the Food Processor has enough power to function
	private boolean hasEnoughPower = false;
	//Flag to determine if the Food Processor has enough biomass to function nominally
	private boolean hasEnoughBiomass = false;
	//The biomass consumed (in kilograms) by the Food Processor at the current tick
	private float massConsumed = 0f;
	//The power consumed (in watts) by the Food Processor at the current tick
	private float currentPowerConsumed = 0f;
	//The food produced (in kilograms) by the Food Processor at the current tick
	private float currentFoodProduced = 0f;
	//References to the servers the Food Processor takes/puts resources (like power, biomass, etc)
	private LogIndex myLogIndex;
	private float myProductionRate = 1f;
	private FoodStore[] myFoodStores;
	private DryWasteStore[] myDryWasteStores;
	private PowerStore[] myPowerStores;
	private BiomassStore[] myBiomassStores;
	private WaterStore[] myWaterStores;
	BioMatter[]  biomatterConsumed;
	private float[] powerMaxFlowRates;
	private float[] powerActualFlowRates;
	private float[] powerDesiredFlowRates;
	private float[] biomassMaxFlowRates;
	private float[] biomassActualFlowRates;
	private float[] biomassDesiredFlowRates;
	private float[] foodMaxFlowRates;
	private float[] foodActualFlowRates;
	private float[] foodDesiredFlowRates;
	private float[] dryWasteMaxFlowRates;
	private float[] dryWasteActualFlowRates;
	private float[] dryWasteDesiredFlowRates;
	private float[] waterMaxFlowRates;
	private float[] waterActualFlowRates;
	private float[] waterDesiredFlowRates;
	
	public FoodProcessorImpl(int pID, String pName){
		super(pID, pName);
		myFoodStores = new FoodStore[0];
		foodMaxFlowRates = new float[0];
		foodActualFlowRates = new float[0];
		foodDesiredFlowRates = new float[0];
		myPowerStores = new PowerStore[0];
		powerMaxFlowRates = new float[0];
		powerActualFlowRates = new float[0];
		powerDesiredFlowRates = new float[0];
		myBiomassStores = new BiomassStore[0];
		biomatterConsumed = new BioMatter[0];
		biomassMaxFlowRates = new float[0];
		biomassActualFlowRates = new float[0];
		biomassDesiredFlowRates = new float[0];
		myDryWasteStores = new DryWasteStore[0];
		dryWasteMaxFlowRates = new float[0];
		dryWasteActualFlowRates = new float[0];
		dryWasteDesiredFlowRates = new float[0];
		myWaterStores = new WaterStore[0];
		waterMaxFlowRates = new float[0];
		waterActualFlowRates = new float[0];
		waterDesiredFlowRates = new float[0];
	}
	
	/**
	* Resets production/consumption levels
	*/
	public void reset(){
		super.reset();
		massConsumed = 0f;
		biomatterConsumed = null;
		currentPowerConsumed = 0f;
		currentFoodProduced = 0f;
	}

	/**
	* Returns the biomass consumed (in kilograms) by the Food Processor during the current tick
	* @return the biomass consumed (in kilograms) by the Food Processor during the current tick
	*/
	public float getBiomassConsumed(){
		return massConsumed;
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
		currentPowerConsumed = getResourceFromStore(myPowerStores, powerMaxFlowRates, powerDesiredFlowRates, powerActualFlowRates, powerNeeded);
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
		biomatterConsumed = getBioMassFromStore(myBiomassStores, biomassMaxFlowRates, biomassDesiredFlowRates, biomassActualFlowRates, biomassNeeded);
		massConsumed = calculateSizeOfBioMatter(biomatterConsumed);
		//if (massConsumed > 0)
			//System.out.println(getModuleName()+": massConsumed = "+massConsumed);
		if (massConsumed < biomassNeeded){
			hasEnoughBiomass = false;
		}
		else{
			hasEnoughBiomass = true;
		}
	}
	
	private static float calculateSizeOfBioMatter(BioMatter[] arrayOfMatter){
		float totalSize = 0f;
		for (int i = 0; i < arrayOfMatter.length; i++)
			totalSize += arrayOfMatter[i].mass;
		return totalSize;
	}
	
	private static float calculateSizeOfFoodMatter(FoodMatter[] arrayOfMatter){
		float totalSize = 0f;
		for (int i = 0; i < arrayOfMatter.length; i++)
			totalSize += arrayOfMatter[i].mass;
		return totalSize;
	}
	
	private BioMatter[] getBioMassFromStore(BiomassStore[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates, float[] pActualFlowRates, float amountNeeded){
		float gatheredResource = 0f;
		List gatheredBioMatterArrays = new Vector();
		int sizeOfMatterArray = 0;
		for (int i = 0; (i < pStores.length) && (gatheredResource < amountNeeded); i++){
			float resourceToGatherFirst = Math.min(amountNeeded, pMaxFlowRates[i]);
			float resourceToGatherFinal = Math.min(resourceToGatherFirst, pDesiredFlowRates[i]);
			BioMatter[] takenMatter = pStores[i].takeBioMatterMass(resourceToGatherFinal);
			sizeOfMatterArray += takenMatter.length;
			gatheredBioMatterArrays.add(takenMatter);
			pActualFlowRates[i] = calculateSizeOfBioMatter(takenMatter);
			gatheredResource += pActualFlowRates[i];
		}
		BioMatter[] fullMatterTaken = new BioMatter[sizeOfMatterArray];
		int lastPosition = 0;
		for (Iterator iter = gatheredBioMatterArrays.iterator(); iter.hasNext();){
			BioMatter[] matterArray = (BioMatter[])(iter.next());
			System.arraycopy(matterArray, 0, fullMatterTaken, lastPosition, matterArray.length);
			lastPosition = matterArray.length;
		}
		
		return fullMatterTaken;
	}
	
	public float pushFoodToStore(FoodStore[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates, float[] pActualFlowRates, FoodMatter[] foodToPush){
		float fullMassToDistribute = calculateSizeOfFoodMatter(foodToPush);
		float resourceDistributed = fullMassToDistribute;
		FoodMatter[] copyOfMatter = new FoodMatter[foodToPush.length];
		System.arraycopy(foodToPush, 0, copyOfMatter, 0, foodToPush.length);
		for (int i = 0; (i < pStores.length) && (resourceDistributed > 0); i++){
			float resourceToDistributeFirst = Math.min(resourceDistributed, pMaxFlowRates[i]);
			float resourceToDistributeFinal = Math.min(resourceToDistributeFirst, pDesiredFlowRates[i]);
			pActualFlowRates[i] = pStores[i].addFoodMatterArray(copyOfMatter);
			resourceDistributed -= pActualFlowRates[i];
		}
		float amountPushed = (fullMassToDistribute - resourceDistributed);
		return amountPushed;
	}
	
	private float calculateInedibleWaterContent(BioMatter inMatter){
		float totalWaterWithinInedibleBioMatter = inMatter.inedibleWaterContent;
		return randomFilter(totalWaterWithinInedibleBioMatter) * myProductionRate;
	}
	
	private FoodMatter transformBioMatter(BioMatter inMatter){
		float foodMass = 0f;
		if (inMatter.inedibleFraction > 0)
			foodMass =  inMatter.mass * (1f - inMatter.inedibleFraction);
		else
			foodMass = inMatter.mass;
		//System.out.println(getModuleName()+": Creating "+foodMass+" kg of food");
		FoodMatter newFoodMatter = new FoodMatter(foodMass, inMatter.edibleWaterContent,inMatter.type);
		newFoodMatter.mass = randomFilter(newFoodMatter.mass) * myProductionRate;
		return newFoodMatter;
	}
	
	/**
	* If the Food Processor has any biomass and enough power, it provides some food to put into the store.
	*/
	private void createFood(){
		if (hasEnoughPower){
			if (biomatterConsumed == null){
				currentFoodProduced = 0f;
				return;
			}
			FoodMatter[] foodMatterArray = new FoodMatter[biomatterConsumed.length];
			float currentWaterProduced = 0f;
			for (int i = 0; i < foodMatterArray.length; i++){
				foodMatterArray[i] = transformBioMatter(biomatterConsumed[i]);
				currentWaterProduced += calculateInedibleWaterContent(biomatterConsumed[i]);
			}
			currentFoodProduced = calculateSizeOfFoodMatter(foodMatterArray);
			float distributedFoodLeft = pushFoodToStore(myFoodStores, foodMaxFlowRates, foodDesiredFlowRates, foodActualFlowRates, foodMatterArray);
			float currentDryWasteProduced = currentFoodProduced - calculateSizeOfBioMatter(biomatterConsumed);
			float distributedDryWasteLeft = pushResourceToStore(myDryWasteStores, dryWasteMaxFlowRates, dryWasteDesiredFlowRates, dryWasteActualFlowRates, currentDryWasteProduced);
			float distributedWaterLeft = pushResourceToStore(myWaterStores, waterMaxFlowRates, waterDesiredFlowRates, waterActualFlowRates, currentWaterProduced);
		}
	}

	/**
	* Attempts to consume resource (power and biomass) for Food Processor
	*/
	private void consumeResources(){
		gatherPower();
		if (hasEnoughPower)
			gatherBiomass();
	}
	
	private void setProductionRate(float pProductionRate){
		myProductionRate = pProductionRate;
	}
	
	protected void performMalfunctions(){
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
		super.tick();
		massConsumed = 0f;
		currentFoodProduced = 0f;
		currentPowerConsumed = 0f;
		consumeResources();
		createFood();
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
	
	public void log(){
		/*
			LogNode powerNeededHead = myLog.addChild("power_needed");
			myLogIndex.powerNeededIndex = powerNeededHead.addChild(""+powerNeeded);
			LogNode hasEnoughPowerHead = myLog.addChild("has_enough_power");
			myLogIndex.hasEnoughPowerIndex = hasEnoughPowerHead.addChild(""+hasEnoughPower);
			LogNode biomassNeededHead = myLog.addChild("biomass_needed");
			myLogIndex.biomassNeededIndex = biomassNeededHead.addChild(""+biomassNeeded);
			LogNode massConsumedHead = myLog.addChild("current_biomass_consumed");
			myLogIndex.massConsumedIndex = massConsumedHead.addChild(""+massConsumed);
			LogNode currentPowerConsumedHead = myLog.addChild("current_power_consumed");
			myLogIndex.currentPowerConsumedIndex = currentPowerConsumedHead.addChild(""+currentPowerConsumed);
			LogNode currentFoodProducedHead = myLog.addChild("current_food_produced");
			myLogIndex.currentFoodProducedIndex = currentFoodProducedHead.addChild(""+currentFoodProduced);
		*/
	}
	
	//Power Input
	public void setPowerInputMaxFlowRate(float watts, int index){
		powerMaxFlowRates[index] = watts;
	}
	public float getPowerInputMaxFlowRate(int index){
		return powerMaxFlowRates[index];
	}
	public float[] getPowerInputMaxFlowRates(){
		return powerMaxFlowRates;
	}
	public void setPowerInputDesiredFlowRate(float watts, int index){
		powerDesiredFlowRates[index] = watts;
	}
	public float getPowerInputDesiredFlowRate(int index){
		return powerDesiredFlowRates[index];
	}
	public float[] getPowerInputDesiredFlowRates(){
		return powerDesiredFlowRates;
	}
	public float getPowerInputActualFlowRate(int index){
		return powerActualFlowRates[index];
	}
	public float[] getPowerInputActualFlowRates(){
		return powerActualFlowRates;
	}
	public void setPowerInputs(PowerStore[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		myPowerStores = sources;
		powerMaxFlowRates = maxFlowRates;
		powerDesiredFlowRates = desiredFlowRates;
		powerActualFlowRates = new float[powerDesiredFlowRates.length]; 
		
	}
	public PowerStore[] getPowerInputs(){
		return myPowerStores;
	}
	
	//Biomass Input
	public void setBiomassInputMaxFlowRate(float kilograms, int index){
		biomassMaxFlowRates[index] = kilograms;
	}
	public float getBiomassInputMaxFlowRate(int index){
		return biomassMaxFlowRates[index];
	}
	public float[] getBiomassInputMaxFlowRates(){
		return biomassMaxFlowRates;
	}
	public void setBiomassInputDesiredFlowRate(float kilograms, int index){
		biomassDesiredFlowRates[index] = kilograms;
	}
	public float getBiomassInputDesiredFlowRate(int index){
		return biomassDesiredFlowRates[index];
	}
	public float[] getBiomassInputDesiredFlowRates(){
		return biomassDesiredFlowRates;
	}
	public float getBiomassInputActualFlowRate(int index){
		return biomassActualFlowRates[index];
	}
	public float[] getBiomassInputActualFlowRates(){
		return biomassActualFlowRates;
	}
	public void setBiomassInputs(BiomassStore[] sources, float[] maxFlowRates, float[] desiredFlowRates){
		myBiomassStores = sources;
		biomassMaxFlowRates = maxFlowRates;
		biomassDesiredFlowRates = desiredFlowRates;
		biomassActualFlowRates = new float[biomassDesiredFlowRates.length]; 
	}
	public BiomassStore[] getBiomassInputs(){
		return myBiomassStores;
	}
	
	//Food Output
	public void setFoodOutputMaxFlowRate(float kilograms, int index){
		foodMaxFlowRates[index] = kilograms;
	}
	public float getFoodOutputMaxFlowRate(int index){
		return foodMaxFlowRates[index];
	}
	public float[] getFoodOutputMaxFlowRates(){
		return foodMaxFlowRates;
	}
	public void setFoodOutputDesiredFlowRate(float kilograms, int index){
		foodDesiredFlowRates[index] = kilograms;
	}
	public float getFoodOutputDesiredFlowRate(int index){
		return foodDesiredFlowRates[index];
	}
	public float[] getFoodOutputDesiredFlowRates(){
		return foodDesiredFlowRates;
	}
	public float getFoodOutputActualFlowRate(int index){
		return foodActualFlowRates[index];
	}
	public float[] getFoodOutputActualFlowRates(){
		return foodActualFlowRates;
	}
	public void setFoodOutputs(FoodStore[] destinations, float[] maxFlowRates, float[] desiredFlowRates){
		myFoodStores = destinations;
		foodMaxFlowRates = maxFlowRates;
		foodDesiredFlowRates = desiredFlowRates;
		foodActualFlowRates = new float[foodDesiredFlowRates.length]; 
	}
	public FoodStore[] getFoodOutputs(){
		return myFoodStores;
	}
	
	//DryWaste Output
	public void setDryWasteOutputMaxFlowRate(float kilograms, int index){
		dryWasteMaxFlowRates[index] = kilograms;
	}
	public float getDryWasteOutputMaxFlowRate(int index){
		return dryWasteMaxFlowRates[index];
	}
	public float[] getDryWasteOutputMaxFlowRates(){
		return dryWasteMaxFlowRates;
	}
	public void setDryWasteOutputDesiredFlowRate(float kilograms, int index){
		dryWasteDesiredFlowRates[index] = kilograms;
	}
	public float getDryWasteOutputDesiredFlowRate(int index){
		return dryWasteDesiredFlowRates[index];
	}
	public float[] getDryWasteOutputDesiredFlowRates(){
		return dryWasteDesiredFlowRates;
	}
	public float getDryWasteOutputActualFlowRate(int index){
		return dryWasteActualFlowRates[index];
	}
	public float[] getDryWasteOutputActualFlowRates(){
		return dryWasteActualFlowRates;
	}
	public void setDryWasteOutputs(DryWasteStore[] destinations, float[] maxFlowRates, float[] desiredFlowRates){
		myDryWasteStores = destinations;
		dryWasteMaxFlowRates = maxFlowRates;
		dryWasteDesiredFlowRates = desiredFlowRates;
		dryWasteActualFlowRates = new float[dryWasteDesiredFlowRates.length]; 
	}
	public DryWasteStore[] getDryWasteOutputs(){
		return myDryWasteStores;
	}
	
	//Water Output
	public void setWaterOutputMaxFlowRate(float kilograms, int index){
		waterMaxFlowRates[index] = kilograms;
	}
	public float getWaterOutputMaxFlowRate(int index){
		return waterMaxFlowRates[index];
	}
	public float[] getWaterOutputMaxFlowRates(){
		return waterMaxFlowRates;
	}
	public void setWaterOutputDesiredFlowRate(float kilograms, int index){
		waterDesiredFlowRates[index] = kilograms;
	}
	public float getWaterOutputDesiredFlowRate(int index){
		return waterDesiredFlowRates[index];
	}
	public float[] getWaterOutputDesiredFlowRates(){
		return waterDesiredFlowRates;
	}
	public float getWaterOutputActualFlowRate(int index){
		return waterActualFlowRates[index];
	}
	public float[] getWaterOutputActualFlowRates(){
		return waterActualFlowRates;
	}
	public void setWaterOutputs(WaterStore[] destinations, float[] maxFlowRates, float[] desiredFlowRates){
		myWaterStores = destinations;
		waterMaxFlowRates = maxFlowRates;
		waterDesiredFlowRates = desiredFlowRates;
		waterActualFlowRates = new float[waterDesiredFlowRates.length];
	}
	public WaterStore[] getWaterOutputs(){
		return myWaterStores;
	}

	/**
	* For fast reference to the log tree
	*/
	private class LogIndex{
		public LogNode powerNeededIndex;
		public LogNode hasEnoughPowerIndex;
		public LogNode biomassNeededIndex;
		public LogNode hasEnoughBiomassIndex;
		public LogNode massConsumedIndex;
		public LogNode currentPowerConsumedIndex;
		public LogNode currentFoodProducedIndex;
	}
}
