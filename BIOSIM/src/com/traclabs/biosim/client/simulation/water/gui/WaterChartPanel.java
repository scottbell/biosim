package biosim.client.water.gui;

import java.awt.*;
import biosim.client.framework.gui.*;

/**
 * This is the JPanel that displays a chart about the Water
 *
 * @author    Scott Bell
 */
public class WaterChartPanel extends BioTabPanel
{
	private WaterStorePanel myWaterStorePanel;

	public WaterChartPanel() {
		setLayout(new BorderLayout());
		myWaterStorePanel = new WaterStorePanel();
		add(myWaterStorePanel, BorderLayout.CENTER);
	}
	
	public void visibilityChange(boolean nowVisible){
		myWaterStorePanel.visibilityChange(nowVisible);
	}
}
