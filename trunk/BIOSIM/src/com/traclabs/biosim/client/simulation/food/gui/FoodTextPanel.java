package biosim.client.simulation.food.gui;

import biosim.client.framework.gui.*;
import biosim.client.simulation.framework.*;
import biosim.idl.simulation.food.*;
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

public class FoodTextPanel extends TimedPanel
{
	//Various GUI componenets
	private JPanel biomassRSPanel;
	private JLabel biomassRSStatusLabel;
	private JLabel biomassRSCO2ConsumedLabel;
	private JLabel biomassRSO2ProducedLabel;
	private JLabel biomassRSPowerLabel;
	private JLabel biomassRSGreyWaterLabel;
	private JLabel biomassRSPotableWaterLabel;
	private JLabel biomassRSPlantTypeLabel;
	private JLabel biomassRSTotalAreaLabel;
	private JLabel biomassRSBiomassProducedLabel;
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
	//For formatting floats
	private DecimalFormat numFormat;

	/**
	* Creates and registers this panel.
	* @param pBioSimulator	The Biosimulator this Panel will register itself with.
	*/
	public FoodTextPanel(){
		myBiomassRS = (BiomassRS)(BioHolder.getBioModule(BioHolder.biomassRSName));
		myBiomassStore = (BiomassStore)(BioHolder.getBioModule(BioHolder.biomassStoreName));
		myFoodProcessor = (FoodProcessor)(BioHolder.getBioModule(BioHolder.foodProcessorName));
		myFoodStore = (FoodStore)(BioHolder.getBioModule(BioHolder.foodStoreName));
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
		biomassRSPanel.setLayout(new GridLayout(9,1));
		biomassRSPanel.setBorder(BorderFactory.createTitledBorder("Biomass Recovery System"));
		biomassRSStatusLabel = 		     new JLabel("status:                             "+coallateBiomassRSStatus());
		biomassRSBiomassProducedLabel = new JLabel("biomass produced:          "+numFormat.format(myBiomassRS.getTotalBiomassProduced())+" kg");
		biomassRSCO2ConsumedLabel = new JLabel("CO2 consumed:               "+numFormat.format(myBiomassRS.getTotalCO2Consumed())+" L");
		biomassRSO2ProducedLabel =    new JLabel("O2 produced:                   "+numFormat.format(myBiomassRS.getTotalO2Produced())+" L");
		biomassRSPowerLabel = 		 new JLabel("power consumed:            "+numFormat.format(myBiomassRS.getTotalPowerConsumed())+" W");
		biomassRSGreyWaterLabel = 	 new JLabel("grey water consumed:      "+numFormat.format(myBiomassRS.getTotalGreyWaterConsumed())+" L");
		biomassRSPotableWaterLabel =   new JLabel("potable water consumed: "+numFormat.format(myBiomassRS.getTotalPotableWaterConsumed())+" L");
		biomassRSTotalAreaLabel = 	 new JLabel("total plant area:                "+numFormat.format(myBiomassRS.getTotalPlantArea())+" m^2");
		biomassRSPlantTypeLabel = 	 new JLabel("plant types                       "+coallatePlantTypes());
		biomassRSPanel.add(biomassRSStatusLabel);
		biomassRSPanel.add(biomassRSBiomassProducedLabel);
		biomassRSPanel.add(biomassRSCO2ConsumedLabel);
		biomassRSPanel.add(biomassRSO2ProducedLabel);
		biomassRSPanel.add(biomassRSPowerLabel);
		biomassRSPanel.add(biomassRSGreyWaterLabel);
		biomassRSPanel.add(biomassRSPotableWaterLabel);
		biomassRSPanel.add(biomassRSTotalAreaLabel);
		biomassRSPanel.add(biomassRSPlantTypeLabel);

		biomassStorePanel = new JPanel();
		biomassStorePanel.setLayout(new GridLayout(1,1));
		biomassStorePanel.setBorder(BorderFactory.createTitledBorder("Biomass Store"));
		biomassStoreLevelLabel = new JLabel("biomass level:    "+numFormat.format(myBiomassStore.getLevel())+" kg");
		biomassStorePanel.add(biomassStoreLevelLabel);

		foodProcessorPanel = new JPanel();
		foodProcessorPanel.setLayout(new GridLayout(4,1));
		foodProcessorPanel.setBorder(BorderFactory.createTitledBorder("Food Processor"));
		foodProcessorStatusLabel =                      new JLabel("status:                       "+coallateFoodProcessorStatus());
		foodProcessorFoodProducedLabel =          new JLabel("food produced:          "+numFormat.format(myFoodProcessor.getFoodProduced())+" kg");
		foodProcessorBiomassConsumedLabel =    new JLabel("biomass consumed:    "+numFormat.format(myFoodProcessor.getBiomassConsumed())+" kg");
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
		Shelf[] shelfArray = myBiomassRS.getShelves();
		StringBuffer statusBuffer = new StringBuffer();
		Shelf currentShelf = shelfArray[0];
		if (currentShelf.isDead()){
			return "plants dead";
		}
		if (currentShelf.getBiomassProduced() > 0){
			return "harvesting";
		}
		if (!currentShelf.hasLight()){
			statusBuffer.append("needs light (power), ");
		}
		if (!currentShelf.hasWater()){
			statusBuffer.append("needs water, ");
		}
		if (!currentShelf.hasCO2()){
			statusBuffer.append("needs CO2, ");
		}
		if (statusBuffer.length() < 1)
			return "nominal";
		else{
			statusBuffer.delete(statusBuffer.length() -2, statusBuffer.length());
			return statusBuffer.toString();
		}
	}

	private String coallatePlantTypes(){
		String[] typeArray = myBiomassRS.getPlantTypes();
		StringBuffer typeBuffer = new StringBuffer();
		for (int i = 0; i < typeArray.length; i++){
			typeBuffer.append(typeArray[i]+", ");
		}
		if (typeBuffer.length() < 1)
			return "nominal";
		else{
			typeBuffer.delete(typeBuffer.length() -2, typeBuffer.length());
			return typeBuffer.toString();
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
	public void refresh(){
		biomassRSStatusLabel.setText("status:                              "+coallateBiomassRSStatus());
		foodProcessorFoodProducedLabel.setText("food produced:          "+numFormat.format(myFoodProcessor.getFoodProduced())+" kg");
		foodProcessorBiomassConsumedLabel.setText("biomass consumed:   "+numFormat.format(myFoodProcessor.getBiomassConsumed())+" kg");
		foodProcessorPowerLabel.setText("power consumed:      "+numFormat.format(myFoodProcessor.getPowerConsumed())+" W");
		foodStoreLevelLabel.setText("food level:    "+numFormat.format(myFoodStore.getLevel())+" kg");
		foodProcessorStatusLabel.setText("status:                       "+coallateFoodProcessorStatus());

		biomassRSStatusLabel.setText("status:                             "+coallateBiomassRSStatus());
		biomassRSBiomassProducedLabel.setText("biomass produced:          "+numFormat.format(myBiomassRS.getTotalBiomassProduced())+" kg");
		biomassRSCO2ConsumedLabel.setText("CO2 consumed:               "+numFormat.format(myBiomassRS.getTotalCO2Consumed())+" L");
		biomassRSO2ProducedLabel.setText("O2 produced:                   "+numFormat.format(myBiomassRS.getTotalO2Produced())+" L");
		biomassRSPowerLabel.setText("power consumed:            "+numFormat.format(myBiomassRS.getTotalPowerConsumed())+" W");
		biomassRSGreyWaterLabel.setText("grey water consumed:      "+numFormat.format(myBiomassRS.getTotalGreyWaterConsumed())+" L");
		biomassRSPotableWaterLabel.setText("potable water consumed: "+numFormat.format(myBiomassRS.getTotalPotableWaterConsumed())+" L");
		biomassRSTotalAreaLabel.setText("total plant area:                "+numFormat.format(myBiomassRS.getTotalPlantArea())+" m^2");
		biomassRSPlantTypeLabel.setText("plant types                       "+coallatePlantTypes());
	}
}
