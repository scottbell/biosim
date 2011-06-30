package com.traclabs.biosim.client.simulation.air.cdrs;

import javax.swing.JButton;

import com.traclabs.biosim.client.framework.UpdatablePanel;

public class GridButtonPanel extends UpdatablePanel {
	protected void addButton(GridButtonPanel panel) {
		JButton button = new JButton();
		button.addActionListener(new LssmActionListener(panel));
		button.setText(panel.getName());
		add(button);
	}
}
