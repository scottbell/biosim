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
	private ImageIcon problemIcon;
	private ImageIcon offIcon;
	private ImageIcon okIcon;
	private JButton potableWaterButton;
	private JButton greyWaterButton;
	private JButton dirtyWaterButton;
	private JButton AESButton;
	private JButton BWPButton;
	private JButton PPSButton;
	private JButton ROButton;
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
		potableWaterButton = new JButton(new PotableWaterAction("Potable Water Store", okIcon));
		greyWaterButton = new JButton(new GreyWaterAction("Grey Water Store", okIcon));
		dirtyWaterButton = new JButton(new DirtyWaterAction("Dirty Water Store", okIcon));
		AESButton = new JButton(new AESAction("AES Subsystem", okIcon));
		BWPButton = new JButton(new BWPAction("BWP Subsystem", okIcon));
		PPSButton = new JButton(new PPSAction("PPS Subsystem", okIcon));
		ROButton = new JButton(new ROAction("RO Subsystem", okIcon));
		setLayout(new GridLayout(7,1));
		add(potableWaterButton);
		add(greyWaterButton);
		add(dirtyWaterButton);
		add(AESButton);
		add(BWPButton);
		add(PPSButton);
		add(ROButton);
	}
	
	public void refresh(){
	}
	
	/**
	* Attempts to load the icons from the resource directory.
	*/
	private void loadIcons(){
		try{
			problemIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/water/gui/problemIcon.gif"));
			offIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/water/gui/offIcon.gif"));
			okIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/water/gui/okIcon.gif"));
		}
		catch (Exception e){
			System.out.println("Couldn't find icon ("+e+"), skipping");
			problemIcon = new ImageIcon();
			offIcon = new ImageIcon();
			okIcon = new ImageIcon();
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
				AESButton.setIcon(okIcon);
			else if (!myWaterRS.AESIsEnabled())
				AESButton.setIcon(offIcon);
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
				ROButton.setIcon(okIcon);
			else if (!myWaterRS.ROIsEnabled())
				ROButton.setIcon(offIcon);
			setCursor(Cursor.getDefaultCursor());
		}
	}
}
