package com.traclabs.biosim.client.util;

import java.net.URL;

import org.apache.log4j.Logger;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.traclabs.biosim.idl.actuator.air.CO2InFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.air.CO2OutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.air.H2InFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.air.H2OutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.air.NitrogenInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.air.NitrogenOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.air.O2InFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.air.O2OutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.AirInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.AirOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.CO2AirEnvironmentInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.CO2AirEnvironmentOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.CO2AirStoreInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.CO2AirStoreOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.NitrogenAirEnvironmentInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.NitrogenAirEnvironmentOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.NitrogenAirStoreInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.NitrogenAirStoreOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.O2AirEnvironmentInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.O2AirEnvironmentOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.O2AirStoreInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.O2AirStoreOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.WaterAirEnvironmentInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.WaterAirEnvironmentOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.WaterAirStoreInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.WaterAirStoreOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.food.BiomassInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.food.BiomassOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.food.FoodInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.food.FoodOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.food.HarvestingActuatorHelper;
import com.traclabs.biosim.idl.actuator.food.PlantingActuatorHelper;
import com.traclabs.biosim.idl.actuator.power.PowerInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.power.PowerOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.waste.DryWasteInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.waste.DryWasteOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.water.DirtyWaterInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.water.DirtyWaterOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.water.GreyWaterInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.water.GreyWaterOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.water.PotableWaterInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.water.PotableWaterOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.water.WaterInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.water.WaterOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.framework.BioDriverHelper;
import com.traclabs.biosim.idl.sensor.air.CO2InFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.CO2OutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.CO2StoreLevelSensorHelper;
import com.traclabs.biosim.idl.sensor.air.H2InFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.H2OutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.H2StoreLevelSensorHelper;
import com.traclabs.biosim.idl.sensor.air.NitrogenInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.NitrogenOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.NitrogenStoreLevelSensorHelper;
import com.traclabs.biosim.idl.sensor.air.O2InFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.O2OutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.O2StoreLevelSensorHelper;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupAnyDeadSensorHelper;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupDeathSensorHelper;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupProductivitySensorHelper;
import com.traclabs.biosim.idl.sensor.environment.AirInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.AirOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.CO2AirConcentrationSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.CO2AirEnvironmentInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.CO2AirEnvironmentOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.CO2AirPressureSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.CO2AirStoreInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.CO2AirStoreOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.NitrogenAirConcentrationSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.NitrogenAirEnvironmentInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.NitrogenAirEnvironmentOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.NitrogenAirPressureSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.NitrogenAirStoreInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.NitrogenAirStoreOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.O2AirConcentrationSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.O2AirEnvironmentInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.O2AirEnvironmentOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.O2AirPressureSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.O2AirStoreInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.O2AirStoreOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.OtherAirConcentrationSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.OtherAirPressureSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.WaterAirConcentrationSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.WaterAirEnvironmentInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.WaterAirEnvironmentOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.WaterAirPressureSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.WaterAirStoreInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.WaterAirStoreOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.food.BiomassInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.food.BiomassOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.food.BiomassStoreLevelSensorHelper;
import com.traclabs.biosim.idl.sensor.food.BiomassStoreWaterContentSensorHelper;
import com.traclabs.biosim.idl.sensor.food.FoodInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.food.FoodOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.food.FoodStoreLevelSensorHelper;
import com.traclabs.biosim.idl.sensor.food.HarvestSensorHelper;
import com.traclabs.biosim.idl.sensor.food.PlantDeathSensorHelper;
import com.traclabs.biosim.idl.sensor.framework.StoreLevelSensorHelper;
import com.traclabs.biosim.idl.sensor.framework.StoreOverflowSensorHelper;
import com.traclabs.biosim.idl.sensor.power.PowerInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.power.PowerOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.power.PowerStoreLevelSensorHelper;
import com.traclabs.biosim.idl.sensor.waste.DryWasteInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.waste.DryWasteOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.waste.DryWasteStoreLevelSensorHelper;
import com.traclabs.biosim.idl.sensor.water.DirtyWaterInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.DirtyWaterOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.DirtyWaterStoreLevelSensorHelper;
import com.traclabs.biosim.idl.sensor.water.GreyWaterInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.GreyWaterOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.GreyWaterStoreLevelSensorHelper;
import com.traclabs.biosim.idl.sensor.water.PotableWaterInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.PotableWaterOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.PotableWaterStoreLevelSensorHelper;
import com.traclabs.biosim.idl.sensor.water.WaterInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.WaterOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.WaterStoreLevelSensorHelper;
import com.traclabs.biosim.idl.simulation.air.AirRSHelper;
import com.traclabs.biosim.idl.simulation.air.CO2StoreHelper;
import com.traclabs.biosim.idl.simulation.air.H2StoreHelper;
import com.traclabs.biosim.idl.simulation.air.NitrogenStoreHelper;
import com.traclabs.biosim.idl.simulation.air.O2StoreHelper;
import com.traclabs.biosim.idl.simulation.air.OGSHelper;
import com.traclabs.biosim.idl.simulation.air.VCCRHelper;
import com.traclabs.biosim.idl.simulation.crew.CrewGroupHelper;
import com.traclabs.biosim.idl.simulation.environment.DehumidifierHelper;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironmentHelper;
import com.traclabs.biosim.idl.simulation.food.BiomassRSHelper;
import com.traclabs.biosim.idl.simulation.food.BiomassStoreHelper;
import com.traclabs.biosim.idl.simulation.food.FoodProcessorHelper;
import com.traclabs.biosim.idl.simulation.food.FoodStoreHelper;
import com.traclabs.biosim.idl.simulation.framework.AccumulatorHelper;
import com.traclabs.biosim.idl.simulation.framework.InjectorHelper;
import com.traclabs.biosim.idl.simulation.power.PowerPSHelper;
import com.traclabs.biosim.idl.simulation.power.PowerStoreHelper;
import com.traclabs.biosim.idl.simulation.waste.DryWasteStoreHelper;
import com.traclabs.biosim.idl.simulation.waste.IncineratorHelper;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterStoreHelper;
import com.traclabs.biosim.idl.simulation.water.GreyWaterStoreHelper;
import com.traclabs.biosim.idl.simulation.water.PotableWaterStoreHelper;
import com.traclabs.biosim.idl.simulation.water.WaterRSHelper;

/**
 * Reads BioSim configuration from XML file.
 * 
 * @author Scott Bell
 */
public class BioHolderInitializer {
    /** Namespaces feature id (http://xml.org/sax/features/moduleNamespaces). */
    private static final String NAMESPACES_FEATURE_ID = "http://xml.org/sax/features/namespaces";

    /** Validation feature id (http://xml.org/sax/features/validation). */
    private static final String VALIDATION_FEATURE_ID = "http://xml.org/sax/features/validation";

    /**
     * Schema validation feature id
     * (http://apache.org/xml/features/validation/schema).
     */
    private static final String SCHEMA_VALIDATION_FEATURE_ID = "http://apache.org/xml/features/validation/schema";

    /**
     * Schema full checking feature id
     * (http://apache.org/xml/features/validation/schema-full-checking).
     */
    private static final String SCHEMA_FULL_CHECKING_FEATURE_ID = "http://apache.org/xml/features/validation/schema-full-checking";

    // default settings
    /** Default moduleNamespaces support (true). */
    private static final boolean DEFAULT_NAMESPACES = true;

    /** Default validation support (false). */
    private static final boolean DEFAULT_VALIDATION = true;

    /** Default Schema validation support (false). */
    private static final boolean DEFAULT_SCHEMA_VALIDATION = true;

    /** Default Schema full checking support (false). */
    private static final boolean DEFAULT_SCHEMA_FULL_CHECKING = true;

    private static String xmlLocation = "com/traclabs/biosim/server/framework/DefaultInit.xml";

    private static BioHolder myBioHolder = null;

    private static DOMParser myParser = null;

    private static int myID = 0;

    private static boolean initialized = false;

    private static synchronized void initialize() {
        if (initialized)
            return;
        myBioHolder = new BioHolder();
        try {
            myParser = new DOMParser();
            myParser.setFeature(SCHEMA_VALIDATION_FEATURE_ID,
                    DEFAULT_SCHEMA_VALIDATION);
            myParser.setFeature(SCHEMA_FULL_CHECKING_FEATURE_ID,
                    DEFAULT_SCHEMA_FULL_CHECKING);
            myParser.setFeature(VALIDATION_FEATURE_ID, DEFAULT_VALIDATION);
            myParser.setFeature(NAMESPACES_FEATURE_ID, DEFAULT_NAMESPACES);
        } catch (SAXException e) {
            Logger.getLogger(BioHolderInitializer.class).warn(
                    "warning: Parser does not support feature ("
                            + NAMESPACES_FEATURE_ID + ")");
        }
        parseFile();
        initialized = true;
    }

    public static void reset() {
        initialized = false;
    }

    public static BioHolder getBioHolder() {
        initialize();
        return myBioHolder;
    }

    public static int getID() {
        return myID;
    }
    
    public static void setFileAndID(int pID, String pFilename){
        if ((myID == pID) && (xmlLocation.equals(pFilename)))
            return;
        myID = pID;
        xmlLocation = pFilename;
        initialized = false;
        initialize();
    }

    public static void setID(int pID) {
        if (myID == pID)
            return;
        myID = pID;
        initialized = false;
        initialize();
    }

    public static void setFile(String pFilename) {
        if (xmlLocation.equals(pFilename))
            return;
        xmlLocation = pFilename;
        initialized = false;
        initialize();
    }

    /** Traverses the specified node, recursively. */
    private static void crawlBiosim(Node node) {
        // is there anything to do?
        if (node == null)
            return;
        String nodeName = node.getNodeName();
        if (nodeName.equals("SimBioModules")) {
            crawlModules(node);
            return;
        } else if (nodeName.equals("Sensors")) {
            crawlSensors(node);
            return;
        } else if (nodeName.equals("Actuators")) {
            crawlActuators(node);
            return;
        } else {
            Node child = node.getFirstChild();
            while (child != null) {
                crawlBiosim(child);
                child = child.getNextSibling();
            }
        }

    }

    private static void parseFile() {
        myBioHolder.theBioDriver = BioDriverHelper
                .narrow(grabModule("BioDriver"));
        URL documentUrl = ClassLoader.getSystemClassLoader().getResource(
                xmlLocation);
        Logger.getLogger(BioHolderInitializer.class).debug(
                "parsing xml file: " + xmlLocation);
        if (documentUrl == null) {
            Logger.getLogger(BioHolderInitializer.class).error(
                    "Couldn't find init xml file: " + xmlLocation);
            Logger.getLogger(BioHolderInitializer.class).error("Exiting...");
            System.exit(1);
        }
        String documentString = documentUrl.toString();
        if (documentString.length() > 0) {
            try {
                Logger.getLogger(BioHolderInitializer.class).info(
                        "Initializing...");
                myParser.parse(documentString);
                Document document = myParser.getDocument();
                crawlBiosim(document);
                Logger.getLogger(BioHolderInitializer.class).info("done");
            } catch (Exception e) {
                Logger.getLogger(BioHolderInitializer.class).error(
                        "error: Parse error occurred - " + e.getMessage());
                Exception se = e;
                if (e instanceof SAXException)
                    se = ((SAXException) e).getException();
                if (se != null)
                    se.printStackTrace();
                else
                    e.printStackTrace();
            }
            myBioHolder.coallateLists();
        }
    }

    public static org.omg.CORBA.Object grabModule(String moduleName) {
        return OrbUtils.getBioModule(myID, moduleName);
    }

    private static String getModuleName(Node node) {
        return node.getAttributes().getNamedItem("name").getNodeValue();
    }

    //Modules
    private static void crawlModules(Node node) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("air"))
                crawlAirModules(child);
            else if (childName.equals("crew"))
                crawlCrewModules(child);
            else if (childName.equals("environment"))
                crawlEnvironmentModules(child);
            else if (childName.equals("food"))
                crawlFoodModules(child);
            else if (childName.equals("framework"))
                crawlFrameworkModules(child);
            else if (childName.equals("power"))
                crawlPowerModules(child);
            else if (childName.equals("water"))
                crawlWaterModules(child);
            else if (childName.equals("waste"))
                crawlWasteModules(child);
            child = child.getNextSibling();
        }
    }

    private static void fetchAirRS(Node node) {
        myBioHolder.theAirRSModules.add(AirRSHelper
                .narrow(grabModule(getModuleName(node))));
    }
    
    private static void fetchOGS(Node node) {
        myBioHolder.theOGSModules.add(OGSHelper
                .narrow(grabModule(getModuleName(node))));
    }
    
    private static void fetchVCCR(Node node) {
        myBioHolder.theVCCRModules.add(VCCRHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchO2Store(Node node) {
        myBioHolder.theO2Stores.add(O2StoreHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchCO2Store(Node node) {
        myBioHolder.theCO2Stores.add(CO2StoreHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchH2Store(Node node) {
        myBioHolder.theH2Stores.add(H2StoreHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchNitrogenStore(Node node) {
        myBioHolder.theNitrogenStores.add(NitrogenStoreHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void crawlAirModules(Node node) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("AirRS"))
                fetchAirRS(child);
            else if (childName.equals("OGS"))
                fetchOGS(child);
            else if (childName.equals("VCCR"))
                fetchVCCR(child);
            else if (childName.equals("O2Store"))
                fetchO2Store(child);
            else if (childName.equals("CO2Store"))
                fetchCO2Store(child);
            else if (childName.equals("H2Store"))
                fetchH2Store(child);
            else if (childName.equals("NitrogenStore"))
                fetchNitrogenStore(child);
            child = child.getNextSibling();
        }
    }

    private static void fetchCrewGroup(Node node) {
        myBioHolder.theCrewGroups.add(CrewGroupHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void crawlCrewModules(Node node) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("CrewGroup"))
                fetchCrewGroup(child);
            child = child.getNextSibling();
        }
    }

    private static void fetchSimEnvironment(Node node) {
        myBioHolder.theSimEnvironments.add(SimEnvironmentHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchDehumidifier(Node node) {
        myBioHolder.theDehumidifiers.add(DehumidifierHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void crawlEnvironmentModules(Node node) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("SimEnvironment"))
                fetchSimEnvironment(child);
            else if (childName.equals("Dehumidifier"))
                fetchDehumidifier(child);
            child = child.getNextSibling();
        }
    }

    private static void fetchAccumulator(Node node) {
        myBioHolder.theAccumulators.add(AccumulatorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchInjector(Node node) {
        myBioHolder.theInjectors.add(InjectorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void crawlFrameworkModules(Node node) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("Accumulator"))
                fetchAccumulator(child);
            else if (childName.equals("Injector"))
                fetchInjector(child);
            child = child.getNextSibling();
        }
    }

    private static void fetchBiomassRS(Node node) {
        myBioHolder.theBiomassRSModules.add(BiomassRSHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchFoodProcessor(Node node) {
        myBioHolder.theFoodProcessors.add(FoodProcessorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchBiomassStore(Node node) {
        myBioHolder.theBiomassStores.add(BiomassStoreHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchFoodStore(Node node) {
        myBioHolder.theFoodStores.add(FoodStoreHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void crawlFoodModules(Node node) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("BiomassRS"))
                fetchBiomassRS(child);
            else if (childName.equals("FoodProcessor"))
                fetchFoodProcessor(child);
            else if (childName.equals("BiomassStore"))
                fetchBiomassStore(child);
            else if (childName.equals("FoodStore"))
                fetchFoodStore(child);
            child = child.getNextSibling();
        }
    }

    private static void fetchPowerPS(Node node) {
        myBioHolder.thePowerPSModules.add(PowerPSHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchPowerStore(Node node) {
        myBioHolder.thePowerStores.add(PowerStoreHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void crawlPowerModules(Node node) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("PowerPS"))
                fetchPowerPS(child);
            else if (childName.equals("PowerStore"))
                fetchPowerStore(child);
            child = child.getNextSibling();
        }
    }

    private static void fetchWaterRS(Node node) {
        myBioHolder.theWaterRSModules.add(WaterRSHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchPotableWaterStore(Node node) {
        myBioHolder.thePotableWaterStores.add(PotableWaterStoreHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchDirtyWaterStore(Node node) {
        myBioHolder.theDirtyWaterStores.add(DirtyWaterStoreHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchGreyWaterStore(Node node) {
        myBioHolder.theGreyWaterStores.add(GreyWaterStoreHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void crawlWaterModules(Node node) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("WaterRS"))
                fetchWaterRS(child);
            else if (childName.equals("PotableWaterStore"))
                fetchPotableWaterStore(child);
            else if (childName.equals("GreyWaterStore"))
                fetchGreyWaterStore(child);
            else if (childName.equals("DirtyWaterStore"))
                fetchDirtyWaterStore(child);
            child = child.getNextSibling();
        }
    }

    private static void fetchIncinerator(Node node) {
        myBioHolder.theIncinerators.add(IncineratorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchDryWasteStore(Node node) {
        myBioHolder.theDryWasteStores.add(DryWasteStoreHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void crawlWasteModules(Node node) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("Incinerator"))
                fetchIncinerator(child);
            else if (childName.equals("DryWasteStore"))
                fetchDryWasteStore(child);
            child = child.getNextSibling();
        }
    }

    //Sensors
    private static void crawlSensors(Node node) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("air"))
                crawlAirSensors(child);
            else if (childName.equals("crew"))
                crawlCrewSensors(child);
            else if (childName.equals("environment"))
                crawlEnvironmentSensors(child);
            else if (childName.equals("food"))
                crawlFoodSensors(child);
            else if (childName.equals("framework"))
                crawlFrameworkSensors(child);
            else if (childName.equals("power"))
                crawlPowerSensors(child);
            else if (childName.equals("water"))
                crawlWaterSensors(child);
            else if (childName.equals("waste"))
                crawlWasteSensors(child);
            child = child.getNextSibling();
        }
    }

    //Air
    private static void fetchCO2InFlowRateSensor(Node node) {
        myBioHolder.theCO2InFlowRateSensors.add(CO2InFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchCO2OutFlowRateSensor(Node node) {
        myBioHolder.theCO2OutFlowRateSensors.add(CO2OutFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchCO2StoreLevelSensor(Node node) {
        myBioHolder.theCO2StoreLevelSensors.add(CO2StoreLevelSensorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchO2InFlowRateSensor(Node node) {
        myBioHolder.theO2InFlowRateSensors.add(O2InFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchO2OutFlowRateSensor(Node node) {
        myBioHolder.theO2OutFlowRateSensors.add(O2OutFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchO2StoreLevelSensor(Node node) {
        myBioHolder.theO2StoreLevelSensors.add(O2StoreLevelSensorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchH2InFlowRateSensor(Node node) {
        myBioHolder.theH2InFlowRateSensors.add(H2InFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchH2OutFlowRateSensor(Node node) {
        myBioHolder.theH2OutFlowRateSensors.add(H2OutFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchH2StoreLevelSensor(Node node) {
        myBioHolder.theH2StoreLevelSensors.add(H2StoreLevelSensorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchNitrogenInFlowRateSensor(Node node) {
        myBioHolder.theNitrogenInFlowRateSensors
                .add(NitrogenInFlowRateSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchNitrogenOutFlowRateSensor(Node node) {
        myBioHolder.theNitrogenOutFlowRateSensors
                .add(NitrogenOutFlowRateSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchNitrogenStoreLevelSensor(Node node) {
        myBioHolder.theNitrogenStoreLevelSensors
                .add(NitrogenStoreLevelSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void crawlAirSensors(Node node) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("CO2InFlowRateSensor"))
                fetchCO2InFlowRateSensor(child);
            else if (childName.equals("CO2OutFlowRateSensor"))
                fetchCO2OutFlowRateSensor(child);
            else if (childName.equals("CO2StoreLevelSensor"))
                fetchCO2StoreLevelSensor(child);
            else if (childName.equals("O2InFlowRateSensor"))
                fetchO2InFlowRateSensor(child);
            else if (childName.equals("O2OutFlowRateSensor"))
                fetchO2OutFlowRateSensor(child);
            else if (childName.equals("O2StoreLevelSensor"))
                fetchO2StoreLevelSensor(child);
            else if (childName.equals("H2InFlowRateSensor"))
                fetchH2InFlowRateSensor(child);
            else if (childName.equals("H2OutFlowRateSensor"))
                fetchH2OutFlowRateSensor(child);
            else if (childName.equals("H2StoreLevelSensor"))
                fetchH2StoreLevelSensor(child);
            else if (childName.equals("NitrogenInFlowRateSensor"))
                fetchNitrogenInFlowRateSensor(child);
            else if (childName.equals("NitrogenOutFlowRateSensor"))
                fetchNitrogenOutFlowRateSensor(child);
            else if (childName.equals("NitrogenStoreLevelSensor"))
                fetchNitrogenStoreLevelSensor(child);
            child = child.getNextSibling();
        }
    }

    //Crew
    private static void fetchCrewGroupDeathSensor(Node node) {
        myBioHolder.theCrewGroupDeathSensors.add(CrewGroupDeathSensorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchCrewGroupAnyDeadSensor(Node node) {
        myBioHolder.theCrewGroupAnyDeadSensors.add(CrewGroupAnyDeadSensorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchCrewGroupProductivitySensor(Node node) {
        myBioHolder.theCrewGroupProductivitySensors
                .add(CrewGroupProductivitySensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void crawlCrewSensors(Node node) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("CrewGroupDeathSensor"))
                fetchCrewGroupDeathSensor(child);
            else if (childName.equals("CrewGroupAnyDeadSensor"))
                fetchCrewGroupAnyDeadSensor(child);
            else if (childName.equals("CrewGroupProductivitySensor"))
                fetchCrewGroupProductivitySensor(child);
            child = child.getNextSibling();
        }
    }

    //Environment
    private static void fetchAirInFlowRateSensor(Node node) {
        myBioHolder.theAirInFlowRateSensors.add(AirInFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchAirOutFlowRateSensor(Node node) {
        myBioHolder.theAirOutFlowRateSensors.add(AirOutFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchCO2AirConcentrationSensor(Node node) {
        myBioHolder.theCO2AirConcentrationSensors
                .add(CO2AirConcentrationSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchCO2AirPressureSensor(Node node) {
        myBioHolder.theCO2AirPressureSensors.add(CO2AirPressureSensorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchCO2AirEnvironmentInFlowRateSensor(Node node) {
        myBioHolder.theCO2AirEnvironmentInFlowRateSensors
                .add(CO2AirEnvironmentInFlowRateSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchCO2AirEnvironmentOutFlowRateSensor(Node node) {
        myBioHolder.theCO2AirEnvironmentOutFlowRateSensors
                .add(CO2AirEnvironmentOutFlowRateSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchCO2AirStoreInFlowRateSensor(Node node) {
        myBioHolder.theCO2AirStoreInFlowRateSensors
                .add(CO2AirStoreInFlowRateSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchCO2AirStoreOutFlowRateSensor(Node node) {
        myBioHolder.theCO2AirStoreOutFlowRateSensors
                .add(CO2AirStoreOutFlowRateSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchO2AirConcentrationSensor(Node node) {
        myBioHolder.theO2AirConcentrationSensors
                .add(O2AirConcentrationSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchO2AirPressureSensor(Node node) {
        myBioHolder.theO2AirPressureSensors.add(O2AirPressureSensorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchO2AirEnvironmentInFlowRateSensor(Node node) {
        myBioHolder.theO2AirEnvironmentInFlowRateSensors
                .add(O2AirEnvironmentInFlowRateSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchO2AirEnvironmentOutFlowRateSensor(Node node) {
        myBioHolder.theO2AirEnvironmentOutFlowRateSensors
                .add(O2AirEnvironmentOutFlowRateSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchO2AirStoreInFlowRateSensor(Node node) {
        myBioHolder.theO2AirStoreInFlowRateSensors
                .add(O2AirStoreInFlowRateSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchO2AirStoreOutFlowRateSensor(Node node) {
        myBioHolder.theO2AirStoreOutFlowRateSensors
                .add(O2AirStoreOutFlowRateSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchOtherAirConcentrationSensor(Node node) {
        myBioHolder.theOtherAirConcentrationSensors
                .add(OtherAirConcentrationSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchOtherAirPressureSensor(Node node) {
        myBioHolder.theOtherAirPressureSensors.add(OtherAirPressureSensorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchWaterAirConcentrationSensor(Node node) {
        myBioHolder.theWaterAirConcentrationSensors
                .add(WaterAirConcentrationSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchWaterAirPressureSensor(Node node) {
        myBioHolder.theWaterAirPressureSensors.add(WaterAirPressureSensorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchWaterAirEnvironmentInFlowRateSensor(Node node) {
        myBioHolder.theWaterAirEnvironmentInFlowRateSensors
                .add(WaterAirEnvironmentInFlowRateSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchWaterAirEnvironmentOutFlowRateSensor(Node node) {
        myBioHolder.theWaterAirEnvironmentOutFlowRateSensors
                .add(WaterAirEnvironmentOutFlowRateSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchWaterAirStoreInFlowRateSensor(Node node) {
        myBioHolder.theWaterAirStoreInFlowRateSensors
                .add(WaterAirStoreInFlowRateSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchWaterAirStoreOutFlowRateSensor(Node node) {
        myBioHolder.theWaterAirStoreOutFlowRateSensors
                .add(WaterAirStoreOutFlowRateSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchNitrogenAirConcentrationSensor(Node node) {
        myBioHolder.theNitrogenAirConcentrationSensors
                .add(NitrogenAirConcentrationSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchNitrogenAirPressureSensor(Node node) {
        myBioHolder.theNitrogenAirPressureSensors
                .add(NitrogenAirPressureSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchNitrogenAirEnvironmentInFlowRateSensor(Node node) {
        myBioHolder.theNitrogenAirEnvironmentInFlowRateSensors
                .add(NitrogenAirEnvironmentInFlowRateSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchNitrogenAirEnvironmentOutFlowRateSensor(Node node) {
        myBioHolder.theNitrogenAirEnvironmentOutFlowRateSensors
                .add(NitrogenAirEnvironmentOutFlowRateSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchNitrogenAirStoreInFlowRateSensor(Node node) {
        myBioHolder.theNitrogenAirStoreInFlowRateSensors
                .add(NitrogenAirStoreInFlowRateSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchNitrogenAirStoreOutFlowRateSensor(Node node) {
        myBioHolder.theNitrogenAirStoreOutFlowRateSensors
                .add(NitrogenAirStoreOutFlowRateSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void crawlEnvironmentSensors(Node node) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("AirInFlowRateSensor"))
                fetchAirInFlowRateSensor(child);
            else if (childName.equals("AirOutFlowRateSensor"))
                fetchAirOutFlowRateSensor(child);
            else if (childName.equals("CO2AirConcentrationSensor"))
                fetchCO2AirConcentrationSensor(child);
            else if (childName.equals("CO2AirEnvironmentInFlowRateSensor"))
                fetchCO2AirEnvironmentInFlowRateSensor(child);
            else if (childName.equals("CO2AirEnvironmentOutFlowRateSensor"))
                fetchCO2AirEnvironmentOutFlowRateSensor(child);
            else if (childName.equals("CO2AirPressureSensor"))
                fetchCO2AirPressureSensor(child);
            else if (childName.equals("CO2AirStoreInFlowRateSensor"))
                fetchCO2AirStoreInFlowRateSensor(child);
            else if (childName.equals("CO2AirStoreOutFlowRateSensor"))
                fetchCO2AirStoreOutFlowRateSensor(child);
            else if (childName.equals("O2AirConcentrationSensor"))
                fetchO2AirConcentrationSensor(child);
            else if (childName.equals("O2AirEnvironmentInFlowRateSensor"))
                fetchO2AirEnvironmentInFlowRateSensor(child);
            else if (childName.equals("O2AirEnvironmentOutFlowRateSensor"))
                fetchO2AirEnvironmentOutFlowRateSensor(child);
            else if (childName.equals("O2AirPressureSensor"))
                fetchO2AirPressureSensor(child);
            else if (childName.equals("O2AirStoreInFlowRateSensor"))
                fetchO2AirStoreInFlowRateSensor(child);
            else if (childName.equals("O2AirStoreOutFlowRateSensor"))
                fetchO2AirStoreOutFlowRateSensor(child);
            else if (childName.equals("OtherAirConcentrationSensor"))
                fetchOtherAirConcentrationSensor(child);
            else if (childName.equals("OtherAirPressureSensor"))
                fetchOtherAirPressureSensor(child);
            else if (childName.equals("WaterAirConcentrationSensor"))
                fetchWaterAirConcentrationSensor(child);
            else if (childName.equals("WaterAirPressureSensor"))
                fetchWaterAirPressureSensor(child);
            else if (childName.equals("WaterAirStoreInFlowRateSensor"))
                fetchWaterAirStoreInFlowRateSensor(child);
            else if (childName.equals("WaterAirStoreOutFlowRateSensor"))
                fetchWaterAirStoreOutFlowRateSensor(child);
            else if (childName.equals("WaterAirEnvironmentInFlowRateSensor"))
                fetchWaterAirEnvironmentInFlowRateSensor(child);
            else if (childName.equals("WaterAirEnvironmentOutFlowRateSensor"))
                fetchWaterAirEnvironmentOutFlowRateSensor(child);
            else if (childName.equals("NitrogenAirConcentrationSensor"))
                fetchNitrogenAirConcentrationSensor(child);
            else if (childName.equals("NitrogenAirEnvironmentInFlowRateSensor"))
                fetchNitrogenAirEnvironmentInFlowRateSensor(child);
            else if (childName
                    .equals("NitrogenAirEnvironmentOutFlowRateSensor"))
                fetchNitrogenAirEnvironmentOutFlowRateSensor(child);
            else if (childName.equals("NitrogenAirPressureSensor"))
                fetchNitrogenAirPressureSensor(child);
            else if (childName.equals("NitrogenAirStoreInFlowRateSensor"))
                fetchNitrogenAirStoreInFlowRateSensor(child);
            else if (childName.equals("NitrogenAirStoreOutFlowRateSensor"))
                fetchNitrogenAirStoreOutFlowRateSensor(child);
            child = child.getNextSibling();
        }
    }

    //Food
    private static void fetchBiomassInFlowRateSensor(Node node) {
        myBioHolder.theBiomassInFlowRateSensors
                .add(BiomassInFlowRateSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchBiomassOutFlowRateSensor(Node node) {
        myBioHolder.theBiomassOutFlowRateSensors
                .add(BiomassOutFlowRateSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchBiomassStoreLevelSensor(Node node) {
        myBioHolder.theBiomassStoreLevelSensors
                .add(BiomassStoreLevelSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchBiomassStoreWaterContentSensor(Node node) {
        myBioHolder.theBiomassStoreWaterContentSensors
                .add(BiomassStoreWaterContentSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchFoodInFlowRateSensor(Node node) {
        myBioHolder.theFoodInFlowRateSensors.add(FoodInFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchFoodOutFlowRateSensor(Node node) {
        myBioHolder.theFoodOutFlowRateSensors.add(FoodOutFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchFoodStoreLevelSensor(Node node) {
        myBioHolder.theFoodStoreLevelSensors.add(FoodStoreLevelSensorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchHarvestSensor(Node node) {
        myBioHolder.theHarvestSensors.add(HarvestSensorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchPlantDeathSensor(Node node) {
        myBioHolder.thePlantDeathSensors.add(PlantDeathSensorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void crawlFoodSensors(Node node) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("BiomassInFlowRateSensor"))
                fetchBiomassInFlowRateSensor(child);
            if (childName.equals("BiomassOutFlowRateSensor"))
                fetchBiomassOutFlowRateSensor(child);
            else if (childName.equals("BiomassStoreLevelSensor"))
                fetchBiomassStoreLevelSensor(child);
            else if (childName.equals("BiomassStoreWaterContentSensor"))
                fetchBiomassStoreWaterContentSensor(child);
            else if (childName.equals("FoodInFlowRateSensor"))
                fetchFoodInFlowRateSensor(child);
            else if (childName.equals("FoodOutFlowRateSensor"))
                fetchFoodOutFlowRateSensor(child);
            else if (childName.equals("FoodStoreLevelSensor"))
                fetchFoodStoreLevelSensor(child);
            else if (childName.equals("HarvestSensor"))
                fetchHarvestSensor(child);
            else if (childName.equals("PlantDeathSensor"))
                fetchPlantDeathSensor(child);
            child = child.getNextSibling();
        }
    }

    //Framework
    private static void fetchStoreLevelSensor(Node node) {
        myBioHolder.theStoreLevelSensors.add(StoreLevelSensorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchStoreOverflowSensor(Node node) {
        myBioHolder.theStoreOverflowSensors.add(StoreOverflowSensorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void crawlFrameworkSensors(Node node) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("StoreLevelSensor"))
                fetchStoreLevelSensor(child);
            else if (childName.equals("StoreOverflowSensor"))
                fetchStoreOverflowSensor(child);
            child = child.getNextSibling();
        }
    }

    //Power
    private static void fetchPowerInFlowRateSensor(Node node) {
        myBioHolder.thePowerInFlowRateSensors.add(PowerInFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchPowerOutFlowRateSensor(Node node) {
        myBioHolder.thePowerOutFlowRateSensors.add(PowerOutFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchPowerStoreLevelSensor(Node node) {
        myBioHolder.thePowerStoreLevelSensors.add(PowerStoreLevelSensorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void crawlPowerSensors(Node node) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("PowerInFlowRateSensor"))
                fetchPowerInFlowRateSensor(child);
            else if (childName.equals("PowerOutFlowRateSensor"))
                fetchPowerOutFlowRateSensor(child);
            else if (childName.equals("PowerStoreLevelSensor"))
                fetchPowerStoreLevelSensor(child);
            child = child.getNextSibling();
        }
    }

    //Water
    private static void fetchPotableWaterInFlowRateSensor(Node node) {
        myBioHolder.thePotableWaterInFlowRateSensors
                .add(PotableWaterInFlowRateSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchPotableWaterOutFlowRateSensor(Node node) {
        myBioHolder.thePotableWaterOutFlowRateSensors
                .add(PotableWaterOutFlowRateSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchPotableWaterStoreLevelSensor(Node node) {
        myBioHolder.thePotableWaterStoreLevelSensors
                .add(PotableWaterStoreLevelSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchGreyWaterInFlowRateSensor(Node node) {
        myBioHolder.theGreyWaterInFlowRateSensors
                .add(GreyWaterInFlowRateSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchGreyWaterOutFlowRateSensor(Node node) {
        myBioHolder.theGreyWaterOutFlowRateSensors
                .add(GreyWaterOutFlowRateSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchGreyWaterStoreLevelSensor(Node node) {
        myBioHolder.theGreyWaterStoreLevelSensors
                .add(GreyWaterStoreLevelSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchDirtyWaterInFlowRateSensor(Node node) {
        myBioHolder.theDirtyWaterInFlowRateSensors
                .add(DirtyWaterInFlowRateSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchDirtyWaterOutFlowRateSensor(Node node) {
        myBioHolder.theDirtyWaterOutFlowRateSensors
                .add(DirtyWaterOutFlowRateSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchDirtyWaterStoreLevelSensor(Node node) {
        myBioHolder.theDirtyWaterStoreLevelSensors
                .add(DirtyWaterStoreLevelSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchWaterInFlowRateSensor(Node node) {
        myBioHolder.theWaterInFlowRateSensors.add(WaterInFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchWaterOutFlowRateSensor(Node node) {
        myBioHolder.theWaterOutFlowRateSensors.add(WaterOutFlowRateSensorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchWaterStoreLevelSensor(Node node) {
        myBioHolder.theWaterStoreLevelSensors.add(WaterStoreLevelSensorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void crawlWaterSensors(Node node) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("PotableWaterInFlowRateSensor"))
                fetchPotableWaterInFlowRateSensor(child);
            else if (childName.equals("PotableWaterOutFlowRateSensor"))
                fetchPotableWaterOutFlowRateSensor(child);
            else if (childName.equals("PotableWaterStoreLevelSensor"))
                fetchPotableWaterStoreLevelSensor(child);
            else if (childName.equals("GreyWaterInFlowRateSensor"))
                fetchGreyWaterInFlowRateSensor(child);
            else if (childName.equals("GreyWaterOutFlowRateSensor"))
                fetchGreyWaterOutFlowRateSensor(child);
            else if (childName.equals("GreyWaterStoreLevelSensor"))
                fetchGreyWaterStoreLevelSensor(child);
            else if (childName.equals("DirtyWaterInFlowRateSensor"))
                fetchDirtyWaterInFlowRateSensor(child);
            else if (childName.equals("DirtyWaterOutFlowRateSensor"))
                fetchDirtyWaterOutFlowRateSensor(child);
            else if (childName.equals("DirtyWaterStoreLevelSensor"))
                fetchDirtyWaterStoreLevelSensor(child);
            else if (childName.equals("WaterInFlowRateSensor"))
                fetchWaterInFlowRateSensor(child);
            else if (childName.equals("WaterOutFlowRateSensor"))
                fetchWaterOutFlowRateSensor(child);
            else if (childName.equals("WaterStoreLevelSensor"))
                fetchWaterStoreLevelSensor(child);
            child = child.getNextSibling();
        }
    }

    //Waste
    private static void fetchDryWasteInFlowRateSensor(Node node) {
        myBioHolder.theDryWasteInFlowRateSensors
                .add(DryWasteInFlowRateSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchDryWasteOutFlowRateSensor(Node node) {
        myBioHolder.theDryWasteOutFlowRateSensors
                .add(DryWasteOutFlowRateSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchDryWasteStoreLevelSensor(Node node) {
        myBioHolder.theDryWasteStoreLevelSensors
                .add(DryWasteStoreLevelSensorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void crawlWasteSensors(Node node) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("DryWasteInFlowRateSensor"))
                fetchDryWasteInFlowRateSensor(child);
            else if (childName.equals("DryWasteOutFlowRateSensor"))
                fetchDryWasteOutFlowRateSensor(child);
            else if (childName.equals("DryWasteStoreLevelSensor"))
                fetchDryWasteStoreLevelSensor(child);
            child = child.getNextSibling();
        }
    }

    //Actuators
    private static void crawlActuators(Node node) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("air")) {
                crawlAirActuators(child);
            } else if (childName.equals("environment")) {
                crawlEnvironmentActuators(child);
            } else if (childName.equals("food")) {
                crawlFoodActuators(child);
            } else if (childName.equals("power")) {
                crawlPowerActuators(child);
            } else if (childName.equals("water")) {
                crawlWaterActuators(child);
            } else if (childName.equals("waste")) {
                crawlWasteActuators(child);
            }
            child = child.getNextSibling();
        }
    }

    //Air
    private static void fetchCO2InFlowRateActuator(Node node) {
        myBioHolder.theCO2InFlowRateActuators.add(CO2InFlowRateActuatorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchCO2OutFlowRateActuator(Node node) {
        myBioHolder.theCO2OutFlowRateActuators.add(CO2OutFlowRateActuatorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchO2InFlowRateActuator(Node node) {
        myBioHolder.theO2InFlowRateActuators.add(O2InFlowRateActuatorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchO2OutFlowRateActuator(Node node) {
        myBioHolder.theO2OutFlowRateActuators.add(O2OutFlowRateActuatorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchH2InFlowRateActuator(Node node) {
        myBioHolder.theH2InFlowRateActuators.add(H2InFlowRateActuatorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchH2OutFlowRateActuator(Node node) {
        myBioHolder.theH2OutFlowRateActuators.add(H2OutFlowRateActuatorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchNitrogenInFlowRateActuator(Node node) {
        myBioHolder.theNitrogenInFlowRateActuators
                .add(NitrogenInFlowRateActuatorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchNitrogenOutFlowRateActuator(Node node) {
        myBioHolder.theNitrogenOutFlowRateActuators
                .add(NitrogenOutFlowRateActuatorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void crawlAirActuators(Node node) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("CO2InFlowRateActuator"))
                fetchCO2InFlowRateActuator(child);
            else if (childName.equals("CO2OutFlowRateActuator"))
                fetchCO2OutFlowRateActuator(child);
            else if (childName.equals("O2InFlowRateActuator"))
                fetchO2InFlowRateActuator(child);
            else if (childName.equals("O2OutFlowRateActuator"))
                fetchO2OutFlowRateActuator(child);
            else if (childName.equals("H2InFlowRateActuator"))
                fetchH2InFlowRateActuator(child);
            else if (childName.equals("H2OutFlowRateActuator"))
                fetchH2OutFlowRateActuator(child);
            else if (childName.equals("NitrogenInFlowRateActuator"))
                fetchNitrogenInFlowRateActuator(child);
            else if (childName.equals("NitrogenOutFlowRateActuator"))
                fetchNitrogenOutFlowRateActuator(child);
            child = child.getNextSibling();
        }
    }

    //Environment
    private static void fetchAirInFlowRateActuator(Node node) {
        myBioHolder.theAirInFlowRateActuators.add(AirInFlowRateActuatorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchAirOutFlowRateActuator(Node node) {
        myBioHolder.theAirOutFlowRateActuators.add(AirOutFlowRateActuatorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchCO2AirEnvironmentInFlowRateActuator(Node node) {
        myBioHolder.theCO2AirEnvironmentInFlowRateActuators
                .add(CO2AirEnvironmentInFlowRateActuatorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchCO2AirEnvironmentOutFlowRateActuator(Node node) {
        myBioHolder.theCO2AirEnvironmentOutFlowRateActuators
                .add(CO2AirEnvironmentOutFlowRateActuatorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchCO2AirStoreInFlowRateActuator(Node node) {
        myBioHolder.theCO2AirStoreInFlowRateActuators
                .add(CO2AirStoreInFlowRateActuatorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchCO2AirStoreOutFlowRateActuator(Node node) {
        myBioHolder.theCO2AirStoreOutFlowRateActuators
                .add(CO2AirStoreOutFlowRateActuatorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchO2AirEnvironmentInFlowRateActuator(Node node) {
        myBioHolder.theO2AirEnvironmentInFlowRateActuators
                .add(O2AirEnvironmentInFlowRateActuatorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchO2AirEnvironmentOutFlowRateActuator(Node node) {
        myBioHolder.theO2AirEnvironmentOutFlowRateActuators
                .add(O2AirEnvironmentOutFlowRateActuatorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchO2AirStoreInFlowRateActuator(Node node) {
        myBioHolder.theO2AirStoreInFlowRateActuators
                .add(O2AirStoreInFlowRateActuatorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchO2AirStoreOutFlowRateActuator(Node node) {
        myBioHolder.theO2AirStoreOutFlowRateActuators
                .add(O2AirStoreOutFlowRateActuatorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchWaterAirEnvironmentInFlowRateActuator(Node node) {
        myBioHolder.theWaterAirEnvironmentInFlowRateActuators
                .add(WaterAirEnvironmentInFlowRateActuatorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchWaterAirEnvironmentOutFlowRateActuator(Node node) {
        myBioHolder.theWaterAirEnvironmentOutFlowRateActuators
                .add(WaterAirEnvironmentOutFlowRateActuatorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchWaterAirStoreInFlowRateActuator(Node node) {
        myBioHolder.theWaterAirStoreInFlowRateActuators
                .add(WaterAirStoreInFlowRateActuatorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchWaterAirStoreOutFlowRateActuator(Node node) {
        myBioHolder.theWaterAirStoreOutFlowRateActuators
                .add(WaterAirStoreOutFlowRateActuatorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchNitrogenAirEnvironmentInFlowRateActuator(Node node) {
        myBioHolder.theNitrogenAirEnvironmentInFlowRateActuators
                .add(NitrogenAirEnvironmentInFlowRateActuatorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchNitrogenAirEnvironmentOutFlowRateActuator(Node node) {
        myBioHolder.theNitrogenAirEnvironmentOutFlowRateActuators
                .add(NitrogenAirEnvironmentOutFlowRateActuatorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchNitrogenAirStoreInFlowRateActuator(Node node) {
        myBioHolder.theNitrogenAirStoreInFlowRateActuators
                .add(NitrogenAirStoreInFlowRateActuatorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchNitrogenAirStoreOutFlowRateActuator(Node node) {
        myBioHolder.theNitrogenAirStoreOutFlowRateActuators
                .add(NitrogenAirStoreOutFlowRateActuatorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void crawlEnvironmentActuators(Node node) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("AirInFlowRateActuator"))
                fetchAirInFlowRateActuator(child);
            else if (childName.equals("AirOutFlowRateActuator"))
                fetchAirOutFlowRateActuator(child);
            else if (childName.equals("CO2AirEnvironmentInFlowRateActuator"))
                fetchCO2AirEnvironmentInFlowRateActuator(child);
            else if (childName.equals("CO2AirEnvironmentOutFlowRateActuator"))
                fetchCO2AirEnvironmentOutFlowRateActuator(child);
            else if (childName.equals("CO2AirStoreInFlowRateActuator"))
                fetchCO2AirStoreInFlowRateActuator(child);
            else if (childName.equals("CO2AirStoreOutFlowRateActuator"))
                fetchCO2AirStoreOutFlowRateActuator(child);
            else if (childName.equals("O2AirEnvironmentInFlowRateActuator"))
                fetchO2AirEnvironmentInFlowRateActuator(child);
            else if (childName.equals("O2AirEnvironmentOutFlowRateActuator"))
                fetchO2AirEnvironmentOutFlowRateActuator(child);
            else if (childName.equals("O2AirStoreInFlowRateActuator"))
                fetchO2AirStoreInFlowRateActuator(child);
            else if (childName.equals("O2AirStoreOutFlowRateActuator"))
                fetchO2AirStoreOutFlowRateActuator(child);
            else if (childName.equals("WaterAirStoreInFlowRateActuator"))
                fetchWaterAirStoreInFlowRateActuator(child);
            else if (childName.equals("WaterAirStoreOutFlowRateActuator"))
                fetchWaterAirStoreOutFlowRateActuator(child);
            else if (childName.equals("WaterAirEnvironmentInFlowRateActuator"))
                fetchWaterAirEnvironmentInFlowRateActuator(child);
            else if (childName.equals("WaterAirEnvironmentOutFlowRateActuator"))
                fetchWaterAirEnvironmentOutFlowRateActuator(child);
            else if (childName
                    .equals("NitrogenAirEnvironmentInFlowRateActuator"))
                fetchNitrogenAirEnvironmentInFlowRateActuator(child);
            else if (childName
                    .equals("NitrogenAirEnvironmentOutFlowRateActuator"))
                fetchNitrogenAirEnvironmentOutFlowRateActuator(child);
            else if (childName.equals("NitrogenAirStoreInFlowRateActuator"))
                fetchNitrogenAirStoreInFlowRateActuator(child);
            else if (childName.equals("NitrogenAirStoreOutFlowRateActuator"))
                fetchNitrogenAirStoreOutFlowRateActuator(child);
            child = child.getNextSibling();
        }
    }

    //Food
    private static void fetchBiomassInFlowRateActuator(Node node) {
        myBioHolder.theBiomassInFlowRateActuators
                .add(BiomassInFlowRateActuatorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchBiomassOutFlowRateActuator(Node node) {
        myBioHolder.theBiomassOutFlowRateActuators
                .add(BiomassOutFlowRateActuatorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchFoodInFlowRateActuator(Node node) {
        myBioHolder.theFoodInFlowRateActuators.add(FoodInFlowRateActuatorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchFoodOutFlowRateActuator(Node node) {
        myBioHolder.theFoodOutFlowRateActuators
                .add(FoodOutFlowRateActuatorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchPlantingActuator(Node node) {
        myBioHolder.thePlantingActuators.add(PlantingActuatorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchHarvestingActuator(Node node) {
        myBioHolder.theHarvestingActuators.add(HarvestingActuatorHelper
                .narrow(grabModule(getModuleName(node))));
    }

    private static void crawlFoodActuators(Node node) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("BiomassInFlowRateActuator"))
                fetchBiomassInFlowRateActuator(child);
            if (childName.equals("BiomassOutFlowRateActuator"))
                fetchBiomassOutFlowRateActuator(child);
            else if (childName.equals("FoodInFlowRateActuator"))
                fetchFoodInFlowRateActuator(child);
            else if (childName.equals("FoodOutFlowRateActuator"))
                fetchFoodOutFlowRateActuator(child);
            else if (childName.equals("PlantingActuator"))
                fetchPlantingActuator(child);
            else if (childName.equals("HarvestingActuator"))
                fetchHarvestingActuator(child);
            child = child.getNextSibling();
        }
    }

    //Power
    private static void fetchPowerInFlowRateActuator(Node node) {
        myBioHolder.thePowerInFlowRateActuators
                .add(PowerInFlowRateActuatorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchPowerOutFlowRateActuator(Node node) {
        myBioHolder.thePowerOutFlowRateActuators
                .add(PowerOutFlowRateActuatorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void crawlPowerActuators(Node node) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("PowerInFlowRateActuator"))
                fetchPowerInFlowRateActuator(child);
            else if (childName.equals("PowerOutFlowRateActuator"))
                fetchPowerOutFlowRateActuator(child);
            child = child.getNextSibling();
        }
    }

    //Water
    private static void fetchPotableWaterInFlowRateActuator(Node node) {
        myBioHolder.thePotableWaterInFlowRateActuators
                .add(PotableWaterInFlowRateActuatorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchPotableWaterOutFlowRateActuator(Node node) {
        myBioHolder.thePotableWaterOutFlowRateActuators
                .add(PotableWaterOutFlowRateActuatorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchGreyWaterInFlowRateActuator(Node node) {
        myBioHolder.theGreyWaterInFlowRateActuators
                .add(GreyWaterInFlowRateActuatorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchGreyWaterOutFlowRateActuator(Node node) {
        myBioHolder.theGreyWaterOutFlowRateActuators
                .add(GreyWaterOutFlowRateActuatorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchDirtyWaterInFlowRateActuator(Node node) {
        myBioHolder.theDirtyWaterInFlowRateActuators
                .add(DirtyWaterInFlowRateActuatorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchDirtyWaterOutFlowRateActuator(Node node) {
        myBioHolder.theDirtyWaterOutFlowRateActuators
                .add(DirtyWaterOutFlowRateActuatorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchWaterInFlowRateActuator(Node node) {
        myBioHolder.theWaterInFlowRateActuators
                .add(WaterInFlowRateActuatorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchWaterOutFlowRateActuator(Node node) {
        myBioHolder.theWaterOutFlowRateActuators
                .add(WaterOutFlowRateActuatorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void crawlWaterActuators(Node node) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("PotableWaterInFlowRateActuator"))
                fetchPotableWaterInFlowRateActuator(child);
            else if (childName.equals("PotableWaterOutFlowRateActuator"))
                fetchPotableWaterOutFlowRateActuator(child);
            else if (childName.equals("GreyWaterInFlowRateActuator"))
                fetchGreyWaterInFlowRateActuator(child);
            else if (childName.equals("GreyWaterOutFlowRateActuator"))
                fetchGreyWaterOutFlowRateActuator(child);
            else if (childName.equals("DirtyWaterInFlowRateActuator"))
                fetchDirtyWaterInFlowRateActuator(child);
            else if (childName.equals("DirtyWaterOutFlowRateActuator"))
                fetchDirtyWaterOutFlowRateActuator(child);
            else if (childName.equals("WaterInFlowRateActuator"))
                fetchWaterInFlowRateActuator(child);
            else if (childName.equals("WaterOutFlowRateActuator"))
                fetchWaterOutFlowRateActuator(child);
            child = child.getNextSibling();
        }
    }

    //Waste
    private static void fetchDryWasteInFlowRateActuator(Node node) {
        myBioHolder.theDryWasteInFlowRateActuators
                .add(DryWasteInFlowRateActuatorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void fetchDryWasteOutFlowRateActuator(Node node) {
        myBioHolder.theDryWasteOutFlowRateActuators
                .add(DryWasteOutFlowRateActuatorHelper
                        .narrow(grabModule(getModuleName(node))));
    }

    private static void crawlWasteActuators(Node node) {
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getNodeName();
            if (childName.equals("DryWasteInFlowRateActuator"))
                fetchDryWasteInFlowRateActuator(child);
            else if (childName.equals("DryWasteOutFlowRateActuator"))
                fetchDryWasteOutFlowRateActuator(child);
            child = child.getNextSibling();
        }
    }
}