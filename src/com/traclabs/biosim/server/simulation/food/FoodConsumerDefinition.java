package com.traclabs.biosim.server.simulation.food;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * @author Scott Bell
 */

public class FoodConsumerDefinition extends StoreFlowRateControllable {

    public FoodConsumerDefinition(BioModule pModule) {
        super(pModule);

    }

    public static FoodMatter[] getCaloriesFromStore(
            FoodConsumerDefinition pConsumer, float amountNeeded) {
        float gatheredResource = 0f;
        List<FoodMatter[]> gatheredFoodMatterArrays = new Vector<FoodMatter[]>();
        int sizeOfMatter = 0;
        for (int i = 0; (i < pConsumer.getStores().length)
                && (gatheredResource < amountNeeded); i++) {
            float limitingMassFactor = Math.min(
                    pConsumer.getDesiredFlowRate(i), pConsumer
                            .getMaxFlowRate(i));
            FoodStore currentFoodStore = (FoodStore) (pConsumer.getStores()[i]);
            FoodMatter[] takenMatter = currentFoodStore.takeFoodMatterCalories(
                    amountNeeded, limitingMassFactor);
            sizeOfMatter += takenMatter.length;
            gatheredFoodMatterArrays.add(takenMatter);
            pConsumer.getActualFlowRates()[i] += calculateSizeOfFoodMatter(takenMatter);
            gatheredResource += FoodStore.calculateCalories(takenMatter);
        }
        FoodMatter[] fullMatterTaken = new FoodMatter[sizeOfMatter];
        int lastPosition = 0;
        for (Iterator iter = gatheredFoodMatterArrays.iterator(); iter.hasNext(); ) {
            FoodMatter[] matterArray = (FoodMatter[]) (iter.next());
            System.arraycopy(matterArray, 0, fullMatterTaken, lastPosition,
                    matterArray.length);
            lastPosition += matterArray.length;
        }
        return fullMatterTaken;
    }

    private static float calculateSizeOfFoodMatter(FoodMatter[] arrayOfMatter) {
        float totalSize = 0f;
        for (int i = 0; i < arrayOfMatter.length; i++)
            totalSize += arrayOfMatter[i].mass;
        return totalSize;
    }

    public void setFoodInputs(FoodStore[] pStores, float[] pMaxFlowRates,
                              float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
    }
}