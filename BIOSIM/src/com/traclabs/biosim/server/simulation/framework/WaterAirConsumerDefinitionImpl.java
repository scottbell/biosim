package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.framework.WaterAirConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.WaterAirConsumerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.framework.WaterAirConsumerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.framework.WaterAirConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.water.WaterStore;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class WaterAirConsumerDefinitionImpl extends StoreEnvironmentFlowRateControllableImpl implements WaterAirConsumerDefinitionOperations {
    private WaterAirConsumerDefinition myWaterAirConsumerDefinition;
    
    public WaterAirConsumerDefinitionImpl(){
        myWaterAirConsumerDefinition = WaterAirConsumerDefinitionHelper.narrow(OrbUtils.poaToCorbaObj(new WaterAirConsumerDefinitionPOATie(this)));
    }
    
    public WaterAirConsumerDefinition getCorbaObject(){
        return myWaterAirConsumerDefinition;
    }
    
    public void setWaterAirEnvironmentInputs(SimEnvironment[] pEnvironments, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setEnvironments(pEnvironments);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }

    public void setWaterAirStoreInputs(WaterStore[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
    
    /**
     * Grabs as much Water as it can (i.e., the maxFlowRate) from environments.
     * @return The total amount of Water grabbed from the environments
     */
    public float getMostWaterFromEnvironment() {
        float gatheredWaterAir = 0f;
        for (int i = 0; i < getEnvironments().length; i++) {
            float amountToTake = Math.min(getEnvironmentMaxFlowRate(i),
                    getEnvironmentDesiredFlowRate(i));
            getEnvironmentActualFlowRates()[i] = getEnvironments()[i]
                    .takeWaterMoles(amountToTake);
            gatheredWaterAir += getEnvironmentActualFlowRate(i);
        }
        return gatheredWaterAir;
    }

}