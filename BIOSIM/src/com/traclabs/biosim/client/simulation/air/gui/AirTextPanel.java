package biosim.client.simulation.air.gui;

import biosim.client.framework.gui.*;
import biosim.client.util.*;
import biosim.idl.simulation.air.*;
import javax.swing.*;
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
	private JPanel O2StorePanel;
	private JLabel O2StoreLevelLabel;
	private JPanel CO2StorePanel;
	private JLabel CO2StoreLevelLabel;
	private JPanel H2StorePanel;
	private JLabel H2StoreLevelLabel;
	private JPanel nitrogenStorePanel;
	private JLabel nitrogenStoreLevelLabel;
	//Servers required for data polling
	private AirRS myAirRS;
	private O2Store myO2Store;
	private CO2Store myCO2Store;
	private H2Store myH2Store;
	private NitrogenStore myNitrogenStore;
	//For formatting floats
	private DecimalFormat numFormat;

	/**
	* Creates and registers this panel.
	*/
	public AirTextPanel(){
		BioHolder myBioHolder = BioHolderInitializer.getBioHolder();
		myAirRS = (AirRS)(myBioHolder.theAirRSModules.get(0));
		myO2Store = (O2Store)(myBioHolder.theO2Stores.get(0));
		myCO2Store = (CO2Store)(myBioHolder.theCO2Stores.get(0));
		myH2Store = (H2Store)(myBioHolder.theH2Stores.get(0));
		myNitrogenStore = (NitrogenStore)(myBioHolder.theNitrogenStores.get(0));
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

		O2StorePanel = new JPanel();
		O2StorePanel.setLayout(new GridLayout(1,1));
		O2StorePanel.setBorder(BorderFactory.createTitledBorder("O2 Store"));
		O2StoreLevelLabel =    new JLabel("O2 level:    "+numFormat.format(myO2Store.getLevel())+" moles");
		O2StorePanel.add(O2StoreLevelLabel);

		CO2StorePanel = new JPanel();
		CO2StorePanel.setLayout(new GridLayout(1,1));
		CO2StorePanel.setBorder(BorderFactory.createTitledBorder("CO2 Store"));
		CO2StoreLevelLabel =    new JLabel("CO2 level:  "+numFormat.format(myCO2Store.getLevel())+" moles");
		CO2StorePanel.add(CO2StoreLevelLabel);
		
		H2StorePanel = new JPanel();
		H2StorePanel.setLayout(new GridLayout(1,1));
		H2StorePanel.setBorder(BorderFactory.createTitledBorder("H2 Store"));
		H2StoreLevelLabel =    new JLabel("H2 level:  "+numFormat.format(myH2Store.getLevel())+" moles");
		H2StorePanel.add(H2StoreLevelLabel);
		
		nitrogenStorePanel = new JPanel();
		nitrogenStorePanel.setLayout(new GridLayout(1,1));
		nitrogenStorePanel.setBorder(BorderFactory.createTitledBorder("Nitrogen Store"));
		nitrogenStoreLevelLabel =    new JLabel("Nitrogen level:  "+numFormat.format(myNitrogenStore.getLevel())+" moles");
		nitrogenStorePanel.add(nitrogenStoreLevelLabel);
		
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
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 2.0;
		c.weighty = 1.0;
		c.gridheight = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(H2StorePanel, c);
		add(H2StorePanel);
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 2.0;
		c.weighty = 1.0;
		c.gridheight = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(nitrogenStorePanel, c);
		add(nitrogenStorePanel);
	}
	
	/**
	 * Updates every label on the panel with new data pulled from the servers.
	 */
	public void refresh(){
		CO2StoreLevelLabel.setText(   "CO2 level:      "+numFormat.format(myCO2Store.getLevel())+" moles");
		H2StoreLevelLabel.setText(   "H2 level:      "+numFormat.format(myH2Store.getLevel())+" moles");
		nitrogenStoreLevelLabel.setText(   "Nitrogen level:      "+numFormat.format(myNitrogenStore.getLevel())+" moles");
		O2StoreLevelLabel.setText(    "O2 level:       "+numFormat.format(myO2Store.getLevel())+" moles");
	}
}
