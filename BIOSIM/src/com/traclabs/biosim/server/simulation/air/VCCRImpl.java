package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.idl.simulation.air.Breath;
import com.traclabs.biosim.idl.simulation.air.CO2Store;
import com.traclabs.biosim.idl.simulation.air.VCCROperations;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.framework.AirConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.AirProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.CO2ProducerOperations;
import com.traclabs.biosim.idl.simulation.framework.PowerConsumerOperations;
import com.traclabs.biosim.idl.simulation.power.PowerStore;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;

/**
 * The Air Revitalization System Implementation. Takes in Air (O2, CO2, other)
 * from the environment and power from the Power Production System and produces
 * air with less CO2 and more O2.
 * 
 * @author Scott Bell
 */

public class VCCRImpl extends SimBioModuleImpl implements VCCROperations,
        PowerConsumerOperations, AirConsumerOperations, AirProducerOperations,
        CO2ProducerOperations {

    private PowerStore[] myPowerStores;

    private CO2Store[] myCO2OutputStores;

    private SimEnvironment[] mySimEnvironmentInputs;

    private SimEnvironment[] mySimEnvironmentOutputs;

    private float[] powerMaxFlowRates;

    private float[] powerActualFlowRates;

    private float[] powerDesiredFlowRates;

    private float[] CO2OutputMaxFlowRates;

    private float[] CO2OutputActualFlowRates;

    private float[] CO2OutputDesiredFlowRates;

    private float[] airInMaxFlowRates;

    private float[] airInActualFlowRates;

    private float[] airInDesiredFlowRates;

    private float[] airOutMaxFlowRates;

    private float[] airOutActualFlowRates;

    private float[] airOutDesiredFlowRates;

    private Breath myBreath;

    private float currentCO2Produced = 0f;

    private float currentPowerConsumed = 0;

    public VCCRImpl(int pID, String pName) {
        super(pID, pName);
        myBreath = new Breath(0f, 0f, 0f, 0f, 0f);

        myPowerStores = new PowerStore[0];
        myCO2OutputStores = new CO2Store[0];
        mySimEnvironmentInputs = new SimEnvironment[0];
        mySimEnvironmentOutputs = new SimEnvironment[0];

        powerMaxFlowRates = new float[0];
        powerActualFlowRates = new float[0];
        powerDesiredFlowRates = new float[0];
        CO2OutputMaxFlowRates = new float[0];
        CO2OutputActualFlowRates = new float[0];
        CO2OutputDesiredFlowRates = new float[0];
        airInMaxFlowRates = new float[0];
        airInActualFlowRates = new float[0];
        airInDesiredFlowRates = new float[0];
        airOutMaxFlowRates = new float[0];
        airOutActualFlowRates = new float[0];
        airOutDesiredFlowRates = new float[0];
    }

    /**
     * Processes a tick by collecting referernces (if needed), resources, and
     * pushing the new air out.
     */
    public void tick() {
        super.tick();
        gatherPower();
        gatherAir();
        pushAir();
    }

    /**
     * Adds power to the subsystem for this tick
     */
    protected void gatherPower() {
        currentPowerConsumed = getMostResourceFromStore(myPowerStores,
                powerMaxFlowRates, powerDesiredFlowRates, powerActualFlowRates);
    }

    /**
     * Resets production/consumption levels.
     */
    public void reset() {
        super.reset();
        myBreath = new Breath(0f, 0f, 0f, 0f, 0f);
        currentPowerConsumed = 0;
        currentCO2Produced = 0f;
    }

    private void gatherAir() {
        //25.625 watts -> 1.2125 moles of Air
        float molesAirNeeded = (currentPowerConsumed / 25.625f) * 1.2125f;
        float airNeededFiltered = randomFilter(molesAirNeeded);
        float gatheredAir = 0f;
        float gatheredO2 = 0f;
        float gatheredCO2 = 0f;
        float gatheredOther = 0f;
        float gatheredWater = 0f;
        float gatheredNitrogen = 0f;
        for (int i = 0; (i < getAirInputs().length)
                && (gatheredAir < airNeededFiltered); i++) {
            float resourceToGatherFirst = Math.min(airNeededFiltered,
                    getAirInputMaxFlowRate(i));
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
        myBreath.O2 = gatheredO2;
        myBreath.CO2 = gatheredCO2;
        myBreath.other = gatheredOther;
        myBreath.water = gatheredWater;
        myBreath.nitrogen = gatheredNitrogen;
    }

    private void pushAir() {
        float distributedO2Left = myBreath.O2;
        float distributedOtherLeft = myBreath.other;
        float distributedWaterLeft = myBreath.water;
        float distributedNitrogenLeft = myBreath.nitrogen;
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
            float O2Added = getAirOutputs()[i].addO2Moles(reducedO2ToPass);
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
            setAirOutputActualFlowRate(reducedO2ToPass + reducedOtherToPass
                    + reducedWaterToPass + reducedNitrogenToPass, i);
        }
        currentCO2Produced = myBreath.CO2;
        float distributedCO2Left = pushResourceToStore(getCO2Outputs(),
                getCO2OutputMaxFlowRates(), getCO2OutputDesiredFlowRates(),
                getCO2OutputActualFlowRates(), currentCO2Produced);
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

}