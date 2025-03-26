package com.traclabs.biosim.server.actuator.framework;

import com.traclabs.biosim.server.actuator.air.*;
import com.traclabs.biosim.server.actuator.environment.AirInFlowRateActuator;
import com.traclabs.biosim.server.actuator.environment.AirOutFlowRateActuator;
import com.traclabs.biosim.server.actuator.food.*;
import com.traclabs.biosim.server.actuator.power.PowerInFlowRateActuator;
import com.traclabs.biosim.server.actuator.power.PowerOutFlowRateActuator;
import com.traclabs.biosim.server.actuator.waste.DryWasteInFlowRateActuator;
import com.traclabs.biosim.server.actuator.waste.DryWasteOutFlowRateActuator;
import com.traclabs.biosim.server.actuator.water.*;
import com.traclabs.biosim.server.framework.BiosimInitializer;
import com.traclabs.biosim.server.framework.BiosimServer;
import org.w3c.dom.Node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Vector;

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
			CO2InFlowRateActuator myCO2InFlowRateActuator = new CO2InFlowRateActuator(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myCO2InFlowRateActuator, node);
			BiosimServer.registerServer(new CO2InFlowRateActuatorPOATie(
					myCO2InFlowRateActuator), myCO2InFlowRateActuator
					.getModuleName(), myCO2InFlowRateActuator.getID());
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
			CO2OutFlowRateActuator myCO2OutFlowRateActuator = new CO2OutFlowRateActuator(
					myID, moduleName);
			BiosimInitializer
					.setupBioModule(myCO2OutFlowRateActuator, node);
			BiosimServer.registerServer(new CO2OutFlowRateActuatorPOATie(
					myCO2OutFlowRateActuator), myCO2OutFlowRateActuator
					.getModuleName(), myCO2OutFlowRateActuator.getID());
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
			O2InFlowRateActuator myO2InFlowRateActuator = new O2InFlowRateActuator(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myO2InFlowRateActuator, node);
			BiosimServer.registerServer(new O2InFlowRateActuatorPOATie(
					myO2InFlowRateActuator), myO2InFlowRateActuator
					.getModuleName(), myO2InFlowRateActuator.getID());
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
			O2OutFlowRateActuator myO2OutFlowRateActuator = new O2OutFlowRateActuator(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myO2OutFlowRateActuator, node);
			BiosimServer.registerServer(new O2OutFlowRateActuatorPOATie(
					myO2OutFlowRateActuator), myO2OutFlowRateActuator
					.getModuleName(), myO2OutFlowRateActuator.getID());
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
			H2InFlowRateActuator myH2InFlowRateActuator = new H2InFlowRateActuator(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myH2InFlowRateActuator, node);
			BiosimServer.registerServer(new H2InFlowRateActuatorPOATie(
					myH2InFlowRateActuator), myH2InFlowRateActuator
					.getModuleName(), myH2InFlowRateActuator.getID());
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
			H2OutFlowRateActuator myH2OutFlowRateActuator = new H2OutFlowRateActuator(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myH2OutFlowRateActuator, node);
			BiosimServer.registerServer(new H2OutFlowRateActuatorPOATie(
					myH2OutFlowRateActuator), myH2OutFlowRateActuator
					.getModuleName(), myH2OutFlowRateActuator.getID());
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
			NitrogenInFlowRateActuator myNitrogenInFlowRateActuator = new NitrogenInFlowRateActuator(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myNitrogenInFlowRateActuator,
					node);
			BiosimServer.registerServer(new NitrogenInFlowRateActuatorPOATie(
					myNitrogenInFlowRateActuator),
					myNitrogenInFlowRateActuator.getModuleName(),
					myNitrogenInFlowRateActuator.getID());
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
			NitrogenOutFlowRateActuator myNitrogenOutFlowRateActuator = new NitrogenOutFlowRateActuator(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myNitrogenOutFlowRateActuator,
					node);
			BiosimServer.registerServer(new NitrogenOutFlowRateActuatorPOATie(
					myNitrogenOutFlowRateActuator),
					myNitrogenOutFlowRateActuator.getModuleName(),
					myNitrogenOutFlowRateActuator.getID());
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
			MethaneInFlowRateActuator myMethaneInFlowRateActuator = new MethaneInFlowRateActuator(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myMethaneInFlowRateActuator,
					node);
			BiosimServer.registerServer(new MethaneInFlowRateActuatorPOATie(
					myMethaneInFlowRateActuator),
					myMethaneInFlowRateActuator.getModuleName(),
					myMethaneInFlowRateActuator.getID());
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
			MethaneOutFlowRateActuator myMethaneOutFlowRateActuator = new MethaneOutFlowRateActuator(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myMethaneOutFlowRateActuator,
					node);
			BiosimServer.registerServer(new MethaneOutFlowRateActuatorPOATie(
					myMethaneOutFlowRateActuator),
					myMethaneOutFlowRateActuator.getModuleName(),
					myMethaneOutFlowRateActuator.getID());
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
			AirInFlowRateActuator myAirInFlowRateActuator = new AirInFlowRateActuator(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myAirInFlowRateActuator, node);
			BiosimServer.registerServer(new AirInFlowRateActuatorPOATie(
					myAirInFlowRateActuator), myAirInFlowRateActuator
					.getModuleName(), myAirInFlowRateActuator.getID());
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
			AirOutFlowRateActuator myAirOutFlowRateActuator = new AirOutFlowRateActuator(
					myID, moduleName);
			BiosimInitializer
					.setupBioModule(myAirOutFlowRateActuator, node);
			BiosimServer.registerServer(new AirOutFlowRateActuatorPOATie(
					myAirOutFlowRateActuator), myAirOutFlowRateActuator
					.getModuleName(), myAirOutFlowRateActuator.getID());
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
			BiomassInFlowRateActuator myBiomassInFlowRateActuator = new BiomassInFlowRateActuator(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myBiomassInFlowRateActuator,
					node);
			BiosimServer.registerServer(new BiomassInFlowRateActuatorPOATie(
					myBiomassInFlowRateActuator),
					myBiomassInFlowRateActuator.getModuleName(),
					myBiomassInFlowRateActuator.getID());
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
			BiomassOutFlowRateActuator myBiomassOutFlowRateActuator = new BiomassOutFlowRateActuator(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myBiomassOutFlowRateActuator,
					node);
			BiosimServer.registerServer(new BiomassOutFlowRateActuatorPOATie(
					myBiomassOutFlowRateActuator),
					myBiomassOutFlowRateActuator.getModuleName(),
					myBiomassOutFlowRateActuator.getID());
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
			FoodInFlowRateActuator myFoodInFlowRateActuator = new FoodInFlowRateActuator(
					myID, moduleName);
			BiosimInitializer
					.setupBioModule(myFoodInFlowRateActuator, node);
			BiosimServer.registerServer(new FoodInFlowRateActuatorPOATie(
					myFoodInFlowRateActuator), myFoodInFlowRateActuator
					.getModuleName(), myFoodInFlowRateActuator.getID());
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
			FoodOutFlowRateActuator myFoodOutFlowRateActuator = new FoodOutFlowRateActuator(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myFoodOutFlowRateActuator,
					node);
			BiosimServer.registerServer(new FoodOutFlowRateActuatorPOATie(
					myFoodOutFlowRateActuator),
					myFoodOutFlowRateActuator.getModuleName(),
					myFoodOutFlowRateActuator.getID());
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
			PlantingActuator myPlantingActuator = new PlantingActuator(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myPlantingActuator, node);
			BiosimServer.registerServer(new PlantingActuatorPOATie(
					myPlantingActuator), myPlantingActuator
					.getModuleName(), myPlantingActuator.getID());
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
			HarvestingActuator myHarvestingActuator = new HarvestingActuator(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myHarvestingActuator, node);
			BiosimServer.registerServer(new HarvestingActuatorPOATie(
					myHarvestingActuator), myHarvestingActuator
					.getModuleName(), myHarvestingActuator.getID());
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
			InfluentValveActuator myInfluentValveActuator = new InfluentValveActuator(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myInfluentValveActuator, node);
			BiosimServer.registerServer(new InfluentValveActuatorPOATie(
					myInfluentValveActuator), myInfluentValveActuator
					.getModuleName(), myInfluentValveActuator.getID());
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
			EffluentValveActuator myEffluentValveActuator = new EffluentValveActuator(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myEffluentValveActuator, node);
			BiosimServer.registerServer(new EffluentValveActuatorPOATie(
					myEffluentValveActuator), myEffluentValveActuator
					.getModuleName(), myEffluentValveActuator.getID());
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
			PowerInFlowRateActuator myPowerInFlowRateActuator = new PowerInFlowRateActuator(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myPowerInFlowRateActuator,
					node);
			BiosimServer.registerServer(new PowerInFlowRateActuatorPOATie(
					myPowerInFlowRateActuator),
					myPowerInFlowRateActuator.getModuleName(),
					myPowerInFlowRateActuator.getID());
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
			PowerOutFlowRateActuator myPowerOutFlowRateActuator = new PowerOutFlowRateActuator(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myPowerOutFlowRateActuator,
					node);
			BiosimServer.registerServer(new PowerOutFlowRateActuatorPOATie(
					myPowerOutFlowRateActuator),
					myPowerOutFlowRateActuator.getModuleName(),
					myPowerOutFlowRateActuator.getID());
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
			PotableWaterInFlowRateActuator myPotableWaterInFlowRateActuator = new PotableWaterInFlowRateActuator(
					myID, moduleName);
			BiosimInitializer.setupBioModule(
					myPotableWaterInFlowRateActuator, node);
			BiosimServer.registerServer(
					new PotableWaterInFlowRateActuatorPOATie(
							myPotableWaterInFlowRateActuator),
					myPotableWaterInFlowRateActuator.getModuleName(),
					myPotableWaterInFlowRateActuator.getID());
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
			PotableWaterOutFlowRateActuator myPotableWaterOutFlowRateActuator = new PotableWaterOutFlowRateActuator(
					myID, moduleName);
			BiosimInitializer.setupBioModule(
					myPotableWaterOutFlowRateActuator, node);
			BiosimServer.registerServer(
					new PotableWaterOutFlowRateActuatorPOATie(
							myPotableWaterOutFlowRateActuator),
					myPotableWaterOutFlowRateActuator.getModuleName(),
					myPotableWaterOutFlowRateActuator.getID());
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
			GreyWaterInFlowRateActuator myGreyWaterInFlowRateActuator = new GreyWaterInFlowRateActuator(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myGreyWaterInFlowRateActuator,
					node);
			BiosimServer.registerServer(new GreyWaterInFlowRateActuatorPOATie(
					myGreyWaterInFlowRateActuator),
					myGreyWaterInFlowRateActuator.getModuleName(),
					myGreyWaterInFlowRateActuator.getID());
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
			GreyWaterOutFlowRateActuator myGreyWaterOutFlowRateActuator = new GreyWaterOutFlowRateActuator(
					myID, moduleName);
			BiosimInitializer.setupBioModule(
					myGreyWaterOutFlowRateActuator, node);
			BiosimServer.registerServer(new GreyWaterOutFlowRateActuatorPOATie(
					myGreyWaterOutFlowRateActuator),
					myGreyWaterOutFlowRateActuator.getModuleName(),
					myGreyWaterOutFlowRateActuator.getID());
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
			DirtyWaterInFlowRateActuator myDirtyWaterInFlowRateActuator = new DirtyWaterInFlowRateActuator(
					myID, moduleName);
			BiosimInitializer.setupBioModule(
					myDirtyWaterInFlowRateActuator, node);
			BiosimServer.registerServer(new DirtyWaterInFlowRateActuatorPOATie(
					myDirtyWaterInFlowRateActuator),
					myDirtyWaterInFlowRateActuator.getModuleName(),
					myDirtyWaterInFlowRateActuator.getID());
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
			DirtyWaterOutFlowRateActuator myDirtyWaterOutFlowRateActuator = new DirtyWaterOutFlowRateActuator(
					myID, moduleName);
			BiosimInitializer.setupBioModule(
					myDirtyWaterOutFlowRateActuator, node);
			BiosimServer.registerServer(
					new DirtyWaterOutFlowRateActuatorPOATie(
							myDirtyWaterOutFlowRateActuator),
					myDirtyWaterOutFlowRateActuator.getModuleName(),
					myDirtyWaterOutFlowRateActuator.getID());
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
			DryWasteInFlowRateActuator myDryWasteInFlowRateActuator = new DryWasteInFlowRateActuator(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myDryWasteInFlowRateActuator,
					node);
			BiosimServer.registerServer(new DryWasteInFlowRateActuatorPOATie(
					myDryWasteInFlowRateActuator),
					myDryWasteInFlowRateActuator.getModuleName(),
					myDryWasteInFlowRateActuator.getID());
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
			DryWasteOutFlowRateActuator myDryWasteOutFlowRateActuator = new DryWasteOutFlowRateActuator(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myDryWasteOutFlowRateActuator,
					node);
			BiosimServer.registerServer(new DryWasteOutFlowRateActuatorPOATie(
					myDryWasteOutFlowRateActuator),
					myDryWasteOutFlowRateActuator.getModuleName(),
					myDryWasteOutFlowRateActuator.getID());
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
