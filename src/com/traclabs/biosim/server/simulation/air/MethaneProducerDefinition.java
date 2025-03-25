package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.simulation.air.MethaneProducerDefinition;
import com.traclabs.biosim.server.simulation.air.MethaneProducerDefinitionOperations;
import com.traclabs.biosim.server.simulation.air.MethaneProducerDefinitionPOATie;
import com.traclabs.biosim.server.simulation.air.MethaneStore;
import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class MethaneProducerDefinition extends
        StoreFlowRateControllable implements
        MethaneProducerDefinitionOperations {
    private MethaneProducerDefinition myMethaneProducerDefinition;

    public MethaneProducerDefinition(BioModule pModule) {
     super(pModule);    	MethaneProducerDefinitionPOATie tie = new MethaneProducerDefinitionPOATie(this);
    	myMethaneProducerDefinition = tie._this(OrbUtils.getORB());
    }

    public MethaneProducerDefinition getCorbaObject() {
        return myMethaneProducerDefinition;
    }

    public void setMethaneOutputs(MethaneStore[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}