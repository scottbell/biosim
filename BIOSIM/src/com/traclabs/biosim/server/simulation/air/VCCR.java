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
	
	/**
	* Collects references to subsystems needed for putting/getting resources
	*/
	private void collectReferences(){
		if (!hasCollectedReferences){
			try{
				myCO2Tank = myAirRS.getCO2Tank();
				mySimEnvironment =SimEnvironmentHelper.narrow(OrbUtils.getNCRef().resolve_str("SimEnvironment"));
				myPowerStore = PowerStoreHelper.narrow(OrbUtils.getNCRef().resolve_str("PowerStore"));
				hasCollectedReferences = true;
			}
			catch (org.omg.CORBA.UserException e){
				e.printStackTrace(System.out);
			}
		}
	}
	
	private void gatherAir(){
		myBreath = mySimEnvironment.takeCO2Breath(CO2Needed);
		currentCO2Consumed = myBreath.CO2;
		currentO2Consumed = myBreath.O2;
		if (CO2Needed < currentCO2Consumed)
			enoughCO2 = false;
		else
			enoughCO2 = true;
	}
	
	private void pushAir(){
		currentO2Produced = myBreath.O2;
		mySimEnvironment.addOther(myBreath.other);
		mySimEnvironment.addO2(myBreath.O2);
		myCO2Tank.addCO2(myBreath.CO2);
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
