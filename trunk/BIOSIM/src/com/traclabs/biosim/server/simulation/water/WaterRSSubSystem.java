/**
 * The abstract class all the water subsystems derive from (the AES, BWP, PPS, and RO).
 *
 * @author    Scott Bell
 */
 
package biosim.server.water;

public abstract class WaterRSSubSystem{
	//The power consumed (in watts) by this water subsystem at this tick (default)
	protected float currentPower = 0;
	//During any given tick, this much power (in watts) is needed for a water subsystem (default)
	protected float powerNeeded =100;
	//During any given tick, this much water (in liters) is needed for a water subsystem (default)
	protected float waterNeeded = 0.25f;
	//Reference to the WaterRS to get other watersubsystems
	protected WaterRSImpl myWaterRS;
	//Flag to determine whether the water subsystem has received enough power for this tick
	protected boolean hasEnoughPower = false;
	//Flag to determine whether the water subsystem has received enough water for this tick
	protected boolean hasEnoughWater = false;
	//Amount of water in this subsystem at the current tick
	protected float waterLevel = 0;
	
	/**
	* Constructor that creates the subsystem
	* @param pWaterRSImpl The Water RS system this subsystem is contained in
	*/
	public WaterRSSubSystem(WaterRSImpl pWaterRSImpl){
		myWaterRS = pWaterRSImpl;
	}
	
	/**
	* Resets power consumption and water levels.
	*/
	public void reset(){
		currentPower = 0;
		hasEnoughPower = false;
		hasEnoughWater = false;
		waterLevel = 0;
	}
	
	/**
	* Returns whether the water subsytem has enough power at the current tick.
	* @return <code>true</code> if the water subsytem has enough power to function, <code>false</code> if not
	*/
	public boolean hasPower(){
		return hasEnoughPower;
	}
	
	/**
	* Returns whether the water subsytem has enough water at the current tick.
	* @return <code>true</code> if the water subsytem has enough water to function, <code>false</code> if not
	*/
	public boolean hasWater(){
		return hasEnoughWater;
	}
	
	/**
	* Returns the power needed (in watts) for this susbsytem
	* @return the power needed (in watts) for this subsystem
	*/
	public float getPowerNeeded(){
		return powerNeeded;
	}
	
	/**
	* Adds power to the subsystem for this tick
	* @param pPower the amount of power to add (in watts)
	*/
	public void addPower(float pPower){
		currentPower = pPower;
		if (currentPower < powerNeeded){
			hasEnoughPower = false;
		}
		else{
			hasEnoughPower = true;
		}
	}
	
	/**
	* Adds water to the subsystem for this tick
	* @param pWater the amount of water to add (in liters)
	*/
	public void addWater(float pWater){
		waterLevel = pWater;
		if (waterLevel < waterNeeded){
			hasEnoughWater = false;
		}
		else{
			hasEnoughWater = true;
		}
	}
	
	/**
	* Tick needs to be implemented by the subsystem
	*/
	public abstract void tick();
}
