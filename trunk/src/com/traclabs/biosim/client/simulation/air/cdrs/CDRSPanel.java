package com.traclabs.biosim.client.simulation.air.cdrs;

public class CDRSPanel extends GridButtonPanel {
	private CDRSValveRTPanel myCDRSValveRTPanel = new CDRSValveRTPanel();
	private CDRSDetailPanel myCDRSDetailPanel = new CDRSDetailPanel();
	private CVVPanel myCVVPanel = new CVVPanel();
	private BlowerPanel myBlowerPanel = new BlowerPanel();
	private AVPanel myRpcmMAPanel = new AVPanel();
	private WaterPumpPanel myWaterPumpPanel = new WaterPumpPanel();

	public CDRSPanel(){
		setName("CDRS");
		addButton(myCDRSValveRTPanel);
		addButton(myCDRSDetailPanel);
		addButton(myCVVPanel);
		addButton(myBlowerPanel);
		addButton(myRpcmMAPanel);
		addButton(myWaterPumpPanel);
	}
	
	@Override
	public void refresh() {
		super.refresh();
		myCDRSValveRTPanel.refresh();
		myCDRSDetailPanel.refresh();
		myCDRSDetailPanel.refresh();
		myBlowerPanel.refresh();
		myRpcmMAPanel.refresh();
		myWaterPumpPanel.refresh();
	}
}
