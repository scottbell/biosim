package com.traclabs.biosim.server.simulation.air;

import com.traclabs.biosim.server.simulation.framework.SimBioModuleImpl;
/**
 * CRS Subsystem
 *
 * @author    Scott Bell
 */

public class CRS extends AirRSSubSystem{
	private final static float CO2Needed = 10f;
	private final static float H2Needed = CO2Needed * 4f;
	private float currentCO2Consumed = 0;
	private float currentH2Consumed = 0;
	private float currentH2OProduced = 0;
	private float currentCH4Produced = 0;

	public CRS(AirRSImpl pAirRSImpl){
		super(pAirRSImpl);
	}

	private void gatherGasses(){
		float gatheredCO2 = 0f;
		float filteredCO2Needed = myAirRS.randomFilter(CO2Needed);
		float filteredH2Needed = myAirRS.randomFilter(H2Needed);
		currentCO2Consumed = SimBioModuleImpl.getResourceFromStore(myAirRS.getCO2Inputs(), myAirRS.getCO2InputMaxFlowRates(), myAirRS.getCO2InputDesiredFlowRates(), myAirRS.getCO2InputActualFlowRates(), filteredCO2Needed);
		currentH2Consumed = SimBioModuleImpl.getResourceFromStore(myAirRS.getH2Inputs(), myAirRS.getH2InputMaxFlowRates(), myAirRS.getH2InputDesiredFlowRates(), myAirRS.getH2InputActualFlowRates(), filteredH2Needed);
	}

	private void pushGasses(){
		if ((currentH2Consumed <= 0) || (currentCO2Consumed <=0)){
			currentH2OProduced = myAirRS.randomFilter(0f);
			currentCH4Produced = myAirRS.randomFilter(0f);
			SimBioModuleImpl.pushResourceToStore(myAirRS.getH2Inputs(), myAirRS.getH2InputMaxFlowRates(), myAirRS.getH2InputDesiredFlowRates(), myAirRS.getH2InputActualFlowRates(), currentH2Consumed);
			SimBioModuleImpl.pushResourceToStore(myAirRS.getCO2Inputs(), myAirRS.getCO2InputMaxFlowRates(), myAirRS.getCO2InputDesiredFlowRates(), myAirRS.getCO2InputActualFlowRates(), currentCO2Consumed);
		}
		else{
			// CO2 + 4H2 --> CH4 + 2H20
			float limitingReactant = Math.min(currentH2Consumed / 4f, currentCO2Consumed);
			if (limitingReactant == currentH2Consumed)
				SimBioModuleImpl.pushResourceToStore(myAirRS.getCO2Inputs(), myAirRS.getCO2InputMaxFlowRates(), myAirRS.getCO2InputDesiredFlowRates(), myAirRS.getCO2InputActualFlowRates(), currentCO2Consumed - limitingReactant);
			else
				SimBioModuleImpl.pushResourceToStore(myAirRS.getH2Inputs(), myAirRS.getH2InputMaxFlowRates(), myAirRS.getH2InputDesiredFlowRates(), myAirRS.getH2InputActualFlowRates(), currentH2Consumed - 4f * limitingReactant);
			float waterMolesProduced = 2f * limitingReactant;
			float waterLitersProduced = (waterMolesProduced * 18.01524f) / 1000f; //1000g/liter, 18.01524g/mole
			float methaneMolesProduced = limitingReactant;
			currentH2OProduced = myAirRS.randomFilter(waterLitersProduced);
			currentCH4Produced = myAirRS.randomFilter(methaneMolesProduced);
		}
		float distributedWaterLeft = SimBioModuleImpl.pushResourceToStore(myAirRS.getPotableWaterOutputs(), myAirRS.getPotableWaterOutputMaxFlowRates(), myAirRS.getPotableWaterOutputDesiredFlowRates(), myAirRS.getPotableWaterOutputActualFlowRates(), currentH2OProduced);
		myAirRS.getCH4Tank().addCH4(currentCH4Produced);
	}

	public void reset(){
		currentPowerConsumed = 0;
		hasEnoughPower = false;
		currentCO2Consumed = 0;
		currentH2Consumed = 0;
		currentH2OProduced = 0;
		currentCH4Produced = 0;
	}

	public void tick(){
		super.tick();
		if (hasEnoughPower){
			gatherGasses();
			pushGasses();
		}
	}

}
