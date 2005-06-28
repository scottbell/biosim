package com.traclabs.biosim.server.simulation.environment;

import java.util.Iterator;

import com.traclabs.biosim.idl.framework.Malfunction;
import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.simulation.air.Breath;
import com.traclabs.biosim.idl.simulation.environment.EnvironmentStore;
import com.traclabs.biosim.idl.simulation.environment.EnvironmentStoreHelper;
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
	
	private EnvironmentStoreImpl myO2Store = new EnvironmentStoreImpl(this);
	
	private EnvironmentStoreImpl myCO2Store = new EnvironmentStoreImpl(this);
	
	private EnvironmentStoreImpl myNitrogenStore = new EnvironmentStoreImpl(this);
	
	private EnvironmentStoreImpl myOtherStore = new EnvironmentStoreImpl(this);
	
	private EnvironmentStoreImpl myWaterStore = new EnvironmentStoreImpl(this);
	
	private EnvironmentStoreImpl[] myEnvironmentStores = {myO2Store, myCO2Store, myNitrogenStore, myOtherStore, myWaterStore};
	
    private final static float temperature = 23f;

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
        setInitialVolumeAtSeaLevel(pInitialVolume);
    }

    public SimEnvironmentImpl(float O2Moles, float CO2Moles, float otherMoles, float waterMoles, float nitrogenMoles, float pVolume, String pName, int pID) {
    	super(pID, pName);
    	setInitialVolume(O2Moles, CO2Moles, otherMoles, waterMoles, nitrogenMoles, pVolume);
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
        float kelvinTemperature = getTemperature() + 239f;
        if (kelvinTemperature <= 0)
            return 0f;
        float exponent = (17.4f * getTemperature()) / kelvinTemperature;
        float saturatedVaporPressure = .611f * exp(exponent);
        return myWaterStore.getPressure() / saturatedVaporPressure;
    }

    //returns temperature in celsius
    public float getTemperature() {
        return temperature;
    }

    private float exp(float a) {
        return (new Double(Math.exp(a))).floatValue();
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
        currentVolume = initialVolume = pInitialVolume;
        myO2Store.setInitialLevel(0.2f);
        myOtherStore.setInitialLevel(0.01f);
        myCO2Store.setInitialLevel(0.02f);
        myWaterStore.setInitialLevel(0.01f);
        myNitrogenStore.setInitialLevel(0.76f);
    }

    public float getInitialVolume() {
        return initialVolume;
    }

    public float getCurrentVolume() {
        return currentVolume;
    }

    public Breath addBreath(Breath pBreath) {
        return new Breath(myO2Store.add(pBreath.O2), myCO2Store.add(pBreath.CO2),
        		myWaterStore.add(pBreath.water), myOtherStore.add(pBreath.other),
        		myNitrogenStore.add(pBreath.nitrogen));
    }

    public Breath takeAirMoles(float molesRequested) {
        //idiot check
        if (Float.isNaN(molesRequested)) {
            myLogger.warn("in takeAirMoles, attemped to remove "
                    + molesRequested);
            return new Breath(0f, 0f, 0f, 0f, 0f);
        }
        if (molesRequested <= 0)
            return new Breath(0f, 0f, 0f, 0f, 0f);
        //asking for more gas than exists
        float CO2Moles = myCO2Store.getCurrentLevel();
        float O2Moles = myO2Store.getCurrentLevel();
        float otherMoles = myOtherStore.getCurrentLevel();
        float waterMoles = myWaterStore.getCurrentLevel();
        float nitrogenMoles = myNitrogenStore.getCurrentLevel();
        if (molesRequested >= getTotalMoles()) {
            float afterRemovalCO2 = randomFilter(CO2Moles);
            float afterRemovalO2 = randomFilter(O2Moles);
            float afterRemovalOther = randomFilter(otherMoles);
            float afterRemovalWater = randomFilter(waterMoles);
            float afterRemovalNitrogen = randomFilter(nitrogenMoles);
            setTotalMoles(0);
            return new Breath(afterRemovalO2, afterRemovalCO2,
                    afterRemovalWater, afterRemovalOther, afterRemovalNitrogen);
        }
		float afterRemovalCO2 = randomFilter(CO2Moles
		        - ((CO2Moles / getTotalMoles()) * molesRequested));
		float afterRemovalO2 = randomFilter(O2Moles
		        - ((O2Moles / getTotalMoles()) * molesRequested));
		float afterRemovalOther = randomFilter(otherMoles
		        - ((otherMoles / getTotalMoles()) * molesRequested));
		float afterRemovalWater = randomFilter(waterMoles
		        - ((waterMoles / getTotalMoles()) * molesRequested));
		float afterRemovalNitrogen = randomFilter(nitrogenMoles
		        - ((nitrogenMoles / getTotalMoles()) * molesRequested));
		float O2MolesTaken = O2Moles - afterRemovalO2;
		float CO2MolesTaken = CO2Moles - afterRemovalCO2;
		float otherMolesTaken = otherMoles - afterRemovalOther;
		float waterMolesTaken = waterMoles - afterRemovalWater;
		float nitrogenMolesTaken = nitrogenMoles - afterRemovalNitrogen;
		myO2Store.setCurrentLevel(afterRemovalO2);
		myCO2Store.setCurrentLevel(afterRemovalCO2);
		myOtherStore.setCurrentLevel(afterRemovalOther);
		myWaterStore.setCurrentLevel(afterRemovalWater);
		myNitrogenStore.setCurrentLevel(afterRemovalNitrogen);
		return new Breath(O2MolesTaken, CO2MolesTaken, waterMolesTaken,
		        otherMolesTaken, nitrogenMolesTaken);
    }

    private void performLeak(float pLeakRate) {
        for (EnvironmentStoreImpl store : myEnvironmentStores)
        	store.performLeak(pLeakRate);
    }

    protected void performMalfunctions() {
        float malfunctionLeakRate = 0f;
        for (Iterator iter = myMalfunctions.values().iterator(); iter.hasNext();) {
            Malfunction currentMalfunction = (Malfunction) (iter.next());
            if (currentMalfunction.getLength() == MalfunctionLength.TEMPORARY_MALF) {
                malfunctionLeakRate = 0f;
                if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
                    malfunctionLeakRate = .20f;
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
                    malfunctionLeakRate = .10f;
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
                    malfunctionLeakRate = .05f;
                performLeak(malfunctionLeakRate);
            } else if ((currentMalfunction.getLength() == MalfunctionLength.PERMANENT_MALF)
                    && (!currentMalfunction.hasPerformed())) {
                if (currentVolume <= 0) {
                    for (EnvironmentStoreImpl store : myEnvironmentStores)
                    	store.setCurrentLevel(0);
                    currentMalfunction.setPerformed(true);
                    return;
                }
                float O2percentage = myO2Store.getCurrentLevel() / getTotalMoles();
                float CO2percentage = myCO2Store.getCurrentLevel()  / getTotalMoles();
                float otherPercentage = myOtherStore.getCurrentLevel()  / getTotalMoles();
                float waterPercentage = myWaterStore.getCurrentLevel()  / getTotalMoles();
                float nitrogenPercentage = myNitrogenStore.getCurrentLevel()  / getTotalMoles();
                if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
                    currentVolume = 0f;
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
                    currentVolume *= 0.5;
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
                    currentVolume *= .25f;
                myO2Store.setCurrentLevel(O2percentage * currentVolume);
                myCO2Store.setCurrentLevel(CO2percentage * currentVolume);
                myOtherStore.setCurrentLevel(otherPercentage * currentVolume);
                myWaterStore.setCurrentLevel(waterPercentage * currentVolume);
                myNitrogenStore.setCurrentLevel(nitrogenPercentage * currentVolume);
                currentMalfunction.setPerformed(true);
            }
        }
    }

    private void calculateLightIntensity() {
        lightIntensity = (float) (maxLumens * Math.pow(Math.sin(Math.PI
                / dayLength * (getMyTicks() - hourOfDayStart)), 2f));
    }

    /**
     * Processes a tick by adding to the tick counter
     */
    public void tick() {
        super.tick();
        performLeak(permanentLeakRate);
        calculateLightIntensity();
        for (EnvironmentStoreImpl store : myEnvironmentStores)
        	store.tick();
    }

    public void log() {
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

	public EnvironmentStore getO2Store() {
		return EnvironmentStoreHelper.narrow(OrbUtils.poaToCorbaObj(myO2Store));
	}

	public EnvironmentStore getCO2Store() {
		return EnvironmentStoreHelper.narrow(OrbUtils.poaToCorbaObj(myCO2Store));
	}

	public EnvironmentStore getOtherStore() {
		return EnvironmentStoreHelper.narrow(OrbUtils.poaToCorbaObj(myOtherStore));
	}

	public EnvironmentStore getWaterStore() {
		return EnvironmentStoreHelper.narrow(OrbUtils.poaToCorbaObj(myWaterStore));
	}

	public EnvironmentStore getNitrogenStore() {
		return EnvironmentStoreHelper.narrow(OrbUtils.poaToCorbaObj(myNitrogenStore));
	}

	public void setInitialVolume(float pInitialO2Moles, float pInitialCO2Moles, float pInitialOtherMoles, float pInitialWaterMoles, float pInitialNitrogenMoles, float pInitialVolume) {
    	currentVolume = initialVolume = pInitialVolume;
    	myO2Store.setInitialLevel(pInitialO2Moles);
    	myCO2Store.setInitialLevel(pInitialCO2Moles);
    	myOtherStore.setInitialLevel(pInitialOtherMoles);
    	myWaterStore.setInitialLevel(pInitialWaterMoles);
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