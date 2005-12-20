package com.traclabs.biosim.client.framework;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;

import org.apache.log4j.Logger;

import com.traclabs.biosim.client.util.BioHolderInitializer;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.framework.Malfunction;
import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.simulation.crew.CrewGroup;
import com.traclabs.biosim.util.OrbUtils;

/**
 * @author Scott Bell
 */
public class MalfunctionPanel extends TimedPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5790062979385349615L;

	private JList currentMalfunctionList;

	private JComboBox lengthComboBox;

	private JComboBox intensityComboBox;

	private ModulePanel myModulePanel;

	private JPanel myOperatorPanel;

	private JPanel myCurrentMalfunctionsPanel;

	private ImageIcon myIcon;

	private Logger myLogger;

	/**
	 * Default constructor.
	 */
	public MalfunctionPanel() {
		super();
		myLogger = Logger.getLogger(this.getClass());
		buildGui();
		refresh();
	}

	public void refresh() {
		BioModule selectedModule = myModulePanel.getSelectedModule();
		if ((selectedModule == null) || (currentMalfunctionList == null))
			return;
		int lastMalfunctionIndex = currentMalfunctionList.getSelectedIndex();
		currentMalfunctionList.setListData(selectedModule.getMalfunctions());
		if ((lastMalfunctionIndex != -1)
				&& (lastMalfunctionIndex < currentMalfunctionList.getModel()
						.getSize()))
			currentMalfunctionList.setSelectedIndex(lastMalfunctionIndex);
		currentMalfunctionList.updateUI();
	}

	protected void buildGui() {
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
		gridbag.setConstraints(myOperatorPanel, c);
		add(myOperatorPanel);
		c.gridy = 2;
		gridbag.setConstraints(myCurrentMalfunctionsPanel, c);
		add(myCurrentMalfunctionsPanel);
	}

	private void createModuleSelectPanel() {
		myModulePanel = new ModulePanel();
		myModulePanel.setBorder(BorderFactory
				.createTitledBorder("Module Select"));
		myModulePanel
				.addModuleSelectionListener(new MalfunctionModuleListener());
	}

	private void createOperatorPanel() {
		myOperatorPanel = new JPanel();
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		myOperatorPanel.setLayout(gridbag);
		myOperatorPanel
				.setBorder(BorderFactory.createTitledBorder("Operators"));
		JButton addMalfunctionButton = new JButton(new AddMalfunctionAction());
		addMalfunctionButton.setText("Add Malfunction");
		JLabel lengthLabel = new JLabel("Length");
		String[] lengthStrings = { "Permanent", "Temporary" };
		lengthComboBox = new JComboBox(lengthStrings);
		JLabel intensityLabel = new JLabel("Intensity");
		String[] intensityStrings = { "Severe", "Medium", "Low" };
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

	private void createCurrentMalfunctionsPanel() {
		myCurrentMalfunctionsPanel = new JPanel();
		myCurrentMalfunctionsPanel.setBorder(BorderFactory
				.createTitledBorder("Current Malfunctions"));
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		myCurrentMalfunctionsPanel.setLayout(gridbag);
		currentMalfunctionList = new JList();
		currentMalfunctionList
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		currentMalfunctionList.setCellRenderer(new MalfunctionRenderer());
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 3;
		c.gridwidth = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		JScrollPane scrollPane = new JScrollPane(currentMalfunctionList);
		gridbag.setConstraints(scrollPane, c);
		myCurrentMalfunctionsPanel.add(scrollPane);
		JButton scheduleRepairButton = new JButton(
				new ScheduleRepairMalfunctionAction());
		scheduleRepairButton.setText("Schedule Repair");
		c.gridheight = 1;
		c.gridwidth = GridBagConstraints.REMAINDER;
		gridbag.setConstraints(scheduleRepairButton, c);
		myCurrentMalfunctionsPanel.add(scheduleRepairButton);
		JButton fixButton = new JButton(new FixMalfunctionAction());
		fixButton.setText("Fix");
		gridbag.setConstraints(fixButton, c);
		myCurrentMalfunctionsPanel.add(fixButton);
		JButton fixAllMalfunctionButton = new JButton(
				new FixAllMalfunctionAction());
		fixAllMalfunctionButton.setText("Fix All");
		gridbag.setConstraints(fixAllMalfunctionButton, c);
		myCurrentMalfunctionsPanel.add(fixAllMalfunctionButton);

	}

	/**
	 * Attempts to load the icons from the resource directory.
	 */
	private void loadIcons() {
		if (myIcon != null)
			return;
		try {
			myIcon = new ImageIcon(
					MalfunctionPanel.class
							.getClassLoader()
							.getResource(
									"com/traclabs/biosim/client/framework/gui/gear.gif"));
		} catch (Exception e) {
			myLogger.warn("Couldn't find icon (" + e + "), skipping");
			e.printStackTrace();
		}
	}

	public ImageIcon getIcon() {
		loadIcons();
		return myIcon;
	}

	public static void main(String[] args) {
		OrbUtils.initializeLog();
		BioFrame myFrame = new BioFrame("BioSim Malfunctions Controller", false);
		MalfunctionPanel myMalfPanel = new MalfunctionPanel();
		myFrame.getContentPane().add(myMalfPanel);
		myFrame.setVisible(true);
		myFrame.setIconImage(myMalfPanel.getIcon().getImage());
		myMalfPanel.setDelay(500);
		myFrame.setSize(700, 450);
		myMalfPanel.visibilityChange(true);
	}

	private MalfunctionIntensity getSelectedIntensity() {
		String intensityString = (String) (intensityComboBox.getSelectedItem());
		if (intensityString.equals("Severe"))
			return MalfunctionIntensity.SEVERE_MALF;
		else if (intensityString.equals("Medium"))
			return MalfunctionIntensity.MEDIUM_MALF;
		else if (intensityString.equals("Low"))
			return MalfunctionIntensity.LOW_MALF;
		else
			return null;
	}

	private MalfunctionLength getSelectedLength() {
		String lengthString = (String) (lengthComboBox.getSelectedItem());
		if (lengthString.equals("Temporary"))
			return MalfunctionLength.TEMPORARY_MALF;
		else if (lengthString.equals("Permanent"))
			return MalfunctionLength.PERMANENT_MALF;
		else
			return null;
	}

	private Malfunction getSelectedMalfunction() {
		return ((Malfunction) (currentMalfunctionList.getSelectedValue()));
	}

	private class FixAllMalfunctionAction extends AbstractAction {
		public void actionPerformed(ActionEvent ae) {
			BioModule myModule = myModulePanel.getSelectedModule();
			if (myModule == null)
				return;
			myModule.fixAllMalfunctions();
			refresh();
		}
	}

	private class FixMalfunctionAction extends AbstractAction {
		public void actionPerformed(ActionEvent ae) {
			Malfunction malfunctionSelected = getSelectedMalfunction();
			BioModule myModule = myModulePanel.getSelectedModule();
			if (malfunctionSelected == null || myModule == null)
				return;
			myModule.fixMalfunction(malfunctionSelected.getID());
			refresh();
		}
	}

	private class ScheduleRepairMalfunctionAction extends AbstractAction {
		public void actionPerformed(ActionEvent ae) {
			Malfunction malfunctionSelected = getSelectedMalfunction();
			BioModule myModule = myModulePanel.getSelectedModule();
			if (malfunctionSelected == null || myModule == null)
				return;
			CrewGroup myCrewGroup = (BioHolderInitializer
					.getBioHolder().theCrewGroups.get(0));
			myCrewGroup.scheduleRepair(myModule.getModuleName(),
					malfunctionSelected.getID(), 5);
			refresh();
		}
	}

	private class AddMalfunctionAction extends AbstractAction {
		public void actionPerformed(ActionEvent ae) {
			BioModule myModule = myModulePanel.getSelectedModule();
			MalfunctionIntensity myIntensity = getSelectedIntensity();
			MalfunctionLength myLength = getSelectedLength();
			myLogger.debug("myModule = " + myModule);
			if (myModule == null || myIntensity == null || myLength == null)
				return;
			myModule.startMalfunction(myIntensity, myLength);
			refresh();
		}
	}

	private class MalfunctionRenderer extends JLabel implements
			ListCellRenderer {
		public MalfunctionRenderer() {
			this.setOpaque(true);
		}

		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			if (isSelected) {
				this.setBackground(list.getSelectionBackground());
				this.setForeground(list.getSelectionForeground());
			} else {
				this.setBackground(list.getBackground());
				this.setForeground(list.getForeground());
			}
			Malfunction myMalfunction = (Malfunction) value;
			this.setText(myMalfunction.getName());
			return this;
		}
	}

	private class MalfunctionModuleListener implements ModuleSelectionListener {
		public void stateChanged(ChangeEvent e) {
			refresh();
		}
	}
}
