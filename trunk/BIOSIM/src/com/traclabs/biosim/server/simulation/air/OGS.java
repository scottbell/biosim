package biosim.server.air;

import biosim.idl.util.log.*;
import biosim.idl.environment.*;
import biosim.idl.power.*;
import biosim.server.util.*;
/**
 * OGS Subsystem
 *
 * @author    Scott Bell
 */

public class OGS extends AirRSSubSystem{
	private SimEnvironment mySimEnvironment;
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
			try{
				myCRS = myAirRS.getCRS();
				mySimEnvironment =SimEnvironmentHelper.narrow(OrbUtils.getNCRef().resolve_str("SimEnvironment"+myAirRS.getID()));
				myPowerStore = PowerStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("PowerStore"+myAirRS.getID()));
				hasCollectedReferences = true;
			}
			catch (org.omg.CORBA.UserException e){
				e.printStackTrace(System.out);
			}
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
		mySimEnvironment.addO2(myAirRS.randomFilter(currentO2Produced));
		currentH2Produced = myAirRS.randomFilter(currentH2OConsumed * 0.30f) * myProductionRate;
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
