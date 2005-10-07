package com.traclabs.biosim.server.simulation.air.vccr;

import java.util.List;
import java.util.Vector;

import com.traclabs.biosim.server.simulation.environment.EnvironmentFlowRateControllableImpl;
import com.traclabs.biosim.server.simulation.framework.StoreFlowRateControllableImpl;

public class VCCRSubsystem {
	private List<VCCRSubsystem> mySubsystemAttachments = new Vector<VCCRSubsystem>();

	private List<EnvironmentFlowRateControllableImpl> myEnvironmentAttachments = new Vector<EnvironmentFlowRateControllableImpl>();

	private List<StoreFlowRateControllableImpl> myStoreAttachments = new Vector<StoreFlowRateControllableImpl>();

	public void attach(VCCRSubsystem attachment) {
		mySubsystemAttachments.add(attachment);
	}

	public void attach(EnvironmentFlowRateControllableImpl attachment) {
		myEnvironmentAttachments.add(attachment);
	}

	public void attach(StoreFlowRateControllableImpl attachment) {
		myStoreAttachments.add(attachment);
	}

}
