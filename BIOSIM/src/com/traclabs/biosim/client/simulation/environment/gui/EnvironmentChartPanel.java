package biosim.client.simulation.environment.gui;

import javax.swing.*;
import java.awt.*;
import biosim.client.framework.gui.*;
import biosim.idl.simulation.environment.*;
import biosim.client.util.*;

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
		BioHolder myBioHolder = BioHolderInitializer.getBioHolder();
		SimEnvironment myCrewEnvironment = (SimEnvironment)(myBioHolder.theSimEnvironments.get(0));
		SimEnvironment myPlantEnvironment = (SimEnvironment)(myBioHolder.theSimEnvironments.get(1));
		myCrewEnvironmentPieChartPanel = new EnvironmentPieChartPanel(myCrewEnvironment, myCrewEnvironment.getModuleName());
		myPlantEnvironmentPieChartPanel = new EnvironmentPieChartPanel(myPlantEnvironment, myCrewEnvironment.getModuleName());
		
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
