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
	private float cropArea;
	private LogIndex myLogIndex;
	private boolean logInitialized = false;
	private BiomassRSImpl myBiomassRSImpl;
	private float waterLevel = 0f;
	private static final float waterNeededPerMeterSquared = 2f; //grab up to 2 liters per meters squared of crops per hour(WAG)
	private float waterNeeded = 0f;;
	private float powerLevel = 0f;
	

	public ShelfImpl(PlantType pType, float pCropArea, BiomassRSImpl pBiomassImpl){
		cropArea = pCropArea;
		myBiomassRSImpl = pBiomassImpl;
		waterNeeded = cropArea * waterNeededPerMeterSquared;
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

	public BiomassRSImpl getBiomassRSImpl(){
		return myBiomassRSImpl;
	}

	public float getCropArea(){
		return cropArea;
	}

	private float calculatePowerNeeded(){
		return myCrop.getPPFNeeded() / (getLampEfficiency() * getPSEfficiency());
	}

	private void gatherWater(){
		float gatheredGreyWater = myBiomassRSImpl.getFractionalResourceFromStore(myBiomassRSImpl.getGreyWaterInputs(), myBiomassRSImpl.getGreyWaterInputMaxFlowRates(), myBiomassRSImpl.getGreyWaterInputDesiredFlowRates(), myBiomassRSImpl.getGreyWaterInputActualFlowRates(), waterNeeded, 1f / myBiomassRSImpl.getNumberOfShelves());
		float gatheredPotableWater = myBiomassRSImpl.getFractionalResourceFromStore(myBiomassRSImpl.getPotableWaterInputs(), myBiomassRSImpl.getPotableWaterInputMaxFlowRates(), myBiomassRSImpl.getPotableWaterInputDesiredFlowRates(), myBiomassRSImpl.getPotableWaterInputActualFlowRates(), waterNeeded - gatheredGreyWater, 1f / myBiomassRSImpl.getNumberOfShelves());
		waterLevel += gatheredGreyWater + gatheredPotableWater;
	}

	private void gatherPower(){
		float powerNeeded = calculatePowerNeeded();
		powerLevel += myBiomassRSImpl.getFractionalResourceFromStore(myBiomassRSImpl.getPowerInputs(), myBiomassRSImpl.getPowerInputMaxFlowRates(), myBiomassRSImpl.getPowerInputDesiredFlowRates(), myBiomassRSImpl.getPowerInputActualFlowRates(), powerNeeded, myBiomassRSImpl.getNumberOfShelves());
	}

	private void flushWater(){
		waterLevel = 0f;
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
		////System.out.println("ShelfImpl: powerLevel: "+powerLevel);
		////System.out.println("ShelfImpl: getLampEfficiency: "+getLampEfficiency());
		////System.out.println("ShelfImpl: getPSEfficiency: "+getPSEfficiency());
		if (powerLevel <= 0)
			powerLevel = pow(1f, -30f);
		float thePPF = powerLevel * getLampEfficiency() * getPSEfficiency();
		////System.out.println("ShelfImpl: thePPF: "+thePPF);
		myCrop.shine(thePPF);
	}

	private float getLampEfficiency(){
		return 261f; //for high pressure sodium bulbs
	}

	private float getPSEfficiency(){
		return 4.68f; //for high pressure sodium bulbs
	}

	public void harvest(){
		myCrop.harvest();
	}

	public void tick(){
		gatherPower();
		gatherWater();
		lightPlants();
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
