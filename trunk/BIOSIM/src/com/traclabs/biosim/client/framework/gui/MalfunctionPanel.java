package biosim.client.framework.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
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
		JPanel malfunctionPanel = new JPanel();
		JButton addNewMalfunctionButton = new JButton(new AddMalfunctionAction(myModule));
		addNewMalfunctionButton.setText("Add Malfunction");
		String[] lengthStrings = {"Permanent", "Temporary"};
		JComboBox lengthComboBox = new JComboBox(lengthStrings);
		String[] severityStrings = {"Severe", "Medium", "Low"};
		JComboBox severityComboBox = new JComboBox(severityStrings);
		malfunctionPanel.add(addNewMalfunctionButton);
		malfunctionPanel.add(lengthComboBox);
		malfunctionPanel.add(severityComboBox);
		MalfunctionVariableIndex newVariableIndex = new MalfunctionVariableIndex(lengthComboBox, severityComboBox);
		myMalfunctionVariables.put(myModule.getModuleName(), newVariableIndex);
		JPanel clearPanel = new JPanel();
		return newPanel;
	}
	
	private class AddMalfunctionAction extends AbstractAction{
		BioModule myModule;
		AddMalfunctionAction(BioModule pModule){
			myModule = pModule;
		}
		public void actionPerformed(ActionEvent ae){
			System.out.println(myModule.getModuleName() + " button was pressed");
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
