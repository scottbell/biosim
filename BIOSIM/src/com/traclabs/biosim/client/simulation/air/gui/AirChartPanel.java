package biosim.client.simulation.air.gui;

import java.awt.*;
import biosim.client.framework.gui.*;

/**
 * This is the JPanel that displays a chart about the Air
 *
 * @author    Scott Bell
 */
public class AirChartPanel extends UpdatablePanel 
{
	private AirStorePanel myAirStorePanel;

	public AirChartPanel() {
		setLayout(new BorderLayout());
		myAirStorePanel = new AirStorePanel();
		add(myAirStorePanel, BorderLayout.CENTER);
	}
	
	public void visibilityChange(boolean nowVisible){
		myAirStorePanel.visibilityChange(nowVisible);
	}
	
	public void refresh(){
		myAirStorePanel.refresh();
	}
}
