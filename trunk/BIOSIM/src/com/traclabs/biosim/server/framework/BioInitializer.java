package biosim.server.framework;

import java.net.*;
import org.w3c.dom.*;
import org.apache.xerces.parsers.*;
import org.xml.sax.*;

import biosim.idl.simulation.air.*;
import biosim.idl.simulation.crew.*;
import biosim.idl.simulation.food.*;
import biosim.idl.simulation.water.*;
import biosim.idl.simulation.power.*;
import biosim.idl.simulation.environment.*;
import biosim.idl.simulation.framework.*;
import biosim.server.simulation.air.*;
import biosim.server.simulation.crew.*;
import biosim.server.simulation.food.*;
import biosim.server.simulation.water.*;
import biosim.server.simulation.power.*;
import biosim.server.simulation.environment.*;
import biosim.server.simulation.framework.*;
import biosim.idl.sensor.air.*;
import biosim.idl.sensor.food.*;
import biosim.idl.sensor.water.*;
import biosim.idl.sensor.power.*;
import biosim.idl.sensor.crew.*;
import biosim.idl.sensor.environment.*;
import biosim.idl.sensor.framework.*;
import biosim.server.sensor.air.*;
import biosim.server.sensor.food.*;
import biosim.server.sensor.water.*;
import biosim.server.sensor.power.*;
import biosim.server.sensor.crew.*;
import biosim.server.sensor.environment.*;
import biosim.server.sensor.framework.*;
import biosim.idl.actuator.air.*;
import biosim.idl.actuator.food.*;
import biosim.idl.actuator.water.*;
import biosim.idl.actuator.power.*;
import biosim.idl.actuator.crew.*;
import biosim.idl.actuator.environment.*;
import biosim.idl.actuator.framework.*;
import biosim.server.actuator.air.*;
import biosim.server.actuator.food.*;
import biosim.server.actuator.water.*;
import biosim.server.actuator.power.*;
import biosim.server.actuator.environment.*;
import biosim.server.actuator.framework.*;
import biosim.idl.util.log.*;
import biosim.server.util.*;
import biosim.server.util.log.*;

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

	private DOMParser myParser = null;
	private int myID = 0;

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
	private void crawlGlobals(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	//Modules
	private void createAirRS(Node node){
		System.out.println("Creating AirRS");
		AirRSImpl myAirRSImpl = new AirRSImpl(myID);
		//registerServer(new AirRSPOATie(myAirRSImpl), myAirRSImpl.getModuleName());
	}

	private void configureAirRS(Node node){
		System.out.println("Configuring AirRS");
	}

	private void createO2Store(Node node){
		System.out.println("Creating O2Store");
	}

	private void createCO2Store(Node node){
		System.out.println("Creating CO2Store");
	}

	private void createH2Store(Node node){
		System.out.println("Creating H2Store");
	}

	private void crawlAirModules(Node node, boolean firstPass){
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

	private void createCrewGroup(Node node){
		System.out.println("Creating CrewGroup");
	}

	private void configureCrewGroup(Node node){
		System.out.println("Configuring CrewGroup");
	}

	private void crawlCrewModules(Node node, boolean firstPass){
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

	private void createSimEnvironment(Node node){
		System.out.println("Creating SimEnvironment");
	}

	private void crawlEnvironmentModules(Node node, boolean firstPass){
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

	private void createActuator(Node node){
		System.out.println("Creating Actuator");
	}

	private void configureActuator(Node node){
		System.out.println("Configuring Actuator");
	}

	private void createInjector(Node node){
		System.out.println("Creating Injector");
	}

	private void configureInjector(Node node){
		System.out.println("Configuring Injector");
	}

	private void crawlFrameworkModules(Node node, boolean firstPass){
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
	
	private void createBiomassRS(Node node){
		System.out.println("Creating BiomassRS");
	}
	private void configureBiomassRS(Node node){
		System.out.println("Configuring BiomassRS");
	}
	private void createFoodProcessor(Node node){
		System.out.println("Creating FoodProcessor");
	}
	private void configureFoodProcessor(Node node){
		System.out.println("Configuring FoodProcessor");
	}
	private void createBiomassStore(Node node){
		System.out.println("Creating BiomassStore");
	}
	private void createFoodStore(Node node){
		System.out.println("Creating FoodStore");
	}

	private void crawlFoodModules(Node node, boolean firstPass){
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
	
	private void createPowerPS(Node node){
		System.out.println("Creating PowerPS");
	}

	private void configurePowerPS(Node node){
		System.out.println("Configuring PowerPS");
	}
	
	private void createPowerStore(Node node){
		System.out.println("Creating PowerStore");
	}
	
	private void crawlPowerModules(Node node, boolean firstPass){
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
	
	private void createWaterRS(Node node){
		System.out.println("Creating WaterRS");
	}

	private void configureWaterRS(Node node){
		System.out.println("Configuring WaterRS");
	}
	
	private void createPotableWaterStore(Node node){
		System.out.println("Creating PotableWaterStore");
	}
	
	private void createGreyWaterStore(Node node){
		System.out.println("Creating GreyWaterStore");
	}
	
	private void createDirtyWaterStore(Node node){
		System.out.println("Creating DirtyWaterStore");
	}
	
	private void crawlWaterModules(Node node, boolean firstPass){
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
	private void crawlAirSensors(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private void crawlCrewSensors(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private void crawlEnvironmentSensors(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private void crawlFrameworkSensors(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private void crawlFoodSensors(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private void crawlPowerSensors(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private void crawlWaterSensors(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	//Actuators
	private void crawlAirActuators(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private void crawlCrewActuators(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private void crawlEnvironmentActuators(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private void crawlFrameworkActuators(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private void crawlFoodActuators(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private void crawlPowerActuators(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private void crawlWaterActuators(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}

	private void crawlModules(Node node, boolean firstPass){
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

	private void crawlSensors(Node node, boolean firstPass){
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

	private void crawlActuators(Node node, boolean firstPass){
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
	private void crawlBiosim(Node node, boolean firstPass) {
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

	private void parseFile(String fileToParse){
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
		BioInitializer myInitializer = new BioInitializer();
		String documentString = documentUrl.toString();
		if (documentString.length() > 0)
			myInitializer.parseFile(documentString);
	}
}
