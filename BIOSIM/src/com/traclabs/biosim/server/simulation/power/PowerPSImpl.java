package com.traclabs.biosim.server.simulation.power;

import java.util.Iterator;

import com.traclabs.biosim.idl.framework.Malfunction;
import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.framework.LightConsumerOperations;
import com.traclabs.biosim.idl.simulation.framework.PowerProducerOperations;
import com.traclabs.biosim.idl.simulation.power.PowerPSOperations;
import com.traclabs.biosim.idl.simulation.power.PowerStore;
import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;

/**
 * The Power Production System creates power from a generator (say a solar
 * panel) and stores it in the power store. This provides power to all the
 * biomodules in the system.
 * 
 * @author Scott Bell
 */

public abstract class PowerPSImpl extends SimBioModuleImpl implements
        PowerPSOperations, PowerProducerOperations, LightConsumerOperations {
    //The power produced (in watts) by the Power PS at the current tick
    float currentPowerProduced = 0f;

    //Flag switched when the Power PS has collected references to other servers
    // it need
    private boolean hasCollectedReferences = false;

    private PowerStore[] myPowerStores;

    private float[] powerMaxFlowRates;

    private float[] powerActualFlowRates;

    private float[] powerDesiredFlowRates;

    private float upperPowerGeneration = 500f;

    private SimEnvironment myLightInput;

    public PowerPSImpl(int pID, String pName) {
        super(pID, pName);
        myPowerStores = new PowerStore[0];
        powerMaxFlowRates = new float[0];
        powerActualFlowRates = new float[0];
        powerDesiredFlowRates = new float[0];
    }

    /**
     * When ticked, the PowerPS does the following: 1) attempts to collect
     * references to various server (if not already done). 2) creates power and
     * places it into the power store.
     */
    public void tick() {
        super.tick();
        currentPowerProduced = calculatePowerProduced();
        float distributedPowerLeft = pushResourceToStore(myPowerStores,
                powerMaxFlowRates, powerDesiredFlowRates, powerActualFlowRates,
                currentPowerProduced);
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
        currentPowerProduced *= productionRate;
    }

    abstract float calculatePowerProduced();

    /**
     * Reset does nothing right now
     */
    public void reset() {
        super.reset();
        currentPowerProduced = 0f;
    }

    /**
     * Returns the power produced (in watts) by the Power PS during the current
     * tick
     * 
     * @return the power produced (in watts) by the Power PS during the current
     *         tick
     */
    public float getPowerProduced() {
        return currentPowerProduced;
    }

    public void log() {
        myLogger.debug("power_produced=" + currentPowerProduced);
    }

    //Power Outputs
    public void setPowerOutputMaxFlowRate(float watts, int index) {
        powerMaxFlowRates[index] = watts;
    }

    public float getPowerOutputMaxFlowRate(int index) {
        return powerMaxFlowRates[index];
    }

    public float[] getPowerOutputMaxFlowRates() {
        return powerMaxFlowRates;
    }

    public void setPowerOutputDesiredFlowRate(float watts, int index) {
        powerDesiredFlowRates[index] = watts;
    }

    public float getPowerOutputDesiredFlowRate(int index) {
        return powerDesiredFlowRates[index];
    }

    public float[] getPowerOutputDesiredFlowRates() {
        return powerDesiredFlowRates;
    }

    public float getPowerOutputActualFlowRate(int index) {
        return powerActualFlowRates[index];
    }

    public float[] getPowerOutputActualFlowRates() {
        return powerActualFlowRates;
    }

    public void setPowerOutputs(PowerStore[] destinations,
            float[] maxFlowRates, float[] desiredFlowRates) {
        myPowerStores = destinations;
        powerMaxFlowRates = maxFlowRates;
        powerDesiredFlowRates = desiredFlowRates;
        powerActualFlowRates = new float[powerDesiredFlowRates.length];
    }

    public PowerStore[] getPowerOutputs() {
        return myPowerStores;
    }

    //Light Inputs
    public void setLightInput(SimEnvironment source) {
        myLightInput = source;
    }

    public SimEnvironment getLightInput() {
        return myLightInput;
    }

    /**
     * @return Returns the upperPowerGeneration.
     */
    public float getUpperPowerGeneration() {
        return upperPowerGeneration;
    }

    /**
     * @param upperPowerGeneration
     *            The upperPowerGeneration to set.
     */
    public void setUpperPowerGeneration(float upperPowerGeneration) {
        this.upperPowerGeneration = upperPowerGeneration;
    }
}