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
import com.traclabs.biosim.server.simulation.air.*;
import com.traclabs.biosim.server.simulation.environment.AirConsumer;
import com.traclabs.biosim.server.simulation.environment.AirProducer;
import com.traclabs.biosim.server.simulation.food.*;
import com.traclabs.biosim.server.simulation.framework.EffluentValve;
import com.traclabs.biosim.server.simulation.framework.InfluentValve;
import com.traclabs.biosim.server.simulation.power.PowerConsumer;
import com.traclabs.biosim.server.simulation.power.PowerProducer;
import com.traclabs.biosim.server.simulation.waste.DryWasteConsumer;
import com.traclabs.biosim.server.simulation.waste.DryWasteProducer;
import com.traclabs.biosim.server.simulation.water.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;

import java.util.HashSet;
import java.util.Set;

/**
 * Reads BioSim configuration from XML file.
 *
 * @author Scott Bell
 */
public class ActuatorInitializer {
    private final Set<GenericActuator> myActuators;
    private final Logger myLogger;
    private int myID = 0;

    /**
     * Default constructor.
     */
    public ActuatorInitializer(int pID) {
        myID = pID;
        myActuators = new HashSet<GenericActuator>();
        myLogger = LoggerFactory.getLogger(this.getClass());
    }

    private static int getShelfIndex(Node pNode) {
        return Integer.parseInt(pNode.getAttributes().getNamedItem("shelfIndex").getNodeValue());
    }

    private static String getOutputName(Node pNode) {
        return pNode.getAttributes().getNamedItem("output").getNodeValue();
    }

    private static int getFlowRateIndex(Node pNode) {
        return Integer.parseInt(pNode.getAttributes().getNamedItem("index").getNodeValue());
    }

    /////////////////
    // AIR ACTUATORS

    /// //////////////

    private void createCO2InFlowRateActuator(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        CO2InFlowRateActuator actuator = new CO2InFlowRateActuator(myID, moduleName);
        BiosimInitializer.setupBioModule(actuator, node);
    }

    private void configureCO2InFlowRateActuator(Node node) {
        CO2InFlowRateActuator actuator = (CO2InFlowRateActuator) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(BiosimInitializer.getModuleName(node));
        actuator.setOutput((CO2Consumer) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(getOutputName(node)), getFlowRateIndex(node));
        myActuators.add(actuator);
    }

    private void createCO2OutFlowRateActuator(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        CO2OutFlowRateActuator actuator = new CO2OutFlowRateActuator(myID, moduleName);
        BiosimInitializer.setupBioModule(actuator, node);
    }

    private void configureCO2OutFlowRateActuator(Node node) {
        CO2OutFlowRateActuator actuator = (CO2OutFlowRateActuator) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(BiosimInitializer.getModuleName(node));
        actuator.setOutput((CO2Producer) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(getOutputName(node)), getFlowRateIndex(node));
        myActuators.add(actuator);
    }

    private void createO2InFlowRateActuator(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        O2InFlowRateActuator actuator = new O2InFlowRateActuator(myID, moduleName);
        BiosimInitializer.setupBioModule(actuator, node);
    }

    private void configureO2InFlowRateActuator(Node node) {
        O2InFlowRateActuator actuator = (O2InFlowRateActuator) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(BiosimInitializer.getModuleName(node));
        actuator.setOutput((O2Consumer) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(getOutputName(node)), getFlowRateIndex(node));
        myActuators.add(actuator);
    }

    private void createO2OutFlowRateActuator(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        O2OutFlowRateActuator actuator = new O2OutFlowRateActuator(myID, moduleName);
        BiosimInitializer.setupBioModule(actuator, node);
    }

    private void configureO2OutFlowRateActuator(Node node) {
        O2OutFlowRateActuator actuator = (O2OutFlowRateActuator) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(BiosimInitializer.getModuleName(node));
        actuator.setOutput((O2Producer) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(getOutputName(node)), getFlowRateIndex(node));
        myActuators.add(actuator);
    }

    private void createH2InFlowRateActuator(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        H2InFlowRateActuator actuator = new H2InFlowRateActuator(myID, moduleName);
        BiosimInitializer.setupBioModule(actuator, node);
    }

    private void configureH2InFlowRateActuator(Node node) {
        H2InFlowRateActuator actuator = (H2InFlowRateActuator) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(BiosimInitializer.getModuleName(node));
        actuator.setOutput((H2Consumer) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(getOutputName(node)), getFlowRateIndex(node));
        myActuators.add(actuator);
    }

    private void createH2OutFlowRateActuator(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        H2OutFlowRateActuator actuator = new H2OutFlowRateActuator(myID, moduleName);
        BiosimInitializer.setupBioModule(actuator, node);
    }

    private void configureH2OutFlowRateActuator(Node node) {
        H2OutFlowRateActuator actuator = (H2OutFlowRateActuator) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(BiosimInitializer.getModuleName(node));
        actuator.setOutput((H2Producer) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(getOutputName(node)), getFlowRateIndex(node));
        myActuators.add(actuator);
    }

    private void createNitrogenInFlowRateActuator(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        NitrogenInFlowRateActuator actuator = new NitrogenInFlowRateActuator(myID, moduleName);
        BiosimInitializer.setupBioModule(actuator, node);
    }

    private void configureNitrogenInFlowRateActuator(Node node) {
        NitrogenInFlowRateActuator actuator = (NitrogenInFlowRateActuator) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(BiosimInitializer.getModuleName(node));
        actuator.setOutput((NitrogenConsumer) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(getOutputName(node)), getFlowRateIndex(node));
        myActuators.add(actuator);
    }

    private void createNitrogenOutFlowRateActuator(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        NitrogenOutFlowRateActuator actuator = new NitrogenOutFlowRateActuator(myID, moduleName);
        BiosimInitializer.setupBioModule(actuator, node);
    }

    private void configureNitrogenOutFlowRateActuator(Node node) {
        NitrogenOutFlowRateActuator actuator = (NitrogenOutFlowRateActuator) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(BiosimInitializer.getModuleName(node));
        actuator.setOutput((NitrogenProducer) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(getOutputName(node)), getFlowRateIndex(node));
        myActuators.add(actuator);
    }

    private void createMethaneInFlowRateActuator(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        MethaneInFlowRateActuator actuator = new MethaneInFlowRateActuator(myID, moduleName);
        BiosimInitializer.setupBioModule(actuator, node);
    }

    private void configureMethaneInFlowRateActuator(Node node) {
        MethaneInFlowRateActuator actuator = (MethaneInFlowRateActuator) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(BiosimInitializer.getModuleName(node));
        actuator.setOutput((MethaneConsumer) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(getOutputName(node)), getFlowRateIndex(node));
        myActuators.add(actuator);
    }

    private void createMethaneOutFlowRateActuator(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        MethaneOutFlowRateActuator actuator = new MethaneOutFlowRateActuator(myID, moduleName);
        BiosimInitializer.setupBioModule(actuator, node);
    }

    private void configureMethaneOutFlowRateActuator(Node node) {
        MethaneOutFlowRateActuator actuator = (MethaneOutFlowRateActuator) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(BiosimInitializer.getModuleName(node));
        actuator.setOutput((MethaneProducer) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(getOutputName(node)), getFlowRateIndex(node));
        myActuators.add(actuator);
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

    /////////////////
    // ENVIRONMENT ACTUATORS

    /// //////////////

    private void createAirInFlowRateActuator(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        AirInFlowRateActuator actuator = new AirInFlowRateActuator(myID, moduleName);
        BiosimInitializer.setupBioModule(actuator, node);
    }

    private void configureAirInFlowRateActuator(Node node) {
        AirInFlowRateActuator actuator = (AirInFlowRateActuator) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(BiosimInitializer.getModuleName(node));
        actuator.setOutput((AirConsumer) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(getOutputName(node)), getFlowRateIndex(node));
        myActuators.add(actuator);
    }

    private void createAirOutFlowRateActuator(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        AirOutFlowRateActuator actuator = new AirOutFlowRateActuator(myID, moduleName);
        BiosimInitializer.setupBioModule(actuator, node);
    }

    private void configureAirOutFlowRateActuator(Node node) {
        AirOutFlowRateActuator actuator = (AirOutFlowRateActuator) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(BiosimInitializer.getModuleName(node));
        actuator.setOutput((AirProducer) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(getOutputName(node)), getFlowRateIndex(node));
        myActuators.add(actuator);
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

    /////////////////
    // FOOD ACTUATORS

    /// //////////////

    private void createBiomassInFlowRateActuator(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        BiomassInFlowRateActuator actuator = new BiomassInFlowRateActuator(myID, moduleName);
        BiosimInitializer.setupBioModule(actuator, node);
    }

    private void configureBiomassInFlowRateActuator(Node node) {
        BiomassInFlowRateActuator actuator = (BiomassInFlowRateActuator) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(BiosimInitializer.getModuleName(node));
        actuator.setOutput((BiomassConsumer) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(getOutputName(node)), getFlowRateIndex(node));
        myActuators.add(actuator);
    }

    private void createBiomassOutFlowRateActuator(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        BiomassOutFlowRateActuator actuator = new BiomassOutFlowRateActuator(myID, moduleName);
        BiosimInitializer.setupBioModule(actuator, node);
    }

    private void configureBiomassOutFlowRateActuator(Node node) {
        BiomassOutFlowRateActuator actuator = (BiomassOutFlowRateActuator) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(BiosimInitializer.getModuleName(node));
        actuator.setOutput((BiomassProducer) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(getOutputName(node)), getFlowRateIndex(node));
        myActuators.add(actuator);
    }

    private void createFoodInFlowRateActuator(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        FoodInFlowRateActuator actuator = new FoodInFlowRateActuator(myID, moduleName);
        BiosimInitializer.setupBioModule(actuator, node);
    }

    private void configureFoodInFlowRateActuator(Node node) {
        FoodInFlowRateActuator actuator = (FoodInFlowRateActuator) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(BiosimInitializer.getModuleName(node));
        actuator.setOutput((FoodConsumer) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(getOutputName(node)), getFlowRateIndex(node));
        myActuators.add(actuator);
    }

    private void createFoodOutFlowRateActuator(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        FoodOutFlowRateActuator actuator = new FoodOutFlowRateActuator(myID, moduleName);
        BiosimInitializer.setupBioModule(actuator, node);
    }

    private void configureFoodOutFlowRateActuator(Node node) {
        FoodOutFlowRateActuator actuator = (FoodOutFlowRateActuator) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(BiosimInitializer.getModuleName(node));
        actuator.setOutput((FoodProducer) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(getOutputName(node)), getFlowRateIndex(node));
        myActuators.add(actuator);
    }

    private void createPlantingActuator(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        PlantingActuator actuator = new PlantingActuator(myID, moduleName);
        BiosimInitializer.setupBioModule(actuator, node);
    }

    private void configurePlantingActuator(Node node) {
        PlantingActuator actuator = (PlantingActuator) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(BiosimInitializer.getModuleName(node));
        actuator.setOutput((BiomassPS) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(getOutputName(node)), getShelfIndex(node));
        myActuators.add(actuator);
    }

    private void createHarvestingActuator(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        HarvestingActuator actuator = new HarvestingActuator(myID, moduleName);
        BiosimInitializer.setupBioModule(actuator, node);
    }

    private void configureHarvestingActuator(Node node) {
        HarvestingActuator actuator = (HarvestingActuator) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(BiosimInitializer.getModuleName(node));
        actuator.setOutput((BiomassPS) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(getOutputName(node)), getShelfIndex(node));
        myActuators.add(actuator);
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

    /////////////////
    // FRAMEWORK ACTUATORS

    /// //////////////

    private void createInfluentValveActuator(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        InfluentValveActuator actuator = new InfluentValveActuator(myID, moduleName);
        BiosimInitializer.setupBioModule(actuator, node);
    }

    private void configureInfluentValveActuator(Node node) {
        InfluentValveActuator actuator = (InfluentValveActuator) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(BiosimInitializer.getModuleName(node));
        actuator.setOutput((InfluentValve) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(getOutputName(node)));
        myActuators.add(actuator);
    }

    private void createEffluentValveActuator(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        EffluentValveActuator actuator = new EffluentValveActuator(myID, moduleName);
        BiosimInitializer.setupBioModule(actuator, node);
    }

    private void configureEffluentValveActuator(Node node) {
        EffluentValveActuator actuator = (EffluentValveActuator) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(BiosimInitializer.getModuleName(node));
        actuator.setOutput((EffluentValve) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(getOutputName(node)));
        myActuators.add(actuator);
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

    /////////////////
    // POWER ACTUATORS

    /// //////////////

    private void createPowerInFlowRateActuator(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        PowerInFlowRateActuator actuator = new PowerInFlowRateActuator(myID, moduleName);
        BiosimInitializer.setupBioModule(actuator, node);
    }

    private void configurePowerInFlowRateActuator(Node node) {
        PowerInFlowRateActuator actuator = (PowerInFlowRateActuator) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(BiosimInitializer.getModuleName(node));
        actuator.setOutput((PowerConsumer) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(getOutputName(node)), getFlowRateIndex(node));
        myActuators.add(actuator);
    }

    private void createPowerOutFlowRateActuator(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        PowerOutFlowRateActuator actuator = new PowerOutFlowRateActuator(myID, moduleName);
        BiosimInitializer.setupBioModule(actuator, node);
    }

    private void configurePowerOutFlowRateActuator(Node node) {
        PowerOutFlowRateActuator actuator = (PowerOutFlowRateActuator) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(BiosimInitializer.getModuleName(node));
        actuator.setOutput((PowerProducer) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(getOutputName(node)), getFlowRateIndex(node));
        myActuators.add(actuator);
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

    /////////////////
    // WATER ACTUATORS

    /// //////////////

    private void createPotableWaterInFlowRateActuator(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        PotableWaterInFlowRateActuator actuator = new PotableWaterInFlowRateActuator(myID, moduleName);
        BiosimInitializer.setupBioModule(actuator, node);
    }

    private void configurePotableWaterInFlowRateActuator(Node node) {
        PotableWaterInFlowRateActuator actuator = (PotableWaterInFlowRateActuator) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(BiosimInitializer.getModuleName(node));
        actuator.setOutput((PotableWaterConsumer) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(getOutputName(node)), getFlowRateIndex(node));
        myActuators.add(actuator);
    }

    private void createPotableWaterOutFlowRateActuator(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        PotableWaterOutFlowRateActuator actuator = new PotableWaterOutFlowRateActuator(myID, moduleName);
        BiosimInitializer.setupBioModule(actuator, node);
    }

    private void configurePotableWaterOutFlowRateActuator(Node node) {
        PotableWaterOutFlowRateActuator actuator = (PotableWaterOutFlowRateActuator) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(BiosimInitializer.getModuleName(node));
        actuator.setOutput((PotableWaterProducer) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(getOutputName(node)), getFlowRateIndex(node));
        myActuators.add(actuator);
    }

    private void createGreyWaterInFlowRateActuator(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        GreyWaterInFlowRateActuator actuator = new GreyWaterInFlowRateActuator(myID, moduleName);
        BiosimInitializer.setupBioModule(actuator, node);
    }

    private void configureGreyWaterInFlowRateActuator(Node node) {
        GreyWaterInFlowRateActuator actuator = (GreyWaterInFlowRateActuator) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(BiosimInitializer.getModuleName(node));
        actuator.setOutput((GreyWaterConsumer) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(getOutputName(node)), getFlowRateIndex(node));
        myActuators.add(actuator);
    }

    private void createGreyWaterOutFlowRateActuator(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        GreyWaterOutFlowRateActuator actuator = new GreyWaterOutFlowRateActuator(myID, moduleName);
        BiosimInitializer.setupBioModule(actuator, node);
    }

    private void configureGreyWaterOutFlowRateActuator(Node node) {
        GreyWaterOutFlowRateActuator actuator = (GreyWaterOutFlowRateActuator) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(BiosimInitializer.getModuleName(node));
        actuator.setOutput((GreyWaterProducer) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(getOutputName(node)), getFlowRateIndex(node));
        myActuators.add(actuator);
    }

    private void createDirtyWaterInFlowRateActuator(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        DirtyWaterInFlowRateActuator actuator = new DirtyWaterInFlowRateActuator(myID, moduleName);
        BiosimInitializer.setupBioModule(actuator, node);
    }

    private void configureDirtyWaterInFlowRateActuator(Node node) {
        DirtyWaterInFlowRateActuator actuator = (DirtyWaterInFlowRateActuator) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(BiosimInitializer.getModuleName(node));
        actuator.setOutput((DirtyWaterConsumer) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(getOutputName(node)), getFlowRateIndex(node));
        myActuators.add(actuator);
    }

    private void createDirtyWaterOutFlowRateActuator(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        DirtyWaterOutFlowRateActuator actuator = new DirtyWaterOutFlowRateActuator(myID, moduleName);
        BiosimInitializer.setupBioModule(actuator, node);
    }

    private void configureDirtyWaterOutFlowRateActuator(Node node) {
        DirtyWaterOutFlowRateActuator actuator = (DirtyWaterOutFlowRateActuator) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(BiosimInitializer.getModuleName(node));
        actuator.setOutput((DirtyWaterProducer) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(getOutputName(node)), getFlowRateIndex(node));
        myActuators.add(actuator);
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

    /////////////////
    // WASTE ACTUATORS

    /// //////////////

    private void createDryWasteInFlowRateActuator(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        DryWasteInFlowRateActuator actuator = new DryWasteInFlowRateActuator(myID, moduleName);
        BiosimInitializer.setupBioModule(actuator, node);
    }

    private void configureDryWasteInFlowRateActuator(Node node) {
        DryWasteInFlowRateActuator actuator = (DryWasteInFlowRateActuator) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(BiosimInitializer.getModuleName(node));
        actuator.setOutput((DryWasteConsumer) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(getOutputName(node)), getFlowRateIndex(node));
        myActuators.add(actuator);
    }

    private void createDryWasteOutFlowRateActuator(Node node) {
        String moduleName = BiosimInitializer.getModuleName(node);
        DryWasteOutFlowRateActuator actuator = new DryWasteOutFlowRateActuator(myID, moduleName);
        BiosimInitializer.setupBioModule(actuator, node);
    }

    private void configureDryWasteOutFlowRateActuator(Node node) {
        DryWasteOutFlowRateActuator actuator = (DryWasteOutFlowRateActuator) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(BiosimInitializer.getModuleName(node));
        actuator.setOutput((DryWasteProducer) BiosimInitializer.getInstance(myID)
                .getBioDriver().getModule(getOutputName(node)), getFlowRateIndex(node));
        myActuators.add(actuator);
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

    /////////////////
    // ENDPOINT: CRAWL ALL ACTUATORS

    /// //////////////

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
    public Set<GenericActuator> getActuators() {
        return myActuators;
    }
}