package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.simulation.air.CO2Store;
import com.traclabs.biosim.idl.simulation.air.CRSOperations;
import com.traclabs.biosim.idl.simulation.air.H2Store;
import com.traclabs.biosim.idl.simulation.air.O2Store;
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

    private PotableWaterStore[] myPotableWaterStoreOutputs;

    private CO2Store[] myCO2InputStores;

    private H2Store[] myH2InputStores;

    private float[] powerMaxFlowRates;

    private float[] powerActualFlowRates;

    private float[] powerDesiredFlowRates;

    private float[] potableWaterOutMaxFlowRates;

    private float[] potableWaterOutActualFlowRates;

    private float[] potableWaterOutDesiredFlowRates;

    private float[] O2MaxFlowRates;

    private float[] O2ActualFlowRates;

    private float[] O2DesiredFlowRates;

    private float[] CO2InputMaxFlowRates;

    private float[] CO2InputActualFlowRates;

    private float[] CO2InputDesiredFlowRates;

    private float[] H2InputMaxFlowRates;

    private float[] H2InputActualFlowRates;

    private float[] H2InputDesiredFlowRates;
    
    private float currentPowerConsumed = 0f;
    
    private float currentCO2Consumed;

    private float currentH2Consumed;

    private float currentH2OProduced;

    private float currentCH4Produced;
    
    private float CH4Produced = 0f;

    private float currentO2Produced;
    
    //multiply times power to determine how much air/H2/water we're consuming
    private static final float LINEAR_MULTIPLICATIVE_FACTOR = 100;

    public CRSImpl(int pID, String pName) {
        super(pID, pName);
       
        myO2Stores = new O2Store[0];
        myPowerStores = new PowerStore[0];
        myCO2InputStores = new CO2Store[0];
        powerMaxFlowRates = new float[0];
        powerActualFlowRates = new float[0];
        powerDesiredFlowRates = new float[0];
        potableWaterOutMaxFlowRates = new float[0];
        potableWaterOutActualFlowRates = new float[0];
        potableWaterOutDesiredFlowRates = new float[0];
        O2MaxFlowRates = new float[0];
        O2ActualFlowRates = new float[0];
        O2DesiredFlowRates = new float[0];
        CO2InputMaxFlowRates = new float[0];
        CO2InputActualFlowRates = new float[0];
        CO2InputDesiredFlowRates = new float[0];
        H2InputMaxFlowRates = new float[0];
        H2InputActualFlowRates = new float[0];
        H2InputDesiredFlowRates = new float[0];
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
        gatherPower();
        gatherH2andCO2();
        pushWaterAndMethane();
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