package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.simulation.air.MethaneConsumerDefinition;
import com.traclabs.biosim.server.simulation.air.MethaneConsumerDefinitionOperations;
import com.traclabs.biosim.server.simulation.air.MethaneConsumerDefinitionPOATie;
import com.traclabs.biosim.server.simulation.air.MethaneStore;
import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class MethaneConsumerDefinition extends StoreFlowRateControllable  {
    private MethaneConsumerDefinition myMethaneConsumerDefinition;

    public MethaneConsumerDefinition(BioModule pModule) {
        super(pModule);

    	MethaneConsumerDefinitionPOATie tie = new MethaneConsumerDefinitionPOATie(this);
    	myMethaneConsumerDefinition = tie._this(OrbUtils.getORB());
    }

    public MethaneConsumerDefinition getCorbaObject() {
        return myMethaneConsumerDefinition;
    }

    public void setMethaneInputs(MethaneStore[] pStores,
            float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}