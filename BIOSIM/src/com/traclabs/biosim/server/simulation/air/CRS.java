package biosim.server.simulation.air;

import biosim.idl.util.log.*;
import biosim.idl.simulation.environment.*;
import biosim.idl.simulation.air.*;
import biosim.idl.simulation.power.*;
import biosim.server.util.*;
/**
 * CRS Subsystem
 *
 * @author    Scott Bell
 */

public class CRS extends AirRSSubSystem{
	private float H2Needed = 50.0f;
	private float CO2Needed = 50.0f;
	private boolean enoughCO2 = false;
	private boolean enoughH2 = false;
	private float currentCO2Consumed = 0;
	private float currentH2Consumed = 0;
	private float currentH2OProduced = 0;
	private float currentCH4Produced = 0;

	public CRS(AirRSImpl pAirRSImpl){
		super(pAirRSImpl);
	}

	public boolean hasEnoughCO2(){
		return enoughCO2;
	}

	public boolean hasEnoughH2(){
		return enoughH2;
	}

	private void gatherGasses(){
		float gatheredCO2 = 0f;
		CO2Needed = myAirRS.randomFilter(CO2Needed);
		currentCO2Consumed = myAirRS.getResourceFromStore(myAirRS.getCO2Inputs(), myAirRS.getCO2InputMaxFlowRates(), CO2Needed);
		currentH2Consumed = myAirRS.getOGS().takeH2(myAirRS.randomFilter(H2Needed));
		if (CO2Needed < currentCO2Consumed)
			enoughCO2 = false;
		else
			enoughCO2 = true;
		if (currentH2Consumed < H2Needed){
			//Get remainder from H2 tank
			currentH2Consumed += myAirRS.getH2Tank().takeH2(H2Needed - currentH2Consumed);
			//check again
			if (currentH2Consumed < H2Needed)
				enoughH2 = false;
			else
				enoughH2 = true;
		}
		else
			enoughH2 = true;
	}

	private void pushGasses(){
		currentH2OProduced = myAirRS.randomFilter(new Double((currentH2Consumed + currentCO2Consumed) * .80).floatValue());
		myAirRS.getOGS().addH2O(currentH2OProduced);
		currentCH4Produced = myAirRS.randomFilter(new Double((currentH2Consumed + currentCO2Consumed) * .20).floatValue());
		myAirRS.getCH4Tank().addCH4(currentCH4Produced);
	}

	public void reset(){
		currentPowerConsumed = 0;
		hasEnoughPower = false;
		enoughCO2 = false;
		enoughH2 = false;
		currentCO2Consumed = 0;
		currentH2Consumed = 0;
		currentH2OProduced = 0;
		currentCH4Produced = 0;
	}

	public void tick(){
		gatherPower();
		if (hasEnoughPower){
			gatherGasses();
			pushGasses();
		}
	}

}
