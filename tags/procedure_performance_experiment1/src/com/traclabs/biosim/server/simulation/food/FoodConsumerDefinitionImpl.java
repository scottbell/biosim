package com.traclabs.biosim.server.simulation.food;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.traclabs.biosim.idl.simulation.food.FoodConsumerDefinition;
import com.traclabs.biosim.idl.simulation.food.FoodConsumerDefinitionOperations;
import com.traclabs.biosim.idl.simulation.food.FoodConsumerDefinitionPOATie;
import com.traclabs.biosim.idl.simulation.food.FoodMatter;
import com.traclabs.biosim.idl.simulation.food.FoodStore;
import com.traclabs.biosim.idl.simulation.food.FoodStoreHelper;
import com.traclabs.biosim.server.framework.BioModuleImpl;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllableImpl;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */

public class FoodConsumerDefinitionImpl extends StoreFlowRateControllableImpl
        implements FoodConsumerDefinitionOperations {
    private FoodConsumerDefinition myFoodConsumerDefinition;

    public FoodConsumerDefinitionImpl(BioModuleImpl pModule) {
super(pModule);
    	FoodConsumerDefinitionPOATie tie = new FoodConsumerDefinitionPOATie(this);
    	myFoodConsumerDefinition = tie._this(OrbUtils.getORB());
    	
    }

    public FoodConsumerDefinition getCorbaObject() {
        return myFoodConsumerDefinition;
    }

    public void setFoodInputs(FoodStore[] pStores, float[] pMaxFlowRates,
            float[] pDesiredFlowRates) {
        setInitialMaxFlowRates(pMaxFlowRates);
        setInitialDesiredFlowRates(pDesiredFlowRates);
        setInitialStores(pStores);
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
            FoodStore currentFoodStore = FoodStoreHelper.narrow(pConsumer
                    .getStores()[i]);
            FoodMatter[] takenMatter = currentFoodStore.takeFoodMatterCalories(
                    amountNeeded, limitingMassFactor);
            sizeOfMatter += takenMatter.length;
            gatheredFoodMatterArrays.add(takenMatter);
            pConsumer.getActualFlowRates()[i] += calculateSizeOfFoodMatter(takenMatter);
            gatheredResource += FoodStoreImpl.calculateCalories(takenMatter);
        }
        FoodMatter[] fullMatterTaken = new FoodMatter[sizeOfMatter];
        int lastPosition = 0;
        for (Iterator iter = gatheredFoodMatterArrays.iterator(); iter.hasNext();) {
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
}