package com.traclabs.biosim.server.simulation.waste;

import java.util.Arrays;
import java.util.Iterator;

import com.traclabs.biosim.idl.framework.CO2ProducerOperations;
import com.traclabs.biosim.idl.framework.DryWasteConsumerOperations;
import com.traclabs.biosim.idl.framework.Malfunction;
import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.framework.O2ConsumerOperations;
import com.traclabs.biosim.idl.framework.PowerConsumerOperations;
import com.traclabs.biosim.idl.simulation.air.CO2Store;
import com.traclabs.biosim.idl.simulation.air.O2Store;
import com.traclabs.biosim.idl.simulation.power.PowerStore;
import com.traclabs.biosim.idl.simulation.waste.DryWasteStore;
import com.traclabs.biosim.idl.simulation.waste.IncineratorOperations;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;

/**
 * The Incinerator takes dryWaste (plants matter) and refines it to food for the
 * crew members.
 * 
 * @author Scott Bell
 */

public class IncineratorImpl extends SimBioModuleImpl implements
        IncineratorOperations, PowerConsumerOperations,
        DryWasteConsumerOperations, O2ConsumerOperations, CO2ProducerOperations {
    //During any given tick, this much power is needed for the food processor
    // to run at all
    private float powerNeeded = 100;

    //During any given tick, this much dry waste is needed for the incinerator
    // to run optimally
    private float dryWasteNeeded = 10f;

    //During any given tick, this much O2 is needed for the incinerator to run
    // optimally
    private float O2Needed = 10f;

    //Flag switched when the Incinerator has collected references to other
    // servers it need
    private boolean hasCollectedReferences = false;

    //Flag to determine if the Incinerator has enough power to function
    private boolean hasEnoughPower = false;

    //Flag to determine if the Incinerator has enough dryWaste to function
    // nominally
    private boolean hasEnoughDryWaste = false;

    //Flag to determine if the Incinerator has enough O2 to function nominally
    private boolean hasEnoughO2 = false;

    //The dryWaste consumed (in kilograms) by the Incinerator at the current
    // tick
    private float currentDryWasteConsumed = 0f;

    //The power consumed (in watts) by the Incinerator at the current tick
    private float currentPowerConsumed = 0f;

    //The oxygen consumed (in moles) by the Incinerator at the current tick
    private float currentO2Consumed = 0f;

    //The CO2 produced (in moles) by the Incinerator at the current tick
    private float currentCO2Produced = 0f;

    //References to the servers the Incinerator takes/puts resources (like
    // power, dryWaste, etc)
    private float myProductionRate = 1f;

    private PowerStore[] myPowerStores;

    private DryWasteStore[] myDryWasteStores;

    private O2Store[] myO2Stores;

    private CO2Store[] myCO2Stores;

    private float[] powerMaxFlowRates;

    private float[] powerActualFlowRates;

    private float[] powerDesiredFlowRates;

    private float[] dryWasteActualFlowRates;

    private float[] dryWasteMaxFlowRates;

    private float[] dryWasteDesiredFlowRates;

    private float[] O2ActualFlowRates;

    private float[] O2MaxFlowRates;

    private float[] O2DesiredFlowRates;

    private float[] CO2ActualFlowRates;

    private float[] CO2MaxFlowRates;

    private float[] CO2DesiredFlowRates;

    public IncineratorImpl(int pID, String pName) {
        super(pID, pName);
        myO2Stores = new O2Store[0];
        myCO2Stores = new CO2Store[0];
        myPowerStores = new PowerStore[0];
        myDryWasteStores = new DryWasteStore[0];
        powerMaxFlowRates = new float[0];
        powerDesiredFlowRates = new float[0];
        powerActualFlowRates = new float[0];
        dryWasteMaxFlowRates = new float[0];
        dryWasteActualFlowRates = new float[0];
        dryWasteDesiredFlowRates = new float[0];
        O2MaxFlowRates = new float[0];
        O2ActualFlowRates = new float[0];
        O2DesiredFlowRates = new float[0];
        CO2MaxFlowRates = new float[0];
        CO2ActualFlowRates = new float[0];
        CO2DesiredFlowRates = new float[0];
    }

    /**
     * Resets production/consumption levels
     */
    public void reset() {
        super.reset();
        currentDryWasteConsumed = 0f;
        currentPowerConsumed = 0f;
        currentO2Consumed = 0f;
        currentCO2Produced = 0f;
    }

    /**
     * Returns the dryWaste consumed (in kilograms) by the Incinerator during
     * the current tick
     * 
     * @return the dryWaste consumed (in kilograms) by the Incinerator during
     *         the current tick
     */
    public float getDryWasteConsumed() {
        return currentDryWasteConsumed;
    }

    /**
     * Returns the power consumed (in watts) by the Incinerator during the
     * current tick
     * 
     * @return the power consumed (in watts) by the Incinerator during the
     *         current tick
     */
    public float getPowerConsumed() {
        return currentPowerConsumed;
    }

    /**
     * Returns the O2 consumed (in moles) by the Incinerator during the current
     * tick
     * 
     * @return the O2 consumed (in moles) by the Incinerator during the current
     *         tick
     */
    public float getO2Consumed() {
        return currentO2Consumed;
    }

    /**
     * Returns the CO2 produced (in moles) by the Incinerator during the current
     * tick
     * 
     * @return the CO2 produced (in moles) by the Incinerator during the current
     *         tick
     */
    public float getCO2Produced() {
        return currentCO2Produced;
    }

    /**
     * Checks whether Incinerator has enough power or not
     * 
     * @return <code>true</code> if the Incinerator has enough power,
     *         <code>false</code> if not.
     */
    public boolean hasPower() {
        return hasEnoughPower;
    }

    /**
     * Checks whether Incinerator has enough dryWaste to run optimally or not
     * 
     * @return <code>true</code> if the Incinerator has enough dryWaste,
     *         <code>false</code> if not.
     */
    public boolean hasDryWaste() {
        return hasEnoughDryWaste;
    }

    /**
     * Checks whether Incinerator has enough O2 to run optimally or not
     * 
     * @return <code>true</code> if the Incinerator has enough O2,
     *         <code>false</code> if not.
     */
    public boolean hasO2() {
        return hasEnoughO2;
    }

    /**
     * Attempts to collect enough power from the Power PS to run the Incinerator
     * for one tick.
     */
    private void gatherPower() {
        currentPowerConsumed = getResourceFromStore(myPowerStores,
                powerMaxFlowRates, powerDesiredFlowRates, powerActualFlowRates,
                powerNeeded);
        if (currentPowerConsumed < powerNeeded)
            hasEnoughPower = false;
        else
            hasEnoughPower = true;
    }

    /**
     * Attempts to collect enough dryWaste from the DryWaste Store to run the
     * Incinerator optimally for one tick.
     */
    private void gatherDryWaste() {
        currentDryWasteConsumed = getResourceFromStore(myDryWasteStores,
                dryWasteMaxFlowRates, dryWasteDesiredFlowRates,
                dryWasteActualFlowRates, dryWasteNeeded);
        if (currentDryWasteConsumed < dryWasteNeeded)
            hasEnoughDryWaste = false;
        else
            hasEnoughDryWaste = true;
    }

    /**
     * Attempts to collect enough O2 from the O2 Store to run the Incinerator
     * optimally for one tick.
     */
    private void gatherO2() {
        currentO2Consumed = getResourceFromStore(myO2Stores, O2MaxFlowRates,
                O2DesiredFlowRates, O2ActualFlowRates, O2Needed);
        if (currentO2Consumed < O2Needed)
            hasEnoughO2 = false;
        else
            hasEnoughO2 = true;
    }

    /**
     * Attempts to consume resource (power and dryWaste) for Incinerator
     */
    private void consumeResources() {
        gatherPower();
        if (hasEnoughPower) {
            gatherDryWaste();
            gatherO2();
        } else {
            currentO2Consumed = 0f;
            Arrays.fill(O2ActualFlowRates, 0f);
            currentDryWasteConsumed = 0f;
            Arrays.fill(dryWasteActualFlowRates, 0f);
        }
    }

    private void createCO2() {
        currentCO2Produced = currentO2Consumed * myProductionRate;
        float distributedCO2Left = pushResourceToStore(myCO2Stores,
                CO2MaxFlowRates, CO2DesiredFlowRates, CO2ActualFlowRates,
                currentCO2Produced);
    }

    private void setProductionRate(float pProductionRate) {
        myProductionRate = pProductionRate;
    }

    protected void performMalfunctions() {
        float productionRate = 1f;
        for (Iterator iter = myMalfunctions.values().iterator(); iter.hasNext();) {
            Malfunction currentMalfunction = (Malfunction) (iter.next());
            if (currentMalfunction.getLength() == MalfunctionLength.TEMPORARY_MALF) {
                if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
                    productionRate *= 0.50;
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
                    productionRate *= 0.25;
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
                    productionRate *= 0.10;
            } else if (currentMalfunction.getLength() == MalfunctionLength.PERMANENT_MALF) {
                if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
                    productionRate *= 0.50;
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
                    productionRate *= 0.25;
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
                    productionRate *= 0.10;
            }
        }
        setProductionRate(productionRate);
    }

    /**
     * When ticked, the Incinerator does the following: 1) attempts to collect
     * references to various server (if not already done). 2) consumes power and
     * dryWaste. 3) creates food (if possible)
     */
    public void tick() {
        super.tick();
        consumeResources();
        createCO2();
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
            returnBuffer.append("Production Rate Decrease (Temporary)");
        else if (pLength == MalfunctionLength.PERMANENT_MALF)
            returnBuffer.append("Production Rate Decrease (Permanent)");
        return returnBuffer.toString();
    }

    public void log() {
        /*
         * LogNode powerNeededHead = myLog.addChild("power_needed");
         * myLogIndex.powerNeededIndex =
         * powerNeededHead.addChild(""+powerNeeded); LogNode hasEnoughPowerHead =
         * myLog.addChild("has_enough_power"); myLogIndex.hasEnoughPowerIndex =
         * hasEnoughPowerHead.addChild(""+hasEnoughPower); LogNode
         * dryWasteNeededHead = myLog.addChild("dryWaste_needed");
         * myLogIndex.dryWasteNeededIndex =
         * dryWasteNeededHead.addChild(""+dryWasteNeeded); LogNode
         * currentDryWasteConsumedHead =
         * myLog.addChild("current_dryWaste_consumed");
         * myLogIndex.currentDryWasteConsumedIndex =
         * currentDryWasteConsumedHead.addChild(""+currentDryWasteConsumed);
         * LogNode currentPowerConsumedHead =
         * myLog.addChild("current_power_consumed");
         * myLogIndex.currentPowerConsumedIndex =
         * currentPowerConsumedHead.addChild(""+currentPowerConsumed);
         */
    }

    //Power Input
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

    //DryWaste Input
    public void setDryWasteInputMaxFlowRate(float kilograms, int index) {
        dryWasteMaxFlowRates[index] = kilograms;
    }

    public float getDryWasteInputMaxFlowRate(int index) {
        return dryWasteMaxFlowRates[index];
    }

    public float[] getDryWasteInputMaxFlowRates() {
        return dryWasteMaxFlowRates;
    }

    public void setDryWasteInputDesiredFlowRate(float kilograms, int index) {
        dryWasteDesiredFlowRates[index] = kilograms;
    }

    public float getDryWasteInputDesiredFlowRate(int index) {
        return dryWasteDesiredFlowRates[index];
    }

    public float[] getDryWasteInputDesiredFlowRates() {
        return dryWasteDesiredFlowRates;
    }

    public float getDryWasteInputActualFlowRate(int index) {
        return dryWasteActualFlowRates[index];
    }

    public float[] getDryWasteInputActualFlowRates() {
        return dryWasteActualFlowRates;
    }

    public void setDryWasteInputs(DryWasteStore[] sources,
            float[] maxFlowRates, float[] desiredFlowRates) {
        myDryWasteStores = sources;
        dryWasteMaxFlowRates = maxFlowRates;
        dryWasteDesiredFlowRates = desiredFlowRates;
        dryWasteActualFlowRates = new float[dryWasteDesiredFlowRates.length];
    }

    public DryWasteStore[] getDryWasteInputs() {
        return myDryWasteStores;
    }

    //O2 Input
    public void setO2InputMaxFlowRate(float kilograms, int index) {
        O2MaxFlowRates[index] = kilograms;
    }

    public float getO2InputMaxFlowRate(int index) {
        return O2MaxFlowRates[index];
    }

    public float[] getO2InputMaxFlowRates() {
        return O2MaxFlowRates;
    }

    public void setO2InputDesiredFlowRate(float kilograms, int index) {
        O2DesiredFlowRates[index] = kilograms;
    }

    public float getO2InputDesiredFlowRate(int index) {
        return O2DesiredFlowRates[index];
    }

    public float[] getO2InputDesiredFlowRates() {
        return O2DesiredFlowRates;
    }

    public float getO2InputActualFlowRate(int index) {
        return O2ActualFlowRates[index];
    }

    public float[] getO2InputActualFlowRates() {
        return O2ActualFlowRates;
    }

    public void setO2Inputs(O2Store[] sources, float[] maxFlowRates,
            float[] desiredFlowRates) {
        myO2Stores = sources;
        O2MaxFlowRates = maxFlowRates;
        O2DesiredFlowRates = desiredFlowRates;
        O2ActualFlowRates = new float[O2DesiredFlowRates.length];
    }

    public O2Store[] getO2Inputs() {
        return myO2Stores;
    }

    //CO2 Output
    public void setCO2OutputMaxFlowRate(float kilograms, int index) {
        CO2MaxFlowRates[index] = kilograms;
    }

    public float getCO2OutputMaxFlowRate(int index) {
        return CO2MaxFlowRates[index];
    }

    public float[] getCO2OutputMaxFlowRates() {
        return CO2MaxFlowRates;
    }

    public void setCO2OutputDesiredFlowRate(float kilograms, int index) {
        CO2DesiredFlowRates[index] = kilograms;
    }

    public float getCO2OutputDesiredFlowRate(int index) {
        return CO2DesiredFlowRates[index];
    }

    public float[] getCO2OutputDesiredFlowRates() {
        return CO2DesiredFlowRates;
    }

    public float getCO2OutputActualFlowRate(int index) {
        return CO2ActualFlowRates[index];
    }

    public float[] getCO2OutputActualFlowRates() {
        return CO2ActualFlowRates;
    }

    public void setCO2Outputs(CO2Store[] destinations, float[] maxFlowRates,
            float[] desiredFlowRates) {
        myCO2Stores = destinations;
        CO2MaxFlowRates = maxFlowRates;
        CO2DesiredFlowRates = desiredFlowRates;
        CO2ActualFlowRates = new float[CO2DesiredFlowRates.length];
    }

    public CO2Store[] getCO2Outputs() {
        return myCO2Stores;
    }
}