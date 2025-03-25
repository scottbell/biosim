package com.traclabs.biosim.server.simulation.framework;

import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.omg.CosNaming.NamingContextExt;
import org.w3c.dom.Node;

import com.traclabs.biosim.server.framework.BioModule;
import com.traclabs.biosim.server.framework.BioModuleHelper;
import com.traclabs.biosim.server.simulation.air.CO2Consumer;
import com.traclabs.biosim.server.simulation.air.CO2Producer;
import com.traclabs.biosim.server.simulation.air.CO2Store;
import com.traclabs.biosim.server.simulation.air.CO2StoreHelper;
import com.traclabs.biosim.server.simulation.air.CO2StorePOATie;
import com.traclabs.biosim.server.simulation.air.CRS;
import com.traclabs.biosim.server.simulation.air.CRSHelper;
import com.traclabs.biosim.server.simulation.air.CRSPOATie;
import com.traclabs.biosim.server.simulation.air.H2Consumer;
import com.traclabs.biosim.server.simulation.air.H2Producer;
import com.traclabs.biosim.server.simulation.air.H2Store;
import com.traclabs.biosim.server.simulation.air.H2StoreHelper;
import com.traclabs.biosim.server.simulation.air.H2StorePOATie;
import com.traclabs.biosim.server.simulation.air.MethaneConsumer;
import com.traclabs.biosim.server.simulation.air.MethaneProducer;
import com.traclabs.biosim.server.simulation.air.MethaneStore;
import com.traclabs.biosim.server.simulation.air.MethaneStoreHelper;
import com.traclabs.biosim.server.simulation.air.MethaneStorePOATie;
import com.traclabs.biosim.server.simulation.air.NitrogenConsumer;
import com.traclabs.biosim.server.simulation.air.NitrogenProducer;
import com.traclabs.biosim.server.simulation.air.NitrogenStore;
import com.traclabs.biosim.server.simulation.air.NitrogenStoreHelper;
import com.traclabs.biosim.server.simulation.air.NitrogenStorePOATie;
import com.traclabs.biosim.server.simulation.air.O2Consumer;
import com.traclabs.biosim.server.simulation.air.O2Producer;
import com.traclabs.biosim.server.simulation.air.O2Store;
import com.traclabs.biosim.server.simulation.air.O2StoreHelper;
import com.traclabs.biosim.server.simulation.air.O2StorePOATie;
import com.traclabs.biosim.server.simulation.air.OGS;
import com.traclabs.biosim.server.simulation.air.OGSHelper;
import com.traclabs.biosim.server.simulation.air.OGSPOATie;
import com.traclabs.biosim.server.simulation.air.Pyrolizer;
import com.traclabs.biosim.server.simulation.air.PyrolizerHelper;
import com.traclabs.biosim.server.simulation.air.PyrolizerPOATie;
import com.traclabs.biosim.server.simulation.air.VCCR;
import com.traclabs.biosim.server.simulation.air.VCCRHelper;
import com.traclabs.biosim.server.simulation.air.VCCRPOATie;
import com.traclabs.biosim.server.simulation.air.cdrs.CDRSModule;
import com.traclabs.biosim.server.simulation.air.cdrs.CDRSModuleHelper;
import com.traclabs.biosim.server.simulation.air.cdrs.CDRSModulePOATie;
import com.traclabs.biosim.server.simulation.crew.Activity;
import com.traclabs.biosim.server.simulation.crew.ActivityHelper;
import com.traclabs.biosim.server.simulation.crew.CrewGroup;
import com.traclabs.biosim.server.simulation.crew.CrewGroupHelper;
import com.traclabs.biosim.server.simulation.crew.CrewGroupPOATie;
import com.traclabs.biosim.server.simulation.crew.EVAActivityHelper;
import com.traclabs.biosim.server.simulation.crew.Sex;
import com.traclabs.biosim.server.simulation.environment.AirConsumer;
import com.traclabs.biosim.server.simulation.environment.AirProducer;
import com.traclabs.biosim.server.simulation.environment.Dehumidifier;
import com.traclabs.biosim.server.simulation.environment.DehumidifierHelper;
import com.traclabs.biosim.server.simulation.environment.DehumidifierPOATie;
import com.traclabs.biosim.server.simulation.environment.Fan;
import com.traclabs.biosim.server.simulation.environment.FanHelper;
import com.traclabs.biosim.server.simulation.environment.FanPOATie;
import com.traclabs.biosim.server.simulation.environment.LightConsumer;
import com.traclabs.biosim.server.simulation.environment.SimEnvironment;
import com.traclabs.biosim.server.simulation.environment.SimEnvironmentHelper;
import com.traclabs.biosim.server.simulation.environment.SimEnvironmentPOATie;
import com.traclabs.biosim.server.simulation.food.BioMatter;
import com.traclabs.biosim.server.simulation.food.BiomassConsumer;
import com.traclabs.biosim.server.simulation.food.BiomassPS;
import com.traclabs.biosim.server.simulation.food.BiomassPSHelper;
import com.traclabs.biosim.server.simulation.food.BiomassPSPOATie;
import com.traclabs.biosim.server.simulation.food.BiomassProducer;
import com.traclabs.biosim.server.simulation.food.BiomassStore;
import com.traclabs.biosim.server.simulation.food.BiomassStoreHelper;
import com.traclabs.biosim.server.simulation.food.BiomassStorePOATie;
import com.traclabs.biosim.server.simulation.food.FoodConsumer;
import com.traclabs.biosim.server.simulation.food.FoodMatter;
import com.traclabs.biosim.server.simulation.food.FoodProcessor;
import com.traclabs.biosim.server.simulation.food.FoodProcessorHelper;
import com.traclabs.biosim.server.simulation.food.FoodProcessorPOATie;
import com.traclabs.biosim.server.simulation.food.FoodProducer;
import com.traclabs.biosim.server.simulation.food.FoodStore;
import com.traclabs.biosim.server.simulation.food.FoodStoreHelper;
import com.traclabs.biosim.server.simulation.food.FoodStorePOATie;
import com.traclabs.biosim.server.simulation.food.PlantType;
import com.traclabs.biosim.server.simulation.framework.Accumulator;
import com.traclabs.biosim.server.simulation.framework.AccumulatorHelper;
import com.traclabs.biosim.server.simulation.framework.AccumulatorPOATie;
import com.traclabs.biosim.server.simulation.framework.EffluentValve;
import com.traclabs.biosim.server.simulation.framework.EffluentValveHelper;
import com.traclabs.biosim.server.simulation.framework.EffluentValvePOATie;
import com.traclabs.biosim.server.simulation.framework.InfluentValve;
import com.traclabs.biosim.server.simulation.framework.InfluentValveHelper;
import com.traclabs.biosim.server.simulation.framework.InfluentValvePOATie;
import com.traclabs.biosim.server.simulation.framework.Injector;
import com.traclabs.biosim.server.simulation.framework.InjectorHelper;
import com.traclabs.biosim.server.simulation.framework.InjectorPOATie;
import com.traclabs.biosim.server.simulation.framework.PassiveModule;
import com.traclabs.biosim.server.simulation.framework.SimBioModule;
import com.traclabs.biosim.server.simulation.power.GenericPowerConsumer;
import com.traclabs.biosim.server.simulation.power.GenericPowerConsumerHelper;
import com.traclabs.biosim.server.simulation.power.GenericPowerConsumerPOATie;
import com.traclabs.biosim.server.simulation.power.PowerConsumer;
import com.traclabs.biosim.server.simulation.power.PowerPS;
import com.traclabs.biosim.server.simulation.power.PowerPSHelper;
import com.traclabs.biosim.server.simulation.power.PowerPSPOATie;
import com.traclabs.biosim.server.simulation.power.PowerProducer;
import com.traclabs.biosim.server.simulation.power.PowerStore;
import com.traclabs.biosim.server.simulation.power.PowerStoreHelper;
import com.traclabs.biosim.server.simulation.power.PowerStorePOATie;
import com.traclabs.biosim.server.simulation.power.RPCM;
import com.traclabs.biosim.server.simulation.power.RPCMHelper;
import com.traclabs.biosim.server.simulation.power.RPCMPOATie;
import com.traclabs.biosim.server.simulation.thermal.IATCS;
import com.traclabs.biosim.server.simulation.thermal.IATCSHelper;
import com.traclabs.biosim.server.simulation.thermal.IATCSPOATie;
import com.traclabs.biosim.server.simulation.waste.DryWasteConsumer;
import com.traclabs.biosim.server.simulation.waste.DryWasteProducer;
import com.traclabs.biosim.server.simulation.waste.DryWasteStore;
import com.traclabs.biosim.server.simulation.waste.DryWasteStoreHelper;
import com.traclabs.biosim.server.simulation.waste.DryWasteStorePOATie;
import com.traclabs.biosim.server.simulation.waste.Incinerator;
import com.traclabs.biosim.server.simulation.waste.IncineratorHelper;
import com.traclabs.biosim.server.simulation.waste.IncineratorPOATie;
import com.traclabs.biosim.server.simulation.water.DirtyWaterConsumer;
import com.traclabs.biosim.server.simulation.water.DirtyWaterProducer;
import com.traclabs.biosim.server.simulation.water.DirtyWaterStore;
import com.traclabs.biosim.server.simulation.water.DirtyWaterStoreHelper;
import com.traclabs.biosim.server.simulation.water.DirtyWaterStorePOATie;
import com.traclabs.biosim.server.simulation.water.GreyWaterConsumer;
import com.traclabs.biosim.server.simulation.water.GreyWaterProducer;
import com.traclabs.biosim.server.simulation.water.GreyWaterStore;
import com.traclabs.biosim.server.simulation.water.GreyWaterStoreHelper;
import com.traclabs.biosim.server.simulation.water.GreyWaterStorePOATie;
import com.traclabs.biosim.server.simulation.water.PotableWaterConsumer;
import com.traclabs.biosim.server.simulation.water.PotableWaterProducer;
import com.traclabs.biosim.server.simulation.water.PotableWaterStore;
import com.traclabs.biosim.server.simulation.water.PotableWaterStoreHelper;
import com.traclabs.biosim.server.simulation.water.PotableWaterStorePOATie;
import com.traclabs.biosim.server.simulation.water.WaterRS;
import com.traclabs.biosim.server.simulation.water.WaterRSHelper;
import com.traclabs.biosim.server.simulation.water.WaterRSPOATie;
import com.traclabs.biosim.server.framework.BiosimInitializer;
import com.traclabs.biosim.server.framework.BiosimServer;
import com.traclabs.biosim.server.simulation.air.CO2Store;
import com.traclabs.biosim.server.simulation.air.CRS;
import com.traclabs.biosim.server.simulation.air.H2Store;
import com.traclabs.biosim.server.simulation.air.MethaneStore;
import com.traclabs.biosim.server.simulation.air.NitrogenStore;
import com.traclabs.biosim.server.simulation.air.O2Store;
import com.traclabs.biosim.server.simulation.air.OGS;
import com.traclabs.biosim.server.simulation.air.Pyrolizer;
import com.traclabs.biosim.server.simulation.air.VCCR;
import com.traclabs.biosim.server.simulation.air.VCCRLinear;
import com.traclabs.biosim.server.simulation.air.cdrs.CDRSModule;
import com.traclabs.biosim.server.simulation.crew.Activity;
import com.traclabs.biosim.server.simulation.crew.CrewGroup;
import com.traclabs.biosim.server.simulation.crew.EVAActivity;
import com.traclabs.biosim.server.simulation.crew.Schedule;
import com.traclabs.biosim.server.simulation.environment.Dehumidifier;
import com.traclabs.biosim.server.simulation.environment.Fan;
import com.traclabs.biosim.server.simulation.environment.SimEnvironment;
import com.traclabs.biosim.server.simulation.food.BiomassPS;
import com.traclabs.biosim.server.simulation.food.BiomassStore;
import com.traclabs.biosim.server.simulation.food.FoodProcessor;
import com.traclabs.biosim.server.simulation.food.FoodStore;
import com.traclabs.biosim.server.simulation.power.GenericPowerConsumer;
import com.traclabs.biosim.server.simulation.power.NuclearPowerPS;
import com.traclabs.biosim.server.simulation.power.PowerPS;
import com.traclabs.biosim.server.simulation.power.PowerStore;
import com.traclabs.biosim.server.simulation.power.RPCM;
import com.traclabs.biosim.server.simulation.power.SolarPowerPS;
import com.traclabs.biosim.server.simulation.power.StateMachinePowerPS;
import com.traclabs.biosim.server.simulation.thermal.IATCS;
import com.traclabs.biosim.server.simulation.waste.DryWasteStore;
import com.traclabs.biosim.server.simulation.waste.Incinerator;
import com.traclabs.biosim.server.simulation.water.DirtyWaterStore;
import com.traclabs.biosim.server.simulation.water.GreyWaterStore;
import com.traclabs.biosim.server.simulation.water.PotableWaterStore;
import com.traclabs.biosim.server.simulation.water.WaterRS;
import com.traclabs.biosim.server.simulation.water.WaterRSLinear;
import com.traclabs.biosim.util.OrbUtils;
import com.traclabs.biosim.util.XMLUtils;

/**
 * Reads BioSim configuration from XML file.
 * 
 * @author Scott Bell
 */
public class SimulationInitializer {
	private int myID = 0;

	private List<SimBioModule> myActiveSimModules;

	private List<PassiveModule> myPassiveSimModules;

	private List<SimBioModule> myPrioritySimModules;

	private Logger myLogger;

	/** Default constructor. */
	public SimulationInitializer(int pID) {
		myID = pID;
		myLogger = Logger.getLogger(this.getClass());
		myPassiveSimModules = new Vector<PassiveModule>();
		myActiveSimModules = new Vector<SimBioModule>();
		myPrioritySimModules = new Vector<SimBioModule>();
	}

	public void crawlSimModules(Node node, boolean firstPass) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("air")) {
					crawlAirModules(child, firstPass);
				} else if (childName.equals("crew")) {
					crawlCrewModules(child, firstPass);
				} else if (childName.equals("environment")) {
					crawlEnvironmentModules(child, firstPass);
				} else if (childName.equals("food")) {
					crawlFoodModules(child, firstPass);
				} else if (childName.equals("framework")) {
					crawlFrameworkModules(child, firstPass);
				} else if (childName.equals("power")) {
					crawlPowerModules(child, firstPass);
				} else if (childName.equals("water")) {
					crawlWaterModules(child, firstPass);
				} else if (childName.equals("waste")) {
					crawlWasteModules(child, firstPass);
				} else if (childName.equals("thermal")) {
					crawlThermalModules(child, firstPass);
				}
			}
			child = child.getNextSibling();
		}
	}

	private static float getAttributeFloat(Node node, String attributeName) {
		float number = 0f;
		try {
			number = Float.parseFloat(node.getAttributes().getNamedItem(
					attributeName).getNodeValue());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return number;
	}

	private static boolean[] getSwitchValues(Node node) {
		if (node == null)
			return new boolean[0];
		Node switchValueNode = node.getAttributes()
				.getNamedItem("switchValues");
		if (switchValueNode == null)
			return new boolean[0];
		String arrayString = switchValueNode.getNodeValue();
		String[] tokens = arrayString.split("\\s");
		boolean[] switchValues = new boolean[tokens.length];

		for (int i = 0; i < tokens.length; i++) {
			try {
				switchValues[i] = tokens[i].equals("1");
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return switchValues;
	}

	private static float getStoreLevel(Node node) {
		float level = 0f;
		try {
			level = Float.parseFloat(node.getAttributes().getNamedItem("level")
					.getNodeValue());
		} catch (NumberFormatException e) {

			e.printStackTrace();
		}
		return level;
	}

	private static float getStoreCapacity(Node node) {
		float capacity = 0f;
		try {
			capacity = Float.parseFloat(node.getAttributes().getNamedItem(
					"capacity").getNodeValue());
		} catch (NumberFormatException e) {

			e.printStackTrace();
		}
		return capacity;
	}

	private static int getStoreResupplyFrequency(Node node) {
		int frequency = 0;
		try {
			frequency = Integer.parseInt(node.getAttributes().getNamedItem(
					"resupplyFrequency").getNodeValue());
		} catch (NumberFormatException e) {

			e.printStackTrace();
		}
		return frequency;
	}

	private static float getStoreResupplyAmount(Node node) {
		float amount = 0f;
		try {
			amount = Float.parseFloat(node.getAttributes().getNamedItem(
					"resupplyAmount").getNodeValue());
		} catch (NumberFormatException e) {

			e.printStackTrace();
		}
		return amount;
	}

	private static float[] getMaxFlowRates(Node node) {
		if (node == null)
			return new float[0];
		String arrayString = node.getAttributes().getNamedItem("maxFlowRates")
				.getNodeValue();
		String[] tokens = arrayString.split("\\s");
		float[] maxFlowRates = new float[tokens.length];

		for (int i = 0; i < tokens.length; i++) {
			try {
				maxFlowRates[i] = Float.parseFloat(tokens[i]);
			} catch (NumberFormatException e) {

				e.printStackTrace();
			}
		}
		return maxFlowRates;
	}

	private static float[] getDesiredFlowRates(Node node) {
		if (node == null)
			return new float[0];
		String arrayString = node.getAttributes().getNamedItem(
				"desiredFlowRates").getNodeValue();
		String[] tokens = arrayString.split("\\s");
		float[] desiredFlowRates = new float[tokens.length];

		for (int i = 0; i < tokens.length; i++) {
			try {
				desiredFlowRates[i] = Float.parseFloat(tokens[i]);
			} catch (NumberFormatException e) {

				e.printStackTrace();
			}
		}
		return desiredFlowRates;
	}

	private void configureSimBioModule(SimBioModule pModule, Node node) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("powerConsumer")) {
					PowerConsumer myPowerConsumer = (PowerConsumer) (pModule);
					BioModule[] modules = getInputs(child);
					PowerStore[] inputs = new PowerStore[modules.length];
					for (int i = 0; i < modules.length; i++)
						inputs[i] = PowerStoreHelper.narrow(modules[i]);
					myPowerConsumer.getPowerConsumerDefinition()
							.setPowerInputs(inputs, getMaxFlowRates(child),
									getDesiredFlowRates(child));
				} else if (childName.equals("potableWaterConsumer")) {
					PotableWaterConsumer myPotableWaterConsumer = (PotableWaterConsumer) (pModule);
					BioModule[] modules = getInputs(child);
					PotableWaterStore[] inputs = new PotableWaterStore[modules.length];
					for (int i = 0; i < modules.length; i++)
						inputs[i] = PotableWaterStoreHelper.narrow(modules[i]);
					myPotableWaterConsumer.getPotableWaterConsumerDefinition()
							.setPotableWaterInputs(inputs,
									getMaxFlowRates(child),
									getDesiredFlowRates(child));
				} else if (childName.equals("greyWaterConsumer")) {
					GreyWaterConsumer myGreyWaterConsumer = (GreyWaterConsumer) (pModule);
					BioModule[] modules = getInputs(child);
					GreyWaterStore[] inputs = new GreyWaterStore[modules.length];
					for (int i = 0; i < modules.length; i++)
						inputs[i] = GreyWaterStoreHelper.narrow(modules[i]);
					myGreyWaterConsumer.getGreyWaterConsumerDefinition()
							.setGreyWaterInputs(inputs, getMaxFlowRates(child),
									getDesiredFlowRates(child));
				} else if (childName.equals("dirtyWaterConsumer")) {
					DirtyWaterConsumer myDirtyWaterConsumer = (DirtyWaterConsumer) (pModule);
					BioModule[] modules = getInputs(child);
					DirtyWaterStore[] inputs = new DirtyWaterStore[modules.length];
					for (int i = 0; i < modules.length; i++)
						inputs[i] = DirtyWaterStoreHelper.narrow(modules[i]);
					myDirtyWaterConsumer.getDirtyWaterConsumerDefinition()
							.setDirtyWaterInputs(inputs,
									getMaxFlowRates(child),
									getDesiredFlowRates(child));
				} else if (childName.equals("airConsumer")) {
					AirConsumer myAirConsumer = (AirConsumer) (pModule);
					BioModule[] modules = getInputs(child);
					SimEnvironment[] inputs = new SimEnvironment[modules.length];
					for (int i = 0; i < modules.length; i++)
						inputs[i] = SimEnvironmentHelper.narrow(modules[i]);
					myAirConsumer.getAirConsumerDefinition().setAirInputs(
							inputs, getMaxFlowRates(child),
							getDesiredFlowRates(child));
				} else if (childName.equals("H2Consumer")) {
					H2Consumer myH2Consumer = (H2Consumer) (pModule);
					BioModule[] modules = getInputs(child);
					H2Store[] inputs = new H2Store[modules.length];
					for (int i = 0; i < modules.length; i++)
						inputs[i] = H2StoreHelper.narrow(modules[i]);
					myH2Consumer.getH2ConsumerDefinition().setH2Inputs(inputs,
							getMaxFlowRates(child), getDesiredFlowRates(child));
				} else if (childName.equals("nitrogenConsumer")) {
					NitrogenConsumer myNitrogenConsumer = (NitrogenConsumer) (pModule);
					BioModule[] modules = getInputs(child);
					NitrogenStore[] inputs = new NitrogenStore[modules.length];
					for (int i = 0; i < modules.length; i++) {
						if (modules[i]._is_a(SimEnvironmentHelper.id())) {
							SimEnvironment currentEnvironment = SimEnvironmentHelper
									.narrow(modules[i]);
							inputs[i] = currentEnvironment.getNitrogenStore();
						} else
							inputs[i] = NitrogenStoreHelper.narrow(modules[i]);
					}
					myNitrogenConsumer.getNitrogenConsumerDefinition()
							.setNitrogenInputs(inputs, getMaxFlowRates(child),
									getDesiredFlowRates(child));
				} else if (childName.equals("methaneConsumer")) {
					MethaneConsumer myMethaneConsumer = (MethaneConsumer) (pModule);
					BioModule[] modules = getInputs(child);
					MethaneStore[] inputs = new MethaneStore[modules.length];
					for (int i = 0; i < modules.length; i++)
						inputs[i] = MethaneStoreHelper.narrow(modules[i]);
					myMethaneConsumer.getMethaneConsumerDefinition()
							.setMethaneInputs(inputs, getMaxFlowRates(child),
									getDesiredFlowRates(child));
				} else if (childName.equals("O2Consumer")) {
					O2Consumer myO2Consumer = (O2Consumer) (pModule);
					BioModule[] modules = getInputs(child);
					O2Store[] inputs = new O2Store[modules.length];
					for (int i = 0; i < modules.length; i++) {
						if (modules[i]._is_a(SimEnvironmentHelper.id())) {
							SimEnvironment currentEnvironment = SimEnvironmentHelper
									.narrow(modules[i]);
							inputs[i] = currentEnvironment.getO2Store();
						} else
							inputs[i] = O2StoreHelper.narrow(modules[i]);
					}
					myO2Consumer.getO2ConsumerDefinition().setO2Inputs(inputs,
							getMaxFlowRates(child), getDesiredFlowRates(child));
				} else if (childName.equals("CO2Consumer")) {
					CO2Consumer myCO2Consumer = (CO2Consumer) (pModule);
					BioModule[] modules = getInputs(child);
					CO2Store[] inputs = new CO2Store[modules.length];
					for (int i = 0; i < modules.length; i++) {
						if (modules[i]._is_a(SimEnvironmentHelper.id())) {
							SimEnvironment currentEnvironment = SimEnvironmentHelper
									.narrow(modules[i]);
							inputs[i] = currentEnvironment.getCO2Store();
						} else
							inputs[i] = CO2StoreHelper.narrow(modules[i]);
					}
					myCO2Consumer.getCO2ConsumerDefinition().setCO2Inputs(
							inputs, getMaxFlowRates(child),
							getDesiredFlowRates(child));
				} else if (childName.equals("lightConsumer")) {
					LightConsumer myLightConsumer = (LightConsumer) (pModule);
					BioModule[] modules = getInputs(child);
					SimEnvironment[] inputs = new SimEnvironment[modules.length];
					for (int i = 0; i < modules.length; i++)
						inputs[i] = SimEnvironmentHelper.narrow(modules[i]);
					myLightConsumer.getLightConsumerDefinition().setLightInput(
							inputs[0]);
				} else if (childName.equals("biomassConsumer")) {
					BiomassConsumer myBiomassConsumer = (BiomassConsumer) (pModule);
					BioModule[] modules = getInputs(child);
					BiomassStore[] inputs = new BiomassStore[modules.length];
					for (int i = 0; i < modules.length; i++)
						inputs[i] = BiomassStoreHelper.narrow(modules[i]);
					myBiomassConsumer.getBiomassConsumerDefinition()
							.setBiomassInputs(inputs, getMaxFlowRates(child),
									getDesiredFlowRates(child));
				} else if (childName.equals("dryWasteConsumer")) {
					DryWasteConsumer myDryWasteConsumer = (DryWasteConsumer) (pModule);
					BioModule[] modules = getInputs(child);
					DryWasteStore[] inputs = new DryWasteStore[modules.length];
					for (int i = 0; i < modules.length; i++)
						inputs[i] = DryWasteStoreHelper.narrow(modules[i]);
					myDryWasteConsumer.getDryWasteConsumerDefinition()
							.setDryWasteInputs(inputs, getMaxFlowRates(child),
									getDesiredFlowRates(child));
				} else if (childName.equals("foodConsumer")) {
					FoodConsumer myFoodConsumer = (FoodConsumer) (pModule);
					BioModule[] modules = getInputs(child);
					FoodStore[] inputs = new FoodStore[modules.length];
					for (int i = 0; i < modules.length; i++)
						inputs[i] = FoodStoreHelper.narrow(modules[i]);
					myFoodConsumer.getFoodConsumerDefinition().setFoodInputs(
							inputs, getMaxFlowRates(child),
							getDesiredFlowRates(child));
				} else if (childName.equals("powerProducer")) {
					PowerProducer myPowerProducer = (PowerProducer) (pModule);
					BioModule[] modules = getOutputs(child);
					PowerStore[] outputs = new PowerStore[modules.length];
					for (int i = 0; i < modules.length; i++)
						outputs[i] = PowerStoreHelper.narrow(modules[i]);
					myPowerProducer.getPowerProducerDefinition()
							.setPowerOutputs(outputs, getMaxFlowRates(child),
									getDesiredFlowRates(child));
				} else if (childName.equals("potableWaterProducer")) {
					PotableWaterProducer myPotableWaterProducer = (PotableWaterProducer) (pModule);
					BioModule[] modules = getOutputs(child);
					PotableWaterStore[] outputs = new PotableWaterStore[modules.length];
					for (int i = 0; i < modules.length; i++)
						outputs[i] = PotableWaterStoreHelper.narrow(modules[i]);
					myPotableWaterProducer.getPotableWaterProducerDefinition()
							.setPotableWaterOutputs(outputs,
									getMaxFlowRates(child),
									getDesiredFlowRates(child));
				} else if (childName.equals("greyWaterProducer")) {
					GreyWaterProducer myGreyWaterProducer = (GreyWaterProducer) (pModule);
					BioModule[] modules = getOutputs(child);
					GreyWaterStore[] outputs = new GreyWaterStore[modules.length];
					for (int i = 0; i < modules.length; i++)
						outputs[i] = GreyWaterStoreHelper.narrow(modules[i]);
					myGreyWaterProducer.getGreyWaterProducerDefinition()
							.setGreyWaterOutputs(outputs,
									getMaxFlowRates(child),
									getDesiredFlowRates(child));
				} else if (childName.equals("dirtyWaterProducer")) {
					DirtyWaterProducer myDirtyWaterProducer = (DirtyWaterProducer) (pModule);
					BioModule[] modules = getOutputs(child);
					DirtyWaterStore[] outputs = new DirtyWaterStore[modules.length];
					for (int i = 0; i < modules.length; i++)
						outputs[i] = DirtyWaterStoreHelper.narrow(modules[i]);
					myDirtyWaterProducer.getDirtyWaterProducerDefinition()
							.setDirtyWaterOutputs(outputs,
									getMaxFlowRates(child),
									getDesiredFlowRates(child));
				} else if (childName.equals("airProducer")) {
					AirProducer myAirProducer = (AirProducer) (pModule);
					BioModule[] modules = getOutputs(child);
					SimEnvironment[] outputs = new SimEnvironment[modules.length];
					for (int i = 0; i < modules.length; i++)
						outputs[i] = SimEnvironmentHelper.narrow(modules[i]);
					myAirProducer.getAirProducerDefinition().setAirOutputs(
							outputs, getMaxFlowRates(child),
							getDesiredFlowRates(child));
				} else if (childName.equals("H2Producer")) {
					H2Producer myH2Producer = (H2Producer) (pModule);
					BioModule[] modules = getOutputs(child);
					H2Store[] outputs = new H2Store[modules.length];
					for (int i = 0; i < modules.length; i++)
						outputs[i] = H2StoreHelper.narrow(modules[i]);
					myH2Producer.getH2ProducerDefinition().setH2Outputs(
							outputs, getMaxFlowRates(child),
							getDesiredFlowRates(child));
				} else if (childName.equals("nitrogenProducer")) {
					NitrogenProducer myNitrogenProducer = (NitrogenProducer) (pModule);
					BioModule[] modules = getOutputs(child);
					NitrogenStore[] outputs = new NitrogenStore[modules.length];
					for (int i = 0; i < modules.length; i++) {
						if (modules[i]._is_a(SimEnvironmentHelper.id())) {
							SimEnvironment currentEnvironment = SimEnvironmentHelper
									.narrow(modules[i]);
							outputs[i] = currentEnvironment.getNitrogenStore();
						} else
							outputs[i] = NitrogenStoreHelper.narrow(modules[i]);
					}
					myNitrogenProducer.getNitrogenProducerDefinition()
							.setNitrogenOutputs(outputs,
									getMaxFlowRates(child),
									getDesiredFlowRates(child));
				} else if (childName.equals("methaneProducer")) {
					MethaneProducer myMethaneProducer = (MethaneProducer) (pModule);
					BioModule[] modules = getOutputs(child);
					MethaneStore[] outputs = new MethaneStore[modules.length];
					for (int i = 0; i < modules.length; i++)
						outputs[i] = MethaneStoreHelper.narrow(modules[i]);
					myMethaneProducer.getMethaneProducerDefinition()
							.setMethaneOutputs(outputs, getMaxFlowRates(child),
									getDesiredFlowRates(child));
				} else if (childName.equals("O2Producer")) {
					O2Producer myO2Producer = (O2Producer) (pModule);
					BioModule[] modules = getOutputs(child);
					O2Store[] outputs = new O2Store[modules.length];
					for (int i = 0; i < modules.length; i++) {
						if (modules[i]._is_a(SimEnvironmentHelper.id())) {
							SimEnvironment currentEnvironment = SimEnvironmentHelper
									.narrow(modules[i]);
							outputs[i] = currentEnvironment.getO2Store();
						} else
							outputs[i] = O2StoreHelper.narrow(modules[i]);
					}
					myO2Producer.getO2ProducerDefinition().setO2Outputs(
							outputs, getMaxFlowRates(child),
							getDesiredFlowRates(child));
				} else if (childName.equals("CO2Producer")) {
					CO2Producer myCO2Producer = (CO2Producer) (pModule);
					BioModule[] modules = getOutputs(child);
					CO2Store[] outputs = new CO2Store[modules.length];
					for (int i = 0; i < modules.length; i++) {
						if (modules[i]._is_a(SimEnvironmentHelper.id())) {
							SimEnvironment currentEnvironment = SimEnvironmentHelper
									.narrow(modules[i]);
							outputs[i] = currentEnvironment.getCO2Store();
						} else
							outputs[i] = CO2StoreHelper.narrow(modules[i]);
					}
					myCO2Producer.getCO2ProducerDefinition().setCO2Outputs(
							outputs, getMaxFlowRates(child),
							getDesiredFlowRates(child));
				} else if (childName.equals("biomassProducer")) {
					BiomassProducer myBiomassProducer = (BiomassProducer) (pModule);
					BioModule[] modules = getOutputs(child);
					BiomassStore[] outputs = new BiomassStore[modules.length];
					for (int i = 0; i < modules.length; i++)
						outputs[i] = BiomassStoreHelper.narrow(modules[i]);
					myBiomassProducer.getBiomassProducerDefinition()
							.setBiomassOutputs(outputs, getMaxFlowRates(child),
									getDesiredFlowRates(child));
				} else if (childName.equals("dryWasteProducer")) {
					DryWasteProducer myDryWasteProducer = (DryWasteProducer) (pModule);
					BioModule[] modules = getOutputs(child);
					DryWasteStore[] outputs = new DryWasteStore[modules.length];
					for (int i = 0; i < modules.length; i++)
						outputs[i] = DryWasteStoreHelper.narrow(modules[i]);
					myDryWasteProducer.getDryWasteProducerDefinition()
							.setDryWasteOutputs(outputs,
									getMaxFlowRates(child),
									getDesiredFlowRates(child));
				} else if (childName.equals("foodProducer")) {
					FoodProducer myFoodProducer = (FoodProducer) (pModule);
					BioModule[] modules = getOutputs(child);
					FoodStore[] outputs = new FoodStore[modules.length];
					for (int i = 0; i < modules.length; i++)
						outputs[i] = FoodStoreHelper.narrow(modules[i]);
					myFoodProducer.getFoodProducerDefinition().setFoodOutputs(
							outputs, getMaxFlowRates(child),
							getDesiredFlowRates(child));
				}
			}
			child = child.getNextSibling();
		}
	}

	private static Node getEnvironmentNode(Node node) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.startsWith("environment"))
					return child;
			}
			child = child.getNextSibling();
		}
		return null;
	}

	private static Node getStoreNode(Node node) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.startsWith("store"))
					return child;
			}
			child = child.getNextSibling();
		}
		return null;
	}

	private BioModule[] getInputs(Node node) {
		if (node == null)
			return new BioModule[0];
		String arrayString = node.getAttributes().getNamedItem("inputs")
				.getNodeValue();
		String[] inputNames = arrayString.split("\\s");
		BioModule[] inputs = new BioModule[inputNames.length];
		for (int i = 0; i < inputs.length; i++) {
			try {
				String inputName = inputNames[i];
				NamingContextExt inputNamingContext = OrbUtils.getNamingContext(myID);
				org.omg.CORBA.Object rawInputObject = inputNamingContext.resolve_str(inputName);
				BioModule inputModule = BioModuleHelper.unchecked_narrow(rawInputObject);
				inputs[i] = (inputModule);
				myLogger.debug("Fetched " + inputs[i].getModuleName());
			} catch (org.omg.CORBA.UserException e) {
				myLogger.error("Couldn't find module " + inputNames[i]
						+ " referenced for input in configuration file");
				e.printStackTrace();
			}
		}
		return inputs;
	}

	private BioModule[] getOutputs(Node node) {
		if (node == null)
			return new BioModule[0];
		String arrayString = node.getAttributes().getNamedItem("outputs")
				.getNodeValue();
		String[] outputNames = arrayString.split("\\s");
		BioModule[] outputs = new BioModule[outputNames.length];
		for (int i = 0; i < outputs.length; i++) {
			try {
				outputs[i] = BioModuleHelper.unchecked_narrow(OrbUtils.getNamingContext(
						myID).resolve_str(outputNames[i]));
				myLogger.debug("Fetched " + outputs[i].getModuleName());
			} catch (org.omg.CORBA.UserException e) {
				myLogger.error("Couldn't find module " + outputNames[i]
						+ " referenced for output in configuration file");

				e.printStackTrace();
			}
		}
		return outputs;
	}

	private void setupStore(Store pStore, Node pNode) {
		pStore.setInitialCapacity(getStoreCapacity(pNode));
		pStore.setInitialLevel(getStoreLevel(pNode));
		pStore.setResupply(getStoreResupplyFrequency(pNode),
				getStoreResupplyAmount(pNode));
		BiosimInitializer.setupBioModule(pStore, pNode);
	}

	/**
	 * @param child
	 */
	private void createVCCR(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating VCCR with moduleName: " + moduleName);
			String implementationString = node.getAttributes().getNamedItem(
					"implementation").getNodeValue();
			if (implementationString.equals("LINEAR")) {
				myLogger.debug("created linear VCCR...");
				VCCRLinear myVCCR = new VCCRLinear(myID, moduleName);
				BiosimInitializer.setupBioModule(myVCCR, node);
				BiosimServer.registerServer(new VCCRPOATie(myVCCR),
						myVCCR.getModuleName(), myVCCR.getID());
			} else {
				VCCR myVCCR = new VCCR(myID, moduleName);
				BiosimInitializer.setupBioModule(myVCCR, node);
				BiosimServer.registerServer(new VCCRPOATie(myVCCR),
						myVCCR.getModuleName(), myVCCR.getID());
			}
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);

	}

	/**
	 * @param child
	 */
	private void configureVCCR(Node node) {
		VCCR myVCCR = VCCRHelper.narrow(BiosimInitializer.getModule(myID,
				BiosimInitializer.getModuleName(node)));
		configureSimBioModule(myVCCR, node);
		myActiveSimModules.add(myVCCR);

	}

	/**
	 * @param child
	 */
	private void createCRS(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating CRS with moduleName: " + moduleName);
			CRS myCRS = new CRS(myID, moduleName);
			BiosimInitializer.setupBioModule(myCRS, node);
			BiosimServer.registerServer(new CRSPOATie(myCRS), myCRS
					.getModuleName(), myCRS.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);

	}

	/**
	 * @param child
	 */
	private void configureCRS(Node node) {
		CRS myCRS = CRSHelper.narrow(BiosimInitializer.getModule(myID,
				BiosimInitializer.getModuleName(node)));
		configureSimBioModule(myCRS, node);
		myActiveSimModules.add(myCRS);

	}

	/**
	 * @param child
	 */
	private void createCDRS(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating CDRS with moduleName: " + moduleName);
			CDRSModule myCDRS = new CDRSModule(myID, moduleName);
			BiosimInitializer.setupBioModule(myCDRS, node);
			BiosimServer.registerServer(new CDRSModulePOATie(myCDRS), myCDRS
					.getModuleName(), myCDRS.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);

	}

	/**
	 * @param child
	 */
	private void configureCDRS(Node node) {
		CDRSModule myCDRS = CDRSModuleHelper.narrow(BiosimInitializer.getModule(myID,
				BiosimInitializer.getModuleName(node)));
		configureSimBioModule(myCDRS, node);
		myActiveSimModules.add(myCDRS);

	}

	/**
	 * @param child
	 */
	private void createOGS(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating OGS with moduleName: " + moduleName);
			OGS myOGS = new OGS(myID, moduleName);
			BiosimInitializer.setupBioModule(myOGS, node);
			BiosimServer.registerServer(new OGSPOATie(myOGS), myOGS
					.getModuleName(), myOGS.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);

	}

	/**
	 * @param child
	 */
	private void configureOGS(Node node) {
		OGS myOGS = OGSHelper.narrow(BiosimInitializer.getModule(myID,
				BiosimInitializer.getModuleName(node)));
		configureSimBioModule(myOGS, node);
		myLogger.debug("Configuring OGS");
		myActiveSimModules.add(myOGS);

	}
	


	/**
	 * @param child
	 */
	private void createPyrolizer(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating Pyrolizer with moduleName: " + moduleName);
			Pyrolizer myPyrolizer = new Pyrolizer(myID, moduleName);
			BiosimInitializer.setupBioModule(myPyrolizer, node);
			BiosimServer.registerServer(new PyrolizerPOATie(myPyrolizer), myPyrolizer
					.getModuleName(), myPyrolizer.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);

	}

	/**
	 * @param child
	 */
	private void configurePyrolizer(Node node) {
		Pyrolizer myPyrolizer = PyrolizerHelper.narrow(BiosimInitializer.getModule(myID,
				BiosimInitializer.getModuleName(node)));
		configureSimBioModule(myPyrolizer, node);
		myLogger.debug("Configuring Pyrolizer");
		myActiveSimModules.add(myPyrolizer);

	}

	private void createO2Store(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			O2Store myO2Store = new O2Store(myID, moduleName);
			setupStore(myO2Store, node);
			BiosimServer.registerServer(new O2StorePOATie(myO2Store),
					myO2Store.getModuleName(), myO2Store.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void createCO2Store(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			CO2Store myCO2Store = new CO2Store(myID, moduleName);
			setupStore(myCO2Store, node);
			BiosimServer.registerServer(new CO2StorePOATie(myCO2Store),
					myCO2Store.getModuleName(), myCO2Store.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void createH2Store(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			H2Store myH2Store = new H2Store(myID, moduleName);
			setupStore(myH2Store, node);
			BiosimServer.registerServer(new H2StorePOATie(myH2Store),
					myH2Store.getModuleName(), myH2Store.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void createNitrogenStore(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			NitrogenStore myNitrogenStore = new NitrogenStore(myID,
					moduleName);
			setupStore(myNitrogenStore, node);
			BiosimServer.registerServer(new NitrogenStorePOATie(
					myNitrogenStore), myNitrogenStore.getModuleName(),
					myNitrogenStore.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void createMethaneStore(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			MethaneStore myMethaneStore = new MethaneStore(myID,
					moduleName);
			setupStore(myMethaneStore, node);
			BiosimServer.registerServer(new MethaneStorePOATie(
					myMethaneStore), myMethaneStore.getModuleName(),
					myMethaneStore.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void crawlAirModules(Node node, boolean firstPass) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("OGS")) {
					if (firstPass)
						createOGS(child);
					else
						configureOGS(child);
				} else if (childName.equals("CRS")) {
					if (firstPass)
						createCRS(child);
					else
						configureCRS(child);
				} else if (childName.equals("Pyrolizer")) {
					if (firstPass)
						createPyrolizer(child);
					else
						configurePyrolizer(child);
				} else if (childName.equals("VCCR")) {
					if (firstPass)
						createVCCR(child);
					else
						configureVCCR(child);
				} else if (childName.equals("CDRS")) {
					if (firstPass)
						createCDRS(child);
					else
						configureCDRS(child);
				}  else if (childName.equals("O2Store")) {
					if (firstPass)
						createO2Store(child);
					else
						myPassiveSimModules
								.add(O2StoreHelper.narrow(BiosimInitializer
										.getModule(myID, BiosimInitializer
												.getModuleName(child))));
				} else if (childName.equals("CO2Store")) {
					if (firstPass)
						createCO2Store(child);
					else
						myPassiveSimModules
								.add(CO2StoreHelper.narrow(BiosimInitializer
										.getModule(myID, BiosimInitializer
												.getModuleName(child))));

				} else if (childName.equals("H2Store")) {
					if (firstPass)
						createH2Store(child);
					else
						myPassiveSimModules
								.add(H2StoreHelper.narrow(BiosimInitializer
										.getModule(myID, BiosimInitializer
												.getModuleName(child))));

				} else if (childName.equals("NitrogenStore")) {
					if (firstPass)
						createNitrogenStore(child);
					else
						myPassiveSimModules
								.add(NitrogenStoreHelper
										.narrow(BiosimInitializer.getModule(
												myID, BiosimInitializer
														.getModuleName(child))));

				} else if (childName.equals("MethaneStore")) {
					if (firstPass)
						createMethaneStore(child);
					else
						myPassiveSimModules
								.add(MethaneStoreHelper
										.narrow(BiosimInitializer.getModule(
												myID, BiosimInitializer
														.getModuleName(child))));

				}
			}
			child = child.getNextSibling();
		}
	}

	private Activity createActivity(Node node, CrewGroup crew) {
		int length = 0;
		int intensity = 0;
		try {
			length = Integer.parseInt(node.getAttributes().getNamedItem(
					"length").getNodeValue());
			intensity = Integer.parseInt(node.getAttributes().getNamedItem(
					"intensity").getNodeValue());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		String name = node.getAttributes().getNamedItem("name").getNodeValue();
		Node activityTypeNode = node.getAttributes().getNamedItem("xsi:type");
		if (activityTypeNode != null) {
			if (activityTypeNode.getNodeValue().equals("EVAActivityType")) {
				myLogger.debug("Type is " + activityTypeNode.getNodeValue());
				String evaCrewGroupName = node.getAttributes().getNamedItem(
						"evaCrewGroup").getNodeValue();
				EVAActivity newEVAActivity = new EVAActivity(name,
						length, intensity, crew.getModuleName(),
						evaCrewGroupName);
				return EVAActivityHelper.narrow(ActivityHelper.narrow(OrbUtils
						.poaToCorbaObj(newEVAActivity)));
			}
			myLogger
					.error("Activity not of expected type even though it was explicitly declared! (can only be EVA type right now)");
			myLogger.error("type was: " + activityTypeNode.getNodeValue()
					+ ", should be: EVAActivityType");
			return null;
		}
		Activity newActivity = new Activity(name, length, intensity);
		return ActivityHelper.narrow(OrbUtils.poaToCorbaObj(newActivity));
	}

	private Schedule createSchedule(Node node, CrewGroup crew) {
		Schedule newSchedule = new Schedule(crew);
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (child.getLocalName().equals("activity")) {
					Activity newActivity = createActivity(child, crew);
					newSchedule.insertActivityInSchedule(newActivity);
				}
			}
			child = child.getNextSibling();
		}
		return newSchedule;
	}

	private void createCrewPerson(Node node, CrewGroup crewGroup, CrewGroup crewGroup) {
		Node child = node.getFirstChild();
		Schedule schedule = null;
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("schedule"))
					schedule = createSchedule(node.getFirstChild()
							.getNextSibling(), crewGroup);
			}
			child = child.getNextSibling();
		}
		String implementation = node.getAttributes().getNamedItem("implementation").getNodeValue();
		String name = node.getAttributes().getNamedItem("name").getNodeValue();
		Sex sex;
		if (node.getAttributes().getNamedItem("sex").getNodeValue().equals(
				"FEMALE"))
			sex = Sex.female;
		else
			sex = Sex.male;
		float age = 0f;
		float weight = 0f;
		int arrivalDate = 0;
		int departureDate = 0;
		try {
			age = Float.parseFloat(node.getAttributes().getNamedItem("age")
					.getNodeValue());
			weight = Float.parseFloat(node.getAttributes().getNamedItem(
					"weight").getNodeValue());
			arrivalDate = Integer.parseInt(node.getAttributes().getNamedItem(
					"arrivalDate").getNodeValue());
			departureDate = Integer.parseInt(node.getAttributes().getNamedItem(
					"departureDate").getNodeValue());
			if (departureDate < 0)
				departureDate = Integer.MAX_VALUE;
		} catch (NumberFormatException e) {

			e.printStackTrace();
		}
		crewGroup.createCrewPerson(implementation, name, age, weight, sex, arrivalDate,
				departureDate, schedule);
		crewGroup.getCrewPerson(name).setLogLevel(
				BiosimInitializer.getLogLevel(node));
	}

	private void createCrewGroup(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating CrewGroup with moduleName: " + moduleName);
			CrewGroup myCrewGroup = new CrewGroup(myID, moduleName);
			BiosimInitializer.setupBioModule(myCrewGroup, node);
			BiosimServer.registerServer(new CrewGroupPOATie(myCrewGroup),
					myCrewGroup.getModuleName(), myCrewGroup.getID());
			boolean deathEnabled = XMLUtils.getBooelanAttribute(node,
					"isDeathEnabled");
			myCrewGroup.setDeathEnabled(deathEnabled);
			// Create crew members
			CrewGroup myCrewGroup = CrewGroupHelper.narrow(BiosimInitializer
					.getModule(myID, myCrewGroup.getModuleName()));
			Node child = node.getFirstChild();
			while (child != null) {
				String childName = child.getLocalName();
				if (childName != null) {
					if (childName.equals("crewPerson"))
						createCrewPerson(child, myCrewGroup, myCrewGroup);
				}
				child = child.getNextSibling();
			}
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureCrewGroup(Node node) {
		CrewGroup myCrewGroup = CrewGroupHelper.narrow(BiosimInitializer
				.getModule(myID, BiosimInitializer.getModuleName(node)));
		configureSimBioModule(myCrewGroup, node);
		myActiveSimModules.add(myCrewGroup);
	}

	private void crawlCrewModules(Node node, boolean firstPass) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("CrewGroup")) {
					if (firstPass)
						createCrewGroup(child);
					else
						configureCrewGroup(child);
				}
			}
			child = child.getNextSibling();
		}
	}

	private void createSimEnvironment(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating SimEnvironment with moduleName: "
					+ moduleName);
			SimEnvironment newSimEnvironment = null;
			float CO2Moles = 0f;
			float O2Moles = 0f;
			float waterMoles = 0f;
			float otherMoles = 0f;
			float nitrogenMoles = 0f;
			float volume = 0f;
			float leakRate = 0f;
			float dayLength = 0f;
			float hourOfDayStart = 0f;
			float maxLumens = 0f;
			float airlockVolume = 0f;
			float dangerousOxygenThreshold = 0f;
			Node CO2MolesNode = null;
			Node O2MolesNode = null;
			Node waterMolesNode = null;
			Node otherMolesNode = null;
			Node nitrogenMolesNode = null;
			try {
				volume = Float.parseFloat(node.getAttributes().getNamedItem(
						"initialVolume").getNodeValue());
				/*
				
						*/
				leakRate = Float.parseFloat(node.getAttributes().getNamedItem(
						"leakRate").getNodeValue());
				dayLength = Float.parseFloat(node.getAttributes().getNamedItem(
						"dayLength").getNodeValue());
				hourOfDayStart = Float.parseFloat(node.getAttributes()
						.getNamedItem("hourOfDayStart").getNodeValue());
				maxLumens = Float.parseFloat(node.getAttributes().getNamedItem(
						"maxLumens").getNodeValue());
				airlockVolume = Float.parseFloat(node.getAttributes()
						.getNamedItem("airlockVolume").getNodeValue());
				dangerousOxygenThreshold = Float.parseFloat(node
						.getAttributes().getNamedItem(
								"dangerousOxygenThreshold").getNodeValue());
			} catch (NumberFormatException e) {

				e.printStackTrace();
			}
			if (creatingEnvironmentWithMoles(node)){
				myLogger.debug("Creating environment with moles");
				newSimEnvironment = createEnvironmentWithMoles(node, myID, volume, moduleName);
			}
			else if (creatingEnvironmentWithPercentages(node)){
				myLogger.debug("Creating environment with percentages");
				newSimEnvironment = createEnvironmentWithPercentages(node, myID, volume, moduleName);
			}
			else{
				myLogger.debug("Creating environment with defaults");
				newSimEnvironment = new SimEnvironment(myID, volume,
						moduleName);
			}
			newSimEnvironment.setLeakRate(leakRate);
			newSimEnvironment.setDayLength(dayLength);
			newSimEnvironment.setHourOfDayStart(hourOfDayStart);
			newSimEnvironment.setMaxLumens(maxLumens);
			newSimEnvironment.setAirlockVolume(airlockVolume);
			newSimEnvironment
					.setDangerousOxygenThreshold(dangerousOxygenThreshold);
			BiosimInitializer.setupBioModule(newSimEnvironment, node);
			BiosimServer.registerServer(new SimEnvironmentPOATie(
					newSimEnvironment),
					newSimEnvironment.getModuleName(), newSimEnvironment
							.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private boolean creatingEnvironmentWithPercentages(Node node) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("percentageInitialization")) {
					return true;
				}
			}
			child = child.getNextSibling();
		}
		return false;
	}

	private boolean creatingEnvironmentWithMoles(Node node) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("moleInitialization")) {
					return true;
				}
			}
			child = child.getNextSibling();
		}
		return false;
	}

	private SimEnvironment createEnvironmentWithPercentages(Node node, int pID, float pVolume, String pName) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("percentageInitialization")) {
					float co2Percentage = Float.parseFloat(child.getAttributes().getNamedItem("co2Percentage").getNodeValue());
					float o2Percentage = Float.parseFloat(child.getAttributes().getNamedItem("o2Percentage").getNodeValue());
					float waterPercentage = Float.parseFloat(child.getAttributes().getNamedItem("waterPercentage").getNodeValue());
					float otherPercentage = Float.parseFloat(child.getAttributes().getNamedItem("otherPercentage").getNodeValue());
					float nitrogenPercentage = Float.parseFloat(child.getAttributes().getNamedItem("nitrogenPercentage").getNodeValue());
					float totalPressure = Float.parseFloat(child.getAttributes().getNamedItem("totalPressure").getNodeValue());
					SimEnvironment newSimEnvironment = new SimEnvironment(pID, pName, pVolume, totalPressure, o2Percentage, co2Percentage, otherPercentage, waterPercentage, nitrogenPercentage);
					return newSimEnvironment;
				}
			}
			child = child.getNextSibling();
		}
		return null;
	}

	private SimEnvironment createEnvironmentWithMoles(Node node, int pID, float pVolume, String pName) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("moleInitialization")) {
					float CO2Moles = Float.parseFloat(child.getAttributes().getNamedItem("initialCO2Moles").getNodeValue());
					float O2Moles = Float.parseFloat(child.getAttributes().getNamedItem("initialO2Moles").getNodeValue());
					float waterMoles = Float.parseFloat(child.getAttributes().getNamedItem("initialWaterMoles").getNodeValue());
					float otherMoles = Float.parseFloat(child.getAttributes().getNamedItem("initialOtherMoles").getNodeValue());
					float nitrogenMoles = Float.parseFloat(child.getAttributes().getNamedItem("initialNitrogenMoles").getNodeValue());
					SimEnvironment newSimEnvironment = new SimEnvironment(O2Moles, CO2Moles, otherMoles, waterMoles, nitrogenMoles, pVolume, pName, pID);
					return newSimEnvironment;
				}
			}
			child = child.getNextSibling();
		}
		return null;
	}

	private void createDehumidifier(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating Dehumidifier with moduleName: "
					+ moduleName);
			Dehumidifier myDehumidifier = new Dehumidifier(myID,
					moduleName);
			BiosimInitializer.setupBioModule(myDehumidifier, node);
			BiosimServer.registerServer(new DehumidifierPOATie(
					myDehumidifier), myDehumidifier.getModuleName(),
					myDehumidifier.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureDehumidifier(Node node) {
		Dehumidifier myDehumidifier = DehumidifierHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		configureSimBioModule(myDehumidifier, node);
		myPrioritySimModules.add(myDehumidifier);
	}

	private void createFan(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating Fan with moduleName: "
					+ moduleName);
			Fan myFan = new Fan(myID,
					moduleName);
			BiosimInitializer.setupBioModule(myFan, node);
			BiosimServer.registerServer(new FanPOATie(
					myFan), myFan.getModuleName(),
					myFan.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureFan(Node node) {
		Fan myFan = FanHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		configureSimBioModule(myFan, node);
		myPrioritySimModules.add(myFan);
	}

	private void crawlEnvironmentModules(Node node, boolean firstPass) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("SimEnvironment")) {
					if (firstPass)
						createSimEnvironment(child);
					else
						myPassiveSimModules
								.add(SimEnvironmentHelper
										.narrow(BiosimInitializer.getModule(
												myID, BiosimInitializer
														.getModuleName(child))));
				}
				if (childName.equals("Dehumidifier")) {
					if (firstPass)
						createDehumidifier(child);
					else
						configureDehumidifier(child);
				}
				if (childName.equals("Fan")) {
					if (firstPass)
						createFan(child);
					else
						configureFan(child);
				}
			}
			child = child.getNextSibling();
		}
	}

	private void createAccumulator(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating Accumulator with moduleName: "
					+ moduleName);
			Accumulator myAccumulator = new Accumulator(myID,
					moduleName);
			BiosimInitializer.setupBioModule(myAccumulator, node);
			BiosimServer.registerServer(
					new AccumulatorPOATie(myAccumulator), myAccumulator
							.getModuleName(), myAccumulator.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureAccumulator(Node node) {
		Accumulator myAccumulator = AccumulatorHelper.narrow(BiosimInitializer
				.getModule(myID, BiosimInitializer.getModuleName(node)));
		configureSimBioModule(myAccumulator, node);
		myActiveSimModules.add(myAccumulator);
	}

	private void createInjector(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating Injector with moduleName: " + moduleName);
			Injector myInjector = new Injector(myID, moduleName);
			BiosimInitializer.setupBioModule(myInjector, node);
			BiosimServer.registerServer(new InjectorPOATie(myInjector),
					myInjector.getModuleName(), myInjector.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureInjector(Node node) {
		Injector myInjector = InjectorHelper.narrow(BiosimInitializer
				.getModule(myID, BiosimInitializer.getModuleName(node)));
		configureSimBioModule(myInjector, node);
		myActiveSimModules.add(myInjector);
	}

	private void createInfluentValve(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating InfluentValve with moduleName: "
					+ moduleName);
			InfluentValve myInfluentValve = new InfluentValve(myID,
					moduleName);
			BiosimInitializer.setupBioModule(myInfluentValve, node);
			BiosimServer.registerServer(new InfluentValvePOATie(
					myInfluentValve), myInfluentValve.getModuleName(),
					myInfluentValve.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureInfluentValve(Node node) {
		InfluentValve myInfluentValve = InfluentValveHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		configureSimBioModule(myInfluentValve, node);
		myActiveSimModules.add(myInfluentValve);
	}

	private void createEffluentValve(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating EffluentValve with moduleName: "
					+ moduleName);
			EffluentValve myEffluentValve = new EffluentValve(myID,
					moduleName);
			BiosimInitializer.setupBioModule(myEffluentValve, node);
			BiosimServer.registerServer(new EffluentValvePOATie(
					myEffluentValve), myEffluentValve.getModuleName(),
					myEffluentValve.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureEffluentValve(Node node) {
		EffluentValve myEffluentValve = EffluentValveHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		configureSimBioModule(myEffluentValve, node);
		myActiveSimModules.add(myEffluentValve);
	}

	private void crawlFrameworkModules(Node node, boolean firstPass) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("Accumulator")) {
					if (firstPass)
						createAccumulator(child);
					else
						configureAccumulator(child);
				} else if (childName.equals("Injector")) {
					if (firstPass)
						createInjector(child);
					else
						configureInjector(child);
				} else if (childName.equals("InfluentValve")) {
					if (firstPass)
						createInfluentValve(child);
					else
						configureInfluentValve(child);
				} else if (childName.equals("EffluentValve")) {
					if (firstPass)
						createEffluentValve(child);
					else
						configureEffluentValve(child);
				}
			}
			child = child.getNextSibling();
		}
	}

	private static PlantType getCropType(Node node) {
		String cropString = node.getAttributes().getNamedItem("cropType")
				.getNodeValue();
		if (cropString.equals("DRY_BEAN"))
			return PlantType.DRY_BEAN;
		else if (cropString.equals("LETTUCE"))
			return PlantType.LETTUCE;
		else if (cropString.equals("PEANUT"))
			return PlantType.PEANUT;
		else if (cropString.equals("RICE"))
			return PlantType.RICE;
		else if (cropString.equals("SOYBEAN"))
			return PlantType.SOYBEAN;
		else if (cropString.equals("SWEET_POTATO"))
			return PlantType.SWEET_POTATO;
		else if (cropString.equals("TOMATO"))
			return PlantType.TOMATO;
		else if (cropString.equals("WHEAT"))
			return PlantType.WHEAT;
		else if (cropString.equals("WHITE_POTATO"))
			return PlantType.WHITE_POTATO;
		else
			return PlantType.UNKNOWN_PLANT;
	}

	private static float getCropArea(Node node) {
		float area = 0f;
		try {
			area = Float.parseFloat(node.getAttributes().getNamedItem(
					"cropArea").getNodeValue());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return area;
	}

	private static int getCropStartDay(Node node) {
		int startDay = 0;
		try {
			startDay = Integer.parseInt(node.getAttributes().getNamedItem(
					"startTick").getNodeValue());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return startDay;
	}

	private void createBiomassPS(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating BiomassPS with moduleName: " + moduleName);
			BiomassPS myBiomassPS = new BiomassPS(myID, moduleName);
			boolean autoHarvestAndReplant = XMLUtils.getBooelanAttribute(node,
					"autoHarvestAndReplant");
			boolean deathEnabled = XMLUtils.getBooelanAttribute(node,
					"isDeathEnabled");
			myBiomassPS
					.setAutoHarvestAndReplantEnabled(autoHarvestAndReplant);
			myBiomassPS.setDeathEnabled(deathEnabled);
			BiosimInitializer.setupBioModule(myBiomassPS, node);
			Node child = node.getFirstChild();
			while (child != null) {
				String childName = child.getLocalName();
				if (childName != null) {
					if (childName.equals("shelf"))
						myBiomassPS.createNewShelf(getCropType(child),
								getCropArea(child), getCropStartDay(child));
				}
				child = child.getNextSibling();
			}
			BiosimServer.registerServer(new BiomassPSPOATie(myBiomassPS),
					myBiomassPS.getModuleName(), myBiomassPS.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureBiomassPS(Node node) {
		BiomassPS myBiomassPS = BiomassPSHelper.narrow(BiosimInitializer
				.getModule(myID, BiosimInitializer.getModuleName(node)));
		configureSimBioModule(myBiomassPS, node);
		myActiveSimModules.add(myBiomassPS);
	}

	private void createFoodProcessor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating FoodProcessor with moduleName: "
					+ moduleName);
			FoodProcessor myFoodProcessor = new FoodProcessor(myID,
					moduleName);
			BiosimInitializer.setupBioModule(myFoodProcessor, node);
			BiosimServer.registerServer(new FoodProcessorPOATie(
					myFoodProcessor), myFoodProcessor.getModuleName(),
					myFoodProcessor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureFoodProcessor(Node node) {
		FoodProcessor myFoodProcessor = FoodProcessorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		configureSimBioModule(myFoodProcessor, node);
		myActiveSimModules.add(myFoodProcessor);
	}

	private void createBiomassStore(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating BiomassStore with moduleName: "
					+ moduleName);
			BiomassStore myBiomassStore = new BiomassStore(myID,
					moduleName);
			BiosimInitializer.setupBioModule(myBiomassStore, node);
			float inedibleFraction = Float.parseFloat(node.getAttributes()
					.getNamedItem("inedibleFraction").getNodeValue());
			float edibleWaterContent = Float.parseFloat(node.getAttributes()
					.getNamedItem("edibleWaterContent").getNodeValue());
			float inedibleWaterContent = Float.parseFloat(node.getAttributes()
					.getNamedItem("inedibleWaterContent").getNodeValue());
			PlantType theCropType = getCropType(node);
			BioMatter newBioMatter = new BioMatter(getStoreLevel(node),
					inedibleFraction, edibleWaterContent, inedibleWaterContent,
					theCropType);
			myBiomassStore.setInitialCapacity(getStoreCapacity(node));
			myBiomassStore.setInitialBioMatterLevel(newBioMatter);
			myBiomassStore.setResupply(getStoreResupplyFrequency(node),
					getStoreResupplyAmount(node));
			BiosimServer.registerServer(new BiomassStorePOATie(
					myBiomassStore), myBiomassStore.getModuleName(),
					myBiomassStore.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void createFoodStore(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating FoodStore with moduleName: " + moduleName);
			FoodStore myFoodStore = new FoodStore(myID, moduleName);
			BiosimInitializer.setupBioModule(myFoodStore, node);
			float waterContent = Float.parseFloat(node.getAttributes()
					.getNamedItem("waterContent").getNodeValue());
			PlantType theCropType = getCropType(node);
			FoodMatter newFoodMatter = new FoodMatter(getStoreLevel(node),
					waterContent, theCropType);
			myFoodStore.setInitialCapacity(getStoreCapacity(node));
			myFoodStore.setInitialFoodMatterLevel(newFoodMatter);
			myFoodStore.setResupply(getStoreResupplyFrequency(node),
					getStoreResupplyAmount(node));
			BiosimServer.registerServer(new FoodStorePOATie(myFoodStore),
					myFoodStore.getModuleName(), myFoodStore.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void crawlFoodModules(Node node, boolean firstPass) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("BiomassPS")) {
					if (firstPass)
						createBiomassPS(child);
					else
						configureBiomassPS(child);
				} else if (childName.equals("FoodProcessor")) {
					if (firstPass)
						createFoodProcessor(child);
					else
						configureFoodProcessor(child);
				} else if (childName.equals("BiomassStore")) {
					if (firstPass)
						createBiomassStore(child);
					else
						myPassiveSimModules
								.add(BiomassStoreHelper
										.narrow(BiosimInitializer.getModule(
												myID, BiosimInitializer
														.getModuleName(child))));
				} else if (childName.equals("FoodStore")) {
					if (firstPass)
						createFoodStore(child);
					else
						myPassiveSimModules
								.add(FoodStoreHelper.narrow(BiosimInitializer
										.getModule(myID, BiosimInitializer
												.getModuleName(child))));
				}
			}
			child = child.getNextSibling();
		}
	}

	private void createPowerPS(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating PowerPS with moduleName: " + moduleName);
			PowerPS myPowerPS = null;
			String generationType = node.getAttributes().getNamedItem("generationType").getNodeValue();
			if (generationType.equals("SOLAR"))
				myPowerPS = new SolarPowerPS(myID, moduleName);
			else if (generationType.equals("STATE_MACHINE"))
				myPowerPS = new StateMachinePowerPS(myID, moduleName);
			else
				myPowerPS = new NuclearPowerPS(myID, moduleName);
			float upperPowerGeneration = Float.parseFloat(node.getAttributes()
					.getNamedItem("upperPowerGeneration").getNodeValue());
			myPowerPS.setInitialUpperPowerGeneration(upperPowerGeneration);
			BiosimInitializer.setupBioModule(myPowerPS, node);
			BiosimServer.registerServer(new PowerPSPOATie(myPowerPS),
					myPowerPS.getModuleName(), myPowerPS.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configurePowerPS(Node node) {
		PowerPS myPowerPS = PowerPSHelper.narrow(BiosimInitializer.getModule(
				myID, BiosimInitializer.getModuleName(node)));
		configureSimBioModule(myPowerPS, node);
		myActiveSimModules.add(myPowerPS);
	}

	private void createGenericPowerConsumer(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating Generic Power Consumer with moduleName: "
					+ moduleName);
			GenericPowerConsumer myGenericPowerConsumer = null;
			myGenericPowerConsumer = new GenericPowerConsumer(myID,
					moduleName);
			myGenericPowerConsumer.setPowerRequired(getAttributeFloat(node,
					"powerRequired"));
			BiosimInitializer.setupBioModule(myGenericPowerConsumer, node);
			BiosimServer.registerServer(new GenericPowerConsumerPOATie(
					myGenericPowerConsumer), myGenericPowerConsumer
					.getModuleName(), myGenericPowerConsumer.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureGenericPowerConsumer(Node node) {
		GenericPowerConsumer myGenericPowerConsumer = GenericPowerConsumerHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		configureSimBioModule(myGenericPowerConsumer, node);
		myActiveSimModules.add(myGenericPowerConsumer);
	}

	private void createRPCM(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating RPCM with moduleName: " + moduleName);
			RPCM myRPCM = null;
			myRPCM = new RPCM(myID, moduleName);
			BiosimInitializer.setupBioModule(myRPCM, node);
			BiosimServer.registerServer(new RPCMPOATie(myRPCM), myRPCM
					.getModuleName(), myRPCM.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureRPCM(Node node) {
		RPCM myRPCM = RPCMHelper.narrow(BiosimInitializer.getModule(myID,
				BiosimInitializer.getModuleName(node)));
		configureSimBioModule(myRPCM, node);
		myActiveSimModules.add(myRPCM);
	}

	private void createPowerStore(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger
					.debug("Creating PowerStore with moduleName: " + moduleName);
			PowerStore myPowerStore = new PowerStore(myID,
					moduleName);
			setupStore(myPowerStore, node);
			BiosimServer.registerServer(new PowerStorePOATie(myPowerStore),
					myPowerStore.getModuleName(), myPowerStore.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void crawlPowerModules(Node node, boolean firstPass) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("PowerPS")) {
					if (firstPass)
						createPowerPS(child);
					else
						configurePowerPS(child);
				} else if (childName.equals("GenericPowerConsumer")) {
					if (firstPass)
						createGenericPowerConsumer(child);
					else
						configureGenericPowerConsumer(child);
				} else if (childName.equals("RPCM")) {
					if (firstPass)
						createRPCM(child);
					else
						configureRPCM(child);
				} else if (childName.equals("PowerStore")) {
					if (firstPass)
						createPowerStore(child);
					else
						myPassiveSimModules
								.add(PowerStoreHelper.narrow(BiosimInitializer
										.getModule(myID, BiosimInitializer
												.getModuleName(child))));
				}
			}
			child = child.getNextSibling();
		}
	}

	private void createWaterRS(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating WaterRS with moduleName: " + moduleName);
			String implementationString = node.getAttributes().getNamedItem(
					"implementation").getNodeValue();
			if (implementationString.equals("LINEAR")) {
				myLogger.debug("created linear WaterRS...");
				WaterRSLinear myWaterRS = new WaterRSLinear(myID,
						moduleName);
				BiosimInitializer.setupBioModule(myWaterRS, node);
				BiosimServer.registerServer(new WaterRSPOATie(myWaterRS),
						myWaterRS.getModuleName(), myWaterRS.getID());
			} else {
				WaterRS myWaterRS = new WaterRS(myID, moduleName);
				BiosimInitializer.setupBioModule(myWaterRS, node);
				BiosimServer.registerServer(new WaterRSPOATie(myWaterRS),
						myWaterRS.getModuleName(), myWaterRS.getID());
			}
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureWaterRS(Node node) {
		WaterRS myWaterRS = WaterRSHelper.narrow(BiosimInitializer.getModule(
				myID, BiosimInitializer.getModuleName(node)));
		configureSimBioModule(myWaterRS, node);
		/*
		 * String operationModeString = node.getAttributes().getNamedItem(
		 * "operationMode").getNodeValue(); if
		 * (operationModeString.equals("FULL"))
		 * myWaterRS.setOperationMode(WaterRSOperationMode.FULL); else if
		 * (operationModeString.equals("PARTIAL"))
		 * myWaterRS.setOperationMode(WaterRSOperationMode.PARTIAL); else if
		 * (operationModeString.equals("GREY_WATER_ONLY"))
		 * myWaterRS.setOperationMode(WaterRSOperationMode.GREY_WATER_ONLY);
		 * else if (operationModeString.equals("OFF"))
		 * myWaterRS.setOperationMode(WaterRSOperationMode.OFF); else
		 * myLogger.error("WaterRSOperationMode not found!");
		 */
		myActiveSimModules.add(myWaterRS);
	}

	private void createPotableWaterStore(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating PotableWaterStore with moduleName: "
					+ moduleName);
			PotableWaterStore myPotableWaterStore = new PotableWaterStore(
					myID, moduleName);
			setupStore(myPotableWaterStore, node);
			BiosimServer.registerServer(new PotableWaterStorePOATie(
					myPotableWaterStore), myPotableWaterStore
					.getModuleName(), myPotableWaterStore.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void createDirtyWaterStore(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating DirtyWaterStore with moduleName: "
					+ moduleName);
			DirtyWaterStore myDirtyWaterStore = new DirtyWaterStore(
					myID, moduleName);
			setupStore(myDirtyWaterStore, node);
			BiosimServer.registerServer(new DirtyWaterStorePOATie(
					myDirtyWaterStore), myDirtyWaterStore
					.getModuleName(), myDirtyWaterStore.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void createGreyWaterStore(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating GreyWaterStore with moduleName: "
					+ moduleName);
			GreyWaterStore myGreyWaterStore = new GreyWaterStore(
					myID, moduleName);
			setupStore(myGreyWaterStore, node);
			BiosimServer.registerServer(new GreyWaterStorePOATie(
					myGreyWaterStore),
					myGreyWaterStore.getModuleName(), myGreyWaterStore
							.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void crawlWaterModules(Node node, boolean firstPass) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("WaterRS")) {
					if (firstPass)
						createWaterRS(child);
					else
						configureWaterRS(child);
				} else if (childName.equals("PotableWaterStore")) {
					if (firstPass)
						createPotableWaterStore(child);
					else
						myPassiveSimModules
								.add(PotableWaterStoreHelper
										.narrow(BiosimInitializer.getModule(
												myID, BiosimInitializer
														.getModuleName(child))));
				} else if (childName.equals("GreyWaterStore")) {
					if (firstPass)
						createGreyWaterStore(child);
					else
						myPassiveSimModules
								.add(GreyWaterStoreHelper
										.narrow(BiosimInitializer.getModule(
												myID, BiosimInitializer
														.getModuleName(child))));
				} else if (childName.equals("DirtyWaterStore")) {
					if (firstPass)
						createDirtyWaterStore(child);
					else
						myPassiveSimModules
								.add(DirtyWaterStoreHelper
										.narrow(BiosimInitializer.getModule(
												myID, BiosimInitializer
														.getModuleName(child))));
				}
			}
			child = child.getNextSibling();
		}
	}

	private void createIncinerator(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating Incinerator with moduleName: "
					+ moduleName);
			Incinerator myIncinerator = new Incinerator(myID,
					moduleName);
			BiosimInitializer.setupBioModule(myIncinerator, node);
			BiosimServer.registerServer(
					new IncineratorPOATie(myIncinerator), myIncinerator
							.getModuleName(), myIncinerator.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureIncinerator(Node node) {
		Incinerator myIncinerator = IncineratorHelper.narrow(BiosimInitializer
				.getModule(myID, BiosimInitializer.getModuleName(node)));
		configureSimBioModule(myIncinerator, node);
		myActiveSimModules.add(myIncinerator);
	}
	
	private void createIATCS(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating IATCS with moduleName: "
					+ moduleName);
			IATCS myIATCS = new IATCS(myID,
					moduleName);
			BiosimInitializer.setupBioModule(myIATCS, node);
			BiosimServer.registerServer(
					new IATCSPOATie(myIATCS), myIATCS
							.getModuleName(), myIATCS.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureIATCS(Node node) {
		IATCS myIATCS = IATCSHelper.narrow(BiosimInitializer
				.getModule(myID, BiosimInitializer.getModuleName(node)));
		configureSimBioModule(myIATCS, node);
		myActiveSimModules.add(myIATCS);
	}

	private void createDryWasteStore(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating DryWasteStore with moduleName: "
					+ moduleName);
			DryWasteStore myDryWasteStore = new DryWasteStore(myID,
					moduleName);
			setupStore(myDryWasteStore, node);
			BiosimServer.registerServer(new DryWasteStorePOATie(
					myDryWasteStore), myDryWasteStore.getModuleName(),
					myDryWasteStore.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void crawlWasteModules(Node node, boolean firstPass) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("Incinerator")) {
					if (firstPass)
						createIncinerator(child);
					else
						configureIncinerator(child);
				} else if (childName.equals("DryWasteStore")) {
					if (firstPass)
						createDryWasteStore(child);
					else
						myPassiveSimModules
								.add(DryWasteStoreHelper
										.narrow(BiosimInitializer.getModule(
												myID, BiosimInitializer
														.getModuleName(child))));
				}
			}
			child = child.getNextSibling();
		}
	}
	
	private void crawlThermalModules(Node node, boolean firstPass) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("IATCS")) {
					if (firstPass)
						createIATCS(child);
					else
						configureIATCS(child);
				}
			}
			child = child.getNextSibling();
		}
	}

	/**
	 * @return Returns the myActiveSimModules.
	 */
	public List<SimBioModule> getActiveSimModules() {
		return myActiveSimModules;
	}

	/**
	 * @return Returns the myPassiveSimModules.
	 */
	public List<PassiveModule> getPassiveSimModules() {
		return myPassiveSimModules;
	}

	/**
	 * @return Returns the myPrioritySimModules.
	 */
	public List<SimBioModule> getPrioritySimModules() {
		return myPrioritySimModules;
	}
}