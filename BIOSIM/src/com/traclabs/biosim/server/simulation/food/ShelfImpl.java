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
	private float totalArea = 8.24f;
	private LogIndex myLogIndex;
	private boolean logInitialized = false;
	private BiomassRSImpl myBiomassImpl;
	float waterLevel = 0f;
	float powerLevel = 0f;

	public ShelfImpl(BiomassRSImpl pBiomassImpl, PlantType pType){
		myBiomassImpl = pBiomassImpl;
		if (pType == PlantType.WHEAT)
			myCrop = new Wheat(this);
	}

	public Plant getPlant(){
		return PlantHelper.narrow(OrbUtils.poaToCorbaObj(myCrop));
	}

	public void reset(){
		waterLevel = 0f;
		powerLevel = 0f;
		myCrop.reset();
	}

	private void gatherWater(){
		float gatheredGreyWater = 0f;
		float gatheredPotableWater = 0f;
		for (int i = 0; (i < myBiomassImpl.getGreyWaterInputs().length) && (gatheredGreyWater < myCrop.getWaterNeeded()); i++){
			float resourceToGatherFirst = Math.min(myCrop.getWaterNeeded(), myBiomassImpl.getGreyWaterInputMaxFlowRate(i) / myBiomassImpl.getNumberOfShelves());
			float resourceToGatherFinal = Math.min(resourceToGatherFirst, myBiomassImpl.getGreyWaterInputDesiredFlowRate(i) / myBiomassImpl.getNumberOfShelves());
			float currentWaterGathered = myBiomassImpl.getGreyWaterInputs()[i].take(resourceToGatherFinal);
			myBiomassImpl.addGreyWaterInputActualFlowRates(i, currentWaterGathered);
			gatheredGreyWater += currentWaterGathered;
		}
		float waterRemainingToGather = myCrop.getWaterNeeded() - gatheredGreyWater;
		for (int i = 0; (i < myBiomassImpl.getPotableWaterInputs().length) && (gatheredPotableWater < waterRemainingToGather); i++){
			float resourceToGatherFirst = Math.min(waterRemainingToGather, myBiomassImpl.getPotableWaterInputMaxFlowRate(i) / myBiomassImpl.getNumberOfShelves());
			float resourceToGatherFinal = Math.min(resourceToGatherFirst, myBiomassImpl.getPotableWaterInputDesiredFlowRate(i) / myBiomassImpl.getNumberOfShelves());
			float currentWaterGathered = myBiomassImpl.getPotableWaterInputs()[i].take(resourceToGatherFinal);
			myBiomassImpl.addPotableWaterInputActualFlowRates(i, currentWaterGathered);
			gatheredPotableWater += currentWaterGathered;
		}
		waterLevel = gatheredGreyWater + gatheredPotableWater;
	}
	
	private void gatherPower(){
		float gatheredPower = 0f;
		for (int i = 0; (i < myBiomassImpl.getPowerInputs().length) && (gatheredPower < myCrop.getWaterNeeded()); i++){
			float resourceToGatherFirst = Math.min(myCrop.getWaterNeeded(), myBiomassImpl.getPowerInputMaxFlowRate(i) / myBiomassImpl.getNumberOfShelves());
			float resourceToGatherFinal = Math.min(resourceToGatherFirst, myBiomassImpl.getPowerInputDesiredFlowRate(i) / myBiomassImpl.getNumberOfShelves());
			float currentWaterGathered = myBiomassImpl.getPowerInputs()[i].take(resourceToGatherFinal);
			myBiomassImpl.addPowerInputActualFlowRates(i, currentWaterGathered);
			gatheredPower += currentWaterGathered;
		}
		powerLevel = gatheredPower;
	}
	
	private void flushWater(){
		waterLevel = 0f;
	}
	
	private void flushPower(){
		powerLevel = 0f;
	}
	
	public float takeWater(float pLiters){
		if (waterLevel < pLiters){
			float waterLeft = waterLevel;
			waterLevel = 0;
			return waterLeft;
		}
		else{
			waterLevel -= pLiters;
			return pLiters;
		}		
	}
	
	private void lightPlants(){
		myCrop.shine();
	}

	public void tick(){
		gatherWater();
		gatherPower();
		myCrop.tick();
		flushWater();
		flushPower();
	}

	public void replant(PlantType pType){
		if (pType == PlantType.WHEAT)
			myCrop = new Wheat(this);
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
