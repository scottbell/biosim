package biosim.server.simulation.framework;

import biosim.server.framework.*;
import biosim.idl.simulation.framework.*;
/**
 * The SimBioModule Implementation.  Modules specific to the simulation.
 *
 * @author    Scott Bell
 */

public abstract class SimBioModuleImpl extends BioModuleImpl implements SimBioModuleOperations{
	
	/**
	* Constructor to create a BioModule, should only be called by those deriving from BioModule.
	* @param pID The unique ID for this module (all the modules this module communicates with should have the same ID)
	*/
	protected SimBioModuleImpl(int pID){
		super(pID);
	}
	
	/**
	* Grabs as much resources as it can (i.e., the flowRate) from a store.
	* @param pStores The stores to grab the resources from
	* @param pFlowRates The flow rates from this module to the stores
	* @return The total amount of resource grabbed from the stores
	*/
	public static float getMaxResourceFromStore(Store[] pStores, float[] pFlowRates){
		float gatheredResource = 0f;
		for (int i = 0; i < pStores.length; i++){
			gatheredResource += pStores[i].take(pFlowRates[i]);
		}
		return gatheredResource;
	}
	
	/**
	* Attempts to grab a specified amount from a collection of stores
	* @param pStores The stores to grab the resources from
	* @param pFlowRates The flow rates from this module to the stores
	* @param amountNeeded The amount to gather from the stores
	* @return The total amount of resource grabbed from the stores (equal to the amount needed if sucessful)
	*/
	public static float getResourceFromStore(Store[] pStores, float[] pFlowRates, float amountNeeded){
		float gatheredResource = 0f;
		for (int i = 0; (i < pStores.length) && (gatheredResource < amountNeeded); i++){
			float resourceToGather = Math.min(amountNeeded, pFlowRates[i]); 
			gatheredResource += pStores[i].take(resourceToGather);
		}
		return gatheredResource;
	}
	
	/**
	* Attempts to push a specified amount to a collection of stores
	* @param pStores The stores to push the resources to
	* @param pFlowRates The flow rates from this module to the stores
	* @param amountToPush The amount to push to the stores
	* @return The total amount of resource pushed to the stores (equal to the amount to push if sucessful)
	*/
	public static float pushResourceToStore(Store[] pStores, float[] pFlowRates, float amountToPush){
		float totalResourceDistributed = amountToPush;
		for (int i = 0; (i < pStores.length) && (totalResourceDistributed > 0); i++){
			float resourceToDistribute = Math.min(totalResourceDistributed, pFlowRates[i]);
			totalResourceDistributed -= pStores[i].add(resourceToDistribute);
		}
		return (amountToPush - totalResourceDistributed);
	}

	/**
	* Returns the name of the module, "Unamed" if not overriden
	* @return the name of this module
	*/
	public String getModuleName(){
		return "SimModule"+getID();
	}
}

