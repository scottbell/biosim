package com.traclabs.biosim.client.simulation.air.cdrs;

import java.text.DecimalFormat;

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
				float pressure = BioHolderInitializer.getBioHolder().theSimEnvironments.get(0).getTotalPressure();
				setText("LSSM Cabin Pressure: "  +  DecimalFormat.getInstance().format(pressure));
			}
		};
		addLabel(pressureLabel);
	}
}
