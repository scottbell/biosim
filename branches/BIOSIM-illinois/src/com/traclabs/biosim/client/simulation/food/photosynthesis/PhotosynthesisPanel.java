package com.traclabs.biosim.client.simulation.food.photosynthesis;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.traclabs.biosim.client.framework.BioFrame;
import com.traclabs.biosim.client.simulation.framework.SimulationPanel;
import com.traclabs.biosim.server.simulation.food.photosynthesis.Chloroplast;
import com.traclabs.biosim.util.OrbUtils;

public class PhotosynthesisPanel extends SimulationPanel {
	private Chloroplast myChloroplast = new Chloroplast();

	private StromaPanel myStromaPanel;

	private LumenPanel myLumenPanel;

	private EnergyLevelPanel myActivityPanel;

	private JPanel myGraphPanel;

	public PhotosynthesisPanel() {
		buildGUI();
	}

	private void buildGUI() {
		myStromaPanel = new StromaPanel(myChloroplast.getStroma());
		myStromaPanel.setBorder(BorderFactory.createTitledBorder("Stroma"));
		myLumenPanel = new LumenPanel(myChloroplast.getThylakoid().getLumen());
		myLumenPanel.setBorder(BorderFactory.createTitledBorder("Lumen"));
		myActivityPanel = new EnergyLevelPanel(myChloroplast.getThylakoid()
				.getMembrane());
		myActivityPanel.setBorder(BorderFactory.createTitledBorder("Activity"));

		myGraphPanel = new JPanel();
		myGraphPanel.setLayout(new GridLayout(1, 3));
		myGraphPanel.add(myStromaPanel);
		myGraphPanel.add(myLumenPanel);
		myGraphPanel.add(myActivityPanel);
		add(myGraphPanel, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		OrbUtils.initializeLog();
		BioFrame myFrame = new BioFrame("Photosynthesis Model", false);
		myFrame.getContentPane().add(new PhotosynthesisPanel());
		myFrame.pack();
		myFrame.setSize(800, 600);
		ImageIcon biosimIcon = new ImageIcon(PhotosynthesisPanel.class
				.getClassLoader().getResource(
						"com/traclabs/biosim/client/food/food.png"));
		myFrame.setIconImage(biosimIcon.getImage());
		myFrame.setVisible(true);
	}

	protected void refresh() {
		myChloroplast.tick();
		myStromaPanel.refresh();
		myLumenPanel.refresh();
		myActivityPanel.refresh();
	}

	protected void reset() {
		myChloroplast.reset();
		myStromaPanel.reset();
		myLumenPanel.reset();
		myActivityPanel.reset();
	}

}