package biosim.server.air;

import biosim.idl.util.log.*;
import biosim.idl.environment.*;
import biosim.idl.air.*;
import biosim.idl.power.*;
import biosim.server.util.*;
/**
 * OGS Subsystem
 *
 * @author    Scott Bell
 */

public class OGS extends AirRSSubSystem{
	private O2Store[] myO2Stores;
	private CRS myCRS;
	private float H2ONeeded = 1.0f;
	private boolean enoughH2O = false;
	private float currentH2OConsumed = 0;
	private float currentO2Produced = 0;
	private float currentH2Produced = 0;
	private float myProductionRate = 1f;

	public OGS(AirRSImpl pAirRSImpl){
		super(pAirRSImpl);
	}

	public boolean hasEnoughH2O(){
		return enoughH2O;
	}

	public float getO2Produced(){
		return currentO2Produced;
	}

	/**
	* Collects references to subsystems needed for putting/getting resources
	*/
	private void collectReferences(){
		if (!hasCollectedReferences){
			myCRS = myAirRS.getCRS();
			myO2Stores = myAirRS.getO2Outputs();
			myPowerStores = myAirRS.getPowerInputs();
			hasCollectedReferences = true;
		}
	}

	public float addH2O(float H2OtoAdd){
		currentH2OConsumed = myAirRS.randomFilter(H2OtoAdd);
		if (currentH2OConsumed < H2ONeeded)
			enoughH2O = false;
		else
			enoughH2O = true;
		return H2OtoAdd;
	}

	private void pushGasses(){
		currentO2Produced = myAirRS.randomFilter(currentH2OConsumed * 0.70f) * myProductionRate;
		currentH2Produced = myAirRS.randomFilter(currentH2OConsumed * 0.30f) * myProductionRate;
		float distributedO2 = myAirRS.randomFilter(currentO2Produced);
		for (int i = 0; (i < myO2Stores.length) && (distributedO2 > 0); i++){
			float O2ToDistribute = Math.min(distributedO2, myAirRS.getO2OutputFlowrate(i));
			distributedO2 -= myO2Stores[i].add(O2ToDistribute);
		}
	}

	public void setProductionRate(float percentage){
		myProductionRate = percentage;
	}

	public float getProductionRate(){
		return myProductionRate;
	}

	public float takeH2(float H2toTake){
		if (H2toTake >= currentH2Produced)
			return H2toTake;
		else
			return currentH2Produced;
	}

	public void reset(){
		currentPowerConsumed = 0;
		hasEnoughPower = false;
		enoughH2O = false;
		currentH2OConsumed = 0;
		currentO2Produced = 0;
	}

	public void tick(){
		collectReferences();
		gatherPower();
		if (hasEnoughPower){
			pushGasses();
		}
	}

}
