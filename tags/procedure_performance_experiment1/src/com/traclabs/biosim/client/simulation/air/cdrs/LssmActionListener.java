package com.traclabs.biosim.client.simulation.air.cdrs;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class LssmActionListener implements ActionListener {

	private GridButtonPanel myPanel;
	private static JFrame lastFrame = new JFrame();
	
	public LssmActionListener(GridButtonPanel panel) {
		this.myPanel = panel;
	}

	public void actionPerformed(ActionEvent e) {
		JFrame frame = new JFrame(myPanel.getName());
		frame.setLayout(new BorderLayout());
		frame.add(myPanel, BorderLayout.CENTER);
		Point panelLocation = lastFrame.getLocation();
		frame.setLocation(panelLocation.x + 50, panelLocation.y + 50);
		frame.pack();
		frame.setLocationByPlatform(false);
		frame.setVisible(true);
		ImageIcon airIcon = new ImageIcon(LssmViewer.class
				.getClassLoader().getResource("com/traclabs/biosim/client/air/air.png"));
		frame.setIconImage(airIcon.getImage());
		lastFrame = frame;
	}

}
