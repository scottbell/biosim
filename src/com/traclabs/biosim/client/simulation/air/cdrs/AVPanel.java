package com.traclabs.biosim.client.simulation.air.cdrs;

public class AVPanel extends GridButtonPanel {
	private CDRSValveRTPanel myCDRSValveRTPanel = new CDRSValveRTPanel();
	private AirInletValvePanel myAirInletValvePanel = new AirInletValvePanel();
	private AirReturnValvePanel myAirReturnValvePanel = new AirReturnValvePanel();

	public AVPanel(){
		setName("AV");
		addButton(myCDRSValveRTPanel);
		addButton(myAirInletValvePanel);
		addButton(myAirReturnValvePanel);
	}
	
	@Override
	public void refresh() {
		super.refresh();
		myCDRSValveRTPanel.refresh();
		myAirInletValvePanel.refresh();
		myAirReturnValvePanel.refresh();
	}
}
