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
	private SimEnvironment[] myAirInputs;
	private SimEnvironment[] myAirOutputs;
	private CO2Tank myCO2Tank;
	private float litersAirNeeded = 4.0f;
	private boolean enoughAir = false;
	private float myProductionRate = 1f;

	public VCCR(AirRSImpl pAirRSImpl){
		super(pAirRSImpl);
		myBreath = new Breath(0,0,0);
	}

	public boolean hasEnoughAir(){
		return enoughAir;
	}

	public float getAirProduced(){
		return (myBreath.CO2 + myBreath.O2 + myBreath.other);
	}

	public float getCO2Consumed(){
		return myBreath.CO2;
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
			myCO2Tank = myAirRS.getCO2Tank();
			myAirInputs = myAirRS.getAirInputs();
			myAirOutputs = myAirRS.getAirOutputs();
			myPowerStores = myAirRS.getPowerInputs();
			hasCollectedReferences = true;
		}
	}

	private void gatherAir(){
		float airNeededFiltered = myAirRS.randomFilter(litersAirNeeded);
		float gatheredAir = 0f;
		float gatheredO2 = 0f;
		float gatheredCO2 = 0f;
		float gatheredOther = 0f;
		for (int i = 0; (i < myAirInputs.length) && (gatheredAir < airNeededFiltered); i++){
			Breath currentBreath = myAirInputs[i].takeVolume(airNeededFiltered);
			gatheredAir += currentBreath.O2 + currentBreath.CO2 + currentBreath.other;
			gatheredO2 += currentBreath.CO2;
			gatheredCO2 += currentBreath.O2;
			gatheredOther += currentBreath.other;
		}
		myBreath.O2 =  gatheredO2;
		myBreath.CO2 = gatheredCO2;
		myBreath.other = gatheredOther;
		if (gatheredAir < airNeededFiltered)
			enoughAir = false;
		else
			enoughAir = true;
	}

	private void pushAir(){
		float distributedCO2Left = myBreath.CO2 * myProductionRate;
		float distributedO2Left = myBreath.O2 * myProductionRate;
		float distributedOtherLeft = myBreath.other * myProductionRate;
		for (int i = 0; (i < myAirOutputs.length) && ((distributedO2Left > 0) || (distributedOtherLeft > 0)); i++){
			distributedO2Left -= myAirOutputs[i].addO2(distributedO2Left);
			distributedOtherLeft -= myAirOutputs[i].addOther(distributedOtherLeft);
		}
		myCO2Tank.addCO2(myBreath.CO2 * myProductionRate);
	}

	public void reset(){
		myBreath = new Breath(0,0,0);
		currentPowerConsumed = 0;
		hasEnoughPower = false;
		enoughAir = false;
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
