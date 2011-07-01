package com.traclabs.biosim.client.simulation.air.cdrs;

public class CVVPanel extends GridButtonPanel {
	private CDRSValveRTPanel myCDRSValveRTPanel = new CDRSValveRTPanel();
	private CO2VentValvePanel myCO2VentValvePanel = new CO2VentValvePanel();
	private CO2IsoValvePanel myCO2IsoValvePanel = new CO2IsoValvePanel();

	public CVVPanel(){
		setName("CVV");
		addButton(myCDRSValveRTPanel);
		addButton(myCO2VentValvePanel);
		addButton(myCO2IsoValvePanel);
	}
	
	@Override
	public void refresh() {
		super.refresh();
		myCDRSValveRTPanel.refresh();
		myCO2VentValvePanel.refresh();
		myCO2IsoValvePanel.refresh();
	}
}
