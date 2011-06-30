package com.traclabs.biosim.client.util;

import java.io.File;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.traclabs.biosim.idl.actuator.air.CO2InFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.air.CO2OutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.air.H2InFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.air.H2OutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.air.MethaneInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.air.MethaneOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.air.NitrogenInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.air.NitrogenOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.air.O2InFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.air.O2OutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.AirInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.environment.AirOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.food.BiomassInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.food.BiomassOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.food.FoodInFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.food.FoodOutFlowRateActuatorHelper;
import com.traclabs.biosim.idl.actuator.food.HarvestingActuatorHelper;
import com.traclabs.biosim.idl.actuator.food.PlantingActuatorHelper;
import com.traclabs.biosim.idl.actuator.framework.EffluentValveActuatorHelper;
import com.traclabs.biosim.idl.actuator.framework.InfluentValveActuatorHelper;
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
import com.traclabs.biosim.idl.sensor.air.H2InFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.H2OutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.MethaneInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.MethaneOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.NitrogenInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.NitrogenOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.O2InFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.air.O2OutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupAnyDeadSensorHelper;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupDeathSensorHelper;
import com.traclabs.biosim.idl.sensor.crew.CrewGroupProductivitySensorHelper;
import com.traclabs.biosim.idl.sensor.environment.AirInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.AirOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.GasConcentrationSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.GasPressureSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.TotalMolesSensorHelper;
import com.traclabs.biosim.idl.sensor.environment.TotalPressureSensorHelper;
import com.traclabs.biosim.idl.sensor.food.BiomassInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.food.BiomassOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.food.BiomassStoreWaterContentSensorHelper;
import com.traclabs.biosim.idl.sensor.food.FoodInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.food.FoodOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.food.HarvestSensorHelper;
import com.traclabs.biosim.idl.sensor.food.PlantDeathSensorHelper;
import com.traclabs.biosim.idl.sensor.food.TimeTillCanopyClosureSensorHelper;
import com.traclabs.biosim.idl.sensor.framework.EffluentValveStateSensorHelper;
import com.traclabs.biosim.idl.sensor.framework.InfluentValveStateSensorHelper;
import com.traclabs.biosim.idl.sensor.framework.StoreLevelSensorHelper;
import com.traclabs.biosim.idl.sensor.framework.StoreOverflowSensorHelper;
import com.traclabs.biosim.idl.sensor.framework.TimeSensorHelper;
import com.traclabs.biosim.idl.sensor.power.PowerInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.power.PowerOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.waste.DryWasteInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.waste.DryWasteOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.DirtyWaterInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.DirtyWaterOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.GreyWaterInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.GreyWaterOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.PotableWaterInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.PotableWaterOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.WaterInFlowRateSensorHelper;
import com.traclabs.biosim.idl.sensor.water.WaterOutFlowRateSensorHelper;
import com.traclabs.biosim.idl.simulation.air.AirRSHelper;
import com.traclabs.biosim.idl.simulation.air.CO2StoreHelper;
import com.traclabs.biosim.idl.simulation.air.CRSHelper;
import com.traclabs.biosim.idl.simulation.air.H2StoreHelper;
import com.traclabs.biosim.idl.simulation.air.MethaneStoreHelper;
import com.traclabs.biosim.idl.simulation.air.NitrogenStoreHelper;
import com.traclabs.biosim.idl.simulation.air.O2StoreHelper;
import com.traclabs.biosim.idl.simulation.air.OGSHelper;
import com.traclabs.biosim.idl.simulation.air.PyrolizerHelper;
import com.traclabs.biosim.idl.simulation.air.VCCRHelper;
import com.traclabs.biosim.idl.simulation.air.cdrs.CDRSModuleHelper;
import com.traclabs.biosim.idl.simulation.crew.CrewGroupHelper;
import com.traclabs.biosim.idl.simulation.environment.DehumidifierHelper;
import com.traclabs.biosim.idl.simulation.environment.FanHelper;
import com.traclabs.biosim.idl.simulation.environment.SimEnvironmentHelper;
import com.traclabs.biosim.idl.simulation.food.BiomassPSHelper;
import com.traclabs.biosim.idl.simulation.food.BiomassStoreHelper;
import com.traclabs.biosim.idl.simulation.food.FoodProcessorHelper;
import com.traclabs.biosim.idl.simulation.food.FoodStoreHelper;
import com.traclabs.biosim.idl.simulation.framework.AccumulatorHelper;
import com.traclabs.biosim.idl.simulation.framework.EffluentValveHelper;
import com.traclabs.biosim.idl.simulation.framework.InfluentValveHelper;
import com.traclabs.biosim.idl.simulation.framework.InjectorHelper;
import com.traclabs.biosim.idl.simulation.power.GenericPowerConsumerHelper;
import com.traclabs.biosim.idl.simulation.power.PowerPSHelper;
import com.traclabs.biosim.idl.simulation.power.PowerStoreHelper;
import com.traclabs.biosim.idl.simulation.power.RPCMHelper;
import com.traclabs.biosim.idl.simulation.waste.DryWasteStoreHelper;
import com.traclabs.biosim.idl.simulation.waste.IncineratorHelper;
import com.traclabs.biosim.idl.simulation.water.DirtyWaterStoreHelper;
import com.traclabs.biosim.idl.simulation.water.GreyWaterStoreHelper;
import com.traclabs.biosim.idl.simulation.water.PotableWaterStoreHelper;
import com.traclabs.biosim.idl.simulation.water.WaterRSHelper;
import com.traclabs.biosim.util.OrbUtils;

/**
 * Reads BioSim configuration from XML file.
 * 
 * @author Scott Bell
 */
public class BioHolderInitializer {

	private static final String SCHEMA_LOCATION_VALUE = "com/traclabs/biosim/server/framework/schema/BiosimInitSchema.xsd";

	private static final String W3C_XML_SCHEMA = "http://www.w3.org/2001/XMLSchema";

	private static final String JAXP_SCHEMA_LANGUAGE = "http://java.sun.com/xml/jaxp/properties/schemaLanguage";

	private static final String JAXP_SCHEMA_SOURCE = "http://java.sun.com/xml/jaxp/properties/schemaSource";

	private static String xmlLocation = "com/traclabs/biosim/server/framework/configuration/default.biosim";

	private static BioHolder myBioHolder = null;

	private static DocumentBuilder myDocumentBuilder = null;

	private static int myID = 0;

	private static boolean initialized = false;

	private static synchronized void initialize() {
		if (initialized)
			return;
		myBioHolder = new BioHolder();
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory
				.newInstance();
		documentBuilderFactory.setNamespaceAware(true);
		documentBuilderFactory.setValidating(true);
		try {
			documentBuilderFactory.setAttribute(JAXP_SCHEMA_LANGUAGE,
					W3C_XML_SCHEMA);
			URL foundURL = BioHolderInitializer.class.getClassLoader()
					.getResource(SCHEMA_LOCATION_VALUE);
			if (foundURL != null) {
				String urlString = foundURL.toString();
				if (urlString.length() > 0)
					documentBuilderFactory.setAttribute(JAXP_SCHEMA_SOURCE,
							urlString);
			}
			myDocumentBuilder = documentBuilderFactory.newDocumentBuilder();
			myDocumentBuilder.setErrorHandler(new DefaultHandler());
			parseFile();
			initialized = true;
		} catch (IllegalArgumentException e) {
			// This can happen if the parser does not support JAXP 1.2
			Logger.getLogger(BioHolderInitializer.class).warn(
					"Had trouble configuring parser for schema validation: "
							+ e);
		} catch (ParserConfigurationException e) {
			Logger.getLogger(BioHolderInitializer.class).warn(
					"Had trouble configuring parser: " + e);
			e.printStackTrace();
		}
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

	public static void setFileAndID(int pID, String pFilename) {
		if ((myID == pID) && (xmlLocation.equals(pFilename)))
			return;
		myID = pID;
		Logger.getLogger(BioHolderInitializer.class).info(
				"setting id to " + myID);
		Logger.getLogger(BioHolderInitializer.class).info(
				"setting xml file to " + pFilename);
		xmlLocation = pFilename;
		initialized = false;
		initialize();
	}

	public static void setID(int pID) {
		if (myID == pID)
			return;
		myID = pID;
		Logger.getLogger(BioHolderInitializer.class).info(
				"setting id to " + myID);
		initialized = false;
		initialize();
	}

	public static void setFile(String pFilename) {
		if (xmlLocation.equals(pFilename))
			return;
		Logger.getLogger(BioHolderInitializer.class).info(
				"setting xml file to " + pFilename);
		xmlLocation = pFilename;
		initialized = false;
		initialize();
	}

	/** Traverses the specified node, recursively. */
	private static void crawlBiosim(Node node) {
		// is there anything to do?
		if (node == null)
			return;
		String nodeName = node.getLocalName();
		if (nodeName != null) {
			if (nodeName.equals("Globals")) {
				crawlGlobals(node);
				return;
			}
			else if (nodeName.equals("SimBioModules")) {
				crawlModules(node);
				return;
			} else if (nodeName.equals("Sensors")) {
				crawlSensors(node);
				return;
			} else if (nodeName.equals("Actuators")) {
				crawlActuators(node);
				return;
			}
		}
		Node child = node.getFirstChild();
		while (child != null) {
			crawlBiosim(child);
			child = child.getNextSibling();
		}

	}

	private static void parseFile() {
		String documentString = OrbUtils.resolveXMLLocation(xmlLocation);
		if (documentString == null) {
			Logger.getLogger(BioHolderInitializer.class).error(
					"Couldn't find configuration file: " + xmlLocation);
			return;
		}
		if (documentString.length() > 0) {
			try {
				Logger.getLogger(BioHolderInitializer.class).info(
						"Initializing...");
				Document document = myDocumentBuilder.parse(documentString);
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
		} else {
			Logger.getLogger(BioHolderInitializer.class).error(
					"Couldn't find configuration file: " + xmlLocation);
			return;
		}
	}
	
	private static void crawlGlobals(Node node) {
			try {
				Node child = node.getFirstChild();
				while (child != null) {
					String childName = child.getLocalName();
					if (childName != null) {
						if (childName.equals("nameServiceLocation")) {
							parseNameServiceLocation(child);
						}
					}
					child = child.getNextSibling();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		myBioHolder.theBioDriver = BioDriverHelper.narrow(getModule("BioDriver"));
	}
	
	private static void parseNameServiceLocation(Node node) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("serverLocation")) {
					parseServerLocation(child);
				}
				else if (childName.equals("fileLocation")) {
					parseFileLocation(child);
				}
			}
			child = child.getNextSibling();
		}
	}

	private static void parseFileLocation(Node node) {
		String iorPathName = node.getAttributes().getNamedItem("path").getNodeValue();
		File iorFile = new File(iorPathName);
		OrbUtils.initializeNamingServerWithFile(iorFile);
	}

	private static void parseServerLocation(Node node) {
		String hostName = node.getAttributes().getNamedItem("hostname").getNodeValue();
		int port = Integer.parseInt(node.getAttributes().getNamedItem("port").getNodeValue());
		OrbUtils.initializeNamingServer(hostName, port);
	}

	public static org.omg.CORBA.Object getModule(String moduleName) {
		org.omg.CORBA.Object moduleToReturn = null;
		while (moduleToReturn == null) {
			try {
				moduleToReturn = OrbUtils.getNamingContext(myID).resolve_str(
						moduleName);
			} catch (org.omg.CORBA.UserException e) {
				Logger.getLogger(BioHolderInitializer.class).error(
						"BioHolder: Couldn't find module " + moduleName
								+ ", polling again...");
				e.printStackTrace();
				OrbUtils.sleepAwhile();
			} catch (Exception e) {
				Logger.getLogger(BioHolderInitializer.class).error(
						"BioHolder: Had problems contacting nameserver with module "
								+ moduleName + ", polling again: ", e);
				OrbUtils.resetInit();
				OrbUtils.sleepAwhile();
			}
		}
		return moduleToReturn;
	}

	private static String getModuleName(Node node) {
		return node.getAttributes().getNamedItem("moduleName").getNodeValue();
	}

	// Modules
	private static void crawlModules(Node node) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
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
			}
			child = child.getNextSibling();
		}
	}

	private static void fetchAirRS(Node node) {
		myBioHolder.theAirRSModules.add(AirRSHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchOGS(Node node) {
		myBioHolder.theOGSModules.add(OGSHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchPyrolizer(Node node) {
		myBioHolder.thePyrolizerModules.add(PyrolizerHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchVCCR(Node node) {
		Logger.getLogger(BioHolderInitializer.class).debug(
				"module named " + getModuleName(node));
		myBioHolder.theVCCRModules.add(VCCRHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchCRS(Node node) {
		myBioHolder.theCRSModules.add(CRSHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchO2Store(Node node) {
		myBioHolder.theO2Stores.add(O2StoreHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchCO2Store(Node node) {
		myBioHolder.theCO2Stores.add(CO2StoreHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchH2Store(Node node) {
		myBioHolder.theH2Stores.add(H2StoreHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchNitrogenStore(Node node) {
		myBioHolder.theNitrogenStores.add(NitrogenStoreHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchMethaneStore(Node node) {
		myBioHolder.theMethaneStores.add(MethaneStoreHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchCDRS(Node node) {
		myBioHolder.theCDRSModules.add(CDRSModuleHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void crawlAirModules(Node node) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				Logger.getLogger(BioHolderInitializer.class).debug(
						"parsing " + childName);
				if (childName.equals("AirRS"))
					fetchAirRS(child);
				else if (childName.equals("OGS"))
					fetchOGS(child);
				else if (childName.equals("CDRS"))
					fetchCDRS(child);
				else if (childName.equals("Pyrolizer"))
					fetchPyrolizer(child);
				else if (childName.equals("VCCR"))
					fetchVCCR(child);
				else if (childName.equals("CRS"))
					fetchCRS(child);
				else if (childName.equals("O2Store"))
					fetchO2Store(child);
				else if (childName.equals("CO2Store"))
					fetchCO2Store(child);
				else if (childName.equals("H2Store"))
					fetchH2Store(child);
				else if (childName.equals("NitrogenStore"))
					fetchNitrogenStore(child);
				else if (childName.equals("MethaneStore"))
					fetchMethaneStore(child);
			}
			child = child.getNextSibling();
		}
	}

	private static void fetchCrewGroup(Node node) {
		myBioHolder.theCrewGroups.add(CrewGroupHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void crawlCrewModules(Node node) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();

			if (childName != null) {
				if (childName.equals("CrewGroup"))
					fetchCrewGroup(child);
			}
			child = child.getNextSibling();
		}
	}

	private static void fetchSimEnvironment(Node node) {
		myBioHolder.theSimEnvironments.add(SimEnvironmentHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchDehumidifier(Node node) {
		myBioHolder.theDehumidifiers.add(DehumidifierHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchFan(Node node) {
		myBioHolder.theFans.add(FanHelper
				.narrow(getModule(getModuleName(node))));
	}
	
	private static void crawlEnvironmentModules(Node node) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();

			if (childName != null) {
				if (childName.equals("SimEnvironment"))
					fetchSimEnvironment(child);
				else if (childName.equals("Dehumidifier"))
					fetchDehumidifier(child);
				else if (childName.equals("Fan"))
					fetchFan(child);
			}
			child = child.getNextSibling();
		}
	}

	private static void fetchAccumulator(Node node) {
		myBioHolder.theAccumulators.add(AccumulatorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchInjector(Node node) {
		myBioHolder.theInjectors.add(InjectorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchInfluentValve(Node node) {
		myBioHolder.theInfluentValves.add(InfluentValveHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchEffluentValve(Node node) {
		myBioHolder.theEffluentValves.add(EffluentValveHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void crawlFrameworkModules(Node node) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();

			if (childName != null) {
				if (childName.equals("Accumulator"))
					fetchAccumulator(child);
				else if (childName.equals("Injector"))
					fetchInjector(child);
				else if (childName.equals("InfluentValve"))
					fetchInfluentValve(child);
				else if (childName.equals("EffluentValve"))
					fetchEffluentValve(child);
			}
			child = child.getNextSibling();
		}
	}

	private static void fetchBiomassPS(Node node) {
		myBioHolder.theBiomassPSModules.add(BiomassPSHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchFoodProcessor(Node node) {
		myBioHolder.theFoodProcessors.add(FoodProcessorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchBiomassStore(Node node) {
		myBioHolder.theBiomassStores.add(BiomassStoreHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchFoodStore(Node node) {
		myBioHolder.theFoodStores.add(FoodStoreHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void crawlFoodModules(Node node) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();

			if (childName != null) {
				if (childName.equals("BiomassPS"))
					fetchBiomassPS(child);
				else if (childName.equals("FoodProcessor"))
					fetchFoodProcessor(child);
				else if (childName.equals("BiomassStore"))
					fetchBiomassStore(child);
				else if (childName.equals("FoodStore"))
					fetchFoodStore(child);
			}
			child = child.getNextSibling();
		}
	}

	private static void fetchPowerPS(Node node) {
		myBioHolder.thePowerPSModules.add(PowerPSHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchPowerStore(Node node) {
		myBioHolder.thePowerStores.add(PowerStoreHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchRPCM(Node node) {
		myBioHolder.theRPCMs.add(RPCMHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchGenericPowerConsumer(Node node) {
		myBioHolder.theGenericPowerConsumers.add(GenericPowerConsumerHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void crawlPowerModules(Node node) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("PowerPS"))
					fetchPowerPS(child);
				else if (childName.equals("PowerStore"))
					fetchPowerStore(child);
				else if (childName.equals("RPCM"))
					fetchRPCM(child);
				else if (childName.equals("GenericPowerConsumer"))
					fetchGenericPowerConsumer(child);
			}
			child = child.getNextSibling();
		}
	}

	private static void fetchWaterRS(Node node) {
		myBioHolder.theWaterRSModules.add(WaterRSHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchPotableWaterStore(Node node) {
		myBioHolder.thePotableWaterStores.add(PotableWaterStoreHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchDirtyWaterStore(Node node) {
		myBioHolder.theDirtyWaterStores.add(DirtyWaterStoreHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchGreyWaterStore(Node node) {
		myBioHolder.theGreyWaterStores.add(GreyWaterStoreHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void crawlWaterModules(Node node) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("WaterRS"))
					fetchWaterRS(child);
				else if (childName.equals("PotableWaterStore"))
					fetchPotableWaterStore(child);
				else if (childName.equals("GreyWaterStore"))
					fetchGreyWaterStore(child);
				else if (childName.equals("DirtyWaterStore"))
					fetchDirtyWaterStore(child);
			}
			child = child.getNextSibling();
		}
	}

	private static void fetchIncinerator(Node node) {
		myBioHolder.theIncinerators.add(IncineratorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchDryWasteStore(Node node) {
		myBioHolder.theDryWasteStores.add(DryWasteStoreHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void crawlWasteModules(Node node) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("Incinerator"))
					fetchIncinerator(child);
				else if (childName.equals("DryWasteStore"))
					fetchDryWasteStore(child);
			}
			child = child.getNextSibling();
		}
	}

	// Sensors
	private static void crawlSensors(Node node) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
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
			}
			child = child.getNextSibling();
		}
	}

	// Air
	private static void fetchCO2InFlowRateSensor(Node node) {
		myBioHolder.theCO2InFlowRateSensors.add(CO2InFlowRateSensorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchCO2OutFlowRateSensor(Node node) {
		myBioHolder.theCO2OutFlowRateSensors.add(CO2OutFlowRateSensorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchO2InFlowRateSensor(Node node) {
		myBioHolder.theO2InFlowRateSensors.add(O2InFlowRateSensorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchO2OutFlowRateSensor(Node node) {
		myBioHolder.theO2OutFlowRateSensors.add(O2OutFlowRateSensorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchH2InFlowRateSensor(Node node) {
		myBioHolder.theH2InFlowRateSensors.add(H2InFlowRateSensorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchH2OutFlowRateSensor(Node node) {
		myBioHolder.theH2OutFlowRateSensors.add(H2OutFlowRateSensorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchNitrogenInFlowRateSensor(Node node) {
		myBioHolder.theNitrogenInFlowRateSensors
				.add(NitrogenInFlowRateSensorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void fetchNitrogenOutFlowRateSensor(Node node) {
		myBioHolder.theNitrogenOutFlowRateSensors
				.add(NitrogenOutFlowRateSensorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void fetchMethaneInFlowRateSensor(Node node) {
		myBioHolder.theMethaneInFlowRateSensors
				.add(MethaneInFlowRateSensorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void fetchMethaneOutFlowRateSensor(Node node) {
		myBioHolder.theMethaneOutFlowRateSensors
				.add(MethaneOutFlowRateSensorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void crawlAirSensors(Node node) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("CO2InFlowRateSensor"))
					fetchCO2InFlowRateSensor(child);
				else if (childName.equals("CO2OutFlowRateSensor"))
					fetchCO2OutFlowRateSensor(child);
				else if (childName.equals("O2InFlowRateSensor"))
					fetchO2InFlowRateSensor(child);
				else if (childName.equals("O2OutFlowRateSensor"))
					fetchO2OutFlowRateSensor(child);
				else if (childName.equals("H2InFlowRateSensor"))
					fetchH2InFlowRateSensor(child);
				else if (childName.equals("H2OutFlowRateSensor"))
					fetchH2OutFlowRateSensor(child);
				else if (childName.equals("NitrogenInFlowRateSensor"))
					fetchNitrogenInFlowRateSensor(child);
				else if (childName.equals("NitrogenOutFlowRateSensor"))
					fetchNitrogenOutFlowRateSensor(child);
				else if (childName.equals("MethaneInFlowRateSensor"))
					fetchMethaneInFlowRateSensor(child);
				else if (childName.equals("MethaneOutFlowRateSensor"))
					fetchMethaneOutFlowRateSensor(child);
			}
			child = child.getNextSibling();
		}
	}

	// Crew
	private static void fetchCrewGroupDeathSensor(Node node) {
		myBioHolder.theCrewGroupDeathSensors.add(CrewGroupDeathSensorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchCrewGroupAnyDeadSensor(Node node) {
		myBioHolder.theCrewGroupAnyDeadSensors.add(CrewGroupAnyDeadSensorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchCrewGroupProductivitySensor(Node node) {
		myBioHolder.theCrewGroupProductivitySensors
				.add(CrewGroupProductivitySensorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void crawlCrewSensors(Node node) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("CrewGroupDeathSensor"))
					fetchCrewGroupDeathSensor(child);
				else if (childName.equals("CrewGroupAnyDeadSensor"))
					fetchCrewGroupAnyDeadSensor(child);
				else if (childName.equals("CrewGroupProductivitySensor"))
					fetchCrewGroupProductivitySensor(child);
			}
			child = child.getNextSibling();
		}
	}

	// Environment
	private static void fetchAirInFlowRateSensor(Node node) {
		myBioHolder.theAirInFlowRateSensors.add(AirInFlowRateSensorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchAirOutFlowRateSensor(Node node) {
		myBioHolder.theAirOutFlowRateSensors.add(AirOutFlowRateSensorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchGasPressureSensor(Node child) {
		myBioHolder.theGasPressureSensors.add(GasPressureSensorHelper
				.narrow(getModule(getModuleName(child))));
	}

	private static void fetchGasConcentrationSensor(Node child) {
		myBioHolder.theGasConcentrationSensors.add(GasConcentrationSensorHelper
				.narrow(getModule(getModuleName(child))));
	}

	private static void fetchTotalMolesSensor(Node child) {
		myBioHolder.theTotalMolesSensors.add(TotalMolesSensorHelper
				.narrow(getModule(getModuleName(child))));
	}

	private static void fetchTotalPressureSensor(Node child) {
		myBioHolder.theTotalPressureSensors.add(TotalPressureSensorHelper
				.narrow(getModule(getModuleName(child))));
	}

	private static void crawlEnvironmentSensors(Node node) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("AirInFlowRateSensor"))
					fetchAirInFlowRateSensor(child);
				else if (childName.equals("AirOutFlowRateSensor"))
					fetchAirOutFlowRateSensor(child);
				else if (childName.equals("GasConcentrationSensor"))
					fetchGasConcentrationSensor(child);
				else if (childName.equals("GasPressureSensor"))
					fetchGasPressureSensor(child);
				else if (childName.equals("TotalMolesSensor"))
					fetchTotalMolesSensor(child);
				else if (childName.equals("TotalPressureSensor"))
					fetchTotalPressureSensor(child);
			}
			child = child.getNextSibling();
		}
	}

	// Food
	private static void fetchBiomassInFlowRateSensor(Node node) {
		myBioHolder.theBiomassInFlowRateSensors
				.add(BiomassInFlowRateSensorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void fetchBiomassOutFlowRateSensor(Node node) {
		myBioHolder.theBiomassOutFlowRateSensors
				.add(BiomassOutFlowRateSensorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void fetchBiomassStoreWaterContentSensor(Node node) {
		myBioHolder.theBiomassStoreWaterContentSensors
				.add(BiomassStoreWaterContentSensorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void fetchFoodInFlowRateSensor(Node node) {
		myBioHolder.theFoodInFlowRateSensors.add(FoodInFlowRateSensorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchFoodOutFlowRateSensor(Node node) {
		myBioHolder.theFoodOutFlowRateSensors.add(FoodOutFlowRateSensorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchHarvestSensor(Node node) {
		myBioHolder.theHarvestSensors.add(HarvestSensorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchPlantDeathSensor(Node node) {
		myBioHolder.thePlantDeathSensors.add(PlantDeathSensorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchTimeTillCanopyClosedSensor(Node node) {
		myBioHolder.theTimeTillCanopyClosureSensors
				.add(TimeTillCanopyClosureSensorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void crawlFoodSensors(Node node) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("BiomassInFlowRateSensor"))
					fetchBiomassInFlowRateSensor(child);
				if (childName.equals("BiomassOutFlowRateSensor"))
					fetchBiomassOutFlowRateSensor(child);
				else if (childName.equals("BiomassStoreWaterContentSensor"))
					fetchBiomassStoreWaterContentSensor(child);
				else if (childName.equals("FoodInFlowRateSensor"))
					fetchFoodInFlowRateSensor(child);
				else if (childName.equals("FoodOutFlowRateSensor"))
					fetchFoodOutFlowRateSensor(child);
				else if (childName.equals("HarvestSensor"))
					fetchHarvestSensor(child);
				else if (childName.equals("PlantDeathSensor"))
					fetchPlantDeathSensor(child);
				else if (childName.equals("TimeTillCanopyClosureSensor"))
					fetchTimeTillCanopyClosedSensor(child);
			}
			child = child.getNextSibling();
		}
	}

	// Framework
	private static void fetchStoreLevelSensor(Node node) {
		myBioHolder.theStoreLevelSensors.add(StoreLevelSensorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchStoreOverflowSensor(Node node) {
		myBioHolder.theStoreOverflowSensors.add(StoreOverflowSensorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchInfluentValveStateSensor(Node node) {
		myBioHolder.theInfluentValveStateSensors
				.add(InfluentValveStateSensorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void fetchEffluentValveStateSensor(Node node) {
		myBioHolder.theEffluentValveStateSensors
				.add(EffluentValveStateSensorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void fetchTimeSensor(Node node) {
		myBioHolder.theTimeSensors
				.add(TimeSensorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void crawlFrameworkSensors(Node node) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("StoreLevelSensor"))
					fetchStoreLevelSensor(child);
				else if (childName.equals("StoreOverflowSensor"))
					fetchStoreOverflowSensor(child);
				else if (childName.equals("InfluentValveStateSensor"))
					fetchInfluentValveStateSensor(child);
				else if (childName.equals("EfffluentValveStateSensor"))
					fetchEffluentValveStateSensor(child);
				else if (childName.equals("TimeSensor"))
					fetchTimeSensor(child);
			}
			child = child.getNextSibling();
		}
	}

	// Power
	private static void fetchPowerInFlowRateSensor(Node node) {
		myBioHolder.thePowerInFlowRateSensors.add(PowerInFlowRateSensorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchPowerOutFlowRateSensor(Node node) {
		myBioHolder.thePowerOutFlowRateSensors.add(PowerOutFlowRateSensorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void crawlPowerSensors(Node node) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("PowerInFlowRateSensor"))
					fetchPowerInFlowRateSensor(child);
				else if (childName.equals("PowerOutFlowRateSensor"))
					fetchPowerOutFlowRateSensor(child);
			}
			child = child.getNextSibling();
		}
	}

	// Water
	private static void fetchPotableWaterInFlowRateSensor(Node node) {
		myBioHolder.thePotableWaterInFlowRateSensors
				.add(PotableWaterInFlowRateSensorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void fetchPotableWaterOutFlowRateSensor(Node node) {
		myBioHolder.thePotableWaterOutFlowRateSensors
				.add(PotableWaterOutFlowRateSensorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void fetchGreyWaterInFlowRateSensor(Node node) {
		myBioHolder.theGreyWaterInFlowRateSensors
				.add(GreyWaterInFlowRateSensorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void fetchGreyWaterOutFlowRateSensor(Node node) {
		myBioHolder.theGreyWaterOutFlowRateSensors
				.add(GreyWaterOutFlowRateSensorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void fetchDirtyWaterInFlowRateSensor(Node node) {
		myBioHolder.theDirtyWaterInFlowRateSensors
				.add(DirtyWaterInFlowRateSensorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void fetchDirtyWaterOutFlowRateSensor(Node node) {
		myBioHolder.theDirtyWaterOutFlowRateSensors
				.add(DirtyWaterOutFlowRateSensorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void fetchWaterInFlowRateSensor(Node node) {
		myBioHolder.theWaterInFlowRateSensors.add(WaterInFlowRateSensorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchWaterOutFlowRateSensor(Node node) {
		myBioHolder.theWaterOutFlowRateSensors.add(WaterOutFlowRateSensorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void crawlWaterSensors(Node node) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("PotableWaterInFlowRateSensor"))
					fetchPotableWaterInFlowRateSensor(child);
				else if (childName.equals("PotableWaterOutFlowRateSensor"))
					fetchPotableWaterOutFlowRateSensor(child);
				else if (childName.equals("GreyWaterInFlowRateSensor"))
					fetchGreyWaterInFlowRateSensor(child);
				else if (childName.equals("GreyWaterOutFlowRateSensor"))
					fetchGreyWaterOutFlowRateSensor(child);
				else if (childName.equals("DirtyWaterInFlowRateSensor"))
					fetchDirtyWaterInFlowRateSensor(child);
				else if (childName.equals("DirtyWaterOutFlowRateSensor"))
					fetchDirtyWaterOutFlowRateSensor(child);
				else if (childName.equals("WaterInFlowRateSensor"))
					fetchWaterInFlowRateSensor(child);
				else if (childName.equals("WaterOutFlowRateSensor"))
					fetchWaterOutFlowRateSensor(child);
			}
			child = child.getNextSibling();
		}
	}

	// Waste
	private static void fetchDryWasteInFlowRateSensor(Node node) {
		myBioHolder.theDryWasteInFlowRateSensors
				.add(DryWasteInFlowRateSensorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void fetchDryWasteOutFlowRateSensor(Node node) {
		myBioHolder.theDryWasteOutFlowRateSensors
				.add(DryWasteOutFlowRateSensorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void crawlWasteSensors(Node node) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("DryWasteInFlowRateSensor"))
					fetchDryWasteInFlowRateSensor(child);
				else if (childName.equals("DryWasteOutFlowRateSensor"))
					fetchDryWasteOutFlowRateSensor(child);
			}
			child = child.getNextSibling();
		}
	}

	// Actuators
	private static void crawlActuators(Node node) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("air")) {
					crawlAirActuators(child);
				} else if (childName.equals("environment")) {
					crawlEnvironmentActuators(child);
				} else if (childName.equals("food")) {
					crawlFoodActuators(child);
				} else if (childName.equals("framework")) {
					crawlFrameworkActuators(child);
				} else if (childName.equals("power")) {
					crawlPowerActuators(child);
				} else if (childName.equals("water")) {
					crawlWaterActuators(child);
				} else if (childName.equals("waste")) {
					crawlWasteActuators(child);
				}
			}
			child = child.getNextSibling();
		}
	}

	// Air
	private static void fetchCO2InFlowRateActuator(Node node) {
		myBioHolder.theCO2InFlowRateActuators.add(CO2InFlowRateActuatorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchCO2OutFlowRateActuator(Node node) {
		myBioHolder.theCO2OutFlowRateActuators.add(CO2OutFlowRateActuatorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchO2InFlowRateActuator(Node node) {
		myBioHolder.theO2InFlowRateActuators.add(O2InFlowRateActuatorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchO2OutFlowRateActuator(Node node) {
		myBioHolder.theO2OutFlowRateActuators.add(O2OutFlowRateActuatorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchH2InFlowRateActuator(Node node) {
		myBioHolder.theH2InFlowRateActuators.add(H2InFlowRateActuatorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchH2OutFlowRateActuator(Node node) {
		myBioHolder.theH2OutFlowRateActuators.add(H2OutFlowRateActuatorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchNitrogenInFlowRateActuator(Node node) {
		myBioHolder.theNitrogenInFlowRateActuators
				.add(NitrogenInFlowRateActuatorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void fetchNitrogenOutFlowRateActuator(Node node) {
		myBioHolder.theNitrogenOutFlowRateActuators
				.add(NitrogenOutFlowRateActuatorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void fetchMethaneInFlowRateActuator(Node node) {
		myBioHolder.theMethaneInFlowRateActuators
				.add(MethaneInFlowRateActuatorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void fetchMethaneOutFlowRateActuator(Node node) {
		myBioHolder.theMethaneOutFlowRateActuators
				.add(MethaneOutFlowRateActuatorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void crawlAirActuators(Node node) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
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
				else if (childName.equals("MethaneInFlowRateActuator"))
					fetchMethaneInFlowRateActuator(child);
				else if (childName.equals("MethaneOutFlowRateActuator"))
					fetchMethaneOutFlowRateActuator(child);
			}
			child = child.getNextSibling();
		}
	}

	// Environment
	private static void fetchAirInFlowRateActuator(Node node) {
		myBioHolder.theAirInFlowRateActuators.add(AirInFlowRateActuatorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchAirOutFlowRateActuator(Node node) {
		myBioHolder.theAirOutFlowRateActuators.add(AirOutFlowRateActuatorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void crawlEnvironmentActuators(Node node) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("AirInFlowRateActuator"))
					fetchAirInFlowRateActuator(child);
				else if (childName.equals("AirOutFlowRateActuator"))
					fetchAirOutFlowRateActuator(child);
			}
			child = child.getNextSibling();
		}
	}

	// Framework
	private static void fetchInfluentValveActuator(Node node) {
		myBioHolder.theInfluentValveActuators.add(InfluentValveActuatorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchEffluentValveActuator(Node node) {
		myBioHolder.theEffluentValveActuators.add(EffluentValveActuatorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void crawlFrameworkActuators(Node node) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("InfluentValveActuator"))
					fetchInfluentValveActuator(child);
				else if (childName.equals("EffluentValveActuator"))
					fetchEffluentValveActuator(child);
			}
			child = child.getNextSibling();
		}
	}

	// Food
	private static void fetchBiomassInFlowRateActuator(Node node) {
		myBioHolder.theBiomassInFlowRateActuators
				.add(BiomassInFlowRateActuatorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void fetchBiomassOutFlowRateActuator(Node node) {
		myBioHolder.theBiomassOutFlowRateActuators
				.add(BiomassOutFlowRateActuatorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void fetchFoodInFlowRateActuator(Node node) {
		myBioHolder.theFoodInFlowRateActuators.add(FoodInFlowRateActuatorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchFoodOutFlowRateActuator(Node node) {
		myBioHolder.theFoodOutFlowRateActuators
				.add(FoodOutFlowRateActuatorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void fetchPlantingActuator(Node node) {
		myBioHolder.thePlantingActuators.add(PlantingActuatorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void fetchHarvestingActuator(Node node) {
		myBioHolder.theHarvestingActuators.add(HarvestingActuatorHelper
				.narrow(getModule(getModuleName(node))));
	}

	private static void crawlFoodActuators(Node node) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
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
			}
			child = child.getNextSibling();
		}
	}

	// Power
	private static void fetchPowerInFlowRateActuator(Node node) {
		myBioHolder.thePowerInFlowRateActuators
				.add(PowerInFlowRateActuatorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void fetchPowerOutFlowRateActuator(Node node) {
		myBioHolder.thePowerOutFlowRateActuators
				.add(PowerOutFlowRateActuatorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void crawlPowerActuators(Node node) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("PowerInFlowRateActuator"))
					fetchPowerInFlowRateActuator(child);
				else if (childName.equals("PowerOutFlowRateActuator"))
					fetchPowerOutFlowRateActuator(child);
			}
			child = child.getNextSibling();
		}
	}

	// Water
	private static void fetchPotableWaterInFlowRateActuator(Node node) {
		myBioHolder.thePotableWaterInFlowRateActuators
				.add(PotableWaterInFlowRateActuatorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void fetchPotableWaterOutFlowRateActuator(Node node) {
		myBioHolder.thePotableWaterOutFlowRateActuators
				.add(PotableWaterOutFlowRateActuatorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void fetchGreyWaterInFlowRateActuator(Node node) {
		myBioHolder.theGreyWaterInFlowRateActuators
				.add(GreyWaterInFlowRateActuatorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void fetchGreyWaterOutFlowRateActuator(Node node) {
		myBioHolder.theGreyWaterOutFlowRateActuators
				.add(GreyWaterOutFlowRateActuatorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void fetchDirtyWaterInFlowRateActuator(Node node) {
		myBioHolder.theDirtyWaterInFlowRateActuators
				.add(DirtyWaterInFlowRateActuatorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void fetchDirtyWaterOutFlowRateActuator(Node node) {
		myBioHolder.theDirtyWaterOutFlowRateActuators
				.add(DirtyWaterOutFlowRateActuatorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void fetchWaterInFlowRateActuator(Node node) {
		myBioHolder.theWaterInFlowRateActuators
				.add(WaterInFlowRateActuatorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void fetchWaterOutFlowRateActuator(Node node) {
		myBioHolder.theWaterOutFlowRateActuators
				.add(WaterOutFlowRateActuatorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void crawlWaterActuators(Node node) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
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
			}
			child = child.getNextSibling();
		}
	}

	// Waste
	private static void fetchDryWasteInFlowRateActuator(Node node) {
		myBioHolder.theDryWasteInFlowRateActuators
				.add(DryWasteInFlowRateActuatorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void fetchDryWasteOutFlowRateActuator(Node node) {
		myBioHolder.theDryWasteOutFlowRateActuators
				.add(DryWasteOutFlowRateActuatorHelper
						.narrow(getModule(getModuleName(node))));
	}

	private static void crawlWasteActuators(Node node) {
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getLocalName();
			if (childName != null) {
				if (childName.equals("DryWasteInFlowRateActuator"))
					fetchDryWasteInFlowRateActuator(child);
				else if (childName.equals("DryWasteOutFlowRateActuator"))
					fetchDryWasteOutFlowRateActuator(child);
			}
			child = child.getNextSibling();
		}
	}
}