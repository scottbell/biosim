package biosim.client.util;

import java.net.*;
import java.util.*;
import org.w3c.dom.*;
import org.apache.xerces.parsers.*;
import org.xml.sax.*;
import biosim.idl.simulation.air.*;
import biosim.idl.simulation.crew.*;
import biosim.idl.simulation.food.*;
import biosim.idl.simulation.water.*;
import biosim.idl.simulation.waste.*;
import biosim.idl.simulation.power.*;
import biosim.idl.simulation.environment.*;
import biosim.idl.simulation.framework.*;
import biosim.idl.framework.*;
import biosim.idl.sensor.air.*;
import biosim.idl.sensor.food.*;
import biosim.idl.sensor.water.*;
import biosim.idl.sensor.power.*;
import biosim.idl.sensor.crew.*;
import biosim.idl.sensor.environment.*;
import biosim.idl.sensor.framework.*;
import biosim.idl.sensor.waste.*;
import biosim.idl.actuator.air.*;
import biosim.idl.actuator.food.*;
import biosim.idl.actuator.water.*;
import biosim.idl.actuator.power.*;
import biosim.idl.actuator.crew.*;
import biosim.idl.actuator.environment.*;
import biosim.idl.actuator.framework.*;
import biosim.idl.actuator.waste.*;

/**
 * Reads BioSim configuration from XML file.
 *
 * @author Scott Bell
 */
public class BioHolder{
	/** Namespaces feature id (http://xml.org/sax/features/moduleNamespaces). */
	private static final String NAMESPACES_FEATURE_ID = "http://xml.org/sax/features/namespaces";
	/** Validation feature id (http://xml.org/sax/features/validation). */
	private static final String VALIDATION_FEATURE_ID = "http://xml.org/sax/features/validation";
	/** Schema validation feature id (http://apache.org/xml/features/validation/schema). */
	private static final String SCHEMA_VALIDATION_FEATURE_ID = "http://apache.org/xml/features/validation/schema";
	/** Schema full checking feature id (http://apache.org/xml/features/validation/schema-full-checking). */
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
	private static String xmlLocation = "biosim/server/framework/DefaultInitialization.xml";
	private static Holder myHolder = null;

	private static DOMParser myParser = null;
	private static int myID = 0;
	private static BioDriver myBioDriver;
	private static boolean initialized = false;

	private static synchronized void initialize(){
		if (initialized)
			return;
		myHolder = new Holder();
		try {
			myParser = new DOMParser();
			myParser.setFeature(SCHEMA_VALIDATION_FEATURE_ID, DEFAULT_SCHEMA_VALIDATION);
			myParser.setFeature(SCHEMA_FULL_CHECKING_FEATURE_ID, DEFAULT_SCHEMA_FULL_CHECKING);
			myParser.setFeature(VALIDATION_FEATURE_ID, DEFAULT_VALIDATION);
			myParser.setFeature(NAMESPACES_FEATURE_ID, DEFAULT_NAMESPACES);
			initialized = true;
		}
		catch (SAXException e) {
			System.err.println("warning: Parser does not support feature ("+NAMESPACES_FEATURE_ID+")");
		}
		parseFile();
		myHolder.coallateLists();
	}

	public static BioDriver getBioDriver(){
		initialize();
		return myBioDriver;
	}

	public static Holder getHolder(){
		initialize();
		return myHolder;
	}

	public static int getID(){
		return myID;
	}

	public static void setID(int pID){
		myID = pID;
		if (initialized)
			myHolder.reset();
		else
			initialize();
		parseFile();
	}

	public static void setFile(String pFilename){
		xmlLocation = pFilename;
		if (initialized)
			myHolder.reset();
		else
			initialize();
		parseFile();
	}

	/** Traverses the specified node, recursively. */
	private static void crawlBiosim(Node node) {
		// is there anything to do?
		if (node == null)
			return;
		String nodeName = node.getNodeName();
		if (nodeName.equals("SimBioModules")){
			crawlModules(node);
			return;
		}
		else if (nodeName.equals("Sensors")){
			crawlSensors(node);
			return;
		}
		else if (nodeName.equals("Actuators")){
			crawlActuators(node);
			return;
		}
		else{
			Node child = node.getFirstChild();
			while (child != null) {
				crawlBiosim(child);
				child = child.getNextSibling();
			}
		}

	}

	private static void coallateLists(){
	}

	private static void parseFile(){
		myBioDriver = BioDriverHelper.narrow(grabModule("BioDriver"));
		URL documentUrl = ClassLoader.getSystemClassLoader().getResource(xmlLocation);
		if (documentUrl == null){
			System.err.println("Couldn't find init xml file: "+xmlLocation);
			System.err.println("Exiting...");
			System.exit(1);
		}
		String documentString = documentUrl.toString();
		if (documentString.length() > 0){
			try{
				System.out.print("Initializing...");
				myParser.parse(documentString);
				Document document = myParser.getDocument();
				crawlBiosim(document);
				System.out.println("done");
				System.out.flush();
			}
			catch (Exception e){
				System.err.println("error: Parse error occurred - "+e.getMessage());
				Exception se = e;
				if (e instanceof SAXException)
					se = ((SAXException)e).getException();
				if (se != null)
					se.printStackTrace(System.err);
				else
					e.printStackTrace(System.err);
			}
			myHolder.coallateLists();
		}
	}

	private static org.omg.CORBA.Object grabModule(String moduleName){
		org.omg.CORBA.Object moduleToReturn = null;
		while (moduleToReturn == null){
			try{
				moduleToReturn = OrbUtils.getNamingContext(myID).resolve_str(moduleName);
			}
			catch (org.omg.CORBA.UserException e){
				System.err.println("BioHolder: Couldn't find module "+moduleName+", polling again...");
				OrbUtils.sleepAwhile();
			}
			catch (Exception e){
				System.err.println("BioHolder: Had problems contacting nameserver with module "+moduleName+", polling again...");
				OrbUtils.resetInit();
				OrbUtils.sleepAwhile();
			}
		}
		return moduleToReturn;
	}

	private static String getModuleName(Node node){
		return node.getAttributes().getNamedItem("name").getNodeValue();
	}

	//Modules
	private static void crawlModules(Node node){
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

	private static void fetchAirRS(Node node){
		myHolder.myAirRSModules.add(AirRSHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchO2Store(Node node){
		myHolder.myO2Stores.add(O2StoreHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchCO2Store(Node node){
		myHolder.myCO2Stores.add(CO2StoreHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchH2Store(Node node){
		myHolder.myH2Stores.add(H2StoreHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchNitrogenStore(Node node){
		myHolder.myNitrogenStores.add(NitrogenStoreHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void crawlAirModules(Node node){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("AirRS"))
				fetchAirRS(child);
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

	private static void fetchCrewGroup(Node node){
		myHolder.myCrewGroups.add(CrewGroupHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void crawlCrewModules(Node node){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("CrewGroup"))
				fetchCrewGroup(child);
			child = child.getNextSibling();
		}
	}

	private static void fetchSimEnvironment(Node node){
		myHolder.mySimEnvironments.add(SimEnvironmentHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void crawlEnvironmentModules(Node node){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("SimEnvironment"))
				fetchSimEnvironment(child);
			child = child.getNextSibling();
		}
	}

	private static void fetchAccumulator(Node node){
		myHolder.myAccumulators.add(AccumulatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchInjector(Node node){
		myHolder.myInjectors.add(InjectorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void crawlFrameworkModules(Node node){
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

	private static void fetchBiomassRS(Node node){
		myHolder.myBiomassRSModules.add(BiomassRSHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchFoodProcessor(Node node){
		myHolder.myFoodProcessors.add(FoodProcessorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchBiomassStore(Node node){
		myHolder.myBiomassStores.add(BiomassStoreHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchFoodStore(Node node){
		myHolder.myFoodStores.add(FoodStoreHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void crawlFoodModules(Node node){
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

	private static void fetchPowerPS(Node node){
		myHolder.myPowerPSModules.add(PowerPSHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchPowerStore(Node node){
		myHolder.myPowerStores.add(PowerStoreHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void crawlPowerModules(Node node){
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

	private static void fetchWaterRS(Node node){
		myHolder.myWaterRSModules.add(WaterRSHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchPotableWaterStore(Node node){
		myHolder.myPotableWaterStores.add(PotableWaterStoreHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchDirtyWaterStore(Node node){
		myHolder.myDirtyWaterStores.add(DirtyWaterStoreHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchGreyWaterStore(Node node){
		myHolder.myGreyWaterStores.add(DirtyWaterStoreHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void crawlWaterModules(Node node){
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

	private static void fetchIncinerator(Node node){
		myHolder.myIncinerators.add(IncineratorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchDryWasteStore(Node node){
		myHolder.myDryWasteStores.add(DryWasteStoreHelper.narrow(grabModule(getModuleName(node))));
	}

	private static void crawlWasteModules(Node node){
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
	private static void crawlSensors(Node node){
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
	private static void fetchCO2InFlowRateSensor(Node node){
		myHolder.myCO2InFlowRateSensors.add(CO2InFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchCO2OutFlowRateSensor(Node node){
		myHolder.myCO2OutFlowRateSensors.add(CO2OutFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchCO2StoreLevelSensor(Node node){
		myHolder.myCO2StoreLevelSensors.add(CO2StoreLevelSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchO2InFlowRateSensor(Node node){
		myHolder.myO2InFlowRateSensors.add(O2InFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchO2OutFlowRateSensor(Node node){
		myHolder.myO2OutFlowRateSensors.add(O2OutFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchO2StoreLevelSensor(Node node){
		myHolder.myO2StoreLevelSensors.add(O2StoreLevelSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchH2InFlowRateSensor(Node node){
		myHolder.myH2InFlowRateSensors.add(H2InFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchH2OutFlowRateSensor(Node node){
		myHolder.myH2OutFlowRateSensors.add(H2OutFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchH2StoreLevelSensor(Node node){
		myHolder.myH2StoreLevelSensors.add(H2StoreLevelSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchNitrogenInFlowRateSensor(Node node){
		myHolder.myNitrogenInFlowRateSensors.add(NitrogenInFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchNitrogenOutFlowRateSensor(Node node){
		myHolder.myNitrogenOutFlowRateSensors.add(NitrogenOutFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchNitrogenStoreLevelSensor(Node node){
		myHolder.myNitrogenStoreLevelSensors.add(NitrogenStoreLevelSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void crawlAirSensors(Node node){
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
	private static void fetchCrewGroupDeathSensor(Node node){
		myHolder.myCrewGroupDeathSensors.add(CrewGroupDeathSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchCrewGroupAnyDeadSensor(Node node){
		myHolder.myCrewGroupAnyDeadSensors.add(CrewGroupAnyDeadSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchCrewGroupProductivitySensor(Node node){
		myHolder.myCrewGroupProductivitySensors.add(CrewGroupProductivitySensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void crawlCrewSensors(Node node){
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
	private static void fetchAirInFlowRateSensor(Node node){
		myHolder.myAirInFlowRateSensors.add(AirInFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchAirOutFlowRateSensor(Node node){
		myHolder.myAirOutFlowRateSensors.add(AirOutFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchCO2AirConcentrationSensor(Node node){
		myHolder.myCO2AirConcentrationSensors.add(CO2AirConcentrationSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchCO2AirPressureSensor(Node node){
		myHolder.myCO2AirPressureSensors.add(CO2AirPressureSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchCO2AirEnvironmentInFlowRateSensor(Node node){
		myHolder.myCO2AirEnvironmentInFlowRateSensors.add(CO2AirEnvironmentInFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchCO2AirEnvironmentOutFlowRateSensor(Node node){
		myHolder.myCO2AirEnvironmentOutFlowRateSensors.add(CO2AirEnvironmentOutFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchCO2AirStoreInFlowRateSensor(Node node){
		myHolder.myCO2AirStoreInFlowRateSensors.add(CO2AirStoreInFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchCO2AirStoreOutFlowRateSensor(Node node){
		myHolder.myCO2AirStoreOutFlowRateSensors.add(CO2AirStoreOutFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchO2AirConcentrationSensor(Node node){
		myHolder.myO2AirConcentrationSensors.add(O2AirConcentrationSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchO2AirPressureSensor(Node node){
		myHolder.myO2AirPressureSensors.add(O2AirPressureSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchO2AirEnvironmentInFlowRateSensor(Node node){
		myHolder.myO2AirEnvironmentInFlowRateSensors.add(O2AirEnvironmentInFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchO2AirEnvironmentOutFlowRateSensor(Node node){
		myHolder.myO2AirEnvironmentOutFlowRateSensors.add(O2AirEnvironmentOutFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchO2AirStoreInFlowRateSensor(Node node){
		myHolder.myO2AirStoreInFlowRateSensors.add(O2AirStoreInFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchO2AirStoreOutFlowRateSensor(Node node){
		myHolder.myO2AirStoreOutFlowRateSensors.add(O2AirStoreOutFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchOtherAirConcentrationSensor(Node node){
		myHolder.myOtherAirConcentrationSensors.add(OtherAirConcentrationSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchOtherAirPressureSensor(Node node){
		myHolder.myOtherAirPressureSensors.add(OtherAirPressureSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchWaterAirConcentrationSensor(Node node){
		myHolder.myWaterAirConcentrationSensors.add(WaterAirConcentrationSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchWaterAirPressureSensor(Node node){
		myHolder.myWaterAirPressureSensors.add(WaterAirPressureSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchWaterAirEnvironmentInFlowRateSensor(Node node){
		myHolder.myWaterAirEnvironmentInFlowRateSensors.add(WaterAirEnvironmentInFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchWaterAirEnvironmentOutFlowRateSensor(Node node){
		myHolder.myWaterAirEnvironmentOutFlowRateSensors.add(WaterAirEnvironmentOutFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchWaterAirStoreInFlowRateSensor(Node node){
		myHolder.myWaterAirStoreInFlowRateSensors.add(WaterAirStoreInFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchWaterAirStoreOutFlowRateSensor(Node node){
		myHolder.myWaterAirStoreOutFlowRateSensors.add(WaterAirStoreOutFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchNitrogenAirConcentrationSensor(Node node){
		myHolder.myNitrogenAirConcentrationSensors.add(NitrogenAirConcentrationSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchNitrogenAirPressureSensor(Node node){
		myHolder.myNitrogenAirPressureSensors.add(NitrogenAirPressureSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchNitrogenAirEnvironmentInFlowRateSensor(Node node){
		myHolder.myNitrogenAirEnvironmentInFlowRateSensors.add(NitrogenAirEnvironmentInFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchNitrogenAirEnvironmentOutFlowRateSensor(Node node){
		myHolder.myNitrogenAirEnvironmentOutFlowRateSensors.add(NitrogenAirEnvironmentOutFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchNitrogenAirStoreInFlowRateSensor(Node node){
		myHolder.myNitrogenAirStoreInFlowRateSensors.add(NitrogenAirStoreInFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchNitrogenAirStoreOutFlowRateSensor(Node node){
		myHolder.myNitrogenAirStoreOutFlowRateSensors.add(NitrogenAirStoreOutFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void crawlEnvironmentSensors(Node node){
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
			else if (childName.equals("NitrogenAirEnvironmentOutFlowRateSensor"))
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
	private static void fetchBiomassInFlowRateSensor(Node node){
		myHolder.myBiomassInFlowRateSensors.add(BiomassInFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchBiomassOutFlowRateSensor(Node node){
		myHolder.myBiomassOutFlowRateSensors.add(BiomassOutFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchBiomassStoreLevelSensor(Node node){
		myHolder.myBiomassStoreLevelSensors.add(BiomassStoreLevelSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchFoodInFlowRateSensor(Node node){
		myHolder.myFoodInFlowRateSensors.add(FoodInFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchFoodOutFlowRateSensor(Node node){
		myHolder.myFoodOutFlowRateSensors.add(FoodOutFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchFoodStoreLevelSensor(Node node){
		myHolder.myFoodStoreLevelSensors.add(FoodStoreLevelSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchHarvestSensor(Node node){
		myHolder.myHarvestSensors.add(HarvestSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void crawlFoodSensors(Node node){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("BiomassInFlowRateSensor"))
				fetchBiomassInFlowRateSensor(child);
			if (childName.equals("BiomassOutFlowRateSensor"))
				fetchBiomassOutFlowRateSensor(child);
			else if (childName.equals("BiomassStoreLevelSensor"))
				fetchBiomassStoreLevelSensor(child);
			else if (childName.equals("FoodInFlowRateSensor"))
				fetchFoodInFlowRateSensor(child);
			else if (childName.equals("FoodOutFlowRateSensor"))
				fetchFoodOutFlowRateSensor(child);
			else if (childName.equals("FoodStoreLevelSensor"))
				fetchFoodStoreLevelSensor(child);
			else if (childName.equals("HarvestSensor"))
				fetchHarvestSensor(child);
			child = child.getNextSibling();
		}
	}

	//Framework
	private static void fetchStoreLevelSensor(Node node){
		myHolder.myStoreLevelSensors.add(StoreLevelSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchStoreOverflowSensor(Node node){
		myHolder.myStoreOverflowSensors.add(StoreOverflowSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void crawlFrameworkSensors(Node node){
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
	private static void fetchPowerInFlowRateSensor(Node node){
		myHolder.myPowerInFlowRateSensors.add(PowerInFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchPowerOutFlowRateSensor(Node node){
		myHolder.myPowerOutFlowRateSensors.add(PowerOutFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchPowerStoreLevelSensor(Node node){
		myHolder.myPowerStoreLevelSensors.add(PowerStoreLevelSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void crawlPowerSensors(Node node){
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
	private static void fetchPotableWaterInFlowRateSensor(Node node){
		myHolder.myPotableWaterInFlowRateSensors.add(PotableWaterInFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchPotableWaterOutFlowRateSensor(Node node){
		myHolder.myPotableWaterOutFlowRateSensors.add(PotableWaterOutFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchPotableWaterStoreLevelSensor(Node node){
		myHolder.myPotableWaterStoreLevelSensors.add(PotableWaterStoreLevelSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchGreyWaterInFlowRateSensor(Node node){
		myHolder.myGreyWaterInFlowRateSensors.add(GreyWaterInFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchGreyWaterOutFlowRateSensor(Node node){
		myHolder.myGreyWaterOutFlowRateSensors.add(GreyWaterOutFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchGreyWaterStoreLevelSensor(Node node){
		myHolder.myGreyWaterStoreLevelSensors.add(GreyWaterStoreLevelSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchDirtyWaterInFlowRateSensor(Node node){
		myHolder.myDirtyWaterInFlowRateSensors.add(DirtyWaterInFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchDirtyWaterOutFlowRateSensor(Node node){
		myHolder.myDirtyWaterOutFlowRateSensors.add(DirtyWaterOutFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchDirtyWaterStoreLevelSensor(Node node){
		myHolder.myDirtyWaterStoreLevelSensors.add(DirtyWaterStoreLevelSensorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void crawlWaterSensors(Node node){
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
			child = child.getNextSibling();
		}
	}

	//Waste
	private static void fetchDryWasteInFlowRateSensor(Node node){
		myHolder.myDryWasteInFlowRateSensors.add(DryWasteInFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}

	private static void fetchDryWasteOutFlowRateSensor(Node node){
		myHolder.myDryWasteOutFlowRateSensors.add(DryWasteOutFlowRateSensorHelper.narrow(grabModule(getModuleName(node))));
	}

	private static void fetchDryWasteStoreLevelSensor(Node node){
		myHolder.myDryWasteStoreLevelSensors.add(DryWasteStoreLevelSensorHelper.narrow(grabModule(getModuleName(node))));
	}

	private static void crawlWasteSensors(Node node){
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
	private static void crawlActuators(Node node){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("air")){
				crawlAirActuators(child);
			}
			else if (childName.equals("environment")){
				crawlEnvironmentActuators(child);
			}
			else if (childName.equals("food")){
				crawlFoodActuators(child);
			}
			else if (childName.equals("power")){
				crawlPowerActuators(child);
			}
			else if (childName.equals("water")){
				crawlWaterActuators(child);
			}
			else if (childName.equals("waste")){
				crawlWasteActuators(child);
			}
			child = child.getNextSibling();
		}
	}

	//Air
	private static void fetchCO2InFlowRateActuator(Node node){
		myHolder.myCO2InFlowRateActuators.add(CO2InFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchCO2OutFlowRateActuator(Node node){
		myHolder.myCO2OutFlowRateActuators.add(CO2OutFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchO2InFlowRateActuator(Node node){
		myHolder.myO2InFlowRateActuators.add(O2InFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchO2OutFlowRateActuator(Node node){
		myHolder.myO2OutFlowRateActuators.add(O2OutFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchH2InFlowRateActuator(Node node){
		myHolder.myH2InFlowRateActuators.add(H2InFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchH2OutFlowRateActuator(Node node){
		myHolder.myH2OutFlowRateActuators.add(H2OutFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchNitrogenInFlowRateActuator(Node node){
		myHolder.myNitrogenInFlowRateActuators.add(NitrogenInFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchNitrogenOutFlowRateActuator(Node node){
		myHolder.myNitrogenOutFlowRateActuators.add(NitrogenOutFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void crawlAirActuators(Node node){
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
	private static void fetchAirInFlowRateActuator(Node node){
		myHolder.myAirInFlowRateActuators.add(AirInFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchAirOutFlowRateActuator(Node node){
		myHolder.myAirOutFlowRateActuators.add(AirOutFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchCO2AirEnvironmentInFlowRateActuator(Node node){
		myHolder.myCO2AirEnvironmentInFlowRateActuators.add(CO2AirEnvironmentInFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchCO2AirEnvironmentOutFlowRateActuator(Node node){
		myHolder.myCO2AirEnvironmentOutFlowRateActuators.add(CO2AirEnvironmentOutFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchCO2AirStoreInFlowRateActuator(Node node){
		myHolder.myCO2AirStoreInFlowRateActuators.add(CO2AirStoreInFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchCO2AirStoreOutFlowRateActuator(Node node){
		myHolder.myCO2AirStoreOutFlowRateActuators.add(CO2AirStoreOutFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchO2AirEnvironmentInFlowRateActuator(Node node){
		myHolder.myO2AirEnvironmentInFlowRateActuators.add(O2AirEnvironmentInFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchO2AirEnvironmentOutFlowRateActuator(Node node){
		myHolder.myO2AirEnvironmentOutFlowRateActuators.add(O2AirEnvironmentOutFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchO2AirStoreInFlowRateActuator(Node node){
		myHolder.myO2AirStoreInFlowRateActuators.add(O2AirStoreInFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchO2AirStoreOutFlowRateActuator(Node node){
		myHolder.myO2AirStoreOutFlowRateActuators.add(O2AirStoreOutFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchWaterAirEnvironmentInFlowRateActuator(Node node){
		myHolder.myWaterAirEnvironmentInFlowRateActuators.add(WaterAirEnvironmentInFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchWaterAirEnvironmentOutFlowRateActuator(Node node){
		myHolder.myWaterAirEnvironmentOutFlowRateActuators.add(WaterAirEnvironmentOutFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchWaterAirStoreInFlowRateActuator(Node node){
		myHolder.myWaterAirStoreInFlowRateActuators.add(WaterAirStoreInFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchWaterAirStoreOutFlowRateActuator(Node node){
		myHolder.myWaterAirStoreOutFlowRateActuators.add(WaterAirStoreOutFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchNitrogenAirEnvironmentInFlowRateActuator(Node node){
		myHolder.myNitrogenAirEnvironmentInFlowRateActuators.add(NitrogenAirEnvironmentInFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchNitrogenAirEnvironmentOutFlowRateActuator(Node node){
		myHolder.myNitrogenAirEnvironmentOutFlowRateActuators.add(NitrogenAirEnvironmentOutFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchNitrogenAirStoreInFlowRateActuator(Node node){
		myHolder.myNitrogenAirStoreInFlowRateActuators.add(NitrogenAirStoreInFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchNitrogenAirStoreOutFlowRateActuator(Node node){
		myHolder.myNitrogenAirStoreOutFlowRateActuators.add(NitrogenAirStoreOutFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void crawlEnvironmentActuators(Node node){
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
			else if (childName.equals("NitrogenAirEnvironmentInFlowRateActuator"))
				fetchNitrogenAirEnvironmentInFlowRateActuator(child);
			else if (childName.equals("NitrogenAirEnvironmentOutFlowRateActuator"))
				fetchNitrogenAirEnvironmentOutFlowRateActuator(child);
			else if (childName.equals("NitrogenAirStoreInFlowRateActuator"))
				fetchNitrogenAirStoreInFlowRateActuator(child);
			else if (childName.equals("NitrogenAirStoreOutFlowRateActuator"))
				fetchNitrogenAirStoreOutFlowRateActuator(child);
			child = child.getNextSibling();
		}
	}

	//Food
	private static void fetchBiomassInFlowRateActuator(Node node){
		myHolder.myBiomassInFlowRateActuators.add(BiomassInFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchBiomassOutFlowRateActuator(Node node){
		myHolder.myBiomassOutFlowRateActuators.add(BiomassOutFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchFoodInFlowRateActuator(Node node){
		myHolder.myFoodInFlowRateActuators.add(FoodInFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchFoodOutFlowRateActuator(Node node){
		myHolder.myFoodOutFlowRateActuators.add(FoodOutFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void crawlFoodActuators(Node node){
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
			child = child.getNextSibling();
		}
	}

	//Power
	private static void fetchPowerInFlowRateActuator(Node node){
		myHolder.myPowerInFlowRateActuators.add(PowerInFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchPowerOutFlowRateActuator(Node node){
		myHolder.myPowerOutFlowRateActuators.add(PowerOutFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void crawlPowerActuators(Node node){
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
	private static void fetchPotableWaterInFlowRateActuator(Node node){
		myHolder.myPotableWaterInFlowRateActuators.add(PotableWaterInFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchPotableWaterOutFlowRateActuator(Node node){
		myHolder.myPotableWaterOutFlowRateActuators.add(PotableWaterOutFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchGreyWaterInFlowRateActuator(Node node){
		myHolder.myGreyWaterInFlowRateActuators.add(GreyWaterInFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchGreyWaterOutFlowRateActuator(Node node){
		myHolder.myGreyWaterOutFlowRateActuators.add(GreyWaterOutFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchDirtyWaterInFlowRateActuator(Node node){
		myHolder.myDirtyWaterInFlowRateActuators.add(DirtyWaterInFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchDirtyWaterOutFlowRateActuator(Node node){
		myHolder.myDirtyWaterOutFlowRateActuators.add(DirtyWaterOutFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void crawlWaterActuators(Node node){
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
			child = child.getNextSibling();
		}
	}

	//Waste
	private static void fetchDryWasteInFlowRateActuator(Node node){
		myHolder.myDryWasteInFlowRateActuators.add(DryWasteInFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void fetchDryWasteOutFlowRateActuator(Node node){
		myHolder.myDryWasteOutFlowRateActuators.add(DryWasteOutFlowRateActuatorHelper.narrow(grabModule(getModuleName(node))));
	}
	private static void crawlWasteActuators(Node node){
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

	private static class Holder{
		//Upper Categories
		public List myModules;
		public List mySimModules;
		public List mySensors;
		public List myActuators;

		//Specific Modules
		//Simulation
		//Air
		public List myAirRSModules;
		public List myO2Stores;
		public List myCO2Stores;
		public List myH2Stores;
		public List myNitrogenStores;
		//Crew
		public List myCrewGroups;
		//Environment
		public List mySimEnvironments;
		//Food
		public List myFoodProcessors;
		public List myBiomassRSModules;
		public List myBiomassStores;
		public List myFoodStores;
		//Framework
		public List myAccumulators;
		public List myInjectors;
		//Power
		public List myPowerPSModules;
		public List myPowerStores;
		//Waste
		public List myIncinerators;
		public List myDryWasteStores;
		//Water
		public List myWaterRSModules;
		public List myPotableWaterStores;
		public List myGreyWaterStores;
		public List myDirtyWaterStores;
		//Sensors
		//Air
		public List myCO2InFlowRateSensors;
		public List myCO2OutFlowRateSensors;
		public List myCO2StoreLevelSensors;
		public List myO2InFlowRateSensors;
		public List myO2OutFlowRateSensors;
		public List myO2StoreLevelSensors;
		public List myH2InFlowRateSensors;
		public List myH2OutFlowRateSensors;
		public List myH2StoreLevelSensors;
		public List myNitrogenInFlowRateSensors;
		public List myNitrogenOutFlowRateSensors;
		public List myNitrogenStoreLevelSensors;
		//Crew
		public List myCrewGroupDeathSensors;
		public List myCrewGroupProductivitySensors;
		public List myCrewGroupAnyDeadSensors;
		//Environment
		public List myAirInFlowRateSensors;
		public List myAirOutFlowRateSensors;
		public List myCO2AirConcentrationSensors;
		public List myCO2AirPressureSensors;
		public List myCO2AirEnvironmentInFlowRateSensors;
		public List myCO2AirEnvironmentOutFlowRateSensors;
		public List myCO2AirStoreInFlowRateSensors;
		public List myCO2AirStoreOutFlowRateSensors;
		public List myO2AirConcentrationSensors;
		public List myO2AirPressureSensors;
		public List myO2AirEnvironmentInFlowRateSensors;
		public List myO2AirEnvironmentOutFlowRateSensors;
		public List myO2AirStoreInFlowRateSensors;
		public List myO2AirStoreOutFlowRateSensors;
		public List myNitrogenAirConcentrationSensors;
		public List myNitrogenAirPressureSensors;
		public List myNitrogenAirEnvironmentInFlowRateSensors;
		public List myNitrogenAirEnvironmentOutFlowRateSensors;
		public List myNitrogenAirStoreInFlowRateSensors;
		public List myNitrogenAirStoreOutFlowRateSensors;
		public List myWaterAirConcentrationSensors;
		public List myWaterAirPressureSensors;
		public List myWaterAirEnvironmentInFlowRateSensors;
		public List myWaterAirEnvironmentOutFlowRateSensors;
		public List myWaterAirStoreInFlowRateSensors;
		public List myWaterAirStoreOutFlowRateSensors;
		public List myOtherAirConcentrationSensors;
		public List myOtherAirPressureSensors;
		//Food
		public List myBiomassInFlowRateSensors;
		public List myBiomassOutFlowRateSensors;
		public List myBiomassStoreLevelSensors;
		public List myFoodInFlowRateSensors;
		public List myFoodOutFlowRateSensors;
		public List myFoodStoreLevelSensors;
		public List myHarvestSensors;
		//Framework
		public List myStoreOverflowSensors;
		public List myStoreLevelSensors;
		//Power
		public List myPowerInFlowRateSensors;
		public List myPowerOutFlowRateSensors;
		public List myPowerStoreLevelSensors;
		//Waste
		public List myDryWasteInFlowRateSensors;
		public List myDryWasteOutFlowRateSensors;
		public List myDryWasteStoreLevelSensors;
		//Water
		public List myPotableWaterInFlowRateSensors;
		public List myPotableWaterOutFlowRateSensors;
		public List myPotableWaterStoreLevelSensors;
		public List myGreyWaterInFlowRateSensors;
		public List myGreyWaterOutFlowRateSensors;
		public List myGreyWaterStoreLevelSensors;
		public List myDirtyWaterInFlowRateSensors;
		public List myDirtyWaterOutFlowRateSensors;
		public List myDirtyWaterStoreLevelSensors;
		//Actuators
		//Air
		public List myCO2InFlowRateActuators;
		public List myCO2OutFlowRateActuators;
		public List myO2InFlowRateActuators;
		public List myO2OutFlowRateActuators;
		public List myH2InFlowRateActuators;
		public List myH2OutFlowRateActuators;
		public List myNitrogenInFlowRateActuators;
		public List myNitrogenOutFlowRateActuators;
		//Environment
		public List myAirInFlowRateActuators;
		public List myAirOutFlowRateActuators;
		public List myCO2AirEnvironmentInFlowRateActuators;
		public List myCO2AirEnvironmentOutFlowRateActuators;
		public List myCO2AirStoreInFlowRateActuators;
		public List myCO2AirStoreOutFlowRateActuators;
		public List myO2AirEnvironmentInFlowRateActuators;
		public List myO2AirEnvironmentOutFlowRateActuators;
		public List myO2AirStoreInFlowRateActuators;
		public List myO2AirStoreOutFlowRateActuators;
		public List myNitrogenAirEnvironmentInFlowRateActuators;
		public List myNitrogenAirEnvironmentOutFlowRateActuators;
		public List myNitrogenAirStoreInFlowRateActuators;
		public List myNitrogenAirStoreOutFlowRateActuators;
		public List myWaterAirEnvironmentInFlowRateActuators;
		public List myWaterAirEnvironmentOutFlowRateActuators;
		public List myWaterAirStoreInFlowRateActuators;
		public List myWaterAirStoreOutFlowRateActuators;
		//Food
		public List myBiomassInFlowRateActuators;
		public List myBiomassOutFlowRateActuators;
		public List myFoodInFlowRateActuators;
		public List myFoodOutFlowRateActuators;
		//Power
		public List myPowerInFlowRateActuators;
		public List myPowerOutFlowRateActuators;
		//Waste
		public List myDryWasteInFlowRateActuators;
		public List myDryWasteOutFlowRateActuators;
		//Water
		public List myPotableWaterInFlowRateActuators;
		public List myPotableWaterOutFlowRateActuators;
		public List myGreyWaterInFlowRateActuators;
		public List myGreyWaterOutFlowRateActuators;
		public List myDirtyWaterInFlowRateActuators;
		public List myDirtyWaterOutFlowRateActuators;

		protected Holder(){
			//Upper Categories
			myModules = new Vector();
			mySimModules = new Vector();
			mySensors = new Vector();
			myActuators = new Vector();

			//Specific Modules
			//Simulation
			//Air
			myAirRSModules = new Vector();
			myO2Stores = new Vector();
			myCO2Stores = new Vector();
			myNitrogenStores = new Vector();
			myH2Stores = new Vector();
			//Crew
			myCrewGroups = new Vector();
			//Environment
			mySimEnvironments = new Vector();
			//Food
			myFoodProcessors = new Vector();
			myBiomassRSModules = new Vector();
			myBiomassStores = new Vector();
			myFoodStores = new Vector();
			//Framework
			myAccumulators = new Vector();
			myInjectors = new Vector();
			//Power
			myPowerPSModules = new Vector();
			myPowerStores = new Vector();
			//Waste
			myIncinerators = new Vector();
			myDryWasteStores = new Vector();
			//Water
			myWaterRSModules = new Vector();
			myPotableWaterStores = new Vector();
			myGreyWaterStores = new Vector();
			myDirtyWaterStores = new Vector();
			//Sensors
			//Air
			myCO2InFlowRateSensors = new Vector();
			myCO2OutFlowRateSensors = new Vector();
			myCO2StoreLevelSensors = new Vector();
			myO2InFlowRateSensors = new Vector();
			myO2OutFlowRateSensors = new Vector();
			myO2StoreLevelSensors = new Vector();
			myH2InFlowRateSensors = new Vector();
			myH2OutFlowRateSensors = new Vector();
			myH2StoreLevelSensors = new Vector();
			myNitrogenInFlowRateSensors = new Vector();
			myNitrogenOutFlowRateSensors = new Vector();
			myNitrogenStoreLevelSensors = new Vector();
			//Crew
			myCrewGroupDeathSensors = new Vector();
			myCrewGroupProductivitySensors = new Vector();
			myCrewGroupAnyDeadSensors = new Vector();
			//Environment
			myAirInFlowRateSensors = new Vector();
			myAirOutFlowRateSensors = new Vector();
			myCO2AirConcentrationSensors = new Vector();
			myCO2AirPressureSensors = new Vector();
			myCO2AirEnvironmentInFlowRateSensors = new Vector();
			myCO2AirEnvironmentOutFlowRateSensors = new Vector();
			myCO2AirStoreInFlowRateSensors = new Vector();
			myCO2AirStoreOutFlowRateSensors = new Vector();
			myO2AirConcentrationSensors = new Vector();
			myO2AirPressureSensors = new Vector();
			myO2AirEnvironmentInFlowRateSensors = new Vector();
			myO2AirEnvironmentOutFlowRateSensors = new Vector();
			myO2AirStoreInFlowRateSensors = new Vector();
			myO2AirStoreOutFlowRateSensors = new Vector();
			myNitrogenAirConcentrationSensors = new Vector();
			myNitrogenAirPressureSensors = new Vector();
			myNitrogenAirEnvironmentInFlowRateSensors = new Vector();
			myNitrogenAirEnvironmentOutFlowRateSensors = new Vector();
			myNitrogenAirStoreInFlowRateSensors = new Vector();
			myNitrogenAirStoreOutFlowRateSensors = new Vector();
			myWaterAirConcentrationSensors = new Vector();
			myWaterAirPressureSensors = new Vector();
			myWaterAirEnvironmentInFlowRateSensors = new Vector();
			myWaterAirEnvironmentOutFlowRateSensors = new Vector();
			myWaterAirStoreInFlowRateSensors = new Vector();
			myWaterAirStoreOutFlowRateSensors = new Vector();
			myOtherAirConcentrationSensors = new Vector();
			myOtherAirPressureSensors = new Vector();
			//Food
			myBiomassInFlowRateSensors = new Vector();
			myBiomassOutFlowRateSensors = new Vector();
			myBiomassStoreLevelSensors = new Vector();
			myFoodInFlowRateSensors = new Vector();
			myFoodOutFlowRateSensors = new Vector();
			myFoodStoreLevelSensors = new Vector();
			myHarvestSensors = new Vector();
			//Framework
			myStoreOverflowSensors = new Vector();
			myStoreLevelSensors = new Vector();
			//Power
			myPowerInFlowRateSensors = new Vector();
			myPowerOutFlowRateSensors = new Vector();
			myPowerStoreLevelSensors = new Vector();
			//Waste
			myDryWasteInFlowRateSensors = new Vector();
			myDryWasteOutFlowRateSensors = new Vector();
			myDryWasteStoreLevelSensors = new Vector();
			//Water
			myPotableWaterInFlowRateSensors = new Vector();
			myPotableWaterOutFlowRateSensors = new Vector();
			myPotableWaterStoreLevelSensors = new Vector();
			myGreyWaterInFlowRateSensors = new Vector();
			myGreyWaterOutFlowRateSensors = new Vector();
			myGreyWaterStoreLevelSensors = new Vector();
			myDirtyWaterInFlowRateSensors = new Vector();
			myDirtyWaterOutFlowRateSensors = new Vector();
			myDirtyWaterStoreLevelSensors = new Vector();
			//Actuators
			//Air
			myCO2InFlowRateActuators = new Vector();
			myCO2OutFlowRateActuators = new Vector();
			myO2InFlowRateActuators = new Vector();
			myO2OutFlowRateActuators = new Vector();
			myH2InFlowRateActuators = new Vector();
			myH2OutFlowRateActuators = new Vector();
			myNitrogenInFlowRateActuators = new Vector();
			myNitrogenOutFlowRateActuators = new Vector();
			//Environment
			myAirInFlowRateActuators = new Vector();
			myAirOutFlowRateActuators = new Vector();
			myCO2AirEnvironmentInFlowRateActuators = new Vector();
			myCO2AirEnvironmentOutFlowRateActuators = new Vector();
			myCO2AirStoreInFlowRateActuators = new Vector();
			myCO2AirStoreOutFlowRateActuators = new Vector();
			myO2AirEnvironmentInFlowRateActuators = new Vector();
			myO2AirEnvironmentOutFlowRateActuators = new Vector();
			myO2AirStoreInFlowRateActuators = new Vector();
			myO2AirStoreOutFlowRateActuators = new Vector();
			myNitrogenAirEnvironmentInFlowRateActuators = new Vector();
			myNitrogenAirEnvironmentOutFlowRateActuators = new Vector();
			myNitrogenAirStoreInFlowRateActuators = new Vector();
			myNitrogenAirStoreOutFlowRateActuators = new Vector();
			myWaterAirEnvironmentInFlowRateActuators = new Vector();
			myWaterAirEnvironmentOutFlowRateActuators = new Vector();
			myWaterAirStoreInFlowRateActuators = new Vector();
			myWaterAirStoreOutFlowRateActuators = new Vector();
			//Food
			myBiomassInFlowRateActuators = new Vector();
			myBiomassOutFlowRateActuators = new Vector();
			myFoodInFlowRateActuators = new Vector();
			myFoodOutFlowRateActuators = new Vector();
			//Power
			myPowerInFlowRateActuators = new Vector();
			myPowerOutFlowRateActuators = new Vector();
			//Waste
			myDryWasteInFlowRateActuators = new Vector();
			myDryWasteOutFlowRateActuators = new Vector();
			//Water
			myPotableWaterInFlowRateActuators = new Vector();
			myPotableWaterOutFlowRateActuators = new Vector();
			myGreyWaterInFlowRateActuators = new Vector();
			myGreyWaterOutFlowRateActuators = new Vector();
			myDirtyWaterInFlowRateActuators = new Vector();
			myDirtyWaterOutFlowRateActuators = new Vector();
		}
		
		protected void coallateLists(){
			//Specific Modules
			//Simulation
			//Air
			mySimModules.addAll(myAirRSModules);
			mySimModules.addAll(myO2Stores);
			mySimModules.addAll(myCO2Stores);
			mySimModules.addAll(myNitrogenStores);
			mySimModules.addAll(myH2Stores);
			//Crew
			mySimModules.addAll(myCrewGroups);
			//Environment
			mySimModules.addAll(mySimEnvironments);
			//Food
			mySimModules.addAll(myFoodProcessors);
			mySimModules.addAll(myBiomassRSModules);
			mySimModules.addAll(myBiomassStores);
			mySimModules.addAll(myFoodStores);
			//Framework
			mySimModules.addAll(myAccumulators);
			mySimModules.addAll(myInjectors);
			//Power
			mySimModules.addAll(myPowerPSModules);
			mySimModules.addAll(myPowerStores);
			//Waste
			mySimModules.addAll(myIncinerators);
			mySimModules.addAll(myDryWasteStores);
			//Water
			mySimModules.addAll(myWaterRSModules);
			mySimModules.addAll(myPotableWaterStores);
			mySimModules.addAll(myGreyWaterStores);
			mySimModules.addAll(myDirtyWaterStores);
			//Sensors
			//Air
			mySensors.addAll(myCO2InFlowRateSensors);
			mySensors.addAll(myCO2OutFlowRateSensors);
			mySensors.addAll(myCO2StoreLevelSensors);
			mySensors.addAll(myO2InFlowRateSensors);
			mySensors.addAll(myO2OutFlowRateSensors);
			mySensors.addAll(myO2StoreLevelSensors);
			mySensors.addAll(myH2InFlowRateSensors);
			mySensors.addAll(myH2OutFlowRateSensors);
			mySensors.addAll(myH2StoreLevelSensors);
			mySensors.addAll(myNitrogenInFlowRateSensors);
			mySensors.addAll(myNitrogenOutFlowRateSensors);
			mySensors.addAll(myNitrogenStoreLevelSensors);
			//Crew
			mySensors.addAll(myCrewGroupDeathSensors);
			mySensors.addAll(myCrewGroupProductivitySensors);
			mySensors.addAll(myCrewGroupAnyDeadSensors);
			//Environment
			mySensors.addAll(myAirInFlowRateSensors);
			mySensors.addAll(myAirOutFlowRateSensors);
			mySensors.addAll(myCO2AirConcentrationSensors);
			mySensors.addAll(myCO2AirPressureSensors);
			mySensors.addAll(myCO2AirEnvironmentInFlowRateSensors);
			mySensors.addAll(myCO2AirEnvironmentOutFlowRateSensors);
			mySensors.addAll(myCO2AirStoreInFlowRateSensors);
			mySensors.addAll(myCO2AirStoreOutFlowRateSensors);
			mySensors.addAll(myO2AirConcentrationSensors);
			mySensors.addAll(myO2AirPressureSensors);
			mySensors.addAll(myO2AirEnvironmentInFlowRateSensors);
			mySensors.addAll(myO2AirEnvironmentOutFlowRateSensors);
			mySensors.addAll(myO2AirStoreInFlowRateSensors);
			mySensors.addAll(myO2AirStoreOutFlowRateSensors);
			mySensors.addAll(myNitrogenAirConcentrationSensors);
			mySensors.addAll(myNitrogenAirPressureSensors);
			mySensors.addAll(myNitrogenAirEnvironmentInFlowRateSensors);
			mySensors.addAll(myNitrogenAirEnvironmentOutFlowRateSensors);
			mySensors.addAll(myNitrogenAirStoreInFlowRateSensors);
			mySensors.addAll(myNitrogenAirStoreOutFlowRateSensors);
			mySensors.addAll(myWaterAirConcentrationSensors);
			mySensors.addAll(myWaterAirPressureSensors);
			mySensors.addAll(myWaterAirEnvironmentInFlowRateSensors);
			mySensors.addAll(myWaterAirEnvironmentOutFlowRateSensors);
			mySensors.addAll(myWaterAirStoreInFlowRateSensors);
			mySensors.addAll(myWaterAirStoreOutFlowRateSensors);
			mySensors.addAll(myOtherAirConcentrationSensors);
			mySensors.addAll(myOtherAirPressureSensors);
			//Food
			mySensors.addAll(myBiomassInFlowRateSensors);
			mySensors.addAll(myBiomassOutFlowRateSensors);
			mySensors.addAll(myBiomassStoreLevelSensors);
			mySensors.addAll(myFoodInFlowRateSensors);
			mySensors.addAll(myFoodOutFlowRateSensors);
			mySensors.addAll(myFoodStoreLevelSensors);
			mySensors.addAll(myHarvestSensors);
			//Framework
			mySensors.addAll(myStoreOverflowSensors);
			mySensors.addAll(myStoreLevelSensors);
			//Power
			mySensors.addAll(myPowerInFlowRateSensors);
			mySensors.addAll(myPowerOutFlowRateSensors);
			mySensors.addAll(myPowerStoreLevelSensors);
			//Waste
			mySensors.addAll(myDryWasteInFlowRateSensors);
			mySensors.addAll(myDryWasteOutFlowRateSensors);
			mySensors.addAll(myDryWasteStoreLevelSensors);
			//Water
			mySensors.addAll(myPotableWaterInFlowRateSensors);
			mySensors.addAll(myPotableWaterOutFlowRateSensors);
			mySensors.addAll(myPotableWaterStoreLevelSensors);
			mySensors.addAll(myGreyWaterInFlowRateSensors);
			mySensors.addAll(myGreyWaterOutFlowRateSensors);
			mySensors.addAll(myGreyWaterStoreLevelSensors);
			mySensors.addAll(myDirtyWaterInFlowRateSensors);
			mySensors.addAll(myDirtyWaterOutFlowRateSensors);
			mySensors.addAll(myDirtyWaterStoreLevelSensors);
			//Actuators
			//Air
			myActuators.addAll(myCO2InFlowRateActuators);
			myActuators.addAll(myCO2OutFlowRateActuators);
			myActuators.addAll(myO2InFlowRateActuators);
			myActuators.addAll(myO2OutFlowRateActuators);
			myActuators.addAll(myH2InFlowRateActuators);
			myActuators.addAll(myH2OutFlowRateActuators);
			myActuators.addAll(myNitrogenInFlowRateActuators);
			myActuators.addAll(myNitrogenOutFlowRateActuators);
			//Environment
			myActuators.addAll(myAirInFlowRateActuators);
			myActuators.addAll(myAirOutFlowRateActuators);
			myActuators.addAll(myCO2AirEnvironmentInFlowRateActuators);
			myActuators.addAll(myCO2AirEnvironmentOutFlowRateActuators);
			myActuators.addAll(myCO2AirStoreInFlowRateActuators);
			myActuators.addAll(myCO2AirStoreOutFlowRateActuators);
			myActuators.addAll(myO2AirEnvironmentInFlowRateActuators);
			myActuators.addAll(myO2AirEnvironmentOutFlowRateActuators);
			myActuators.addAll(myO2AirStoreInFlowRateActuators);
			myActuators.addAll(myO2AirStoreOutFlowRateActuators);
			myActuators.addAll(myNitrogenAirEnvironmentInFlowRateActuators);
			myActuators.addAll(myNitrogenAirEnvironmentOutFlowRateActuators);
			myActuators.addAll(myNitrogenAirStoreInFlowRateActuators);
			myActuators.addAll(myNitrogenAirStoreOutFlowRateActuators);
			myActuators.addAll(myWaterAirEnvironmentInFlowRateActuators);
			myActuators.addAll(myWaterAirEnvironmentOutFlowRateActuators);
			myActuators.addAll(myWaterAirStoreInFlowRateActuators);
			myActuators.addAll(myWaterAirStoreOutFlowRateActuators);
			//Food
			myActuators.addAll(myBiomassInFlowRateActuators);
			myActuators.addAll(myBiomassOutFlowRateActuators);
			myActuators.addAll(myFoodInFlowRateActuators);
			myActuators.addAll(myFoodOutFlowRateActuators);
			//Power
			myActuators.addAll(myPowerInFlowRateActuators);
			myActuators.addAll(myPowerOutFlowRateActuators);
			//Waste
			myActuators.addAll(myDryWasteInFlowRateActuators);
			myActuators.addAll(myDryWasteOutFlowRateActuators);
			//Water
			myActuators.addAll(myPotableWaterInFlowRateActuators);
			myActuators.addAll(myPotableWaterOutFlowRateActuators);
			myActuators.addAll(myGreyWaterInFlowRateActuators);
			myActuators.addAll(myGreyWaterOutFlowRateActuators);
			myActuators.addAll(myDirtyWaterInFlowRateActuators);
			myActuators.addAll(myDirtyWaterOutFlowRateActuators);
			
			myModules.addAll(mySimModules);
			myModules.addAll(mySensors);
			myModules.addAll(myActuators);
		}

		protected void reset(){
			//Upper Categories
			myModules.clear();
			mySimModules.clear();
			mySensors.clear();
			myActuators.clear();

			//Specific Modules
			//Simulation
			//Air
			myAirRSModules.clear();
			myO2Stores.clear();
			myCO2Stores.clear();
			myNitrogenStores.clear();
			myH2Stores.clear();
			//Crew
			myCrewGroups.clear();
			//Environment
			mySimEnvironments.clear();
			//Food
			myFoodProcessors.clear();
			myBiomassRSModules.clear();
			myBiomassStores.clear();
			myFoodStores.clear();
			//Framework
			myAccumulators.clear();
			myInjectors.clear();
			//Power
			myPowerPSModules.clear();
			myPowerStores.clear();
			//Waste
			myIncinerators.clear();
			myDryWasteStores.clear();
			//Water
			myWaterRSModules.clear();
			myPotableWaterStores.clear();
			myGreyWaterStores.clear();
			myDirtyWaterStores.clear();
			//Sensors
			//Air
			myCO2InFlowRateSensors.clear();
			myCO2OutFlowRateSensors.clear();
			myCO2StoreLevelSensors.clear();
			myO2InFlowRateSensors.clear();
			myO2OutFlowRateSensors.clear();
			myO2StoreLevelSensors.clear();
			myH2InFlowRateSensors.clear();
			myH2OutFlowRateSensors.clear();
			myH2StoreLevelSensors.clear();
			myNitrogenInFlowRateSensors.clear();
			myNitrogenOutFlowRateSensors.clear();
			myNitrogenStoreLevelSensors.clear();
			//Crew
			myCrewGroupDeathSensors.clear();
			myCrewGroupProductivitySensors.clear();
			myCrewGroupAnyDeadSensors.clear();
			//Environment
			myAirInFlowRateSensors.clear();
			myAirOutFlowRateSensors.clear();
			myCO2AirConcentrationSensors.clear();
			myCO2AirPressureSensors.clear();
			myCO2AirEnvironmentInFlowRateSensors.clear();
			myCO2AirEnvironmentOutFlowRateSensors.clear();
			myCO2AirStoreInFlowRateSensors.clear();
			myCO2AirStoreOutFlowRateSensors.clear();
			myO2AirConcentrationSensors.clear();
			myO2AirPressureSensors.clear();
			myO2AirEnvironmentInFlowRateSensors.clear();
			myO2AirEnvironmentOutFlowRateSensors.clear();
			myO2AirStoreInFlowRateSensors.clear();
			myO2AirStoreOutFlowRateSensors.clear();
			myNitrogenAirConcentrationSensors.clear();
			myNitrogenAirPressureSensors.clear();
			myNitrogenAirEnvironmentInFlowRateSensors.clear();
			myNitrogenAirEnvironmentOutFlowRateSensors.clear();
			myNitrogenAirStoreInFlowRateSensors.clear();
			myNitrogenAirStoreOutFlowRateSensors.clear();
			myWaterAirConcentrationSensors.clear();
			myWaterAirPressureSensors.clear();
			myWaterAirEnvironmentInFlowRateSensors.clear();
			myWaterAirEnvironmentOutFlowRateSensors.clear();
			myWaterAirStoreInFlowRateSensors.clear();
			myWaterAirStoreOutFlowRateSensors.clear();
			myOtherAirConcentrationSensors.clear();
			myOtherAirPressureSensors.clear();
			//Food
			myBiomassInFlowRateSensors.clear();
			myBiomassOutFlowRateSensors.clear();
			myBiomassStoreLevelSensors.clear();
			myFoodInFlowRateSensors.clear();
			myFoodOutFlowRateSensors.clear();
			myFoodStoreLevelSensors.clear();
			myHarvestSensors.clear();
			//Framework
			myStoreOverflowSensors.clear();
			myStoreLevelSensors.clear();
			//Power
			myPowerInFlowRateSensors.clear();
			myPowerOutFlowRateSensors.clear();
			myPowerStoreLevelSensors.clear();
			//Waste
			myDryWasteInFlowRateSensors.clear();
			myDryWasteOutFlowRateSensors.clear();
			myDryWasteStoreLevelSensors.clear();
			//Water
			myPotableWaterInFlowRateSensors.clear();
			myPotableWaterOutFlowRateSensors.clear();
			myPotableWaterStoreLevelSensors.clear();
			myGreyWaterInFlowRateSensors.clear();
			myGreyWaterOutFlowRateSensors.clear();
			myGreyWaterStoreLevelSensors.clear();
			myDirtyWaterInFlowRateSensors.clear();
			myDirtyWaterOutFlowRateSensors.clear();
			myDirtyWaterStoreLevelSensors.clear();
			//Actuators
			//Air
			myCO2InFlowRateActuators.clear();
			myCO2OutFlowRateActuators.clear();
			myO2InFlowRateActuators.clear();
			myO2OutFlowRateActuators.clear();
			myH2InFlowRateActuators.clear();
			myH2OutFlowRateActuators.clear();
			myNitrogenInFlowRateActuators.clear();
			myNitrogenOutFlowRateActuators.clear();
			//Environment
			myAirInFlowRateActuators.clear();
			myAirOutFlowRateActuators.clear();
			myCO2AirEnvironmentInFlowRateActuators.clear();
			myCO2AirEnvironmentOutFlowRateActuators.clear();
			myCO2AirStoreInFlowRateActuators.clear();
			myCO2AirStoreOutFlowRateActuators.clear();
			myO2AirEnvironmentInFlowRateActuators.clear();
			myO2AirEnvironmentOutFlowRateActuators.clear();
			myO2AirStoreInFlowRateActuators.clear();
			myO2AirStoreOutFlowRateActuators.clear();
			myNitrogenAirEnvironmentInFlowRateActuators.clear();
			myNitrogenAirEnvironmentOutFlowRateActuators.clear();
			myNitrogenAirStoreInFlowRateActuators.clear();
			myNitrogenAirStoreOutFlowRateActuators.clear();
			myWaterAirEnvironmentInFlowRateActuators.clear();
			myWaterAirEnvironmentOutFlowRateActuators.clear();
			myWaterAirStoreInFlowRateActuators.clear();
			myWaterAirStoreOutFlowRateActuators.clear();
			//Food
			myBiomassInFlowRateActuators.clear();
			myBiomassOutFlowRateActuators.clear();
			myFoodInFlowRateActuators.clear();
			myFoodOutFlowRateActuators.clear();
			//Power
			myPowerInFlowRateActuators.clear();
			myPowerOutFlowRateActuators.clear();
			//Waste
			myDryWasteInFlowRateActuators.clear();
			myDryWasteOutFlowRateActuators.clear();
			//Water
			myPotableWaterInFlowRateActuators.clear();
			myPotableWaterOutFlowRateActuators.clear();
			myGreyWaterInFlowRateActuators.clear();
			myGreyWaterOutFlowRateActuators.clear();
			myDirtyWaterInFlowRateActuators.clear();
			myDirtyWaterOutFlowRateActuators.clear();
		}
	}
}
