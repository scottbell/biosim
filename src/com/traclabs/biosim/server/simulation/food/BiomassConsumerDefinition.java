package com.traclabs.biosim.server.simulation.food;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * @author Scott Bell
 */

public class BiomassConsumerDefinition extends
        StoreFlowRateControllable {

    public BiomassConsumerDefinition(BioModule pModule) {
super(pModule);
    }

    public void setBiomassInputs(BiomassStore[] pStores, float[] pMaxFlowRates,
            float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }

    public BioMatter[] getBioMassFromStore(float amountNeeded) {
        float gatheredResource = 0f;
        List<BioMatter[]> gatheredBioMatterArrays = new Vector<BioMatter[]>();
        int sizeOfMatterArray = 0;
        for (int i = 0; (i < getStores().length)
                && (gatheredResource < amountNeeded); i++) {
            float resourceToGatherFirst = Math.min(amountNeeded,
                    getMaxFlowRate(i));
            float resourceToGatherFinal = Math.min(resourceToGatherFirst,
                    getDesiredFlowRate(i));
            BiomassStore currentBiomassStore = (BiomassStore)(getStores()[i]);
            BioMatter[] takenMatter = currentBiomassStore
                    .takeBioMatterMass(resourceToGatherFinal);
            sizeOfMatterArray += takenMatter.length;
            gatheredBioMatterArrays.add(takenMatter);
            getActualFlowRates()[i] = calculateSizeOfBioMatter(takenMatter);
            gatheredResource += getActualFlowRate(i);
        }
        BioMatter[] fullMatterTaken = new BioMatter[sizeOfMatterArray];
        int lastPosition = 0;
        for (Iterator iter = gatheredBioMatterArrays.iterator(); iter.hasNext();) {
            BioMatter[] matterArray = (BioMatter[]) (iter.next());
            System.arraycopy(matterArray, 0, fullMatterTaken, lastPosition,
                    matterArray.length);
            lastPosition += matterArray.length;
        }
        return fullMatterTaken;
    }

    private static float calculateSizeOfBioMatter(BioMatter[] arrayOfMatter) {
        float totalSize = 0f;
        for (int i = 0; i < arrayOfMatter.length; i++)
            totalSize += arrayOfMatter[i].mass;
        return totalSize;
    }
}