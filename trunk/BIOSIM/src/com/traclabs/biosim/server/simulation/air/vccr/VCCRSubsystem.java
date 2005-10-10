package com.traclabs.biosim.server.simulation.air.vccr;

import java.util.List;
import java.util.Vector;

import com.traclabs.biosim.idl.simulation.framework.Store;
import com.traclabs.biosim.server.simulation.environment.EnvironmentFlowRateControllableImpl;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllableImpl;

public class VCCRSubsystem {
	private List<VCCRSubsystem> mySubsystemAttachments = new Vector<VCCRSubsystem>();

	private List<EnvironmentFlowRateControllableImpl> myEnvironmentAttachments = new Vector<EnvironmentFlowRateControllableImpl>();

	private List<StoreFlowRateControllableImpl> myStoreAttachments = new Vector<StoreFlowRateControllableImpl>();

	private float initialPressure = 101f;
	
	private float currentPressure = initialPressure;
	
	public void attach(VCCRSubsystem attachment) {
		mySubsystemAttachments.add(attachment);
	}

	public void attach(EnvironmentFlowRateControllableImpl attachment) {
		myEnvironmentAttachments.add(attachment);
	}

	public void attach(StoreFlowRateControllableImpl attachment) {
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
		for (EnvironmentFlowRateControllableImpl environmentAttachment : myEnvironmentAttachments)
			if (environmentAttachment.getEnvironments()[0].getTotalPressure() != getPressure())
				equalizePressure(environmentAttachment, this);
		for (StoreFlowRateControllableImpl storeAttachment : myStoreAttachments)
			if (calculatePressure(storeAttachment.getStores()[0]) != getPressure())
				equalizePressure(storeAttachment, this);
	}

	private static float calculatePressure(Store store) {
		//TODO calculate pressure from boyle's law
		return 100;
	}

	private static void equalizePressure(StoreFlowRateControllableImpl storeAttachment, VCCRSubsystem subsystem) {
		float storePressure = calculatePressure(storeAttachment.getStores()[0]);
		float gasDifference = Math.abs(subsystem.getPressure() - storePressure);
		//put gas into store
		if (subsystem.getPressure() > storePressure){
			//TODO
		}
		//take gas from store
		else{
			//TODO
		}
	}

	private static void equalizePressure(EnvironmentFlowRateControllableImpl environmentFlowRate, VCCRSubsystem subsystem) {
		float gasDifference = Math.abs(subsystem.getPressure() - environmentFlowRate.getEnvironments()[0].getTotalPressure());
		//put gas into environment
		if (subsystem.getPressure() > environmentFlowRate.getEnvironments()[0].getTotalPressure()){
			//TODO
		}
		//take gas from environment
		else{
			//TODO
		}
	}

	private static void equalizePressure(VCCRSubsystem subsystem1, VCCRSubsystem subsystem2) {
		//TODO right now just average them, in the future, should be slow rate increase
		float average = (subsystem1.getPressure() + subsystem2.getPressure()) / 2f;
		subsystem1.setPressure(average);
		subsystem2.setPressure(average);
	}

	private void setPressure(float newPressure) {
		this.currentPressure = newPressure;
	}

	public void reset(){
		currentPressure = initialPressure;
	}

}
