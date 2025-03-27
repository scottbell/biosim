package com.traclabs.biosim.server.framework;

import com.traclabs.biosim.server.actuator.framework.ActuatorInitializer;
import com.traclabs.biosim.server.sensor.framework.SensorInitializer;
import com.traclabs.biosim.server.simulation.crew.CrewGroup;
import com.traclabs.biosim.server.simulation.food.BiomassPS;
import com.traclabs.biosim.server.simulation.framework.SimulationInitializer;
import com.traclabs.biosim.server.util.failure.*;
import com.traclabs.biosim.server.util.stochastic.NormalFilter;
import com.traclabs.biosim.server.util.stochastic.StochasticFilter;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.net.URL;
import java.util.Set;
import java.util.Properties;
import java.util.HashSet;

/**
 * Reads BioSim configuration from XML file.
 * 
 * @author Scott Bell
 */
public class BiosimInitializer {
	private static final String SCHEMA_LOCATION_VALUE = "com/traclabs/biosim/server/framework/schema/BiosimInitSchema.xsd";

	private static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";

	private static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";

	private static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

	// default settings
	/** Default moduleNamespaces support (true). */
	private static final boolean DEFAULT_NAMESPACES = true;

	/** Default validation support (false). */
	private static final boolean DEFAULT_VALIDATION = true;

	/** Default Schema validation support (false). */
	private static final boolean DEFAULT_SCHEMA_VALIDATION = true;

	/** Default Schema full checking support (false). */
	private static final boolean DEFAULT_SCHEMA_FULL_CHECKING = true;

	private DocumentBuilder myDocumentBuilder = null;

	private int myID = 0;

	private Set<BioModule> myModules;

	private Logger myLogger;

	private SimulationInitializer mySimulationInitializer;

	private SensorInitializer mySensorInitializer;

	private ActuatorInitializer myActuatorInitializer;
	
	private BioDriver myBioDriver;
	
	private static BiosimInitializer instance = null;

	public static synchronized BiosimInitializer getInstance(int pID) {
        if (instance == null) {
            instance = new BiosimInitializer(pID);
        }
        return instance;
    }

	/**
     * Returns the BioDriver associated with this initializer.
     */
    public BioDriver getBioDriver() {
        return myBioDriver;
    }

	/** Default constructor. */
	private BiosimInitializer(int pID) {
		myID = pID;
		mySimulationInitializer = new SimulationInitializer(myID);
		mySensorInitializer = new SensorInitializer(myID);
		myActuatorInitializer = new ActuatorInitializer(myID);
		myModules = new HashSet<BioModule>();
		myLogger = LoggerFactory.getLogger(this.getClass());
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();
		documentBuilderFactory.setNamespaceAware(true);
		documentBuilderFactory.setValidating(true);
		try {
			documentBuilderFactory.setAttribute(JAXP_SCHEMA_LANGUAGE,
					W3C_XML_SCHEMA);
			URL foundURL = BiosimInitializer.class.getClassLoader()
					.getResource(SCHEMA_LOCATION_VALUE);
			if (foundURL != null) {
				String urlString = foundURL.toString();
				if (urlString.length() > 0)
					documentBuilderFactory.setAttribute(JAXP_SCHEMA_SOURCE,
							urlString);
			}
			myDocumentBuilder = documentBuilderFactory.newDocumentBuilder();
			myDocumentBuilder.setErrorHandler(new DefaultHandler());
		} catch (IllegalArgumentException e) {
			// This can happen if the parser does not support JAXP 1.2
			myLogger
					.warn("Had trouble configuring parser for schema validation: "
							+ e);
		} catch (ParserConfigurationException e) {
			myLogger.warn("Had trouble configuring parser: " + e);
			e.printStackTrace();
		}
	}

	/** Traverses the specified node, recursively. */
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

	public void parseFile(String fileToParse) {
		try {
			myLogger.info("Initializing...");
			Document document = myDocumentBuilder.parse(fileToParse);
			crawlBiosim(document, true);
			crawlBiosim(document, false);

			BioDriver myDriver = null;
			myDriver = new BioDriver(myID);
			// Fold Actuators, SimModules, and Sensors into modules
			myModules.addAll(mySensorInitializer.getSensors());
			myModules.addAll(mySimulationInitializer.getPassiveSimModules());
			myModules.addAll(mySimulationInitializer.getActiveSimModules());
			myModules.addAll(mySimulationInitializer.getPrioritySimModules());
			myModules.addAll(myActuatorInitializer.getActuators());

			// Give Modules, Sensors, Actuatos to BioDriver to tick
			BioModule[] moduleArray = convertSet(myModules);
			BioModule[] sensorArray = convertSet(mySensorInitializer
					.getSensors());
			BioModule[] actuatorArray = convertSet(myActuatorInitializer
					.getActuators());
			BioModule[] passiveSimModulesArray = convertSet(mySimulationInitializer
					.getPassiveSimModules());
			BioModule[] activeSimModulesArray = convertSet(mySimulationInitializer
					.getActiveSimModules());
			BioModule[] prioritySimModulesArray = convertSet(mySimulationInitializer
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

				Properties logProperties = new Properties();
				Node child = node.getFirstChild();
				while (child != null) {
					String childName = child.getLocalName();
					if (childName != null) {
						if (childName.equals("log4jProperty")) {
							String nameProperty = child.getAttributes()
									.getNamedItem("name").getNodeValue();
							String valueProperty = child.getAttributes()
									.getNamedItem("value").getNodeValue();
							logProperties.setProperty(nameProperty,
									valueProperty);
						}
					}
					child = child.getNextSibling();
				}
			} catch (Exception e) {
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
					crewGroups[i] = (CrewGroup) getBioDriver().getModule(crewsToWatchArray[i]);
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
					biomassPSs[i] = (BiomassPS) getBioDriver().getModule(plantsToWatchArray[i]);
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

	public static void setupBioModule(BioModule pModule, Node node) {
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
}
