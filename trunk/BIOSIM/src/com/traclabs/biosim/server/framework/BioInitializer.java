package biosim.server.framework;

import java.net.*;
import java.util.*;
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

	private DOMParser myParser = null;
	private int myID = 0;

	/** Default constructor. */
	public BioInitializer(int pID){
		myID = pID;
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

	private static boolean isCreatedLocally(Node node){
		return node.getAttributes().getNamedItem("createLocally").getNodeValue().equals("true");
	}

	private static String getModuleName(Node node){
		return node.getAttributes().getNamedItem("name").getNodeValue();
	}

	private static void printRemoteWarningMessage(String pName){
		System.out.println("Instance of the module moduleNamed "+pName+" should be created remotely (if not already done)");
	}

	private static float getStoreLevel(Node node){
		float level = 0f;
		try{
			level = Float.parseFloat(node.getAttributes().getNamedItem("level").getNodeValue());
		}
		catch (NumberFormatException e){
			System.out.println("Had problems parsing a float...");
			e.printStackTrace();
		}
		return level;
	}

	private static float getStoreCapacity(Node node){
		float capacity = 0f;
		try{
			capacity = Float.parseFloat(node.getAttributes().getNamedItem("capacity").getNodeValue());
		}
		catch (NumberFormatException e){
			System.out.println("Had problems parsing a float...");
			e.printStackTrace();
		}
		return capacity;
	}

	private static float[] getMaxFlowRates(Node node){
		String arrayString = node.getAttributes().getNamedItem("maxFlowRates").getNodeValue();
		StringTokenizer tokenizer = new StringTokenizer(arrayString);
		float[] maxFlowRates = new float[tokenizer.countTokens()];

		for (int i = 0; tokenizer.hasMoreTokens(); i++){
			try{
				maxFlowRates[i] = Float.parseFloat(tokenizer.nextToken());
			}
			catch (NumberFormatException e){
				System.out.println("Had problems parsing a float...");
				e.printStackTrace();
			}
		}
		return maxFlowRates;
	}

	private static float[] getDesiredFlowRates(Node node){
		String arrayString = node.getAttributes().getNamedItem("desiredFlowRates").getNodeValue();
		StringTokenizer tokenizer = new StringTokenizer(arrayString);
		float[] desiredFlowRates = new float[tokenizer.countTokens()];

		for (int i = 0; tokenizer.hasMoreTokens(); i++){
			try{
				desiredFlowRates[i] = Float.parseFloat(tokenizer.nextToken());
			}
			catch (NumberFormatException e){
				System.out.println("Had problems parsing a float...");
				e.printStackTrace();
			}
		}
		return desiredFlowRates;
	}

	private static String getInput(Node node){
		return node.getAttributes().getNamedItem("input").getNodeValue();
	}

	private static String getOutput(Node node){
		return node.getAttributes().getNamedItem("output").getNodeValue();
	}

	//Modules
	private void createAirRS(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			System.out.println("Creating AirRS with moduleName: "+moduleName);
			AirRSImpl myAirRSImpl = new AirRSImpl(myID, moduleName);
			BiosimServer.registerServer(new AirRSPOATie(myAirRSImpl), myAirRSImpl.getModuleName(), myAirRSImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureAirRS(Node node){
		System.out.println("Configuring AirRS");
		String moduleName = getModuleName(node);
		try{
			AirRS myAirRS = AirRSHelper.narrow(OrbUtils.getNamingContext(myID).resolve_str(moduleName));
		}
		catch(org.omg.CORBA.UserException e){
			e.printStackTrace();
		}
	}

	private void createO2Store(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			System.out.println("Creating O2Store with moduleName: "+moduleName);
			O2StoreImpl myO2StoreImpl = new O2StoreImpl(myID, moduleName);
			myO2StoreImpl.setLevel(getStoreLevel(node));
			myO2StoreImpl.setCapacity(getStoreCapacity(node));
			BiosimServer.registerServer(new O2StorePOATie(myO2StoreImpl), myO2StoreImpl.getModuleName(), myO2StoreImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void createCO2Store(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			System.out.println("Creating CO2Store with moduleName: "+moduleName);
			CO2StoreImpl myCO2StoreImpl = new CO2StoreImpl(myID, moduleName);
			myCO2StoreImpl.setLevel(getStoreLevel(node));
			myCO2StoreImpl.setCapacity(getStoreCapacity(node));
			BiosimServer.registerServer(new CO2StorePOATie(myCO2StoreImpl), myCO2StoreImpl.getModuleName(), myCO2StoreImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void createH2Store(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			System.out.println("Creating H2Store with moduleName: "+moduleName);
			H2StoreImpl myH2StoreImpl = new H2StoreImpl(myID, moduleName);
			myH2StoreImpl.setLevel(getStoreLevel(node));
			myH2StoreImpl.setCapacity(getStoreCapacity(node));
			BiosimServer.registerServer(new H2StorePOATie(myH2StoreImpl), myH2StoreImpl.getModuleName(), myH2StoreImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
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

	private Activity createActivity(Node node){
		String moduleName = node.getAttributes().getNamedItem("moduleName").getNodeValue();
		int length = 0;
		int intensity = 0;
		try{
			length = Integer.parseInt(node.getAttributes().getNamedItem("length").getNodeValue());
			intensity = Integer.parseInt(node.getAttributes().getNamedItem("intensity").getNodeValue());
		}
		catch (NumberFormatException e){
			System.out.println("Had problems parsing a float...");
			e.printStackTrace();
		}
		ActivityImpl newActivityImpl = new ActivityImpl(moduleName, length, intensity);
		return ActivityHelper.narrow(OrbUtils.poaToCorbaObj(newActivityImpl));
	}

	private Schedule createSchedule(Node node){
		Schedule newSchedule = new Schedule();
		Node child = node.getFirstChild();
		for (int i = 0; child != null; i++){
			newSchedule.insertActivityInSchedule(createActivity(child), i);
			child = child.getNextSibling();
		}
		return newSchedule;
	}

	private void createCrewPerson(Node node, CrewGroupImpl crew){
		Schedule schedule = createSchedule(node.getFirstChild());
		String moduleName = node.getAttributes().getNamedItem("moduleName").getNodeValue();
		Sex sex;
		if (node.getAttributes().getNamedItem("moduleName").getNodeValue().equals("FEMALE"))
			sex = Sex.female;
		else
			sex = Sex.male;
		float age = 0f;
		float weight = 0f;
		try{
			age = Float.parseFloat(node.getAttributes().getNamedItem("age").getNodeValue());
			weight = Float.parseFloat(node.getAttributes().getNamedItem("weight").getNodeValue());
		}
		catch (NumberFormatException e){
			System.out.println("Had problems parsing a float...");
			e.printStackTrace();
		}
		crew.createCrewPerson(moduleName, age, weight, sex, schedule);
	}

	private void createCrewGroup(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			System.out.println("Creating CrewGroup with moduleName: "+moduleName);
			CrewGroupImpl myCrewGroupImpl = new CrewGroupImpl(myID, moduleName);
			BiosimServer.registerServer(new CrewGroupPOATie(myCrewGroupImpl), myCrewGroupImpl.getModuleName(), myCrewGroupImpl.getID());
			Node child = node.getFirstChild();
			while (child != null) {
				if (child.getNodeName().equals("CrewPerson"))
					createCrewPerson(child, myCrewGroupImpl);
				child = child.getNextSibling();
			}
		}
		else
			printRemoteWarningMessage(moduleName);
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
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			System.out.println("Creating SimEnvironment with moduleName: "+moduleName);
			SimEnvironmentImpl mySimEnvironmentImpl = null;
			float CO2Moles = 0f;
			float O2Moles = 0f;
			float waterMoles = 0f;
			float otherMoles = 0f;
			float volume = 0f;
			Node CO2MolesNode = null;
			Node O2MolesNode = null;
			Node waterMolesNode = null;
			Node otherMolesNode = null;
			try{
				volume = Float.parseFloat(node.getAttributes().getNamedItem("initialVolume").getNodeValue());
				CO2MolesNode = node.getAttributes().getNamedItem("initialCO2Moles");
				O2MolesNode = node.getAttributes().getNamedItem("initialO2Moles");
				waterMolesNode = node.getAttributes().getNamedItem("initialWaterMoles");
				otherMolesNode = node.getAttributes().getNamedItem("initialOtherMoles");
				if (CO2MolesNode != null)
					CO2Moles = Float.parseFloat(CO2MolesNode.getNodeValue());
				if (O2MolesNode != null)
					O2Moles = Float.parseFloat(O2MolesNode.getNodeValue());
				if (waterMolesNode != null)
					waterMoles = Float.parseFloat(waterMolesNode.getNodeValue());
				if (otherMolesNode != null)
					otherMoles = Float.parseFloat(otherMolesNode.getNodeValue());
			}
			catch (NumberFormatException e){
				System.out.println("Had problems parsing a float...");
				e.printStackTrace();
			}
			if ((CO2MolesNode != null) || (O2MolesNode != null) || (waterMolesNode != null) || (otherMolesNode != null))
				mySimEnvironmentImpl = new SimEnvironmentImpl(CO2Moles, O2Moles, otherMoles, waterMoles, volume, moduleName, myID);
			else
				mySimEnvironmentImpl = new SimEnvironmentImpl(myID, volume, moduleName);
			BiosimServer.registerServer(new SimEnvironmentPOATie(mySimEnvironmentImpl), mySimEnvironmentImpl.getModuleName(), mySimEnvironmentImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
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

	private void createAccumulator(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			System.out.println("Creating Accumulator with moduleName: "+moduleName);
			AccumulatorImpl myAccumulatorImpl = new AccumulatorImpl(myID, moduleName);
			BiosimServer.registerServer(new AccumulatorPOATie(myAccumulatorImpl), myAccumulatorImpl.getModuleName(), myAccumulatorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureAccumulator(Node node){
		System.out.println("Configuring Accumulator");
	}

	private void createInjector(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			System.out.println("Creating Injector with moduleName: "+moduleName);
			InjectorImpl myInjectorImpl = new InjectorImpl(myID, moduleName);
			BiosimServer.registerServer(new InjectorPOATie(myInjectorImpl), myInjectorImpl.getModuleName(), myInjectorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
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
					createAccumulator(child);
				else
					configureAccumulator(child);
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
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			System.out.println("Creating BiomassRS with moduleName: "+moduleName);
			BiomassRSImpl myBiomassRSImpl = new BiomassRSImpl(myID, moduleName);
			BiosimServer.registerServer(new BiomassRSPOATie(myBiomassRSImpl), myBiomassRSImpl.getModuleName(), myBiomassRSImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureBiomassRS(Node node){
		System.out.println("Configuring BiomassRS");
	}

	private void createFoodProcessor(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			System.out.println("Creating FoodProcessor with moduleName: "+moduleName);
			FoodProcessorImpl myFoodProcessorImpl = new FoodProcessorImpl(myID, moduleName);
			BiosimServer.registerServer(new FoodProcessorPOATie(myFoodProcessorImpl), myFoodProcessorImpl.getModuleName(), myFoodProcessorImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureFoodProcessor(Node node){
		System.out.println("Configuring FoodProcessor");
	}

	private void createBiomassStore(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			System.out.println("Creating BiomassStore with moduleName: "+moduleName);
			BiomassStoreImpl myBiomassStoreImpl = new BiomassStoreImpl(myID, moduleName);
			myBiomassStoreImpl.setLevel(getStoreLevel(node));
			myBiomassStoreImpl.setCapacity(getStoreCapacity(node));
			BiosimServer.registerServer(new BiomassStorePOATie(myBiomassStoreImpl), myBiomassStoreImpl.getModuleName(), myBiomassStoreImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void createFoodStore(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			System.out.println("Creating FoodStore with moduleName: "+moduleName);
			FoodStoreImpl myFoodStoreImpl = new FoodStoreImpl(myID, moduleName);
			myFoodStoreImpl.setLevel(getStoreLevel(node));
			myFoodStoreImpl.setCapacity(getStoreCapacity(node));
			BiosimServer.registerServer(new FoodStorePOATie(myFoodStoreImpl), myFoodStoreImpl.getModuleName(), myFoodStoreImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
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
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			System.out.println("Creating PowerPS with moduleName: "+moduleName);
			PowerPSImpl myPowerPSImpl = null;
			if (node.getAttributes().getNamedItem("generation").getNodeValue().equals("SOLAR"))
				myPowerPSImpl = new SolarPowerPS(myID, moduleName);
			else
				myPowerPSImpl = new NuclearPowerPS(myID, moduleName);
			BiosimServer.registerServer(new PowerPSPOATie(myPowerPSImpl), myPowerPSImpl.getModuleName(), myPowerPSImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configurePowerPS(Node node){
		System.out.println("Configuring PowerPS");
	}

	private void createPowerStore(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			System.out.println("Creating PowerStore with moduleName: "+moduleName);
			PowerStoreImpl myPowerStoreImpl = new PowerStoreImpl(myID, moduleName);
			myPowerStoreImpl.setLevel(getStoreLevel(node));
			myPowerStoreImpl.setCapacity(getStoreCapacity(node));
			BiosimServer.registerServer(new PowerStorePOATie(myPowerStoreImpl), myPowerStoreImpl.getModuleName(), myPowerStoreImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
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
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			System.out.println("Creating WaterRS with moduleName: "+moduleName);
			WaterRSImpl myWaterRSImpl = new WaterRSImpl(myID, moduleName);
			BiosimServer.registerServer(new WaterRSPOATie(myWaterRSImpl), myWaterRSImpl.getModuleName(), myWaterRSImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void configureWaterRS(Node node){
		System.out.println("Configuring WaterRS");
	}

	private void createPotableWaterStore(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			System.out.println("Creating PotableWaterStore with moduleName: "+moduleName);
			PotableWaterStoreImpl myPotableWaterStoreImpl = new PotableWaterStoreImpl(myID, moduleName);
			myPotableWaterStoreImpl.setLevel(getStoreLevel(node));
			myPotableWaterStoreImpl.setCapacity(getStoreCapacity(node));
			BiosimServer.registerServer(new PotableWaterStorePOATie(myPotableWaterStoreImpl), myPotableWaterStoreImpl.getModuleName(), myPotableWaterStoreImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void createDirtyWaterStore(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			System.out.println("Creating DirtyWaterStore with moduleName: "+moduleName);
			DirtyWaterStoreImpl myDirtyWaterStoreImpl = new DirtyWaterStoreImpl(myID, moduleName);
			myDirtyWaterStoreImpl.setLevel(getStoreLevel(node));
			myDirtyWaterStoreImpl.setCapacity(getStoreCapacity(node));
			BiosimServer.registerServer(new DirtyWaterStorePOATie(myDirtyWaterStoreImpl), myDirtyWaterStoreImpl.getModuleName(), myDirtyWaterStoreImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
	}

	private void createGreyWaterStore(Node node){
		String moduleName = getModuleName(node);
		if (isCreatedLocally(node)){
			System.out.println("Creating GreyWaterStore with moduleName: "+moduleName);
			GreyWaterStoreImpl myGreyWaterStoreImpl = new GreyWaterStoreImpl(myID, moduleName);
			myGreyWaterStoreImpl.setLevel(getStoreLevel(node));
			myGreyWaterStoreImpl.setCapacity(getStoreCapacity(node));
			BiosimServer.registerServer(new GreyWaterStorePOATie(myGreyWaterStoreImpl), myGreyWaterStoreImpl.getModuleName(), myGreyWaterStoreImpl.getID());
		}
		else
			printRemoteWarningMessage(moduleName);
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

	public void parseFile(String fileToParse){
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
}
