package biosim.server.air;

import biosim.idl.air.*;
import biosim.idl.environment.*;
import biosim.idl.power.*;
import biosim.idl.util.*;
import biosim.server.framework.*;
import biosim.server.util.*;

/**
 * The Air Revitalization System Implementation.  Takes in Air (O2, CO2, other) from the environment and
 * power from the Power Production System and produces air with less CO2 and more O2.
 *
 * @author    Scott Bell
 */

public class AirRSImpl extends BioModuleImpl implements AirRSOperations {
	//During any given tick, this much power (in watts) is needed for the AirRS to run at all.
	private float powerNeeded = 130;
	//During any given tick, this much CO2 (in liters) is needed for the AirRS to run optimally.
	private float CO2Needed = 0.2f;
	//Flag switched when the AirRS has collected references to other servers it needs
	private boolean hasCollectedReferences = false;
	//Flag to determine whether the AirRS has received enough power for this tick
	private boolean hasEnoughPower = false;
	//Flag to determine whether the AirRS has received enough CO2 to run at full
	private boolean hasEnoughCO2 = false;
	//The O2 produced (in liters) by the AirRS at this tick
	private float currentO2Produced = 0f;
	//The CO2 consumed (in liters) by the AirRS at this tick
	private float currentCO2Consumed = 0f;
	//The CO2 produced (in liters) by the AirRS at this tick
	private float currentCO2Produced = 0f;
	//The power consumed (in watts) by the AirRS at this tick
	private float currentPowerConsumed = 0f;
	//The air consumed by the AirRS at this tick (includes O2, CO2, and other)
	private Breath airRetrieved;
	//References to the servers the AirRS takes/puts resources (like air, power, etc)
	private SimEnvironment myEnvironment;
	private PowerStore myPowerStore;
	
	/**
	* Returns the power consumption (in watts) of the AirRS at the current tick.
	* @return the power consumed (in watts) at the current tick
	*/
	public float getPowerConsumed(){
		return currentPowerConsumed;
	}
	
	/**
	* Returns the CO2 consumption (in liters) of the AirRS at the current tick.
	* @return the CO2 consumed at the current tick
	*/
	public float getCO2Consumed(){
		return currentCO2Consumed;
	}
	
	/**
	* Returns the O2 produced (in liters) of the AirRS at the current tick.
	* @return the O2 produced (in liters) at the current tick
	*/
	public float getO2Produced(){
		return currentO2Produced;
	}
	
	/**
	* Returns the CO2 produced (in liters) of the AirRS at the current tick.
	* @return the CO2 produced (in liters) at the current tick
	*/
	public float getCO2Produced(){
		return currentCO2Produced;
	}
	
	/**
	* Returns whether the AirRS has enough power at the current tick.
	* @return <code>true</code> if the AirRS has enough power to function, <code>false</code> if the AirRS doesn't
	*/
	public boolean hasPower(){
		return hasEnoughPower;
	}
	
	/**
	* Returns whether the AirRS has enough CO2 at the current tick.
	* @return <code>true</code> if the AirRS has enough power to optimally, <code>false</code> if the AirRS doesn't
	*/
	public boolean hasCO2(){
		return hasEnoughCO2;
	}
	
	/**
	* Collects references to servers needed for putting/getting resources.
	*/
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
	
	/**
	* Attempts to collect enough power from the Power PS to run the AirRS for one tick.
	*/
	private void gatherPower(){
		currentPowerConsumed = myPowerStore.take(powerNeeded);
		if (currentPowerConsumed < powerNeeded){
			hasEnoughPower = false;
		}
		else{
			hasEnoughPower = true;
		}
	}
	
	/**
	* If the Air RS has enough power, it attempts to collect enough air from the environment such that the CO2 requirement is filled.
	*/
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
	
	/**
	* Calculates how much O2 has been produced (in liters) during this tick.
	* @return the O2 produced (in liters) during this tick.
	*/
	private float calculateO2Produced(){
		float theO2 = airRetrieved.O2 * 2.15f;
		return theO2;
	}
	
	/**
	* Calculates how much CO2 has been produced (in liters) during this tick.
	* @return the CO2 produced (in liters) during this tick.
	*/
	private float calculateCO2Produced(){
		float theCO2 = airRetrieved.CO2 * 0.15f;
		return theCO2;
	}
	
	/**
	* If the Air RS has enough power, it scrubs the CO2, adds O2 and pushes it into the environment.
	*/
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
	
	/**
	* Consumes the resources required for one tick
	*/
	private void consumeResources(){
		gatherPower();
		gatherAir();
	}
	
	/**
	* Processes a tick by collecting referernces (if needed), resources, and pushing the new air out.
	*/
	public void tick(){
		collectReferences();
		consumeResources();
		pushAir();
	}
	
	/**
	* Resets production/consumption levels.
	*/
	public void reset(){
		currentO2Produced = 0.0f;
		currentCO2Consumed = 0.0f;
		currentPowerConsumed = 0.0f;
		currentCO2Produced = 0f;
	}
	
	/**
	* Returns the name of this module (AirRS)
	* @return the name of this module
	*/
	public String getModuleName(){
		return "AirRS";
	}
	
	public void log(){
	}
}
