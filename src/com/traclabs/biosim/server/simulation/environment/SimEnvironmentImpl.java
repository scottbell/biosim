package com.traclabs.biosim.server.simulation.environment;

import java.util.Iterator;

import com.traclabs.biosim.idl.framework.LogLevel;
import com.traclabs.biosim.idl.framework.Malfunction;
import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.simulation.environment.EnvironmentCO2Store;
import com.traclabs.biosim.idl.simulation.environment.EnvironmentCO2StorePOATie;
import com.traclabs.biosim.idl.simulation.environment.EnvironmentNitrogenStore;
import com.traclabs.biosim.idl.simulation.environment.EnvironmentNitrogenStorePOATie;
import com.traclabs.biosim.idl.simulation.environment.EnvironmentO2Store;
import com.traclabs.biosim.idl.simulation.environment.EnvironmentO2StorePOATie;
import com.traclabs.biosim.idl.simulation.environment.EnvironmentOtherStore;
import com.traclabs.biosim.idl.simulation.environment.EnvironmentOtherStorePOATie;
import com.traclabs.biosim.idl.simulation.environment.EnvironmentVaporStore;
import com.traclabs.biosim.idl.simulation.environment.EnvironmentVaporStorePOATie;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironmentOperations;
import com.traclabs.biosim.server.simulation.framework.PassiveModuleImpl;
import com.traclabs.biosim.util.OrbUtils;

/**
 * The SimEnvironment acts as the environment in which the crew breathes from
 * and as the keeper of time.
 * 
 * @author Scott Bell
 */

public class SimEnvironmentImpl extends PassiveModuleImpl implements
        SimEnvironmentOperations {
	
	private EnvironmentO2StoreImpl myO2StoreImpl = new EnvironmentO2StoreImpl(this);
	
	private EnvironmentCO2StoreImpl myCO2StoreImpl = new EnvironmentCO2StoreImpl(this);
	
	private EnvironmentNitrogenStoreImpl myNitrogenStoreImpl = new EnvironmentNitrogenStoreImpl(this);
	
	private EnvironmentOtherStoreImpl myOtherStoreImpl = new EnvironmentOtherStoreImpl(this);
	
	private EnvironmentVaporStoreImpl myVaporStoreImpl = new EnvironmentVaporStoreImpl(this);
	
	private EnvironmentStoreImpl[] myEnvironmentStores = {myO2StoreImpl, myCO2StoreImpl, myNitrogenStoreImpl, myOtherStoreImpl, myVaporStoreImpl};
	
	private EnvironmentO2Store myO2Store;
	
	private EnvironmentCO2Store myCO2Store;
	
	private EnvironmentNitrogenStore myNitrogenStore;
	
	private EnvironmentOtherStore myOtherStore;
	
	private EnvironmentVaporStore myVaporStore;
    
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
     * resets the gas levels to correct percantages of sea level air
     */
    public SimEnvironmentImpl(int pID, String pName) {
        this(pID, 100000, pName);
    }
    
    public SimEnvironmentImpl(int pID, String pName, float pVolume, float pTotalPressure, float o2Percentage, float co2Percentage, float otherPercentage, float waterPercentage, float nitrogenPercentage){
    	super(pID, pName);
        float o2Moles = calculateMoles(o2Percentage, pTotalPressure, pVolume);
        float co2Moles = calculateMoles(co2Percentage, pTotalPressure, pVolume);
        float otherMoles = calculateMoles(otherPercentage, pTotalPressure, pVolume);
        float waterMoles = calculateMoles(waterPercentage, pTotalPressure, pVolume);
        float nitrogenMoles = calculateMoles(nitrogenPercentage, pTotalPressure, pVolume);
        createPOAObjects();
    	setInitialVolume(o2Moles, co2Moles, otherMoles, waterMoles, nitrogenMoles, pVolume);
    }

    /**
     * Creates a SimEnvironment with a set initial currentVolume and resets the
     * gas levels to correct percantages of sea level air.
     * 
     * @param pInitialVolume
     *            the initial currentVolume of the environment in liters
     * @param pID
     *            the ID of the server
     * @param pName
     *            the name of this environment
     */
    public SimEnvironmentImpl(int pID, float pInitialVolume, String pName) {
        super(pID, pName);
        createPOAObjects();
        setInitialVolumeAtSeaLevel(pInitialVolume);
        currentVolume = initialVolume = pInitialVolume;
    }

	public SimEnvironmentImpl(float O2Moles, float CO2Moles, float otherMoles, float waterMoles, float nitrogenMoles, float pVolume, String pName, int pID) {
    	super(pID, pName);
        createPOAObjects();
    	setInitialVolume(O2Moles, CO2Moles, otherMoles, waterMoles, nitrogenMoles, pVolume);
	}

    public SimEnvironmentImpl() {
    	this(0, 100000, "Unnamed Environment");
	}
    
    private float calculateMoles(float percentage, float totalPressure, float volume) {
    	float moles = (percentage * totalPressure * volume) / (idealGasConstant * getTemperatureInKelvin());
		return moles;
    }

	private void createPOAObjects() {
    	EnvironmentO2StorePOATie O2Tie = new EnvironmentO2StorePOATie(myO2StoreImpl);
    	myO2Store = O2Tie._this(OrbUtils.getORB());
    	
    	EnvironmentCO2StorePOATie CO2Tie = new EnvironmentCO2StorePOATie(myCO2StoreImpl);
    	myCO2Store = CO2Tie._this(OrbUtils.getORB());
    	
    	EnvironmentNitrogenStorePOATie nitrogenTie = new EnvironmentNitrogenStorePOATie(myNitrogenStoreImpl);
    	myNitrogenStore = nitrogenTie._this(OrbUtils.getORB());
    	
    	EnvironmentVaporStorePOATie vaporTie = new EnvironmentVaporStorePOATie(myVaporStoreImpl);
    	myVaporStore = vaporTie._this(OrbUtils.getORB());
    	
    	EnvironmentOtherStorePOATie otherTie = new EnvironmentOtherStorePOATie(myOtherStoreImpl);
    	myOtherStore = otherTie._this(OrbUtils.getORB());
	}

	/**
     * Resets gas levels to correct percantages of sea level air
     */
    public void reset() {
        super.reset();
        lightIntensity = 0f;
        currentVolume = initialVolume;
        for (EnvironmentStoreImpl store : myEnvironmentStores)
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
        return myVaporStoreImpl.getPressure() / saturatedVaporPressure;
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
        myO2StoreImpl.setInitialLevel(currentVolume *  moleOfHumidAirPerLiter * 0.185f);
        myCO2StoreImpl.setInitialLevel(currentVolume *  moleOfHumidAirPerLiter * 0.117f/101f);
        myOtherStoreImpl.setInitialLevel(currentVolume *  moleOfHumidAirPerLiter * 0.0f);
        myVaporStoreImpl.setInitialLevel(currentVolume *  moleOfHumidAirPerLiter * 0.0218910f);
        myNitrogenStoreImpl.setInitialLevel(currentVolume *  moleOfHumidAirPerLiter * (1f - 0.185f - 0.117f/101f - 0.0f - 0.0218910f));
    }
    
    public void setCurrentVolumeAtSeaLevel(float pVolume) {
       	float moleOfHumidAirPerLiter = 0.04115f;	//	assuming air temperature of 23C
       	currentVolume = pVolume;
        myO2StoreImpl.setCurrentLevel(currentVolume *  moleOfHumidAirPerLiter * 0.185f);
        myCO2StoreImpl.setCurrentLevel(currentVolume *  moleOfHumidAirPerLiter * 0.117f/101f);
        myOtherStoreImpl.setCurrentLevel(currentVolume *  moleOfHumidAirPerLiter * 0.0f);
        myVaporStoreImpl.setCurrentLevel(currentVolume *  moleOfHumidAirPerLiter * 0.0218910f);
        myNitrogenStoreImpl.setCurrentLevel(currentVolume *  moleOfHumidAirPerLiter * (1f - 0.185f - 0.117f/101f - 0.0f - 0.0218910f));
    }

    public float getInitialTotalPressure() {
    	float initialTotalPressure = 0f;
        for (EnvironmentStoreImpl store : myEnvironmentStores)
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
        for (EnvironmentStoreImpl store : myEnvironmentStores)
        	store.performLeak(pLeakRate);
    }
    
    public void setLogLevel(LogLevel pLevel){
    	super.setLogLevel(pLevel);
        for (EnvironmentStoreImpl store : myEnvironmentStores)
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
                    for (EnvironmentStoreImpl store : myEnvironmentStores)
                    	store.setCurrentLevel(0);
                    currentMalfunction.setPerformed(true);
                    return;
                }
                float O2percentage = myO2StoreImpl.getCurrentLevel() / getTotalMoles();
                float CO2percentage = myCO2StoreImpl.getCurrentLevel()  / getTotalMoles();
                float otherPercentage = myOtherStoreImpl.getCurrentLevel()  / getTotalMoles();
                float waterPercentage = myVaporStoreImpl.getCurrentLevel()  / getTotalMoles();
                float nitrogenPercentage = myNitrogenStoreImpl.getCurrentLevel()  / getTotalMoles();
                if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
                    currentVolume = 0f;
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
                    currentVolume *= 0.50f; // 50% reduction
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
                    currentVolume *= 0.75f; // 25% reduction
                myO2StoreImpl.setCurrentLevel(O2percentage * currentVolume);
                myCO2StoreImpl.setCurrentLevel(CO2percentage * currentVolume);
                myOtherStoreImpl.setCurrentLevel(otherPercentage * currentVolume);
                myVaporStoreImpl.setCurrentLevel(waterPercentage * currentVolume);
                myNitrogenStoreImpl.setCurrentLevel(nitrogenPercentage * currentVolume);
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
        for (EnvironmentStoreImpl store : myEnvironmentStores)
        	store.tick();
        performLeak(permanentLeakRate);
        calculateLightIntensity();
    }

    public void log() {
    	myLogger.debug(getModuleName()+ " levels");
        for (EnvironmentStoreImpl store : myEnvironmentStores)
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
     * @see com.traclabs.biosim.idl.simulation.environment.SimEnvironmentOperations#removePercentage(float)
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
     * @see com.traclabs.biosim.idl.simulation.environment.SimEnvironmentOperations#setAirlockSize(float)
     */
    public void setAirlockVolume(float pAirLockVolume) {
        myAirLockVolume = pAirLockVolume;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.traclabs.biosim.idl.simulation.environment.SimEnvironmentOperations#getAirlockSize()
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
    	myO2StoreImpl.setInitialLevel(pInitialO2Moles);
    	myCO2StoreImpl.setInitialLevel(pInitialCO2Moles);
    	myOtherStoreImpl.setInitialLevel(pInitialOtherMoles);
    	myVaporStoreImpl.setInitialLevel(pInitialWaterMoles);
    	myNitrogenStoreImpl.setInitialLevel(pInitialNitrogenMoles);
	}
	
	/**
     * Retrieves the the total level of gas in the environment (in moles)
     * 
     * @return retrieves the the total level of gas in the environment (in
     *         moles)
     */
    public float getTotalMoles() {
    	float totalMoles = 0f;
        for (EnvironmentStoreImpl store : myEnvironmentStores)
        	totalMoles += store.getCurrentLevel();
        return totalMoles;
    }
    
    /**
     * Retrieves the the total level of gas in the environment (in moles)
     * 
     * @return retrieves the the total level of gas in the environment (in
     *         moles)
     */
    private void setTotalMoles(float pLevel) {
        for (EnvironmentStoreImpl store : myEnvironmentStores)
        	store.setCurrentLevel(pLevel);
    }

    /**
     * Retrieves the the total level of gas in the environment (in kPA)
     * 
     * @return retrieves the the total level of gas in the environment (in kPA)
     */
    public float getTotalPressure() {
    	float totalPressure = 0f;
        for (EnvironmentStoreImpl store : myEnvironmentStores)
        	totalPressure += store.getPressure();
        return totalPressure;
    }

}
