package biosim.client.food.gui;

import biosim.client.framework.*;
import biosim.client.framework.gui.*;
import biosim.idl.food.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.*;
import java.text.*;
/**
 * This is the JPanel that displays information about the Food Processor, the Biomass RS, and their respective stores.
 * Each tick it polls each related server for new information regarding these systems.
 *
 * @author    Scott Bell
 */

public class FoodTextPanel extends BioTabPanel
{
	//Various GUI componenets
	private JPanel biomassRSPanel;
	private JLabel biomassRSStatusLabel;
	private JLabel biomassRSPowerLabel;
	private JPanel biomassStorePanel;
	private JLabel biomassStoreLevelLabel;
	private JPanel foodProcessorPanel;
	private JLabel foodProcessorStatusLabel;
	private JLabel foodProcessorPowerLabel;
	private JLabel foodProcessorBiomassConsumedLabel;
	private JLabel foodProcessorFoodProducedLabel;
	private JPanel foodStorePanel;
	private JLabel foodStoreLevelLabel;
	//Servers required for data polling
	private BiomassRS myBiomassRS;
	private BiomassStore myBiomassStore;
	private FoodProcessor myFoodProcessor;
	private FoodStore myFoodStore;
	//Used for registereing this panel (for knowing when a tick occurs)
	private BioSimulator myBioSimulator;
	//For formatting floats
	private DecimalFormat numFormat;
	
	/**
	* Creates and registers this panel.
	* @param pBioSimulator	The Biosimulator this Panel will register itself with.
	*/
	public FoodTextPanel(BioSimulator pBioSimulator){
		myBioSimulator = pBioSimulator;
		myBiomassRS = (BiomassRS)(myBioSimulator.getBioModule(BioSimulator.biomassRSName));
		myBiomassStore = (BiomassStore)(myBioSimulator.getBioModule(BioSimulator.biomassStoreName));
		myFoodProcessor = (FoodProcessor)(myBioSimulator.getBioModule(BioSimulator.foodProcessorName));
		myFoodStore = (FoodStore)(myBioSimulator.getBioModule(BioSimulator.foodStoreName));
		buildGui();
	}
	
	/**
	* Contructs GUI components, adds them to the panel.
	*/
	private void buildGui(){
		numFormat = new DecimalFormat("#,##0.00;(#)");
		GridBagLayout gridbag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		setLayout(gridbag);

		biomassRSPanel = new JPanel();
		biomassRSPanel.setLayout(new GridLayout(2,1));
		biomassRSPanel.setBorder(BorderFactory.createTitledBorder("Biomass Recovery System"));
		biomassRSStatusLabel =                       new JLabel("status:                           "+coallateBiomassRSStatus());
		biomassRSPowerLabel =                      new JLabel("power consumed:         "+numFormat.format(myBiomassRS.getPowerConsumed())+" W");
		biomassRSPanel.add(biomassRSStatusLabel);
		biomassRSPanel.add(biomassRSPowerLabel);

		biomassStorePanel = new JPanel();
		biomassStorePanel.setLayout(new GridLayout(1,1));
		biomassStorePanel.setBorder(BorderFactory.createTitledBorder("Biomass Store"));
		biomassStoreLevelLabel =    new JLabel("biomass level:    "+numFormat.format(myBiomassStore.getLevel())+" kg");
		biomassStorePanel.add(biomassStoreLevelLabel);

		foodProcessorPanel = new JPanel();
		foodProcessorPanel.setLayout(new GridLayout(4,1));
		foodProcessorPanel.setBorder(BorderFactory.createTitledBorder("Food Processor"));
		foodProcessorStatusLabel =                      new JLabel("status:                       "+coallateFoodProcessorStatus());
		foodProcessorFoodProducedLabel =          new JLabel("food produced:          "+numFormat.format(myFoodProcessor.getFoodProduced())+" kg");
		foodProcessorBiomassConsumedLabel =    new JLabel("biomass consumed:   "+numFormat.format(myFoodProcessor.getBiomassConsumed())+" kg");
		foodProcessorPowerLabel =                      new JLabel("power consumed:      "+numFormat.format(myFoodProcessor.getPowerConsumed())+" W");
		foodProcessorPanel.add(foodProcessorStatusLabel);
		foodProcessorPanel.add(foodProcessorFoodProducedLabel);
		foodProcessorPanel.add(foodProcessorBiomassConsumedLabel);
		foodProcessorPanel.add(foodProcessorPowerLabel);

		foodStorePanel = new JPanel();
		foodStorePanel.setLayout(new GridLayout(1,1));
		foodStorePanel.setBorder(BorderFactory.createTitledBorder("Food Store"));
		foodStoreLevelLabel =    new JLabel("food level:    "+numFormat.format(myFoodStore.getLevel())+" kg");
		foodStorePanel.add(foodStoreLevelLabel);

		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		gridbag.setConstraints(biomassRSPanel, c);
		add(biomassRSPanel);

		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		gridbag.setConstraints(biomassStorePanel, c);
		add(biomassStorePanel);

		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		gridbag.setConstraints(foodProcessorPanel, c);
		add(foodProcessorPanel);

		c.fill = GridBagConstraints.BOTH;
		c.gridheight = 1;
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.weighty = 1.0;
		gridbag.setConstraints(foodStorePanel, c);
		add(foodStorePanel);
	}
	
	/**
	 * Checks the status of Biomass RS and constructs a string describing it.
	 * @return	A String representing the status of the Biomass RS
	 */
	private String coallateBiomassRSStatus(){
		StringBuffer statusBuffer = new StringBuffer();
		if (!myBiomassRS.hasPower())
			statusBuffer.append("needs power, ");
		if (statusBuffer.length() < 1)
			return "nominal";
		else{
			statusBuffer.delete(statusBuffer.length() -2, statusBuffer.length());
			return statusBuffer.toString();
		}
	}
	
	/**
	 * Checks the status of Food Processor and constructs a string describing it.
	 * @return	A String representing the status of the Food Processor 
	 */
	private String coallateFoodProcessorStatus(){
		StringBuffer statusBuffer = new StringBuffer();
		if (!myFoodProcessor.hasPower())
			statusBuffer.append("needs power, ");
		if (!myFoodProcessor.hasBiomass())
			statusBuffer.append("needs biomass, ");
		if (statusBuffer.length() < 1)
			return "nominal";
		else{
			statusBuffer.delete(statusBuffer.length() -2, statusBuffer.length());
			return statusBuffer.toString();
		}
	}
	
	/**
	 * Updates every label on the panel with new data pulled from the servers.
	 */
	public void processUpdate(){
		biomassRSStatusLabel.setText("status:                              "+coallateBiomassRSStatus());
		biomassRSPowerLabel.setText("power consumed:             "+numFormat.format(myBiomassRS.getPowerConsumed())+" W");
		foodProcessorFoodProducedLabel.setText("food produced:          "+numFormat.format(myFoodProcessor.getFoodProduced())+" kg");
		foodProcessorBiomassConsumedLabel.setText("biomass consumed:  "+numFormat.format(myFoodProcessor.getBiomassConsumed())+" kg");
		foodProcessorPowerLabel.setText("power consumed:      "+numFormat.format(myFoodProcessor.getPowerConsumed())+" W");
		foodStoreLevelLabel.setText("food level:    "+numFormat.format(myFoodStore.getLevel())+" kg");
		foodProcessorStatusLabel.setText("status:                       "+coallateFoodProcessorStatus());
	}
}
