package biosim.server.simulation.environment;

import biosim.idl.simulation.environment.*;
import biosim.idl.simulation.air.*;
import biosim.idl.util.log.*;
import biosim.idl.simulation.framework.*;
import biosim.idl.framework.*;
import biosim.idl.framework.*;
import biosim.server.simulation.framework.*;
import java.util.*;
/**
 * The SimEnvironment acts as the environment in which the crew breathes from and as the keeper of time.
 *
 * @author    Scott Bell
 */

public class SimEnvironmentImpl extends SimBioModuleImpl implements SimEnvironmentOperations {
	//The current amount of O2 in the environment (in moles)
	private float O2Moles = 0f;
	private float O2Pressure = 0f;
	//The current amount of CO2 in the environment (in moles)
	private float CO2Moles = 0f;
	private float CO2Pressure = 0f;
	//The current amount of other gasses in the environment (in moles)
	private float otherMoles = 0f;
	private float otherPressure = 0f;
	//The current amount of water gas in the environment (in moles)
	private float waterMoles = 0f;
	private float waterPressure = 0f;
	//cached levels
	private float cachedO2Moles = 0f;
	private float cachedCO2Moles = 0f;
	private float cachedOtherMoles = 0f;
	private float cachedWaterMoles = 0f;
	private float cachedO2Pressure = 0f;
	private float cachedCO2Pressure = 0f;
	private float cachedOtherPressure = 0f;
	private float cachedWaterPressure = 0f;
	private float initialO2Moles = 0f;
	private float initialCO2Moles = 0f;
	private float initialOtherMoles = 0f;
	private float initialWaterMoles = 0f;
	private float initialO2Pressure = 0f;
	private float initialCO2Pressure = 0f;
	private float initialOtherPressure = 0f;
	private float initialWaterPressure = 0f;
	private final float temperature = 23f;
	private final static float idealGasConstant = 8.314f; // J K ^-1 mol -1 (assumes units in moles, kevlin, and kPascals)
	//The total volume of the environment (all the open space)
	private float volume = 0f;
	private float initialVolume = 0f;
	//The number of ticks the simulation has gone through
	private int ticks;
	//The light intensity outside
	private float lightIntensity = 0f;
	private static final float MAXIMUM_LUMENS = 50000f;
	private static final float STARTING_HOUR = 0f;
	private static final float DAY_LENGTH = 24f;
	private float leakRate = 0.0f;
	private String myName;
	private LogIndex myLogIndex;

	/**
	* Creates a SimEnvironment (with a volume of 100 liters) and resets the gas levels to correct percantages of sea level air
	*/
	public SimEnvironmentImpl(int pID, String pName){
		this(pID, 100, pName);
	}

	/**
	* Creates a SimEnvironment with a set initial volume and resets the gas levels to correct percantages of sea level air.
	* @param initialVolume the initial volume of the environment in liters
	*/
	public SimEnvironmentImpl(int pID, float initialVolume, String pName){
		super(pID);
		myName = pName;
		volume = initialVolume;
		O2Moles = cachedO2Moles = initialO2Moles = volume * 0.20f;
		otherMoles = cachedOtherMoles = initialOtherMoles = volume * 0.77f;
		waterMoles = cachedWaterMoles = initialWaterMoles = volume * 0.02f;
		CO2Moles = cachedCO2Moles = initialCO2Moles = volume * 0.01f;
		O2Pressure = cachedO2Pressure = initialO2Pressure = calculatePressure(O2Moles);
		CO2Pressure = cachedCO2Pressure = initialCO2Pressure = calculatePressure(CO2Moles);
		otherPressure = cachedOtherPressure = initialOtherPressure = calculatePressure(otherMoles);
		waterPressure = cachedWaterPressure = initialWaterPressure = calculatePressure(waterMoles);
	}

	/**
	* Creates a SimEnvironment with a set initial volume and gas levels to correct percantages of sea level air
	* @param initialCO2Moles the initial volume of the CO2 (in moles) in the environment
	* @param initialO2Moles the initial volume of the O2 (in moles) in the environment
	* @param initialOtherMoles the initial volume of the other gasses (in moles) in the environment
	* @param initialVolume the initial volume of the environment in liters
	*/
	public SimEnvironmentImpl (float pInitialCO2Moles, float pInitialO2Moles, float pInitialOtherMoles, float pInitialWaterMoles,
				float pInitialCO2Pressure, float pInitialO2Pressure, float pInitialOtherPressure, float pInitialWaterPressure,
				float pInitialVolume, String pName, int pID)
	{
		super(pID);
		myName = pName;
		CO2Moles = cachedCO2Moles = initialCO2Moles = pInitialCO2Moles;
		O2Moles = cachedO2Moles = initialO2Moles = pInitialO2Moles;
		otherMoles = cachedOtherMoles = initialOtherMoles = pInitialOtherMoles;
		waterMoles = cachedWaterMoles = initialWaterMoles = pInitialWaterMoles;
		volume = initialVolume = pInitialVolume;
		O2Pressure = cachedO2Pressure = initialO2Pressure = calculatePressure(pInitialO2Pressure);
		CO2Pressure = cachedCO2Pressure = initialCO2Pressure = calculatePressure(pInitialCO2Pressure);
		otherPressure = cachedOtherPressure = initialOtherPressure = calculatePressure(pInitialOtherPressure);
		waterPressure = cachedWaterPressure = initialWaterPressure = calculatePressure(pInitialWaterPressure);
	}
	
	private float calculatePressure(float pNumberOfMoles){
		return (pNumberOfMoles * idealGasConstant * (temperature + 273f)) / volume;
	}

	/**
	* Resets the ticks to 0 and the gas levels to correct percantages of sea level air
	*/
	public void reset(){
		ticks = 0;
		volume = initialVolume;
		resetGasses();
	}
	
	private void resetGasses(){
		O2Moles = cachedO2Moles = initialO2Moles;
		otherMoles = cachedOtherMoles = initialOtherMoles;
		CO2Moles = cachedCO2Moles = initialCO2Moles;
		O2Pressure = cachedO2Pressure = initialO2Pressure = calculatePressure(O2Moles);
		CO2Pressure = cachedCO2Pressure = initialCO2Pressure = calculatePressure(CO2Moles);
		otherPressure = cachedOtherPressure = initialOtherPressure = calculatePressure(otherMoles);
	}

	/**
	* Gives the light intensity outside
	* @return the light intensity in lumens
	*/
	public float getLightIntensity(){
		return lightIntensity;
	}

	public float getAirPressure(){
		return cachedCO2Pressure + cachedO2Pressure + cachedWaterPressure + cachedOtherPressure;
	}
	
	//returns temperature in celsius
	public float getTemperature(){
		return temperature;
	}

	protected String getMalfunctionName(MalfunctionIntensity pIntensity, MalfunctionLength pLength){
		StringBuffer returnBuffer = new StringBuffer();
		if (pIntensity == MalfunctionIntensity.SEVERE_MALF)
			returnBuffer.append("Severe ");
		else if (pIntensity == MalfunctionIntensity.MEDIUM_MALF)
			returnBuffer.append("Medium ");
		else if (pIntensity == MalfunctionIntensity.LOW_MALF)
			returnBuffer.append("Low ");
		if (pLength == MalfunctionLength.TEMPORARY_MALF)
			returnBuffer.append("Leak");
		else if (pLength == MalfunctionLength.PERMANENT_MALF)
			returnBuffer.append("Volume Reduction");
		return returnBuffer.toString();
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
	* @param molesRequested the amount of CO2 (in moles) to be in the air
	*/
	public void setCO2Moles(float molesRequested){
		CO2Pressure = CO2Pressure * molesRequested / CO2Moles;
		CO2Moles = molesRequested;
	}

	/**
	* Sets the O2 level in the air to a set amount
	* @param molesRequested the amount of O2 (in moles) to be in the air
	*/
	public void setO2Moles(float molesRequested){
		O2Pressure = O2Pressure * molesRequested / O2Moles;
		O2Moles = molesRequested;
	}
	
	/**
	* Sets the water gasses level in the air to a set amount
	* @param molesRequested the amount of water gasses (in moles) to be in the air
	*/
	public void setWaterMoles(float molesRequested){
		waterPressure = waterPressure * molesRequested / waterMoles;
		waterMoles = molesRequested;
	}

	/**
	* Sets the other gasses level in the air to a set amount
	* @param molesRequested the amount of other gasses (in moles) to be in the air
	*/
	public void setOtherMoles(float molesRequested){
		otherPressure = otherPressure * molesRequested / otherMoles;
		otherMoles = molesRequested;
	}
	
	/**
	* Sets the CO2 level in the air to a set amount
	* @param pressureRequested the amount of CO2 (in kilo Pascals) to be in the air
	*/
	public void setCO2Pressure(float pressureRequested){
		CO2Moles = pressureRequested * CO2Moles / CO2Pressure;
		CO2Pressure = pressureRequested;
	}

	/**
	* Sets the O2 level in the air to a set amount
	* @param pressureRequested the amount of O2 (in kilo Pascals) to be in the air
	*/
	public void setO2Pressure(float pressureRequested){
		O2Moles = pressureRequested * O2Moles / O2Pressure;
		O2Pressure = pressureRequested;
	}
	
	/**
	* Sets the water gasses level in the air to a set amount
	* @param pressureRequested the amount of water gasses (in kilo Pascals) to be in the air
	*/
	public void setWaterPressure(float pressureRequested){
		waterMoles = pressureRequested * waterMoles / waterPressure;
		waterPressure = pressureRequested;
	}

	/**
	* Sets the other gasses level in the air to a set amount
	* @param pressureRequested the amount of other gasses (in kilo Pascals) to be in the air
	*/
	public void setOtherPressure(float pressureRequested){
		otherMoles = pressureRequested * otherMoles / otherPressure;
		otherPressure = pressureRequested;
	}

	/**
	* Sets the volume of the environment (how much gas it can hold)
	* @param litersRequested the new volume of the environment (in liters)
	*/
	public void setVolume(float litersRequested){
		super.reset();
		volume = litersRequested;
		resetGasses();
	}

	/**
	* Retrieves the the total level of gas in the environment (in moles)
	* @return retrieves the the total level of gas in the environment (in moles)
	*/
	public float getTotalMoles(){
		return CO2Moles + O2Moles + waterMoles + otherMoles;
	}
	
	/**
	* Retrieves the the total level of gas in the environment (in kPA)
	* @return retrieves the the total level of gas in the environment (in kPA)
	*/
	public float getTotalPressure(){
		return CO2Pressure + O2Pressure + waterPressure + otherPressure;
	}

	/**
	* Sets every gas level (O2, CO2, other) to one value
	* @param molesRequested the amount (in moles) to set all the gas levels to
	*/
	public void setTotalMoles(float molesRequested){
		O2Pressure = O2Pressure * molesRequested / O2Moles;
		CO2Pressure = CO2Pressure * molesRequested / CO2Moles;
		otherPressure = otherPressure * molesRequested / otherMoles;
		waterPressure = waterPressure * molesRequested / waterMoles;
		
		CO2Moles = molesRequested;
		O2Moles = molesRequested;
		otherMoles = molesRequested;
		waterMoles = molesRequested;
	}
	
	/**
	* Sets every gas level (O2, CO2, other) to one value
	* @param pressureRequested the amount (in pressure) to set all the gas levels to
	*/
	public void setTotalPressure(float pressureRequested){
		O2Moles = O2Moles * pressureRequested / O2Pressure;
		CO2Moles = CO2Moles * pressureRequested / CO2Pressure;
		otherMoles = otherMoles * pressureRequested / otherPressure;
		waterMoles = waterMoles * pressureRequested / waterPressure;
		
		CO2Pressure = pressureRequested;
		O2Pressure = pressureRequested;
		otherPressure = pressureRequested;
		waterPressure = pressureRequested;
	}

	/**
	* Retrieves the other gasses level (in moles)
	* @return the other gasses level (in moles)
	*/
	public float getOtherMoles(){
		return cachedOtherMoles;
	}

	/**
	* Retrieves the O2 level (in moles)
	* @return the O2 level (in moles)
	*/
	public float getO2Moles(){
		return cachedO2Moles;
	}

	/**
	* Retrieves the CO2 level (in moles)
	* @return the CO2 level (in moles)
	*/
	public float getCO2Moles(){
		return cachedCO2Moles;
	}
	
	/**
	* Retrieves the water gas level (in moles)
	* @return the water gas level (in moles)
	*/
	public float getWaterMoles(){
		return cachedWaterMoles;
	}
	
	/**
	* Retrieves the other gasses level (in kPA)
	* @return the other gasses level (in kPA)
	*/
	public float getOtherPressure(){
		return cachedOtherPressure;
	}
	
	/**
	* Retrieves the water gasses level (in kPA)
	* @return the water gasses level (in kPA)
	*/
	public float getWaterPressure(){
		return cachedWaterPressure;
	}

	/**
	* Retrieves the O2 level (in kPA)
	* @return the O2 level (in kPA)
	*/
	public float getO2Pressure(){
		return cachedO2Pressure;
	}

	/**
	* Retrieves the CO2 level (in kPA)
	* @return the CO2 level (in kPA)
	*/
	public float getCO2Pressure(){
		return cachedCO2Pressure;
	}

	/**
	* Attempts to add CO2 to the environment.  If the total gas level is near volume, it will only up to volume
	* @param molesRequested the amount of CO2 (in moles) wanted to add to the environment
	* @return the amount of CO2 (in moles) actually added to the environment
	*/
	public float addCO2(float molesRequested){
		float acutallyAdded = 0f;
		if ((molesRequested + getTotalMoles()) > volume){
			//adding more CO2 than volume
			acutallyAdded = randomFilter(volume - getTotalMoles());
			CO2Moles += acutallyAdded;
			return acutallyAdded;
		}
		else{
			acutallyAdded = randomFilter(molesRequested);
			CO2Moles = CO2Moles + molesRequested;
			return acutallyAdded;
		}
	}

	/**
	* Attempts to add O2 to the environment.  If the total gas level is near volume, it will only up to volume
	* @param molesRequested the amount of O2 (in moles) wanted to add to the environment
	* @return the amount of O2 (in moles) actually added to the environment
	*/
	public float addO2(float molesRequested){
		float acutallyAdded = 0f;
		if ((molesRequested + getTotalMoles()) > volume){
			//adding more O2 than volume
			acutallyAdded = randomFilter(volume - getTotalMoles());
			O2Moles += acutallyAdded;
			return  acutallyAdded;
		}
		else{
			acutallyAdded = randomFilter(molesRequested);
			O2Moles = O2Moles + molesRequested;
			return acutallyAdded;
		}
	}

	/**
	* Attempts to add other gasses to the environment.  If the total gas level is near volume, it will only up to volume
	* @param molesRequested the amount of other gasses (in moles) wanted to add to the environment
	* @return the amount of other gasses (in moles) actually added to the environment
	*/
	public float addOther(float molesRequested){
		float acutallyAdded = 0f;
		if ((molesRequested + getTotalMoles()) > volume){
			//adding more Other than volume
			acutallyAdded = randomFilter(volume - getTotalMoles());
			otherMoles += acutallyAdded;
			return  acutallyAdded;
		}
		else{
			acutallyAdded = randomFilter(molesRequested);
			otherMoles = otherMoles + molesRequested;
			return acutallyAdded;
		}
	}

	public float takeO2(float amountRequested){
		//idiot check
		if (amountRequested < 0){
			return 0f;
		}
		float takenAmount;
		//asking for more stuff than exists
		if (amountRequested > O2Moles){
			takenAmount = randomFilter(O2Moles);
			O2Moles = 0;
		}
		//stuff exists for request
		else{
			takenAmount = randomFilter(amountRequested);
			O2Moles -= takenAmount;
		}
		return takenAmount;
	}

	public float takeCO2(float amountRequested){
		//idiot check
		if (amountRequested < 0){
			return 0f;
		}
		float takenAmount;
		//asking for more stuff than exists
		if (amountRequested > CO2Moles){
			takenAmount = randomFilter(CO2Moles);
			CO2Moles = 0;
		}
		//stuff exists for request
		else{
			takenAmount = randomFilter(amountRequested);
			CO2Moles -= takenAmount;
		}
		return takenAmount;
	}

	public float takeOther(float amountRequested){
		//idiot check
		if (amountRequested < 0){
			return 0f;
		}
		float takenAmount;
		//asking for more stuff than exists
		if (amountRequested > otherMoles){
			takenAmount = randomFilter(otherMoles);
			otherMoles = 0;
		}
		//stuff exists for request
		else{
			takenAmount = randomFilter(amountRequested);
			otherMoles -= takenAmount;
		}
		return takenAmount;
	}

	public Breath addBreath(Breath pBreath){
		return new Breath( addO2(pBreath.O2), addCO2(pBreath.CO2), addOther(pBreath.other));
	}

	/**
	* Attemps to return a breath of air given a needed amount of O2 (in moles)
	* @param molesO2Requested the amount of O2 (in moles) wanted in this breath
	* @return the breath actually retrieved
	*/
	public Breath takeO2Breath(float molesO2Requested){
		//idiot check
		if (molesO2Requested <= 0){
			return new Breath(0,0,0);
		}
		//asking for more gas than exists
		if (molesO2Requested >= O2Moles){
			float takenCO2 = randomFilter(CO2Moles);
			float takenO2 = randomFilter(O2Moles);
			float takenOther = randomFilter(otherMoles);
			setTotalMoles(0);
			return new Breath(takenO2, takenCO2, takenOther);
		}
		//gas exists for request
		else{
			float percentageOfTotalGas = molesO2Requested / O2Moles;
			float takenCO2 = randomFilter((CO2Moles * percentageOfTotalGas));
			float takenO2 = randomFilter(molesO2Requested);
			float takenOther = randomFilter((otherMoles * percentageOfTotalGas));
			O2Moles -= takenO2;
			CO2Moles -= takenCO2;
			otherMoles -= takenOther;
			return new Breath(takenO2, takenCO2, takenOther);
		}
	}

	public Breath takeVolume(float molesRequested){
		//idiot check
		if (molesRequested <= 0){
			return new Breath(0,0,0);
		}
		//asking for more gas than exists
		if (molesRequested >= volume){
			float takenCO2 = randomFilter(CO2Moles);
			float takenO2 = randomFilter(O2Moles);
			float takenOther = randomFilter(otherMoles);
			setTotalMoles(0);
			return new Breath(takenO2, takenCO2, takenOther);
		}
		//gas exists for request
		else{
			float takenCO2 = randomFilter((CO2Moles / volume) * molesRequested);
			float takenO2 = randomFilter((O2Moles / volume) * molesRequested);
			float takenOther = randomFilter((otherMoles / volume) * molesRequested);
			O2Moles -= takenO2;
			CO2Moles -= takenCO2;
			otherMoles -= takenOther;
			return new Breath(takenO2, takenCO2, takenOther);
		}

	}

	private void performMalfunctions(){
		for (Iterator iter = myMalfunctions.values().iterator(); iter.hasNext(); ){
			Malfunction currentMalfunction = (Malfunction)(iter.next());
			if (currentMalfunction.getLength() == MalfunctionLength.TEMPORARY_MALF){
				float leakRate = 0f;
				if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
					leakRate = .20f;
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
					leakRate = .10f;
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
					leakRate = .05f;
				O2Moles -= (O2Moles * leakRate);
				CO2Moles -= (CO2Moles * leakRate);
				otherMoles -= (otherMoles * leakRate);
			}
			else if ((currentMalfunction.getLength() == MalfunctionLength.PERMANENT_MALF) && (!currentMalfunction.hasPerformed())){
				float O2percentage;
				float CO2percentage;
				float otherPercentage;
				if (volume <= 0){
					O2Moles = 0;
					CO2Moles = 0;
					otherMoles = 0;
					O2percentage = 0;
					CO2percentage = 0;
					otherPercentage = 0;
					currentMalfunction.setPerformed(true);
					return;
				}
				O2percentage = O2Moles / volume;
				CO2percentage = CO2Moles / volume;
				otherPercentage = otherMoles / volume;
				if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
					volume = 0f;
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
					volume *= 0.5;
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
					volume *= .25f;
				O2Moles = O2percentage * volume;
				CO2Moles = CO2percentage * volume;
				otherMoles = otherPercentage * volume;
				currentMalfunction.setPerformed(true);
			}
		}
	}

	/**
	* Attemps to return a breath of air given a needed amount of CO2 (in moles)
	* @param molesCO2Requested the amount of CO2 (in moles) wanted in this breath
	* @return the breath actually retrieved
	*/
	public Breath takeCO2Breath(float molesCO2Requested){
		//idiot check
		if (molesCO2Requested <= 0){
			return new Breath(0,0,0);
		}
		//asking for more gas than exists
		if (molesCO2Requested >= CO2Moles){
			float takenCO2 = randomFilter(CO2Moles);
			float takenO2 = randomFilter(O2Moles);
			float takenOther = randomFilter(otherMoles);
			setTotalMoles(0);
			return new Breath(takenO2, takenCO2, takenOther);
		}
		//gas exists for request
		else{
			float percentageOfTotalGas = molesCO2Requested / CO2Moles;
			float takenO2 = randomFilter(O2Moles * percentageOfTotalGas);
			float takenCO2 = randomFilter(molesCO2Requested);
			float takenOther = randomFilter(otherMoles * percentageOfTotalGas);
			O2Moles -= takenO2;
			CO2Moles -= takenCO2;
			otherMoles -= takenOther;
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
		cachedO2Moles = O2Moles;
		cachedCO2Moles = CO2Moles;
		cachedOtherMoles = otherMoles;
		calculateLightIntensity();
		if (moduleLogging)
			log();
		ticks++;
		if (isMalfunctioning())
			performMalfunctions();
	}

	/**
	* Returns the name of this module (SimEnvironment)
	* @return the name of this module
	*/
	public String getModuleName(){
		return myName+getID();
	}

	private void log(){
		//If not initialized, fill in the log
		if (!logInitialized){
			myLogIndex = new LogIndex();
			LogNode O2MolesHead = myLog.addChild("O2_level");
			myLogIndex.O2MolesIndex = O2MolesHead.addChild(""+O2Moles);
			LogNode CO2MolesHead = myLog.addChild("CO2_level");
			myLogIndex.CO2MolesIndex = CO2MolesHead.addChild(""+CO2Moles);
			LogNode otherMolesHead = myLog.addChild("other_level");
			myLogIndex.otherMolesIndex = otherMolesHead.addChild(""+otherMoles);
			LogNode volumeHead = myLog.addChild("volume");
			myLogIndex.volumeIndex = volumeHead.addChild(""+volume);
			LogNode lightIntensityHead = myLog.addChild("light_intensity");
			myLogIndex.lightIntensityIndex = lightIntensityHead.addChild(""+lightIntensity);
			logInitialized = true;
		}
		else{
			myLogIndex.O2MolesIndex.setValue(""+O2Moles);
			myLogIndex.CO2MolesIndex.setValue(""+CO2Moles);
			myLogIndex.otherMolesIndex.setValue(""+otherMoles);
			myLogIndex.volumeIndex.setValue(""+volume);
			myLogIndex.lightIntensityIndex.setValue(""+lightIntensity);
		}
		sendLog(myLog);
	}

	/**
	* For fast reference to the log tree
	*/
	private class LogIndex{
		public LogNode O2MolesIndex;
		public LogNode CO2MolesIndex;
		public LogNode otherMolesIndex;
		public LogNode volumeIndex;
		public LogNode lightIntensityIndex;
	}
}
