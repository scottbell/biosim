package biosim.server.framework;

import java.net.*;
import org.w3c.dom.*;
import org.apache.xerces.parsers.*;
import org.xml.sax.*;

/**
 * Reads BioSim configuration from XML file.
 *
 * @author Scott Bell
 */
public class BioInitializer{
	/** Namespaces feature id (http://xml.org/sax/features/namespaces). */
	private static final String NAMESPACES_FEATURE_ID = "http://xml.org/sax/features/namespaces";
	/** Validation feature id (http://xml.org/sax/features/validation). */
	private static final String VALIDATION_FEATURE_ID = "http://xml.org/sax/features/validation";
	/** Schema validation feature id (http://apache.org/xml/features/validation/schema). */
	private static final String SCHEMA_VALIDATION_FEATURE_ID = "http://apache.org/xml/features/validation/schema";
	/** Schema full checking feature id (http://apache.org/xml/features/validation/schema-full-checking). */
	private static final String SCHEMA_FULL_CHECKING_FEATURE_ID = "http://apache.org/xml/features/validation/schema-full-checking";


	// default settings
	/** Default namespaces support (true). */
	private static final boolean DEFAULT_NAMESPACES = true;
	/** Default validation support (false). */
	private static final boolean DEFAULT_VALIDATION = true;
	/** Default Schema validation support (false). */
	private static final boolean DEFAULT_SCHEMA_VALIDATION = true;
	/** Default Schema full checking support (false). */
	private static final boolean DEFAULT_SCHEMA_FULL_CHECKING = true;

	private static DOMParser myParser = null;
	private static BioInitializer myInitializer = null;

	/** Default constructor. */
	private BioInitializer(){
		try {
			myParser = new DOMParser();
			myParser.setFeature(SCHEMA_VALIDATION_FEATURE_ID, DEFAULT_SCHEMA_VALIDATION);
			myParser.setFeature(SCHEMA_FULL_CHECKING_FEATURE_ID, DEFAULT_SCHEMA_FULL_CHECKING);
			myParser.setFeature(VALIDATION_FEATURE_ID, DEFAULT_VALIDATION);
			myParser.setFeature(NAMESPACES_FEATURE_ID, DEFAULT_NAMESPACES);
		}
		catch (SAXException e) {
			System.err.println("warning: Parser does not support feature ("+NAMESPACES_FEATURE_ID+")");
		}
	}

	//Globals
	private static void crawlGlobals(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	//Modules
	private static void createAirRS(Node node){
		System.out.println("Creating AirRS");
	}

	private static void configureAirRS(Node node){
		System.out.println("Configuring AirRS");
	}

	private static void createO2Store(Node node){
		System.out.println("Creating O2Store");
	}

	private static void createCO2Store(Node node){
		System.out.println("Creating CO2Store");
	}

	private static void createH2Store(Node node){
		System.out.println("Creating H2Store");
	}

	private static void crawlAirModules(Node node, boolean firstPass){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("AirRS")){
				if (firstPass)
					createAirRS(child);
				else
					configureAirRS(child);

			}
			else if (childName.equals("O2Store")){
				if (firstPass)
					createO2Store(child);

			}
			else if (childName.equals("CO2Store")){
				if (firstPass)
					createCO2Store(child);

			}
			else if (childName.equals("H2Store")){
				if (firstPass)
					createH2Store(child);

			}
			child = child.getNextSibling();
		}
	}

	private static void createCrewGroup(Node node){
		System.out.println("Creating CrewGroup");
	}

	private static void configureCrewGroup(Node node){
		System.out.println("Configuring CrewGroup");
	}

	private static void crawlCrewModules(Node node, boolean firstPass){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("CrewGroup")){
				if (firstPass)
					createCrewGroup(child);
				else
					configureCrewGroup(child);

			}
			child = child.getNextSibling();
		}
	}

	private static void createSimEnvironment(Node node){
		System.out.println("Creating SimEnvironment");
	}

	private static void crawlEnvironmentModules(Node node, boolean firstPass){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("SimEnvironment")){
				if (firstPass)
					createSimEnvironment(child);
			}
			child = child.getNextSibling();
		}
	}

	private static void createActuator(Node node){
		System.out.println("Creating Actuator");
	}

	private static void configureActuator(Node node){
		System.out.println("Configuring Actuator");
	}

	private static void createInjector(Node node){
		System.out.println("Creating Injector");
	}

	private static void configureInjector(Node node){
		System.out.println("Configuring Injector");
	}

	private static void crawlFrameworkModules(Node node, boolean firstPass){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("Actuator")){
				if (firstPass)
					createActuator(child);
				else
					configureActuator(child);
			}
			else if (childName.equals("Injector")){
				if (firstPass)
					createInjector(child);
				else
					configureInjector(child);
			}
			child = child.getNextSibling();
		}
	}
	
	private static void createBiomassRS(Node node){
		System.out.println("Creating BiomassRS");
	}
	private static void configureBiomassRS(Node node){
		System.out.println("Configuring BiomassRS");
	}
	private static void createFoodProcessor(Node node){
		System.out.println("Creating FoodProcessor");
	}
	private static void configureFoodProcessor(Node node){
		System.out.println("Configuring FoodProcessor");
	}
	private static void createBiomassStore(Node node){
		System.out.println("Creating BiomassStore");
	}
	private static void createFoodStore(Node node){
		System.out.println("Creating FoodStore");
	}

	private static void crawlFoodModules(Node node, boolean firstPass){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("BiomassRS")){
				if (firstPass)
					createBiomassRS(child);
				else
					configureBiomassRS(child);
			}
			else if (childName.equals("FoodProcessor")){
				if (firstPass)
					createFoodProcessor(child);
				else
					configureFoodProcessor(child);
			}
			else if (childName.equals("BiomassStore")){
				if (firstPass)
					createBiomassStore(child);
			}
			else if (childName.equals("FoodStore")){
				if (firstPass)
					createFoodStore(child);
			}
			child = child.getNextSibling();
		}
	}
	
	private static void createPowerPS(Node node){
		System.out.println("Creating PowerPS");
	}

	private static void configurePowerPS(Node node){
		System.out.println("Configuring PowerPS");
	}
	
	private static void createPowerStore(Node node){
		System.out.println("Creating PowerStore");
	}
	
	private static void crawlPowerModules(Node node, boolean firstPass){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("PowerPS")){
				if (firstPass)
					createPowerPS(child);
				else
					configurePowerPS(child);
			}
			else if (childName.equals("PowerStore")){
				if (firstPass)
					createPowerStore(child);
			}
			child = child.getNextSibling();
		}
	}
	
	private static void createWaterRS(Node node){
		System.out.println("Creating WaterRS");
	}

	private static void configureWaterRS(Node node){
		System.out.println("Configuring WaterRS");
	}
	
	private static void createPotableWaterStore(Node node){
		System.out.println("Creating PotableWaterStore");
	}
	
	private static void createGreyWaterStore(Node node){
		System.out.println("Creating GreyWaterStore");
	}
	
	private static void createDirtyWaterStore(Node node){
		System.out.println("Creating DirtyWaterStore");
	}
	
	private static void crawlWaterModules(Node node, boolean firstPass){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("WaterRS")){
				if (firstPass)
					createWaterRS(child);
				else
					configureWaterRS(child);
			}
			else if (childName.equals("PotableWaterStore")){
				if (firstPass)
					createPotableWaterStore(child);
			}
			else if (childName.equals("GreyWaterStore")){
				if (firstPass)
					createGreyWaterStore(child);
			}
			else if (childName.equals("DirtyWaterStore")){
				if (firstPass)
					createDirtyWaterStore(child);
			}
			child = child.getNextSibling();
		}
	}

	//Sensors
	private static void crawlAirSensors(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private static void crawlCrewSensors(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private static void crawlEnvironmentSensors(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private static void crawlFrameworkSensors(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private static void crawlFoodSensors(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private static void crawlPowerSensors(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private static void crawlWaterSensors(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	//Actuators
	private static void crawlAirActuators(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private static void crawlCrewActuators(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private static void crawlEnvironmentActuators(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private static void crawlFrameworkActuators(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private static void crawlFoodActuators(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private static void crawlPowerActuators(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private static void crawlWaterActuators(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private static void crawlModules(Node node, boolean firstPass){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("air")){
				crawlAirModules(child, firstPass);

			}
			else if (childName.equals("crew")){
				crawlCrewModules(child, firstPass);

			}
			else if (childName.equals("environment")){
				crawlEnvironmentModules(child, firstPass);

			}
			else if (childName.equals("food")){
				crawlFoodModules(child, firstPass);

			}
			else if (childName.equals("framework")){
				crawlFrameworkModules(child, firstPass);

			}
			else if (childName.equals("power")){
				crawlPowerModules(child, firstPass);

			}
			else if (childName.equals("water")){
				crawlWaterModules(child, firstPass);

			}
			child = child.getNextSibling();
		}
	}

	private static void crawlSensors(Node node, boolean firstPass){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("air")){
				crawlAirSensors(child, firstPass);

			}
			else if (childName.equals("crew")){
				crawlCrewSensors(child, firstPass);

			}
			else if (childName.equals("environment")){
				crawlEnvironmentSensors(child, firstPass);

			}
			else if (childName.equals("food")){
				crawlFoodSensors(child, firstPass);

			}
			else if (childName.equals("framework")){
				crawlFrameworkSensors(child, firstPass);

			}
			else if (childName.equals("power")){
				crawlPowerSensors(child, firstPass);

			}
			else if (childName.equals("water")){
				crawlWaterSensors(child, firstPass);

			}
			child = child.getNextSibling();
		}
	}

	private static void crawlActuators(Node node, boolean firstPass){
		Node child = node.getFirstChild();
		while (child != null) {
			String childName = child.getNodeName();
			if (childName.equals("air")){
				crawlAirActuators(child, firstPass);
			}
			else if (childName.equals("crew")){
				crawlCrewActuators(child, firstPass);

			}
			else if (childName.equals("environment")){
				crawlEnvironmentActuators(child, firstPass);

			}
			else if (childName.equals("food")){
				crawlFoodActuators(child, firstPass);

			}
			else if (childName.equals("framework")){
				crawlFrameworkActuators(child, firstPass);

			}
			else if (childName.equals("power")){
				crawlPowerActuators(child, firstPass);

			}
			else if (childName.equals("water")){
				crawlWaterActuators(child, firstPass);

			}
			child = child.getNextSibling();
		}
	}

	/** Traverses the specified node, recursively. */
	private static void crawlBiosim(Node node, boolean firstPass) {
		// is there anything to do?
		if (node == null)
			return;
		String nodeName = node.getNodeName();
		if (nodeName.equals("Globals")){
			crawlGlobals(node, firstPass);
			return;
		}
		else if (nodeName.equals("SimModules")){
			crawlModules(node, firstPass);
			return;
		}
		else if (nodeName.equals("Sensors")){
			crawlSensors(node, firstPass);
			return;
		}
		else if (nodeName.equals("Actuators")){
			crawlActuators(node, firstPass);
			return;
		}
		else{
			Node child = node.getFirstChild();
			while (child != null) {
				crawlBiosim(child, firstPass);
				child = child.getNextSibling();
			}
		}

	}

	private static void parseFile(String fileToParse){
		if (myInitializer == null)
			myInitializer = new BioInitializer();
		try{
			System.out.println("Starting to parse file: "+fileToParse);
			myParser.parse(fileToParse);
			Document document = myParser.getDocument();
			crawlBiosim(document, true);
			crawlBiosim(document, false);
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
	}

	/** Main program entry point. */
	public static void main(String argv[]) {
		URL documentUrl = ClassLoader.getSystemClassLoader().getResource("biosim/server/framework/DefaultInitialization.xml");
		String documentString = documentUrl.toString();
		if (documentString.length() > 0)
			BioInitializer.parseFile(documentString);
	}
}
