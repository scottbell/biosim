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

	private static void crawlGlobals(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}
	
	//Modules
	private static void crawlAirModules(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}
	
	private static void crawlCrewModules(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}
	
	private static void crawlEnvironmentModules(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}  
	
	private static void crawlFrameworkModules(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	} 
	
	private static void crawlFoodModules(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}
	
	private static void crawlPowerModules(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
	}
	
	private static void crawlWaterModules(Node node, boolean firstPass){
		System.out.println(node.getNodeName());
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
	
	private static void crawlSimModules(Node node, boolean firstPass){
		String nodeName = node.getNodeName();
		if (nodeName.equals("air")){
			crawlAirModules(node, firstPass);
			return;
		}
		else if (nodeName.equals("crew")){
			crawlCrewModules(node, firstPass);
			return;
		}
		else if (nodeName.equals("environment")){
			crawlEnvironmentModules(node, firstPass);
			return;
		}
		else if (nodeName.equals("food")){
			crawlFoodModules(node, firstPass);
			return;
		}
		else if (nodeName.equals("framework")){
			crawlFrameworkModules(node, firstPass);
			return;
		}
		else if (nodeName.equals("power")){
			crawlPowerModules(node, firstPass);
			return;
		}
		else if (nodeName.equals("water")){
			crawlWaterModules(node, firstPass);
			return;
		}
	}

	private static void crawlSensors(Node node, boolean firstPass){
		String nodeName = node.getNodeName();
		if (nodeName.equals("air")){
			crawlAirSensors(node, firstPass);
			return;
		}
		else if (nodeName.equals("crew")){
			crawlCrewSensors(node, firstPass);
			return;
		}
		else if (nodeName.equals("environment")){
			crawlEnvironmentSensors(node, firstPass);
			return;
		}
		else if (nodeName.equals("food")){
			crawlFoodSensors(node, firstPass);
			return;
		}
		else if (nodeName.equals("framework")){
			crawlFrameworkSensors(node, firstPass);
			return;
		}
		else if (nodeName.equals("power")){
			crawlPowerSensors(node, firstPass);
			return;
		}
		else if (nodeName.equals("water")){
			crawlWaterSensors(node, firstPass);
		}	
	}

	private static void crawlActuators(Node node, boolean firstPass){
		String nodeName = node.getNodeName();
		if (nodeName.equals("air")){
			crawlAirActuators(node, firstPass);
			return;
		}
		else if (nodeName.equals("crew")){
			crawlCrewActuators(node, firstPass);
			return;
		}
		else if (nodeName.equals("environment")){
			crawlEnvironmentActuators(node, firstPass);
			return;
		}
		else if (nodeName.equals("food")){
			crawlFoodActuators(node, firstPass);
			return;
		}
		else if (nodeName.equals("framework")){
			crawlFrameworkActuators(node, firstPass);
			return;
		}
		else if (nodeName.equals("power")){
			crawlPowerActuators(node, firstPass);
			return;
		}
		else if (nodeName.equals("water")){
			crawlWaterActuators(node, firstPass);
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
			crawlSimModules(node, firstPass);
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
