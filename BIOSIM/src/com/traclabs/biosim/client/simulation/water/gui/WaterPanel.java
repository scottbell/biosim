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
	private JLabel waterRSPowerConsumedLabel;
	private JLabel waterRSDirtyWaterConsumedLabel;
	private JLabel waterRSGreyWaterProducedLabel;
	private JLabel waterRSPotableWaterProducedLabel;
	
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

		waterRSPanel = new JPanel();
		waterRSPanel.setLayout(new GridLayout(4,1));
		waterRSPanel.setBorder(BorderFactory.createTitledBorder("Water Recovery System"));
		waterRSPotableWaterProducedLabel =new JLabel("potable water produced:   "+numFormat.format(myWaterRS.getPotableWaterProduced())+" L");
		waterRSGreyWaterProducedLabel =    new JLabel("grey water produced:        "+numFormat.format(myWaterRS.getGreyWaterProduced())+" L");
		waterRSDirtyWaterConsumedLabel =   new JLabel("dirty water consumed:      "+numFormat.format(myWaterRS.getDirtyWaterConsumed())+" L");
		waterRSPowerConsumedLabel =         new JLabel("power consumed:             "+numFormat.format(myWaterRS.getPowerConsumed())+" W");
		waterRSPanel.add(waterRSPotableWaterProducedLabel);
		waterRSPanel.add(waterRSGreyWaterProducedLabel);
		waterRSPanel.add(waterRSDirtyWaterConsumedLabel);
		waterRSPanel.add(waterRSPowerConsumedLabel);

		potableWaterStorePanel = new JPanel();
		potableWaterStorePanel.setLayout(new GridLayout(1,1));
		potableWaterStorePanel.setBorder(BorderFactory.createTitledBorder("Potable Water Store"));
		potableWaterStoreLevelLabel =    new JLabel("water level:    "+numFormat.format(myPotableWaterStore.getWaterLevel())+" L");
		potableWaterStorePanel.add(potableWaterStoreLevelLabel);
		
		greyWaterStorePanel = new JPanel();
		greyWaterStorePanel.setLayout(new GridLayout(1,1));
		greyWaterStorePanel.setBorder(BorderFactory.createTitledBorder("Grey Water Store"));
		greyWaterStoreLevelLabel =    new JLabel("water level:    "+numFormat.format(myGreyWaterStore.getWaterLevel())+" L");
		greyWaterStorePanel.add(greyWaterStoreLevelLabel);
		
		dirtyWaterStorePanel = new JPanel();
		dirtyWaterStorePanel.setLayout(new GridLayout(1,1));
		dirtyWaterStorePanel.setBorder(BorderFactory.createTitledBorder("Dirty Water Store"));
		dirtyWaterStoreLevelLabel =    new JLabel("water level:    "+numFormat.format(myDirtyWaterStore.getWaterLevel())+" L");
		dirtyWaterStorePanel.add(dirtyWaterStoreLevelLabel);

		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = 1;
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
		c.gridwidth = 1;
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

	public void processTick(){
		waterRSPotableWaterProducedLabel.setText("potable water produced:   "+numFormat.format(myWaterRS.getPotableWaterProduced())+" L");
		waterRSGreyWaterProducedLabel.setText("grey water produced:        "+numFormat.format(myWaterRS.getGreyWaterProduced())+" L");
		waterRSDirtyWaterConsumedLabel.setText("dirty water consumed:      "+numFormat.format(myWaterRS.getDirtyWaterConsumed())+" L");
		waterRSPowerConsumedLabel.setText("power consumed:             "+numFormat.format(myWaterRS.getPowerConsumed())+" W");
		potableWaterStoreLevelLabel.setText("water level:    "+numFormat.format(myPotableWaterStore.getWaterLevel())+" L");
		greyWaterStoreLevelLabel.setText("water level:    "+numFormat.format(myGreyWaterStore.getWaterLevel())+" L");
		dirtyWaterStoreLevelLabel.setText("water level:    "+numFormat.format(myDirtyWaterStore.getWaterLevel())+" L");
	}
}
