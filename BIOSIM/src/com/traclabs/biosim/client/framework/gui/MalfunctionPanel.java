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
	private JComboBox lengthComboBox;
	private JComboBox severityComboBox;
	private JPanel myModulePanel;
	private JPanel myOperatorPanel;
	/**
	 * Default constructor.
	 */
	public MalfunctionPanel() {
		super();
		myMalfunctionVariables = new Hashtable();
		buildGui();
	}
	
	public void refresh(){
	}

	protected void buildGui(){
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(gridbag);
		
		createModuleSelectPanel();
		createOperatorPanel();
		createCurrentMalfunctionsPanel();
		
		c.fill = GridBagConstraints.NONE;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		gridbag.setConstraints(myModulePanel, c);
		add(myModulePanel);
		c.gridx = 2;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weightx = 0.1;
		c.weighty = 0.1;
		gridbag.setConstraints(myOperatorPanel, c);
		add(myOperatorPanel);
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
		JLabel severityLabel = new JLabel("Severity");
		String[] severityStrings = {"Severe", "Medium", "Low"};
		severityComboBox = new JComboBox(severityStrings);
		JButton clearMalfunctionButton = new JButton(new ClearMalfunctionAction());
		clearMalfunctionButton.setText("Clear All Malfunctions");
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
		gridbag.setConstraints(severityLabel, c);
		myOperatorPanel.add(severityLabel);
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(severityComboBox, c);
		myOperatorPanel.add(severityComboBox);
		c.gridwidth = GridBagConstraints.RELATIVE;
		gridbag.setConstraints(addMalfunctionButton, c);
		myOperatorPanel.add(addMalfunctionButton);
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(clearMalfunctionButton, c);
		myOperatorPanel.add(clearMalfunctionButton);
	}
	
	private void createCurrentMalfunctionsPanel(){
	}
	
	public static void main(String[] args){
		BioFrame myFrame = new BioFrame("BioSIM Malfunctions Controller", false);
		MalfunctionPanel myMalfPanel = new MalfunctionPanel();
		myFrame.getContentPane().add(myMalfPanel);
		myFrame.pack();
		myFrame.setVisible(true);
	}
	
	private class ClearMalfunctionAction extends AbstractAction{
		public void actionPerformed(ActionEvent ae){
			System.out.println("ClearMalfunctionAction button was pressed");
		}
	}
	
	private class AddMalfunctionAction extends AbstractAction{
		public void actionPerformed(ActionEvent ae){
			System.out.println("AddMalfunctionAction button was pressed");
		}
	}
}
