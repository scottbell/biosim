package biosim.client.simulation.environment.gui;

import biosim.client.framework.gui.*;
import biosim.client.simulation.framework.*;
import biosim.idl.simulation.environment.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;
import java.text.*;
/** 
 * This is the JPanel that displays information about the environment (gas levels, current time, etc.)
 *
 * @author    Scott Bell
 */

public class EnvironmentTextPanel extends TimedPanel
{
	//Various GUI componenets
	private JLabel tickLabel;
	private JPanel tickPanel;
	private JLabel O2Label;
	private JLabel CO2Label;
	private JLabel otherLabel;
	private JPanel airPanel;
	//Server required for data polling
	private SimEnvironment mySimEnvironment;
	//For formatting floats
	private DecimalFormat numFormat;
	
	/**
	* Creates and registers this panel.
	* @param pBioSimulator	The Biosimulator this Panel will register itself with.
	*/
	public EnvironmentTextPanel(String environmentName){
		super();
		mySimEnvironment = (SimEnvironment)(BioHolder.getBioModule(environmentName));
		buildGui();
	}
	
	/**
	* Contructs GUI components, adds them to the panel.
	*/
	private void buildGui(){
		numFormat = new DecimalFormat("#,##0.00;(#)");
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(gridbag);
		JLabel moduleLabel = new JLabel("Environment");
		if (mySimEnvironment.getModuleName().startsWith(BioHolder.crewEnvironmentName))
			moduleLabel.setText("Crew Environment");
		else if (mySimEnvironment.getModuleName().startsWith(BioHolder.plantEnvironmentName))
			moduleLabel.setText("Plant Environment");
		JPanel modulePanel =  new JPanel();
		modulePanel.setLayout(new BorderLayout());
		modulePanel.setBorder(BorderFactory.createTitledBorder("Module"));
		modulePanel.add(moduleLabel, BorderLayout.CENTER);
		
		
		long ticksExpired = mySimEnvironment.getTicks();
		tickLabel = new JLabel(ticksExpired + " hours ("+(ticksExpired/24)+" days)");
		tickPanel = new JPanel();
		tickPanel.setLayout(new BorderLayout());
		tickPanel.setBorder(BorderFactory.createTitledBorder("Time"));
		tickPanel.add(tickLabel, BorderLayout.CENTER);

		airPanel = new JPanel();
		airPanel.setLayout(new GridLayout(3,1));
		airPanel.setBorder(BorderFactory.createTitledBorder("Air"));
		O2Label =    new JLabel("O2:     "+numFormat.format(mySimEnvironment.getO2Level()) +" L");
		CO2Label =  new JLabel("CO2:   "+numFormat.format(mySimEnvironment.getCO2Level()) + " L");
		otherLabel = new JLabel("other:  "+numFormat.format(mySimEnvironment.getOtherLevel()) + " L");
		airPanel.add(O2Label);
		airPanel.add(CO2Label);
		airPanel.add(otherLabel);

		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		gridbag.setConstraints(modulePanel, c);
		add(modulePanel);
		
		gridbag.setConstraints(tickPanel, c);
		add(tickPanel);

		gridbag.setConstraints(airPanel, c);
		add(airPanel);
	}
	
	/**
	 * Updates every label on the panel with new data pulled from the servers.
	 */
	public void refresh(){
		long ticksExpired = mySimEnvironment.getTicks();
		tickLabel.setText(ticksExpired + " hours ("+(ticksExpired/24)+" days)");
		O2Label.setText("O2:     "+numFormat.format(mySimEnvironment.getO2Level()) +" L");
		CO2Label.setText("CO2:   "+numFormat.format(mySimEnvironment.getCO2Level()) + " L");
		otherLabel.setText("other:  "+numFormat.format(mySimEnvironment.getOtherLevel()) + " L");
	}
}
