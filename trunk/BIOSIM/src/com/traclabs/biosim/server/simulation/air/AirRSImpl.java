package biosim.server.air;

import biosim.idl.air.*;
import biosim.idl.environment.*;
import biosim.idl.power.*;
import biosim.server.framework.*;
import biosim.server.util.*;

public class AirRSImpl extends BioModuleImpl implements AirRSOperations {
	private float powerNeeded = 130;
	private float CO2Needed = 0.2f;
	private boolean hasCollectedReferences = false;
	private boolean hasEnoughPower = false;
	private boolean hasEnoughCO2 = false;
	private float currentO2Produced = 0f;
	private float currentCO2Consumed = 0f;
	private float currentCO2Produced = 0f;
	private float currentPowerConsumed = 0f;
	private Breath airRetrieved;
	private SimEnvironment myEnvironment;
	private PowerStore myPowerStore;

	public float getPowerConsumed(){
		return currentPowerConsumed;
	}

	public float getCO2Consumed(){
		return currentCO2Consumed;
	}

	public float getO2Produced(){
		return currentO2Produced;
	}

	public float getCO2Produced(){
		return currentCO2Produced;
	}

	public boolean hasPower(){
		return hasEnoughPower;
	}

	public boolean hasCO2(){
		return hasEnoughCO2;
	}

	private void collectReferences(){
		try{
			if (!hasCollectedReferences){
				myPowerStore = PowerStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("PowerStore"));
				myEnvironment = SimEnvironmentHelper.narrow(OrbUtils.getNCRef().resolve_str("SimEnvironment"));
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

	private void gatherAir(){
		if (hasEnoughPower){
			airRetrieved = myEnvironment.takeCO2Breath(CO2Needed);
			currentCO2Consumed = airRetrieved.CO2;
			if (currentCO2Consumed < CO2Needed){
				hasEnoughCO2 = false;
			}
			else{
				hasEnoughCO2 = true;
			}
		}
		else{
			currentCO2Consumed = 0f;
		}
			
	}
	
	private float calculateO2Produced(){
		float theO2 = airRetrieved.O2 * 2.15f;
		return theO2;
	}
	
	private float calculateCO2Produced(){
		float theCO2 = airRetrieved.CO2 * 0.15f;
		return theCO2;
	}

	private void pushAir(){
		if (hasEnoughPower){
			currentO2Produced = calculateO2Produced();
			currentCO2Produced = calculateCO2Produced();
			myEnvironment.addO2(currentO2Produced);
			myEnvironment.addCO2(currentCO2Produced);
			myEnvironment.addOther(airRetrieved.other);
		}
		else{
			currentCO2Produced = 0f;
			currentO2Produced = 0f;
		}
	}

	private void consumeResources(){
		gatherPower();
		gatherAir();
	}

	public void tick(){
		collectReferences();
		consumeResources();
		pushAir();
	}

	public void reset(){
		currentO2Produced = 0.0f;
		currentCO2Consumed = 0.0f;
		currentPowerConsumed = 0.0f;
		currentCO2Produced = 0f;
	}

	public String getModuleName(){
		return "AirRS";
	}
}
