 package biosim.client.power.gui;

import biosim.client.framework.*;
import biosim.client.framework.gui.*;
import biosim.idl.power.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;
import java.text.*;
/**
 * This is the JPanel that displays information about the Power Production System and it's store.
 * Each tick it polls each power related server for new information regarding these systems.
 *
 * @author    Scott Bell
 */

public class PowerTextPanel extends BioTabPanel
{
	//Various GUI componenets
	private JPanel powerPSPanel;
	private JLabel powerPSPowerProducedLabel;
	private JPanel powerStorePanel;
	private JLabel powerStoreLevelLabel;
	//Servers required for data polling
	private PowerPS myPowerPS;
	private PowerStore myPowerStore;
	//Used for registereing this panel (for knowing when a tick occurs)
	private BioSimulator myBioSimulator;
	//For formatting floats
	private DecimalFormat numFormat;
	
	/**
	* Creates and registers this panel.
	* @param pBioSimulator	The Biosimulator this Panel will register itself with.
	*/
	public PowerTextPanel(BioSimulator pBioSimulator){
		myBioSimulator = pBioSimulator;
		myPowerPS = (PowerPS)(myBioSimulator.getBioModule(BioSimulator.powerPSName));
		myPowerStore = (PowerStore)(myBioSimulator.getBioModule(BioSimulator.powerStoreName));
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

		powerPSPanel = new JPanel();
		powerPSPanel.setLayout(new GridLayout(1,1));
		powerPSPanel.setBorder(BorderFactory.createTitledBorder("Power Production System"));
		powerPSPowerProducedLabel =    new JLabel("power produced:         "+numFormat.format(myPowerPS.getPowerProduced())+" W");
		powerPSPanel.add(powerPSPowerProducedLabel);

		powerStorePanel = new JPanel();
		powerStorePanel.setLayout(new GridLayout(1,1));
		powerStorePanel.setBorder(BorderFactory.createTitledBorder("Power Store"));
		powerStoreLevelLabel =    new JLabel("power level:    "+numFormat.format(myPowerStore.getLevel())+" W");
		powerStorePanel.add(powerStoreLevelLabel);

		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = 1;
		c.weighty = 1.0;
		gridbag.setConstraints(powerPSPanel, c);
		add(powerPSPanel);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		gridbag.setConstraints(powerStorePanel, c);
		add(powerStorePanel);
	}
	
	/**
	 * Updates every label on the panel with new data pulled from the servers.
	 */
	public void processUpdate(){
		powerPSPowerProducedLabel.setText("power produced:         "+numFormat.format(myPowerPS.getPowerProduced())+" W");
		powerStoreLevelLabel.setText("power level:    "+numFormat.format(myPowerStore.getLevel())+" W");
	}
}
