package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.server.framework.Malfunction;
import com.traclabs.biosim.server.framework.MalfunctionIntensity;
import com.traclabs.biosim.server.framework.MalfunctionLength;
import com.traclabs.biosim.server.simulation.framework.PassiveModule;

import ch.qos.logback.classic.Level;

import java.util.Iterator;

/**
 * The SimEnvironment acts as the environment in which the crew breathes from
 * and as the keeper of time.
 * 
 * @author Scott Bell
 */

public class SimEnvironment extends PassiveModule {
	
	private EnvironmentO2Store myO2Store = new EnvironmentO2Store(this);
	
	private EnvironmentCO2Store myCO2Store = new EnvironmentCO2Store(this);
	
	private EnvironmentNitrogenStore myNitrogenStore = new EnvironmentNitrogenStore(this);
	
	private EnvironmentOtherStore myOtherStore = new EnvironmentOtherStore(this);
	
	private EnvironmentVaporStore myVaporStore = new EnvironmentVaporStore(this);
	
	private EnvironmentStore[] myEnvironmentStores = {myO2Store, myCO2Store, myNitrogenStore, myOtherStore, myVaporStore};
    
	private final static float temperature = 23f;
	
    private final static float idealGasConstant = 8.314f; // J K ^-1 mol -1

    //The total currentVolume of the environment (all the open space)
    private float currentVolume = 0f;

    private float initialVolume = 0f;

    private float permanentLeakRate = 0.0f;

    //The light intensity outside
    private float lightIntensity = 0f;

    private float maxLumens = 50000f;

    private float hourOfDayStart = 0f;

    private float dayLength = 24f;

    private float myAirLockVolume = 3.7f;

    private float myDangerousOxygenThreshold = 1f;

    /**
     * Creates a SimEnvironment (with a currentVolume of 100000 liters) and
     * sets the gas levels to correct percentages of sea level air
     */
    public SimEnvironment(int pID, String pName) {
        this(pID, 100000, pName);
    }
    
    public SimEnvironment(int pID, String pName, float pVolume, float pTotalPressure, float o2Percentage, float co2Percentage, float otherPercentage, float waterPercentage, float nitrogenPercentage){
    	super(pID, pName);
        float o2Moles = calculateMoles(o2Percentage, pTotalPressure, pVolume);
        float co2Moles = calculateMoles(co2Percentage, pTotalPressure, pVolume);
        float otherMoles = calculateMoles(otherPercentage, pTotalPressure, pVolume);
        float waterMoles = calculateMoles(waterPercentage, pTotalPressure, pVolume);
        float nitrogenMoles = calculateMoles(nitrogenPercentage, pTotalPressure, pVolume);
    	setInitialVolume(o2Moles, co2Moles, otherMoles, waterMoles, nitrogenMoles, pVolume);
    }

    /**
     * Creates a SimEnvironment with a set initial currentVolume and sets the
     * gas levels to correct percentages of sea level air.
     * 
     * @param pInitialVolume
     *            the initial currentVolume of the environment in liters
     * @param pID
     *            the ID of the server
     * @param pName
     *            the name of this environment
     */
    public SimEnvironment(int pID, float pInitialVolume, String pName) {
        super(pID, pName);
        setInitialVolumeAtSeaLevel(pInitialVolume);
        currentVolume = initialVolume = pInitialVolume;
    }

	public SimEnvironment(float O2Moles, float CO2Moles, float otherMoles, float waterMoles, float nitrogenMoles, float pVolume, String pName, int pID) {
    	super(pID, pName);
    	setInitialVolume(O2Moles, CO2Moles, otherMoles, waterMoles, nitrogenMoles, pVolume);
	}

    public SimEnvironment() {
    	this(0, 100000, "Unnamed Environment");
	}
    
    private float calculateMoles(float percentage, float totalPressure, float volume) {
    	float moles = (percentage * totalPressure * volume) / (idealGasConstant * getTemperatureInKelvin());
		return moles;
    }

    /**
     * Resets environment volume and gas levels and percentages
     * to initial values
     */
    public void reset() {
        super.reset();
        lightIntensity = 0f;
        currentVolume = initialVolume;
        for (EnvironmentStore store : myEnvironmentStores)
        	store.reset();
    }

    /**
     * Gives the light intensity outside
     * 
     * @return the light intensity in lumens
     */
    public float getLightIntensity() {
        return lightIntensity;
    }

    //constant for right now (function of temperature);
    public float getWaterDensity() {
        return 998.23f;
    }

    public float getRelativeHumidity() {
        float exponent = (17.4f * getTemperature()) / getTemperatureInKelvin();
        float saturatedVaporPressure = .611f * (float)(Math.exp(exponent));
        return myVaporStore.getPressure() / saturatedVaporPressure;
    }

    //returns temperature in celsius
    public float getTemperature() {
        return temperature;
    }
    
    //returns temperature in kelvin
    public float getTemperatureInKelvin() {
        float kelvinTemperature = getTemperature() + 273.15f;
        if (kelvinTemperature <= 0)
            return 0f;
        else
        	return kelvinTemperature;
    }

    protected String getMalfunctionName(MalfunctionIntensity pIntensity,
            MalfunctionLength pLength) {
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
     * Sets the volume of the environment (how much gas it can hold) w/ gas
     * mixture at earth sea level
     * 
     * @param pInitialVolume
     *            the new currentVolume of the environment (in liters)
     */
    public void setInitialVolumeAtSeaLevel(float pInitialVolume) {
      	float moleOfHumidAirPerLiter = 0.04115f;	//	assuming air temperature of 23C
    	currentVolume = initialVolume = pInitialVolume;
        myO2Store.setInitialLevel(currentVolume *  moleOfHumidAirPerLiter * 0.185f);
        myCO2Store.setInitialLevel(currentVolume *  moleOfHumidAirPerLiter * 0.117f/101f);
        myOtherStore.setInitialLevel(currentVolume *  moleOfHumidAirPerLiter * 0.0f);
        myVaporStore.setInitialLevel(currentVolume *  moleOfHumidAirPerLiter * 0.0218910f);
        myNitrogenStore.setInitialLevel(currentVolume *  moleOfHumidAirPerLiter * (1f - 0.185f - 0.117f/101f - 0.0f - 0.0218910f));
    }
    
    public void setCurrentVolumeAtSeaLevel(float pVolume) {
       	float moleOfHumidAirPerLiter = 0.04115f;	//	assuming air temperature of 23C
       	currentVolume = pVolume;
        myO2Store.setCurrentLevel(currentVolume *  moleOfHumidAirPerLiter * 0.185f);
        myCO2Store.setCurrentLevel(currentVolume *  moleOfHumidAirPerLiter * 0.117f/101f);
        myOtherStore.setCurrentLevel(currentVolume *  moleOfHumidAirPerLiter * 0.0f);
        myVaporStore.setCurrentLevel(currentVolume *  moleOfHumidAirPerLiter * 0.0218910f);
        myNitrogenStore.setCurrentLevel(currentVolume *  moleOfHumidAirPerLiter * (1f - 0.185f - 0.117f/101f - 0.0f - 0.0218910f));
    }

    public float getInitialTotalPressure() {
    	float initialTotalPressure = 0f;
        for (EnvironmentStore store : myEnvironmentStores)
        	initialTotalPressure += store.getInitialPressure();
        return initialTotalPressure;
    }

    public float getInitialVolume() {
        return initialVolume;
    }

    public float getCurrentVolume() {
        return currentVolume;
    }

    private void performLeak(float pLeakRate) {
        for (EnvironmentStore store : myEnvironmentStores)
        	store.performLeak(pLeakRate);
    }
    
    public void setLogLevel(Level pLevel){
    	super.setLogLevel(pLevel);
        for (EnvironmentStore store : myEnvironmentStores)
        	store.setLogLevel(pLevel);
    }

    protected void performMalfunctions() {
        float malfunctionLeakRate = 0f;
        for (Iterator iter = myMalfunctions.values().iterator(); iter.hasNext();) {
            Malfunction currentMalfunction = (Malfunction) (iter.next());
            if (currentMalfunction.getLength() == MalfunctionLength.TEMPORARY_MALF) {
                malfunctionLeakRate = 0f;
                if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
                    malfunctionLeakRate = .20f * getTickLength();
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
                    malfunctionLeakRate = .10f * getTickLength();
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
                    malfunctionLeakRate = .05f * getTickLength();
                performLeak(malfunctionLeakRate);
            } else if ((currentMalfunction.getLength() == MalfunctionLength.PERMANENT_MALF)
                    && (!currentMalfunction.hasPerformed())) {
                if (currentVolume <= 0) {
                    for (EnvironmentStore store : myEnvironmentStores)
                        store.setCurrentLevel(0);
                    currentMalfunction.setPerformed(true);
                    return;
                }
                float volumeScalingFactor = 1.0f;
                if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
                    volumeScalingFactor = 0f; // complete loss of pressurized volume
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
                    volumeScalingFactor = 0.25f; // 75% reduction
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
                    volumeScalingFactor = 0.50f; // 50% reduction
                currentVolume *= volumeScalingFactor;
                for (EnvironmentStore store : myEnvironmentStores)
                    store.setCurrentLevel(store.getCurrentLevel() * volumeScalingFactor);
                currentMalfunction.setPerformed(true);
            }
        }
    }

    private void calculateLightIntensity() {
        lightIntensity = (float) (maxLumens * Math.pow(Math.sin(Math.PI
                / dayLength * (getMyTicks() * getTickLength() - hourOfDayStart)), 2f));
    }

    /**
     * Processes a tick by adding to the tick counter
     */
    public void tick() {
        super.tick();
        for (EnvironmentStore store : myEnvironmentStores)
        	store.tick();
        performLeak(permanentLeakRate);
        calculateLightIntensity();
    }

    public void log() {
    	myLogger.debug(getModuleName()+ " levels");
        for (EnvironmentStore store : myEnvironmentStores)
        	store.log();
    }

    /**
     * @return Returns the permanentLeakRate.
     */
    public float getLeakRate() {
        return permanentLeakRate;
    }

    /**
     * @param pLeakRate
     *            The permanentLeakRate to set.
     */
    public void setLeakRate(float pLeakRate) {
        permanentLeakRate = pLeakRate;
    }

    /**
     * @return Returns the dayLength.
     */
    public float getDayLength() {
        return dayLength;
    }

    /**
     * @param dayLength
     *            The dayLength to set.
     */
    public void setDayLength(float dayLength) {
        this.dayLength = dayLength;
    }

    /**
     * @return Returns the hourOfDayStart.
     */
    public float getHourOfDayStart() {
        return hourOfDayStart;
    }

    /**
     * @param hourOfDayStart
     *            The hourOfDayStart to set.
     */
    public void setHourOfDayStart(float hourOfDayStart) {
        this.hourOfDayStart = hourOfDayStart;
    }

    /**
     * @return Returns the maxLumens.
     */
    public float getMaxLumens() {
        return maxLumens;
    }

    /**
     * @param maxLumens
     *            The maxLumens to set.
     */
    public void setMaxLumens(float maxLumens) {
        this.maxLumens = maxLumens;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.traclabs.biosim.server.simulation.environment.SimEnvironmentOperations#removePercentage(float)
     */
    public void removeAirlockPercentage(float airlockPercentageToRemove) {
        if (currentVolume <= 0f)
            return;
        float airlockPercentageAdjusted = airlockPercentageToRemove
                * (myAirLockVolume / currentVolume);
        performLeak(airlockPercentageAdjusted);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.traclabs.biosim.server.simulation.environment.SimEnvironmentOperations#setAirlockSize(float)
     */
    public void setAirlockVolume(float pAirLockVolume) {
        myAirLockVolume = pAirLockVolume;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.traclabs.biosim.server.simulation.environment.SimEnvironmentOperations#getAirlockSize()
     */
    public float getAirlockVolume() {
        return myAirLockVolume;
    }

    /**
     * @return Returns the myDangerousOxygenThreshold.
     */
    public float getDangerousOxygenThreshold() {
        return myDangerousOxygenThreshold;
    }

    /**
     * @param myDangerousOxygenThreshold
     *            The myDangerousOxygenThreshold to set.
     */
    public void setDangerousOxygenThreshold(float pDangerousOxygenThreshold) {
        myDangerousOxygenThreshold = pDangerousOxygenThreshold;
    }

	public EnvironmentO2Store getO2Store() {
		return myO2Store;
	}

	public EnvironmentCO2Store getCO2Store() {
		return myCO2Store;
	}

	public EnvironmentOtherStore getOtherStore() {
		return myOtherStore;
	}

	public EnvironmentVaporStore getVaporStore() {
		return myVaporStore;
	}

	public EnvironmentNitrogenStore getNitrogenStore() {
		return myNitrogenStore;
	}

	public void setInitialVolume(float pInitialO2Moles, float pInitialCO2Moles, float pInitialOtherMoles, float pInitialWaterMoles, float pInitialNitrogenMoles, float pInitialVolume) {
    	currentVolume = initialVolume = pInitialVolume;
    	myO2Store.setInitialLevel(pInitialO2Moles);
    	myCO2Store.setInitialLevel(pInitialCO2Moles);
    	myOtherStore.setInitialLevel(pInitialOtherMoles);
    	myVaporStore.setInitialLevel(pInitialWaterMoles);
    	myNitrogenStore.setInitialLevel(pInitialNitrogenMoles);
	}

    /**
     * Retrieves the the total level of gas in the environment (in moles)
     * 
     * @return retrieves the the total level of gas in the environment (in
     *         moles)
     */
    public float getTotalMoles() {
    	float totalMoles = 0f;
        for (EnvironmentStore store : myEnvironmentStores)
        	totalMoles += store.getCurrentLevel();
        return totalMoles;
    }

    /**
     * Retrieves the the total level of gas in the environment (in kPA)
     * 
     * @return retrieves the the total level of gas in the environment (in kPA)
     */
    public float getTotalPressure() {
    	float totalPressure = 0f;
        for (EnvironmentStore store : myEnvironmentStores)
        	totalPressure += store.getPressure();
        return totalPressure;
    }

}
