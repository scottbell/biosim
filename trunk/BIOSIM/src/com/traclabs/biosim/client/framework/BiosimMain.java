package biosim.client.control;

import biosim.client.control.gui.*;
import javax.swing.*;

public class TestDriver
{
	public static void main(String args[]) throws java.lang.InterruptedException
	{
		BioSimulator biosim = new BioSimulator();
		biosim.spawnSimulation();
		SimPanel newPanel = new SimPanel();
		Thread theCurrentThread = Thread.currentThread();
		JFrame theFrame = new JFrame("Advanced Life Support System");
		theCurrentThread.sleep(5000);
		biosim.pauseSimulation();
		theCurrentThread.sleep(5000);
		biosim.resumeSimulation();
		theCurrentThread.sleep(5000);
		biosim.endSimulation();
		theFrame.getContentPane().add(newPanel);
		theFrame.setVisible(true);
	}
}

