package com.traclabs.biosim.client.simulation.air.cdrs;

public class CVVPanel extends GridButtonPanel {

	public CVVPanel(){
		setName("CVV");
		addButton(new CDRSValveRTPanel());
		addButton(new CO2VentValvePanel());
		addButton(new CO2IsoValvePanel());
	}
}
