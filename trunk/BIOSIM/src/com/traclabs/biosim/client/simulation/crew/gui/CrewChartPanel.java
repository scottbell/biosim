package biosim.client.crew.gui;

import javax.swing.*;
import java.awt.*;
import biosim.client.framework.gui.*;

/**
 * This is the JPanel that displays a chart about the Air
 *
 * @author    Scott Bell
 */
public class CrewChartPanel extends UpdatablePanel
{
	private UpdatablePanel myCrewAirChartPanel;
	private UpdatablePanel myCrewFoodChartPanel;
	private UpdatablePanel myCrewWaterChartPanel;
	private UpdatablePanel myCrewIntensityChartPanel;

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
	
	public void refresh(){
		myCrewAirChartPanel.refresh();
		myCrewFoodChartPanel.refresh();
		myCrewWaterChartPanel.refresh();
	}
	
	public void visibilityChange(boolean nowVisible){
		myCrewAirChartPanel.visibilityChange(nowVisible);
		myCrewFoodChartPanel.visibilityChange(nowVisible);
		myCrewWaterChartPanel.visibilityChange(nowVisible);
	}
}
