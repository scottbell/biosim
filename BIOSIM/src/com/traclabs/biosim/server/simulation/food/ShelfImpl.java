package biosim.server.food;

import biosim.idl.food.*;
import biosim.idl.water.*;
import biosim.server.util.*;
import biosim.idl.power.*;
import java.util.*;
/**
 * Tray contains Plants
 * @author    Scott Bell
 */

public class ShelfImpl extends ShelfPOA {
	private Plant myCrop;
	private int cropCapacity = 10;
	private float currentPowerConsumed = 0f;
	private float currentPowerNeeded = 10f;
	private float totalArea = 8.24f;
	private float areaPerCrop = totalArea / cropCapacity;
	private float currentGreyWaterConsumed = 0f;
	private float currentPotableWaterConsumed = 0f;
	private boolean hasCollectedReferences = false;
	private boolean hasEnoughWater = false;
	private boolean hasEnoughPower = false;
	private PotableWaterStore myPotableWaterStore;
	private GreyWaterStore myGreyWaterStore;
	private PowerStore myPowerStore;
	
	public ShelfImpl(){
		myCrop = new Wheat(areaPerCrop, cropCapacity);
	}
	
	public ShelfImpl(float pTotalArea, int pCropCapacity){
		cropCapacity = pCropCapacity;
		totalArea = pCropCapacity;
		areaPerCrop = totalArea / cropCapacity;
		myCrop = new Wheat(areaPerCrop, cropCapacity);
	}
	
	/**
	* Collects references to subsystems needed for putting/getting resources
	*/
	private void collectReferences(){
		if (!hasCollectedReferences){
			try{
				myPotableWaterStore = PotableWaterStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("PotableWaterStore"));
				myGreyWaterStore = GreyWaterStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("GreyWaterStore"));
				myPowerStore = PowerStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("PowerStore"));
				hasCollectedReferences = true;
			}
			catch (org.omg.CORBA.UserException e){
				e.printStackTrace(System.out);
			}
		}
	}
	
	private void waterPlants(){
		myCrop.addWater(currentGreyWaterConsumed + currentPotableWaterConsumed);
	}
	
	/**
	* Adds power for this tick
	*/
	private void gatherPower(){
		currentPowerConsumed = myPowerStore.take(currentPowerNeeded);
		if (currentPowerConsumed < currentPowerNeeded){
			hasEnoughPower = false;
		}
		else{
			hasEnoughPower = true;
		}
	}
	
	/**
	* Adds power for this tick
	*/
	private void gatherWater(){
		currentGreyWaterConsumed = myGreyWaterStore.take(myCrop.getWaterNeeded());
		if (currentGreyWaterConsumed < myCrop.getWaterNeeded()){
			hasEnoughWater = false;
		}
		else{
			hasEnoughWater = true;
		}
	}
	
	public void reset(){
		 cropCapacity = 10;
		 currentPowerConsumed = 0f;
		 currentPowerNeeded = 10f;
		 currentGreyWaterConsumed = 0f;
		 currentPotableWaterConsumed = 0f;
		 hasCollectedReferences = false;
		 hasEnoughWater = false;
		 hasEnoughPower = false;
		 myCrop.reset();
	}
	
	public void tick(){
		collectReferences();
		gatherPower();
		if (hasEnoughPower){
			gatherWater();
			waterPlants();
		}
		myCrop.tick();
	}
}
