package biosim.server.food;

import biosim.idl.food.*;
import biosim.idl.water.*;
import biosim.server.util.*;
import biosim.idl.power.*;
import biosim.idl.util.log.*;
import java.util.*;
/**
 * Tray contains Plants
 * @author    Scott Bell
 */

public class ShelfImpl extends ShelfPOA {
	private Plant myCrop;
	private float currentPowerConsumed = 0f;
	private float totalArea = 8.24f;
	private float currentGreyWaterConsumed = 0f;
	private float currentPotableWaterConsumed = 0f;
	private boolean hasCollectedReferences = false;
	private boolean hasEnoughWater = false;
	private boolean hasEnoughPower = false;
	private LogIndex myLogIndex;
	private boolean logInitialized = false;
	private int myID = 0;
	private BiomassRSImpl myBiomassImpl;
	
	public ShelfImpl(int pID, BiomassRSImpl pBiomassImpl){
		myBiomassImpl = pBiomassImpl;
		myCrop = new Wheat(myID, totalArea, pBiomassImpl);
		myID = pID;
	}
	
	public ShelfImpl(int pID, float pTotalArea, BiomassRSImpl pBiomassImpl){
		myBiomassImpl = pBiomassImpl;
		totalArea = pTotalArea;
		myCrop = new Wheat(myID, totalArea, pBiomassImpl);
		myID = pID;
	}
	
	public boolean hasWater(){
		return myCrop.hasWater();
	}
	
	public boolean hasLight(){
		return myCrop.hasLight();
	}
	
	public boolean isDead(){
		return myCrop.isDead();
	}
	
	public boolean hasCO2(){
		return myCrop.hasCO2();
	}
	
	public float getArea(){
		return totalArea;
	}
	
	public float getCO2Consumed(){
		return myCrop.getCO2Consumed();
	}
	
	public float getBiomassProduced(){
		return myCrop.getBiomassProduced();
	}
	
	public float getO2Produced(){
		return myCrop.getO2Produced();
	}
	
	public float getPowerConsumed(){
		return currentPowerConsumed;
	}
	
	public float getGreyWaterConsumed(){
		return currentGreyWaterConsumed;
	}
	
	public float getPotableWaterConsumed(){
		return currentPotableWaterConsumed;
	}
	
	private void waterPlants(){
		myCrop.addWater(currentGreyWaterConsumed + currentPotableWaterConsumed);
	}
	
	private void lightPlants(){
		myCrop.lightPlants(currentPowerConsumed);
	}
	
	/**
	* Adds power for this tick
	*/
	private void collectPower(){
		float gatheredPower = 0f;
		PowerStore[] myPowerStores = myBiomassImpl.getPowerInputs();
		for (int i = 0; (i < myPowerStores.length) || (gatheredPower >= myCrop.getPowerNeeded()); i++){
			gatheredPower += myPowerStores[i].take(myCrop.getPowerNeeded());
		}
		currentPowerConsumed = gatheredPower;
		if (currentPowerConsumed < myCrop.getPowerNeeded()){
			hasEnoughPower = false;
		}
		else{
			hasEnoughPower = true;
		}
	}
	
	public String getPlantType(){
		return myCrop.getPlantType();
	}
	
	/**
	* Adds power for this tick
	*/
	private void collectWater(){
		float gatheredWater = 0f;
		GreyWaterStore[] myGreyWaterStores = myBiomassImpl.getGreyWaterInputs();
		PotableWaterStore[] myPotableWaterStores = myBiomassImpl.getPotableWaterInputs();
		for (int i = 0; (i < myGreyWaterStores.length) || (gatheredWater >= myCrop.getWaterNeeded()); i++){
			gatheredWater += myGreyWaterStores[i].take(myCrop.getWaterNeeded());
		}
		for (int i = 0; (i < myPotableWaterStores.length) || (gatheredWater >= myCrop.getWaterNeeded()); i++){
			gatheredWater += myPotableWaterStores[i].take(myCrop.getWaterNeeded());
		}
		currentGreyWaterConsumed = gatheredWater;
		if (currentGreyWaterConsumed < myCrop.getWaterNeeded()){
			hasEnoughWater = false;
		}
		else{
			hasEnoughWater = true;
		}
	}
	
	public void reset(){
		 currentPowerConsumed = 0f;
		 currentGreyWaterConsumed = 0f;
		 currentPotableWaterConsumed = 0f;
		 hasCollectedReferences = false;
		 hasEnoughWater = false;
		 hasEnoughPower = false;
		 myCrop.reset();
	}
	
	public void tick(){
		collectPower();
		if (hasEnoughPower){
			collectWater();
			lightPlants();
			waterPlants();
		}
		myCrop.tick();
	}
	
	public void setProductionRate(float pProductionRate){
		myCrop.setProductionRate(pProductionRate);
	}
	
	public void log(LogNode myLogHead){
		//If not initialized, fill in the log
		if (!logInitialized){
			myLogIndex = new LogIndex();
			myLogIndex.plantHead = myLogHead.addChild("plant");
			myCrop.log(myLogIndex.plantHead);
			LogNode currentPowerConsumedHead = myLogHead.addChild("power_consumed");
			myLogIndex.currentPowerConsumedIndex = currentPowerConsumedHead.addChild(""+currentPowerConsumed);
			LogNode totalAreaHead = myLogHead.addChild("total_area");
			myLogIndex.totalAreaIndex = totalAreaHead.addChild(""+totalArea);
			LogNode currentGreyWaterConsumedHead = myLogHead.addChild("grey_water_consumed");
			myLogIndex.currentGreyWaterConsumedIndex = currentGreyWaterConsumedHead.addChild(""+currentGreyWaterConsumed);
			LogNode PotableWaterConsumedHead = myLogHead.addChild("potable_water_consumed");
			myLogIndex.currentPotableWaterConsumedIndex = PotableWaterConsumedHead.addChild(""+currentPotableWaterConsumed);
			LogNode hasEnoughWaterHead = myLogHead.addChild("has_enough_water");
			myLogIndex.hasEnoughWaterIndex = hasEnoughWaterHead.addChild(""+hasEnoughWater);
			LogNode hasEnoughPowerHead = myLogHead.addChild("has_enough_power");
			myLogIndex.hasEnoughPowerIndex = hasEnoughPowerHead.addChild(""+hasEnoughPower);
			logInitialized = true; 
		}
		else{
			myCrop.log(myLogIndex.plantHead);
			myLogIndex.currentPowerConsumedIndex.setValue(""+currentPowerConsumed);
			myLogIndex.totalAreaIndex.setValue(""+totalArea);
			myLogIndex.currentGreyWaterConsumedIndex.setValue(""+currentGreyWaterConsumed);
			myLogIndex.currentPotableWaterConsumedIndex.setValue(""+currentPotableWaterConsumed);
			myLogIndex.hasEnoughWaterIndex.setValue(""+hasEnoughWater);
			myLogIndex.hasEnoughPowerIndex.setValue(""+hasEnoughPower);
		}
	}
	
	/**
	* For fast reference to the log tree
	*/
	private class LogIndex{
		public LogNode plantHead;
		public LogNode currentPowerConsumedIndex;
		public LogNode totalAreaIndex;
		public LogNode currentGreyWaterConsumedIndex;
		public LogNode currentPotableWaterConsumedIndex;
		public LogNode hasEnoughWaterIndex;
		public LogNode hasEnoughPowerIndex;
	}
}
