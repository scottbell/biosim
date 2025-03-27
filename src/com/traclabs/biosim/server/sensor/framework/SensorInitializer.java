package com.traclabs.biosim.server.sensor.framework;

import com.traclabs.biosim.server.framework.BiosimInitializer;
import com.traclabs.biosim.server.framework.BiosimServer;
import com.traclabs.biosim.server.sensor.air.*;
import com.traclabs.biosim.server.sensor.crew.CrewGroupAnyDeadSensor;
import com.traclabs.biosim.server.sensor.crew.CrewGroupDeathSensor;
import com.traclabs.biosim.server.sensor.crew.CrewGroupProductivitySensor;
import com.traclabs.biosim.server.sensor.environment.*;
import com.traclabs.biosim.server.sensor.food.*;
import com.traclabs.biosim.server.sensor.power.PowerInFlowRateSensor;
import com.traclabs.biosim.server.sensor.power.PowerOutFlowRateSensor;
import com.traclabs.biosim.server.sensor.waste.DryWasteInFlowRateSensor;
import com.traclabs.biosim.server.sensor.waste.DryWasteOutFlowRateSensor;
import com.traclabs.biosim.server.sensor.water.*;
import com.traclabs.biosim.server.simulation.environment.EnvironmentStore;
import com.traclabs.biosim.server.simulation.environment.SimEnvironment;
import com.traclabs.biosim.util.XMLUtils;
import org.w3c.dom.Node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.HashSet;

/**
 * Reads BioSim configuration from XML file.
 * 
 * @author Scott Bell
 */
public class SensorInitializer {
	private int myID = 0;

	private Set<GenericSensor> mySensors;

	private Logger myLogger;

	/** Default constructor. */
	public SensorInitializer(int pID) {
		myID = pID;
		mySensors = new HashSet<GenericSensor>();
		myLogger = LoggerFactory.getLogger(this.getClass());
	}

	// Sensors
	public void crawlSensors(Node node, boolean firstPass) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("air")) {
					crawlAirSensors(child, firstPass);

				} else if (childName.equals("crew")) {
					crawlCrewSensors(child, firstPass);

				} else if (childName.equals("environment")) {
					crawlEnvironmentSensors(child, firstPass);

				} else if (childName.equals("food")) {
					crawlFoodSensors(child, firstPass);

				} else if (childName.equals("framework")) {
					crawlFrameworkSensors(child, firstPass);

				} else if (childName.equals("power")) {
					crawlPowerSensors(child, firstPass);

				} else if (childName.equals("water")) {
					crawlWaterSensors(child, firstPass);

				} else if (childName.equals("waste")) {
					crawlWasteSensors(child, firstPass);

				}
			}
			child = child.getNextSibling();
		}
	}

	private static String getInputName(Node pNode) {
		return pNode.getAttributes().getNamedItem("input").getNodeValue();
	}

	private static int getFlowRateIndex(Node pNode) {
		return Integer.parseInt(pNode.getAttributes().getNamedItem("index")
				.getNodeValue());
	}

	// Air
	private void createCO2InFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating CO2InFlowRateSensor with moduleName: "
					+ moduleName);
			CO2InFlowRateSensor myCO2InFlowRateSensor = new CO2InFlowRateSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myCO2InFlowRateSensor, node);
			BiosimServer.registerServer(new CO2InFlowRateSensorPOATie(
					myCO2InFlowRateSensor), myCO2InFlowRateSensor
					.getModuleName(), myCO2InFlowRateSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureCO2InFlowRateSensor(Node node) {
		CO2InFlowRateSensor myCO2InFlowRateSensor = CO2InFlowRateSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myCO2InFlowRateSensor.setInput(
				CO2ConsumerHelper.narrow(BiosimInitializer.getModule(myID,
						getInputName(node))), getFlowRateIndex(node));
		mySensors.add(myCO2InFlowRateSensor);
	}

	private void createCO2OutFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating CO2OutFlowRateSensor with moduleName: "
					+ moduleName);
			CO2OutFlowRateSensor myCO2OutFlowRateSensor = new CO2OutFlowRateSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myCO2OutFlowRateSensor, node);
			BiosimServer.registerServer(new CO2OutFlowRateSensorPOATie(
					myCO2OutFlowRateSensor), myCO2OutFlowRateSensor
					.getModuleName(), myCO2OutFlowRateSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureCO2OutFlowRateSensor(Node node) {
		CO2OutFlowRateSensor myCO2OutFlowRateSensor = CO2OutFlowRateSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myCO2OutFlowRateSensor.setInput(
				CO2ProducerHelper.narrow(BiosimInitializer.getModule(myID,
						getInputName(node))), getFlowRateIndex(node));
		mySensors.add(myCO2OutFlowRateSensor);
	}

	private void createO2InFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating O2InFlowRateSensor with moduleName: "
					+ moduleName);
			O2InFlowRateSensor myO2InFlowRateSensor = new O2InFlowRateSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myO2InFlowRateSensor, node);
			BiosimServer.registerServer(new O2InFlowRateSensorPOATie(
					myO2InFlowRateSensor), myO2InFlowRateSensor
					.getModuleName(), myO2InFlowRateSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureO2InFlowRateSensor(Node node) {
		O2InFlowRateSensor myO2InFlowRateSensor = O2InFlowRateSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myO2InFlowRateSensor.setInput(O2ConsumerHelper.narrow(BiosimInitializer
				.getModule(myID, getInputName(node))), getFlowRateIndex(node));
		mySensors.add(myO2InFlowRateSensor);
	}

	private void createO2OutFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating O2OutFlowRateSensor with moduleName: "
					+ moduleName);
			O2OutFlowRateSensor myO2OutFlowRateSensor = new O2OutFlowRateSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myO2OutFlowRateSensor, node);
			BiosimServer.registerServer(new O2OutFlowRateSensorPOATie(
					myO2OutFlowRateSensor), myO2OutFlowRateSensor
					.getModuleName(), myO2OutFlowRateSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureO2OutFlowRateSensor(Node node) {
		O2OutFlowRateSensor myO2OutFlowRateSensor = O2OutFlowRateSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myO2OutFlowRateSensor.setInput(
				O2ProducerHelper.narrow(BiosimInitializer.getModule(myID,
						getInputName(node))), getFlowRateIndex(node));
		mySensors.add(myO2OutFlowRateSensor);
	}

	private void createH2InFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating H2InFlowRateSensor with moduleName: "
					+ moduleName);
			H2InFlowRateSensor myH2InFlowRateSensor = new H2InFlowRateSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myH2InFlowRateSensor, node);
			BiosimServer.registerServer(new H2InFlowRateSensorPOATie(
					myH2InFlowRateSensor), myH2InFlowRateSensor
					.getModuleName(), myH2InFlowRateSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureH2InFlowRateSensor(Node node) {
		H2InFlowRateSensor myH2InFlowRateSensor = H2InFlowRateSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myH2InFlowRateSensor.setInput(H2ConsumerHelper.narrow(BiosimInitializer
				.getModule(myID, getInputName(node))), getFlowRateIndex(node));
		mySensors.add(myH2InFlowRateSensor);
	}

	private void createH2OutFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating H2OutFlowRateSensor with moduleName: "
					+ moduleName);
			H2OutFlowRateSensor myH2OutFlowRateSensor = new H2OutFlowRateSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myH2OutFlowRateSensor, node);
			BiosimServer.registerServer(new H2OutFlowRateSensorPOATie(
					myH2OutFlowRateSensor), myH2OutFlowRateSensor
					.getModuleName(), myH2OutFlowRateSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureH2OutFlowRateSensor(Node node) {
		H2OutFlowRateSensor myH2OutFlowRateSensor = H2OutFlowRateSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myH2OutFlowRateSensor.setInput(
				H2ProducerHelper.narrow(BiosimInitializer.getModule(myID,
						getInputName(node))), getFlowRateIndex(node));
		mySensors.add(myH2OutFlowRateSensor);
	}

	private void createNitrogenInFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger
					.debug("Creating NitrogenInFlowRateSensor with moduleName: "
							+ moduleName);
			NitrogenInFlowRateSensor myNitrogenInFlowRateSensor = new NitrogenInFlowRateSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myNitrogenInFlowRateSensor,
					node);
			BiosimServer.registerServer(new NitrogenInFlowRateSensorPOATie(
					myNitrogenInFlowRateSensor),
					myNitrogenInFlowRateSensor.getModuleName(),
					myNitrogenInFlowRateSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureNitrogenInFlowRateSensor(Node node) {
		NitrogenInFlowRateSensor myNitrogenInFlowRateSensor = NitrogenInFlowRateSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myNitrogenInFlowRateSensor.setInput(
				NitrogenConsumerHelper.narrow(BiosimInitializer.getModule(
						myID, getInputName(node))), getFlowRateIndex(node));
		mySensors.add(myNitrogenInFlowRateSensor);
	}

	private void createNitrogenOutFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger
					.debug("Creating NitrogenOutFlowRateSensor with moduleName: "
							+ moduleName);
			NitrogenOutFlowRateSensor myNitrogenOutFlowRateSensor = new NitrogenOutFlowRateSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myNitrogenOutFlowRateSensor,
					node);
			BiosimServer.registerServer(new NitrogenOutFlowRateSensorPOATie(
					myNitrogenOutFlowRateSensor),
					myNitrogenOutFlowRateSensor.getModuleName(),
					myNitrogenOutFlowRateSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureNitrogenOutFlowRateSensor(Node node) {
		NitrogenOutFlowRateSensor myNitrogenOutFlowRateSensor = NitrogenOutFlowRateSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myNitrogenOutFlowRateSensor.setInput(
				NitrogenProducerHelper.narrow(BiosimInitializer.getModule(
						myID, getInputName(node))), getFlowRateIndex(node));
		mySensors.add(myNitrogenOutFlowRateSensor);
	}

	private void createMethaneInFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating MethaneInFlowRateSensor with moduleName: "
					+ moduleName);
			MethaneInFlowRateSensor myMethaneInFlowRateSensor = new MethaneInFlowRateSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myMethaneInFlowRateSensor,
					node);
			BiosimServer.registerServer(new MethaneInFlowRateSensorPOATie(
					myMethaneInFlowRateSensor),
					myMethaneInFlowRateSensor.getModuleName(),
					myMethaneInFlowRateSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureMethaneInFlowRateSensor(Node node) {
		MethaneInFlowRateSensor myMethaneInFlowRateSensor = MethaneInFlowRateSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myMethaneInFlowRateSensor.setInput(
				MethaneConsumerHelper.narrow(BiosimInitializer.getModule(myID,
						getInputName(node))), getFlowRateIndex(node));
		mySensors.add(myMethaneInFlowRateSensor);
	}

	private void createMethaneOutFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger
					.debug("Creating MethaneOutFlowRateSensor with moduleName: "
							+ moduleName);
			MethaneOutFlowRateSensor myMethaneOutFlowRateSensor = new MethaneOutFlowRateSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myMethaneOutFlowRateSensor,
					node);
			BiosimServer.registerServer(new MethaneOutFlowRateSensorPOATie(
					myMethaneOutFlowRateSensor),
					myMethaneOutFlowRateSensor.getModuleName(),
					myMethaneOutFlowRateSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureMethaneOutFlowRateSensor(Node node) {
		MethaneOutFlowRateSensor myMethaneOutFlowRateSensor = MethaneOutFlowRateSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myMethaneOutFlowRateSensor.setInput(
				MethaneProducerHelper.narrow(BiosimInitializer.getModule(myID,
						getInputName(node))), getFlowRateIndex(node));
		mySensors.add(myMethaneOutFlowRateSensor);
	}

	private void crawlAirSensors(Node node, boolean firstPass) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("CO2InFlowRateSensor")) {
					if (firstPass)
						createCO2InFlowRateSensor(child);
					else
						configureCO2InFlowRateSensor(child);
				} else if (childName.equals("CO2OutFlowRateSensor")) {
					if (firstPass)
						createCO2OutFlowRateSensor(child);
					else
						configureCO2OutFlowRateSensor(child);
				} else if (childName.equals("O2InFlowRateSensor")) {
					if (firstPass)
						createO2InFlowRateSensor(child);
					else
						configureO2InFlowRateSensor(child);
				} else if (childName.equals("O2OutFlowRateSensor")) {
					if (firstPass)
						createO2OutFlowRateSensor(child);
					else
						configureO2OutFlowRateSensor(child);
				} else if (childName.equals("H2InFlowRateSensor")) {
					if (firstPass)
						createH2InFlowRateSensor(child);
					else
						configureH2InFlowRateSensor(child);
				} else if (childName.equals("H2OutFlowRateSensor")) {
					if (firstPass)
						createH2OutFlowRateSensor(child);
					else
						configureH2OutFlowRateSensor(child);
				} else if (childName.equals("NitrogenInFlowRateSensor")) {
					if (firstPass)
						createNitrogenInFlowRateSensor(child);
					else
						configureNitrogenInFlowRateSensor(child);
				} else if (childName.equals("NitrogenOutFlowRateSensor")) {
					if (firstPass)
						createNitrogenOutFlowRateSensor(child);
					else
						configureNitrogenOutFlowRateSensor(child);
				} else if (childName.equals("MethaneInFlowRateSensor")) {
					if (firstPass)
						createMethaneInFlowRateSensor(child);
					else
						configureMethaneInFlowRateSensor(child);
				} else if (childName.equals("MethaneOutFlowRateSensor")) {
					if (firstPass)
						createMethaneOutFlowRateSensor(child);
					else
						configureMethaneOutFlowRateSensor(child);
				}
			}
			child = child.getNextSibling();
		}
	}

	// Crew
	private void createCrewGroupDeathSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating CrewGroupDeathSensor with moduleName: "
					+ moduleName);
			CrewGroupDeathSensor myCrewGroupDeathSensor = new CrewGroupDeathSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myCrewGroupDeathSensor, node);
			BiosimServer.registerServer(new CrewGroupDeathSensorPOATie(
					myCrewGroupDeathSensor), myCrewGroupDeathSensor
					.getModuleName(), myCrewGroupDeathSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureCrewGroupDeathSensor(Node node) {
		CrewGroupDeathSensor myCrewGroupDeathSensor = CrewGroupDeathSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myCrewGroupDeathSensor
				.setInput(CrewGroupHelper.narrow(BiosimInitializer.getModule(
						myID, getInputName(node))));
		mySensors.add(myCrewGroupDeathSensor);
	}

	private void createCrewGroupAnyDeadSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating CrewGroupAnyDeadSensor with moduleName: "
					+ moduleName);
			CrewGroupAnyDeadSensor myCrewGroupAnyDeadSensor = new CrewGroupAnyDeadSensor(
					myID, moduleName);
			BiosimInitializer
					.setupBioModule(myCrewGroupAnyDeadSensor, node);
			BiosimServer.registerServer(new CrewGroupAnyDeadSensorPOATie(
					myCrewGroupAnyDeadSensor), myCrewGroupAnyDeadSensor
					.getModuleName(), myCrewGroupAnyDeadSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureCrewGroupAnyDeadSensor(Node node) {
		CrewGroupAnyDeadSensor myCrewGroupAnyDeadSensor = CrewGroupAnyDeadSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myCrewGroupAnyDeadSensor
				.setInput(CrewGroupHelper.narrow(BiosimInitializer.getModule(
						myID, getInputName(node))));
		mySensors.add(myCrewGroupAnyDeadSensor);
	}

	private void createCrewGroupProductivitySensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger
					.debug("Creating CrewGroupProductivitySensor with moduleName: "
							+ moduleName);
			CrewGroupProductivitySensor myCrewGroupProductivitySensor = new CrewGroupProductivitySensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myCrewGroupProductivitySensor,
					node);
			BiosimServer.registerServer(new CrewGroupProductivitySensorPOATie(
					myCrewGroupProductivitySensor),
					myCrewGroupProductivitySensor.getModuleName(),
					myCrewGroupProductivitySensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureCrewGroupProductivitySensor(Node node) {
		CrewGroupProductivitySensor myCrewGroupProductivitySensor = CrewGroupProductivitySensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myCrewGroupProductivitySensor
				.setInput(CrewGroupHelper.narrow(BiosimInitializer.getModule(
						myID, getInputName(node))));
		mySensors.add(myCrewGroupProductivitySensor);
	}

	private void crawlCrewSensors(Node node, boolean firstPass) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("CrewGroupDeathSensor")) {
					if (firstPass)
						createCrewGroupDeathSensor(child);
					else
						configureCrewGroupDeathSensor(child);
				} else if (childName.equals("CrewGroupAnyDeadSensor")) {
					if (firstPass)
						createCrewGroupAnyDeadSensor(child);
					else
						configureCrewGroupAnyDeadSensor(child);
				} else if (childName.equals("CrewGroupProductivitySensor")) {
					if (firstPass)
						createCrewGroupProductivitySensor(child);
					else
						configureCrewGroupProductivitySensor(child);
				}
			}
			child = child.getNextSibling();
		}
	}

	// Environment
	private void createAirInFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating AirInFlowRateSensor with moduleName: "
					+ moduleName);
			AirInFlowRateSensor myAirInFlowRateSensor = new AirInFlowRateSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myAirInFlowRateSensor, node);
			BiosimServer.registerServer(new AirInFlowRateSensorPOATie(
					myAirInFlowRateSensor), myAirInFlowRateSensor
					.getModuleName(), myAirInFlowRateSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureAirInFlowRateSensor(Node node) {
		AirInFlowRateSensor myAirInFlowRateSensor = AirInFlowRateSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myAirInFlowRateSensor.setInput(
				AirConsumerHelper.narrow(BiosimInitializer.getModule(myID,
						getInputName(node))), getFlowRateIndex(node));
		mySensors.add(myAirInFlowRateSensor);
	}

	private void createAirOutFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating AirOutFlowRateSensor with moduleName: "
					+ moduleName);
			AirOutFlowRateSensor myAirOutFlowRateSensor = new AirOutFlowRateSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myAirOutFlowRateSensor, node);
			BiosimServer.registerServer(new AirOutFlowRateSensorPOATie(
					myAirOutFlowRateSensor), myAirOutFlowRateSensor
					.getModuleName(), myAirOutFlowRateSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureAirOutFlowRateSensor(Node node) {
		AirOutFlowRateSensor myAirOutFlowRateSensor = AirOutFlowRateSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myAirOutFlowRateSensor.setInput(
				AirProducerHelper.narrow(BiosimInitializer.getModule(myID,
						getInputName(node))), getFlowRateIndex(node));
		mySensors.add(myAirOutFlowRateSensor);
	}

	private void createGasConcentrationSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating GasConcentrationSensor with moduleName: "
					+ moduleName);
			GasConcentrationSensor myGasConcentrationSensor = new GasConcentrationSensor(
					myID, moduleName);
			BiosimInitializer
					.setupBioModule(myGasConcentrationSensor, node);
			BiosimServer.registerServer(new GasConcentrationSensorPOATie(
					myGasConcentrationSensor), myGasConcentrationSensor
					.getModuleName(), myGasConcentrationSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);

	}

	private void configureGasConcentrationSensor(Node node) {
		GasConcentrationSensor myGasConcentrationSensor = GasConcentrationSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		SimEnvironment inputEnvironment = SimEnvironmentHelper
				.narrow(BiosimInitializer.getModule(myID, getInputName(node)));
		myGasConcentrationSensor.setInput(inputEnvironment, getGasStore(node,
				inputEnvironment));
		mySensors.add(myGasConcentrationSensor);
	}

	private EnvironmentStore getGasStore(Node node,
			SimEnvironment pSimEnvironment) {
		String gasString = node.getAttributes().getNamedItem("gasType")
				.getNodeValue();
		if (gasString.equals("O2"))
			return pSimEnvironment.getO2Store();
		else if (gasString.equals("CO2"))
			return pSimEnvironment.getCO2Store();
		else if (gasString.equals("NITROGEN"))
			return pSimEnvironment.getNitrogenStore();
		else if (gasString.equals("VAPOR"))
			return pSimEnvironment.getVaporStore();
		else if (gasString.equals("OTHER"))
			return pSimEnvironment.getOtherStore();
		else
			return null;

	}

	private void createGasPressureSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating GasPressureSensor with moduleName: "
					+ moduleName);
			GasPressureSensor myGasPressureSensor = new GasPressureSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myGasPressureSensor, node);
			BiosimServer.registerServer(new GasPressureSensorPOATie(
					myGasPressureSensor), myGasPressureSensor
					.getModuleName(), myGasPressureSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureGasPressureSensor(Node node) {
		GasPressureSensor myGasPressureSensor = GasPressureSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		SimEnvironment inputEnvironment = SimEnvironmentHelper
				.narrow(BiosimInitializer.getModule(myID, getInputName(node)));
		myGasPressureSensor.setInput(getGasStore(node, inputEnvironment));
		mySensors.add(myGasPressureSensor);
	}
	
	private void createTotalMolesSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating TotalMolesSensor with moduleName: "
					+ moduleName);
			TotalMolesSensor myTotalMolesSensor = new TotalMolesSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myTotalMolesSensor, node);
			BiosimServer.registerServer(new TotalMolesSensorPOATie(
					myTotalMolesSensor), myTotalMolesSensor
					.getModuleName(), myTotalMolesSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureTotalMolesSensor(Node node) {
		TotalMolesSensor myTotalMolesSensor = TotalMolesSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		SimEnvironment inputEnvironment = SimEnvironmentHelper
				.narrow(BiosimInitializer.getModule(myID, getInputName(node)));
		myTotalMolesSensor.setInput(inputEnvironment);
		mySensors.add(myTotalMolesSensor);
	}
	
	private void createTotalPressureSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating TotalPressureSensor with moduleName: "
					+ moduleName);
			TotalPressureSensor myTotalPressureSensor = new TotalPressureSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myTotalPressureSensor, node);
			BiosimServer.registerServer(new TotalPressureSensorPOATie(
					myTotalPressureSensor), myTotalPressureSensor
					.getModuleName(), myTotalPressureSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureTotalPressureSensor(Node node) {
		TotalPressureSensor myTotalPressureSensor = TotalPressureSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		SimEnvironment inputEnvironment = SimEnvironmentHelper
				.narrow(BiosimInitializer.getModule(myID, getInputName(node)));
		myTotalPressureSensor.setInput(inputEnvironment);
		mySensors.add(myTotalPressureSensor);
	}

	private void crawlEnvironmentSensors(Node node, boolean firstPass) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("AirInFlowRateSensor")) {
					if (firstPass)
						createAirInFlowRateSensor(child);
					else
						configureAirInFlowRateSensor(child);
				} else if (childName.equals("AirOutFlowRateSensor")) {
					if (firstPass)
						createAirOutFlowRateSensor(child);
					else
						configureAirOutFlowRateSensor(child);
				} else if (childName.equals("GasPressureSensor")) {
					if (firstPass)
						createGasPressureSensor(child);
					else
						configureGasPressureSensor(child);
				} else if (childName.equals("GasConcentrationSensor")) {
					if (firstPass)
						createGasConcentrationSensor(child);
					else
						configureGasConcentrationSensor(child);
				} else if (childName.equals("TotalMolesSensor")) {
					if (firstPass)
						createTotalMolesSensor(child);
					else
						configureTotalMolesSensor(child);
				} else if (childName.equals("TotalPressureSensor")) {
					if (firstPass)
						createTotalPressureSensor(child);
					else
						configureTotalPressureSensor(child);
				}
			}
			child = child.getNextSibling();
		}
	}

	// Food
	private void createBiomassInFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating BiomassInFlowRateSensor with moduleName: "
					+ moduleName);
			BiomassInFlowRateSensor myBiomassInFlowRateSensor = new BiomassInFlowRateSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myBiomassInFlowRateSensor,
					node);
			BiosimServer.registerServer(new BiomassInFlowRateSensorPOATie(
					myBiomassInFlowRateSensor),
					myBiomassInFlowRateSensor.getModuleName(),
					myBiomassInFlowRateSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureBiomassInFlowRateSensor(Node node) {
		BiomassInFlowRateSensor myBiomassInFlowRateSensor = BiomassInFlowRateSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myBiomassInFlowRateSensor.setInput(
				BiomassConsumerHelper.narrow(BiosimInitializer.getModule(myID,
						getInputName(node))), getFlowRateIndex(node));
		mySensors.add(myBiomassInFlowRateSensor);
	}

	private void createBiomassOutFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger
					.debug("Creating BiomassOutFlowRateSensor with moduleName: "
							+ moduleName);
			BiomassOutFlowRateSensor myBiomassOutFlowRateSensor = new BiomassOutFlowRateSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myBiomassOutFlowRateSensor,
					node);
			BiosimServer.registerServer(new BiomassOutFlowRateSensorPOATie(
					myBiomassOutFlowRateSensor),
					myBiomassOutFlowRateSensor.getModuleName(),
					myBiomassOutFlowRateSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureBiomassOutFlowRateSensor(Node node) {
		BiomassOutFlowRateSensor myBiomassOutFlowRateSensor = BiomassOutFlowRateSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myBiomassOutFlowRateSensor.setInput(
				BiomassProducerHelper.narrow(BiosimInitializer.getModule(myID,
						getInputName(node))), getFlowRateIndex(node));
		mySensors.add(myBiomassOutFlowRateSensor);
	}

	private void createFoodInFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating FoodInFlowRateSensor with moduleName: "
					+ moduleName);
			FoodInFlowRateSensor myFoodInFlowRateSensor = new FoodInFlowRateSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myFoodInFlowRateSensor, node);
			BiosimServer.registerServer(new FoodInFlowRateSensorPOATie(
					myFoodInFlowRateSensor), myFoodInFlowRateSensor
					.getModuleName(), myFoodInFlowRateSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureFoodInFlowRateSensor(Node node) {
		FoodInFlowRateSensor myFoodInFlowRateSensor = FoodInFlowRateSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myFoodInFlowRateSensor.setInput(
				FoodConsumerHelper.narrow(BiosimInitializer.getModule(myID,
						getInputName(node))), getFlowRateIndex(node));
		mySensors.add(myFoodInFlowRateSensor);
	}

	private void createFoodOutFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating FoodOutFlowRateSensor with moduleName: "
					+ moduleName);
			FoodOutFlowRateSensor myFoodOutFlowRateSensor = new FoodOutFlowRateSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myFoodOutFlowRateSensor, node);
			BiosimServer.registerServer(new FoodOutFlowRateSensorPOATie(
					myFoodOutFlowRateSensor), myFoodOutFlowRateSensor
					.getModuleName(), myFoodOutFlowRateSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureFoodOutFlowRateSensor(Node node) {
		FoodOutFlowRateSensor myFoodOutFlowRateSensor = FoodOutFlowRateSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myFoodOutFlowRateSensor.setInput(
				FoodProducerHelper.narrow(BiosimInitializer.getModule(myID,
						getInputName(node))), getFlowRateIndex(node));
		mySensors.add(myFoodOutFlowRateSensor);
	}

	private void configureBiomassStoreWaterContentSensor(Node node) {
		BiomassStoreWaterContentSensor myBiomassStoreWaterContentSensor = BiomassStoreWaterContentSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myBiomassStoreWaterContentSensor
				.setInput(BiomassStoreHelper.narrow(BiosimInitializer
						.getModule(myID, getInputName(node))));
		mySensors.add(myBiomassStoreWaterContentSensor);
	}

	private void createBiomassStoreWaterContentSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger
					.debug("Creating BiomassStoreWaterContentSensor with moduleName: "
							+ moduleName);
			BiomassStoreWaterContentSensor myBiomassStoreWaterContentSensor = new BiomassStoreWaterContentSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(
					myBiomassStoreWaterContentSensor, node);
			BiosimServer.registerServer(
					new BiomassStoreWaterContentSensorPOATie(
							myBiomassStoreWaterContentSensor),
					myBiomassStoreWaterContentSensor.getModuleName(),
					myBiomassStoreWaterContentSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void createHarvestSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating HarvestSensor with moduleName: "
					+ moduleName);
			HarvestSensor myHarvestSensor = new HarvestSensor(myID,
					moduleName);
			BiosimInitializer.setupBioModule(myHarvestSensor, node);
			BiosimServer.registerServer(new HarvestSensorPOATie(
					myHarvestSensor), myHarvestSensor.getModuleName(),
					myHarvestSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureHarvestSensor(Node node) {
		try {
			int index = XMLUtils.getIntAttribute(node, "shelfIndex");
			HarvestSensor myHarvestSensor = HarvestSensorHelper
					.narrow(BiosimInitializer.getModule(myID,
							BiosimInitializer.getModuleName(node)));
			myHarvestSensor.setInput(BiomassPSHelper.narrow(BiosimInitializer
					.getModule(myID, getInputName(node))), index);
			mySensors.add(myHarvestSensor);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	private void createPlantDeathSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating PlantDeathSensor with moduleName: "
					+ moduleName);
			PlantDeathSensor myPlantDeathSensor = new PlantDeathSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myPlantDeathSensor, node);
			BiosimServer.registerServer(new PlantDeathSensorPOATie(
					myPlantDeathSensor), myPlantDeathSensor
					.getModuleName(), myPlantDeathSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configurePlantDeathSensor(Node node) {
		try {
			int index = XMLUtils.getIntAttribute(node, "shelfIndex");
			PlantDeathSensor myPlantDeathSensor = PlantDeathSensorHelper
					.narrow(BiosimInitializer.getModule(myID,
							BiosimInitializer.getModuleName(node)));
			myPlantDeathSensor.setInput(BiomassPSHelper
					.narrow(BiosimInitializer.getModule(myID,
							getInputName(node))), index);
			mySensors.add(myPlantDeathSensor);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	private void createTimeTillCanopyClosureSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger
					.debug("Creating TimeTillCanopyClosureSensor with moduleName: "
							+ moduleName);
			TimeTillCanopyClosureSensor myTimeTillCanopyClosureSensor = new TimeTillCanopyClosureSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myTimeTillCanopyClosureSensor,
					node);
			BiosimServer.registerServer(new TimeTillCanopyClosureSensorPOATie(
					myTimeTillCanopyClosureSensor),
					myTimeTillCanopyClosureSensor.getModuleName(),
					myTimeTillCanopyClosureSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureTimeTillCanopyClosureSensor(Node node) {
		try {
			int index = XMLUtils.getIntAttribute(node, "shelfIndex");
			TimeTillCanopyClosureSensor myTimeTillCanopyClosureSensor = TimeTillCanopyClosureSensorHelper
					.narrow(BiosimInitializer.getModule(myID,
							BiosimInitializer.getModuleName(node)));
			myTimeTillCanopyClosureSensor.setInput(BiomassPSHelper
					.narrow(BiosimInitializer.getModule(myID,
							getInputName(node))), index);
			mySensors.add(myTimeTillCanopyClosureSensor);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	private void crawlFoodSensors(Node node, boolean firstPass) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("BiomassInFlowRateSensor")) {
					if (firstPass)
						createBiomassInFlowRateSensor(child);
					else
						configureBiomassInFlowRateSensor(child);
				} else if (childName.equals("BiomassOutFlowRateSensor")) {
					if (firstPass)
						createBiomassOutFlowRateSensor(child);
					else
						configureBiomassOutFlowRateSensor(child);
				} else if (childName.equals("BiomassStoreWaterContentSensor")) {
					if (firstPass)
						createBiomassStoreWaterContentSensor(child);
					else
						configureBiomassStoreWaterContentSensor(child);
				} else if (childName.equals("FoodInFlowRateSensor")) {
					if (firstPass)
						createFoodInFlowRateSensor(child);
					else
						configureFoodInFlowRateSensor(child);
				} else if (childName.equals("FoodOutFlowRateSensor")) {
					if (firstPass)
						createFoodOutFlowRateSensor(child);
					else
						configureFoodOutFlowRateSensor(child);
				} else if (childName.equals("HarvestSensor")) {
					if (firstPass)
						createHarvestSensor(child);
					else
						configureHarvestSensor(child);
				} else if (childName.equals("PlantDeathSensor")) {
					if (firstPass)
						createPlantDeathSensor(child);
					else
						configurePlantDeathSensor(child);
				} else if (childName.equals("TimeTillCanopyClosureSensor")) {
					if (firstPass)
						createTimeTillCanopyClosureSensor(child);
					else
						configureTimeTillCanopyClosureSensor(child);
				}
			}
			child = child.getNextSibling();
		}
	}

	// Framework
	private void createStoreLevelSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating StoreLevelSensor with moduleName: "
					+ moduleName);
			StoreLevelSensor myStoreLevelSensor = new StoreLevelSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myStoreLevelSensor, node);
			BiosimServer.registerServer(new StoreLevelSensorPOATie(
					myStoreLevelSensor), myStoreLevelSensor
					.getModuleName(), myStoreLevelSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureStoreLevelSensor(Node node) {
		StoreLevelSensor myStoreLevelSensor = StoreLevelSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myStoreLevelSensor.setInput(StoreHelper.narrow(BiosimInitializer
				.getModule(myID, getInputName(node))));
		mySensors.add(myStoreLevelSensor);
	}

	private void createStoreOverflowSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating StoreOverflowSensor with moduleName: "
					+ moduleName);
			StoreOverflowSensor myStoreOverflowSensor = new StoreOverflowSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myStoreOverflowSensor, node);
			BiosimServer.registerServer(new StoreOverflowSensorPOATie(
					myStoreOverflowSensor), myStoreOverflowSensor
					.getModuleName(), myStoreOverflowSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureStoreOverflowSensor(Node node) {
		StoreOverflowSensor myStoreOverflowSensor = StoreOverflowSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myStoreOverflowSensor.setInput(StoreHelper.narrow(BiosimInitializer
				.getModule(myID, getInputName(node))));
		mySensors.add(myStoreOverflowSensor);
	}
	
	private void createTimeSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating TimeSensor with moduleName: "
					+ moduleName);
			TimeSensor myTimeSensor = new TimeSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myTimeSensor, node);
			BiosimServer.registerServer(new TimeSensorPOATie(
					myTimeSensor), myTimeSensor
					.getModuleName(), myTimeSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureTimeSensor(Node node) {
		TimeSensor myTimeSensor = TimeSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		mySensors.add(myTimeSensor);
	}

	private void createInfluentValveStateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger
					.debug("Creating InfluentValveStateSensor with moduleName: "
							+ moduleName);
			InfluentValveStateSensor myInfluentValveStateSensor = new InfluentValveStateSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myInfluentValveStateSensor,
					node);
			BiosimServer.registerServer(new InfluentValveStateSensorPOATie(
					myInfluentValveStateSensor),
					myInfluentValveStateSensor.getModuleName(),
					myInfluentValveStateSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureInfluentValveStateSensor(Node node) {
		InfluentValveStateSensor myInfluentValveStateSensor = InfluentValveStateSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myInfluentValveStateSensor
				.setInput(InfluentValveHelper.narrow(BiosimInitializer
						.getModule(myID, getInputName(node))));
		mySensors.add(myInfluentValveStateSensor);
	}

	private void createEffluentValveStateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger
					.debug("Creating EffluentValveStateSensor with moduleName: "
							+ moduleName);
			EffluentValveStateSensor myEffluentValveStateSensor = new EffluentValveStateSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myEffluentValveStateSensor,
					node);
			BiosimServer.registerServer(new EffluentValveStateSensorPOATie(
					myEffluentValveStateSensor),
					myEffluentValveStateSensor.getModuleName(),
					myEffluentValveStateSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureEffluentValveStateSensor(Node node) {
		EffluentValveStateSensor myEffluentValveStateSensor = EffluentValveStateSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myEffluentValveStateSensor
				.setInput(EffluentValveHelper.narrow(BiosimInitializer
						.getModule(myID, getInputName(node))));
		mySensors.add(myEffluentValveStateSensor);
	}

	private void crawlFrameworkSensors(Node node, boolean firstPass) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("StoreLevelSensor")) {
					if (firstPass)
						createStoreLevelSensor(child);
					else
						configureStoreLevelSensor(child);
				} else if (childName.equals("StoreOverflowSensor")) {
					if (firstPass)
						createStoreOverflowSensor(child);
					else
						configureStoreOverflowSensor(child);
				} else if (childName.equals("InfluentValveStateSensor")) {
					if (firstPass)
						createInfluentValveStateSensor(child);
					else
						configureInfluentValveStateSensor(child);
				} else if (childName.equals("EffluentValveStateSensor")) {
					if (firstPass)
						createEffluentValveStateSensor(child);
					else
						configureEffluentValveStateSensor(child);
				} else if (childName.equals("TimeSensor")) {
					if (firstPass)
						createTimeSensor(child);
					else
						configureTimeSensor(child);
				} 
			}
			child = child.getNextSibling();
		}
	}

	// Power
	private void createPowerInFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating PowerInFlowRateSensor with moduleName: "
					+ moduleName);
			PowerInFlowRateSensor myPowerInFlowRateSensor = new PowerInFlowRateSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myPowerInFlowRateSensor, node);
			BiosimServer.registerServer(new PowerInFlowRateSensorPOATie(
					myPowerInFlowRateSensor), myPowerInFlowRateSensor
					.getModuleName(), myPowerInFlowRateSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configurePowerInFlowRateSensor(Node node) {
		PowerInFlowRateSensor myPowerInFlowRateSensor = PowerInFlowRateSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myPowerInFlowRateSensor.setInput(
				PowerConsumerHelper.narrow(BiosimInitializer.getModule(myID,
						getInputName(node))), getFlowRateIndex(node));
		mySensors.add(myPowerInFlowRateSensor);
	}

	private void createPowerOutFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating PowerOutFlowRateSensor with moduleName: "
					+ moduleName);
			PowerOutFlowRateSensor myPowerOutFlowRateSensor = new PowerOutFlowRateSensor(
					myID, moduleName);
			BiosimInitializer
					.setupBioModule(myPowerOutFlowRateSensor, node);
			BiosimServer.registerServer(new PowerOutFlowRateSensorPOATie(
					myPowerOutFlowRateSensor), myPowerOutFlowRateSensor
					.getModuleName(), myPowerOutFlowRateSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configurePowerOutFlowRateSensor(Node node) {
		PowerOutFlowRateSensor myPowerOutFlowRateSensor = PowerOutFlowRateSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myPowerOutFlowRateSensor.setInput(
				PowerProducerHelper.narrow(BiosimInitializer.getModule(myID,
						getInputName(node))), getFlowRateIndex(node));
		mySensors.add(myPowerOutFlowRateSensor);
	}

	private void crawlPowerSensors(Node node, boolean firstPass) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("PowerInFlowRateSensor")) {
					if (firstPass)
						createPowerInFlowRateSensor(child);
					else
						configurePowerInFlowRateSensor(child);
				} else if (childName.equals("PowerOutFlowRateSensor")) {
					if (firstPass)
						createPowerOutFlowRateSensor(child);
					else
						configurePowerOutFlowRateSensor(child);
				}
			}
			child = child.getNextSibling();
		}
	}

	// Water
	private void createPotableWaterInFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger
					.debug("Creating PotableWaterInFlowRateSensor with moduleName: "
							+ moduleName);
			PotableWaterInFlowRateSensor myPotableWaterInFlowRateSensor = new PotableWaterInFlowRateSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(
					myPotableWaterInFlowRateSensor, node);
			BiosimServer.registerServer(new PotableWaterInFlowRateSensorPOATie(
					myPotableWaterInFlowRateSensor),
					myPotableWaterInFlowRateSensor.getModuleName(),
					myPotableWaterInFlowRateSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configurePotableWaterInFlowRateSensor(Node node) {
		PotableWaterInFlowRateSensor myPotableWaterInFlowRateSensor = PotableWaterInFlowRateSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myPotableWaterInFlowRateSensor.setInput(
				PotableWaterConsumerHelper.narrow(BiosimInitializer.getModule(
						myID, getInputName(node))), getFlowRateIndex(node));
		mySensors.add(myPotableWaterInFlowRateSensor);
	}

	private void createPotableWaterOutFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger
					.debug("Creating PotableWaterOutFlowRateSensor with moduleName: "
							+ moduleName);
			PotableWaterOutFlowRateSensor myPotableWaterOutFlowRateSensor = new PotableWaterOutFlowRateSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(
					myPotableWaterOutFlowRateSensor, node);
			BiosimServer.registerServer(
					new PotableWaterOutFlowRateSensorPOATie(
							myPotableWaterOutFlowRateSensor),
					myPotableWaterOutFlowRateSensor.getModuleName(),
					myPotableWaterOutFlowRateSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configurePotableWaterOutFlowRateSensor(Node node) {
		PotableWaterOutFlowRateSensor myPotableWaterOutFlowRateSensor = PotableWaterOutFlowRateSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myPotableWaterOutFlowRateSensor.setInput(
				PotableWaterProducerHelper.narrow(BiosimInitializer.getModule(
						myID, getInputName(node))), getFlowRateIndex(node));
		mySensors.add(myPotableWaterOutFlowRateSensor);
	}

	private void createGreyWaterInFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger
					.debug("Creating GreyWaterInFlowRateSensor with moduleName: "
							+ moduleName);
			GreyWaterInFlowRateSensor myGreyWaterInFlowRateSensor = new GreyWaterInFlowRateSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myGreyWaterInFlowRateSensor,
					node);
			BiosimServer.registerServer(new GreyWaterInFlowRateSensorPOATie(
					myGreyWaterInFlowRateSensor),
					myGreyWaterInFlowRateSensor.getModuleName(),
					myGreyWaterInFlowRateSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureGreyWaterInFlowRateSensor(Node node) {
		GreyWaterInFlowRateSensor myGreyWaterInFlowRateSensor = GreyWaterInFlowRateSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myGreyWaterInFlowRateSensor.setInput(
				GreyWaterConsumerHelper.narrow(BiosimInitializer.getModule(
						myID, getInputName(node))), getFlowRateIndex(node));
		mySensors.add(myGreyWaterInFlowRateSensor);
	}

	private void createGreyWaterOutFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger
					.debug("Creating GreyWaterOutFlowRateSensor with moduleName: "
							+ moduleName);
			GreyWaterOutFlowRateSensor myGreyWaterOutFlowRateSensor = new GreyWaterOutFlowRateSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myGreyWaterOutFlowRateSensor,
					node);
			BiosimServer.registerServer(new GreyWaterOutFlowRateSensorPOATie(
					myGreyWaterOutFlowRateSensor),
					myGreyWaterOutFlowRateSensor.getModuleName(),
					myGreyWaterOutFlowRateSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureGreyWaterOutFlowRateSensor(Node node) {
		GreyWaterOutFlowRateSensor myGreyWaterOutFlowRateSensor = GreyWaterOutFlowRateSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myGreyWaterOutFlowRateSensor.setInput(
				GreyWaterProducerHelper.narrow(BiosimInitializer.getModule(
						myID, getInputName(node))), getFlowRateIndex(node));
		mySensors.add(myGreyWaterOutFlowRateSensor);
	}

	private void createDirtyWaterInFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger
					.debug("Creating DirtyWaterInFlowRateSensor with moduleName: "
							+ moduleName);
			DirtyWaterInFlowRateSensor myDirtyWaterInFlowRateSensor = new DirtyWaterInFlowRateSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myDirtyWaterInFlowRateSensor,
					node);
			BiosimServer.registerServer(new DirtyWaterInFlowRateSensorPOATie(
					myDirtyWaterInFlowRateSensor),
					myDirtyWaterInFlowRateSensor.getModuleName(),
					myDirtyWaterInFlowRateSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureDirtyWaterInFlowRateSensor(Node node) {
		DirtyWaterInFlowRateSensor myDirtyWaterInFlowRateSensor = DirtyWaterInFlowRateSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myDirtyWaterInFlowRateSensor.setInput(
				DirtyWaterConsumerHelper.narrow(BiosimInitializer.getModule(
						myID, getInputName(node))), getFlowRateIndex(node));
		mySensors.add(myDirtyWaterInFlowRateSensor);
	}

	private void createDirtyWaterOutFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger
					.debug("Creating DirtyWaterOutFlowRateSensor with moduleName: "
							+ moduleName);
			DirtyWaterOutFlowRateSensor myDirtyWaterOutFlowRateSensor = new DirtyWaterOutFlowRateSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myDirtyWaterOutFlowRateSensor,
					node);
			BiosimServer.registerServer(new DirtyWaterOutFlowRateSensorPOATie(
					myDirtyWaterOutFlowRateSensor),
					myDirtyWaterOutFlowRateSensor.getModuleName(),
					myDirtyWaterOutFlowRateSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureDirtyWaterOutFlowRateSensor(Node node) {
		DirtyWaterOutFlowRateSensor myDirtyWaterOutFlowRateSensor = DirtyWaterOutFlowRateSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myDirtyWaterOutFlowRateSensor.setInput(
				DirtyWaterProducerHelper.narrow(BiosimInitializer.getModule(
						myID, getInputName(node))), getFlowRateIndex(node));
		mySensors.add(myDirtyWaterOutFlowRateSensor);
	}

	private void crawlWaterSensors(Node node, boolean firstPass) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("PotableWaterInFlowRateSensor")) {
					if (firstPass)
						createPotableWaterInFlowRateSensor(child);
					else
						configurePotableWaterInFlowRateSensor(child);
				} else if (childName.equals("PotableWaterOutFlowRateSensor")) {
					if (firstPass)
						createPotableWaterOutFlowRateSensor(child);
					else
						configurePotableWaterOutFlowRateSensor(child);
				} else if (childName.equals("GreyWaterInFlowRateSensor")) {
					if (firstPass)
						createGreyWaterInFlowRateSensor(child);
					else
						configureGreyWaterInFlowRateSensor(child);
				} else if (childName.equals("GreyWaterOutFlowRateSensor")) {
					if (firstPass)
						createGreyWaterOutFlowRateSensor(child);
					else
						configureGreyWaterOutFlowRateSensor(child);
				} else if (childName.equals("DirtyWaterInFlowRateSensor")) {
					if (firstPass)
						createDirtyWaterInFlowRateSensor(child);
					else
						configureDirtyWaterInFlowRateSensor(child);
				} else if (childName.equals("DirtyWaterOutFlowRateSensor")) {
					if (firstPass)
						createDirtyWaterOutFlowRateSensor(child);
					else
						configureDirtyWaterOutFlowRateSensor(child);
				}
			}
			child = child.getNextSibling();
		}
	}

	// Waste
	private void createDryWasteInFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger
					.debug("Creating DryWasteInFlowRateSensor with moduleName: "
							+ moduleName);
			DryWasteInFlowRateSensor myDryWasteInFlowRateSensor = new DryWasteInFlowRateSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myDryWasteInFlowRateSensor,
					node);
			BiosimServer.registerServer(new DryWasteInFlowRateSensorPOATie(
					myDryWasteInFlowRateSensor),
					myDryWasteInFlowRateSensor.getModuleName(),
					myDryWasteInFlowRateSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureDryWasteInFlowRateSensor(Node node) {
		DryWasteInFlowRateSensor myDryWasteInFlowRateSensor = DryWasteInFlowRateSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myDryWasteInFlowRateSensor.setInput(
				DryWasteConsumerHelper.narrow(BiosimInitializer.getModule(
						myID, getInputName(node))), getFlowRateIndex(node));
		mySensors.add(myDryWasteInFlowRateSensor);
	}

	private void createDryWasteOutFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger
					.debug("Creating DryWasteOutFlowRateSensor with moduleName: "
							+ moduleName);
			DryWasteOutFlowRateSensor myDryWasteOutFlowRateSensor = new DryWasteOutFlowRateSensor(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myDryWasteOutFlowRateSensor,
					node);
			BiosimServer.registerServer(new DryWasteOutFlowRateSensorPOATie(
					myDryWasteOutFlowRateSensor),
					myDryWasteOutFlowRateSensor.getModuleName(),
					myDryWasteOutFlowRateSensor.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureDryWasteOutFlowRateSensor(Node node) {
		DryWasteOutFlowRateSensor myDryWasteOutFlowRateSensor = DryWasteOutFlowRateSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myDryWasteOutFlowRateSensor.setInput(
				DryWasteProducerHelper.narrow(BiosimInitializer.getModule(
						myID, getInputName(node))), getFlowRateIndex(node));
		mySensors.add(myDryWasteOutFlowRateSensor);
	}

	private void crawlWasteSensors(Node node, boolean firstPass) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName.equals("DryWasteInFlowRateSensor")) {
				if (firstPass)
					createDryWasteInFlowRateSensor(child);
				else
					configureDryWasteInFlowRateSensor(child);
			} else if (childName.equals("DryWasteOutFlowRateSensor")) {
				if (firstPass)
					createDryWasteOutFlowRateSensor(child);
				else
					configureDryWasteOutFlowRateSensor(child);
			}
			child = child.getNextSibling();
		}
	}

	/**
	 * @return Returns the mySensors.
	 */
	public Set<GenericSensor> getSensors() {
		return mySensors;
	}
}