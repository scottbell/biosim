package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.air.CO2Store;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.framework.CO2AirConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.CO2AirConsumerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.framework.CO2AirConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.framework.CO2AirConsumerDefinitionPOATie;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class CO2AirConsumerDefinitionImpl extends StoreEnvironmentFlowRateControllableImpl implements CO2AirConsumerDefinitionOperations {
    private CO2AirConsumerDefinition myCO2AirConsumerDefinition;
    
    public CO2AirConsumerDefinitionImpl(){
        myCO2AirConsumerDefinition = CO2AirConsumerDefinitionHelper.narrow(OrbUtils.poaToCorbaObj(new CO2AirConsumerDefinitionPOATie(this)));
    }
    
    public CO2AirConsumerDefinition getCorbaObject(){
        return myCO2AirConsumerDefinition;
    }
    
    public void setCO2AirEnvironmentInputs(SimEnvironment[] pEnvironments, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setEnvironments(pEnvironments);
        setEnvironmentMaxFlowRates(pMaxFlowRates);
        setEnvironmentDesiredFlowRates(pDesiredFlowRates);
    }

    public void setCO2AirStoreInputs(CO2Store[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setStoreMaxFlowRates(pMaxFlowRates);
        setStoreDesiredFlowRates(pDesiredFlowRates);
    }
    
    /**
     * Grabs as much CO2 as it can (i.e., the maxFlowRate) from environments.
     * @return The total amount of CO2 grabbed from the environments
     */
    public float getMostCO2FromEnvironment() {
        if (getEnvironments() == null)
            return 0f;
        float gatheredCO2Air = 0f;
        for (int i = 0; i < getEnvironments().length; i++) {
            float amountToTake = Math.min(getEnvironmentMaxFlowRate(i),
                    getEnvironmentDesiredFlowRate(i));
            getEnvironmentActualFlowRates()[i] = getEnvironments()[i]
                    .takeCO2Moles(amountToTake);
            gatheredCO2Air += getEnvironmentActualFlowRate(i);
        }
        return gatheredCO2Air;
    }
    
    

}