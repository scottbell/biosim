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
		BioModule[] myModules = BioHolder.getBioModules();
		if (myModules.length == 0){
			setLayout(new GridLayout(1,1));
			JLabel noModulesLabel = new JLabel("No modules to display");
			add(noModulesLabel);
			return;
		}
		else{
			if (myModules.length == 1)
				setLayout(new GridLayout(1, 1));
			if ((myModules.length % 2) != 0)
				setLayout(new GridLayout(myModules.length / 2 + 1, myModules.length / 2));
			else
				setLayout(new GridLayout(myModules.length / 2, myModules.length / 2));
		}
		for (int i = 0; i < myModules.length; i++){
			add(createMalfunctionRow(myModules[i]));
		}
	}
	
	public static void main(String[] args){
		BioFrame myFrame = new BioFrame("BioSIM Malfunctions Controller", false);
		MalfunctionPanel myMalfPanel = new MalfunctionPanel();
		myFrame.getContentPane().add(myMalfPanel);
		myFrame.pack();
		myFrame.setVisible(true);
	}
	
	private JPanel createMalfunctionRow(BioModule myModule){
		JPanel newPanel = new JPanel();
		newPanel.setLayout(new BorderLayout());
		JPanel addMalfunctionPanel = new JPanel();
		JButton addMalfunctionButton = new JButton(new AddMalfunctionAction(myModule));
		addMalfunctionButton.setText("Add Malfunction");
		String[] lengthStrings = {"Permanent", "Temporary"};
		JComboBox lengthComboBox = new JComboBox(lengthStrings);
		String[] severityStrings = {"Severe", "Medium", "Low"};
		JComboBox severityComboBox = new JComboBox(severityStrings);
		addMalfunctionPanel.add(addMalfunctionButton);
		addMalfunctionPanel.add(lengthComboBox);
		addMalfunctionPanel.add(severityComboBox);
		MalfunctionVariableIndex newVariableIndex = new MalfunctionVariableIndex(lengthComboBox, severityComboBox);
		myMalfunctionVariables.put(myModule.getModuleName(), newVariableIndex);
		JPanel clearMalfunctionPanel = new JPanel();
		JButton clearMalfunctionButton = new JButton(new ClearMalfunctionAction(myModule));
		clearMalfunctionButton.setText("Clear Malfunctions");
		clearMalfunctionPanel.add(clearMalfunctionButton);
		newPanel.add(addMalfunctionPanel, BorderLayout.CENTER);
		newPanel.add(clearMalfunctionPanel, BorderLayout.SOUTH);
		newPanel.setBorder(BorderFactory.createTitledBorder(myModule.getModuleName()));
		return newPanel;
	}
	
	private class ClearMalfunctionAction extends AbstractAction{
		BioModule myModule;
		ClearMalfunctionAction(BioModule pModule){
			myModule = pModule;
		}
		public void actionPerformed(ActionEvent ae){
			System.out.println(myModule.getModuleName() + " ClearMalfunctionAction button was pressed");
		}
	}
	
	private class AddMalfunctionAction extends AbstractAction{
		BioModule myModule;
		AddMalfunctionAction(BioModule pModule){
			myModule = pModule;
		}
		public void actionPerformed(ActionEvent ae){
			System.out.println(myModule.getModuleName() + " AddMalfunctionAction button was pressed");
		}
	}
	
	private class MalfunctionVariableIndex{
		public MalfunctionVariableIndex(JComboBox pLengthComboBox, JComboBox pSeverityComboBox){
			myLengthComboBox = pLengthComboBox;
			mySeverityComboBox = pSeverityComboBox;
		}
		public JComboBox myLengthComboBox;
		public JComboBox mySeverityComboBox;
	}
}
