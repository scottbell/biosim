package biosim.client.water.gui;

import biosim.client.framework.*;
import biosim.idl.water.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;
import java.text.*;

public class WaterPanel extends JPanel implements BioSimulatorListener
{
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

	private WaterRS myWaterRS;
	private PotableWaterStore myPotableWaterStore;
	private GreyWaterStore myGreyWaterStore;
	private DirtyWaterStore myDirtyWaterStore;
	private BioSimulator myBioSimulator;
	private DecimalFormat numFormat;

	public WaterPanel(BioSimulator pBioSimulator){
		myBioSimulator = pBioSimulator;
		myWaterRS = (WaterRS)(myBioSimulator.getBioModule(BioSimulator.waterRSName));
		myPotableWaterStore = (PotableWaterStore)(myBioSimulator.getBioModule(BioSimulator.potableWaterStoreName));
		myDirtyWaterStore = (DirtyWaterStore)(myBioSimulator.getBioModule(BioSimulator.dirtyWaterStoreName));
		myGreyWaterStore = (GreyWaterStore)(myBioSimulator.getBioModule(BioSimulator.greyWaterStoreName));
		buildGui();
		myBioSimulator.registerListener(this);
	}

	private void buildGui(){
		numFormat = new DecimalFormat("#,###.00;(#)");
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
	
	private void createWaterRSStatusPanel(){
		waterRSStatusPanel = new JPanel();
		waterRSStatusPanel.setLayout(new GridLayout(4,1));
	        waterRSBWPStatusLabel =new JLabel("BWP status: "+"empty");
	        waterRSROStatusLabel =  new JLabel("RO status:  "+"empty");
	        waterRSAESStatusLabel = new JLabel("AES status: "+"empty");
	        waterRSPPSStatusLabel = new JLabel("PPS status: "+"empty");;
		waterRSStatusPanel.add(waterRSBWPStatusLabel);
		waterRSStatusPanel.add(waterRSROStatusLabel);
		waterRSStatusPanel.add(waterRSAESStatusLabel);
		waterRSStatusPanel.add(waterRSPPSStatusLabel);
	}

	private void createDirtyWaterStorePanel(){
	        dirtyWaterStorePanel = new JPanel();
	        dirtyWaterStorePanel.setLayout(new GridLayout(1,1));
	        dirtyWaterStorePanel.setBorder(BorderFactory.createTitledBorder("Dirty Water Store"));
	        dirtyWaterStoreLevelLabel =    new JLabel("water level:    "+numFormat.format(myDirtyWaterStore.getLevel())+" L");
	        dirtyWaterStorePanel.add(dirtyWaterStoreLevelLabel);
	}

	private void createGreyWaterStorePanel(){
	        greyWaterStorePanel = new JPanel();
	        greyWaterStorePanel.setLayout(new GridLayout(1,1));
	        greyWaterStorePanel.setBorder(BorderFactory.createTitledBorder("Grey Water Store"));
	        greyWaterStoreLevelLabel =    new JLabel("water level:    "+numFormat.format(myGreyWaterStore.getLevel())+" L");
	        greyWaterStorePanel.add(greyWaterStoreLevelLabel);
	}

	private void createPotableWaterStorePanel(){
	        potableWaterStorePanel = new JPanel();
	        potableWaterStorePanel.setLayout(new GridLayout(1,1));
	        potableWaterStorePanel.setBorder(BorderFactory.createTitledBorder("Potable Water Store"));
	        potableWaterStoreLevelLabel =    new JLabel("water level:    "+numFormat.format(myPotableWaterStore.getLevel())+" L");
	        potableWaterStorePanel.add(potableWaterStoreLevelLabel);
	}
	
	private String coallateAESStatus(){
		return null;
	}
	
	private String coallateBWPStatus(){
		return null;
	}
	
	private String coallateROStatus(){
		return null;
	}
	
	private String coallatePPSStatus(){
		return null;
	}

	public void processTick(){
		waterRSPotableWaterProducedLabel.setText("potable water produced:   "+numFormat.format(myWaterRS.getPotableWaterProduced())+" L");
		waterRSGreyWaterProducedLabel.setText("grey water produced:        "+numFormat.format(myWaterRS.getGreyWaterProduced())+" L");
		waterRSDirtyWaterConsumedLabel.setText("dirty water consumed:      "+numFormat.format(myWaterRS.getDirtyWaterConsumed())+" L");
		waterRSPowerConsumedLabel.setText("power consumed:             "+numFormat.format(myWaterRS.getPowerConsumed())+" W");
		waterRSGreyWaterConsumedLabel.setText("grey water consumed:      "+numFormat.format(myWaterRS.getGreyWaterConsumed())+" L");
		waterRSAESStatusLabel.setText("AES status: "+"empty");
		waterRSBWPStatusLabel.setText("BWP status: "+"empty");
		waterRSROStatusLabel.setText("RO status:  "+"empty");
		waterRSPPSStatusLabel.setText("PPS status: "+"empty");
		potableWaterStoreLevelLabel.setText("water level:    "+numFormat.format(myPotableWaterStore.getLevel())+" L");
		greyWaterStoreLevelLabel.setText("water level:    "+numFormat.format(myGreyWaterStore.getLevel())+" L");
		dirtyWaterStoreLevelLabel.setText("water level:    "+numFormat.format(myDirtyWaterStore.getLevel())+" L");
	}
}
