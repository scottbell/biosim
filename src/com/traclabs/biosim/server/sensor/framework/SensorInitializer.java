package com.traclabs.biosim.server.sensor.framework;

import com.traclabs.biosim.server.framework.BiosimInitializer;
import com.traclabs.biosim.server.sensor.air.*;
import com.traclabs.biosim.server.sensor.crew.*;
import com.traclabs.biosim.server.sensor.environment.*;
import com.traclabs.biosim.server.sensor.food.*;
import com.traclabs.biosim.server.sensor.power.*;
import com.traclabs.biosim.server.sensor.water.*;
import com.traclabs.biosim.server.sensor.waste.*;
import com.traclabs.biosim.server.simulation.environment.*;
import com.traclabs.biosim.server.simulation.air.*;
import com.traclabs.biosim.server.simulation.crew.*;
import com.traclabs.biosim.server.simulation.food.*;
import com.traclabs.biosim.server.simulation.framework.*;
import com.traclabs.biosim.server.simulation.power.*;
import com.traclabs.biosim.server.simulation.water.*;
import com.traclabs.biosim.server.simulation.waste.*;
import com.traclabs.biosim.util.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import java.util.HashSet;
import java.util.Set;

public class SensorInitializer {
	private int myID = 0;
	private Set<GenericSensor> mySensors;
	private Logger myLogger;

	public SensorInitializer(int pID) {
		myID = pID;
		mySensors = new HashSet<GenericSensor>();
		myLogger = LoggerFactory.getLogger(this.getClass());
	}

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
		return Integer.parseInt(pNode.getAttributes().getNamedItem("index").getNodeValue());
	}

	// **************** Air Sensors ********************

	private void createCO2InFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		CO2InFlowRateSensor sensor = new CO2InFlowRateSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureCO2InFlowRateSensor(Node node) {
		CO2InFlowRateSensor sensor = (CO2InFlowRateSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((CO2Consumer) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)), getFlowRateIndex(node));
		mySensors.add(sensor);
	}

	private void createCO2OutFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		CO2OutFlowRateSensor sensor = new CO2OutFlowRateSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureCO2OutFlowRateSensor(Node node) {
		CO2OutFlowRateSensor sensor = (CO2OutFlowRateSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((CO2Producer) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)), getFlowRateIndex(node));
		mySensors.add(sensor);
	}

	private void createO2InFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		O2InFlowRateSensor sensor = new O2InFlowRateSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureO2InFlowRateSensor(Node node) {
		O2InFlowRateSensor sensor = (O2InFlowRateSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((O2Consumer) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)), getFlowRateIndex(node));
		mySensors.add(sensor);
	}

	private void createO2OutFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		O2OutFlowRateSensor sensor = new O2OutFlowRateSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureO2OutFlowRateSensor(Node node) {
		O2OutFlowRateSensor sensor = (O2OutFlowRateSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((O2Producer) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)), getFlowRateIndex(node));
		mySensors.add(sensor);
	}

	private void createH2InFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		H2InFlowRateSensor sensor = new H2InFlowRateSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureH2InFlowRateSensor(Node node) {
		H2InFlowRateSensor sensor = (H2InFlowRateSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((H2Consumer) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)), getFlowRateIndex(node));
		mySensors.add(sensor);
	}

	private void createH2OutFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		H2OutFlowRateSensor sensor = new H2OutFlowRateSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureH2OutFlowRateSensor(Node node) {
		H2OutFlowRateSensor sensor = (H2OutFlowRateSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((H2Producer) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)), getFlowRateIndex(node));
		mySensors.add(sensor);
	}

	private void createNitrogenInFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		NitrogenInFlowRateSensor sensor = new NitrogenInFlowRateSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureNitrogenInFlowRateSensor(Node node) {
		NitrogenInFlowRateSensor sensor = (NitrogenInFlowRateSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((NitrogenConsumer) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)), getFlowRateIndex(node));
		mySensors.add(sensor);
	}

	private void createNitrogenOutFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		NitrogenOutFlowRateSensor sensor = new NitrogenOutFlowRateSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureNitrogenOutFlowRateSensor(Node node) {
		NitrogenOutFlowRateSensor sensor = (NitrogenOutFlowRateSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((NitrogenProducer) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)), getFlowRateIndex(node));
		mySensors.add(sensor);
	}

	private void createMethaneInFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		MethaneInFlowRateSensor sensor = new MethaneInFlowRateSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureMethaneInFlowRateSensor(Node node) {
		MethaneInFlowRateSensor sensor = (MethaneInFlowRateSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((MethaneConsumer) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)), getFlowRateIndex(node));
		mySensors.add(sensor);
	}

	private void createMethaneOutFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		MethaneOutFlowRateSensor sensor = new MethaneOutFlowRateSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureMethaneOutFlowRateSensor(Node node) {
		MethaneOutFlowRateSensor sensor = (MethaneOutFlowRateSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((MethaneProducer) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)), getFlowRateIndex(node));
		mySensors.add(sensor);
	}

	// **************** Crew Sensors ********************

	private void createCrewGroupDeathSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		CrewGroupDeathSensor sensor = new CrewGroupDeathSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureCrewGroupDeathSensor(Node node) {
		CrewGroupDeathSensor sensor = (CrewGroupDeathSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((CrewGroup) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)));
		mySensors.add(sensor);
	}

	private void createCrewGroupAnyDeadSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		CrewGroupAnyDeadSensor sensor = new CrewGroupAnyDeadSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureCrewGroupAnyDeadSensor(Node node) {
		CrewGroupAnyDeadSensor sensor = (CrewGroupAnyDeadSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((CrewGroup) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)));
		mySensors.add(sensor);
	}

	private void createCrewGroupProductivitySensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		CrewGroupProductivitySensor sensor = new CrewGroupProductivitySensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureCrewGroupProductivitySensor(Node node) {
		CrewGroupProductivitySensor sensor = (CrewGroupProductivitySensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((CrewGroup) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)));
		mySensors.add(sensor);
	}

	// **************** Environment Sensors ********************

	private void createAirInFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		AirInFlowRateSensor sensor = new AirInFlowRateSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureAirInFlowRateSensor(Node node) {
		AirInFlowRateSensor sensor = (AirInFlowRateSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((AirConsumer) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)), getFlowRateIndex(node));
		mySensors.add(sensor);
	}

	private void createAirOutFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		AirOutFlowRateSensor sensor = new AirOutFlowRateSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureAirOutFlowRateSensor(Node node) {
		AirOutFlowRateSensor sensor = (AirOutFlowRateSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((AirProducer) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)), getFlowRateIndex(node));
		mySensors.add(sensor);
	}

	private void createGasConcentrationSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		GasConcentrationSensor sensor = new GasConcentrationSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureGasConcentrationSensor(Node node) {
		GasConcentrationSensor sensor = (GasConcentrationSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		SimEnvironment inputEnvironment = (SimEnvironment) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node));
		sensor.setInput(inputEnvironment, getGasStore(node, inputEnvironment));
		mySensors.add(sensor);
	}

	private void createGasPressureSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		GasPressureSensor sensor = new GasPressureSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureGasPressureSensor(Node node) {
		GasPressureSensor sensor = (GasPressureSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		SimEnvironment inputEnvironment = (SimEnvironment) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node));
		sensor.setInput(getGasStore(node, inputEnvironment));
		mySensors.add(sensor);
	}

	private void createTotalMolesSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		TotalMolesSensor sensor = new TotalMolesSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureTotalMolesSensor(Node node) {
		TotalMolesSensor sensor = (TotalMolesSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		SimEnvironment inputEnvironment = (SimEnvironment) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node));
		sensor.setInput(inputEnvironment);
		mySensors.add(sensor);
	}

	private void createTotalPressureSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		TotalPressureSensor sensor = new TotalPressureSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureTotalPressureSensor(Node node) {
		TotalPressureSensor sensor = (TotalPressureSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		SimEnvironment inputEnvironment = (SimEnvironment) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node));
		sensor.setInput(inputEnvironment);
		mySensors.add(sensor);
	}

	// **************** Food Sensors ********************

	private void createBiomassInFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		BiomassInFlowRateSensor sensor = new BiomassInFlowRateSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureBiomassInFlowRateSensor(Node node) {
		BiomassInFlowRateSensor sensor = (BiomassInFlowRateSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((BiomassConsumer) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)), getFlowRateIndex(node));
		mySensors.add(sensor);
	}

	private void createBiomassOutFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		BiomassOutFlowRateSensor sensor = new BiomassOutFlowRateSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureBiomassOutFlowRateSensor(Node node) {
		BiomassOutFlowRateSensor sensor = (BiomassOutFlowRateSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((BiomassProducer) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)), getFlowRateIndex(node));
		mySensors.add(sensor);
	}

	private void createFoodInFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		FoodInFlowRateSensor sensor = new FoodInFlowRateSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureFoodInFlowRateSensor(Node node) {
		FoodInFlowRateSensor sensor = (FoodInFlowRateSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((FoodConsumer) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)), getFlowRateIndex(node));
		mySensors.add(sensor);
	}

	private void createFoodOutFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		FoodOutFlowRateSensor sensor = new FoodOutFlowRateSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureFoodOutFlowRateSensor(Node node) {
		FoodOutFlowRateSensor sensor = (FoodOutFlowRateSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((FoodProducer) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)), getFlowRateIndex(node));
		mySensors.add(sensor);
	}

	private void createBiomassStoreWaterContentSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		BiomassStoreWaterContentSensor sensor = new BiomassStoreWaterContentSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureBiomassStoreWaterContentSensor(Node node) {
		BiomassStoreWaterContentSensor sensor = (BiomassStoreWaterContentSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((BiomassStore) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)));
		mySensors.add(sensor);
	}

	private void createHarvestSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		HarvestSensor sensor = new HarvestSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureHarvestSensor(Node node) {
		int index = XMLUtils.getIntAttribute(node, "shelfIndex");
		HarvestSensor sensor = (HarvestSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((BiomassPS) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)), index);
		mySensors.add(sensor);
	}

	private void createPlantDeathSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		PlantDeathSensor sensor = new PlantDeathSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configurePlantDeathSensor(Node node) {
		int index = XMLUtils.getIntAttribute(node, "shelfIndex");
		PlantDeathSensor sensor = (PlantDeathSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((BiomassPS) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)), index);
		mySensors.add(sensor);
	}

	private void createTimeTillCanopyClosureSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		TimeTillCanopyClosureSensor sensor = new TimeTillCanopyClosureSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureTimeTillCanopyClosureSensor(Node node) {
		int index = XMLUtils.getIntAttribute(node, "shelfIndex");
		TimeTillCanopyClosureSensor sensor = (TimeTillCanopyClosureSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((BiomassPS) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)), index);
		mySensors.add(sensor);
	}

	// **************** Framework Sensors ********************

	private void createStoreLevelSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		StoreLevelSensor sensor = new StoreLevelSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureStoreLevelSensor(Node node) {
		StoreLevelSensor sensor = (StoreLevelSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((Store) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)));
		mySensors.add(sensor);
	}

	private void createStoreOverflowSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		StoreOverflowSensor sensor = new StoreOverflowSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureStoreOverflowSensor(Node node) {
		StoreOverflowSensor sensor = (StoreOverflowSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((Store) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)));
		mySensors.add(sensor);
	}

	private void createInfluentValveStateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		InfluentValveStateSensor sensor = new InfluentValveStateSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureInfluentValveStateSensor(Node node) {
		InfluentValveStateSensor sensor = (InfluentValveStateSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((InfluentValve) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)));
		mySensors.add(sensor);
	}

	private void createEffluentValveStateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		EffluentValveStateSensor sensor = new EffluentValveStateSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureEffluentValveStateSensor(Node node) {
		EffluentValveStateSensor sensor = (EffluentValveStateSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((EffluentValve) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)));
		mySensors.add(sensor);
	}

	private void createTimeSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		TimeSensor sensor = new TimeSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureTimeSensor(Node node) {
		TimeSensor sensor = (TimeSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		mySensors.add(sensor);
	}

	// **************** Power Sensors ********************

	private void createPowerInFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		PowerInFlowRateSensor sensor = new PowerInFlowRateSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configurePowerInFlowRateSensor(Node node) {
		PowerInFlowRateSensor sensor = (PowerInFlowRateSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((PowerConsumer) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)), getFlowRateIndex(node));
		mySensors.add(sensor);
	}

	private void createPowerOutFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		PowerOutFlowRateSensor sensor = new PowerOutFlowRateSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configurePowerOutFlowRateSensor(Node node) {
		PowerOutFlowRateSensor sensor = (PowerOutFlowRateSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((PowerProducer) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)), getFlowRateIndex(node));
		mySensors.add(sensor);
	}

	// **************** Water Sensors ********************

	private void createPotableWaterInFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		PotableWaterInFlowRateSensor sensor = new PotableWaterInFlowRateSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configurePotableWaterInFlowRateSensor(Node node) {
		PotableWaterInFlowRateSensor sensor = (PotableWaterInFlowRateSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((PotableWaterConsumer) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)), getFlowRateIndex(node));
		mySensors.add(sensor);
	}

	private void createPotableWaterOutFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		PotableWaterOutFlowRateSensor sensor = new PotableWaterOutFlowRateSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configurePotableWaterOutFlowRateSensor(Node node) {
		PotableWaterOutFlowRateSensor sensor = (PotableWaterOutFlowRateSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((PotableWaterProducer) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)), getFlowRateIndex(node));
		mySensors.add(sensor);
	}

	private void createGreyWaterInFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		GreyWaterInFlowRateSensor sensor = new GreyWaterInFlowRateSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureGreyWaterInFlowRateSensor(Node node) {
		GreyWaterInFlowRateSensor sensor = (GreyWaterInFlowRateSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((GreyWaterConsumer) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)), getFlowRateIndex(node));
		mySensors.add(sensor);
	}

	private void createGreyWaterOutFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		GreyWaterOutFlowRateSensor sensor = new GreyWaterOutFlowRateSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureGreyWaterOutFlowRateSensor(Node node) {
		GreyWaterOutFlowRateSensor sensor = (GreyWaterOutFlowRateSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((GreyWaterProducer) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)), getFlowRateIndex(node));
		mySensors.add(sensor);
	}

	private void createDirtyWaterInFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		DirtyWaterInFlowRateSensor sensor = new DirtyWaterInFlowRateSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureDirtyWaterInFlowRateSensor(Node node) {
		DirtyWaterInFlowRateSensor sensor = (DirtyWaterInFlowRateSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((DirtyWaterConsumer) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)), getFlowRateIndex(node));
		mySensors.add(sensor);
	}

	private void createDirtyWaterOutFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		DirtyWaterOutFlowRateSensor sensor = new DirtyWaterOutFlowRateSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureDirtyWaterOutFlowRateSensor(Node node) {
		DirtyWaterOutFlowRateSensor sensor = (DirtyWaterOutFlowRateSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((DirtyWaterProducer) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)), getFlowRateIndex(node));
		mySensors.add(sensor);
	}

	// **************** Waste Sensors ********************

	private void createDryWasteInFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		DryWasteInFlowRateSensor sensor = new DryWasteInFlowRateSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureDryWasteInFlowRateSensor(Node node) {
		DryWasteInFlowRateSensor sensor = (DryWasteInFlowRateSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((DryWasteConsumer) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)), getFlowRateIndex(node));
		mySensors.add(sensor);
	}

	private void createDryWasteOutFlowRateSensor(Node node) {
		String moduleName = BiosimInitializer.getModuleName(node);
		DryWasteOutFlowRateSensor sensor = new DryWasteOutFlowRateSensor(myID, moduleName);
		BiosimInitializer.setupBioModule(sensor, node);
	}

	private void configureDryWasteOutFlowRateSensor(Node node) {
		DryWasteOutFlowRateSensor sensor = (DryWasteOutFlowRateSensor) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(BiosimInitializer.getModuleName(node));
		sensor.setInput((DryWasteProducer) BiosimInitializer.getInstance(myID)
				.getBioDriver().getModule(getInputName(node)), getFlowRateIndex(node));
		mySensors.add(sensor);
	}

	// **************** Crawling Methods ********************

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

	private void crawlWasteSensors(Node node, boolean firstPass) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
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
			}
			child = child.getNextSibling();
		}
	}

	// Helper to get gas store from an environment sensor
	private EnvironmentStore getGasStore(Node node, SimEnvironment inputEnvironment) {
		String gasString = node.getAttributes().getNamedItem("gasType").getNodeValue();
		if (gasString.equals("O2"))
			return inputEnvironment.getO2Store();
		else if (gasString.equals("CO2"))
			return inputEnvironment.getCO2Store();
		else if (gasString.equals("NITROGEN"))
			return inputEnvironment.getNitrogenStore();
		else if (gasString.equals("VAPOR"))
			return inputEnvironment.getVaporStore();
		else if (gasString.equals("OTHER"))
			return inputEnvironment.getOtherStore();
		else
			return null;
	}

	public Set<GenericSensor> getSensors() {
		return mySensors;
	}
}