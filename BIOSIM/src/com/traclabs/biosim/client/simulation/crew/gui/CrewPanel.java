package biosim.client.crew.gui;

import biosim.client.framework.*;
import biosim.client.framework.gui.*;
import biosim.idl.crew.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.text.*;

public class CrewPanel extends JPanel implements BioSimulatorListener
{
	private JPanel crewPanel;

	private CrewGroup myCrew;
	private BioSimulator myBioSimulator;
	private CrewPerson[] myCrewPeople;
	private Vector crewPersonGUIVector;
	private JPanel noCrewPanel;
	private JLabel noCrewLabel;

	public CrewPanel(BioSimulator pBioSimulator){
		myBioSimulator = pBioSimulator;
		myCrew = (CrewGroup)(myBioSimulator.getBioModule(BioSimulator.crewName));
		crewPersonGUIVector = new Vector();
		buildGui();
		myBioSimulator.registerListener(this);
	}
	
	private void rebuildGui(){
		myCrew = (CrewGroup)(myBioSimulator.getBioModule(BioSimulator.crewName));
		crewPersonGUIVector = new Vector();
		buildGui();
		SimDesktopFrame mySimFrame = getSimFrame();
		if (mySimFrame != null)
			mySimFrame.pack();
	}

	private void buildGui(){
		removeAll();
		myCrewPeople = myCrew.getCrewPeople();
		if (myCrewPeople.length == 0){
			setLayout(new BorderLayout());
			noCrewPanel = new JPanel();
			noCrewPanel.setLayout(new BorderLayout());
			noCrewLabel = new JLabel("No crew to display");
			noCrewPanel.add(noCrewLabel, BorderLayout.CENTER);
			add(noCrewPanel, BorderLayout.CENTER);
		}
		setLayout(new GridLayout(myCrewPeople.length / 2, 2));
		for (int i = 0; i < myCrewPeople.length; i++){
			JPanel newPersonPanel = new JPanel();
			newPersonPanel.setLayout(new GridLayout(8,1));
			newPersonPanel.setBorder(BorderFactory.createTitledBorder(myCrewPeople[i].getName()));
			CrewPersonGUI newPersonGUI = new CrewPersonGUI();
			newPersonGUI.name = myCrewPeople[i].getName();
			newPersonGUI.ageLabel = new JLabel("age: "+myCrewPeople[i].getAge());
			newPersonPanel.add(newPersonGUI.ageLabel);
			newPersonGUI.weightLabel = new JLabel("weight: "+myCrewPeople[i].getWeight());
			newPersonPanel.add(newPersonGUI.weightLabel);
			String sexString;
			if (myCrewPeople[i].getSex() == Sex.male)
				sexString = "male";
			else
				sexString = "female";
			newPersonGUI.sexLabel = new JLabel("sex: "+sexString);
			newPersonPanel.add(newPersonGUI.sexLabel);
			newPersonGUI.statusLabel = new JLabel("status: "+coallateStatus(myCrewPeople[i]));
			newPersonPanel.add(newPersonGUI.statusLabel);
			newPersonGUI.activityNameLabel = new JLabel("current activity: "+myCrewPeople[i].getCurrentActivity().getName());
			newPersonPanel.add(newPersonGUI.activityNameLabel);
			newPersonGUI.activityCurrentDurationLabel = new JLabel("	performed for: "+myCrewPeople[i].getTimeActivityPerformed());
			newPersonPanel.add(newPersonGUI.activityCurrentDurationLabel);
			newPersonGUI.activityTotalDurationLabel = new JLabel("	total duration: "+myCrewPeople[i].getCurrentActivity().getTimeLength());
			newPersonPanel.add(newPersonGUI.activityTotalDurationLabel);
			newPersonGUI.activityIntensityLabel = new JLabel("	intensity: "+myCrewPeople[i].getCurrentActivity().getActivityIntensity());
			newPersonPanel.add(newPersonGUI.activityIntensityLabel);
			crewPersonGUIVector.add(newPersonGUI);
			add(newPersonPanel);
		}
	}
	
	private String coallateStatus(CrewPerson pCrewPerson){
		if (pCrewPerson.isDead())
			return "dead";
		StringBuffer statusBuffer = new StringBuffer();
		if (pCrewPerson.isStarving())
			statusBuffer.append("starving, ");
		if (pCrewPerson.isPoisoned())
			statusBuffer.append("CO2 poisoned, ");
		if (pCrewPerson.isThirsty())
			statusBuffer.append("thirsty, ");
		if (pCrewPerson.isSuffocating())
			statusBuffer.append("suffocating, ");
		if (statusBuffer.length() < 1)
			return "nominal";
		else{
			statusBuffer.delete(statusBuffer.length() -2, statusBuffer.length());
			return statusBuffer.toString();
		}
	}

	public void processTick(){
		if (crewPersonGUIVector.size() == 0){
			myCrewPeople = myCrew.getCrewPeople();
			if (myCrewPeople.length > 0){
				rebuildGui();
			}
		}
		for (Enumeration e = crewPersonGUIVector.elements(); e.hasMoreElements();){
			CrewPersonGUI newPersonGUI = (CrewPersonGUI)(e.nextElement());
			CrewPerson crewPerson = myCrew.getCrewPerson(newPersonGUI.name);
			newPersonGUI.ageLabel.setText("age: "+crewPerson.getAge());
			newPersonGUI.weightLabel.setText("weight: "+crewPerson.getWeight());
			newPersonGUI.activityNameLabel.setText("current activity: "+crewPerson.getCurrentActivity().getName());
			newPersonGUI.activityCurrentDurationLabel.setText("	performed for: "+crewPerson.getTimeActivityPerformed());
			newPersonGUI.activityTotalDurationLabel.setText("	total duration: "+crewPerson.getCurrentActivity().getTimeLength());
			newPersonGUI.activityIntensityLabel.setText("	intensity: "+crewPerson.getCurrentActivity().getActivityIntensity());
			newPersonGUI.statusLabel.setText("status: "+coallateStatus(crewPerson));
			String sexString;
			if (crewPerson.getSex() == Sex.male)
				sexString = "male";
			else
				sexString = "female";
			newPersonGUI.sexLabel.setText("sex: "+sexString);
		}
	}
	
	private SimDesktopFrame getSimFrame(){
		Container theContainer = getParent();
		while (theContainer != null){
			if (theContainer instanceof SimDesktopFrame)
				return ((SimDesktopFrame)(theContainer));
			else
				theContainer = theContainer.getParent();
		}
		return null;
	}

	private class CrewPersonGUI{
		String name;
		JLabel ageLabel;
		JLabel weightLabel;
		JLabel sexLabel;
		JLabel activityNameLabel;
		JLabel activityCurrentDurationLabel;
		JLabel activityTotalDurationLabel;
		JLabel activityIntensityLabel;
		JLabel statusLabel;
	}
}
