package biosim.client.water.gui;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import javax.swing.*;
import biosim.idl.water.*;
import biosim.client.framework.gui.*;
import biosim.client.framework.*;

/**
 * This is the JPanel that displays a schematic
 *
 * @author    Scott Bell
 */
public class WaterSchematicPanel extends TimedPanel
{
	private ImageIcon potableWaterStoreIcon;
	private ImageIcon dirtyWaterStoreIcon;
	private ImageIcon greyWaterStoreIcon;
	private ImageIcon horizontalPipeIcon;
	private ImageIcon horizontalWaterPipeIcon;
	private ImageIcon susbsytemDisabledIcon;
	private ImageIcon susbsytemOkIcon;
	private ImageIcon susbsytemProblemIcon;
	private ImageIcon verticalPipeIcon;
	private ImageIcon verticalWaterPipeIcon;
	private ImageIcon tDownPipeIcon;
	private ImageIcon tDownWaterRightPipeIcon;
	private ImageIcon tDownWaterLeftPipeIcon;
	private ImageIcon tDownWaterFullPipeIcon;
	private ImageIcon tUpPipeIcon;
	private ImageIcon tUpWaterRightPipeIcon;
	private ImageIcon tUpWaterLeftPipeIcon;
	private ImageIcon tUpWaterFullPipeIcon;
	private JButton AESButton;
	private JButton BWPButton;
	private JButton PPSButton;
	private JButton ROButton;
	private JButton potableWaterButton;
	private JButton greyWaterButton;
	private JButton dirtyWaterButton;
	private JLabel horizontalPipeLabel;
	private JLabel verticalPipeLabel1;
	private JLabel verticalPipeLabel2;
	private JLabel tDownPipeLabel1;
	private JLabel tDownPipeLabel2;
	private JLabel tUpPipeLabel;
	private JPanel mainPanel;
	private JScrollPane mainScrollPane;
	private WaterRS myWaterRS;
	private PotableWaterStore myPotableWaterStore;
	private GreyWaterStore myGreyWaterStore;
	private DirtyWaterStore myDirtyWaterStore;
	//For formatting floats
	private DecimalFormat numFormat;

	/**
	 * Default constructor.
	 */
	public WaterSchematicPanel() {
		myWaterRS = (WaterRS)(BioHolder.getBioModule(BioHolder.waterRSName));
		myPotableWaterStore = (PotableWaterStore)(BioHolder.getBioModule(BioHolder.potableWaterStoreName));
		myDirtyWaterStore = (DirtyWaterStore)(BioHolder.getBioModule(BioHolder.dirtyWaterStoreName));
		myGreyWaterStore = (GreyWaterStore)(BioHolder.getBioModule(BioHolder.greyWaterStoreName));
		buildGui();
	}


	private void buildGui(){
		numFormat = new DecimalFormat("#,##0.00;(#)");
		loadIcons();
		mainPanel = new JPanel();
		AESButton = new JButton(new AESAction("AES (nominal)"));
		AESButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		AESButton.setHorizontalTextPosition(SwingConstants.CENTER);
		BWPButton = new JButton(new BWPAction("BWP (nominal)"));
		BWPButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		BWPButton.setHorizontalTextPosition(SwingConstants.CENTER);
		PPSButton = new JButton(new PPSAction("PPS (nominal)"));
		PPSButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		PPSButton.setHorizontalTextPosition(SwingConstants.CENTER);
		ROButton = new JButton(new ROAction("RO (nominal)"));
		ROButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		ROButton.setHorizontalTextPosition(SwingConstants.CENTER);
		tDownPipeLabel1 = new JLabel(tDownPipeIcon);
		tDownPipeLabel2 = new JLabel(tDownPipeIcon);
		tUpPipeLabel = new JLabel(tUpPipeIcon);
		horizontalPipeLabel = new JLabel(horizontalPipeIcon);
		verticalPipeLabel1 = new JLabel(verticalPipeIcon);
		verticalPipeLabel2 = new JLabel(verticalPipeIcon);
		potableWaterButton = new JButton("Potable Water", potableWaterStoreIcon);
		potableWaterButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		potableWaterButton.setHorizontalTextPosition(SwingConstants.CENTER);
		dirtyWaterButton = new JButton("Dirty Water", dirtyWaterStoreIcon);
		dirtyWaterButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		dirtyWaterButton.setHorizontalTextPosition(SwingConstants.CENTER);
		greyWaterButton = new JButton("Grey Water", greyWaterStoreIcon);
		greyWaterButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		greyWaterButton.setHorizontalTextPosition(SwingConstants.CENTER);

		//add components
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		mainPanel.setLayout(gridbag);

		c.fill = GridBagConstraints.NONE;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.weighty = 1.0;
		c.ipadx = 0;
		c.ipady = 0;
		gridbag.setConstraints(greyWaterButton, c);
		mainPanel.add(greyWaterButton);

		c.fill = GridBagConstraints.NONE;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		c.ipadx = 0;
		c.ipady = 0;
		gridbag.setConstraints(dirtyWaterButton, c);
		mainPanel.add(dirtyWaterButton);

		c.fill = GridBagConstraints.NONE;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		c.ipadx = 0;
		c.ipady = 0;
		gridbag.setConstraints(tDownPipeLabel1, c);
		mainPanel.add(tDownPipeLabel1);

		c.fill = GridBagConstraints.NONE;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		c.ipadx = 0;
		c.ipady = 0;
		gridbag.setConstraints(BWPButton, c);
		mainPanel.add(BWPButton);

		c.fill = GridBagConstraints.NONE;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		c.ipadx = 0;
		c.ipady = 0;
		gridbag.setConstraints(tUpPipeLabel, c);
		mainPanel.add(tUpPipeLabel);

		c.fill = GridBagConstraints.NONE;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = 1;
		c.weighty = 1.0;
		c.ipadx = 0;
		c.ipady = 0;
		gridbag.setConstraints(ROButton, c);
		mainPanel.add(ROButton);

		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.weighty = 1.0;
		c.ipadx = 0;
		c.ipady = 0;
		gridbag.setConstraints(horizontalPipeLabel, c);
		mainPanel.add(horizontalPipeLabel);

		c.fill = GridBagConstraints.NONE;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		c.ipadx = 0;
		c.ipady = 0;
		gridbag.setConstraints(AESButton, c);
		mainPanel.add(AESButton);

		c.fill = GridBagConstraints.NONE;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		c.ipadx = 0;
		c.ipady = 0;
		gridbag.setConstraints(tDownPipeLabel2, c);
		mainPanel.add(tDownPipeLabel2);

		c.fill = GridBagConstraints.NONE;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		c.ipadx = 0;
		c.ipady = 0;
		gridbag.setConstraints(PPSButton, c);
		mainPanel.add(PPSButton);

		c.fill = GridBagConstraints.NONE;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		c.ipadx = 0;
		c.ipady = 0;
		gridbag.setConstraints(verticalPipeLabel2, c);
		mainPanel.add(verticalPipeLabel2);

		c.fill = GridBagConstraints.NONE;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		c.ipadx = 0;
		c.ipady = 0;
		gridbag.setConstraints(potableWaterButton, c);
		mainPanel.add(potableWaterButton);
		//mainScrollPane = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		refresh();
		add(mainPanel);
	}

	public void refresh(){
		potableWaterButton.setToolTipText(numFormat.format(myPotableWaterStore.getLevel())+" L");
		dirtyWaterButton.setToolTipText(numFormat.format(myDirtyWaterStore.getLevel())+" L");
		greyWaterButton.setToolTipText(numFormat.format(myGreyWaterStore.getLevel())+" L");
		
		tDownPipeLabel1.setToolTipText(numFormat.format(myWaterRS.getGreyWaterConsumed() + myWaterRS.getDirtyWaterConsumed())+" L");
		if ((myWaterRS.getGreyWaterConsumed() > 0) && (myWaterRS.getDirtyWaterConsumed() > 0))
			tDownPipeLabel1.setIcon(tDownWaterFullPipeIcon);
		else if (myWaterRS.getGreyWaterConsumed() > 0)
			tDownPipeLabel1.setIcon(tDownWaterLeftPipeIcon);
		else if (myWaterRS.getDirtyWaterConsumed() > 0)
			tDownPipeLabel1.setIcon(tDownWaterRightPipeIcon);
		else{
			tDownPipeLabel1.setIcon(tDownPipeIcon);
		}
		
		tUpPipeLabel.setToolTipText(numFormat.format(myWaterRS.getBWPtoROWater() + myWaterRS.getBWPtoAESWater())+" L");
		if ((myWaterRS.getBWPtoROWater() > 0) && (myWaterRS.getBWPtoAESWater() > 0))
			tUpPipeLabel.setIcon(tUpWaterFullPipeIcon);
		else if (myWaterRS.getBWPtoROWater() > 0)
			tUpPipeLabel.setIcon(tUpWaterLeftPipeIcon);
		else if (myWaterRS.getBWPtoAESWater() > 0)
			tUpPipeLabel.setIcon(tUpWaterRightPipeIcon);
		else{
			tUpPipeLabel.setIcon(tUpPipeIcon);
		}
		
		horizontalPipeLabel.setToolTipText(numFormat.format(myWaterRS.getROtoAESWater())+" L");
		if (myWaterRS.getROtoAESWater() > 0)
			horizontalPipeLabel.setIcon(horizontalWaterPipeIcon);
		else
			horizontalPipeLabel.setIcon(horizontalPipeIcon);
		
		tDownPipeLabel2.setToolTipText(numFormat.format(myWaterRS.getROtoPPSWater() + myWaterRS.getAEStoPPSWater())+" L");
		if ((myWaterRS.getROtoPPSWater() > 0) && (myWaterRS.getAEStoPPSWater() > 0))
			tDownPipeLabel2.setIcon(tDownWaterFullPipeIcon);
		else if (myWaterRS.getROtoPPSWater() > 0)
			tDownPipeLabel2.setIcon(tDownWaterLeftPipeIcon);
		else if (myWaterRS.getAEStoPPSWater() > 0)
			tDownPipeLabel2.setIcon(tDownWaterRightPipeIcon);
		else{
			tDownPipeLabel2.setIcon(tDownPipeIcon);
		}
		
		verticalPipeLabel2.setToolTipText(numFormat.format(myWaterRS.getPotableWaterProduced())+" L");
		if (myWaterRS.getPotableWaterProduced() > 0)
			verticalPipeLabel2.setIcon(verticalWaterPipeIcon);
		else
			verticalPipeLabel2.setIcon(verticalPipeIcon);

		if (!myWaterRS.AESIsEnabled()){
			AESButton.setText("AES");
			AESButton.setIcon(susbsytemDisabledIcon);
			AESButton.setToolTipText("Disabled");
		}
		else if (!myWaterRS.AESHasPower()){
			AESButton.setText("AES");
			AESButton.setIcon(susbsytemProblemIcon);
			AESButton.setToolTipText("Needs Power");
		}
		else if (!myWaterRS.AESHasWater()){
			AESButton.setText("AES");
			AESButton.setIcon(susbsytemProblemIcon);
			AESButton.setToolTipText("Needs Water");
		}
		else if (myWaterRS.AESIsEnabled()){
			AESButton.setText("AES");
			AESButton.setIcon(susbsytemOkIcon);
			AESButton.setToolTipText("Nominal");
		}

		if (!myWaterRS.ROIsEnabled()){
			ROButton.setText("RO");
			ROButton.setIcon(susbsytemDisabledIcon);
			ROButton.setToolTipText("Disabled");
		}
		else if (!myWaterRS.ROHasPower()){
			ROButton.setText("RO");
			ROButton.setIcon(susbsytemProblemIcon);
			ROButton.setToolTipText("Needs Power");
		}
		else if (!myWaterRS.ROHasWater()){
			ROButton.setText("RO");
			ROButton.setIcon(susbsytemProblemIcon);
			ROButton.setToolTipText("Needs Water");
		}
		else if (myWaterRS.ROIsEnabled()){
			ROButton.setText("RO");
			ROButton.setIcon(susbsytemOkIcon);
			ROButton.setToolTipText("Nominal");
		}
		
		if (!myWaterRS.BWPHasPower()){
			BWPButton.setText("BWP");
			BWPButton.setIcon(susbsytemProblemIcon);
			BWPButton.setToolTipText("Needs Power");
		}
		else if (!myWaterRS.BWPHasWater()){
			BWPButton.setText("BWP");
			BWPButton.setIcon(susbsytemProblemIcon);
			BWPButton.setToolTipText("Needs Water");
		}
		else{
			BWPButton.setText("BWP");
			BWPButton.setIcon(susbsytemOkIcon);
			BWPButton.setToolTipText("Nominal");
		}
		
		if (!myWaterRS.PPSHasPower()){
			PPSButton.setText("PPS");
			PPSButton.setIcon(susbsytemProblemIcon);
			PPSButton.setToolTipText("Needs Power");
		}
		else if (!myWaterRS.PPSHasWater()){
			PPSButton.setText("PPS");
			PPSButton.setIcon(susbsytemProblemIcon);
			PPSButton.setToolTipText("Needs Water");
		}
		else{
			PPSButton.setText("PPS");
			PPSButton.setIcon(susbsytemOkIcon);
			PPSButton.setToolTipText("Nominal");
		}
	}

	/**
	* Attempts to load the icons from the resource directory.
	*/
	private void loadIcons(){
		try{
			potableWaterStoreIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/water/gui/potable-watertank.jpg"));
			dirtyWaterStoreIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/water/gui/dirty-watertank.jpg"));
			greyWaterStoreIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/water/gui/grey-watertank.jpg"));
			horizontalPipeIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/water/gui/horizontal-pipe.jpg"));
			horizontalWaterPipeIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/water/gui/horizontalWater-pipe.jpg"));
			susbsytemDisabledIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/water/gui/subsystem-disabled.jpg"));
			susbsytemOkIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/water/gui/subsystem-ok.jpg"));
			susbsytemProblemIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/water/gui/subsystem-problem.jpg"));
			verticalPipeIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/water/gui/vertical-pipe.jpg"));
			verticalWaterPipeIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/water/gui/verticalWater-pipe.jpg"));
			tUpPipeIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/water/gui/tUp-pipe.jpg"));
			tUpWaterRightPipeIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/water/gui/tUpWaterRight-pipe.jpg"));
			tUpWaterLeftPipeIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/water/gui/tUpWaterLeft-pipe.jpg"));
			tUpWaterFullPipeIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/water/gui/tUpWaterFull-pipe.jpg"));
			tDownPipeIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/water/gui/tDown-pipe.jpg"));
			tDownWaterRightPipeIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/water/gui/tDownWaterRight-pipe.jpg"));
			tDownWaterLeftPipeIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/water/gui/tDownWaterLeft-pipe.jpg"));
			tDownWaterFullPipeIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/water/gui/tDownWaterFull-pipe.jpg"));
		}
		catch (Exception e){
			System.out.println("Couldn't find icon, skipping icon loading");
			e.printStackTrace();
		}
	}

	private class PotableWaterAction extends AbstractAction{
		public PotableWaterAction(String pName, Icon pIcon){
			super(pName, pIcon);
		}
		public void actionPerformed(ActionEvent ae){
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			setCursor(Cursor.getDefaultCursor());
		}
	}

	private class GreyWaterAction extends AbstractAction{
		public GreyWaterAction(String pName, Icon pIcon){
			super(pName, pIcon);
		}
		public void actionPerformed(ActionEvent ae){
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			setCursor(Cursor.getDefaultCursor());
		}
	}

	private class DirtyWaterAction extends AbstractAction{
		public DirtyWaterAction(String pName, Icon pIcon){
			super(pName, pIcon);
		}
		public void actionPerformed(ActionEvent ae){
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			setCursor(Cursor.getDefaultCursor());
		}
	}

	private class AESAction extends AbstractAction{
		public AESAction(String pName){
			super(pName);
		}
		public void actionPerformed(ActionEvent ae){
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			myWaterRS.setAESEnabled(!myWaterRS.AESIsEnabled());
			setCursor(Cursor.getDefaultCursor());
		}
	}

	private class BWPAction extends AbstractAction{
		public BWPAction(String pName){
			super(pName);
		}
		public void actionPerformed(ActionEvent ae){
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			setCursor(Cursor.getDefaultCursor());
		}
	}

	private class PPSAction extends AbstractAction{
		public PPSAction(String pName){
			super(pName);
		}
		public void actionPerformed(ActionEvent ae){
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			setCursor(Cursor.getDefaultCursor());
		}
	}

	private class ROAction extends AbstractAction{
		public ROAction(String pName){
			super(pName);
		}
		public void actionPerformed(ActionEvent ae){
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			myWaterRS.setROEnabled(!myWaterRS.ROIsEnabled());
			setCursor(Cursor.getDefaultCursor());
		}
	}
}
