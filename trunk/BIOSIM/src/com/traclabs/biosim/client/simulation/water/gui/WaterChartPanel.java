package biosim.client.water.gui;

import java.awt.*;
import javax.swing.*;
import biosim.client.framework.*;

/**
 * This is the JPanel that displays a chart about the Water
 *
 * @author    Scott Bell
 */
public class WaterChartPanel extends JPanel {
	//Used for registereing this panel (for knowing when a tick occurs)
	private BioSimulator myBioSimulator;

	/**
	 * Default constructor.
	 */
	public WaterChartPanel(BioSimulator pBioSimulator) {
		myBioSimulator = pBioSimulator;

		

	}

}
