package com.traclabs.biosim.client.simulation.crew;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.traclabs.biosim.client.framework.TimedPanel;
import com.traclabs.biosim.client.simulation.framework.SimDesktopFrame;
import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.simulation.crew.CrewGroup;
import com.traclabs.biosim.idl.simulation.crew.CrewPerson;
import com.traclabs.biosim.idl.simulation.crew.Sex;

/**
 * This is the JPanel that displays information about the crew and the resources
 * they consume. Each tick it polls the crew server for new information.
 * 
 * @author Scott Bell
 */

public class CrewTextPanel extends TimedPanel {

	// Panel and Label displayed when no crew member are found on the server
	private JPanel noCrewPanel;

	private JLabel noCrewLabel;

	private Map<String, CrewPerson> myCrewPeople = new HashMap<String, CrewPerson>();

	// Vector of crew people GUI's and their respective GUI components
	private java.util.List<CrewPersonGUI> crewPersonGUIList = new Vector<CrewPersonGUI>();

	// Used to format floats
	private DecimalFormat numFormat = new DecimalFormat("#,##0.00;(#)");

	/**
	 * Creates and registers this panel.
	 */
	public CrewTextPanel() {
		buildGui();
	}

	/**
	 * Contructs GUI components, adds them to the panel.
	 */
	private void buildGui() {
		for (CrewGroup crewGroup : BioHolderInitializer.getBioHolder().theCrewGroups)
			for (CrewPerson person : crewGroup.getCrewPeople())
				myCrewPeople.put(person.getName(), person);
		removeAll();
		if (myCrewPeople.size() == 0) {
			setLayout(new GridLayout(1, 1));
			noCrewPanel = new JPanel();
			noCrewPanel.setLayout(new BorderLayout());
			noCrewLabel = new JLabel("No crew to display");
			noCrewPanel.add(noCrewLabel);
			add(noCrewPanel, BorderLayout.CENTER);
		} else {
			if (myCrewPeople.size() == 1)
				setLayout(new GridLayout(1, 1));
			if ((myCrewPeople.size() % 2) != 0)
				setLayout(new GridLayout(myCrewPeople.size() / 2 + 1,
						myCrewPeople.size() / 2));
			else
				setLayout(new GridLayout(myCrewPeople.size() / 2,
						myCrewPeople.size() / 2));
			
			for (CrewPerson person : myCrewPeople.values()) {
				JPanel newPersonPanel = new JPanel();
				newPersonPanel.setLayout(new GridLayout(16, 1));
				newPersonPanel.setBorder(BorderFactory
						.createTitledBorder(person.getName()));
				CrewPersonGUI newPersonGUI = new CrewPersonGUI();
				newPersonGUI.name = person.getName();
				newPersonGUI.ageLabel = new JLabel("age: "
						+ person.getAge());
				newPersonPanel.add(newPersonGUI.ageLabel);
				newPersonGUI.weightLabel = new JLabel("weight: "
						+ person.getWeight());
				newPersonPanel.add(newPersonGUI.weightLabel);
				String sexString;
				if (person.getSex() == Sex.male)
					sexString = "male";
				else
					sexString = "female";
				newPersonGUI.sexLabel = new JLabel("sex: " + sexString);
				newPersonPanel.add(newPersonGUI.sexLabel);
				newPersonGUI.statusLabel = new JLabel("status: "
						+ coallateStatus(person));
				newPersonPanel.add(newPersonGUI.statusLabel);
				newPersonGUI.dirtyWaterProducedLabel = new JLabel(
						"dirty water produced: "
								+ numFormat.format(person
										.getDirtyWaterProduced()) + " L");
				newPersonPanel.add(newPersonGUI.dirtyWaterProducedLabel);
				newPersonGUI.greyWaterProducedLabel = new JLabel(
						"grey water produced: "
								+ numFormat.format(person
										.getGreyWaterProduced()) + " L");
				newPersonPanel.add(newPersonGUI.greyWaterProducedLabel);
				newPersonGUI.potableWaterConsumedLabel = new JLabel(
						"potable water consumed: "
								+ numFormat.format(person
										.getPotableWaterConsumed()) + " L");
				newPersonPanel.add(newPersonGUI.potableWaterConsumedLabel);
				newPersonGUI.foodConsumedLabel = new JLabel("food consumed: "
						+ numFormat.format(person
								.getFoodConsumed()) + " kCal");
				newPersonPanel.add(newPersonGUI.foodConsumedLabel);
				newPersonGUI.CO2ProducedLabel = new JLabel("CO2 produced: "
						+ numFormat.format(person
								.getCO2Produced()) + " moles");
				newPersonPanel.add(newPersonGUI.CO2ProducedLabel);
				newPersonGUI.O2ConsumedLabel = new JLabel("O2 consumed: "
						+ numFormat
								.format(person.getO2Consumed())
						+ " moles");
				newPersonPanel.add(newPersonGUI.O2ConsumedLabel);
				newPersonGUI.activityNameLabel = new JLabel(
						"current activity: "
								+ person.getCurrentActivity()
										.getName());
				newPersonPanel.add(newPersonGUI.activityNameLabel);
				newPersonGUI.activityCurrentDurationLabel = new JLabel(
						"	performed for: "
								+ person
										.getTimeActivityPerformed() + " h");
				newPersonPanel.add(newPersonGUI.activityCurrentDurationLabel);
				newPersonGUI.activityTotalDurationLabel = new JLabel(
						"	total duration: "
								+ person.getCurrentActivity()
										.getTimeLength() + " h");
				newPersonPanel.add(newPersonGUI.activityTotalDurationLabel);
				newPersonGUI.activityIntensityLabel = new JLabel("	intensity: "
						+ person.getCurrentActivity()
								.getActivityIntensity());
				newPersonPanel.add(newPersonGUI.activityIntensityLabel);
				newPersonGUI.productivityLabel = new JLabel("productivity: "
						+ numFormat.format(person
								.getProductivity()));
				newPersonPanel.add(newPersonGUI.productivityLabel);
				newPersonGUI.locationLabel = new JLabel("location: "+person.getCurrentCrewGroup().getAirConsumerDefinition().getEnvironments()[0].getModuleName());
				newPersonPanel.add(newPersonGUI.locationLabel);
				crewPersonGUIList.add(newPersonGUI);
				add(newPersonPanel);
			}
		}
	}

	/**
	 * Checks the status of a CrewPerson and constructs a string describing
	 * them.
	 * 
	 * @return A String representing the status of the crew person
	 * @param pCrewPerson
	 *            the crew person who's status needs to be described
	 */
	private String coallateStatus(CrewPerson pCrewPerson) {
		if (pCrewPerson.isDead())
			return "dead";
		StringBuffer statusBuffer = new StringBuffer();
		if (pCrewPerson.isSuffocating())
			statusBuffer.append("suffocating, ");
		if (pCrewPerson.isPoisoned())
			statusBuffer.append("CO2 poisoned, ");
		if (pCrewPerson.isThirsty())
			statusBuffer.append("thirsty, ");
		if (pCrewPerson.isStarving())
			statusBuffer.append("starving, ");
		if (pCrewPerson.isSick())
			statusBuffer.append("sick, ");
		if (statusBuffer.length() < 1)
			return "nominal";

		statusBuffer.delete(statusBuffer.length() - 2, statusBuffer.length());
		return statusBuffer.toString();

	}

	/**
	 * Enumerates through all the crew memebers this panel knows about and
	 * updates their labels by pulling from the crew server.
	 */
	public void refresh() {
		for (CrewPersonGUI newPersonGUI : crewPersonGUIList) {
			CrewPerson crewPerson = myCrewPeople.get(newPersonGUI.name);
			newPersonGUI.ageLabel.setText("age: " + crewPerson.getAge());
			newPersonGUI.weightLabel.setText("weight: "
					+ crewPerson.getWeight());
			newPersonGUI.activityNameLabel.setText("current activity: "
					+ crewPerson.getCurrentActivity().getName());
			newPersonGUI.activityCurrentDurationLabel
					.setText("	performed for: "
							+ crewPerson.getTimeActivityPerformed() + " h");
			newPersonGUI.activityTotalDurationLabel.setText("	total duration: "
					+ crewPerson.getCurrentActivity().getTimeLength() + " h");
			newPersonGUI.activityIntensityLabel.setText("	intensity: "
					+ crewPerson.getCurrentActivity().getActivityIntensity());
			newPersonGUI.statusLabel.setText("status: "
					+ coallateStatus(crewPerson));
			newPersonGUI.dirtyWaterProducedLabel
					.setText("dirty water produced: "
							+ numFormat.format(crewPerson
									.getDirtyWaterProduced()) + " L");
			newPersonGUI.greyWaterProducedLabel.setText("grey water produced: "
					+ numFormat.format(crewPerson.getGreyWaterProduced())
					+ " L");
			newPersonGUI.potableWaterConsumedLabel
					.setText("potable water consumed: "
							+ numFormat.format(crewPerson
									.getPotableWaterConsumed()) + " L");
			newPersonGUI.foodConsumedLabel.setText("food consumed: "
					+ numFormat.format(crewPerson.getFoodConsumed()) + " kCal");
			newPersonGUI.CO2ProducedLabel.setText("CO2 produced: "
					+ numFormat.format(crewPerson.getCO2Produced()) + " moles");
			newPersonGUI.O2ConsumedLabel.setText("O2 consumed: "
					+ numFormat.format(crewPerson.getO2Consumed()) + " moles");
			newPersonGUI.activityNameLabel.setText("current activity: "
					+ crewPerson.getCurrentActivity().getName());
			newPersonGUI.productivityLabel.setText("productivity: "
					+ numFormat.format(crewPerson.getProductivity()));
			newPersonGUI.locationLabel.setText("location: "+crewPerson.getCurrentCrewGroup().getAirConsumerDefinition().getEnvironments()[0].getModuleName());
			String sexString;
			if (crewPerson.getSex() == Sex.male)
				sexString = "male";
			else
				sexString = "female";
			newPersonGUI.sexLabel.setText("sex: " + sexString);
		}
	}

	/**
	 * Recursively seeks the SimDesktopFrame that contains this panel.
	 * 
	 * @return The SimDesktopFrame parent this panel is in.
	 */
	private SimDesktopFrame getSimFrame() {
		Container theContainer = getParent();
		while (theContainer != null) {
			if (theContainer instanceof SimDesktopFrame)
				return ((SimDesktopFrame) (theContainer));
			theContainer = theContainer.getParent();
		}
		return null;
	}

	/**
	 * A small internal class containing lables for one crew person.
	 */
	private class CrewPersonGUI {
		String name;

		JLabel ageLabel;

		JLabel weightLabel;

		JLabel sexLabel;

		JLabel activityNameLabel;

		JLabel activityCurrentDurationLabel;

		JLabel activityTotalDurationLabel;

		JLabel activityIntensityLabel;

		JLabel statusLabel;

		JLabel greyWaterProducedLabel;

		JLabel dirtyWaterProducedLabel;

		JLabel potableWaterConsumedLabel;

		JLabel foodConsumedLabel;

		JLabel CO2ProducedLabel;

		JLabel O2ConsumedLabel;

		JLabel productivityLabel;
		
		JLabel locationLabel;
	}
}