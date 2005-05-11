package com.traclabs.biosim.server.simulation.environment;

import com.traclabs.biosim.idl.simulation.air.NitrogenStore;
import com.traclabs.biosim.idl.simulation.environment.NitrogenAirProducerDefinition;
import com.traclabs.biosim.idl.simulation.environment.NitrogenAirProducerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.environment.NitrogenAirProducerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.environment.NitrogenAirProducerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class NitrogenAirProducerDefinitionImpl extends
        StoreEnvironmentFlowRateControllableImpl implements
        NitrogenAirProducerDefinitionOperations {
    private NitrogenAirProducerDefinition myNitrogenAirProducerDefinition;

    public NitrogenAirProducerDefinitionImpl() {
        myNitrogenAirProducerDefinition = NitrogenAirProducerDefinitionHelper
                .narrow(OrbUtils
                        .poaToCorbaObj(new NitrogenAirProducerDefinitionPOATie(
                                this)));
    }

    public NitrogenAirProducerDefinition getCorbaObject() {
        return myNitrogenAirProducerDefinition;
    }

    public void setNitrogenAirEnvironmentOutputs(
            SimEnvironment[] pEnvironments, float[] pMaxFlowRates,
            float[] pDesiredFlowRates) {
        setEnvironments(pEnvironments);
        setEnvironmentMaxFlowRates(pMaxFlowRates);
        setEnvironmentDesiredFlowRates(pDesiredFlowRates);
    }

    public void setNitrogenAirStoreOutputs(NitrogenStore[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setStoreMaxFlowRates(pMaxFlowRates);
        setStoreDesiredFlowRates(pDesiredFlowRates);
    }

    /**
     * @return The total amount of Nitrogen pushed to the environments
     */
    public float pushNitrogenToEnvironment(float pMolesToPush) {
        if (getEnvironments() == null)
            return 0f;
        float NitrogenAirLeft = pMolesToPush;
        for (int i = 0; (i < getEnvironments().length) && (NitrogenAirLeft > 0); i++) {
            float amountToPushFirst = Math.min(getEnvironmentMaxFlowRate(i),
                    getEnvironmentDesiredFlowRate(i));
            float amountToPushFinal = Math.min(amountToPushFirst,
                    NitrogenAirLeft);
            getEnvironmentActualFlowRates()[i] = getEnvironments()[i]
                    .addNitrogenMoles(amountToPushFinal);
            NitrogenAirLeft -= getEnvironmentActualFlowRate(i);
        }
        return pMolesToPush - NitrogenAirLeft;
    }

}