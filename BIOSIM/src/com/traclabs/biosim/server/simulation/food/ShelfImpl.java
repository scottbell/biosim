package biosim.server.simulation.food;

import biosim.idl.simulation.food.*;
import biosim.idl.simulation.water.*;
import biosim.server.util.*;
import biosim.idl.simulation.power.*;
import biosim.idl.util.log.*;
import java.util.*;
/**
 * Tray contains Plants
 * @author    Scott Bell
 */

public class ShelfImpl extends ShelfPOA {
	private PlantImpl myCrop;
	private PlantType initialType;
	private float cropAreaTotal = 0f;
	private float cropAreaUsed = 0f;
	private LogIndex myLogIndex;
	private boolean logInitialized = false;
	private BiomassRSImpl myBiomassRSImpl;
	private float waterLevel = 0f;
	private static final float waterNeededPerMeterSquared = 50f; //grab up to 50 liters per meters squared of crops per hour(WAG)
	private float waterNeeded = 0f;
	private float powerLevel = 0f;
	private float powerPerLamp = 400f;
	private float numberOfLamps = 1f;
	private int myStartTick;


	public ShelfImpl(PlantType pType, float pCropAreaTotal, BiomassRSImpl pBiomassImpl){
		this(pType, pCropAreaTotal, pBiomassImpl, 0);
	}
	
	public ShelfImpl(PlantType pType, float pCropAreaTotal, BiomassRSImpl pBiomassImpl, int pStartTick){
		myStartTick = pStartTick;
		cropAreaTotal = pCropAreaTotal;
		myBiomassRSImpl = pBiomassImpl;
		initialType = pType;
		replant(pType, cropAreaTotal);
		waterNeeded = cropAreaUsed * waterNeededPerMeterSquared;
	}

	public Plant getPlant(){
		return PlantHelper.narrow(OrbUtils.poaToCorbaObj(myCrop));
	}

	public void reset(){
		waterLevel = 0f;
		powerLevel = 0f;
		myCrop.reset();
	}

	public PlantType getCropType(){
		return myCrop.getPlantType();
	}
	
	public String getCropTypeString(){
		return myCrop.getPlantTypeString();
	}

	public BiomassRS getBiomassRS(){
		return BiomassRSHelper.narrow(OrbUtils.poaToCorbaObj(myBiomassRSImpl));
	}

	public BiomassRSImpl getBiomassRSImpl(){
		return myBiomassRSImpl;
	}

	public float getCropAreaTotal(){
		return cropAreaTotal;
	}

	public float getCropAreaUsed(){
		return cropAreaUsed;
	}
	
	public void setStartTick(int tick){
		myStartTick = tick;
	}

	private float calculatePowerNeeded(){
		return powerPerLamp * numberOfLamps;
	}

	private void gatherWater(){
		float extraWaterNeeded = waterNeeded-waterLevel; 
		if (extraWaterNeeded < 0) {extraWaterNeeded=0;}
		float gatheredGreyWater = myBiomassRSImpl.getFractionalResourceFromStore(myBiomassRSImpl.getGreyWaterInputs(), myBiomassRSImpl.getGreyWaterInputMaxFlowRates(), myBiomassRSImpl.getGreyWaterInputDesiredFlowRates(), myBiomassRSImpl.getGreyWaterInputActualFlowRates(), extraWaterNeeded, 1f / myBiomassRSImpl.getNumberOfShelves());
		float gatheredPotableWater = myBiomassRSImpl.getFractionalResourceFromStore(myBiomassRSImpl.getPotableWaterInputs(), myBiomassRSImpl.getPotableWaterInputMaxFlowRates(), myBiomassRSImpl.getPotableWaterInputDesiredFlowRates(), myBiomassRSImpl.getPotableWaterInputActualFlowRates(), extraWaterNeeded - gatheredGreyWater, 1f / myBiomassRSImpl.getNumberOfShelves());
		waterLevel += gatheredGreyWater + gatheredPotableWater;
	}

	private void gatherPower(){
		float powerNeeded = calculatePowerNeeded();
		powerLevel += myBiomassRSImpl.getFractionalResourceFromStore(myBiomassRSImpl.getPowerInputs(), myBiomassRSImpl.getPowerInputMaxFlowRates(), myBiomassRSImpl.getPowerInputDesiredFlowRates(), myBiomassRSImpl.getPowerInputActualFlowRates(), powerNeeded, myBiomassRSImpl.getNumberOfShelves());
	}

	private void flushWater(){
		waterLevel -= myBiomassRSImpl.pushFractionalResourceToStore(myBiomassRSImpl.getDirtyWaterOutputs(), myBiomassRSImpl.getDirtyWaterOutputMaxFlowRates(), myBiomassRSImpl.getDirtyWaterOutputDesiredFlowRates(), myBiomassRSImpl.getDirtyWaterOutputActualFlowRates(), waterLevel, myBiomassRSImpl.getNumberOfShelves());
	}

	private void flushPower(){
		powerLevel = 0f;
	}

	public float takeWater(float pLiters){
		if (waterLevel < pLiters){
			float waterTaken = waterLevel;
			waterLevel = 0;
			return waterTaken;
		}
		else{
			waterLevel -= pLiters;
			return pLiters;
		}
	}

	private float pow(float a, float b){
		return (new Double(Math.pow(a,b))).floatValue();
	}

	private void lightPlants(){
		//System.out.println("ShelfImpl: powerLevel: "+powerLevel);
		//System.out.println("ShelfImpl: getLampEfficiency: "+getLampEfficiency());
		//System.out.println("ShelfImpl: getPSEfficiency: "+getPSEfficiency());
		float powerToDeliver = 	Math.min(powerLevel, myCrop.getPPFNeeded() * getCropAreaUsed() / (getLampEfficiency() * getPSEfficiency()));
		if (powerToDeliver <= 0)
			powerToDeliver = Float.MIN_VALUE;
		float thePPF = powerToDeliver * getLampEfficiency() * getPSEfficiency() / getCropAreaUsed();
		//System.out.println("ShelfImpl: thePPF: "+thePPF);
		myCrop.shine(thePPF);
	}

	private float getLampEfficiency(){
		return 261f; //for high pressure sodium bulbs
	}

	private float getPSEfficiency(){
		return 4.68f; //for high pressure sodium bulbs
	}

	public void harvest(){
		BioMatter biomassProduced = myCrop.harvest();
		pushFractionalResourceToBiomassStore(myBiomassRSImpl.getBiomassOutputs(), myBiomassRSImpl.getBiomassOutputMaxFlowRates(), myBiomassRSImpl.getBiomassOutputDesiredFlowRates(), myBiomassRSImpl.getBiomassOutputActualFlowRates(), biomassProduced, myBiomassRSImpl.getNumberOfShelves());
	}

	public boolean isReadyForHavest(){
		return myCrop.readyForHarvest();
	}
	
	public boolean isDead(){
		return myCrop.isDead();
	}
	
	public float getHarvestInterval(){
		return myCrop.getTimeAtCropMaturity();
	}

	private void tryHarvesting(){
		if (myBiomassRSImpl.autoHarvestAndReplantEnabled()){
			if (myCrop.readyForHarvest() || myCrop.isDead()){
				System.out.println("ShelfImpl: Harvested "+myCrop.getPlantTypeString());
				BioMatter biomassProduced = myCrop.harvest();
				float biomassAdded = pushFractionalResourceToBiomassStore(myBiomassRSImpl.getBiomassOutputs(), myBiomassRSImpl.getBiomassOutputMaxFlowRates(), myBiomassRSImpl.getBiomassOutputDesiredFlowRates(), myBiomassRSImpl.getBiomassOutputActualFlowRates(), biomassProduced, myBiomassRSImpl.getNumberOfShelves());
				myCrop.reset();
			}
		}
	}

	private float pushFractionalResourceToBiomassStore(BiomassStore[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates, float[] pActualFlowRates, BioMatter matterToPush, float shelfFraction){
		float resourceDistributed = matterToPush.mass;
		for (int i = 0; (i < pStores.length) && (resourceDistributed > 0); i++){
			float resourceToDistributeFirst = Math.min(resourceDistributed, pMaxFlowRates[i] * shelfFraction);
			float resourceToDistributeFinal = Math.min(resourceToDistributeFirst, pDesiredFlowRates[i] * shelfFraction);
			
			float fractionOfOriginal = resourceToDistributeFinal / resourceDistributed;
			BioMatter newBioMatter = new BioMatter(resourceToDistributeFinal, matterToPush.inedibleFraction, matterToPush.edibleWaterContent * fractionOfOriginal, matterToPush.inedibleWaterContent * fractionOfOriginal, matterToPush.type);
			pActualFlowRates[i] += pStores[i].addBioMatter(newBioMatter);
			resourceDistributed -= pActualFlowRates[i];
		}
		return (matterToPush.mass - resourceDistributed);
	}

	public void tick(){
		if (cropAreaUsed > 0 && (myBiomassRSImpl.getMyTicks() >= myStartTick)){
			tryHarvesting();
			gatherPower();
			gatherWater();
			lightPlants();
			myCrop.tick();
			//flushWater();
			flushPower();
		}
	}

	public void replant(PlantType pType){
		replant(pType, cropAreaTotal);
	}

	public void replant(PlantType pType, float pArea){
		if (pArea > cropAreaTotal)
			cropAreaUsed = cropAreaTotal;
		else
			cropAreaUsed = pArea;
		if (pType == PlantType.DRY_BEAN)
			myCrop = new DryBean(this);
		else if (pType == PlantType.LETTUCE)
			myCrop = new Lettuce(this);
		else if (pType == PlantType.PEANUT)
			myCrop = new Peanut(this);
		else if (pType == PlantType.SOYBEAN)
			myCrop = new Soybean(this);
		else if (pType == PlantType.RICE)
			myCrop = new Rice(this);
		else if (pType == PlantType.SWEET_POTATO)
			myCrop = new SweetPotato(this);
		else if (pType == PlantType.TOMATO)
			myCrop = new Tomato(this);
		else if (pType == PlantType.WHEAT)
			myCrop = new Wheat(this);
		else if (pType == PlantType.WHITE_POTATO)
			myCrop = new WhitePotato(this);
		waterNeeded = cropAreaUsed * waterNeededPerMeterSquared;
	}
	
	public void log(LogNode myLogHead){
		//If not initialized, fill in the log
		if (!logInitialized){
			myLogIndex = new LogIndex();
			myLogIndex.plantHead = myLogHead.addChild("plant");
			myCrop.log(myLogIndex.plantHead);
			logInitialized = true;
		}
		else{
			myCrop.log(myLogIndex.plantHead);
		}
	}

	/**
	* For fast reference to the log tree
	*/
	private class LogIndex{
		public LogNode plantHead;
	}
}