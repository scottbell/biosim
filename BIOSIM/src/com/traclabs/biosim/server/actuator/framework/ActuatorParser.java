package com.traclabs.biosim.server.actuator.framework;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;

/**
 * Reads BioSim configuration from XML file.
 * 
 * @author Scott Bell
 */
public abstract class ActuatorParser {
	private int myID = 0;

	private Logger myLogger;

	/** Default constructor. */
	public ActuatorParser(int pID) {
		myID = pID;
		myLogger = Logger.getLogger(this.getClass());
	}
	/*
	private static int getShelfIndex(Node pNode) {
		return Integer.parseInt(pNode.getAttributes()
				.getNamedItem("shelfIndex").getNodeValue());
	}

	private static String getOutputName(Node pNode) {
		return pNode.getAttributes().getNamedItem("output").getNodeValue();
	}

	private static int getFlowRateIndex(Node pNode) {
		return Integer.parseInt(pNode.getAttributes().getNamedItem("index")
				.getNodeValue());
	}

	private void crawlAirActuators(Node node, boolean firstPass) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			
			boolean createdLocally;
			String moduleName;
			String outputName;
			int flowrateIndex;
			
			if (firstPass){
				createdLocally = BiosimParser.isCreatedLocally(node);
				moduleName = BiosimParser.getModuleName(node);
			}
			else
			{
				moduleName = BiosimParser.getModuleName(node);
				outputName = getOutputName(node);
				flowrateIndex = getFlowRateIndex(node);
			}
			
			if (childName.equals("CO2InFlowRateActuator")) {
				if (firstPass)
					handleCreateCO2InFlowRateActuator(moduleName, createdLocally);
				else
					handleConfigureCO2InFlowRateActuator(moduleName, createdLocally. flowrateIndex);
			} else if (childName.equals("CO2OutFlowRateActuator")) {
				if (firstPass)
					handleCreateCO2OutFlowRateActuator(moduleName, createdLocally);
				else
					handleConfigureCO2OutFlowRateActuator(moduleName, createdLocally. flowrateIndex);
			} else if (childName.equals("O2InFlowRateActuator")) {
				if (firstPass)
					handleCreateO2InFlowRateActuator(moduleName, createdLocally);
				else
					handleConfigureO2InFlowRateActuator(moduleName, createdLocally. flowrateIndex);
			} else if (childName.equals("O2OutFlowRateActuator")) {
				if (firstPass)
					handleCreateO2OutFlowRateActuator(moduleName, createdLocally);
				else
					handleConfigureO2OutFlowRateActuator(moduleName, createdLocally);
			} else if (childName.equals("H2InFlowRateActuator")) {
				if (firstPass)
					handleCreateH2InFlowRateActuator(moduleName, createdLocally. flowrateIndex);
				else
					handleConfigureH2InFlowRateActuator(moduleName, createdLocally);
			} else if (childName.equals("H2OutFlowRateActuator")) {
				if (firstPass)
					handleCreateH2OutFlowRateActuator(moduleName, createdLocally);
				else
					handleConfigureH2OutFlowRateActuator(moduleName, createdLocally. flowrateIndex);
			} else if (childName.equals("NitrogenInFlowRateActuator")) {
				if (firstPass)
					handleCreateNitrogenInFlowRateActuator(moduleName, createdLocally);
				else
					handleConfigureNitrogenInFlowRateActuator(moduleName, createdLocally. flowrateIndex);
			} else if (childName.equals("NitrogenOutFlowRateActuator")) {
				if (firstPass)
					handleCreateNitrogenOutFlowRateActuator(moduleName, createdLocally);
				else
					handleConfigureNitrogenOutFlowRateActuator(moduleName, createdLocally. flowrateIndex);
			} else if (childName.equals("MethaneInFlowRateActuator")) {
				if (firstPass)
					handleCreateMethaneInFlowRateActuator(moduleName, createdLocally);
				else
					handleConfigureMethaneInFlowRateActuator(moduleName, createdLocally. flowrateIndex);
			} else if (childName.equals("MethaneOutFlowRateActuator")) {
				if (firstPass)
					handleCreateMethaneOutFlowRateActuator(moduleName, createdLocally);
				else
					handleConfigureMethaneOutFlowRateActuator(moduleName, createdLocally. flowrateIndex);
			}
			child = child.getNextSibling();
		}
	}

	private void crawlEnvironmentActuators(Node node, boolean firstPass) {
		Node child = node.getFirstChild();
		while (child != null) {
			boolean createdLocally;
			String moduleName;
			String outputName;
			int flowrateIndex;
			
			if (firstPass){
				createdLocally = BiosimParser.isCreatedLocally(node);
				moduleName = BiosimParser.getModuleName(node);
			}
			else
			{
				moduleName = BiosimParser.getModuleName(node);
				outputName = getOutputName(node);
				flowrateIndex = getFlowRateIndex(node);
			}
			
			String childName = child.getNodeName();
			if (childName.equals("AirInFlowRateActuator")) {
				if (firstPass)
					handleCreateAirInFlowRateActuator(moduleName, createdLocally);
				else
					handleConfigureAirInFlowRateActuator(moduleName, createdLocally. flowrateIndex);
			} else if (childName.equals("AirOutFlowRateActuator")) {
				if (firstPass)
					handleCreateAirOutFlowRateActuator(moduleName, createdLocally);
				else
					handleConfigureAirOutFlowRateActuator(moduleName, createdLocally. flowrateIndex);
			}
			child = child.getNextSibling();
		}
	}

	private void crawlFoodActuators(Node node, boolean firstPass) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("BiomassInFlowRateActuator")) {
				if (firstPass)
					handleCreateBiomassInFlowRateActuator(moduleName, createdLocally);
				else
					handleConfigureBiomassInFlowRateActuator(moduleName, createdLocally. flowrateIndex);
			} else if (childName.equals("BiomassOutFlowRateActuator")) {
				if (firstPass)
					handleCreateBiomassOutFlowRateActuator(moduleName, createdLocally);
				else
					handleConfigureBiomassOutFlowRateActuator(moduleName, createdLocally. flowrateIndex);
			} else if (childName.equals("FoodInFlowRateActuator")) {
				if (firstPass)
					handleCreateFoodInFlowRateActuator(moduleName, createdLocally);
				else
					handleConfigureFoodInFlowRateActuator(moduleName, createdLocally. flowrateIndex);
			} else if (childName.equals("FoodOutFlowRateActuator")) {
				if (firstPass)
					handleCreateFoodOutFlowRateActuator(moduleName, createdLocally);
				else
					handleConfigureFoodOutFlowRateActuator(moduleName, createdLocally. flowrateIndex);
			} else if (childName.equals("HarvestingActuator")) {
				if (firstPass)
					handleCreateHarvestingActuator(moduleName, createdLocally);
				else
					handleConfigureHarvestingActuator(moduleName, createdLocally. flowrateIndex);
			} else if (childName.equals("PlantingActuator")) {
				if (firstPass)
					handleCreatePlantingActuator(moduleName, createdLocally);
				else
					handleConfigurePlantingActuator(moduleName, createdLocally. flowrateIndex);
			}
			child = child.getNextSibling();
		}
	}
	
	private void crawlFrameworkActuators(Node node, boolean firstPass) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("InfluentValveActuator")) {
				if (firstPass)
					handleCreateInfluentValveActuator(moduleName, createdLocally);
				else
					handleConfigureInfluentValveActuator(moduleName, createdLocally. flowrateIndex);
			} else if (childName.equals("EffluentValveActuator")) {
				if (firstPass)
					handleCreateEffluentValveActuator(moduleName, createdLocally);
				else
					handleConfigureEffluentValveActuator(moduleName, createdLocally. flowrateIndex);
			}
			child = child.getNextSibling();
		}
	}

	private void crawlPowerActuators(Node node, boolean firstPass) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("PowerInFlowRateActuator")) {
				if (firstPass)
					handleCreatePowerInFlowRateActuator(moduleName, createdLocally);
				else
					handleConfigurePowerInFlowRateActuator(moduleName, createdLocally. flowrateIndex);
			} else if (childName.equals("PowerOutFlowRateActuator")) {
				if (firstPass)
					handleCreatePowerOutFlowRateActuator(moduleName, createdLocally);
				else
					handleConfigurePowerOutFlowRateActuator(moduleName, createdLocally. flowrateIndex);
			}
			child = child.getNextSibling();
		}
	}

	private void crawlWaterActuators(Node node, boolean firstPass) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("PotableWaterInFlowRateActuator")) {
				if (firstPass)
					handleCreatePotableWaterInFlowRateActuator(moduleName, createdLocally);
				else
					handleConfigurePotableWaterInFlowRateActuator(moduleName, createdLocally. flowrateIndex);
			} else if (childName.equals("PotableWaterOutFlowRateActuator")) {
				if (firstPass)
					handleCreatePotableWaterOutFlowRateActuator(moduleName, createdLocally);
				else
					handleConfigurePotableWaterOutFlowRateActuator(moduleName, createdLocally. flowrateIndex);
			} else if (childName.equals("GreyWaterInFlowRateActuator")) {
				if (firstPass)
					handleCreateGreyWaterInFlowRateActuator(moduleName, createdLocally);
				else
					handleConfigureGreyWaterInFlowRateActuator(moduleName, createdLocally. flowrateIndex);
			} else if (childName.equals("GreyWaterOutFlowRateActuator")) {
				if (firstPass)
					handleCreateGreyWaterOutFlowRateActuator(moduleName, createdLocally);
				else
					handleConfigureGreyWaterOutFlowRateActuator(moduleName, createdLocally. flowrateIndex);
			} else if (childName.equals("DirtyWaterInFlowRateActuator")) {
				if (firstPass)
					handleCreateDirtyWaterInFlowRateActuator(moduleName, createdLocally);
				else
					handleConfigureDirtyWaterInFlowRateActuator(moduleName, createdLocally. flowrateIndex);
			} else if (childName.equals("DirtyWaterOutFlowRateActuator")) {
				if (firstPass)
					handleCreateDirtyWaterOutFlowRateActuator(moduleName, createdLocally);
				else
					handleConfigureDirtyWaterOutFlowRateActuator(moduleName, createdLocally. flowrateIndex);
			} else if (childName.equals("WaterInFlowRateActuator")) {
				if (firstPass)
					handleCreateWaterInFlowRateActuator(moduleName, createdLocally);
				else
					handleConfigureWaterInFlowRateActuator(moduleName, createdLocally. flowrateIndex);
			} else if (childName.equals("WaterOutFlowRateActuator")) {
				if (firstPass)
					handleCreateWaterOutFlowRateActuator(moduleName, createdLocally);
				else
					handleConfigureWaterOutFlowRateActuator(moduleName, createdLocally. flowrateIndex);
			}
			child = child.getNextSibling();
		}
	}

	private void crawlWasteActuators(Node node, boolean firstPass) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("DryWasteInFlowRateActuator")) {
				if (firstPass)
					handleCreateDryWasteInFlowRateActuator(moduleName, createdLocally);
				else
					handleConfigureDryWasteInFlowRateActuator(moduleName, createdLocally. flowrateIndex);
			} else if (childName.equals("DryWasteOutFlowRateActuator")) {
				if (firstPass)
					handleCreateDryWasteOutFlowRateActuator(moduleName, createdLocally);
				else
					handleConfigureDryWasteOutFlowRateActuator(moduleName, createdLocally. flowrateIndex);
			}
			child = child.getNextSibling();
		}
	}

	*/
	public void crawlActuators(Node node, boolean firstPass) {
		/*
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("air"))
				crawlAirActuators(child, firstPass);
			else if (childName.equals("environment"))
				crawlEnvironmentActuators(child, firstPass);
			else if (childName.equals("food"))
				crawlFoodActuators(child, firstPass);
			else if (childName.equals("framework"))
				crawlFrameworkActuators(child, firstPass);
			else if (childName.equals("power"))
				crawlPowerActuators(child, firstPass);
			else if (childName.equals("water"))
				crawlWaterActuators(child, firstPass);
			else if (childName.equals("waste"))
				crawlWasteActuators(child, firstPass);
			child = child.getNextSibling();
		}
		*/
	}

}