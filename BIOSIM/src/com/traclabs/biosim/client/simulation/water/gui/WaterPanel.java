package biosim.client.water.gui;

import biosim.client.framework.*;
import javax.swing.*;
import java.awt.*;
/** 
 * This is the JPanel that displays information about the Water
 *
 * @author    Scott Bell
 */

public class WaterPanel extends JPanel
{
	private JTabbedPane myTabbedPane;
	private JPanel myWaterTextPanel;
	private JPanel myWaterChartPanel;
	private BioSimulator myBioSimulator;
	
	/**
	* Creates and registers this panel.
	* @param pBioSimulator	The Biosimulator this Panel will register itself with.
	*/
	public WaterPanel(BioSimulator pBioSimulator){
		myBioSimulator = pBioSimulator;
		buildGui();
	}
	
	/**
	* Contructs GUI components, adds them to the panel.
	*/
	private void buildGui(){
		setLayout(new BorderLayout());
		myTabbedPane = new JTabbedPane();
		myWaterTextPanel = new WaterTextPanel(myBioSimulator);
		myWaterChartPanel = new WaterChartPanel(myBioSimulator);
		myTabbedPane.addTab("Text", myWaterTextPanel);
		add(myTabbedPane, BorderLayout.CENTER);
	}
}
