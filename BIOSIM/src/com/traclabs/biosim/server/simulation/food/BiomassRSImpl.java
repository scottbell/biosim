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
	private boolean hasCollectedReferences = false;
	private boolean hasEnoughPower = false;
	private boolean hasEnoughWater = false;
	private boolean hasEnoughCO2 = false;
	private SimEnvironment myEnvironment;
	private GreyWaterStore myGreyWaterStore;
	private PowerStore myPowerStore;
	private String status = "off";
	private boolean isDead = false;
	
	public float getPowerConsumed(){
		return currentPowerConsumed;
	}
	
	public float getCO2Consumed(){
		return currentCO2Consumed;
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
			hasEnoughPower = false;
		}
		else
			hasEnoughPower = true;
			
	}
	
	private void gatherWater(){
		float waterNeeded = 2;
		currentGreyWaterConsumed = myGreyWaterStore.take(waterNeeded);
		if (currentGreyWaterConsumed < waterNeeded){
			hasEnoughWater = false;
		}
		else
			hasEnoughWater = true;		
	}
	
	private float getCO2Ratio(Breath aBreath){
		Double ratio = new Double(aBreath.CO2 / (aBreath.O2 + aBreath.CO2 + aBreath.other));
		return ratio.floatValue();
	}
	
	private void gatherAir(){
		float CO2Needed = 5f;
		Breath airRetrieved = myEnvironment.takeO2Breath(CO2Needed);
		currentCO2Consumed = airRetrieved.CO2;
		if (getCO2Ratio(airRetrieved) < .02){
			hasEnoughCO2 = false;
		}
		else{
			hasEnoughCO2 = true;
		}
	}
	
	private void checkStatus(){
		if (isDead)
			status = "plants dead";
		else if (!hasEnoughCO2)
			status = "not enough CO2";
		else if (!hasEnoughWater)
			status = "not enough water";
		else if (!hasEnoughPower)
			status = "not enough power";
	}

	public void tick(){
		//collect references
		collectReferences();
		//gather power for each system
		gatherPower();
		//gather water for system
		gatherWater();
		//gather air for system
		gatherAir();
		//check status for plants
		checkStatus();
	}
	
	public String getModuleName(){
		return "BiomassRS";
	}
}
