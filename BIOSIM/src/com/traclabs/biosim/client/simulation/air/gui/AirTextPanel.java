package biosim.client.simulation.air.gui;

import biosim.client.framework.gui.*;
import biosim.client.framework.gui.*;
import biosim.client.simulation.framework.*;
import biosim.idl.simulation.air.*;
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
 
public class AirTextPanel extends TimedPanel
{
	//Various GUI componenets
	private JPanel airRSPanel;
	private JLabel airRSVCCRStatusLabel;
	private JLabel airRSCRSStatusLabel;
	private JLabel airRSOGSStatusLabel;
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
	//For formatting floats
	private DecimalFormat numFormat;

	/**
	* Creates and registers this panel.
	* @param pBioSimulator	The Biosimulator this Panel will register itself with.
	*/
	public AirTextPanel(){
		myAirRS = (AirRS)(BioHolder.getBioModule(BioHolder.airRSName));
		myO2Store = (O2Store)(BioHolder.getBioModule(BioHolder.O2StoreName));
		myCO2Store = (CO2Store)(BioHolder.getBioModule(BioHolder.CO2StoreName));
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

		airRSPanel = new JPanel();
		airRSPanel.setLayout(new GridLayout(7,1));
		airRSPanel.setBorder(BorderFactory.createTitledBorder("Air Recovery System"));
		airRSVCCRStatusLabel =             new JLabel("VCCR status:    "+coallateVCCRStatus());
		airRSCRSStatusLabel =              new JLabel("CRS status:     "+coallateCRSStatus());
		airRSOGSStatusLabel =              new JLabel("OGS status:     "+coallateOGSStatus());
		airRSO2ProducedLabel =             new JLabel("O2 produced:    "+numFormat.format(myAirRS.getO2Produced())+" L");
		airRSCO2ConsumedLabel =            new JLabel("CO2 consumed:   "+numFormat.format(myAirRS.getCO2Consumed())+" L");
		airRSCO2ProducedLabel =            new JLabel("CO2 produced:   "+numFormat.format(myAirRS.getCO2Produced())+" L");
		airRSPowerLabel =                  new JLabel("power consumed: "+numFormat.format(myAirRS.getPowerConsumed())+" W");
		airRSPanel.add(airRSVCCRStatusLabel);
		airRSPanel.add(airRSCRSStatusLabel);
		airRSPanel.add(airRSOGSStatusLabel);
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
	 * Checks the status of VCCR AirRS subsystem and constructs a string describing it.
	 * @return	A String representing the status of the VCCR
	 */
	private String coallateVCCRStatus(){
		StringBuffer statusBuffer = new StringBuffer();
		if (!myAirRS.VCCRHasPower())
			statusBuffer.append("needs power, ");
		if (!myAirRS.VCCRHasEnoughAir())
			statusBuffer.append("needs air, ");
		if (statusBuffer.length() < 1)
			return "nominal";
		else{
			statusBuffer.delete(statusBuffer.length() -2, statusBuffer.length());
			return statusBuffer.toString();
		}
	}
	
	/**
	 * Checks the status of CRS AirRS subsystem and constructs a string describing it.
	 * @return	A String representing the status of the CRS
	 */
	private String coallateCRSStatus(){
		StringBuffer statusBuffer = new StringBuffer();
		if (!myAirRS.CRSHasPower())
			statusBuffer.append("needs power, ");
		if (!myAirRS.CRSHasEnoughCO2())
			statusBuffer.append("needs CO2, ");
		if (!myAirRS.CRSHasEnoughH2())
				statusBuffer.append("needs H2, ");
		if (statusBuffer.length() < 1)
			return "nominal";
		else{
			statusBuffer.delete(statusBuffer.length() -2, statusBuffer.length());
			return statusBuffer.toString();
		}
	}
	
	/**
	 * Checks the status of OGS AirRS subsystem and constructs a string describing it.
	 * @return	A String representing the status of the OGS
	 */
	private String coallateOGSStatus(){
		StringBuffer statusBuffer = new StringBuffer();
		if (!myAirRS.OGSHasPower())
			statusBuffer.append("needs power, ");
		if (!myAirRS.OGSHasEnoughH2O())
			statusBuffer.append("needs H2O, ");
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
	public void refresh(){
		airRSVCCRStatusLabel.setText( "VCCR status:    "+coallateVCCRStatus());
		airRSCRSStatusLabel.setText(  "CRS status:     "+coallateCRSStatus());
		airRSOGSStatusLabel.setText(  "OGS status:     "+coallateOGSStatus());
		airRSO2ProducedLabel.setText( "O2 produced:    "+numFormat.format(myAirRS.getO2Produced())+" L");
		airRSCO2ConsumedLabel.setText("CO2 consumed:   "+numFormat.format(myAirRS.getCO2Consumed())+" L");
		airRSPowerLabel.setText(      "power consumed: "+numFormat.format(myAirRS.getPowerConsumed())+" W");
		airRSCO2ProducedLabel.setText("CO2 produced:   "+numFormat.format(myAirRS.getCO2Produced())+" L");
		CO2StoreLevelLabel.setText(   "CO2 level:      "+numFormat.format(myCO2Store.getLevel())+" L");
		O2StoreLevelLabel.setText(    "O2 level:       "+numFormat.format(myO2Store.getLevel())+" L");
	}
}
