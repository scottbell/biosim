package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.air.NitrogenStore;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.framework.NitrogenAirConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.NitrogenAirConsumerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.framework.NitrogenAirConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.framework.NitrogenAirConsumerDefinitionPOATie;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class NitrogenAirConsumerDefinitionImpl extends
        StoreEnvironmentFlowRateControllableImpl implements
        NitrogenAirConsumerDefinitionOperations {
    private NitrogenAirConsumerDefinition myNitrogenAirConsumerDefinition;

    public NitrogenAirConsumerDefinitionImpl() {
        myNitrogenAirConsumerDefinition = NitrogenAirConsumerDefinitionHelper
                .narrow(OrbUtils
                        .poaToCorbaObj(new NitrogenAirConsumerDefinitionPOATie(
                                this)));
    }

    public NitrogenAirConsumerDefinition getCorbaObject() {
        return myNitrogenAirConsumerDefinition;
    }

    public void setNitrogenAirEnvironmentInputs(SimEnvironment[] pEnvironments,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setEnvironments(pEnvironments);
        setEnvironmentMaxFlowRates(pMaxFlowRates);
        setEnvironmentDesiredFlowRates(pDesiredFlowRates);
    }

    public void setNitrogenAirStoreInputs(NitrogenStore[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setStoreMaxFlowRates(pMaxFlowRates);
        setStoreDesiredFlowRates(pDesiredFlowRates);
    }

    /**
     * Grabs as much Nitrogen as it can (i.e., the maxFlowRate) from
     * environments.
     * 
     * @return The total amount of Nitrogen grabbed from the environments
     */
    public float getMostNitrogenFromEnvironment() {
        if (getEnvironments() == null)
            return 0f;
        float gatheredNitrogenAirConsumer = 0f;
        for (int i = 0; i < getEnvironments().length; i++) {
            float amountToTake = Math.min(getEnvironmentMaxFlowRate(i),
                    getEnvironmentDesiredFlowRate(i));
            getEnvironmentActualFlowRates()[i] = getEnvironments()[i]
                    .takeNitrogenMoles(amountToTake);
            gatheredNitrogenAirConsumer += getEnvironmentActualFlowRate(i);
        }
        return gatheredNitrogenAirConsumer;
    }

}