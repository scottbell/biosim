package biosim.server.food;

import biosim.idl.food.*;
import biosim.idl.power.*;
import biosim.server.util.*;
import biosim.server.framework.*;

public class FoodProcessorImpl extends BioModuleImpl implements FoodProcessorOperations {
	private float powerNeeded = 100;
	private float biomassNeeded = 0.2f;
	private boolean hasCollectedReferences = false;
	private boolean hasEnoughPower = false;
	private boolean hasEnoughBiomass = false;
	private float currentBiomassConsumed = 0f;
	private float currentPowerConsumed = 0f;
	private float currentFoodProduced = 0f;
	private FoodStore myFoodStore;
	private PowerStore myPowerStore;
	private BiomassStore myBiomassStore;
	
	public void reset(){
		 currentBiomassConsumed = 0f;
		 currentPowerConsumed = 0f;
		 currentFoodProduced = 0f;
	}

	public float getBiomassConsumed(){
		return currentBiomassConsumed;
	}

	public float getPowerConsumed(){
		return currentPowerConsumed;
	}

	public float getFoodProduced(){
		return currentFoodProduced;
	}
	
	public boolean hasPower(){
		return hasEnoughPower;
	}
	
	public boolean hasBiomass(){
		return hasEnoughBiomass;
	}
	
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
	
	private void gatherPower(){
		currentPowerConsumed = myPowerStore.take(powerNeeded);
		if (currentPowerConsumed < powerNeeded){
			hasEnoughPower = false;
		}
		else{
			hasEnoughPower = true;
		}
	}
	
	private void gatherBiomass(){
		currentBiomassConsumed = myBiomassStore.take(biomassNeeded);
		if (currentBiomassConsumed < biomassNeeded){
			hasEnoughBiomass = false;
		}
		else{
			hasEnoughBiomass = true;
		}
	}
	
	private void createFood(){
		if (hasEnoughPower){
			currentFoodProduced = currentBiomassConsumed * 0.8f;
			myFoodStore.add(currentFoodProduced);
		}
	}
	
	private void consumeResources(){
		gatherPower();
		gatherBiomass();
	}

	public void tick(){
		collectReferences();
		consumeResources();
		createFood();
	}
	
	public String getModuleName(){
		return "FoodProcessor";
	}
}
