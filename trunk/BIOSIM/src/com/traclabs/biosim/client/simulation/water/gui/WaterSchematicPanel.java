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
	private ImageIcon susbsytemIcon;
	private JButton AESButton;
	private JButton BWPButton;
	private JButton PPSButton;
	private JButton ROButton;
	private JLabel horizontalPipeLabel1;
	private JLabel horizontalPipeLabel2;
	private JLabel horizontalPipeLabel3;
	private JLabel potableWaterLabel;
	private JLabel greyWaterLabel;
	private JLabel dirtyWaterLabel;
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
		horizontalPipeLabel1 = new JLabel(horizontalPipeIcon);
		horizontalPipeLabel2 = new JLabel(horizontalPipeIcon);
		horizontalPipeLabel3 = new JLabel(horizontalPipeIcon);
		potableWaterLabel = new JLabel("Potable Water Store", potableWaterStoreIcon, SwingConstants.CENTER);
		potableWaterLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
		potableWaterLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		dirtyWaterLabel = new JLabel("Dirty Water Store", dirtyWaterStoreIcon, SwingConstants.CENTER);
		dirtyWaterLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
		dirtyWaterLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		greyWaterLabel = new JLabel("Grey Water Store", greyWaterStoreIcon, SwingConstants.CENTER);
		greyWaterLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
		greyWaterLabel.setHorizontalTextPosition(SwingConstants.CENTER);
		Color tempColor = this.getBackground();
		System.out.println("red: "+tempColor.getRed());
		System.out.println("green: "+tempColor.getGreen());
		System.out.println("blue: "+tempColor.getBlue());
		
		//add components
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(gridbag);
		c.fill = GridBagConstraints.NONE;
		c.gridheight = 1;
		c.weightx = 0.1;
		c.gridwidth = 1;
		c.weighty = 0.1;
		c.ipadx = 0;
		c.ipady = 0;
		gridbag.setConstraints(BWPButton, c);
		add(BWPButton);

		c.fill = GridBagConstraints.NONE;
		c.gridheight = 1;
		c.weightx = 0.1;
		c.gridwidth = 1;
		c.weighty = 0.1;
		c.ipadx = 0;
		c.ipady = 0;
		gridbag.setConstraints(horizontalPipeLabel1, c);
		add(horizontalPipeLabel1);

		c.fill = GridBagConstraints.NONE;
		c.gridheight = 1;
		c.weightx = 0.1;
		c.gridwidth = 1;
		c.weighty = 0.1;
		c.ipadx = 0;
		c.ipady = 0;
		gridbag.setConstraints(ROButton, c);
		add(ROButton);
		
		c.fill = GridBagConstraints.NONE;
		c.gridheight = 1;
		c.weightx = 0.1;
		c.gridwidth = 1;
		c.weighty = 0.1;
		c.ipadx = 0;
		c.ipady = 0;
		gridbag.setConstraints(horizontalPipeLabel2, c);
		add(horizontalPipeLabel2);
		
		c.fill = GridBagConstraints.NONE;
		c.gridheight = 1;
		c.weightx = 0.1;
		c.gridwidth = 1;
		c.weighty = 0.1;
		c.ipadx = 0;
		c.ipady = 0;
		gridbag.setConstraints(AESButton, c);
		add(AESButton);
		
		c.fill = GridBagConstraints.NONE;
		c.gridheight = 1;
		c.weightx = 0.1;
		c.gridwidth = 1;
		c.weighty = 0.1;
		c.ipadx = 0;
		c.ipady = 0;
		gridbag.setConstraints(horizontalPipeLabel3, c);
		add(horizontalPipeLabel3);
		
		c.fill = GridBagConstraints.NONE;
		c.gridheight = 1;
		c.weightx = 0.1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 0.1;
		c.ipadx = 0;
		c.ipady = 0;
		gridbag.setConstraints(PPSButton, c);
		add(PPSButton);
		
		c.fill = GridBagConstraints.NONE;
		c.gridheight = 1;
		c.weightx = 0.1;
		c.gridwidth = 1;
		c.weighty = 0.1;
		c.ipadx = 0;
		c.ipady = 0;
		gridbag.setConstraints(dirtyWaterLabel, c);
		add(dirtyWaterLabel);
		
		c.fill = GridBagConstraints.NONE;
		c.gridheight = 1;
		c.weightx = 0.1;
		c.gridwidth = 1;
		c.weighty = 0.1;
		c.ipadx = 0;
		c.ipady = 0;
		gridbag.setConstraints(greyWaterLabel, c);
		add(greyWaterLabel);
		
		c.fill = GridBagConstraints.NONE;
		c.gridheight = 1;
		c.weightx = 0.1;
		c.gridwidth = 1;
		c.weighty = 0.1;
		c.ipadx = 0;
		c.ipady = 0;
		gridbag.setConstraints(potableWaterLabel, c);
		add(potableWaterLabel);
	}
	
	public void refresh(){
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
			susbsytemIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/water/gui/subsystem.jpg"));
		}
		catch (Exception e){
			System.out.println("Couldn't find icon, skipping icon loading");
			e.printStackTrace();
			potableWaterStoreIcon = new ImageIcon();
			dirtyWaterStoreIcon = new ImageIcon();
			greyWaterStoreIcon = new ImageIcon();
			horizontalPipeIcon = new ImageIcon();
			susbsytemIcon = new ImageIcon();
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
