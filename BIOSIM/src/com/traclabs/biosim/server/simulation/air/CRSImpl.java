package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.simulation.air.Breath;
import com.traclabs.biosim.idl.simulation.air.CO2Store;
import com.traclabs.biosim.idl.simulation.air.CRSOperations;
import com.traclabs.biosim.idl.simulation.air.H2Store;
import com.traclabs.biosim.idl.simulation.air.O2Store;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.framework.CO2ConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.H2ConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.O2ProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.PowerConsumerOperations;
import com.traclabs.biosim.idl.simulation.power.PowerStore;
import com.traclabs.biosim.idl.simulation.water.PotableWaterStore;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;

/**
 * The Air Revitalization System Implementation. Takes in Air (O2, CO2, other)
 * from the environment and power from the Power Production System and produces
 * air with less CO2 and more O2.
 * 
 * @author Scott Bell
 */

public class CRSImpl extends SimBioModuleImpl implements CRSOperations,
        PowerConsumerOperations, PotableWaterProducerOperations, O2ProducerOperations,
        CO2ConsumerOperations, H2ConsumerOperations {
    private O2Store[] myO2Stores;

    private PowerStore[] myPowerStores;

    private PotableWaterStore[] myPotableWaterStoreInputs;

    private PotableWaterStore[] myPotableWaterStoreOutputs;

    private CO2Store[] myCO2InputStores;

    private CO2Store[] myCO2OutputStores;

    private H2Store[] myH2InputStores;

    private H2Store[] myH2OutputStores;

    private SimEnvironment[] mySimEnvironmentInputs;

    private SimEnvironment[] mySimEnvironmentOutputs;

    private float[] powerMaxFlowRates;

    private float[] powerActualFlowRates;

    private float[] powerDesiredFlowRates;

    private float[] potableWaterOutMaxFlowRates;

    private float[] potableWaterOutActualFlowRates;

    private float[] potableWaterOutDesiredFlowRates;

    private float[] potableWaterInMaxFlowRates;

    private float[] potableWaterInActualFlowRates;

    private float[] potableWaterInDesiredFlowRates;

    private float[] O2MaxFlowRates;

    private float[] O2ActualFlowRates;

    private float[] O2DesiredFlowRates;

    private float[] CO2InputMaxFlowRates;

    private float[] CO2InputActualFlowRates;

    private float[] CO2InputDesiredFlowRates;

    private float[] CO2OutputMaxFlowRates;

    private float[] CO2OutputActualFlowRates;

    private float[] CO2OutputDesiredFlowRates;

    private float[] H2InputMaxFlowRates;

    private float[] H2InputActualFlowRates;

    private float[] H2InputDesiredFlowRates;

    private float[] H2OutputMaxFlowRates;

    private float[] H2OutputActualFlowRates;

    private float[] H2OutputDesiredFlowRates;

    private float[] airInMaxFlowRates;

    private float[] airInActualFlowRates;

    private float[] airInDesiredFlowRates;

    private float[] airOutMaxFlowRates;

    private float[] airOutActualFlowRates;

    private float[] airOutDesiredFlowRates;
    
    private Breath myCurrentBreath = new Breath(0f, 0f, 0f, 0f, 0f);
    
    private float currentPowerConsumed = 0f;

    private float currentCO2Produced = 0f;

    private float currentCO2Consumed;

    private float currentH2Consumed;

    private float currentH2OProduced;

    private float currentCH4Produced;
    
    private float CH4Produced = 0f;

    private float currentH2OConsumed;

    private float currentO2Produced;

    private float currentH2Produced;
    
    //multiply times power to determine how much air/H2/water we're consuming
    private static final float LINEAR_MULTIPLICATIVE_FACTOR = 100;

    public CRSImpl(int pID, String pName) {
        super(pID, pName);
       
        myO2Stores = new O2Store[0];
        myPowerStores = new PowerStore[0];
        myCO2InputStores = new CO2Store[0];
        myCO2OutputStores = new CO2Store[0];
        mySimEnvironmentInputs = new SimEnvironment[0];
        mySimEnvironmentOutputs = new SimEnvironment[0];
        powerMaxFlowRates = new float[0];
        powerActualFlowRates = new float[0];
        powerDesiredFlowRates = new float[0];
        potableWaterInMaxFlowRates = new float[0];
        potableWaterInActualFlowRates = new float[0];
        potableWaterInDesiredFlowRates = new float[0];
        potableWaterOutMaxFlowRates = new float[0];
        potableWaterOutActualFlowRates = new float[0];
        potableWaterOutDesiredFlowRates = new float[0];
        O2MaxFlowRates = new float[0];
        O2ActualFlowRates = new float[0];
        O2DesiredFlowRates = new float[0];
        CO2InputMaxFlowRates = new float[0];
        CO2InputActualFlowRates = new float[0];
        CO2InputDesiredFlowRates = new float[0];
        CO2OutputMaxFlowRates = new float[0];
        CO2OutputActualFlowRates = new float[0];
        CO2OutputDesiredFlowRates = new float[0];
        H2InputMaxFlowRates = new float[0];
        H2InputActualFlowRates = new float[0];
        H2InputDesiredFlowRates = new float[0];
        H2OutputMaxFlowRates = new float[0];
        H2OutputActualFlowRates = new float[0];
        H2OutputDesiredFlowRates = new float[0];
        airInMaxFlowRates = new float[0];
        airInActualFlowRates = new float[0];
        airInDesiredFlowRates = new float[0];
        airOutMaxFlowRates = new float[0];
        airOutActualFlowRates = new float[0];
        airOutDesiredFlowRates = new float[0];
    }
    
    private void gatherPower() {
        currentPowerConsumed = getMostResourceFromStore(myPowerStores, powerMaxFlowRates, powerDesiredFlowRates, powerActualFlowRates);
    }


    /**
     * Processes a tick by collecting referernces (if needed), resources, and
     * pushing the new air out.
     */
    public void tick() {
        super.tick();
        myLogger.debug("tick");
        gatherPower();
        gatherAir();
        pushAir();
        gatherH2andCO2();
        pushWaterAndMethane();
        gatherWater();
        pushOxygen();
    }
    
    private void gatherAir() {
        
        float gatheredAir = 0f;
        float gatheredO2 = 0f;
        float gatheredCO2 = 0f;
        float gatheredOther = 0f;
        float gatheredWater = 0f;
        float gatheredNitrogen = 0f;
        float airNeeded = currentPowerConsumed * LINEAR_MULTIPLICATIVE_FACTOR;
        for (int i = 0; (i < getAirInputs().length)
                && (gatheredAir < airNeeded); i++) {
            float resourceToGatherFirst = Math.min(airNeeded, getAirInputMaxFlowRate(i));
            float resourceToGatherFinal = Math.min(resourceToGatherFirst,
                    getAirInputDesiredFlowRate(i));
            Breath currentBreath = getAirInputs()[i]
                    .takeAirMoles(resourceToGatherFinal);
            gatheredAir += currentBreath.O2 + currentBreath.CO2
                    + currentBreath.other + currentBreath.water
                    + currentBreath.nitrogen;
            setAirInputActualFlowRate(gatheredAir, i);
            gatheredO2 += currentBreath.O2;
            gatheredCO2 += currentBreath.CO2;
            gatheredOther += currentBreath.other;
            gatheredWater += currentBreath.water;
            gatheredNitrogen += currentBreath.nitrogen;
        }
        myCurrentBreath.O2 = gatheredO2;
        myCurrentBreath.CO2 = gatheredCO2;
        myCurrentBreath.other = gatheredOther;
        myCurrentBreath.water = gatheredWater;
        myCurrentBreath.nitrogen = gatheredNitrogen;
    }

    private void pushAir() {
        float distributedO2Left = myCurrentBreath.O2;
        float distributedOtherLeft = myCurrentBreath.other;
        float distributedWaterLeft = myCurrentBreath.water;
        float distributedNitrogenLeft = myCurrentBreath.nitrogen;
        for (int i = 0; (i < getAirOutputs().length)
                && ((distributedO2Left > 0) || (distributedOtherLeft > 0)
                        || (distributedWaterLeft > 0) || (distributedNitrogenLeft > 0)); i++) {
            float totalToDistribute = distributedO2Left + distributedOtherLeft
                    + distributedWaterLeft + distributedNitrogenLeft;
            float resourceToDistributeFirst = Math.min(totalToDistribute,
                    getAirOutputMaxFlowRate(i));
            float resourceToDistributeFinal = Math.min(
                    resourceToDistributeFirst, getAirOutputDesiredFlowRate(i));
            //Recalculate percentages based on smaller volume
            float reducedO2ToPass = resourceToDistributeFinal
                    * (distributedO2Left / totalToDistribute);
            float reducedOtherToPass = resourceToDistributeFinal
                    * (distributedOtherLeft / totalToDistribute);
            float reducedWaterToPass = resourceToDistributeFinal
                    * (distributedWaterLeft / totalToDistribute);
            float reducedNitrogenToPass = resourceToDistributeFinal
                    * (distributedNitrogenLeft / totalToDistribute);
            float O2Added = getAirOutputs()[i]
                    .addO2Moles(reducedO2ToPass);
            float otherAdded = getAirOutputs()[i]
                    .addOtherMoles(reducedOtherToPass);
            float waterAdded = getAirOutputs()[i]
                    .addWaterMoles(reducedWaterToPass);
            float nitrogenAdded = getAirOutputs()[i]
                    .addNitrogenMoles(reducedNitrogenToPass);
            distributedO2Left -= O2Added;
            distributedOtherLeft -= otherAdded;
            distributedWaterLeft -= waterAdded;
            distributedNitrogenLeft -= nitrogenAdded;
            setAirOutputActualFlowRate(reducedO2ToPass
                    + reducedOtherToPass + reducedWaterToPass
                    + reducedNitrogenToPass, i);
        }
        currentCO2Produced = myCurrentBreath.CO2;
        float distributedCO2Left = SimBioModuleImpl.pushResourceToStore(getCO2Outputs(), getCO2OutputMaxFlowRates(), getCO2OutputDesiredFlowRates(), getCO2OutputActualFlowRates(), currentCO2Produced = 0f);
    }
    
    private void gatherH2andCO2() {
        float CO2Needed = currentPowerConsumed * LINEAR_MULTIPLICATIVE_FACTOR;
        float H2Needed = CO2Needed * 4f;
        float filteredCO2Needed = randomFilter(CO2Needed);
        float filteredH2Needed = randomFilter(H2Needed);
        currentCO2Consumed = SimBioModuleImpl.getResourceFromStore(getCO2Inputs(), getCO2InputMaxFlowRates(), getCO2InputDesiredFlowRates(), getCO2InputActualFlowRates(), filteredCO2Needed);
        currentH2Consumed = SimBioModuleImpl.getResourceFromStore(getH2Inputs(), getH2InputMaxFlowRates(), getH2InputDesiredFlowRates(), getH2InputActualFlowRates(), filteredH2Needed);
    }

    private void pushWaterAndMethane() {
        if ((currentH2Consumed <= 0) || (currentCO2Consumed <= 0)) {
            currentH2OProduced = 0f;
            currentCH4Produced = 0f;
            SimBioModuleImpl.pushResourceToStore(getH2Inputs(), getH2InputMaxFlowRates(), getH2InputDesiredFlowRates(), getH2InputActualFlowRates(), currentH2Consumed);
            SimBioModuleImpl.pushResourceToStore(getCO2Inputs(),
                    getCO2InputMaxFlowRates(), getCO2InputDesiredFlowRates(), getCO2InputActualFlowRates(), currentCO2Consumed);
        } else {
            // CO2 + 4H2 --> CH4 + 2H20
            float limitingReactant = Math.min(currentH2Consumed / 4f,
                    currentCO2Consumed);
            if (limitingReactant == currentH2Consumed)
                SimBioModuleImpl.pushResourceToStore(getCO2Inputs(),
                        getCO2InputMaxFlowRates(), getCO2InputDesiredFlowRates(), getCO2InputActualFlowRates(),
                        currentCO2Consumed - limitingReactant);
            else
                SimBioModuleImpl.pushResourceToStore(getH2Inputs(),
                        getH2InputMaxFlowRates(), getH2InputDesiredFlowRates(), getH2InputActualFlowRates(), currentH2Consumed
                                - 4f * limitingReactant);
            float waterMolesProduced = 2f * limitingReactant;
            float waterLitersProduced = (waterMolesProduced * 18.01524f) / 1000f; //1000g/liter,
            // 18.01524g/mole
            float methaneMolesProduced = limitingReactant;
            currentH2OProduced = randomFilter(waterLitersProduced);
            currentCH4Produced = randomFilter(methaneMolesProduced);
        }
        float distributedWaterLeft = SimBioModuleImpl.pushResourceToStore(
                getPotableWaterOutputs(), getPotableWaterOutputMaxFlowRates(), getPotableWaterOutputDesiredFlowRates(), getPotableWaterOutputActualFlowRates(),
                currentH2OProduced);
        CH4Produced += currentCH4Produced;
    }
    
    private void gatherWater() {
        float waterNeeded = currentPowerConsumed * LINEAR_MULTIPLICATIVE_FACTOR;
        currentH2OConsumed = SimBioModuleImpl.getResourceFromStore(getPotableWaterInputs(), getPotableWaterInputMaxFlowRates(), getPotableWaterInputDesiredFlowRates(), getPotableWaterInputActualFlowRates(), waterNeeded);
    }

    private void pushOxygen() {
        //2H20 --> 2H2 + O2
        float molesOfWater = (currentH2OConsumed * 1000f) / 18.01524f; //1000g/liter,
        // 18.01524g/mole
        float molesOfReactant = molesOfWater / 2f;
        currentO2Produced = randomFilter(molesOfReactant);
        currentH2Produced = randomFilter(molesOfReactant * 2f);
        float O2ToDistrubute = randomFilter(currentO2Produced);
        float H2ToDistrubute = randomFilter(currentH2Produced);
        float distributedO2 = SimBioModuleImpl.pushResourceToStore(getO2Outputs(), getO2OutputMaxFlowRates(), getO2OutputDesiredFlowRates(), getO2OutputActualFlowRates(), O2ToDistrubute);
        float distributedH2 = SimBioModuleImpl.pushResourceToStore(getH2Outputs(), getH2OutputMaxFlowRates(), getH2OutputDesiredFlowRates(), getH2OutputActualFlowRates(), H2ToDistrubute);
    }
    
    protected String getMalfunctionName(MalfunctionIntensity pIntensity,
            MalfunctionLength pLength) {
        return "None";
    }

    protected void performMalfunctions() {
    }

    /**
     * Resets production/consumption levels.
     */
    public void reset() {
        super.reset();
        CH4Produced = 0f;
    }

    //Power Inputs
    public void setPowerInputMaxFlowRate(float watts, int index) {
        powerMaxFlowRates[index] = watts;
    }

    public float getPowerInputMaxFlowRate(int index) {
        return powerMaxFlowRates[index];
    }

    public float[] getPowerInputMaxFlowRates() {
        return powerMaxFlowRates;
    }

    public void setPowerInputDesiredFlowRate(float watts, int index) {
        powerDesiredFlowRates[index] = watts;
    }

    public float getPowerInputDesiredFlowRate(int index) {
        return powerDesiredFlowRates[index];
    }

    public float[] getPowerInputDesiredFlowRates() {
        return powerDesiredFlowRates;
    }

    public float getPowerInputActualFlowRate(int index) {
        return powerActualFlowRates[index];
    }

    public float[] getPowerInputActualFlowRates() {
        return powerActualFlowRates;
    }

    public void setPowerInputs(PowerStore[] sources, float[] maxFlowRates,
            float[] desiredFlowRates) {
        myPowerStores = sources;
        powerMaxFlowRates = maxFlowRates;
        powerDesiredFlowRates = desiredFlowRates;
        powerActualFlowRates = new float[powerDesiredFlowRates.length];
    }

    public PowerStore[] getPowerInputs() {
        return myPowerStores;
    }

    void addPowerInputActualFlowRate(float watts, int index) {
        powerActualFlowRates[index] += watts;
    }

    //Potable Water Outputs
    public void setPotableWaterOutputMaxFlowRate(float watts, int index) {
        potableWaterOutMaxFlowRates[index] = watts;
    }

    public float getPotableWaterOutputMaxFlowRate(int index) {
        return potableWaterOutMaxFlowRates[index];
    }

    public float[] getPotableWaterOutputMaxFlowRates() {
        return potableWaterOutMaxFlowRates;
    }

    public void setPotableWaterOutputDesiredFlowRate(float watts, int index) {
        potableWaterOutDesiredFlowRates[index] = watts;
    }

    public float getPotableWaterOutputDesiredFlowRate(int index) {
        return potableWaterOutDesiredFlowRates[index];
    }

    public float[] getPotableWaterOutputDesiredFlowRates() {
        return potableWaterOutDesiredFlowRates;
    }

    public float getPotableWaterOutputActualFlowRate(int index) {
        return potableWaterOutActualFlowRates[index];
    }

    public float[] getPotableWaterOutputActualFlowRates() {
        return potableWaterOutActualFlowRates;
    }

    public void setPotableWaterOutputs(PotableWaterStore[] sources,
            float[] maxFlowRates, float[] desiredFlowRates) {
        myPotableWaterStoreOutputs = sources;
        potableWaterOutMaxFlowRates = maxFlowRates;
        potableWaterOutDesiredFlowRates = desiredFlowRates;
        potableWaterOutActualFlowRates = new float[potableWaterOutDesiredFlowRates.length];
    }

    public PotableWaterStore[] getPotableWaterOutputs() {
        return myPotableWaterStoreOutputs;
    }

    void addPotableWaterOutputActualFlowRate(float watts, int index) {
        potableWaterOutActualFlowRates[index] += watts;
    }

    //Potable Water Inputs
    public void setPotableWaterInputMaxFlowRate(float watts, int index) {
        potableWaterInMaxFlowRates[index] = watts;
    }

    public float getPotableWaterInputMaxFlowRate(int index) {
        return potableWaterInMaxFlowRates[index];
    }

    public float[] getPotableWaterInputMaxFlowRates() {
        return potableWaterInMaxFlowRates;
    }

    public void setPotableWaterInputDesiredFlowRate(float liters, int index) {
        myLogger.debug(getModuleName()+": setting potableWaterInDesiredFlowRates["+index+"] to "+liters);
        potableWaterInDesiredFlowRates[index] = liters;
    }

    public float getPotableWaterInputDesiredFlowRate(int index) {
        return potableWaterInDesiredFlowRates[index];
    }

    public float[] getPotableWaterInputDesiredFlowRates() {
        return potableWaterInDesiredFlowRates;
    }

    public float getPotableWaterInputActualFlowRate(int index) {
        return potableWaterInActualFlowRates[index];
    }

    public float[] getPotableWaterInputActualFlowRates() {
        return potableWaterInActualFlowRates;
    }

    public void setPotableWaterInputs(PotableWaterStore[] sources,
            float[] maxFlowRates, float[] desiredFlowRates) {
        myPotableWaterStoreInputs = sources;
        potableWaterInMaxFlowRates = maxFlowRates;
        potableWaterInDesiredFlowRates = desiredFlowRates;
        potableWaterInActualFlowRates = new float[potableWaterInDesiredFlowRates.length];
    }

    public PotableWaterStore[] getPotableWaterInputs() {
        return myPotableWaterStoreInputs;
    }

    void addPotableWaterInputActualFlowRate(float watts, int index) {
        potableWaterInActualFlowRates[index] += watts;
    }

    //Air Inputs
    public void setAirInputMaxFlowRate(float moles, int index) {
        airInMaxFlowRates[index] = moles;
    }

    public float getAirInputMaxFlowRate(int index) {
        return airInMaxFlowRates[index];
    }

    public float[] getAirInputMaxFlowRates() {
        return airInMaxFlowRates;
    }

    public void setAirInputDesiredFlowRate(float moles, int index) {
        airInDesiredFlowRates[index] = moles;
    }

    public float getAirInputDesiredFlowRate(int index) {
        return airInDesiredFlowRates[index];
    }

    public float[] getAirInputDesiredFlowRates() {
        return airInDesiredFlowRates;
    }

    public float getAirInputActualFlowRate(int index) {
        return airInActualFlowRates[index];
    }

    public float[] getAirInputActualFlowRates() {
        return airInActualFlowRates;
    }

    public void setAirInputs(SimEnvironment[] sources, float[] maxFlowRates,
            float[] desiredFlowRates) {
        mySimEnvironmentInputs = sources;
        airInMaxFlowRates = maxFlowRates;
        airInDesiredFlowRates = desiredFlowRates;
        airInActualFlowRates = new float[airInDesiredFlowRates.length];
    }

    public SimEnvironment[] getAirInputs() {
        return mySimEnvironmentInputs;
    }

    void setAirInputActualFlowRate(float moles, int index) {
        airInActualFlowRates[index] = moles;
    }

    //Air Ouputs
    public void setAirOutputMaxFlowRate(float moles, int index) {
        airOutMaxFlowRates[index] = moles;
    }

    public float getAirOutputMaxFlowRate(int index) {
        return airOutMaxFlowRates[index];
    }

    public float[] getAirOutputMaxFlowRates() {
        return airOutMaxFlowRates;
    }

    public void setAirOutputDesiredFlowRate(float moles, int index) {
        airOutDesiredFlowRates[index] = moles;
    }

    public float getAirOutputDesiredFlowRate(int index) {
        return airOutDesiredFlowRates[index];
    }

    public float[] getAirOutputDesiredFlowRates() {
        return airOutDesiredFlowRates;
    }

    public float getAirOutputActualFlowRate(int index) {
        return airOutActualFlowRates[index];
    }

    public float[] getAirOutputActualFlowRates() {
        return airOutActualFlowRates;
    }

    public void setAirOutputs(SimEnvironment[] sources, float[] maxFlowRates,
            float[] desiredFlowRates) {
        mySimEnvironmentOutputs = sources;
        airOutMaxFlowRates = maxFlowRates;
        airOutDesiredFlowRates = desiredFlowRates;
        airOutActualFlowRates = new float[airOutDesiredFlowRates.length];
    }

    public SimEnvironment[] getAirOutputs() {
        return mySimEnvironmentOutputs;
    }

    void setAirOutputActualFlowRate(float moles, int index) {
        airOutActualFlowRates[index] = moles;
    }

    //O2 Ouputs
    public void setO2OutputMaxFlowRate(float moles, int index) {
        O2MaxFlowRates[index] = moles;
    }

    public float getO2OutputMaxFlowRate(int index) {
        return O2MaxFlowRates[index];
    }

    public float[] getO2OutputMaxFlowRates() {
        return O2MaxFlowRates;
    }

    public void setO2OutputDesiredFlowRate(float moles, int index) {
        O2DesiredFlowRates[index] = moles;
    }

    public float getO2OutputDesiredFlowRate(int index) {
        return O2DesiredFlowRates[index];
    }

    public float[] getO2OutputDesiredFlowRates() {
        return O2DesiredFlowRates;
    }

    public float getO2OutputActualFlowRate(int index) {
        return O2ActualFlowRates[index];
    }

    public float[] getO2OutputActualFlowRates() {
        return O2ActualFlowRates;
    }

    public void setO2Outputs(O2Store[] sources, float[] maxFlowRates,
            float[] desiredFlowRates) {
        myO2Stores = sources;
        O2MaxFlowRates = maxFlowRates;
        O2DesiredFlowRates = desiredFlowRates;
        O2ActualFlowRates = new float[O2DesiredFlowRates.length];
    }

    public O2Store[] getO2Outputs() {
        return myO2Stores;
    }

    void setO2OutputActualFlowRate(float moles, int index) {
        O2ActualFlowRates[index] = moles;
    }

    //CO2 Ouputs
    public void setCO2OutputMaxFlowRate(float moles, int index) {
        CO2OutputMaxFlowRates[index] = moles;
    }

    public float getCO2OutputMaxFlowRate(int index) {
        return CO2OutputMaxFlowRates[index];
    }

    public float[] getCO2OutputMaxFlowRates() {
        return CO2OutputMaxFlowRates;
    }

    public void setCO2OutputDesiredFlowRate(float moles, int index) {
        CO2OutputDesiredFlowRates[index] = moles;
    }

    public float getCO2OutputDesiredFlowRate(int index) {
        return CO2OutputDesiredFlowRates[index];
    }

    public float[] getCO2OutputDesiredFlowRates() {
        return CO2OutputDesiredFlowRates;
    }

    public float getCO2OutputActualFlowRate(int index) {
        return CO2OutputActualFlowRates[index];
    }

    public float[] getCO2OutputActualFlowRates() {
        return CO2OutputActualFlowRates;
    }

    public void setCO2Outputs(CO2Store[] sources, float[] maxFlowRates,
            float[] desiredFlowRates) {
        myCO2OutputStores = sources;
        CO2OutputMaxFlowRates = maxFlowRates;
        CO2OutputDesiredFlowRates = desiredFlowRates;
        CO2OutputActualFlowRates = new float[CO2OutputDesiredFlowRates.length];
    }

    public CO2Store[] getCO2Outputs() {
        return myCO2OutputStores;
    }

    void setCO2OutputActualFlowRate(float moles, int index) {
        CO2OutputActualFlowRates[index] = moles;
    }

    //CO2 Inputs
    public void setCO2InputMaxFlowRate(float moles, int index) {
        CO2InputMaxFlowRates[index] = moles;
    }

    public float getCO2InputMaxFlowRate(int index) {
        return CO2InputMaxFlowRates[index];
    }

    public float[] getCO2InputMaxFlowRates() {
        return CO2InputMaxFlowRates;
    }

    public void setCO2InputDesiredFlowRate(float moles, int index) {
        CO2InputDesiredFlowRates[index] = moles;
    }

    public float getCO2InputDesiredFlowRate(int index) {
        return CO2InputDesiredFlowRates[index];
    }

    public float[] getCO2InputDesiredFlowRates() {
        return CO2InputDesiredFlowRates;
    }

    public float getCO2InputActualFlowRate(int index) {
        return CO2InputActualFlowRates[index];
    }

    public float[] getCO2InputActualFlowRates() {
        return CO2InputActualFlowRates;
    }

    public void setCO2Inputs(CO2Store[] sources, float[] maxFlowRates,
            float[] desiredFlowRates) {
        myCO2InputStores = sources;
        CO2InputMaxFlowRates = maxFlowRates;
        CO2InputDesiredFlowRates = desiredFlowRates;
        CO2InputActualFlowRates = new float[CO2InputDesiredFlowRates.length];
    }

    public CO2Store[] getCO2Inputs() {
        return myCO2InputStores;
    }

    void setCO2InputActualFlowRate(float moles, int index) {
        CO2InputActualFlowRates[index] = moles;
    }

    //H2 Ouputs
    public void setH2OutputMaxFlowRate(float moles, int index) {
        H2OutputMaxFlowRates[index] = moles;
    }

    public float getH2OutputMaxFlowRate(int index) {
        return H2OutputMaxFlowRates[index];
    }

    public float[] getH2OutputMaxFlowRates() {
        return H2OutputMaxFlowRates;
    }

    public void setH2OutputDesiredFlowRate(float moles, int index) {
        H2OutputDesiredFlowRates[index] = moles;
    }

    public float getH2OutputDesiredFlowRate(int index) {
        return H2OutputDesiredFlowRates[index];
    }

    public float[] getH2OutputDesiredFlowRates() {
        return H2OutputDesiredFlowRates;
    }

    public float getH2OutputActualFlowRate(int index) {
        return H2OutputActualFlowRates[index];
    }

    public float[] getH2OutputActualFlowRates() {
        return H2OutputActualFlowRates;
    }

    public void setH2Outputs(H2Store[] sources, float[] maxFlowRates,
            float[] desiredFlowRates) {
        myH2OutputStores = sources;
        H2OutputMaxFlowRates = maxFlowRates;
        H2OutputDesiredFlowRates = desiredFlowRates;
        H2OutputActualFlowRates = new float[H2OutputDesiredFlowRates.length];
    }

    public H2Store[] getH2Outputs() {
        return myH2OutputStores;
    }

    void setH2OutputActualFlowRate(float moles, int index) {
        H2OutputActualFlowRates[index] = moles;
    }

    //H2 Inputs
    public void setH2InputMaxFlowRate(float moles, int index) {
        H2InputMaxFlowRates[index] = moles;
    }

    public float getH2InputMaxFlowRate(int index) {
        return H2InputMaxFlowRates[index];
    }

    public float[] getH2InputMaxFlowRates() {
        return H2InputMaxFlowRates;
    }

    public void setH2InputDesiredFlowRate(float moles, int index) {
        H2InputDesiredFlowRates[index] = moles;
    }

    public float getH2InputDesiredFlowRate(int index) {
        return H2InputDesiredFlowRates[index];
    }

    public float[] getH2InputDesiredFlowRates() {
        return H2InputDesiredFlowRates;
    }

    public float getH2InputActualFlowRate(int index) {
        return H2InputActualFlowRates[index];
    }

    public float[] getH2InputActualFlowRates() {
        return H2InputActualFlowRates;
    }

    public void setH2Inputs(H2Store[] sources, float[] maxFlowRates,
            float[] desiredFlowRates) {
        myH2InputStores = sources;
        H2InputMaxFlowRates = maxFlowRates;
        H2InputDesiredFlowRates = desiredFlowRates;
        H2InputActualFlowRates = new float[H2InputDesiredFlowRates.length];
    }

    public H2Store[] getH2Inputs() {
        return myH2InputStores;
    }

    void setH2InputActualFlowRate(float moles, int index) {
        H2InputActualFlowRates[index] = moles;
    }
}