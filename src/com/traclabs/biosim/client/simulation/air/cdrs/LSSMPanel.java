package com.traclabs.biosim.client.simulation.air.cdrs;

import java.awt.GridLayout;

public class LSSMPanel extends GridButtonPanel {
	private WaterRackPanel myWaterRackPanel = new WaterRackPanel();
	private ARRackPanel myARRackPanel = new ARRackPanel();
	private TCSRackPanel myTCSRackPanel = new TCSRackPanel();
	private RpcmM1Panel myRpcmM1Panel = new RpcmM1Panel();
	private RpcmMAPanel myRpcmMAPanel = new RpcmMAPanel();
	private RpcmM6Panel myRpcmM6Panel = new RpcmM6Panel();
	
	public LSSMPanel(){
		setName("LSSM");
		GridLayout gridLayout = new GridLayout(2, 3);
		setLayout(gridLayout);
		addButton(myWaterRackPanel);
		addButton(myARRackPanel);
		addButton(myTCSRackPanel);
		addButton(myRpcmM1Panel);
		addButton(myRpcmMAPanel);
		addButton(myRpcmM6Panel);
	}
	
	@Override
	public void refresh() {
		super.refresh();
		myWaterRackPanel.refresh();
		myARRackPanel.refresh();
		myTCSRackPanel.refresh();
		myRpcmM1Panel.refresh();
		myRpcmMAPanel.refresh();
		myRpcmM6Panel.refresh();
	}
	
}
