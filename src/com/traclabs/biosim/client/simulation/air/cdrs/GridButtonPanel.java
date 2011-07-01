package com.traclabs.biosim.client.simulation.air.cdrs;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import com.traclabs.biosim.client.framework.UpdatablePanel;
import com.traclabs.biosim.client.framework.Updateable;

public class GridButtonPanel extends UpdatablePanel {
	private List<Updateable> myUpdateableChildren = new ArrayList<Updateable>();
	
	public GridButtonPanel(){
		setLayout(new FlowLayout());
	}
	
	protected void addButton(GridButtonPanel panel) {
		JButton button = new JButton();
		button.addActionListener(new LssmActionListener(panel));
		button.setText(panel.getName());
		add(button);
		addUpdateable(panel);
	}

	protected void addLabel(StatusLabel label) {
		add(label);
		addUpdateable(label);
	}

	@Override
	public void refresh() {
		super.refresh();
		for (Updateable child : myUpdateableChildren) {
			child.refresh();
		}
	}
	
	protected void addUpdateable(Updateable child){
		myUpdateableChildren.add(child);
	}
}
