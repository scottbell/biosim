package biosim.client.simulation.environment.gui;

import biosim.client.simulation.framework.gui.*;
import biosim.client.simulation.framework.*;
import javax.swing.*;
import java.awt.*;
/** 
 * 
 * @author    Scott Bell
 */

public class EnvironmentDualTextPanel extends UpdatablePanel
{
	private UpdatablePanel myCrewEnvironmentTextPanel;
	private UpdatablePanel myPlantEnvironmentTextPanel;

	public EnvironmentDualTextPanel() {
		setLayout(new GridLayout(1,2));
		myCrewEnvironmentTextPanel = new EnvironmentTextPanel(BioHolder.crewEnvironmentName);
		myPlantEnvironmentTextPanel = new EnvironmentTextPanel(BioHolder.plantEnvironmentName);
		
		add(myCrewEnvironmentTextPanel);
		add(myPlantEnvironmentTextPanel);
	}
	
	public void refresh(){
		myCrewEnvironmentTextPanel.refresh();
		myPlantEnvironmentTextPanel.refresh();
	}
	
	public void visibilityChange(boolean nowVisible){
		myCrewEnvironmentTextPanel.visibilityChange(nowVisible);
		myPlantEnvironmentTextPanel.visibilityChange(nowVisible);
	}
}
