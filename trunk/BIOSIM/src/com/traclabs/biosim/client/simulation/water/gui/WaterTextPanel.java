package biosim.client.water.gui;

import biosim.client.framework.gui.*;
import biosim.client.framework.*;
import biosim.idl.water.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;
import java.text.*;
/**
 * This is the JPanel that displays information about the Water RS and the water stores.
 * Each tick it polls each water related server for new information regarding these systems.
 *
 * @author    Scott Bell
 */


public class WaterTextPanel extends TimedPanel
{
	//Various GUI componenets
	private JPanel waterRSPanel;
	private JPanel waterRSLevelPanel;
	private JLabel waterRSPowerConsumedLabel;
	private JLabel waterRSDirtyWaterConsumedLabel;
	private JLabel waterRSGreyWaterProducedLabel;
	private JLabel waterRSPotableWaterProducedLabel;
	private JLabel waterRSGreyWaterConsumedLabel;
	private JPanel waterRSStatusPanel;
	private JLabel waterRSAESStatusLabel;
	private JLabel waterRSBWPStatusLabel;
	private JLabel waterRSROStatusLabel;
	private JLabel waterRSPPSStatusLabel;
	private JPanel potableWaterStorePanel;
	private JLabel potableWaterStoreLevelLabel;
	private JPanel greyWaterStorePanel;
	private JLabel greyWaterStoreLevelLabel;
	private JPanel dirtyWaterStorePanel;
	private JLabel dirtyWaterStoreLevelLabel;
	//Servers required for data polling
	private WaterRS myWaterRS;
	private PotableWaterStore myPotableWaterStore;
	private GreyWaterStore myGreyWaterStore;
	private DirtyWaterStore myDirtyWaterStore;
	//For formatting floats
	private DecimalFormat numFormat;

	/**
	* Creates and registers this panel.
	* @param pBioSimulator	The Biosimulator this Panel will register itself with.
	*/
	public WaterTextPanel(){
		myWaterRS = (WaterRS)(BioHolder.getBioModule(BioHolder.waterRSName));
		myPotableWaterStore = (PotableWaterStore)(BioHolder.getBioModule(BioHolder.potableWaterStoreName));
		myDirtyWaterStore = (DirtyWaterStore)(BioHolder.getBioModule(BioHolder.dirtyWaterStoreName));
		myGreyWaterStore = (GreyWaterStore)(BioHolder.getBioModule(BioHolder.greyWaterStoreName));
		buildGui();
	}
	
	/**
	* Contructs main GUI components, adds them to the panel.
	*/
	private void buildGui(){
		numFormat = new DecimalFormat("#,##0.00;(#)");
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(gridbag);
		
		createWaterRSPanel();
		createDirtyWaterStorePanel();
		createGreyWaterStorePanel();
		createPotableWaterStorePanel();
		
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth =  GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		gridbag.setConstraints(waterRSPanel, c);
		add(waterRSPanel);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		gridbag.setConstraints(potableWaterStorePanel, c);
		add(potableWaterStorePanel);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		gridbag.setConstraints(greyWaterStorePanel, c);
		add(greyWaterStorePanel);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		gridbag.setConstraints(dirtyWaterStorePanel, c);
		add(dirtyWaterStorePanel);
	}
	
	/**
	* Contructs WaterRS GUI components, adds them to the WaterRS panel.
	*/
	private void createWaterRSPanel(){
	        waterRSPanel = new JPanel();
	        waterRSPanel.setBorder(BorderFactory.createTitledBorder("Water Recovery System"));
		createWaterRSLevelPanel();
		createWaterRSStatusPanel();
		
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		waterRSPanel.setLayout(gridbag);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		gridbag.setConstraints(waterRSStatusPanel, c);
		waterRSPanel.add(waterRSStatusPanel);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		gridbag.setConstraints(waterRSLevelPanel, c);
		waterRSPanel.add(waterRSLevelPanel);
	}
	
	/**
	* Contructs WaterRS water levels GUI components, adds them to the WaterRS level panel.
	*/
	private void createWaterRSLevelPanel(){
		waterRSLevelPanel = new JPanel();
		waterRSLevelPanel.setLayout(new GridLayout(5,1));
		waterRSPotableWaterProducedLabel =new JLabel("potable water produced:   "+numFormat.format(myWaterRS.getPotableWaterProduced())+" L");
	        waterRSGreyWaterProducedLabel =    new JLabel("grey water produced:        "+numFormat.format(myWaterRS.getGreyWaterProduced())+" L");
	        waterRSDirtyWaterConsumedLabel =   new JLabel("dirty water consumed:      "+numFormat.format(myWaterRS.getDirtyWaterConsumed())+" L");
	        waterRSGreyWaterConsumedLabel =    new JLabel("grey water consumed:      "+numFormat.format(myWaterRS.getGreyWaterConsumed())+" L");
	        waterRSPowerConsumedLabel =         new JLabel("power consumed:             "+numFormat.format(myWaterRS.getPowerConsumed())+" W");
		waterRSLevelPanel.add(waterRSPotableWaterProducedLabel);
		waterRSLevelPanel.add(waterRSGreyWaterProducedLabel);
		waterRSLevelPanel.add(waterRSDirtyWaterConsumedLabel);
		waterRSLevelPanel.add(waterRSGreyWaterConsumedLabel);
		waterRSLevelPanel.add(waterRSPowerConsumedLabel);
	}
	
	/**
	* Contructs WaterRS status GUI components, adds them to the WaterRS status panel.
	*/
	private void createWaterRSStatusPanel(){
		waterRSStatusPanel = new JPanel();
		waterRSStatusPanel.setLayout(new GridLayout(4,1));
	        waterRSBWPStatusLabel =new JLabel("BWP status: "+coallateBWPStatus());
	        waterRSROStatusLabel =  new JLabel("RO status:  "+coallateROStatus());
	        waterRSAESStatusLabel = new JLabel("AES status: "+coallateAESStatus());
	        waterRSPPSStatusLabel = new JLabel("PPS status: "+coallatePPSStatus());
		waterRSStatusPanel.add(waterRSBWPStatusLabel);
		waterRSStatusPanel.add(waterRSROStatusLabel);
		waterRSStatusPanel.add(waterRSAESStatusLabel);
		waterRSStatusPanel.add(waterRSPPSStatusLabel);
	}
	
	/**
	* Contructs dirty water store GUI components, adds them to the dirty water store panel.
	*/
	private void createDirtyWaterStorePanel(){
	        dirtyWaterStorePanel = new JPanel();
	        dirtyWaterStorePanel.setLayout(new GridLayout(1,1));
	        dirtyWaterStorePanel.setBorder(BorderFactory.createTitledBorder("Dirty Water Store"));
	        dirtyWaterStoreLevelLabel =    new JLabel("water level:    "+numFormat.format(myDirtyWaterStore.getLevel())+" L");
	        dirtyWaterStorePanel.add(dirtyWaterStoreLevelLabel);
	}
	
	/**
	* Contructs grey water store GUI components, adds them to the grey water store panel.
	*/
	private void createGreyWaterStorePanel(){
	        greyWaterStorePanel = new JPanel();
	        greyWaterStorePanel.setLayout(new GridLayout(1,1));
	        greyWaterStorePanel.setBorder(BorderFactory.createTitledBorder("Grey Water Store"));
	        greyWaterStoreLevelLabel =    new JLabel("water level:    "+numFormat.format(myGreyWaterStore.getLevel())+" L");
	        greyWaterStorePanel.add(greyWaterStoreLevelLabel);
	}
	
	/**
	* Contructs potable water store GUI components, adds them to the potable water store panel.
	*/
	private void createPotableWaterStorePanel(){
	        potableWaterStorePanel = new JPanel();
	        potableWaterStorePanel.setLayout(new GridLayout(1,1));
	        potableWaterStorePanel.setBorder(BorderFactory.createTitledBorder("Potable Water Store"));
	        potableWaterStoreLevelLabel =    new JLabel("water level:    "+numFormat.format(myPotableWaterStore.getLevel())+" L");
	        potableWaterStorePanel.add(potableWaterStoreLevelLabel);
	}
	
	/**
	 * Checks the status of AES WaterRS subsystem and constructs a string describing it.
	 * @return	A String representing the status of the AES
	 */
	private String coallateAESStatus(){
		if (!myWaterRS.AESIsEnabled())
			return ("disabled");
		StringBuffer statusBuffer = new StringBuffer();
		if (!myWaterRS.AESHasPower())
			statusBuffer.append("needs power, ");
		if (!myWaterRS.AESHasWater())
			statusBuffer.append("needs water, ");
		if (statusBuffer.length() < 1)
			return "nominal";
		else{
			statusBuffer.delete(statusBuffer.length() -2, statusBuffer.length());
			return statusBuffer.toString();
		}
	}
	
	/**
	 * Checks the status of BWP WaterRS subsystem and constructs a string describing it.
	 * @return	A String representing the status of the BWP
	 */
	private String coallateBWPStatus(){
		StringBuffer statusBuffer = new StringBuffer();
		if (!myWaterRS.BWPHasPower())
			statusBuffer.append("needs power, ");
		if (!myWaterRS.BWPHasWater())
			statusBuffer.append("needs water, ");
		if (statusBuffer.length() < 1)
			return "nominal";
		else{
			statusBuffer.delete(statusBuffer.length() -2, statusBuffer.length());
			return statusBuffer.toString();
		}
	}
	
	/**
	 * Checks the status of RO WaterRS subsystem and constructs a string describing it.
	 * @return	A String representing the status of the BWP
	 */
	private String coallateROStatus(){
		if (!myWaterRS.ROIsEnabled())
			return ("disabled");
		StringBuffer statusBuffer = new StringBuffer();
		if (!myWaterRS.ROHasPower())
			statusBuffer.append("needs power, ");
		if (!myWaterRS.ROHasWater())
			statusBuffer.append("needs water, ");
		if (statusBuffer.length() < 1)
			return "nominal";
		else{
			statusBuffer.delete(statusBuffer.length() -2, statusBuffer.length());
			return statusBuffer.toString();
		}
	}
	
	/**
	 * Checks the status of PPS WaterRS subsystem and constructs a string describing it.
	 * @return	A String representing the status of the BWP
	 */
	private String coallatePPSStatus(){
		StringBuffer statusBuffer = new StringBuffer();
		if (!myWaterRS.PPSHasPower())
			statusBuffer.append("needs power, ");
		if (!myWaterRS.PPSHasWater())
			statusBuffer.append("needs water, ");
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
		waterRSPotableWaterProducedLabel.setText("potable water produced:   "+numFormat.format(myWaterRS.getPotableWaterProduced())+" L");
		waterRSGreyWaterProducedLabel.setText("grey water produced:        "+numFormat.format(myWaterRS.getGreyWaterProduced())+" L");
		waterRSDirtyWaterConsumedLabel.setText("dirty water consumed:      "+numFormat.format(myWaterRS.getDirtyWaterConsumed())+" L");
		waterRSPowerConsumedLabel.setText("power consumed:             "+numFormat.format(myWaterRS.getPowerConsumed())+" W");
		waterRSGreyWaterConsumedLabel.setText("grey water consumed:      "+numFormat.format(myWaterRS.getGreyWaterConsumed())+" L");
		waterRSAESStatusLabel.setText("AES status: "+coallateAESStatus());
		waterRSBWPStatusLabel.setText("BWP status: "+coallateBWPStatus());
		waterRSROStatusLabel.setText("RO status:  "+coallateROStatus());
		waterRSPPSStatusLabel.setText("PPS status: "+coallatePPSStatus());
		potableWaterStoreLevelLabel.setText("water level:    "+numFormat.format(myPotableWaterStore.getLevel())+" L");
		greyWaterStoreLevelLabel.setText("water level:    "+numFormat.format(myGreyWaterStore.getLevel())+" L");
		dirtyWaterStoreLevelLabel.setText("water level:    "+numFormat.format(myDirtyWaterStore.getLevel())+" L");
	}
}
