package com.traclabs.biosim.server.sensor.framework;

import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.w3c.dom.Node;

import com.traclabs.biosim.idl.sensor.air.CO2InFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.CO2InFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.CO2InFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.air.CO2OutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.CO2OutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.CO2OutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.air.H2InFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.H2InFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.H2InFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.air.H2OutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.H2OutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.H2OutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.air.MethaneInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.MethaneInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.MethaneInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.air.MethaneOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.MethaneOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.MethaneOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.air.NitrogenInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.NitrogenInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.NitrogenInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.air.NitrogenOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.NitrogenOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.NitrogenOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.air.O2InFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.O2InFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.O2InFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.air.O2OutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.air.O2OutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.O2OutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupAnyDeadSensor;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupAnyDeadSensorHelper;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupAnyDeadSensorPOATie;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupDeathSensor;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupDeathSensorHelper;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupDeathSensorPOATie;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupProductivitySensor;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupProductivitySensorHelper;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupProductivitySensorPOATie;
import com.traclabs.biosim.idl.sensor.environment.AirInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.AirInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.AirInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.environment.AirOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.environment.AirOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.AirOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.environment.GasConcentrationSensor;
import com.traclabs.biosim.idl.sensor.environment.GasConcentrationSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.GasConcentrationSensorPOATie;
import com.traclabs.biosim.idl.sensor.environment.GasPressureSensor;
import com.traclabs.biosim.idl.sensor.environment.GasPressureSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.GasPressureSensorPOATie;
import com.traclabs.biosim.idl.sensor.environment.TotalMolesSensor;
import com.traclabs.biosim.idl.sensor.environment.TotalMolesSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.TotalMolesSensorPOATie;
import com.traclabs.biosim.idl.sensor.environment.TotalPressureSensor;
import com.traclabs.biosim.idl.sensor.environment.TotalPressureSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.TotalPressureSensorPOATie;
import com.traclabs.biosim.idl.sensor.food.BiomassInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.food.BiomassInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.food.BiomassInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.food.BiomassOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.food.BiomassOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.food.BiomassOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.food.BiomassStoreWaterContentSensor;
import com.traclabs.biosim.idl.sensor.food.BiomassStoreWaterContentSensorHelper;
import com.traclabs.biosim.idl.sensor.food.BiomassStoreWaterContentSensorPOATie;
import com.traclabs.biosim.idl.sensor.food.FoodInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.food.FoodInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.food.FoodInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.food.FoodOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.food.FoodOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.food.FoodOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.food.HarvestSensor;
import com.traclabs.biosim.idl.sensor.food.HarvestSensorHelper;
import com.traclabs.biosim.idl.sensor.food.HarvestSensorPOATie;
import com.traclabs.biosim.idl.sensor.food.PlantDeathSensor;
import com.traclabs.biosim.idl.sensor.food.PlantDeathSensorHelper;
import com.traclabs.biosim.idl.sensor.food.PlantDeathSensorPOATie;
import com.traclabs.biosim.idl.sensor.food.TimeTillCanopyClosureSensor;
import com.traclabs.biosim.idl.sensor.food.TimeTillCanopyClosureSensorHelper;
import com.traclabs.biosim.idl.sensor.food.TimeTillCanopyClosureSensorPOATie;
import com.traclabs.biosim.idl.sensor.framework.EffluentValveStateSensor;
import com.traclabs.biosim.idl.sensor.framework.EffluentValveStateSensorHelper;
import com.traclabs.biosim.idl.sensor.framework.EffluentValveStateSensorPOATie;
import com.traclabs.biosim.idl.sensor.framework.GenericSensor;
import com.traclabs.biosim.idl.sensor.framework.InfluentValveStateSensor;
import com.traclabs.biosim.idl.sensor.framework.InfluentValveStateSensorHelper;
import com.traclabs.biosim.idl.sensor.framework.InfluentValveStateSensorPOATie;
import com.traclabs.biosim.idl.sensor.framework.StoreLevelSensor;
import com.traclabs.biosim.idl.sensor.framework.StoreLevelSensorHelper;
import com.traclabs.biosim.idl.sensor.framework.StoreLevelSensorPOATie;
import com.traclabs.biosim.idl.sensor.framework.StoreOverflowSensor;
import com.traclabs.biosim.idl.sensor.framework.StoreOverflowSensorHelper;
import com.traclabs.biosim.idl.sensor.framework.StoreOverflowSensorPOATie;
import com.traclabs.biosim.idl.sensor.framework.TimeSensor;
import com.traclabs.biosim.idl.sensor.framework.TimeSensorHelper;
import com.traclabs.biosim.idl.sensor.framework.TimeSensorPOATie;
import com.traclabs.biosim.idl.sensor.power.PowerInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.power.PowerInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.power.PowerInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.power.PowerOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.power.PowerOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.power.PowerOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.waste.DryWasteInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.waste.DryWasteInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.waste.DryWasteInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.waste.DryWasteOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.waste.DryWasteOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.waste.DryWasteOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.water.DirtyWaterInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.DirtyWaterInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.DirtyWaterInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.water.DirtyWaterOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.DirtyWaterOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.DirtyWaterOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.water.GreyWaterInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.GreyWaterInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.GreyWaterInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.water.GreyWaterOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.GreyWaterOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.GreyWaterOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.water.PotableWaterInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.PotableWaterInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.PotableWaterInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.water.PotableWaterOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.PotableWaterOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.PotableWaterOutFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.water.WaterInFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.WaterInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.WaterInFlowRateSensorPOATie;
import com.traclabs.biosim.idl.sensor.water.WaterOutFlowRateSensor;
import com.traclabs.biosim.idl.sensor.water.WaterOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.WaterOutFlowRateSensorPOATie;
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
import com.traclabs.biosim.idl.simulation.crew.CrewGroupHelper;
import com.traclabs.biosim.idl.simulation.environment.AirConsumerHelper;
import com.traclabs.biosim.idl.simulation.environment.AirProducerHelper;
import com.traclabs.biosim.idl.simulation.environment.EnvironmentStore;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironment;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironmentHelper;
import com.traclabs.biosim.idl.simulation.food.BiomassConsumerHelper;
import com.traclabs.biosim.idl.simulation.food.BiomassPSHelper;
import com.traclabs.biosim.idl.simulation.food.BiomassProducerHelper;
import com.traclabs.biosim.idl.simulation.food.BiomassStoreHelper;
import com.traclabs.biosim.idl.simulation.food.FoodConsumerHelper;
import com.traclabs.biosim.idl.simulation.food.FoodProducerHelper;
import com.traclabs.biosim.idl.simulation.framework.EffluentValveHelper;
import com.traclabs.biosim.idl.simulation.framework.InfluentValveHelper;
import com.traclabs.biosim.idl.simulation.framework.StoreHelper;
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
import com.traclabs.biosim.idl.simulation.water.WaterConsumerHelper;
import com.traclabs.biosim.idl.simulation.water.WaterProducerHelper;
import com.traclabs.biosim.server.framework.BiosimInitializer;
import com.traclabs.biosim.server.framework.BiosimServer;
import com.traclabs.biosim.server.sensor.air.CO2InFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.air.CO2OutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.air.H2InFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.air.H2OutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.air.MethaneInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.air.MethaneOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.air.NitrogenInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.air.NitrogenOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.air.O2InFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.air.O2OutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.crew.CrewGroupAnyDeadSensorImpl;
import com.traclabs.biosim.server.sensor.crew.CrewGroupDeathSensorImpl;
import com.traclabs.biosim.server.sensor.crew.CrewGroupProductivitySensorImpl;
import com.traclabs.biosim.server.sensor.environment.AirInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.environment.AirOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.environment.GasConcentrationSensorImpl;
import com.traclabs.biosim.server.sensor.environment.GasPressureSensorImpl;
import com.traclabs.biosim.server.sensor.environment.TotalMolesSensorImpl;
import com.traclabs.biosim.server.sensor.environment.TotalPressureSensorImpl;
import com.traclabs.biosim.server.sensor.food.BiomassInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.food.BiomassOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.food.BiomassStoreWaterContentSensorImpl;
import com.traclabs.biosim.server.sensor.food.FoodInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.food.FoodOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.food.HarvestSensorImpl;
import com.traclabs.biosim.server.sensor.food.PlantDeathSensorImpl;
import com.traclabs.biosim.server.sensor.food.TimeTillCanopyClosureSensorImpl;
import com.traclabs.biosim.server.sensor.power.PowerInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.power.PowerOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.waste.DryWasteInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.waste.DryWasteOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.water.DirtyWaterInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.water.DirtyWaterOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.water.GreyWaterInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.water.GreyWaterOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.water.PotableWaterInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.water.PotableWaterOutFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.water.WaterInFlowRateSensorImpl;
import com.traclabs.biosim.server.sensor.water.WaterOutFlowRateSensorImpl;
import com.traclabs.biosim.util.XMLUtils;

/**
 * Reads BioSim configuration from XML file.
 * 
 * @author Scott Bell
 */
public class SensorInitializer {
	private int myID = 0;

	private List<GenericSensor> mySensors;

	private Logger myLogger;

	/** Default constructor. */
	public SensorInitializer(int pID) {
		myID = pID;
		mySensors = new Vector<GenericSensor>();
		myLogger = Logger.getLogger(this.getClass());
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
			CO2InFlowRateSensorImpl myCO2InFlowRateSensorImpl = new CO2InFlowRateSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myCO2InFlowRateSensorImpl, node);
			BiosimServer.registerServer(new CO2InFlowRateSensorPOATie(
					myCO2InFlowRateSensorImpl), myCO2InFlowRateSensorImpl
					.getModuleName(), myCO2InFlowRateSensorImpl.getID());
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
			CO2OutFlowRateSensorImpl myCO2OutFlowRateSensorImpl = new CO2OutFlowRateSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myCO2OutFlowRateSensorImpl, node);
			BiosimServer.registerServer(new CO2OutFlowRateSensorPOATie(
					myCO2OutFlowRateSensorImpl), myCO2OutFlowRateSensorImpl
					.getModuleName(), myCO2OutFlowRateSensorImpl.getID());
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
			O2InFlowRateSensorImpl myO2InFlowRateSensorImpl = new O2InFlowRateSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myO2InFlowRateSensorImpl, node);
			BiosimServer.registerServer(new O2InFlowRateSensorPOATie(
					myO2InFlowRateSensorImpl), myO2InFlowRateSensorImpl
					.getModuleName(), myO2InFlowRateSensorImpl.getID());
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
			O2OutFlowRateSensorImpl myO2OutFlowRateSensorImpl = new O2OutFlowRateSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myO2OutFlowRateSensorImpl, node);
			BiosimServer.registerServer(new O2OutFlowRateSensorPOATie(
					myO2OutFlowRateSensorImpl), myO2OutFlowRateSensorImpl
					.getModuleName(), myO2OutFlowRateSensorImpl.getID());
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
			H2InFlowRateSensorImpl myH2InFlowRateSensorImpl = new H2InFlowRateSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myH2InFlowRateSensorImpl, node);
			BiosimServer.registerServer(new H2InFlowRateSensorPOATie(
					myH2InFlowRateSensorImpl), myH2InFlowRateSensorImpl
					.getModuleName(), myH2InFlowRateSensorImpl.getID());
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
			H2OutFlowRateSensorImpl myH2OutFlowRateSensorImpl = new H2OutFlowRateSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myH2OutFlowRateSensorImpl, node);
			BiosimServer.registerServer(new H2OutFlowRateSensorPOATie(
					myH2OutFlowRateSensorImpl), myH2OutFlowRateSensorImpl
					.getModuleName(), myH2OutFlowRateSensorImpl.getID());
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
			NitrogenInFlowRateSensorImpl myNitrogenInFlowRateSensorImpl = new NitrogenInFlowRateSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myNitrogenInFlowRateSensorImpl,
					node);
			BiosimServer.registerServer(new NitrogenInFlowRateSensorPOATie(
					myNitrogenInFlowRateSensorImpl),
					myNitrogenInFlowRateSensorImpl.getModuleName(),
					myNitrogenInFlowRateSensorImpl.getID());
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
			NitrogenOutFlowRateSensorImpl myNitrogenOutFlowRateSensorImpl = new NitrogenOutFlowRateSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myNitrogenOutFlowRateSensorImpl,
					node);
			BiosimServer.registerServer(new NitrogenOutFlowRateSensorPOATie(
					myNitrogenOutFlowRateSensorImpl),
					myNitrogenOutFlowRateSensorImpl.getModuleName(),
					myNitrogenOutFlowRateSensorImpl.getID());
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
			MethaneInFlowRateSensorImpl myMethaneInFlowRateSensorImpl = new MethaneInFlowRateSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myMethaneInFlowRateSensorImpl,
					node);
			BiosimServer.registerServer(new MethaneInFlowRateSensorPOATie(
					myMethaneInFlowRateSensorImpl),
					myMethaneInFlowRateSensorImpl.getModuleName(),
					myMethaneInFlowRateSensorImpl.getID());
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
			MethaneOutFlowRateSensorImpl myMethaneOutFlowRateSensorImpl = new MethaneOutFlowRateSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myMethaneOutFlowRateSensorImpl,
					node);
			BiosimServer.registerServer(new MethaneOutFlowRateSensorPOATie(
					myMethaneOutFlowRateSensorImpl),
					myMethaneOutFlowRateSensorImpl.getModuleName(),
					myMethaneOutFlowRateSensorImpl.getID());
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
			CrewGroupDeathSensorImpl myCrewGroupDeathSensorImpl = new CrewGroupDeathSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myCrewGroupDeathSensorImpl, node);
			BiosimServer.registerServer(new CrewGroupDeathSensorPOATie(
					myCrewGroupDeathSensorImpl), myCrewGroupDeathSensorImpl
					.getModuleName(), myCrewGroupDeathSensorImpl.getID());
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
			CrewGroupAnyDeadSensorImpl myCrewGroupAnyDeadSensorImpl = new CrewGroupAnyDeadSensorImpl(
					myID, moduleName);
			BiosimInitializer
					.setupBioModule(myCrewGroupAnyDeadSensorImpl, node);
			BiosimServer.registerServer(new CrewGroupAnyDeadSensorPOATie(
					myCrewGroupAnyDeadSensorImpl), myCrewGroupAnyDeadSensorImpl
					.getModuleName(), myCrewGroupAnyDeadSensorImpl.getID());
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
			CrewGroupProductivitySensorImpl myCrewGroupProductivitySensorImpl = new CrewGroupProductivitySensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myCrewGroupProductivitySensorImpl,
					node);
			BiosimServer.registerServer(new CrewGroupProductivitySensorPOATie(
					myCrewGroupProductivitySensorImpl),
					myCrewGroupProductivitySensorImpl.getModuleName(),
					myCrewGroupProductivitySensorImpl.getID());
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
			AirInFlowRateSensorImpl myAirInFlowRateSensorImpl = new AirInFlowRateSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myAirInFlowRateSensorImpl, node);
			BiosimServer.registerServer(new AirInFlowRateSensorPOATie(
					myAirInFlowRateSensorImpl), myAirInFlowRateSensorImpl
					.getModuleName(), myAirInFlowRateSensorImpl.getID());
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
			AirOutFlowRateSensorImpl myAirOutFlowRateSensorImpl = new AirOutFlowRateSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myAirOutFlowRateSensorImpl, node);
			BiosimServer.registerServer(new AirOutFlowRateSensorPOATie(
					myAirOutFlowRateSensorImpl), myAirOutFlowRateSensorImpl
					.getModuleName(), myAirOutFlowRateSensorImpl.getID());
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
			GasConcentrationSensorImpl myGasConcentrationSensorImpl = new GasConcentrationSensorImpl(
					myID, moduleName);
			BiosimInitializer
					.setupBioModule(myGasConcentrationSensorImpl, node);
			BiosimServer.registerServer(new GasConcentrationSensorPOATie(
					myGasConcentrationSensorImpl), myGasConcentrationSensorImpl
					.getModuleName(), myGasConcentrationSensorImpl.getID());
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
			GasPressureSensorImpl myGasPressureSensorImpl = new GasPressureSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myGasPressureSensorImpl, node);
			BiosimServer.registerServer(new GasPressureSensorPOATie(
					myGasPressureSensorImpl), myGasPressureSensorImpl
					.getModuleName(), myGasPressureSensorImpl.getID());
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
			TotalMolesSensorImpl myTotalMolesSensorImpl = new TotalMolesSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myTotalMolesSensorImpl, node);
			BiosimServer.registerServer(new TotalMolesSensorPOATie(
					myTotalMolesSensorImpl), myTotalMolesSensorImpl
					.getModuleName(), myTotalMolesSensorImpl.getID());
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
			TotalPressureSensorImpl myTotalPressureSensorImpl = new TotalPressureSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myTotalPressureSensorImpl, node);
			BiosimServer.registerServer(new TotalPressureSensorPOATie(
					myTotalPressureSensorImpl), myTotalPressureSensorImpl
					.getModuleName(), myTotalPressureSensorImpl.getID());
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
			BiomassInFlowRateSensorImpl myBiomassInFlowRateSensorImpl = new BiomassInFlowRateSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myBiomassInFlowRateSensorImpl,
					node);
			BiosimServer.registerServer(new BiomassInFlowRateSensorPOATie(
					myBiomassInFlowRateSensorImpl),
					myBiomassInFlowRateSensorImpl.getModuleName(),
					myBiomassInFlowRateSensorImpl.getID());
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
			BiomassOutFlowRateSensorImpl myBiomassOutFlowRateSensorImpl = new BiomassOutFlowRateSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myBiomassOutFlowRateSensorImpl,
					node);
			BiosimServer.registerServer(new BiomassOutFlowRateSensorPOATie(
					myBiomassOutFlowRateSensorImpl),
					myBiomassOutFlowRateSensorImpl.getModuleName(),
					myBiomassOutFlowRateSensorImpl.getID());
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
			FoodInFlowRateSensorImpl myFoodInFlowRateSensorImpl = new FoodInFlowRateSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myFoodInFlowRateSensorImpl, node);
			BiosimServer.registerServer(new FoodInFlowRateSensorPOATie(
					myFoodInFlowRateSensorImpl), myFoodInFlowRateSensorImpl
					.getModuleName(), myFoodInFlowRateSensorImpl.getID());
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
			FoodOutFlowRateSensorImpl myFoodOutFlowRateSensorImpl = new FoodOutFlowRateSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myFoodOutFlowRateSensorImpl, node);
			BiosimServer.registerServer(new FoodOutFlowRateSensorPOATie(
					myFoodOutFlowRateSensorImpl), myFoodOutFlowRateSensorImpl
					.getModuleName(), myFoodOutFlowRateSensorImpl.getID());
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
			BiomassStoreWaterContentSensorImpl myBiomassStoreWaterContentSensorImpl = new BiomassStoreWaterContentSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(
					myBiomassStoreWaterContentSensorImpl, node);
			BiosimServer.registerServer(
					new BiomassStoreWaterContentSensorPOATie(
							myBiomassStoreWaterContentSensorImpl),
					myBiomassStoreWaterContentSensorImpl.getModuleName(),
					myBiomassStoreWaterContentSensorImpl.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void createHarvestSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating HarvestSensor with moduleName: "
					+ moduleName);
			HarvestSensorImpl myHarvestSensorImpl = new HarvestSensorImpl(myID,
					moduleName);
			BiosimInitializer.setupBioModule(myHarvestSensorImpl, node);
			BiosimServer.registerServer(new HarvestSensorPOATie(
					myHarvestSensorImpl), myHarvestSensorImpl.getModuleName(),
					myHarvestSensorImpl.getID());
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
			PlantDeathSensorImpl myPlantDeathSensorImpl = new PlantDeathSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myPlantDeathSensorImpl, node);
			BiosimServer.registerServer(new PlantDeathSensorPOATie(
					myPlantDeathSensorImpl), myPlantDeathSensorImpl
					.getModuleName(), myPlantDeathSensorImpl.getID());
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
			TimeTillCanopyClosureSensorImpl myTimeTillCanopyClosureSensorImpl = new TimeTillCanopyClosureSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myTimeTillCanopyClosureSensorImpl,
					node);
			BiosimServer.registerServer(new TimeTillCanopyClosureSensorPOATie(
					myTimeTillCanopyClosureSensorImpl),
					myTimeTillCanopyClosureSensorImpl.getModuleName(),
					myTimeTillCanopyClosureSensorImpl.getID());
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
			StoreLevelSensorImpl myStoreLevelSensorImpl = new StoreLevelSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myStoreLevelSensorImpl, node);
			BiosimServer.registerServer(new StoreLevelSensorPOATie(
					myStoreLevelSensorImpl), myStoreLevelSensorImpl
					.getModuleName(), myStoreLevelSensorImpl.getID());
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
			StoreOverflowSensorImpl myStoreOverflowSensorImpl = new StoreOverflowSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myStoreOverflowSensorImpl, node);
			BiosimServer.registerServer(new StoreOverflowSensorPOATie(
					myStoreOverflowSensorImpl), myStoreOverflowSensorImpl
					.getModuleName(), myStoreOverflowSensorImpl.getID());
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
			TimeSensorImpl myTimeSensorImpl = new TimeSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myTimeSensorImpl, node);
			BiosimServer.registerServer(new TimeSensorPOATie(
					myTimeSensorImpl), myTimeSensorImpl
					.getModuleName(), myTimeSensorImpl.getID());
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
			InfluentValveStateSensorImpl myInfluentValveStateSensorImpl = new InfluentValveStateSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myInfluentValveStateSensorImpl,
					node);
			BiosimServer.registerServer(new InfluentValveStateSensorPOATie(
					myInfluentValveStateSensorImpl),
					myInfluentValveStateSensorImpl.getModuleName(),
					myInfluentValveStateSensorImpl.getID());
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
			EffluentValveStateSensorImpl myEffluentValveStateSensorImpl = new EffluentValveStateSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myEffluentValveStateSensorImpl,
					node);
			BiosimServer.registerServer(new EffluentValveStateSensorPOATie(
					myEffluentValveStateSensorImpl),
					myEffluentValveStateSensorImpl.getModuleName(),
					myEffluentValveStateSensorImpl.getID());
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
			PowerInFlowRateSensorImpl myPowerInFlowRateSensorImpl = new PowerInFlowRateSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myPowerInFlowRateSensorImpl, node);
			BiosimServer.registerServer(new PowerInFlowRateSensorPOATie(
					myPowerInFlowRateSensorImpl), myPowerInFlowRateSensorImpl
					.getModuleName(), myPowerInFlowRateSensorImpl.getID());
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
			PowerOutFlowRateSensorImpl myPowerOutFlowRateSensorImpl = new PowerOutFlowRateSensorImpl(
					myID, moduleName);
			BiosimInitializer
					.setupBioModule(myPowerOutFlowRateSensorImpl, node);
			BiosimServer.registerServer(new PowerOutFlowRateSensorPOATie(
					myPowerOutFlowRateSensorImpl), myPowerOutFlowRateSensorImpl
					.getModuleName(), myPowerOutFlowRateSensorImpl.getID());
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
			PotableWaterInFlowRateSensorImpl myPotableWaterInFlowRateSensorImpl = new PotableWaterInFlowRateSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(
					myPotableWaterInFlowRateSensorImpl, node);
			BiosimServer.registerServer(new PotableWaterInFlowRateSensorPOATie(
					myPotableWaterInFlowRateSensorImpl),
					myPotableWaterInFlowRateSensorImpl.getModuleName(),
					myPotableWaterInFlowRateSensorImpl.getID());
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
			PotableWaterOutFlowRateSensorImpl myPotableWaterOutFlowRateSensorImpl = new PotableWaterOutFlowRateSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(
					myPotableWaterOutFlowRateSensorImpl, node);
			BiosimServer.registerServer(
					new PotableWaterOutFlowRateSensorPOATie(
							myPotableWaterOutFlowRateSensorImpl),
					myPotableWaterOutFlowRateSensorImpl.getModuleName(),
					myPotableWaterOutFlowRateSensorImpl.getID());
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
			GreyWaterInFlowRateSensorImpl myGreyWaterInFlowRateSensorImpl = new GreyWaterInFlowRateSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myGreyWaterInFlowRateSensorImpl,
					node);
			BiosimServer.registerServer(new GreyWaterInFlowRateSensorPOATie(
					myGreyWaterInFlowRateSensorImpl),
					myGreyWaterInFlowRateSensorImpl.getModuleName(),
					myGreyWaterInFlowRateSensorImpl.getID());
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
			GreyWaterOutFlowRateSensorImpl myGreyWaterOutFlowRateSensorImpl = new GreyWaterOutFlowRateSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myGreyWaterOutFlowRateSensorImpl,
					node);
			BiosimServer.registerServer(new GreyWaterOutFlowRateSensorPOATie(
					myGreyWaterOutFlowRateSensorImpl),
					myGreyWaterOutFlowRateSensorImpl.getModuleName(),
					myGreyWaterOutFlowRateSensorImpl.getID());
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
			DirtyWaterInFlowRateSensorImpl myDirtyWaterInFlowRateSensorImpl = new DirtyWaterInFlowRateSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myDirtyWaterInFlowRateSensorImpl,
					node);
			BiosimServer.registerServer(new DirtyWaterInFlowRateSensorPOATie(
					myDirtyWaterInFlowRateSensorImpl),
					myDirtyWaterInFlowRateSensorImpl.getModuleName(),
					myDirtyWaterInFlowRateSensorImpl.getID());
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
			DirtyWaterOutFlowRateSensorImpl myDirtyWaterOutFlowRateSensorImpl = new DirtyWaterOutFlowRateSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myDirtyWaterOutFlowRateSensorImpl,
					node);
			BiosimServer.registerServer(new DirtyWaterOutFlowRateSensorPOATie(
					myDirtyWaterOutFlowRateSensorImpl),
					myDirtyWaterOutFlowRateSensorImpl.getModuleName(),
					myDirtyWaterOutFlowRateSensorImpl.getID());
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

	private void createWaterInFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating WaterInFlowRateSensor with moduleName: "
					+ moduleName);
			WaterInFlowRateSensorImpl myWaterInFlowRateSensorImpl = new WaterInFlowRateSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myWaterInFlowRateSensorImpl, node);
			BiosimServer.registerServer(new WaterInFlowRateSensorPOATie(
					myWaterInFlowRateSensorImpl), myWaterInFlowRateSensorImpl
					.getModuleName(), myWaterInFlowRateSensorImpl.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureWaterInFlowRateSensor(Node node) {
		WaterInFlowRateSensor myWaterInFlowRateSensor = WaterInFlowRateSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myWaterInFlowRateSensor.setInput(
				WaterConsumerHelper.narrow(BiosimInitializer.getModule(myID,
						getInputName(node))), getFlowRateIndex(node));
		mySensors.add(myWaterInFlowRateSensor);
	}

	private void createWaterOutFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		if (BiosimInitializer.isCreatedLocally(node)) {
			myLogger.debug("Creating WaterOutFlowRateSensor with moduleName: "
					+ moduleName);
			WaterOutFlowRateSensorImpl myWaterOutFlowRateSensorImpl = new WaterOutFlowRateSensorImpl(
					myID, moduleName);
			BiosimInitializer
					.setupBioModule(myWaterOutFlowRateSensorImpl, node);
			BiosimServer.registerServer(new WaterOutFlowRateSensorPOATie(
					myWaterOutFlowRateSensorImpl), myWaterOutFlowRateSensorImpl
					.getModuleName(), myWaterOutFlowRateSensorImpl.getID());
		} else
			BiosimInitializer.printRemoteWarningMessage(moduleName);
	}

	private void configureWaterOutFlowRateSensor(Node node) {
		WaterOutFlowRateSensor myWaterOutFlowRateSensor = WaterOutFlowRateSensorHelper
				.narrow(BiosimInitializer.getModule(myID, BiosimInitializer
						.getModuleName(node)));
		myWaterOutFlowRateSensor.setInput(
				WaterProducerHelper.narrow(BiosimInitializer.getModule(myID,
						getInputName(node))), getFlowRateIndex(node));
		mySensors.add(myWaterOutFlowRateSensor);
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
				} else if (childName.equals("WaterInFlowRateSensor")) {
					if (firstPass)
						createWaterInFlowRateSensor(child);
					else
						configureWaterInFlowRateSensor(child);
				} else if (childName.equals("WaterOutFlowRateSensor")) {
					if (firstPass)
						createWaterOutFlowRateSensor(child);
					else
						configureWaterOutFlowRateSensor(child);
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
			DryWasteInFlowRateSensorImpl myDryWasteInFlowRateSensorImpl = new DryWasteInFlowRateSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myDryWasteInFlowRateSensorImpl,
					node);
			BiosimServer.registerServer(new DryWasteInFlowRateSensorPOATie(
					myDryWasteInFlowRateSensorImpl),
					myDryWasteInFlowRateSensorImpl.getModuleName(),
					myDryWasteInFlowRateSensorImpl.getID());
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
			DryWasteOutFlowRateSensorImpl myDryWasteOutFlowRateSensorImpl = new DryWasteOutFlowRateSensorImpl(
					myID, moduleName);
			BiosimInitializer.setupBioModule(myDryWasteOutFlowRateSensorImpl,
					node);
			BiosimServer.registerServer(new DryWasteOutFlowRateSensorPOATie(
					myDryWasteOutFlowRateSensorImpl),
					myDryWasteOutFlowRateSensorImpl.getModuleName(),
					myDryWasteOutFlowRateSensorImpl.getID());
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
	public List<GenericSensor> getSensors() {
		return mySensors;
	}
}