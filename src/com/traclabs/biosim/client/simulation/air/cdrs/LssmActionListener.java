package com.traclabs.biosim.client.simulation.air.cdrs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class LssmActionListener implements ActionListener {
	private GridButtonPanel myPanel;
	
	public LssmActionListener(GridButtonPanel panel) {
		this.myPanel = panel;
	}

	public void actionPerformed(ActionEvent e) {
		JFrame frame = new JFrame(myPanel.getName());
		frame.setLayout(new BorderLayout());
		frame.add(myPanel, BorderLayout.CENTER);
		frame.setSize(myPanel.getPreferredSize());
		frame.setVisible(true);
	}

}
