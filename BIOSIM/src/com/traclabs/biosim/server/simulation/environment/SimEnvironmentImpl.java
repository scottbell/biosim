package biosim.server.environment;

import biosim.idl.environment.*;
import biosim.idl.air.*;
import biosim.idl.util.log.*;
import biosim.server.framework.*;
/**
 * The SimEnvironment acts as the environment in which the crew breathes from and as the keeper of time.
 *
 * @author    Scott Bell
 */

public class SimEnvironmentImpl extends BioModuleImpl implements SimEnvironmentOperations {
	//The current amount of O2 in the environment (in liters)
	private float O2Level;
	//The current amount of CO2 in the environment (in liters)
	private float CO2Level;
	//The current amount of other gasses in the environment (in liters) 
	private float otherLevel;
	//The total capacity of the environment (all the open space)
	private float capacity = 100f;
	//The number of ticks the simulation has gone through
	private int ticks;
	//The light intensity outside
	private float lightIntensity = 0f;
	private static final float MAXIMUM_LUMENS = 50000f;
	private static final float STARTING_HOUR = 0f;
	private static final float DAY_LENGTH = 24f;
	private LogIndex myLogIndex;
	
	/**
	* Creates a SimEnvironment (with a capacity of 100 liters) and resets the gas levels to correct percantages of sea level air
	*/
	public SimEnvironmentImpl(int pID){
		super(pID);
		reset();
	}
	
	/**
	* Creates a SimEnvironment with a set initial capacity and resets the gas levels to correct percantages of sea level air
	* @param initialCapacity the initial capacity of the environment in liters
	*/
	public SimEnvironmentImpl(int pID, float initialCapacity){
		super(pID);
		capacity = initialCapacity;
		reset();
	}
	
	/**
	* Creates a SimEnvironment with a set initial capacity and gas levels to correct percantages of sea level air
	* @param initialCO2Level the initial capacity of the CO2 (in liters) in the environment
	* @param initialO2Level the initial capacity of the O2 (in liters) in the environment
	* @param initialOtherLevel the initial capacity of the other gasses (in liters) in the environment
	* @param initialCapacity the initial capacity of the environment in liters
	*/
	public SimEnvironmentImpl (int pID, float initialCO2Level, float initialO2Level, float initialOtherLevel, float initialCapacity){
		super(pID);
		CO2Level = initialCO2Level;
		O2Level = initialO2Level;
		otherLevel = initialOtherLevel;
		capacity = initialCapacity;
	}
	
	/**
	* Resets the ticks to 0 and the gas levels to correct percantages of sea level air
	*/
	public void reset(){
		ticks = 0;
		resetLevels();
	}
	
	/**
	* Gives the light intensity outside
	* @return the light intensity in lumens
	*/
	public float getLightIntensity(){
		return lightIntensity;
	}
	
	/**
	* Resets the gas levels to correct percantages of sea level air
	*/
	private void resetLevels(){
		O2Level = (new Double(capacity * 0.21)).floatValue();
		otherLevel = (new Double(capacity * 0.786)).floatValue();
		CO2Level = (new Double(capacity * 0.004)).floatValue();
	}
	
	/**
	* Gets the number of ticks that have occured during this simulation run
	* @return the number of ticks that have occured during this simulation run 
	*/
	public int getTicks(){
		return ticks;
	}
	
	/**
	* Sets the CO2 level in the air to a set amount
	* @param litersRequested the amount of CO2 (in liters) to be in the air
	*/
	public void setCO2Level(float litersRequested){
		CO2Level = litersRequested;
	}
	
	/**
	* Sets the O2 level in the air to a set amount
	* @param litersRequested the amount of O2 (in liters) to be in the air
	*/
	public void setO2Level(float litersRequested){
		O2Level = litersRequested;
	}
	
	/**
	* Sets the other gasses level in the air to a set amount
	* @param litersRequested the amount of other gasses (in liters) to be in the air
	*/
	public void setOtherLevel(float litersRequested){
		otherLevel = litersRequested;
	}
	
	/**
	* Sets the capacity of the environment (how much gas it can hold)
	* @param litersRequested the new volume of the environment (in liters)
	*/
	public void setCapacity(float litersRequested){
		capacity = litersRequested;
		resetLevels();
	}
	
	/**
	* Retrieves the the total level of gas in the environment (in liters)
	* @return retrieves the the total level of gas in the environment (in liters)
	*/
	public float getTotalLevel(){
		return CO2Level + O2Level + otherLevel;
	}
	
	/**
	* Sets every gas level (O2, CO2, other) to one value
	* @param litersRequested the amount (in liters) to set all the gas levels to
	*/
	public void setTotalLevel(float litersRequested){
		CO2Level = litersRequested;
		O2Level = litersRequested;
		otherLevel = litersRequested;
	}
	
	/**
	* Retrieves the other gasses level (in liters)
	* @return the other gasses level (in liters)
	*/
	public float getOtherLevel(){
		return otherLevel;
	}
	
	/**
	* Retrieves the O2 level (in liters)
	* @return the O2 level (in liters)
	*/
	public float getO2Level(){
		return O2Level;
	}
	
	/**
	* Retrieves the CO2 level (in liters)
	* @return the CO2 level (in liters)
	*/
	public float getCO2Level(){
		return CO2Level;
	}
	
	/**
	* Attempts to add CO2 to the environment.  If the total gas level is near capacity, it will only up to capacity
	* @param litersRequested the amount of CO2 (in liters) wanted to add to the environment
	* @return the amount of CO2 (in liters) actually added to the environment
	*/
	public float addCO2(float litersRequested){
		float acutallyAdded = 0f;
		if ((litersRequested + getTotalLevel()) > capacity){
			//adding more CO2 than capacity
			acutallyAdded = (capacity - getTotalLevel());
			CO2Level += acutallyAdded;
			return  acutallyAdded;
		}
		else{
			acutallyAdded = litersRequested;
			CO2Level = CO2Level + litersRequested;
			return acutallyAdded;
		}
	}
	
	/**
	* Attempts to add O2 to the environment.  If the total gas level is near capacity, it will only up to capacity
	* @param litersRequested the amount of O2 (in liters) wanted to add to the environment
	* @return the amount of O2 (in liters) actually added to the environment
	*/
	public float addO2(float litersRequested){
		float acutallyAdded = 0f;
		if ((litersRequested + getTotalLevel()) > capacity){
			//adding more O2 than capacity
			acutallyAdded = (capacity - getTotalLevel());
			O2Level += acutallyAdded;
			return  acutallyAdded;
		}
		else{
			acutallyAdded = litersRequested;
			O2Level = O2Level + litersRequested;
			return acutallyAdded;
		}
	}
	
	/**
	* Attempts to add other gasses to the environment.  If the total gas level is near capacity, it will only up to capacity
	* @param litersRequested the amount of other gasses (in liters) wanted to add to the environment
	* @return the amount of other gasses (in liters) actually added to the environment
	*/
	public float addOther(float litersRequested){
		float acutallyAdded = 0f;
		if ((litersRequested + getTotalLevel()) > capacity){
			//adding more Other than capacity
			acutallyAdded = (capacity - getTotalLevel());
			otherLevel += acutallyAdded;
			return  acutallyAdded;
		}
		else{
			acutallyAdded = litersRequested;
			otherLevel = otherLevel + litersRequested;
			return acutallyAdded;
		}
	}
	
	/**
	* Attemps to return a breath of air given a needed amount of O2 (in liters)
	* @param litersO2Requested the amount of O2 (in liters) wanted in this breath
	* @return the breath actually retrieved
	*/
	public Breath takeO2Breath(float litersO2Requested){
		//idiot check
		if (litersO2Requested < 0){
			return new Breath(0,0,0);
		}
		//asking for more gas than exists
		if (litersO2Requested > O2Level){
			float takenCO2 = CO2Level;
			float takenO2 = O2Level;
			float takenOther = otherLevel;
			setTotalLevel(0);
			return new Breath(takenO2, takenCO2, takenOther);
		}
		//gas exists for request
		else{
			float percentageOfTotalGas = litersO2Requested / O2Level;
			float takenCO2 = (CO2Level * percentageOfTotalGas);
			float takenO2 = litersO2Requested;
			float takenOther = (otherLevel * percentageOfTotalGas);
			O2Level -= takenO2;
			CO2Level -= takenCO2;
			otherLevel -= takenOther;
			return new Breath(takenO2, takenCO2, takenOther);
		}
	}
	
	/**
	* Attemps to return a breath of air given a needed amount of CO2 (in liters)
	* @param litersCO2Requested the amount of CO2 (in liters) wanted in this breath
	* @return the breath actually retrieved
	*/
	public Breath takeCO2Breath(float litersCO2Requested){
		//idiot check
		if (litersCO2Requested < 0){
			return new Breath(0,0,0);
		}
		//asking for more gas than exists
		if (litersCO2Requested > CO2Level){
			float takenCO2 = CO2Level;
			float takenO2 = O2Level;
			float takenOther = otherLevel;
			setTotalLevel(0);
			return new Breath(takenO2, takenCO2, takenOther);
		}
		//gas exists for request
		else{
			float percentageOfTotalGas = litersCO2Requested / CO2Level;
			float takenO2 = (O2Level * percentageOfTotalGas);
			float takenCO2 = litersCO2Requested;
			float takenOther = (otherLevel * percentageOfTotalGas);
			O2Level -= takenO2;
			CO2Level -= takenCO2;
			otherLevel -= takenOther;
			return new Breath(takenO2, takenCO2, takenOther);
		}
	}
	
	private void calculateLightIntensity(){
		lightIntensity = new Double(MAXIMUM_LUMENS*(Math.sin(2*Math.PI*(ticks-STARTING_HOUR)/DAY_LENGTH) + 1)).floatValue();
	}
	
	/**
	* Processes a tick by adding to the tick counter
	*/
	public void tick(){
		calculateLightIntensity();
		if (moduleLogging)
			log();
		ticks++;
	}
	
	/**
	* Returns the name of this module (SimEnvironment)
	* @return the name of this module
	*/
	public String getModuleName(){
		return "SimEnvironment"+getID();
	}
	
	private void log(){
		//If not initialized, fill in the log
		if (!logInitialized){
			myLogIndex = new LogIndex();
			LogNode O2LevelHead = myLog.addChild("O2_level");
			myLogIndex.O2LevelIndex = O2LevelHead.addChild(""+O2Level);
			LogNode CO2LevelHead = myLog.addChild("CO2_level");
			myLogIndex.CO2LevelIndex = CO2LevelHead.addChild(""+CO2Level);
			LogNode otherLevelHead = myLog.addChild("other_level");
			myLogIndex.otherLevelIndex = otherLevelHead.addChild(""+otherLevel);
			LogNode capacityHead = myLog.addChild("capacity");
			myLogIndex.capacityIndex = capacityHead.addChild(""+capacity);
			LogNode lightIntensityHead = myLog.addChild("light_intensity");
			myLogIndex.lightIntensityIndex = lightIntensityHead.addChild(""+lightIntensity);
			logInitialized = true;
		}
		else{
			myLogIndex.O2LevelIndex.setValue(""+O2Level);
			myLogIndex.CO2LevelIndex.setValue(""+CO2Level);
			myLogIndex.otherLevelIndex.setValue(""+otherLevel);
			myLogIndex.capacityIndex.setValue(""+capacity);
			myLogIndex.lightIntensityIndex.setValue(""+lightIntensity);
		}
		sendLog(myLog);
	}
	
	/**
	* For fast reference to the log tree
	*/
	private class LogIndex{
		public LogNode O2LevelIndex;
		public LogNode CO2LevelIndex;
		public LogNode otherLevelIndex;
		public LogNode capacityIndex;
		public LogNode lightIntensityIndex;
	}
}
