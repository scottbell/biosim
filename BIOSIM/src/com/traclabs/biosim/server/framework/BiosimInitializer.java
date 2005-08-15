package com.traclabs.biosim.server.framework;

import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.traclabs.biosim.idl.framework.BioDriver;
import com.traclabs.biosim.idl.framework.BioDriverHelper;
import com.traclabs.biosim.idl.framework.BioModule;
import com.traclabs.biosim.idl.framework.BioModuleHelper;
import com.traclabs.biosim.idl.framework.MalfunctionIntensity;
import com.traclabs.biosim.idl.framework.MalfunctionLength;
import com.traclabs.biosim.idl.framework.StochasticIntensity;
import com.traclabs.biosim.idl.simulation.crew.CrewGroup;
import com.traclabs.biosim.idl.simulation.crew.CrewGroupHelper;
import com.traclabs.biosim.idl.simulation.food.BiomassRS;
import com.traclabs.biosim.idl.simulation.food.BiomassRSHelper;
import com.traclabs.biosim.server.actuator.framework.ActuatorInitializer;
import com.traclabs.biosim.server.sensor.framework.SensorInitializer;
import com.traclabs.biosim.server.simulation.framework.SimulationInitializer;
import com.traclabs.biosim.util.OrbUtils;

/**
 * Reads BioSim configuration from XML file.
 * 
 * @author Scott Bell
 */
public class BiosimInitializer {
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
    
    private static final String SCHEMA_LOCATION_LABEL = "http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation";
    private static final String SCHEMA_LOCATION_VALUE = "com/traclabs/biosim/server/framework/BiosimInitSchema.xsd";
    
    // default settings
    /** Default moduleNamespaces support (true). */
    private static final boolean DEFAULT_NAMESPACES = true;

    /** Default validation support (false). */
    private static final boolean DEFAULT_VALIDATION = true;

    /** Default Schema validation support (false). */
    private static final boolean DEFAULT_SCHEMA_VALIDATION = true;

    /** Default Schema full checking support (false). */
    private static final boolean DEFAULT_SCHEMA_FULL_CHECKING = true;

    private DOMParser myParser = null;

    private int myID = 0;

    private List<BioModule> myModules;

    private Logger myLogger;

    private SimulationInitializer mySimulationInitializer;

    private SensorInitializer mySensorInitializer;

    private ActuatorInitializer myActuatorInitializer;

    /** Default constructor. */
    public BiosimInitializer(int pID) {
        myID = pID;
        mySimulationInitializer = new SimulationInitializer(myID);
        mySensorInitializer = new SensorInitializer(myID);
        myActuatorInitializer = new ActuatorInitializer(myID);
        myModules = new Vector<BioModule>();
        myLogger = Logger.getLogger(this.getClass());

        try {
            myParser = new DOMParser();
            myParser.setFeature(SCHEMA_VALIDATION_FEATURE_ID,
                    DEFAULT_SCHEMA_VALIDATION);
            myParser.setFeature(SCHEMA_FULL_CHECKING_FEATURE_ID,
                    DEFAULT_SCHEMA_FULL_CHECKING);
            myParser.setFeature(VALIDATION_FEATURE_ID, DEFAULT_VALIDATION);
            myParser.setFeature(NAMESPACES_FEATURE_ID, DEFAULT_NAMESPACES);
            URL foundURL = BiosimInitializer.class.getClassLoader().getResource(SCHEMA_LOCATION_VALUE);
            if (foundURL != null){
            	String urlString = foundURL.toString();
            	if (urlString.length() > 0)
                    myParser.setProperty(SCHEMA_LOCATION_LABEL, urlString);
            }
        } catch (SAXException e) {
            myLogger.error("warning: Parser does not support feature ("
                    + NAMESPACES_FEATURE_ID + ")");
        }
    }

    /** Traverses the specified node, recursively. */
    private void crawlBiosim(Node node, boolean firstPass) {
        // is there anything to do?
        if (node == null)
            return;
        String nodeName = node.getNodeName();
        if (nodeName.equals("Globals")) {
            crawlGlobals(node, firstPass);
            return;
        } else if (nodeName.equals("SimBioModules")) {
            mySimulationInitializer.crawlSimModules(node, firstPass);
            return;
        } else if (nodeName.equals("Sensors")) {
            mySensorInitializer.crawlSensors(node, firstPass);
            return;
        } else if (nodeName.equals("Actuators")) {
            myActuatorInitializer.crawlActuators(node, firstPass);
            return;
        } else {
            Node child = node.getFirstChild();
            while (child != null) {
                crawlBiosim(child, firstPass);
                child = child.getNextSibling();
            }
        }

    }

    public void parseFile(String fileToParse) {
        try {
            myLogger.info("Initializing...");
            myParser.parse(fileToParse);
            Document document = myParser.getDocument();
            crawlBiosim(document, true);
            crawlBiosim(document, false);

            BioDriver myDriver = null;
            try {
                myDriver = BioDriverHelper.narrow(OrbUtils.getNamingContext(
                        myID).resolve_str("BioDriver"));
            } catch (Exception e) {
                myLogger.error(e.getMessage());
                e.printStackTrace();
            }
            //Fold Actuators, SimModules, and Sensors into modules
            myModules.addAll(mySensorInitializer.getSensors());
            myModules.addAll(mySimulationInitializer.getPassiveSimModules());
            myModules.addAll(mySimulationInitializer.getActiveSimModules());
            myModules.addAll(mySimulationInitializer.getPrioritySimModules());
            myModules.addAll(myActuatorInitializer.getActuators());

            //Give Modules, Sensors, Actuatos to BioDriver to tick
            BioModule[] moduleArray = convertList(myModules);
            BioModule[] sensorArray = convertList(mySensorInitializer
                    .getSensors());
            BioModule[] actuatorArray = convertList(myActuatorInitializer
                    .getActuators());
            BioModule[] passiveSimModulesArray = convertList(mySimulationInitializer
                    .getPassiveSimModules());
            BioModule[] activeSimModulesArray = convertList(mySimulationInitializer
                    .getActiveSimModules());
            BioModule[] prioritySimModulesArray = convertList(mySimulationInitializer
                    .getPrioritySimModules());
            myDriver.setModules(moduleArray);
            myDriver.setSensors(sensorArray);
            myDriver.setActuators(actuatorArray);
            myDriver.setActiveSimModules(activeSimModulesArray);
            myDriver.setPassiveSimModules(passiveSimModulesArray);
            myDriver.setPrioritySimModules(prioritySimModulesArray);

            myLogger.info("done");
        } catch (Exception e) {
            myLogger.error("error: Parse error occurred - " + e.getMessage());
            Exception se = e;
            if (e instanceof SAXException)
                se = ((SAXException) e).getException();
            if (se != null)
                se.printStackTrace();
            else
                e.printStackTrace();
        }
    }

    //Globals
    private void crawlGlobals(Node node, boolean firstPass) {
        BioDriver myDriver = null;
        try {
            myDriver = BioDriverHelper.narrow(OrbUtils.getNamingContext(myID)
                    .resolve_str("BioDriver"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (firstPass) {
            try {
                //set the tickLength
                float tickLength = Float.parseFloat(node.getAttributes()
                        .getNamedItem("tickLength").getNodeValue());
                myDriver.setTickLength(tickLength);
                
                myDriver.setRunTillN(Integer.parseInt(node.getAttributes()
                        .getNamedItem("runTillN").getNodeValue()));
                myDriver.setPauseSimulation(node.getAttributes().getNamedItem(
                        "startPaused").getNodeValue().equals("true"));
                myDriver.setRunTillCrewDeath(node.getAttributes().getNamedItem(
                        "runTillCrewDeath").getNodeValue().equals("true"));
                myDriver.setRunTillPlantDeath(node.getAttributes()
                        .getNamedItem("runTillPlantDeath").getNodeValue()
                        .equals("true"));
                int stutterLength = Integer.parseInt(node.getAttributes()
                        .getNamedItem("driverStutterLength").getNodeValue());
                if (stutterLength >= 0)
                    myDriver.setDriverStutterLength(stutterLength);
                myDriver.setLooping(node.getAttributes().getNamedItem(
                        "isLooping").getNodeValue().equals("true"));

                String stochasticString = node.getAttributes().getNamedItem(
                        "stochasticIntensity").getNodeValue();
                if (stochasticString.equals("HIGH_STOCH"))
                    myDriver
                            .setStochasticIntensity(StochasticIntensity.HIGH_STOCH);
                else if (stochasticString.equals("MEDIUM_STOCH"))
                    myDriver
                            .setStochasticIntensity(StochasticIntensity.MEDIUM_STOCH);
                else if (stochasticString.equals("LOW_STOCH"))
                    myDriver
                            .setStochasticIntensity(StochasticIntensity.LOW_STOCH);
                else
                    myDriver
                            .setStochasticIntensity(StochasticIntensity.NONE_STOCH);

                Properties logProperties = new Properties();
                Node child = node.getFirstChild();
                while (child != null) {
                    String childName = child.getNodeName();
                    if (childName.equals("log4jProperty")) {
                        String nameProperty = child.getAttributes()
                                .getNamedItem("name").getNodeValue();
                        String valueProperty = child.getAttributes()
                                .getNamedItem("value").getNodeValue();
                        logProperties.setProperty(nameProperty, valueProperty);
                    }
                    child = child.getNextSibling();
                }
                PropertyConfigurator.configure(logProperties);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } 
        //second pass
        else {
            //Give BioDriver crew to watch for (if we're doing run till dead)
            Node crewsToWatchNode = node.getAttributes().getNamedItem(
                    "crewsToWatch");
            if (crewsToWatchNode != null) {
                String crewsToWatchString = crewsToWatchNode.getNodeValue();
                String[] crewsToWatchArray = crewsToWatchString.split("\\s");
                CrewGroup[] crewGroups = new CrewGroup[crewsToWatchArray.length];
                for (int i = 0; i < crewGroups.length; i++) {
                    try {
                        crewGroups[i] = CrewGroupHelper.narrow(OrbUtils
                                .getNamingContext(myID).resolve_str(
                                        crewsToWatchArray[i]));
                        myLogger.debug("Fetched "
                                + crewGroups[i].getModuleName());
                    } catch (org.omg.CORBA.UserException e) {
                        e.printStackTrace();
                    }
                }
                myDriver.setCrewsToWatch(crewGroups);
            }

            //Give BioDriver plant to watch for (if we're doing run till dead)
            Node plantsToWatchNode = node.getAttributes().getNamedItem(
                    "plantsToWatch");
            if (plantsToWatchNode != null) {
                String plantsToWatchString = plantsToWatchNode.getNodeValue();
                String[] plantsToWatchArray = plantsToWatchString.split("\\s");
                BiomassRS[] biomassRSs = new BiomassRS[plantsToWatchArray.length];
                for (int i = 0; i < biomassRSs.length; i++) {
                    try {
                        biomassRSs[i] = BiomassRSHelper.narrow(OrbUtils
                                .getNamingContext(myID).resolve_str(
                                        plantsToWatchArray[i]));
                        myLogger.debug("Fetched "
                                + biomassRSs[i].getModuleName());
                    } catch (org.omg.CORBA.UserException e) {
                        e.printStackTrace();
                    }
                }
                myDriver.setPlantsToWatch(biomassRSs);
            }
        }
    }

    private static BioModule[] convertList(List pBioModules) {
        BioModule[] newArray = new BioModule[pBioModules.size()];
        int i = 0;
        for (Iterator iter = pBioModules.iterator(); iter.hasNext(); i++) {
            newArray[i] = BioModuleHelper.narrow((org.omg.CORBA.Object) (iter
                    .next()));
        }
        return newArray;
    }

    public static boolean isCreatedLocally(Node node) {
        return node.getAttributes().getNamedItem("createLocally")
                .getNodeValue().equals("true");
    }

    public static String getModuleName(Node node) {
        return node.getAttributes().getNamedItem("moduleName").getNodeValue();
    }

    public static org.omg.CORBA.Object grabModule(int pID, String moduleName) {
        org.omg.CORBA.Object moduleToReturn = null;
        while (moduleToReturn == null) {
            try {
                moduleToReturn = OrbUtils.getNamingContext(pID).resolve_str(
                        moduleName);
            } catch (org.omg.CORBA.UserException e) {
                Logger.getLogger(BiosimInitializer.class).error(
                        "Couldn't find module " + moduleName
                                + ", polling again...");
                OrbUtils.sleepAwhile();
            } catch (Exception e) {
                Logger.getLogger(BiosimInitializer.class).error(
                        "Had problems contacting nameserver with module "
                                + moduleName + ", polling again...");
                OrbUtils.resetInit();
                OrbUtils.sleepAwhile();
            }
        }
        return moduleToReturn;
    }

    public static void printRemoteWarningMessage(String pName) {
        Logger.getLogger(BiosimInitializer.class).warn(
                "\nInstance of the module named " + pName
                        + " should be created remotely (if not already done)");
    }

    private static boolean getEnableBreakDown(Node pNode) {
        return pNode.getAttributes().getNamedItem("isLoggingEnabled")
                .getNodeValue().equals("true");
    }

    private static StochasticIntensity getStochasticIntensity(Node pNode) {
        String intensityString = pNode.getAttributes().getNamedItem(
                "setStochasticIntensity").getNodeValue();
        if (intensityString.equals("HIGH_STOCH"))
            return StochasticIntensity.HIGH_STOCH;
        else if (intensityString.equals("MEDIUM_STOCH"))
            return StochasticIntensity.MEDIUM_STOCH;
        else if (intensityString.equals("LOW_STOCH"))
            return StochasticIntensity.LOW_STOCH;
        else
            return StochasticIntensity.NONE_STOCH;
    }

    private static MalfunctionLength getMalfunctionLength(Node pNode) {
        String lengthString = pNode.getAttributes().getNamedItem("length")
                .getNodeValue();
        if (lengthString.equals("TEMPORARY_MALF"))
            return MalfunctionLength.TEMPORARY_MALF;
		return MalfunctionLength.PERMANENT_MALF;
    }

    private static int getMalfunctionTick(Node pNode) {
        int occursAtTick = 0;
        try {
            occursAtTick = Integer.parseInt(pNode.getAttributes().getNamedItem(
                    "occursAtTick").getNodeValue());
        } catch (NumberFormatException e) {

            e.printStackTrace();
        }
        return occursAtTick;
    }

    private static MalfunctionIntensity getMalfunctionIntensity(Node pNode) {
        String intensityString = pNode.getAttributes()
                .getNamedItem("intensity").getNodeValue();
        if (intensityString.equals("SEVERE_MALF"))
            return MalfunctionIntensity.SEVERE_MALF;
        else if (intensityString.equals("MEDIUM_MALF"))
            return MalfunctionIntensity.MEDIUM_MALF;
        else
            return MalfunctionIntensity.LOW_MALF;
    }

    public static void setupBioModule(BioModuleImpl pModule, Node node) {
        pModule.setEnableBreakdown(getEnableBreakDown(node));
        pModule.setStochasticIntensity(getStochasticIntensity(node));
        Node child = node.getFirstChild();
        while (child != null) {
            if (child.getNodeName().equals("malfunction")) {
                pModule.scheduleMalfunction(getMalfunctionIntensity(child),
                        getMalfunctionLength(child), getMalfunctionTick(child));
            }
            child = child.getNextSibling();
        }
    }
}