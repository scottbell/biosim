package com.traclabs.biosim.server.sensor.framework;

import com.traclabs.biosim.server.framework.BiosimInitializer;
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
import com.traclabs.biosim.server.simulation.air.*;
import com.traclabs.biosim.server.simulation.crew.CrewGroup;
import com.traclabs.biosim.server.simulation.environment.AirConsumer;
import com.traclabs.biosim.server.simulation.environment.AirProducer;
import com.traclabs.biosim.server.simulation.environment.EnvironmentStore;
import com.traclabs.biosim.server.simulation.environment.SimEnvironment;
import com.traclabs.biosim.server.simulation.food.*;
import com.traclabs.biosim.server.simulation.framework.EffluentValve;
import com.traclabs.biosim.server.simulation.framework.InfluentValve;
import com.traclabs.biosim.server.simulation.framework.Store;
import com.traclabs.biosim.server.simulation.power.PowerConsumer;
import com.traclabs.biosim.server.simulation.power.PowerProducer;
import com.traclabs.biosim.server.simulation.waste.DryWasteConsumer;
import com.traclabs.biosim.server.simulation.waste.DryWasteProducer;
import com.traclabs.biosim.server.simulation.water.*;
import com.traclabs.biosim.util.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import java.util.HashSet;
import java.util.Set;

public class SensorInitializer {
    private final Set<GenericSensor> mySensors;
    private final Logger myLogger;
    private int myID = 0;

    public SensorInitializer(int pID) {
        myID = pID;
        mySensors = new HashSet<GenericSensor>();
        myLogger = LoggerFactory.getLogger(this.getClass());
    }

    private static String getInputName(Node pNode) {
        return pNode.getAttributes().getNamedItem("input").getNodeValue();
    }

    private static int getFlowRateIndex(Node pNode) {
        return Integer.parseInt(pNode.getAttributes().getNamedItem("index").getNodeValue());
    }

    /**
     * Generic method to configure a sensor with common operations:
     * 1. Add to mySensors collection
     * 2. Configure alarm thresholds
     * 3. Check if sensor alarms are valid
     * 4. Validate input module is set
     *
     * @param sensor The sensor to configure
     * @param node The XML node containing configuration data
     * @return The configured sensor (for method chaining if needed)
     */
    private GenericSensor configureSensor(GenericSensor sensor, Node node) {
        // 1. Add sensor to mySensors collection
        mySensors.add(sensor);
        
        // 2. Configure alarm thresholds
        configureAlarmThresholds(sensor, node);
        
        // 3. Check if sensor alarms are valid
        if (!sensor.areAlarmRangesValid()) {
            myLogger.warn("🛑 Sensor " + sensor.getModuleName() + " has invalid alarm ranges (min > max)!");
        }
        
        // 4. Validate input module is set
        if (sensor.getInputModule() == null) {
            myLogger.warn("⚠️ Sensor " + sensor.getModuleName() + " has no input module set!");
        }
        
        return sensor;
    }

    /**
     * Sets alarm range boundaries on a sensor if they are specified in the XML.
     * Supports both attribute-based configuration and nested alarms element.
     *
     * @param sensor The sensor to configure
     * @param node   The XML node containing range attributes or alarms element
     */
    private void configureAlarmThresholds(GenericSensor sensor, Node node) {
        // First check for nested alarms element
        Node child = node.getFirstChild();

        while (child != null) {
            String childName = child.getLocalName();
            if (childName != null && childName.equals("alarms")) {
                parseAlarmsElement(sensor, child);
                break;
            }
            child = child.getNextSibling();
        }
    }

    /**
     * Parses the alarms element and configures the sensor accordingly.
     * Expected XML children (all optional):
     *   watch_low,  watch_high,
     *   warning_low, warning_high,
     *   distress_low, distress_high,
     *   critical_low, critical_high,
     *   severe_low,  severe_high
     * Each child element must carry both "min" and "max" attributes.
     */
    private void parseAlarmsElement(GenericSensor sensor, Node alarmsNode) {
        Node child = alarmsNode.getFirstChild();
        while (child != null) {
            String childName = child.getLocalName();
            if (childName != null &&
                child.getAttributes() != null &&
                child.getAttributes().getNamedItem("min") != null &&
                child.getAttributes().getNamedItem("max") != null) {

                float min = Float.parseFloat(child.getAttributes().getNamedItem("min").getNodeValue());
                float max = Float.parseFloat(child.getAttributes().getNamedItem("max").getNodeValue());

                switch (childName) {
                    case "watch_low" -> {
                        sensor.setWatchLowMin(min);
                        sensor.setWatchLowMax(max);
                    }
                    case "watch_high" -> {
                        sensor.setWatchHighMin(min);
                        sensor.setWatchHighMax(max);
                    }
                    case "warning_low" -> {
                        sensor.setWarningLowMin(min);
                        sensor.setWarningLowMax(max);
                    }
                    case "warning_high" -> {
                        sensor.setWarningHighMin(min);
                        sensor.setWarningHighMax(max);
                    }
                    case "distress_low" -> {
                        sensor.setDistressLowMin(min);
                        sensor.setDistressLowMax(max);
                    }
                    case "distress_high" -> {
                        sensor.setDistressHighMin(min);
                        sensor.setDistressHighMax(max);
                    }
                    case "critical_low" -> {
                        sensor.setCriticalLowMin(min);
                        sensor.setCriticalLowMax(max);
                    }
                    case "critical_high" -> {
                        sensor.setCriticalHighMin(min);
                        sensor.setCriticalHighMax(max);
                    }
                    case "severe_low" -> {
                        sensor.setSevereLowMin(min);
                        sensor.setSevereLowMax(max);
                    }
                    case "severe_high" -> {
                        sensor.setSevereHighMin(min);
                        sensor.setSevereHighMax(max);
                    }
                }
            }
            child = child.getNextSibling();
        }
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

    // **************** Air Sensors ********************

    private void createCO2InFlowRateSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        CO2InFlowRateSensor sensor = new CO2InFlowRateSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureCO2InFlowRateSensor(Node node) {
        CO2InFlowRateSensor sensor = (CO2InFlowRateSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((CO2Consumer) BiosimInitializer.getModule(myID, getInputName(node)), getFlowRateIndex(node));
        configureSensor(sensor, node);
    }

    private void createCO2OutFlowRateSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        CO2OutFlowRateSensor sensor = new CO2OutFlowRateSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureCO2OutFlowRateSensor(Node node) {
        CO2OutFlowRateSensor sensor = (CO2OutFlowRateSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((CO2Producer) BiosimInitializer.getModule(myID, getInputName(node)), getFlowRateIndex(node));
        configureSensor(sensor, node);
    }

    private void createO2InFlowRateSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        O2InFlowRateSensor sensor = new O2InFlowRateSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureO2InFlowRateSensor(Node node) {
        O2InFlowRateSensor sensor = (O2InFlowRateSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((O2Consumer) BiosimInitializer.getModule(myID, getInputName(node)), getFlowRateIndex(node));
        configureSensor(sensor, node);
    }

    private void createO2OutFlowRateSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        O2OutFlowRateSensor sensor = new O2OutFlowRateSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureO2OutFlowRateSensor(Node node) {
        O2OutFlowRateSensor sensor = (O2OutFlowRateSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((O2Producer) BiosimInitializer.getModule(myID, getInputName(node)), getFlowRateIndex(node));
        configureSensor(sensor, node);
    }

    private void createH2InFlowRateSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        H2InFlowRateSensor sensor = new H2InFlowRateSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureH2InFlowRateSensor(Node node) {
        H2InFlowRateSensor sensor = (H2InFlowRateSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((H2Consumer) BiosimInitializer.getModule(myID, getInputName(node)), getFlowRateIndex(node));
        configureSensor(sensor, node);
    }

    private void createH2OutFlowRateSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        H2OutFlowRateSensor sensor = new H2OutFlowRateSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureH2OutFlowRateSensor(Node node) {
        H2OutFlowRateSensor sensor = (H2OutFlowRateSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((H2Producer) BiosimInitializer.getModule(myID, getInputName(node)), getFlowRateIndex(node));
        configureSensor(sensor, node);
    }

    private void createNitrogenInFlowRateSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        NitrogenInFlowRateSensor sensor = new NitrogenInFlowRateSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureNitrogenInFlowRateSensor(Node node) {
        NitrogenInFlowRateSensor sensor = (NitrogenInFlowRateSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((NitrogenConsumer) BiosimInitializer.getModule(myID, getInputName(node)), getFlowRateIndex(node));
        configureSensor(sensor, node);
    }

    private void createNitrogenOutFlowRateSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        NitrogenOutFlowRateSensor sensor = new NitrogenOutFlowRateSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureNitrogenOutFlowRateSensor(Node node) {
        NitrogenOutFlowRateSensor sensor = (NitrogenOutFlowRateSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((NitrogenProducer) BiosimInitializer.getModule(myID, getInputName(node)), getFlowRateIndex(node));
        configureSensor(sensor, node);
    }

    private void createMethaneInFlowRateSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        MethaneInFlowRateSensor sensor = new MethaneInFlowRateSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureMethaneInFlowRateSensor(Node node) {
        MethaneInFlowRateSensor sensor = (MethaneInFlowRateSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((MethaneConsumer) BiosimInitializer.getModule(myID, getInputName(node)), getFlowRateIndex(node));
        configureSensor(sensor, node);
    }

    private void createMethaneOutFlowRateSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        MethaneOutFlowRateSensor sensor = new MethaneOutFlowRateSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureMethaneOutFlowRateSensor(Node node) {
        MethaneOutFlowRateSensor sensor = (MethaneOutFlowRateSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((MethaneProducer) BiosimInitializer.getModule(myID, getInputName(node)), getFlowRateIndex(node));
        configureSensor(sensor, node);
    }

    // **************** Crew Sensors ********************

    private void createCrewGroupDeathSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        CrewGroupDeathSensor sensor = new CrewGroupDeathSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureCrewGroupDeathSensor(Node node) {
        CrewGroupDeathSensor sensor = (CrewGroupDeathSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((CrewGroup) BiosimInitializer.getModule(myID, getInputName(node)));
        configureSensor(sensor, node);
    }

    private void createCrewGroupAnyDeadSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        CrewGroupAnyDeadSensor sensor = new CrewGroupAnyDeadSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureCrewGroupAnyDeadSensor(Node node) {
        CrewGroupAnyDeadSensor sensor = (CrewGroupAnyDeadSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((CrewGroup) BiosimInitializer.getModule(myID, getInputName(node)));
        configureSensor(sensor, node);
    }

    private void createCrewGroupProductivitySensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        CrewGroupProductivitySensor sensor = new CrewGroupProductivitySensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureCrewGroupProductivitySensor(Node node) {
        CrewGroupProductivitySensor sensor = (CrewGroupProductivitySensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((CrewGroup) BiosimInitializer.getModule(myID, getInputName(node)));
        configureSensor(sensor, node);
    }

    // **************** Environment Sensors ********************

    private void createAirInFlowRateSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        AirInFlowRateSensor sensor = new AirInFlowRateSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureAirInFlowRateSensor(Node node) {
        AirInFlowRateSensor sensor = (AirInFlowRateSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((AirConsumer) BiosimInitializer.getModule(myID, getInputName(node)), getFlowRateIndex(node));
        configureSensor(sensor, node);
    }

    private void createAirOutFlowRateSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        AirOutFlowRateSensor sensor = new AirOutFlowRateSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureAirOutFlowRateSensor(Node node) {
        AirOutFlowRateSensor sensor = (AirOutFlowRateSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((AirProducer) BiosimInitializer.getModule(myID, getInputName(node)), getFlowRateIndex(node));
        configureSensor(sensor, node);
    }

    private void createGasConcentrationSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        GasConcentrationSensor sensor = new GasConcentrationSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureGasConcentrationSensor(Node node) {
        GasConcentrationSensor sensor = (GasConcentrationSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        SimEnvironment inputEnvironment = (SimEnvironment) BiosimInitializer.getModule(myID, getInputName(node));
        sensor.setInput(inputEnvironment, getGasStore(node, inputEnvironment));
        configureSensor(sensor, node);
    }

    private void createGasPressureSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        GasPressureSensor sensor = new GasPressureSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureGasPressureSensor(Node node) {
        GasPressureSensor sensor = (GasPressureSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        SimEnvironment inputEnvironment = (SimEnvironment) BiosimInitializer.getModule(myID, getInputName(node));
        sensor.setInput(getGasStore(node, inputEnvironment));
        configureSensor(sensor, node);
    }

    private void createTotalMolesSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        TotalMolesSensor sensor = new TotalMolesSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureTotalMolesSensor(Node node) {
        TotalMolesSensor sensor = (TotalMolesSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        SimEnvironment inputEnvironment = (SimEnvironment) BiosimInitializer.getModule(myID, getInputName(node));
        sensor.setInput(inputEnvironment);
        configureSensor(sensor, node);
    }

    private void createTotalPressureSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        TotalPressureSensor sensor = new TotalPressureSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureTotalPressureSensor(Node node) {
        TotalPressureSensor sensor = (TotalPressureSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        SimEnvironment inputEnvironment = (SimEnvironment) BiosimInitializer.getModule(myID, getInputName(node));
        sensor.setInput(inputEnvironment);
        configureSensor(sensor, node);
    }

    // **************** Food Sensors ********************

    private void createBiomassInFlowRateSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        BiomassInFlowRateSensor sensor = new BiomassInFlowRateSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureBiomassInFlowRateSensor(Node node) {
        BiomassInFlowRateSensor sensor = (BiomassInFlowRateSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((BiomassConsumer) BiosimInitializer.getModule(myID, getInputName(node)), getFlowRateIndex(node));
        configureSensor(sensor, node);
    }

    private void createBiomassOutFlowRateSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        BiomassOutFlowRateSensor sensor = new BiomassOutFlowRateSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureBiomassOutFlowRateSensor(Node node) {
        BiomassOutFlowRateSensor sensor = (BiomassOutFlowRateSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((BiomassProducer) BiosimInitializer.getModule(myID, getInputName(node)), getFlowRateIndex(node));
        configureSensor(sensor, node);
    }

    private void createFoodInFlowRateSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        FoodInFlowRateSensor sensor = new FoodInFlowRateSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureFoodInFlowRateSensor(Node node) {
        FoodInFlowRateSensor sensor = (FoodInFlowRateSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((FoodConsumer) BiosimInitializer.getModule(myID, getInputName(node)), getFlowRateIndex(node));
        configureSensor(sensor, node);
    }

    private void createFoodOutFlowRateSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        FoodOutFlowRateSensor sensor = new FoodOutFlowRateSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureFoodOutFlowRateSensor(Node node) {
        FoodOutFlowRateSensor sensor = (FoodOutFlowRateSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((FoodProducer) BiosimInitializer.getModule(myID, getInputName(node)), getFlowRateIndex(node));
        configureSensor(sensor, node);
    }

    private void createBiomassStoreWaterContentSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        BiomassStoreWaterContentSensor sensor = new BiomassStoreWaterContentSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureBiomassStoreWaterContentSensor(Node node) {
        BiomassStoreWaterContentSensor sensor = (BiomassStoreWaterContentSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((BiomassStore) BiosimInitializer.getModule(myID, getInputName(node)));
        configureSensor(sensor, node);
    }

    private void createHarvestSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        HarvestSensor sensor = new HarvestSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureHarvestSensor(Node node) {
        int index = XMLUtils.getIntAttribute(node, "shelfIndex");
        HarvestSensor sensor = (HarvestSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((BiomassPS) BiosimInitializer.getModule(myID, getInputName(node)), index);
        configureSensor(sensor, node);
    }

    private void createPlantDeathSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        PlantDeathSensor sensor = new PlantDeathSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configurePlantDeathSensor(Node node) {
        int index = XMLUtils.getIntAttribute(node, "shelfIndex");
        PlantDeathSensor sensor = (PlantDeathSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((BiomassPS) BiosimInitializer.getModule(myID, getInputName(node)), index);
        configureSensor(sensor, node);
    }

    private void createTimeTillCanopyClosureSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        TimeTillCanopyClosureSensor sensor = new TimeTillCanopyClosureSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureTimeTillCanopyClosureSensor(Node node) {
        int index = XMLUtils.getIntAttribute(node, "shelfIndex");
        TimeTillCanopyClosureSensor sensor = (TimeTillCanopyClosureSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((BiomassPS) BiosimInitializer.getModule(myID, getInputName(node)), index);
        configureSensor(sensor, node);
    }

    // **************** Framework Sensors ********************

    private void createStoreLevelSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        StoreLevelSensor sensor = new StoreLevelSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureStoreLevelSensor(Node node) {
        StoreLevelSensor sensor = (StoreLevelSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((Store) BiosimInitializer.getModule(myID, getInputName(node)));
        configureSensor(sensor, node);
    }

    private void createStoreOverflowSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        StoreOverflowSensor sensor = new StoreOverflowSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureStoreOverflowSensor(Node node) {
        StoreOverflowSensor sensor = (StoreOverflowSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((Store) BiosimInitializer.getModule(myID, getInputName(node)));
        configureSensor(sensor, node);
    }

    private void createInfluentValveStateSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        InfluentValveStateSensor sensor = new InfluentValveStateSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureInfluentValveStateSensor(Node node) {
        InfluentValveStateSensor sensor = (InfluentValveStateSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((InfluentValve) BiosimInitializer.getModule(myID, getInputName(node)));
        configureSensor(sensor, node);
    }

    private void createEffluentValveStateSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        EffluentValveStateSensor sensor = new EffluentValveStateSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureEffluentValveStateSensor(Node node) {
        EffluentValveStateSensor sensor = (EffluentValveStateSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((EffluentValve) BiosimInitializer.getModule(myID, getInputName(node)));
        configureSensor(sensor, node);
    }

    private void createTimeSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        TimeSensor sensor = new TimeSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureTimeSensor(Node node) {
        TimeSensor sensor = (TimeSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        configureSensor(sensor, node);
    }

    // **************** Power Sensors ********************

    private void createPowerInFlowRateSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        PowerInFlowRateSensor sensor = new PowerInFlowRateSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configurePowerInFlowRateSensor(Node node) {
        PowerInFlowRateSensor sensor = (PowerInFlowRateSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((PowerConsumer) BiosimInitializer.getModule(myID, getInputName(node)), getFlowRateIndex(node));
        configureSensor(sensor, node);
    }

    private void createPowerOutFlowRateSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        PowerOutFlowRateSensor sensor = new PowerOutFlowRateSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configurePowerOutFlowRateSensor(Node node) {
        PowerOutFlowRateSensor sensor = (PowerOutFlowRateSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((PowerProducer) BiosimInitializer.getModule(myID, getInputName(node)), getFlowRateIndex(node));
        configureSensor(sensor, node);
    }

    // **************** Water Sensors ********************

    private void createPotableWaterInFlowRateSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        PotableWaterInFlowRateSensor sensor = new PotableWaterInFlowRateSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configurePotableWaterInFlowRateSensor(Node node) {
        PotableWaterInFlowRateSensor sensor = (PotableWaterInFlowRateSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((PotableWaterConsumer) BiosimInitializer.getModule(myID, getInputName(node)), getFlowRateIndex(node));
        configureSensor(sensor, node);
    }

    private void createPotableWaterOutFlowRateSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        PotableWaterOutFlowRateSensor sensor = new PotableWaterOutFlowRateSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configurePotableWaterOutFlowRateSensor(Node node) {
        PotableWaterOutFlowRateSensor sensor = (PotableWaterOutFlowRateSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((PotableWaterProducer) BiosimInitializer.getModule(myID, getInputName(node)), getFlowRateIndex(node));
        configureSensor(sensor, node);
    }

    private void createGreyWaterInFlowRateSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        GreyWaterInFlowRateSensor sensor = new GreyWaterInFlowRateSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureGreyWaterInFlowRateSensor(Node node) {
        GreyWaterInFlowRateSensor sensor = (GreyWaterInFlowRateSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((GreyWaterConsumer) BiosimInitializer.getModule(myID, getInputName(node)), getFlowRateIndex(node));
        configureSensor(sensor, node);
    }

    private void createGreyWaterOutFlowRateSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        GreyWaterOutFlowRateSensor sensor = new GreyWaterOutFlowRateSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureGreyWaterOutFlowRateSensor(Node node) {
        GreyWaterOutFlowRateSensor sensor = (GreyWaterOutFlowRateSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((GreyWaterProducer) BiosimInitializer.getModule(myID, getInputName(node)), getFlowRateIndex(node));
        configureSensor(sensor, node);
    }

    private void createDirtyWaterInFlowRateSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        DirtyWaterInFlowRateSensor sensor = new DirtyWaterInFlowRateSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureDirtyWaterInFlowRateSensor(Node node) {
        DirtyWaterInFlowRateSensor sensor = (DirtyWaterInFlowRateSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((DirtyWaterConsumer) BiosimInitializer.getModule(myID, getInputName(node)), getFlowRateIndex(node));
        configureSensor(sensor, node);
    }

    private void createDirtyWaterOutFlowRateSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        DirtyWaterOutFlowRateSensor sensor = new DirtyWaterOutFlowRateSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureDirtyWaterOutFlowRateSensor(Node node) {
        DirtyWaterOutFlowRateSensor sensor = (DirtyWaterOutFlowRateSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((DirtyWaterProducer) BiosimInitializer.getModule(myID, getInputName(node)), getFlowRateIndex(node));
        configureSensor(sensor, node);
    }

    // **************** Waste Sensors ********************

    private void createDryWasteInFlowRateSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        DryWasteInFlowRateSensor sensor = new DryWasteInFlowRateSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureDryWasteInFlowRateSensor(Node node) {
        DryWasteInFlowRateSensor sensor = (DryWasteInFlowRateSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((DryWasteConsumer) BiosimInitializer.getModule(myID, getInputName(node)), getFlowRateIndex(node));
        configureSensor(sensor, node);
    }

    private void createDryWasteOutFlowRateSensor(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        DryWasteOutFlowRateSensor sensor = new DryWasteOutFlowRateSensor(myID, moduleName);
        BiosimInitializer.setupBioModule(myID, sensor, node);
    }

    private void configureDryWasteOutFlowRateSensor(Node node) {
        DryWasteOutFlowRateSensor sensor = (DryWasteOutFlowRateSensor) BiosimInitializer.getModule(myID, BiosimInitializer.getModuleName(node));
        sensor.setInput((DryWasteProducer) BiosimInitializer.getModule(myID, getInputName(node)), getFlowRateIndex(node));
        configureSensor(sensor, node);
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
