package biosim.client.simulation.environment.gui;

import biosim.client.framework.gui.*;
import biosim.idl.simulation.environment.*;
import biosim.client.util.*;
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
		BioHolder myBioHolder = BioHolderInitializer.getBioHolder();
		SimEnvironment myCrewEnvironment = (SimEnvironment)(myBioHolder.theSimEnvironments.get(0));
		SimEnvironment myPlantEnvironment = (SimEnvironment)(myBioHolder.theSimEnvironments.get(1));
		myCrewEnvironmentTextPanel = new EnvironmentTextPanel(myCrewEnvironment);
		myPlantEnvironmentTextPanel = new EnvironmentTextPanel(myPlantEnvironment);
		
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
