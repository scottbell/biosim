package biosim.client.environment.gui;

import biosim.client.framework.gui.*;
import biosim.client.framework.*;
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
		setLayout(new BorderLayout());
		myCrewEnvironmentTextPanel = new EnvironmentTextPanel(BioHolder.crewEnvironmentName);
		myPlantEnvironmentTextPanel = new EnvironmentTextPanel(BioHolder.plantEnvironmentName);
		
		add(myCrewEnvironmentTextPanel, BorderLayout.WEST);
		add(myPlantEnvironmentTextPanel, BorderLayout.EAST);
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
