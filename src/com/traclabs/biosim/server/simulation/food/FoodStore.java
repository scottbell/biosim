package com.traclabs.biosim.server.simulation.food;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import com.traclabs.biosim.server.framework.Malfunction;
import com.traclabs.biosim.server.simulation.food.FoodMatter;
import com.traclabs.biosim.server.simulation.food.FoodStoreOperations;
import com.traclabs.biosim.server.simulation.food.PlantType;
import com.traclabs.biosim.server.simulation.framework.Store;

/**
 * The Food Store ementation. Takes raw plant matter from the Food RS to be
 * used by the Food Processor.
 * 
 * @author Scott Bell
 */

public class FoodStore extends Store implements FoodStoreOperations {
    private List<FoodMatter> currentFoodItems;

    private FoodMatter myOriginalMatter;

    public FoodStore(int pID, String pName) {
        super(pID, pName);
        currentFoodItems = new Vector<FoodMatter>();
        myOriginalMatter = new FoodMatter(0, 0, PlantType.UNKNOWN_PLANT);
    }

    public FoodStore() {
		this(0, "Unnamed Food Store");
	}

	public float add(float pMass) {
        FoodMatter newFoodMatter = new FoodMatter(pMass, 0,
                PlantType.UNKNOWN_PLANT);
        return addFoodMatterMass(newFoodMatter);
    }

    public void setInitialLevel(float metricAmount) {
        super.setInitialLevel(metricAmount);
        setCurrentLevel(metricAmount);
    }

    public void setCurrentLevel(float metricAmount) {
        super.setCurrentLevel(metricAmount);
        currentFoodItems.clear();
        if (metricAmount > 0) {
            FoodMatter newFoodMatter = new FoodMatter(metricAmount, 0,
                    PlantType.UNKNOWN_PLANT);
            currentFoodItems.add(newFoodMatter);
            myOriginalMatter = newFoodMatter;
        }
    }

    public void setInitialFoodMatterLevel(FoodMatter pMatter) {
        super.setInitialLevel(pMatter.mass);
        currentFoodItems.clear();
        if (pMatter.mass > 0) {
            myOriginalMatter = cloneMatter(pMatter);
            currentFoodItems.add(pMatter);
        }
    }

    /**
     * @param matter
     * @return
     */
    private static FoodMatter cloneMatter(FoodMatter matter) {
        return new FoodMatter(matter.mass, matter.waterContent, matter.type);
    }

    public float take(float pMass) {
        FoodMatter[] massArray = takeFoodMatterMass(pMass);
        float matterToReturn = 0f;
        for (int i = 0; i < massArray.length; i++)
            matterToReturn += massArray[i].mass;
        return matterToReturn;
    }

    public float addFoodMatterArray(FoodMatter[] pFood) {
        float totalAdded = 0f;
        for (int i = 0; i < pFood.length; i++) {
            FoodMatter currentFood = pFood[i];
            if (currentFood != null) {
                float currentAdded = addFoodMatterMass(currentFood);
                totalAdded += currentAdded;
                currentFood.mass -= currentAdded;
            }
        }
        return totalAdded;
    }
    
    /**
     * Actually performs the malfunctions. Reduces levels/currentCapacity
     */
    @Override
    protected void performMalfunctions() {
        for (Iterator iter = myMalfunctions.values().iterator(); iter.hasNext();) {
            Malfunction currentMalfunction = (Malfunction) (iter.next());
            setCurrentLevel(0);
            setCurrentCapacity(0);
            currentMalfunction.setPerformed(true);
        }
    }

    public float addFoodMatterMass(FoodMatter pMatter) {
        float acutallyAdded = 0f;
        if ((pMatter.mass + currentLevel) > currentCapacity) {
            //adding more than currentCapacity
            acutallyAdded = currentCapacity - currentLevel;
            currentLevel += acutallyAdded;
            overflow += (pMatter.mass - acutallyAdded);
            float fractionOfOriginal = acutallyAdded / pMatter.mass;

            FoodMatter newFoodMatter = new FoodMatter(acutallyAdded,
                    pMatter.waterContent * fractionOfOriginal, pMatter.type);
            currentFoodItems.add(newFoodMatter);
            return acutallyAdded;
        }
		acutallyAdded = pMatter.mass;
		currentLevel += acutallyAdded;
		currentFoodItems.add(pMatter);
		return acutallyAdded;
    }

    public FoodMatter[] takeFoodMatterMass(float pMass) {
        List<FoodMatter> itemsToReturn = new Vector<FoodMatter>();
        List<FoodMatter> itemsToRemove = new Vector<FoodMatter>();
        float collectedMass = 0f;
        for (Iterator iter = currentFoodItems.iterator(); iter.hasNext()
                && (collectedMass <= pMass);) {
            FoodMatter currentFoodMatter = (FoodMatter) (iter.next());
            float massStillNeeded = pMass - collectedMass;
            //we need to get more bio matter
            if (currentFoodMatter.mass < massStillNeeded) {
                itemsToReturn.add(currentFoodMatter);
                itemsToRemove.add(currentFoodMatter);
                collectedMass += currentFoodMatter.mass;
            }
            //we have enough, let's cut up the biomass (if too much)
            else if (currentFoodMatter.mass >= massStillNeeded) {
                float fractionOfOriginal = massStillNeeded
                        / currentFoodMatter.mass;
                FoodMatter partialReturnedFoodMatter = new FoodMatter(
                        massStillNeeded, currentFoodMatter.waterContent
                                * fractionOfOriginal, currentFoodMatter.type);
                currentFoodMatter.mass -= partialReturnedFoodMatter.mass;
                itemsToReturn.add(partialReturnedFoodMatter);
                if (currentFoodMatter.mass <= 0)
                    itemsToRemove.add(currentFoodMatter);
                collectedMass += partialReturnedFoodMatter.mass;
            }
        }
        //Remove items from List
        for (Iterator iter = itemsToRemove.iterator(); iter.hasNext();) {
            currentFoodItems.remove(iter.next());
        }
        currentLevel -= collectedMass;
        //return the array
        FoodMatter[] returnArrayType = new FoodMatter[0];
        return (itemsToReturn.toArray(returnArrayType));
    }

    private static float calculateCaloriesSingular(FoodMatter pFood) {
        PlantType theType = pFood.type;
        float theMass = pFood.mass;
        if (theType == PlantType.DRY_BEAN)
            return theMass * DryBean.getCaloriesPerKilogram();
        else if (theType == PlantType.LETTUCE)
            return theMass * Lettuce.getCaloriesPerKilogram();
        else if (theType == PlantType.PEANUT)
            return theMass * Peanut.getCaloriesPerKilogram();
        else if (theType == PlantType.SOYBEAN)
            return theMass * Soybean.getCaloriesPerKilogram();
        else if (theType == PlantType.SWEET_POTATO)
            return theMass * SweetPotato.getCaloriesPerKilogram();
        else if (theType == PlantType.TOMATO)
            return theMass * Tomato.getCaloriesPerKilogram();
        else if (theType == PlantType.WHEAT)
            return theMass * Wheat.getCaloriesPerKilogram();
        else if (theType == PlantType.WHITE_POTATO)
            return theMass * WhitePotato.getCaloriesPerKilogram();
        else
            return theMass * 1000f;
    }

    public static float calculateWaterContent(FoodMatter[] foodArray) {
        float totalWater = 0f;
        for (int i = 0; i < foodArray.length; i++) {
            totalWater += foodArray[i].waterContent;
        }
        return totalWater;
    }

    public static float calculateCalories(FoodMatter[] foodArray) {
        float totalCalories = 0f;
        for (int i = 0; i < foodArray.length; i++) {
            totalCalories += calculateCaloriesSingular(foodArray[i]);
        }
        return totalCalories;
    }

    public FoodMatter[] takeFoodMatterCalories(float pCalories,
            float limitingMass) {
        List<FoodMatter> itemsToReturn = new Vector<FoodMatter>();
        List<FoodMatter> itemsToRemove = new Vector<FoodMatter>();
        float collectedCalories = 0f;
        float collectedMass = 0f;
        for (Iterator iter = currentFoodItems.iterator(); iter.hasNext()
                && (collectedCalories < pCalories)
                && (collectedMass < limitingMass);) {
            FoodMatter currentFoodMatter = (FoodMatter) (iter.next());
            float currentCalories = calculateCaloriesSingular(currentFoodMatter);
            float caloriesStillNeeded = pCalories - collectedCalories;

            //Too many calories or too big, need to pare down to flowrate
            // or pare down to needed calories
            if (((currentCalories > caloriesStillNeeded) || (collectedMass + currentFoodMatter.mass) > limitingMass)) {

                float paredToFlowrateCalories = currentCalories;
                FoodMatter paredToFlowrateFoodMatter = cloneMatter(currentFoodMatter);

                //pare it to mass if necessary
                if (currentFoodMatter.mass > limitingMass) {
                    float flowRateMass = limitingMass - collectedMass;
                    float flowrateFractionOfOriginal = flowRateMass
                            / currentFoodMatter.mass;

                    paredToFlowrateFoodMatter = new FoodMatter(limitingMass
                            - collectedMass, currentFoodMatter.waterContent
                            * flowrateFractionOfOriginal,
                            currentFoodMatter.type);
                    paredToFlowrateCalories = calculateCaloriesSingular(paredToFlowrateFoodMatter);
                }
                
                float paredToCaloriesMassToKeep = paredToFlowrateFoodMatter.mass;
                //pare it to calories if necessary
                if (paredToFlowrateCalories > caloriesStillNeeded){
                    float fractionOfMassToKeepForCalories = (paredToFlowrateCalories - caloriesStillNeeded)
                        / paredToFlowrateCalories;
                    paredToCaloriesMassToKeep = paredToFlowrateFoodMatter.mass
                        * fractionOfMassToKeepForCalories;
                }
                
                float massToReturn = paredToFlowrateFoodMatter.mass - paredToCaloriesMassToKeep;
                float caloriesFractionOfOriginal = massToReturn
                        / paredToFlowrateFoodMatter.mass;
                float waterToReturn = caloriesFractionOfOriginal
                        * paredToFlowrateFoodMatter.waterContent;

                currentFoodMatter.mass = currentFoodMatter.mass - massToReturn;
                currentFoodMatter.waterContent = currentFoodMatter.waterContent
                        - waterToReturn;

                FoodMatter partialReturnedFoodMatter = new FoodMatter(
                        massToReturn, waterToReturn, currentFoodMatter.type);
                itemsToReturn.add(partialReturnedFoodMatter);
                if (currentFoodMatter.mass <= 0)
                    itemsToRemove.add(currentFoodMatter);
                collectedMass += partialReturnedFoodMatter.mass;
                collectedCalories = pCalories;
            }
            //Not enough calories and too big. Pair down to flowrate
            else if (((collectedMass + currentFoodMatter.mass) > limitingMass)
                    && (currentCalories <= caloriesStillNeeded)) {
                float flowRateMass = limitingMass - collectedMass;
                float flowrateFractionOfOriginal = flowRateMass
                        / currentFoodMatter.mass;
                FoodMatter paredToFlowrateFoodMatter = new FoodMatter(
                        limitingMass - collectedMass,
                        currentFoodMatter.waterContent
                                * flowrateFractionOfOriginal,
                        currentFoodMatter.type);
                currentFoodMatter.mass -= paredToFlowrateFoodMatter.mass;
                itemsToReturn.add(paredToFlowrateFoodMatter);
                if (currentFoodMatter.mass <= 0)
                    itemsToRemove.add(currentFoodMatter);
                collectedMass += paredToFlowrateFoodMatter.mass;
                collectedCalories = calculateCaloriesSingular(paredToFlowrateFoodMatter);
            }
            //Too many calories and small. Pair down to calories
            else if (((collectedMass + currentFoodMatter.mass) <= limitingMass)
                    && (currentCalories > caloriesStillNeeded)) {
                float fractionOfMassToKeep = (currentCalories - caloriesStillNeeded)
                        / currentCalories;
                float massToKeep = currentFoodMatter.mass
                        * fractionOfMassToKeep;
                float massToReturn = currentFoodMatter.mass - massToKeep;
                currentFoodMatter.mass = massToKeep;
                FoodMatter partialReturnedFoodMatter = new FoodMatter(
                        massToReturn, currentFoodMatter.waterContent
                                * fractionOfMassToKeep, currentFoodMatter.type);

                itemsToReturn.add(partialReturnedFoodMatter);
                if (currentFoodMatter.mass <= 0)
                    itemsToRemove.add(currentFoodMatter);
                collectedMass += partialReturnedFoodMatter.mass;
                collectedCalories = pCalories;
            }
            //Too little calories and small. Just add
            else {
                itemsToReturn.add(currentFoodMatter);
                itemsToRemove.add(currentFoodMatter);
                collectedCalories += currentCalories;
                collectedMass += currentFoodMatter.mass;
            }
        }
        //Remove items from List
        for (Iterator iter = itemsToRemove.iterator(); iter.hasNext();) {
            currentFoodItems.remove(iter.next());
        }
        currentLevel -= collectedMass;
        //return the array
        FoodMatter[] returnArray = new FoodMatter[0];
        returnArray = (itemsToReturn.toArray(returnArray));
        return returnArray;
    }

    public void reset() {
        super.reset();
        currentFoodItems.clear();
        if (currentLevel > 0)
            currentFoodItems.add(cloneMatter(myOriginalMatter));
    }
}