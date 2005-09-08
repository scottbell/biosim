package com.traclabs.biosim.server.framework;

import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.traclabs.biosim.idl.framework.StochasticIntensity;
import com.traclabs.biosim.server.actuator.framework.ActuatorParser;
import com.traclabs.biosim.server.sensor.framework.SensorParser;
import com.traclabs.biosim.server.simulation.framework.SimulationParser;

/**
 * Reads BioSim configuration from XML file.
 * 
 * @author Scott Bell
 */
public abstract class BiosimParser {
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
    
	private int myID;
	
	private Logger myLogger;
    
    /** Default constructor. */
    public BiosimParser(int pID) {
        myID = pID;
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

    /** Traverses the specified node recursively. */
    private void crawlBiosim(Node node, boolean firstPass) {
        // is there anything to do?
        if (node == null)
            return;
        String nodeName = node.getNodeName();
        if (nodeName.equals("Globals")) {
            crawlGlobals(node, firstPass);
            return;
        } else if (nodeName.equals("SimBioModules")) {
            getSimulationParser().crawlSimModules(node, firstPass);
            return;
        } else if (nodeName.equals("Sensors")) {
            getSensorParser().crawlSensors(node, firstPass);
            return;
        } else if (nodeName.equals("Actuators")) {
            getActuatorParser().crawlActuators(node, firstPass);
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
            myParser.parse(fileToParse);
            Document document = myParser.getDocument();
            crawlBiosim(document, true);
            crawlBiosim(document, false);
        } catch (Exception e) {
            myLogger.error("error: Parse error occurred - " + e.getMessage());
            e.printStackTrace();
        }
    }

	//Globals
    private void crawlGlobals(Node node, boolean firstPass) {
        if (firstPass) {
            try {
            	handleTickLength(Float.parseFloat(node.getAttributes()
                        .getNamedItem("tickLength").getNodeValue()));
            	
            	handleRunTillN(Integer.parseInt(node.getAttributes()
                        .getNamedItem("runTillN").getNodeValue()));
            	
            	handlePauseSimulation(node.getAttributes().getNamedItem(
                "startPaused").getNodeValue().equals("true"));
            	
            	handleRunTillCrewDeath(node.getAttributes().getNamedItem(
                "runTillCrewDeath").getNodeValue().equals("true"));
            	
            	handleRunTillPlantDeath(node.getAttributes().getNamedItem("runTillPlantDeath").getNodeValue().equals("true"));
            	
            	handleDriverStutterLength(Integer.parseInt(node.getAttributes()
                        .getNamedItem("driverStutterLength").getNodeValue()));
            	
                handleIsLopping(node.getAttributes().getNamedItem(
                "isLooping").getNodeValue().equals("true"));
                
                handleStochasticIntensity(getStochasticIntensity(node));
                

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
                handleLogProperty(logProperties);
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
                handleCrewsToWatch(crewsToWatchArray);
            }

            //Give BioDriver plant to watch for (if we're doing run till dead)
            Node plantsToWatchNode = node.getAttributes().getNamedItem(
                    "plantsToWatch");
            if (plantsToWatchNode != null) {
                String plantsToWatchString = plantsToWatchNode.getNodeValue();
                String[] plantsToWatchArray = plantsToWatchString.split("\\s");
                handleCrewsToWatch(plantsToWatchArray);
            }
        }
    }

	public int getID(){
    	return myID;
    }

    protected void handleStochasticIntensity(StochasticIntensity stochasticIntensity) {
	}

    protected void handleRunTillPlantDeath(boolean b) {
	}

    protected void handleRunTillCrewDeath(boolean b) {
	}

    protected void handleCrewsToWatch(String[] crewsToWatchArray) {
	}

    protected void handleLogProperty(Properties logProperties) {
	}

    protected void handleIsLopping(boolean b) {
	}

    protected void handlePauseSimulation(boolean b) {
	}

    protected void handleRunTillN(int i) {
	}

    protected void handleTickLength(float f) {
	}
    
    protected void handleDriverStutterLength(int i) {
	}

    protected abstract SimulationParser getSimulationParser();
    
    protected abstract SensorParser getSensorParser();
    
    protected abstract ActuatorParser getActuatorParser();

    private static StochasticIntensity getStochasticIntensity(Node pNode) {
        String intensityString = pNode.getAttributes().getNamedItem(
                "stochasticIntensity").getNodeValue();
        if (intensityString.equals("HIGH_STOCH"))
            return StochasticIntensity.HIGH_STOCH;
        else if (intensityString.equals("MEDIUM_STOCH"))
            return StochasticIntensity.MEDIUM_STOCH;
        else if (intensityString.equals("LOW_STOCH"))
            return StochasticIntensity.LOW_STOCH;
        else
            return StochasticIntensity.NONE_STOCH;
    }
    
    public static String getModuleName(Node node) {
        return node.getAttributes().getNamedItem("moduleName").getNodeValue();
    }

    public static boolean isCreatedLocally(Node node) {
        return node.getAttributes().getNamedItem("createLocally")
                .getNodeValue().equals("true");
    }
}