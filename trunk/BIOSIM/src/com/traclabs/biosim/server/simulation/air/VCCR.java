package biosim.server.air;

import biosim.idl.util.log.*;
import biosim.idl.air.*;
import biosim.idl.environment.*;
import biosim.idl.power.*;
import biosim.server.util.*;
/**
 * VCCR Subsystem
 *
 * @author    Scott Bell
 */

public class VCCR extends AirRSSubSystem{
	private Breath myBreath;
	private SimEnvironment mySimEnvironment;
	private CO2Tank myCO2Tank;
	private float CO2Needed = 1.0f;
	private boolean enoughCO2 = false;
	private float currentCO2Consumed = 0;
	private float currentO2Consumed = 0;
	private float currentO2Produced = 0;
	private float myProductionRate = 1f;

	public VCCR(AirRSImpl pAirRSImpl){
		super(pAirRSImpl);
	}
	
	public boolean hasEnoughCO2(){
		return enoughCO2;
	}
	
	public float getO2Produced(){
		return currentO2Produced;
	}
	
	public float getCO2Consumed(){
		return currentCO2Consumed;
	}
	
	public void setProductionRate(float percentage){
		myProductionRate = percentage;
	}
	
	public float getProductionRate(){
		return myProductionRate;
	}
	
	/**
	* Collects references to subsystems needed for putting/getting resources
	*/
	private void collectReferences(){
		if (!hasCollectedReferences){
			try{
				myCO2Tank = myAirRS.getCO2Tank();
				mySimEnvironment =SimEnvironmentHelper.narrow(OrbUtils.getNCRef().resolve_str("SimEnvironment"+myAirRS.getID()));
				myPowerStore = PowerStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("PowerStore"+myAirRS.getID()));
				hasCollectedReferences = true;
			}
			catch (org.omg.CORBA.UserException e){
				e.printStackTrace(System.out);
			}
		}
	}
	
	private void gatherAir(){
		myBreath = mySimEnvironment.takeCO2Breath(myAirRS.randomFilter(CO2Needed));
		currentCO2Consumed = myBreath.CO2;
		currentO2Consumed = myBreath.O2;
		if (CO2Needed < currentCO2Consumed)
			enoughCO2 = false;
		else
			enoughCO2 = true;
	}
	
	private void pushAir(){
		currentO2Produced = myBreath.O2 * myProductionRate;
		mySimEnvironment.addOther(myBreath.other * myProductionRate);
		mySimEnvironment.addO2(currentO2Produced);
		myCO2Tank.addCO2(myBreath.CO2 * myProductionRate);
	}
	
	public void reset(){
		myBreath = new Breath(0,0,0);
		currentPowerConsumed = 0;
		hasEnoughPower = false;
		enoughCO2 = false;
		currentO2Consumed = 0f;
		currentCO2Consumed = 0f;
		currentO2Produced = 0f;
	}
	
	public void tick(){
		collectReferences();
		gatherPower();
		if (hasEnoughPower){
			gatherAir();
			pushAir();
		}
	}

}
