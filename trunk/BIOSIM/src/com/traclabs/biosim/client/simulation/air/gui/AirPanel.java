package biosim.client.air.gui;

import biosim.client.framework.*;
import biosim.idl.air.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;
import java.text.*;
/**
 * This is the JPanel that displays information about the AirRS, the O2 store, and the CO2 Store.
 * Each tick it polls each air related server for new information regarding these systems.
 *
 * @author    Scott Bell
 */
 
public class AirPanel extends JPanel implements BioSimulatorListener
{
	//Various GUI componenets
	private JPanel airRSPanel;
	private JLabel airRSStatusLabel;
	private JLabel airRSPowerLabel;
	private JLabel airRSCO2ConsumedLabel;
	private JLabel airRSO2ProducedLabel;
	private JLabel airRSCO2ProducedLabel;
	private JPanel O2StorePanel;
	private JLabel O2StoreLevelLabel;
	private JPanel CO2StorePanel;
	private JLabel CO2StoreLevelLabel;
	//Servers required for data polling
	private AirRS myAirRS;
	private O2Store myO2Store;
	private CO2Store myCO2Store;
	//Used for registereing this panel (for knowing when a tick occurs)
	private BioSimulator myBioSimulator;
	//For formatting floats
	private DecimalFormat numFormat;

	/**
	* Creates and registers this panel.
	* @param pBioSimulator	The Biosimulator this Panel will register itself with.
	*/
	public AirPanel(BioSimulator pBioSimulator){
		myBioSimulator = pBioSimulator;
		myAirRS = (AirRS)(myBioSimulator.getBioModule(BioSimulator.airRSName));
		myO2Store = (O2Store)(myBioSimulator.getBioModule(BioSimulator.O2StoreName));
		myCO2Store = (CO2Store)(myBioSimulator.getBioModule(BioSimulator.CO2StoreName));
		buildGui();
		myBioSimulator.registerListener(this);
	}

	/**
	* Contructs GUI components, adds them to the panel.
	*/
	private void buildGui(){
		numFormat = new DecimalFormat("#,##0.00;(#)");
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(gridbag);

		airRSPanel = new JPanel();
		airRSPanel.setLayout(new GridLayout(5,1));
		airRSPanel.setBorder(BorderFactory.createTitledBorder("Air Recovery System"));
		airRSStatusLabel =              new JLabel("status:                   "+coallateAirRSStatus());
		airRSO2ProducedLabel =    new JLabel("O2 produced:         "+numFormat.format(myAirRS.getO2Produced())+" L");
		airRSCO2ConsumedLabel = new JLabel("CO2 consumed:     "+numFormat.format(myAirRS.getCO2Consumed())+" L");
		airRSCO2ProducedLabel =  new JLabel("CO2 produced:      "+numFormat.format(myAirRS.getCO2Produced())+" L");
		airRSPowerLabel =              new JLabel("power consumed:  "+numFormat.format(myAirRS.getPowerConsumed())+" W");
		airRSPanel.add(airRSStatusLabel);
		airRSPanel.add(airRSO2ProducedLabel);
		airRSPanel.add(airRSCO2ConsumedLabel);
		airRSPanel.add(airRSCO2ProducedLabel);
		airRSPanel.add(airRSPowerLabel);

		O2StorePanel = new JPanel();
		O2StorePanel.setLayout(new GridLayout(1,1));
		O2StorePanel.setBorder(BorderFactory.createTitledBorder("O2 Store"));
		O2StoreLevelLabel =    new JLabel("O2 level:    "+numFormat.format(myO2Store.getLevel())+" L");
		O2StorePanel.add(O2StoreLevelLabel);

		CO2StorePanel = new JPanel();
		CO2StorePanel.setLayout(new GridLayout(1,1));
		CO2StorePanel.setBorder(BorderFactory.createTitledBorder("CO2 Store"));
		CO2StoreLevelLabel =    new JLabel("CO2 level:  "+numFormat.format(myCO2Store.getLevel())+" L");
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

	/**
	 * Checks the status of AirRS and constructs a string describing it.
	 * @return	A String representing the status of the AirRS
	 */
	private String coallateAirRSStatus(){
		StringBuffer statusBuffer = new StringBuffer();
		if (!myAirRS.hasPower())
			statusBuffer.append("needs power, ");
		if (!myAirRS.hasCO2())
			statusBuffer.append("needs CO2, ");
		if (statusBuffer.length() < 1)
			return "nominal";
		else{
			statusBuffer.delete(statusBuffer.length() -2, statusBuffer.length());
			return statusBuffer.toString();
		}
	}
	
	/**
	 * Updates every label on the panel with new data pulled from the servers.
	 */
	public void processTick(){
		airRSStatusLabel.setText("status:                   "+coallateAirRSStatus());
		airRSO2ProducedLabel.setText("O2 produced:         "+numFormat.format(myAirRS.getO2Produced())+" L");
		airRSCO2ConsumedLabel.setText("CO2 consumed:     "+numFormat.format(myAirRS.getCO2Consumed())+" L");
		airRSPowerLabel.setText("power consumed:  "+numFormat.format(myAirRS.getPowerConsumed())+" W");
		airRSCO2ProducedLabel.setText("CO2 produced:      "+numFormat.format(myAirRS.getCO2Produced())+" L");
		CO2StoreLevelLabel.setText("CO2 level:  "+numFormat.format(myCO2Store.getLevel())+" L");
		O2StoreLevelLabel.setText("O2 level:    "+numFormat.format(myO2Store.getLevel())+" L");
	}
}
