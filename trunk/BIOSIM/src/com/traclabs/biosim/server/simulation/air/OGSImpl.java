package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.idl.simulation.air.H2Store;
import com.traclabs.biosim.idl.simulation.air.O2Store;
import com.traclabs.biosim.idl.simulation.air.OGSOperations;
import com.traclabs.biosim.idl.simulation.framework.H2ProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.O2ProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.PotableWaterConsumerOperations;
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

public class OGSImpl extends SimBioModuleImpl implements OGSOperations,
        PowerConsumerOperations, PotableWaterConsumerOperations,
        O2ProducerOperations, H2ProducerOperations{
    
    private O2Store[] myO2Stores;

    private PowerStore[] myPowerStores;

    private PotableWaterStore[] myPotableWaterStoreInputs;

    private H2Store[] myH2OutputStores;

    private float[] powerMaxFlowRates;

    private float[] powerActualFlowRates;

    private float[] powerDesiredFlowRates;

    private float[] potableWaterInMaxFlowRates;

    private float[] potableWaterInActualFlowRates;

    private float[] potableWaterInDesiredFlowRates;

    private float[] O2MaxFlowRates;

    private float[] O2ActualFlowRates;

    private float[] O2DesiredFlowRates;

    private float[] H2OutputMaxFlowRates;

    private float[] H2OutputActualFlowRates;

    private float[] H2OutputDesiredFlowRates;

    private float currentH2OConsumed = 0;

    private float currentO2Produced = 0;

    private float currentH2Produced = 0;

    private float myProductionRate = 1f;

    private float currentPowerConsumed = 0f;

    public OGSImpl(int pID, String pName) {
        super(pID, pName);

        myO2Stores = new O2Store[0];
        myPowerStores = new PowerStore[0];
        powerMaxFlowRates = new float[0];
        powerActualFlowRates = new float[0];
        powerDesiredFlowRates = new float[0];
        potableWaterInMaxFlowRates = new float[0];
        potableWaterInActualFlowRates = new float[0];
        potableWaterInDesiredFlowRates = new float[0];
        O2MaxFlowRates = new float[0];
        O2ActualFlowRates = new float[0];
        O2DesiredFlowRates = new float[0];
        H2OutputMaxFlowRates = new float[0];
        H2OutputActualFlowRates = new float[0];
        H2OutputDesiredFlowRates = new float[0];
    }

    /**
     * Resets production/consumption levels.
     */
    public void reset() {
        super.reset();
        currentH2OConsumed = 0;
        currentO2Produced = 0;
        currentH2Produced = 0;
        currentPowerConsumed = 0;
    }
    
    private void gatherPower() {
        currentPowerConsumed = getMostResourceFromStore(myPowerStores, powerMaxFlowRates, powerDesiredFlowRates, powerActualFlowRates);
    }
    
    public void tick(){
        super.tick();
        gatherPower();
        gatherWater();
        pushGasses();
    }
    

    private void gatherWater() {
        // 75 Watts -> 0.04167 liters of water
        float waterToConsume = (currentPowerConsumed / 75f) * 0.04167f;
        currentH2OConsumed = getResourceFromStore(getPotableWaterInputs(), getPotableWaterInputMaxFlowRates(), getPotableWaterInputDesiredFlowRates(), getPotableWaterInputActualFlowRates(), waterToConsume);
    }

    private void pushGasses() {
        //2H20 --> 2H2 + O2
        float molesOfWater = (currentH2OConsumed * 1000f) / 18.01524f; //1000g/liter,
        // 18.01524g/mole
        float molesOfReactant = molesOfWater / 2f;
        currentO2Produced = randomFilter(molesOfReactant)
                * myProductionRate;
        currentH2Produced = randomFilter(molesOfReactant * 2f)
                * myProductionRate;
        float O2ToDistrubute = randomFilter(currentO2Produced);
        float H2ToDistrubute = randomFilter(currentH2Produced);
        float distributedO2 = SimBioModuleImpl.pushResourceToStore(getO2Outputs(), getO2OutputMaxFlowRates(), getO2OutputDesiredFlowRates(), getO2OutputActualFlowRates(), O2ToDistrubute);
        float distributedH2 = SimBioModuleImpl.pushResourceToStore(getH2Outputs(), getH2OutputMaxFlowRates(), getH2OutputDesiredFlowRates(), getH2OutputActualFlowRates(), H2ToDistrubute);
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

    public void setPotableWaterInputDesiredFlowRate(float watts, int index) {
        potableWaterInDesiredFlowRates[index] = watts;
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
}