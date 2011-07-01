package com.traclabs.biosim.client.simulation.air.cdrs;

import com.traclabs.biosim.client.util.BioHolderInitializer;


public class ARRackPanel extends GridButtonPanel {
	public ARRackPanel(){
		setName("AR Rack");
		addButton(new CDRSPanel());
		addButton(new MCAPanel());
		addButton(new SmokeDetectorPanel());
		addButton(new RpcmM1Panel());
		addButton(new RpcmMAPanel());

		StatusLabel pressureLabel = new StatusLabel() {
			public void refresh() {
				setText("LSSM Cabin Pressure: "  + BioHolderInitializer.getBioHolder().theSimEnvironments.get(0).getTotalPressure() );
			}
		};
		addLabel(pressureLabel);
	}
}
