package com.traclabs.biosim.client.simulation.framework;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.traclabs.biosim.client.framework.UpdatablePanel;

/**
 * This is the JPanel that displays information about the a bio module
 * 
 * @author Scott Bell
 */

public abstract class SimTabbedPanel extends UpdatablePanel {
	protected JTabbedPane myTabbedPane;

	protected UpdatablePanel myTextPanel;

	protected UpdatablePanel myChartPanel;

	protected UpdatablePanel mySchematicPanel;

	private int oldSelectedIndex = -1;

	private final static int ADDED_TAB_HEIGHT = 20;

	private final static int ADDED_TAB_WIDTH = 10;

	private final static int MIN_TAB_HEIGHT = 40;

	private final static int MIN_TAB_WIDTH = 200;

	/**
	 * Creates and registers this panel.
	 */
	public SimTabbedPanel() {
		createPanels();
		buildGui();
	}

	protected SimDesktopFrame getSimFrame() {
		Container theContainer = this.getParent();
		while (theContainer != null) {
			if (theContainer instanceof SimDesktopFrame)
				return ((SimDesktopFrame) (theContainer));
			theContainer = theContainer.getParent();
		}
		return null;
	}

	protected abstract void createPanels();

	/**
	 * Contructs GUI components, adds them to the panel.
	 */
	private void buildGui() {
		setLayout(new BorderLayout());
		myTabbedPane = new JTabbedPane();
		myTabbedPane.addTab("Text", myTextPanel);
		myTabbedPane.addTab("Chart", myChartPanel);
		myTabbedPane.addTab("Schematic", mySchematicPanel);
		add(myTabbedPane, BorderLayout.CENTER);
		myTabbedPane.addChangeListener(new TabChangeListener());
	}

	public void visibilityChange(boolean nowVisible) {
		if (nowVisible)
			alterVisibility();
		else
			myTextPanel.visibilityChange(false);
		myChartPanel.visibilityChange(false);
		mySchematicPanel.visibilityChange(false);
	}

	public void refresh() {
	}

	public Dimension getPreferredSize() {
		Dimension theDimension = myTabbedPane.getSelectedComponent()
				.getPreferredSize();
		double width = theDimension.getWidth();
		double height = theDimension.getHeight();
		if (width < MIN_TAB_WIDTH)
			width = MIN_TAB_WIDTH;
		else
			width += ADDED_TAB_WIDTH;
		if (height < MIN_TAB_HEIGHT)
			height = MIN_TAB_HEIGHT;
		else
			height += ADDED_TAB_HEIGHT;
		theDimension.setSize(width, height);
		return theDimension;
	}

	protected void alterVisibility() {
		// Notify panel that we lost focus
		if (oldSelectedIndex == 0) {
			myTextPanel.visibilityChange(false);
		} else if (oldSelectedIndex == 1) {
			myChartPanel.visibilityChange(false);
		} else if (oldSelectedIndex == 2) {
			mySchematicPanel.visibilityChange(false);
		}
		// Notify panel that we gained focus
		if (myTabbedPane.getSelectedIndex() == 0) {
			myTextPanel.visibilityChange(true);
		} else if (myTabbedPane.getSelectedIndex() == 1) {
			myChartPanel.visibilityChange(true);
		} else if (myTabbedPane.getSelectedIndex() == 2) {
			mySchematicPanel.visibilityChange(true);
		}
		oldSelectedIndex = myTabbedPane.getSelectedIndex();
		setSize(getPreferredSize());
		getSimFrame().pack();
	}

	private class TabChangeListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			alterVisibility();
		}
	}
}