package biosim.client.framework.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
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
		refresh();
	}

	public void refresh(){
		BioModule myModule = getSelectedModule();
		if (myModule == null)
			return;
		else{
			int lastMalfunctionIndex = currentMalfunctionList.getSelectedIndex();
			currentMalfunctionList.setListData(myModule.getMalfunctions());
			if ((lastMalfunctionIndex != -1) && (lastMalfunctionIndex < currentMalfunctionList.getModel().getSize()))
				currentMalfunctionList.setSelectedIndex(lastMalfunctionIndex);
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
		myModulePanel.setLayout(new BorderLayout());
		String[] myModuleNames = BioHolder.getBioModuleNames();
		moduleList = new JList(myModuleNames);
		moduleList.addListSelectionListener(new ModuleListener());
		moduleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		myModulePanel.add(moduleList, BorderLayout.CENTER);
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
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(addMalfunctionButton, c);
		myOperatorPanel.add(addMalfunctionButton);
	}

	private void createCurrentMalfunctionsPanel(){
		myCurrentMalfunctionsPanel = new JPanel();
		myCurrentMalfunctionsPanel.setBorder(BorderFactory.createTitledBorder("Current Malfunctions"));
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		myCurrentMalfunctionsPanel.setLayout(gridbag);
		currentMalfunctionList = new JList();
		currentMalfunctionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		currentMalfunctionList.setCellRenderer(new MalfunctionRenderer());
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = 1;
		gridbag.setConstraints(currentMalfunctionList, c);
		myCurrentMalfunctionsPanel.add(currentMalfunctionList);
		JButton fixButton = new JButton(new FixMalfunctionAction());
		fixButton.setText("Fix");
		c.weightx = 0.1;
		c.weighty = 0.1;
		c.gridwidth = GridBagConstraints.RELATIVE;
		gridbag.setConstraints(fixButton, c);
		myCurrentMalfunctionsPanel.add(fixButton);
		JButton fixAllMalfunctionButton = new JButton(new FixAllMalfunctionAction());
		fixAllMalfunctionButton.setText("Fix All");
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(fixAllMalfunctionButton, c);
		myCurrentMalfunctionsPanel.add(fixAllMalfunctionButton);

	}

	public static void main(String[] args){
		BioFrame myFrame = new BioFrame("BioSIM Malfunctions Controller", false);
		MalfunctionPanel myMalfPanel = new MalfunctionPanel();
		myFrame.getContentPane().add(myMalfPanel);
		myFrame.pack();
		myFrame.setVisible(true);
		myMalfPanel.setDelay(500);
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

	private Malfunction getSelectedMalfunction(){
		return ((Malfunction)(currentMalfunctionList.getSelectedValue()));

	}

	private class FixAllMalfunctionAction extends AbstractAction{
		public void actionPerformed(ActionEvent ae){
			BioModule myModule = getSelectedModule();
			if (myModule == null)
				return;
			else
				myModule.fixAllMalfunctions();
			refresh();
		}
	}

	private class FixMalfunctionAction extends AbstractAction{
		public void actionPerformed(ActionEvent ae){
			Malfunction malfunctionSelected = getSelectedMalfunction();
			BioModule myModule = getSelectedModule();
			if (malfunctionSelected == null || myModule == null)
				return;
			else
				myModule.fixMalfunction(malfunctionSelected.getID());
			refresh();
		}
	}

	private class AddMalfunctionAction extends AbstractAction{
		public void actionPerformed(ActionEvent ae){
			BioModule myModule = getSelectedModule();
			MalfunctionIntensity myIntensity = getSelectedIntensity();
			MalfunctionLength myLength = getSelectedLength();
			if (myModule == null || myIntensity == null || myLength == null)
				return;
			else{
				myModule.startMalfunction(myIntensity, myLength);
			}
			refresh();
		}
	}

	private class MalfunctionRenderer extends JLabel implements ListCellRenderer {
		public MalfunctionRenderer(){
			this.setOpaque(true);
			//this.setHorizontalAlignment(LEFT_ALIGNMENT);
			//this.setVerticalAlignment(CENTER);
		}
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus){
			if (isSelected){
				this.setBackground(list.getSelectionBackground());
				this.setForeground(list.getSelectionForeground());
			}
			else{
				this.setBackground(list.getBackground());
				this.setForeground(list.getForeground());
			}
			Malfunction myMalfunction = (Malfunction)value;
			this.setText(myMalfunction.getName());
			return this;
		}
	}

	private class ModuleListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e){
			refresh();
		}
	}
}
