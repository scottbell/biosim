package biosim.client.water.gui;

import java.awt.*;
import java.awt.event.*;
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
	private ImageIcon susbsytemIcon;
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
	private WaterRS myWaterRS;
	private PotableWaterStore myPotableWaterStore;
	private GreyWaterStore myGreyWaterStore;
	private DirtyWaterStore myDirtyWaterStore;
	
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
		loadIcons();
		AESButton = new JButton(new AESAction("AES (nominal)", susbsytemIcon));
		AESButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		AESButton.setHorizontalTextPosition(SwingConstants.CENTER);
		BWPButton = new JButton(new BWPAction("BWP (nominal)", susbsytemIcon));
		BWPButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		BWPButton.setHorizontalTextPosition(SwingConstants.CENTER);
		PPSButton = new JButton(new PPSAction("PPS (nominal)", susbsytemIcon));
		PPSButton.setVerticalTextPosition(SwingConstants.BOTTOM);
		PPSButton.setHorizontalTextPosition(SwingConstants.CENTER);
		ROButton = new JButton(new ROAction("RO (nominal)", susbsytemIcon));
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
		setLayout(gridbag);
		
		c.fill = GridBagConstraints.NONE;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.weighty = 1.0;
		c.ipadx = 0;
		c.ipady = 0;
		gridbag.setConstraints(greyWaterButton, c);
		add(greyWaterButton);
		
		c.fill = GridBagConstraints.NONE;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		c.ipadx = 0;
		c.ipady = 0;
		gridbag.setConstraints(dirtyWaterButton, c);
		add(dirtyWaterButton);
		
		c.fill = GridBagConstraints.NONE;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		c.ipadx = 0;
		c.ipady = 0;
		gridbag.setConstraints(tDownPipeLabel1, c);
		add(tDownPipeLabel1);
		
		c.fill = GridBagConstraints.NONE;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		c.ipadx = 0;
		c.ipady = 0;
		gridbag.setConstraints(BWPButton, c);
		add(BWPButton);

		c.fill = GridBagConstraints.NONE;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		c.ipadx = 0;
		c.ipady = 0;
		gridbag.setConstraints(tUpPipeLabel, c);
		add(tUpPipeLabel);
		
		c.fill = GridBagConstraints.NONE;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = 1;
		c.weighty = 1.0;
		c.ipadx = 0;
		c.ipady = 0;
		gridbag.setConstraints(ROButton, c);
		add(ROButton);
		
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.weighty = 1.0;
		c.ipadx = 0;
		c.ipady = 0;
		gridbag.setConstraints(horizontalPipeLabel, c);
		add(horizontalPipeLabel);
		
		c.fill = GridBagConstraints.NONE;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		c.ipadx = 0;
		c.ipady = 0;
		gridbag.setConstraints(AESButton, c);
		add(AESButton);
		
		c.fill = GridBagConstraints.NONE;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		c.ipadx = 0;
		c.ipady = 0;
		gridbag.setConstraints(tDownPipeLabel2, c);
		add(tDownPipeLabel2);
		
		c.fill = GridBagConstraints.NONE;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		c.ipadx = 0;
		c.ipady = 0;
		gridbag.setConstraints(PPSButton, c);
		add(PPSButton);
		
		c.fill = GridBagConstraints.NONE;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		c.ipadx = 0;
		c.ipady = 0;
		gridbag.setConstraints(verticalPipeLabel2, c);
		add(verticalPipeLabel2);
		
		c.fill = GridBagConstraints.NONE;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		c.ipadx = 0;
		c.ipady = 0;
		gridbag.setConstraints(potableWaterButton, c);
		add(potableWaterButton);
		
	}
	
	public void refresh(){
		if ((myWaterRS.getGreyWaterConsumed() > 0) && (myWaterRS.getDirtyWaterConsumed() > 0))
			tDownPipeLabel1.setIcon(tDownWaterFullPipeIcon);
		else if (myWaterRS.getGreyWaterConsumed() > 0)
			tDownPipeLabel1.setIcon(tDownWaterLeftPipeIcon);
		else if (myWaterRS.getDirtyWaterConsumed() > 0)
			tDownPipeLabel1.setIcon(tDownWaterRightPipeIcon);
		
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
			susbsytemIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/water/gui/subsystem.jpg"));
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
		public AESAction(String pName, Icon pIcon){
			super(pName, pIcon);
		}
		public void actionPerformed(ActionEvent ae){
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			myWaterRS.setAESEnabled(!myWaterRS.AESIsEnabled());
			if (myWaterRS.AESIsEnabled())
				AESButton.setText("AES (nominal)");
			else if (!myWaterRS.AESIsEnabled())
				AESButton.setText("AES (off)");
			setCursor(Cursor.getDefaultCursor());
		}
	}
	
	private class BWPAction extends AbstractAction{
		public BWPAction(String pName, Icon pIcon){
			super(pName, pIcon);
		}
		public void actionPerformed(ActionEvent ae){
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			setCursor(Cursor.getDefaultCursor());
		}
	}
	
	private class PPSAction extends AbstractAction{
		public PPSAction(String pName, Icon pIcon){
			super(pName, pIcon);
		}
		public void actionPerformed(ActionEvent ae){
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			setCursor(Cursor.getDefaultCursor());
		}
	}
	
	private class ROAction extends AbstractAction{
		public ROAction(String pName, Icon pIcon){
			super(pName, pIcon);
		}
		public void actionPerformed(ActionEvent ae){
			setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			myWaterRS.setROEnabled(!myWaterRS.ROIsEnabled());
			if (myWaterRS.ROIsEnabled())
				ROButton.setText("RO (nominal)");
			else if (!myWaterRS.ROIsEnabled())
				ROButton.setText("RO (off)");
			setCursor(Cursor.getDefaultCursor());
		}
	}
}
