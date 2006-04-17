package com.traclabs.biosim.server.simulation.food;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.traclabs.biosim.idl.simulation.food.BioMatter;
import com.traclabs.biosim.idl.simulation.food.BiomassConsumerDefinition;
import com.traclabs.biosim.idl.simulation.food.BiomassConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.food.BiomassConsumerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.food.BiomassStore;
import com.traclabs.biosim.idl.simulation.food.BiomassStoreHelper;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllableImpl;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class BiomassConsumerDefinitionImpl extends
        StoreFlowRateControllableImpl implements
        BiomassConsumerDefinitionOperations {
    private BiomassConsumerDefinition myBiomassConsumerDefinition;

    public BiomassConsumerDefinitionImpl() {

    	BiomassConsumerDefinitionPOATie tie = new BiomassConsumerDefinitionPOATie(this);
    	myBiomassConsumerDefinition = tie._this(OrbUtils.getORB());
    }

    public BiomassConsumerDefinition getCorbaObject() {
        return myBiomassConsumerDefinition;
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
            BiomassStore currentBiomassStore = BiomassStoreHelper
                    .narrow(getStores()[i]);
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