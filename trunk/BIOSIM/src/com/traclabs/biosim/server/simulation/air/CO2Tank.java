package biosim.server.air;

import biosim.idl.util.*;
import biosim.idl.environment.*;
import biosim.idl.power.*;
/**
 * CO2Tank Subsystem
 *
 * @author    Scott Bell
 */

public class CO2Tank extends AirRSSubSystem{
	//The level of whatever this store is holding
	private float level = 0.0f;
	//The capacity of what this store can hold
	private float capacity = 0.0f;

	public CO2Tank(AirRSImpl pAirRSImpl){
		super(pAirRSImpl);
		hasEnoughPower = true;
		level = 0.0f;
		capacity = 10.0f;
	}
	
	public CO2Tank(AirRSImpl pAirRSImpl, float initialLevel, float  initialCapacity){
		super(pAirRSImpl);
		hasEnoughPower = true;
		level = initialLevel;
		capacity = initialCapacity;
	}
	
	/**
	* Sets the capacity of the store (how much it can hold)
	* @param metricAmount the new volume of the store
	*/
	public void setCapacity(float metricAmount){
		capacity = metricAmount;
	}
	
	/**
	* Sets the level to a set amount
	* @param the level to set the store to
	*/
	public void setLevel(float metricAmount){
		level = metricAmount;
	}
	
	/**
	* Attempts to add to the store.  If the level is near capacity, it will only up to capacity
	* @param amountRequested the amount wanted to add to the store
	* @return the amount actually added to the store
	*/
	public float addCO2(float amountRequested){
		float acutallyAdded = 0f;
		if ((amountRequested + level) > capacity){
			//adding more than capacity
			acutallyAdded = (capacity - level);
			level += acutallyAdded;
			return  acutallyAdded;
		}
		else{
			acutallyAdded = amountRequested;
			level += amountRequested;
			return acutallyAdded;
		}
	}
	
	/**
	* Attemps to take the amount requested from the store
	* @param amountRequested the amount wanted
	* @return the amount actually retrieved
	*/
	public float takeCO2(float amountRequested){
		//idiot check
		if (amountRequested < 0){
			return 0f;
		}
		float takenAmount;
		//asking for more stuff than exists
		if (amountRequested > level){
			takenAmount = level;
			level = 0;
		}
		//stuff exists for request
		else{
			takenAmount = amountRequested;
			level -= amountRequested; 
		}
		return takenAmount;
	}
	
	/**
	* Retrieves the level of the store
	* @return the level of the store
	*/
	public float getLevel(){
		return level;
	}
	
	/**
	* Retrieves the capacity of the store
	* @return the capacity of the store
	*/
	public float getCapacity(){
		return capacity;
	}
	
	/**
	* Resets the level to 0
	*/
	public void reset(){
		level = 0.0f;
		currentPowerConsumed = 0;
		hasEnoughPower = true;
	}
	
	public void tick(){
	}

}
