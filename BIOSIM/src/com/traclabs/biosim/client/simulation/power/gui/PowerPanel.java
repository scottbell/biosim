package biosim.client.power.gui;

import biosim.client.framework.*;
import biosim.idl.power.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;
import java.text.*;

public class PowerPanel extends JPanel implements BioSimulatorListener
{
	private JPanel powerPSPanel;
	private JLabel powerPSPowerProducedLabel;
	private JPanel powerStorePanel;
	private JLabel powerStoreLevelLabel;

	private PowerPS myPowerPS;
	private PowerStore myPowerStore;
	private BioSimulator myBioSimulator;
	private DecimalFormat numFormat;

	public PowerPanel(BioSimulator pBioSimulator){
		myBioSimulator = pBioSimulator;
		myPowerPS = (PowerPS)(myBioSimulator.getBioModule(BioSimulator.powerPSName));
		myPowerStore = (PowerStore)(myBioSimulator.getBioModule(BioSimulator.powerStoreName));
		buildGui();
		myBioSimulator.registerListener(this);
	}

	private void buildGui(){
		numFormat = new DecimalFormat("#,###.00;(#)");
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
		powerStoreLevelLabel =    new JLabel("power level:    "+numFormat.format(myPowerStore.getPowerLevel())+" W");
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

	public void processTick(){
		powerPSPowerProducedLabel.setText("power produced:         "+numFormat.format(myPowerPS.getPowerProduced())+" W");
		powerStoreLevelLabel.setText("power level:    "+numFormat.format(myPowerStore.getPowerLevel())+" W");
	}
}
