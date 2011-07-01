package com.traclabs.biosim.client.simulation.air.cdrs;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;

import com.traclabs.biosim.client.framework.UpdatablePanel;

public class GridButtonPanel extends UpdatablePanel {
	public GridButtonPanel(){
		setLayout(new FlowLayout());
	}
	
	protected void addButton(GridButtonPanel panel) {
		JButton button = new JButton();
		button.addActionListener(new LssmActionListener(panel));
		button.setText(panel.getName());
		add(button);
	}

	protected void addLabel(JLabel label) {
		add(label);
	}
}
