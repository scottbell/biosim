package com.traclabs.biosim.client.simulation.air.cdrs;

public class CDRSPanel extends GridButtonPanel {

	public CDRSPanel(){
		setName("CDRS");
		addButton(new CDRSValveRTPanel());
		addButton(new CDRSDetailPanel());
		addButton(new CVVPanel());
		addButton(new BlowerPanel());
		addButton(new AVPanel());
		addButton(new WaterPumpPanel());
	}
}
