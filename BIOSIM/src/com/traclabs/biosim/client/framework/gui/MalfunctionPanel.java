package biosim.client.framework.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.border.*;
import biosim.client.framework.*;
import biosim.idl.framework.*;
/**
 * @author    Scott Bell
 */
public class MalfunctionPanel extends TimedPanel
{
	private Hashtable myMalfunctionVariables;
	private JList moduleList;
	private JList currentMalfunctionList;
	private JComboBox lengthComboBox;
	private JComboBox intensityComboBox;
	private JPanel myModulePanel;
	private JPanel myOperatorPanel;
	private JPanel myCurrentMalfunctionsPanel;
	/**
	 * Default constructor.
	 */
	public MalfunctionPanel() {
		super();
		myMalfunctionVariables = new Hashtable();
		buildGui();
	}
	
	public void refresh(){
		BioModule myModule = getSelectedModule();
		if (myModule == null)
			return;
		else{
			currentMalfunctionList.setListData(myModule.getMalfunctionNames());
		}
	}

	protected void buildGui(){
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(gridbag);
		
		createModuleSelectPanel();
		createOperatorPanel();
		createCurrentMalfunctionsPanel();
		
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 2;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		gridbag.setConstraints(myModulePanel, c);
		add(myModulePanel);
		c.gridheight = 1;
		c.gridx = 2;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weightx = 0.1;
		c.weighty = 0.1;
		gridbag.setConstraints(myOperatorPanel, c);
		add(myOperatorPanel);
		c.gridy = 2;
		gridbag.setConstraints(myCurrentMalfunctionsPanel, c);
		add(myCurrentMalfunctionsPanel);
	}
	
	private void createModuleSelectPanel(){
		myModulePanel = new JPanel();
		myModulePanel.setBorder(BorderFactory.createTitledBorder("Module Select"));
		String[] myModuleNames = BioHolder.getBioModuleNames();
		moduleList = new JList(myModuleNames);
		moduleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		myModulePanel.add(moduleList);
	}
	
	private void createOperatorPanel(){
		myOperatorPanel = new JPanel();
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		myOperatorPanel.setLayout(gridbag);
		myOperatorPanel.setBorder(BorderFactory.createTitledBorder("Operators"));
		JButton addMalfunctionButton = new JButton(new AddMalfunctionAction());
		addMalfunctionButton.setText("Add Malfunction");
		JLabel lengthLabel = new JLabel("Length");
		String[] lengthStrings = {"Permanent", "Temporary"};
		lengthComboBox = new JComboBox(lengthStrings);
		JLabel intensityLabel = new JLabel("Intensity");
		String[] intensityStrings = {"Severe", "Medium", "Low"};
		intensityComboBox = new JComboBox(intensityStrings);
		JButton fixAllMalfunctionButton = new JButton(new FixAllMalfunctionAction());
		fixAllMalfunctionButton.setText("Fix All");
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.weighty = 1.0;
		gridbag.setConstraints(lengthLabel, c);
		myOperatorPanel.add(lengthLabel);
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(lengthComboBox, c);
		myOperatorPanel.add(lengthComboBox);
		c.gridwidth = GridBagConstraints.RELATIVE;
		gridbag.setConstraints(intensityLabel, c);
		myOperatorPanel.add(intensityLabel);
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(intensityComboBox, c);
		myOperatorPanel.add(intensityComboBox);
		c.gridwidth = GridBagConstraints.RELATIVE;
		gridbag.setConstraints(addMalfunctionButton, c);
		myOperatorPanel.add(addMalfunctionButton);
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(fixAllMalfunctionButton, c);
		myOperatorPanel.add(fixAllMalfunctionButton);
	}
	
	private void createCurrentMalfunctionsPanel(){
		myCurrentMalfunctionsPanel = new JPanel();
		myCurrentMalfunctionsPanel.setBorder(BorderFactory.createTitledBorder("Current Malfunctions"));
		myCurrentMalfunctionsPanel.setLayout(new BorderLayout());
		currentMalfunctionList = new JList();
		myCurrentMalfunctionsPanel.add(currentMalfunctionList, BorderLayout.CENTER);
		JButton fixButton = new JButton(new FixMalfunctionAction());
		fixButton.setText("Fix");
		myCurrentMalfunctionsPanel.add(fixButton, BorderLayout.EAST);
		
	}
	
	public static void main(String[] args){
		BioFrame myFrame = new BioFrame("BioSIM Malfunctions Controller", false);
		MalfunctionPanel myMalfPanel = new MalfunctionPanel();
		myFrame.getContentPane().add(myMalfPanel);
		myFrame.pack();
		myFrame.setVisible(true);
		myMalfPanel.setDelay(200);
		myMalfPanel.visibilityChange(true);
	}
	
	private BioModule getSelectedModule(){
		String currentName = (String)(moduleList.getSelectedValue());
		return (BioHolder.getBioModule(currentName));
	}
	
	private MalfunctionIntensity getSelectedIntensity(){
		String intensityString = (String)(intensityComboBox.getSelectedItem());
		if (intensityString.equals("Severe"))
			return MalfunctionIntensity.SEVERE_MALF;
		else if (intensityString.equals("Medium"))
			return MalfunctionIntensity.MEDIUM_MALF;
		else if (intensityString.equals("Low"))
			return MalfunctionIntensity.LOW_MALF;
		else return null;
	}
	
	private MalfunctionLength getSelectedLength(){
		String lengthString = (String)(lengthComboBox.getSelectedItem());
		if (lengthString.equals("Temporary"))
			return MalfunctionLength.TEMPORARY_MALF;
		else if (lengthString.equals("Permanent"))
			return MalfunctionLength.PERMANENT_MALF;
		else return null;
	}
	
	private class FixAllMalfunctionAction extends AbstractAction{
		public void actionPerformed(ActionEvent ae){
			System.out.println("FixAllMalfunctionAction button was pressed");
			BioModule myModule = getSelectedModule();
			if (myModule == null)
				return;
			else
				myModule.fixAllMalfunctions();
		}
	}
	
	private class FixMalfunctionAction extends AbstractAction{
		public void actionPerformed(ActionEvent ae){
			System.out.println("FixMalfunctionAction button was pressed");
		}
	}
	
	private class AddMalfunctionAction extends AbstractAction{
		public void actionPerformed(ActionEvent ae){
			System.out.println("AddMalfunctionAction button was pressed");
			BioModule myModule = getSelectedModule();
			MalfunctionIntensity myIntensity = getSelectedIntensity();
			MalfunctionLength myLength = getSelectedLength();
			if (myModule == null || myIntensity == null || myLength == null)
				return;
			else{
				myModule.startMalfunction(myIntensity, myLength);
			}
		}
	}
}
