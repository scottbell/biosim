package biosim.client.gui;

import biosim.client.control.*;
import javax.swing.*;

public class SimPanel extends JPanel;
{
	private BioSimulator myBiosim;

	public SimPanel(){
		myBiosim = new BioSimulator();
		runSimulation();
	}

	private void buildGUI(){
	}

	public static void main(String args[])
	{
		SimPanel newPanel = new SimPanel();
		JFrame theFrame = new JFrame("Advanced Life Support System");
		theFrame.getContentPane().add(newPanel);
	}
}

