package biosim.client.simulation.environment.gui;

import java.awt.BorderLayout;

import biosim.client.framework.gui.UpdatablePanel;

/**
 * This is the JPanel that displays a chart about the Water
 *
 * @author    Scott Bell
 */
public class EnvironmentChartPanel extends UpdatablePanel
{
	private UpdatablePanel myCrewEnvironmentPieChartPanel;
	private UpdatablePanel myPlantEnvironmentPieChartPanel;

	public EnvironmentChartPanel() {
		setLayout(new BorderLayout());
		myCrewEnvironmentPieChartPanel = new EnvironmentPieChartPanel("Crew");
		myPlantEnvironmentPieChartPanel = new EnvironmentPieChartPanel("Plant");
		
		add(myCrewEnvironmentPieChartPanel, BorderLayout.WEST);
		add(myPlantEnvironmentPieChartPanel, BorderLayout.EAST);
	}
	
	public void refresh(){
		myCrewEnvironmentPieChartPanel.refresh();
		myPlantEnvironmentPieChartPanel.refresh();
	}
	
	public void visibilityChange(boolean nowVisible){
		myCrewEnvironmentPieChartPanel.visibilityChange(nowVisible);
		myPlantEnvironmentPieChartPanel.visibilityChange(nowVisible);
	}
}
