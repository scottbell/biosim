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
	float capacity;
	float currentLevel;
	float cachedLevel;
	List currentBiomassItems;
	List cachedBiomassItems;
	//Used for finding what the current tick is (to see if we're behind or ahead)
	private BioDriver myDriver;
	//What I think the current tick is.
	private int myTicks = 0;
	//What the capacity was before the permanent malfunction
	private float preMalfunctionCapacity = 0.0f;
	
	public BiomassStoreImpl(int pID){
		super(pID);
		currentBiomassItems = new Vector();
		cachedBiomassItems = new Vector();
		capacity = 10f;
		currentLevel = cachedLevel = 0f;
	}
	
	public float addBiomatter(float pMass, PlantType pType){
		float acutallyAdded = 0f;
		if ((pMass + currentLevel) > capacity){
			//adding more than capacity
			acutallyAdded = capacity - currentLevel;
			currentLevel += acutallyAdded;
			BioMatter newBiomatter = new BioMatter(acutallyAdded, pType);
			currentBiomassItems.add(newBiomatter);
			return acutallyAdded;
		}
		else{
			acutallyAdded = randomFilter(pMass);
			level += acutallyAdded;
			return acutallyAdded;
		}
	}
	
	/**
	* Returns the name of this module (BiomassStore)
	* @return the name of this module
	*/
	public String getModuleName(){
		return "BiomassStore"+getID();
	}
}
