package biosim.client.environment.gui;

import javax.swing.*;
import java.awt.*;
import biosim.client.framework.gui.*;

/**
 * This is the JPanel that displays a chart about the Water
 *
 * @author    Scott Bell
 */
public class EnvironmentChartPanel extends UpdatablePanel
{
	private UpdatablePanel myEnvironmentPieChartPanel;

	public EnvironmentChartPanel() {
		setLayout(new BorderLayout());
		myEnvironmentPieChartPanel = new EnvironmentPieChartPanel();
		add(myEnvironmentPieChartPanel, BorderLayout.CENTER);
	}
	
	public void refresh(){
		myEnvironmentPieChartPanel.refresh();
	}
	
	public void visibilityChange(boolean nowVisible){
		myEnvironmentPieChartPanel.visibilityChange(nowVisible);
	}
}
