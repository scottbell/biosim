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
public class StochasticPanel extends TimedPanel
{
	private Hashtable myMalfunctionVariables;
	private JList moduleList;
	private JPanel myModulePanel;
	private JPanel myOperatorPanel;
	private ImageIcon myIcon;
	/**
	 * Default constructor.
	 */
	public StochasticPanel(){
		super();
		buildGui();
		refresh();
	}

	public void refresh(){
		BioModule myModule = getSelectedModule();
		if (myModule == null)
			return;
		else{
			//find where slider used to be
		}
	}

	protected void buildGui(){
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(gridbag);

		createModuleSelectPanel();
		createOperatorPanel();

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
	}

	private void createModuleSelectPanel(){
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		myModulePanel = new JPanel();
		myModulePanel.setBorder(BorderFactory.createTitledBorder("Module Select"));
		myModulePanel.setLayout(gridbag);
		String[] myModuleNames = BioHolder.getBioModuleNames();
		moduleList = new JList(myModuleNames);
		moduleList.addListSelectionListener(new ModuleListener());
		moduleList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		JScrollPane scrollPane = new JScrollPane(moduleList);
		gridbag.setConstraints(scrollPane, c);
		myModulePanel.add(scrollPane);
	}

	private void createOperatorPanel(){
		myOperatorPanel = new JPanel();
	}
	
	/**
	* Attempts to load the icons from the resource directory.
	*/
	private void loadIcons(){
		if (myIcon != null)
			return;
		try{
			myIcon = new ImageIcon(ClassLoader.getSystemClassLoader().getResource("biosim/client/framework/gui/dice.jpg"));
		}
		catch (Exception e){
			System.err.println("Couldn't find icon ("+e+"), skipping");
			e.printStackTrace();
		}
	}
	
	public ImageIcon getIcon(){
		loadIcons();
		return myIcon;
	}

	public static void main(String[] args){
		BioFrame myFrame = new BioFrame("BioSIM Stochastic Controller", false);
		StochasticPanel myStochasticPanel = new StochasticPanel();
		myFrame.getContentPane().add(myStochasticPanel);
		myFrame.setSize(550,350);
		myFrame.setVisible(true);
		myFrame.setIconImage(myStochasticPanel.getIcon().getImage());
		myStochasticPanel.visibilityChange(true);
	}

	private BioModule getSelectedModule(){
		String currentName = (String)(moduleList.getSelectedValue());
		return (BioHolder.getBioModule(currentName));
	}

	private class ModuleListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e){
			refresh();
		}
	}
}
