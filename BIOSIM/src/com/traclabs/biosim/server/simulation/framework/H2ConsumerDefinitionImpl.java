package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.air.H2Store;
import com.traclabs.biosim.idl.simulation.framework.H2ConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.framework.H2ConsumerDefinition;
import com.traclabs.biosim.idl.simulation.framework.H2ConsumerDefinitionHelper;
import com.traclabs.biosim.idl.simulation.framework.H2ConsumerDefinitionPOATie;
import com.traclabs.biosim.server.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class H2ConsumerDefinitionImpl extends StoreFlowRateControllableImpl implements H2ConsumerDefinitionOperations {
    private H2ConsumerDefinition myH2ConsumerDefinition;
    
    public H2ConsumerDefinitionImpl(){
        myH2ConsumerDefinition = H2ConsumerDefinitionHelper.narrow(OrbUtils.poaToCorbaObj(new H2ConsumerDefinitionPOATie(this)));
    }
    
    public H2ConsumerDefinition getCorbaObject(){
        return myH2ConsumerDefinition;
    }
    public void setH2Inputs(H2Store[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
}