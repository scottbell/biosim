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

	public FoodStoreImpl(int pID){
		super(pID);
		currentFoodItems = new Vector();
	}
	
	public float add(float pMass){
		return addFoodMatter(pMass, PlantType.UNKNOWN_PLANT);
	}
	
	public float take(float pMass){
		FoodMatter[] massArray = takeFoodMatterMass(pMass);
		float matterToReturn = 0f;
		for (int i = 0; i < massArray.length; i++)
			matterToReturn += massArray[i].mass;
		return matterToReturn;
	}

	public float addFoodMatter(float pMass, PlantType pType){
		float acutallyAdded = 0f;
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
			//we need to get more bio matter
			if (currentFoodMatter.mass < (pMass - collectedMass)){
				itemsToReturn.add(currentFoodMatter);
				itemsToRemove.add(currentFoodMatter);
				collectedMass += currentFoodMatter.mass;
			}
			//we have enough, let's cut up the biomass (if too much)
			else if (currentFoodMatter.mass >= (pMass - collectedMass)){
				FoodMatter partialReturnedFoodMatter = new FoodMatter(pMass - collectedMass, currentFoodMatter.type);
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

	public FoodMatter takeFoodMatterMassAndType(float pMass, PlantType pType){
		FoodMatter matterToReturn = new FoodMatter(0f, pType);
		List itemsToRemove = new Vector();
		for (Iterator iter = currentFoodItems.iterator(); iter.hasNext() &&  (matterToReturn.mass <= pMass);){
			FoodMatter currentFoodMatter = (FoodMatter)(iter.next());
			if (currentFoodMatter.type == pType){
				//we need to get more bio matter
				if (currentFoodMatter.mass < (pMass - matterToReturn.mass)){
					matterToReturn.mass += currentFoodMatter.mass;
					itemsToRemove.add(currentFoodMatter);
				}
				//we have enough, let's cut up the biomass (if too much)
				else if (currentFoodMatter.mass >= (pMass - matterToReturn.mass)){
					float partialMassToReturn = (pMass - matterToReturn.mass);
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
	}

	/**
	* Returns the name of this module (FoodStore)
	* @return the name of this module
	*/
	public String getModuleName(){
		return "FoodStore"+getID();
	}
}
