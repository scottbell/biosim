package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.framework.WaterAirProducerDefinition;
import com.traclabs.biosim.idl.simulation.framework.WaterAirProducerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.framework.WaterAirProducerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.framework.WaterAirProducerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.water.WaterStore;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class WaterAirProducerDefinitionImpl extends StoreEnvironmentFlowRateControllableImpl implements WaterAirProducerDefinitionOperations {
    private WaterAirProducerDefinition myWaterAirProducerDefinition;
    
    public WaterAirProducerDefinitionImpl(){
        myWaterAirProducerDefinition = WaterAirProducerDefinitionHelper.narrow(OrbUtils.poaToCorbaObj(new WaterAirProducerDefinitionPOATie(this)));
    }
    
    public WaterAirProducerDefinition getCorbaObject(){
        return myWaterAirProducerDefinition;
    }
    
    public void setWaterAirEnvironmentOutputs(SimEnvironment[] pEnvironments, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setEnvironments(pEnvironments);
        setEnvironmentMaxFlowRates(pMaxFlowRates);
        setEnvironmentDesiredFlowRates(pDesiredFlowRates);
    }

    public void setWaterAirStoreOutputs(WaterStore[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setStoreMaxFlowRates(pMaxFlowRates);
        setStoreDesiredFlowRates(pDesiredFlowRates);
    }
    
    /**
     * @return The total amount of Water pushed to the environments
     */
    public float pushWaterToEnvironment(float pMolesToPush) {
        if (getEnvironments() == null)
            return 0f;
        float WaterAirLeft = pMolesToPush;
        for (int i = 0; (i < getEnvironments().length)
                && (WaterAirLeft > 0); i++) {
            float amountToPushFirst = Math.min(
                    getEnvironmentMaxFlowRate(i),
                    getEnvironmentDesiredFlowRate(i));
            float amountToPushFinal = Math.min(amountToPushFirst, WaterAirLeft);
            getEnvironmentActualFlowRates()[i] = getEnvironments()[i]
                    .addWaterMoles(amountToPushFinal);
            WaterAirLeft -= getEnvironmentActualFlowRate(i);
        }
        return pMolesToPush - WaterAirLeft;
    }

}