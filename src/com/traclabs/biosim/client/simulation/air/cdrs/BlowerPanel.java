package com.traclabs.biosim.client.simulation.air.cdrs;

public class BlowerPanel extends GridButtonPanel {
	public BlowerPanel(){
		setName("Blower LSSMB1");
		addButton(new BlowerDetailsPanel());
		addButton(new LssmArRtStatus());
	}
}
