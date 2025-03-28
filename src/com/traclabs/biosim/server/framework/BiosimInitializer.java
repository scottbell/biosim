package com.traclabs.biosim.server.framework;

import ch.qos.logback.classic.Level;
import com.traclabs.biosim.server.actuator.framework.ActuatorInitializer;
import com.traclabs.biosim.server.sensor.framework.SensorInitializer;
import com.traclabs.biosim.server.simulation.crew.CrewGroup;
import com.traclabs.biosim.server.simulation.food.BiomassPS;
import com.traclabs.biosim.server.simulation.framework.SimulationInitializer;
import com.traclabs.biosim.server.util.failure.*;
import com.traclabs.biosim.server.util.stochastic.NormalFilter;
import com.traclabs.biosim.server.util.stochastic.StochasticFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Reads BioSim configuration from XML file.
 *
 * @author Scott Bell
 */
public class BiosimInitializer {
    private static final String SCHEMA_LOCATION_VALUE = "schema/BiosimInitSchema.xsd";
    private static final Map<Integer, BiosimInitializer> instances = new HashMap<Integer, BiosimInitializer>();
    private final Map<String, IBioModule> myModules;
    private final Logger myLogger;
    private final SimulationInitializer mySimulationInitializer;
    private final SensorInitializer mySensorInitializer;
    private final ActuatorInitializer myActuatorInitializer;
    private DocumentBuilder myDocumentBuilder = null;
    private int myID = 0;
    private BioDriver myBioDriver;

    private BiosimInitializer(int pID) {
        myID = pID;
        mySimulationInitializer = new SimulationInitializer(myID);
        mySensorInitializer = new SensorInitializer(myID);
        myActuatorInitializer = new ActuatorInitializer(myID);
        myModules = new HashMap<String, IBioModule>();
        myLogger = LoggerFactory.getLogger(this.getClass());

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            factory.setValidating(true);
            factory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);

            SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            // Load the schema from the classpath
            URL schemaURL = getClass().getClassLoader().getResource(SCHEMA_LOCATION_VALUE);
            if (schemaURL == null) {
                throw new FileNotFoundException("Schema file " + SCHEMA_LOCATION_VALUE + " not found in classpath.");
            }
            Schema schema = sf.newSchema(schemaURL);
            factory.setSchema(schema);

            myDocumentBuilder = factory.newDocumentBuilder();
            myDocumentBuilder.setErrorHandler(new DefaultHandler());
        } catch (ParserConfigurationException | SAXException e) {
            myLogger.error("Error configuring parser: {}", e.getMessage());
            throw new RuntimeException("Failed to configure XML parser", e);
        } catch (Exception e) {
            myLogger.error("Error initializing document builder: {}", e.getMessage());
            throw new RuntimeException("Failed to initialize document builder", e);
        }
    }

    public static synchronized IBioModule getModule(int id, String name) {
        return getInstance(id).getModule(name);
    }

	public synchronized void addModule(IBioModule module) {
		myModules.put(module.getModuleName(), module);
	}

    public static synchronized void addModule(int id, IBioModule module) {
		BiosimInitializer.getInstance(id).addModule(module);
    }

    public static synchronized void testConfiguration() {
        new BiosimInitializer(-1);
    }


    public static synchronized BiosimInitializer getInstance(int pID) {
        BiosimInitializer existingInstance = instances.get(pID);
        if (existingInstance == null) {
            existingInstance = new BiosimInitializer(pID);
            instances.put(pID, existingInstance);
        }
        return existingInstance;
    }

    public static synchronized void deleteInstance(int pID) {
        instances.remove(pID);
    }

    public IBioModule getModule(String name) {
        return myModules.get(name);
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

    public static Level getLogLevel(Node pNode) {
        String logString = pNode.getAttributes().getNamedItem("logLevel")
                .getNodeValue();
        if (logString.equals("OFF"))
            return Level.OFF;
        else if (logString.equals("INFO"))
            return Level.INFO;
        else if (logString.equals("DEBUG"))
            return Level.DEBUG;
        else if (logString.equals("ERROR"))
            return Level.ERROR;
        else if (logString.equals("WARN"))
            return Level.WARN;
        else if (logString.equals("FATAL"))
            return Level.ERROR;
        else if (logString.equals("ALL"))
            return Level.TRACE;
        else
            return null;
    }

    public static String getModuleName(Node node) {
        return node.getAttributes().getNamedItem("moduleName").getNodeValue();
    }

    public static void setupBioModule(int id, BioModule pModule, Node node) {
        BiosimInitializer.addModule(id, pModule);
        Level logLevel = getLogLevel(node);
        if (logLevel != null)
            pModule.setLogLevel(logLevel);
        Node child = node.getFirstChild();
        while (child != null) {
            String childName = child.getLocalName();
            if (childName != null) {
                StochasticFilter filter = getStochasticFilter(childName, child);
                if (filter != null) {
                    pModule.setStochasticFilter(filter);
                    LoggerFactory.getLogger(BiosimInitializer.class).debug(
                            "created stochastic filter for"
                                    + pModule.getModuleName());
                } else if (checkForFailureDecider(childName, pModule, child)) {
                    LoggerFactory.getLogger(BiosimInitializer.class).debug(
                            "created failure decider for"
                                    + pModule.getModuleName());
                } else if (childName.equals("malfunction")) {
                    pModule.scheduleMalfunction(getMalfunctionIntensity(child),
                            getMalfunctionLength(child),
                            getMalfunctionTick(child));
                }
            }
            child = child.getNextSibling();
        }
    }

    private static StochasticFilter getStochasticFilter(String childName,
                                                        Node child) {
        if (childName.equals("normalStochasticFilter")) {
            double deviation = getDoubleFromAttribute(child, "deviation");
            NormalFilter filter = new NormalFilter(deviation);
            filter.setEnabled(getFilterEnabled(child));
            return filter;
        } else
            return null;
    }

    private static boolean getFilterEnabled(Node pNode) {
        return pNode.getAttributes().getNamedItem("isFilterEnabled")
                .getNodeValue().equals("true");
    }

    private static boolean checkForFailureDecider(String childName,
                                                  BioModule pModule, Node child) {
        if (childName.equals("cauchyFailureDecider")) {
            double mu = getDoubleFromAttribute(child, "mu");
            double sd = getDoubleFromAttribute(child, "sd");
            CauchyDecider decider = new CauchyDecider(mu, sd);
            pModule.setFailureDecider(decider);
            pModule.setEnableFailure(getFailureEnabled(child));
        } else if (childName.equals("expFailureDecider")) {
            double lambda = getDoubleFromAttribute(child, "lambda");
            ExpDecider decider = new ExpDecider(lambda);
            pModule.setFailureDecider(decider);
            pModule.setEnableFailure(getFailureEnabled(child));
        } else if (childName.equals("logisticFailureDecider")) {
            double mu = getDoubleFromAttribute(child, "mu");
            double sd = getDoubleFromAttribute(child, "sd");
            LogisticDecider decider = new LogisticDecider(mu, sd);
            pModule.setFailureDecider(decider);
            pModule.setEnableFailure(getFailureEnabled(child));
        } else if (childName.equals("lognormalFailureDecider")) {
            double logmean = getDoubleFromAttribute(child, "logmean");
            double logsd = getDoubleFromAttribute(child, "logsd");
            LognormalDecider decider = new LognormalDecider(logmean, logsd);
            pModule.setFailureDecider(decider);
            pModule.setEnableFailure(getFailureEnabled(child));
        } else if (childName.equals("normalFailureDecider")) {
            double logmean = getDoubleFromAttribute(child, "logmean");
            double logsd = getDoubleFromAttribute(child, "logsd");
            NormalDecider decider = new NormalDecider(logmean, logsd);
            pModule.setFailureDecider(decider);
            pModule.setEnableFailure(getFailureEnabled(child));
        } else if (childName.equals("uniformFailureDecider")) {
            double alpha = getDoubleFromAttribute(child, "alpha");
            double beta = getDoubleFromAttribute(child, "beta");
            UniformDecider decider = new UniformDecider(alpha, beta);
            pModule.setFailureDecider(decider);
            pModule.setEnableFailure(getFailureEnabled(child));
        } else if (childName.equals("weibull2FailureDecider")) {
            double lambda = getDoubleFromAttribute(child, "lambda");
            double beta = getDoubleFromAttribute(child, "beta");
            Weibull2Decider decider = new Weibull2Decider(lambda, beta);
            pModule.setFailureDecider(decider);
            pModule.setEnableFailure(getFailureEnabled(child));
        } else if (childName.equals("weibull3FailureDecider")) {
            double lambda = getDoubleFromAttribute(child, "lambda");
            double beta = getDoubleFromAttribute(child, "beta");
            double hold = getDoubleFromAttribute(child, "hold");
            Weibull3Decider decider = new Weibull3Decider(lambda, beta, hold);
            pModule.setFailureDecider(decider);
            pModule.setEnableFailure(getFailureEnabled(child));
        } else
            return false;
        return true;
    }

    private static double getDoubleFromAttribute(Node child, String name) {
        double value = 0;
        try {
            value = Double.parseDouble(child.getAttributes().getNamedItem(name)
                    .getNodeValue());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return value;
    }

    private static boolean getFailureEnabled(Node pNode) {
        return pNode.getAttributes().getNamedItem("isFailureEnabled")
                .getNodeValue().equals("true");
    }

    /**
     * Returns the BioDriver associated with this initializer.
     */
    public BioDriver getBioDriver() {
        return myBioDriver;
    }

    /**
     * Traverses the specified node, recursively.
     */
    private void crawlBiosim(Node node, boolean firstPass) {
        // is there anything to do?
        if (node == null)
            return;
        String nodeName = node.getLocalName();
        if (nodeName != null) {
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
            }
        }
        Node child = node.getFirstChild();
        while (child != null) {
            crawlBiosim(child, firstPass);
            child = child.getNextSibling();
        }

    }

    // Added helper method to convert a Set of BioModules to an array using native functions.
    private BioModule[] convertSet(Set<? extends BioModule> set) {
        return set.toArray(new BioModule[0]);
    }

    /**
     * Parses the XML configuration from a string.
     *
     * @param xmlConfig The XML configuration as a string
     */
    public void parseXmlConfiguration(String xmlConfig) {
        try {
            myLogger.info("Initializing...");
            InputSource is = new InputSource(new StringReader(xmlConfig));
            Document document = myDocumentBuilder.parse(is);

            // First and second pass to process the XML document
            crawlBiosim(document, true);
            crawlBiosim(document, false);

            // Configure the BioDriver with modules
            IBioModule[] moduleArray = myModules.values().toArray(new IBioModule[0]);
            IBioModule[] sensorArray = convertSet(mySensorInitializer.getSensors());
            IBioModule[] actuatorArray = convertSet(myActuatorInitializer.getActuators());
            IBioModule[] passiveSimModulesArray = convertSet(mySimulationInitializer.getPassiveSimModules());
            IBioModule[] activeSimModulesArray = convertSet(mySimulationInitializer.getActiveSimModules());
            IBioModule[] prioritySimModulesArray = convertSet(mySimulationInitializer.getPrioritySimModules());

            myBioDriver.setModules(moduleArray);
            myBioDriver.setSensors(sensorArray);
            myBioDriver.setActuators(actuatorArray);
            myBioDriver.setActiveSimModules(activeSimModulesArray);
            myBioDriver.setPassiveSimModules(passiveSimModulesArray);
            myBioDriver.setPrioritySimModules(prioritySimModulesArray);

            myLogger.info("Initialization complete.");
        } catch (SAXException e) {
            myLogger.error("Parse error occurred: {}", e.getMessage());
            throw new RuntimeException(e);
        } catch (Exception e) {
            myLogger.error("🛑 Error during initialization: {}", e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    // Globals
    private void crawlGlobals(Node node, boolean firstPass) {
        if (firstPass) {
            try {
                myBioDriver = new BioDriver(myID);
                // set the tickLength
                float tickLength = Float.parseFloat(node.getAttributes()
                        .getNamedItem("tickLength").getNodeValue());
                myBioDriver.setTickLength(tickLength);

                myBioDriver.setRunTillN(Integer.parseInt(node.getAttributes()
                        .getNamedItem("runTillN").getNodeValue()));
                myBioDriver.setPauseSimulation(node.getAttributes().getNamedItem(
                        "startPaused").getNodeValue().equals("true"));
                myBioDriver.setRunTillCrewDeath(node.getAttributes().getNamedItem(
                        "runTillCrewDeath").getNodeValue().equals("true"));
                myBioDriver.setExitWhenFinished(node.getAttributes().getNamedItem(
                        "exitWhenFinished").getNodeValue().equals("true"));
                myBioDriver.setRunTillPlantDeath(node.getAttributes()
                        .getNamedItem("runTillPlantDeath").getNodeValue()
                        .equals("true"));
                int stutterLength = Integer.parseInt(node.getAttributes()
                        .getNamedItem("driverStutterLength").getNodeValue());
                if (stutterLength >= 0)
                    myBioDriver.setDriverStutterLength(stutterLength);
                myBioDriver.setLooping(node.getAttributes().getNamedItem(
                        "isLooping").getNodeValue().equals("true"));
            } catch (Exception e) {
                myLogger.error("🛑 Could not crawl globals in configuration! " + e.getMessage());
                e.printStackTrace();
            }
        }
        // second pass
        else {
            // Give BioDriver crew to watch for (if we're doing run till dead)
            Node crewsToWatchNode = node.getAttributes().getNamedItem(
                    "crewsToWatch");
            if (crewsToWatchNode != null) {
                String crewsToWatchString = crewsToWatchNode.getNodeValue();
                String[] crewsToWatchArray = crewsToWatchString.split("\\s");
                CrewGroup[] crewGroups = new CrewGroup[crewsToWatchArray.length];
                for (int i = 0; i < crewGroups.length; i++) {
                    crewGroups[i] = (CrewGroup) getModule(crewsToWatchArray[i]);
                }
                myBioDriver.setCrewsToWatch(crewGroups);
            }

            // Give BioDriver plant to watch for (if we're doing run till dead)
            Node plantsToWatchNode = node.getAttributes().getNamedItem(
                    "plantsToWatch");
            if (plantsToWatchNode != null) {
                String plantsToWatchString = plantsToWatchNode.getNodeValue();
                String[] plantsToWatchArray = plantsToWatchString.split("\\s");
                BiomassPS[] biomassPSs = new BiomassPS[plantsToWatchArray.length];
                for (int i = 0; i < biomassPSs.length; i++) {
                    biomassPSs[i] = (BiomassPS) getModule(plantsToWatchArray[i]);
                }
                myBioDriver.setPlantsToWatch(biomassPSs);
            }

            Node child = node.getFirstChild();
            while (child != null) {
                String childName = child.getLocalName();
                if (childName != null) {
                    StochasticFilter filter = getStochasticFilter(childName,
                            child);
                    if (filter != null) {
                        // myDriver.setStochasticFilter(filter);
                        // Logger.getLogger(BiosimInitializer.class).debug("created
                        // stochastic filter for all modules");
                    }
                }
                child = child.getNextSibling();
            }
        }
    }
}
