package biosim.client.water.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import biosim.idl.water.*;
import biosim.client.framework.gui.*;

/**
 * This is the JPanel that displays a schematic
 *
 * @author    Scott Bell
 */
public class WaterSchematicPanel extends BioTabPanel
{
	private ImageIcon problemIcon;
	private ImageIcon offIcon;
	private ImageIcon okIcon;
	
	/**
	 * Default constructor.
	 */
	public WaterSchematicPanel() {
		buildGui();
	}
	
	
	private void buildGui(){
		loadIcons();
	}
	
	/**
	* Attempts to load the icons from the resource directory.
	*/
	private void loadIcons(){
		try{
			problemIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/water/gui/problemIcon.jpg"));
			offIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/water/gui/offIcon.jpg"));
			okIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/water/gui/okIcon.jpg"));
		}
		catch (Exception e){
			System.out.println("Couldn't find icon ("+e+"), skipping");
			e.printStackTrace();
			problemIcon = new ImageIcon();
			offIcon = new ImageIcon();
			okIcon = new ImageIcon();
		}
	}
}
