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
	private float leakRate = 0.0f;
	//The light intensity outside
	private float lightIntensity = 0f;
	//The number of ticks the simulation has gone through
	private int ticks;
	private static final float MAXIMUM_LUMENS = 50000f;
	private static final float STARTING_HOUR = 0f;
	private static final float DAY_LENGTH = 24f;
	private String myName;
	private LogIndex myLogIndex;

	/**
	* Creates a SimEnvironment (with a volume of 100000 liters) and resets the gas levels to correct percantages of sea level air
	*/
	public SimEnvironmentImpl(int pID, String pName){
		this(pID, 100000, pName);
	}

	/**
	* Creates a SimEnvironment with a set initial volume and resets the gas levels to correct percantages of sea level air.
	* @param initialVolume the initial volume of the environment in liters
	*/
	public SimEnvironmentImpl(int pID, float pInitialVolume, String pName){
		super(pID);
		myName = pName;
		volume = initialVolume = pInitialVolume;
		O2Pressure = cachedO2Pressure = initialO2Pressure = 2.0f;
		CO2Pressure = cachedCO2Pressure = initialCO2Pressure = 0.2f;
		otherPressure = cachedOtherPressure = initialOtherPressure = 7.7f;
		waterPressure = cachedWaterPressure = initialWaterPressure = 0.1f;
		O2Moles = cachedO2Moles = initialO2Moles = calculateMoles(O2Pressure);
		otherMoles = cachedOtherMoles = initialOtherMoles = calculateMoles(otherPressure);
		waterMoles = cachedWaterMoles = initialWaterMoles = calculateMoles(waterPressure);
		CO2Moles = cachedCO2Moles = initialCO2Moles = calculateMoles(CO2Pressure);
	}

	/**
	* Creates a SimEnvironment with a set initial volume and gas levels to correct percantages of sea level air
	* @param initialCO2Moles the initial volume of the CO2 (in moles) in the environment
	* @param initialO2Moles the initial volume of the O2 (in moles) in the environment
	* @param initialOtherMoles the initial volume of the other gasses (in moles) in the environment
	* @param initialVolume the initial volume of the environment in liters
	*/
	public SimEnvironmentImpl (float pInitialCO2Moles, float pInitialO2Moles, float pInitialOtherMoles, float pInitialWaterMoles,
	                           float pInitialVolume, String pName, int pID)
	{
		super(pID);
		myName = pName;
		CO2Moles = cachedCO2Moles = initialCO2Moles = pInitialCO2Moles;
		O2Moles = cachedO2Moles = initialO2Moles = pInitialO2Moles;
		otherMoles = cachedOtherMoles = initialOtherMoles = pInitialOtherMoles;
		waterMoles = cachedWaterMoles = initialWaterMoles = pInitialWaterMoles;
		volume = initialVolume = pInitialVolume;
		O2Pressure = cachedO2Pressure = initialO2Pressure = calculatePressure(O2Moles);
		CO2Pressure = cachedCO2Pressure = initialCO2Pressure = calculatePressure(CO2Moles);
		otherPressure = cachedOtherPressure = initialOtherPressure = calculatePressure(otherMoles);
		waterPressure = cachedWaterPressure = initialWaterPressure = calculatePressure(waterMoles);
	}

	private float calculatePressure(float pNumberOfMoles){
		if (volume > 0)
			return (pNumberOfMoles * idealGasConstant * (temperature + 273f)) / volume;
		else
			return 0;
	}

	private float calculateMoles(float pPressure){
		float kelvinTemperature = temperature + 273f;
		if (kelvinTemperature > 0)
			return (pPressure * volume) / (kelvinTemperature * idealGasConstant);
		else
			return 0;
	}

	/**
	* Resets the ticks to 0 and the gas levels to correct percantages of sea level air
	*/
	public void reset(){
		super.reset();
		ticks = 0;
		leakRate = 0.0f;
		lightIntensity = 0f;
		volume = initialVolume;
		O2Moles = cachedO2Moles = initialO2Moles;
		otherMoles = cachedOtherMoles = initialOtherMoles;
		CO2Moles = cachedCO2Moles = initialCO2Moles;
		waterMoles = cachedWaterMoles = initialWaterMoles;
		O2Pressure = cachedO2Pressure = initialO2Pressure;
		CO2Pressure = cachedCO2Pressure = initialCO2Pressure;
		otherPressure = cachedOtherPressure = initialOtherPressure;
		waterPressure = cachedWaterPressure = initialWaterPressure;
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

	//constant for right now (function of temperature);
	public float getWaterDensity(){
		return 998.23f;
	}

	public float getRelativeHumidity(){
		float kelvinTemperature = getTemperature() + 239f;
		if (kelvinTemperature <= 0)
			return 0f;
		float exponent = (17.4f * getTemperature()) / kelvinTemperature;
		float saturatedVaporPressure = .611f * exp(exponent);
		return getWaterPressure() / saturatedVaporPressure;
	}

	//returns temperature in celsius
	public float getTemperature(){
		return temperature;
	}

	private float exp(float a){
		return (new Double(Math.exp(a))).floatValue();
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
		CO2Moles = molesRequested;
		adjustPressure();
	}

	/**
	* Sets the O2 level in the air to a set amount
	* @param molesRequested the amount of O2 (in moles) to be in the air
	*/
	public void setO2Moles(float molesRequested){
		O2Moles = molesRequested;
		adjustPressure();
	}

	/**
	* Sets the water gasses level in the air to a set amount
	* @param molesRequested the amount of water gasses (in moles) to be in the air
	*/
	public void setWaterMoles(float molesRequested){
		waterMoles = molesRequested;
		adjustPressure();
	}

	/**
	* Sets the other gasses level in the air to a set amount
	* @param molesRequested the amount of other gasses (in moles) to be in the air
	*/
	public void setOtherMoles(float molesRequested){
		otherMoles = molesRequested;
		adjustPressure();
	}

	/**
	* Sets the CO2 level in the air to a set amount
	* @param pressureRequested the amount of CO2 (in kilo Pascals) to be in the air
	*/
	public void setCO2Pressure(float pressureRequested){
		CO2Pressure = pressureRequested;
		adjustMoles();
	}

	/**
	* Sets the O2 level in the air to a set amount
	* @param pressureRequested the amount of O2 (in kilo Pascals) to be in the air
	*/
	public void setO2Pressure(float pressureRequested){
		O2Pressure = pressureRequested;
		adjustMoles();
	}

	/**
	* Sets the water gasses level in the air to a set amount
	* @param pressureRequested the amount of water gasses (in kilo Pascals) to be in the air
	*/
	public void setWaterPressure(float pressureRequested){
		waterPressure = pressureRequested;
		adjustMoles();
	}

	/**
	* Sets the other gasses level in the air to a set amount
	* @param pressureRequested the amount of other gasses (in kilo Pascals) to be in the air
	*/
	public void setOtherPressure(float pressureRequested){
		otherPressure = pressureRequested;
		adjustMoles();
	}

	/**
	* Sets the volume of the environment (how much gas it can hold)
	* @param initialCO2Moles the initial volume of the CO2 (in moles) in the environment
	* @param initialO2Moles the initial volume of the O2 (in moles) in the environment
	* @param initialOtherMoles the initial volume of the other gasses (in moles) in the environment
	* @param initialVolume the initial volume of the environment in liters
	*/
	public void setInitialVolume(float pInitialCO2Moles, float pInitialO2Moles, float pInitialOtherMoles, float pInitialWaterMoles, float pInitialVolume)
	{
		CO2Moles = cachedCO2Moles = initialCO2Moles = pInitialCO2Moles;
		O2Moles = cachedO2Moles = initialO2Moles = pInitialO2Moles;
		otherMoles = cachedOtherMoles = initialOtherMoles = pInitialOtherMoles;
		waterMoles = cachedWaterMoles = initialWaterMoles = pInitialWaterMoles;
		volume = initialVolume = pInitialVolume;
		O2Pressure = cachedO2Pressure = initialO2Pressure = calculatePressure(O2Moles);
		CO2Pressure = cachedCO2Pressure = initialCO2Pressure = calculatePressure(CO2Moles);
		otherPressure = cachedOtherPressure = initialOtherPressure = calculatePressure(otherMoles);
		waterPressure = cachedWaterPressure = initialWaterPressure = calculatePressure(waterMoles);
	}

	/**
	* Sets the volume of the environment (how much gas it can hold) w/ gas mixture at earth sea level
	* @param litersRequested the new volume of the environment (in liters)
	*/
	public void setInitialVolumeAtSeaLevel(float pInitialVolume){
		volume = initialVolume = pInitialVolume;
		O2Pressure = cachedO2Pressure = initialO2Pressure = 2.0f;
		otherPressure = cachedOtherPressure = initialOtherPressure = 7.7f;
		CO2Pressure = cachedCO2Pressure = initialCO2Pressure = 0.2f;
		waterPressure = cachedWaterPressure = initialWaterPressure = 0.1f;
		O2Moles = cachedO2Moles = initialO2Moles = calculateMoles(O2Pressure);
		otherMoles = cachedOtherMoles = initialOtherMoles = calculateMoles(otherPressure);
		waterMoles = cachedWaterMoles = initialWaterMoles = calculateMoles(waterPressure);
		CO2Moles = cachedCO2Moles = initialCO2Moles = calculateMoles(CO2Pressure);
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
		CO2Moles = molesRequested;
		O2Moles = molesRequested;
		otherMoles = molesRequested;
		waterMoles = molesRequested;
		adjustPressure();
	}

	/**
	* Sets every gas level (O2, CO2, other) to one value
	* @param pressureRequested the amount (in pressure) to set all the gas levels to
	*/
	public void setTotalPressure(float pressureRequested){
		CO2Pressure = pressureRequested;
		O2Pressure = pressureRequested;
		otherPressure = pressureRequested;
		waterPressure = pressureRequested;
		adjustMoles();
	}

	private void adjustMoles(){
		CO2Moles = calculateMoles(CO2Pressure);
		O2Moles = calculateMoles(O2Pressure);
		otherMoles = calculateMoles(otherPressure);
		waterMoles = calculateMoles(waterPressure);
	}

	private void adjustPressure(){
		CO2Pressure = calculatePressure(CO2Moles);
		O2Pressure = calculatePressure(O2Moles);
		otherPressure = calculatePressure(otherMoles);
		waterPressure = calculatePressure(waterMoles);
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
	* Attempts to add CO2 gas to the environment.  If the total gas level is near volume, it will only up to volume
	* @param molesRequested the amount of CO2 gasses (in moles) wanted to add to the environment
	* @return the amount of CO2 gasses (in moles) actually added to the environment
	*/
	public float addCO2Moles(float molesRequested){
		float afterAdditionCO2 = 0f;
		float actuallyAddedCO2 = 0f;
		/*
		if ((molesRequested + getTotalMoles()) > volume){
			//adding more CO2 than volume
			afterAddition = randomFilter(CO2Moles + (volume - getTotalMoles()));
			//add moles
			actuallyAdded = afterAddition - CO2Moles;
			CO2Moles = afterAddition;
	}
		else{
		*/
		afterAdditionCO2 = randomFilter(CO2Moles + molesRequested);
		actuallyAddedCO2 = afterAdditionCO2 - CO2Moles;
		CO2Moles = afterAdditionCO2;

		return  actuallyAddedCO2;
	}

	/**
	* Attempts to add O2 gas to the environment.  If the total gas level is near volume, it will only up to volume
	* @param molesRequested the amount of O2 gasses (in moles) wanted to add to the environment
	* @return the amount of O2 gasses (in moles) actually added to the environment
	*/
	public float addO2Moles(float molesRequested){
		float afterAdditionO2 = 0f;
		float actuallyAddedO2 = 0f;
		/*if ((molesRequested + getTotalMoles()) > volume){
			//adding more O2 than volume
			afterAddition = randomFilter(O2Moles + (volume - getTotalMoles()));
			//add moles
			actuallyAdded = afterAddition - O2Moles;
			O2Moles = afterAddition;
	}
		else{
		*/
		afterAdditionO2 = randomFilter(O2Moles + molesRequested);
		actuallyAddedO2 = afterAdditionO2 - O2Moles;
		O2Moles = afterAdditionO2;

		return  actuallyAddedO2;
	}

	/**
	* Attempts to add other gas to the environment.  If the total gas level is near volume, it will only up to volume
	* @param molesRequested the amount of other gasses (in moles) wanted to add to the environment
	* @return the amount of other gasses (in moles) actually added to the environment
	*/
	public float addOtherMoles(float molesRequested){
		float afterAdditionOther = 0f;
		float actuallyAddedOther = 0f;
		/*
		if ((molesRequested + getTotalMoles()) > volume){
			//adding more other than volume
			afterAddition = randomFilter(otherMoles + (volume - getTotalMoles()));
			//add moles
			actuallyAdded = afterAddition - otherMoles;
			otherMoles = afterAddition;
	}
		else{
		*/
		afterAdditionOther = randomFilter(otherMoles + molesRequested);
		actuallyAddedOther = afterAdditionOther - otherMoles;
		otherMoles = afterAdditionOther;

		return  actuallyAddedOther;
	}

	/**
	* Attempts to add water gas to the environment.  If the total gas level is near volume, it will only up to volume
	* @param molesRequested the amount of water gasses (in moles) wanted to add to the environment
	* @return the amount of water gasses (in moles) actually added to the environment
	*/
	public float addWaterMoles(float molesRequested){
		float afterAdditionWater = 0f;
		float actuallyAddedWater = 0f;
		/*
		if ((molesRequested + getTotalMoles()) > volume){
			//adding more water than volume
			afterAddition = randomFilter(waterMoles + (volume - getTotalMoles()));
			//add moles
			actuallyAdded = afterAddition - waterMoles;
			waterMoles = afterAddition;
	}
		else{
		*/
		afterAdditionWater = randomFilter(waterMoles + molesRequested);
		actuallyAddedWater = afterAdditionWater - waterMoles;
		waterMoles = actuallyAddedWater;

		return  actuallyAddedWater;
	}

	public float takeCO2Moles(float amountRequested){
		//idiot check
		if (amountRequested < 0){
			return 0f;
		}
		float actuallyTaken;
		//asking for more stuff than exists
		if (amountRequested > CO2Moles){
			actuallyTaken = randomFilter(CO2Moles);
			CO2Moles = 0;
			CO2Pressure = 0;
		}
		//stuff exists for request
		else{
			actuallyTaken = randomFilter(amountRequested);
			//take moles
			CO2Moles -= actuallyTaken;
		}
		return actuallyTaken;
	}

	public float takeO2Moles(float amountRequested){
		//idiot check
		if (amountRequested < 0){
			return 0f;
		}
		float actuallyTaken;
		//asking for more stuff than exists
		if (amountRequested > O2Moles){
			actuallyTaken = randomFilter(O2Moles);
			O2Moles = 0;
			O2Pressure = 0;
		}
		//stuff exists for request
		else{
			actuallyTaken = O2Moles - randomFilter(amountRequested);
			//take moles
			O2Moles = actuallyTaken;
		}
		return actuallyTaken;
	}

	public float takeOtherMoles(float amountRequested){
		//idiot check
		if (amountRequested < 0){
			return 0f;
		}
		float actuallyTaken;
		//asking for more stuff than exists
		if (amountRequested > otherMoles){
			actuallyTaken = randomFilter(otherMoles);
			otherMoles = 0;
			otherPressure = 0;
		}
		//stuff exists for request
		else{
			actuallyTaken = otherMoles - randomFilter(amountRequested);
			//take moles
			otherMoles = actuallyTaken;
		}
		return actuallyTaken;
	}

	public float takeWaterMoles(float amountRequested){
		//idiot check
		if (amountRequested < 0){
			return 0f;
		}
		float actuallyTaken;
		//asking for more stuff than exists
		if (amountRequested > waterMoles){
			actuallyTaken = randomFilter(waterMoles);
			waterMoles = 0;
			waterPressure = 0;
		}
		//stuff exists for request
		else{
			actuallyTaken = waterMoles - randomFilter(amountRequested);
			//take moles
			waterMoles = actuallyTaken;
		}
		return actuallyTaken;
	}

	public Breath addBreath(Breath pBreath){
		return new Breath( addO2Moles(pBreath.O2), addCO2Moles(pBreath.CO2), addWaterMoles(pBreath.water), addOtherMoles(pBreath.other));
	}

	public Breath takeAirMoles(float molesRequested){
		//idiot check
		if (molesRequested <= 0){
			return new Breath(0f, 0f, 0f, 0f);
		}
		//asking for more gas than exists
		if (molesRequested >= getTotalMoles()){
			float afterRemovalCO2 = randomFilter(CO2Moles);
			float afterRemovalO2 = randomFilter(O2Moles);
			float afterRemovalOther = randomFilter(otherMoles);
			float afterRemovalWater = randomFilter(waterMoles);
			setTotalMoles(0);
			return new Breath(afterRemovalO2, afterRemovalCO2, afterRemovalWater, afterRemovalOther);
		}
		//gas exists for request
		else{
			float afterRemovalCO2 = randomFilter(CO2Moles - (CO2Moles / getTotalMoles()) * molesRequested);
			float afterRemovalO2 = randomFilter(O2Moles - (O2Moles / getTotalMoles()) * molesRequested);
			float afterRemovalOther = randomFilter(otherMoles - (otherMoles / getTotalMoles()) * molesRequested);
			float afterRemovalWater = randomFilter(waterMoles - (waterMoles / getTotalMoles()) * molesRequested);
			O2Moles = afterRemovalO2;
			CO2Moles = afterRemovalCO2;
			otherMoles = afterRemovalOther;
			waterMoles = afterRemovalWater;
			return new Breath(afterRemovalO2, afterRemovalCO2, afterRemovalWater, afterRemovalOther);
		}
	}

	protected void performMalfunctions(){
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
				float leakedO2Moles =  O2Moles - (O2Moles * leakRate);
				float leakedCO2Moles = CO2Moles - (CO2Moles * leakRate);
				float leakedOtherMoles = otherMoles - (otherMoles * leakRate);
				float leakedWaterMoles = waterMoles - (waterMoles * leakRate);
				waterPressure = waterPressure * leakedWaterMoles / waterMoles;
				O2Pressure = O2Pressure * leakedO2Moles / O2Moles;
				CO2Pressure = CO2Pressure * leakedCO2Moles / CO2Moles;
				otherPressure = otherPressure * leakedOtherMoles / otherMoles;

				O2Moles = leakedO2Moles;
				CO2Moles = leakedCO2Moles;
				otherMoles = leakedOtherMoles;
				waterMoles = leakedWaterMoles;
			}
			else if ((currentMalfunction.getLength() == MalfunctionLength.PERMANENT_MALF) && (!currentMalfunction.hasPerformed())){
				float O2percentage;
				float CO2percentage;
				float otherPercentage;
				float waterPercentage;
				if (volume <= 0){
					O2Moles = 0;
					CO2Moles = 0;
					otherMoles = 0;
					waterMoles = 0;
					O2Pressure = 0;
					CO2Pressure = 0;
					otherPressure = 0;
					waterPressure = 0;
					O2percentage = 0;
					CO2percentage = 0;
					otherPercentage = 0;
					currentMalfunction.setPerformed(true);
					return;
				}
				O2percentage = O2Moles / getTotalMoles();
				CO2percentage = CO2Moles / getTotalMoles();
				otherPercentage = otherMoles / getTotalMoles();
				waterPercentage = waterMoles / getTotalMoles();
				if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
					volume = 0f;
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
					volume *= 0.5;
				else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
					volume *= .25f;
				O2Moles = O2percentage * volume;
				CO2Moles = CO2percentage * volume;
				otherMoles = otherPercentage * volume;
				waterMoles = waterPercentage * volume;
				currentMalfunction.setPerformed(true);
			}
		}
	}

	private void calculateLightIntensity(){
		lightIntensity = new Double(MAXIMUM_LUMENS*(Math.sin(2*Math.PI*(ticks-STARTING_HOUR)/DAY_LENGTH) + 1)).floatValue();
	}

	/**
	* Processes a tick by adding to the tick counter
	*/
	public void tick(){
		super.tick();
		adjustPressure();
		cachedO2Moles = O2Moles;
		cachedCO2Moles = CO2Moles;
		cachedOtherMoles = otherMoles;
		cachedWaterMoles = waterMoles;
		cachedO2Pressure = O2Pressure;
		cachedCO2Pressure = CO2Pressure;
		cachedOtherPressure = otherPressure;
		cachedWaterPressure = waterPressure;
		calculateLightIntensity();
		//System.out.println(getModuleName()+": O2Moles: "+O2Moles);
		//System.out.println(getModuleName()+": CO2Moles: "+CO2Moles);
		//System.out.println(getModuleName()+": otherMoles: "+otherMoles);
		//System.out.println(getModuleName()+": waterMoles: "+waterMoles);
		//System.out.println(getModuleName()+": O2Pressure: "+O2Pressure);
		//System.out.println(getModuleName()+": CO2Pressure: "+CO2Pressure);
		//System.out.println(getModuleName()+": otherPressure: "+otherPressure);
		//System.out.println(getModuleName()+": waterPressure: "+waterPressure);
		ticks++;
	}

	/**
	* Returns the name of this module (SimEnvironment)
	* @return the name of this module
	*/
	public String getModuleName(){
		return myName+getID();
	}

	protected void log(){
		//If not initialized, fill in the log
		if (!logInitialized){
			myLogIndex = new LogIndex();
			LogNode O2MolesHead = myLog.addChild("O2_moles");
			myLogIndex.O2MolesIndex = O2MolesHead.addChild(""+O2Moles);
			LogNode CO2MolesHead = myLog.addChild("CO2_moles");
			myLogIndex.CO2MolesIndex = CO2MolesHead.addChild(""+CO2Moles);
			LogNode otherMolesHead = myLog.addChild("other_moles");
			myLogIndex.otherMolesIndex = otherMolesHead.addChild(""+otherMoles);
			LogNode waterMolesHead = myLog.addChild("water_moles");
			myLogIndex.waterMolesIndex = waterMolesHead.addChild(""+waterMoles);
			LogNode O2PressureHead = myLog.addChild("O2_pressure");
			myLogIndex.O2PressureIndex = O2PressureHead.addChild(""+O2Pressure);
			LogNode CO2PressureHead = myLog.addChild("CO2_pressure");
			myLogIndex.CO2PressureIndex = CO2PressureHead.addChild(""+CO2Pressure);
			LogNode otherPressureHead = myLog.addChild("other_pressure");
			myLogIndex.otherPressureIndex = otherPressureHead.addChild(""+otherPressure);
			LogNode waterPressureHead = myLog.addChild("water_pressure");
			myLogIndex.waterPressureIndex = waterPressureHead.addChild(""+waterPressure);
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
			myLogIndex.waterMolesIndex.setValue(""+waterMoles);
			myLogIndex.O2PressureIndex.setValue(""+O2Pressure);
			myLogIndex.CO2PressureIndex.setValue(""+CO2Pressure);
			myLogIndex.otherPressureIndex.setValue(""+otherPressure);
			myLogIndex.waterPressureIndex.setValue(""+waterPressure);
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
		public LogNode waterMolesIndex;
		public LogNode O2PressureIndex;
		public LogNode CO2PressureIndex;
		public LogNode otherPressureIndex;
		public LogNode waterPressureIndex;
		public LogNode volumeIndex;
		public LogNode lightIntensityIndex;
	}
}
