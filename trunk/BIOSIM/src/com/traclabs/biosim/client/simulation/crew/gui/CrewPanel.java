package biosim.client.crew.gui;

import biosim.client.framework.*;
import biosim.idl.crew.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;

public class CrewPanel extends JPanel implements BioSimulatorListener
{
	private JPanel crewPanel;

	private CrewGroup myCrew;
	private BioSimulator myBioSimulator;
	private CrewPerson[] myCrewPeople;
	private Vector crewPeopleGUI;

	public CrewPanel(BioSimulator pBioSimulator){
		myBioSimulator = pBioSimulator;
		myCrew = (CrewGroup)(myBioSimulator.getBioModule(BioSimulator.crewName));
		crewPeopleGUI = new Vector();
		buildGui();
		myBioSimulator.registerListener(this);
	}

	private void buildGui(){
		myCrewPeople = myCrew.getCrewPeople();
		setLayout(new GridLayout(myCrewPeople.length / 2, 2));
		
		for (int i = 0; i < myCrewPeople.length; i++){
			JPanel newPersonPanel = new JPanel();
			newPersonPanel.setLayout(new GridLayout(8,1));
			newPersonPanel.setBorder(BorderFactory.createTitledBorder(myCrewPeople[i].getName()));
			CrewPersonGUI newPersonGUI = new CrewPersonGUI();
			newPersonGUI.crewPerson = myCrewPeople[i];
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
			newPersonGUI.statusLabel = new JLabel("status: "+myCrewPeople[i].getStatus());
			newPersonPanel.add(newPersonGUI.statusLabel);
			newPersonGUI.activityNameLabel = new JLabel("current activity: "+myCrewPeople[i].getCurrentActivity().getName());
			newPersonPanel.add(newPersonGUI.activityNameLabel);
			newPersonGUI.activityCurrentDurationLabel = new JLabel("	performed for: "+myCrewPeople[i].getTimeActivityPerformed());
			newPersonPanel.add(newPersonGUI.activityCurrentDurationLabel);
			newPersonGUI.activityTotalDurationLabel = new JLabel("	total duration: "+myCrewPeople[i].getCurrentActivity().getTimeLength());
			newPersonPanel.add(newPersonGUI.activityTotalDurationLabel);
			newPersonGUI.activityIntensityLabel = new JLabel("	intensity: "+myCrewPeople[i].getCurrentActivity().getActivityIntensity());
			newPersonPanel.add(newPersonGUI.activityIntensityLabel);
			crewPeopleGUI.add(newPersonGUI);
			add(newPersonPanel);
		}
	}

	public void processTick(){
		for (Enumeration e = crewPeopleGUI.elements(); e.hasMoreElements();){
			CrewPersonGUI newPersonGUI = (CrewPersonGUI)(e.nextElement());
			newPersonGUI.ageLabel.setText("age: "+newPersonGUI.crewPerson.getAge());
			newPersonGUI.weightLabel.setText("weight: "+newPersonGUI.crewPerson.getWeight());
			newPersonGUI.activityNameLabel.setText("current activity: "+newPersonGUI.crewPerson.getCurrentActivity().getName());
			newPersonGUI.activityCurrentDurationLabel.setText("	performed for: "+newPersonGUI.crewPerson.getTimeActivityPerformed());
			newPersonGUI.activityTotalDurationLabel.setText("	total duration: "+newPersonGUI.crewPerson.getCurrentActivity().getTimeLength());
			newPersonGUI.activityIntensityLabel.setText("	intensity: "+newPersonGUI.crewPerson.getCurrentActivity().getActivityIntensity());
			newPersonGUI.statusLabel.setText("status: "+newPersonGUI.crewPerson.getStatus());
			String sexString;
			if (newPersonGUI.crewPerson.getSex() == Sex.male)
				sexString = "male";
			else
				sexString = "female";
			newPersonGUI.sexLabel.setText("sex: "+sexString);
		}
	}

	private class CrewPersonGUI{
		JLabel ageLabel;
		JLabel weightLabel;
		JLabel sexLabel;
		JLabel activityNameLabel;
		JLabel activityCurrentDurationLabel;
		JLabel activityTotalDurationLabel;
		JLabel activityIntensityLabel;
		JLabel statusLabel;
		CrewPerson crewPerson;
	}
}
