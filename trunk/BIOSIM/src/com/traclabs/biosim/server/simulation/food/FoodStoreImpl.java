package biosim.server.simulation.food;

import biosim.idl.simulation.food.*;
import biosim.server.simulation.framework.*;
import java.util.*;
import biosim.idl.framework.*;
/**
 * The Food Store Implementation.  Takes raw plant matter from the Food RS to be used by the Food Processor.
 *
 * @author    Scott Bell
 */

public class FoodStoreImpl extends StoreImpl implements FoodStoreOperations{
	List currentFoodItems;

	public FoodStoreImpl(int pID, String pName){
		super(pID, pName);
		currentFoodItems = new Vector();
	}
	
	public float add(float pMass){
		return addFoodMatterMass(pMass, PlantType.UNKNOWN_PLANT);
	}
	
	public void setLevel(float metricAmount){
		super.setLevel(metricAmount);
		currentFoodItems.clear();
		if (metricAmount > 0){
			FoodMatter newFoodMatter = new FoodMatter(metricAmount, PlantType.UNKNOWN_PLANT);
			currentFoodItems.add(newFoodMatter);
		}
	}
	
	public float take(float pMass){
		FoodMatter[] massArray = takeFoodMatterMass(pMass);
		float matterToReturn = 0f;
		for (int i = 0; i < massArray.length; i++)
			matterToReturn += massArray[i].mass;
		return matterToReturn;
	}
	
	public float addFoodMatterArray(FoodMatter[] pFood){
		float totalAdded = 0f;
		for (int i = 0; i < pFood.length; i++){
			FoodMatter currentFood = pFood[i];
			if (currentFood != null){
				float currentAdded = addFoodMatterMass(currentFood.mass, currentFood.type);
				totalAdded += currentAdded;
				currentFood.mass -= currentAdded;
			}
		}
		return totalAdded;
	}

	public float addFoodMatterMass(float pMass, PlantType pType){
		float acutallyAdded = 0f;
		if (pMass == 0)
			return 0f;
		if ((pMass + level) > capacity){
			//adding more than capacity
			acutallyAdded = capacity - level;
			level += acutallyAdded;
			overflow += (pMass - acutallyAdded);
			FoodMatter newFoodMatter = new FoodMatter(acutallyAdded, pType);
			currentFoodItems.add(newFoodMatter);
			return acutallyAdded;
		}
		else{
			acutallyAdded = randomFilter(pMass);
			level += acutallyAdded;
			FoodMatter newFoodMatter = new FoodMatter(acutallyAdded, pType);
			currentFoodItems.add(newFoodMatter);
			return acutallyAdded;
		}
	}

	public FoodMatter[] takeFoodMatterMass(float pMass){
		List itemsToReturn = new Vector();
		List itemsToRemove = new Vector();
		float collectedMass = 0f;
		for (Iterator iter = currentFoodItems.iterator(); iter.hasNext() &&  (collectedMass <= pMass);){
			FoodMatter currentFoodMatter = (FoodMatter)(iter.next());
			float massStillNeeded = pMass - collectedMass;
			//we need to get more bio matter
			if (currentFoodMatter.mass < massStillNeeded){
				itemsToReturn.add(currentFoodMatter);
				itemsToRemove.add(currentFoodMatter);
				collectedMass += currentFoodMatter.mass;
			}
			//we have enough, let's cut up the biomass (if too much)
			else if (currentFoodMatter.mass >= massStillNeeded){
				FoodMatter partialReturnedFoodMatter = new FoodMatter(massStillNeeded, currentFoodMatter.type);
				currentFoodMatter.mass -= partialReturnedFoodMatter.mass;
				itemsToReturn.add(partialReturnedFoodMatter);
				if (currentFoodMatter.mass <= 0)
					itemsToRemove.add(currentFoodMatter);
				collectedMass += partialReturnedFoodMatter.mass;
			}
		}
		//Remove items from List
		for (Iterator iter = itemsToRemove.iterator(); iter.hasNext();){
			currentFoodItems.remove(iter.next());
		}
		level -= collectedMass;
		//return the array
		FoodMatter[] returnArrayType = new FoodMatter[0];
		return (FoodMatter[])(itemsToReturn.toArray(returnArrayType));
	}
	
	private static float calculateCaloriesSingular(FoodMatter pFood){
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
	
	private static float calculateWaterContentSingular(FoodMatter pFood){
		PlantType theType = pFood.type;
		if (theType == PlantType.DRY_BEAN)
			return pFood.mass * DryBean.getEdibleFreshBasisWaterContent();
		else if (theType == PlantType.LETTUCE)
			return pFood.mass * Lettuce.getEdibleFreshBasisWaterContent();
		else if (theType == PlantType.PEANUT)
			return pFood.mass * Peanut.getEdibleFreshBasisWaterContent();
		else if (theType == PlantType.SOYBEAN)
			return pFood.mass * Soybean.getEdibleFreshBasisWaterContent();
		else if (theType == PlantType.SWEET_POTATO)
			return pFood.mass * SweetPotato.getEdibleFreshBasisWaterContent();
		else if (theType == PlantType.TOMATO)
			return pFood.mass * Tomato.getEdibleFreshBasisWaterContent();
		else if (theType == PlantType.WHEAT)
			return pFood.mass * Wheat.getEdibleFreshBasisWaterContent();
		else if (theType == PlantType.WHITE_POTATO)
			return pFood.mass * WhitePotato.getEdibleFreshBasisWaterContent();
		else
			return pFood.mass * 0.5f;
	}
	
	public float calculateWaterContent(FoodMatter[] foodArray){
		float totalWater = 0f;
		for (int i = 0; i < foodArray.length; i++){
			totalWater += calculateWaterContentSingular(foodArray[i]);
		}
		return totalWater;
	}
	
	public float calculateCalories(FoodMatter[] foodArray){
		float totalCalories = 0f;
		for (int i = 0; i < foodArray.length; i++){
			totalCalories += calculateCaloriesSingular(foodArray[i]);
		}
		return totalCalories;
	}
	
	public FoodMatter[] takeFoodMatterCalories(float pCalories, float limitingMass){
		List itemsToReturn = new Vector();
		List itemsToRemove = new Vector();
		float collectedCalories = 0f;
		float collectedMass = 0f;
		for (Iterator iter = currentFoodItems.iterator(); iter.hasNext() &&  (collectedCalories < pCalories) && (collectedMass < limitingMass);){
			FoodMatter currentFoodMatter = (FoodMatter)(iter.next());
			float currentCalories = calculateCaloriesSingular(currentFoodMatter);
			float caloriesStillNeeded = pCalories - collectedCalories;
			
			//Too many calories and too big, need to pair down to flowrate, then pair down to needed calories
			if (((currentCalories > caloriesStillNeeded) || (collectedMass + currentFoodMatter.mass) > limitingMass)){
				//pair it to mass
				FoodMatter pairedToFlowrateFoodMatter = new FoodMatter(limitingMass - collectedMass, currentFoodMatter.type);
				float pairedToFlowrateCalories = calculateCaloriesSingular(pairedToFlowrateFoodMatter);
				//pair it to calories
				float fractionOfMassToKeep = (pairedToFlowrateCalories - caloriesStillNeeded) / pairedToFlowrateCalories;
				float pairedToCaloriesMassToKeep = pairedToFlowrateFoodMatter.mass * fractionOfMassToKeep;
				float massToReturn = pairedToFlowrateFoodMatter.mass - pairedToCaloriesMassToKeep;
				currentFoodMatter.mass -= massToReturn;
				
				FoodMatter partialReturnedFoodMatter = new FoodMatter(massToReturn, currentFoodMatter.type);
				itemsToReturn.add(partialReturnedFoodMatter);
				if (currentFoodMatter.mass <= 0)
					itemsToRemove.add(currentFoodMatter);
				collectedMass += partialReturnedFoodMatter.mass;
				collectedCalories = pCalories;
			}
			//Not enough calories and too big.  Pair down to flowrate
			else if (((collectedMass + currentFoodMatter.mass) > limitingMass) && (currentCalories <= caloriesStillNeeded)){
				FoodMatter partialReturnedFoodMatter = new FoodMatter(limitingMass - collectedMass, currentFoodMatter.type);
				currentFoodMatter.mass -= partialReturnedFoodMatter.mass;
				itemsToReturn.add(partialReturnedFoodMatter);
				if (currentFoodMatter.mass <= 0)
					itemsToRemove.add(currentFoodMatter);
				collectedMass += partialReturnedFoodMatter.mass;
				collectedCalories = calculateCaloriesSingular(partialReturnedFoodMatter);
			}
			//Too many calories and small.  Pair down to calories
			else if (((collectedMass + currentFoodMatter.mass) <= limitingMass) && (currentCalories > caloriesStillNeeded)){
				float fractionOfMassToKeep = (currentCalories - caloriesStillNeeded) / currentCalories;
				float massToKeep = currentFoodMatter.mass * fractionOfMassToKeep;
				float massToReturn = currentFoodMatter.mass - massToKeep;
				currentFoodMatter.mass = massToKeep;
				FoodMatter partialReturnedFoodMatter = new FoodMatter(massToReturn, currentFoodMatter.type);
				
				itemsToReturn.add(partialReturnedFoodMatter);
				if (currentFoodMatter.mass <= 0)
					itemsToRemove.add(currentFoodMatter);
				collectedMass += partialReturnedFoodMatter.mass;
				collectedCalories = pCalories;
			}
			//Too little calories and small.  Just add
			else{
				itemsToReturn.add(currentFoodMatter);
				itemsToRemove.add(currentFoodMatter);
				collectedCalories += currentCalories;
				collectedMass += currentFoodMatter.mass;
			}
		}
		//Remove items from List
		for (Iterator iter = itemsToRemove.iterator(); iter.hasNext();){
			currentFoodItems.remove(iter.next());
		}
		level -= collectedMass;
		//return the array
		FoodMatter[] returnArray = new FoodMatter[0];
		returnArray = (FoodMatter[])(itemsToReturn.toArray(returnArray));
		return returnArray;
	}

	public FoodMatter takeFoodMatterMassAndType(float pMass, PlantType pType){
		FoodMatter matterToReturn = new FoodMatter(0f, pType);
		List itemsToRemove = new Vector();
		for (Iterator iter = currentFoodItems.iterator(); iter.hasNext() &&  (matterToReturn.mass <= pMass);){
			FoodMatter currentFoodMatter = (FoodMatter)(iter.next());
			if (currentFoodMatter.type == pType){
				float massStillNeeded = pMass - matterToReturn.mass;
				//we need to get more bio matter
				if (currentFoodMatter.mass < massStillNeeded){
					matterToReturn.mass += currentFoodMatter.mass;
					itemsToRemove.add(currentFoodMatter);
				}
				//we have enough, let's cut up the biomass (if too much)
				else if (currentFoodMatter.mass >= massStillNeeded){
					float partialMassToReturn = massStillNeeded;
					currentFoodMatter.mass -= partialMassToReturn;
					matterToReturn.mass += partialMassToReturn;
					if (currentFoodMatter.mass <= 0)
						itemsToRemove.add(currentFoodMatter);
				}
			}
		}
		//Remove items from List
		for (Iterator iter = itemsToRemove.iterator(); iter.hasNext();){
			currentFoodItems.remove(iter.next());
		}
		level -= matterToReturn.mass;
		return matterToReturn;
	}

	public FoodMatter takeFoodMatterType(PlantType pType){
		FoodMatter matterToReturn = new FoodMatter(0f, pType);
		List itemsToRemove = new Vector();
		for (Iterator iter = currentFoodItems.iterator(); iter.hasNext();){
			FoodMatter currentFoodMatter = (FoodMatter)(iter.next());
			if (currentFoodMatter.type == pType){
				matterToReturn.mass += currentFoodMatter.mass;
				itemsToRemove.add(currentFoodMatter);
			}
		}
		//Remove items from List
		for (Iterator iter = itemsToRemove.iterator(); iter.hasNext();){
			currentFoodItems.remove(iter.next());
		}
		level -= matterToReturn.mass;
		return matterToReturn;
	}
	
	public void reset(){
		super.reset();
		currentFoodItems.clear();
		if (level > 0)
			currentFoodItems.add(new FoodMatter(level, PlantType.UNKNOWN_PLANT));
	}
}