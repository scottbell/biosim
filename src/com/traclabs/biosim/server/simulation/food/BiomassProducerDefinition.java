package com.traclabs.biosim.server.simulation.food;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;

/**
 * @author Scott Bell
 */

public class BiomassProducerDefinition extends
        StoreFlowRateControllable {
    private BiomassProducerDefinition myBiomassProducerDefinition;

    public BiomassProducerDefinition(BioModule pModule) {
        super(pModule);
    }

    public void setBiomassOutputs(BiomassStore[] pStores,
                                  float[] pMaxFlowRates, float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
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
            BiomassStore currentBiomassStore = (BiomassStore) (getStores()[i]);
            getActualFlowRates()[i] += currentBiomassStore
                    .addBioMatter(newBioMatter);
            resourceDistributed -= getActualFlowRate(i);
        }
        return (matterToPush.mass - resourceDistributed);
    }
}