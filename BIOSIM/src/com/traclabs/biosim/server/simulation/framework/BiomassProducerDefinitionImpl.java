package com.traclabs.biosim.server.simulation.framework;

import com.traclabs.biosim.idl.simulation.food.BioMatter;
import com.traclabs.biosim.idl.simulation.food.BiomassStore;
import com.traclabs.biosim.idl.simulation.food.BiomassStoreHelper;
import com.traclabs.biosim.idl.simulation.framework.BiomassProducerDefinitionOperations;

/**
 * @author Scott Bell
 */

public class BiomassProducerDefinitionImpl extends StoreFlowRateControllableImpl implements BiomassProducerDefinitionOperations {
    public void setBiomassOutputs(BiomassStore[] pStores, float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setStores(pStores);
        setMaxFlowRates(pMaxFlowRates);
        setDesiredFlowRates(pDesiredFlowRates);
    }
    
    public float pushFractionalResourceToBiomassStore(BioMatter matterToPush,
            float shelfFraction) {
        float resourceDistributed = matterToPush.mass;
        for (int i = 0; (i < getStores().length) && (resourceDistributed > 0); i++) {
            float resourceToDistributeFirst = Math.min(resourceDistributed,
                    getMaxFlowRate(i) * shelfFraction);
            float resourceToDistributeFinal = Math.min(
                    resourceToDistributeFirst, getDesiredFlowRate(i)
                            * shelfFraction);

            float fractionOfOriginal = resourceToDistributeFinal
                    / resourceDistributed;
            BioMatter newBioMatter = new BioMatter(resourceToDistributeFinal,
                    matterToPush.inedibleFraction,
                    matterToPush.edibleWaterContent * fractionOfOriginal,
                    matterToPush.inedibleWaterContent * fractionOfOriginal,
                    matterToPush.type);
            BiomassStore currentBiomassStore = BiomassStoreHelper.narrow(getStores()[i]);
            getActualFlowRates()[i] += currentBiomassStore.addBioMatter(newBioMatter);
            resourceDistributed -= getActualFlowRate(i);
        }
        return (matterToPush.mass - resourceDistributed);
    }
}