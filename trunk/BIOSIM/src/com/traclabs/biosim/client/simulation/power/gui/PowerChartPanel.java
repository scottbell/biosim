package biosim.client.power.gui;

import java.awt.*;
import biosim.client.framework.gui.*;

/**
 * This is the JPanel that displays a chart about the Power
 *
 * @author    Scott Bell
 */
public class PowerChartPanel extends BioTabPanel
{
	private PowerStorePanel myPowerStorePanel;

	public PowerChartPanel() {
		setLayout(new BorderLayout());
		myPowerStorePanel = new PowerStorePanel();
		add(myPowerStorePanel, BorderLayout.CENTER);
	}
	
	public void visibilityChange(boolean nowVisible){
		myPowerStorePanel.visibilityChange(nowVisible);
	}
}
