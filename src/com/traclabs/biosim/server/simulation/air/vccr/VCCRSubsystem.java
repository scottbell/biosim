package com.traclabs.biosim.server.simulation.air.vccr;

import java.util.List;
import java.util.Vector;

import com.traclabs.biosim.server.simulation.framework.Store;
import com.traclabs.biosim.server.simulation.environment.EnvironmentFlowRateControllable;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllable;

public class VCCRSubsystem {
	private List<VCCRSubsystem> mySubsystemAttachments = new Vector<VCCRSubsystem>();

	private List<EnvironmentFlowRateControllable> myEnvironmentAttachments = new Vector<EnvironmentFlowRateControllable>();

	private List<StoreFlowRateControllable> myStoreAttachments = new Vector<StoreFlowRateControllable>();

	private float initialPressure = 101f;
	
	private float currentPressure = initialPressure;
	
	private float initialTemperature = 30f;

	private float currentTemperature = initialTemperature;
	
	private GasMoles myAirMoles;
	
	//in meters cubed
	private float myVolume = 10f;
	
	//fluid density of dry air
	private static final float FLUID_DENSITY = 1.29f;
	private static final float DISCHARGE_COEFFICIENT = 0.98f;
	private static final float PIPE_CROSS_SECTIONAL_AREA = 0.1f; //in meters
	private static final float EFFLUENT_CROSS_SECTIONAL_AREA = 0.5f; // in meters
	
	private float volume = 1f;
	
	
	public void attach(VCCRSubsystem attachment) {
		mySubsystemAttachments.add(attachment);
	}

	public void attach(EnvironmentFlowRateControllable attachment) {
		myEnvironmentAttachments.add(attachment);
	}

	public void attach(StoreFlowRateControllable attachment) {
		myStoreAttachments.add(attachment);
	}
	
	public void tick(){
		equalizeSurroundingPressures();
	}
	
	public float getPressure(){
		return currentPressure;
	}
	
	private void equalizeSurroundingPressures() {
		for (VCCRSubsystem subsystem : mySubsystemAttachments)
			if (subsystem.getPressure() != getPressure())
				equalizePressure(subsystem, this);
		for (EnvironmentFlowRateControllable environmentAttachment : myEnvironmentAttachments)
			if (environmentAttachment.getEnvironments()[0].getTotalPressure() != getPressure())
				equalizePressure(environmentAttachment, this);
		for (StoreFlowRateControllable storeAttachment : myStoreAttachments)
			if (calculatePressure(storeAttachment.getStores()[0]) != getPressure())
				equalizePressure(storeAttachment, this);
	}

	private static float calculatePressure(Store store) {
		//TODO
		return 100;
	}

	private static void equalizePressure(StoreFlowRateControllable storeAttachment, VCCRSubsystem subsystem) {
		float storePressure = calculatePressure(storeAttachment.getStores()[0]);
		float pressureDifference = Math.abs(subsystem.getPressure() - storePressure);
		float massFlowrate = calculateMassFlowRate(pressureDifference);
		//add gas to store, take from subsystem
		if (subsystem.getPressure() > storePressure){
			//TODO add to store
			//subsystem.take(Air);
		}
		//take gas from store, add to subsystem
		else{
			//TODO take from store
			//subsystem.add(moleFlowrate);
		}
	}

	private static void equalizePressure(EnvironmentFlowRateControllable environmentFlowRate, VCCRSubsystem subsystem) {
		float environmentPressure = environmentFlowRate.getEnvironments()[0].getTotalPressure();
		float pressureDifference = Math.abs(subsystem.getPressure() - environmentPressure);
		float massFlowrate = calculateMassFlowRate(pressureDifference);
		float moleFlowrate = calculateMoleFlowrate(massFlowrate);
		//add gas into environment, take from subsystem
		if (subsystem.getPressure() > environmentPressure){
			//TODO add to environment
			//subsystem.takeMoles(moleFlowrate);
		}
		//add gas to subsystem, take from environment
		else {
			//TODO take from environment
			//subsystem.addMoles(moleFlowrate);
		}
	}

	private static void equalizePressure(VCCRSubsystem subsystem1, VCCRSubsystem subsystem2) {
		float pressureDifference = Math.abs(subsystem1.getPressure() - subsystem2.getPressure());
		float massFlowrate = calculateMassFlowRate(pressureDifference);
		float moleFlowrate = calculateMoleFlowrate(massFlowrate);
		//add gas into subsystem2, take from subsystem 1
		if (subsystem1.getPressure() > subsystem2.getPressure()){
			//subsystem1.takeMoles(moleFlowrate);
			//subsystem2.addMoles(moleFlowrate);
		}
		//add gas into subsystem1, take from subsystem 2
		else{
			//subsystem2.takeMoles(moleFlowrate);
			//subsystem1.addMoles(moleFlowrate);
		}
	}

	private void add(float moleFlowrate) {
	}

	private void take(float moleFlowrate) {
	}

	private void setPressure(float newPressure) {
		this.currentPressure = newPressure;
	}

	public void reset(){
		currentPressure = initialPressure;
	}
	
	private static float calculateMassFlowRate(float pressureDifference){
		//taken from http://www.efunda.com/formulae/fluids/venturi_flowmeter.cfm
		//basically Bernoulli's equation
		double firstTerm = DISCHARGE_COEFFICIENT * Math.sqrt((2 * pressureDifference) / FLUID_DENSITY);
		double secondTerm = EFFLUENT_CROSS_SECTIONAL_AREA / Math.sqrt(Math.pow((EFFLUENT_CROSS_SECTIONAL_AREA / PIPE_CROSS_SECTIONAL_AREA), 2) - 1);
		double volumetricFlowrate = firstTerm * secondTerm;
		double massFlowrate = volumetricFlowrate * FLUID_DENSITY;
		return (float)massFlowrate * 3600f;
	}
	


	private static float calculateMoleFlowrate(float massFlowrate) {
		return 0;
	}

}
