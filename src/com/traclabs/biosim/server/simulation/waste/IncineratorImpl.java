package com.traclabs.biosim.server.simulation.waste;

import java.util.Arrays;
import java.util.Iterator;

import com.traclabs.biosim.idl.framework.Malfunction;
import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.simulation.air.CO2ProducerDefinition;
import com.traclabs.biosim.idl.simulation.air.CO2ProducerOperations;
import com.traclabs.biosim.idl.simulation.air.O2ConsumerDefinition;
import com.traclabs.biosim.idl.simulation.air.O2ConsumerOperations;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerDefinition;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerOperations;
import com.traclabs.biosim.idl.simulation.waste.DryWasteConsumerDefinition;
import com.traclabs.biosim.idl.simulation.waste.DryWasteConsumerOperations;
import com.traclabs.biosim.idl.simulation.waste.IncineratorOperations;
import com.traclabs.biosim.server.simulation.air.CO2ProducerDefinitionImpl;
import com.traclabs.biosim.server.simulation.air.O2ConsumerDefinitionImpl;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
import com.traclabs.biosim.server.simulation.power.PowerConsumerDefinitionImpl;

/**
 * The Incinerator takes dryWaste (plants matter) and refines it to food for the
 * crew members.
 * 
 * @author Scott Bell
 */

public class IncineratorImpl extends SimBioModuleImpl implements
        IncineratorOperations, PowerConsumerOperations,
        DryWasteConsumerOperations, O2ConsumerOperations, CO2ProducerOperations {
    //Consumers, Producers
    private PowerConsumerDefinitionImpl myPowerConsumerDefinitionImpl;

    private DryWasteConsumerDefinitionImpl myDryWasteConsumerDefinitionImpl;

    private O2ConsumerDefinitionImpl myO2ConsumerDefinitionImpl;

    private CO2ProducerDefinitionImpl myCO2ProducerDefinitionImpl;

    //During any given tick, this much power is needed for the incinerator
    // to run at all
    private static final float POWER_NEEDED_BASE = 100;

    //During any given tick, this much dry waste is needed for the incinerator
    // to run optimally
    private static final float DRY_WASTE_NEEDED_BASE = 10f;

    //During any given tick, this much O2 is needed for the incinerator to run
    // optimally
    private static final float O2_NEEDED_BASE = 10f;

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

    public IncineratorImpl(int pID, String pName) {
        super(pID, pName);
        myPowerConsumerDefinitionImpl = new PowerConsumerDefinitionImpl(this);
        myO2ConsumerDefinitionImpl = new O2ConsumerDefinitionImpl(this);
        myDryWasteConsumerDefinitionImpl = new DryWasteConsumerDefinitionImpl(this);
        myCO2ProducerDefinitionImpl = new CO2ProducerDefinitionImpl(this);
    }

    public PowerConsumerDefinition getPowerConsumerDefinition() {
        return myPowerConsumerDefinitionImpl.getCorbaObject();
    }

    public O2ConsumerDefinition getO2ConsumerDefinition() {
        return myO2ConsumerDefinitionImpl.getCorbaObject();
    }

    public DryWasteConsumerDefinition getDryWasteConsumerDefinition() {
        return myDryWasteConsumerDefinitionImpl.getCorbaObject();
    }

    public CO2ProducerDefinition getCO2ProducerDefinition() {
        return myCO2ProducerDefinitionImpl.getCorbaObject();
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
        myPowerConsumerDefinitionImpl.reset();
        myO2ConsumerDefinitionImpl.reset();
        myDryWasteConsumerDefinitionImpl.reset();
        myCO2ProducerDefinitionImpl.reset();
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
    	float powerNeeded = POWER_NEEDED_BASE * getTickLength();
        currentPowerConsumed = myPowerConsumerDefinitionImpl
                .getResourceFromStores(powerNeeded);
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
    	float dryWasteNeeded = DRY_WASTE_NEEDED_BASE * getTickLength();
        currentDryWasteConsumed = myDryWasteConsumerDefinitionImpl
                .getResourceFromStores(dryWasteNeeded);
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
    	float o2Needed = O2_NEEDED_BASE * getTickLength();
        currentO2Consumed = myO2ConsumerDefinitionImpl
                .getResourceFromStores(o2Needed);
        if (currentO2Consumed < o2Needed)
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
            Arrays.fill(myO2ConsumerDefinitionImpl.getActualFlowRates(), 0f);
            currentDryWasteConsumed = 0f;
            Arrays.fill(myDryWasteConsumerDefinitionImpl.getActualFlowRates(),
                    0f);
        }
    }

    private void createCO2() {
        currentCO2Produced = currentO2Consumed * myProductionRate;
        myCO2ProducerDefinitionImpl
                .pushResourceToStores(currentCO2Produced);
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
                    productionRate *= 0.50; // 50% reduction
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
                    productionRate *= 0.75; // 25% reduction
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
                    productionRate *= 0.90; // 10% reduction
            } else if (currentMalfunction.getLength() == MalfunctionLength.PERMANENT_MALF) {
                if (currentMalfunction.getIntensity() == MalfunctionIntensity.SEVERE_MALF)
                    productionRate *= 0.50; // 50% reduction
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.MEDIUM_MALF)
                    productionRate *= 0.75; // 25% reduction
                else if (currentMalfunction.getIntensity() == MalfunctionIntensity.LOW_MALF)
                    productionRate *= 0.90; // 10% reduction
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
        myLogger.debug("power_needed=" + POWER_NEEDED_BASE);
        myLogger.debug("has_enough_power=" + hasEnoughPower);
        myLogger.debug("dryWaste_needed=" + DRY_WASTE_NEEDED_BASE);
        myLogger.debug("current_dryWaste_consumed=" + currentDryWasteConsumed);
        myLogger.debug("current_power_consumed=" + currentPowerConsumed);
    }
}
