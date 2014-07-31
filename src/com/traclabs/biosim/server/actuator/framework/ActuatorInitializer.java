package com.traclabs.biosim.server.actuator.framework;

import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;

import com.traclabs.biosim.idl.actuator.air.CO2InFlowRateActuator;
import com.traclabs.biosim.idl.actuator.air.CO2InFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.air.CO2InFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.air.CO2OutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.air.CO2OutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.air.CO2OutFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.air.H2InFlowRateActuator;
import com.traclabs.biosim.idl.actuator.air.H2InFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.air.H2InFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.air.H2OutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.air.H2OutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.air.H2OutFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.air.MethaneInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.air.MethaneInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.air.MethaneInFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.air.MethaneOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.air.MethaneOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.air.MethaneOutFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.air.NitrogenInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.air.NitrogenInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.air.NitrogenInFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.air.NitrogenOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.air.NitrogenOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.air.NitrogenOutFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.air.O2InFlowRateActuator;
import com.traclabs.biosim.idl.actuator.air.O2InFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.air.O2InFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.air.O2OutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.air.O2OutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.air.O2OutFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.environment.AirInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.environment.AirInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.AirInFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.environment.AirOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.environment.AirOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.AirOutFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.food.BiomassInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.food.BiomassInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.food.BiomassInFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.food.BiomassOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.food.BiomassOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.food.BiomassOutFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.food.FoodInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.food.FoodInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.food.FoodInFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.food.FoodOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.food.FoodOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.food.FoodOutFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.food.HarvestingActuator;
import com.traclabs.biosim.idl.actuator.food.HarvestingActuatorHelper;
import com.traclabs.biosim.idl.actuator.food.HarvestingActuatorPOATie;
import com.traclabs.biosim.idl.actuator.food.PlantingActuator;
import com.traclabs.biosim.idl.actuator.food.PlantingActuatorHelper;
import com.traclabs.biosim.idl.actuator.food.PlantingActuatorPOATie;
import com.traclabs.biosim.idl.actuator.framework.EffluentValveActuator;
import com.traclabs.biosim.idl.actuator.framework.EffluentValveActuatorHelper;
import com.traclabs.biosim.idl.actuator.framework.EffluentValveActuatorPOATie;
import com.traclabs.biosim.idl.actuator.framework.GenericActuator;
import com.traclabs.biosim.idl.actuator.framework.InfluentValveActuator;
import com.traclabs.biosim.idl.actuator.framework.InfluentValveActuatorHelper;
import com.traclabs.biosim.idl.actuator.framework.InfluentValveActuatorPOATie;
import com.traclabs.biosim.idl.actuator.power.PowerInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.power.PowerInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.power.PowerInFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.power.PowerOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.power.PowerOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.power.PowerOutFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.waste.DryWasteInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.waste.DryWasteInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.waste.DryWasteInFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.waste.DryWasteOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.waste.DryWasteOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.waste.DryWasteOutFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.water.DirtyWaterInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.water.DirtyWaterInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.water.DirtyWaterInFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.water.DirtyWaterOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.water.DirtyWaterOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.water.DirtyWaterOutFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.water.GreyWaterInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.water.GreyWaterInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.water.GreyWaterInFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.water.GreyWaterOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.water.GreyWaterOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.water.GreyWaterOutFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.water.PotableWaterInFlowRateActuator;
import com.traclabs.biosim.idl.actuator.water.PotableWaterInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.water.PotableWaterInFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.actuator.water.PotableWaterOutFlowRateActuator;
import com.traclabs.biosim.idl.actuator.water.PotableWaterOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.water.PotableWaterOutFlowRateActuatorPOATie;
import com.traclabs.biosim.idl.simulation.air.CO2ConsumerHelper;
import com.traclabs.biosim.idl.simulation.air.CO2ProducerHelper;
import com.traclabs.biosim.idl.simulation.air.H2ConsumerHelper;
import com.traclabs.biosim.idl.simulation.air.H2ProducerHelper;
import com.traclabs.biosim.idl.simulation.air.MethaneConsumerHelper;
import com.traclabs.biosim.idl.simulation.air.MethaneProducerHelper;
import com.traclabs.biosim.idl.simulation.air.NitrogenConsumerHelper;
import com.traclabs.biosim.idl.simulation.air.NitrogenProducerHelper;
import com.traclabs.biosim.idl.simulation.air.O2ConsumerHelper;
import com.traclabs.biosim.idl.simulation.air.O2ProducerHelper;
import com.traclabs.biosim.idl.simulation.environment.AirConsumerHelper;
import com.traclabs.biosim.idl.simulation.environment.AirProducerHelper;
import com.traclabs.biosim.idl.simulation.food.BiomassConsumerHelper;
import com.traclabs.biosim.idl.simulation.food.BiomassPSHelper;
import com.traclabs.biosim.idl.simulation.food.BiomassProducerHelper;
import com.traclabs.biosim.idl.simulation.food.FoodConsumerHelper;
import com.traclabs.biosim.idl.simulation.food.FoodProducerHelper;
import com.traclabs.biosim.idl.simulation.framework.EffluentValveHelper;
import com.traclabs.biosim.idl.simulation.framework.InfluentValveHelper;
import com.traclabs.biosim.idl.simulation.power.PowerConsumerHelper;
import com.traclabs.biosim.idl.simulation.power.PowerProducerHelper;
import com.traclabs.biosim.idl.simulation.waste.DryWasteConsumerHelper;
import com.traclabs.biosim.idl.simulation.waste.DryWasteProducerHelper;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterConsumerHelper;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterProducerHelper;
import com.traclabs.biosim.idl.simulation.water.GreyWaterConsumerHelper;
import com.traclabs.biosim.idl.simulation.water.GreyWaterProducerHelper;
import com.traclabs.biosim.idl.simulation.water.PotableWaterConsumerHelper;
import com.traclabs.biosim.idl.simulation.water.PotableWaterProducerHelper;
import com.traclabs.biosim.server.actuator.air.CO2InFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.air.CO2OutFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.air.H2InFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.air.H2OutFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.air.MethaneInFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.air.MethaneOutFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.air.NitrogenInFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.air.NitrogenOutFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.air.O2InFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.air.O2OutFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.environment.AirInFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.environment.AirOutFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.food.BiomassInFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.food.BiomassOutFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.food.FoodInFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.food.FoodOutFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.food.HarvestingActuatorImpl;
import com.traclabs.biosim.server.actuator.food.PlantingActuatorImpl;
import com.traclabs.biosim.server.actuator.power.PowerInFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.power.PowerOutFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.waste.DryWasteInFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.waste.DryWasteOutFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.water.DirtyWaterInFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.water.DirtyWaterOutFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.water.GreyWaterInFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.water.GreyWaterOutFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.water.PotableWaterInFlowRateActuatorImpl;
import com.traclabs.biosim.server.actuator.water.PotableWaterOutFlowRateActuatorImpl;
import com.traclabs.biosim.server.framework.BiosimInitializer;
import com.traclabs.biosim.server.framework.BiosimServer;

/**
 * Reads BioSim configuration from XML file.
 * 
 * @author Scott Bell
 */
public class ActuatorInitializer {
	private int myID = 0;

	private List<GenericActuator> myActuators;

	private Logger myLogger;

	/** Default constructor. */
	public ActuatorInitializer(int pID) {
		myID = pID;
		myActuators = new Vector<GenericActuator>();
		myLogger = Logger.getLogger(this.getClass());
	}

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

	// Air
	private void createCO2InFlowRateActuator(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating CO2InFlowRateActuator with moduleName: "
					+ moduleName);
			CO2InFlowRateActuatorImpl myCO2InFlowRateActuatorImpl = new CO2InFlowRateActuatorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myCO2InFlowRateActuatorImpl, node);
			BiosimServer.registerServer(new CO2InFlowRateActuatorPOATie(
					myCO2InFlowRateActuatorImpl), myCO2InFlowRateActuatorImpl
					.getModuleName(), myCO2InFlowRateActuatorImpl.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureCO2InFlowRateActuator(Node node) {
		CO2InFlowRateActuator myCO2InFlowRateActuator = CO2InFlowRateActuatorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myCO2InFlowRateActuator.setOutput(
				CO2ConsumerHelper.narrow(BiosimInitializer.getModule(myID,
						getOutputName(node))), getFlowRateIndex(node));
		myActuators.add(myCO2InFlowRateActuator);
	}

	private void createCO2OutFlowRateActuator(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating CO2OutFlowRateActuator with moduleName: "
					+ moduleName);
			CO2OutFlowRateActuatorImpl myCO2OutFlowRateActuatorImpl = new CO2OutFlowRateActuatorImpl(
					myID, moduleName);
			BiosimInitializer
					.setupBioModule(myCO2OutFlowRateActuatorImpl, node);
			BiosimServer.registerServer(new CO2OutFlowRateActuatorPOATie(
					myCO2OutFlowRateActuatorImpl), myCO2OutFlowRateActuatorImpl
					.getModuleName(), myCO2OutFlowRateActuatorImpl.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureCO2OutFlowRateActuator(Node node) {
		CO2OutFlowRateActuator myCO2OutFlowRateActuator = CO2OutFlowRateActuatorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myCO2OutFlowRateActuator.setOutput(
				CO2ProducerHelper.narrow(BiosimInitializer.getModule(myID,
						getOutputName(node))), getFlowRateIndex(node));
		myActuators.add(myCO2OutFlowRateActuator);
	}

	private void createO2InFlowRateActuator(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating O2InFlowRateActuator with moduleName: "
					+ moduleName);
			O2InFlowRateActuatorImpl myO2InFlowRateActuatorImpl = new O2InFlowRateActuatorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myO2InFlowRateActuatorImpl, node);
			BiosimServer.registerServer(new O2InFlowRateActuatorPOATie(
					myO2InFlowRateActuatorImpl), myO2InFlowRateActuatorImpl
					.getModuleName(), myO2InFlowRateActuatorImpl.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureO2InFlowRateActuator(Node node) {
		O2InFlowRateActuator myO2InFlowRateActuator = O2InFlowRateActuatorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myO2InFlowRateActuator.setOutput(
				O2ConsumerHelper.narrow(BiosimInitializer.getModule(myID,
						getOutputName(node))), getFlowRateIndex(node));
		myActuators.add(myO2InFlowRateActuator);
	}

	private void createO2OutFlowRateActuator(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating O2OutFlowRateActuator with moduleName: "
					+ moduleName);
			O2OutFlowRateActuatorImpl myO2OutFlowRateActuatorImpl = new O2OutFlowRateActuatorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myO2OutFlowRateActuatorImpl, node);
			BiosimServer.registerServer(new O2OutFlowRateActuatorPOATie(
					myO2OutFlowRateActuatorImpl), myO2OutFlowRateActuatorImpl
					.getModuleName(), myO2OutFlowRateActuatorImpl.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureO2OutFlowRateActuator(Node node) {
		O2OutFlowRateActuator myO2OutFlowRateActuator = O2OutFlowRateActuatorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myO2OutFlowRateActuator.setOutput(
				O2ProducerHelper.narrow(BiosimInitializer.getModule(myID,
						getOutputName(node))), getFlowRateIndex(node));
		myActuators.add(myO2OutFlowRateActuator);
	}

	private void createH2InFlowRateActuator(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating H2InFlowRateActuator with moduleName: "
					+ moduleName);
			H2InFlowRateActuatorImpl myH2InFlowRateActuatorImpl = new H2InFlowRateActuatorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myH2InFlowRateActuatorImpl, node);
			BiosimServer.registerServer(new H2InFlowRateActuatorPOATie(
					myH2InFlowRateActuatorImpl), myH2InFlowRateActuatorImpl
					.getModuleName(), myH2InFlowRateActuatorImpl.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureH2InFlowRateActuator(Node node) {
		H2InFlowRateActuator myH2InFlowRateActuator = H2InFlowRateActuatorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myH2InFlowRateActuator.setOutput(
				H2ConsumerHelper.narrow(BiosimInitializer.getModule(myID,
						getOutputName(node))), getFlowRateIndex(node));
		myActuators.add(myH2InFlowRateActuator);
	}

	private void createH2OutFlowRateActuator(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating H2OutFlowRateActuator with moduleName: "
					+ moduleName);
			H2OutFlowRateActuatorImpl myH2OutFlowRateActuatorImpl = new H2OutFlowRateActuatorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myH2OutFlowRateActuatorImpl, node);
			BiosimServer.registerServer(new H2OutFlowRateActuatorPOATie(
					myH2OutFlowRateActuatorImpl), myH2OutFlowRateActuatorImpl
					.getModuleName(), myH2OutFlowRateActuatorImpl.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureH2OutFlowRateActuator(Node node) {
		H2OutFlowRateActuator myH2OutFlowRateActuator = H2OutFlowRateActuatorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myH2OutFlowRateActuator.setOutput(
				H2ProducerHelper.narrow(BiosimInitializer.getModule(myID,
						getOutputName(node))), getFlowRateIndex(node));
		myActuators.add(myH2OutFlowRateActuator);
	}

	private void createNitrogenInFlowRateActuator(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger
					.debug("Creating NitrogenInFlowRateActuator with moduleName: "
							+ moduleName);
			NitrogenInFlowRateActuatorImpl myNitrogenInFlowRateActuatorImpl = new NitrogenInFlowRateActuatorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myNitrogenInFlowRateActuatorImpl,
					node);
			BiosimServer.registerServer(new NitrogenInFlowRateActuatorPOATie(
					myNitrogenInFlowRateActuatorImpl),
					myNitrogenInFlowRateActuatorImpl.getModuleName(),
					myNitrogenInFlowRateActuatorImpl.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureNitrogenInFlowRateActuator(Node node) {
		NitrogenInFlowRateActuator myNitrogenInFlowRateActuator = NitrogenInFlowRateActuatorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myNitrogenInFlowRateActuator.setOutput(
				NitrogenConsumerHelper.narrow(BiosimInitializer.getModule(
						myID, getOutputName(node))), getFlowRateIndex(node));
		myActuators.add(myNitrogenInFlowRateActuator);
	}

	private void createNitrogenOutFlowRateActuator(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger
					.debug("Creating NitrogenOutFlowRateActuator with moduleName: "
							+ moduleName);
			NitrogenOutFlowRateActuatorImpl myNitrogenOutFlowRateActuatorImpl = new NitrogenOutFlowRateActuatorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myNitrogenOutFlowRateActuatorImpl,
					node);
			BiosimServer.registerServer(new NitrogenOutFlowRateActuatorPOATie(
					myNitrogenOutFlowRateActuatorImpl),
					myNitrogenOutFlowRateActuatorImpl.getModuleName(),
					myNitrogenOutFlowRateActuatorImpl.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureNitrogenOutFlowRateActuator(Node node) {
		NitrogenOutFlowRateActuator myNitrogenOutFlowRateActuator = NitrogenOutFlowRateActuatorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myNitrogenOutFlowRateActuator.setOutput(
				NitrogenProducerHelper.narrow(BiosimInitializer.getModule(
						myID, getOutputName(node))), getFlowRateIndex(node));
		myActuators.add(myNitrogenOutFlowRateActuator);
	}

	private void createMethaneInFlowRateActuator(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger
					.debug("Creating MethaneInFlowRateActuator with moduleName: "
							+ moduleName);
			MethaneInFlowRateActuatorImpl myMethaneInFlowRateActuatorImpl = new MethaneInFlowRateActuatorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myMethaneInFlowRateActuatorImpl,
					node);
			BiosimServer.registerServer(new MethaneInFlowRateActuatorPOATie(
					myMethaneInFlowRateActuatorImpl),
					myMethaneInFlowRateActuatorImpl.getModuleName(),
					myMethaneInFlowRateActuatorImpl.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureMethaneInFlowRateActuator(Node node) {
		MethaneInFlowRateActuator myMethaneInFlowRateActuator = MethaneInFlowRateActuatorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myMethaneInFlowRateActuator.setOutput(
				MethaneConsumerHelper.narrow(BiosimInitializer.getModule(myID,
						getOutputName(node))), getFlowRateIndex(node));
		myActuators.add(myMethaneInFlowRateActuator);
	}

	private void createMethaneOutFlowRateActuator(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger
					.debug("Creating MethaneOutFlowRateActuator with moduleName: "
							+ moduleName);
			MethaneOutFlowRateActuatorImpl myMethaneOutFlowRateActuatorImpl = new MethaneOutFlowRateActuatorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myMethaneOutFlowRateActuatorImpl,
					node);
			BiosimServer.registerServer(new MethaneOutFlowRateActuatorPOATie(
					myMethaneOutFlowRateActuatorImpl),
					myMethaneOutFlowRateActuatorImpl.getModuleName(),
					myMethaneOutFlowRateActuatorImpl.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureMethaneOutFlowRateActuator(Node node) {
		MethaneOutFlowRateActuator myMethaneOutFlowRateActuator = MethaneOutFlowRateActuatorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myMethaneOutFlowRateActuator.setOutput(
				MethaneProducerHelper.narrow(BiosimInitializer.getModule(myID,
						getOutputName(node))), getFlowRateIndex(node));
		myActuators.add(myMethaneOutFlowRateActuator);
	}

	private void crawlAirActuators(Node node, boolean firstPass) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("CO2InFlowRateActuator")) {
					if (firstPass)
						createCO2InFlowRateActuator(child);
					else
						configureCO2InFlowRateActuator(child);
				} else if (childName.equals("CO2OutFlowRateActuator")) {
					if (firstPass)
						createCO2OutFlowRateActuator(child);
					else
						configureCO2OutFlowRateActuator(child);
				} else if (childName.equals("O2InFlowRateActuator")) {
					if (firstPass)
						createO2InFlowRateActuator(child);
					else
						configureO2InFlowRateActuator(child);
				} else if (childName.equals("O2OutFlowRateActuator")) {
					if (firstPass)
						createO2OutFlowRateActuator(child);
					else
						configureO2OutFlowRateActuator(child);
				} else if (childName.equals("H2InFlowRateActuator")) {
					if (firstPass)
						createH2InFlowRateActuator(child);
					else
						configureH2InFlowRateActuator(child);
				} else if (childName.equals("H2OutFlowRateActuator")) {
					if (firstPass)
						createH2OutFlowRateActuator(child);
					else
						configureH2OutFlowRateActuator(child);
				} else if (childName.equals("NitrogenInFlowRateActuator")) {
					if (firstPass)
						createNitrogenInFlowRateActuator(child);
					else
						configureNitrogenInFlowRateActuator(child);
				} else if (childName.equals("NitrogenOutFlowRateActuator")) {
					if (firstPass)
						createNitrogenOutFlowRateActuator(child);
					else
						configureNitrogenOutFlowRateActuator(child);
				} else if (childName.equals("MethaneInFlowRateActuator")) {
					if (firstPass)
						createMethaneInFlowRateActuator(child);
					else
						configureMethaneInFlowRateActuator(child);
				} else if (childName.equals("MethaneOutFlowRateActuator")) {
					if (firstPass)
						createMethaneOutFlowRateActuator(child);
					else
						configureMethaneOutFlowRateActuator(child);
				}
			}
			child = child.getNextSibling();
		}
	}

	// Environment
	private void createAirInFlowRateActuator(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating AirInFlowRateActuator with moduleName: "
					+ moduleName);
			AirInFlowRateActuatorImpl myAirInFlowRateActuatorImpl = new AirInFlowRateActuatorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myAirInFlowRateActuatorImpl, node);
			BiosimServer.registerServer(new AirInFlowRateActuatorPOATie(
					myAirInFlowRateActuatorImpl), myAirInFlowRateActuatorImpl
					.getModuleName(), myAirInFlowRateActuatorImpl.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureAirInFlowRateActuator(Node node) {
		AirInFlowRateActuator myAirInFlowRateActuator = AirInFlowRateActuatorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myAirInFlowRateActuator.setOutput(
				AirConsumerHelper.narrow(BiosimInitializer.getModule(myID,
						getOutputName(node))), getFlowRateIndex(node));
		myActuators.add(myAirInFlowRateActuator);
	}

	private void createAirOutFlowRateActuator(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating AirOutFlowRateActuator with moduleName: "
					+ moduleName);
			AirOutFlowRateActuatorImpl myAirOutFlowRateActuatorImpl = new AirOutFlowRateActuatorImpl(
					myID, moduleName);
			BiosimInitializer
					.setupBioModule(myAirOutFlowRateActuatorImpl, node);
			BiosimServer.registerServer(new AirOutFlowRateActuatorPOATie(
					myAirOutFlowRateActuatorImpl), myAirOutFlowRateActuatorImpl
					.getModuleName(), myAirOutFlowRateActuatorImpl.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureAirOutFlowRateActuator(Node node) {
		AirOutFlowRateActuator myAirOutFlowRateActuator = AirOutFlowRateActuatorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myAirOutFlowRateActuator.setOutput(
				AirProducerHelper.narrow(BiosimInitializer.getModule(myID,
						getOutputName(node))), getFlowRateIndex(node));
		myActuators.add(myAirOutFlowRateActuator);
	}

	private void crawlEnvironmentActuators(Node node, boolean firstPass) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("AirInFlowRateActuator")) {
					if (firstPass)
						createAirInFlowRateActuator(child);
					else
						configureAirInFlowRateActuator(child);
				} else if (childName.equals("AirOutFlowRateActuator")) {
					if (firstPass)
						createAirOutFlowRateActuator(child);
					else
						configureAirOutFlowRateActuator(child);
				}
			}
			child = child.getNextSibling();
		}
	}

	// Food
	private void createBiomassInFlowRateActuator(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger
					.debug("Creating BiomassInFlowRateActuator with moduleName: "
							+ moduleName);
			BiomassInFlowRateActuatorImpl myBiomassInFlowRateActuatorImpl = new BiomassInFlowRateActuatorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myBiomassInFlowRateActuatorImpl,
					node);
			BiosimServer.registerServer(new BiomassInFlowRateActuatorPOATie(
					myBiomassInFlowRateActuatorImpl),
					myBiomassInFlowRateActuatorImpl.getModuleName(),
					myBiomassInFlowRateActuatorImpl.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureBiomassInFlowRateActuator(Node node) {
		BiomassInFlowRateActuator myBiomassInFlowRateActuator = BiomassInFlowRateActuatorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myBiomassInFlowRateActuator.setOutput(
				BiomassConsumerHelper.narrow(BiosimInitializer.getModule(myID,
						getOutputName(node))), getFlowRateIndex(node));
		myActuators.add(myBiomassInFlowRateActuator);
	}

	private void createBiomassOutFlowRateActuator(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger
					.debug("Creating BiomassOutFlowRateActuator with moduleName: "
							+ moduleName);
			BiomassOutFlowRateActuatorImpl myBiomassOutFlowRateActuatorImpl = new BiomassOutFlowRateActuatorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myBiomassOutFlowRateActuatorImpl,
					node);
			BiosimServer.registerServer(new BiomassOutFlowRateActuatorPOATie(
					myBiomassOutFlowRateActuatorImpl),
					myBiomassOutFlowRateActuatorImpl.getModuleName(),
					myBiomassOutFlowRateActuatorImpl.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureBiomassOutFlowRateActuator(Node node) {
		BiomassOutFlowRateActuator myBiomassOutFlowRateActuator = BiomassOutFlowRateActuatorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myBiomassOutFlowRateActuator.setOutput(
				BiomassProducerHelper.narrow(BiosimInitializer.getModule(myID,
						getOutputName(node))), getFlowRateIndex(node));
		myActuators.add(myBiomassOutFlowRateActuator);
	}

	private void createFoodInFlowRateActuator(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating FoodInFlowRateActuator with moduleName: "
					+ moduleName);
			FoodInFlowRateActuatorImpl myFoodInFlowRateActuatorImpl = new FoodInFlowRateActuatorImpl(
					myID, moduleName);
			BiosimInitializer
					.setupBioModule(myFoodInFlowRateActuatorImpl, node);
			BiosimServer.registerServer(new FoodInFlowRateActuatorPOATie(
					myFoodInFlowRateActuatorImpl), myFoodInFlowRateActuatorImpl
					.getModuleName(), myFoodInFlowRateActuatorImpl.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureFoodInFlowRateActuator(Node node) {
		FoodInFlowRateActuator myFoodInFlowRateActuator = FoodInFlowRateActuatorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myFoodInFlowRateActuator.setOutput(
				FoodConsumerHelper.narrow(BiosimInitializer.getModule(myID,
						getOutputName(node))), getFlowRateIndex(node));
		myActuators.add(myFoodInFlowRateActuator);
	}

	private void createFoodOutFlowRateActuator(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating FoodOutFlowRateActuator with moduleName: "
					+ moduleName);
			FoodOutFlowRateActuatorImpl myFoodOutFlowRateActuatorImpl = new FoodOutFlowRateActuatorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myFoodOutFlowRateActuatorImpl,
					node);
			BiosimServer.registerServer(new FoodOutFlowRateActuatorPOATie(
					myFoodOutFlowRateActuatorImpl),
					myFoodOutFlowRateActuatorImpl.getModuleName(),
					myFoodOutFlowRateActuatorImpl.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureFoodOutFlowRateActuator(Node node) {
		FoodOutFlowRateActuator myFoodOutFlowRateActuator = FoodOutFlowRateActuatorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myFoodOutFlowRateActuator.setOutput(
				FoodProducerHelper.narrow(BiosimInitializer.getModule(myID,
						getOutputName(node))), getFlowRateIndex(node));
		myActuators.add(myFoodOutFlowRateActuator);
	}

	private void createPlantingActuator(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating PlantingActuator with moduleName: "
					+ moduleName);
			PlantingActuatorImpl myPlantingActuatorImpl = new PlantingActuatorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myPlantingActuatorImpl, node);
			BiosimServer.registerServer(new PlantingActuatorPOATie(
					myPlantingActuatorImpl), myPlantingActuatorImpl
					.getModuleName(), myPlantingActuatorImpl.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configurePlantingActuator(Node node) {
		PlantingActuator myPlantingActuator = PlantingActuatorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myPlantingActuator.setOutput(BiomassPSHelper.narrow(BiosimInitializer
				.getModule(myID, getOutputName(node))), getShelfIndex(node));
		myActuators.add(myPlantingActuator);
	}

	private void createHarvestingActuator(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating HarvestingActuator with moduleName: "
					+ moduleName);
			HarvestingActuatorImpl myHarvestingActuatorImpl = new HarvestingActuatorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myHarvestingActuatorImpl, node);
			BiosimServer.registerServer(new HarvestingActuatorPOATie(
					myHarvestingActuatorImpl), myHarvestingActuatorImpl
					.getModuleName(), myHarvestingActuatorImpl.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureHarvestingActuator(Node node) {
		HarvestingActuator myHarvestingActuator = HarvestingActuatorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myHarvestingActuator.setOutput(BiomassPSHelper.narrow(BiosimInitializer
				.getModule(myID, getOutputName(node))), getShelfIndex(node));
		myActuators.add(myHarvestingActuator);
	}

	private void crawlFoodActuators(Node node, boolean firstPass) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("BiomassInFlowRateActuator")) {
					if (firstPass)
						createBiomassInFlowRateActuator(child);
					else
						configureBiomassInFlowRateActuator(child);
				} else if (childName.equals("BiomassOutFlowRateActuator")) {
					if (firstPass)
						createBiomassOutFlowRateActuator(child);
					else
						configureBiomassOutFlowRateActuator(child);
				} else if (childName.equals("FoodInFlowRateActuator")) {
					if (firstPass)
						createFoodInFlowRateActuator(child);
					else
						configureFoodInFlowRateActuator(child);
				} else if (childName.equals("FoodOutFlowRateActuator")) {
					if (firstPass)
						createFoodOutFlowRateActuator(child);
					else
						configureFoodOutFlowRateActuator(child);
				} else if (childName.equals("HarvestingActuator")) {
					if (firstPass)
						createHarvestingActuator(child);
					else
						configureHarvestingActuator(child);
				} else if (childName.equals("PlantingActuator")) {
					if (firstPass)
						createPlantingActuator(child);
					else
						configurePlantingActuator(child);
				}
			}
			child = child.getNextSibling();
		}
	}

	private void createInfluentValveActuator(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating InfluentValveActuator with moduleName: "
					+ moduleName);
			InfluentValveActuatorImpl myInfluentValveActuatorImpl = new InfluentValveActuatorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myInfluentValveActuatorImpl, node);
			BiosimServer.registerServer(new InfluentValveActuatorPOATie(
					myInfluentValveActuatorImpl), myInfluentValveActuatorImpl
					.getModuleName(), myInfluentValveActuatorImpl.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureInfluentValveActuator(Node node) {
		InfluentValveActuator myInfluentValveActuator = InfluentValveActuatorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myInfluentValveActuator
				.setOutput(InfluentValveHelper.narrow(BiosimInitializer
						.getModule(myID, getOutputName(node))));
		myActuators.add(myInfluentValveActuator);
	}

	private void createEffluentValveActuator(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating EffluentValveActuator with moduleName: "
					+ moduleName);
			EffluentValveActuatorImpl myEffluentValveActuatorImpl = new EffluentValveActuatorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myEffluentValveActuatorImpl, node);
			BiosimServer.registerServer(new EffluentValveActuatorPOATie(
					myEffluentValveActuatorImpl), myEffluentValveActuatorImpl
					.getModuleName(), myEffluentValveActuatorImpl.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureEffluentValveActuator(Node node) {
		EffluentValveActuator myEffluentValveActuator = EffluentValveActuatorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myEffluentValveActuator
				.setOutput(EffluentValveHelper.narrow(BiosimInitializer
						.getModule(myID, getOutputName(node))));
		myActuators.add(myEffluentValveActuator);
	}

	private void crawlFrameworkActuators(Node node, boolean firstPass) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("InfluentValveActuator")) {
					if (firstPass)
						createInfluentValveActuator(child);
					else
						configureInfluentValveActuator(child);
				} else if (childName.equals("EffluentValveActuator")) {
					if (firstPass)
						createEffluentValveActuator(child);
					else
						configureEffluentValveActuator(child);
				}
			}
			child = child.getNextSibling();
		}
	}

	// Power
	private void createPowerInFlowRateActuator(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating PowerInFlowRateActuator with moduleName: "
					+ moduleName);
			PowerInFlowRateActuatorImpl myPowerInFlowRateActuatorImpl = new PowerInFlowRateActuatorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myPowerInFlowRateActuatorImpl,
					node);
			BiosimServer.registerServer(new PowerInFlowRateActuatorPOATie(
					myPowerInFlowRateActuatorImpl),
					myPowerInFlowRateActuatorImpl.getModuleName(),
					myPowerInFlowRateActuatorImpl.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configurePowerInFlowRateActuator(Node node) {
		PowerInFlowRateActuator myPowerInFlowRateActuator = PowerInFlowRateActuatorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myPowerInFlowRateActuator.setOutput(
				PowerConsumerHelper.narrow(BiosimInitializer.getModule(myID,
						getOutputName(node))), getFlowRateIndex(node));
		myActuators.add(myPowerInFlowRateActuator);
	}

	private void createPowerOutFlowRateActuator(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger
					.debug("Creating PowerOutFlowRateActuator with moduleName: "
							+ moduleName);
			PowerOutFlowRateActuatorImpl myPowerOutFlowRateActuatorImpl = new PowerOutFlowRateActuatorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myPowerOutFlowRateActuatorImpl,
					node);
			BiosimServer.registerServer(new PowerOutFlowRateActuatorPOATie(
					myPowerOutFlowRateActuatorImpl),
					myPowerOutFlowRateActuatorImpl.getModuleName(),
					myPowerOutFlowRateActuatorImpl.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configurePowerOutFlowRateActuator(Node node) {
		PowerOutFlowRateActuator myPowerOutFlowRateActuator = PowerOutFlowRateActuatorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myPowerOutFlowRateActuator.setOutput(
				PowerProducerHelper.narrow(BiosimInitializer.getModule(myID,
						getOutputName(node))), getFlowRateIndex(node));
		myActuators.add(myPowerOutFlowRateActuator);
	}

	private void crawlPowerActuators(Node node, boolean firstPass) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("PowerInFlowRateActuator")) {
					if (firstPass)
						createPowerInFlowRateActuator(child);
					else
						configurePowerInFlowRateActuator(child);
				} else if (childName.equals("PowerOutFlowRateActuator")) {
					if (firstPass)
						createPowerOutFlowRateActuator(child);
					else
						configurePowerOutFlowRateActuator(child);
				}
			}
			child = child.getNextSibling();
		}
	}

	// Water
	private void createPotableWaterInFlowRateActuator(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger
					.debug("Creating PotableWaterInFlowRateActuator with moduleName: "
							+ moduleName);
			PotableWaterInFlowRateActuatorImpl myPotableWaterInFlowRateActuatorImpl = new PotableWaterInFlowRateActuatorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(
					myPotableWaterInFlowRateActuatorImpl, node);
			BiosimServer.registerServer(
					new PotableWaterInFlowRateActuatorPOATie(
							myPotableWaterInFlowRateActuatorImpl),
					myPotableWaterInFlowRateActuatorImpl.getModuleName(),
					myPotableWaterInFlowRateActuatorImpl.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configurePotableWaterInFlowRateActuator(Node node) {
		PotableWaterInFlowRateActuator myPotableWaterInFlowRateActuator = PotableWaterInFlowRateActuatorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myPotableWaterInFlowRateActuator.setOutput(
				PotableWaterConsumerHelper.narrow(BiosimInitializer.getModule(
						myID, getOutputName(node))), getFlowRateIndex(node));
		myActuators.add(myPotableWaterInFlowRateActuator);
	}

	private void createPotableWaterOutFlowRateActuator(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger
					.debug("Creating PotableWaterOutFlowRateActuator with moduleName: "
							+ moduleName);
			PotableWaterOutFlowRateActuatorImpl myPotableWaterOutFlowRateActuatorImpl = new PotableWaterOutFlowRateActuatorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(
					myPotableWaterOutFlowRateActuatorImpl, node);
			BiosimServer.registerServer(
					new PotableWaterOutFlowRateActuatorPOATie(
							myPotableWaterOutFlowRateActuatorImpl),
					myPotableWaterOutFlowRateActuatorImpl.getModuleName(),
					myPotableWaterOutFlowRateActuatorImpl.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configurePotableWaterOutFlowRateActuator(Node node) {
		PotableWaterOutFlowRateActuator myPotableWaterOutFlowRateActuator = PotableWaterOutFlowRateActuatorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myPotableWaterOutFlowRateActuator.setOutput(
				PotableWaterProducerHelper.narrow(BiosimInitializer.getModule(
						myID, getOutputName(node))), getFlowRateIndex(node));
		myActuators.add(myPotableWaterOutFlowRateActuator);
	}

	private void createGreyWaterInFlowRateActuator(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger
					.debug("Creating GreyWaterInFlowRateActuator with moduleName: "
							+ moduleName);
			GreyWaterInFlowRateActuatorImpl myGreyWaterInFlowRateActuatorImpl = new GreyWaterInFlowRateActuatorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myGreyWaterInFlowRateActuatorImpl,
					node);
			BiosimServer.registerServer(new GreyWaterInFlowRateActuatorPOATie(
					myGreyWaterInFlowRateActuatorImpl),
					myGreyWaterInFlowRateActuatorImpl.getModuleName(),
					myGreyWaterInFlowRateActuatorImpl.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureGreyWaterInFlowRateActuator(Node node) {
		GreyWaterInFlowRateActuator myGreyWaterInFlowRateActuator = GreyWaterInFlowRateActuatorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myGreyWaterInFlowRateActuator.setOutput(
				GreyWaterConsumerHelper.narrow(BiosimInitializer.getModule(
						myID, getOutputName(node))), getFlowRateIndex(node));
		myActuators.add(myGreyWaterInFlowRateActuator);
	}

	private void createGreyWaterOutFlowRateActuator(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger
					.debug("Creating GreyWaterOutFlowRateActuator with moduleName: "
							+ moduleName);
			GreyWaterOutFlowRateActuatorImpl myGreyWaterOutFlowRateActuatorImpl = new GreyWaterOutFlowRateActuatorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(
					myGreyWaterOutFlowRateActuatorImpl, node);
			BiosimServer.registerServer(new GreyWaterOutFlowRateActuatorPOATie(
					myGreyWaterOutFlowRateActuatorImpl),
					myGreyWaterOutFlowRateActuatorImpl.getModuleName(),
					myGreyWaterOutFlowRateActuatorImpl.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureGreyWaterOutFlowRateActuator(Node node) {
		GreyWaterOutFlowRateActuator myGreyWaterOutFlowRateActuator = GreyWaterOutFlowRateActuatorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myGreyWaterOutFlowRateActuator.setOutput(
				GreyWaterProducerHelper.narrow(BiosimInitializer.getModule(
						myID, getOutputName(node))), getFlowRateIndex(node));
		myActuators.add(myGreyWaterOutFlowRateActuator);
	}

	private void createDirtyWaterInFlowRateActuator(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger
					.debug("Creating DirtyWaterInFlowRateActuator with moduleName: "
							+ moduleName);
			DirtyWaterInFlowRateActuatorImpl myDirtyWaterInFlowRateActuatorImpl = new DirtyWaterInFlowRateActuatorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(
					myDirtyWaterInFlowRateActuatorImpl, node);
			BiosimServer.registerServer(new DirtyWaterInFlowRateActuatorPOATie(
					myDirtyWaterInFlowRateActuatorImpl),
					myDirtyWaterInFlowRateActuatorImpl.getModuleName(),
					myDirtyWaterInFlowRateActuatorImpl.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureDirtyWaterInFlowRateActuator(Node node) {
		DirtyWaterInFlowRateActuator myDirtyWaterInFlowRateActuator = DirtyWaterInFlowRateActuatorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myDirtyWaterInFlowRateActuator.setOutput(
				DirtyWaterConsumerHelper.narrow(BiosimInitializer.getModule(
						myID, getOutputName(node))), getFlowRateIndex(node));
		myActuators.add(myDirtyWaterInFlowRateActuator);
	}

	private void createDirtyWaterOutFlowRateActuator(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger
					.debug("Creating DirtyWaterOutFlowRateActuator with moduleName: "
							+ moduleName);
			DirtyWaterOutFlowRateActuatorImpl myDirtyWaterOutFlowRateActuatorImpl = new DirtyWaterOutFlowRateActuatorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(
					myDirtyWaterOutFlowRateActuatorImpl, node);
			BiosimServer.registerServer(
					new DirtyWaterOutFlowRateActuatorPOATie(
							myDirtyWaterOutFlowRateActuatorImpl),
					myDirtyWaterOutFlowRateActuatorImpl.getModuleName(),
					myDirtyWaterOutFlowRateActuatorImpl.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureDirtyWaterOutFlowRateActuator(Node node) {
		DirtyWaterOutFlowRateActuator myDirtyWaterOutFlowRateActuator = DirtyWaterOutFlowRateActuatorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myDirtyWaterOutFlowRateActuator.setOutput(
				DirtyWaterProducerHelper.narrow(BiosimInitializer.getModule(
						myID, getOutputName(node))), getFlowRateIndex(node));
		myActuators.add(myDirtyWaterOutFlowRateActuator);
	}

	private void crawlWaterActuators(Node node, boolean firstPass) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("PotableWaterInFlowRateActuator")) {
					if (firstPass)
						createPotableWaterInFlowRateActuator(child);
					else
						configurePotableWaterInFlowRateActuator(child);
				} else if (childName.equals("PotableWaterOutFlowRateActuator")) {
					if (firstPass)
						createPotableWaterOutFlowRateActuator(child);
					else
						configurePotableWaterOutFlowRateActuator(child);
				} else if (childName.equals("GreyWaterInFlowRateActuator")) {
					if (firstPass)
						createGreyWaterInFlowRateActuator(child);
					else
						configureGreyWaterInFlowRateActuator(child);
				} else if (childName.equals("GreyWaterOutFlowRateActuator")) {
					if (firstPass)
						createGreyWaterOutFlowRateActuator(child);
					else
						configureGreyWaterOutFlowRateActuator(child);
				} else if (childName.equals("DirtyWaterInFlowRateActuator")) {
					if (firstPass)
						createDirtyWaterInFlowRateActuator(child);
					else
						configureDirtyWaterInFlowRateActuator(child);
				} else if (childName.equals("DirtyWaterOutFlowRateActuator")) {
					if (firstPass)
						createDirtyWaterOutFlowRateActuator(child);
					else
						configureDirtyWaterOutFlowRateActuator(child);
				}
			}
			child = child.getNextSibling();
		}
	}

	// Waste
	private void createDryWasteInFlowRateActuator(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger
					.debug("Creating DryWasteInFlowRateActuator with moduleName: "
							+ moduleName);
			DryWasteInFlowRateActuatorImpl myDryWasteInFlowRateActuatorImpl = new DryWasteInFlowRateActuatorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myDryWasteInFlowRateActuatorImpl,
					node);
			BiosimServer.registerServer(new DryWasteInFlowRateActuatorPOATie(
					myDryWasteInFlowRateActuatorImpl),
					myDryWasteInFlowRateActuatorImpl.getModuleName(),
					myDryWasteInFlowRateActuatorImpl.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureDryWasteInFlowRateActuator(Node node) {
		DryWasteInFlowRateActuator myDryWasteInFlowRateActuator = DryWasteInFlowRateActuatorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myDryWasteInFlowRateActuator.setOutput(
				DryWasteConsumerHelper.narrow(BiosimInitializer.getModule(
						myID, getOutputName(node))), getFlowRateIndex(node));
		myActuators.add(myDryWasteInFlowRateActuator);
	}

	private void createDryWasteOutFlowRateActuator(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger
					.debug("Creating DryWasteOutFlowRateActuator with moduleName: "
							+ moduleName);
			DryWasteOutFlowRateActuatorImpl myDryWasteOutFlowRateActuatorImpl = new DryWasteOutFlowRateActuatorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myDryWasteOutFlowRateActuatorImpl,
					node);
			BiosimServer.registerServer(new DryWasteOutFlowRateActuatorPOATie(
					myDryWasteOutFlowRateActuatorImpl),
					myDryWasteOutFlowRateActuatorImpl.getModuleName(),
					myDryWasteOutFlowRateActuatorImpl.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureDryWasteOutFlowRateActuator(Node node) {
		DryWasteOutFlowRateActuator myDryWasteOutFlowRateActuator = DryWasteOutFlowRateActuatorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myDryWasteOutFlowRateActuator.setOutput(
				DryWasteProducerHelper.narrow(BiosimInitializer.getModule(
						myID, getOutputName(node))), getFlowRateIndex(node));
		myActuators.add(myDryWasteOutFlowRateActuator);
	}

	private void crawlWasteActuators(Node node, boolean firstPass) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("DryWasteInFlowRateActuator")) {
					if (firstPass)
						createDryWasteInFlowRateActuator(child);
					else
						configureDryWasteInFlowRateActuator(child);
				} else if (childName.equals("DryWasteOutFlowRateActuator")) {
					if (firstPass)
						createDryWasteOutFlowRateActuator(child);
					else
						configureDryWasteOutFlowRateActuator(child);
				}
			}
			child = child.getNextSibling();
		}
	}

	public void crawlActuators(Node node, boolean firstPass) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
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
			}
			child = child.getNextSibling();
		}
	}

	/**
	 * @return Returns the myActuators.
	 */
	public List<GenericActuator> getActuators() {
		return myActuators;
	}
}
