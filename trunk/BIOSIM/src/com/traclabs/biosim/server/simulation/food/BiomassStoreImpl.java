package biosim.server.simulation.food;

import biosim.idl.simulation.food.*;
import biosim.server.simulation.framework.*;
import java.util.*;
import biosim.idl.framework.*;
/**
 * The Biomass Store Implementation.  Takes raw plant matter from the Biomass RS to be used by the Food Processor.
 *
 * @author    Scott Bell
 */

public class BiomassStoreImpl extends StoreImpl implements BiomassStoreOperations{
	List currentBiomassItems;

	public BiomassStoreImpl(int pID, String pName){
		super(pID, pName);
		currentBiomassItems = new Vector();
	}
	
	public float add(float pMass){
		return addBioMatter(pMass, PlantType.UNKNOWN_PLANT);
	}
	
	public void setLevel(float metricAmount){
		super.setLevel(metricAmount);
		currentBiomassItems.clear();
		if (metricAmount > 0){
			BioMatter newFoodMatter = new BioMatter(metricAmount, PlantType.UNKNOWN_PLANT);
			currentBiomassItems.add(newFoodMatter);
		}
	}
	
	public float take(float pMass){
		BioMatter[] massArray = takeBioMatterMass(pMass);
		float matterToReturn = 0f;
		for (int i = 0; i < massArray.length; i++)
			matterToReturn += massArray[i].mass;
		return matterToReturn;
	}

	public float addBioMatter(float pMass, PlantType pType){
		float acutallyAdded = 0f;
		if ((pMass + level) > capacity){
			//adding more than capacity
			acutallyAdded = capacity - level;
			level += acutallyAdded;
			overflow += (pMass - acutallyAdded);
			BioMatter newBioMatter = new BioMatter(acutallyAdded, pType);
			currentBiomassItems.add(newBioMatter);
			return acutallyAdded;
		}
		else{
			acutallyAdded = randomFilter(pMass);
			level += acutallyAdded;
			BioMatter newBioMatter = new BioMatter(acutallyAdded, pType);
			currentBiomassItems.add(newBioMatter);
			return acutallyAdded;
		}
	}

	public BioMatter[] takeBioMatterMass(float pMass){
		List itemsToReturn = new Vector();
		List itemsToRemove = new Vector();
		float collectedMass = 0f;
		for (Iterator iter = currentBiomassItems.iterator(); iter.hasNext() &&  (collectedMass <= pMass);){
			BioMatter currentBioMatter = (BioMatter)(iter.next());
			float massStillNeeded = pMass - collectedMass;
			//we need to get more bio matter
			if (currentBioMatter.mass < massStillNeeded){
				itemsToReturn.add(currentBioMatter);
				itemsToRemove.add(currentBioMatter);
				collectedMass += currentBioMatter.mass;
			}
			//we have enough, let's cut up the biomass (if too much)
			else if (currentBioMatter.mass >= massStillNeeded){
				BioMatter partialReturnedBioMatter = new BioMatter(massStillNeeded, currentBioMatter.type);
				currentBioMatter.mass -= partialReturnedBioMatter.mass;
				itemsToReturn.add(partialReturnedBioMatter);
				if (currentBioMatter.mass <= 0)
					itemsToRemove.add(currentBioMatter);
				collectedMass += partialReturnedBioMatter.mass;
			}
		}
		//Remove items from List
		for (Iterator iter = itemsToRemove.iterator(); iter.hasNext();){
			currentBiomassItems.remove(iter.next());
		}
		level -= collectedMass;
		//return the array
		BioMatter[] emptyArray = new BioMatter[0];
		BioMatter[] returnArray = (BioMatter[])(itemsToReturn.toArray(emptyArray));
		if (returnArray == null)
			return emptyArray;
		else
			return returnArray;
	}

	public BioMatter takeBioMatterMassAndType(float pMass, PlantType pType){
		BioMatter matterToReturn = new BioMatter(0f, pType);
		List itemsToRemove = new Vector();
		for (Iterator iter = currentBiomassItems.iterator(); iter.hasNext() &&  (matterToReturn.mass <= pMass);){
			BioMatter currentBioMatter = (BioMatter)(iter.next());
			if (currentBioMatter.type == pType){
				float massStillNeeded = pMass - matterToReturn.mass;
				//we need to get more bio matter
				if (currentBioMatter.mass < massStillNeeded){
					matterToReturn.mass += currentBioMatter.mass;
					itemsToRemove.add(currentBioMatter);
				}
				//we have enough, let's cut up the biomass (if too much)
				else if (currentBioMatter.mass >= massStillNeeded){
					float partialMassToReturn = massStillNeeded;
					currentBioMatter.mass -= partialMassToReturn;
					matterToReturn.mass += partialMassToReturn;
					if (currentBioMatter.mass <= 0)
						itemsToRemove.add(currentBioMatter);
				}
			}
		}
		//Remove items from List
		for (Iterator iter = itemsToRemove.iterator(); iter.hasNext();){
			currentBiomassItems.remove(iter.next());
		}
		level -= matterToReturn.mass;
		return matterToReturn;
	}

	public BioMatter takeBioMatterType(PlantType pType){
		BioMatter matterToReturn = new BioMatter(0f, pType);
		List itemsToRemove = new Vector();
		for (Iterator iter = currentBiomassItems.iterator(); iter.hasNext();){
			BioMatter currentBioMatter = (BioMatter)(iter.next());
			if (currentBioMatter.type == pType){
				matterToReturn.mass += currentBioMatter.mass;
				itemsToRemove.add(currentBioMatter);
			}
		}
		//Remove items from List
		for (Iterator iter = itemsToRemove.iterator(); iter.hasNext();){
			currentBiomassItems.remove(iter.next());
		}
		level -= matterToReturn.mass;
		return matterToReturn;
	}
	
	public void reset(){
		super.reset();
		currentBiomassItems.clear();
		if (level > 0)
			currentBiomassItems.add(new BioMatter(level, PlantType.UNKNOWN_PLANT));
	}

}
