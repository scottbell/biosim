package com.traclabs.biosim.server.simulation.power;

import java.util.Iterator;

import com.traclabs.biosim.idl.framework.Malfunction;
import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.simulation.environment.LightConsumerDefinition;
import com.traclabs.biosim.idl.simulation.environment.LightConsumerOperations;
import com.traclabs.biosim.idl.simulation.power.PowerPSOperations;
import com.traclabs.biosim.idl.simulation.power.PowerProducerDefinition;
import com.traclabs.biosim.idl.simulation.power.PowerProducerOperations;
import com.traclabs.biosim.server.simulation.environment.LightConsumerDefinitionImpl;
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

    private float currentUpperPowerGeneration = 500f;

    private float initialUpperPowerGeneration = 500f;

    //Consumers, Producers
    private PowerProducerDefinitionImpl myPowerProducerDefinitionImpl;

    private LightConsumerDefinitionImpl myLightConsumerDefinitionImpl;

    public PowerPSImpl(int pID, String pName) {
        super(pID, pName);
        myPowerProducerDefinitionImpl = new PowerProducerDefinitionImpl();
        myLightConsumerDefinitionImpl = new LightConsumerDefinitionImpl();
    }

    public PowerProducerDefinition getPowerProducerDefinition() {
        return myPowerProducerDefinitionImpl.getCorbaObject();
    }

    public LightConsumerDefinition getLightConsumerDefinition() {
        return myLightConsumerDefinitionImpl.getCorbaObject();
    }

    /**
     * When ticked, the PowerPS does the following: 1) attempts to collect
     * references to various server (if not already done). 2) creates power and
     * places it into the power store.
     */
    public void tick() {
        currentPowerProduced = calculatePowerProduced();
        super.tick();
        myPowerProducerDefinitionImpl
                .pushResourceToStore(currentPowerProduced);
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

    /**
     * @return Returns the currentUpperPowerGeneration.
     */
    public float getCurrentUpperPowerGeneration() {
        return currentUpperPowerGeneration;
    }

    /**
     * @param currentUpperPowerGeneration
     *            The currentUpperPowerGeneration to set.
     */
    public void setCurrentUpperPowerGeneration(
            float pCurrentUpperPowerGeneration) {
        currentUpperPowerGeneration = pCurrentUpperPowerGeneration;
    }

    /**
     * @return Returns the initialUpperPowerGeneration.
     */
    public float getInitialUpperPowerGeneration() {
        return initialUpperPowerGeneration;
    }

    /**
     * @param initialUpperPowerGeneration
     *            The initialUpperPowerGeneration to set.
     */
    public void setInitialUpperPowerGeneration(
            float pInitialUpperPowerGeneration) {
        setCurrentUpperPowerGeneration(pInitialUpperPowerGeneration);
        initialUpperPowerGeneration = pInitialUpperPowerGeneration;
    }

}