package biosim.client.air.gui;

import biosim.client.framework.*;
import biosim.idl.air.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;
import java.text.*;

public class AirPanel extends JPanel implements BioSimulatorListener
{
	private JPanel airRSPanel;
	private JLabel airRSPowerLabel;
	private JLabel airRSCO2ConsumedLabel;
	private JLabel airRSO2ProducedLabel;
	private JPanel O2StorePanel;
	private JLabel O2StoreLevelLabel;
	private JPanel CO2StorePanel;
	private JLabel CO2StoreLevelLabel;

	private AirRS myAirRS;
	private O2Store myO2Store;
	private CO2Store myCO2Store;
	private BioSimulator myBioSimulator;
	private DecimalFormat numFormat;

	public AirPanel(BioSimulator pBioSimulator){
		myBioSimulator = pBioSimulator;
		myAirRS = (AirRS)(myBioSimulator.getBioModule(BioSimulator.airRSName));
		myO2Store = (O2Store)(myBioSimulator.getBioModule(BioSimulator.O2StoreName));
		myCO2Store = (CO2Store)(myBioSimulator.getBioModule(BioSimulator.CO2StoreName));
		buildGui();
		myBioSimulator.registerListener(this);
	}

	private void buildGui(){
		numFormat = new DecimalFormat("#,###.00;(#)");
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(gridbag);

		airRSPanel = new JPanel();
		airRSPanel.setLayout(new GridLayout(3,1));
		airRSPanel.setBorder(BorderFactory.createTitledBorder("Air Recovery System"));
		airRSO2ProducedLabel =    new JLabel("O2 produced:         "+numFormat.format(myAirRS.getO2Produced())+" L");
		airRSCO2ConsumedLabel = new JLabel("CO2 consumed:     "+numFormat.format(myAirRS.getCO2Consumed())+" L");
		airRSPowerLabel =              new JLabel("power consumed:  "+numFormat.format(myAirRS.getPowerConsumed())+" W");
		airRSPanel.add(airRSO2ProducedLabel);
		airRSPanel.add(airRSCO2ConsumedLabel);
		airRSPanel.add(airRSPowerLabel);

		O2StorePanel = new JPanel();
		O2StorePanel.setLayout(new GridLayout(1,1));
		O2StorePanel.setBorder(BorderFactory.createTitledBorder("O2 Store"));
		O2StoreLevelLabel =    new JLabel("O2 level:    "+numFormat.format(myO2Store.getO2Level())+" L");
		O2StorePanel.add(O2StoreLevelLabel);
		
		CO2StorePanel = new JPanel();
		CO2StorePanel.setLayout(new GridLayout(1,1));
		CO2StorePanel.setBorder(BorderFactory.createTitledBorder("CO2 Store"));
		CO2StoreLevelLabel =    new JLabel("CO2 level:  "+numFormat.format(myCO2Store.getCO2Level())+" L");
		CO2StorePanel.add(CO2StoreLevelLabel);
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		gridbag.setConstraints(airRSPanel, c);
		add(airRSPanel);
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		gridbag.setConstraints(O2StorePanel, c);
		add(O2StorePanel);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 2.0;
		c.weighty = 1.0;
		c.gridheight = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(CO2StorePanel, c);
		add(CO2StorePanel);
	}

	public void processTick(){
		airRSO2ProducedLabel.setText("O2 produced:         "+numFormat.format(myAirRS.getO2Produced())+" L");
		airRSCO2ConsumedLabel.setText("CO2 consumed:     "+numFormat.format(myAirRS.getCO2Consumed())+" L");
		airRSPowerLabel.setText("power consumed:  "+numFormat.format(myAirRS.getPowerConsumed())+" W");
		CO2StoreLevelLabel.setText("CO2 level:  "+numFormat.format(myCO2Store.getCO2Level())+" L");
		O2StoreLevelLabel.setText("O2 level:    "+numFormat.format(myO2Store.getO2Level())+" L");
	}
}
