package biosim.server.food;

import biosim.idl.food.*;
import biosim.idl.environment.*;
import biosim.idl.water.*;
import biosim.idl.power.*;
import biosim.server.util.*;
import biosim.idl.air.*;
import biosim.server.framework.*;

public class BiomassRSImpl extends BioModuleImpl implements BiomassRSOperations {
	private float currentPowerConsumed = 0f;
	private float currentGreyWaterConsumed = 0f;
	private float currentBiomassProduced = 0f;
	private float currentCO2Consumed= 0f;
	private float currentO2Produced = 0f;
	private boolean hasCollectedReferences = false;
	private boolean systemHasEnoughPower = false;
	private boolean plantsHaveEnoughWater = false;
	private boolean plantsHaveEnoughCO2 = false;
	private SimEnvironment myEnvironment;
	private GreyWaterStore myGreyWaterStore;
	private PowerStore myPowerStore;
	private String status = "off";
	private boolean plantsDead = false;

	public float getPowerConsumed(){
		return currentPowerConsumed;
	}

	public float getCO2Consumed(){
		return currentCO2Consumed;
	}
	
	public float getO2Produced(){
		return currentO2Produced;
	}

	public float getGreyWaterConsumed(){
		return currentGreyWaterConsumed;
	}

	public float getBiomassProduced(){
		return currentBiomassProduced;
	}

	public String getStatus(){
		return status;
	}

	private void collectReferences(){
		try{
			if (!hasCollectedReferences){
				myPowerStore = PowerStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("PowerStore"));
				myGreyWaterStore = GreyWaterStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("GreyWaterStore"));
				myEnvironment = SimEnvironmentHelper.narrow(OrbUtils.getNCRef().resolve_str("SimEnvironment"));
				hasCollectedReferences = true;
			}
		}
		catch (org.omg.CORBA.UserException e){
			e.printStackTrace(System.out);
		}
	}

	private void gatherPower(){
		float powerNeeded = 100;
		currentPowerConsumed = myPowerStore.take(powerNeeded);
		if (currentPowerConsumed < powerNeeded){
			systemHasEnoughPower = false;
		}
		else
			systemHasEnoughPower = true;

	}

	private void gatherWater(){
		float waterNeeded = 2;
		currentGreyWaterConsumed = myGreyWaterStore.take(waterNeeded);
		if (currentGreyWaterConsumed < waterNeeded){
			plantsHaveEnoughWater = false;
		}
		else
			plantsHaveEnoughWater = true;
	}

	private float getCO2Ratio(Breath aBreath){
		Double ratio = new Double(aBreath.CO2 / (aBreath.O2 + aBreath.CO2 + aBreath.other));
		return ratio.floatValue();
	}
	
	private float calculateCO2Needed(){
		Double result = new Double(.02);
		return result.floatValue();
	}
	
	private float calculateO2Produced(float CO2Consumed){
		Double result = new Double(CO2Consumed * 0.86);
		return result.floatValue();
	}

	private void gatherAir(){
		float CO2Needed = calculateCO2Needed();
		Breath airRetrieved = myEnvironment.takeCO2Breath(CO2Needed);
		currentCO2Consumed = airRetrieved.CO2;
		if (getCO2Ratio(airRetrieved) < .02){
			plantsHaveEnoughCO2 = false;
		}
		else{
			plantsHaveEnoughCO2 = true;
		}
		currentO2Produced = calculateO2Produced(currentCO2Consumed);
		myEnvironment.addO2(currentO2Produced);
		myEnvironment.addOther(airRetrieved.other);
	}

	public boolean isDead(){
		return plantsDead;
	}

	public boolean hasCO2(){
		return plantsHaveEnoughCO2;
	}

	public boolean hasWater(){
		return plantsHaveEnoughWater;
	}

	public boolean hasPower(){
		return systemHasEnoughPower;
	}

	private void consumeResources(){
		//gather power for each system
		gatherPower();
		//gather water for system
		gatherWater();
		//gather air for system
		gatherAir();
	}

	public void tick(){
		if (plantsDead)
			return;
		else{
			//collect references
			collectReferences();
			consumeResources();
		}
	}

	public String getModuleName(){
		return "BiomassRS";
	}
}
