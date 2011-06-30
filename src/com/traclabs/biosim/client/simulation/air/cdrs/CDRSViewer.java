package com.traclabs.biosim.client.simulation.air.cdrs;

import javax.swing.ImageIcon;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;

import com.traclabs.biosim.client.framework.BioFrame;
import com.traclabs.biosim.client.simulation.framework.SimulationPanel;
import com.traclabs.biosim.client.util.BioHolder;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.util.OrbUtils;

public class CDRSViewer extends SimulationPanel {
	private Logger myLogger;

	private BioHolder myBioHolder;

	public CDRSViewer() {
		myLogger = Logger.getLogger(this.getClass());
		myBioHolder = BioHolderInitializer.getBioHolder();
	}

	@Override
	protected synchronized void refresh() {
		// TODO Auto-generated method stub
	}

	public static void main(String[] strings) {
		OrbUtils.initializeLog();
		CDRSViewer newViewer = new CDRSViewer();
		BioFrame myFrame = new BioFrame("CDRS Viewer", false);
		myFrame.getContentPane().add(newViewer);
		myFrame.pack();
		myFrame.setSize(800, 600);
		myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		ImageIcon airIcon = new ImageIcon(CDRSViewer.class
				.getClassLoader().getResource("com/traclabs/biosim/client/air/air.png"));
		myFrame.setIconImage(airIcon.getImage());
		myFrame.setVisible(true);
	}

	@Override
	protected void reset() {
		// TODO Auto-generated method stub
	}

}
