package biosim.client.control;

import biosim.client.control.gui.*;
import javax.swing.*;

public class TestDriver
{
	public static void main(String args[])
	{
		BioSimulator biosim = new BioSimulator();
		biosim.spawnSimulation();
		SimPanel newPanel = new SimPanel();
		JFrame theFrame = new JFrame("Advanced Life Support System");
		theFrame.getContentPane().add(newPanel);
		theFrame.setVisible(true);
	}
}

