package biosim.client.crew.gui;

import java.awt.*;
import biosim.client.framework.gui.*;

/**
 * This is the JPanel that displays a chart about the Air
 *
 * @author    Scott Bell
 */
public class CrewChartPanel extends BioTabPanel
{
	private CrewAirChartPanel myCrewAirChartPanel;
	private CrewFoodChartPanel myCrewFoodChartPanel;
	private CrewWaterChartPanel myCrewWaterChartPanel;
	private CrewIntensityChartPanel myCrewIntensityChartPanel;

	public CrewChartPanel() {
		setLayout(new GridLayout(2,2));
		myCrewAirChartPanel = new CrewAirChartPanel();
		myCrewFoodChartPanel = new CrewFoodChartPanel();
		myCrewWaterChartPanel = new CrewWaterChartPanel();
		myCrewIntensityChartPanel = new CrewIntensityChartPanel();
		add(myCrewAirChartPanel);
		add(myCrewFoodChartPanel);
		add(myCrewWaterChartPanel);
		add(myCrewIntensityChartPanel);
	}
	
	public void visibilityChange(boolean nowVisible){
		myCrewAirChartPanel.visibilityChange(nowVisible);
		myCrewFoodChartPanel.visibilityChange(nowVisible);
		myCrewWaterChartPanel.visibilityChange(nowVisible);
	}
}
