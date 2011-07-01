package com.traclabs.biosim.client.simulation.air.cdrs;

public class AVPanel extends GridButtonPanel {
	public AVPanel(){
		setName("AV");
		addButton(new CDRSValveRTPanel());
		addButton(new AirInletValvePanel());
		addButton(new AirReturnValvePanel());
	}
}
